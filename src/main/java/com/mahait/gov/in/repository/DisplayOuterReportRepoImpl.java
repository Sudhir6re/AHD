package com.mahait.gov.in.repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

//import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.hibernate.transform.AliasToEntityMapResultTransformer;
import org.springframework.stereotype.Repository;

import com.mahait.gov.in.common.StringHelperUtils;
import com.mahait.gov.in.model.DisplayOuterReportModel;

@Repository
public class DisplayOuterReportRepoImpl implements DisplayOuterReportRepo{
	@PersistenceContext
	EntityManager manager;
	public String getOffice(String ddoCode) {
		Session currentSession = manager.unwrap(Session.class);
		List list = new ArrayList();
		String rtnStr = null;
		StringBuffer query = new StringBuffer();
		
		String split[]=ddoCode.split("_AST");
		ddoCode=split[0];
		
		query.append("select off_name from mst_dcps_ddo_office where   ddo_code  ='"+ddoCode+"' ");
		Query hsqlQuery = currentSession.createSQLQuery(query.toString());
		list = hsqlQuery.list();

		if (list != null && list.size() > 0)
			rtnStr = list.get(0).toString();
		return rtnStr;
	}
	
	public List getReportHeaderDetails(String bill_no) {
		List list = new ArrayList();

		Session hibSession = manager.unwrap(Session.class);

		StringBuffer query = new StringBuffer();
		
		query.append("select drm.ddo_code ddocode,sm.scheme_name schemename,sm.scheme_code schemecode,pgt.bill_gross_amt grossamount,");
		query.append("pgt.paybill_month paymonth ,pgt.bill_creation_date billcreationdate , ");
		query.append("sm.demand_code demandcode,sm.major_head majorhead,sm.sub_major_head submajorhead,sm.minor_head minorhead,sm.sub_minor_head subminorhead,sm.sub_head subhead");
		query.append(",trsy.treasury_office_code teasurycode,trsy.treasury_office_name teasuryname, pgt.bill_net_amount netamount,pgt.paybill_year,d.designation_name ");
        query.append("from paybill_generation_trn pgt ");
		query.append("inner join scheme_billgroup_mpg sbm on pgt.scheme_billgroup_id = sbm.bill_group_id ");
		query.append("inner join ddo_map_rlt dmr on sbm.ddo_map_id = dmr.ddo_map_id ");
		query.append("inner join ddo_reg_mst drm  on drm.ddo_reg_id = dmr.ddo_code_user_id1 "); 
		query.append("inner join scheme_mst sm on sm.scheme_id = sbm.scheme_id ");
		query.append("inner join bill_group_mst bgm on sbm.bill_group_id = bgm.bill_group_id ");
		query.append("inner join YEAR_MST ym on ym.year_id=pgt.paybill_year ");
		query.append("inner join treasury_office_mst trsy on drm.district_code=trsy.city_code left outer join designation_mst d on d.designation_code=drm.designation_code ");
		query.append("where pgt.paybill_generation_trn_id = "+bill_no);
		query.append(" order by pgt.paybill_generation_trn_id ");
		Query sqlQuery = hibSession.createSQLQuery(query.toString());
		list = sqlQuery.list();

		return list;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public int getTotalDeduction(double billno) {
		
		Session currentSession = manager.unwrap(Session.class);
		String HQL = "select sum(total_deduction) from paybill_generation_trn_details where paybill_generation_trn_id = "+billno;
		Query query = currentSession.createSQLQuery(HQL);
		List<BigDecimal> lstprop = query.list();
		int totalded=0;
		if (!lstprop.isEmpty()) {
            for (BigDecimal objLst : lstprop) {
            	totalded=(int)objLst.intValue();
            }
		}
            return totalded ;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<DisplayOuterReportModel> findBillDescription(String ddoCode,int month,int year) {
		
		Session currentSession = manager.unwrap(Session.class);
		String HQL = "select a.paybill_generation_trn_id,ddd.bill_description,a.paybill_month,a.paybill_year from paybill_generation_trn a inner join \r\n" + 
				"scheme_billgroup_mpg b on a.scheme_billgroup_id = b.bill_group_id \r\n" + 
				"inner join ddo_map_rlt c on b.ddo_map_id = c.ddo_map_id \r\n" + 
				"inner join scheme_mst dd on dd.scheme_id = b.scheme_id \r\n" + 
				"inner join bill_group_mst ddd on b.bill_group_id = ddd.bill_group_id inner join ddo_reg_mst cccc on a.ddo_code = cccc.ddo_code and cccc.ddo_reg_id = c.ddo_code_user_id1  where a.ddo_code = '"+ddoCode+"' and a.is_active <>'8'and a.paybill_month = "+month+" and a.paybill_year = "+year+"  order by paybill_generation_trn_id desc";
		Query query = currentSession.createSQLQuery(HQL);
		List<Object[]> lstprop = query.list();
		List<DisplayOuterReportModel> lstObj = new ArrayList<>();
        if (!lstprop.isEmpty()) {
            for (Object[] objLst : lstprop) {
            	DisplayOuterReportModel obj = new DisplayOuterReportModel();
                obj.setPaybillgenerationtrnid(StringHelperUtils.isNullInt(objLst[0]));
                obj.setBilldescription(StringHelperUtils.isNullString(objLst[1]));
                lstObj.add(obj);
            }
        }
		
		return lstObj;
		
	}
	
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getAllDataForOuternew(String ddocode,Long billNumber)
	{
		Session currentSession = manager.unwrap(Session.class);
		/*String HQL="select distinct COALESCE(d.department_allowdeduc_col_nm, d.department_allowdeduc_name) allded, d.is_type,d.department_allowdeduc_id,d.head_account_code,department_allowdeduc_seqno from  "
		+ " employee_mst a inner join employee_allowdeduc_mpg b ON b.sevaarth_id=a.sevaarth_id inner join paybill_generation_trn_details c ON c.sevaarth_id=a.sevaarth_id  inner join department_allowdeduc_mst d ON  "
		+ " b.department_allowdeduc_code=d.department_allowdeduc_code where a.ddo_code= '"+ddocode+"' and paybill_generation_trn_id  = "+billNumber+" and b.is_active = '1' and  d.department_allowdeduc_col_nm <> 'LIC' "
				+ " and  d.department_allowdeduc_col_nm <> 'COP_BANK' and  d.department_allowdeduc_col_nm <> 'COP_Bank' and  d.department_allowdeduc_col_nm <> 'CREDIT_SOC' and  d.department_allowdeduc_col_nm <> 'RECURRING_DEP' and  d.department_allowdeduc_col_nm <> 'RD'  order by department_allowdeduc_seqno ";*/
		String HQL="select distinct COALESCE(d.department_allowdeduc_col_nm, d.department_allowdeduc_name) allded, d.is_type,d.department_allowdeduc_id,d.head_account_code,department_allowdeduc_seqno from  "
				+ " employee_mst a inner join employee_allowdeduc_mpg b ON b.sevaarth_id=a.sevaarth_id inner join paybill_generation_trn_details c ON c.sevaarth_id=a.sevaarth_id  inner join department_allowdeduc_mst d ON  "
				+ " b.department_allowdeduc_code=d.department_allowdeduc_code where a.ddo_code= '"+ddocode+"' and paybill_generation_trn_id  = '"+billNumber+"' and b.is_active = '1'   order by department_allowdeduc_seqno ";
		Query query = currentSession.createSQLQuery(HQL);
		return query.list();
	}
	
	@Override
	public List<Map<String,Object>> getempDetails(String bill_no)
	{
		Session currentSession = manager.unwrap(Session.class);
		
		String HQL = "select distinct c.sevaarth_id sevaarthid,c.employee_full_name_en,c.employee_l_name_en,d.designation_name,b.* from paybill_generation_trn a\r\n" + 
				"inner join paybill_generation_trn_details b on a.paybill_generation_trn_id = b.paybill_generation_trn_id\r\n" + 
				"inner join employee_mst c on b.sevaarth_id = c.sevaarth_id\r\n" + 
				"inner join designation_mst d on d.designation_code = c.designation_code\r\n" + 
				"where a.paybill_generation_trn_id ="+bill_no;
		
		 org.hibernate.Query hibernateQuery =  currentSession.createNativeQuery(HQL);
	      hibernateQuery.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
	        List<Map<String,Object>> resvalue = hibernateQuery.list();
		return resvalue;
	}

	@Override
	public List<Object[]> getempDetails(int monthName, int yearName, String ddocode, String billnumber) {

		Session currentSession = manager.unwrap(Session.class);
		String HQL="select * from paybill_generation_trn  where bill_gross_amt  >0 and ddo_code ='"+ddocode+"' and paybill_year  ="+yearName+" and paybill_month  ="+monthName+" and is_active <>'8' and scheme_billgroup_id="+billnumber;
  		Query query = currentSession.createSQLQuery(HQL);
		return query.list();
	
	}

	@Override
	public List<DisplayOuterReportModel> findAllSchemeBillGroupByDDOCode(String DDOCode,int roleid) {
		Session currentSession = manager.unwrap(Session.class);
		String HQL = null;
		if (roleid == 1) {
			HQL = "select ddo_map_id from ddo_reg_mst a inner join ddo_map_rlt b on a.ddo_reg_id = b.ddo_code_user_id1 and a.ddo_code = b.ddo_code_level1 where a.ddo_code = '"
					+ DDOCode + "'";
		} else {
			HQL = "select ddo_map_id from ddo_reg_mst a inner join ddo_map_rlt b on a.ddo_reg_id= b.ddo_code_user_id2 and a.ddo_code = b.ddo_code_level2 where a.ddo_code = '"
					+ DDOCode + "'";
		}
		Query query = currentSession.createSQLQuery(HQL);
		List<Long> lstIds = query.getResultList();
		if (query.getResultList().size() > 0) {
			String HQL11 = "FROM MpgSchemeBillGroupEntity as t where t.isActive = '1' and t.ddoMapId IN (:ids) ORDER BY t.billDescription ";
			Query q1 = (Query) manager.createQuery(HQL11);
			q1.setParameterList("ids", lstIds);
			return (List<DisplayOuterReportModel>) q1.getResultList();
		}

		return null;
	}

	@Override
	public List<Object[]> getcardecode(String strddo) {
			Session currentSession = manager.unwrap(Session.class);
			String HQL="select  * from cadre_group_mst  where id in(select cast(emp_class as integer) from employee_mst  where   ddo_code  ='"+strddo+"') ";
			Query query = currentSession.createSQLQuery(HQL);
			return query.list();
		
	
	}

	@Override
	public String getbillDetails(Long billDetails) {
		Session currentSession = manager.unwrap(Session.class);
		List list = new ArrayList();
		String rtnStr = null;
		StringBuffer query = new StringBuffer();
		query.append("select description from mst_dcps_bill_group where bill_group_id in (select scheme_billgroup_id from paybill_generation_trn \r\n" + 
				" where paybill_generation_trn_id='"+billDetails+"') ");
		Query hsqlQuery = currentSession.createSQLQuery(query.toString());
		list = hsqlQuery.list();

		if (list != null && list.size() > 0)
			rtnStr = list.get(0).toString();
		return rtnStr;
	}
}

