package com.xq.gymnasium.model;

import java.util.Date;

/**
 * 体育馆信息
 * @ClassName Message
 * @Author yangweihang
 * @Date 2018年9月11日 上午10:21:42
 */
public class GymnasiumMessage {
	private Integer gid;//体育馆信息id
	private String gname;//体育馆名称
	private String glocation;//体育馆位置
	private String gnumber;//体育馆编号
	private String gphonenum;//体育馆联系电话
	private String gstarttime;//体育馆开馆时间
	private String gendtime;//体育馆闭馆时间
	private String gclosedtime;//体育馆休馆时间  	0-无休 1-星期一	2.星期二 3.星期三 4.星期四 5.星期五 6.星期六 7.星期天
	private String facilities;//体育馆场馆设施
	private String noticeuse;//体育馆使用须知
	private Date time;//时间
	private String hcode;//医院编码
	private Integer state;//1.开馆 2.闭馆
	private String stietype;
	public GymnasiumMessage() {
	}
	public GymnasiumMessage(Integer gid, String gname, String glocation, String gnumber, String gphonenum,
			String gstarttime, String gendtime, String gclosedtime, String facilities, String noticeuse, Date time, String sitetype, String hcode, Integer state) {
		this.gid = gid;
		this.gname = gname;
		this.glocation = glocation;
		this.gnumber = gnumber;
		this.gphonenum = gphonenum;
		this.gstarttime = gstarttime;
		this.gendtime = gendtime;
		this.gclosedtime = gclosedtime;
		this.facilities = facilities;
		this.noticeuse = noticeuse;
		this.time = time;
		this.hcode = hcode;
		this.state = state;
		this.stietype = sitetype;
	}
	public Integer getGid() {
		return gid;
	}
	public void setGid(Integer gid) {
		this.gid = gid;
	}
	public String getGname() {
		return gname;
	}
	public void setGname(String gname) {
		this.gname = gname;
	}
	public String getGlocation() {
		return glocation;
	}
	public void setGlocation(String glocation) {
		this.glocation = glocation;
	}
	public String getGnumber() {
		return gnumber;
	}
	public void setGnumber(String gnumber) {
		this.gnumber = gnumber;
	}
	public String getGphonenum() {
		return gphonenum;
	}
	public void setGphonenum(String gphonenum) {
		this.gphonenum = gphonenum;
	}
	public String getGstarttime() {
		return gstarttime;
	}
	public void setGstarttime(String gstarttime) {
		this.gstarttime = gstarttime;
	}
	public String getGendtime() {
		return gendtime;
	}
	public void setGendtime(String gendtime) {
		this.gendtime = gendtime;
	}
	public String getGclosedtime() {
		return gclosedtime;
	}
	public void setGclosedtime(String gclosedtime) {
		this.gclosedtime = gclosedtime;
	}
	public String getFacilities() {
		return facilities;
	}
	public void setFacilities(String facilities) {
		this.facilities = facilities;
	}
	public String getNoticeuse() {
		return noticeuse;
	}
	public void setNoticeuse(String noticeuse) {
		this.noticeuse = noticeuse;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	public String getHcode() {
		return hcode;
	}
	public void setHcode(String hcode) {
		this.hcode = hcode;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public String getStietype() {
		return stietype;
	}
	public void setStietype(String stietype) {
		this.stietype = stietype;
	}
	@Override
	public String toString() {
		return "GymnasiumMessage [gid=" + gid + ", gname=" + gname + ", glocation=" + glocation + ", gnumber=" + gnumber
				+ ", gphonenum=" + gphonenum + ", gstarttime=" + gstarttime + ", gendtime=" + gendtime
				+ ", gclosedtime=" + gclosedtime + ", facilities=" + facilities + ", noticeuse=" + noticeuse + ", time="
				+ time + ", hcode=" + hcode + ", state=" + state + ", stietype=" + stietype + "]";
	}
}
