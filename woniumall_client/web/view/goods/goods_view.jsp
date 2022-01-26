<%@ page language="java" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <base href="${base}/"/>
    <meta charset="utf-8"/>
    <title>蜗牛图书商城_${goods.name}</title>
    <jsp:include page="/view/base.jsp"/>
    <script type="text/javascript" src="js/axios.js"></script>
    <script type="text/javascript" src="bootstrap/js/bootstrap.js.js"></script>
    <script type="text/javascript">
        function order_add() {
            location.href = "view/order/order_add.jsp";
        }

       // let user = '<%=request.getSession().getAttribute("user") %>'
        //添加到购物车
        function joinCart(goodsid,price) {
          let user =  document.getElementById("user").value;
           if ( user !=null && user != '') {
                let buyNums = document.getElementById("buyNums").value;
                if (buyNums > 0) {
                    axios.get('ucenter/cart', {params: {opr:'add',buyNums: buyNums,goodsid: goodsid,price: price}})
                        .then(result => {
                        alert("添加成功！");
                        location.reload();
                    }).then(e => {
                        alert(e);
                    });
                } else {
                    alert("请至少选择一件商品");
                }
            } else {
                alert("请先登录");
                window.location.href='login?opr=showlogin'
            }
        }

        //立即购买
        function nowBuy(id,price) {
            let images=document.getElementById("images").value;
            let goodname=document.getElementById("names").value;
            let user =  document.getElementById("user").value;
            if (user != null && user != '') {
                //获取购买数量 和商品id
                let buyNums = document.getElementById("buyNums").value;
                if (buyNums > 0) {
                  window.location.href="ucenter/order?opr=quicks&price="+price+"&images="+images+"&goodname="+goodname+"&buyNums="+buyNums+"&id="+id;
                }else {
                    alert("请至少选择一件商品")
                }
            } else {
                alert("请先登录");
                window.location.href='login?opr=showlogin'
            }
        }

    </script>

</head>
<body class="index">
<div id="content"></div>
<div class="container">
    <jsp:include page="/view/header.jsp"></jsp:include>
    <jsp:include page="/view/navbar.jsp"></jsp:include>
    <jsp:include page="/view/search.jsp"></jsp:include>
    <div class="wrapper clearfix">
        <div class="summary">
            <h2>${goods.name}</h2>
            <input type="hidden" id="names" value="${goods.name}"/>
            <input type="hidden" id="user" value="${user}"/>
            <!--基本信息区域-->
            <ul>
                <li><span class="f_r light_gray">商品编号：<label
                        id="data_goodsNo">${goods.goodsno}</label></span></li>
                <li id="priceLi">销售价：<b class="price red2"><span
                        class="f30" id="real_price">￥${goods.salesprice}</span></b></li>
                <li>市场价：<s id="data_marketPrice">￥${goods.marketprice}</s>
                </li>
                <li>库存：现货<span>(<label id="data_storeNums">${goods.stock}</label>)
					</span></li>
                <li>顾客评分：<span class="grade"><i style="width:0px;"></i></span>(已有0人评价)
                </li>
            </ul>
            <div class="current">
                <dl class="m_10 clearfix">
                    <dt>购买数量：</dt>
                    <dd>
                        <input class="gray_t f_l" type="number" name="buyNums" id="buyNums" value="1"
                               maxlength="5"/>
                    </dd>
                </dl>
                <input class="submit_buy" type="button" id="buyNowButton"
                       value="立即购买" onclick="nowBuy('${goods.id}','${goods.salesprice}');"/>
                <div class="shop_cart" style="z-index:1">
                    <input class="submit_join" type="button" id="joinCarButton"
                           onclick="joinCart('${goods.id}','${goods.salesprice}');" value="加入购物车"/>

                    <div class="shopping" id="product_myCart" style='display:none'>
                        <dl class="cart_stats">
                            <dt class="gray f14 bold">
                                <a class="close_2 f_r" href="javascript:closeCartDiv();"
                                   title="关闭">关闭</a> <img src="images/front/right_s.gif"
                                                          width="24" height="24" alt=""/>成功加入购物车
                            </dt>
                            <dd class="gray">
                                目前选购商品共<b class="orange" name='mycart_count'></b>件<span>合计：<b
                                    name='mycart_sum'></b></span>
                            </dd>
                            <dd>
                                <a class="btn_blue bold" href="">进入购物车</a><a
                                    class="btn_blue bold" href="javascript:void(0)"
                                    onclick="closeCartDiv();">继续购物>></a>
                            </dd>
                        </dl>
                    </div>
                </div>
            </div>

        </div>

        <!--图片放大镜-->
        <div>
            <div class="pic_show"
                 style="width:435px;height:435px;position:relative;z-index:5;padding-bottom:5px;">
                <input id="images" type="hidden" value="${goods.image}" />
                <img src="${goods.image}" title="苹果（Apple）iPhone 6 (A1586) 64GB"
                     style="border:none;width:435px;height:435px"/>
            </div>
        </div>
    </div>

    <div class="wrapper clearfix container_2">
        <!--左边栏-->
        <div class="sidebar f_l">
            <div class="box m_10">
                <div class="title">热卖商品</div>
                <div class="content">
                    <ul class="ranklist">
                        <c:forEach begin="1" end="5" items="${hotGoods}" var="hot">
                            <li class="current"><a href="good?opr=details&id=${hot.id}"><img width="58px"
                                                                                             height="58px"
                                                                                             alt="${hot.name}"
                                                                                             src="${hot.image}"/></a> <a
                                    title="" class="p_name" href=""></a> <b>￥${hot.salesprice}</b></li>
                        </c:forEach>
                    </ul>
                </div>
            </div>
        </div>

        <!--滑动面tab标签-->
        <div class="main f_r" style="overflow:hidden">
            <div class="uc_title" name="showButton">
                <label class="current"><span onclick="showDetails()">商品详情</span></label> <label><span onclick="showComment()">顾客评价(99+)</span></label>
                <label><span onclick="showHistory(${goods.id})" >购买记录(${count})</span></label>
            </div>
            <div name="showBox">
                <!-- 商品详情 start -->
                <div id="Details">${goods.description}</div>
                <!-- 商品详情 end -->

                <!-- 顾客评论 start -->
                <div id="Comment" class="hidden comment_list box">
                    <div class="title3">
							<span class="f_r f12 light_gray normal"> 只有购买过该商品的用户才能进行评价
							</span> <img src="images/front/comm.gif" width="16px" height="16px"/>商品评论<span
                            class="f12 normal">（已有<b class="red2">99+</b>条）
							</span>
                    </div>
                    <c:if test="${not empty sessionScope.user}">
                        <div>
                            <textarea id="judgecontent">很好！ </textarea>
                            <input type="button" value="发表" id="judge"/>
                        </div>
                    </c:if>
                    <div id='commentBox'></div>
                </div>
                <!-- 顾客评论 end -->

                <!-- 购买记录 start -->
                <div id="history" class="hidden box">
                    <div class="title3">
                        <img src="images/front/cart.gif" width="16" height="16" alt=""/>
                     购买记录 <span class="f12 normal">（已有<b class="red2">${count}</b>购买）
							</span>
                    </div>

                    <table width="100%" class="list_table m_10 mt_10">
                        <col width="150"/>
                        <col width="120"/>
                        <col width="120"/>
                        <col width="150"/>
                        <col/>
                        <thead class="thead">
                        <tr>
                            <th>购买人</th>
                            <th>出价</th>
                            <th>数量</th>
                            <th>购买时间</th>
                            <th>状态</th>
                        </tr>
                        </thead>
                        <tbody id="tb">
<%--                        <tr>--%>
<%--                            <td style="color: red">1</td>--%>
<%--                            <td>2</td>--%>
<%--                            <td>3</td>--%>
<%--                            <td>4</td>--%>
<%--                            <td>5</td>--%>
<%--                        </tr>--%>
                        </tbody>
                    </table>
                    <table width="100%" class="list_table m_10">
                        <col width="150"/>
                        <col width="120"/>
                        <col width="120"/>
                        <col width="150"/>
                        <col/>
                        <tbody class="dashed" id="historyBox"></tbody>
                    </table>
                </div>
                <!-- 购买记录 end -->
            </div>
        </div>
    </div>
    <jsp:include page="/view/help.jsp"></jsp:include>
    <jsp:include page="/view/footer.jsp"></jsp:include>
</div>
<script>
    //详情
    function showDetails(){
        document.getElementById("Details").style.display="block";//详情
        document.getElementById("Comment").style.display="none";//评论
        document.getElementById("history").style.display="none";//记录
    }

    //评论
    function showComment(){
        document.getElementById("Details").style.display="none";//详情
        document.getElementById("Comment").style.display="block";//评论
        document.getElementById("history").style.display="none";//记录
    }

    //购买记录
    function showHistory(goodsid){
            document.getElementById("Details").style.display="none";//详情
            document.getElementById("Comment").style.display="none";//评论
            document.getElementById("history").style.display="block";//记录

        axios.get('ucenter/order',{params:{opr:'showhistorys',goodsid:goodsid}}).then(result=>{
            let str = '';
            let arr = result.data;
           // console.log(arr)
               for (let i = 0;i<arr.length;i++){
                   if (arr[i].orders.status==1){
                       arr[i].orders.status='待付款'
                   }else if(arr[i].orders.status==2){
                       arr[i].orders.status='已付款'
                   }else if(arr[i].orders.status==3){
                       arr[i].orders.status='已收货 '
                   }else if(arr[i].orders.status==4){
                       arr[i].orders.status='已取消'
                   }else if(arr[i].orders.status==5){
                       arr[i].orders.status='已关闭'
                   }else if(arr[i].orders.status==6){
                       arr[i].orders.status='已完成'
                   }
                    //格式化日期
                   //购买时间
                   let date = new Date(arr[i].orders.ordertime);
                   let buydayStr = date/1000/60/60/24
                   //当前时间
                   let nowdate = new Date();
                   let nowdayStr = nowdate/1000/60/60/24
                   //距离购买时间的天数
                   let distanceDayStr = (nowdayStr-buydayStr)+"";
                   let distance = distanceDayStr.split(".");
                   if (distance[0] == '0'){
                       distance[0]='今天';
                   }else if (parseInt(distance[0]) >= (365/2)){
                       //超过半年显示年月日
                       distance[0]= date.getFullYear()+"年"+(date.getMonth()+1)+"月"+date.getDate()+"日";
                   }else {
                       distance[0]=distance[0]+'天前';
                   }
                   str+='<tr>'
                   str+='<td style="color: red">'+arr[i].orders.user.account+'</td>'
                   str+='<td>'+arr[i].price+'</td>'
                   str+='<td>'+arr[i].nums+'</td>'
                   str+='<td>'+distance[0]+'</td>'
                   str+='<td>'+arr[i].orders.status+'</td>'
                   str+='</tr>'
               }
           document.getElementById("tb").innerHTML=str;
        }).catch(e=>{
           alert(e)
        });

    }
</script>
</body>
</html>