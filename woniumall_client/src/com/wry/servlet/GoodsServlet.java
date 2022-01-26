package com.wry.servlet;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wry.domain.Goods;
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

@WebServlet("/good")
public class GoodsServlet extends HttpServlet {
    private GoodsService goodsService = ServiceProxyFactory.getProxy(GoodsService.class);
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String opr = request.getParameter("opr");
        switch (opr){
            case "details":
                //商品详情
                goodsDetails(request,response);
                break;
            case "categoryList":
                //根据商品类别查询
                getGoodsByCategory(request,response);
                break;
            case "query":
                //模糊查询
                queryGoods(request,response);
                break;
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }

    /**
     * 商品详情
     * @param request
     * @param response
     */
    public void goodsDetails(HttpServletRequest request, HttpServletResponse response){
        try {
            //获取id
            Integer id = new Integer( request.getParameter("id"));
            //根据id查当前商品对象
            Goods goods = goodsService.getGoodById(id);
            request.setAttribute("goods", goods);
            //热卖商品
            List<Goods> hotGoods = goodsService.findHotGoods();
            request.setAttribute("hotGoods",hotGoods);
            request.getRequestDispatcher("view/goods/goods_view.jsp").forward(request, response);
        }catch (Exception e) {
            try {
                response.getWriter().write(JSON.toJSONString(new ResponseResult<Object>(500, "显示首页列表时错误", e.getMessage())));
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }

    /**
     * 根据商品类别查询
     * @param request
     * @param response
     */
    public void getGoodsByCategory(HttpServletRequest request, HttpServletResponse response){
        try {
            //清除搜索框的Session
            request.getSession().removeAttribute("word");

            //获得页码
            Integer pageNo=new Integer(1);
            if(request.getParameter("pageNo")!=null && request.getParameter("pageNo")!=""){
                pageNo=  new Integer(request.getParameter("pageNo"));
            }
            //获得每页条数
            Integer pageSize=new Integer(12);
            if(request.getParameter("pageSize")!=null && request.getParameter("pageSize")!=""){
                pageSize=  new Integer(request.getParameter("pageSize"));
            }
            PageHelper.startPage(pageNo,pageSize);
            Integer id = new Integer(request.getParameter("categoryid"));
            List<Goods> goodsList = goodsService.getGoodsByCategory(id);
            //销售排行榜
            List<Goods> hotGoods = goodsService.findHotGoods();
            request.setAttribute("hotGoods",hotGoods);
            //封装到pageInfo
            PageInfo<Goods> pageInfo = new PageInfo<>(goodsList);
            request.setAttribute("pageInfo",pageInfo);
            request.setAttribute("categoryid",id);
            request.getRequestDispatcher("view/goods/goods_list.jsp").forward(request, response);
        }catch (Exception e) {
            try {
                response.getWriter().write(JSON.toJSONString(new ResponseResult<Object>(500, "显示首页列表时错误", e.getMessage())));
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }

    /**
     * 模糊查询
     * @param request
     * @param response
     */
    public void queryGoods(HttpServletRequest request, HttpServletResponse response){
        try {
            //获得页码
            Integer pageNo=new Integer(1);
            if(request.getParameter("pageNo")!=null && request.getParameter("pageNo")!=""){
                pageNo=  new Integer(request.getParameter("pageNo"));
            }
            //获得每页条数
            Integer pageSize=new Integer(12);
            if(request.getParameter("pageSize")!=null && request.getParameter("pageSize")!=""){
                pageSize=  new Integer(request.getParameter("pageSize"));
            }
            PageHelper.startPage(pageNo,pageSize);
//            Integer id = new Integer(request.getParameter("categoryid"));
//            request.setAttribute("categoryid",id);
            //获取搜索框内容
            String name = request.getParameter("word");
            List<Goods> goodsList = goodsService.queryGoods(name);
            //封装到pageInfo
            PageInfo<Goods> pageInfo = new PageInfo<>(goodsList);
            request.setAttribute("pageInfo",pageInfo);
            //销售排行榜
            List<Goods> hotGoods = goodsService.findHotGoods();
            request.setAttribute("hotGoods",hotGoods);
            //回显查询条件
            request.getSession().setAttribute("word",name);
            //模糊查询标志
            request.setAttribute("vague","vagues");

            request.getRequestDispatcher("view/goods/goods_list.jsp").forward(request, response);
        }catch (Exception e) {
            try {
                response.getWriter().write(JSON.toJSONString(new ResponseResult<Object>(500, "查询商品时错误", e.getMessage())));
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }
}
