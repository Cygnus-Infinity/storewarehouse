package fun.smapp.securelogin.validator.auth;

import fun.smapp.securelogin.model.auth.SystemUser;
import fun.smapp.securelogin.service.utility.UserUtilityService;
import org.apache.commons.validator.EmailValidator;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class UserValidator implements Validator {

    private UserUtilityService userUtilityService;
    private EmailValidator emailValidator;

    /**
     * This Constructor to use Dependency Injection UserUtilityService
     * @param userUtilityService
     * @param emailValidator
     */
    public UserValidator(UserUtilityService userUtilityService, EmailValidator emailValidator) {
        this.userUtilityService = userUtilityService;
        this.emailValidator = emailValidator;
    }


    @Override
    public boolean supports(Class<?> aClass) {
        return SystemUser.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        SystemUser user = (SystemUser) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "userName", "NotEmpty");
        if (user.getUserName().length() < 6 || user.getUserName().length() > 50){
            errors.rejectValue("userName", "Size.userForm.username");
        }

        if (!emailValidator.isValid(user.getUserName())) {
            errors.rejectValue("userName", "Email.userForm.notValid");
        }

        if(userUtilityService.findByUserName(user.getUserName()) != null) {
            errors.rejectValue("userName", "Duplicate.userForm.username");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "encryptedPassword", "NotEmpty");
        if (user.getEncryptedPassword().length() < 8) {
            errors.rejectValue("encryptedPassword", "Size.userForm.password");
        }

        if(!user.getEncryptedPassword().equals(user.getConfirmPassword())) {
            errors.rejectValue("encryptedPassword", "Diff.userForm.passwordConfirm");
        }
    }
}
