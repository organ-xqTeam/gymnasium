package com.xq.gymnasium.controller;


import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xq.gymnasium.model.GymnasiumMessage;
import com.xq.gymnasium.model.Sitetype;
import com.xq.gymnasium.service.IAdministratorService;
import com.xq.gymnasium.service.IGymnasiumMessageService;
import com.xq.gymnasium.service.ISitetypeService;
import com.xq.gymnasium.util.CodeUtil;

/**
 * 体育馆信息
 * @ClassName MessageController
 * @Author yangweihang
 * @Date 2018年9月12日 上午11:54:52
 */
@RestController
@RequestMapping("/message")
public class GymnasiumMessageController {
	
	@Autowired
	private IGymnasiumMessageService ims;
	
	@Autowired
	private ISitetypeService iss;
	
	@Autowired
	private IAdministratorService ias;
	
	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private HttpSession session;
	
	/**
	 * 增加体育馆信息
	 * yangweihang
	 * @Date 2018年9月12日 上午11:56:58
	 * @param m 体育场
	 * @param st 体育场场地类别
	 * @return
	 * http://localhost:8089/message/insertmessage?gname=天湖体育馆&glocation=北京市朝阳区&gphonenum=001-123456&gstarttime=9:00&gendtime=20:00&gclosedtime=1,2&facilities=灯光&noticeuse=1、篮球馆每块号地为半场，如需全场需预订两块号地；2、预订成功后，可以退改
	 */
	@RequestMapping("/insertmessage")
	public String insertMessage(GymnasiumMessage g) {
		String hcode = (String)request.getSession().getAttribute("hcode");
		String str = "";
		if(hcode != null) {
			//新增体育馆信息
			g.setTime(new Date());
			g.setHcode(hcode);
			int result = ims.insertmessage(g);
			int gid = g.getGid();
			if(result > 0) {
				//批量录入场地类别
				str = iss.insertsitetype(gid,0);
			}
			return str;
		}else {
			str = "login";
			return str;
		}
	}
	
	/**
	 * 修改体育馆信息
	 * yangweihang
	 * @Date 2018年9月12日 上午11:53:58
	 * @param m 体育场
	 * @return
	 */
	@RequestMapping("/updatemessage")
	public String updatemessage(GymnasiumMessage g,Integer update) {
		//修改体育馆信息
		int result = ims.updatemessage(g);
		int gid = g.getGid();
		String str = "";
		if(result > 0) {
			str = "update.success";
		}
		return str;
	}
	
	/**
	 * 按体育场id查询体育场类别
	 * yangweihang
	 * @Date 2018年9月13日 上午10:00:59
	 * @param gid 体育馆id
	 * @return
	 * http://localhost:8089/message/selectsitetype?gid=1
	 */
	@RequestMapping("/selectsitetype")
	public List<Sitetype> selectsitetype(Integer gid){
		List<Sitetype> list = iss.selectsitetype(gid);
		return list;
	}
	
	/**
	 * 查询全部的体育馆
	 * yangweihang
	 * @Date 2018年9月13日 下午8:41:53
	 * @return
	 * http://localhost:8089/message/selectbygm
	 */
	@RequestMapping("/selectbygm")
	public List<GymnasiumMessage> selectbygm(){
		List<GymnasiumMessage> list = ims.selectbygm();
		return list;
	}
	
	/**
	 * 查询体育馆信息
	 * yangweihang
	 * @Date 2018年9月13日 下午8:41:53
	 * @return
	 * http://localhost:8089/message/selectbygm
	 */
	@RequestMapping("/selectbygymgid")
	public Map<String,Object> selectbygymgid(String gid){
		Map<String,Object> map = ims.selectbygymgid(gid);
		return map;
	}
	
	/**
	 * 场馆管理
	 * yangweihang
	 * @Date 2018年9月13日 下午8:41:53
	 * @return
	 * http://localhost:8089/message/selectbygym
	 */
	@RequestMapping("/selectbygym")
	public List<Map<String,Object>> selectbygym(String sname){
		String hcode = (String)request.getSession().getAttribute("hcode");
		List<Map<String, Object>> list = ims.selectbygym(sname,hcode);
		return list;
	}
	
	/**
	 * 按gid查询
	 * yangweihang
	 * @Date 2018年9月18日 下午2:21:51
	 * @param gid
	 * @return
	 */
	@RequestMapping("/selectbygid")
	public GymnasiumMessage selectbygid(Integer gid) {
		GymnasiumMessage gm = ims.selectbygid(gid);
		String gnumber = CodeUtil.gymnasiumnumber(gid);
		gm.setGnumber(gnumber);
		List<Sitetype> list = iss.selectsitetype(gid);
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < list.size(); i++) {
			if(list.get(i).getStname() != null) {
				sb = sb.append(list.get(i).getStname());
				sb = sb.append(",");
			}
		}
		if(sb.length() >= 1) {
			sb.delete(sb.length()-1, sb.length());
		}
		gm.setStietype(sb.toString());
		return gm;
	}
	
	/**
	 * 按体育馆id删除
	 * yangweihang
	 * @Date 2018年9月19日 下午6:06:41
	 * @param gid
	 * @return
	 */
	@RequestMapping("/deletebygid")
	public Integer deletebygid(Integer gid) {
		Integer i = ims.deletebygid(gid);
		return i;
	}
	
	/**
	 * 生成excle
	 * @param response 响应对象
	 * @param oid 订单id
	 */
	@RequestMapping("/getgmdown")
	public String getgmdown(HttpServletResponse response) {
		try {
			response.setContentType("application/x-msdownload");
			response.setHeader("Content-disposition",
					"attachment; filename=" + new String("gymnasium.xlsx".getBytes("gb2312"), "iso8859-1"));
			//第一步创建workbook 
			HSSFWorkbook wb = new HSSFWorkbook();
			//第二步创建sheet 
			HSSFSheet sheet = wb.createSheet("gymnasium");
			//第三步创建行row:添加表头0行 
			HSSFRow row = sheet.createRow(0);
			HSSFCellStyle style = wb.createCellStyle();
			style.setAlignment(HSSFCellStyle.ALIGN_CENTER);//居中 
			//第四步创建单元格 
			HSSFCell cell = row.createCell(0);//第一个单元格 
			cell.setCellValue("序号");//设定值 
			cell.setCellStyle(style);//内容居中 
			cell = row.createCell(1);//第二个单元格   
			cell.setCellValue("所属场馆");
			cell.setCellStyle(style);
			cell = row.createCell(2);//第三个单元格  
			cell.setCellValue("场馆编号");
			cell.setCellStyle(style);
			cell = row.createCell(3);//第四个单元格  
			cell.setCellValue("开闭馆时间");
			cell.setCellStyle(style);
			cell = row.createCell(4);//第五个单元格  
			cell.setCellValue("预定状态");
			cell.setCellStyle(style);
			cell = row.createCell(5);//第六个单元格  
			//第五步插入数据 
			String hcode = (String)request.getSession().getAttribute("hcode");
			List<Map<String, Object>> gl = ims.selectbygym(null,hcode);
			for (int i = 0; i < gl.size(); i++) {
				Map<String, Object> r = gl.get(i);
				//创建行 
				row = sheet.createRow(i + 1);
				//创建单元格并且添加数据 
				row.createCell(0).setCellValue(i + 1);
				row.createCell(1).setCellValue((String)r.get("glocation"));
				row.createCell(2).setCellValue((String)r.get("gnumber"));
				row.createCell(3).setCellValue((String)r.get("gstarttime"));
				row.createCell(4).setCellValue((String)r.get("pstatus"));
			}
			//第六步将生成excel文件保存到指定路径下 
			wb.write(response.getOutputStream());
		} catch (Exception e) {
			return "fail";
		}
		return "success";
	}
	
	/**
	 * 修改开馆闭馆状态
	 * yangweihang
	 * @Date 2018年9月26日 下午4:36:33
	 * @param map
	 * @return
	 */
	@RequestMapping("/updatestate")
	public String updatestate(Integer gid) {
		String str = ims.updatestate(gid);
		return str;
	}
	
	/**
	 * 查询和会员医院id一样的体育馆
	 * yangweihang
	 * @Date 2018年9月26日 下午5:36:59
	 * @param map
	 * @return
	 */
	@RequestMapping("/selectbycode")
	public List<GymnasiumMessage> selectbycode(){
		String hcode = (String)request.getSession().getAttribute("hcode");
		List<GymnasiumMessage> list = ims.selectbycode(hcode);
		return list;
	}
}
