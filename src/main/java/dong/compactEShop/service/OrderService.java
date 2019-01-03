package dong.compactEShop.service;

import dong.compactEShop.error.BusinessException;
import dong.compactEShop.service.model.OrderModel;

public interface OrderService {

    //Get promoId in URL, validate it in createOrder API

    OrderModel createOrder(Integer userId, Integer itemId, Integer promoId, Integer amount) throws BusinessException;
}
