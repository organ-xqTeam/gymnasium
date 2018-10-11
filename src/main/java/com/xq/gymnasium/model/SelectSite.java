package com.xq.gymnasium.model;

/**
 * 查询体育馆场地
 * @ClassName SelectSite
 * @Author yangweihang
 * @Date 2018年9月13日 下午9:28:35
 */
public class SelectSite {
	private Integer gid;
	private String sname;//体育馆场地名称
	private String stname;//体育馆类别名称
	private String pstatus;//发布状态 2.可预订 2.不可预订
	private String starttime;//开始时间
	private String endtime;//结束时间
	private Integer pageNum;//页数
	private Integer pageSize;//一页多少个
	public SelectSite() {
	}
	public SelectSite(Integer gid,String sname, String stname, String pstatus, String starttime, String endtime, Integer pageNum,
			Integer pageSize) {
		this.gid = gid;
		this.sname = sname;
		this.stname = stname;
		this.pstatus = pstatus;
		this.starttime = starttime;
		this.endtime = endtime;
		this.pageNum = pageNum;
		this.pageSize = pageSize;
	}
	public Integer getGid() {
		return gid;
	}
	public void setGid(Integer gid) {
		this.gid = gid;
	}
	public String getSname() {
		return sname;
	}
	public void setSname(String sname) {
		this.sname = sname;
	}
	public String getStname() {
		return stname;
	}
	public void setStname(String stname) {
		this.stname = stname;
	}
	public String getPstatus() {
		return pstatus;
	}
	public void setPstatus(String pstatus) {
		this.pstatus = pstatus;
	}
	public String getStarttime() {
		return starttime;
	}
	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}
	public String getEndtime() {
		return endtime;
	}
	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}
	public Integer getPageNum() {
		return pageNum;
	}
	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	@Override
	public String toString() {
		return "SelectSite [sname=" + sname + ", stname=" + stname + ", pstatus=" + pstatus + ", starttime=" + starttime
				+ ", endtime=" + endtime + ", pageNum=" + pageNum + ", pageSize=" + pageSize + "]";
	}
}
