package com.xq.gymnasium.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xq.gymnasium.util.DateTools;

/**
 * 计算日期
 * @ClassName TimeController
 * @Author yangweihang
 * @Date 2018年9月17日 下午5:31:25
 */
@RestController
@RequestMapping("/time")
public class TimeController {
	
	/**
	 * 计算时间
	 * yangweihang
	 * @Date 2018年9月17日 下午5:41:43
	 * @param date
	 * @return
	 */
	@RequestMapping("/calculatetime")
	public static List<String> calculatetime(String date){
		List<String> list = new ArrayList<String>();
		DateTools dt = DateTools.getFactory();
		Date time = dt.formatDate(date, "yyyy-MM-dd");
		String weekone = DateTools.convertWeekDate(time);
		for(int i = 0; i < 7; i++) {
			Date d = dt.addDay(dt.formatDate(weekone,"yyyy-MM-dd"), i);
			String month = dt.formatDate(d, "yyyy-MM-dd").split("-")[1];
			String day = dt.formatDate(d, "yyyy-MM-dd").split("-")[2];
			String dates = month+"."+day;
			list.add(dates);
		}
		return list;
	}
}
