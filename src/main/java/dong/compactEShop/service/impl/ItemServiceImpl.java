package dong.compactEShop.service.impl;

import dong.compactEShop.dao.ItemDOMapper;
import dong.compactEShop.dao.ItemStockDOMapper;
import dong.compactEShop.dataobject.ItemDO;
import dong.compactEShop.dataobject.ItemStockDO;
import dong.compactEShop.error.BusinessException;
import dong.compactEShop.error.EmBusinessError;
import dong.compactEShop.service.ItemService;
import dong.compactEShop.service.model.ItemModel;
import dong.compactEShop.validator.ValidationResult;
import dong.compactEShop.validator.ValidatorImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Validator;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ValidatorImpl validator;
    @Autowired
    private ItemDOMapper itemDOMapper;
    @Autowired
    private ItemStockDOMapper itemStockDOMapper;

    @Override
    @Transactional

    public ItemModel createItem(ItemModel itemModel) throws BusinessException {
        //Validate incoming params
        ValidationResult validationResult = validator.validate(itemModel);
        if(validationResult.isHasErrors()){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, validationResult.getErrMsg());
        }

        //Convert item model to data object
        ItemDO itemDO = this.convertItemDOFromItemModel(itemModel);

        //Persistence to db and the new item data object will take its id.
        itemDOMapper.insertSelective(itemDO);

        //From domain object to domain model
        itemModel.setId(itemDO.getId());

        //Update item stock table
        ItemStockDO itemStockDO = this.convertItemStockDOFromItemModel(itemModel);
        itemStockDOMapper.insertSelective(itemStockDO);


        //return created item model
        return this.getItemById(itemModel.getId());
    }
    private ItemDO convertItemDOFromItemModel(ItemModel itemModel){
        if(itemModel == null) return null;
        ItemDO itemDO = new ItemDO();
        BeanUtils.copyProperties(itemModel, itemDO);
        itemDO.setPrice(itemModel.getPrice().doubleValue());
        return itemDO;
    }

    //Create ItemStockDO instance from ItemModel
    private ItemStockDO convertItemStockDOFromItemModel(ItemModel itemModel){
        if(itemModel == null) return null;
        ItemStockDO itemStockDO = new ItemStockDO();
        itemStockDO.setItemId(itemModel.getId());
        itemStockDO.setStock(itemModel.getStock());
        return itemStockDO;
    }

    @Override
    public List<ItemModel> listItem() {
        List<ItemDO> itemDOList = itemDOMapper.listItem();

        List<ItemModel> itemModelList = itemDOList.stream().map(itemDO -> {
            ItemStockDO itemStockDO = itemStockDOMapper.selectByItemId(itemDO.getId());
            ItemModel itemModel = this.convertModelFromDataObject(itemDO, itemStockDO);
            return itemModel;
        }).collect(Collectors.toList());
        return itemModelList;
    }

    @Override
    public ItemModel getItemById(Integer id) {
        ItemDO itemDO = itemDOMapper.selectByPrimaryKey(id);
        if(itemDO == null) return null;
        //Get stock info
        ItemStockDO itemStockDO = itemStockDOMapper.selectByItemId(id);

        //Assembly a item model from item data object and item stock data object
        ItemModel itemModel = this.convertModelFromDataObject(itemDO, itemStockDO);
        return itemModel;
    }

    @Override
    @Transactional
    public boolean decreaseStock(Integer itemId, Integer amount) throws BusinessException {
        int affectRow = itemStockDOMapper.decreaseStock(itemId, amount);
        //affectRow == 0, update fails; otherwise success.
        return affectRow!=0;
    }

    @Override
    @Transactional
    public void increaseSales(Integer itemId, Integer amount) throws BusinessException {
        itemDOMapper.increaseSales(itemId, amount);
    }

    //Assembly a item model from item data object and item stock data object
    private ItemModel convertModelFromDataObject(ItemDO itemDO, ItemStockDO itemStockDO){
        if(itemDO == null) return null;
        ItemModel itemModel = new ItemModel();
        BeanUtils.copyProperties(itemDO, itemModel);
        itemModel.setPrice(new BigDecimal(itemDO.getPrice()));
        itemModel.setStock(itemStockDO.getStock());
        return itemModel;
    }
}
