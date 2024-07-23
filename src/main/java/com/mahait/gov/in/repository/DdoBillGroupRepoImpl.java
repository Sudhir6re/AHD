package com.mahait.gov.in.repository;

import java.io.Serializable;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;
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
		String hql = "Select a.sevaarth_id,a.employee_full_name_en,b.designation_name, c.ddo_name,a.employee_id,a.pay_commission_code,d.commission_name_en,a.dcps_gpf_flag,a.emp_service_end_date,e.description  FROM employee_mst a,designation_mst b,org_ddo_mst c,\r\n"
				+ " pay_commission_mst d,mst_dcps_bill_group e where a.designation_code = b.designation_code and a.ddo_code = c.ddo_code and a.pay_commission_code=d.pay_commission_code and e.bill_group_id = a.billgroup_id and a.billgroup_id is not null  and a.is_active='1' and  a.ddo_code = '"
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
	@Override
	public List<Object[]> isPaybillIsInProcess(String sevaarthId) {
		Session currentSession = entityManager.unwrap(Session.class);
		String hql1 = " select pd.sevaarth_id, pg.is_active,pd.paybill_month,pd.paybill_year,pg.scheme_billgroup_id  from paybill_generation_trn pg inner join paybill_generation_trn_details pd  on pg.paybill_generation_trn_id =  pd.paybill_generation_trn_id where   pd.sevaarth_id='"+sevaarthId+"' and pg.is_active not in(14,8)";;
		Query query12 = currentSession.createSQLQuery(hql1);
		return (List<Object[]>) query12.list();
	}
	@Override
	public int deleteEmpMpgDdoAllowDeduc(String sevaarthId) {
		// TODO Auto-generated method stub
		Session currentSession = entityManager.unwrap(Session.class);
		String hql1 = "delete from employee_allowdeduc_mpg where sevaarth_id='"+sevaarthId+"'";
		Query query1 = currentSession.createSQLQuery(hql1);
		query1.executeUpdate();
		return 1;
	}
	@Override
	public int saveEmpMpgDdoAllowDeduc(Object allow_deduct_id, String department_id, String empId, String sevaarthId,
			String effectiveDate) {
		// TODO Auto-generated method stub
		try {
			
			Session currentSession = entityManager.unwrap(Session.class);
			 SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");
			 Date date1 = dt.parse(effectiveDate); // *** same for the format String below 
			 SimpleDateFormat dt1 = new SimpleDateFormat("yyyy-MM-dd"); 
				String hql = "insert into employee_allowdeduc_mpg (sevaarth_id,department_code,department_allowdeduc_code,is_active,created_date,employee_id,with_effective_date) values ('"+sevaarthId+"',"+department_id+","+allow_deduct_id+","+"1"+",current_timestamp,"+empId+",'"+dt1.format(date1)+"')";
				Query query = currentSession.createSQLQuery(hql);
				query.executeUpdate();
				
				hql = "insert into employee_allowdeduc_mpg_hst (sevaarth_id,department_code,department_allowdeduc_code,is_active,created_date,employee_id,with_effective_date) values ('"+sevaarthId+"',"+department_id+","+allow_deduct_id+","+"1"+",current_timestamp,"+empId+",'"+dt1.format(date1)+"')";
				query = currentSession.createSQLQuery(hql);
				query.executeUpdate();

			return  1;
			}catch(Exception e) {
				e.printStackTrace();
			}
			return  1;
	}
	@Override
	public List<Object[]> empEligibilityForAllowAndDeductCheckBoxId(String id) {
		// TODO Auto-generated method stub
		Session currentSession = entityManager.unwrap(Session.class);
		String hql =  "select e.department_allowdeduc_code,c.department_allowdeduc_name,e.sevaarth_id,e.with_effective_date from employee_mst d inner join employee_allowdeduc_mpg e on d.sevaarth_id = e.sevaarth_id  and e.is_active = '1' inner join department_allowdeduc_mst c on e.department_allowdeduc_code = c.department_allowdeduc_code  where  e.sevaarth_id =  '"+id+"'"; ///changed from c.department_allowdeduc_id to c.department_allowdeduc_code
		Query query = currentSession.createSQLQuery(hql);
		return query.list();
	}
	@Override
	public List<Object[]> findMpgSchemeBillGroupBySchemeBillGroupId1(int id) {
		// TODO Auto-generated method stub
		Session currentSession = entityManager.unwrap(Session.class);
		String hql =  "select b.sevaarth_id,b.employee_full_name_en,c.designation_name, a.sub_department_name_en ,d.department_name_en from sub_department_mst a inner join   employee_mst b on a.sub_department_id = b.field_department_id inner join  department_mst d on d.department_id = b.admin_department_id inner join designation_mst c on c.designation_id = b.designation_id  where b.is_active = '1' and d.is_active = '1' and a.is_active = '1' and d.department_id = "+id+"order by b.employee_full_name_en asc";
		Query query = currentSession.createSQLQuery(hql);
		return query.list();
	}
	@Override
	public List getBillgroupDtlsForAlreadySaved(String billGrpId) {
		// TODO Auto-generated method stub
		Session currentSession = entityManager.unwrap(Session.class);
		String hql = "SELECT r.DCPS_CLASS_GROUP FROM MST_DCPS_BILL_GROUP gr" + 
				" inner join RLT_DCPS_BILLGROUP_CLASSGROUP r on r.DCPS_BILLGROUP_ID = gr.BILL_GROUP_ID where gr.BILL_GROUP_ID =  '"+billGrpId+"'";
		Query query = currentSession.createSQLQuery(hql);
		return query.list();
	}
	@Override
	public List<Object[]> findattachpostlist(String userName, String billgrpId) {
		Session currentSession = entityManager.unwrap(Session.class);
		String hql = "select\r\n" + 
				"        a.post_name,\r\n" + 
				"        a.post_id,\r\n" + 
				"        f.employee_full_name_en,\r\n" + 
				"        c.designation_name,\r\n" + 
				"        e.PSR_NO,\r\n" + 
				"        i.DESCRIPTION,\r\n" + 
				"        b.post_type_lookup_id,\r\n" + 
				"        d.lookup_name , \r\n" + 
				"		case when b.post_type_lookup_id = 10001198129 then 'P' \r\n" + 
				"		 when b.post_type_lookup_id = 10001198130 then 'T'\r\n" + 
				"		else 'S' end \r\n" + 
				"    from\r\n" + 
				"        org_post_details_rlt a   \r\n" + 
				"    inner join\r\n" + 
				"        org_post_mst b \r\n" + 
				"            on a.post_id=b.post_id  \r\n" + 
				"    inner join\r\n" + 
				"        designation_mst c \r\n" + 
				"            on a.dsgn_id = c.designation_id  \r\n" + 
				"    inner join\r\n" + 
				"        cmn_lookup_mst d \r\n" + 
				"            on d.lookup_id=b.post_type_lookup_id   \r\n" + 
				"    inner join\r\n" + 
				"        HR_PAY_POST_PSR_MPG e \r\n" + 
				"            on e.POST_ID=b.post_id   \r\n" + 
				"    left join\r\n" + 
				"        employee_mst f \r\n" + 
				"            on f.post_detail_id=a.post_id   \r\n" + 
				"    inner join\r\n" + 
				"        org_ddo_mst g \r\n" + 
				"            on a.loc_id = cast(g.location_code as bigint)   \r\n" + 
				"    inner join\r\n" + 
				"        cmn_lookup_mst  h \r\n" + 
				"            on h.lookup_id=b.post_type_lookup_id    \r\n" + 
				"    left join\r\n" + 
				"        mst_dcps_bill_group  i \r\n" + 
				"            on i.BILL_GROUP_ID=e.bill_no     \r\n" + 
				"    where b.end_date > CURRENT_DATE and\r\n" + 
				"         i.BILL_GROUP_ID = '"+billgrpId+"' and  \r\n" + 
				"        a.loc_id in (\r\n" + 
				"            SELECT\r\n" + 
				"                cast(location_code as bigint) \r\n" + 
				"            FROM\r\n" + 
				"                org_ddo_mst  a \r\n" + 
				"            inner join\r\n" + 
				"                rlt_zp_ddo_map b \r\n" + 
				"                    on a.ddo_code=b.zp_ddo_code  \r\n" + 
				"            where\r\n" + 
				"                zp_ddo_code='"+userName+"'\r\n" + 
				"        ) \r\n" + 
				"    order by\r\n" + 
				"        a.CREATED_DATE desc ";
		Query query = currentSession.createSQLQuery(hql);
		return query.list();
	}
	@Override
	public List<Object[]> finddetachpostlist(String userName, String billgrpId) {
		Session currentSession = entityManager.unwrap(Session.class);
		String hql = "select\r\n" + 
				"        a.post_name,\r\n" + 
				"        a.post_id,\r\n" + 
				"        f.employee_full_name_en,\r\n" + 
				"        c.designation_name,\r\n" + 
				"        e.PSR_NO,\r\n" + 
				"        i.DESCRIPTION,\r\n" + 
				"        b.post_type_lookup_id,\r\n" + 
				"        d.lookup_name , \r\n" + 
				"		case when b.post_type_lookup_id = 10001198129 then 'P' \r\n" + 
				"		 when b.post_type_lookup_id = 10001198130 then 'T'\r\n" + 
				"		else 'S' end \r\n" + 
				"    from\r\n" + 
				"        org_post_details_rlt a   \r\n" + 
				"    inner join\r\n" + 
				"        org_post_mst b \r\n" + 
				"            on a.post_id=b.post_id  \r\n" + 
				"    inner join\r\n" + 
				"        designation_mst c \r\n" + 
				"            on a.dsgn_id = c.designation_id  \r\n" + 
				"    inner join\r\n" + 
				"        cmn_lookup_mst d \r\n" + 
				"            on d.lookup_id=b.post_type_lookup_id   \r\n" + 
				"    inner join\r\n" + 
				"        HR_PAY_POST_PSR_MPG e \r\n" + 
				"            on e.POST_ID=b.post_id   \r\n" + 
				"    left join\r\n" + 
				"        employee_mst f \r\n" + 
				"            on f.post_detail_id=a.post_id   \r\n" + 
				"    inner join\r\n" + 
				"        org_ddo_mst g \r\n" + 
				"            on a.loc_id = cast(g.location_code as bigint)   \r\n" + 
				"    inner join\r\n" + 
				"        cmn_lookup_mst  h \r\n" + 
				"            on h.lookup_id=b.post_type_lookup_id    \r\n" + 
				"    left join\r\n" + 
				"        mst_dcps_bill_group  i \r\n" + 
				"            on i.BILL_GROUP_ID=e.bill_no     \r\n" + 
				"    where b.end_date > CURRENT_DATE and  f.employee_full_name_en isnull and DESCRIPTION isnull and "+
				"        a.loc_id in (\r\n" + 
				"            SELECT\r\n" + 
				"                cast(location_code as bigint) \r\n" + 
				"            FROM\r\n" + 
				"                org_ddo_mst  a \r\n" + 
				"            inner join\r\n" + 
				"                rlt_zp_ddo_map b \r\n" + 
				"                    on a.ddo_code=b.zp_ddo_code  \r\n" + 
				"            where\r\n" + 
				"                zp_ddo_code='"+userName+"') \r\n" + 
				"    order by\r\n" + 
				"        a.CREATED_DATE desc ";
		Query query = currentSession.createSQLQuery(hql);
		return query.list();
	}
	@Override
	public String saveAttachDettachPostToBillGroup(String sevaarthId, int empid, Long schemebillGroupId,
			String status) {
		Session currentSession = entityManager.unwrap(Session.class);
		StringBuffer hql = new StringBuffer();

		if (status.equals("Detach")) {
			hql.append("update hr_pay_post_psr_mpg set billgroup_id = null");
		} else if (status.equals("Attach")) {
			hql.append("update hr_pay_post_psr_mpg set billgroup_id = " + schemebillGroupId);
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
	

	}

