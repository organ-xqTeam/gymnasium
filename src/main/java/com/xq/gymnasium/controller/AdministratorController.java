package com.xq.gymnasium.controller;


import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xq.gymnasium.model.Administrator;
import com.xq.gymnasium.service.IAdministratorService;

/**
 * 管理员
 * @ClassName AdministratorController
 * @Author yangweihang
 * @Date 2018年9月11日 下午12:15:41
 */
@RestController
@RequestMapping("/administrator")
public class AdministratorController {
	
	@Autowired
	private IAdministratorService ias;
	
	@Autowired
	private HttpServletRequest request;
	
	/**
	 * 新增管理员
	 * yangweihang
	 * @Date 2018年9月11日 下午12:07:16
	 * @param a	管理员类
	 * @return
	 * http://localhost:8089/administrator/insertadmin?aname=admin&ausername=admin&apwd=admin&aphonenum=123&aemile123@qq.com&arole=1
	 */
	@RequestMapping("/insertadmin")
	public String insertadmin(Administrator a) {
		String result = ias.insertadmin(a);
		return result;
	}
	
	/**
	 * 后台管理员登陆
	 * yangweihang
	 * @Date 2018年9月11日 下午1:32:11
	 * @param ausername	用户名
	 * @param apwd	密码
	 * @return
	 * http://localhost:8089/administrator/adminlogin?ausername=admin&apwd=admin
	 */
	@RequestMapping("/adminlogin")
	public String adminlogin(String ausername,String apwd) {
		String str = "";
		Administrator a = ias.selectadmin(ausername,apwd);
		if(a != null) {
			request.getSession().setAttribute("admin", a);
			str = "login.success";
		}else {
			str = "login.fail";
		}
		return str;
	}
	
	/**
	 * 退出
	 * yangweihang
	 * @Date 2018年9月6日 下午7:34:07
	 * @return
	 */
	@RequestMapping("/dropout")
	public String dropout() {
		request.getSession().invalidate();
		return "exit.login";
	}
	
	/**
	 * 按aid查询
	 * yangweihang
	 * @Date 2018年9月20日 上午11:13:10
	 * @return
	 */
	@RequestMapping("/selectByAid")
	public String selectByAid(){
		Administrator sela = (Administrator)request.getSession().getAttribute("admin");
		return sela.getApwd();
	}
	
	/**
	 * 修改密码
	 * yangweihang
	 * @Date 2018年9月20日 上午11:01:28
	 * @param apwd
	 * @return
	 */
	@RequestMapping("/updateaid")
	public String updateaid(Administrator a) {
		Administrator sela = (Administrator)request.getSession().getAttribute("admin");
		Administrator admin = ias.selectByAid(sela.getAid());
		admin.setApwd(a.getApwd());
		int result = ias.updateaid(admin);
		if(result > 0) {
			return "update.success";
		}
		return "update.fail";
	}
}
