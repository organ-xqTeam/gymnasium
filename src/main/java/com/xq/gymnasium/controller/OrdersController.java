package com.xq.gymnasium.controller;

import java.util.ArrayList;
import java.util.HashMap;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xq.gymnasium.model.Selectbyordersyd;
import com.xq.gymnasium.model.Sitetime;
import com.xq.gymnasium.service.IOrdersService;
import com.xq.gymnasium.util.DateTools;

//import net.sf.json.JSONArray;

/**
 * 体育馆场地
 * @ClassName OrdersController
 * @Author yangweihang
 * @Date 2018年9月14日 上午9:53:43
 */
@RestController
@RequestMapping("/orders")
public class OrdersController {
	
	@Autowired
	private IOrdersService ios;
	
	@Autowired
	private HttpServletRequest request;

	@Autowired
	private HttpServletResponse response;
	
	/**
	 * 预定场地
	 * yangweihang
	 * @Date 2018年9月14日 下午2:42:25
	 * @param sid 场地id
	 * @param ostarttime 预定开始时间
	 * @param oendtime	预定结束时间
	 * @return
	 * http://localhost:8089/orders/insertorders?sid=1&sid=2&ostarttime=2018-09-09%2012:00:00&ostarttime=2018-09-09%2013:00:00&oendtime=2018-09-09%2013:00:00&oendtime=2018-09-09%2014:00:00
	 */
	@RequestMapping("/insertorders")
	public String insertorders(Integer[] sid,String[] ostarttime,String[] oendtime,Integer gid) {
		/*System.out.println("sid"+sid[0]);
		System.out.println("ostarttime"+ostarttime[0]);
		System.out.println("oendtime"+oendtime[0]);*/
		//获得操作人
		String oname = (String)request.getSession().getAttribute("username");
		String str = ios.insertorders(sid, oname, ostarttime, oendtime,gid);
		System.out.println("str"+str);
		return str;
	}
	
	/**
	 * 预定场地
	 * yangweihang
	 * @Date 2018年9月14日 下午2:42:25
	 * @param sid 场地id
	 * @param ostarttime 预定开始时间
	 * @param oendtime	预定结束时间
	 * @return
	 * http://localhost:8089/orders/insertorders?sid=1&sid=2&ostarttime=2018-09-09%2012:00:00&ostarttime=2018-09-09%2013:00:00&oendtime=2018-09-09%2013:00:00&oendtime=2018-09-09%2014:00:00
	 */
	@RequestMapping(value ="/insertorderss" , produces = "text/json;charset=UTF-8")
    @ResponseBody
	public String insertorderss(@RequestBody JSONObject map) {
		//获得操作人
		String oname = (String)request.getSession().getAttribute("username");
		String str = ios.insertorderss(map,oname);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("status", "success");
		jsonObject.put("count", str);
		return jsonObject.toString();
	}
	
	/**
	 * 查询该人的订单
	 * yangweihang
	 * @Date 2018年9月14日 下午7:04:41
	 * @param oname	预订人
	 * @param starttime	开始时间
	 * @param endtime	结束时间
	 * @return
	 * http://localhost:8089/orders/selectByOname?oname=hy&starttime=2018-09-09 12:00:00&endtime=2018-09-09 13:00:00
	 */
	@RequestMapping("/selectByOname")
	public List<Map<String,Object>> selectByOname(String starttime,String endtime,Integer pageNum,Integer pageSize,String num){
		String hcode = (String)request.getSession().getAttribute("hcode");
		String oname = null;
		if(!num.equals("1")) {
			oname = (String)request.getSession().getAttribute("username");
		}
		List<Map<String, Object>> list = ios.selectByOname(hcode, oname, starttime, endtime, pageNum, pageSize);
		//System.out.println("list"+list);
		return list;
	}
	
	/**
	 * 批量退订
	 * yangweihang
	 * @Date 2018年9月14日 下午8:29:21
	 * @param oid 订单id
	 * @return
	 * http://localhost:8089/orders/updatestate?oid=12&oid=13&oid=14
	 */
	@RequestMapping("/updatestate")
	public String updatestate(String otime) {
		String result = ios.updatestate(otime);
		return result;
	}
	
	/**
	 * 查询场地预定信息
	 * yangweihang
	 * @Date 2018年9月15日 上午11:15:40
	 * @param starttime	开始时间
	 * @param endtime	结束时间
	 * @return
	 * http://localhost:8089/orders/selectbyorders?starttime=2018-09-09&endtime=2018-09-10
	 */
	@RequestMapping("/selectbyorders")
	public Map<String, Object> selectbyorders(String starttime,String endtime,Integer pageNum,Integer pageSize) {
		pageNum = (pageNum - 1) * pageSize;
		List<Map<String, Object>> list = ios.selectbyorders(starttime, endtime, pageNum, pageSize);
		List<Map<String, Object>> count = ios.selectbyorderscount(starttime, endtime, pageNum, pageSize);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("list", list);
		map.put("count", count.size());
		return map;
	}
	
	/**
	 * 按日期查询场馆预定情况
	 * yangweihang
	 * @Date 2018年9月15日 上午11:09:25
	 * @param map
	 * @return
	 * http://localhost:8089/orders/selectbyordersyd?year=2018&month=9&oneday=12&twoday=13&stname=篮球&snumber=b001
	 */
	@RequestMapping("/selectbyordersyd")
	public List<Map<String,Object>> selectbyordersyd(Selectbyordersyd sb){
		List<Map<String, Object>> list = ios.selectbyordersyd(sb);
		return list;
	}
	
	/**
	 * 按日期查询场馆预定情况
	 * yangweihang
	 * @Date 2018年9月15日 上午11:09:25
	 * @param map
	 * @return
	 */
	@RequestMapping("/selectbyordersyds")
	public JSONArray selectbyordersyds(Selectbyordersyd sb){
		JSONArray jsonarray = null;
		try {
			jsonarray = ios.selectbyordersyds(sb);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jsonarray;
	}
	
	/**
	 * 前端查询订单
	 * yangweihang
	 * @Date 2018年9月19日 下午2:05:29
	 * @param sb
	 * @return
	 */
	@RequestMapping("/selectbyordersydss")
	public List<Sitetime> selectbyordersydss(Selectbyordersyd sb) {
		List<Sitetime> list = ios.selectbyordersydss(sb);
		return list;
	}
	
	/**
	 * 查询当天的使用的人数和场地数
	 * yangweihang
	 * @Date 2018年9月17日 下午1:37:54
	 * @return
	 */
	@RequestMapping("/selectbycount")
	public Map<String,Object> selectbycount(String time){
		String hcode = (String)request.getSession().getAttribute("hcode");
		Map<String, Object> map = ios.selectbycount(time,hcode);
		return map;
	}
	
	/**
	 * 判断体育馆休息日期
	 * yangweihang
	 * @Date 2018年9月26日 下午1:31:51
	 * @param sb
	 * @return
	 */
	@RequestMapping("/selectbyweek")
	public String selectbyweek(Integer gid,String servenday) {
		String str = ios.selectbyweek(gid,servenday);
		return str;
	}
	
	/**
	 * 导出excle
	 * yangweihang
	 * @Date 2018年9月20日 下午2:53:09
	 * @return
	 */
	@RequestMapping("/getexcle")
	public void getexcle(HttpServletResponse response,String starttime,String endtime,Integer pageNum,Integer pageSize) {//设置响应为下载
		String hcode = (String)request.getSession().getAttribute("hcode");
		response = this.response;
		try {
			response.setContentType("application/x-msdownload");
			response.setHeader("Content-disposition",
					"attachment; filename=" + new String("订单信息.xls".getBytes("gb2312"), "iso8859-1"));
			//第一步创建workbook 
			HSSFWorkbook wb = new HSSFWorkbook();
			//第二步创建sheet 
			HSSFSheet sheet = wb.createSheet("会议室");
			//第三步创建行row:添加表头0行 
			HSSFRow row = sheet.createRow(0);
			HSSFCellStyle style = wb.createCellStyle();
			style.setAlignment(HSSFCellStyle.ALIGN_CENTER);//居中 
			//第四步创建单元格 
			HSSFCell cell = row.createCell(0);//第一个单元格 
			cell.setCellValue("序号");//设定值 
			cell.setCellStyle(style);//内容居中 
			cell = row.createCell(1);//第二个单元格   
			cell.setCellValue("场地名称");
			cell.setCellStyle(style);
			cell = row.createCell(2);//第三个单元格  
			cell.setCellValue("场地编号");
			cell.setCellStyle(style);
			cell = row.createCell(3);//第四个单元格  
			cell.setCellValue("预订人");
			cell.setCellStyle(style);
			cell = row.createCell(4);//第五个单元格  
			cell.setCellValue("预定时间");
			cell.setCellStyle(style);
			cell = row.createCell(5);//第六个单元格  
			cell.setCellValue("费用");
			cell.setCellStyle(style);
			cell = row.createCell(6);//第七个单元格  
			cell.setCellValue("操作时间");
			cell.setCellStyle(style);
			cell = row.createCell(7);//第七个单元格  
			cell.setCellValue("预定状态");
			cell.setCellStyle(style);
			
			

			//第五步插入数据 
			List<Map<String, Object>> list = ios.selectByOname(hcode,null, starttime, endtime, pageNum, pageSize);
			
			for (int i = 0; i < list.size()-1; i++) {
				Map<String, Object> m = list.get(i);
				//创建行 
				row = sheet.createRow(i + 1);
				//创建单元格并且添加数据 
				row.createCell(0).setCellValue(i + 1);
				row.createCell(1).setCellValue((String)m.get("sname"));
				row.createCell(2).setCellValue((String)m.get("snumber"));
				row.createCell(3).setCellValue((String)m.get("oname"));
				row.createCell(4).setCellValue((String)m.get("ostarttime"));
				row.createCell(5).setCellValue((Double)m.get("money"));
				row.createCell(6).setCellValue((String)m.get("otime"));
				try {
					row.createCell(7).setCellValue((String)m.get("state"));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			//第六步将生成excel文件保存到指定路径下 
			wb.write(response.getOutputStream());
		} catch (Exception e) {
		}
	}
	
	/**
	 * 生成excle
	 * @param response 响应对象
	 * @param oid 订单id
	 */
	@RequestMapping("/getRoomDown")
	public void getRoomDown(HttpServletResponse response) {
		//创建一个只有String的list
		//这里查出 "会议室名称","预定时间","对接时间","服务状态","预定人","预定人电话","星级评价"这几个字段的值  然后装进list中
		try {
			//设置响应为下载
			response.setContentType("application/x-msdownload");
			response.setHeader("Content-disposition", "attachment; filename="
					+ new String("订单信息.xlsx".getBytes("gb2312"), "iso8859-1"));
			//1.建立一个excle
			Workbook book = new HSSFWorkbook();
			//2.在这个excle建立一个分表
			Sheet sheet1 = (Sheet) book.createSheet();
			//设置第一行
			Row row = sheet1.createRow(0);
			//设置第二行
			Row row2 = sheet1.createRow(1);
			String[] roomInfo = new String[]{"序号","场地名称","预订人","预定时间","费用","操作时间"};
			List<Map<String, Object>> list = ios.selectbyorders(null, null, 0, 1000000);
			//Set<String> keySet = list.get(0).keySet();
			/*List<String> li = new ArrayList<String>();
			for (String string : keySet) {
				li.add(string);
			}*/
			//设置第一行各个内容
			for (int i = 0; i < roomInfo.length; i++) {
				Cell cell = row.createCell(i);
				cell.setCellValue(roomInfo[i]);
			}
			Cell cell2 = row2.createCell(0);
			for (int i = 0; i < list.size(); i++) {
				try {
					Map<String, Object> m = list.get(i);
					cell2 = row2.createCell(i + 1);
					//创建单元格并且添加数据 
					cell2.setCellValue(i + 1);
					cell2.setCellValue((String)m.get("sname"));
					cell2.setCellValue((String)m.get("oname"));
					cell2.setCellValue((String)m.get("ostarttime"));
					cell2.setCellValue((Double)m.get("money"));
					cell2.setCellValue((String)m.get("otime"));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			// 写出
			book.write(response.getOutputStream());
		} catch (Exception e) {
		}
	}
	
	/**
	 * 后端首页预定查询
	 * yangweihang
	 * @Date 2018年10月10日 下午3:55:35
	 * @param sb
	 * @return
	 */
	@RequestMapping("/selectbyordersydhou")
	public List<Map<String,Object>> selectbyordersydhou(Selectbyordersyd sb){
		return ios.selectbyordersydhou(sb);
	}
	
}
