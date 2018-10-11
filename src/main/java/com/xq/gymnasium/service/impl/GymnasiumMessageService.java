package com.xq.gymnasium.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xq.gymnasium.dao.GymnasiumMessageMapper;
import com.xq.gymnasium.dao.OrdersMapper;
import com.xq.gymnasium.dao.SiteMapper;
import com.xq.gymnasium.model.GymnasiumMessage;
import com.xq.gymnasium.model.Orders;
import com.xq.gymnasium.service.IGymnasiumMessageService;
import com.xq.gymnasium.util.CodeUtil;
import com.xq.gymnasium.util.GclosedtimeUtil;

/**
 * 体育馆信息
 * @ClassName MessageService
 * @Author yangweihang
 * @Date 2018年9月12日 上午11:51:15
 */
@Service("ims")
public class GymnasiumMessageService implements IGymnasiumMessageService {

	@Autowired
	private GymnasiumMessageMapper imm;
	
	@Autowired
	private SiteMapper ism;
	
	@Autowired
	private OrdersMapper om;
	
	/**
	 * 新增体育馆信息
	 * yangweihang
	 * @Date 2018年9月12日 上午11:51:41
	 * @param m 体育场
	 * @return
	 */
	@Override
	public int insertmessage(GymnasiumMessage m) {
		return imm.insertmessage(m);
	}
	
	/**
	 * 修改体育馆信息
	 * yangweihang
	 * @Date 2018年9月12日 上午11:53:58
	 * @param m 体育场
	 * @return
	 */
	public int updatemessage(GymnasiumMessage m) {
		return imm.updatemessage(m);
	}

	/**
	 * 查询全部的体育馆
	 * yangweihang
	 * @Date 2018年9月13日 下午8:38:32
	 * @return
	 */
	public List<GymnasiumMessage> selectbygm(){
		return imm.selectbygm();
	}
	
	/**
	 * 场馆管理
	 * yangweihang
	 * @Date 2018年9月18日 上午9:58:17
	 * @return
	 */
	public List<Map<String,Object>> selectbygym(String sname,String hcode){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("sname", sname);
		map.put("hcode", hcode);
		List<Map<String, Object>> list = imm.selectbygym(map);
		for (Map<String,Object> m : list) {
			String gnumber = CodeUtil.gymnasiumnumber((int)m.get("gid"));
			m.put("gnumber", gnumber);
			if((int)m.get("state") == 1) {
				m.put("pstatus", "可预订");
			}else {
				m.put("pstatus", "不可预订");
			}
			/*if(m.get("sname") != null && (int)m.get("pstatus") == 1) {
			}else {
				m.put("pstatus", "不可预订");
			}*/
			//获得闭馆时间
			String[] gts = m.get("gclosedtime").toString().split(",");
			String getgctime = GclosedtimeUtil.getgctime(gts);
			//开闭馆时间
			m.replace("gstarttime", "早"+m.get("gstarttime")+"-晚"+m.get("gendtime")+" "+getgctime);
		}
		return list;
	}
	
	/**
	 * 场馆管理
	 * yangweihang
	 * @Date 2018年9月18日 上午9:58:17
	 * @return
	 */
	public Map<String,Object> selectbygymgid(String gid){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("gid", gid);
		List<Map<String, Object>> list = imm.selectbygym(map);
		Map<String,Object> ms = new HashMap<String,Object>();
		for (Map<String,Object> m : list) {
			//开闭馆时间
			String time = "早"+m.get("gstarttime")+"-晚"+m.get("gendtime");
			ms.put("gname", m.get("gname"));
			ms.put("gstarttime", time);
			ms.put("facilities", m.get("facilities"));
			ms.put("glocation", m.get("glocation"));
			ms.put("gphonenum", m.get("gphonenum"));
			ms.put("noticeuse", m.get("noticeuse"));
		}
		return ms;
	}
	
	/**
	 * 按gid查询
	 * yangweihang
	 * @Date 2018年9月18日 下午2:21:51
	 * @param gid
	 * @return
	 */
	public GymnasiumMessage selectbygid(Integer gid) {
		GymnasiumMessage gm = imm.selectbygid(gid);
		return gm;
	}
	
	/**
	 * 按体育馆id删除
	 * yangweihang
	 * @Date 2018年9月19日 下午6:06:41
	 * @param gid
	 * @return
	 */
	public Integer deletebygid(Integer gid) {
		Integer i = 9;
		//修改订单
		List<Orders> s = om.selectbygm(gid);
		if(s.size() > 0) {
			return i;
		}
		i = imm.deletebygid(gid);
		return i;
	}
	
	/**
	 * 修改开馆闭馆状态
	 * yangweihang
	 * @Date 2018年9月26日 下午4:36:33
	 * @param map
	 * @return
	 */
	public String updatestate(Integer gid) {
		String str = "";
		GymnasiumMessage g = selectbygid(gid);
		int state = 0;
		if(g != null) {
			if(g.getState() == 1) {
				state = 2;
			}else {
				state = 1;
			}
		}
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("state", state);
		map.put("gid", gid);
		Integer i = imm.updatestate(map);
		if(i > 0) {
			str = "update.state.success";
		}
		if(i > 0) {
			//根据体育馆状态修改场地状态
			Map<String,Object> map1 = new HashMap<String,Object>();
			map1.put("pstatus", state);
			map1.put("gid", g.getGid());
			int res = ism.updatebygmstate(map1);
			return str;
		}
		str = "update.state.fail";
		return str;
	}
	
	/**
	 * 查询和会员医院id一样的体育馆
	 * yangweihang
	 * @Date 2018年9月26日 下午5:36:59
	 * @param map
	 * @return
	 */
	public List<GymnasiumMessage> selectbycode(String hcode){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("hcode", hcode);
		List<GymnasiumMessage> list = imm.selectbycode(map);
		return list;
	}
}
