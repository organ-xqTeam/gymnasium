package com.xq.gymnasium.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xq.gymnasium.model.InsertSite;
import com.xq.gymnasium.model.SelectSite;
import com.xq.gymnasium.model.Site;
import com.xq.gymnasium.model.Sitetype;
import com.xq.gymnasium.service.ISiteService;
import com.xq.gymnasium.service.ISitetypeService;

/**
 * 体育馆场地
 * @ClassName SiteController
 * @Author yangweihang
 * @Date 2018年9月12日 下午8:05:58
 */
@RestController
@RequestMapping("/site")
public class SiteController {

	@Autowired
	private ISiteService its;
	
	@Autowired
	private ISitetypeService iss;
	
	@Autowired
	private HttpServletRequest request;
	
	/**
	 * 新增场地
	 * yangweihang
	 * @Date 2018年9月13日 上午8:35:31
	 * @param stid 体育馆场地类别id
	 * @param srsnumber 羽毛球场地关联篮球场地
	 * @param sname 体育馆场地名称
	 * @param snumber 体育馆场地编号
	 * @param sprice 费用
	 * @param pstatus 发布状态 1.可预订 2.不可预订
	 * @param sremark 备注
	 * @param num 羽毛球场地数
	 * @return
	 * 新增篮球场地:http://localhost:8089/site/insertsite?stid=5&sname=篮球场地1&sname=篮球场地2&sprice=40&pstatus=1&sremark=没什么
	 * 新增羽毛球场地:http://localhost:8089/site/insertsite?stid=6&srsnumber=b001&sname=羽毛球场地1&sprice=50&pstatus=1&sremark=没什么&num=4
	 */
	@RequestMapping("/insertsite")
	public String insertsite(Integer stid,String srsnumber,String sname,Double sprice,Integer pstatus,String sremark,String num) {
		InsertSite is = new InsertSite(stid, srsnumber, sname, sprice, pstatus, sremark, num);
		//根据stid查询场地类别
		Sitetype st = iss.selectById(is.getStid());
		String result = "";
		if(st != null) {
			//新增场地
			result = its.insertsite(st,is);
		}
		return result;
	}
	
	/**
	 * 查询体育馆的篮球场地编号
	 * yangweihang
	 * @Date 2018年9月13日 上午11:27:44
	 * @param gid
	 * @return
	 * http://localhost:8089/site/selectsnumber?gid=1
	 */
	@RequestMapping("/selectsnumber")
	public List<String> selectsnumber(Integer gid){
		List<String> list = its.selectsnumber(gid);
		return list;
	}
	
	/**
	 * 关联篮球场地名称
	 * yangweihang
	 * @Date 2018年9月13日 上午11:27:44
	 * @param gid
	 * @return
	 * http://localhost:8089/site/selectsnumber?gid=1
	 */
	@RequestMapping("/selectsnumbers")
	public Site selectsnumbernum(Integer gid,String num){
		String str = "";
		Site st = null;
		List<String> list = its.selectsnumber(gid);
		for (int i = 0; i < list.size(); i++) {
			String snumber = list.get(i);
			String s = snumber.substring(snumber.length()-1, snumber.length());
			if(s.equals(num)) {
				str = snumber;
				Map<String,Object> map = new HashMap<String,Object>();
				if(snumber != null) {
					map.put("snumber", snumber);
				}
				map.put("gid", gid);
				st = its.selectBySnumber(map);
				return st;
			}
		}
		return st;
	}
	
	/**
	 * 关联篮球场地
	 * yangweihang
	 * @Date 2018年9月22日 上午9:06:54
	 * @param gid
	 * @param stname
	 * @return
	 */
	@RequestMapping("/selectBylqSnumber")
	public List<Site> selectBylqSnumber(Integer gid,String stname) {
		List<Site> list = its.selectBylqSnumber(gid,stname);
		return list;
	}
	
	/**
	 * 查询体育馆场地
	 * yangweihang
	 * @Date 2018年9月13日 下午1:35:11
	 * @param ss 封装类
	 * @return
	 * http://localhost:8089/site/selectsite?sname=篮球1号场地&stname=篮球&pstatus=1&starttime=2018-09-09&endtime=2018-09-09&pageNum=1&pageSize=2
	 */
	@RequestMapping(value = "/selectsite" , produces="application/json;charset=UTF-8")
	public Map<String,Object> selectsite(SelectSite ss){
		Map<String,Object> map = new HashMap<String,Object>();
		List<Map<String, Object>> list = its.selectsite(ss);
		Integer count = its.selectbycounts(ss);
		map.put("list", list);
		map.put("count", count);
		return map;
	}
	
	/**
	 * 变更预定状态
	 * yangweihang
	 * @Date 2018年9月13日 下午4:41:29
	 * @param s
	 * @return
	 * http://localhost:8089/site/updatepstatus?sid=1
	 */
	@RequestMapping("/updatepstatus")
	public String updatepstatus(Site s) {
		String str = "";
		//按sid查询
		Site sels = its.selectBySid(s.getSid(),null,0);
		if(sels != null) {
			//变更预定状态
			str = its.updatepstatus(sels);
		}
		return str;
	}
	
	/**
	 * 按sid查询
	 * yangweihang
	 * @Date 2018年9月13日 下午5:01:19
	 * @param sid
	 * @return
	 * http://localhost:8089/site/selectBySite?sid=1
	 */
	@RequestMapping("/selectBySite")
	public Site selectBySite(int sid,String type,Integer gid) {
		Site s = its.selectBySid(sid,type,gid);
		return s;
	}
	
	/**
	 * 按体育场类别id查询场地
	 * yangweihang
	 * @Date 2018年9月17日 下午3:56:01
	 * @param stid
	 * @return
	 */
	@RequestMapping("/selectbystids")
	public List<Site> selectbystids(Integer stid){
		List<Site> list = its.selectbystids(stid);
		return list;
	}
	
	/**
	 * 编辑场地
	 * yangweihang
	 * @Date 2018年9月13日 下午5:09:26
	 * @param s
	 * @return
	 * http://localhost:8089/site/updatesite?sname=haha&sprice=50&sremark=dhdhajdas&sid=7
	 */
	@RequestMapping("/updatesite")
	public String updatesite(Site s,Integer gid) {
		String result = its.updatesite(s,gid);
		return result;
	}
	
	/**
	 * 删除场地
	 * yangweihang
	 * @Date 2018年9月13日 下午6:05:50
	 * @param sid
	 * @return
	 * http://localhost:8089/site/deletesite?sid=7
	 */
	@RequestMapping("/deletesite")
	public String deletesite(int sid) {
		String result = its.deletesite(sid);
		return result;
	}
	
	/**
	 * 按体育馆类别显示预定
	 * yangweihang
	 * @Date 2018年9月14日 上午9:02:46
	 * @param year 年份
	 * @param month 月份
	 * @param day 日
	 * @return
	 * http://localhost:8089/site/selectsitepstate?year=2018&month=9&day=9
	 */
	@RequestMapping("/selectsitepstate")
	public List<Map<String,Object>> selectsitepstate(String year,String month,String day){
		List<Map<String, Object>> list = its.selectsitepstate(year,month,day);
		return list;
	}
	
	/**
	 * 查询场地预定情况
	 * yangweihang
	 * @Date 2018年9月15日 上午10:33:12
	 * @param stname	场地类别名称
	 * @param starttime	开始时间
	 * @param endtime	结束时间
	 * @return
	 * http://localhost:8089/site/selectbygymaiumorder?stname=篮球&starttime=2018-09-09&&endtime=2019-09-09
	 */
	@RequestMapping("/selectbygymaiumorder")
	public List<Map<String,Object>> selectbygymaiumorder(String stname,String starttime,String endtime){
		List<Map<String, Object>> list = its.selectbygymaiumorder(stname, starttime, endtime);
		return list;
	}
	
	/**
	 * 按体育场类别id查询场地
	 * yangweihang
	 * @Date 2018年9月17日 下午3:56:01
	 * @param stid
	 * @return
	 */
	@RequestMapping("/selectbystid")
	public List<Site> selectbystid(Integer stid){
		String hcode = (String)request.getSession().getAttribute("hcode");
		List<Site> list = its.selectbystid(stid,hcode);
		return list;
	}
	
	/**
	 * 查询最受欢迎的场地
	 * yangweihang
	 * @Date 2018年9月17日 下午4:55:19
	 * @param map
	 * @return
	 */
	@RequestMapping("/selectbycount")
	public List<Map<String,Object>> selectbycount(String key,String value){
		List<Map<String, Object>> m = its.selectbycount(key,value);
		return m;
	}
	
	/**
	 * 根据体育馆id查询体育馆场地
	 * yangweihang
	 * @Date 2018年9月20日 上午6:41:21
	 * @param gid
	 * @return
	 */
	@RequestMapping("/selectbygid")
	public Site selectbygid(Integer gid) {
		Site s = its.selectbygid(gid);
		return s;
	}
	
	/**
	 * 查询体育馆下的场地
	 * yangweihang
	 * @Date 2018年9月21日 下午2:08:43
	 * @param sid
	 * @param gid
	 * @return
	 */
	@RequestMapping("/selectbysitegm")
	public Site selectbysitegm(Integer sid,Integer gid) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("sid", sid);
		map.put("gid", gid);
		Site s = its.selectBySnumber(map);
		return s;
	}
	
	/**
	 * 查询体育馆场地名称
	 * yangweihang
	 * @Date 2018年9月25日 下午1:37:21
	 * @param map
	 * @return
	 */
	@RequestMapping("/selectsname")
	public List<Site> selectsname(Integer gid){
		List<Site> list = its.selectsname(gid);
		return list;
	}
	
	/**
	 * 按条件查询
	 * yangweihang
	 * @Date 2018年9月25日 下午1:37:21
	 * @param map
	 * @return
	 */
	@RequestMapping("/selectBySnumbers")
	public List<Site> selectBySnumbers(String stname,Integer gid){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("stname", stname);
		map.put("gid", gid);
		return its.selectBySnumbers(map);
	}
	
	/**
	 * 获得场地下是否是开馆或闭关
	 * yangweihang
	 * @Date 2018年10月13日 下午2:18:27
	 * @param stname
	 * @param gid
	 * @return
	 */
	@RequestMapping("/selectBycloce")
	public Map<String,Object> selectBycloce(String stname,Integer gid){
		return its.selectBycloce(stname, gid);
	}
}
