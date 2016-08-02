<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<% 
    String context = request.getContextPath();
    request.setAttribute("context",context);
    request.setAttribute("page", "health");
    
    String loginState = "notLogin";
  	if(session.getAttribute("loginState")!=null){
  		loginState = "login";
  	}
    
    String deviceId = request.getParameter("deviceId");
    if(deviceId==null){
    	deviceId = "";
    }
%>

<html>
<head>
	<title>健康</title>

    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
    <meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    
    <link rel="stylesheet" href="css/demo.css">
    <link rel="stylesheet" href="css/iconfont.css">
	<link rel="stylesheet" href="css/bootstrap.min.css">
	<link rel="stylesheet" href="css/sb-admin-2.css" >
    <link rel="stylesheet" href="css/pintuer.css">
    <link rel="stylesheet" href="css/admin.css">
    <link type="image/x-icon" href="/favicon.ico" rel="shortcut icon" />
    <link href="/favicon.ico" rel="bookmark icon" />
    
    <script src="js/jquery-2.2.3.js"></script>
    <script src="js/pintuer.js"></script>
    <script src="js/respond.js"></script>
    <script src="js/admin.js"></script>
    <script src="js/layer.js"></script>
    <script src="js/sb-admin-2.js"></script>
</head>

<script type='text/javascript'>
	if('<%=loginState%>' == 'notLogin'){
	   	 location.href = "login.html";
	}

	var formatDateTime = function (date) {  
    	var y = date.getFullYear();  
    	var m = date.getMonth() + 1;  
    	m = m < 10 ? ('0' + m) : m;  
   		var d = date.getDate();  
    	d = d < 10 ? ('0' + d) : d;  
    	var h = date.getHours();  
    	var minute = date.getMinutes();  
    	minute = minute < 10 ? ('0' + minute) : minute;  
    	var second = date.getSeconds();
    	second = second <10 ? ('0' + second) : second;
    	return y + '-' + m + '-' + d + ' '+ h+':'+minute+':'+second;  
	};  
	
	$(document).ready(function(){
		$("#query").click(function(){
			$(".huge").text('');
			if($("#deviceId").val()==""){
				layer.msg('Tips：请先填写设备ID');
				return;
			}
			else{			
				layer.load();
				$.ajax({
					url:"health/" + $("#deviceId").val() + "/getHealthInfo",
					type:"get",
					dataType:'json',
					contentType: 'application/json;charset=utf-8',
					success:function(data){					
						$("#height").text(data.height);
						$("#weight").text(data.weight);
						$("#step").text(data.step);
						$("#mileage").text((data.mileage/1000).toFixed(2));
						$("#calorie").text(data.calorie);
						$("#sleep").text((data.sleepTime/10).toFixed(1));
						$("#heartRate").text(data.heartRateSize);
						$("#surfaceTem").text((data.surfaceTem/10).toFixed(1));
						$("#humidity").text(data.humidity);
						$("#temperature").text((data.temperature).toFixed(1));
						$("#press").text((data.press/10).toFixed(1));
						layer.closeAll('loading');
				    },
				    error:function(){
				    	layer.msg('数据不存在!');
				    	layer.closeAll('loading');
				    }
				    
			    });
			}
		});
		
		//query
		if($("#deviceId").val()!='')
			$("#query").trigger('click');
		//clear
		$("#clear").click(function(){
			$("#deviceId").val('');
			$(".huge").text('');
		});
		//View Details
		$("#mileage_details").click(function(){
			if($("#deviceId").val()==""){
				layer.msg('Tips：请先填写设备ID');
			}
			else{
  				window.open("chart_mileage.html?deviceId=" + $("#deviceId").val());	
			}
		});
		$("#step_details").click(function(){
			if($("#deviceId").val()==""){
				layer.msg('Tips：请先填写设备ID');
			}
			else{
  				window.open("chart_step.html?deviceId=" + $("#deviceId").val());	
			}
		});
		$("#calorie_details").click(function(){
			if($("#deviceId").val()==""){
				layer.msg('Tips：请先填写设备ID');
			}
			else{
  				window.open("chart_calorie.html?deviceId=" + $("#deviceId").val());	
			}
		});
		$("#sleep_details").click(function(){
			if($("#deviceId").val()==""){
				layer.msg('Tips：请先填写设备ID');
			}
			else{
  				window.open("chart_sleep.html?deviceId=" + $("#deviceId").val());	
			}
		});
		$("#heartRate_details").click(function(){
			if($("#deviceId").val()==""){
				layer.msg('Tips：请先填写设备ID');
			}
			else{
				window.open("chart_heartRate.html?deviceId=" + $("#deviceId").val());
			}
		});
		$("#surfaceTem_details").click(function(){
			if($("#deviceId").val()==""){
				layer.msg('Tips：请先填写设备ID');
			}
			else{
  				window.open("chart_surfaceTem.html?deviceId=" + $("#deviceId").val());
			}
		});
		$("#humidity_details").click(function(){
			if($("#deviceId").val()==""){
				layer.msg('Tips：请先填写设备ID');
			}
			else{
  				window.open("chart_humidity.html?deviceId=" + $("#deviceId").val());
			}
		});
		$("#temperature_details").click(function(){
			if($("#deviceId").val()==""){
				layer.msg('Tips：请先填写设备ID');
			}
			else{
  				window.open("chart_temperature.html?deviceId=" + $("#deviceId").val());
			}
		});
		$("#press_details").click(function(){
			if($("#deviceId").val()==""){
				layer.msg('Tips：请先填写设备ID');
			}
			else{
  				window.open("chart_press.html?deviceId=" + $("#deviceId").val());
			}
		});
	});
</script>

<body>
<%@include file="naviBar.jsp"%>
<div class="admin">
	<form method="get" id="form">
		<div class="field" style="display:inline-block;width:36%;">&nbsp;</div>
    	<div class="field" style="display:inline-block;width:18%;"><input class="input_" type="text" id="deviceId" name="deviceId" value="<%=deviceId%>" placeholder="请填入设备编号"/></div>	
  		<div style="display:inline-block;"><button type="button" id="query" class="button button-block bg-green text-small"><h5> 查  询</h5></button></div>
		<div style="display:inline-block;"><button type="button" id="clear" class="button button-block bg-green text-small"><h5> 重 置</h5></button></div>
		<div>&nbsp;</div>
	</form>	
	<div class="row">
		<div class="col-lg-3 col-md-6">
			<div class="panel panel-primary">
				<div class="panel-heading">
					<div class="row">
						<div class="icon_lists clear">
							<i class="icon iconfont">&#xe615;</i>
						</div>
						<div class="icon_lists1">
							<div class="huge" id="height"></div>
							<div>身高(厘米)</div>
						</div>
					</div>
				</div>
				<a href="#">
					<div class="panel-footer">
						<span class="pull-left">View Details</span>
						<span class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>
						<div class="clearfix"></div>
					</div>
				</a>
			</div>
		</div>
		<div class="col-lg-3 col-md-6">
			<div class="panel panel-green">
				<div class="panel-heading">
					<div class="row">
						<div class="icon_lists clear">
							<i class="icon iconfont">&#xe60e;</i>
						</div>
						<div class="icon_lists1">
							<div class="huge" id="weight"></div>
							<div>体重(公斤)</div>
						</div>
					</div>
				</div>
				<a href="#">
					<div class="panel-footer">
						<span class="pull-left">View Details</span>
						<span class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>
						<div class="clearfix"></div>
					</div>
				</a>
			</div>
		</div>
		<div class="col-lg-3 col-md-6">
			<div class="panel panel-yellow">
				<div class="panel-heading">
					<div class="row">
						<div class="icon_lists clear">
							<i class="icon iconfont">&#xe6b0;</i>
						</div>
						<div class="icon_lists1">
							<div class="huge" id="step"></div>
							<div>步数(步)</div>
						</div>
					</div>
				</div>
				<a href="#" id="step_details">
					<div class="panel-footer">
						<span class="pull-left">View Details</span>
						<span class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>
						<div class="clearfix"></div>
					</div>
				</a>
			</div>
		</div>
		<div class="col-lg-3 col-md-6">
			<div class="panel panel-red">
				<div class="panel-heading">
					<div class="row">
						<div class="icon_lists clear">
							<i class="icon iconfont">&#xe607;</i>
						</div>
						<div class="icon_lists1">
							<div class="huge" id="mileage"></div>
							<div>里程(千米)</div>
						</div>
					</div>
				</div>
				<a href="#" id="mileage_details">
					<div class="panel-footer">
						<span class="pull-left">View Details</span>
						<span class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>
						<div class="clearfix"></div>
					</div>
				</a>
			</div>
		</div>
		<div class="col-lg-3 col-md-6">
			<div class="panel panel-danger">
				<div class="panel-heading">
					<div class="row">
						<div class="icon_lists clear">
							<i class="icon iconfont">&#xe7b4;</i>
						</div>
						<div class="icon_lists1">
							<div class="huge" id="calorie"></div>
							<div>卡路里(大卡)</div>
						</div>
					</div>
				</div>
				<a href="#" id="calorie_details">
					<div class="panel-footer">
						<span class="pull-left">View Details</span>
						<span class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>
						<div class="clearfix"></div>
					</div>
				</a>
			</div>
		</div>
		<div class="col-lg-3 col-md-6">
			<div class="panel panel-default">
				<div class="panel-heading">
					<div class="row">
						<div class="icon_lists clear">
							<i class="icon iconfont">&#xe6e6;</i>
						</div>
						<div class="icon_lists1">
							<div class="huge" id="sleep"></div>
							<div>睡眠时间(小时)</div>
						</div>
					</div>
				</div>
				<a href="#" id="sleep_details">
					<div class="panel-footer">
						<span class="pull-left">View Details</span>
						<span class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>
						<div class="clearfix"></div>
					</div>
				</a>
			</div>
		</div>
		<div class="col-lg-3 col-md-6">
			<div class="panel panel-warning">
				<div class="panel-heading">
					<div class="row">
						<div class="icon_lists clear">
							<i class="icon iconfont">&#xe621;</i>
						</div>
						<div class="icon_lists1">
							<div class="huge" id="heartRate"></div>
							<div>心率(次/分钟)</div>
						</div>
					</div>
				</div>
				<a href="#" id="heartRate_details">
					<div class="panel-footer">
						<span class="pull-left">View Details</span>
						<span class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>
						<div class="clearfix"></div>
					</div>
				</a>
			</div>
		</div>
		<div class="col-lg-3 col-md-6">
			<div class="panel panel-primary">
				<div class="panel-heading">
					<div class="row">
						<div class="icon_lists clear">
							<i class="icon iconfont">&#xe627;</i>
						</div>
						<div class="icon_lists1">
							<div class="huge" id="surfaceTem"></div>
							<div>手表皮温度(℃)</div>
						</div>
					</div>
				</div>
				<a href="#" id="surfaceTem_details">
					<div class="panel-footer">
						<span class="pull-left">View Details</span>
						<span class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>
						<div class="clearfix"></div>
					</div>
				</a>
			</div>
		</div>
		<div class="col-lg-3 col-md-6">
			<div class="panel panel-default ">
				<div class="panel-heading">
					<div class="row">
						<div class="icon_lists clear">
							<i class="icon iconfont">&#xe60f;</i>
						</div>
						<div class="icon_lists1">
							<div class="huge" id="humidity"></div>
							<div>湿度(%)</div>
						</div>
					</div>
				</div>
				<a href="#" id="humidity_details">
					<div class="panel-footer">
						<span class="pull-left">View Details</span>
						<span class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>
						<div class="clearfix"></div>
					</div>
				</a>
			</div>
		</div>
		<div class="col-lg-3 col-md-6">
			<div class="panel panel-info ">
				<div class="panel-heading">
					<div class="row">
						<div class="icon_lists clear">
							<i class="icon iconfont">&#xe627;</i>
						</div>
						<div class="icon_lists1">
							<div class="huge" id="temperature"></div>
							<div>温度(℃)</div>
						</div>
					</div>
				</div>
				<a href="#" id="temperature_details">
					<div class="panel-footer">
						<span class="pull-left">View Details</span>
						<span class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>
						<div class="clearfix"></div>
					</div>
				</a>
			</div>
		</div>
		<div class="col-lg-3 col-md-6">
			<div class="panel panel-danger">
				<div class="panel-heading">
					<div class="row">
						<div class="icon_lists clear">
							<i class="icon iconfont">&#xe61d;</i>
						</div>
						<div class="icon_lists1">
							<div class="huge" id="press"></div>
							<div>气压 (mb)</div>
						</div>
					</div>
				</div>
				<a href="#" id="press_details">
					<div class="panel-footer">
						<span class="pull-left">View Details</span>
						<span class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>
						<div class="clearfix"></div>
					</div>
				</a>
			</div>
		</div>	 
	</div>
 </div>
</body>
</html>