package com.xq.gymnasium.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xq.gymnasium.dao.GymnasiumMessageMapper;
import com.xq.gymnasium.dao.OrdersMapper;
import com.xq.gymnasium.dao.SiteMapper;
import com.xq.gymnasium.dao.SitetypeMapper;
import com.xq.gymnasium.model.GymnasiumMessage;
import com.xq.gymnasium.model.InsertSite;
import com.xq.gymnasium.model.Orders;
import com.xq.gymnasium.model.SelectSite;
import com.xq.gymnasium.model.Site;
import com.xq.gymnasium.model.Sitetype;
import com.xq.gymnasium.service.ISiteService;
import com.xq.gymnasium.util.CodeUtil;
import com.xq.gymnasium.util.GclosedtimeUtil;
import com.xq.gymnasium.util.StringTools;

/**
 * 体育馆场地
 * @ClassName SiteService
 * @Author yangweihang
 * @Date 2018年9月12日 下午8:03:15
 */
@Service("its")
public class SiteService implements ISiteService {
	
	@Autowired
	private SiteMapper ism;
	
	@Autowired
	private SitetypeMapper sm;
	
	@Autowired
	private GymnasiumMessageMapper imm;
	
	@Autowired
	private OrdersMapper om;
	
	/**
	 * 新增体育馆场地
	 * yangweihang
	 * @Date 2018年9月12日 下午7:53:20
	 * @param list
	 * @return
	 */
	public String insertsite(Sitetype st,InsertSite is) {
		String str = "";
		List<Site> list = new ArrayList<Site>();
		//新增篮球场地
		String snumber = "";
		if(st != null && st.getStname().equals("篮球")) {
			if(is.getSname() != null) {
				//获得场地编号
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("stid", is.getStid());
				map.put("stname", "篮球");
				Site snumbers = ism.selectbymaxsid(map);
				Integer nums = 0;
				if(snumbers != null) {
					String num = snumbers.getSnumber().split("-")[0].substring(snumbers.getSnumber().split("-")[0].length()-1, snumbers.getSnumber().split("-")[0].length());
					nums = new Integer(num);
				}
				snumber = CodeUtil.sitenumber(1, nums + 1);
				for (int i = 0; i < 2; i++) {
					String str1 = "";
					if(i == 1) {
						nums += 1;
						str1 = "-B";
					}else {
						str1 = "-A";
					}
					str1 = snumber + str1;
					//把site对象放在list中
					Site s = new Site(null, is.getStid(), null, is.getSname(), str1, is.getSprice(), 1, is.getPstatus(), is.getSremark(),new Date(),null,null,null);
					list.add(s);
				}
				//新增篮球场地
				int result = ism.insertsite(list);
				if(result > 0) {
					str = "insert.bk.success";
				}else {
					str = "insert.bk.fail";
				}
			}else {
				str = "insert.bk.error";
			}
			//增加羽毛球场地
		}else if(st != null && st.getStname().equals("羽毛球")) {
			if(is.getSname() != null) {
				String str1 = null;
				if(!is.getNum().equals("0")) {
					//获得场地编号
					Integer nums = 0;
					String sst = "";
					if(is.getSrsnumber() != null) {
						sst = is.getSrsnumber().split("-")[0];
					}
					//获得羽毛球场地编号
					snumber = CodeUtil.sitenumber(2, nums + 1);
					str1 = sst + "-" + is.getNum();
				}else {
					String sst = "";
					if(is.getSrsnumber() != null) {
						sst = is.getSrsnumber().split("-")[0];
					}
					String s = sst.substring(0,1);
					String sd = sst.substring(1);
					str1 = s+"y"+sd;
				}
				//把site对象放在list中
				Site s = new Site(null, is.getStid(), is.getSrsnumber(), is.getSname(), str1, is.getSprice(), 1, is.getPstatus(), is.getSremark(),new Date(),null,null,null);
				list.add(s);
				//新增羽毛球场地
				int result = ism.insertsite(list);
				if(result > 0) {
					str = "insert.bm.success";
				}else {
					str = "insert.bm.fail";
				}
			}else {
				str = "insert.bm.error";
			}
		}else {
			str = "insert.error";
		}
		return str;
	}
	
	/**
	 * 查询体育馆的篮球场地编号
	 * yangweihang
	 * @Date 2018年9月13日 上午11:22:54
	 * @param gid 体育馆id
	 * @return
	 */
	public List<String> selectsnumber(Integer gid){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("stname", "篮球");
		map.put("gid", gid);
		return ism.selectsnumber(map);
	}
	
	/**
	 * 查询体育馆场地
	 * yangweihang
	 * @Date 2018年9月13日 下午1:35:11
	 * @param ss 封装类
	 * @return
	 */
	public List<Map<String,Object>> selectsite(SelectSite ss){
		Map<String,Object> map = new HashMap<String,Object>();
		int pageNum = (ss.getPageNum() - 1) * ss.getPageSize();
		map.put("pageNum", pageNum);
		map.put("pageSize", ss.getPageSize());
		StringTools st = StringTools.getFactory();
		map.put("gid", ss.getGid());
		if(!st.isNullOrEmpty(ss.getSname())) {
			map.put("sname", ss.getSname());
		}
		if(!st.isNullOrEmpty(ss.getStname())) {
			map.put("stname", ss.getStname());
		}
		if(!st.isNullOrEmpty(ss.getPstatus())) {
			map.put("pstatus", ss.getPstatus());
		}
		if(!st.isNullOrEmpty(ss.getStarttime())) {
			map.put("starttime", ss.getStarttime());
		}
		if(!st.isNullOrEmpty(ss.getEndtime())) {
			map.put("endtime", ss.getEndtime());
		}
		map.put("states", 1);
		List<Map<String, Object>> list = ism.selectsite(map);
		for (Map<String, Object> m : list) {
			String gnumber = CodeUtil.gymnasiumnumber((int)m.get("gid"));
			//体育馆编号
			m.put("gnumber", gnumber);
			//获得闭馆时间
			String[] gts = m.get("gclosedtime").toString().split(",");
			String getgctime = GclosedtimeUtil.getgctime(gts);
			//开闭馆时间
			m.replace("gstarttime", "早"+m.get("gstarttime")+"-晚"+m.get("gendtime")+" "+getgctime);
			if((Integer)m.get("state") == 1) {
				m.put("state", "可预订");
			}else {
				m.put("state", "不可预订");
			}
		}
		return list;
	}
	
	/**
	 * 变更预定状态
	 * yangweihang
	 * @Date 2018年9月13日 下午4:41:29
	 * @param s
	 * @return
	 */
	public String updatepstatus(Site s) {
		String str = "";
		GymnasiumMessage g = imm.selectbygmstate(s.getSid());
		if(g != null) {
			if(g.getState() == 2) {
				str = "gm.cloce";
				return str;
			}
		}
		if(s.getPstatus() == 1) {
			s.setPstatus(2);
		}else {
			s.setPstatus(1);
		}
		int result = ism.updatepstatus(s);
		if(result > 0) {
			str = "site.change.success";
		}else {
			str = "site.change.fail";
		}
		return str;
	}
	
	/**
	 * 按sid查询
	 * yangweihang
	 * @Date 2018年9月13日 下午4:42:04
	 * @param sid
	 * @return
	 */
	public Site selectBySid(int sid,String type,Integer gid) {
		Site s = null;
		if(type == null) {
			s = ism.selectBySid(sid);
			return s;
		}
		if(type.equals("chakan")) {
			s = ism.selectBySid(sid);
			Sitetype st = sm.selectById(s.getStid());
			//场地类型
			s.setStrstname(st.getStname());
			//查询体育馆
			GymnasiumMessage g = imm.selectbygid(gid);
			s.setStrgname(g.getGname());
			if(s.getStrstname().equals("羽毛球")) {
				//查询羽毛球关联篮球场地
				Site s1 = ism.selectBysnumbers(s.getSrsnumber()+"-A");
				s.setSremark(s1.getSnumber());
			}
		}
		return s;
	}
	
	/**
	 * 编辑场地
	 * yangweihang
	 * @Date 2018年9月13日 下午5:09:26
	 * @param s
	 * @return
	 */
	public String updatesite(Site s,Integer gid) {
		String str = "";
		//按sid查询
		Site ss = ism.selectbysid(s.getSid());
		String sname = ss.getSname();
		String[] snum = ss.getSnumber().split("-");
		int result = ism.updatesite(s);
		boolean flag = true;
		if(snum.length == 2) {
			//查询场地名称相同的场地
			Map<String,Object> m = new HashMap<String,Object>();
			m.put("sname", sname);
			m.put("snum", snum[0]+"%");
			m.put("gid", gid);
			System.out.println("mmm"+m);
			List<Site> sl = ism.selectbysname(m);
			for(Site s1 : sl) {
				s1.setSname(s.getSname());
				//Integer sid = s1.getSid();
				int result1 = ism.updatebysname(s1);
				if(result1 <= 0) {
					flag = false;
				}
			}
		}
		//查询修改的一块场地
		if(result > 0 && flag) {
			str = "site.update.success";
		}else {
			str = "site.update.fail";
		}
		return str;
	}
	
	/**
	 * 删除场地
	 * yangweihang
	 * @Date 2018年9月13日 下午6:05:50
	 * @param sid
	 * @return
	 */
	public String deletesite(int sid) {
		String str = "";
		//修改对应订单
		List<Orders> list = om.selectbys(sid);
		if(list.size() > 0) {
			return str = "list";
		}
		int result = ism.deletesite(sid);
		if(result > 0) {
			str = "site.delete.success";
		}else {
			str = "site.delete.fail";
		}
		return str;
	}
	
	/**
	 * 按体育场类别显示预定
	 * yangweihang
	 * @Date 2018年9月14日 上午8:58:10
	 * @param year 年份
	 * @param month 月份
	 * @param day 日
	 * @return
	 */
	public List<Map<String,Object>> selectsitepstate(String year,String month,String day){
		Map<String,Object> map = new HashMap<String,Object>();
		StringTools st = StringTools.getFactory();
		if(!st.isNullOrEmpty(year)) {
			if(!st.isNullOrEmpty(month)) {
				if(!st.isNullOrEmpty(day)) {
					map.put("year", year);
					map.put("month", month);
					map.put("day", day);
				}
			}
		}
		List<Map<String, Object>> list = ism.selectsitepstate(map);
		return list;
	}
	
	/**
	 * 按体育馆场地编号查询
	 * yangweihang
	 * @Date 2018年9月14日 上午10:07:59
	 * @param map
	 * @return
	 */
	public Site selectBySnumber(Map<String,Object> map) {
		return ism.selectBySnumber(map);
	}
	
	/**
	 * 查询指定的时间该场地是否预定了
	 * yangweihang
	 * @Date 2018年9月14日 上午11:42:23
	 * @param map
	 * @return
	 */
	public List<Site> selectnotororder(Map<String,Object> map) {
		return ism.selectnotororder(map);
	}
	
	/**
	 * 查询场地预定情况
	 * yangweihang
	 * @Date 2018年9月15日 上午10:23:09
	 * @param stname	场地类别名称
	 * @param starttime	开始时间
	 * @param endtime	结束时间
	 * @return
	 */
	public List<Map<String,Object>> selectbygymaiumorder(String stname,String starttime,String endtime){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("stname", stname);
		map.put("starttime", starttime);
		map.put("endtime", endtime);
		List<Map<String, Object>> list = ism.selectbygymaiumorder(map);
		for (Map<String,Object> m : list) {
			String gnumber = CodeUtil.gymnasiumnumber((int)m.get("gid"));
			m.put("gnumber", gnumber);
			m.put("num", m.get("count")+"%");
			if((int)m.get("pstatus") == 1) {
				m.replace("pstatus", "可预订");
			}else {
				m.replace("pstatus", "不可预订");
			}
			//获得闭馆时间
			String[] gts = m.get("gclosedtime").toString().split(",");
			String getgctime = GclosedtimeUtil.getgctime(gts);
			//开闭馆时间
			m.replace("gstarttime", "早"+m.get("gstarttime")+"-晚"+m.get("gendtime")+" "+getgctime);
		}
		return list;
	}
	
	/**
	 * 按体育场类别id查询场地
	 * yangweihang
	 * @Date 2018年9月17日 下午3:56:01
	 * @param stid
	 * @return
	 */
	public List<Site> selectbystid(Integer stid,String hcode){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("stid", stid);
		map.put("hcode", hcode);
		List<Site> list = ism.selectbystid(map);
		return list;
	}
	
	/**
	 * 按体育场类别id查询场地
	 * yangweihang
	 * @Date 2018年9月17日 下午3:56:01
	 * @param stid
	 * @return
	 */
	public List<Site> selectbystids(Integer stid){
		Map<String,Object> map = new HashMap<String,Object>();
		StringTools st = StringTools.getFactory();
		map.put("stid", stid);
		map.put("state", 1);
		List<Site> list = ism.selectbystids(map);
		int i = 0;
		for (Site s : list) {
			String[] ss = list.get(i).getSnumber().split("-");
			if (ss.length == 2) {
				s.setSnumber(ss[0]);
			}else {
				s.setSnumber(list.get(i).getSnumber());
			}
			i++;
		}
		return list;
	}
	
	/**
	 * 查询最受欢迎的场地
	 * yangweihang
	 * @Date 2018年9月17日 下午4:55:19
	 * @param map
	 * @return
	 */
	public List<Map<String,Object>> selectbycount(String key,String value){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put(key, value);
		List<Map<String, Object>> list = ism.selectbycount(map);
		if(list != null && list.size() > 0) {
			if(list.get(0) == null) {
				list.removeAll(list);
			}
		}
		/*List<String> strlist = new ArrayList<String>();
		int i = 0;
		for (Map<String,Object> m : list) {
			
		}*/
		return list;
	}
	
	/**
	 * 根据体育馆id查询体育馆场地
	 * yangweihang
	 * @Date 2018年9月20日 上午6:41:21
	 * @param gid
	 * @return
	 */
	public Site selectbygid(Integer gid) {
		Site s = ism.selectbygid(gid);
		return s;
	}
	
	/**
	 * 查询全部体育馆场地总数
	 * yangweihang
	 * @Date 2018年9月20日 上午10:16:16
	 * @param map
	 * @return
	 */
	public Integer selectbycounts(SelectSite ss) {
		Map<String,Object> map = new HashMap<String,Object>();
		int pageNum = (ss.getPageNum() - 1) * ss.getPageSize();
		map.put("pageNum", pageNum);
		map.put("pageSize", ss.getPageSize());
		StringTools st = StringTools.getFactory();
		map.put("gid", ss.getGid());
		if(!st.isNullOrEmpty(ss.getSname())) {
			map.put("sname", ss.getSname());
		}
		if(!st.isNullOrEmpty(ss.getStname())) {
			map.put("stname", ss.getStname());
		}
		if(!st.isNullOrEmpty(ss.getPstatus())) {
			map.put("pstatus", ss.getPstatus());
		}
		if(!st.isNullOrEmpty(ss.getStarttime())) {
			map.put("starttime", ss.getStarttime());
		}
		if(!st.isNullOrEmpty(ss.getEndtime())) {
			map.put("endtime", ss.getEndtime());
		}
		List<Map<String,Object>> m = ism.selectbycounts(map);
		return m.size();
	}
	
	/**
	 * 关联篮球场地
	 * yangweihang
	 * @Date 2018年9月22日 上午10:33:56
	 * @param map
	 * @return
	 */
	public List<Site> selectBylqSnumber(Integer gid,String stname){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("stname", stname);
		map.put("gid", gid);
		List<Site> list = ism.selectBylqSnumber(map);
		List<Site> list1 = new ArrayList<Site>();
		for(Site s : list) {
			Site s1 = new Site();
			String number = s.getSnumber().split("-")[0];
			s.setStrnumber(number);
			s1.setSnumber(number);
			s1.setSname(s.getSname());
			list1.add(s1);
		}
		return list1;
	}
	
	/**
	 * 查询体育馆场地名称
	 * yangweihang
	 * @Date 2018年9月25日 下午1:37:21
	 * @param map
	 * @return
	 */
	public List<Site> selectsname(Integer gid){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("gid", gid);
		map.put("stname", "篮球");
		List<Site> list = ism.selectsname(map);
		List<Site> list1 = new ArrayList<Site>();
		Site s1 = new Site();
		for(Site s : list) {
			s1.setSnumber(s.getSnumber().split("-")[0]);
			s1.setSname(s.getSname());
			list1.add(s1);
		}
		return list1;
	}
}
