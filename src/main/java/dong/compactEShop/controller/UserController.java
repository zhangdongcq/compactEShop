package dong.compactEShop.controller;

import dong.compactEShop.controller.userobject.UserVO;
import dong.compactEShop.response.CommonReturnType;
import dong.compactEShop.service.UserService;
import dong.compactEShop.service.model.UserModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller("user")
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/get")
    @ResponseBody
    public CommonReturnType getUser(@RequestParam(name="id")Integer id){
        //Invoking service to get domain object
        UserModel userModel = userService.getUserById(id);
        //Converts domain object to frontend user object
        UserVO userVO = convertFromModel(userModel);
        //Return generic object
        return CommonReturnType.create(userVO);
    }

    private UserVO convertFromModel(UserModel userModel){
        if(userModel == null) return null;
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(userModel, userVO);
        return userVO;
    }
}
