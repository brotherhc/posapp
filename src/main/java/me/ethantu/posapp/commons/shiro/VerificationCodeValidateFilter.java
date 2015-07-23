package me.ethantu.posapp.commons.shiro;

import com.octo.captcha.service.CaptchaServiceException;
import com.octo.captcha.service.image.ImageCaptchaService;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * Author: EthanTu <ethan.l.tu@gmail.com>
 * Date: 5/29/14
 * Time: 1:19 AM
 */
public class VerificationCodeValidateFilter extends AccessControlFilter {

    public static final String DEFAULT_ERROR_KEY_ATTRIBUTE_NAME = "shiroLoginFailure";

    private String jcaptchaParam = "verificationCode";//前台提交的验证码参数名
    private String failureKeyAttribute = DEFAULT_ERROR_KEY_ATTRIBUTE_NAME; //验证失败后存储到的属性名

    @Autowired
    private ImageCaptchaService imageCaptchaService;

//    public void setFailureKeyAttribute(String failureKeyAttribute) {
//        this.failureKeyAttribute = failureKeyAttribute;
//    }

    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {

//        Subject subject = getSubject(request, response);
//        Session session = subject.getSession(false);
//        if (null == session) {
//            subject.getSession();
//        }

        HttpServletRequest httpServletRequest = WebUtils.toHttp(request);

        if (!"post".equalsIgnoreCase(httpServletRequest.getMethod())) {
            return true;
        }

        boolean flag = false;
        try {
            flag = imageCaptchaService.validateResponseForID(httpServletRequest.getRequestedSessionId(),
                    httpServletRequest.getParameter(jcaptchaParam));
        } catch (CaptchaServiceException e) {
            //ignore
        }
        return flag;
    }


    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {

        request.setAttribute(failureKeyAttribute, VerificationCodeException.class.getName());

        return true;
    }

    public void setFailureKeyAttribute(String failureKeyAttribute) {
        this.failureKeyAttribute = failureKeyAttribute;
    }


    public void setJcaptchaParam(String jcaptchaParam) {
        this.jcaptchaParam = jcaptchaParam;
    }
}