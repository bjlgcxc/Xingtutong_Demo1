package com.framework.dao;

import java.util.List;

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
	public UserInfo findUserByUserName(String userName){
		String sqlStr = " select * from t_user where userName =? ";
	    List<UserInfo> result = jdbcTemplate.query(sqlStr, new Object[]{userName},new BeanPropertyRowMapper<UserInfo>(UserInfo.class));
		if(result.size()!=0)
			return result.get(0);
		else
			return null;
	}
	
	//插入注册信息
	public void insertUserInfo(String userName,String password){
		String sql = " insert into t_user(userName,password) values(?,?) ";
		Object args[] = new Object[]{userName,password};
		jdbcTemplate.update(sql, args);
	}
	
	//更新用户登录信息
	public void updateUserInfo(UserInfo userInfo){
		String sqlStr = " update t_user set lastLogin=?,loginCount=? where userName=? ";
		jdbcTemplate.update(sqlStr,new Object[]{userInfo.getLastLogin(),userInfo.getLoginCount(),userInfo.getUserName()});
	}
	
}
