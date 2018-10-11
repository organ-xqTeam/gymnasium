package com.xq.gymnasium.service;

import java.util.List;
import java.util.Map;

import com.xq.gymnasium.model.Sitetype;

/**
 * 场地类别
 * @ClassName ISitetypeService
 * @Author yangweihang
 * @Date 2018年9月12日 下午2:58:41
 */
public interface ISitetypeService {
	
	/**
	 * 新增场地类别
	 * yangweihang
	 * @Date 2018年9月12日 下午2:59:35
	 * @param st
	 * @return
	 */
	String insertsitetype(Integer gid,Integer update);
	
	/**
	 * 删除场馆类别
	 * yangweihang
	 * @Date 2018年9月12日 下午2:08:48
	 * @param st
	 * @return
	 */
	int deletesitetype(Integer gid);
	
	/**
	 * 根据id查询
	 * yangweihang
	 * @Date 2018年9月12日 下午8:23:28
	 * @param stid
	 * @return
	 */
	Sitetype selectById(Integer stid);
	
	/**
	 * 按体育场id查询体育场类别
	 * yangweihang
	 * @Date 2018年9月13日 上午10:00:59
	 * @param gid 体育馆id
	 * @return
	 */
	List<Sitetype> selectsitetype(Integer gid);
	
	/**
	 * 按体育馆场地编号查询场地类别名称
	 * yangweihang
	 * @Date 2018年9月14日 上午10:23:25
	 * @param snumber 体育馆场地编号
	 * @return
	 */
	Sitetype selectStnameBySnumber(String snumber);
	
	/**
	 * 查询体育场类别
	 * yangweihang
	 * @Date 2018年9月17日 下午3:30:44
	 * @return
	 */
	List<Sitetype> selectbysitetype(String hcode);
	
	/**
	 * 按stname查询
	 * yangweihang
	 * @Date 2018年9月18日 下午10:58:35
	 * @param stname
	 * @return
	 */
	Sitetype selectbystname(String stname);
}
