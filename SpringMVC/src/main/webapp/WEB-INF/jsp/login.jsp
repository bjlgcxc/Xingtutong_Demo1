<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%  
    String context = request.getContextPath();
    request.setAttribute("context",context);
    session.setAttribute("passcode","6982");    
%>
<html>
<head>
    <title>管理员登录</title>
    
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
		$("body").keydown(function() {
            if (event.keyCode == "13") {//keyCode=13是回车键
                $("#submit").click();
            }
        });
		
		$("#submit").click(function(){
			if($("#userName").val()=='' || $("#password").val()==''){
				layer.msg('请输入账号和密码');
			}
			else if($("#passcode").val()==<%=session.getAttribute("passcode")%>){
				$.ajax({
					url:"user/loginCheck",
					type:"get",			
					dataType:"json",
					data:{userName:$("#userName").val(),password:$("#password").val()},
					success:function(data){
						if(data.info=="error"){
							layer.alert('账号或密码错误');
						}
						else{
							<%session.setAttribute("loginStatus", "login");%>
							location.href = "index.html";
						}
					}
			
				});
				
			}
			else{
			    layer.msg('验证码错误');
			    $("#passcode").val('');
			}
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
                <div class="panel-head" style="text-align:center;"><h2><strong>管理员登录</strong></h2></div>
                <div class="panel-body" style="padding:30px;">
                    <div class="form-group">
                        <div class="field field-icon-right">
                            <input type="text" class="input" id="userName" name="userName" placeholder="登录账号" data-validate="required:请填写账号,length#>=5:账号长度不符合要求" />
							<span class="icon icon-user"></span>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="field field-icon-right">
                            <input type="password" class="input" id="password" name="password" placeholder="登录密码" data-validate="required:请填写密码,length#>=6:密码长度不符合要求" />            
                            <span class="icon icon-key"></span>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="field">
                            <input type="text" class="input" id="passcode" name="passcode" placeholder="填写右侧的验证码" data-validate="required:请填写右侧的验证码" />
                            <img src="images/passcode.jpg" width="80" height="32" class="passcode" />
                            <p id="info"></p>
                        </div>                  
                    </div>               
                </div>
                <div class="panel-foot text-center"><button type="button" id="submit" class="button button-block bg-main text-big">登录</button></div>
            </div>
            </form>
        </div>
    </div>
</div>
</body>
</html>