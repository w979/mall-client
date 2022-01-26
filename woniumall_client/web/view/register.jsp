<%@ page language="java" pageEncoding="UTF-8"%>


<html>
<head>
<base href="${base}/" />
<meta charset="utf-8" />
<title>用户注册_蜗牛图书商城</title>
<jsp:include page="base.jsp" />
<link rel="stylesheet" type="text/css" href="css/index.css" />
<script type="text/javascript" src="js/jquery-3.5.1.js"></script>
	<script type="text/javascript" src="js/axios.js"></script>
<style type="text/css">

.btnCode {
	background-color: chocolate;
	width: 100px;
	height: 30px;
	color: white;
}
.dis{
    background-color:#F0F0F0;
    color:red;
 }
 </style>
<script type="text/javascript">
	let flag = false;

	//账号唯一性验证
	function checkAccount(){
		let account = document.getElementById("account").value;

		axios.get('login',{params:{opr:'check',account:account}}).then(result=>{
			console.log(result.data)
				if (result.data.code==501){
					//账号存在
					document.getElementById("account").innerText=result.data.msg;
					flag = true;
				}else {
					flag = false;
				}
		}).catch(e=>{
			alert("服务器起飞了")
		});
	}

	//表单非空验证
	function isForm(){
		let account = document.getElementById("account");
		let password = document.getElementById("password");
		let repassword = document.getElementById("repassword");
		let email = document.getElementById("email");
		let captcha = document.getElementById("captcha");

		if (account.value.length <= 0){
			document.getElementById("accountmsg").innerText="请输入账号！"
			account.focus();
			return false;
		}else {
			document.getElementById("accountmsg").innerText=""

			if (flag){
				document.getElementById("accountmsg").innerText="账号已存在"
				account.focus();
				return false;
			}else {
				document.getElementById("accountmsg").innerText=""
			}
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

		if (repassword.value.length <= 0){
			document.getElementById("repwdmsg").innerText="请输入确认密码！"
			repassword.focus();
			return false;
		}else {
			let reg = /^\w{6,12}$/;
			if (reg.test(repassword.value)){
				document.getElementById("repwdmsg").innerText=""
			}else {
				document.getElementById("repwdmsg").innerText="6~12数字,字母或下划线"
				return false;
			}

			//document.getElementById("repwdmsg").innerText=""
		}

		if (password.value != repassword.value){
			document.getElementById("repwdmsg").innerText="两次密码不一致,请重新输入！"
			repassword.focus();
			return false;
		}else {
			document.getElementById("repwdmsg").innerText=""
		}
		//邮箱验证
		if (email.value.length <= 0){
			document.getElementById("emailmsg").innerText="请输入邮箱！"
			email.focus();
			return false;
		}else {
			let reg= /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/;
			if (reg.test(email.value)){
				document.getElementById("emailmsg").innerText=""
			}else {
				document.getElementById("emailmsg").innerText="邮箱格式不正确！"
				email.focus();
				return false;
			}
		}

		if (captcha.value.length <= 0){
			document.getElementById("codemsg").innerText="请输入激活码！"
			captcha.focus();
			return false;
		}else {
			document.getElementById("codemsg").innerText=""
		}
		return true;
	}

	let t=2;
	//异步注册
	function checkReg(){
		if (isForm()){
            let account = document.getElementById("account").value;
            let password = document.getElementById("password").value;
            let email = document.getElementById("email").value;
            let captcha = document.getElementById("captcha").value;

            let formdata= new URLSearchParams({
                opr:'register',
                account:account,
                password:password,
                email:email,
                captcha:captcha
            });

            axios.post('login',formdata.toString()).then(result=>{
				if (result.data.code==200){
					//添加成功
					setInterval("refer()",1000); //启动1秒定时
				}else {
					alert(result.data.msg)
				}
            }).catch(e=>{
                alert(e);
            });
		}
	}

	//自动跳转
	function refer(){
		if(t==0){
			location.href='login?opr=showlogin'; //#设定跳转的链接地址
		}
		document.getElementById("shows").innerHTML=""+t+"秒后跳转到登录页面"; // 显示倒计时
		t--; // 计数器递减
	}

	//发送邮箱验证码
	function btnCode(){
		let email=document.getElementById("email").value;
		//倒计时
		let tim=60;
		if(email.length>0){
			//发送
			axios.get('login',{params:{opr:'code',email:email}})
					.then(result=>{
						console.log(result.data)
						if(result.data.code==200){
							alert("验证码发送成功，请注意查收");

							let code=document.getElementById("code");
							interval=setInterval(function(){
								//禁用发送按钮
								code.setAttribute("disabled","disabled");
								//设置发送按钮禁用状态的样式
								code.setAttribute("class","dis")
								//改变按钮上的文字
								code.setAttribute("value",(--tim)+ " 秒后重新发送");

								if(tim<=0){
									//关闭计时器
									clearInterval(interval);
									//恢复按钮状态
									code.removeAttribute("disabled");
									code.setAttribute("class","btnCode")
									code.setAttribute("value","发送验证码");
								}
							},1000);
						}else{
							alert("验证码发送失败,请重试");
						}
					}).catch(e=>{
				alert(e);
			})
		}else{
			alert("请先输入邮箱地址");
		}
	}


</script>
</head>
<body class="second">
	<div class="brand_list container_2">
		<jsp:include page="header.jsp"></jsp:include>
		<div class="wrapper clearfix">
			<div class="wrap_box">
				<h3 class="notice">用户注册</h3>
				<p class="tips">
					<span class="gray f_r">已有帐号？请点<a class="orange bold"
						href="login?opr=showlogin">这里</a>登录
					</span>欢迎来到我们的网站，如果您是新用户，请填写下面的表单进行注册
				</p>

				<div class="box clearfix">
					<form action='' method="post">

						<table class="form_table f_l">
							<col width="260px" /><span id="shows" style="color: red;size: 50px"></span>
							<col />
							<tr>
								<th>用户名：</th>
								<td><input class="gray" name='account' id="account" onblur="checkAccount()" placeholder="2-20个字符，可以为字数，数字下划线和中文"
									type="text" value="" tabindex="1" /><label style="color: red" id="accountmsg"></label></td>
							</tr>
							<tr>
								<th>设置密码：</th>
								<td><input class="gray" type="password" name='password' id="password" placeholder="登录密码，6-32个字符"
									tabindex="2" /><label style="color: red" id="pwdmsg"></label></td>
							</tr>
							<tr>
								<th>确认密码：</th>
								<td><input class="gray" type="password" name='repassword' id="repassword" placeholder="重复上面所填写的密码"
									tabindex="3" /><label style="color: red" id="repwdmsg"></label></td>
							</tr>
							<tr>
								<th>电子邮箱：</th>
								<td><input class="gray" name='email' type="email" id="email" placeholder="xxx@xxx.com"
									tabindex="5"> <input type="button" id="code" class="btnCode"
									value="发激活码" onclick="btnCode()" /> <label style="color: red" id="emailmsg"></label></td>
							</tr>
							<tr>
								<th>激活码：</th>
								<td><input type='text' class="gray" id="captcha" placeholder="填写六位激活码"  name='captcha' alt='填写激活码' />
									<label style="color: red" id="codemsg"></label></td>
							</tr>
							<tr>
								<td></td>
								<td><input class="submit_reg" type="button" onclick="checkReg()"
									value="同意一下条款，提交" /><label></label></td>
							</tr>
						</table>
					</form>
					<div class="agreement gray">
						一、本站服务条款的确认和接纳本站的各项电子服务的所有权和运作权归本站。本站提供的服务将完全按照其发布的服务条款和操作规则严格执行。用户同意所有服
						务条款并完成注册程序，才能成为本站的正式用户。用户确认：本协议条款是处理双方权利义务的约定，除非违反国家强制性法律，否则始终有效。在下订单的同
						时，您也同时承认了您拥有购买这些产品的权利能力和行为能力，并且将您对您在订单中提供的所有信息的真实性负责。
						二、服务简介本站运用自己的操作系统通过国际互联网络为用户提供网络服务。同时，用户必须：
						(1)自行配备上网的所需设备，包括个人电脑、调制解调器或其它必备上网装置。
						(2)自行负担个人上网所支付的与此服务有关的电话费用、网络费用。基于本站所提供的网络服务的重要性，用户应同意
						(1)提供详尽、准确的个人资料。 (2)不断更新注册资料，符合及时、详尽、准确的要求。本站不公开用户的姓名、地址、电子邮箱和笔名，
						除以下情况外： (1)用户授权本站透露这些信息。 (2)相应的法律及程序要求本站提供用户的个人资料。
						三、价格和数量本站将尽最大努力保证您所购商品与网站上公布的价格一致。产品的价格和可获性都在本站
						上指明，这类信息将随时更改。您所订购的商品，如果发生缺货，您有权取消定单。
						四、送货及费用本站将会把产品送到您所指定的送货地址。所有在本站上列出的
						送货时间为参考时间，参考时间的计算是根据库存状况、正常的处理过程和送货时间、送货地点的基础上估计得出的。送货费用根据您选择的配送方式的不同而异。
						请清楚准确地填写您的真实姓名、送货地址及联系方式。因如下情况造成订单延迟或无法配送等，本站将不迟延配送的责任：
						(1)客户提供错误信息和不详细的地址； (2)货物送达无人签收，由此造成的重复配送所产生的费用及相关的后果。
						(3)不可抗力，例如：自然灾害、交通戒严、突发战争等。
						五、服务条款的修改本站将可能不定期的修改本用户协议的有关条款，一旦条款及服务内容产生变动， 本站将会在重要页面上提示修改内容。
						六、用户隐私制度尊重用户个人隐私是本站的一项基本政策。所以，作为对以上第二点人注册资料分析的补充，本站一定不会
						在未经合法用户授权时公开、编辑或透露其注册资料及保存在本站中的非公开内容，除非有法律许可要求或本站在诚信的基础上认为透露这些信件在以下四种情况是
						必要的。
						七、用户的帐号，密码和安全性用户一旦注册成功，成为本站的合法用户，将得到一个密码和用户名。您可随时根据指示改变您的密码。用户需谨慎合理的
						保存、使用用户名和密码。用户若发现任何非法使用用户帐号或存在安全漏洞的情况，请立即通知本站和向公安机关报案。
						八、对用户信息的存储和限制本站有判定
						用户的行为是否符合国家法律法规规定及本站服务条款权利，如果用户违背了国家法律法规规定或服务条款的规定，本站有中断对其提供网络服务的权利。
						九、用户 管理用户单独承担发布内容的责任。用户对服务的使用是根据所有适用于本站的国家法律、地方法律和国际法律标准的。用户必须遵循：
						(1)从中国境内向外传输技术性资料时必须符合中国有关法规。 (2)使用网络服务不作非法用途。 (3)不干扰或混乱网络服务。
						(4)遵守所有使用网络服务的网络协议、规定、程序和惯例。用户须承诺不传输任何非法的、骚扰性的、中伤他人的、辱骂性的、恐性的、伤害性的、庸俗的，淫
						秽等信息资料。另外，用户也不能传输何教唆他人构成犯罪行为的资料；不能传输助长国内不利条件和涉及国家安全的资料；不能传输任何不符合当地法规、国家法
						律和国际法律的资料。未经许可而非法进入其它电脑系统是禁止的。若用户的行为不符合以上提到的服务条款，本站将作出独立判断立即取消用户服务帐号。用户需
						对自己在网上的行为承担法律责任。用户若在本站上散布和传播反动、色情或其它违反国家法律的信息，本站的系统记录有可能作为用户违反法律的证据。
						十、通告
						所有发给用户的通告都可通过重要页面的公告或电子邮件或常规的信件传送。用户协议条款的修改、服务变更、或其它重要事件的通告都会以此形式进行。
						十一、参
						与广告策划用户在他们发表的信息中加入宣传资料或参与广告策划，在本站的免费服务上展示他们的产品，任何这类促销方法，包括运输货物、付款、服务、商业条
						件、担保及与广告有关的描述都只是在相应的用户和广告销售商之间发生。
						十二、网络服务内容的所有权本站定义的网络服务内容包括：文字、软件、声音、图片、
						录象、图表、广告中的全部内容；电子邮件的全部内容；本站为用户提供的其它信息。所有这些内容受版权、商标、标签和其它财产所有权法律的保护。所以，用户
						只能在本站和广告商授权下才能使用这些内容，而不能擅自复制、再造这些内容、或创造与内容有关的派生产品。本站所有的文章版权归原文作者和本站共同所有，
						任何人需要转载本站的文章，必须征得原文作者或本站授权。
						十三、责任限制如因不可抗力或其它本站无法控制的原因使本站销售系统崩溃或无法正常使用导致网上
						交易无法完成或丢失有关的信息、记录等本站会尽可能合理地协助处理善后事宜，并努力使客户免受经济损失。
						十四、法律管辖和适用本协议的订立、执行和解释及
						争议的解决均应适用中国法律。如发生本站服务条款与中国法律相抵触时，则这些条款将完全按法律规定重新解释，而其它合法条款则依旧保持对用户产生法律效力
						和影响。本协议的规定是可分割的，如本协议任何规定被裁定为无效或不可执行，该规定可被删除而其余条款应予以执行。如双方就本协议内容或其执行发生任何争
						议，双方应尽力友好协商解决；协商不成时，任何一方均可向本站所在地的人民法院提起诉讼。</div>
				</div>
			</div>
		</div>
		<jsp:include page="footer.jsp"></jsp:include>
	</div>
</body>
</html>
