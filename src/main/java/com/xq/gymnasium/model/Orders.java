package com.xq.gymnasium.model;

import java.util.Date;

/**
 * 体育馆订单
 * @ClassName Order
 * @Author yangweihang
 * @Date 2018年9月11日 上午11:16:51
 */
public class Orders {
	private Integer oid;//体育馆订单id
	private Integer sid;//体育馆场地id
	private String oname;//预定人
	private Date ostarttime;//预定开始时间
	private Date oendtime;//预定结束时间
	private Date otime;//操作时间
	private Double money;//价钱
	private Integer state;//预定状态	1.已预订	2.已退订
	private String remark;//备注
	private String strstate;
	public Orders() {
	}
	public Orders(Integer oid, Integer sid, String oname, Date ostarttime, Date oendtime, Date otime, Double money,
			Integer state, String strstate, String remark) {
		this.oid = oid;
		this.sid = sid;
		this.oname = oname;
		this.ostarttime = ostarttime;
		this.oendtime = oendtime;
		this.otime = otime;
		this.money = money;
		this.state = state;
		this.remark = remark;
		this.strstate = strstate;
	}
	public Integer getOid() {
		return oid;
	}
	public void setOid(Integer oid) {
		this.oid = oid;
	}
	public Integer getSid() {
		return sid;
	}
	public void setSid(Integer sid) {
		this.sid = sid;
	}
	public String getOname() {
		return oname;
	}
	public void setOname(String oname) {
		this.oname = oname;
	}
	public Date getOstarttime() {
		return ostarttime;
	}
	public void setOstarttime(Date ostarttime) {
		this.ostarttime = ostarttime;
	}
	public Date getOendtime() {
		return oendtime;
	}
	public void setOendtime(Date oendtime) {
		this.oendtime = oendtime;
	}
	public Date getOtime() {
		return otime;
	}
	public void setOtime(Date otime) {
		this.otime = otime;
	}
	public Double getMoney() {
		return money;
	}
	public void setMoney(Double money) {
		this.money = money;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public String getStrstate() {
		return strstate;
	}
	public void setStrstate(String strstate) {
		this.strstate = strstate;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Override
	public String toString() {
		return "Orders [oid=" + oid + ", sid=" + sid + ", oname=" + oname + ", ostarttime=" + ostarttime + ", oendtime="
				+ oendtime + ", otime=" + otime + ", money=" + money + ", state=" + state + ", strstate=" + strstate
				+ "]";
	}
}
