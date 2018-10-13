package com.xq.gymnasium.dao;

import java.util.List;

import java.util.Map;


import com.xq.gymnasium.model.Orders;
import com.xq.gymnasium.model.Selectbyordersyd;

/**
 * 体育馆订单
 * @ClassName OrdersMapper
 * @Author yangweihang
 * @Date 2018年9月14日 上午9:28:42
 */
public interface OrdersMapper {
	
	/**
	 * 批量录入订单
	 * yangweihang
	 * @Date 2018年9月14日 上午9:32:36
	 * @param list
	 * @return
	 */
	int insertorders(List<Orders> list);
	
	/**
	 * 查询当前的人是否有没有完成的订单
	 * yangweihang
	 * @Date 2018年9月14日 下午3:39:18
	 * @param oname	预订人
	 * @return
	 */
	List<Orders> selectByNotOrders(String oname);
	
	/**
	 * 查询该人的订单
	 * yangweihang
	 * @Date 2018年9月14日 下午4:29:17
	 * @param map 预订人
	 * @return
	 */
	List<Map<String,Object>> selectByOname(Map<String,Object> map);
	
	/**
	 * 查询总价格
	 * yangweihang
	 * @Date 2018年9月30日 下午1:00:28
	 * @param map
	 * @return
	 */
	Double selectbymoney(Map<String,Object> map);
	
	/**
	 * 批量退订
	 * yangweihang
	 * @Date 2018年9月14日 下午8:08:39
	 * @param list
	 * @return
	 */
	int updatestate(Map<String,Object> map);
	
	/**
	 * 查询状态
	 * yangweihang
	 * @Date 2018年9月14日 下午8:20:46
	 * @param oid 订单id
	 * @return
	 */
	Orders selectbystate(Integer oid);
	
	/**
	 * 查询场地预定信息
	 * yangweihang
	 * @Date 2018年9月15日 上午11:09:25
	 * @param map
	 * @return
	 */
	List<Map<String,Object>> selectbyorders(Map<String,Object> map);
	
	/**
	 * 查询场地预定信息总数
	 * yangweihang
	 * @Date 2018年9月20日 下午2:06:00
	 * @param map
	 * @return
	 */
	List<Map<String,Object>> selectbyorderscount(Map<String,Object> map);
	
	/**
	 * 按日期查询场馆预定情况
	 * yangweihang
	 * @Date 2018年9月15日 上午11:09:25
	 * @param map
	 * @return
	 */
	List<Map<String,Object>> selectbyordersyd(Selectbyordersyd sb);
	
	/**
	 * 查询当天的使用的人数和场地数
	 * yangweihang
	 * @Date 2018年9月17日 下午1:37:54
	 * @return
	 */
	Map<String,Object> selectbycount(Map<String,Object> map);
	
	/**
	 * 前端查询订单
	 * yangweihang
	 * @Date 2018年9月19日 下午1:53:11
	 * @param sb
	 * @return
	 */
	List<Map<String,Object>> selectbyordersydss(Selectbyordersyd sb);
	
	/**
	 * 查询一个订单
	 * yangweihang
	 * @Date 2018年9月30日 上午10:55:22
	 * @param map
	 * @return
	 */
	Orders selectbyotime(Map<String,Object> map);
	
	/**
	 * 查询多个订单
	 * yangweihang
	 * @Date 2018年9月30日 上午11:06:44
	 * @param map
	 * @return
	 */
	List<Orders> selectbyotimes(Map<String,Object> map);
	
	/**
	 * 删除体育馆对应的订单退订
	 * yangweihang
	 * @Date 2018年9月30日 下午3:32:56
	 * @param gid
	 * @return
	 */
	List<Orders> selectbygm(Integer gid);
	
	/**
	 * 删除场地对应的订单退订
	 * yangweihang
	 * @Date 2018年9月30日 下午3:32:56
	 * @param gid
	 * @return
	 */
	List<Orders> selectbys(Integer sid);
	
	/**
	 * 按场地查询订单
	 * yangweihang
	 * @Date 2018年10月10日 下午6:57:43
	 * @param map
	 * @return
	 */
	List<Map<String,Object>> selectodersbysite(Map<String,Object> map);
	
	/**
	 * 查询订单
	 * yangweihang
	 * @Date 2018年10月13日 上午9:13:52
	 * @param map
	 * @return
	 */
	Orders selectbyss(Map<String,Object> map);
}
