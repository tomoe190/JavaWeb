package org.example.servlet;

import org.example.dao.ArticleDAO;
import org.example.model.Article;
import org.example.model.JSONResponse;
import org.example.util.JSONUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/articleAdd")
public class ArticleAddServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");

        JSONResponse json = new JSONResponse();

        try{
            // 1、解析请求数据
            Article a = JSONUtil.deserialize(
                    req.getInputStream(),Article.class
            );
            // 2、业务处理
            int n = ArticleDAO.insert(a);

            json.setSuccess(true);
            // 处理成功，可以设置业务数据（业务数据一般是数据库查出来的）
        } catch (Exception e) {
            // 异常堆栈信息要打印，不要吃异常
            e.printStackTrace();
            json.setCode("ERROR");
            json.setMessage("后端程序出错了，请联系管理员");
        }
        // 3、返回相应数据
        String s = JSONUtil.serialize(json);
        resp.getWriter().println(s);
    }
}
