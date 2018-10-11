package com.xq.gymnasium.util;

/**
 * 编号工具类
 * @ClassName CodeUtil
 * @Author yangweihang
 * @Date 2018年9月12日 下午12:14:34
 */
public class CodeUtil {
	
	/**
	 * 获得体育馆编号
	 * yangweihang
	 * @Date 2018年9月12日 下午1:26:55
	 * @param key
	 * @return
	 */
	public static String gymnasiumnumber(int key) {
		String gnumber = "GBJ"; 
		Integer num = new Integer(key);
		if(num.toString().length() == 1) {
			gnumber += "00"+num;
		}else if(num.toString().length() == 2) {
			gnumber += "0"+num;
		}else {
			gnumber += num;
		}
		return gnumber;
	}
	
	/**
	 * 查询场地编号
	 * yangweihang
	 * @Date 2018年9月12日 下午4:21:09
	 * @param key
	 * @return
	 */
	public static String sitenumber(int type,int sitenumber) {
		String snumber = "";
		//篮球的场地编号
		if(type == 1) {
			Integer num = new Integer(sitenumber);
			if(num.toString().length() == 1) {
				snumber = "b00"+num;
			}else if(num.toString().length() == 2) {
				snumber = "b0"+num;
			}else {
				snumber = "b"+num;
			}
		//羽毛球的第一块场地
		}else if(type == 2) {
			Integer num = new Integer(sitenumber);
			if(num.toString().length() == 1) {
				snumber = "b00"+num;
			}else if(num.toString().length() == 2) {
				snumber = "b0"+num;
			}else {
				snumber = "b"+num;
			}
		}
		return snumber;
	}
}
