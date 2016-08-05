<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<% 
    String context = request.getContextPath();
    request.setAttribute("context",context);
    request.setAttribute("page", "config");
    
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
    <title>设置</title>
    
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
    <meta name="renderer" content="webkit">
    
    <link rel="stylesheet" href="css/pintuer.css">
    <link rel="stylesheet" href="css/admin.css">
    <link type="image/x-icon" href="/favicon.ico" rel="shortcut icon" />
    <link href="/favicon.ico" rel="bookmark icon" />
    
    <script src="js/jquery-2.2.3.js"></script>
    <script src="js/jquery-form.js"></script>
    <script src="js/pintuer.js"></script>
    <script src="js/respond.js"></script>
    <script src="js/admin.js"></script>
    <script src="js/layer.js"></script> 
</head>

<script type='text/javascript'>
	if('<%=loginState%>' == 'notLogin'){
	   	 location.href = "login.html";
	}
	
	var type=1;
	$(document).ready(function(){
			
		$("[id^=submit]").each(function(){
			$(this).click(function(){
				if($("#deviceId").val()==''){
					layer.msg('Tips：请先填写设备ID');
				}
			});
		});
		$("input").focus(function(){
			$(".info").html('&nbsp;');
		});
			
		//form1表单提交
		$("#submit1").click(function(){
			var $deviceId = $("#deviceId").val();
			var $minutes = $("#minutes").val();			
			if($minutes=='' || $("#deviceId").val()==''){
				return;
			}
			else{
				var m = Number($minutes);		
				if(isNaN(m)){
					$(".info").text('格式错误!');
					$("#form1").resetForm();
					return;
				}
				else{
					$.ajax({url:"instruction/"+ $deviceId + "/saveSampleInterval",
							data:{minutes:$minutes},
							type:"post",
							success:function(data){
							},
							error:function(){
						  	  notice(type);
							}
						}
					);
					layer.alert('提交成功!');
					$("#form1").resetForm();					
				}
			}
		});
		
		//form2表单提交
		$("#submit2").click(function(){
		    var $deviceId = $("#deviceId").val();
			var $uploadEverytime = $("#uploadEverytime").val();
			if($deviceId=='' || $uploadEverytime==''){
				return ;
			}
			else{
				var i = Number($uploadEverytime);
				if(isNaN(i)){
					$(".info").text('格式错误!');
					$("#form2").resetForm();
					return;
				}
				else{
					$.ajax({url:"instruction/"+ $deviceId + "/saveUploadEverytime",
						data:{uploadEverytime:$uploadEverytime},
						type:"post",
						success:function(data){
						},
						error:function(){
						   notice(type);
						}
					  }
					);
					layer.alert('提交成功');
					$("#form2").resetForm();
				}
			}
		});
			
		//form3表单提交
		$("#submit3").click(function(){
		    var $deviceId = $("#deviceId").val();
			var $locateInterval = $("#locateInterval").val();
			var $locateTimes = $("#locateTimes").val();
			if($locateInterval=='' || $locateTimes=='' || $deviceId==''){
				return ;
			}
			else{
				var i = Number($locateInterval);
				var t = Number($locateTimes);
				if(isNaN(i) || isNaN(t)){
					$(".info").text('格式错误!');
					$("#form3").resetForm();
					return;
				}
				else{
					$.ajax({url:"instruction/"+ $deviceId + "/saveLocateInfo",
							data:{locateInterval:$locateInterval,locateTimes:$locateTimes},
							type:"post",
							success:function(data){
							},
							error:function(){
						  		notice(type);
							}}
					);
					layer.alert('提交成功');
					$("#form3").resetForm();
				}
			}
		});
			
		//form4表单提交
		$("#submit4").click(function(){
		    var $deviceId = $("#deviceId").val();
			var $teleNumber = $("#teleNumber").val();
			if($teleNumber=='' || $("#deviceId").val()==''){
				return;
			}
			else{
				var myreg = /^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))+\d{8})$/; 
				if(!myreg.test($teleNumber)){
					$(".info").text('格式错误!');
					$("#form4").resetForm();
				}
				else{
					$.ajax({url:"instruction/"+ $deviceId + "/saveBasicInfo",
							data:{teleNumber:$teleNumber},
							type:"post",
							success:function(data){
							},
							error:function(){
						  	  notice(type);
							}}
					);
					layer.alert('提交成功');
					$("#form4").resetForm();
								
				}
			}
		});
		
		if($("#deviceId").val()!=''){
			notice(1);	
		}
		else{
			$("#alert").hide();
		}
		$("#deviceId").bind('input propertychange', 
			function(){	
				if(isNaN($("#deviceId").val()) || $("#deviceId").val()==''){
					$("#alert").hide();
					return;	
				}
				else{
					notice(type);
				}
				$("#alert").show();
			}
		); 
		
		$("#tab1").click(function(){type=1;if($("#deviceId").val()!='') notice(1);});
		$("#tab2").click(function(){type=2;if($("#deviceId").val()!='') notice(2);});
		$("#tab3").click(function(){type=3;if($("#deviceId").val()!='') notice(3);});
		$("#tab4").click(function(){type=4;if($("#deviceId").val()!='') notice(4);});
	
	});
	
	function notice(type){
		$.ajax({
			url:"config/" + $("#deviceId").val() + "/getConfigInfo",
			type:"get",
			dataType:'json',
			contentType: 'application/json;charset=utf-8',
			success:
				function(data){
					if(type==1){
						if(data.sampleInterval!=null){
							$("#text").text("数据采集间隔的最新设置为： " + data.sampleInterval + "秒");
						}
						else{
							$("#text").text("没有数据采集间隔的历史设置");
						}
					}
					else if(type==2){
						if(data.locateInterval!=null && data.locateTimes!=null){
							$("#text").text("数据上传的最新设置为： 每次上传 " + data.uploadEverytime + " 条数据");
						}
						else{
							$("#text").text("没有数据上传的历史设置");
						}
					}
					else if(type==3){
						if(data.locateInterval!=null && data.locateTimes!=null){
							$("#text").text("紧急定位的最新设置为： 间隔" + data.locateInterval + "秒,采集" + data.locateTimes + "次");
						}
						else{
							$("#text").text("没有紧急定位的历史设置");
						}
					}
					else if(type==4){
						if(data.teleNumber!=null){
							$("#text").text("短信通知的号码设置为： " + data.teleNumber);
						}
						else{
							$("#text").text("没有设置短信通知号码");
						}
					}
			    },
			error:
			     function(){
			      	if(type==1){
			      		$("#text").text("没有数据采集间隔的历史设置");
			      	}
			      	else if(type==2){
			      		$("#text").text("没有数据上传的历史设置");
			      	}
			      	else if(type==3){
			      		$("#text").text("没有紧急定位的历史设置");
			      	}
			      	else if(type==4){
			      		$("#text").text("没有设置短信通知号码");
			      	}
			     }
		    }
		);
	}
</script>

<body>
<%@include file="naviBar.jsp"%>

<!-- Tab形式  -->
<div class="admin" style="font-size:15px">
	<div>
		<div class="label" style="display:inline-block;width:5.5%"><label for="readme">设备编号:</label></div>
    	<div class="field" style="display:inline-block;width:16%;">
    		<input class="input_" type="text" id="deviceId" value="<%=deviceId%>" placeholder="请填入设备编号"/>
    	</div>	
    </div>
    <br/>
    <br/>
	<div class="alert alert-yellow" id="alert" style="width:30%;font-size:15px;">
		<span class="close"></span>
		<strong id="text"></strong>
	</div>
    <div class="tab">	
      	<div class="tab-head">
        	<strong>设备设置</strong>
        	<ul class="tab-nav">
          		<li class="active" id="tab1"><a href="#tab-set1">&nbsp;手环采集&nbsp;</a></li>
          		<li><a href="#tab-set2" id="tab2">&nbsp;位置采集&nbsp;</a></li>
          		<li><a href="#tab-set3" id="tab3">&nbsp;紧急定位&nbsp;</a></li>
          		<li><a href="#tab-set4" id="tab4">&nbsp;基本信息&nbsp;</a></li>
        	</ul>
      	</div>
      	<div class="tab-body">
        <br/>
        <!-- 数据采集 -->
        <div class="tab-panel active" id="tab-set1">
        	<br/>
        	<form method="post" class="form-x" id="form1">       
                <div class="form-group">
                    <div class="label"><label for="desc">采集间隔(分钟) :</label></div>
                    <div class="field" style="width:15%;">
                    	<input type="text" class="input" id="minutes" name="minutes" placeholder="请填入数据采集间隔" />
                    </div>
                </div> 
            	<div style="width:20%;text-align:right"><font color="red"><p class="info">&nbsp;</p></font></div>
                <div>
                	<div style="float:left;width:20%;text-align:right"><button class="button bg-main" id="submit1" type="button">提交</button></div>
                	<div style="float:left;padding:0px 0px 0px 15px"><button class="button bg-main" type="reset">重置</button></div>
				</div>
            </form>
        </div>
        
        <!-- 数据上传 -->
        <div class="tab-panel" id="tab-set2">
        	<br/>
        	<form method="post" class="form-x" id="form2">         
                <div class="form-group">
                    <div class="label"><label for="desc">每次上传条数 : </label></div>
                    <div class="field" style="width:15%;">
                    	<input type="text" class="input" id="uploadEverytime" name="uploadEverytime" placeholder="请填入每次上传数据条数" />
                    </div>
                </div>          
            	<div style="width:20%;text-align:right"><font color="red"><p class="info">&nbsp;</p></font></div>
                <div>
                	<div style="float:left;width:20%;text-align:right"><button class="button bg-main" id="submit2" type="button">提交</button></div>
                	<div style="float:left;padding:0px 0px 0px 15px"><button class="button bg-main" type="reset">重置</button></div>
				</div>
            </form>
        </div>

		<!-- 紧急定位 -->
        <div class="tab-panel" id="tab-set3">
        	<br/>
        	<form method="post" class="form-x" id="form2">         
                <div class="form-group">
                    <div class="label"><label for="desc">定位间隔(秒) : </label></div>
                    <div class="field" style="width:15%;">
                    	<input type="text" class="input" id="locateInterval" name="locateInterval" placeholder="请填入间隔时间" />
                    </div>
                </div>
                <div class="form-group">
                    <div class="label"><label for="desc">定位次数 :</label></div>
                    <div class="field" style="width:15%;">
                    	<input type="text" class="input" id="locateTimes" name="locateTimes" placeholder="请填入定位次数" />
                    </div>
                </div>            
            	<div style="width:20%;text-align:right"><font color="red"><p class="info">&nbsp;</p></font></div>
                <div>
                	<div style="float:left;width:20%;text-align:right"><button class="button bg-main" id="submit3" type="button">提交</button></div>
                	<div style="float:left;padding:0px 0px 0px 15px"><button class="button bg-main" type="reset">重置</button></div>
				</div>
            </form>
        </div>
        
        <!-- 基本信息 -->
        <div class="tab-panel" id="tab-set4">
        	<br/>
        	<form method="post" class="form-x" id="form3">         
                <div class="form-group">
                    <div class="label"><label for="desc">Tele : </label></div>
                    <div class="field" style="width:15%;">
                    	<input type="text" class="input" id="teleNumber" placeholder="请填入短信通知号码" />
                    </div>
                </div> 
                <div style="width:20%;text-align:right"><font color="red"><p class="info">&nbsp;</p></font></div>  
                <div>
                	<div style="float:left;width:20%;text-align:right"><button class="button bg-main" id="submit4" type="button">提交</button></div>
                	<div style="float:left;padding:0px 0px 0px 15px"><button class="button bg-main" type="reset">重置</button></div>
				</div>
            </form>
        </div> 
      </div>
    </div>
</div>
</body>
</html>