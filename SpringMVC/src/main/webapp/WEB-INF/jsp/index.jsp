<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%  
    String context = request.getContextPath();
    request.setAttribute("context",context);
    request.setAttribute("page", "index");
    
    Object user = session.getAttribute("user");
  	String loginState = "notLogin";
  	if(session.getAttribute("loginState")!=null){
  		loginState = "login";
  	}
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
 	<title>首页</title>
   
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
    <meta name="renderer" content="webkit">
   
    <link rel="stylesheet" href="css/pintuer.css">
    <link rel="stylesheet" href="css/admin.css">
    <link type="image/x-icon" href="/favicon.ico" rel="shortcut icon" />
    <link href="/favicon.ico" rel="bookmark icon" />
   
    <script src="js/jquery-2.2.3.js"></script>
    <script src="js/pintuer.js"></script>
    <script src="js/respond.js"></script>
    <script src="js/admin.js"></script>
    <script src="js/layer.js"></script>
</head>

<script>
  if('<%=loginState%>' == 'notLogin'){
   	   location.href = "login.html";
  }
  
  $(document).ready(function(){
   	   $("#userGuide").click(function(){
   	   		layer.tips('未设置', '#userGuide');
   	   });    
   });
</script>

<body>
<%@include file="naviBar.jsp"%>
<div class="admin">	
	<div class="line-big">
    	<div class="xm3" style="font-size:16px">
        	<div class="panel border-back">
            	<div class="panel-body text-center" style="height:65%">
            		<br/><br/><br/>
                	<img src="<%=context%>/images/face.jpg" width="160" class="radius-circle"/><br/><br/>
                    <strong>admin</strong>
                </div>
                <div class="panel-foot bg-back border-back" style="height:30%">
                	<p>您好，${user.userName},这是您第 ${user.loginCount} 次登录 </p><p>上次登录为 ${user.lastLogin}</p>
                </div>
            </div>
        </div>
        <div class="xm9">
            <div class="alert" style="height:50%;font-size:16px">
                <div style="height:90%;">
               		<strong>功能介绍</strong>
               	    <br/><br/>
               	    <div style="float:left;width:20%;">&nbsp;</div>
                	<div style="float:left;">
                   	    <p><a href="device.html">1.设备信息管理：设备信息查询、修改</a></p>
               		    <p><a href="health.html">2.健康信息管理：健康信息查询</a></p>
                	    <p><a href="position.html">3.位置信息管理：位置信息显示</a></p>
               		    <p><a href="config.html">4.设备相关设置：设备设置</a></p>
               		</div>
               	    <br/>
               	</div>
            	<div style="float:right;">
            		<a class="button border-main icon-file" href="#" id="userGuide"> 使用教程</a>
            	</div>
             </div>
            <div class="panel" style="height:50%;font-size:16px">
            	<div class="panel-head"><strong>系统信息</strong></div>
                <table class="table" >
                	<tr><th colspan="2">服务器信息</th><th colspan="2">系统信息</th></tr>
                    <tr><td>操作系统：</td><td align="left">Windows</td><td>系统开发：</td><td><a href="#" target="_blank">前端/后台</a></td></tr>
                    <tr><td>Web服务器：</td><td>Apache</td><td>主页：</td><td><a href="#">#</a></td></tr>
                    <tr><td>程序语言：</td><td>JAVA</td><td>演示：</td><td><a href="#">#</a></td></tr>
                    <tr><td>数据库：</td><td>MySQL</td><td>&nbsp;</td><td>&nbsp;</td></tr>
                    <tr><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td></tr>
                </table>
            </div>
        </div>
    </div>
</div>
</body>
</html>