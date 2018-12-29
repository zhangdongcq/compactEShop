package dong.compactEShop.controller;

import dong.compactEShop.controller.BaseController;
import dong.compactEShop.controller.userobject.ItemVO;
import dong.compactEShop.dao.ItemDOMapper;
import dong.compactEShop.error.BusinessException;
import dong.compactEShop.error.EmBusinessError;
import dong.compactEShop.response.CommonReturnType;
import dong.compactEShop.service.impl.ItemServiceImpl;
import dong.compactEShop.service.model.ItemModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@Controller("/item")
@RequestMapping("/item")
@CrossOrigin
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

    //Item List
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
        return itemVO;
    }

}
