<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%  
    String context = request.getContextPath();
    request.setAttribute("context",context);
    
    String verifyCode = (String)session.getAttribute("verifyCode");    
%>
<html>
<head>
    <title>用户注册</title>
    
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
    <meta name="renderer" content="webkit">
   
    <link rel="stylesheet" href="css/pintuer.css">
    <link rel="stylesheet" href="css/admin.css">
    <link type="image/x-icon" href="/favicon.ico" rel="shortcut icon" />
    <link href="/favicon.ico" rel="bookmark icon" />
    
    <script src="js/jquery-2.2.3.js"></script>
    <script src="js/pintuer.js"></script>
    <script src="js/respond.js"></script>
    <script src="js/admin.js"></script>
    <script src="js/layer.js"></script>
</head>

<script type="text/javascript">
	$(document).ready(function(){
		$("#submit").click(function(){
			if($("#userName").val()=='' || $("#password1").val()=='' || $("#password2").val()==''){
				layer.msg('请填写完整的信息');
			}
			else if($("#password1").val()!=$("#password2").val()){
				layer.msg('输入的密码不一致');
			}
			else{
			    $.ajax({
					url:"user/register",
					type:"get",			
					dataType:"json",
					data:{userName:$("#userName").val(),password:$("#password1").val()},
					success:function(data){
						if(data.info!="error"){
							layer.msg('注册成功!');						
							setTimeout(function(){location.href="login.html";},2000);
						}
						else{
							layer.msg('账号已存在，请重新选择');
							setTimeout(function(){location.href="register.html";},2000);
						}
					}			
				});
			}
		});
		
		$("#return").click(function(){
			location.href="login.html";
		});
	});	
</script>

<body>
<div class="container">
    <div class="line">
        <div class="xs6 xm4 xs3-move xm4-move">
          	<br/><br/>
            <br/><br/>
            <br/><br/>
            <form id="form" method="post" action="index.html">
            <div class="panel">
                <div class="panel-head" style="text-align:center;"><h2><strong>用户注册</strong></h2></div>
                <div class="panel-body" style="padding:30px;">
                    <div style="font-size:16px">1.填写登录账号</div>
                    <div class="form-group">
                        <div class="field field-icon-right">
                            <input type="text" class="input" id="userName" name="userName" data-validate="required:请填写账号,length#>=5:账号长度不符合要求" />
                        </div>
                        <ul style="color:red;" id="userNameVerify">
                    	</ul>
                    </div>
                    <div style="font-size:16px">2.填写登录密码</div>
                    <div class="form-group">
                        <div class="field field-icon-right">
                            <input type="password" class="input" id="password1" name="password1" data-validate="required:请填写密码,length#>=6:密码长度不符合要求" />                             
                        </div>
                    </div>
                    <div style="font-size:16px">3.确认登录密码</div>
                    <div class="form-group">
                        <div class="field field-icon-right">
                            <input type="password" class="input" id="password2" name="password2" data-validate="required:请填写密码,length#>=6:密码长度不符合要求" />                             
                        </div>
                    </div>       
                </div>
                <div class="panel-foot text-center">
                	<button type="button" id="submit" class="button bg-main text-big">&nbsp;&nbsp;注&nbsp;册&nbsp;&nbsp;</button>
                	<button type="button" id="return" class="button bg-main text-big">&nbsp;&nbsp;返&nbsp;回&nbsp;&nbsp;</button>
                </div>
            </div>
            </form>
        </div>
    </div>
</div>
</body>
</html>