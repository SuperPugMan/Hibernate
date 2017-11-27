package com.xdf.servlet;

import com.xdf.bean.District;
import com.xdf.dao.DistrictDao;
import com.xdf.dao.DistrictDaoImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/findById")
public class DistrictServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        DistrictDao dao=new DistrictDaoImpl();  //实例化service对象
        District district=dao.getDistrictById(3);
        req.setAttribute("d",district);
        req.getRequestDispatcher("index.jsp").forward(req,resp);
    }
}
