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
	
	$(document).ready(function(){
		//submit按钮的点击事件
		$("[id^=submit]").each(function(){
			$(this).click(function(){
				if($("#text").text()=='Tips：请填写设备编号'){
					$("#deviceId").focus();
					$("#text").fadeOut();
					$("#text").fadeIn();
				}
				if($("#text").text()=='Error：不存在的设备编号'){
					$("#deviceId").focus();
					$("#text").fadeOut();
					$("#text").fadeIn();
				}
			});
			$(this).mousedown(function(){
				setTimeout(function(){},300);
			});
		});
		//form1表单提交事件
		$("#submit1").click(function(){
			var deviceId = $("#deviceId").val();
			var braceletInterval = $("#braceletInterval").val();			
			var braceletUpload = $("#braceletUpload").val();
			if(deviceId=='' || (braceletInterval=='' && braceletUpload=='')){
				return;
			}
			else{
				var m = Number(braceletInterval);
				var n = Number(braceletUpload);
				if(isNaN(m) || isNaN(n)){			
					return;
				}
				else{
					$.ajax({
						url:"instruction/"+ deviceId + "/saveBraceletSample",
						data:{"braceletInterval":braceletInterval,"braceletUpload":braceletUpload},
						type:"post",
						success:function(){
							layer.alert('提交成功!');
							check();		
						},
						error:function(){
						}
					});				
				}
			}
		});	
		//form2表单提交事件
		$("#submit2").click(function(){
			var deviceId = $("#deviceId").val();
			var locationInterval = $("#locationInterval").val();
			var locationUpload = $("#locationUpload").val();
			if(deviceId=='' || (locationInterval=='' && locationUpload=='')){
				return ;
			}
			else{
				var m = Number(locationInterval);
				var n = Number(locationUpload);
				if(isNaN(m) || isNaN(n)){
					return;
				}
				else{
					$.ajax({
						url:"instruction/"+ deviceId + "/saveLocationSample",
						data:{"locationInterval":locationInterval,"locationUpload":locationUpload},
						type:"post",
						success:function(data){
							layer.alert('提交成功');
							check();
						},
						error:function(){					 	
						}
					});
				}
			}
		});		
		//form3表单提交事件
		$("#submit3").click(function(){
		    var deviceId = $("#deviceId").val();
			var locateInterval = $("#locateInterval").val();
			var locateTimes = $("#locateTimes").val();
			if(deviceId=='' || (locateTimes=='' && locateInterval=='')){
				return ;
			}
			else{
				var m = Number(locateInterval);
				var n = Number(locateTimes);
				if(isNaN(m) || isNaN(n)){
					return;
				}
				else{
					$.ajax({
						url:"instruction/"+ deviceId + "/saveLocateInfo",
						data:{"locateInterval":locateInterval,"locateTimes":locateTimes},
						type:"post",
						success:function(data){
							layer.alert('提交成功');
							check();
						},
						error:function(){
						}
					});
				}
			}
		});			
		//form4表单提交事件
		$("#submit4").click(function(){
		    var deviceId = $("#deviceId").val();
			var teleNumber = $("#teleNumber").val();
			if(teleNumber=='' || deviceId==''){
				return;
			}
			else{
				var myreg = /^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))+\d{8})$/; 
				if(!myreg.test(teleNumber)){
					return;	
				}
				$.ajax({
				     url:"instruction/"+ deviceId + "/saveBasicInfo",
					 data:{"teleNumber":teleNumber},
					 type:"post",
					 success:function(data){
						layer.alert('提交成功');
						check();
					 },
					 error:function(){
					 }}
				);						
			}
		});
			
		//deviceId的check
		check();
		if($("#deviceId").val()==''){
			$("#deviceId").focus();
		}

		//deviceId输出框change事件
		$("#deviceId").bind('input propertychange',function(){check();}); 
		
		//reset按钮点击事件
		$(".form-reset").mousedown(function(){
			check();
		});
			
	});
	
	
	function check(){
		$(".form-reset").click();
		if($("#deviceId").val()==''){
			$("#text").text('Tips：请填写设备编号');
  			$("form input").val('');	
		}
		else{
			$.ajax({
				url:"config/" + $("#deviceId").val() + "/getConfigInfo",
				type:"get",
				dataType:'json',
			success:
				function(data){		
	    			if(!data){
	    				$("#text").text('Error：不存在的设备编号');
	    				$("form input").val('');	
	    			}
	    			else{		    
	    			    $("#text").text('Info：正确的设备编号');
						$("#braceletInterval").val(data.braceletInterval);
						$("#braceletUpload").val(data.braceletUpload);
						$("#locationInterval").val(data.locationInterval);	
						$("#locationUpload").val(data.locationUpload);
						$("#locateInterval").val(data.locateInterval);
						$("#locateTimes").val(data.locateTimes);
						$("#teleNumber").val(data.teleNumber);    			    
	    			}
	    		},
			error:
	     		function(){
	     			$("#text").text('Error：不存在的设备编号');
	     			$("form input").val('');
	     		}
    		});	
		}
	}
	
</script>

<body>
<%@include file="naviBar.jsp"%>

<!-- Tab形式  -->
<div class="admin" style="font-size:15px">
	<div>
		<div class="label" style="display:inline-block;width:5.5%"><label for="readme">设备编号:</label></div>
    	<div class="field" style="display:inline-block;width:16%;">
    		<input class="input_" type="text" id="deviceId" value="<%=deviceId%>"/>
    	</div>	
    </div>
    <br/>
	<div class="alert alert-yellow" id="alert" style="width:25%;">
		<strong id="text">Tips：请填写设备编号</strong>
	</div>
	<br/>
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
        <!-- 手环采集 -->
        <div class="tab-panel active" id="tab-set1">
        	<br/>
        	<form method="post" class="form-x" id="form1">       
                <div class="form-group">
                    <div class="label"><label for="desc">采集间隔(分钟):</label></div>
                    <div class="field" style="width:15%;">
                    	<input type="text" class="input" id="braceletInterval" name="braceletInterval" data-validate="number:格式错误(数字)"/>
                    </div>
                </div>
                <div class="form-group">
                    <div class="label"><label for="desc">上送条数 :</label></div>
                    <div class="field" style="width:15%;">
                    	<input type="text" class="input" id="braceletUpload" name="braceletUpload" data-validate="number:格式错误(数字)"/>
                    </div>
                </div>    
                <br/><br/>           
                <div>
                	<div style="float:left;width:20%;text-align:right"><button class="button bg-main" id="submit1" type="button">提交</button></div>
                	<div style="float:left;padding:0px 0px 0px 15px"><button class="button bg-main form-reset" type="button">重置</button></div>
				</div>
            </form>
        </div>
        
        <!-- 位置采集 -->
        <div class="tab-panel" id="tab-set2">
        	<br/>
        	<form method="post" class="form-x" id="form2">   
        		<div class="form-group">
                    <div class="label"><label for="desc">采集间隔(分钟):</label></div>
                    <div class="field" style="width:15%;">
                    	<input type="text" class="input" id="locationInterval" name="locationInterval"  data-validate="number:格式错误(数字)"/>
                    </div>
                </div>      
                <div class="form-group">
                    <div class="label"><label for="desc">上送条数 : </label></div>
                    <div class="field" style="width:15%;">
                    	<input type="text" class="input" id="locationUpload" name="locationUpload" data-validate="number:格式错误(数字)"/>
                    </div>
                </div> 
                <br/><br/>                     
                <div>
                	<div style="float:left;width:20%;text-align:right"><button class="button bg-main" id="submit2" type="button">提交</button></div>
                	<div style="float:left;padding:0px 0px 0px 15px"><button class="button bg-main form-reset" type="button">重置</button></div>
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
                    	<input type="text" class="input" id="locateInterval" name="locateInterval" data-validate="number:格式错误(数字)"/>
                    </div>
                </div>
                <div class="form-group">
                    <div class="label"><label for="desc">定位次数 :</label></div>
                    <div class="field" style="width:15%;">
                    	<input type="text" class="input" id="locateTimes" name="locateTimes" data-validate="number:格式错误(数字)"/>
                    </div>
                </div>
                <br/><br/>                      
                <div>
                	<div style="float:left;width:20%;text-align:right"><button class="button bg-main" id="submit3" type="button">提交</button></div>
                	<div style="float:left;padding:0px 0px 0px 15px"><button class="button bg-main form-reset" type="button">重置</button></div>
				</div>
            </form>
        </div>
        
        <!-- 基本信息 -->
        <div class="tab-panel" id="tab-set4">
        	<br/>
        	<form method="post" class="form-x" id="form3">         
                <div class="form-group">
                    <div class="label"><label for="desc">短信通知号码: </label></div>
                    <div class="field" style="width:15%;">
                    	<input type="text" class="input" id="teleNumber" data-validate="mobile:格式错误(手机号码)"/>
                    </div>
                </div>
                <div class="form-group">
                   	<div class="label"><label>&nbsp;</label></div>
                </div> 
                <br/><br/>
                <div>
                	<div style="float:left;width:20%;text-align:right"><button class="button bg-main" id="submit4" type="button">提交</button></div>
                	<div style="float:left;padding:0px 0px 0px 15px"><button class="button bg-main form-reset" type="button">重置</button></div>
				</div>
            </form>
        </div> 
      </div>
    </div>
</div>
</body>
</html>