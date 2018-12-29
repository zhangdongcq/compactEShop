package dong.compactEShop.controller;

import dong.compactEShop.controller.BaseController;
import dong.compactEShop.controller.userobject.ItemVO;
import dong.compactEShop.error.BusinessException;
import dong.compactEShop.response.CommonReturnType;
import dong.compactEShop.service.impl.ItemServiceImpl;
import dong.compactEShop.service.model.ItemModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

@Controller("/item")
@RequestMapping("/item")
@CrossOrigin
public class ItemController extends BaseController {

    @Autowired
    private ItemServiceImpl itemService;

    //Create item controller
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
        return CommonReturnType.create(itemVO);
    }

    private ItemVO convertItemVOFromItemModel(ItemModel itemModel){
        if(itemModel == null) return null;
        ItemVO itemVO = new ItemVO();
        BeanUtils.copyProperties(itemModel, itemVO);
        return itemVO;
    }

}
