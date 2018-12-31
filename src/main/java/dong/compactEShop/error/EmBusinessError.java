package dong.compactEShop.error;

public enum EmBusinessError implements CommonError{
    //Universal Error Type 10001
    PARAMETER_VALIDATION_ERROR(100001, "Illegal Parameters,"),
    UNKNOWN_ERROR(200002, "Unknown Error,"),
    //Code begin with 10000 indicates user info related errors
    USER_NOT_EXIST(20001, "User does not exist."),
    USER_LOGIN_FAIL(20002, "Wrong phone number or password."),
    USER_NOT_LOGIN(20003, "user is not login."),

    //Code begin with 30000 indicates item infp
    ITEM_NOT_EXIST(30001, "Item does not exist."),
    STOCK_NO_ENOUGH(30002, "No enough stock."),


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
