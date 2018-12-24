package dong.compactEShop.response;

import java.net.CookieHandler;

public class CommonReturnType {
    //Indicating the process result : success / fail
    private String status;

    //If status is success, data returns good response in json
    //If status is fail, generic response returns
    private Object data;

    //Define a generic creating method
    public static CommonReturnType create(Object result){
        return CommonReturnType.create(result, "success");
    }

    public static CommonReturnType create(Object result, String status){
        CommonReturnType type = new CommonReturnType();
        type.setStatus(status);
        type.setData(result);
        return type;
    }



    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
