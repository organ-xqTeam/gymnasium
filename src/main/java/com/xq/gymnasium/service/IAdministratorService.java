package com.xq.gymnasium.service;


import com.xq.gymnasium.model.Administrator;

/**
 * 管理员
 * @ClassName IAdministratorService
 * @Author yangweihang
 * @Date 2018年9月11日 下午12:12:18
 */
public interface IAdministratorService {
	
	/**
	 * 新增管理员
	 * yangweihang
	 * @Date 2018年9月11日 下午12:07:16
	 * @param a	管理员类
	 * @return
	 */
	String insertadmin(Administrator a);
	
	/**
	 * 按用户名和密码查询
	 * yangweihang
	 * @Date 2018年9月11日 下午1:32:11
	 * @param ausername	用户名
	 * @param apwd	密码
	 * @return
	 */
	Administrator selectadmin(String ausername,String apwd);
	
	/**
	 * 按aid查询
	 * yangweihang
	 * @Date 2018年9月13日 下午2:32:24
	 * @param aid	用户id
	 * @return
	 */
	Administrator selectByAid(int aid);
	
	/**
	 * 修改密码
	 * yangweihang
	 * @Date 2018年9月20日 上午11:01:28
	 * @param apwd
	 * @return
	 */
	int updateaid(Administrator a);
}
