package com.mahait.gov.in.repository;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.mahait.gov.in.common.StringHelperUtils;
import com.mahait.gov.in.model.MstEmployeeModel;

@Repository
@SuppressWarnings("unchecked")
public class EmpChangeDetailsRepoImpl implements EmpChangeDetailsRepo {

	@PersistenceContext
	EntityManager manager;

	@Override
	public List<Object[]> findEmpforChangeDtls(String userName) {
		Session currentSession = manager.unwrap(Session.class);
		String hql = "select employee_full_name_en,sevaarth_id,dob,gender,employee_id from employee_mst where ddo_code = '"
				+ userName + "' and is_active = '1'";
		Query query = currentSession.createSQLQuery(hql);
		return (List<Object[]>) query.list();
	}

	@Override
	public MstEmployeeModel getEmpData(int empId) {
     Session currentSession = manager.unwrap(Session.class);
		
		String HQL = "select a.salutation,a.employee_f_name_en,a.employee_m_name_en,a.employee_l_name_en,a.employee_full_name_mr,a.uid_no,a.gender,a.marital_status,a.dob,a.doj,a.address1,"
				+ "a.address2,a.address3,a.mobile_no1,a.mobile_no2,a.pan_no,a.email_id,a.pincode,a.village_name,a.pyhical_handicapped,a.cadre_code,a.emp_class,a.super_ann_date,a.pay_commission_code,"
				+ "a.designation_code,a.pay_scale_code,a.pay_scale_level_id,case when a.pay_commission_code =8 then seven_pc_level else a.grade_pay end,case when a.pay_commission_code =8 then a.seven_pc_basic else a.basic_pay end,a.post_detail_id,a.admin_department_code,a.appointment_date,b.superannuation_age,a.bank_code,a.bank_branch_code"
				+ ",a.bank_acnt_no ,a.ifsc_code,a.dcps_gpf_flag,a.eid_no,a.employee_full_name_en,trim(a.state_code) as state,trim(a.district_code) as district,a.pay_in_pay_band,c.tel_no2,c.email,c.remarks,a.order_no,a.order_date,a.employee_id,a.sevaarth_id"
				+ ",d.nominee_name,d.nominee_address,d.relation,d.percent_share,c.ddo_city_category,e.gis_applicable,e.gis_group,a.landline_no,e.membership_date from employee_mst a  "
				+ "inner join cadre_mst b on a.cadre_code = b.cadre_code inner join ddo_reg_mst c on a.ddo_code = c.ddo_code left join nominee_details_mst d on a.employee_id = d.employee_id left join gis_details_mst e on a.employee_id = e.employee_id where a.employee_id ="+empId;

	
		
		Query query = currentSession.createSQLQuery(HQL);
		List<Object[]> lstprop = query.list();
		MstEmployeeModel lstObj = new MstEmployeeModel();
		int i=1;
		if (!lstprop.isEmpty()) {
            for (Object[] objLst : lstprop) {
            	lstObj.setSalutation(StringHelperUtils.isNullLong(objLst[0]));
            	lstObj.setEmployeeFNameEn(StringHelperUtils.isNullString(objLst[1]));
            	lstObj.setEmployeeMNameEn(StringHelperUtils.isNullString(objLst[2]));
            	lstObj.setEmployeeLNameEn(StringHelperUtils.isNullString(objLst[3]));
            	lstObj.setEmployeeFullNameMr(StringHelperUtils.isNullString(objLst[4]));
            	
            	if(objLst[5]!=null)
            	{

                	BigInteger b1=StringHelperUtils.isNullBigInteger(objLst[5]);
                    String uidNOs=b1.toString();
                    if(uidNOs.substring(0,4)!=null) {
                    	lstObj.setUidNo1(uidNOs.substring(0,4));
                    }
                    
                    if(uidNOs.substring(4,8)!=null) {
                    	lstObj.setUidNo2(uidNOs.substring(4,8));
                    }
                    
                    
                    if(uidNOs.substring(8,12)!=null) {
                    	lstObj.setUidNo3(uidNOs.substring(8,12));
                   }
            		
            	/*BigDecimal b2=(BigDecimal) objLst[5];
            	Long uidNo=b2.longValue();
            	lstObj.setUidNo(StringHelperUtils.isNullLong(uidNo));*/
            	
            	}
            	lstObj.setGender(StringHelperUtils.isNullCharacter(objLst[6]));
            	lstObj.setMaritalStatus(StringHelperUtils.isNullCharacter(objLst[7]));
            	lstObj.setDob(StringHelperUtils.isNullDate(objLst[8]));
            	lstObj.setDoj(StringHelperUtils.isNullDate(objLst[9]));
            	lstObj.setAddress1(StringHelperUtils.isNullString(objLst[10]));
            	lstObj.setAddress2(StringHelperUtils.isNullString(objLst[11]));
            	lstObj.setAddress3(StringHelperUtils.isNullString(objLst[12]));
            	 BigDecimal bg1 = (BigDecimal) (objLst[13]) ;
               Long mobNo1 = bg1.longValue();
            	lstObj.setMobileNo1(StringHelperUtils.isNullLong(mobNo1));
            	lstObj.setMobileNo2(StringHelperUtils.isNullLong(objLst[14]));
            	lstObj.setPanNo(StringHelperUtils.isNullString(objLst[15]));
            	lstObj.setEmailId(StringHelperUtils.isNullString(objLst[16]));
            	lstObj.setPinCode(StringHelperUtils.isNullLong(Integer.parseInt((String) objLst[17])));
            	lstObj.setVillageName(StringHelperUtils.isNullString(objLst[18]));
            	lstObj.setPhysicallyHandicapped(StringHelperUtils.isNullString(String.valueOf(objLst[19])));
            	lstObj.setPhysicallyHandicapped(StringHelperUtils.isNullString(objLst[19]));
            	lstObj.setCadreId(StringHelperUtils.isNullLong(objLst[20]));
            	lstObj.setEmpClass(StringHelperUtils.isNullLong(objLst[21]));
            	lstObj.setSuperAnnDate(StringHelperUtils.isNullDate(objLst[22]));
            	lstObj.setPayCommissionCode(StringHelperUtils.isNullLong(objLst[23]));
            	lstObj.setDesignationId(StringHelperUtils.isNullLong(objLst[24]));
            	lstObj.setPayScaleCode(StringHelperUtils.isNullLong(objLst[25]));
            	lstObj.setPayscalelevelId(StringHelperUtils.isNullString(objLst[26]));
            	//lstObj.setGradePay(StringHelperUtils.isNullInt(objLst[27]));
            	BigInteger b1 = (BigInteger) (objLst[28]);
            	int intValueOfb1 = b1.intValue();
            	lstObj.setBasicPay(StringHelperUtils.isNullDouble(intValueOfb1));
            	lstObj.setPostdetailid(StringHelperUtils.isNullLong(objLst[29]));
            	lstObj.setAdminDepartmentId(StringHelperUtils.isNullLong(objLst[30]));
            	lstObj.setAppointmentDate(((Date) objLst[31]));
            	BigDecimal b =  (BigDecimal) (objLst[32]);
                 String age = b.toString();
            	lstObj.setSuperannuationage(StringHelperUtils.isNullLong(age));
            	lstObj.setSuperannuationage(StringHelperUtils.isNullLong(age));
            	lstObj.setBankId(StringHelperUtils.isNullLong(objLst[33]));
            	lstObj.setBankBranchId(StringHelperUtils.isNullLong(objLst[34]));
            	lstObj.setBankAcntNo(StringHelperUtils.isNullString(objLst[35]));
            	lstObj.setIfscCode(StringHelperUtils.isNullString(objLst[36]));
            	lstObj.setDcpsgpfflag(StringHelperUtils.isNullString(String.valueOf(objLst[37])));
            	lstObj.setEidNo(StringHelperUtils.isNullString(objLst[38]));
            	lstObj.setEmployeeFullNameEn(StringHelperUtils.isNullString(objLst[39]));
            	lstObj.setStateCode(StringHelperUtils.isNullLong(Integer.valueOf((String)objLst[40])));
            	if(objLst[41]!=null)
            	{
            	lstObj.setDistrictCode(StringHelperUtils.isNullLong(Integer.valueOf((String)objLst[41])));
            	lstObj.setStateCode(StringHelperUtils.isNullLong(objLst[40]));
            	if(objLst[41]!=null)
            	{
            	lstObj.setDistrictCode(StringHelperUtils.isNullLong(objLst[41]));
            	}
            	lstObj.setCurrentOfficeId(StringHelperUtils.isNullLong(objLst[42]));
            	
            	lstObj.setInsttelnotwo(StringHelperUtils.isNullString(objLst[43].toString()));
            	lstObj.setInstemail(StringHelperUtils.isNullString(objLst[44]));
            	lstObj.setRemark(StringHelperUtils.isNullString(objLst[45]));
            	
            	lstObj.setOrderNo(StringHelperUtils.isNullLong(objLst[46]));
            	lstObj.setApprovalByDdoDate(StringHelperUtils.isNullDate(objLst[47]));
            	lstObj.setEmployeeId(StringHelperUtils.isNullLong(objLst[48]));
            	lstObj.setSevaarthId(StringHelperUtils.isNullString(objLst[49]));
            	
            	lstObj.setNomineename(StringHelperUtils.isNullString(objLst[50]));
            	lstObj.setNomineeaddress(StringHelperUtils.isNullString(objLst[51]));
            	lstObj.setRelation(StringHelperUtils.isNullString(objLst[52]));
            	
            	
            	if(objLst[53] !=null)
            	{
            	BigDecimal d = (BigDecimal) (objLst[53]);
            	int percent = d.intValue();
            	lstObj.setPercent_share(StringHelperUtils.isNullLong(percent));
            	}
            	lstObj.setCityClass(StringHelperUtils.isNullString(objLst[54]));
            	
            	char c=(char) objLst[55];  
            	int gisAppl=Character.getNumericValue(c);  
            	lstObj.setGisApplId(StringHelperUtils.isNullLong(gisAppl));
            	lstObj.setGisgroup(StringHelperUtils.isNullString(objLst[56].toString()));
            	lstObj.setLandlineNo(StringHelperUtils.isNullString(objLst[57]));
            	lstObj.setMembership_date(StringHelperUtils.isNullDate(objLst[58]));
            	
            	
            	
            
                i++;
            }
        }
		}
		return lstObj;
	}

	@Override
	public List<Object[]> getEmpSignPhoto(Integer employeeId) {
		// TODO Auto-generated method stub 04/08/2021
		Session currentSession = manager.unwrap(Session.class);
		String hql = "select photo_attachment_id,signature_attachment_id from employee_mst where employee_id ="
				+ employeeId;
		Query query = currentSession.createSQLQuery(hql);
		return (List<Object[]>) query.list();
	}

	@Override
	public List<Object[]> GetCurrentPostDesigation(Integer postdetailid) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * @Override public List<MstSevenMatrixEntity> getsevenPCBasic(int payscaleId) {
	 * 
	 * Session currentSession = manager.unwrap(Session.class);
	 * 
	 * String HQL = "SELECT state_matrix_7pc_id, s_"+ payscaleId +
	 * " FROM state_matrix_7pc_mst"; Query query =
	 * currentSession.createSQLQuery(HQL);
	 * 
	 * 
	 * List<MstSevenMatrixEntity> obj= new ArrayList<>();
	 * 
	 * List<Object[]> lstprop = query.list();
	 * 
	 * int i=1; if (!lstprop.isEmpty()) { for (Object[] objLst : lstprop) {
	 * MstSevenMatrixEntity lstObj = new MstSevenMatrixEntity();
	 * lstObj.setS1(StringHelperUtils.isNullInt(objLst[1]));
	 * lstObj.setStatematrix7PCId(StringHelperUtils.isNullInt(objLst[0]));
	 * obj.add(lstObj); }
	 * 
	 * 
	 * } return obj; }
	 * 
	 * @Override public List<MstEmployeeEntity> findEmpLst(String ddocode) { Session
	 * currentSession = manager.unwrap(Session.class); // String HQL =
	 * "FROM MstEmployeeEntity as t where t.isActive='1' and t.ddoCode is not null "
	 * ; // String HQL =
	 * "SELECT a FROM MstEmployeeEntity a INNER JOIN a.mstSubDepartmentEntity b INNER JOIN a.mstGpfDetailsEntity c  where a.isActive='1' and a.ddoCode is not null "
	 * ; String HQL =
	 * "SELECT a FROM MstEmployeeEntity a INNER JOIN a.mstSubDepartmentEntity b  where (a.isActive='1' or a.isActive='4') and a.ddoCode ='"
	 * +ddocode+"' "; return (List<MstEmployeeEntity>)
	 * manager.createQuery(HQL).getResultList(); }
	 * 
	 * @Override public MstEmployeeEntity getEmployeeData(int empId) {
	 * MstEmployeeEntity objDept = null; Session currentSession =
	 * manager.unwrap(Session.class); objDept =
	 * currentSession.get(MstEmployeeEntity.class, empId); return objDept; }
	 * 
	 * @Override public List<Object[]> GetCurrentPost(int designationId, String
	 * ddocode) {
	 * 
	 * Session currentSession = manager.unwrap(Session.class); String hql =
	 * "select c.post_details_id,c.post_name,a.designation_code,a.designation_name from designation_mst a inner join post_mst b on a.designation_code = b.designation_code inner join post_details_rlt c on b.post_code = c.post_code where a.is_active = '1' and a.designation_code = '"
	 * + designationId +
	 * "' and c.ddo_id in (select ddo_code_user_id1 from ddo_map_rlt where ddo_code_user_id1 in (select ddo_reg_id from ddo_reg_mst where ddo_code='"
	 * + ddocode + "'));"; Query query = currentSession.createSQLQuery(hql); return
	 * query.list(); }
	 * 
	 * @Override public void updateChangeEmpDtls(MstEmployeeEntity objDeptForReject)
	 * { Session currentSession = manager.unwrap(Session.class);
	 * currentSession.update(objDeptForReject); }
	 * 
	 * 
	 * @Override public List<MstEmployeeEntity> findEmpLstforApprovChngDtls() {
	 * Session currentSession = manager.unwrap(Session.class); //String HQL =
	 * "FROM MstEmployeeEntity as t where t.isActive='1' and t.ddoCode is not null "
	 * ; String HQL =
	 * "SELECT a FROM MstEmployeeEntity a INNER JOIN a.mstSubDepartmentEntity b  where a.isActive='5' and a.ddoCode is not null "
	 * ; return (List<MstEmployeeEntity>) manager.createQuery(HQL).getResultList();
	 * }
	 * 
	 * @Override public MstEmployeeEntity findempid(Integer employeeId) {
	 * MstEmployeeEntity objDept = null; Session currentSession =
	 * manager.unwrap(Session.class); objDept =
	 * currentSession.get(MstEmployeeEntity.class, employeeId); return objDept; }
	 * 
	 * @Override public List<Object[]> GetCurrentPostDesigation(Integer
	 * postdetailid) {
	 * 
	 * Session currentSession = manager.unwrap(Session.class); String hql =
	 * "select post_details_id,post_name from post_details_rlt where post_details_id="
	 * +postdetailid; Query query = currentSession.createSQLQuery(hql); return
	 * (List<Object[]>) query.list(); }
	 * 
	 * @Override public void updateChangeEmpHstDtls(ChangeDtlsHst changeDtlsHst) {
	 * Session currentSession = manager.unwrap(Session.class);
	 * currentSession.save(changeDtlsHst); }
	 */
}
