<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<% 
    String context = request.getContextPath();
    request.setAttribute("context",context);
    request.setAttribute("page", "system");
    
    String loginState = "notLogin";
  	if(session.getAttribute("loginState")!=null){
  		loginState = "login";
  	}
%>

<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <title>系统</title>
    
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

<script>
	function setDefault(){
		$.ajax({
	  	   	  url:"sysDefault/getSysDefault",
	  	   	  type:"get",
	  	   	  dataType:"json",
	  	   	  success:function(data){
	  	   	  	 $("#sampleInterval").val(data.sampleInterval);
	  	   	  	 $("#uploadEverytime").val(data.uploadEverytime);
				 $("#locateInterval").val(data.locateInterval);
				 $("#locateTimes").val(data.locateTimes);
	  	   	  },
	  	   	  error:function(){
	  	   	  	alert('error');
	  	   	  }	   
	  	});	
	}
    
    $(document).ready(function(){
  	    setDefault();
  	    $("#submit").click(function(){
  	    	//判断是否有未填
  	    	if($("#sampleInterval").val()=='' || $("#uploadEverytime").val()=='' || 
  	    						$("#locateInterval").val()=='' || $("#locateTimes").val()==''){
  	    		 return;
  	    	}
  	    	//判断数据格式
  	    	var $sampleInterval = Number($("#sampleInterval").val());
  	    	var $uploadEverytime = Number($("#uploadEverytime").val());
  	    	var $locateInterval = Number($("#locateInterval").val());
  	    	var $locateTimes = Number($("#locateTimes").val());
  	    	if(isNaN($sampleInterval) || isNaN($uploadEverytime) || isNaN($locateInterval) || isNaN($locateTimes)){
  	    		layer.msg('格式错误');
  	    		return;		
  	    	}
  	    	//更新默认配置
  	    	$.ajax({
	  	   	   url:"sysDefault/updateSysDefault",
	  	   	   type:"post",
	  	   	   data:{
	  	   	   		sampleInterval:$sampleInterval,uploadEverytime:$uploadEverytime,
	  	   	        locateInterval:$locateInterval,locateTimes:$locateTimes
	  	   	   },
	  	   	   success:function(data){
	 			  layer.alert('更新成功!',function(){
	 			 	 location.href="system.html";
	 			  });
	  	   	   },
	  	   	   error:function(){
	  	   	  	  layer.alert('更新失败!');
	  	   	   }	   
	  		});	
  	    });
		
		$("#reset").click(function(){
			setDefault();
		});
		
    });

</script>

<body>
<%@include file="naviBar.jsp"%>
<div class="admin" style="font-size:15px">
   <form method="post" class="form-x" action="system.html"> 
   	   <div class="form-group">
           <div class="label" style="font-size:18px"><label for="sitename">系统默认配置：</label></div>
       </div>
       <br/>
       <div class="form-group">
           <div class="label"><label for="sampleInterval">数据采集间隔 </label></div>
           <div class="field" style="width:16%;">
               <input type="text" class="input" id="sampleInterval" size="50" placeholder="填写数据采集间隔" data-validate="required:请填写数据采集间隔" />
           </div>
       </div>
       <br/>
       <div class="form-group">
           <div class="label"><label for="uploadEverytime">上传数据条数</label></div>
           <div class="field" style="width:16%;">
               <input type="text" class="input" id="uploadEverytime" size="50" placeholder="填写上传数据条数" data-validate="required:请填写上传数据条数" />
           </div>
       </div>
       <br/>
       <div class="form-group">
           <div class="label"><label for="locateInterval">紧急定位间隔</label></div>
           <div class="field" style="width:16%;">
               <input type="text" class="input" id="locateInterval" size="50" placeholder="填写紧急定位间隔" data-validate="required:请填写紧急定位间隔" />
           </div>
       </div>
       <br/>
       <div class="form-group">
           <div class="label"><label for="locateTimes">紧急定位次数</label></div>
           <div class="field" style="width:16%;">
               <input type="text" class="input" id="locateTimes" size="50" placeholder="填写紧急定位次数" data-validate="required:请填写紧急定位次数" />
           </div>
       </div>
       <br/><br/>   
       <div class="form-button">
       	   <button class="button bg-main" type="button" id="submit">提交</button>
       	   &nbsp;&nbsp;
   	   	   <button class="button bg-main" type="button" id="reset">重置</button>
   	   </div>
   </form>
</div>
</body>
</html>