<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="header">
	<h1 class="logo">
		<a title="" style="background:url(images/logo.png);" href="">电子商务平台</a>
	</h1>
	<ul class="shortcut">
		<li class="first"><a href="javascript:alert('努力建设中0.0')">我的账户</a></li>
		<li><a href="javascript:alert('努力建设中0.0')">我的订单</a></li>
		<li class='last'><a href="">使用帮助</a></li>
	</ul>
	<p class="loginfo">
		<c:if test="${not empty sessionScope.user}">
		${user.account}您好，欢迎您来到${site}购物！[<a href="javascript:out()" class="reg">安全退出</a>]
	  </c:if>
		<c:if test="${empty sessionScope.user}">
	  	[<a href="login?opr=showlogin">登录</a>
			<a class="reg" href="login?opr=showRegister">免费注册</a>]
	  </c:if>
	</p>
	<script>
		function out(){
			if (confirm("确定退出码？")){
				window.location.href='login?opr=logout';
			}
		}
	</script>
</div>