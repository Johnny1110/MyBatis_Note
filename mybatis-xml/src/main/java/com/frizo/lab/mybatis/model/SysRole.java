package com.frizo.lab.mybatis.model;

import com.frizo.lab.mybatis.type.Enabled;

import java.io.Serializable;
import java.util.Date;
import java.util.List;



public class SysRole implements Serializable {
	private static final long serialVersionUID = 6320941908222932112L;

	private Long id;

	private String roleName;

	private boolean enabled;

	private String createBy;

	private Date createTime;

	private SysUser user;

	public SysUser getUser() {
		return user;
	}

	public void setUser(SysUser user) {
		this.user = user;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
}
