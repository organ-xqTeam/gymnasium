package com.xq.gymnasium.model;

import java.util.Date;

/**
 * 体育馆场地
 * @ClassName Site
 * @Author yangweihang
 * @Date 2018年9月11日 上午11:10:20
 */
public class Site {
	private Integer sid;//体育馆场地id
	private Integer stid;//体育馆场地类别id
	private String srsnumber;//羽毛球场地关联篮球场地
	private String sname;//体育馆场地名称
	private String snumber;//体育馆场地编号
	private Double sprice;//费用
	private Integer pstate;//预定状态 1.未预定 2.已预定
	private Integer pstatus;//发布状态 1.可预订 2.不可预订
	private String sremark;//备注
	private Date time;
	private String strnumber;
	private String strstname;
	private String strgname;
	public Site() {
	}
	public Site(Integer sid, Integer stid, String srsnumber, String sname, String snumber, Double sprice,
			Integer pstate, Integer pstatus, String sremark, Date time, String strnumber, String strstname, String strgname) {
		this.sid = sid;
		this.stid = stid;
		this.srsnumber = srsnumber;
		this.sname = sname;
		this.snumber = snumber;
		this.sprice = sprice;
		this.pstate = pstate;
		this.pstatus = pstatus;
		this.sremark = sremark;
		this.time = time;
		this.strnumber = strnumber;
		this.strstname = strstname;
		this.strgname = strgname;
	}
	public Integer getSid() {
		return sid;
	}
	public void setSid(Integer sid) {
		this.sid = sid;
	}
	public Integer getStid() {
		return stid;
	}
	public void setStid(Integer stid) {
		this.stid = stid;
	}
	public String getSrsnumber() {
		return srsnumber;
	}
	public void setSrsnumber(String srsnumber) {
		this.srsnumber = srsnumber;
	}
	public String getSname() {
		return sname;
	}
	public void setSname(String sname) {
		this.sname = sname;
	}
	public String getSnumber() {
		return snumber;
	}
	public void setSnumber(String snumber) {
		this.snumber = snumber;
	}
	public Double getSprice() {
		return sprice;
	}
	public void setSprice(Double sprice) {
		this.sprice = sprice;
	}
	public Integer getPstate() {
		return pstate;
	}
	public void setPstate(Integer pstate) {
		this.pstate = pstate;
	}
	public Integer getPstatus() {
		return pstatus;
	}
	public void setPstatus(Integer pstatus) {
		this.pstatus = pstatus;
	}
	public String getSremark() {
		return sremark;
	}
	public void setSremark(String sremark) {
		this.sremark = sremark;
	}
	public String getStrnumber() {
		return strnumber;
	}
	public void setStrnumber(String strnumber) {
		this.strnumber = strnumber;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	public String getStrstname() {
		return strstname;
	}
	public void setStrstname(String strstname) {
		this.strstname = strstname;
	}
	public String getStrgname() {
		return strgname;
	}
	public void setStrgname(String strgname) {
		this.strgname = strgname;
	}
	@Override
	public String toString() {
		return "Site [sid=" + sid + ", stid=" + stid + ", srsnumber=" + srsnumber + ", sname=" + sname + ", snumber="
				+ snumber + ", sprice=" + sprice + ", pstate=" + pstate + ", pstatus=" + pstatus + ", sremark="
				+ sremark + "]";
	}
}
