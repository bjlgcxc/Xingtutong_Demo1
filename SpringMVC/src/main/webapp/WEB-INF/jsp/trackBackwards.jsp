<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>My JSP 'track.jsp' starting page</title>

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
<style type="text/css">
#allmap {
	width: 100%;
	height: 85%;
	overflow: hidden;
	margin: 0;
	font-family: "微软雅黑";
}
</style>
<script type="text/javascript"
	src="https://webapi.amap.com/maps?v=1.3&key=39faf15680c306043954764664a660ab"></script>
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
	  
	  		//时间控件
	  		var times = [];
	  		
	  		//存放运动轨迹的坐标点
	  		 var pdatas = [];
	  		 
	  		  var animate1;//循环执行
	  		  
	  		  var m = 0;//坐标索引
	  		  
	  		  var runbutton = 3000;//运行速度
	  		  
	  		  var infoWindow = new AMap.InfoWindow({offset: new AMap.Pixel(0, -30)});
	  		  
	  		  var img;//存放图片
	  		  
	  		  var d = 0; //统计时间间隔数
	  		  
	  		  var positionSE = [];//存储间隔时间前后位置点
		 
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
		  });
		  
		  $(function() {
		    $( "#slider-range-max" ).slider({
		      range: "max",
		      min: 1,
		      max: 10,
		      value: 5,
		      slide: function( event, ui ) {
		        $( "#amount" ).val( ui.value );
		        runSpeed(ui.value);
		       
		      }
		    });
		    $( "#amount" ).val( $( "#slider-range-max" ).slider( "value" ));
		  });
		  
		  function queryData(){
		  
		    map1.clearMap();
		    times=[];
		    pdatas=[];
		    positionSE=[];
	        m = 0;
	        d = 0;
		  	
		  	var bng  = $('#start').val();
			var end = $('#end').val();
			
			var data1 = Date.parse(bng.replace(/-/g,   "/"));  
		    var data2 = Date.parse(end.replace(/-/g,   "/"));  
		    var datadiff = data2-data1;  
		    //var time = 1*24*60*60*1000;       
		    if(bng.length>0 && end.length>0){  
		          if(datadiff<0){  
		             msg("提示信息","开始时间应小于结束时间，请检查!"); 
		             return;  
		          }  
		    }
				
			var bngDate = new Date(bng);
            var endDate = new Date(end);
			var oneDay = 24*60*60*1000; // hours*minutes*seconds*milliseconds
			var diffDays = Math.abs((endDate.getTime() - bngDate.getTime())/(oneDay));
			
			var days = parseInt(diffDays);
			times.push(bng);
			if(days>0){
				for(var i=0;i<days;i++){
				    var date = GetDateStr(bngDate);
					bngDate = new Date(date);
					times.push(date);
				}
			}else{
			   times.push(end);
			}
			
			
			setTimeout(function(){
               gjcxPositionLine("GET","position/track/"+id+"?st="+times[d]+"&et="+times[d+1],status,name);//位置轨迹线-一次性展现24小时的轨迹
            },1000); 
		  }
		  
		  //轨迹查询-位置连线-轨迹
			function gjcxPositionLine(type,url,iconSkin,name){
				$.ajax({
					type: type,
					url: url,
					dataType:'json',
					contentType: 'application/json; charset=utf-8',
					success:function(data){
					    
	 					pdatas=[];
	 					if(data!=""){
	 					    for(var i=0,length=data.length;i<length;i++){
	 					    	var log =  data[i].longitude;
				            	var lat =  data[i].latitude;
				            	if(log!=undefined && lat!=undefined){
							       pdatas.push(data[i]);
							    }
	 					    }
	 					    setTimeout(function(){
				               runGJ(name,iconSkin);
				            },1000); 
			            }else{
			                 
			                 window.clearInterval(animate1);
			                 d++;
			                 if(d==times.length-1){
					          	msg("提示信息","运行结束");
					          	positionSE=[];
					          	return;
					         }
					         m=0;
			                 gjcxPositionLine("GET","position/track/"+id+"?st="+times[d]+"&et="+times[d+1],status,name);
			            }
					},
					error:function(result){
						var str=result.responseText;
			   				msg("系统异常",str);
					}
				});
			}
		
		
		function runGJ(name,status){
			if(pdatas != ""){
				var log0 =  pdatas[0].longitude;
			    var lat0 =  pdatas[0].latitude;
		        var toGps0 = GPS.gcj_encrypt(lat0,log0);
			    
			    if(positionSE.length==1){
			    	positionSE.push(new AMap.LngLat(toGps0.lon,toGps0.lat));
			    }
			    if(positionSE.length==2){
			    	var polyline = new AMap.Polyline({
						map:map1,
						path:positionSE,
						strokeColor:"#3366FF",//线颜色
						strokeOpacity:1,//线透明度
						strokeWeight:5,//线宽
						strokeStyle:"solid"//线样式
					  });  
					  polyline.setMap(map1);
					  positionSE=[];
			    }
			    
			    
			    var marker = new AMap.Marker({
					icon : "", //复杂图标
					position : new AMap.LngLat(toGps0.lon,toGps0.lat),
					map:map1
					//offset : { x : -14, y : -28 }
			    });
	           
	            marker.setMap(map1); 
	            map1.setCenter(new AMap.LngLat(toGps0.lon,toGps0.lat));
	            
               if(pdatas[0].ns=="南纬"){
			       lat0 = "南纬"+lat0;
			    }
			    if(pdatas[0].ns=="北纬"){
			       lat0 = "北纬"+lat0;
			    }
			    if(pdatas[0].ew=="东经"){
			       log0 = "东经"+log0;
			    }
			    if(pdatas[0].ew=="西经"){
			       log0 = "西经"+log0;
			    }
	            
	            var info = ""; 
				info+="<div style=\"padding:0px 0px 0px 4px;\"><b>"+name+"</b>"+"<br/>";
				info+="经度 :"+log0+"<br/>";
				info+="纬度 :"+ lat0+"<br/>";
				info+="速度 :"+pdatas[0].speed+"KM/H"+"<br/>";
				info+="高程 :"+pdatas[0].high+"M"+"<br/>";
				info+="数据来源 :"+pdatas[0].datasource+"<br/>";
				info+="位置采样时间 :"+pdatas[0].capturetime+"<br/>";
			    info+="</div>";  
			    info = info.replace(undefined, "0");
	            marker.content=info;
				marker.name = name;
	            var infoWindow = new AMap.InfoWindow({
				    isCustom: true,  //使用自定义窗体
				    content: createInfoWindow(marker.name, marker.content),
				    offset: new AMap.Pixel(100, -45)
		        });
		        infoWindow.open(map1, new AMap.LngLat(toGps0.lon,toGps0.lat)); 
		       
				load();
				
			    
		   }
		}
		
		
		function load(){
		     animate1=window.setInterval(function () {
		      
		       if(m==pdatas.length-1){
		              
			          window.clearInterval(animate1);
			          d++;
			          if(d==times.length-1){
			          	
			          	msg("提示信息","运行结束");
			          	positionSE=[];
			          	return;
			          } 
			          
			          //将上一次的时间段的最后一个位置信息和下一个时间段的位置信息也保存
			          var log3 =  pdatas[m].longitude;
					  var lat3 =  pdatas[m].latitude;
				      var toGps3 = GPS.gcj_encrypt(lat3,log3);
			          positionSE.push(new AMap.LngLat(toGps3.lon,toGps3.lat));
			          m=0;
			          gjcxPositionLine("GET","position/track/"+id+"?st="+times[d]+"&et="+times[d+1],status,name);//位置轨迹线-一次性展现24小时的轨迹
			          return;
			      }
		       
			       
			       if(m<pdatas.length){
				      var log =  pdatas[m].longitude;
					  var lat =  pdatas[m].latitude;
				      var toGps = GPS.gcj_encrypt(lat,log);
				      var ps=[];
				      ps.push(new AMap.LngLat(toGps.lon,toGps.lat));
				      m++;//找到下一个位置点
				      
				      var log2 =  pdatas[m].longitude;
					  var lat2 =  pdatas[m].latitude;
				      var toGps2 = GPS.gcj_encrypt(lat2,log2);
				      ps.push(new AMap.LngLat(toGps2.lon,toGps2.lat));
					 
				      
				      
				      if(ps.length==2){
				          var polyline = new AMap.Polyline({
							map:map1,
							path:ps,
							strokeColor:"#3366FF",//线颜色
							strokeOpacity:1,//线透明度
							strokeWeight:5,//线宽
							strokeStyle:"solid"//线样式
						  });  
						  polyline.setMap(map1);
						  var marker = new AMap.Marker({
								icon : "", //复杂图标
								position : new AMap.LngLat(toGps2.lon,toGps2.lat),
								map:map1,
								//offset : { x : -14, y : -28 }
						  });
						  marker.setMap(map1);
						  map1.setCenter(new AMap.LngLat(toGps2.lon,toGps2.lat)); 
						  
						if(pdatas[m].ns=="南纬"){
					       lat2 = "南纬"+lat2;
					    }
					    if(pdatas[m].ns=="北纬"){
					       lat2 = "北纬"+lat2;
					    }
					    if(pdatas[m].ew=="东经"){
					       log2 = "东经"+log2;
					    }
					    if(pdatas[m].ew=="西经"){
					       log2 = "西经"+log2;
					    }
			            
			            var info = ""; 
						info+="<div style=\"padding:0px 0px 0px 4px;\"><b>"+name+"</b>"+"<br/>";
						info+="经度 :"+log2+"<br/>";
						info+="纬度 :"+ lat2+"<br/>";
						info+="速度 :"+pdatas[m].speed+"KM/H"+"<br/>";
						info+="高程 :"+pdatas[m].high+"M"+"<br/>";
						info+="数据来源 :"+pdatas[m].datasource+"<br/>";
						info+="位置采样时间 :"+pdatas[m].capturetime+"<br/>";
					    info+="</div>";  
					    info = info.replace(undefined, "0");
		        		            
			            marker.content=info;
						marker.name = name;
			            var infoWindow = new AMap.InfoWindow({
						    isCustom: true,  //使用自定义窗体
						    content: createInfoWindow(marker.name, marker.content),
						    offset: new AMap.Pixel(100, -45)
				        });
				        infoWindow.open(map1, new AMap.LngLat(toGps2.lon,toGps2.lat)); 
				      }
			        }  
			    }, runbutton);
		}
		
		
		
		
		function runSpeed(value){
			window.clearInterval(animate1);
			if(value>0 && value<=2){
				runbutton = "3000";
				if(d==times.length-1){
					return;
				}
				load();
			}else if(value>2 && value<=4){
				runbutton = "2000";
				if(d==times.length-1){
					return;
				}
				load();
			}else if(value>4 && value<=6){
				runbutton = "1000";
				if(d==times.length-1){
					return;
				}
				load();
			}else if(value>6 && value<=8){
				runbutton = "800";
				if(d==times.length-1){
					return;
				}
				load();
			}else if(value>8 && value<=10){
				runbutton = "400";
				if(d==times.length-1){
					return;
				}
				load();
			}
		}	
		  
		  //获取当前时间+24小时
		function GetDateStr(bngDate) {
		     var date =bngDate;
		    var seperator1 = "-";
		    var seperator2 = ":";
		    var month = date.getMonth() + 1;
		    var hours = date.getHours();
		    var minutes = date.getMinutes();
		    var strDate = date.getDate()+1;
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
	<div>
		开始时间:<input id="start" type="text" style="width:160px" />结束时间:<input
			id="end" type="text" style="width:160px" />
		<button onclick="queryData()">查询</button>
	</div>
	<p>
		<label for="amount">当前速度：</label> <input type="text" id="amount"
			style="border:0; color:#f6931f; font-weight:bold;">
	</p>
	<div id="slider-range-max"></div>
	<div id="" style="height:10px"></div>
	<div id="allmap"></div>
</body>

<script type="text/javascript">
    
    var id = '<%=request.getParameter("id")%>';
    var name = '<%=request.getParameter("name")%>';
    var status = '<%=request.getParameter("status")%>';
   
	//var map1 = new AMap.Map('allmap');
	var map1 = new AMap.Map('allmap', {
        resizeEnable: true,
        zoom:11,
        center: [112.549248,37.857014]
        
    }); 
	//加载比例尺插件
	map1.plugin(["AMap.Scale"], function(){		
		scale = new AMap.Scale();
		map1.addControl(scale);
	});
	//在地图中添加ToolBar插件
	map1.plugin(["AMap.ToolBar"],function(){		
	    toolBar = new AMap.ToolBar();
	    map1.addControl(toolBar);		
	});
	//在地图中添加鹰眼插件
	map1.plugin(["AMap.OverView"],function(){
		//加载鹰眼
		//初始化隐藏鹰眼
		overView = new AMap.OverView({
			visible:false 
		});
		map1.addControl(overView);
	});  

  </script>
</html>
