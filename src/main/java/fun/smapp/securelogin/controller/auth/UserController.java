package fun.smapp.securelogin.controller.auth;

import fun.smapp.securelogin.model.auth.SystemUser;
import fun.smapp.securelogin.model.utility.Activation;
import fun.smapp.securelogin.repository.utility.ActivationRepository;
import fun.smapp.securelogin.service.security.SecurityService;
import fun.smapp.securelogin.service.utility.Message;
import fun.smapp.securelogin.service.utility.Notification;
import fun.smapp.securelogin.service.utility.UserUtilityService;
import fun.smapp.securelogin.validator.auth.UserValidator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.time.LocalDateTime;
import java.util.Base64;
import java.util.UUID;

@Controller
public class UserController {

    private UserUtilityService userUtilityService;
    private SecurityService securityService;
    private UserValidator userValidator;
    private ActivationRepository activationRepository;
    private Message message;
    private Notification emailNotification;

    @Value("${account.activation.duration}")
    private int accountActivationTimeFrame;

    /**
     * Inject all the Required Things
     * @param userUtilityService
     * @param securityService
     * @param userValidator
     * @param activationRepository
     * @param message
     * @param emailNotification
     */
    public UserController(UserUtilityService userUtilityService, SecurityService securityService, UserValidator userValidator, ActivationRepository activationRepository, Message message, Notification emailNotification) {
        this.userUtilityService = userUtilityService;
        this.securityService = securityService;
        this.userValidator = userValidator;
        this.activationRepository = activationRepository;
        this.message = message;
        this.emailNotification = emailNotification;
    }

    @RequestMapping(path = "/registration", method = RequestMethod.GET)
    public String userRegistration(Model model) {
        if(this.isUserLoggedIn()) return "redirect:/dashboard";
        model.addAttribute("userForm", new SystemUser());
        return "auth/registration";
    }

    @RequestMapping( path = "/login", method = RequestMethod.GET)
    public String userLogin(Model model, String error, String logout, @ModelAttribute("flashAttribute") Object flashAttribute, @ModelAttribute("success") Object successAttribute) {
        if(this.isUserLoggedIn()) return "redirect:/dashboard";

        if (flashAttribute != null){
            model.addAttribute("redirection", true);
            model.addAttribute("success", successAttribute);
            model.addAttribute("redirectionMessage", flashAttribute);
        }

        if(error != null) {
            model.addAttribute("error", "Your username and password is invalid.");
        }

        if(logout != null) {
            model.addAttribute("message", "You have been logged out successfully.");
        }

        model.addAttribute("userForm", new SystemUser());
        return "auth/login";
    }

    @RequestMapping(path = "/registration", method = RequestMethod.POST)
    public String userPostRegistration(@ModelAttribute("userForm") SystemUser userForm, BindingResult bindingResult, Model model){

         try {
            userValidator.validate(userForm, bindingResult);
            if(bindingResult.hasErrors()) {
                return "auth/registration";
            }

            String activationCode = UUID.randomUUID().toString();
            userForm.getActivation()
                    .setActivationToken(activationCode.replace("-", ""))
                    .setValidUpto(LocalDateTime.now().plusMinutes(this.accountActivationTimeFrame))
                    .setIssueDate(LocalDateTime.now());

            userUtilityService.save(userForm);
            String activationLink = String.format(message.get("activation.url"), Base64.getEncoder().encodeToString(activationCode.getBytes()));
            String activationEmailContent = String.format(message.get("activation.email.template"), activationLink, activationLink );
            this.emailNotification.send(userForm.getUserName(), "", activationEmailContent, message.get("activation.email.subject"));

            model.addAttribute("success", true);
            model.addAttribute("message", message.get("registration.success"));
        }
        catch (Exception ex) {

            model.addAttribute("success", false);
            model.addAttribute("message", message.get("registration.failed"));
        }

        /**
         * To enable autologin after registration discomment the below lines
         */
        // securityService.autoLogin(userForm.getUserName(), userForm.getConfirmPassword());

        return "auth/registration";
    }

    private Boolean isUserLoggedIn() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken)){
            return true;
        }

        return false;
    }

    @RequestMapping(path = "/account/activate", method = RequestMethod.GET)
    private RedirectView activateUserAccount(@RequestParam String token, RedirectAttributes attributes) {

        byte[] decodedBytes = Base64.getDecoder().decode(token);
        String decodedString = new String(decodedBytes).replace("-", "");

        Activation activation = activationRepository.findByActivationToken(decodedString);

        if(activation == null) {
            attributes.addFlashAttribute("success", false);
            attributes.addFlashAttribute("flashAttribute", message.get("activation.notFound"));
            return new RedirectView("/login");
        }

        /**
         * Check activation time greater then validUpto time or not
         */
        if (activation.getValidUpto().isBefore(LocalDateTime.now()) && activation.getConfirmDate() == null) {
            attributes.addFlashAttribute("success", false);
            attributes.addFlashAttribute("flashAttribute", message.get("activation.notFound"));
        }
        else {
            activation.getSystemUser().setActive(true);
            userUtilityService.save(activation.getSystemUser());
            activation.setConfirmDate(LocalDateTime.now());
            activationRepository.save(activation);

            attributes.addFlashAttribute("success", true);
            attributes.addFlashAttribute("flashAttribute", message.get("activation.success"));
        }

        return new RedirectView("/login");
    }
}
