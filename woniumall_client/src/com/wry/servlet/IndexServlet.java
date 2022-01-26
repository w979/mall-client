package com.wry.servlet;

import com.alibaba.fastjson.JSON;
import com.wry.domain.Cart;
import com.wry.domain.Category;
import com.wry.domain.Goods;
import com.wry.domain.User;
import com.wry.service.impl.CartService;
import com.wry.service.impl.CategoryService;
import com.wry.service.impl.GoodsService;
import com.wry.utils.ResponseResult;
import com.wry.utils.ServiceProxyFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/index")
public class IndexServlet extends HttpServlet {
    private CategoryService categoryService= ServiceProxyFactory.getProxy(CategoryService.class);
    private GoodsService goodsService = ServiceProxyFactory.getProxy(GoodsService.class);
    private CartService cartService = ServiceProxyFactory.getProxy(CartService.class);
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String opr = request.getParameter("opr");
        switch (opr){
            case "list":
                //显示首页列表
               showIndex(request,response);
                break;
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }

    /**
     * 显示首页数据
     * @param request
     * @param response
     */
    public void showIndex(HttpServletRequest request, HttpServletResponse response){
        try {
            //全部商品类别导航
            List<Category> categoryList = categoryService.findCategory();
            request.getSession().setAttribute("categoryList",categoryList);
            //全部商品类别列表
            List<Category> listCategory = categoryService.findListCategory();
            request.getSession().setAttribute("listCategory",listCategory);
            //最新商品列表
            List<Goods> newGoods = goodsService.findNewGoods();
            request.setAttribute("newGoods",newGoods);
            //热卖商品
            List<Goods> hotGoods = goodsService.findHotGoods();
            request.setAttribute("hotGoods",hotGoods);

            //加载购物车列表
            User user = (User) request.getSession().getAttribute("user");
            if (user != null){
                //存储当前用户的购物车信息
                List<Cart> cartList = cartService.getUserCart(user.getId());
                request.getSession().setAttribute("cartList",cartList);
            }

            request.getSession().setAttribute("site", "蜗牛图书商城");
            request.getRequestDispatcher("view/index.jsp").forward(request, response);
        }catch (Exception e) {
            try {
                response.getWriter().write(JSON.toJSONString(new ResponseResult<Object>(500, "显示首页列表时错误", e.getMessage())));
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }
}
