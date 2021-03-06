package com.framework.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.framework.dao.*;
import com.framework.domain.*;

@Service  
public class UserService {

	@Autowired
	private UserDao userDao;
	
	//1.检查用户名/密码的正确性
	public boolean hasMatchUser(String userName,String password){
		int matchCount = userDao.getMatchCount(userName, password);
		return matchCount > 0;
	}
	
	//2.以用户名为条件加载User对象
	public UserInfo findUserByUserName(String userName){
		return userDao.findUserByUserName(userName);
	}
	
	//3.添加注册信息
	public void addUserInfo(String userName,String password){
		userDao.insertUserInfo(userName, password);
	}
	
	//4.更新用户登录信息
	public void loginSuccess(UserInfo userInfo){
		userDao.updateUserInfo(userInfo);
	}
	
	//5.更新用户密码
	public void updatePassword(UserInfo userInfo){
		userDao.updatePassword(userInfo);
	}
	
}
