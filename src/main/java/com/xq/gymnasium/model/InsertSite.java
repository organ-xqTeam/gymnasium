package com.xq.gymnasium.model;

import java.util.Arrays;

/**
 * 新增场地
 * @ClassName InsertSite
 * @Author yangweihang
 * @Date 2018年9月13日 下午7:57:30
 */
public class InsertSite {
	private Integer stid;//体育馆场地类别id
	private String srsnumber;//羽毛球场地关联篮球场地
	private String sname;//体育馆场地名称
	private Double sprice;//费用
	private Integer pstatus;//发布状态 1.可预订 2.不可预订
	private String sremark;//备注
	private String num;//羽毛球场地数
	public InsertSite() {
	}
	public InsertSite(Integer stid, String srsnumber, String sname, Double sprice, Integer pstatus, String sremark,
			String num) {
		this.stid = stid;
		this.srsnumber = srsnumber;
		this.sname = sname;
		this.sprice = sprice;
		this.pstatus = pstatus;
		this.sremark = sremark;
		this.num = num;
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
	public Double getSprice() {
		return sprice;
	}
	public void setSprice(Double sprice) {
		this.sprice = sprice;
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
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
	@Override
	public String toString() {
		return "InsertSite [stid=" + stid + ", srsnumber=" + srsnumber + ", sname=" + sname
				+ ", sprice=" + sprice + ", pstatus=" + pstatus + ", sremark=" + sremark + ", num=" + num + "]";
	}
}
