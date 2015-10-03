<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"
	contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
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
				<meta name="keywords"
					content="GIS在线,GIS网络,通行证,密宝,SDO,SNDA,网络游戏,游戏充值">
					<title>CGISER通行证</title>
					<link href="./css/public.css" rel="stylesheet">
						<script src="http://code.jquery.com/jquery-latest.js">
</script>
						<script type="text/javascript">
// 测试引用是否成功
$(document).ready(function(e) {
	$('#serverSelect').change(function() {
		var serverId = $(this).children('option:selected').val()
		var userIden = $('#userIden').val();
		var url = "http://a.moonlightol.com:8080/getWebRole.do?uniden="+userIden+"&serverid="+serverId;
		//$.getJSON(url,function(result){
			//alert(result)
		//});
		$.ajax({  
			url:url,  
			dataType:'jsonp',  
			data:'',
			jsonp:'callback',  
			success:function(r) {  
			   for (var i=0;i<r.length;i++)
				{
					$("#roleContentId_lol").append("<option value='"+r[i].roleName+"'>"+r[i].roleName+"</option>"); 
				}
			},
			timeout:3000  
		}); 
	});
	$('#confirmButtonId_lol').click(function(){
		
		var roleName = $('#roleContentId_lol').children('option:selected').val()
		if(roleName==''){
			alert('请选择角色');
		}
		var url = "http://a.moonlightol.com:8080/webLogin.do?roleName="+roleName;
		//$.getJSON(url,function(result){
			//alert(result)
		//});
		window.location = url;
	});
});
function selectServer() {
	$('#login1').animate( {
		left : "388"
	}, 1000);
			var serverId = $(this).children('option:selected').val()
		var userIden = $('#userIden').val();
		var url = "http://a.moonlightol.com:8080/getWebRole.do?uniden="+userIden+"&serverid="+serverId;
	$.ajax({  
			url:url,  
			dataType:'jsonp',  
			data:'',
			jsonp:'callback',  
			success:function(r) {  
			   for (var i=0;i<r.length;i++)
				{
					$("#roleContentId_lol").append("<option value='"+r[i].roleName+"'>"+r[i].roleName+"</option>"); 
				}
			},
			timeout:3000  
		}); 
}
</script>
						<script>
document.domain = "cgiser.com";
</script>
	</head>
	<body style="">
		<div id="server" class="publicspr toplogin"
			style="zIndex: 1; left: 0px; position: absolute">
			<div id="jUserLoginDiv" class="topunlogin">
					<li>
						<span>请选择大区：</span>
						<select id="serverSelect">
							<option value="">
								请选择大区
							</option>
							<logic:notEmpty name="game">
								<logic:notEmpty name="game" property="gameServerList">
									<logic:iterate id="server" name="game"
										property="gameServerList">
										<option
											value='<bean:write name="server" property="serverId"/>'>
											<bean:write name="server" property="serverName" />
										</option>
									</logic:iterate>
								</logic:notEmpty>
							</logic:notEmpty>
						</select>
					</li>
					<li class="role_select_li" style="display: block;">
						<span>请选择角色：</span>
						<select id="roleContentId_lol">
							
						</select>
					</li>
					<li class="error_message_li" id="errorMessage_lol"
						style="display: none;"></li>
					<li class="button">
						<button id="confirmButtonId_lol">
							确 定
						</button>
						<button id="cancelButtonId_lol">
							取 消
						</button>
					</li>
			</div>
		</div>
		<input type="hidden" id="userIden" name="userIden"
			value='<bean:write name="user" property="userIden" />'>
			<div id="login1" class="publicspr toplogin"
				style="zIndex: 1; left: 0px; position: absolute">
				<div id="jUserLoginDiv" class="topunlogin">
					<p id="jUserLoginP" class="un1">
						您已登陆，请
						<a href="javascript:selectServer();">选择大区</a>，或者
						<a href="javascript:LW201310_Userinfo.userLogout();">注销</a>
					</p>
					<i class="publicspr un0"></i>
					<p>
						登录后查看自己的月光宝盒游戏信息，包括
						<br>
							<span class="un2">召唤师等级、战斗力</span> 等。 
					</p>
				</div>
				<div id="jUserLoginedDiv" class="clearfix toplogined"
					style="display: none">
					<div id="jUserIcon" class="loginheader"></div>
					<div class="loginname">
						<h4 id="jUserName"></h4>
						<p id="jUserArea" class="publicspr login-fwq">
							-
						</p>
						<a class="login-out lnk-normal"
							href="javascript:LW201310_Userinfo.userLogout();">注销</a>
					</div>
					<ul class="loginnumber">
						<li>
							<em id="jUserGames" class="cgreen">-</em>元宝
						</li>
						<li>
							<em id="jUserRP">-</em>神龙券
						</li>
						<li>
							<em id="jUserIP">-</em>铜钱
						</li>
						<li>
							<em id="jUserRS">-</em>战斗力
						</li>
					</ul>
				</div>
			</div>
	</body>
</html>
