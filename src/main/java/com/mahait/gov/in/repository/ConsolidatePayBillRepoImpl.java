package com.mahait.gov.in.repository;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.mahait.gov.in.entity.ConsolidatePayBillTrnEntity;
import com.mahait.gov.in.entity.ConsolidatePayBillTrnMpgEntity;

@Repository
public class ConsolidatePayBillRepoImpl implements ConsolidatePayBillRepo {
//	protected final Log logger = LogFactory.getLog(getClass());
	@PersistenceContext
	EntityManager entityManager;
	public int saveConsolidatePayBill(ConsolidatePayBillTrnEntity objEntity)
	{
		
		Session currentSession = entityManager.unwrap(Session.class);
		Serializable saveId = currentSession.save(objEntity);
		return (Integer) saveId;
	}
	/*@Override
	public int getConsolidatePayBillTrnId() {
		Session currentSession = entityManager.unwrap(Session.class);
		String hql = " SELECT coalesce(max(ch.ConsolidatePayBillTrnEntity), 0) FROM ConsolidatePayBillTrnEntity ch";
		Query query = currentSession.createQuery(hql);
		return (int) query.list().get(0);
	}*/
	@Override
	public int saveConsolidatePayBillTrnMpg(ConsolidatePayBillTrnMpgEntity objEntity) {
		Session currentSession = entityManager.unwrap(Session.class);
		Serializable saveId = currentSession.save(objEntity);
		return (Integer) saveId;
	}

	@Override
	public List<Object[]> addConsComponents(String ddoCode,List<Integer> payBillGenerationTransId) {
		Session currentSession = entityManager.unwrap(Session.class);

		String HQL="  select SUM(COALESCE(it,0)+ COALESCE(income_tax,0)) as IT,sum(dcps_arr)as DCPS_ARR,  sum(gis)as GIS,sum(pt)as PT,sum(group_acc_policy)as GROUP_ACC_POLICY,sum(hrr)as HRR, Sum(total_deduction)as TotalDeduct, SUM(COALESCE(gpf_grp_abc,0) + COALESCE(GPF_ADV_GRP_ABC,0)+COALESCE(GPF_ABC_ARR,0)+COALESCE(gpf_grp_d,0)+COALESCE(GPF_ADV_GRP_D,0)+COALESCE(GPF_D_ARR,0))as prov_fund,sum(COALESCE(dcps_da_arr,0)+COALESCE(dcps_delay,0)+COALESCE(dcps_employer,0)+COALESCE(dcps_pay_arr,0)+COALESCE(dcps_regular_recovery,0))as dcps\r\n" + 
				" from paybill_generation_trn_details  where paybill_generation_trn_id in :payBillGenerationTransId ";
		Query query = currentSession.createSQLQuery(HQL);
		query.setParameter("payBillGenerationTransId", payBillGenerationTransId);
		
		System.out.println("raw query"+query.getQueryString());
		return query.list();
		}
	@Override
	public List<Object[]> fetchDDOLst(String ddoCode) {
		Session currentSession = entityManager.unwrap(Session.class);

		String HQL=" select c.ddo_code,a.off_name from mst_dcps_ddo_office a inner join rlt_zp_ddo_map b on a.ddo_code=b.zp_ddo_code\r\n" + 
				"inner join paybill_generation_trn c on a.ddo_code=c.ddo_code\r\n" + 
				"where rept_ddo_code =:ddoCode and c.is_active = '6'";
		
		Query query = currentSession.createSQLQuery(HQL);
		query.setParameter("ddoCode", ddoCode);
		
		System.out.println("raw query"+query.getQueryString());
		return query.list();
		}
	
}
