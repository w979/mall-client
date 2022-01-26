<%@ page language="java" pageEncoding="UTF-8"%>


<html>
<head>
<base href="${base}/" />
<meta charset="utf-8" />
<title>用户登录_蜗牛图书商城</title>
<link rel="stylesheet" type="text/css" href="css/index.css" />
	<link rel="stylesheet" type="text/css" href="css/admin.css" />
	<link rel="stylesheet" type="text/css" href="css/pintuer.css" />
	<script type="text/javascript" src="js/axios.js"></script>

	<script type="text/javascript">
		//表单验证
		function checkLogin(){
			let account = document.getElementById("account");
			let password = document.getElementById("password");
			if (account.value.length <= 0){
				document.getElementById("namemsg").innerText="请输入账号！"
				account.focus();
				return false;
			}else {
				document.getElementById("namemsg").innerText=""
			}

			if (password.value.length <= 0){
				document.getElementById("pwdmsg").innerText="请输入密码！"
				password.focus();
				return false;
			}else {
				let reg = /^\w{6,12}$/;
				if (reg.test(password.value)){
					document.getElementById("pwdmsg").innerText=""
				}else {
					document.getElementById("pwdmsg").innerText="6~12数字,字母或下划线"
					return false;
				}
			}

			return true;
		}
		//异步登录
		function login(){
			if (checkLogin()){
				let account = document.getElementById("account").value;
				let password = document.getElementById("password").value;
				axios.get('login?opr=dologin',{params:{account:account,password:password}}).then(result=>{
					console.log(result.data);
					if (result.data.code==200){
						document.getElementById("loginMsg").innerText=""
						//登录成功
						window.location.href="index?opr=list";
					}else {
						document.getElementById("loginMsg").innerText=result.data.msg;
					}
				}).catch(e=>{
					alert("服务器起飞了");
				})
			}
		}
	</script>
</head>

<body class="second">
	<div class="brand_list container_2">
		<jsp:include page="header.jsp"></jsp:include>

		<div class="wrapper clearfix">
			<div class="wrap_box">
				<h3 class="notice">已注册用户，请登录</h3>
				<p class="tips">欢迎来到我们的网站，如果您已是本站会员请登录</p>
				<div class="box login_box clearfix">
					<form action='' method="post" >
					<input type="hidden" name="opr" value="login" />
						<table width="515" class="form_table f_l">
							<col width="120px" />
							<col />
							<tr>
								<th id="loginMsg" colspan="2" style="color:red;text-align:center;"></th>
							</tr>
							<tr>
								<th>用户名：</th>
								<td><input class="gray" type="text" name="account" id="account"
									 placeholder="请输入用户名" /><span style="color: red" id="namemsg"></span></td>
							</tr>
							<tr>
								<th>密码：</th>
								<td><input class="gray" type="password" id="password"
									name="password" placeholder="请输入6-20位长度的密码" /><span style="color: #ff0000"
									id="pwdmsg"></span></td>
							</tr>
							<tr>
								<td></td>
								<td><input class="submit_login" onclick="login()" type="button" value="登录" /></td>
							</tr>
						</table>
					</form>

					<!--正常登录时-->
					<table width="360px" class="form_table prompt_3 f_l">
						<col width="75px" />
						<col />
						<tr>
							<th></th>
							<td>
								<p class="mt_10">
									<strong class="f14">您还不是<span class="orange">蜗牛图书商城</span>用户
									</strong>
								</p>
								<p>
									现在免费注册成为嗨购商城用户，便能立即享受便宜又放心的购物乐趣。<a class="blue" href="">网站首页>></a>
								</p>
								<p class="mt_10">
									<a class="reg_btn" href="login?opr=showRegister">注册新用户</a>
								</p>
							</td>
						</tr>
					</table>
				</div>
			</div>
		</div>
		<jsp:include page="footer.jsp"></jsp:include>
	</div>
</body>
</html>
