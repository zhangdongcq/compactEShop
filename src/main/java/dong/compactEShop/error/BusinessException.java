package dong.compactEShop.error;

import org.springframework.beans.factory.annotation.Autowired;

public class BusinessException extends Exception implements CommonError {

    private CommonError commonError;

    //Receiving the error param to construct business exception
    public BusinessException(CommonError commonError){
        super();
        this.commonError = commonError;
    }

    //Build business exception with customized error message
    public BusinessException(CommonError commonError, String errMsg){
        super();
        this.commonError = commonError;
        this.commonError.setErrMsg(errMsg);
    }


    @Override
    public int getErrorCode() {
        return this.commonError.getErrorCode();
    }

    @Override
    public String getErrMsg() {
        return this.commonError.getErrMsg();
    }

    @Override
    public CommonError setErrMsg(String errMsg) {
        this.commonError.setErrMsg(errMsg);
        return this;
    }
}
