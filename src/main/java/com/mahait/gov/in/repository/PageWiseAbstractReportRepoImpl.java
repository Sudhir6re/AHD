package com.mahait.gov.in.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.AliasToEntityMapResultTransformer;
import org.springframework.stereotype.Repository;

import com.mahait.gov.in.common.StringHelperUtils;
import com.mahait.gov.in.model.DisplayPageWiseAbstractReportModel;

@Repository
public class PageWiseAbstractReportRepoImpl implements PageWiseAbstractReportRepo{

	
	@PersistenceContext
	EntityManager manager;
	
	
	@Override
	public List<DisplayPageWiseAbstractReportModel> getAllDataForinnernew(String strddo, Long billNumber) {
		Session currentSession = manager.unwrap(Session.class);
		
		String HQL = "select distinct COALESCE(d.department_allowdeduc_col_nm, d.department_allowdeduc_name) allded, d.is_type,d.department_allowdeduc_id,'0' tempvalue,' ' tempempty,department_allowdeduc_seq "
				+ " from employee_mst a inner join employee_allowdeduc_mpg b ON b.sevaarth_id=a.sevaarth_id inner join paybill_generation_trn_details c ON c.sevaarth_id=a.sevaarth_id "
				+ " inner join department_allowdeduc_mst d ON b.department_allowdeduc_code=d.department_allowdeduc_code where a.ddo_code= '"+strddo+"' and paybill_generation_trn_id  = "+billNumber+"   order by department_allowdeduc_seq";
					//	+ " and  d.department_allowdeduc_col_nm <> 'COP_BANK' and  d.department_allowdeduc_col_nm <> 'COP_Bank' and  d.department_allowdeduc_col_nm <> 'CREDIT_SOC' and  d.department_allowdeduc_col_nm <> 'RECURRING_DEP' and  d.department_allowdeduc_col_nm <> 'RD' and  d.department_allowdeduc_col_nm <> 'LIC'  ";
		// logger.info("page wise departmentt .." +HQL);
		Query query = currentSession.createSQLQuery(HQL);
		List<Object[]> lstprop = query.list();
		List<DisplayPageWiseAbstractReportModel> lstObj = new ArrayList<>();
 if (!lstprop.isEmpty()) {
     for (Object[] objLst : lstprop) {
     	DisplayPageWiseAbstractReportModel obj = new DisplayPageWiseAbstractReportModel();
  obj.setDeptalldetNm(StringHelperUtils.isNullString(objLst[0]));
  obj.setType(StringHelperUtils.isNullInt(objLst[1]));
  obj.setDeptallowdeducid(StringHelperUtils.isNullInt(objLst[2]));
  obj.setTempvalue(StringHelperUtils.isNullString(objLst[3]));
  obj.setTempempty(StringHelperUtils.isNullString(objLst[4]));
  lstObj.add(obj);
     }
 }
		
		return lstObj;
	}

	@Override
	public List<Map<String, Object>> getempDetails(String billNo) {
		Session currentSession = manager.unwrap(Session.class);
		// TODO Auto-generated method stub
	String HQL = "select sum(temp1.basic_pay)as basic_pay,sum(temp1.da)as da,sum(temp1.hra)as hra,sum(temp1.cla)as cla,sum(temp1.Income_tax)as Income_Tax,sum(temp1.hrr)as hrr,sum(temp1.pli)as pli, " + 
				" sum(temp1.pt)as pt,sum(temp1.hba)as hba,sum(temp1.gpf_advance)as gpf_advance,sum(temp1.dcps) as dcps,sum(temp1.gpf_grp_abc)as gpf_grp_abc,sum(temp1.gpf_grp_d) as gpf_grp_d,sum(temp1.gpf_adv_grp_abc)as gpf_adv_grp_abc, " + 
				" sum(temp1.gpf_adv_grp_d)as gpf_adv_grp_d,sum(temp1.other_veh_adv)as other_veh_adv,sum(temp1.ded_adjust)as ded_adjust,sum(temp1.gross_adjust)as gross_adjust,sum(temp1.gross_total_amt)as gross_total_amt, " + 
				" sum(temp1.total_net_amt)as total_net_amt,sum(temp1.gross_amt)as gross_amt,sum(temp1.total_deduction)as total_deduction,sum(temp1.adjust_dcps_employer)as adjust_dcps_employer," + 
				" sum(temp1.dcps_arr)as dcps_arr,sum(temp1.dcps_employer)as dcps_employer,sum(temp1.gis)as gis,sum(temp1.ta)as ta,sum(temp1.revenue_stamp)as revenue_stamp,sum(temp1.fa)as fa,sum(temp1.add_pay)as add_pay, " + 
				" sum(temp1.basic_arr)as basic_arr,sum(temp1.co_hsg_soc)as co_hsg_soc,sum(temp1.contri_prov_fund)as contri_prov_fund,sum(temp1.convey_allow)as convey_allow,sum(temp1.cop_bank)as cop_bank, " + 
				" sum(temp1.credit_soc)as credit_soc,sum(temp1.family_plan_allow)as family_plan_allow,sum(temp1.gis_zp)as gis_zp,sum(temp1.gpf_abc_arr)as gpf_abc_arr,sum(temp1.gpf_d_arr)as gpf_d_arr,sum(temp1.group_acc_policy)as group_acc_policy, " + 
				" sum(temp1.hba_house)as hba_house,sum(temp1.non_comp_hra)as non_comp_hra,sum(temp1.non_pract_allow)as non_pract_allow,sum(temp1.other_deduct_by_treasury)as other_deduct_by_treasury, " + 
				" sum(temp1.permanent_travel_allow)as permanent_travel_allow,sum(temp1.personal_pay)as personal_pay,sum(temp1.recurring_dep)as recurring_dep,sum(temp1.serv_charg)as serv_charg,sum(temp1.special_pay)as special_pay, " + 
				" sum(temp1.trans_allow_arr)as trans_allow_arr,sum(temp1.tribal_allow)as tribal_allow,sum(temp1.uniform_allow)as uniform_allow,sum(temp1.wa)as wa,sum(temp1.da_arr)as da_arr,sum(temp1.lic)as lic, " + 
				" sum(temp1.add_hra)as add_hra,sum(temp1.naxal_area_allow)as naxal_area_allow,sum(temp1.other_rec)as other_rec,sum(temp1.pt_arr)as pt_arr,sum(temp1.other_allow)as other_allow,sum(temp1.other_deduct)as other_deduct, " + 
				" sum(temp1.svn_pc_da)as svn_pc_da,sum(temp1.spcl_duty_allow)as spcl_duty_allow,sum(temp1.dearness_pay)as dearness_pay,sum(temp1.deduct_adj_try)as deduct_adj_try,sum(temp1.deduct_adj_ag)as deduct_adj_ag,sum(temp1.deduct_adj_otr)as deduct_adj_otr, " + 
				" sum(temp1.da_on_ta)as da_on_ta,sum(temp1.dcps_da)as dcps_da,sum(temp1.misc)as MISC,sum(coalesce(temp1.exc_payrc,0))as exc_payrc,sum(coalesce(temp1.cm_fund_ac_ins,0))as cm_fund_ac_ins,sum(temp1.excess_payment)as excess_payment," + 
				" sum(coalesce(temp1.nps_emp_contri,0))as NPS_EMP_CONTRI,sum(coalesce(temp1.nps_empr_deduct,0))as NPS_EMPR_DEDUCT,sum(temp1.nps_empr_allow)as nps_empr_allow,sum(temp1.employer_dcps_da_arrears)as employer_dcps_da_arrears," + 
				" sum(coalesce(temp1.employer_dcps_delayed_rec,0))as employer_dcps_delayed_rec,sum(temp1.employer_dcps_pay_arrears)as employer_dcps_pay_arrears,sum(temp1.employer_dcps_regular_rec)as employer_dcps_regular_rec," + 
				" sum(temp1.ta5th)as ta5th,sum(temp1.ta6th)as ta6th,sum(temp1.arrears)as arrears, sum(temp1.deputation_allow)as deputation_allow,sum(temp1.naksalied_allow)as naksalied_allow,sum(temp1.tracer_allow)as tracer_allow," + 
				" sum(temp1.gpf_subscription)as gpf_subscription,sum(temp1.gpf_arrears)as gpf_arrears, sum(temp1.gpf_special_arr)as gpf_special_arr,sum(temp1.ndcps_subscription)as ndcps_subscription, sum(temp1.leave_pay)as leave_pay,sum(temp1.mantralaya_soci)as mantralaya_soci," + 
				" sum(temp1.gpf_da_sub)as gpf_da_sub,sum(temp1.excess_pay_rec)as excess_pay_rec, sum(temp1.flag_day)as flag_day,temp1.rn from (select temp.*,(ROW_NUMBER() " + 
				" OVER( ORDER BY temp.emp_class)-1)/8+1 as rn from (select distinct c.sevaarth_id sevaarthid,c.employee_full_name_en,c.employee_l_name_en,d.designation_name," + 
				" c.emp_class,b.* from paybill_generation_trn a inner join paybill_generation_trn_details b on a.paybill_generation_trn_id = b.paybill_generation_trn_id " + 
				" inner join employee_mst c on b.sevaarth_id = c.sevaarth_id inner join designation_mst d on d.designation_code = c.designation_code" + 
				" where a.paybill_generation_trn_id ='"+billNo+"' order by c.emp_class ) as temp order by  temp.emp_class ) as temp1 group by temp1.rn order by temp1.rn";
	
	 org.hibernate.Query hibernateQuery =  currentSession.createNativeQuery(HQL);
     hibernateQuery.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
List<Map<String,Object>> resvalue = hibernateQuery.list();

// logger.info("page wise report .." +HQL);
	return resvalue;
	}

}
