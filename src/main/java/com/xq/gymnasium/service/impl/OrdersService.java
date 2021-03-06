package com.xq.gymnasium.service.impl;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.xq.gymnasium.dao.GymnasiumMessageMapper;
import com.xq.gymnasium.dao.OrdersMapper;
import com.xq.gymnasium.dao.SiteMapper;
import com.xq.gymnasium.dao.SitetypeMapper;
import com.xq.gymnasium.model.GymnasiumMessage;
import com.xq.gymnasium.model.Orders;
import com.xq.gymnasium.model.Selectbyordersyd;
import com.xq.gymnasium.model.Site;
import com.xq.gymnasium.model.Sitetime;
import com.xq.gymnasium.model.Sitetype;
import com.xq.gymnasium.service.IOrdersService;
import com.xq.gymnasium.util.CodeUtil;
import com.xq.gymnasium.util.DateTools;
import com.xq.gymnasium.util.StringTools;

/**
 * 体育馆订单
 * @ClassName OrdersService
 * @Author yangweihang
 * @Date 2018年9月14日 上午9:34:30
 */
@Service("ios")
public class OrdersService implements IOrdersService {

	private static final String String = null;

	@Autowired
	private OrdersMapper om;
	
	@Autowired
	private SiteMapper ism;
	
	@Autowired
	private SitetypeMapper sm;
	
	@Autowired
	private GymnasiumMessageMapper imm;
	
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
	public String insertorders(Integer[] sid,String oname,String[] ostarttime,String[] oendtime,Integer gid) {
		if(oname == null) {
			return "login";
		}
		//查询当前的人是否有没有完成的订单
		List<Orders> os = om.selectByNotOrders(oname);
		//System.out.println("os"+os);
		if(os.size() >= 1) {
			//有预订订单未完成
			return "There are orders pending";
		}
		Double money = 0.0;
		DateTools dt = DateTools.getFactory();
		Date time = dt.formatDate(dt.formatDate(new Date(), "yyyy-MM-dd HH:ss:mm"), "yyyy-MM-dd HH:ss:mm");
		List<Orders> list = new ArrayList<Orders>();
		for (int i = 0; i < sid.length; i++) {
			//判断时间
			long stime = new Date().getTime();
			long sd = dt.formatDate(ostarttime[i], "yyyy-MM-dd HH:ss:mm").getTime();
			if(sd < stime) {
				return "99";
			}
			//按场地id查询场地
			Site s = ism.selectBySid(sid[i]);
			//按体育馆场地编号查询场地类别名称
			Sitetype st = sm.selectStnameBySnumber(s.getSnumber());
			Orders o = null;
			//如果是羽毛球场地的话就进行判断是否是按顺序预定的
			if(st.getStname().equals("羽毛球")) {
				//找到羽毛球场地对应的篮球场地是否预定了
				Integer selpstate = 0;
				for (int j = 0; j < 2; j++) {
					Map<String,Object> map = new HashMap<String,Object>();
					if(j == 0) {
						map.put("snumber", s.getSrsnumber()+"-A");
						map.put("gid", gid);
						Site sels = ism.selectBySnumber(map);
						selpstate = selectreservationornot(sels.getSid(),ostarttime[i],oendtime[i],sels.getSnumber());
					}
					if(j == 1) {
						map.put("snumber", s.getSrsnumber()+"-B");
						map.put("gid", gid);
						Site sels = ism.selectBySnumber(map);
						selpstate = selectreservationornot(sels.getSid(),ostarttime[i],oendtime[i],sels.getSnumber());
					}
				}
				/*map.put("snumber", s.getSrsnumber());
				map.put("gid", gid);
				Site sels = ism.selectBySnumber(map);
				System.out.println("sels.getSnumber()"+sels);*/
				//int selpstate = selectreservationornot(sels.getSid(),ostarttime[i],oendtime[i],sels.getSnumber());
				if(selpstate == 1) {
					//羽毛球场地对应的篮球场地已经预定了
					return "1";
				}
				//获得是预定的第几块场地
				//String num = s.getSnumber().substring(s.getSnumber().length()-1, s.getSnumber().length());
				String[] split = s.getSnumber().split("-");
				if(split.length == 2) {
					String code = s.getSnumber().split("-")[0];
					String num = s.getSnumber().split("-")[1];
					String oncode = oncode(code, num);
					/*String n = new Integer(num);*/
					//int number = n - 1;
					//String sitenumber = CodeUtil.sitenumber(2, number);
					if(num.equals("C")) {
						//查找上一块场地是否预定了
						int pstate = selectreservationornot(s.getSid(),ostarttime[i],oendtime[i],oncode);
						if(pstate == 1) {
							//抱歉，暂不可选，请选择其他场地
							return "s";
						}
					}
					//查找要预定的场地是否已经预定了
					int pstate1 = selectreservationornot(s.getSid(),ostarttime[i],oendtime[i],oncode);
					if(pstate1 == 1) {
						//羽毛球场地已经预定了
						return "2";
					}
				}
			}
			if(st.getStname().equals("篮球")) {
				//找到篮球场地对应的羽毛球场地是否预定了
				List<Site> lists = ism.selectBySrsnumber(s.getSnumber());
				for (int j = 0; j < lists.size(); j++) {
					int pstate1 = selectreservationornot(lists.get(i).getSid(),ostarttime[i],oendtime[i],lists.get(i).getSnumber());
					if(pstate1 == 1) {
						//篮球场地对应的羽毛球场地已经预定了
						return "3";
					}
				}
				//获得是预定的第几块场地
				/*String num = s.getSnumber().substring(s.getSnumber().length()-1, s.getSnumber().length());
				Integer n = new Integer(num);
				String sitenumber = CodeUtil.sitenumber(1, n);*/
				/*System.out.println("snumber"+s.getSnumber());
				System.out.println("s.getSid()"+s.getSid());
				System.out.println("ostarttime[i]"+ostarttime[i]);
				System.out.println("oendtime[i]"+oendtime[i]);*/
				//查找要预定的场地是否已经预定了
				int pstate1 = selectreservationornot(s.getSid(),ostarttime[i],oendtime[i],s.getSnumber());
				if(pstate1 == 1) {
					//篮球场地已经预定不可预定
					return "4";
				}
			}
			money += s.getSprice();//总消费金额消费金额
			o = new Orders(null, sid[i], oname, dt.formatDate(ostarttime[i], "yyyy-MM-dd HH:ss:mm"), dt.formatDate(oendtime[i], "yyyy-MM-dd HH:ss:mm"), time, s.getSprice(), 1, null, null);
			list.add(o);
		}
		//批量录入订单
		int result = om.insertorders(list);
		if(result > 0) {
			return "insert.orders.success";
		}
		return "insert.orders.fail";
	}
	
	/**
	 * 批量录入订单
	 * yangweihang
	 * @Date 2018年10月12日 下午1:37:46
	 * @param map
	 * @param oname
	 * @return
	 */
	public String insertorderss(JSONObject map,String oname) {
		//System.out.println("map"+map);
		StringBuffer sids = new StringBuffer();
		StringBuffer starttimes = new StringBuffer();
		StringBuffer endtimes = new StringBuffer();
		Integer gid = (Integer)map.get("gid");
		List<Map<String,Object>> list = (List<Map<String,Object>>)map.get("count");
		for (Map<String,Object> m : list) {
			Integer sid = (Integer)m.get("sid");
			if(sid == null) {
				return "not.site";
			}
			List<List<Object>> sl = (List<List<Object>>)m.get("value");
			for (List<Object> s : sl) {
				String starttime = s.get(0).toString();
				String endtime = s.get(1).toString();
				sids.append(sid);
				starttimes.append(starttime);
				endtimes.append(endtime);
				sids.append(",");
				starttimes.append(",");
				endtimes.append(",");
			}
		}
		sids.delete(sids.length()-1, sids.length());
		starttimes.delete(starttimes.length()-1, starttimes.length());
		endtimes.delete(endtimes.length()-1, endtimes.length());
		String[] strsid = sids.toString().split(",");
		Integer[] sid = new Integer[strsid.length];
		for (int i = 0; i < strsid.length; i++) {
			sid[i] = new Integer(strsid[i]);
			//判断当前是否是闭馆的场地
			Site s = ism.selectBySid(sid[i]);
			if(s.getPstate() == 2) {
				return "pstate";
			}
		}
		String[] ostarttime = starttimes.toString().split(",");
		String[] oendtime = endtimes.toString().split(",");
		//获得操作人
		return inserto(sid, oname, ostarttime, oendtime,gid);
	}
	
	public synchronized String inserto(Integer[] sid,String oname,String[] ostarttime,String[] oendtime,Integer gid) {
		for (int i = 0; i < sid.length; i++) {
			Map<String,Object> map1 = new HashMap<String,Object>();
			map1.put("sid", sid[i]);
			map1.put("starttime", ostarttime[i]);
			Orders o = om.selectbyss(map1);
			if(o != null) {
				return "orders.not.null";
			}
		}
		return insertorders(sid, oname, ostarttime, oendtime,gid);
	}
	
	/**
	 * 获得场地编码
	 * yangweihang
	 * @Date 2018年9月25日 上午11:01:17
	 * @param code
	 * @param num
	 * @return
	 */
	public String oncode(String code,String num) {
		String oncode = "";
		if(num != "C") {
			if(num.equals("D")) {
				num = "C";
				oncode = code + "-" + num;
			}else if(num.equals("E")) {
				num = "D";
				oncode = code + "-" + num;
			}else if(num.equals("F")) {
				num = "E";
				oncode = code + "-" + num;
			}
		}else if(num.equals("C")) {
			oncode = code + "-" + num;
		}
		return oncode;
	}
	
	/**
	 * 查询该场地的指定时间是否已经预定了
	 * yangweihang
	 * @Date 2018年9月14日 上午10:11:53
	 * @param snumber 体育馆场地编号
	 * @return
	 */
	public int selectreservationornot(Integer sid,String ostarttime,String oendtime,String sitenumber) {
		int pstate = 0;
		//按场地编号查询这块场地是否预定了
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("sid", sid);
		map.put("ostarttime", ostarttime);
		map.put("oendtime", oendtime);
		map.put("snumber", sitenumber);
		List<Site> s = ism.selectnotororder(map);
		if(s.size() >= 1) {
			pstate = 1;//已经预定了
		}
		return pstate;
	}
	
	/**
	 * 查询该人的订单
	 * yangweihang
	 * @Date 2018年9月14日 下午4:29:17
	 * @param oname	预订人
	 * @param starttime	开始时间
	 * @param endtime	结束时间
	 * @return
	 */
	public List<Map<String,Object>> selectByOname(String hcode, String oname,String starttime,String endtime,Integer pageNum,Integer pageSize) {
		DateTools dt = DateTools.getFactory();
		Map<String,Object> map = new HashMap<String,Object>();
		StringTools st = StringTools.getFactory();
		if(!st.isNullOrEmpty("oname")) {
			map.put("oname", oname);
		}
		map.put("starttime", starttime);
		map.put("endtime", endtime);
		if(pageNum == null) {
			pageNum = 0;
		}
		if(pageSize == null) {
			pageSize = 100000;
		}
		map.put("pageNum", pageNum);
		map.put("pageSize", pageSize);
		map.put("hcode", hcode);
		List<Map<String,Object>> list = om.selectByOname(map);
		List<Map<String,Object>> list1 = new ArrayList<Map<String,Object>>();
		StringBuffer sb = new StringBuffer();
		for (Map<String,Object> m : list) {
			Map<String,Object> map1 = new HashMap<String,Object>();
			/*//替换时间格式
			Date d = (Date)m.get("ostarttime");;
			//判断开始时间有没有超出当前时间
			if(d.getTime() > new Date().getTime()) {
				//超时了
				map1.put("states", 1);
			}else {
				map1.put("states", 2);
			}*/
			map1.put("states", m.get("states"));
			//查询对应的订单
			String otime = dt.formatDate((Date)m.get("otime"), "yyyy-MM-dd HH:mm:ss");
			List<Orders> os = selectbyotimes((Date)m.get("otime"));
			if(os.size() == 1) {
				//替换时间格式
				String start = dt.formatDate((Date)m.get("ostarttime"), "yyyy.MM.dd HH:mm");
				String end = dt.formatDate((Date)m.get("oendtime"), "HH:mm");
				sb.append(start);
				sb.append("-");
				sb.append(end);
				//状态
				if((int)m.get("state") == 1) {
					map1.put("state", "已预订");
					//判断是否是使用过了
					long date = dt.getMilliseconds((Date)m.get("oendtime"));
					Date today = new Date();
					long time = today.getTime();
					if(date < time) {
						map1.put("state", "已使用");
					}
				}else if((int)m.get("state") == 2) {
					map1.put("state", "已退订");
				}
				map1.put("snumber", m.get("snumber"));
				map1.put("oname", m.get("oname"));
				map1.put("sname", m.get("sname"));
				map1.put("ostarttime", sb.toString());
				String otime1 = dt.formatDate(os.get(0).getOtime(), "yyyy-MM-dd HH:mm:ss");
				//查询总价格
				/*Map<String,Object> map2 = new HashMap<String,Object>();
				map2.put("otime", os.get(0).getOtime());
				Double money = om.selectbymoney(map1);*/
				map1.put("money", os.get(0).getMoney());
				map1.put("otime", otime1);
				list1.add(map1);
				//清空StringBuffer
				sb.setLength(0);
			}else if(os.size() > 1) {
				//查询开始时间
				Orders o1 = selectbyotime((Date)m.get("otime"), 0, 1);
				//查找结束时间
				int num1 = os.size()-1;
				Orders o2 = selectbyotime((Date)m.get("otime"), num1, os.size());
				map1.put("sname", m.get("sname"));
				map1.put("ostarttime", sb.toString());
				//替换时间格式
				String start = dt.formatDate(o1.getOstarttime(), "yyyy.MM.dd HH:mm");
				String end = dt.formatDate(o2.getOendtime(), "HH:mm");
				sb.append(start);
				sb.append("-");
				sb.append(end);
				if((Integer)m.get("state") == 1) {
					map1.put("state", "已预订");
					//判断是否是使用过了
					long date = dt.getMilliseconds((Date)m.get("oendtime"));
					Date today = new Date();
					long time = today.getTime();
					if(date < time) {
						map1.put("state", "已使用");
					}
				}else if((int)m.get("state") == 2) {
					map1.put("state", "已退订");
				}
				//查询总价格
			/*	Map<String,Object> map2 = new HashMap<String,Object>();
				map2.put("otime", o1.getOtime());
				Double money = om.selectbymoney(map1);*/
				map1.put("money", o1.getMoney());
				map1.put("snumber", m.get("snumber"));
				map1.put("oname", o1.getOname());
				map1.put("sname", m.get("sname"));
				map1.put("ostarttime", sb.toString());
				String otime1 = dt.formatDate(o1.getOtime(), "yyyy-MM-dd HH:mm:ss");
				map1.put("otime", otime1);
				list1.add(map1);
				sb.setLength(0);
			}
		}
		List<Map<String, Object>> count = om.selectbyorderscount(map);
		Map<String,Object> maps = new HashMap<String,Object>();
		maps.put("count", count.size());
		list1.add(maps);
		return list1;
	}
	
	/**
	 * 查询多个订单
	 * yangweihang
	 * @Date 2018年9月30日 上午11:08:55
	 * @param otime
	 * @return
	 */
	public List<Orders> selectbyotimes(Date otime){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("otime", otime);
		return om.selectbyotimes(map);
	}
	
	
	/**
	 * 查询一个订单
	 * yangweihang
	 * @Date 2018年9月30日 上午11:00:37
	 * @param otime
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public Orders selectbyotime(Date otime,Integer pageNum,Integer pageSize) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("otime", otime);
		map.put("pageNum", pageNum);
		map.put("pageSize", pageSize);
		return om.selectbyotime(map);
	}
	
	/**
	 * 批量退订
	 * yangweihang
	 * @Date 2018年9月14日 下午8:08:39
	 * @param oid 订单id
	 * @return
	 */
	public String updatestate(String otime) {
		DateTools dt = DateTools.getFactory();
		/*List<Orders> list = new ArrayList<Orders>();*/
		/*for (int i = 0; i < oid.length; i++) {
			//查询是否存在不是已预订的状态
			Orders o = om.selectbystate(oid[i]);
			if(o != null) {
				if(o.getState() == 1) {
					//您好，此预订不能退订，谢谢
					return "Hello, this reservation is unrefundable, thank you";
				}else if(o.getState() == 2) {
					//您已退订，请勿重复操作
					return "Please do not repeat after you unsubscribe";
				}
			}
			Orders os = new Orders();
			os.setOid(oid[i]);
			list.add(os);
		}*/
		int result = 0;
		String[] otimes = otime.split(",");
		if(otimes.length > 1) {
			for (int i = 0; i < otimes.length; i++) {
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("otime", otimes[i]);
				result = om.updatestate(map);
			}
		}else {
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("otime", otime);
			result = om.updatestate(map);
		}
		if(result > 0) {
			return "order.unsubscribe.success";
		}
		return "order.unsubscribe.fail";
	}
	
	/**
	 * 查询场地预定信息
	 * yangweihang
	 * @Date 2018年9月15日 上午11:09:25
	 * @param map
	 * @return
	 */
	public List<Map<String,Object>> selectbyorders(String starttime,String endtime,Integer pageNum,Integer pageSize){
		endtime = endtime +" 23:59:59";
		DateTools dt = DateTools.getFactory();
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("starttime", starttime);
		map.put("endtime", endtime);
		map.put("pageNum", pageNum);
		map.put("pageSize", pageSize);
		List<Map<String, Object>> list = om.selectbyorders(map);
		List<Map<String,Object>> list1 = new ArrayList<Map<String,Object>>();
		for (Map<String, Object> m : list) {
			StringBuffer sb = new StringBuffer();
			//替换时间格式
			Date d = (Date)m.get("ostarttime");
			String start = dt.formatDate(d, "yyyy.MM.dd HH:mm");
			String end = dt.formatDate((Date)m.get("oendtime"), "HH:mm");
			sb.append(start);
			sb.append("-");
			sb.append(end);
			m.replace("ostarttime", sb.toString());
			String otime = dt.formatDate((Date)m.get("otime"), "yyyy-MM-dd HH:mm:ss");
			m.replace("otime", otime);
			//判断开始时间有没有超出当前时间
			if(d.getTime() > new Date().getTime()) {
				//超时了
				m.put("state", 1);
			}else {
				m.put("state", 2);
			}
		}
		return list;
	}
	
	/**
	 * 查询场地预定信息总数
	 * yangweihang
	 * @Date 2018年9月20日 下午2:06:00
	 * @param map
	 * @return
	 */
	public List<Map<String,Object>> selectbyorderscount(String starttime,String endtime,Integer pageNum,Integer pageSize){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("starttime", starttime);
		map.put("endtime", endtime);
		map.put("pageNum", pageNum);
		map.put("pageSize", pageSize);
		List<Map<String, Object>> list = om.selectbyorderscount(map);
		return list;
	}
	
	/**
	 * 按日期查询场馆预定情况
	 * yangweihang
	 * @Date 2018年9月15日 上午11:09:25
	 * @param map
	 * @return
	 */
	public List<Map<String,Object>> selectbyordersyd(Selectbyordersyd sb){
		DateTools dt = DateTools.getFactory();
		List<Map<String, Object>> list = om.selectbyordersyd(sb);
		for (Map<String, Object> m : list) {
			m.replace("ostarttime", dt.formatDate((Date)m.get("ostarttime"), "yyyy-MM-dd HH:ss:mm"));
			m.replace("oendtime", dt.formatDate((Date)m.get("oendtime"), "yyyy-MM-dd HH:ss:mm"));
		}
		return list;
	}
	
	/**
	 * 按日期查询场馆预定情况
	 * yangweihang
	 * @Date 2018年9月15日 上午11:09:25
	 * @param map
	 * @return
	 * @throws JSONException 
	 */
	public JSONArray selectbyordersyds(Selectbyordersyd sb) throws Exception{
		//array[{"time":"10.10","value":["20:00:00","22:00:00","19:00:00","21:00:00"]},{"time":"10.11","value":[]},{"time":"10.12","value":[]},{"time":"10.13","value":[]},{"time":"10.14","value":[]},{"time":"10.15","value":[]},{"time":"10.16","value":[]}]
		JSONArray array = null;//大
		JSONObject jsonObject = null;
		Set<String> set = null;
		String f = "";
		DateTools dt = DateTools.getFactory();
		array = new JSONArray();
		for (int l = 0; l < 7; l++) {
			if(l != 0) {
				Date d = dt.formatDate(sb.getServenday(), "yyyy-MM-dd HH:ss:mm");
				Date d1 = dt.addDay(d, 1);
				f = dt.formatDate(d1, "yyyy-MM-dd HH:ss:mm");
				sb.setServenday(f);
			}
			sb.setState(1);
			List<Map<String, Object>> list = om.selectbyordersyd(sb);
			if(!list.isEmpty() && list != null) {
					String one1 = dt.formatDate((Date) list.get(0).get("ostarttime"), "yyyy-MM-dd HH:ss:mm").split(" ")[0];
					String two1 = one1.split("-")[1];
					String three1 = one1.split("-")[2];
					String str = two1 + "." + three1;
					jsonObject = new JSONObject();
					set = new HashSet<String>();
					jsonObject.put("time", str);
					for (Map<String, Object> m : list) {
						String one = dt.formatDate((Date) m.get("ostarttime"), "yyyy-MM-dd HH:ss:mm").split(" ")[0];
						String o = dt.formatDate((Date) m.get("ostarttime"), "yyyy-MM-dd HH:ss:mm").split(" ")[1];
						String two = one.split("-")[1];
						String three = one.split("-")[2];
						String time = two + "." + three;
						if (jsonObject.get("time").equals(time)) {
							set.add(o);
						}
					}
					jsonObject.put("value", set);
					array.add(jsonObject);
			}else {
				if(f != null && !f.equals("")) {
					jsonObject = new JSONObject();
					String one = f.split(" ")[0];
					String two = one.split("-")[1];
					String three = one.split("-")[2];
					String time = two + "." + three;
					jsonObject.put("time", time);
					jsonObject.put("value", new String[] {});
					array.add(jsonObject);
				}else {
					jsonObject = new JSONObject();
					String one = sb.getServenday().split(" ")[0];
					String two = one.split("-")[1];
					String three = one.split("-")[2];
					String time = two + "." + three;
					jsonObject.put("time", time);
					jsonObject.put("value",  new String[] {});
					array.add(jsonObject);
				}
			}
		}
		//System.out.println("array"+array);
		return array;
	}
	
	/**
	 * 按日期查询场馆预定情况前台
	 * yangweihang
	 * @Date 2018年9月15日 上午11:09:25
	 * @param map
	 * @return
	 * @throws JSONException 
	 */
	public List<Sitetime> selectbyordersydss(Selectbyordersyd sb){
		//System.out.println("sb"+sb);
		DateTools dt = DateTools.getFactory();
		List<Map<String, Object>> list = om.selectbyordersydss(sb);
		List<String> l = new ArrayList<String>();
		List<Integer> sid = new ArrayList<Integer>();
		List<String> time = null;
		List<Sitetime> st = new ArrayList<Sitetime>();
		for (Map<String, Object> m : list) {
			if(!sid.contains((Integer)m.get("sid"))) {
				sid.add((Integer) m.get("sid"));
			}
		}
		for (int i = 0; i < sid.size(); i++) {
			Sitetime s = new Sitetime();
			s.setSid(sid.get(i));
			st.add(s);
		}
		Integer sids = 0;
		for (int i = 0; i < sid.size(); i++) {
			time = new ArrayList<String>();
			for(Map<String, Object> m : list) {
				if(sid.get(i) == (Integer) m.get("sid")) {
					if(m.get("ostarttime") != null) {
						String o = dt.formatDate((Date) m.get("ostarttime"), "yyyy-MM-dd HH:ss:mm").split(" ")[1];
						time.add(o);
						sids = (Integer) m.get("sid");
					}
				}
			}
			for (int k = 0; k < st.size(); k++) {
				if(st.get(k).getSid().equals(sids)) {
					st.get(k).setTime(time);
					break;
				}
			}
		}
		//System.out.println("st"+st);
		return st;
	}
	
	/**
	 * 判断体育馆休息日期
	 * yangweihang
	 * @Date 2018年9月26日 下午1:31:51
	 * @param sb
	 * @return
	 */
	public String selectbyweek(Integer gid,String servenday) {
		GymnasiumMessage g = imm.selectbygid(gid);
		String str = "1";
		if(g != null) {
			//场馆闭馆星期
			String[] time = g.getGclosedtime().split(",");
			int week = 0;
			try {
				week = DateTools.dayForWeek(servenday);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			for (int i = 0; i < time.length; i++) {
				if(week == new Integer(time[i])) {
					str = "0";
					return str;
				}
			}
			//场馆闭馆状态
			if(g.getState() == 2) {
				str = "0";
			}
		}
		return str;
		/*System.out.println("sb"+sb);
		DateTools dt = DateTools.getFactory();
		List<Map<String, Object>> list = om.selectbyordersydss(sb);
		System.out.println("list"+list);
		int a = 0;
		boolean flag = true;
		String str = "1";
		//判断星期几
		for (Map<String, Object> m : list) {
			String loctime = (String)m.get("gclosedtime");
			String[] time = loctime.split(",");
			if(time != null) {
				int week = 0;
				try {
					week = DateTools.dayForWeek(sb.getServenday());
				} catch (Exception e) {
					e.printStackTrace();
				}
				for (int i = 0; i < time.length; i++) {
					if(week == new Integer(time[i])) {
						flag = false;
						str = "0";
						break;
					}
				}
			}
			a++;
		}
		return str;*/
	}
	
	/**
	 * 查询当天的使用的人数和场地数
	 * yangweihang
	 * @Date 2018年9月17日 下午1:37:54
	 * @return
	 */
	public Map<String,Object> selectbycount(String time,String hcode){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("hcode", hcode);
		Map<String, Object> selmap = om.selectbycount(map);
		return selmap;
	}
	
	/**
	 * 按日期查询场馆预定情况
	 * yangweihang
	 * @Date 2018年9月15日 上午11:09:25
	 * @param map
	 * @return
	 * @throws JSONException 
	 */
	@SuppressWarnings("rawtypes")
	public List<Map<String,Object>> selectbyordersydhou(Selectbyordersyd sb) {
		sb.setStname(null);
		Map<String,Object> map = null;
		List<Map<String,Object>> ls = new ArrayList<Map<String,Object>>();
		String f = "";
		List<Map<String,Object>> lis = new ArrayList<Map<String,Object>>();
		StringTools st = StringTools.getFactory();
		DateTools dt = DateTools.getFactory();
		String str = "";
		String date1 = "";
		String snumbers = "";
		for (int l = 0; l < sb.getNum(); l++) {
			if(l != 0) {
				Date d = dt.formatDate(sb.getServenday(), "yyyy-MM-dd HH:ss:mm");
				Date d1 = dt.addDay(d, 1);
				f = dt.formatDate(d1, "yyyy-MM-dd HH:ss:mm");
				sb.setServenday(f);
			}else {
				Date d = dt.formatDate(sb.getServenday(), "yyyy-MM-dd HH:ss:mm");
				f = dt.formatDate(d, "yyyy-MM-dd HH:ss:mm");
			}
			sb.setState(1);
			List<Map<String, Object>> list = om.selectbyordersyd(sb);
			List<String> strl = new ArrayList<String>();
			if(!list.isEmpty() && list != null) {
				String snum = (String)list.get(0).get("snumber");
				String[] sp = snum.split("-");
				if(sp.length == 2) {
					String snumber = snum.split("-")[0];
					String date = dt.formatDate((Date)list.get(0).get("ostarttime"), "yyyy-MM-dd HH:ss:mm");
					strl.add(snumber+"="+date);
				}else {
					String date = dt.formatDate((Date)list.get(0).get("ostarttime"), "yyyy-MM-dd HH:ss:mm");
					strl.add((String)list.get(0).get("snumber")+"="+date);
				}
				for (int i = 1; i < list.size(); i++) {
					String snum1 = (String)list.get(i).get("snumber");
					String[] sp1 = snum.split("-");
					if(sp1.length == 2) {
						snumbers = snum1.split("-")[0];
						date1 = dt.formatDate((Date)list.get(i).get("ostarttime"), "yyyy-MM-dd HH:ss:mm");
						str = snumbers+"="+date1;
					}else {
						snumbers = (String)list.get(i).get("snumber");
						date1 = dt.formatDate((Date)list.get(i).get("ostarttime"), "yyyy-MM-dd HH:ss:mm");
						str = snumbers+"="+date1;
					}
					if(!strl.contains(str) && !snumbers.equals("by001")) {
						strl.add(snumbers+"="+date1);
					}else {
						strl.add(snumbers+"="+date1);
					}
				}
				//System.out.println("strl"+strl);
				if(!strl.isEmpty()) {
					for (int i = 0; i < strl.size(); i++) {
						map = new HashMap<String,Object>();
						String one = strl.get(i).split("=")[0];
						String two = strl.get(i).split("=")[1];
						map.put("snumber", one+"%");
						map.put("ostarttime", two);
						//map.put("stname", sb.getStname());
						map.put("gid", sb.getGid());
						if(one.equals("by001")) {
							map.put("str", "1");
							map.put("sid", sb.getSid());
						}
						//System.out.println("map"+map);
						List<Map<String, Object>> sell = om.selectodersbysite(map);
						System.out.println("sell"+sell);
						List<Map<String,Object>> ss = new ArrayList<Map<String,Object>>();
						List<String> ll = new ArrayList<String>();
						for (Map<String, Object> m : sell) {
							Map<String,Object> m1 = new HashMap<String,Object>();
							String snumber = (String)m.get("snumber");
							Integer sid = (Integer)m.get("sid");
							m1.put("snumber", snumber);
							m1.put("sname", m.get("sname"));
							m1.put("sid", sid);
							ss.add(m1);
							String[] split = snumber.split("-");
//							String snums = null;
//							if(split.length == 2) {
//								snums = sb.getGid()+"-"+split[0];
//							}else {
//								snums = sb.getGid()+"-"+snumber;
//							}
//							ll.add(snums);
							if(split.length == 2 && split[1].equals("A") || split[1].equals("B")) {
								String[] s1 = new String[]{"C","D","E","F"};
								for (int j = 0; j < 2; j++) {
									Map<String,Object> mm = new HashMap<String,Object>();
									if(split[1].equals("A")) {
										if(j == 0) {
											mm.put("snumber", split[0]+"-"+s1[0]);
										}else {
											mm.put("snumber", split[0]+"-"+s1[2]);
										}
									}else if(split[1].equals("B")){
										if(j == 0) {
											mm.put("snumber", split[0]+"-"+s1[1]);
										}else {
											mm.put("snumber", split[0]+"-"+s1[3]);
										}
									}
									mm.put("sname", "名");
									mm.put("sid", 0);
									//System.out.println("mm1--"+mm);
									ss.add(mm);
								}
							}else if(split.length == 2 && split[1].equals("C") || split[1].equals("E") || split[1].equals("D") || split[1].equals("F")) {
								String[] s1 = new String[]{"A","B"};
								Map<String,Object> mm = new HashMap<String,Object>();
								if(split[1].equals("C") || split[1].equals("E")) {
									mm.put("snumber", split[0]+"-"+s1[0]);
								}else if(split[1].equals("D") || split[1].equals("F")) {
									mm.put("snumber", split[0]+"-"+s1[1]);
								}
								mm.put("sname", "名");
								mm.put("sid", 0);
								//System.out.println("mm2--"+mm);
								ss.add(mm);
							}
						}
						System.out.println("ss"+ss);
						System.out.println("ll"+ll);
						Map<String,Object> map1 = new HashMap<String,Object>();
						System.out.println("ssssss"+strl.get(i));
						/*String time = strl.get(i).split(" ")[0];
						String[] ds = new String[] {"00:00:00","01:00:00","02:00:00","03:00:00","04:00:00","05:00:00","06:00:00","07:00:00","08:00:00","09:00:00","10:00:00","11:00:00","12:00:00","13:00:00","14:00:00","15:00:00","16:00:00","17:00:00","18:00:00","19:00:00","20:00:00","21:00:00","22:00:00","23:00:00"};
						for (int j = 0; j < ll.size(); j++) {
							int gid = new Integer(ll.get(j).split("-")[0]);
							String snum1 = ll.get(j).split("-")[1];
							Map<String,Object> mss = new HashMap<String,Object>();
							mss.put("gid", gid);
							mss.put("snumber", snum1+"%");
							//判断当前是否是闭馆的场地
							List<Site> sl = ism.selectbysname(mss);
							for (int k = 0; k < sl.size(); k++) {
								if(sl.get(l).getPstatus() == 2) {
									for (int k2 = 0; k2 < ds.length; k2++) {
										Map<String,Object> m = new HashMap<String,Object>();
										m.put(time+" "+ds[k2], ss);
										ls.add(m);
									}
								}
							}
						}*/
						map1.put(strl.get(i), ss);
						ls.add(map1);
					}
					System.out.println("ls"+ls);
					Map<String,Object> m = new HashMap<String,Object>();
					String one1 = f.split(" ")[0];
					String two1 = one1.split("-")[1];
					String three = one1.split("-")[2];
					String time = two1 + "-" + three;
					m.put(time, ls);
					lis.add(m);
				}
			}else {
				Map<String,Object> m = new HashMap<String,Object>();
				String one1 = f.split(" ")[0];
				String two1 = one1.split("-")[1];
				String three = one1.split("-")[2];
				String time = two1 + "-" + three;
				m.put(time, new ArrayList());
				lis.add(m);
			}
		}
//		for (int i = 0; i < lis.size(); i++) {
//			System.out.println("--"+lis.get(i));
//		}
		System.out.println("lis"+lis);
		return lis;
	}
	
}
