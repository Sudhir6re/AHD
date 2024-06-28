package com.mahait.gov.in.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="sub_menu_mst",schema="public")
public class MstSubMenuEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="SUB_MENU_ID")
	private Integer subMenuId;
	
	@Column(name="MENU_CODE")
	private Integer menuCode;
	
	@Column(name="SUB_MENU_CODE")
	private Integer subMenuCode;

	@Column(name="ROLE_ID")
	private Integer roleId;
	
	@Column(name="sub_menu_name_english")
	private String sub_menu_name_english;
	
	@Column(name="controller_name")
	private String controller_name;
	
	@Column(name="link_name")
	private String link_name;
	
	@Column(name="sub_menu_name_marathi")
	private String sub_menu_name_marathi;

	@Column(name="is_active")
	private Character is_active;

	@Column(name = "CREATED_USER_ID")
	private Integer createdUserId;
	
	@Column(name = "CREATED_DATE")
	private Date createdDate;
	
	@Column(name = "UPDATED_USER_ID")
	private Integer updatedUserId;

	@Column(name = "UPDATED_DATE")
	private Date updatedDate;
	
	public Integer getSubMenuId() {
		return subMenuId;
	}

	public void setSubMenuId(Integer subMenuId) {
		this.subMenuId = subMenuId;
	}

	public Integer getMenuCode() {
		return menuCode;
	}

	public void setMenuCode(Integer menuCode) {
		this.menuCode = menuCode;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public String getSub_menu_name_english() {
		return sub_menu_name_english;
	}

	public void setSub_menu_name_english(String sub_menu_name_english) {
		this.sub_menu_name_english = sub_menu_name_english;
	}

	public String getLink_name() {
		return link_name;
	}

	public void setLink_name(String link_name) {
		this.link_name = link_name;
	}

	public String getSub_menu_name_marathi() {
		return sub_menu_name_marathi;
	}

	public void setSub_menu_name_marathi(String sub_menu_name_marathi) {
		this.sub_menu_name_marathi = sub_menu_name_marathi;
	}

	public Character getIs_active() {
		return is_active;
	}

	public void setIs_active(Character is_active) {
		this.is_active = is_active;
	}

	public Integer getCreatedUserId() {
		return createdUserId;
	}

	public void setCreatedUserId(Integer createdUserId) {
		this.createdUserId = createdUserId;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Integer getUpdatedUserId() {
		return updatedUserId;
	}

	public void setUpdatedUserId(Integer updatedUserId) {
		this.updatedUserId = updatedUserId;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public String getController_name() {
		return controller_name;
	}

	public void setController_name(String controller_name) {
		this.controller_name = controller_name;
	}

	public Integer getSubMenuCode() {
		return subMenuCode;
	}

	public void setSubMenuCode(Integer subMenuCode) {
		this.subMenuCode = subMenuCode;
	}
}
