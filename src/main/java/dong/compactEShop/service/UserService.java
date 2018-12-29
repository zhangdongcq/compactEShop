package dong.compactEShop.service;


import dong.compactEShop.error.BusinessException;
import dong.compactEShop.service.model.UserModel;

public interface UserService {
    public UserModel getUserById(Integer id);
    void register(UserModel userModel) throws BusinessException;

    /**
     *
     * @param telphone : User phone number
     * @param encrptPassword : User password (Encrepted)
     * @throws BusinessException
     * @return UserModel
     */
    UserModel validateLogin(String telphone, String encrptPassword) throws BusinessException;
}
