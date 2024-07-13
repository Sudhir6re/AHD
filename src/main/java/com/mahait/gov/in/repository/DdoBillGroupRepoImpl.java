package com.mahait.gov.in.repository;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.hibernate.type.LongType;
import org.springframework.stereotype.Repository;

import com.mahait.gov.in.entity.MstDcpsBillGroup;
import com.mahait.gov.in.entity.MstEmployeeEntity;
import com.mahait.gov.in.entity.MstScheme;
import com.mahait.gov.in.entity.RltBillgroupClassgroup;

@Repository
public class DdoBillGroupRepoImpl implements DdoBillGroupRepo {
	
	@PersistenceContext
	EntityManager entityManager;
	public List<MstDcpsBillGroup> lstBillName(String username) {
		// TODO Auto-generated method stub
		String ddocode = username.substring(0,11);
		String HQL = "FROM MstDcpsBillGroup as t where dcpsDdoCode ='"+ddocode+"' and (billDeleted is null or billDeleted <> 'Y') and (billDcps is null or billDcps <> 'Y') ORDER BY t.dcpsDdoBillGroupId DESC";
		return (List<MstDcpsBillGroup>) entityManager.createQuery(HQL).getResultList();
	}
	@Override
	public long saveBillGroupMaintainance(MstDcpsBillGroup mstDcpsBillGroup) {
		// TODO Auto-generated method stub
		
		Session currentSession = entityManager.unwrap(Session.class);
		if(mstDcpsBillGroup.getDcpsDdoBillGroupId()!=null) {
		 currentSession.update(mstDcpsBillGroup);
		 return mstDcpsBillGroup.getDcpsDdoBillGroupId();
		}else {
			return (long) currentSession.save(mstDcpsBillGroup);
		}
	}
	@Override
	public List<MstScheme> getSchemeCodeAgainstName(String schemeId) {
		// TODO Auto-generated method stub
		String HQL = "FROM MstScheme as t where t.schemeCode = '"+schemeId+"'  ORDER BY t.schemeId DESC";
		return (List<MstScheme>) entityManager.createQuery(HQL).getResultList();
	}
	@Override
	public List<Object[]> findAllEmployeesByDDOName(String ddoCode) {
		// TODO Auto-generated method stub
		Session currentSession = entityManager.unwrap(Session.class);
		// String hql = "Select a.ddo_code,a.sevaarth_id,a.employee_full_name_en FROM
		// employee_mst a where a.ddo_code = '"+ ddoCode +"'";
		String hql = "Select a.sevaarth_id,a.employee_full_name_en,b.designation_name,c.department_name_en,a.employee_id,a.pay_commission_code,d.commission_name_en,a.dcps_gpf_flag,a.emp_service_end_date,e.bill_description  FROM employee_mst a,designation_mst b,department_mst c,\r\n"
				+ " pay_commission_mst d,bill_group_mst e where a.designation_code = b.designation_code and a.admin_department_code = c.department_code and a.pay_commission_code=d.pay_commission_code and e.bill_group_id = a.billgroup_id and a.billgroup_id is not null  and a.is_active='1' and  a.ddo_code = '"
				+ ddoCode + "'  order by a.employee_full_name_en"; // and emp_service_end_date > now()
		Query query = currentSession.createSQLQuery(hql);
		return query.list();
	}
	@Override
	public List<MstEmployeeEntity> firstgetfindAllEmployeeByddoCode(String ddocode) {
		// TODO Auto-generated method stub
		String HQL = "FROM MstEmployeeEntity as  t  where ddoCode = '" + ddocode
				+ "' and isActive='1' and billgroup_id is null  ORDER BY t.employeeFullNameEn";
		return (List<MstEmployeeEntity>) entityManager.createQuery(HQL).getResultList();
	}
	@Override
	public List<Object[]> findAttachedEmployee(String ddocode, String scmebillgroupid) {
		Session currentSession = entityManager.unwrap(Session.class);
		String hql = "select distinct a.employee_id,a.sevaarth_id,a.employee_full_name_en,a.billgroup_id,c.designation_name, "
				+ "   b.description  from employee_mst a inner join mst_dcps_bill_group b on a.billgroup_id =b.bill_group_id "
				+ "inner join designation_mst c "
				+ "  on a.designation_code = c.designation_code  where  a.ddo_code = '" + ddocode
				+ "'  and a.is_active='1' and a.billgroup_id = " + scmebillgroupid;
		Query query = currentSession.createSQLQuery(hql);
		System.out.println("-----" + hql);
		return query.list();
	}
	@Override
	public List<Object[]> findDettachEmployee(String ddocode, String scmebillgroupid) {
		// TODO Auto-generated method stub
		Session currentSession = entityManager.unwrap(Session.class);
		String hql = "select a.employee_id,a.sevaarth_id,a.employee_full_name_en,a.billgroup_id,c.designation_name from employee_mst a\r\n"
				+ "inner join designation_mst c on a.designation_code = c.designation_code\r\n"
				+ " where a.billgroup_id is  null and a.ddo_code = '" + ddocode + "' and a.is_active='1' ";
		Query query = currentSession.createSQLQuery(hql);
		return query.list();
	}
	
	@Override
	public List<Object[]> getBillDtlsForAlreadySaved(String billGrpId) {

		Session currentSession = entityManager.unwrap(Session.class);
		String hql = "select a.description,a.sub_bg_or_not,a.type_of_post,a.scheme_code,b.scheme_name,a.bill_GROUP_ID from mst_dcps_bill_group a \r\n" + 
				"inner join mst_scheme b on a.scheme_code = b.scheme_code where a.bill_group_id =  '"+billGrpId+"'";
		Query query = currentSession.createSQLQuery(hql);
		return query.list();
	}

	@Override
	public void deleteClassGroupsForGivenBGId(Long billGroupId) {
		// TODO Auto-generated method stub
		Session currentSession = entityManager.unwrap(Session.class);
		
		String hql = "Delete from rlt_dcps_billgroup_classgroup where DCPS_BILLGROUP_ID= '"+billGroupId+"'";
		Query query = currentSession.createSQLQuery(hql);;
		query.executeUpdate();
	}
	@Override
	public long saveDcpsBillGroupMpg(RltBillgroupClassgroup rltBillgroupClassgroup) {
		// TODO Auto-generated method stub
		Session currentSession = entityManager.unwrap(Session.class);
		Serializable saveId = currentSession.save(rltBillgroupClassgroup);
		return (Long) saveId;
	}
	@Override
	public MstDcpsBillGroup findDcpsBillGroupById(Long billGroupId) {
		// TODO Auto-generated method stub
		Session currentSession = entityManager.unwrap(Session.class);
		return currentSession.find(MstDcpsBillGroup.class, billGroupId);
	}
	@Override
	public int checkGroupExistsOrNotForBG(Long long1) {
		// TODO Auto-generated method stub
		Session currentSession = entityManager.unwrap(Session.class);
		String HQL = "select cast(count(*) as int) from rlt_dcps_billgroup_classgroup where DCPS_BILLGROUP_ID = '"+long1+"'";
		Query query = currentSession.createSQLQuery(HQL);
		int result = (int) query.list().get(0);
		return result;
	}


	@Override
	public String saveAttachDettachEmployeeBillGroup(String sevaarthId, int empid, Long billGroupId, String status) {
		// TODO Auto-generated method stub
		Session currentSession = entityManager.unwrap(Session.class);
		StringBuffer hql = new StringBuffer();

		if (status.equals("Detach")) {
			hql.append("update employee_mst set billgroup_id = null");
		} else if (status.equals("Attach")) {
			hql.append("update employee_mst set billgroup_id = " + billGroupId);
		}
		// hql.append(" where sevaarth_id = '" + sevaarthId+ "' and
		// employee_id="+empid);
		hql.append(" where  employee_id=" + empid);
		// String hql = "update employee_mst set billgroup_id = " + billGroupId + "
		// where sevaarth_id = '" + sevaarthId
		// + "'";
		Query query = currentSession.createSQLQuery(hql.toString());
		long result = query.executeUpdate();
		return "save";
	}
	@Override
	public MstDcpsBillGroup findMpgSchemeBillGroupBySchemeBillGroupId(Long valueOf) {
		// TODO Auto-generated method stub
		MstDcpsBillGroup mpgSchemeBillGroupEntity = null;
		Session currentSession = entityManager.unwrap(Session.class);
		mpgSchemeBillGroupEntity = currentSession.get(MstDcpsBillGroup.class, valueOf);
		return mpgSchemeBillGroupEntity;
	}

	}

