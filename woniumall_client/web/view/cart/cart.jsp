<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<html>
<head>
<meta charset="UTF-8"></meta>
<base href="${base}/" />
<title>购物车_${site}</title>
	<script type="text/javascript" src="js/axios.js"></script>
<jsp:include page="/view/base.jsp" />
	<script type="text/javascript">
		function finish() {
			$("#form1").submit();
		}
	</script>
<script>
	//未选商品结算验证
	function checkNull(){
		let count = 0;
		let cartid =document.getElementsByName("id");
		for (let i=0;i<cartid.length;i++){
			if (cartid[i].checked==false){
				count++;
			}
		}

		if (count==cartid.length){
			alert("请至少选择一件商品结算！")
			return false;
		}else {
			return true;
		}
	}

//购物车全选
function selectAll(){
	let checkAll = document.getElementById("checkAll");
	//获取所有复选框
	let cartid =document.getElementsByName("id");
	for (let i=0;i<cartid.length;i++){
		cartid[i].checked = checkAll.checked;
	}
	goodState();
}

function fun(){
	let flag = true
	let checkAll = document.getElementById("checkAll");
	//获取所有复选框
	let cartid =document.getElementsByName("id");
	for (let i=0;i<cartid.length;i++){
		if (cartid[i].checked == false){
			flag = false;
		}
	}
	checkAll.checked = flag;

	goodState();
}
//计算总金额
function goodState(){
	let totalMoney = 0;
	//获取所有复选框
	let cartid =document.getElementsByName("id");
	for (let i=0;i<cartid.length;i++){
		if (cartid[i].checked==true){
			//获得小计
			let Subtotal = parseFloat( cartid[i].parentElement.parentElement.childNodes[10].childNodes[0].innerText);
			console.log(Subtotal);
			//总计
			totalMoney += Subtotal;
		}
	}
	document.getElementById("sum_price").innerText=totalMoney;
}


</script>
</head>
<body class="second">
	<div class="brand_list container_2">
		<jsp:include page="/view/header.jsp"></jsp:include>
		<div class="wrapper clearfix">
			<div class="position mt_10">
				<span>您当前的位置：</span> <a href=""> 首页</a> » 购物车
			</div>
			<div class="myshopping m_10">
				<ul class="order_step">
					<li class="current"><span class="first">1、查看购物车</span></li>
					<li><span>2、填写核对订单信息</span></li>
					<li class="last"><span>3、成功提交订单</span></li>
				</ul>
			</div>
			<form action="ucenter/order?opr=checkCart" id="form1" method="post" onsubmit="return checkNull()">
				<table width="100%" class="cart_table m_10">
					<caption>查看购物车</caption>
					<thead>
						<tr>
							<th><input type="checkbox" checked="checked" name="checkAll" id="checkAll" onclick="selectAll()">选择</th>
							<th>图片</th>
							<th>商品名称</th>
							<th>单价</th>
							<th>数量</th>
							<th>小计</th>
							<th class="last">操作</th>
						</tr>
					</thead>
					<tbody id="goodsList">
					<c:set var="money" value="0"></c:set>
				<c:forEach items="${cartList}" var="cart">
					<tr><td><input type="checkbox" id="id" checked="checked"  name="id" value="${cart.id}" onclick="fun()"></td>
						<td><input type="hidden" name="" value=""/>
							<img src="${cart.goods.image}" width="66px"
								 height="66px" alt=""
								 title="" /></td>
						<div style="width: 100px"><td class="t_l"><a disabled="true" class="blue">${cart.goods.name}</a></td></div>
						<td>￥<b>${cart.price}</b></td>
						<td>
							<div class="num">
								<a class="reduce" href="javascript:void(0)" onclick=''>-
								</a>
								<label><input
										name="" class="tiny" value="${cart.nums}" onblur='' type="text" id="goods_count_3"></label>
								<a class="add" href="javascript:void(0)" onclick=''>+</a>
							</div>
						</td>
						<td><b class="red2" id="goods_sum_3">${cart.price * cart.nums}</b></td>
						<td><a href="javascript:delGoods(${cart.id});">删除</a></td>
					</tr>
					<c:set var="money" value="${money+cart.price * cart.nums}"></c:set>
				</c:forEach>
						<tr class="stats">
							<td colspan="8">金额总计（不含运费）：￥<b class="orange" id='sum_price'>${money}</b></td>
						</tr>
					</tbody>
					<tfoot>
						<tr>
							<td colspan="2" class="t_l"></td>
							<td colspan="6" class="t_r"><a class="btn_continue" href="">继续购物</a>
								<a class="btn_pay" href="javascript:finish();">去结算</a></td>
						</tr>
					</tfoot>
				</table>
			</form>
		<jsp:include page="/view/footer.jsp" />
	</div>
			<script>
				//删除购物车商品
				function delGoods(cartid){
					if (confirm("确定移除该商品码？")){
						axios.get('cart',{params:{opr:'delGood',id:cartid}}).then(result=>{
							alert(result.data.msg);
							//刷新
							location.reload();
						}).catch(e=>{
							alert("服务器离家出走了0.0");
						});
					}
				}
			</script>
</body>
</html>
