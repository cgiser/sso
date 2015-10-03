<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8"%>
<%@ page import="com.hudong.sso.server.constant.HDSSOServerConstant" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<title>抱歉，您访问的页面出错了！</title>
<link href="http://www.huimg.cn/cas/css/other.css" type="text/css" rel="stylesheet" media="all"/>
</head>

<body>

  <div class="page_640_300">
    <div class="head">
      <div class="head_t">
	      <span class="bk">
	      	<a href="http://www.baike.com/" class="bold">Baike.com</a>
	      	|<a href="http://www.baike.com/">互动百科</a>
	      </span>
	      <span class="login">还不是小百科的用户？
	      	<a href="javascript:void(0);" onclick="toRegister();return false" class="reg">注册</a>
	      	<a href="javascript:void(0);" onclick="toLogin();return false">登录</a>
	      </span>
     </div>
    </div>

    <div class="wrap">
      <div class="error500">
        <h2>
        <%String request_opt_err=(String)request.getAttribute(HDSSOServerConstant.request_opt_err);%>
          <%if(request_opt_err!=null&&!("".equals(request_opt_err))){%>
          <%=request_opt_err%>
          <br />
          <%}else{%>
        	  抱歉，您的访问出错了！
          <%}%>
        
        </h2>
        <p>请<a href="javascript:void(0)" onclick="window.location.reload();return false">刷新</a>页面或访问小百科的其它页面，<a href="http://x.baike.com/">返回小百科首页</a></p>
      </div>
    
    
    </div>
<script type="text/javascript">
function toLogin(){
	var url='http://passport.baike.com/login.do?site_ource=xiaobaike_page&service='+encodeURIComponent("http://x.baike.com");
	window.open(url,'_self');
}
function toRegister(){
	 var url='http://passport.baike.com/user/xbkUserRegister.jsp';
	 window.open(url,'_self');
}
</script>
<jsp:directive.include file="/user/includes/xbkbottomInclud.jsp" />

</div>

</body>
</html>
