package com.xq.gymnasium.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xq.gymnasium.model.Sitetype;
import com.xq.gymnasium.service.ISitetypeService;

/**
 * 体育场类别
 * @ClassName SitetypeController
 * @Author yangweihang
 * @Date 2018年9月17日 下午3:32:06
 */
@RestController
@RequestMapping("sitetype")
public class SitetypeController {
	
	@Autowired
	private ISitetypeService iss;
	
	@Autowired
	private HttpServletRequest request;
	
	
	@RequestMapping("/selectbysitetype")
	public List<Sitetype> selectbysitetype(){
		String hcode = (String)request.getSession().getAttribute("hcode");
		List<Sitetype> list = iss.selectbysitetype(hcode);
		return list;
	}
	
	/**
	 * 按体育场id查询体育场类别
	 * yangweihang
	 * @Date 2018年9月13日 上午10:00:59
	 * @param gid 体育馆id
	 * @return
	 */
	@RequestMapping("/selectsitetype")
	public List<Sitetype> selectsitetype(Integer gid){
		return iss.selectsitetype(gid);
	}
}
