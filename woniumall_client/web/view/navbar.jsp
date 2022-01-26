<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<script type="text/javascript" src="js/jquery-3.5.1.js"></script>
<script type="text/javascript" src="js/axios.js"></script>
<script>
	$(function() {
		$('.mycart').hover(function() {
			$('#div_mycart').stop().show('slow');
		}, function() {
			$('#div_mycart').stop().hide('slow');
		});
		
		
		//首页购物车列表，标题截取
		$(".goodsname").each(function(){
			let goodsname=$(this).html();
			$(this).html(goodsname.substring(0,30)+"...");
		});
	});

	function goCart(){
		let nums= document.getElementById("nums").innerText;
		if (parseInt(nums) > 0){
			window.location.href='ucenter/cart?opr=cartResult';
		}else {
			alert("您还没有添加商品呢");
		}
	}

	//删除
	function removeCart(cartid){
		if (confirm("确定删除吗？")){
			axios.get('ucenter/cart',{params:{opr:'delGood',id:cartid}}).then(result=>{
				alert(result.data.msg);
				//刷新
				location.reload();
			}).catch(e=>{
				alert("服务器离家出走了0.0")
			})
		}
	}
</script>
<div class="navbar">
	<ul>
		<li><a href="index?opr=list">首页</a></li>
		<c:forEach items="${categoryList}" var="category">
			<li><a href="good?opr=categoryList&categoryid=${category.id }">${category.name }</a></li>
		</c:forEach>
	</ul>
	<div class="mycart">
		<dl>
			<dt>
				<a href="javascript:goCart()">购物车<b id="nums" name="mycart_count">${fn:length(cartList)}</b>件
				</a>
			</dt>
			<dd>
				<a href="javascript:goCart()">去结算</a>
			</dd>
		</dl>

		<!--购物车浮动div 开始-->
		<div class="shopping" id='div_mycart' style='display: none;'>
		<!--购物车浮动div 结束-->

		<dl class="cartlist">
		<!-- 设置全局变量，保存总金额 -->
	        <c:set var="money" value="0"/>
			<c:forEach items="${cartList }" var="cart" varStatus="cartIndex">
			<dd id="site_cart_dd_${cartIndex.index }">
				<div class="pic f_l">
					<img width="55" height="55" title="${cart.goods.name }" src="${cart.goods.image }">
				</div>
				<h3 class="title f_l">
					<a href="goods?opr=showGoods&goodsid=${cart.goods.id }" 
					class="goodsname" title="${cart.goods.name }">${cart.goods.name }</a>
				</h3>
				<div class="price f_r t_r">
					<b class="block">￥${cart.goods.salesprice} x ${cart.nums}</b> <input
						class="del" type="button" value="删除"
						onclick="removeCart(${cart.id });" />
				</div>
			</dd>
			<!-- 修改全局变量的值  -->
			<c:set var="money"  value="${money+cart.goods.salesprice*cart.nums }"></c:set>
			</c:forEach>
			
			<dd class="static">
				<span>共<b name="mycart_count">${fn:length(cartList)}</b>件商品
				</span>金额总计：<b name="mycart_sum">￥${money }</b>
			</dd>
			<dd class="static">
				<label class="btn_orange"><input type="button"
					value="去购物车结算" onclick="javascript:goCart()"/></label>
			</dd>
		</dl>
	</div>
		<!--购物车模板 结束-->
	</div>
</div>