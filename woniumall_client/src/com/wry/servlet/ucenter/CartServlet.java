package com.wry.servlet.ucenter;

import com.alibaba.fastjson.JSON;
import com.wry.domain.Cart;
import com.wry.domain.User;
import com.wry.service.impl.CartService;
import com.wry.utils.ResponseResult;
import com.wry.utils.ServiceProxyFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@WebServlet("/ucenter/cart")
public class CartServlet extends HttpServlet {
    private CartService cartService = ServiceProxyFactory.getProxy(CartService.class);
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String opr = request.getParameter("opr");
        switch (opr) {
            case "cartResult":
                //跳转到结算页面
                response.sendRedirect("../view/cart/cart.jsp");
                break;
            case "delGood":
                //从购物车删除商品
                delCartGood(request,response);
                break;
            case "add":
                //添加商品购物车
                addGoods(request,response);
                break;
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }

    /**
     * 删除购物车中商品
     * @param request
     * @param response
     */
    public void delCartGood(HttpServletRequest request, HttpServletResponse response) {
        try {
            //获取id
            Integer cartid = new Integer(request.getParameter("id"));
            //删除
            int n = cartService.delCartGoodById(cartid);
            ResponseResult<Void> responseResult = null;
            if (n > 0) {
                //更新Session
                User user = (User) request.getSession().getAttribute("user");
                List<Cart> cartList = cartService.getUserCart(user.getId());
                request.getSession().setAttribute("cartList",cartList);

                responseResult = new ResponseResult<>(200, "删除成功");
            } else {
                responseResult = new ResponseResult<>(500, "删除失败");
            }
            response.getWriter().write(JSON.toJSONString(responseResult));
        } catch (Exception e) {
            try {
                response.getWriter().write(JSON.toJSONString(new ResponseResult<Object>(500, "显示首页列表时错误", e.getMessage())));
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }

    /**
     * 添加商品购物车
     * @param request
     * @param response
     */
    public void addGoods(HttpServletRequest request, HttpServletResponse response){
        try {
            //获取当前操作用户的id
            User user = (User) request.getSession().getAttribute("user");
            //获取商品id 和数量
            Integer goodsid = new Integer(request.getParameter("goodsid"));
            Integer nums= new Integer(request.getParameter("buyNums"));
            int n = 0;
            //查询当前用户的购物车商品
            Cart good = cartService.getGoodByUserId(goodsid, user.getId());
           // System.out.println("=>"+good);
            if (good != null){
                //商品存在更新数量即可
                good.setUserid(user.getId());
                good.setGoodsid(goodsid);
                good.setNums(good.getNums()+nums);
                good.setAddtime(new Date());
                n = cartService.updateCart(good);
            }else {
                //商品不存在调用添加方法
                Cart cart = new Cart();
                cart.setNums(nums);
                cart.setAddtime(new Date());
                cart.setPrice(new BigDecimal(request.getParameter("price")));
                cart.setGoodsid(goodsid);
                cart.setUserid(user.getId());
                //调用添加方法
                n = cartService.saveCart(cart);
            }

            //更新Session
            List<Cart> cartList = cartService.getUserCart(user.getId());
            request.getSession().setAttribute("cartList",cartList);

            ResponseResult<Object> responseResult = null;
            if (n > 0){
                responseResult = new ResponseResult<>(200, "添加成功");
            }else {
                responseResult = new ResponseResult<>(501, "添加失败");
            }
            response.getWriter().write(JSON.toJSONString(responseResult));
        }catch (Exception e) {
            try {
                response.getWriter().write(JSON.toJSONString(new ResponseResult<Object>(500, "添加商品到购物车时错误", e.getMessage())));
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }
}
