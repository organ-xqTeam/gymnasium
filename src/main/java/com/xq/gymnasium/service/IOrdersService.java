package com.xq.gymnasium.service;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.xq.gymnasium.model.Selectbyordersyd;
import com.xq.gymnasium.model.Sitetime;



/**
 * 体育馆订单
 * @ClassName IOrderService
 * @Author yangweihang
 * @Date 2018年9月14日 上午9:33:20
 */
public interface IOrdersService {

	/**
	 * 批量录入订单
	 * yangweihang
	 * @Date 2018年9月14日 上午9:39:23
	 * @param sid	场地id
	 * @param oname	预订人
	 * @param ostarttime 预定开始时间
	 * @param oendtime	预定结束时间
	 * @return
	 */
	String insertorders(Integer[] sid,String oname,String[] ostarttime,String[] oendtime,Integer gid);
	
	/**
	 * 查询该场地的指定时间是否已经预定了
	 * yangweihang
	 * @Date 2018年9月14日 上午11:18:58
	 * @param snumber
	 * @return
	 */
	int selectreservationornot(Integer sid,String ostarttime,String oendtime,String sitenumber);
	
	/**
	 * 查询该人的订单
	 * yangweihang
	 * @Date 2018年9月14日 下午4:29:17
	 * @param map 预订人
	 * @return
	 */
	List<Map<String,Object>> selectByOname(String hcode, String oname,String starttime,String endtime,Integer pageNum,Integer pageSize);
	
	/**
	 * 批量退订
	 * yangweihang
	 * @Date 2018年9月14日 下午8:08:39
	 * @param oid 订单id
	 * @return
	 */
	String updatestate(String otime);
	
	/**
	 * 查询场地预定信息
	 * yangweihang
	 * @Date 2018年9月15日 上午11:09:25
	 * @param map
	 * @return
	 */
	List<Map<String,Object>> selectbyorders(String starttime,String endtime,Integer pageNum,Integer pageSize);
	
	/**
	 * 查询场地预定信息总数
	 * yangweihang
	 * @Date 2018年9月20日 下午2:06:00
	 * @param map
	 * @return
	 */
	List<Map<String,Object>> selectbyorderscount(String starttime,String endtime,Integer pageNum,Integer pageSize);
	
	/**
	 * 按日期查询场馆预定情况
	 * yangweihang
	 * @Date 2018年9月15日 上午11:09:25
	 * @param map
	 * @return
	 */
	List<Map<String,Object>> selectbyordersyd(Selectbyordersyd sb);
	
	/**
	 * 按日期查询场馆预定情况
	 * yangweihang
	 * @Date 2018年9月15日 上午11:09:25
	 * @param map
	 * @return
	 * @throws Exception 
	 */
	JSONArray selectbyordersyds(Selectbyordersyd sb) throws Exception;

	/**
	 * 查询当天的使用的人数和场地数
	 * yangweihang
	 * @Date 2018年9月17日 下午1:37:54
	 * @return
	 */
	Map<String, Object> selectbycount(String time,String hcode);
	
	/**
	 * 前端查询订单
	 * yangweihang
	 * @Date 2018年9月19日 下午1:53:11
	 * @param sb
	 * @return
	 */
	List<Sitetime> selectbyordersydss(Selectbyordersyd sb);
	
	/**
	 * 判断体育馆休息日期
	 * yangweihang
	 * @Date 2018年9月26日 下午1:31:51
	 * @param sb
	 * @return
	 */
	String selectbyweek(Integer gid,String servenday);
	
	/**
	 * 后端首页预定查询
	 * yangweihang
	 * @Date 2018年10月10日 下午3:54:07
	 * @param sb
	 * @return
	 */
	List<Map<String,Object>> selectbyordersydhou(Selectbyordersyd sb);
}
