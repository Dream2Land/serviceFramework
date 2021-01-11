package cn.xdaoy.common.bean;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public abstract class BaseBean implements Serializable{

	protected static final long serialVersionUID = 1L;
	
	private String id;
	
	private String delFlag;
	
	private String creater;
	
	private String createrOrg;
	
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="UTC+8")
	private Date createTime;
	
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="UTC+8")
	private Date updateTime;

	private Integer pageNum;
	
	private Integer pageSize;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}

	public String getCreater() {
		return creater;
	}

	public void setCreater(String creater) {
		this.creater = creater;
	}

	public String getCreaterOrg() {
		return createrOrg;
	}

	public void setCreaterOrg(String createrOrg) {
		this.createrOrg = createrOrg;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
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
	
	public BaseBean(String id) {
		this.id = id;
	}
	
}
