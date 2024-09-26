package com.mahait.gov.in.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

@Repository
public class GPFAbstractReportRepoImpl implements GPFAbstractReportRepo {
	
	@PersistenceContext
	EntityManager entityManager;

	@Override
	public List<Object[]> findgpfRptDtls(Integer monthId, Integer yearId, Long billGroup) {

		// TODO Auto-generated method stub
		Session session = entityManager.unwrap(Session.class);
		String queryString = "select pfdescription,count(a.sevaarth_id),sum(b.D_PAY) as DP,sum(b.GPF_GRP_ABC+b.GPF_IAS+b.GPF_IAS_OTHER+b.GPF_IPS+b.GPF_IFS) as subs\r\n" + 
				", sum(b.GPF_ABC_Arr_Mr+b.GPF_IAS_Arr_Mr+b.GPF_IFS_Arr_Mr) as payDaArrMrg,0 as gpfArr,sum(b.GPF_ADV_GRP_ABC) as refund from EMPLOYEE_MST a \r\n" + 
				"inner join PAYBILL_GENERATION_TRN_DETAILS b on a.sevaarth_id=b.sevaarth_id\r\n" + 
				"inner join PAYBILL_GENERATION_TRN c on b.paybill_generation_trn_id=c.paybill_generation_trn_id\r\n" + 
				"where a.dcps_gpf_flag = 'N' and c.paybill_month = 1 and c.paybill_year = 25  and c.scheme_billgroup_id = '9940008435'  and c.is_active <>8\r\n" + 
				"group by a.pfdescription ";
		Query query = session.createSQLQuery(queryString);
		System.out.println("-------" + queryString);
		return query.list();
	
	}
	
	

}
