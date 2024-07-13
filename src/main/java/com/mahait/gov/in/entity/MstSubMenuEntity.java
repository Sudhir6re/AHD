package com.mahait.gov.in.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@NoArgsConstructor
@AllArgsConstructor
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

}
