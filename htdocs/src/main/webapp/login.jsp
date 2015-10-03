<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- saved from url=(0219)http://login.sdo.com/sdo/Login/LoginFrameFC.php?target=top&appId=4012&pm=2&areaId=1&loginifrmId=iframeLogin&proxyUrl=&returnURL=http%3A%2F%2Fsw.sdo.com%2FLoginCheck.aspx&backUrl=http%3A%2F%2Fsw.sdo.com%2FLoginCheck.aspx -->
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta name="description" content="GIS在线用户登录窗口">
		<meta name="keywords" content="GIS在线,GIS网络,通行证,密宝,SDO,SNDA,网络游戏,游戏充值">
		<title>CGISER通行证</title>
		<link href="./css/public.css" rel="stylesheet">
<script src="http://code.jquery.com/jquery-latest.js"></script>
<script type="text/javascript">
// 测试引用是否成功
	$(document).ready(function(e) {
		
		
	});

	
	function userLogin(){
	 //$('#login1').css({'display':'none'})
	  $('#login1').animate({left:"388"},1000);
	};
	function userLogin1(){
		if(document.getElementById("un").value == ""){
			document.getElementById("un").value="E-Mail/用户名";
			$('#un').css({'color':'#BFBFBF'});
			alert("请输入用户名");
			return;
		}
		if(document.getElementById("un").value == "E-Mail/用户名"){
			alert("请输入用户名");
			return;
		}
		if(document.getElementById("password2").value == ""){
			alert("请输入密码");
			return;
		}
		var data = "un="+document.getElementById("un").value+"&pw="+document.getElementById("password2").value+"&logintype=USERPWD&devicetype=iPhone%20OS%207.0.4%20iPhone5,2&game=moonlightforapple";
		var url = "http://passport.moonlightol.com:8080/weblogin.do?"+data;
		//$.getJSON(url,function(result){
			//alert(result)
		//});
		window.location = url;
	};
	function UserNameClick(aEvent){
	  aEvent.returnValue=false;
	  aEvent.cancel = true;
	  if(document.getElementById("un").value == "E-Mail/用户名"){
		document.getElementById("un").value="";
		$('#un').css({'color':''});
	  }
	}
 function lostFocus(aEvent){
      aEvent.returnValue=false;
      aEvent.cancel = true;
	  if(document.getElementById("un").value == ""){
		document.getElementById("un").value="E-Mail/用户名";
		$('#un').css({'color':'#BFBFBF'})
	  }
}
      //$('#jUserLoginDiv').css({'display':'none'})
	  //$('#jUserLoginDiv1').css({'display':''});
</script>
		<script>
document.domain = "cgiser.com";
</script>
	</head>
<body style="">

	<div id = "login2" class="publicspr toplogin" style ="zIndex:0;left:0px;position:absolute">
		<div id="jUserLoginDiv1"  class="topunlogin">
			<li>
				<span class="l">用户名：</span>
				<span class="r">
					<input id="un" name="un" size="25" tabindex="1" value="E-Mail/用户名" style="color: rgb(191, 191, 191);width:130px" htmlescape="true" onclick="UserNameClick(event);" onblur="lostFocus(event)">
					<a href="javascript:userLogin1();" class="a-in" tabindex="3">登录</a>
				</span>
			</li>
			<span class="l">&nbsp;&nbsp;&nbsp;</span>
			<li>
				<span class="l">密&nbsp;&nbsp;&nbsp;码：</span>
				<span class="r">
					<input type="password" id="password2" name="password2" class="required" style = "width:130px" tabindex="2" size="25" value="" autocomplete="false">
					<a href="/user/userGetPass.jsp" target="_blank">忘记密码？</a>
				</span>
			</li>
		</div>
	</div>
<div id = "login1" class="publicspr toplogin" style ="zIndex:1;left:0px;position:absolute">
		<div id="jUserLoginDiv" class="topunlogin">
			<p id="jUserLoginP" class="un1">亲爱的召唤师，欢迎<a href="javascript:userLogin();" class="a-in">登录</a></p>
			<i class="publicspr un0"></i>
			<p>登录后查看自己的月光宝盒游戏信息，包括<br><span class="un2">召唤师等级、战斗力</span> 等。</p>
		</div>
		<div id="jUserLoginedDiv" class="clearfix toplogined" style="display:none">
			<div id="jUserIcon" class="loginheader"></div>
			<div class="loginname">
				<h4 id="jUserName"></h4>
				<p id="jUserArea" class="publicspr login-fwq">-</p>
				<a class="login-out lnk-normal" href="javascript:LW201310_Userinfo.userLogout();">注销</a>
			</div>
			<ul class="loginnumber">
				<li><em id="jUserGames" class="cgreen">-</em>元宝</li>
				<li><em id="jUserRP">-</em>神龙券券</li>
				<li><em id="jUserIP">-</em>铜钱</li>
				<li><em id="jUserRS">-</em>战斗力</li>
			</ul>
		</div>
	</div>
</body>
</html>
