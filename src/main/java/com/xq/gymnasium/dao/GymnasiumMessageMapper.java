package com.xq.gymnasium.dao;

import java.util.List;
import java.util.Map;

import com.xq.gymnasium.model.GymnasiumMessage;

/**
 * 体育馆信息
 * @ClassName MessageMapper
 * @Author yangweihang
 * @Date 2018年9月12日 上午11:01:02
 */
public interface GymnasiumMessageMapper {
	
	/**
	 * 增加体育馆信息
	 * yangweihang
	 * @Date 2018年9月12日 上午11:53:58
	 * @param m 体育场
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
	List<Map<String,Object>> selectbygym(Map<String,Object> map);
	
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
	 * 修改开馆闭馆状态
	 * yangweihang
	 * @Date 2018年9月26日 下午4:36:33
	 * @param map
	 * @return
	 */
	Integer updatestate(Map<String,Object> map);
	
	/**
	 * 查询和会员医院id一样的体育馆
	 * yangweihang
	 * @Date 2018年9月26日 下午5:36:59
	 * @param map
	 * @return
	 */
	List<GymnasiumMessage> selectbycode(Map<String,Object> map);
	
	/**
	 * 查询体育馆状态
	 * yangweihang
	 * @Date 2018年9月26日 下午8:07:36
	 * @param sid
	 * @return
	 */
	GymnasiumMessage selectbygmstate(Integer sid);
}
