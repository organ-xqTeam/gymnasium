package com.xq.gymnasium.util;

public class GclosedtimeUtil {
	
	/**
	 * 获得开闭馆的时间
	 * yangweihang
	 * @Date 2018年9月13日 下午2:09:22
	 * @param gts
	 * @return
	 */
	public static String getgctime(String[] gts) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < gts.length; i++) {
			if(gts[i].equals("0")) {
				sb.append("无休");
				break;
			}
			if(gts[i].equals("1")) {
				sb.append("周一");
			}else if(gts[i].equals("2")) {
				sb.append("周二");
			}else if(gts[i].equals("3")) {
				sb.append("周三");
			}else if(gts[i].equals("4")) {
				sb.append("周四");
			}else if(gts[i].equals("5")) {
				sb.append("周五");
			}else if(gts[i].equals("6")) {
				sb.append("周六");
			}else if(gts[i].equals("7")) {
				sb.append("周日");
			}
			if(i != gts.length - 1) {
				sb.append(",");
			}
		}
		if(!gts[0].equals("0")) {
			sb.append("闭馆");
		}
		return sb.toString();
	}
}
