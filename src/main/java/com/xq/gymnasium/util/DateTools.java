package com.xq.gymnasium.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * 日期工具类
 * @author Administrator
 *
 */
public class DateTools {
	private static DateTools tools = null;
	
	/**
	 * 运行时，当前的小时
	 */
	public int hours;
	
	/**
	 * 设置的最小修改时间-用于掩埋卡
	 */
	public Date baseDate = null;
	
	
	private DateTools(){
		baseDate = formatDate("2000-01-01","yyyy-MM-dd");
		hours = getHours(new Date());
		init();
	}
	
	/**
	 * 该类的初始化方法
	 */
	public void init(){
		
	}
	
	/**
	 * 重置该类
	 */
	public void reset() {
		init();
	}

	/**
	 * 工厂方法，返回
	 * @return
	 */
	public static DateTools getFactory() {
		if (tools == null) {
			create();
		}
		return tools;
	}

	/**
	 * 线程安全的创建方法，防止并发多次创建
	 */
	private static synchronized void create() {
		if (tools == null) {
			tools = new DateTools();
		}
	}
	
	/**
	 * 格式化今天
	 * @return
	 */
	public String formatDate() {
		return formatDate(new Date(), "yyyy-MM-dd");
	}
	/**
	 * 格式化今天
	 * @return
	 */
	public String formatDatetime() {
		return formatDate(new Date(), "yyyy-MM-dd HH:ss:mm");
	}
	/**
	 * 格式化日期 返回格式:yyyy-MM-dd
	 * @param date
	 * @return
	 */
	public String formatDate(Date date) {
		return formatDate(date, "yyyy-MM-dd");
	}
	/**
	 * 格式化日期 返回格式:自定义
	 * @param d
	 * @param rege
	 * @return
	 */
	public String formatDate(Date d, String rege) {
		if (d == null) {
			return "";
		}
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(rege);
			return sdf.format(d);
		} catch (Exception e) {
			e.printStackTrace();
			StringTools.getFactory().print(d,rege);
		}
		return "";
	}
	
	/**
	 * 将字符串转成Date
	 * @param date
	 * @param rege
	 * @return
	 */
	public Date formatDate(String date,String rege){
		SimpleDateFormat sdf = new SimpleDateFormat(rege);
		try {
			return sdf.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 取得人性化的时间
	 * @param d
	 * @return
	 */
	public String formatHumanDate(Date d){
//		5分钟以内叫“刚刚”
//		然后有“5分钟前”“10分钟前”“15分钟前”“20分钟前”
//		然后当天的是时间“8:50”“15:20”
//		然后前一天的是日期“5-9”“6-18”
		Date td = new Date();
		long l = td.getTime()-d.getTime();
		if(l < 300000){
			return "刚刚";
		}
		if(l < 600000){
			return "5分钟前";
		}
		if(l < 900000){
			return "10分钟前";
		}
		if(l < 1200000){
			return "15分钟前";
		}
		if(l < 1800000){
			return "20分钟前";
		}
		//同一天
		if(equalsDate(td,d,"yyyy-MM-dd")){
			return formatDate(d,"H:mm");
		}
		//同一年
		if(equalsDate(td,d,"yyyy")){
			return formatDate(d,"M-d");
		}
		//一年前
		return formatDate(d,"yyyy-M-d");
	}
	
	/**
	 * 增加年
	 * @param d 日期
	 * @param n 数量
	 * @return
	 */
	public Date addYears(Date d,int n){
		return addDate(d,Calendar.YEAR,n);
	}
	/**
	 * 增加月
	 * @param d 日期
	 * @param n 数量
	 * @return
	 */
	public Date addMonth(Date d,int n){
		return addDate(d,Calendar.MONTH,n);
	}
	/**
	 * 增加日
	 * @param d 日期
	 * @param n 数量
	 * @return
	 */
	public Date addDay(Date d,int n){
		return addDate(d,Calendar.DAY_OF_YEAR,n);
	}
	/**
	 * 增加时
	 * @param d 日期
	 * @param n 数量
	 * @return
	 */
	public Date addHours(Date d,int n){
		return addDate(d,Calendar.HOUR_OF_DAY,n);
	}
	/**
	 * 增加分
	 * @param d 日期
	 * @param n 数量
	 * @return
	 */
	public Date addMinutes(Date d,int n){
		return addDate(d,Calendar.MINUTE,n);
	}
	/**
	 * 增加秒
	 * @param d 日期
	 * @param n 数量
	 * @return
	 */
	public Date addSeconds(Date d,int n){
		return addDate(d,Calendar.SECOND,n);
	}
	/**
	 * 实际处理类
	 * @param d
	 * @param field
	 * @param n
	 * @return
	 */
	private Date addDate(Date d,int field,int n){
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(d);
		gc.add(field, n);
		return gc.getTime();
	}
	
	/**
	 * 只保留指定格式的时间，其他位用最小值（0或1）代替
	 * @param d 日期
	 * @param reg 规则
	 * @return
	 */
	public Date clearTime(Date d,String reg){
		return formatDate(formatDate(d,reg),reg);
	}
	public Date clearTime(Date d){
		return clearTime(d,"yyyy-MM-dd");
	}
	
	/**
	 * 比较两个时间的 某字段 是否相等
	 * @param d1 时间一
	 * @param d2 时间二
	 * @param reg 时间规则，就是都判断哪个部分是否相同 
	 * @return true相等 false不等
	 */
	public boolean equalsDate(Date d1,Date d2,String reg){
		return formatDate(d1,reg).equals(formatDate(d2,reg));
	}
	
	/**
	 * 算两个时间-年-的差（无负数）
	 * @param d1 第一个时间
	 * @param d2 第二个时间
	 * @return
	 */
	public int deffYear(Date d1,Date d2){
		Calendar gc1 = new GregorianCalendar();
		gc1.setTime(d1);
		Calendar gc2 = new GregorianCalendar();
		gc2.setTime(d2);
		return Math.abs(gc1.get(Calendar.YEAR) - gc2.get(Calendar.YEAR));
	}
	/**
	 * 算两个时间-月-的差（无负数）
	 * @param d1 第一个时间
	 * @param d2 第二个时间
	 * @return
	 */
	public int deffMonth(Date d1,Date d2){
		Calendar gc1 = new GregorianCalendar();
		gc1.setTime(d1);
		Calendar gc2 = new GregorianCalendar();
		gc2.setTime(d2);
		return Math.abs((gc1.get(Calendar.YEAR)*12+gc1.get(Calendar.MONTH)) - (gc2.get(Calendar.YEAR)*12+gc2.get(Calendar.MONTH)) );
	}
	/**
	 * 算两个时间-日-的差（无负数）
	 * @param d1 第一个时间
	 * @param d2 第二个时间
	 * @return
	 */
	public int deffDay(Date d1,Date d2){
		return (int)deffTime(d1,d2,Calendar.DAY_OF_YEAR);
	}
	/**
	 * 算两个时间-时-的差（无负数）
	 * @param d1 第一个时间
	 * @param d2 第二个时间
	 * @return
	 */
	public int deffHour(Date d1,Date d2){
		return (int)deffTime(d1,d2,Calendar.HOUR_OF_DAY);
	}
	/**
	 * 算两个时间-分-的差（无负数）
	 * @param d1 第一个时间
	 * @param d2 第二个时间
	 * @return
	 */
	public long deffMinute(Date d1,Date d2){
		return deffTime(d1,d2,Calendar.MINUTE);
	}
	/**
	 * 算两个时间-秒-的差（无负数）
	 * @param d1 第一个时间
	 * @param d2 第二个时间
	 * @return
	 */
	public long deffSecond(Date d1,Date d2){
		return deffTime(d1,d2,Calendar.SECOND);
	}
	
	/**
	 * 算两个时间的差（无负数）
	 * @param d1 第一个时间
	 * @param d2 第二个时间
	 * @param n 标识字段，区分操作哪个字段
	 * @return 相差的 天数
	 */
	public long deffTime(Date d1,Date d2,int n){
		long deff = Math.abs(d1.getTime()-d2.getTime())/1000;
		int base = 0;
		switch(n){
		case Calendar.SECOND : return deff;
		case Calendar.MINUTE : base=60;
		case Calendar.HOUR_OF_DAY : base=3600;
		case Calendar.DAY_OF_YEAR : base=86400;
		case Calendar.MONTH : base=86400;
		}
		return deff/base;
	}
	
	/**
	 * 获取指定时间的小时
	 * @param d
	 * @return
	 */
	public int getHours(Date d){
		Calendar gc = new GregorianCalendar();
		gc.setTime(d);
		return gc.get(Calendar.HOUR_OF_DAY);
	}
	
	/**
	 * 获取指定时间的月份
	 * @param d
	 * @return
	 */
	public int getMonth(Date d){
		Calendar gc = new GregorianCalendar();
		gc.setTime(d);
		return gc.get(Calendar.MONTH)+1;
	}

	/**
	 * 获取指定时间的年份
	 * @param d
	 * @return
	 */
	public int getYear(Date d){
		Calendar gc = new GregorianCalendar();
		gc.setTime(d);
		return gc.get(Calendar.YEAR);
	}
	
	/**
	 * 获取指定时间的毫秒数
	 * @return
	 */
	public long getMilliseconds(String date){
		return formatDate(date,"yyyy-MM-dd HH:mm").getTime();
	}
	
	/**
	 * 获取指定时间的毫秒数
	 * @return
	 */
	public long getMilliseconds(Date date){
		return date.getTime();
	}
	
	/**
	 * 是否是午夜
	 * @return true是	false不是
	 */
	public boolean isMidnight(){
		return hours>1 && hours<6;
	}
	
	/**
	 * 控制台输入 监控信息
	 * @param v
	 */
	public void print(String v){
		System.out.println(formatDate(new Date(),"yyMMdd HHmmss")+" "+v);
	}
	/**
	 * 判断一个传进来的时间是否在其他两个时间 之间
	 * @return true是	false不是
	 */
	public boolean isBetweenTwoDate(String date,int type){
		if(!date.matches("\\d{4}-\\d{2}-\\d{2}")){
			return false;
		}
		long limitSupport1 = 0;
		long limitSupport2 = 0;
		Date d = formatDate(date,"yyyy-MM-dd");
		if(type == 1){
			String condDate1 = date + " 08:30";
			String condDate2 = formatDate(addDay(d,1),"yyyy-MM-dd")+ " 08:30";
			limitSupport1 = formatDate(condDate1,"yyyy-MM-dd HH:mm").getTime();
			limitSupport2 = formatDate(condDate2,"yyyy-MM-dd HH:mm").getTime();
		}else if(type == 4){
			int m = getMonth(d);
			int y = getYear(d);
			String month = m > 9 ? m+"" : "0"+m;
			String year = m == 12 ? (y+1)+"" : y+"";
			String nextMonth = m > 9 ? (m==12?"01":(m+1)+"") : "0"+(m+1);
			String prewDate = y+"-"+month+"-"+"20 08:30";
			String nextDate = year+"-"+nextMonth+"-"+"04 08:30";
			limitSupport1 = formatDate(prewDate,"yyyy-MM-dd HH:mm").getTime();
			limitSupport2 = formatDate(nextDate,"yyyy-MM-dd HH:mm").getTime();
		}
		return new Date().getTime() > limitSupport1 && new Date().getTime() < limitSupport2;
	}
	/**
	 * 判断写月报时间或者修改月报的时间是否是合法时间，用于限定写月报的月份
	 * @return true是	false不是
	 */
	public boolean isInRightMonth(int i,String cYear){
		Date date = new Date();
		String d = formatDate(date,"yyyy-MM-dd");
		int year = Integer.parseInt(d.split("-")[0]);
		int month = Integer.parseInt(d.split("-")[1]);
		int day = Integer.parseInt(d.split("-")[2]);
		//如果现在是一月而且是3号之前，可以写或者修改去年12月的月报
		if(month == 1 && year == Integer.parseInt(cYear)+1 && day<=2 && i == 12){
			return true;
		}
		//如果日在20号以后只能写或者修改今年本月的月报，如果日在3号之前只能写或者修改今年上个月的月报
		if((year==Integer.parseInt(cYear) && i == month && day>=20)||(year==Integer.parseInt(cYear) && month == i+1 && day<=2)){
			return true;
		}else{
			return false;
		}

	}
	
	/**
	 * 传入一个指定日期获取该日期所在周的周一的日期
	 * yangweihang
	 * @Date 2018年9月17日 下午6:00:44
	 * @param time
	 * @return
	 */
	public static String convertWeekDate(Date time) {  
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd"); //设置时间格式  
        Calendar cal = Calendar.getInstance();  
        cal.setTime(time);  
        //判断要计算的日期是否是周日，如果是则减一天计算周六的，否则会出问题，计算到下一周去了  
        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);//获得当前日期是一个星期的第几天  
        if(1 == dayWeek) {  
          cal.add(Calendar.DAY_OF_MONTH, -1);  
        }  
        cal.setFirstDayOfWeek(Calendar.MONDAY);//设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一  
        int day = cal.get(Calendar.DAY_OF_WEEK);//获得当前日期是一个星期的第几天  
        cal.add(Calendar.DATE, cal.getFirstDayOfWeek()-day);//根据日历的规则，给当前日期减去星期几与一个星期第一天的差值   
        String imptimeBegin = sdf.format(cal.getTime()); //周一时间 
        return imptimeBegin;
   } 
	
	/**
     * 判断该日期是否是该月的第一天
     * @param date 需要判断的日期
     * @return
     */
    public static boolean isFirstDayOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_MONTH) == 1;
    }
    
    /**
	 * 判断该日期是否是该月的最后一天
	 * @param date 需要判断的日期
	 * @return
	 */
	public static boolean isLastDayOfMonth(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.DAY_OF_MONTH) == calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
	}
	
	/**
	 * 将日期加天数
	 * yangweihang
	 * @Date 2018年9月17日 下午6:50:31
	 * @param date
	 * @return
	 */
	public static Date addday(Date date,int day) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String stringDate = sdf.format(date);//date-->String
        //下面这四行是重要的，将date日期加1
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(calendar.DATE, day);//日期向后+1天，整数往后推，负数向前推
        date = calendar.getTime();//这个时间就是日期向后推一天的结果
        stringDate = sdf.format(date);//date-->String
        System.out.println(stringDate);
        return date;
	}
	
	 /**
	    * 判断当前日期是星期几<br>
	    * <br>
	    * @param pTime 修要判断的时间<br>
	    * @return dayForWeek 判断结果<br>
	    * @Exception 发生异常<br>
	    */
	public static int dayForWeek(String pTime) throws Exception {  
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");  
		Calendar c = Calendar.getInstance();  
		c.setTime(format.parse(pTime));  
		int dayForWeek = 0;  
		if(c.get(Calendar.DAY_OF_WEEK) == 1){  
			dayForWeek = 7;  
		}else{  
			dayForWeek = c.get(Calendar.DAY_OF_WEEK) - 1;  
		}  
		return dayForWeek;  
	}  
		
}