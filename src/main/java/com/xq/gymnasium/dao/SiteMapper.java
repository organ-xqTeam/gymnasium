package com.xq.gymnasium.dao;

import java.util.List;
import java.util.Map;

import com.xq.gymnasium.model.Site;

/**
 * 体育馆场地
 * @ClassName SiteMapper
 * @Author yangweihang
 * @Date 2018年9月12日 下午7:52:36
 */
public interface SiteMapper {
	
	/**
	 * 新增体育馆场地
	 * yangweihang
	 * @Date 2018年9月12日 下午7:53:20
	 * @param list
	 * @return
	 */
	int insertsite(List<Site> list);
	
	/**
	 * 查询体育馆的篮球场地编号
	 * yangweihang
	 * @Date 2018年9月13日 上午11:22:54
	 * @param gid 体育馆id
	 * @return
	 */
	List<String> selectsnumber(Map<String,Object> map);
	
	/**
	 * 按体育馆id查询体育馆场地
	 * yangweihang
	 * @Date 2018年9月13日 下午1:35:11
	 * @param gid
	 * @return
	 */
	List<Map<String,Object>> selectsite(Map<String,Object> map);
	
	/**
	 * 变更预定状态
	 * yangweihang
	 * @Date 2018年9月13日 下午4:41:29
	 * @param s
	 * @return
	 */
	int updatepstatus(Site s);
	
	/**
	 * 按sid查询
	 * yangweihang
	 * @Date 2018年9月13日 下午4:42:04
	 * @param sid
	 * @return
	 */
	Site selectBySid(int sid);
	
	/**
	 * 编辑场地
	 * yangweihang
	 * @Date 2018年9月13日 下午5:09:26
	 * @param s
	 * @return
	 */
	int updatesite(Site s);
	
	/**
	 * 删除场地
	 * yangweihang
	 * @Date 2018年9月13日 下午6:05:50
	 * @param sid
	 * @return
	 */
	int deletesite(int sid);
	
	/**
	 * 按体育场类别显示预定
	 * yangweihang
	 * @Date 2018年9月14日 上午8:58:10
	 * @param map
	 * @return
	 */
	List<Map<String,Object>> selectsitepstate(Map<String,Object> map);
	
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
	 * 按羽毛球关联篮球场地查询
	 * yangweihang
	 * @Date 2018年9月14日 下午2:31:27
	 * @param srsnumber 
	 * @return
	 */
	List<Site> selectBySrsnumber(String srsnumber);
	
	/**
	 * 按羽毛球关联篮球场地查询
	 * yangweihang
	 * @Date 2018年9月14日 下午2:31:27
	 * @param srsnumber 
	 * @return
	 */
	Site selectBysnumbers(String snumber);
	
	/**
	 * 查询场地预定情况
	 * yangweihang
	 * @Date 2018年9月15日 上午10:21:40
	 * @param map
	 * @return
	 */
	List<Map<String,Object>> selectbygymaiumorder(Map<String,Object> map);
	
	/**
	 * 按体育场类别id查询场地
	 * yangweihang
	 * @Date 2018年9月17日 下午3:56:01
	 * @param stid
	 * @return
	 */
	List<Site> selectbystid(Map<String,Object> map);
	
	/**
	 * 按体育场类别id查询场地
	 * yangweihang
	 * @Date 2018年9月17日 下午3:56:01
	 * @param stid
	 * @return
	 */
	List<Site> selectbystids(Map<String,Object> map);
	
	/**
	 * 查询最受欢迎的场地
	 * yangweihang
	 * @Date 2018年9月17日 下午4:55:19
	 * @param map
	 * @return
	 */
	List<Map<String,Object>> selectbycount(Map<String,Object> map);
	
	/**
	 * 查询没有的体育场场地
	 * yangweihang
	 * @Date 2018年9月19日 下午6:41:11
	 * @param map
	 * @return
	 */
	List<Map<String,Object>> selectbynotsite(Map<String,Object> map);
	
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
	List<Map<String,Object>> selectbycounts(Map<String,Object> map);
	
	/**
	 * 查询场地最大的id
	 * yangweihang
	 * @Date 2018年9月22日 上午8:08:56
	 * @param map
	 * @return
	 */
	Site selectbymaxsid(Map<String,Object> map);
	
	/**
	 * 关联篮球场地
	 * yangweihang
	 * @Date 2018年9月22日 上午10:33:56
	 * @param map
	 * @return
	 */
	List<Site> selectBylqSnumber(Map<String,Object> map);
	
	/**
	 * 查询体育馆场地名称
	 * yangweihang
	 * @Date 2018年9月25日 下午1:37:21
	 * @param map
	 * @return
	 */
	List<Site> selectsname(Map<String,Object> map);
	
	/**
	 * 根据体育馆状态修改场地状态
	 * yangweihang
	 * @Date 2018年9月26日 下午7:47:10
	 * @param map
	 * @return
	 */
	int updatebygmstate(Map<String,Object> map);
	
	/**
	 * 按sname查询
	 * yangweihang
	 * @Date 2018年10月11日 下午4:31:38
	 * @param sname
	 * @return
	 */
	List<Site> selectbysname(Map<String,Object> map);
	
	/**
	 * 修改场地名称
	 * yangweihang
	 * @Date 2018年10月11日 下午4:37:40
	 * @param s
	 * @return
	 */
	int updatebysname(Site s);
	
	/**
	 * 按sid查询
	 * yangweihang
	 * @Date 2018年10月11日 下午4:47:52
	 * @param sid
	 * @return
	 */
	Site selectbysid(int sid);
}
