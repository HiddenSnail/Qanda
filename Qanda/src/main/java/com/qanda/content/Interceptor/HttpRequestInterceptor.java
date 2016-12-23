package com.qanda.content.Interceptor;

import com.qanda.content.functionKit.EasyCookie;
import com.qanda.content.model.ServerNotice;
import com.qanda.content.service.UserServiceImp;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by huangrui on 2016/12/20.
 */

public class HttpRequestInterceptor extends HandlerInterceptorAdapter {

    private UserServiceImp userServiceImp() {
        return new UserServiceImp();
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        ServerNotice serverNotice = new ServerNotice();
        request.setAttribute("serverNotice", serverNotice);
        Cookie sessionCookie = EasyCookie.getCookieByName(request, "sessionId");
        if (sessionCookie != null) {
            String session = sessionCookie.getValue();
            if (userServiceImp().verifyUserState(session)) {
                serverNotice.active();
                return true;
            }
        }
        serverNotice.inactive();
        return true;
    }

    @Override
    public void postHandle(
            HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)
            throws Exception {
    }
}
