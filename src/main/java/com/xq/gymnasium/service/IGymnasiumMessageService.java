package com.xq.gymnasium.service;

import java.util.List;
import java.util.Map;

import com.xq.gymnasium.model.GymnasiumMessage;

/**
 * 体育馆信息
 * @ClassName IMessageService
 * @Author yangweihang
 * @Date 2018年9月12日 上午11:48:51
 */
public interface IGymnasiumMessageService {
	
	/**
	 * 新增体育馆信息
	 * yangweihang
	 * @Date 2018年9月12日 上午11:50:23
	 * @param m	体育场
	 * @return
	 */
	int insertmessage(GymnasiumMessage m);
	
	/**
	 * 修改体育馆信息
	 * yangweihang
	 * @Date 2018年9月12日 上午11:53:58
	 * @param m 体育场
	 * @return
	 */
	int updatemessage(GymnasiumMessage m);
	
	/**
	 * 查询全部的体育馆
	 * yangweihang
	 * @Date 2018年9月13日 下午8:38:32
	 * @return
	 */
	List<GymnasiumMessage> selectbygm();
	
	/**
	 * 场馆管理
	 * yangweihang
	 * @Date 2018年9月18日 上午9:58:17
	 * @return
	 */
	List<Map<String,Object>> selectbygym(String sname,String hcode);
	
	/**
	 * 按gid查询
	 * yangweihang
	 * @Date 2018年9月18日 下午2:21:51
	 * @param gid
	 * @return
	 */
	GymnasiumMessage selectbygid(Integer gid);
	
	/**
	 * 按体育馆id删除
	 * yangweihang
	 * @Date 2018年9月19日 下午6:06:41
	 * @param gid
	 * @return
	 */
	Integer deletebygid(Integer gid);
	
	/**
	 * 场馆管理
	 * yangweihang
	 * @Date 2018年9月18日 上午9:58:17
	 * @return
	 */
	Map<String,Object> selectbygymgid(String gid);
	
	/**
	 * 修改开馆闭馆状态
	 * yangweihang
	 * @Date 2018年9月26日 下午4:36:33
	 * @param map
	 * @return
	 */
	String updatestate(Integer gid);
	
	/**
	 * 查询和会员医院id一样的体育馆
	 * yangweihang
	 * @Date 2018年9月26日 下午5:36:59
	 * @param map
	 * @return
	 */
	List<GymnasiumMessage> selectbycode(String hcode);
}
