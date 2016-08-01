package com.framework.web;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.framework.domain.UserInfo;
import com.framework.service.UserService;
import com.framework.utils.VerifyCodeUtil;

@Controller 
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(value="login.html")
	public String login(HttpServletRequest request) throws IOException{

		String verifyCode = VerifyCodeUtil.generateTextCode(0, 4, null);  		
		BufferedImage bufferedImage = VerifyCodeUtil.generateImageCode(verifyCode, 90, 30, 10, true, Color.WHITE, Color.BLACK, null);  
		
		String path = request.getSession().getServletContext().getRealPath("images/verifyCode.jpg");
		ImageIO.write(bufferedImage, "JPEG", new File(path));  
		request.getSession().setAttribute("verifyCode",verifyCode);
		
		return "login";
	}
	
	//负责处理用户验证的请求
	@ResponseBody
	@RequestMapping(value="/user/loginCheck")
	public JSONObject loginCheck(HttpServletRequest request,UserInfo userInfo, HttpSession session){
		
		JSONObject jsonObj = new JSONObject();
		
		boolean isValidUser = userService.hasMatchUser(userInfo.getUserName(),userInfo.getPassword());
		if(!isValidUser){
			jsonObj.put("info","error");
		}
		else{
			UserInfo user = userService.findUserByUserName(userInfo.getUserName());
			user.setLoginCount(user.getLoginCount()+1);
			SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			user.setLastLogin(formater.format(new Date(System.currentTimeMillis())));
			userService.loginSuccess(user);	
			
			jsonObj.put("info","success");
			session.setAttribute("user", user);
			session.setAttribute("loginState", "login");
		}
		
		return jsonObj;
	}
}
