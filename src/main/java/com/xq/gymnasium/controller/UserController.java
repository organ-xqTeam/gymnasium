package com.xq.gymnasium.controller;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xq.gymnasium.util.InterfaceCallAndReturn;
import com.xq.gymnasium.util.UrlUtil;

/**
 * 用户
 * @ClassName UserController
 * @Author yangweihang
 * @Date 2018年9月5日 下午4:17:13
 */
@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private HttpSession session;
	
	/**
	 * 用户登录
	 * yangweihang
	 * @Date 2018年9月5日 下午4:27:27
	 * @param interfaceNum	接口号
	 * @param userName	用户名
	 * @param pwd	密码
	 * @return
	 * http://localhost:8089/user/userlogin?interfaceNum=1&userNames=hy&password=123456
	 */
	@RequestMapping("/userlogin")
	public String login(String interfaceNum,String userNames,String password) {
		/*JSONObject attribute = (JSONObject) session.getAttribute("user");
		JSONArray jsonarray = attribute.getJSONArray("userOffice");
		JSONObject object = (JSONObject) jsonarray.get(0);
		String deptCode = object.getString("deptCode");
		String hcode = object.getString("hospitalCode");*/
		String str = "";
		String url = UrlUtil.URL+"userController/userLoginIn";
		StringBuffer sb = new StringBuffer("?");
		sb.append("interfaceNum=" + interfaceNum);
		sb.append("&userNames=" + userNames);
		sb.append("&password=" + password);
		try {
			JSONObject json = InterfaceCallAndReturn.returnJson(url, sb, "data");
			String username = (String)json.get("userName");
			//用户
			if(username != null) {
				//会话跟踪用户
				request.getSession().setAttribute("username", username);
				request.getSession().setAttribute("json", json);
				//获得医院id
				JSONObject attribute = (JSONObject) session.getAttribute("json");
				JSONArray jsonarray = attribute.getJSONArray("userOffice");
				JSONObject object = (JSONObject) jsonarray.get(0);
				String deptCode = object.getString("deptCode");
				String hcode = object.getString("hospitalCode");
				request.getSession().setAttribute("hcode", hcode);
				str = "userlogin.success";
			//没有找到用户
			}else {
				str = "userlogin.fail";
			}
		} catch (Exception e) {
			e.printStackTrace();
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
		request.getSession().removeAttribute("userName");
		request.getSession().removeAttribute("json");
		return "exit.login";
	}

}
