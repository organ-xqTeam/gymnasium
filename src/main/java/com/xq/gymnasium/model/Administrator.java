package com.xq.gymnasium.model;

import java.util.Date;

/**
 * 管理员
 * @ClassName Administrator
 * @Author yangweihang
 * @Date 2018年9月11日 上午11:36:16
 */
public class Administrator {
	private Integer aid;//管理员id
	private String ausername;//管理员用户名
	private String apwd;//管理员密码
	private String aname;//管理员姓名
	private String aphonenum;//管理员手机号
	private Date astarttime;//创建时间
	private Date aendtime;//修改时间
	private String aemile;//邮箱
	private String arole;//角色
	private Integer gid;//绑定体育馆id
	public Administrator() {
	}
	public Administrator(Integer aid, String ausername, String apwd, String aname, String aphonenum, Date astarttime,
			Date aendtime, String aemile, String arole, Integer gid) {
		this.aid = aid;
		this.ausername = ausername;
		this.apwd = apwd;
		this.aname = aname;
		this.aphonenum = aphonenum;
		this.astarttime = astarttime;
		this.aendtime = aendtime;
		this.aemile = aemile;
		this.arole = arole;
		this.gid = gid;
	}
	public Integer getAid() {
		return aid;
	}
	public void setAid(Integer aid) {
		this.aid = aid;
	}
	public String getAname() {
		return aname;
	}
	public void setAname(String aname) {
		this.aname = aname;
	}
	public String getAphonenum() {
		return aphonenum;
	}
	public void setAphonenum(String aphonenum) {
		this.aphonenum = aphonenum;
	}
	public Date getAstarttime() {
		return astarttime;
	}
	public void setAstarttime(Date astarttime) {
		this.astarttime = astarttime;
	}
	public Date getAendtime() {
		return aendtime;
	}
	public void setAendtime(Date aendtime) {
		this.aendtime = aendtime;
	}
	public String getAemile() {
		return aemile;
	}
	public void setAemile(String aemile) {
		this.aemile = aemile;
	}
	public String getArole() {
		return arole;
	}
	public void setArole(String arole) {
		this.arole = arole;
	}
	public String getAusername() {
		return ausername;
	}
	public void setAusername(String ausername) {
		this.ausername = ausername;
	}
	public String getApwd() {
		return apwd;
	}
	public void setApwd(String apwd) {
		this.apwd = apwd;
	}
	public Integer getGid() {
		return gid;
	}
	public void setGid(Integer gid) {
		this.gid = gid;
	}
	@Override
	public String toString() {
		return "Administrator [aid=" + aid + ", ausername=" + ausername + ", apwd=" + apwd + ", aname=" + aname
				+ ", aphonenum=" + aphonenum + ", astarttime=" + astarttime + ", aendtime=" + aendtime + ", aemile="
				+ aemile + ", arole=" + arole + ", gid=" + gid + "]";
	}
}
