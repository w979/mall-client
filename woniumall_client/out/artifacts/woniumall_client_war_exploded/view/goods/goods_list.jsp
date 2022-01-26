<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
<meta charset="UTF-8"></meta>
<base href="${base}/" />
<title>首页_${site}</title>
<jsp:include page="/view/base.jsp" />
	<link rel="stylesheet" href="/css/fenye.css">
</head>

<body class="index">
	<div class="container">
		<jsp:include page="/view/header.jsp"></jsp:include>
		<jsp:include page="/view/navbar.jsp"></jsp:include>
		<jsp:include page="/view/search.jsp"></jsp:include>

		<div class="wrapper clearfix">
		<div class="wrapper clearfix container_2">
			<div class="sidebar f_l">
				<!--销售排行-->
				<div class="box m_10">
					<div class="title">销售排行榜</div>
					<div class="content">
						<ul class="ranklist" id='ranklist'>
							<c:forEach items="${hotGoods}" var="hot" begin="1" end="8" varStatus="vs">
							<li><span>${vs.count}</span>
								<a class="p_name" target="_blank" href="good?opr=details&id=${hot.id}">${hot.name}</a>
						    </li>
							</c:forEach>
						</ul>
					</div>
				</div>
				<!--销售排行-->
			</div>
			
			<div class="main f_r">
				<!--最新商品-->
				<form id="queryForm">
				<div class="box yellow m_10">
				 	<div class="cont clearfix">
						<ul class="prolist">
							<c:forEach items="${pageInfo.list}" var="goods">
								<li style="overflow: hidden"><a href="good?opr=details&id=${goods.id}" target="_blank"><img
										src="${goods.image }" width="175" height="175" alt="" /></a>
									<p class="pro_title">
										<a title="" href="good?opr=details&id=${goods.id}">${goods.name }</a>
									</p>
									<p class="brown">
										惊喜价：<b>￥${goods.salesprice }</b>
									</p>
									<p class="light_gray">
										市场价：<s>￥${goods.marketprice }</s>
									</p></li>
							</c:forEach>
						</ul>
					</div>
				</div>
				</form>
				<div class='pages_bar'>
					<a href="javascript:javascript:prePage('${pageInfo.pageNum}','${pageInfo.pageSize}')">上一页</a>
					<c:forEach begin="1" end="${pageInfo.pages}" var="pagenum">
					<a href="javascript:goPage('${pagenum}','${pageInfo.pageSize}')">${pagenum}</a>
					</c:forEach>
					<a href="javascript:nextPage('${pageInfo.pageNum}','${pageInfo.pageSize}','${pageInfo.pages}')">下一页</a>
					<a disabled="true">当前第 ${pageInfo.pageNum} 页 | 共 ${pageInfo.pages} 页 | ${pageInfo.total} 条数据</a>
				</div>
			</div>
		</div>
		<jsp:include page="/view/help.jsp"></jsp:include>
		<jsp:include page="/view/footer.jsp"></jsp:include>
	</div>

<script>
	let flag= '${vague}';
	//搜索框分页
	if (flag != null && flag!= ""){
		//上一页
		function prePage(pageNo,pageSize){
			if(parseInt(pageNo)>1){
				pageNo--;
				let name = '${word}';
				let queryForm = document.getElementById("queryForm");
				let formdata = new URLSearchParams({
					opr: 'query',
					pageNo: pageNo,
					pageSize: pageSize,
					word:name
				});
				window.location.href="good?"+formdata.toString();
			}
		}

		//下一页
		function nextPage(pageNo,pageSize,pages){
			if(parseInt(pageNo)<parseInt(pages)){
				pageNo++;
				let name = '${word}';
				let queryForm = document.getElementById("queryForm");
				let formdata = new URLSearchParams({
					opr: 'query',
					pageNo: pageNo,
					pageSize: pageSize,
					word:name
				});
				window.location.href="good?"+formdata.toString();
			}
		}

		//页码跳转
		function goPage(pageNo,pageSize){
			let name = '${word}';
			let queryForm = document.getElementById("queryForm");
			let formdata = new URLSearchParams({
				opr: 'query',
				pageNo: pageNo,
				pageSize: pageSize,
				word:name
			});
			window.location.href="good?"+formdata.toString();
		}
	}else {
		//商品类别查询分页
	//上一页
	function prePage(pageNo,pageSize){
		let categoryid = '${categoryid}'
		if(parseInt(pageNo)>1){
			pageNo--;
			window.location.href="good?opr=categoryList&pageNo="+pageNo+"&pageSize="+pageSize+"&categoryid="+categoryid;
		}
	}

	//下一页
	function nextPage(pageNo,pageSize,pages){
		let categoryid = '${categoryid}'
		if(parseInt(pageNo)<parseInt(pages)){
			pageNo++;
			window.location.href="good?opr=categoryList&pageNo="+pageNo+"&pageSize="+pageSize+"&categoryid="+categoryid;
		}
	}

	//页码跳转
	function goPage(pageNo,pageSize){
		let categoryid = '${categoryid}'
		window.location.href="good?opr=categoryList&pageNo="+pageNo+"&pageSize="+pageSize+"&categoryid="+categoryid;
	}
	}
</script>
</body>
</html>