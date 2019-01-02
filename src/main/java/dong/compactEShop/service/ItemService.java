package dong.compactEShop.service;

import dong.compactEShop.error.BusinessException;
import dong.compactEShop.service.model.ItemModel;

import java.util.List;

public interface ItemService {

    //Create item
    ItemModel createItem(ItemModel itemModel) throws BusinessException;


    //Item list
    List<ItemModel> listItem();

    //Item detail
    ItemModel getItemById(Integer id);


    //Reduce stock
    boolean decreaseStock(Integer itemId, Integer amount) throws BusinessException;

    //Increase the sales
    void increaseSales(Integer itemId, Integer amount) throws BusinessException;

}
