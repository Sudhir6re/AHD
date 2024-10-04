package com.mahait.gov.in.nps.repository;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.validation.Valid;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.mahait.gov.in.entity.MstEmployeeEntity;
import com.mahait.gov.in.entity.MstNomineeDetailsEntity;
import com.mahait.gov.in.entity.MstStateEntity;
import com.mahait.gov.in.entity.ZpRltDdoMap;
import com.mahait.gov.in.nps.entity.MstEmployeeNPSEntity;
import com.mahait.gov.in.nps.entity.NSDLBHDtlsEntity;
import com.mahait.gov.in.nps.entity.TrnNpsRegFileEntity;
import com.mahait.gov.in.nps.model.MstEmployeeNPSModel;

@Repository

public class CSRFFormRepoImpl implements CSRFFormRepo {

	@PersistenceContext
	EntityManager entityManager;

	// protected final Log logger = LogFactory.getLog(getClass());

	@Override
	public List<Object[]> findAllEmployees(String ddoCode) {
		// TODO Auto-generated method stub

		Session currentSession = entityManager.unwrap(Session.class);
		String hql = "select a.sevaarth_id,a.employee_full_name_en,to_char(a.doj,'dd/MM/YYYY') as DOJ, c.designation_name,d.ddo_code_level2,e.office_name,f.dcps_no,a.employee_id from employee_mst a      \r\n" + 
				"    inner join designation_mst c on c.designation_code = a.designation_code      \r\n" + 
				"    inner join ddo_map_rlt d on d.ddo_code_level1 = a.ddo_code      \r\n" + 
				"    inner join ddo_reg_mst e on d.ddo_code_level1=e.ddo_code  \r\n" + 
//				 "  inner join nominee_details_mst g on g.employee_id=a.employee_id    \r\n" + 
				"    left join dcps_details_mst f on a.sevaarth_id=f.sevaarth_id and f.is_active='Y'       \r\n" + 
				"     where a.billgroup_id is not null   and  dcps_gpf_flag ='Y' and a.is_mapped_with_nps='0' and a.is_active='1'\r\n" + 
				"     and a.ddo_code='"+ddoCode+"' and a.employee_id not in (select employee_id from employee_nps_mst)order by a.employee_full_name_en"; //
		Query query = currentSession.createSQLQuery(hql);  //and a.is_mapped_with_nps='0'
		System.out.println("--hql-"+hql);
		return query.list();
	}

	@Override
	public MstEmployeeEntity findEmployeeBySevaarthId(Long empId) {

		// TypedQuery<MstEmployeeEntity> query = entityManager.createQuery( "SELECT e
		// FROM MstEmployeeEntity e JOIN e.mstNomineeDetailsEntity ph ",
		// MstEmployeeEntity.class);
		TypedQuery<MstEmployeeEntity> query = entityManager.createQuery(
				"SELECT a FROM MstEmployeeEntity a INNER JOIN a.mstNomineeDetailsEntity b  where a.employeeId=" + empId,
				MstEmployeeEntity.class);
		List<MstEmployeeEntity> resultList = query.getResultList();

		MstEmployeeEntity mstEmployeeEntity = new MstEmployeeEntity();

		if (resultList.size() > 0) {
			mstEmployeeEntity = resultList.get(0);
		} else {
			mstEmployeeEntity = null;
		}

		return mstEmployeeEntity;
	}

	@Override
	public List<MstNomineeDetailsEntity> findNomineeBySevaarthId(int sevaarthId) {
		MstEmployeeEntity mstEmployeeEntity;
		Session currentSession = entityManager.unwrap(Session.class);
		String HQL = "FROM MstNomineeDetailsEntity as t where t.employeeId='" + sevaarthId + "' ";
		List<MstNomineeDetailsEntity> lstMstNomineeDetailsEntity = (List<MstNomineeDetailsEntity>) entityManager
				.createQuery(HQL).getResultList();
		return lstMstNomineeDetailsEntity;
	}

	@Override
	public int saveCSRF(MstEmployeeNPSEntity mstEmployeeNPSEntity) {
		int saveId=0;
		Session currentSession = entityManager.unwrap(Session.class);
		saveId=(int) currentSession.save(mstEmployeeNPSEntity);
		return saveId;
	}

	@Override
	public MstEmployeeNPSEntity findEmployeeDtlsBySevaarthId(Long empId) {
		MstEmployeeNPSEntity mstEmployeeNPSEntity;

		Session currentSession = entityManager.unwrap(Session.class);
		String HQL = "FROM MstEmployeeNPSEntity as t where t.employeeId='" + empId + "' ";
		List<MstEmployeeNPSEntity> lstMstEmployeeNPSEntity = (List<MstEmployeeNPSEntity>) entityManager.createQuery(HQL)
				.getResultList();
		if (lstMstEmployeeNPSEntity.size() > 0) {
			mstEmployeeNPSEntity = lstMstEmployeeNPSEntity.get(0);
		} else {
			mstEmployeeNPSEntity = null;
		}

		return mstEmployeeNPSEntity;

	}

	@Override
	public List<Object[]> findAllCSRFApprovedEmployees() {
		Session currentSession = entityManager.unwrap(Session.class);
		String hql = "select a.sevaarth_id,a.employee_full_name,b.ddo_code,c.ddo_code_level2,a.employee_id from employee_nps_mst a inner join employee_mst"
				+ " b on a.sevaarth_id =b.sevaarth_id inner join ddo_map_rlt c on b.ddo_code=c.ddo_code_level1 where a.pran_no is null"; //
		Query query = currentSession.createSQLQuery(hql);
		return query.list();
	}

	@Override
	public List<Object[]> findAllCSRFApprovedEmployees(String sevaarthId, String ddoAsst) {
		Session currentSession = entityManager.unwrap(Session.class);
		String hql = "select sevaarth_id,employee_full_name from employee_nps_mst where  sevaarth_id ='" + sevaarthId
				+ "' "; //
		Query query = currentSession.createSQLQuery(hql);
		return query.list();
	}

	@Override
	public List<MstEmployeeNPSEntity> getEmpDataForTextFile(String userName, int countEmp) {
		Session currentSession = entityManager.unwrap(Session.class);
		String HQL = "FROM MstEmployeeNPSEntity as t where t.pranNo is null and  t.ddoCode='" + userName + "'ORDER BY t.createdDate ";
		List<MstEmployeeNPSEntity> lstMstEmployeeNPSEntity = (List<MstEmployeeNPSEntity>) entityManager.createQuery(HQL)
				.setMaxResults(countEmp).getResultList();
		return lstMstEmployeeNPSEntity;
	}

	@Override
	public String getDDOLevel2FromDDO1(String userName) {
		Session currentSession = entityManager.unwrap(Session.class);
		// String hql = "SELECT ddo_code_level2 FROM ddo_map_rlt where ddo_code_level1 =
		// '"+userName+"'";
		String hql = "From ZpRltDdoMap as t where t.zpDdoCode='" + userName + "'";
		Query query = currentSession.createSQLQuery(hql);
		List<ZpRltDdoMap> lstWorkflowCharterAssignWithDDOEntity = (List<ZpRltDdoMap>) entityManager
				.createQuery(hql).getResultList();
		String strDDO = lstWorkflowCharterAssignWithDDOEntity.stream().map(m -> m.getReptDdoCode()).findFirst()
				.orElse("0");
		return strDDO;
	}

	@Override
	public List<Object[]> getDeptNameFromDDO2(String userName) {
		Session currentSession = entityManager.unwrap(Session.class);
		// String hql = "SELECT ddo_code_level2 FROM ddo_map_rlt where ddo_code_level1 =
		// '"+userName+"'";
		String hql = "select b.sub_department_name_en,a.office_name,b.sub_department_short_name from ddo_reg_mst a inner join sub_department_mst b on a.sub_department_code = b.sub_department_code where a.ddo_code='"
				+ userName + "'";
		Query query = currentSession.createSQLQuery(hql);
		System.out.println("getDeptNameFromDDO2--------" + hql);
		return query.list();
	}

	@Override
	public List<Object[]> getempData(String sevaarthId) {
		// TODO Auto-generated method stub
		Session currentSession = entityManager.unwrap(Session.class);
		// String hql = "SELECT ddo_code_level2 FROM ddo_map_rlt where ddo_code_level1 =
		// '"+userName+"'";
		String hql = "select basic_pay,seven_pc_basic,seven_pc_level,case when a.pay_commission_code=8 then pay_in_payband else b.scale_desc end as scale_desc,c.group_name_en from employee_mst a " + 
				"left join pay_scale_sixpc_mst b on a.pay_scale_code=b.pay_scale_code " + 
				"left join payband_gp_state_7pc d on d.level_id=a.seven_pc_level " + 
				"inner join cadre_group_mst c on c.id=a.emp_class where a.sevaarth_id='"
				+ sevaarthId +"'";
		
		System.out.println("getempData--------" + hql);
		Query query = currentSession.createSQLQuery(hql);
	
		return query.list();
	}

	@Override
	public Integer getSeqUpdate() {
		Session currentSession = entityManager.unwrap(Session.class);
		List list = new ArrayList();
		Integer rtnStr = null;
		BigInteger count = null;
		StringBuffer query = new StringBuffer();
		query.append("select nextval ('acknowledgement_no_seq') ");
		Query hsqlQuery = currentSession.createSQLQuery(query.toString());
		list = hsqlQuery.list();
		if (list != null && list.size() > 0)
			count = (BigInteger) list.get(0);
		rtnStr = count.intValue();
		return rtnStr;
	}

	@Override
	public Long getSequenceForTextFile(String sequenceName) {
		// TODO Auto-generated method stub
		Session currentSession = entityManager.unwrap(Session.class);
		List list = new ArrayList();
		Integer rtnStr = null;
		Long count = null;
		StringBuffer query = new StringBuffer();
		query.append("select nextval ('" + sequenceName + "') ");
		Query hsqlQuery = currentSession.createSQLQuery(query.toString());
		list = hsqlQuery.list();
		if (list != null && list.size() > 0)
			count = (Long) list.get(0);

		return count;
	}

	//finding single object with non-primary key column
	@Override
	public MstEmployeeNPSEntity getEmployeeNPSEntity(Integer employeeId) {
		System.out.println(employeeId);
		MstEmployeeNPSEntity mstEmployeeNPSEntity;
		Session currentSession = entityManager.unwrap(Session.class);
	//	String HQL = "FROM MstEmployeeNPSEntity as t where sevaarthId='" + sevaarthId + "'";
		Query<MstEmployeeNPSEntity> query = currentSession.createQuery(
				"FROM MstEmployeeNPSEntity as t where t.employeeId=:employeeId", MstEmployeeNPSEntity.class);
		query.setParameter("employeeId", employeeId);
		mstEmployeeNPSEntity = query.uniqueResult();
		return mstEmployeeNPSEntity;
	}

	@Override
	public int saveTrnNpsRegFile(TrnNpsRegFileEntity trnNpsRegFile) {
		// TODO Auto-generated method stub
		Session currentSession = entityManager.unwrap(Session.class);
		
		Serializable saveId = currentSession.save(trnNpsRegFile);
		return (Integer) saveId;
	}

	@Override
	public String getPayScale(Long payScaleCode) {
		// TODO Auto-generated method stub
		Session currentSession = entityManager.unwrap(Session.class);
		List list = new ArrayList();
		String  rtnStr = null;
		StringBuffer query = new StringBuffer();
		query.append("select scale_desc from pay_scale_sixpc_mst  where pay_scale_code="+payScaleCode);
		Query hsqlQuery = currentSession.createSQLQuery(query.toString());
		list = hsqlQuery.list();
		if (list != null && list.size() >= 0)
			rtnStr = (String) list.get(0);
		return rtnStr;
	}

	@Override
	public TrnNpsRegFileEntity findTrnNpsFileEntityById(Integer id) {
		// TODO Auto-generated method stub
		return entityManager.find(TrnNpsRegFileEntity.class, id);
	}

	@Override
	public void updateTrnNpsFileEntity(TrnNpsRegFileEntity trnNpsRegFileEntity) {
		// TODO Auto-generated method stub
		Session currentSession = entityManager.unwrap(Session.class);
		currentSession.update(trnNpsRegFileEntity);
	}

	@Override
	public List<Object[]> findNpsIdByAckNo(String ackNo) {
		Session currentSession = entityManager.unwrap(Session.class);
		String hql = "select nps_id,id from trn_nps_reg_file where   ack_no='"+ackNo+"'"; //
		Query query = currentSession.createSQLQuery(hql);  
		return query.list();
	}

	@Override
	public MstEmployeeNPSEntity findEmployeeByNpsid(Integer npsId) {
		// TODO Auto-generated method stub
		return entityManager.find(MstEmployeeNPSEntity.class, npsId);
	}

	@Override
	public void updatePranNumberByNpsId(MstEmployeeNPSEntity mstEmployeeNPSEntity) {
		// TODO Auto-generated method stub
		Session currentSession = entityManager.unwrap(Session.class);
		currentSession.update(mstEmployeeNPSEntity);
	}

	@Override
	public void updateEmployeeByEmpId(MstEmployeeEntity mstEmployeeEntity) {
		// TODO Auto-generated method stub
		Session currentSession = entityManager.unwrap(Session.class);
		currentSession.update(mstEmployeeEntity);
	}

	@Override
	public MstEmployeeNPSModel findCSRFEmployeeBySevaarthId(Long empId) {

		// TypedQuery<MstEmployeeEntity> query = entityManager.createQuery( "SELECT e
		// FROM MstEmployeeEntity e JOIN e.mstNomineeDetailsEntity ph ",
		// MstEmployeeEntity.class);
		TypedQuery<MstEmployeeEntity> query = entityManager.createQuery(
				"SELECT a FROM MstEmployeeEntity a INNER JOIN a.mstNomineeDetailsEntity b  where a.employeeId=" + empId,
				MstEmployeeEntity.class);
		List<MstEmployeeEntity> resultList = query.getResultList();

	
		
		for(MstEmployeeEntity mstEmployeeEntity:resultList) {
			MstEmployeeNPSModel mstEmployeeNPSModel = new MstEmployeeNPSModel();
			mstEmployeeNPSModel.setEmployeeId(mstEmployeeEntity.getEmployeeId());
			mstEmployeeNPSModel.setDdoCode(mstEmployeeEntity.getDdoCode());
			mstEmployeeNPSModel.setPayCommissionCode(mstEmployeeEntity.getPayCommissionCode());
			mstEmployeeNPSModel.setBasicPay(mstEmployeeEntity.getBasicPay());
			mstEmployeeNPSModel.setSevenPcBasic(mstEmployeeEntity.getSevenPcBasic());
			mstEmployeeNPSModel.setEmployeeFirstName(mstEmployeeEntity.getEmployeeFNameEn());
			mstEmployeeNPSModel.setEmployeeFullName(mstEmployeeEntity.getEmployeeFullNameEn());
			mstEmployeeNPSModel.setEmployeeMiddleName(mstEmployeeEntity.getEmployeeMNameEn());
			mstEmployeeNPSModel.setEmployeeLastName(mstEmployeeEntity.getEmployeeLNameEn());
			mstEmployeeNPSModel.setDistrict(mstEmployeeEntity.getDistrict());
			mstEmployeeNPSModel.setState(mstEmployeeEntity.getState());
			mstEmployeeNPSModel.setEmpPermanentBuildingName(mstEmployeeEntity.getEmpPermanentBuildingName());
			mstEmployeeNPSModel.setEmpPermanentFlatUnitNo(mstEmployeeEntity.getEmpPermanentFlatUnitNo());
			mstEmployeeNPSModel.setEmpPermanentLocality(mstEmployeeEntity.getEmpPermanentLocality());
			mstEmployeeNPSModel.setEmpPermanentDistrict(mstEmployeeEntity.getEmpPermanentDistrict());
			mstEmployeeNPSModel.setEmpPermanentState(mstEmployeeEntity.getEmpPermanentState());
			mstEmployeeNPSModel.setEmpPermanentPinCode(mstEmployeeEntity.getEmpPermanentPinCode());
			mstEmployeeNPSModel.setEmployeeBankName(mstEmployeeEntity.getEmployeeBankName());
			mstEmployeeNPSModel.setEmployeeBankAccountNo(mstEmployeeEntity.getBankAcntNo().toString());
			mstEmployeeNPSModel.setEmployeeBankBranchName(mstEmployeeEntity.getEmployeeBankBranchName());
			mstEmployeeNPSModel.setEmployeeBankBankAddress(mstEmployeeEntity.getEmployeeBankBankAddress());
			mstEmployeeNPSModel.setEmployeeBankPinCode(mstEmployeeEntity.getEmployeeBankPinCode());
			mstEmployeeNPSModel.setIFSCCode(mstEmployeeEntity.getIfscCode());
			mstEmployeeNPSModel.setEmployeeFatherHubandName(mstEmployeeEntity.getEmployeeFatherHubandName());
			mstEmployeeNPSModel.setEmployeeBirthPlace(mstEmployeeEntity.getEmployeeBirthPlace());
			mstEmployeeNPSModel.setEmpNominee1GuardName(mstEmployeeEntity.getEmpNominee1GuardName());
			mstEmployeeNPSModel.setEmployeeDOB(mstEmployeeEntity.getDob());
			mstEmployeeNPSModel.setEmployeeSpouseName(mstEmployeeEntity.getEmployeeSpouseName());
			mstEmployeeNPSModel.setPanNo(mstEmployeeEntity.getPanNo());
			mstEmployeeNPSModel.setEmpMobileNo(mstEmployeeEntity.getMobileNo1().toString());
			mstEmployeeNPSModel.setEmpEmailId(mstEmployeeEntity.getEmailId());
			mstEmployeeNPSModel.setEmployeeMaritalStatus(mstEmployeeEntity.getMaritalStatus().toString());
			mstEmployeeNPSModel.setCountry(mstEmployeeEntity.getCountry()); 
			mstEmployeeNPSModel.setEmployeeGender(mstEmployeeEntity.getGender()); 
			mstEmployeeNPSModel.setEmployeeSpouseName(mstEmployeeEntity.getEmployeeSpouseName());
			mstEmployeeNPSModel.setEmployeeAadhar(mstEmployeeEntity.getUidNo());
			mstEmployeeNPSModel.setAddress1(mstEmployeeEntity.getAddress1());
			mstEmployeeNPSModel.setAddress2(mstEmployeeEntity.getAddress2());
			
			return mstEmployeeNPSModel;
		}
		
		return null;

	
	}

	@Override
	public String getStateName(String empState) {
		// TODO Auto-generated method stub.
		Session currentSession = entityManager.unwrap(Session.class);
	/*	Query<MstStateEntity> query = currentSession.createQuery(
				"FROM MstStateEntity as t where t.StateId=:empState", MstStateEntity.class);*/
		
		String HQL = "FROM CmnStateMst as t where t.stateId='" + empState + "' ";
		
		List<MstStateEntity> lstMstNomineeDetailsEntity = (List<MstStateEntity>) entityManager
				.createQuery(HQL).getResultList();
	     return 	lstMstNomineeDetailsEntity.get(0).getStateNameEn();
		
		
	
	}

	@Override
	public List<Object[]> viewCSRFPhotoSign(int empId) {
		// TODO Auto-generated method stub
		Session currentSession = entityManager.unwrap(Session.class);
		String hql = "select employee_photo_attachment,employee_sign_attachment from employee_nps_mst where   employee_id="+empId; //
		Query query = currentSession.createSQLQuery(hql);  
		return query.list();
		
	}


	@Override
	public Long getNextSeqNum(String seqName) {
		// TODO Auto-generated method stub
		Long value = Long
				.parseLong(entityManager.createNativeQuery("select nextval('" + seqName + "')").getSingleResult().toString());
		return value;
	}

	@Override
	public Integer getRegSeqNo(String strDate) {
		// TODO Auto-generated method stub
		Integer value = Integer.parseInt(entityManager
				.createNativeQuery(
						"SELECT count(*) FROM employee_nps_mst WHERE CAST(created_date AS DATE) ='" + strDate + "'")
				.getSingleResult().toString());
		return value;
	}
	
	@Override
	public String getDtoRegNumber(String ddoLevel2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getDdoRegNumber(String userName) {
		// TODO Auto-generated method stub

		Session currentSession = entityManager.unwrap(Session.class);
		List list = new ArrayList();
		String rtnStr = null;
		StringBuffer query = new StringBuffer();
		query.append("select ddo_reg_no from ddo_reg_mst  where   ddo_code  = '" + userName + "' limit 1");
		Query hsqlQuery = currentSession.createSQLQuery(query.toString());
		list = hsqlQuery.list();
		if (list != null && list.size() > 0)
			rtnStr = (String) list.get(0);
		return rtnStr;

	}

	
	@Override
	public NSDLBHDtlsEntity findBHEntityById(Integer bhId) {
		// TODO Auto-generated method stub
		return entityManager.find(NSDLBHDtlsEntity.class, bhId);
	}

	@Override
	public void updateBhEntity(NSDLBHDtlsEntity nSDLBHDtlsEntity) {
		// TODO Auto-generated method stub
		Session hibSession = entityManager.unwrap(Session.class);
		hibSession.update(nSDLBHDtlsEntity);
	}

	@Override
	public int gettrano(String userName) {
		Session currentSession = entityManager.unwrap(Session.class);
		List list = new ArrayList();
		int rtnStr = 0;
		StringBuffer query = new StringBuffer();
		query.append("select treasury_office_id from ddo_reg_mst where  ddo_code ='"+userName+"'");
		Query hsqlQuery = currentSession.createSQLQuery(query.toString());
		list = hsqlQuery.list();
		if (list != null && list.size() > 0)
			rtnStr = (int) list.get(0);
		return rtnStr;}

	@Override
	public Object saveOrUpdate(@Valid MstEmployeeEntity mstEmployeeEntity) {
		Session currentSession = entityManager.unwrap(Session.class);
		currentSession.saveOrUpdate(mstEmployeeEntity);
		return null;
	}


}
