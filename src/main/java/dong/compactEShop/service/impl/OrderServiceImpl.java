package dong.compactEShop.service.impl;

import dong.compactEShop.dao.OrderDOMapper;
import dong.compactEShop.dao.SequenceDOMapper;
import dong.compactEShop.dataobject.OrderDO;
import dong.compactEShop.dataobject.SequenceDO;
import dong.compactEShop.error.BusinessException;
import dong.compactEShop.error.EmBusinessError;
import dong.compactEShop.service.ItemService;
import dong.compactEShop.service.OrderService;
import dong.compactEShop.service.UserService;
import dong.compactEShop.service.model.ItemModel;
import dong.compactEShop.service.model.OrderModel;
import dong.compactEShop.service.model.UserModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private SequenceDOMapper sequenceDOMapper;

    @Autowired
    private ItemService itemService;
    @Autowired
    private UserService userService;
    @Autowired
    private OrderDOMapper orderDOMapper;

    @Override
    @Transactional
    public OrderModel createOrder(Integer userId, Integer itemId, Integer amount) throws BusinessException {

        //1: Validation -> Item exist? Legal User? Valid order amount?
        ItemModel itemModel = itemService.getItemById(itemId);
        if(itemModel == null) {
            throw new BusinessException(EmBusinessError.ITEM_NOT_EXIST);
        }
        UserModel userModel = userService.getUserById(userId);
        if(userModel == null){
            throw new BusinessException(EmBusinessError.USER_NOT_EXIST);
        }
        if(amount <=0 || amount > 99){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, "The amount you put in order is invalid.");
        }

        //2: Reduce stock once order is made
        boolean result = itemService.decreaseStock(itemId, amount);
        if(!result){
            throw new BusinessException(EmBusinessError.STOCK_NO_ENOUGH);
        }

        //3: Persistence Order info
        OrderModel orderModel = new OrderModel();
        orderModel.setUserId(userId);
        orderModel.setAmount(amount);
        orderModel.setItemId(itemId);
        orderModel.setItemPrice(itemModel.getPrice());
        orderModel.setOrderPrice(itemModel.getPrice().multiply(new BigDecimal(amount)));

        //Convert Order data object from order model
        orderModel.setId(generateOrderNo());
        OrderDO orderDO = this.convertFromOrderModel(orderModel);
        orderDOMapper.insertSelective(orderDO);
        //Increase sales record
        itemService.increaseSales(itemId, amount);
        //4: Return result to front end
        return orderModel;
    }


    /**
     * First 8 digits indicate date
     * Middle 6 digits are auto incremented sequence
     * Last 2 digits are sub-db and sub-table index
     * The method is called by createOrder service which is a transactional one,
     * in order to ensure the unity of order number, this method should be out of transaction.
     * @return 16 digits order number
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    String generateOrderNo(){

        StringBuilder stringBuilder = new StringBuilder();
        LocalDateTime localDateTime = LocalDateTime.now();
        String nowDate = localDateTime.format(DateTimeFormatter.ISO_DATE).replace("-","");
        stringBuilder.append(nowDate);

        //Get current sequence
        int sequence = 0;
        SequenceDO sequenceDO = sequenceDOMapper.getSequenceByName("order_info");
        sequence = sequenceDO.getCurrentValue();

        //Set new value
        sequenceDO.setCurrentValue(sequenceDO.getCurrentValue()+sequenceDO.getStep());

        //Update sequenceDO which has a new value
        sequenceDOMapper.updateByPrimaryKeySelective(sequenceDO);
        //Append sequence number to be 6 digits
        String sequenceStr = String.valueOf(sequence);
        for (int i = 0; i < 6-sequenceStr.length(); i++) {
            stringBuilder.append("0");
        }
        stringBuilder.append(sequenceStr);

        //hardcode sub db and sub table numbers
        stringBuilder.append("00");
        return stringBuilder.toString();
    }


    private OrderDO convertFromOrderModel(OrderModel orderModel){
        if(orderModel == null) return null;
        OrderDO orderDO = new OrderDO();
        BeanUtils.copyProperties(orderModel, orderDO);
        orderDO.setItemPrice(orderModel.getItemPrice().doubleValue());
        orderDO.setOrderPrice(orderModel.getOrderPrice().doubleValue());

        return orderDO;
    }
}
