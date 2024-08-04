package com.mahait.gov.in.repository;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.mahait.gov.in.entity.ConsolidatePayBillTrnEntity;

@Repository
public class ViewConsolidatePayBillRepoImpl implements ViewConsolidatePayBillRepo {
//	protected final Log logger = LogFactory.getLog(getClass());
	@PersistenceContext
	EntityManager entityManager;
		
	/*@Autowired
	SessionFactory sessionFactory;
	*/
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> findAllConsolidatedPaybillList(int monthName,int yearName,String schemeCodeArr,int afterSaveId) {
		
		
		/*select * from consolidate_paybill_trn a 
		inner join consolidate_paybill_trn_mpg b on a.consolidate_paybill_trn_id=b.consolidate_paybill_trn_id
		inner join paybill_generation_trn c on c.paybill_generation_trn_id=b.paybill_generation_trn_id
		inner join rlt_zp_ddo_map d on a.ddo_code=d.rept_ddo_code
		inner join mst_dcps_bill_group e on e.bill_group_id=c.scheme_billgroup_id*/
		
		Session currentSession = entityManager.unwrap(Session.class);
		String HQL = "select \r\n" + 
				"  cpt.consolidate_paybill_trn_id, \r\n" + 
				"  dd.scheme_code, \r\n" + 
				"  dd.scheme_name, \r\n" + 
				"  sum (a.bill_gross_amt) as bill_gross_amt, \r\n" + 
				"  sum (a.bill_net_amount) as bill_net_amount, \r\n" + 
				"  cpt.is_active \r\n" + 
				"from \r\n" + 
				"  consolidate_paybill_trn cpt \r\n" + 
				"  inner join consolidate_paybill_trn_mpg cptm on cpt.consolidate_paybill_trn_id = cptm.consolidate_paybill_trn_id \r\n" + 
				"  inner join paybill_generation_trn a on cptm.paybill_generation_trn_id = a.paybill_generation_trn_id \r\n" + 
				"  inner join scheme_billgroup_mpg b on a.scheme_billgroup_id = b.bill_group_id \r\n" + 
				"  inner join ddo_map_rlt c on b.ddo_map_id = c.ddo_map_id \r\n" + 
				"  inner join scheme_mst dd on dd.scheme_id = b.scheme_id \r\n" + 
				"  inner join bill_group_mst ddd on b.bill_group_id = ddd.bill_group_id \r\n" + 
				"  inner join ddo_reg_mst cccc on a.ddo_code = cccc.ddo_code \r\n" + 
				"  and cccc.ddo_reg_id = ddo_code_user_id1 \r\n" + 
				"where \r\n" + 
				"  a.ddo_code IN (\r\n" + 
				"    select \r\n" + 
				"      aa.ddo_code \r\n" + 
				"    from \r\n" + 
				"      ddo_reg_mst a \r\n" + 
				"      inner join ddo_map_rlt b on a.ddo_reg_id = b.ddo_code_user_id2 \r\n" + 
				"      inner join ddo_reg_mst aa on aa.ddo_reg_id = b.ddo_code_user_id1 \r\n" + 
				"    where \r\n" + 
				"      a.level_hierarchy = '2' \r\n" + 
				"      and a.ddo_code = '"+afterSaveId+"'\r\n" + 
				"  ) \r\n" + 
				"  and a.is_active in (9, 10, 11, 12, 14) \r\n" + 
				"  and cpt.is_active not in (13) \r\n" + 
				//"  and dd.scheme_code = '20490109' \r\n" + 
				"  and cpt.paybill_year ="+yearName+" \r\n" + 
				"  and cpt.paybill_month ="+monthName+" \r\n" + 
				"group by \r\n" + 
				"  cpt.consolidate_paybill_trn_id, \r\n" + 
				"  dd.scheme_code, \r\n" + 
				"  dd.scheme_name, \r\n" + 
				"  cpt.is_active \r\n" + 
				"order by \r\n" + 
				"  cpt.consolidate_paybill_trn_id desc\r\n" + 
				"";
//		logger.info("squery   "+HQL);
		Query query = currentSession.createSQLQuery(HQL);
		
		/*String HQL11 = "FROM MpgSchemeBillGroupEntity as t where t.isActive = '1' and t.ddoMapId = "+query.uniqueResult()+" ORDER BY t.billDescription ";*/
		return query.list();
		
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> findAllConsolidatedPaybillListUsingFilter(int monthName,int yearName,String schemeCodeArr,int afterSaveId,String ddoName) {
		
		Session currentSession = entityManager.unwrap(Session.class);
		String HQL = "select \r\n" + 
				"  cpt.consolidate_paybill_trn_id, \r\n" + 
				"  dd.scheme_code, \r\n" + 
				"  dd.scheme_name, \r\n" + 
				"  sum (a.bill_gross_amt) as bill_gross_amt, \r\n" + 
				"  sum (a.bill_net_amount) as bill_net_amount, \r\n" + 
				"  cpt.is_active \r\n" + 
				"from \r\n" + 
				"  consolidate_paybill_trn cpt \r\n" + 
				"  inner join consolidate_paybill_trn_mpg cptm on cpt.consolidate_paybill_trn_id = cptm.consolidate_paybill_trn_id \r\n" + 
				"  inner join paybill_generation_trn a on cptm.paybill_generation_trn_id = a.paybill_generation_trn_id \r\n" + 
				"  inner join scheme_billgroup_mpg b on a.scheme_billgroup_id = b.bill_group_id \r\n" + 
				"  inner join ddo_map_rlt c on b.ddo_map_id = c.ddo_map_id \r\n" + 
				"  inner join scheme_mst dd on dd.scheme_id = b.scheme_id \r\n" + 
				"  inner join bill_group_mst ddd on b.bill_group_id = ddd.bill_group_id \r\n" + 
				"  inner join ddo_reg_mst cccc on a.ddo_code = cccc.ddo_code \r\n" + 
				"  and cccc.ddo_reg_id = ddo_code_user_id1 \r\n" + 
				"where \r\n" + 
				"  a.ddo_code IN (\r\n" + 
				"    select \r\n" + 
				"      aa.ddo_code \r\n" + 
				"    from \r\n" + 
				"      ddo_reg_mst a \r\n" + 
				"      inner join ddo_map_rlt b on a.ddo_reg_id = b.ddo_code_user_id2 \r\n" + 
				"      inner join ddo_reg_mst aa on aa.ddo_reg_id = b.ddo_code_user_id1 \r\n" + 
				"    where \r\n" + 
				"      a.level_hierarchy = '2' \r\n" + 
				"      and a.ddo_code = '"+ddoName+"'\r\n" + 
				"  ) \r\n" + 
				"  and a.is_active in (9, 10, 11, 12,14) \r\n" + 
				"  and cpt.is_active not in (13) \r\n" + 
				"  and dd.scheme_code = '"+schemeCodeArr+"' \r\n" + 
				"  and cpt.paybill_year ="+yearName+" \r\n" + 
				"  and cpt.paybill_month ="+monthName+" \r\n" + 
				"group by \r\n" + 
				"  cpt.consolidate_paybill_trn_id, \r\n" + 
				"  dd.scheme_code, \r\n" + 
				"  dd.scheme_name, \r\n" + 
				"  cpt.is_active \r\n" + 
				"order by \r\n" + 
				"  cpt.consolidate_paybill_trn_id desc\r\n" + 
				"";
//		logger.info("squery   "+HQL);
		Query query = currentSession.createSQLQuery(HQL);
		
		/*String HQL11 = "FROM MpgSchemeBillGroupEntity as t where t.isActive = '1' and t.ddoMapId = "+query.uniqueResult()+" ORDER BY t.billDescription ";*/
		return query.list();
		
	}
	//Searching Consolidate paybill without filter//
	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> findAllConsolidatedPaybillListWithoutFilter(int monthName,int yearName,int afterSaveId,String ddoName) {
		
		Session currentSession = entityManager.unwrap(Session.class);
		String HQL = "select \r\n" + 
				"  cpt.consolidate_paybill_trn_id, \r\n" + 
				"  dd.scheme_code, \r\n" + 
				"  dd.scheme_name, \r\n" + 
				"  sum (a.bill_gross_amt) as bill_gross_amt, \r\n" + 
				"  sum (a.bill_net_amount) as bill_net_amount, \r\n" + 
				"  cpt.is_active \r\n" + 
				"from \r\n" + 
				"  consolidate_paybill_trn cpt \r\n" + 
				"  inner join consolidate_paybill_trn_mpg cptm on cpt.consolidate_paybill_trn_id = cptm.consolidate_paybill_trn_id \r\n" + 
				"  inner join paybill_generation_trn a on cptm.paybill_generation_trn_id = a.paybill_generation_trn_id \r\n" + 
				"  inner join scheme_billgroup_mpg b on a.scheme_billgroup_id = b.bill_group_id \r\n" + 
				"  inner join ddo_map_rlt c on b.ddo_map_id = c.ddo_map_id \r\n" + 
				"  inner join scheme_mst dd on dd.scheme_id = b.scheme_id \r\n" + 
				"  inner join bill_group_mst ddd on b.bill_group_id = ddd.bill_group_id \r\n" + 
				"  inner join ddo_reg_mst cccc on a.ddo_code = cccc.ddo_code \r\n" + 
				"  and cccc.ddo_reg_id = ddo_code_user_id1 \r\n" + 
				"where \r\n" + 
				"  a.ddo_code IN (\r\n" + 
				"    select \r\n" + 
				"      aa.ddo_code \r\n" + 
				"    from \r\n" + 
				"      ddo_reg_mst a \r\n" + 
				"      inner join ddo_map_rlt b on a.ddo_reg_id = b.ddo_code_user_id2 \r\n" + 
				"      inner join ddo_reg_mst aa on aa.ddo_reg_id = b.ddo_code_user_id1 \r\n" + 
				"    where \r\n" + 
				"      a.level_hierarchy = '2' \r\n" + 
				"      and a.ddo_code = '"+ddoName+"'\r\n" + 
				"  ) \r\n" + 
				"  and a.is_active in (9, 10, 11, 12,14) \r\n" + 
				"  and cpt.is_active not in (13) \r\n" + 
				"  and cpt.paybill_year ="+yearName+" \r\n" + 
				"  and cpt.paybill_month ="+monthName+" \r\n" + 
				"group by \r\n" + 
				"  cpt.consolidate_paybill_trn_id, \r\n" + 
				"  dd.scheme_code, \r\n" + 
				"  dd.scheme_name, \r\n" + 
				"  cpt.is_active \r\n" + 
				"order by \r\n" + 
				"  cpt.consolidate_paybill_trn_id desc\r\n" + 
				"";
		
	System.out.println("squery   "+HQL);
		Query query = currentSession.createSQLQuery(HQL);
		return query.list();
		
	}
	//for delete consolidate paybill
	@Override
	public ConsolidatePayBillTrnEntity updateConsolidateStatusById(int consPaybillGenerationTrnId) {
		ConsolidatePayBillTrnEntity objCadre = null;
		Session currentSession = entityManager.unwrap(Session.class);
		objCadre = currentSession.get(ConsolidatePayBillTrnEntity.class, consPaybillGenerationTrnId);
		return objCadre;
	}
	
	@Override
	public void updateConsolidateStatus(ConsolidatePayBillTrnEntity consolidatePayBillTrnEntity) {
		// TODO Auto-generated method stub
		Session currentSession = entityManager.unwrap(Session.class);
		currentSession.update(consolidatePayBillTrnEntity);
		
	}
	/*@Override
	public void updatePaybillStatus(int lstConsolidatedBillList) {
		// TODO Auto-generated method stub
		Session currentSession = entityManager.unwrap(Session.class);
		String HQL ="update paybill_generation_trn set is_active = 15 where paybill_generation_trn_id = "+lstConsolidatedBillList+"";
		logger.info("updatePaybillStatus"+HQL);
		currentSession.update(lstConsolidatedBillList);
		
	}*/
	
	
	
	
	
	public int updatePaybillStatus(int lstConsolidatedBillList) {
		Session currentSession = entityManager.unwrap(Session.class);
		String hql1  ="update paybill_generation_trn set is_active = 5 where paybill_generation_trn_id in(select paybill_generation_trn_id from consolidate_paybill_trn_mpg where consolidate_paybill_trn_id ="+lstConsolidatedBillList+") ";
		Query query12 = currentSession.createSQLQuery(hql1);
//		logger.info(hql1);
		query12.executeUpdate();
		return query12.executeUpdate();
	}
	
	
	
	@Override
	public Integer findConsolidatedPaybillNumber(int consPaybillGenerationTrnId) {
		 
    		Session currentSession = entityManager.unwrap(Session.class);
    		List list = new ArrayList();
    		Integer rtnStr = null;
    		StringBuffer query = new StringBuffer();
    		query.append("select pd.paybill_generation_trn_id  from consolidate_paybill_trn cp inner join consolidate_paybill_trn_mpg cpm on cp.consolidate_paybill_trn_id = cpm.consolidate_paybill_trn_id inner join paybill_generation_trn pd on cpm.paybill_generation_trn_id = pd.paybill_generation_trn_id where  cp.consolidate_paybill_trn_id in ("+consPaybillGenerationTrnId+") ");
    		Query hsqlQuery = currentSession.createSQLQuery(query.toString());
//    		logger.info("findConsolidatedPaybillNumber"+hsqlQuery);
    		list = hsqlQuery.list();

    		if (list != null && list.size() > 0)
    			rtnStr = (Integer) list.get(0);
    		return rtnStr;
    	
            
            
            
	}
}
