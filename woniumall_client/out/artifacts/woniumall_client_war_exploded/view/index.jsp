<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
<meta charset="UTF-8"></meta>
<base href="${base}/" />
<title>首页_${site}</title>
<jsp:include page="base.jsp" />
</head>

<body class="index">
	<div class="container">
		<jsp:include page="/view/header.jsp"></jsp:include>
		<jsp:include page="/view/navbar.jsp"></jsp:include>
		<jsp:include page="/view/search.jsp"></jsp:include>

		<div class="wrapper clearfix">
			<div class="sidebar f_r">
				<!--热卖商品-->
				<div class="hot box m_10">
					<div class="title">
						<h2>热卖商品</h2>
					</div>
					<div class="cont clearfix">
						<ul class="prolist">
							<c:forEach items="${hotGoods}" var="hot">
								<li><a href="good?opr=details&id=${hot.id}"><img src="${hot.image}"
										width="85" height="85" alt="${hot.name}" /></a>
									<p class="pro_title">
										<a href="good?opr=details&id=${hot.id}">${hot.name}</a>
									</p>
									<p class="brown">
										<b>￥${hot.salesprice}</b>
									</p></li>
							</c:forEach>
						</ul>
					</div>
				</div>
				<!--热卖商品-->

			</div>

			<div class="main f_l">
				<!--最新商品-->
				<div class="box yellow m_10">
					<div class="title title3">
						<h2>
							<img src="images/front/new_product.gif" alt="最新商品" width="160"
								height="36" />
						</h2>
					</div>
					<div class="cont clearfix">
						<ul class="prolist">
							<c:forEach items="${newGoods}" var="goods">
								<li style="overflow: hidden"><a href="good?opr=details&id=${goods.id}" target="_blank"><img
										src="${goods.image}" width="175" height="175" alt="${goods.name}" /></a>
									<p class="pro_title">
										<a title="" href="good?opr=details&id=${goods.id}">${goods.name}</a>
									</p>
									<p class="brown">
										惊喜价：<b>￥${goods.salesprice}</b>
									</p>
									<p class="light_gray">
										市场价：<s>￥${goods.marketprice}</s>
									</p></li>
							</c:forEach>
						</ul>
					</div>
				</div>
			</div>
		</div>
		<jsp:include page="help.jsp"></jsp:include>
		<jsp:include page="footer.jsp"></jsp:include>
	</div>
</body>
</html>