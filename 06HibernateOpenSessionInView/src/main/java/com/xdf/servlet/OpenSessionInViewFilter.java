package com.xdf.servlet;

import com.xdf.util.SessionFactoryUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

@WebFilter("/*")
public class OpenSessionInViewFilter  implements Filter {
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        Session session= SessionFactoryUtil.getCurrentSession();  //获取session
        Transaction transaction= session.beginTransaction();  //开启事务
        System.out.println("过滤器中的======》"+session.hashCode());
        filterChain.doFilter(servletRequest,servletResponse);
    }

    public void destroy() {

    }
}
