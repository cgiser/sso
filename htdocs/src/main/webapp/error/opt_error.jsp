<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ page import="com.hudong.sso.server.constant.HDSSOServerConstant" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>操作错误 - 互动百科</title>
    <link href="http://www.huimg.cn/wikispace/css/hd-tb.css?1104" rel="stylesheet" type="text/css" />
    <link href="http://www.huimg.cn/wikispace/css/basic.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="http://www.huimg.cn/wikispace/js/popwin_baikedasai.js"></script>
    <script type="text/javascript" src="http://www.huimg.cn/wikispace/js/ajax.js"></script>
    <script type="text/javascript" src="http://www.huimg.cn/wikispace/js/newestUser.js"></script>
    <script type="text/javascript" src="http://www.huimg.cn/wikispace/js/4print.js"></script>
    <script type="text/javascript" src="http://www.huimg.cn/wikispace/js/userloginnav_index.js"></script>
  </head>
  <body>
    <div id="hd-top">
<%@include file="/inc/index_head_new.html"%>
</div>
    <div id="cjtmjs">
      <div class="hdwiki_neirong">
        <div class="hdwiki_banbenbjye">
          <table width="950" height="400" border="0" align="center" cellpadding="0" cellspacing="0">
            <tr>
              <td width="115">
                &nbsp;
              </td>
              <td width="313">
                &nbsp; 
              </td>
              <td colspan="2">
                &nbsp;
              </td>
              <td width="287">
                &nbsp;
              </td>
            </tr>
            <tr>
              <td height="24">
                &nbsp;
              </td>
              <td height="30" class="zc1">
                <table width="100%" border="0" cellpadding="0" cellspacing="0">
                  <tr>
                    <td align="center" class="hdz14">
                    </td>
                  </tr>
                  <tr class="hdz12">
                    <td>
                      <br />
                      <li>
                        <%String request_opt_err=(String)request.getAttribute(HDSSOServerConstant.request_opt_err);%>
                        <%if(request_opt_err!=null&&!("".equals(request_opt_err))){%>
                        <%=request_opt_err%>
                        <br />
                        <%}%>
                      </li>
                    </td>
                  </tr>
                  <tr class="hdz12">
                    <td></td>
                  </tr>
                  <tr>
                    <td>
                      &nbsp;
                    </td>
                  </tr>
                  <tr>
                    <td align="center">
                      <a class="e" href="<%if((String)request.getAttribute(HDSSOServerConstant.request_forword_url)==null){ %>javascript:history.go(-1)<%}else{ %><%=(String)request.getAttribute(HDSSOServerConstant.request_forword_url)%><%} %>">返&nbsp; 回</a>
                    </td>
                  </tr>
                </table>
              </td>
              <td width="112" align="right" valign="top">
              </td>
              <td width="154">
                <img src="http://www.huimg.cn/wikispace/images/c1.gif" width="130" height="177" alt="" />
              </td>
              <td>
                &nbsp;
              </td>
            </tr>
            <tr>
              <td>
                &nbsp;
              </td>
              <td>
                &nbsp;
              </td>
              <td colspan="2">
                &nbsp;
              </td>
              <td>
                &nbsp;
              </td>
            </tr>
          </table>
        </div>
      </div>
    </div>
<div class="clear"></div>
<%@include file="/inc/index_foot_new.html"%> 
  </body>
</html>
