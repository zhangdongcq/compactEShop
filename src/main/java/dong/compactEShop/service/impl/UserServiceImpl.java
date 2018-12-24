package dong.compactEShop.service.impl;

import dong.compactEShop.dao.UserDOMapper;
import dong.compactEShop.dao.UserPasswordDOMapper;
import dong.compactEShop.dataobject.UserDO;
import dong.compactEShop.dataobject.UserPasswordDO;
import dong.compactEShop.service.UserService;
import dong.compactEShop.service.model.UserModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDOMapper userDOMapper;
    @Autowired
    private UserPasswordDOMapper userPasswordDOMapper;

    @Override
    public UserModel getUserById(Integer id) {
        //Invoking userDOMapper to get dataobject based on given id
        UserDO userDO = userDOMapper.selectByPrimaryKey(1);
        if(userDO == null) return null;
        //Getting encrypted password by user id
        UserPasswordDO userPasswordDO = userPasswordDOMapper.selectByUserId(userDO.getId());


        UserModel userModel = convertFromDataObject(userDO, userPasswordDO);
        return userModel;
    }

    private UserModel convertFromDataObject(UserDO userDO, UserPasswordDO userPasswordDO){
        if(userDO == null){
            return null;
        }
        UserModel userModel = new UserModel();
        BeanUtils.copyProperties(userDO, userModel);
        if(userPasswordDO != null){
            userModel.setEncrptPassword(userPasswordDO.getEncrptPassword());
        }
        return userModel;
    }
}
