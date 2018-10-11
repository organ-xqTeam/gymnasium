package com.xq.gymnasium.model;

import java.util.List;
import java.util.Map;

/**
 * 场地时间
 * @ClassName Sitetime
 * @Author yangweihang
 * @Date 2018年9月19日 下午3:04:47
 */
public class Sitetime {
	private Integer sid;
	private String sname;
	private List<String> time;
	public Sitetime() {
	}
	public Sitetime(Integer sid,String sname, List<String> time) {
		this.sid = sid;
		this.sname = sname;
		this.time = time;
	}
	public Integer getSid() {
		return sid;
	}
	public void setSid(Integer sid) {
		this.sid = sid;
	}
	public String getSname() {
		return sname;
	}
	public void setSname(String sname) {
		this.sname = sname;
	}
	public List<String> getTime() {
		return time;
	}
	public void setTime(List<String> time) {
		this.time = time;
	}
	@Override
	public String toString() {
		return "Sitetime [sid=" + sid + ", sname=" + sname + ", time=" + time + "]";
	}
}
