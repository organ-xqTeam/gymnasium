package com.xq.gymnasium.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xq.gymnasium.dao.AdministratorMapper;
import com.xq.gymnasium.model.Administrator;
import com.xq.gymnasium.service.IAdministratorService;
import com.xq.gymnasium.util.StringTools;

@Service("ias")
public class AdministratorService implements IAdministratorService {
	
	@Autowired
	private AdministratorMapper iam;
	
	/**
	 * 新增管理员
	 * yangweihang
	 * @Date 2018年9月11日 下午12:07:16
	 * @param a	管理员类
	 * @return
	 */
	public String insertadmin(Administrator a) {
		String str = "";
		int result = iam.insertadmin(a);
		if(result > 0) {
			str = "insert.admin.success";
		}else {
			str = "insert.admin.fail";
		}
		return str; 
	}
	
	/**
	 * 按用户名和密码查询
	 * yangweihang
	 * @Date 2018年9月13日 下午6:47:20
	 * @param ausername	用户名
	 * @param apwd	密码
	 * @return
	 */
	public Administrator selectadmin(String ausername,String apwd) {
		StringTools st = StringTools.getFactory();
		Map<String,Object> map = new HashMap<String,Object>();
		if(!st.isNullOrEmpty(ausername)) {
			map.put("ausername", ausername);
		}
		if(!st.isNullOrEmpty(apwd)) {
			map.put("apwd", apwd);
		}
		Administrator a = iam.selectadmin(map);
		return a;
	}
	
	/**
	 * 按aid查询
	 * yangweihang
	 * @Date 2018年9月13日 下午2:32:24
	 * @param aid	用户id
	 * @return
	 */
	public Administrator selectByAid(int aid) {
		return iam.selectByAid(aid);
	}
	
	/**
	 * 修改密码
	 * yangweihang
	 * @Date 2018年9月20日 上午11:01:28
	 * @param apwd
	 * @return
	 */
	public int updateaid(Administrator a) {
		return iam.updateaid(a);
	}
}
