package com.mahait.gov.in.entity;

//@Entity
//@Table(name="sub_department_mst",schema="public")
public class MstSubDepartmentEntity {
	
//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	/*@OneToMany(cascade=CascadeType.ALL,fetch=FetchType.EAGER,mappedBy = 'SUB_DEPARTMENT_ID')*/
//	@Column(name="SUB_DEPARTMENT_ID")
//	private int subDepartmentId;
//	
//	@ManyToOne
//	@JoinColumn(name = "DEPARTMENT_CODE",  nullable=true,insertable = false, updatable = false)
//    private MstDepartmentEntity mstDepartmentEntity;
//
////	
////	  @OneToMany(cascade = CascadeType.ALL, mappedBy = "mstSubDepartmentEntity", orphanRemoval = true) 
////	  private List<MstEmployeeEntity> mstEmployeeEntity;
//	
//	    @ManyToOne
//		@JoinColumn(name = "SUB_SE_DEPARTMENT_ID",  nullable=true,insertable = false, updatable = false)
//	    private MstSESubDepartmentEntity mstSESubDepartmentEntity;
//
//	public List<MstEmployeeEntity> getMstEmployeeEntity() {
//		return mstEmployeeEntity;
//	}
//
//	public void setMstEmployeeEntity(List<MstEmployeeEntity> mstEmployeeEntity) {
//		this.mstEmployeeEntity = mstEmployeeEntity;
//	}
//
//
//	@Column(name="DEPARTMENT_CODE")
//	private Integer departmentCode;
//	
//	
//	public MstDepartmentEntity getMstDepartmentEntity() {
//		return mstDepartmentEntity;
//	}
//
//	public void setMstDepartmentEntity(MstDepartmentEntity mstDepartmentEntity) {
//		this.mstDepartmentEntity = mstDepartmentEntity;
//	}
//
//	
//	@Column(name="SUB_DEPARTMENT_CODE")
//	private Integer subDepartmentCode;
//	
//	
//	@Column(name="SUB_DEPARTMENT_SHORT_NAME")
//	private String subDepartmentShortName;
//	
//	@Column(name="SUB_DEPARTMENT_NAME_EN")
//	private String subDepartmentNameEn;
//	
//
//	@Column(name="SUB_DEPARTMENT_NAME_Mr")
//	private String subDepartmentNameMr;
//	
//	@Column(name = "IS_ACTIVE")
//	private Character isActive;
//	
//	public String getSubDepartmentShortName() {
//		return subDepartmentShortName;
//	}
//
//	public void setSubDepartmentShortName(String subDepartmentShortName) {
//		this.subDepartmentShortName = subDepartmentShortName;
//	}
//
//	@Column(name = "CREATED_USER_ID")
//	private Integer createdUserId;
//	
//	@Column(name = "CREATED_DATE")
//	private Date createdDate;
//	
//	@Column(name = "UPDATED_USER_ID")
//	private Integer updatedUserId;
//
//	@Column(name = "UPDATED_DATE")
//	private Date updatedDate;
//	
//	@Column(name = "divisiontype")
//	private Integer divisiontype;
//
//	public int getSubDepartmentId() {
//		return subDepartmentId;
//	}
//
//	public void setSubDepartmentId(int subDepartmentId) {
//		this.subDepartmentId = subDepartmentId;
//	}
//
//	public Integer getDepartmentCode() {
//		return departmentCode;
//	}
//
//	public void setDepartmentCode(Integer departmentCode) {
//		this.departmentCode = departmentCode;
//	}
//	
//
//	public Integer getSubDepartmentCode() {
//		return subDepartmentCode;
//	}
//
//	public void setSubDepartmentCode(Integer subDepartmentCode) {
//		this.subDepartmentCode = subDepartmentCode;
//	}
//
//	public char getIsActive() {
//		return isActive;
//	}
//
//	public void setIsActive(char isActive) {
//		this.isActive = isActive;
//	}
//
//	public int getCreatedUserId() {
//		return createdUserId;
//	}
//
//	public void setCreatedUserId(int createdUserId) {
//		this.createdUserId = createdUserId;
//	}
//
//	public Date getCreatedDate() {
//		return createdDate;
//	}
//
//	public void setCreatedDate(Date createdDate) {
//		this.createdDate = createdDate;
//	}
//
//	public int getUpdatedUserId() {
//		return updatedUserId;
//	}
//
//	public void setUpdatedUserId(int updatedUserId) {
//		this.updatedUserId = updatedUserId;
//	}
//
//	public Date getUpdatedDate() {
//		return updatedDate;
//	}
//
//	public void setUpdatedDate(Date updatedDate) {
//		this.updatedDate = updatedDate;
//	}
//
//	public String getSubDepartmentNameEn() {
//		return subDepartmentNameEn;
//	}
//
//	public void setSubDepartmentNameEn(String subDepartmentNameEn) {
//		this.subDepartmentNameEn = subDepartmentNameEn;
//	}
//
//	public String getSubDepartmentNameMr() {
//		return subDepartmentNameMr;
//	}
//
//	public void setSubDepartmentNameMr(String subDepartmentNameMr) {
//		this.subDepartmentNameMr = subDepartmentNameMr;
//	}
//
//	public void setIsActive(Character isActive) {
//		this.isActive = isActive;
//	}
//
//	public void setCreatedUserId(Integer createdUserId) {
//		this.createdUserId = createdUserId;
//	}
//
//	public void setUpdatedUserId(Integer updatedUserId) {
//		this.updatedUserId = updatedUserId;
//	}
//
//	public Integer getDivisiontype() {
//		return divisiontype;
//	}
//
//	public void setDivisiontype(Integer divisiontype) {
//		this.divisiontype = divisiontype;
//	}
//	
//	
//	
//	/*@Override
//	public String toString() {
//		return "MstSubDepartmentEntity [subDepartmentId=" + subDepartmentId + ", mstDepartmentEntity="
//				+ mstDepartmentEntity + ", mstEmployeeEntity=" + mstEmployeeEntity + ", departmentCode="
//				+ departmentCode + ", subDepartmentCode=" + subDepartmentCode + ", subDepartmentShortName="
//				+ subDepartmentShortName + ", subDepartmentNameEn=" + subDepartmentNameEn + ", subDepartmentNameMr="
//				+ subDepartmentNameMr + ", isActive=" + isActive + ", createdUserId=" + createdUserId + ", createdDate="
//				+ createdDate + ", updatedUserId=" + updatedUserId + ", updatedDate=" + updatedDate + "]";
//	}*/

}