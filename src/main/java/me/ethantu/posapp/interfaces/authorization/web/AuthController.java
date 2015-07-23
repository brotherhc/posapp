package me.ethantu.posapp.interfaces.authorization.web;

import com.octo.captcha.service.image.ImageCaptchaService;
import me.ethantu.posapp.commons.shiro.VerificationCodeException;
import me.ethantu.posapp.domain.model.user.User;
import me.ethantu.posapp.domain.service.user.IUserService;
import me.ethantu.posapp.interfaces.shared.web.AlertCommand;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.session.SessionException;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.Locale;

/**
 * Author: EthanTu <ethan.l.tu@gmail.com>
 * Date: 13-10-31
 * Time: PM2:15
 */
@Controller
@RequestMapping("/")
public class AuthController {

    @Autowired
    private ImageCaptchaService imageCaptchaService;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private IUserService userService;

    @RequestMapping(value = "/verificationCode",headers = "Accept=image/jpeg, image/jpg, image/png, image/gif")
    public @ResponseBody
    byte[] verificationCode(HttpServletRequest request) throws Exception {
        String captchaId = request.getRequestedSessionId();
        BufferedImage challenge = (BufferedImage)imageCaptchaService.getChallengeForID(captchaId, request.getLocale());
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ImageIO.write(challenge, "jpg", byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    @RequestMapping
    public String index() throws Exception {
        return "redirect:/login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView login(AlertCommand alertCommand){
        return new ModelAndView("/authorization/login", "alertCommand", alertCommand);
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ModelAndView login(@ModelAttribute LoginFailedCommand loginFailedCommand, RedirectAttributes redirectAttributes,
                              HttpServletRequest request, HttpSession session,
                              Locale locale) throws Exception {

        //VerificationCodeValidateFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME and LoginAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME
        String exceptionClassName = (String) request.getAttribute("shiroLoginFailure");


        String alertContent = null;
        if (StringUtils.isEmpty(exceptionClassName)) {
            Subject subject = SecurityUtils.getSubject();

            String username = (String) subject.getPrincipal();
            if (subject.hasRole("administrator") || subject.hasRole("user")) {
                return new ModelAndView("redirect:/dashboard");
            } else {

                alertContent = messageSource.getMessage("LoginFailedCommand.incorrectCredential.error.message",
                        null, locale);
                redirectAttributes.addFlashAttribute("alertCommand", new AlertCommand(AlertCommand.AlertType.DANGER, alertContent));
                redirectAttributes.addFlashAttribute("loginFailedCommand", loginFailedCommand);
            }

            //try/catch added for SHIRO-298:
            try {
                subject.logout();
            } catch (SessionException ise) {
                //Encountered session exception during logout.  This can generally safely be ignored.
            }

            return new ModelAndView("redirect:/");
        } else {
            if(UnknownAccountException.class.getName().equals(exceptionClassName)) {
              alertContent = messageSource.getMessage("LoginFailedCommand.incorrectCredential.error.message",
                      null, locale);
            } else if(IncorrectCredentialsException.class.getName().equals(exceptionClassName)) {
              alertContent = messageSource.getMessage("LoginFailedCommand.incorrectCredential.error.message",
                      null, locale);
            } else if(VerificationCodeException.class.getName().equals(exceptionClassName)) {
              alertContent = messageSource.getMessage("LoginFailedCommand.verificationCode.error.message",
                      null, locale);
            } else if(exceptionClassName != null) {
              alertContent = messageSource.getMessage("LoginFailedCommand.illegalOperation.error.message",
                      null, locale);
            }

            redirectAttributes.addFlashAttribute("alertCommand", new AlertCommand(AlertCommand.AlertType.DANGER, alertContent));
            redirectAttributes.addFlashAttribute("loginFailedCommand", loginFailedCommand);
            return new ModelAndView("redirect:/");
        }
    }

    @RequestMapping(value = "/unauthorized", method = RequestMethod.GET)
    public String unauthorized(){
        return "/authorization/unauthorized";
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(){
        Subject subject = SecurityUtils.getSubject();

        if (null != subject) {
            String username = (String) subject.getPrincipal();
            //try/catch added for SHIRO-298:
            try {
                subject.logout();
            } catch (SessionException ise) {
                //Encountered session exception during logout.  This can generally safely be ignored.
                return "redirect:/login";
            }

            User user = userService.findByUsername(username);
            if (null != user) {
                user.setLastSignedDate(user.getSignedDate());
                userService.edit(user, false);
            }
        }
        return "redirect:/";
    }

}
