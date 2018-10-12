package com.xq.gymnasium.model;

/**
 * 按日期查询场馆预定情况
 * @ClassName Selectbyordersyd
 * @Author yangweihang
 * @Date 2018年9月15日 上午11:53:58
 */
public class Selectbyordersyd {
	private String year;
	private String month;
	private String oneday;
	private String twoday;
	private String stname;//篮球
	private String snumber;
	private String sname;
	private String date;
	private String servenday;//天
	private Integer sid;//场地id
	private Integer state;
	private Integer gid;
	private Integer num;
	public Selectbyordersyd() {
	}
	public Selectbyordersyd(String year, String month, String oneday, String twoday, String stname, String snumber, String sname, String date, String servenday, Integer sid, Integer state, Integer gid, Integer num) {
		this.year = year;
		this.month = month;
		this.oneday = oneday;
		this.twoday = twoday;
		this.stname = stname;
		this.snumber = snumber;
		this.sname = sname;
		this.date = date;
		this.servenday = servenday;
		this.sid = sid;
		this.state = state;
		this.gid = gid;
		this.num = num;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public String getOneday() {
		return oneday;
	}
	public void setOneday(String oneday) {
		this.oneday = oneday;
	}
	public String getTwoday() {
		return twoday;
	}
	public void setTwoday(String twoday) {
		this.twoday = twoday;
	}
	public String getStname() {
		return stname;
	}
	public void setStname(String stname) {
		this.stname = stname;
	}
	public String getSnumber() {
		return snumber;
	}
	public void setSnumber(String snumber) {
		this.snumber = snumber;
	}
	public String getSname() {
		return sname;
	}
	public void setSname(String sname) {
		this.sname = sname;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getServenday() {
		return servenday;
	}
	public void setServenday(String servenday) {
		this.servenday = servenday;
	}
	public Integer getSid() {
		return sid;
	}
	public void setSid(Integer sid) {
		this.sid = sid;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public Integer getGid() {
		return gid;
	}
	public void setGid(Integer gid) {
		this.gid = gid;
	}
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}
	@Override
	public String toString() {
		return "Selectbyordersyd [year=" + year + ", month=" + month + ", oneday=" + oneday + ", twoday=" + twoday
				+ ", stname=" + stname + ", snumber=" + snumber + ", sname=" + sname + ", date=" + date + ", servenday="
				+ servenday + ", sid=" + sid + ", state=" + state + ", gid=" + gid + ", num=" + num + "]";
	}
}	
