<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<% 
	String deviceId = request.getParameter("deviceId");
	String pageInfo = "卡路里";
%>

<html>
<head>
<title>Calorie</title>

<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">

<link href="css/bootstrap.min.css" rel="stylesheet">
<link href="css/dataTables.bootstrap.css" rel="stylesheet">

<script src="js/jquery-2.2.3.js"></script>
<script src="js/bootstrap.min.js"></script>
<script src="js/jquery.dataTables.min.js"></script>
<script src="js/dataTables.bootstrap.min.js"></script>
<script type="text/javascript" src="js/jedate.js"></script>
</head>

<script>

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
		if (hours >= 0 && hours <= 9) {
			hours = "0" + hours;
		}
		if (minutes >= 0 && minutes <= 9) {
			minutes = "0" + minutes;
		}
		if (seconds >= 0 && seconds <= 9) {
			seconds = "0" + seconds;
		}
		var formatDate = date.getFullYear() + seperator1 + month + seperator1
				+ strDate + " " + hours + seperator2 + minutes + seperator2
				+ seconds;

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
		var formatDate = date.getFullYear() + seperator1 + month + seperator1
				+ strDate;

		return formatDate;
	}

	//表格初始化
	dt = $('#dataTable').dataTable({
		"bDestroy" : true
	});
	function toDayView(startTime, endTime) {
		$("#change").text('>>多日数据视图');
		$.ajax({
			url : "sport/" + <%=deviceId%> + "/getCalorieInfo?duration=1&startTime=" + startTime + "&endTime="+endTime,
     		type:"get",
			dataType:'json',
			contentType: 'application/json;charset=utf-8',
			success:function(data){ 
				dt.fnClearTable();
				dt.fnDestroy();
			
				var str='';
				str += "<tr>" + 
				   	    "<th>ID</th>" +
						"<th>运动开始时间</th>" + 
						"<th>运动结束时间</th>" + 
						"<th>运动持续时间</th>" + 
						"<th>运动类型</th>" + 
						"<th>消耗热量</th>" +
					"</tr>";	
				$("#thead").html(str);
			
				str='';
				for(i=0;i<data.length;i++){	
					str += "<tr>" +
							"<td>" + (i+1) + "</td>" + 
				            "<td>" + data[i].startTime + "</td>" +
				       		"<td>" + data[i].endTime + "</td>" +
				       		"<td>" + data[i].duration + "</td>" + 
				       		"<td>" + data[i].type + "</td>" +
				       		"<td>" + data[i].calorie + "</td>" +
				       	"</tr>";
				} 
				$("#tbody").html(str);
				dt = $('#dataTable').dataTable({"bDestroy":true});
      	   }	
       });
	}
	function toDaysView(startTime,endTime){
		$("#change").text('>>单日数据视图');
		$.ajax({
	     	url:"sport/" + <%=deviceId%> + "/getCalorieInfo?duration=2&startTime=" + startTime + "&endTime="+endTime,
	     	type:"get",
			dataType:'json',
			contentType: 'application/json;charset=utf-8',
			success:function(data){ 
				dt.fnClearTable();
				dt.fnDestroy();
				
				var str='';
				str += "<tr>" + 
					   	    "<th>ID</th>" +
					   	    "<th>日期</th>" + 
							"<th>步行消耗</th>" + 
							"<th>跑步消耗</th>" + 
							"<th>总消耗</th>" +  
						"</tr>";	
				$("#thead").html(str);
				
				str='';
				for(i=0;i<data.length;i++){	
					str += "<tr>" +
								"<td>" + (i+1) + "</td>" + 
					            "<td>" + data[i].date + "</td>" +
					       		"<td>" + data[i].walk + "</td>" +
					       		"<td>" + data[i].run + "</td>" +
					       		"<td>" + data[i].total + "</td>" +  
					       "</tr>";
				}
				$("#tbody").html(str);
				dt = $('#dataTable').dataTable({"bDestroy":true});
	       }	
        });
	}

	//设置时间插件
	var start1 = {
		dateCell : "#startTime1",
		format : "YYYY-MM-DD  hh:mm:ss",
		isinitVal : true,
		isTime : true, //isClear:false,
		minDate : "2000-01-01 00:00:00",
		maxDate : jeDate.now(-1),
		choosefun : function(date) {
			var d = new Date(date);
			d.setDate(d.getDate() + 1);
			$("#endTime1").val(GetDateTimeFormatStr(d));
		},
		okfun : function(date) {
			var d = new Date(date);
			d.setDate(d.getDate() + 1);
			$("#endTime1").val(GetDateTimeFormatStr(d));
		}
	};
	var end1 = {
		dateCell : "#endTime1",
		format : "YYYY-MM-DD hh:mm:ss",
		isinitVal : true,
		isTime : true, //isClear:false,
		minDate : "2000-01-01 00:00:00",
		maxDate : jeDate.now(0),
		choosefun : function(date) {
			var d = new Date(date);
			d.setDate(d.getDate() - 1);
			$("#startTime1").val(GetDateTimeFormatStr(d));
		},
		okfun : function(date) {
			var d = new Date(date);
			d.setDate(d.getDate() - 1);
			$("#startTime1").val(GetDateTimeFormatStr(d));
		}
	};
	function setDayTime(start, end) {
		jeDate(start);
		jeDate(end);
		var et = new Date();
		et.setDate(et.getDate() - 1);
		$("#startTime1").val(GetDateTimeFormatStr(et));
	}
	
	var flag = 0;
	var start2 = {
		dateCell : "#startTime2",
		format : "YYYY-MM-DD",
		isinitVal : true,
		isTime : false, //isClear:false,
		minDate : "2000-01-01",
		maxDate : jeDate.now(0),
		choosefun : function(date) {
			if (flag == 1) {
				flag = 0;
				return;
			}
			end2.minDate = date;
			jeDate(end2);
			flag = 1;
		},
		okfun : function(date) {
			if (flag == 1) {
				flag = 0;
				return;
			}
			end2.minDate = date;
			jeDate(end2);
			flag = 1;
		}
	};

	var end2 = {
		dateCell : "#endTime2",
		format : "YYYY-MM-DD",
		isinitVal : true,
		isTime : false, //isClear:false,
		minDate : "2000-01-01",
		maxDate : jeDate.now(0),
		choosefun : function(date) {
			if (flag == 1) {
				flag = 0;
				return;
			}
			start2.maxDate = date;
			jeDate(start2);
			flag = 1;
		},
		okfun : function(date) {
			if (flag == 1) {
				flag = 0;
				return;
			}
			start2.maxDate = date;
			jeDate(start2);
			flag = 1;
		}
	};

	function setDaysTime(start, end) {
		jeDate(start);
		jeDate(end);
		$("#startTime2").val(GetDateFormatStr(new Date(new Date().getTime() - 6 * 24 * 60 * 60 * 1000)));
	}

	$(document).ready(function() {
		$("#startTime2").hide();
		$("#endTime2").hide();
		//时间插件初始化
		setDayTime(start1, end1);
		setDaysTime(start2, end2);
		//表格初始化
		toDayView($("#startTime1").val(), $("#endTime1").val());
		//切换视图事件
		var type = 'day';
		$("#change").click(function() {
			if (type == 'day') {
				toDaysView($("#startTime2").val(), $("#endTime2").val());
				$("#startTime1").hide();
				$("#endTime1").hide();
				$("#startTime2").show();
				$("#endTime2").show();
				type = 'week';
			} else if (type == 'week') {
				toDayView($("#startTime1").val(), $("#endTime1").val());
				$("#startTime2").hide();
				$("#endTime2").hide();
				$("#startTime1").show();
				$("#endTime1").show();
				type = 'day';
			}
		});
		//查询事件
		$("#query").click(function() {
			if (type == 'day') {
				toDayView($("#startTime1").val(), $("#endTime1").val());
			} else {
				toDaysView($("#startTime2").val(), $("#endTime2").val());
			}
		});

	});
</script>

<body>
	<div id="page-wrapper">
		<div class="row">
			<div class="col-lg-12">
				<div class="panel panel-default">
					<div class="panel-heading" style="font-size:16px">
						<div>DeviceID：<%=deviceId%>&nbsp;&nbsp;&nbsp;&nbsp;Type：<%=pageInfo%></div>
						<br/> 
						开始时间：<input id="startTime1" size=16 readonly></input>
						<input id="startTime2" size=16 readonly></input>&nbsp;&nbsp;&nbsp;&nbsp;
						结束时间：<input id="endTime1" size=16 readonly></input> 
						<input id="endTime2" size=16 readonly></input>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<button id="query">查 询</button>
						<div style="float:right">
							<a id="change" href="#"></a>
						</div>
					</div>
					<div class="panel-body" id="table">
						<div class="dataTable_wrapper">
							<table class="table table-striped table-bordered table-hover" id="dataTable">
								<thead id="thead">
								</thead>
								<tbody id="tbody">
								</tbody>
							</table>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
