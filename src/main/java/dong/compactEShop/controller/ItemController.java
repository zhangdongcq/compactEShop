package dong.compactEShop.controller;

import dong.compactEShop.controller.BaseController;
import dong.compactEShop.controller.userobject.ItemVO;
import dong.compactEShop.dao.ItemDOMapper;
import dong.compactEShop.error.BusinessException;
import dong.compactEShop.error.EmBusinessError;
import dong.compactEShop.response.CommonReturnType;
import dong.compactEShop.service.impl.ItemServiceImpl;
import dong.compactEShop.service.model.ItemModel;
import dong.compactEShop.service.model.PromoModel;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Controller("/item")
@RequestMapping("/item")
@CrossOrigin(allowCredentials = "true", allowedHeaders = "*")

public class ItemController extends BaseController {

    @Autowired
    private ItemServiceImpl itemService;

    //Create item controller
    @RequestMapping(value = "/create", method = {RequestMethod.POST}, consumes = {CONTENT_TYPE_FORMED})
    @ResponseBody
    public CommonReturnType createItem(
            @RequestParam(name="title")String title,
            @RequestParam(name="price")BigDecimal price,
            @RequestParam(name="stock")Integer stock,
            @RequestParam(name="description")String description,
            @RequestParam(name="imgUrl")String imgUrl
            ) throws BusinessException {
    //Create item with itemService
        ItemModel itemModel = new ItemModel();
        itemModel.setStock(stock);
        itemModel.setTitle(title);
        itemModel.setPrice(price);
        itemModel.setImgUrl(imgUrl);
        itemModel.setDescription(description);
        ItemModel itemModelForReturn = itemService.createItem(itemModel);
        ItemVO itemVO = this.convertItemVOFromItemModel(itemModelForReturn);
        return CommonReturnType.create(itemVO, "success");
    }

    //Item detail
    @RequestMapping(value = "/get", method = {RequestMethod.GET})
    @ResponseBody
    public CommonReturnType getItem(
            @RequestParam(name = "id")Integer id) throws BusinessException {
        ItemModel itemModel = itemService.getItemById(id);
        if(itemModel == null){
            throw new BusinessException(EmBusinessError.ITEM_NOT_EXIST);
        }
        ItemVO itemVO = this.convertItemVOFromItemModel(itemModel);
        return CommonReturnType.create(itemVO, "success");
    }


    private ItemVO convertItemVOFromItemModel(ItemModel itemModel){
        if(itemModel == null) return null;
        ItemVO itemVO = new ItemVO();
        BeanUtils.copyProperties(itemModel, itemVO);

        //The item is in promotion.
        if(itemModel.getPromoModel() != null){
            itemVO.setPromoStatus(itemModel.getPromoModel().getStatus());
            itemVO.setPromoPrice(itemModel.getPromoModel().getPromoItemPrice());
            itemVO.setStartDate(itemModel.getPromoModel().getStartDate().toString(DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")));
            itemVO.setPromoId(itemModel.getPromoModel().getId());
        }else{
            itemVO.setPromoStatus(0);
        }
        return itemVO;
    }


    //Item List
    @RequestMapping(value = "/list", method = {RequestMethod.GET})
    @ResponseBody
    public CommonReturnType listItem() throws BusinessException {
        List<ItemModel> itemModelList = itemService.listItem();
        if(itemModelList.size()==0 || itemModelList == null){
            throw new BusinessException(EmBusinessError.ITEM_NOT_EXIST);
        }

        //Convert itemVO list to itemModel list via stream() API
        List<ItemVO> itemVOList = itemModelList.stream().map(itemModel -> {
            ItemVO itemVO = this.convertItemVOFromItemModel(itemModel);
            return itemVO;
        }).collect(Collectors.toList());

        return CommonReturnType.create(itemVOList, "success");
    }

}
