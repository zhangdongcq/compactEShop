package dong.compactEShop.controller;

import dong.compactEShop.error.BusinessException;
import dong.compactEShop.error.EmBusinessError;
import dong.compactEShop.response.CommonReturnType;
import dong.compactEShop.service.ItemService;
import dong.compactEShop.service.OrderService;
import dong.compactEShop.service.model.OrderModel;
import dong.compactEShop.service.model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller("/order")
@RequestMapping("/order")
@CrossOrigin(allowCredentials = "true", allowedHeaders = "*")
public class OrderController extends BaseController{

    @Autowired
    private OrderService orderService;
    @Autowired
    private HttpServletRequest httpServletRequest;
    @Autowired
    private ItemService itemService;

    //Binding order transaction
    @RequestMapping(value = "/createorder", method = {RequestMethod.POST}, consumes = {CONTENT_TYPE_FORMED})
    @ResponseBody
    public CommonReturnType createOrder(@RequestParam(name = "itemId")Integer itemId,
                                        @RequestParam(name = "amount")Integer amount,
                                        @RequestParam(name = "promoId", required = false)Integer promoId
                                        ) throws BusinessException {
        Boolean isLogin = (Boolean) httpServletRequest.getSession().getAttribute("IS_LOGIN");
        if(isLogin == null || !isLogin.booleanValue()){
            throw new BusinessException(EmBusinessError.USER_NOT_LOGIN);
        }
        UserModel userModel = (UserModel) httpServletRequest.getSession().getAttribute("LOG_USER");
        OrderModel orderModel = orderService.createOrder(userModel.getId(), itemId, promoId, amount);
        return CommonReturnType.create(null);
    }
}
