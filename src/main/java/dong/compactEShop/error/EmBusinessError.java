package dong.compactEShop.error;

public enum EmBusinessError implements CommonError{
    //Universal Error Type 00001
    PARAMETER_VALIDATION_ERROR(100001, "Illegal Parameters,"),
    UNKNOWN_ERROR(100002, "Unknown Error,"),
    //Code starts with 10000 indicates user info related errors
    USER_NOT_EXIST(20001, "User does not exist."),
    USER_LOGIN_FAIL(20002, "Wrong phone number or password.")

    ;

    private EmBusinessError(int errCode, String errMsg){
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    private int errCode;
    private String errMsg;
    @Override
    public int getErrorCode() {
        return this.errCode;
    }

    @Override
    public String getErrMsg() {
        return this.errMsg;
    }

    @Override
    public CommonError setErrMsg(String errMsg) {
        this.errMsg = errMsg;
        return this;
    }
}
