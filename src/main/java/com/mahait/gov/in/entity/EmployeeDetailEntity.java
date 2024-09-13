package com.mahait.gov.in.entity;


import javax.persistence.*;

import lombok.Data;

import java.io.Serializable;

@Data
@Entity
@Table(name = "employee_details_no_approval")
public class EmployeeDetailEntity implements Serializable {

    @Id
    @Column(name = "employee_id")
    private String employeeId;

    @Column(name = "sevaarth_id")
    private String sevaarthId;

    @Column(name = "gender")
    private String gender;

    @Column(name = "salutation")
    private String salutation;

    @Column(name = "employee_full_name_en")
    private String employeeFullNameEn;

    @Column(name = "employee_f_name_en")
    private String employeeFNameEn;

    @Column(name = "employee_m_name_en")
    private String employeeMNameEn;

    @Column(name = "employee_l_name_en")
    private String employeeLNameEn;

    @Column(name = "dob")
    private String dob; 

    @Column(name = "mobile_no1")
    private String mobileNo1;

    @Column(name = "mobile_no2")
    private String mobileNo2;

    @Column(name = "email_id")
    private String emailId;

    @Column(name = "eid_no")
    private String eidNo;

    @Column(name = "uid_no")
    private String uidNo;

    @Column(name = "pan_no")
    private String panNo;

    @Column(name = "address1")
    private String address1;

    @Column(name = "address2")
    private String address2;

    @Column(name = "address3")
    private String address3;

    @Column(name = "pincode")
    private String pincode;

    @Column(name = "district_code")
    private String districtCode;

    @Column(name = "state_code")
    private String stateCode;

    @Column(name = "appointment_date")
    private String appointmentDate;

    @Column(name = "doj")
    private String doj; 

    @Column(name = "user_id")
    private String userId;

    @Column(name = "cadre_code")
    private String cadreCode;

    @Column(name = "first_designation_code")
    private String firstDesignationCode;

    @Column(name = "designation_code")
    private String designationCode;

    @Column(name = "field_department_code")
    private String fieldDepartmentCode;

    @Column(name = "pay_scale_code")
    private String payScaleCode;

    @Column(name = "pay_in_pay_band")
    private String payInPayBand;

    @Column(name = "grade_pay")
    private String gradePay;

    @Column(name = "bank_code")
    private String bankCode;

    @Column(name = "ifsc_code")
    private String ifscCode;

    @Column(name = "bank_acnt_no")
    private String bankAcntNo;

    @Column(name = "bank_branch_code")
    private String bankBranchCode;

    @Column(name = "ddo_code")
    private String ddoCode;

    @Column(name = "approval_by_ddo_date")
    private String approvalByDdoDate; 

    @Column(name = "billgroup_id")
    private String billgroupId;

    @Column(name = "basic_pay")
    private String basicPay;

    @Column(name = "percentage_of_basic")
    private String percentageOfBasic;

    @Column(name = "head_act_code")
    private String headActCode;

    @Column(name = "employee_type")
    private String employeeType;

    @Column(name = "is_active")
    private String isActive;

    @Column(name = "emp_service_end_date")
    private String empServiceEndDate; 

    @Column(name = "super_ann_date")
    private String superAnnDate;

    @Column(name = "with_effect_from_date")
    private String withEffectFromDate;

    @Column(name = "grade_id")
    private String gradeId;

    @Column(name = "photo_attachment_id")
    private String photoAttachmentId;

    @Column(name = "signature_attachment_id")
    private String signatureAttachmentId;

    @Column(name = "created_user_id")
    private String createdUserId;

    @Column(name = "created_date")
    private String createdDate; 
    @Column(name = "updated_date")
    private String updatedDate; 

    @Column(name = "updated_user_id")
    private String updatedUserId;

    @Column(name = "pay_scale_level_id")
    private String payScaleLevelId;

    @Column(name = "post_detail_id")
    private String postDetailId;

    @Column(name = "remark")
    private String remark;

    @Column(name = "state_matrix_7pc_id")
    private String stateMatrix7pcId;

    @Column(name = "pyhical_handicapped")
    private String pyhicalHandicapped;

    @Column(name = "dcps_gpf_flag")
    private String dcpsGpfFlag;

    @Column(name = "pran_no")
    private String pranNo;

    @Column(name = "pran_status")
    private String pranStatus;

    @Column(name = "pran_remarks")
    private String pranRemarks;

    @Column(name = "pay_commission_code")
    private String payCommissionCode;

    @Column(name = "current_office_code")
    private String currentOfficeCode;

    @Column(name = "pay_scale_id_old")
    private String payScaleIdOld;

    @Column(name = "billgroup_id_old")
    private String billgroupIdOld;

    @Column(name = "seven_pc_basic")
    private String sevenPcBasic;

    @Column(name = "seven_pc_level")
    private String sevenPcLevel;

    @Column(name = "pay_scale_id")
    private String payScaleId;

    @Column(name = "ac_dcps_maintained_by")
    private String acDcpsMaintainedBy;

    @Column(name = "buckle_no")
    private String buckleNo;

    @Column(name = "emp_type")
    private String empType;

    @Column(name = "reason_for_change_parent_field_dept")
    private String reasonForChangeParentFieldDept;

    @Column(name = "city_class")
    private String cityClass;

    @Column(name = "reason_for_changedtls")
    private String reasonForChangedtls;

    @Column(name = "emp_class")
    private String empClass;

    @Column(name = "acc_maintained_by_other")
    private String accMaintainedByOther;

    @Column(name = "locality")
    private String locality;

    @Column(name = "inst_name")
    private String instName;

    @Column(name = "inst_email")
    private String instEmail;

    @Column(name = "curr_post_joining_date")
    private String currPostJoiningDate;

    @Column(name = "dt_join_current_post")
    private String dtJoinCurrentPost; 

    @Column(name = "accountmaintainby")
    private String accountmaintainby;

    @Column(name = "dcpsaccountmaintainby")
    private String dcpsaccountmaintainby;

    @Column(name = "gisapplicable")
    private String gisapplicable;

    @Column(name = "gisgroup")
    private String gisgroup;

    @Column(name = "indi_approve_order_no")
    private String indiApproveOrderNo;

    @Column(name = "membership_date")
    private String membershipDate; 

    @Column(name = "pfacno")
    private String pfacno;

    @Column(name = "pfdescription")
    private String pfdescription;

    @Column(name = "pfseries")
    private String pfseries;

    @Column(name = "dcps_no")
    private String dcpsNo;

    @Column(name = "head_of_acc_code")
    private String headOfAccCode;

    @Column(name = "appointment")
    private String appointment;

    @Column(name = "qualification")
    private String qualification;

    @Column(name = "more_qualification")
    private String moreQualification;

    @Column(name = "emp_types")
    private String empTypes;

}
