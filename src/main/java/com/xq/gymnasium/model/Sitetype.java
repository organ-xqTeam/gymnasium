package com.xq.gymnasium.model;

/**
 * 场地类别
 * @ClassName Sitetype
 * @Author yangweihang
 * @Date 2018年9月11日 上午11:14:48
 */
public class Sitetype {
	private Integer stid;//体育馆场地类别id
	private Integer gid;//体育馆id
	private String stname;//体育馆场地类别名称
	public Sitetype() {
	}
	public Sitetype(Integer stid, Integer gid, String stname) {
		this.stid = stid;
		this.gid = gid;
		this.stname = stname;
	}
	public Integer getStid() {
		return stid;
	}
	public void setStid(Integer stid) {
		this.stid = stid;
	}
	public Integer getGid() {
		return gid;
	}
	public void setGid(Integer gid) {
		this.gid = gid;
	}
	public String getStname() {
		return stname;
	}
	public void setStname(String stname) {
		this.stname = stname;
	}
	@Override
	public String toString() {
		return "Sitetype [stid=" + stid + ", gid=" + gid + ", stname=" + stname + "]";
	}
}
