package com.wry.servlet.ucenter;

import com.alibaba.fastjson.JSON;
import com.wry.domain.*;
import com.wry.service.impl.*;
import com.wry.utils.RandomCode;
import com.wry.utils.ResponseResult;
import com.wry.utils.ServiceProxyFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@WebServlet("/ucenter/order")
public class OrderServlet extends HttpServlet {
    private AddressInfoService addressInfoService = ServiceProxyFactory.getProxy(AddressInfoService.class);
    private CartService cartService = ServiceProxyFactory.getProxy(CartService.class);
    private OrderService orderService = ServiceProxyFactory.getProxy(OrderService.class);
    private OrderDetailService orderDetailService =ServiceProxyFactory.getProxy(OrderDetailService.class);
    private GoodsService goodsService = ServiceProxyFactory.getProxy(GoodsService.class);
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String opr = request.getParameter("opr");
        switch (opr){
            case "checkCart":
                //核对购物车
                checkCart(request,response);
                  break;
            case "submitCart":
                //获取提交的购物车信息
                getSubmitCart(request,response);
                break;
            case "quicks":
                nowBuy(request,response);
                break;
            case "pay":
                //支付
                payFinish(request,response);
                break;
            case "showhistorys":
                //查询购买记录
                getHistory(request,response);
                break;
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }

    /**
     * 核验购物车
     * @param request
     * @param response
     */
    public void checkCart(HttpServletRequest request, HttpServletResponse response){
        try {
            //获取当前用户
            User user = (User) request.getSession().getAttribute("user");
            //获取地址
            List<AddressInfo> addressList = addressInfoService.findUserAddress(user.getId());
            //获取所有被选中的购物车id
            String[] ids = request.getParameterValues("id");
            List<Cart> cartList = cartService.getCartGood(ids);
           // System.out.println("cartList=>"+cartList);

            request.setAttribute("cartList",cartList);
            request.setAttribute("addressList",addressList);
            //将数据发送到核对页面
            request.getRequestDispatcher("../view/order/order_add.jsp").forward(request, response);
        }catch (Exception e) {
            try {
                response.getWriter().write(JSON.toJSONString(new ResponseResult<Object>(500, "核验购物车时错误", e.getMessage())));
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }

    /**
     * 获取提交的购物车信息
     * @param request
     * @param response
     */
    public  void getSubmitCart(HttpServletRequest request, HttpServletResponse response){
        try {
            //获取当前用户
            User user = (User) request.getSession().getAttribute("user");
            //订单表中添加数据
            Orders orders = new Orders();
            //获取提交的订单信息
            String orderNo = RandomCode.getOrderNo();
            orders.setOrderno(orderNo);//订单编号
            orders.setUserid(user.getId());
            orders.setOrdertime(new Date());//下单时间
            //获取收货人，收货地址，电话
            String addressCart = request.getParameter("addressCart");
            String[] address = addressCart.split(",");
            orders.setAccept(address[0]);//收货人
            orders.setAddress(address[1]);//收货地址
            orders.setTelphone(address[2]);//电话
            BigDecimal totalMoney = new BigDecimal( request.getParameter("totalMoney"));//总金额
            orders.setMoney(totalMoney);
            String payType = request.getParameter("payType");
            orders.setPaytype(new Integer(payType));//支付方式
            orders.setStatus(1);//状态：待付款
            orders.setIsdel("n");//标识：未删除
            int saveOrder = orderService.saveOrder(orders);//添加成功后返回 该数据得主键id
            if (saveOrder > 0){
                //订单表添加成功后添加订单明细表
                OrderDetail orderDetail = new OrderDetail();
                String[] goodsid = request.getParameterValues("goodsid");//商品编号
                String[] nums = request.getParameterValues("nums");//数量
                String[] price = request.getParameterValues("price");//价格
                List<OrderDetail> orderDetailList = new ArrayList<>();
                //批量添加
                for (int i = 0; i < goodsid.length; i++) {
                    orderDetail.setOrderid(orders.getId());
                    orderDetail.setGoodsid(new Integer(goodsid[i]));
                    orderDetail.setNums(new Integer(nums[i]));
                    orderDetail.setPrice(new BigDecimal(price[i]));
                    orderDetailList.add(orderDetail);
                }
                System.out.println("要结算得商品=>"+orderDetailList);
                //调用添加订单明细表方法
                int saveOrderDetail = orderDetailService.saveOrderDetail(orderDetailList);
                //立即购买标志
                String type = request.getParameter("tips");

                if (saveOrderDetail > 0){
                    if (type == null || type=="") {
                        //添加订单明细信息成功
                        //删除对应得购物车信息
                        List<String> goodsidList = Arrays.asList(goodsid);
                        //根据商品id和用户id删除
                        int delCartGoods = cartService.delCartGoods(goodsidList, user.getId());
                        if (delCartGoods > 0) {
                            //删除成功
                            //拿到下个页面所需数据
                            request.setAttribute("orderid", orders.getId());//订单主键
                            request.setAttribute("orderno", orderNo);//订单编号
                            request.setAttribute("totalMoney", totalMoney);//订单金额
                            request.setAttribute("payType", payType);//支付方式
                            String deliveryType = request.getParameter("deliveryType");//方式
                            String deliveryTime = request.getParameter("deliveryTime");//时间
                            request.setAttribute("delivery", deliveryType + " " + deliveryTime);//配送方式
                            request.getRequestDispatcher("../view/order/order_submit.jsp").forward(request, response);
                        } else {
                            request.setAttribute("msg", "删除购物车商品失败");
                            request.getRequestDispatcher("../view/order/order_add.jsp").forward(request, response);
                        }
                    }else {
                        //拿到下个页面所需数据
                        request.setAttribute("orderid", orders.getId());//订单主键
                        request.setAttribute("orderno", orderNo);//订单编号
                        request.setAttribute("totalMoney", totalMoney);//订单金额
                        request.setAttribute("payType", payType);//支付方式
                        String deliveryType = request.getParameter("deliveryType");//方式
                        String deliveryTime = request.getParameter("deliveryTime");//时间
                        request.setAttribute("delivery", deliveryType + " " + deliveryTime);//配送方式
                        request.getRequestDispatcher("../view/order/order_submit.jsp").forward(request, response);
                    }
                }else {
                    request.setAttribute("msg", "提交订单明细信息失败");
                    request.getRequestDispatcher("../view/order/order_add.jsp").forward(request, response);
                }

            }else {
                request.setAttribute("msg", "提交订单失败");
                request.getRequestDispatcher("../view/order/order_add.jsp").forward(request, response);
            }

        }catch (Exception e) {
            try {
                response.getWriter().write(JSON.toJSONString(new ResponseResult<Object>(500, "提交订单时错误", e.getMessage())));
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }

    /**
     * 支付
     * @param request
     * @param response
     */
    public void payFinish(HttpServletRequest request, HttpServletResponse response){
        try {
            //获取当前订单id
            Integer orderid = new Integer( request.getParameter("orderid"));
            //将订单中状态改为已支付
            int updateOrderStatus = orderService.updateOrderStatus(orderid);
            if (updateOrderStatus >0){
                //支付成功
                //更新当前用户购物车Session
                User user = (User) request.getSession().getAttribute("user");
                List<Cart> cartList = cartService.getUserCart(user.getId());
                request.getSession().setAttribute("cartList",cartList);

                request.getRequestDispatcher("../view/cart/pay_suc.jsp").forward(request, response);
            }else {
                request.setAttribute("msg", "支付失败");
                request.getRequestDispatcher("../view/order/order_submit.jsp").forward(request, response);
            }
        }catch (Exception e) {
            try {
                response.getWriter().write(JSON.toJSONString(new ResponseResult<Object>(500, "支付时错误", e.getMessage())));
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }

    /**
     * 立即购买
     * @param request
     * @param response
     */
    public void nowBuy(HttpServletRequest request, HttpServletResponse response){
        try {
            //获取当前用户
            User user = (User) request.getSession().getAttribute("user");
            //获取地址
            List<AddressInfo> addressList = addressInfoService.findUserAddress(user.getId());
            request.setAttribute("addressList",addressList);
            //获取当前商品的信息
            Integer id = new Integer(request.getParameter("id"));//商品id
            Integer nums = new Integer( request.getParameter("buyNums")); //购买数量
            BigDecimal price = new BigDecimal(request.getParameter("price"));//单价
            String name = request.getParameter("goodname");//商品名称
            String image = request.getParameter("images");//图片
            //创建一个Cart对象
            Cart cart = new Cart();
            cart.setPrice(price);
            cart.setNums(nums);
            //商品对象
            Goods goods = new Goods();
            goods.setId(id);
            goods.setName(name);
            goods.setImage(image);
            cart.setGoods(goods);
            List<Cart> cartList = new ArrayList<>();
            cartList.add(cart);
            request.setAttribute("cartList",cartList);
            request.setAttribute("tip", "2");
            //将数据发送到核对页面
            request.getRequestDispatcher("../view/order/order_add.jsp").forward(request, response);
        }catch (Exception e) {
            try {
                response.getWriter().write(JSON.toJSONString(new ResponseResult<Object>(500, "立即购买时错误", e.getMessage())));
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }

    /**
     * 查询当前用户购买记录
     * @param request
     * @param response
     */
    public void getHistory(HttpServletRequest request, HttpServletResponse response){
        try {
            //获取当前的商品id
           Integer goodsid = new Integer(request.getParameter("goodsid"));
           //调用查询方法
            List<OrderDetail> orderDetailList = orderDetailService.queryOrderDetailByGoodsId(goodsid);
            if (orderDetailList != null){
                //购买人数
                request.getSession().setAttribute("count", orderDetailList.size());
                //转为json发送到页面
                response.getWriter().write(JSON.toJSONString(orderDetailList));
            }else {
                //购买人数
                request.getSession().setAttribute("count",0);
            }

        }catch (Exception e) {
            try {
                response.getWriter().write(JSON.toJSONString(new ResponseResult<Object>(500, "查询购买记录时错误", e.getMessage())));
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }
}
