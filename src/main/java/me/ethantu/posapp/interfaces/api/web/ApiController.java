package me.ethantu.posapp.interfaces.api.web;

import me.ethantu.posapp.commons.shiro.VerificationCodeException;
import me.ethantu.posapp.domain.model.user.User;
import me.ethantu.posapp.domain.model.voucher.Voucher;
import me.ethantu.posapp.domain.service.UnAvailableException;
import me.ethantu.posapp.domain.service.user.IUserService;
import me.ethantu.posapp.domain.service.voucher.IVoucherService;
import me.ethantu.posapp.interfaces.authorization.web.LoginFailedCommand;
import me.ethantu.posapp.interfaces.shared.web.AlertCommand;
import me.ethantu.posapp.interfaces.shared.web.ApiResponse;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Locale;

/**
 * Author: EthanTu <ethan.l.tu@gmail.com>
 * Date: 2015/7/15
 * Time: 1:51
 */
@Controller
@RequestMapping("/api")
public class ApiController {


    @Autowired
    private MessageSource messageSource;

    @Autowired
    private IVoucherService voucherService;

    @Autowired
    private IUserService userService;

    @ResponseBody
    @RequestMapping(value = "/login")
    public ApiResponse login(@ModelAttribute LoginFailedCommand loginFailedCommand, HttpServletRequest request, Locale locale) throws Exception {

        //VerificationCodeValidateFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME and LoginAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME
        String exceptionClassName = (String) request.getAttribute("shiroLoginFailure");


        String alertContent = null;
        ApiResponse response = new ApiResponse();
        if (StringUtils.isEmpty(exceptionClassName)) {
            Subject subject = SecurityUtils.getSubject();

            String username = (String) subject.getPrincipal();
            if (subject.hasRole("user")) {
                response.setCode("1");
                response.setData(username);
                response.setMessage("");

                User user = userService.findByUsername(username);
                user.setSignedDate(new Date());
                return response;
            } else {

                alertContent = messageSource.getMessage("LoginFailedCommand.incorrectCredential.error.message",
                        null, locale);
                response.setCode("0");
                response.setMessage(alertContent);
                //try/catch added for SHIRO-298:
                try {
                    subject.logout();
                } catch (SessionException ise) {
                    //Encountered session exception during logout.  This can generally safely be ignored.
                }
            }

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

            response.setCode("0");
            response.setMessage(alertContent);
        }
        return response;
    }

    @ResponseBody
    @RequestMapping(value = "/voucher")
    public ApiResponse voucher(String mobileNo, String password, String amount) throws Exception {
        ApiResponse apiResponse = new ApiResponse();

        if (StringUtils.isEmpty(mobileNo) || StringUtils.isEmpty(password) || StringUtils.isEmpty(amount)) {
            apiResponse.setCode("0");
            apiResponse.setMessage("提交内容不正确");
            return apiResponse;
        }

        if (mobileNo.length() != 11) {
            apiResponse.setCode("0");
            apiResponse.setMessage("号码不足11位");
            return apiResponse;
        }

        if (password.length() != 6) {
            apiResponse.setCode("0");
            apiResponse.setMessage("密码不对");
            return apiResponse;
        }

        BigDecimal bigDecimal;
        try {
            bigDecimal = new BigDecimal(amount);
        } catch (NumberFormatException e) {
            apiResponse.setCode("0");
            apiResponse.setMessage("金额不对");
            return apiResponse;
        }
        Voucher voucher = new Voucher();
        voucher.setMobileNo(mobileNo);
        voucher.setPassword(password);
        voucher.setAmount(bigDecimal);
        try {
            voucherService.create(voucher);
        } catch (UnAvailableException e) {
            apiResponse.setCode("0");
            apiResponse.setMessage("非法操作");
            return apiResponse;
        }

        apiResponse.setCode("1");
        apiResponse.setMessage("提交成功");
        return apiResponse;
    }

}
