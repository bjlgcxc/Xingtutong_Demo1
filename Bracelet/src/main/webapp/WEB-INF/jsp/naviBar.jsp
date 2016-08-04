<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<script type='text/javascript'>
   $(document).ready(function(){
   		$("#<%=request.getAttribute("page")%>").addClass("active");
   		$("#logOut").click(function(){
   			location.href="login.html";
   		});
   });
</script>

<div class="righter nav-navicon" id="admin-nav">
    <div class="mainer">
        <div class="admin-navbar">
            <span class="float-right">
            	<a class="button button-small bg-main" href="index.html"> 首 页</a>
                <a class="button button-small bg-red" id="logOut" href="#"> 注 销</a>
            </span>
            <ul class="nav nav-inline admin-nav">
                <li id="index"><a href="index.html" class="icon-home"> 首页</a></li>	
                <li id="user"><a href="device.html" class="icon-user"> 设备</a></li>
                <li id="health"><a href="health.html" class="icon-heart"> 健康</a></li>      
                <li id="position"><a href="position.html" class="icon-map-marker"> 位置</a></li>
                <li id="config"><a href="config.html" class="icon-cog"> 设置</a></li>
                <li id="system"><a href="system.html" class="icon-desktop"> 系统</a></li>
            </ul>
        </div>
    </div>
</div>