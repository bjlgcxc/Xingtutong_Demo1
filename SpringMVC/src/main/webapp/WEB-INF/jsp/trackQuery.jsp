<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'track_query.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
    <link rel="stylesheet" type="text/css" href="css/jquery-ui.css">
	<script src="js/jquery-2.2.3.js" type="text/javascript"></script>
	<!--地图 -->
	<style type="text/css">#allmap {width: 100%;height: 95%;overflow: hidden;margin:0;font-family:"微软雅黑";}</style>
	<script type="text/javascript" src="https://webapi.amap.com/maps?v=1.3&key=39faf15680c306043954764664a660ab"></script>
    <script src="js/jquery-ui-1.10.3.custom.js" type="text/javascript"></script> 
	<!--时间控件 -->
    <script src="js/jquery-ui-timepicker-addon.js"></script>
	<script src="js/jquery.ui.datepicker-zh-CN.js"></script>
	<script src="js/jquery-ui-timepicker-zh-CN.js"></script>	  
	<!-- 消息 --> 
	<script src="js/msg/jquery.messager.js"></script>
	<script src="js/brower.js"></script>
	<!--自定义 -->
	<link rel="stylesheet" type="text/css" href="css/window.css">
	<script src="js/map.js" type="text/javascript"></script>
	<script src="js/common.js" type="text/javascript"></script>
	<script type="text/javascript">
		
		var infoWindow = new AMap.InfoWindow({offset: new AMap.Pixel(0, -30)});
		
		 $(function() {
		    $( "#start" ).datetimepicker({
		        timeFormat: "HH:mm:ss",
                dateFormat: "yy-mm-dd",
                 changeMonth: true,
                  changeYear: true
		    });
		    $( "#end" ).datetimepicker({
		        timeFormat: "HH:mm:ss",
                dateFormat: "yy-mm-dd",
                 changeMonth: true,
                changeYear: true          

		    }); 
		    
		    var nowtime = getNowFormatDate(); 
 			var starttime = GetDateStr();
	        $('#start').val(starttime);
	        $('#end').val(nowtime); 
		  });
			
		
		//轨迹查询时间段窗口
		function gjcx(){
			var st = $('#start').val();
			var et = $('#end').val();
			
			//判断开始时间间隔是否小于一天
			var data1 = Date.parse(st.replace(/-/g,   "/"));  
		    var data2 = Date.parse(et.replace(/-/g,   "/"));  
		    var datadiff = data2-data1;  
		    var time = 1*24*60*60*1000;       
		    if(st.length>0 && et.length>0){  
		          if(datadiff<0||datadiff>time){  
		             msg("提示信息","开始时间应小于结束时间并且间隔小于1天，请检查!"); 
		             return;  
		          }  
		    }
		    //$('#dialog').dialog('close');
		    
		    gjcxPositionLine("GET","position/track/"+id+"?st="+st+"&et="+et,status,name);//位置轨迹线-一次性展现24小时的轨迹
		     
		      
		}
		//轨迹查询窗口关闭
		/* function gjcxClose(){
			$('#dialog').dialog('close'); 
		} */
		
		
				//轨迹查询-位置连线-轨迹
		function gjcxPositionLine(type,url,iconSkin,name){
				
			map3.clearMap();
			
			$.ajax({
				type: type,
				url: url,
				dataType:'json',
				contentType: 'application/json; charset=utf-8',
				success:function(data){
 					if(data!=""){
 					    var pdatas = [];
 					     
 					    for(var i=0,length=data.length;i<length;i++){
 					    	var log =  data[i].longitude;
			            	var lat =  data[i].latitude;
			            	if(log!=undefined && lat!=undefined){
						     
							     var toGps = GPS.gcj_encrypt(lat,log);
							     
			               
				                 //if(iconSkin=="icon01" ||iconSkin=="icon02"){
				                     if(data[i].datasource=="卫星"){
					                     img = "img/222.png";
						             }else if(data[i].datasource=="WIFI"){
						                 img = "img/444.png";
						             }else{
						                 img = "img/111.png";
						             }
				                // }else{
				                 //    img = "img/333.png";
				                // }
						     
							    var marker = new AMap.Marker({                    
					              icon:img,  
					              position:new AMap.LngLat(toGps.lon,toGps.lat),
					             
					              offset : { x : -10, y : -25 }
					            });  
					            
						        marker.setMap(map3);  //在地图上添加点 
						        
						       pdatas.push(new AMap.LngLat(toGps.lon,toGps.lat));
						        
						        
						        
							    if(data[i].ns=="南纬"){
							       lat = "南纬"+lat;
							    }
							    if(data[i].ns=="北纬"){
							       lat = "北纬"+lat;
							    }
							    if(data[i].ew=="东经"){
							       log = "东经"+log;
							    }
							    if(data[i].ew=="西经"){
							       log = "西经"+log;
							    }
							    
							    var info = ""; 
								info+="<div style=\"padding:0px 0px 0px 4px;\"><b>"+name+"</b>"+"<br/>";
								info+="经度 :"+log+"<br/>";
								info+="纬度 :"+ lat+"<br/>";
								info+="速度 :"+data[i].speed+"KM/H"+"<br/>";
								info+="高程 :"+data[i].high+"M"+"<br/>";
								info+="数据来源 :"+data[i].datasource+"<br/>";
								info+="位置采样时间 :"+data[i].capturetime+"<br/>";
							    info+="</div>";  
							    info = info.replace(undefined, "0");
							    
							
							    marker.content = info;
								marker.on('click', markerClick);
								//marker.emit('click', {target: marker});
					            
						    }else{
				                msg("提示信息","无位置信息！");
						    }
 					    }
 					    
 					    var polyline = new AMap.Polyline({
					        path: pdatas,          //设置线覆盖物路径
					        strokeColor: "#3366FF", //线颜色
					        strokeOpacity: 1,       //线透明度
					        strokeWeight: 5,        //线宽
					        strokeStyle: "solid",   //线样式
					        strokeDasharray: [10, 5] //补充线样式
					    });
					    polyline.setMap(map3);
			           
					 map3.setFitView();	
					 
					 
		            }else{
		            	 msg("提示信息","无数据！");
		            }
				},
				error:function(result){
					var str=result.responseText;
		   				msg("系统异常",str);
				}
			});
		}
		
		//信息弹窗
		function markerClick(e) {
	        infoWindow.setContent(e.target.content);
	        infoWindow.open(map3, e.target.getPosition());
	    } 
		
		
		//获取当前时间
		function getNowFormatDate() {
			var date = new Date();
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
			var currentdate = date.getFullYear() + seperator1 + month + seperator1 + strDate
			        + " " + hours + seperator2 + minutes
			        + seperator2 + seconds;
			return currentdate;
		} 
		//获取当前时间-24小时
		function GetDateStr() {
		      var date = new Date();
		    var seperator1 = "-";
		    var seperator2 = ":";
		    var month = date.getMonth() + 1;
		    var hours = date.getHours();
		    var minutes = date.getMinutes();
		    var strDate = date.getDate()-1;
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
		    var currentdate = date.getFullYear() + seperator1 + month + seperator1 + strDate
		            + " " + hours + seperator2 + minutes
		            + seperator2 + seconds;
		    return currentdate;
		}
	</script>
  </head>
  
  <body>
  	<br/>
    <div>开始时间:<input class="input" id="start" type="text" style="width:140px"/>&nbsp;&nbsp;&nbsp;
    	  结束时间:<input class="input" id="end" type="text" style="width:140px"/>&nbsp;&nbsp;&nbsp;
    <button onclick="gjcx()">查询</button></div>
  	<div id="" style="height:10px"></div>
    <div id="allmap"></div>
  </body>
  
  <script type="text/javascript">
    
    var id = '<%=request.getParameter("id")%>';
    var name = '<%=request.getParameter("name")%>';
    var status = '<%=request.getParameter("status")%>';
   
    //var map3 = new AMap.Map('allmap'); 
	var map3 = new AMap.Map('allmap', {
        resizeEnable: true,
        zoom:10,
        center: [112.549248,37.857014]
        
    });
	//加载比例尺插件
	map3.plugin(["AMap.Scale"], function(){		
		scale = new AMap.Scale();
		map3.addControl(scale);
	});
	//在地图中添加ToolBar插件
	map3.plugin(["AMap.ToolBar"],function(){		
	    toolBar = new AMap.ToolBar();
	    map3.addControl(toolBar);		
	});
	//在地图中添加鹰眼插件
	map3.plugin(["AMap.OverView"],function(){
		//加载鹰眼
		//初始化隐藏鹰眼
		overView = new AMap.OverView({
			visible:false 
		});
		map3.addControl(overView);
	});  

  </script>
</html>
