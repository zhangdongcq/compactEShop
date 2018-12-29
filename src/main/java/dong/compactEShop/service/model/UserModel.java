package dong.compactEShop.service.model;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class UserModel {
    private Integer id;

    @NotBlank(message = "Name could not be blank.")
    private String name;

    @NotNull(message = "Please input gender.")
    private Byte gender;

    @NotNull(message = "Please input age.")
    @Min(value = 0, message = "Age should be greater than 0.")
    @Max(value = 150, message = "Age should not greater than 150.")
    private Integer age;

    @NotBlank(message = "telphone could not be blank.")
    private String telphone;
    private String registerMode;
    private String thirdPartyId;

    @NotBlank(message = "Password could not be blank.")
    private String encrptPassword;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Byte getGender() {
        return gender;
    }

    public void setGender(Byte gender) {
        this.gender = gender;
    }



    public String getTelphone() {
        return telphone;
    }

    public void setTelphone(String telphone) {
        this.telphone = telphone;
    }

    public String getRegisterMode() {
        return registerMode;
    }

    public void setRegisterMode(String registerMode) {
        this.registerMode = registerMode;
    }

    public String getThirdPartyId() {
        return thirdPartyId;
    }

    public void setThirdPartyId(String thirdPartyId) {
        this.thirdPartyId = thirdPartyId;
    }

    public String getEncrptPassword() {
        return encrptPassword;
    }

    public void setEncrptPassword(String encrptPassword) {
        this.encrptPassword = encrptPassword;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}

