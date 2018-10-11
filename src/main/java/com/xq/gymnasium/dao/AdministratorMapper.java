package com.xq.gymnasium.dao;

import java.util.Map;

import com.xq.gymnasium.model.Administrator;

/**
 * 管理员
 * @ClassName AdministratorMapper
 * @Author yangweihang
 * @Date 2018年9月11日 下午12:05:07
 */
public interface AdministratorMapper {
	
	/**
	 * 新增管理员
	 * yangweihang
	 * @Date 2018年9月11日 下午12:07:16
	 * @param a	管理员类
	 * @return
	 */
	int insertadmin(Administrator a);
	
	/**
	 * 按用户名和密码查询
	 * yangweihang
	 * @Date 2018年9月11日 下午1:32:11
	 * @param ausername	用户名
	 * @param apwd	密码
	 * @return
	 */
	Administrator selectadmin(Map<String,Object> map);
	
	/**
	 * 关联体育馆
	 * yangweihang
	 * @Date 2018年9月13日 下午2:30:58
	 * @param a	管理员类
	 * @return
	 */
	int updategid(Administrator a);
	
	/**
	 * 按aid查询
	 * yangweihang
	 * @Date 2018年9月13日 下午2:32:24
	 * @param aid 管理员id
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
