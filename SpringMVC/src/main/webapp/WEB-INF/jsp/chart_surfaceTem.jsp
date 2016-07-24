<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<% 
    String context = request.getContextPath();
    request.setAttribute("context",context);
 
    String deviceId = request.getParameter("deviceId");
    String pageInfo = "手表皮温度";
%>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    <title>SufaceTem</title>
    
    <!-- Bootstrap Core CSS -->
    <link href="css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom CSS -->
    <link href="css/sb-admin-2.css" rel="stylesheet">

    <!-- Morris Charts CSS -->
    <link href="css/morris.css" rel="stylesheet">
    <link rel="stylesheet" href="css/pintuer.css">
    <link rel="stylesheet" href="css/admin.css">
    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
    
    <!-- jQuery -->
    <script src="js/jquery-2.2.3.js"></script>

    <!-- Bootstrap Core JavaScript -->
    <script src="js/bootstrap.min.js"></script>

    <!-- Morris Charts JavaScript -->
    <script src="js/raphael.min.js"></script>
    <script src="js/morris.min.js"></script>

    <!-- Flot Charts JavaScript -->
    <!--[if lte IE 8]><script src="js/excanvas.min.js"></script><![endif]-->
    <script src="js/jquery.flot.resize.js"></script>
    
    <script src="js/pintuer.js"></script>
    <script src="js/respond.js"></script>
    <script src="js/admin.js"></script>
    <script src="js/layer.js"></script> 
    <script type="text/javascript" src="js/jedate.js"></script>
	
	<link type="image/x-icon" href="/favicon.ico" rel="shortcut icon" />
    <link href="/favicon.ico" rel="bookmark icon" />
</head>

<script type='text/javascript'>
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
//获取格式化日期
function GetDateFormatStr(date) {
	var seperator1 = "-";
	var month = date.getMonth() + 1;
	if (month >= 1 && month <= 9) {
	    month = "0" + month;
	}
	var strDate = date.getDate();
	if (strDate >= 0 && strDate <= 9) {
	    strDate = "0" + strDate;
	}
	var formatDate = date.getFullYear() + seperator1 + month + seperator1 + strDate;
	
	return formatDate;
}

var morris1,morris2,morris3;
var line1 = {	
    element: 'morris-line-chart-day',  		
    xkey: 'time',      		
   	ykeys: ['surfaceTem'],        	
   	labels: ['Surface Temperature(℃)'],      		
    smooth: false,
    resize: true
};
var line2 = {	
    element: 'morris-line-chart-week',  		
    xkey: 'time',      		
   	ykeys: ['surfaceTem'],        	
   	labels: ['Surface Temperature(℃)'],      		
    smooth: false,
    resize: true
};
var line3 = {	
    element: 'morris-line-chart-month',  		
    xkey: 'time',      		
   	ykeys: ['surfaceTem'],        	
   	labels: ['Surface Temperature(℃)'],      		
    smooth: false,
    resize: true
};

showChart = function(type){
	var duration;
	if(type=='day'){
		$.ajax({
			url:"heartRate/" + <%=deviceId%> + "/getSurfaceTem?duration=1&startTime=" + $("#startTime1").val() + 
		                                          "&endTime=" + $("#endTime1").val(),
			type:"get",
			dataType:'json',
			contentType: 'application/json;charset=utf-8',
			success:function(data){
				morris1.setData(data);
       	    },
        	error:function(){
        		alert('error');
        	}
		});
	}
	else if(type=='week'){
		$.ajax({
			url:"heartRate/" + <%=deviceId%> + "/getSurfaceTem?duration=2&startTime=" + $("#startTime2").val() + 
		                                          "&endTime=" + $("#endTime2").val(),
			type:"get",
			dataType:'json',
			contentType: 'application/json;charset=utf-8',
			success:function(data){
				morris2.setData(data);
       	    },
        	error:function(){
        		alert('error');
        	}
		});
	}
	else if(type=='month'){
		$.ajax({
			url:"heartRate/" + <%=deviceId%> + "/getSurfaceTem?duration=2&startTime=" + $("#startTime3").val() + 
		                                          "&endTime=" + $("#endTime3").val(),
			type:"get",
			dataType:'json',
			contentType: 'application/json;charset=utf-8',
			success:function(data){
				morris3.setData(data);
       	    },
        	error:function(){
        		alert('error');
        	}
		});
	}
};

//init
$(document).ready(function(){
	
	var type='day';
	var flag2=0,flag3=0;
	
	$("#startTime2").hide();$("#endTime2").hide();
	$("#startTime3").hide();$("#endTime3").hide();
	
	$("#tab1").click(function(){
		showChart('day');
		type = 'day';	
		$("#startTime1").show();$("#endTime1").show();
		$("#startTime2").hide();$("#endTime2").hide();
		$("#startTime3").hide();$("#endTime3").hide();
	});	
	$("#tab2").click(function(){
		if(flag2==0){
			morris2 = new Morris.Line(line2);
			flag2=1;
		}
		showChart('week');	
		type='week';
		$("#startTime2").show();$("#endTime2").show();
		$("#startTime1").hide();$("#endTime1").hide();
		$("#startTime3").hide();$("#endTime3").hide();
	});
	$("#tab3").click(function(){
		if(flag3==0){
			morris3 = new Morris.Line(line3);
			flag3=1;
		}
		showChart('month');
		type='month';
		$("#startTime3").show();$("#endTime3").show();
		$("#startTime1").hide();$("#endTime1").hide();
		$("#startTime2").hide();$("#endTime2").hide();
	});
	
	//设置时间插件(日)
	var start1 = {
		dateCell:"#startTime1",
		format:"YYYY-MM-DD hh:mm:ss",
		isinitVal:true,
		isTime:true, //isClear:false,
		minDate:"2000-01-01 00:00:00",
		maxDate:jeDate.now(-1),
		choosefun:function(date){
			var d = new Date(date);
			d.setDate(d.getDate()+1);
			$("#endTime1").val(GetDateTimeFormatStr(d));
		},
		okfun:function(date){
			var d = new Date(date);
			d.setDate(d.getDate()+1);
			$("#endTime1").val(GetDateTimeFormatStr(d));
		}
	 };
	 var end1 = {
		dateCell:"#endTime1",
		format:"YYYY-MM-DD hh:mm:ss",
		isinitVal:true,
		isTime:true, //isClear:false,
		minDate:"2000-01-01 00:00:00",
		maxDate:jeDate.now(0),
		choosefun:function(date){
			var d = new Date(date);
			d.setDate(d.getDate()-1);
			$("#startTime1").val(GetDateTimeFormatStr(d));
		},
		okfun:function(date){
			var d = new Date(date);
			d.setDate(d.getDate()-1);
			$("#startTime1").val(GetDateTimeFormatStr(d));
		}
	};
	jeDate(start1);
	jeDate(end1);
	var et = new Date();
	et.setDate(et.getDate()-1);
	$("#startTime1").val(GetDateTimeFormatStr(et));
	
	//设置时间插件(周)
	var start2 = {
		dateCell:"#startTime2",
		format:"YYYY-MM-DD",
		isinitVal:true,
		isTime:false, //isClear:false,
		minDate:"2000-01-01",
		maxDate:jeDate.now(-6),
		choosefun:function(date){
			var d = new Date(date);
			d.setDate(d.getDate()+6);
			$("#endTime2").val(GetDateFormatStr(d));
		},
		okfun:function(date){
			var d = new Date(date);
			d.setDate(d.getDate()+6);
			$("#endTime2").val(GetDateFormatStr(d));
		}
	 };
	 var end2 = {
		dateCell:"#endTime2",
		format:"YYYY-MM-DD",
		isinitVal:true,
		isTime:false, //isClear:false,
		minDate:"2000-01-01 00:00:00",
		maxDate:jeDate.now(0),
		choosefun:function(date){
			var d = new Date(date);
			d.setDate(d.getDate()-6);
			$("#startTime2").val(GetDateFormatStr(d));
		},
		okfun:function(date){
			var d = new Date(date);
			d.setDate(d.getDate()-6);
			$("#startTime2").val(GetDateFormatStr(d));
		}
	};
	jeDate(start2);
	jeDate(end2);
	var et = new Date();
	et.setDate(et.getDate()-6);
	$("#startTime2").val(GetDateFormatStr(et));
	
	//设置时间插件(月)
	var start3 = {
		dateCell:"#startTime3",
		format:"YYYY-MM-DD",
		isinitVal:true,
		isTime:false, //isClear:false,
		minDate:"2000-01-01",
		maxDate:jeDate.now(-29),
		choosefun:function(date){
			var d = new Date(date);
			d.setDate(d.getDate()+29);
			$("#endTime3").val(GetDateFormatStr(d));
		},
		okfun:function(date){
			var d = new Date(date);
			d.setDate(d.getDate()+29);
			$("#endTime3").val(GetDateFormatStr(d));
		}
	 };
	 var end3 = {
		dateCell:"#endTime3",
		format:"YYYY-MM-DD",
		isinitVal:true,
		isTime:false, //isClear:false,
		minDate:"2000-01-01 00:00:00",
		maxDate:jeDate.now(0),
		choosefun:function(date){
			var d = new Date(date);
			d.setDate(d.getDate()-29);
			$("#startTime3").val(GetDateFormatStr(d));
		},
		okfun:function(date){
			var d = new Date(date);
			d.setDate(d.getDate()-29);
			$("#startTime3").val(GetDateFormatStr(d));
		}
	};
	jeDate(start3);
	jeDate(end3);
	var et = new Date();
	et.setDate(et.getDate()-29);
	$("#startTime3").val(GetDateFormatStr(et));
	
	$("#query").click(function(){
		showChart(type);
	});
	
	morris1 = new Morris.Line(line1);
	showChart('day');
});
</script>

<body style="background-color:#f5f5f5">
	
	<div class="panel-heading" style="font-size:16px">
		<div>DeviceId：<%=deviceId%>&nbsp;&nbsp;&nbsp;&nbsp;Type：<%=pageInfo%></div>
		<div>&nbsp;</div>
		开始时间：<div style="display:inline-block;width:15%;">
					<input id="startTime1" type="text" size=16/>
				 	<input id="startTime2" type="text" size=16/> 
				 	<input id="startTime3" type="text" size=16/>
				 </div>
		结束时间：<div style="display:inline-block;width:15%;">
				 	<input id="endTime1" type="text" size=16/>
				 	<input id="endTime2" type="text" size=16/>
				 	<input id="endTime3" type="text" size=16/>	
				 </div>
		<button id="query">查   询</button>
	    <div>&nbsp;</div>
	</div>
	
	<div class="tab">	
		<div class="tab-head">
       		<ul class="tab-nav">
         		<li id="tab1" class="active"><a href="#tab-set1">&nbsp;&nbsp;&nbsp;&nbsp;日&nbsp;&nbsp;&nbsp;&nbsp;</a></li>
          		<li id="tab2"><a href="#tab-set2">&nbsp;&nbsp;&nbsp;&nbsp;周&nbsp;&nbsp;&nbsp;&nbsp;</a></li>
         		<li id="tab3"><a href="#tab-set3">&nbsp;&nbsp;&nbsp;&nbsp;月&nbsp;&nbsp;&nbsp;&nbsp;</a></li>
        	</ul>
      	</div>
    	<div class="tab-body">
    		<!-- 日 -->
      	 	<div class="tab-panel active" id="tab-set1">
	      		<div class="container-fluid">
		     		<div class="col-lg-12">
			    		<div class="panel panel-red">
							<div class="panel-heading">
								<h3 class="panel-title">&nbsp;</h3>
							</div>
							<div class="panel-body">
								<div id="morris-line-chart-day"></div>
							</div>
						</div>
			  		</div>
		   		</div>
	    	</div>	    	
	    	<!-- 周 -->
	 		<div class="tab-panel" id="tab-set2">
	 			<div class="container-fluid">
		     		<div class="col-lg-12">
			    		<div class="panel panel-red">
							<div class="panel-heading">
								<h3 class="panel-title">&nbsp;</h3>
							</div>
							<div class="panel-body">
								<div id="morris-line-chart-week"></div>
							</div>
						</div>
			  		</div>
		   		</div>
	 		</div>	
	 		
	 		<!-- 月 -->
	 		<div class="tab-panel" id="tab-set3">
	 			<div class="container-fluid">
		     		<div class="col-lg-12">
			    		<div class="panel panel-red">
							<div class="panel-heading">
								<h3 class="panel-title">&nbsp;</h3>
							</div>
							<div class="panel-body">
								<div id="morris-line-chart-month"></div>
							</div>
						</div>
			  		</div>
		   	   </div>	 		
	 	   </div>
	 	</div>
	</div>
</body>
</html>
