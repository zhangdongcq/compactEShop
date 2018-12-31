package dong.compactEShop.service;

import dong.compactEShop.error.BusinessException;
import dong.compactEShop.service.model.OrderModel;

public interface OrderService {

    OrderModel createOrder(Integer userId, Integer itemId, Integer amount) throws BusinessException;
}
