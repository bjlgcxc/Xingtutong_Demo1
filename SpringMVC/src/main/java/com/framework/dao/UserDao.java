package com.framework.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.framework.domain.UserInfo;

@Repository   
public class UserDao {
	
	@Autowired 
	private JdbcTemplate jdbcTemplate;
	
	//用户登录验证
	public int getMatchCount(String userName,String password){		
		String sqlStr = " select count(*) from t_user where userName =? and password =? ";	
		return jdbcTemplate.queryForInt(sqlStr,new Object[]{userName,password});
	}
	
	//通过用户名获取用户信息
	public UserInfo findUserByUserName(final String userName){
		String sqlStr = " select * from t_user where userName =? ";
	    UserInfo user = jdbcTemplate.query(sqlStr, new Object[]{userName},
	    		            new BeanPropertyRowMapper<UserInfo>(UserInfo.class)).get(0);
		return user;
	}
	
	//更新用户登录信息
	public void updateLoginInfo(UserInfo userInfo){
		String sqlStr = " update t_user set lastLogin=?,loginCount=? where userName=? ";
		jdbcTemplate.update(sqlStr,new Object[]{userInfo.getLastLogin(),userInfo.getLoginCount(),userInfo.getUserName()});
	}
	
}
