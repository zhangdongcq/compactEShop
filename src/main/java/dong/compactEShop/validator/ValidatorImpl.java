package dong.compactEShop.validator;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;


@Component
public class ValidatorImpl implements InitializingBean {

    private Validator validator;

    //Get validation methods and return validation result
    public ValidationResult validate(Object bean) {
        final ValidationResult validationResult = new ValidationResult();
        Set<ConstraintViolation<Object>> constraintViolations = validator.validate(bean);
        if (constraintViolations.size() > 0) {
            validationResult.setHasErrors(true);
            constraintViolations.forEach(constraintViolation -> {
                String errMsg = constraintViolation.getMessage();
                String propertyName = constraintViolation.getPropertyPath().toString();// Which field produces error.
                validationResult.getErrorMsgMap().put(propertyName, errMsg);
            });
        }
        return validationResult;
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        //Instancilizing the hibernate validator through factory
        this.validator = Validation.buildDefaultValidatorFactory().getValidator();

    }
}
