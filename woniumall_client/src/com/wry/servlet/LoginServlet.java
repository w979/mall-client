package com.wry.servlet;

import com.alibaba.fastjson.JSON;
import com.ndktools.javamd5.Mademd5;
import com.wry.domain.Cart;
import com.wry.domain.User;
import com.wry.service.impl.CartService;
import com.wry.service.impl.UserService;
import com.wry.utils.EmailUtil;
import com.wry.utils.RandomCode;
import com.wry.utils.ResponseResult;
import com.wry.utils.ServiceProxyFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private UserService userService = ServiceProxyFactory.getProxy(UserService.class);

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String opr = request.getParameter("opr");
        switch (opr){
            case "showlogin":
                //跳转到登录页面
                response.sendRedirect("view/login.jsp");
                break;
            case "showRegister":
                //跳转到注册页面
                response.sendRedirect("view/register.jsp");
                break;
            case "code":
                //邮箱激活码
                emailCode(request,response);
                break;
            case "register":
                userRegister(request,response);
                break;
            case "dologin":
                //用户登录
                getLogin(request,response);
                break;
            case "logout":
                //用户退出
                request.getSession().invalidate();
                response.sendRedirect("index?opr=list");
                break;
            case "check":
                //账号唯一性验证
                checkAccount(request,response);
                break;
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }

    /**
     * 邮箱验证码
     * @param request
     * @param response
     */
    public void emailCode(HttpServletRequest request, HttpServletResponse response){
        try {
            //获取收件人邮箱
            String email = request.getParameter("email");
            //生成6为激活码
            String code = RandomCode.getEmailCode();
            //调用发送邮箱的工具类
            boolean sendEmail = EmailUtil.sendEmail(email, code);
            if (sendEmail){
                //发送成功后保存到Session中
                HttpSession session = request.getSession();
                session.setAttribute("code",code);
                //设置Session过期时间
                session.setMaxInactiveInterval(120);
                response.getWriter().write(JSON.toJSONString(new ResponseResult<Object>(200, "验证码发送成功")));
            }else {
                response.getWriter().write(JSON.toJSONString(new ResponseResult<Object>(501, "验证码发送失败,请重试")));
            }

        }catch (Exception e) {
            try {
                response.getWriter().write(JSON.toJSONString(new ResponseResult<Object>(500, "验证激活码时错误", e.getMessage())));
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }

    /**
     * 用户注册
     * @param request
     * @param response
     */
    public void userRegister(HttpServletRequest request, HttpServletResponse response){
        try {
             //获取页面输入的验证码  转为全大写
            String captcha = request.getParameter("captcha").toUpperCase();
            //获取session中的激活码
            String code = (String) request.getSession().getAttribute("code");
            if (code == null){
                response.getWriter().write(JSON.toJSONString(new ResponseResult<Object>(501, "验证码已失效或不存在")));
            }else if (!captcha.equals(code)){
                response.getWriter().write(JSON.toJSONString(new ResponseResult<Object>(502, "验证码错误")));
            }else {
                User user = new User();
                //获取用户输入
                user.setAccount(request.getParameter("account"));
                user.setPassword(new Mademd5().toMd5(request.getParameter("password")));
                user.setEmail(request.getParameter("email"));
                user.setAvatar("images/avatar/default.jpg");
                user.setScore(0);
                user.setRegtime(new Date());
                user.setActivecode(captcha);
                user.setStatus(2);
                //调用添加方法
                int n = userService.saveUser(user);
                if (n > 0){
                    response.getWriter().write(JSON.toJSONString(new ResponseResult<Object>(200,"添加成功")));
                }else {
                    response.getWriter().write(JSON.toJSONString(new ResponseResult<Object>(200,"添加失败")));
                }
            }

        }catch (Exception e) {
            try {
                response.getWriter().write(JSON.toJSONString(new ResponseResult<Object>(500, "注册时错误", e.getMessage())));
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }

    /**
     * 用户登录验证
     */
    public void getLogin(HttpServletRequest request, HttpServletResponse response){
        try {
            //获取账号和密码
            String account = request.getParameter("account");
            String password = request.getParameter("password");
            //验证账号密码
            User user = userService.userLogin(account, new Mademd5().toMd5(password));
            ResponseResult<Object> responseResult = null;
            if (user != null){
                //登录成功
                request.getSession().setAttribute("user", user);
                responseResult = new ResponseResult<>(200,"ok");
            }else {
                responseResult = new ResponseResult<>(500,"账号或密码错误");
            }
            //转为json
            response.getWriter().write(JSON.toJSONString(responseResult));
        }catch (Exception e) {
            try {
                response.getWriter().write(JSON.toJSONString(new ResponseResult<Object>(504, "登录时错误", e.getMessage())));
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }

    /**
     * 账号唯一性验证
     */
    public void checkAccount(HttpServletRequest request, HttpServletResponse response){
        try {
            //获取页面当前输入的账号
            String account = request.getParameter("account");
            User userByAccount = userService.getUserByAccount(account);
            if (userByAccount != null){
                //账号存在
                response.getWriter().write(JSON.toJSONString(new ResponseResult<Void>(501, "账号已存在")));
            }else {
                response.getWriter().write(JSON.toJSONString(new ResponseResult<Void>(200, "账号不存在")));
            }
        }catch (Exception e) {
            try {
                response.getWriter().write(JSON.toJSONString(new ResponseResult<Object>(500, "验证账号时错误", e.getMessage())));
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }
}
