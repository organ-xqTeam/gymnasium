package com.xq.gymnasium.service;

import java.util.List;
import java.util.Map;

import com.xq.gymnasium.model.InsertSite;
import com.xq.gymnasium.model.SelectSite;
import com.xq.gymnasium.model.Site;
import com.xq.gymnasium.model.Sitetype;

/**
 * 体育馆场地
 * @ClassName ISiteService
 * @Author yangweihang
 * @Date 2018年9月12日 下午8:02:10
 */
public interface ISiteService {
	
	/**
	 * 新增体育馆场地
	 * yangweihang
	 * @Date 2018年9月12日 下午7:53:20
	 * @param list
	 * @return
	 */
	String insertsite(Sitetype st,InsertSite is);
	
	/**
	 * 查询体育馆的篮球场地编号
	 * yangweihang
	 * @Date 2018年9月13日 上午11:22:54
	 * @param gid 体育馆id
	 * @return
	 */
	List<String> selectsnumber(Integer gid);
	
	/**
	 * 查询体育馆场地
	 * yangweihang
	 * @Date 2018年9月13日 下午1:35:11
	 * @param ss 封装类
	 * @return
	 */
	List<Map<String,Object>> selectsite(SelectSite ss);
	
	/**
	 * 变更预定状态
	 * yangweihang
	 * @Date 2018年9月13日 下午4:41:29
	 * @param s
	 * @return
	 */
	String updatepstatus(Site s);
	
	/**
	 * 按sid查询
	 * yangweihang
	 * @Date 2018年9月13日 下午4:42:04
	 * @param sid
	 * @return
	 */
	Site selectBySid(int sid,String type,Integer gid);
	
	/**
	 * 编辑场地
	 * yangweihang
	 * @Date 2018年9月13日 下午5:09:26
	 * @param s
	 * @return
	 */
	String updatesite(Site s,Integer gid);
	
	/**
	 * 删除场地
	 * yangweihang
	 * @Date 2018年9月13日 下午6:05:50
	 * @param sid
	 * @return
	 */
	String deletesite(int sid);
	
	/**
	 * 按体育场类别显示预定
	 * yangweihang
	 * @Date 2018年9月14日 上午8:58:10
	 * @param year 年份
	 * @param month 月份
	 * @param day 日
	 * @return
	 */
	List<Map<String,Object>> selectsitepstate(String year,String month,String day);
	
	/**
	 * 按体育馆场地查询
	 * yangweihang
	 * @Date 2018年9月14日 上午10:07:59
	 * @param map
	 * @return
	 */
	Site selectBySnumber(Map<String,Object> map);
	
	/**
	 * 查询指定的时间该场地是否预定了
	 * yangweihang
	 * @Date 2018年9月14日 上午11:42:23
	 * @param map
	 * @return
	 */
	List<Site> selectnotororder(Map<String,Object> map);
	
	/**
	 * 查询场地预定情况
	 * yangweihang
	 * @Date 2018年9月15日 上午10:23:09
	 * @param stname	场地类别名称
	 * @param starttime	开始时间
	 * @param endtime	结束时间
	 * @return
	 */
	List<Map<String,Object>> selectbygymaiumorder(String stname,String starttime,String endtime);
	
	/**
	 * 按体育场类别id查询场地
	 * yangweihang
	 * @Date 2018年9月17日 下午3:56:01
	 * @param stid
	 * @return
	 */
	List<Site> selectbystid(Integer stid,String hcode);
	
	/**
	 * 按体育场类别id查询场地
	 * yangweihang
	 * @Date 2018年9月17日 下午3:56:01
	 * @param stid
	 * @return
	 */
	List<Site> selectbystids(Integer stid);
	
	/**
	 * 查询最受欢迎的场地
	 * yangweihang
	 * @Date 2018年9月17日 下午4:55:19
	 * @param map
	 * @return
	 */
	List<Map<String,Object>> selectbycount(String key,String value);
	
	/**
	 * 根据体育馆id查询体育馆场地
	 * yangweihang
	 * @Date 2018年9月20日 上午6:41:21
	 * @param gid
	 * @return
	 */
	Site selectbygid(Integer gid);
	
	/**
	 * 查询全部体育馆场地总数
	 * yangweihang
	 * @Date 2018年9月20日 上午10:16:16
	 * @param map
	 * @return
	 */
	Integer selectbycounts(SelectSite ss);
	
	/**
	 * 关联篮球场地
	 * yangweihang
	 * @Date 2018年9月22日 上午10:33:56
	 * @param map
	 * @return
	 */
	List<Site> selectBylqSnumber(Integer gid,String stname);
	
	/**
	 * 查询体育馆场地名称
	 * yangweihang
	 * @Date 2018年9月25日 下午1:37:21
	 * @param map
	 * @return
	 */
	List<Site> selectsname(Integer gid);
	
	/**
	 * 按条件查询
	 * yangweihang
	 * @Date 2018年10月12日 上午9:05:40
	 * @param map
	 * @return
	 */
	List<Site> selectBySnumbers(Map<String,Object> map);
	
	/**
	 * 获得场地下是否是开馆或闭关
	 * yangweihang
	 * @Date 2018年10月13日 下午2:07:17
	 * @param stname
	 * @param gid
	 * @return
	 * 
	 */
	Map<String,Object> selectBycloce(String stname,Integer gid);
}
