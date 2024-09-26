package com.mahait.gov.in.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class GISReportRepoImpl implements  GISReportRepo{

	
	@PersistenceContext
	EntityManager entityManager;
	
	
	@Override
	public List<Object[]> findRptDtls(Integer monthId, Integer yearId, Long billGroup,
			Long gisAllowDeducCode) {

		Session currentSession = entityManager.unwrap(Session.class);
		StringBuffer hql = new StringBuffer();
		String componentName =null;
		
	String sumComponentClause = null;
		 
		hql.append(" select count (a.sevaarth_id),d.group_name_en,'premium_amount'");
		switch(gisAllowDeducCode.intValue()) {
		case 84:
			hql.append(" sum(gis_ias)");
			sumComponentClause ="sum(gis_ias)"; 
			componentName = "gis_ias";
			break;
		case 85:
			hql.append(" sum(gis_ifs)");
			sumComponentClause ="sum(gis_ifs)";
			componentName = "gis_ifs";
			break;
		case 87:
			hql.append(" sum(gis)");
			sumComponentClause ="sum(gis)";
			componentName = "gis";
			break;
		case 98:
			hql.append(" sum(central_gis)");
			sumComponentClause ="sum(central_gis)";
			componentName = "central_gis";
			break;
		case 104:
			hql.append(" sum(gis_ips)");
			sumComponentClause ="sum(gis_ips)";
			componentName = "gis_ips";
			break;
		}
		
		hql.append(" , case when d.group_name_en = 'BnGz' then 160 case when d.group_name_en = 'A' then 320 case when d.group_name_en = 'B' then 160 case when d.group_name_en = 'C' then 120 case when d.group_name_en = 'D' then 80 end as premiumRate");
		hql.append(" from EMPLOYEE_MST a inner join  ");
		hql.append(" PAYBILL_GENERATION_TRN_DETAILS b on a.sevaarth_id= b.sevaarth_id ");
		hql.append(" inner join PAYBILL_GENERATION_TRN c on b.paybill_generation_trn_id= c.paybill_generation_trn_id ");
		hql.append(" inner join cadre_group_mst d on d.id = a.emp_class where  c.is_active <> 8 and ");
		hql.append(" to_char(a.doj,'YY') = cast((c.paybill_year - 1) as varchar ) and c.paybill_month = "+monthId+" and c.paybill_year = "+yearId+" and c.scheme_billgroup_id = '"+billGroup+"' group by d.group_name_en, "+componentName);
		hql.append(" UNION ALL ");
		hql.append(" select count (a.sevaarth_id),d.group_name_en,'composite_amount'  from EMPLOYEE_MST a inner join  ");
		hql.append(sumComponentClause);
		hql.append(" , case when d.group_name_en = 'BnGz' then 480 case when d.group_name_en = 'A' then 960 case when d.group_name_en = 'B' then 480 case when d.group_name_en = 'C' then 360 case when d.group_name_en = 'D' then 240 end as premiumRate");
		hql.append(" PAYBILL_GENERATION_TRN_DETAILS b on a.sevaarth_id= b.sevaarth_id ");
		hql.append(" inner join PAYBILL_GENERATION_TRN c on b.paybill_generation_trn_id= c.paybill_generation_trn_id ");
		hql.append(" inner join cadre_group_mst d on d.id = a.emp_class where  c.is_active <> 8 and  ");
		hql.append(" to_char(a.doj,'YY') <> cast((c.paybill_year - 1) as varchar ) and c.paybill_month = "+monthId+" and c.paybill_year = "+yearId+" and c.scheme_billgroup_id = '"+billGroup+"'group by d.group_name_en, "+componentName);

		Query query = currentSession.createSQLQuery(hql.toString());
		return query.list();
	
	}

}
