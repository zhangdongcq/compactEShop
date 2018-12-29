package dong.compactEShop.service.impl;

import dong.compactEShop.dao.UserDOMapper;
import dong.compactEShop.dao.UserPasswordDOMapper;
import dong.compactEShop.dataobject.UserDO;
import dong.compactEShop.dataobject.UserPasswordDO;
import dong.compactEShop.error.BusinessException;
import dong.compactEShop.error.EmBusinessError;
import dong.compactEShop.service.UserService;
import dong.compactEShop.service.model.UserModel;
import dong.compactEShop.validator.ValidationResult;
import dong.compactEShop.validator.ValidatorImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDOMapper userDOMapper;
    @Autowired
    private UserPasswordDOMapper userPasswordDOMapper;
    @Autowired
    private ValidatorImpl validator;

    @Override
    public UserModel getUserById(Integer id) {
        //Invoking userDOMapper to get dataobject based on given id
        UserDO userDO = userDOMapper.selectByPrimaryKey(id);
        if (userDO == null) return null;
        //Getting encrypted password by user id
        UserPasswordDO userPasswordDO = userPasswordDOMapper.selectByUserId(userDO.getId());


        UserModel userModel = convertFromDataObject(userDO, userPasswordDO);
        return userModel;
    }

    @Override
    @Transactional
    public void register(UserModel userModel) throws BusinessException {
        if (userModel == null) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }

        //Validation of fields of user model.
        ValidationResult validationResult = validator.validate(userModel);
        if (validationResult.isHasErrors()) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, validationResult.getErrMsg());
        }

        //Convert model -> dataobject
        UserDO userDO = convertFromModel(userModel);
        try {
            userDOMapper.insertSelective(userDO);
        } catch (DuplicateKeyException ex) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, "The user with same phone number already exist.");
        }
        userModel.setId(userDO.getId());
        UserPasswordDO userPasswordDO = convertPasswordFromModel(userModel);
        userPasswordDOMapper.insertSelective(userPasswordDO);
        return;
    }


    @Override
    public UserModel validateLogin(String telphone, String encrptPassword) throws BusinessException {
        //Get user detail against telphone number
        UserDO userDo = userDOMapper.selectByTelphone(telphone);
        if (userDo == null) {
            throw new BusinessException(EmBusinessError.USER_LOGIN_FAIL);
        }
        UserPasswordDO userPasswordDO = userPasswordDOMapper.selectByUserId(userDo.getId());
        UserModel userModel = convertFromDataObject(userDo, userPasswordDO);
        //Validate the password against that in db
        if (com.alibaba.druid.util.StringUtils.equals(userModel.getEncrptPassword(), encrptPassword)) {
            throw new BusinessException(EmBusinessError.USER_LOGIN_FAIL);
        }
        return userModel;
    }

    private UserPasswordDO convertPasswordFromModel(UserModel userModel) {
        if (userModel == null) return null;
        UserPasswordDO userPasswordDO = new UserPasswordDO();
        userPasswordDO.setEncrptPassword(userModel.getEncrptPassword());
        userPasswordDO.setUserId(userModel.getId());
        return userPasswordDO;
    }

    private UserDO convertFromModel(UserModel userModel) {
        if (userModel == null) return null;
        UserDO userDO = new UserDO();
        BeanUtils.copyProperties(userModel, userDO);
        return userDO;
    }

    private UserModel convertFromDataObject(UserDO userDO, UserPasswordDO userPasswordDO) {
        if (userDO == null) {
            return null;
        }
        UserModel userModel = new UserModel();
        BeanUtils.copyProperties(userDO, userModel);
        if (userPasswordDO != null) {
            userModel.setEncrptPassword(userPasswordDO.getEncrptPassword());
        }
        return userModel;
    }
}
