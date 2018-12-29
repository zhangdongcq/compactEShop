package dong.compactEShop.service;


import dong.compactEShop.error.BusinessException;
import dong.compactEShop.service.model.UserModel;

public interface UserService {
    public UserModel getUserById(Integer id);
    void register(UserModel userModel) throws BusinessException;
}
