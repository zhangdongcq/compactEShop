package dong.compactEShop.validator;

import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

public class ValidationResult {
    //General assertion
    private boolean hasErrors = false;

    //Error Map
    private Map<String, String> errorMsgMap = new HashMap<>();

    public Map<String, String> getErrorMsgMap() {
        return errorMsgMap;
    }

    public void setErrorMsgMap(Map<String, String> errorMsgMap) {
        this.errorMsgMap = errorMsgMap;
    }

    public boolean isHasErrors() {
        return hasErrors;
    }

    public void setHasErrors(boolean hasErrors) {
        this.hasErrors = hasErrors;
    }


    public String getErrMsg(){
        return StringUtils.join(errorMsgMap.values().toArray(),",");
    }
}
