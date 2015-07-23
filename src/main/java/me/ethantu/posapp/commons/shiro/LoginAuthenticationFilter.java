package me.ethantu.posapp.commons.shiro;

import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * Author: EthanTu <ethan.l.tu@gmail.com>
 * Date: 5/29/14
 * Time: 3:32 AM
 */
public class LoginAuthenticationFilter extends FormAuthenticationFilter {

//    @Override
//       protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
//           return true;
//       }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        return super.onAccessDenied(request, response, mappedValue);
    }

    protected boolean onLoginSuccess(AuthenticationToken token, Subject subject,
                                     ServletRequest request, ServletResponse response) throws Exception {
//        issueSuccessRedirect(request, response);
        //we handled the success redirect directly, prevent the chain from continuing:
        return true;
    }
}

