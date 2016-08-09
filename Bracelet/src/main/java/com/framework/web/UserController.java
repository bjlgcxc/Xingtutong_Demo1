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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.framework.domain.UserInfo;
import com.framework.service.UserService;
import com.framework.utils.EncodeMD5;
import com.framework.utils.VerifyCodeUtil;
import java.security.NoSuchAlgorithmException;

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
		request.setAttribute("verifyCode",verifyCode);
		
		return "login";
	}
	
	
	@ResponseBody
	@RequestMapping(value="/user/register",method=RequestMethod.GET)
	public JSONObject register(HttpServletRequest request,UserInfo userInfo) throws NoSuchAlgorithmException {
		JSONObject jsonObj = new JSONObject();
		if(userService.findUserByUserName(userInfo.getUserName())!=null){
			jsonObj.put("info", "error");
		}
		else{
			jsonObj.put("info", "success"); 
			userService.addUserInfo(userInfo.getUserName(),EncodeMD5.encode(userInfo.getPassword()));
		}
		return jsonObj;
	}
	
	
	//负责处理用户验证的请求
	@ResponseBody
	@RequestMapping(value="/user/loginCheck",method=RequestMethod.GET)
	public JSONObject loginCheck(HttpServletRequest request,UserInfo userInfo, HttpSession session) throws NoSuchAlgorithmException{
		
		JSONObject jsonObj = new JSONObject();
		
		boolean isValidUser = userService.hasMatchUser(userInfo.getUserName(),EncodeMD5.encode(userInfo.getPassword()));
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
	
	
	//修改用户密码
	@ResponseBody
	@RequestMapping(value="/user/changePsw",method=RequestMethod.POST)
	public void changePsw(HttpSession session,UserInfo userInfo) throws NoSuchAlgorithmException{
		userInfo.setPassword(EncodeMD5.encode(userInfo.getPassword()));
		userService.updatePassword(userInfo);
		
		UserInfo user = userService.findUserByUserName(userInfo.getUserName());
		session.setAttribute("user", user);
	}
	
}
