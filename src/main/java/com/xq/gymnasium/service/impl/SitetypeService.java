package com.xq.gymnasium.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xq.gymnasium.dao.SitetypeMapper;
import com.xq.gymnasium.model.Sitetype;
import com.xq.gymnasium.service.ISitetypeService;

/**
 * 场地类别
 * @ClassName SitetypeService
 * @Author yangweihang
 * @Date 2018年9月12日 下午2:54:49
 */
@Service("iss")
public class SitetypeService implements ISitetypeService {
	
	@Autowired
	private SitetypeMapper sm;
	
	/**
	 * 新增场地类别
	 * yangweihang
	 * @Date 2018年9月13日 下午8:25:06
	 * @param gid	体育馆id
	 * @param stnames	场地类别名称
	 * @return
	 */
	public String insertsitetype(Integer gid,Integer update) {
		String str = "insert.success";
		String[] stname = new String[] {"篮球","羽毛球"};
		List<Sitetype> list = new ArrayList<Sitetype>();
		for (int i = 0; i < stname.length; i++) {
			Sitetype sts = new Sitetype(null, gid, stname[i]);
			list.add(sts);
		}
		int result = sm.insertsitetype(list);
		if(result > 0) {
			str = "insert.success";
		}else {
			str = "insert.fail";
		}
		return str;
	}
	
	/**
	 * 删除场馆类别
	 * yangweihang
	 * @Date 2018年9月12日 下午2:08:48
	 * @param st
	 * @return
	 */
	public int deletesitetype(Integer gid) {
		int result = sm.deletesitetype(gid);
		return result;
	}
	
	/**
	 * 根据id查询
	 * yangweihang
	 * @Date 2018年9月12日 下午8:23:28
	 * @param stid
	 * @return
	 */
	public Sitetype selectById(Integer stid) {
		return sm.selectById(stid);
	}
	
	/**
	 * 按体育场id查询体育场类别
	 * yangweihang
	 * @Date 2018年9月13日 上午10:00:59
	 * @param gid 体育馆id
	 * @return
	 */
	public List<Sitetype> selectsitetype(Integer gid){
		return sm.selectsitetype(gid);
	}
	
	/**
	 * 按体育馆场地编号查询场地类别名称
	 * yangweihang
	 * @Date 2018年9月14日 上午10:23:25
	 * @param snumber 体育馆场地编号
	 * @return
	 */
	public Sitetype selectStnameBySnumber(String snumber) {
		return sm.selectStnameBySnumber(snumber);
	}
	
	/**
	 * 查询体育场类别
	 * yangweihang
	 * @Date 2018年9月17日 下午3:30:44
	 * @return
	 */
	public List<Sitetype> selectbysitetype(String hcode){
		return sm.selectbysitetype(hcode);
	}
	
	/**
	 * 按stname查询
	 * yangweihang
	 * @Date 2018年9月18日 下午10:58:35
	 * @param stname
	 * @return
	 */
	public Sitetype selectbystname(String stname) {
		return sm.selectbystname(stname);
	}
}
