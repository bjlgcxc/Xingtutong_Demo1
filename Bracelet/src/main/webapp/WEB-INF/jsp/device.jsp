<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%> 
<% 
    String context = request.getContextPath();
    request.setAttribute("context",context);
    request.setAttribute("page", "user");
    
    String loginState = "notLogin";
  	if(session.getAttribute("loginState")!=null){
  		loginState = "login";
  	}
  	
  	String deviceId="",deviceName="",deviceAlias="";
  	if(request.getParameter("deviceId")!=null){
  		deviceId = request.getParameter("deviceId");
  	}
  	if(request.getParameter("deviceName")!=null){
  		deviceName = request.getParameter("deviceName");
  	}
  	if(request.getParameter("deviceAlias")!=null){
  		deviceAlias = request.getParameter("deviceAlias");
  	}
%>

<html>
<head>
	<title>用户</title>
	
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
    <meta name="renderer" content="webkit">
   
    <link rel="stylesheet" href="css/pintuer.css">
    <link rel="stylesheet" href="css/admin.css">
    <link href="css/dataTables.bootstrap.css" rel="stylesheet">
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <link type="image/x-icon" href="/favicon.ico" rel="shortcut icon" />
    <link href="/favicon.ico" rel="bookmark icon" />
   
    <script src="js/jquery-2.2.3.js"></script>
    <script src="js/pintuer.js"></script>
    <script src="js/respond.js"></script>
    <script src="js/admin.js"></script>
    <script src="js/layer.js"></script>
    <script src="js/bootstrap.min.js"></script>
    <script src="js/jquery.dataTables.min.js"></script>
    <script src="js/dataTables.bootstrap.min.js"></script> 
</head>

<script type='text/javascript'>
	//登录状态判断
	if('<%=loginState%>' == 'notLogin'){
   	 	location.href = "login.html";
	}

	//获取格式化时间
	function GetDateTimeFormatStr(date) {
		var seperator1 = "-";
		var seperator2 = ":";
		var month = date.getMonth() + 1;
		var hours = date.getHours();
		var minutes = date.getMinutes();
		var strDate = date.getDate();
		var seconds = date.getSeconds();
		if (month >= 1 && month <= 9) {
		    month = "0" + month;
		}
		if (strDate >= 0 && strDate <= 9) {
		    strDate = "0" + strDate;
		}
		if(hours>=0 && hours<=9){
			hours = "0"+hours;
		}
		if(minutes>=0 && minutes<=9){
			minutes = "0"+minutes;
		}
		if(seconds>=0 && seconds<=9){
			seconds = "0"+seconds;
		}
		var formatDate = date.getFullYear() + seperator1 + month + seperator1 + strDate
	            + " " + hours + seperator2 + minutes + seperator2 + seconds;
		
		return formatDate;
	}

    $(document).ready(function(){
    	//设备相关操作(点击事件)
   		$("tr").find("#health").click(function(){
        	var deviceId = $(this).parents("td").siblings("#deviceId").text();
        	location.href="health.html?deviceId=" + deviceId;
    	});
    	$("tr").find("#position").click(function(){
        	var deviceId = $(this).parents("td").siblings("#deviceId").text();
        	location.href="position.html?deviceId=" + deviceId;
    	});
    	$("tr").find("#config").click(function(){
        	var deviceId = $(this).parents("td").siblings("#deviceId").text();
        	location.href="config.html?deviceId=" + deviceId;
    	});
    	
    	//显示设备信息(点击事件)
    	$("tr").find("#deviceName").click(function(){
    		var deviceId = $(this).siblings("#deviceId").text();
    		$.ajax({
    			url:"bracelet/" + deviceId + "/getBraceletInfo",
    			type:"get",
    			dataType:'json',
				contentType: 'application/json;charset=utf-8',
    			success:
    				function(data){
    			   		var info = '<div style="padding:20px;"><table width="100%"><font size=4>';
    			   		info += '<tr><td width="20%"></td><td width="20%">设备名<td>' + '<td>' + data.name + '</td><tr>';
    			   		info += '<tr><td width="20%"></td><td width="20%">MAC<td>' + '<td>' + data.mac + '</td><tr>';
    			   		info += '<tr><td width="20%"></td><td width="20%">固件版本<td>' + '<td>' + data.firmwareVersion + '</td><tr>';
    			   		info += '<tr><td width="20%"></td><td width="20%">电池等级 <td>' + '<td>' + data.batteryLevel + '</td><tr>';
    			   		info += '<tr><td width="20%"></td><td width="20%">电池状态<td>' + '<td>' + data.batteryState + '</td><tr>';
    			   		info += '</font></table></div>';
    			   		layer.open({
    			   			title:'设备信息',
        		   			type: 1,
        		   			area: ['400px', '220px'],
        		   			shadeClose: true, //点击遮罩关闭
        		   			content: info
    					});
    		  		},
    		  	error:
    		  	    function(){
    		  	    }
    		});
    		
    	});
    	
    	//修改设备别名
    	$("tr").find("#deviceAlias").click(function(){
    		var deviceId = $(this).siblings("#deviceId").text();  
    		layer.prompt({
    				title:"修改设备别名",
    				formType:0
    			},
    			function(val){	  	
    				$.ajax({
    					url:"bracelet/" + deviceId + "/updateBraceletAlias",
    					type:"post",
    					data:{deviceAlias:val},
    					success:function(){
    						location.href="device.html";
    					},
    					error:function(){
    						alert('error');
    					}
    				});
    		    }
    		); 		
    	});
    	
    	//判断设备的在线状态(30分钟以内为在线)		
    	$("[id=data]").each(function(){ 
    	    if($(this).find("#connectTime").text()==""){
    	    	$(this).find("#status").text('未知');
    	    }	     	
    	    else{
	    	    var startTime = parseInt($(this).find("#connectTime").text());
	     		$(this).find("#connectTime").text(GetDateTimeFormatStr(new Date(startTime)));
	     		
	     		var endTime = new Date().getTime();
	           	if(endTime-startTime>30*60*1000){
	           	  	 $(this).find("#status").html('<font color="red">离线</font>');
	           	}
	           	else{
	           	   	$(this).find("#status").text('在线');
	           	}
	        }
        });
    	 
   		$('#dataTables-example').DataTable({
            responsive: true
        });
   		
   		$("#clear").click(function(){
   			$(".input_").each(function(){
   				$(this).val('');
   			});
   			$("#submit").click();
   		});
   	
   });
   
</script>

<body>
<%@include file="naviBar.jsp"%>
<div class="admin" style="font-size:16px;">
	<form method="get" action="device.html">
		<div style="display:inline-block;"><label for="readme">设备编号：</label></div>
    	<div class="field" style="display:inline-block;width:15%;"><input class="input_" type="text" name="deviceId" value="<%=deviceId%>" placeholder="请填入设备编号"/></div>	
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<div style="display:inline-block;"><label for="readme">设备名：</label></div>
    	<div class="field" style="display:inline-block;width:15%;"><input class="input_" type="text" name="deviceName" value="<%=deviceName%>" placeholder="请填入设备名"/></div>	
  		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
  		<div style="display:inline-block;"><label for="readme">设备别名：</label></div>
    	<div class="field" style="display:inline-block;width:15%;"><input class="input_" type="text" name="deviceAlias" value="<%=deviceAlias%>" placeholder="请填入设备别名"/></div>	
  		<div style="display:inline-block;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div>
  		<div style="display:inline-block;"><button type="submit" id="submit" class="button button-block bg-green text-little">查  询</button></div>
  		<div style="display:inline-block;"><button  id="clear" class="button button-block bg-green text-small">重 置</button></div>
	</form>	
	<br/>
    <div id="page-wrapper">
		<div class="row">
			<div class="col-lg-12">
				<div class="panel panel-default">
					<div class="panel-body">
						<div class="dataTable_wrapper">
							<table class="table table-striped table-bordered table-hover" id="dataTables-example">
								<thead>
									<tr>	
										<th>设备编号</th>
        								<th>设备名    </th>
        								<th>设备别名</th>
        								<th>连接时间</th>
        								<th>连接状态</th>
        								<th>相关操作</th>
									</tr>
								</thead>
								<tbody>    
           							<c:forEach items="${deviceInfo}" var="item"  varStatus="status">
            						<tr id="data">
            							<td id="deviceId">${item.id}</td>
            							<td id="deviceName"><a href="#">${item.name}</a></td>
            							<td id="deviceAlias"><a href="#">${item.alias}</a></td>
            							<td id="connectTime">${item.connectTime}</td>
            							<td id="status">未知</td>
            							<td id="operation">
            								<a class="button border-black button-medium" id="health" href="#">健康</a> 
            	    						<a class="button border-black button-medium" id="position" href="#">位置</a>
            	    						<a class="button border-black button-medium" id="config" href="#">设置</a>
            							</td>
            						</tr>
           							</c:forEach>
           						</tbody>
       					 </table>
       				</div>
				</div>
			 </div>
		  </div>
       </div>
    </div>
</div>
</body>
</html>