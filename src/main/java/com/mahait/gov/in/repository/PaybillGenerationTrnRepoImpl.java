package com.mahait.gov.in.repository;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Repository;

import com.mahait.gov.in.common.StringHelperUtils;
import com.mahait.gov.in.entity.AllowanceDeductionMstEntity;
import com.mahait.gov.in.entity.AllowanceDeductionRuleMstEntity;
import com.mahait.gov.in.entity.CLAMstEntity;
import com.mahait.gov.in.entity.CentralGovtDAMasterEntity;
import com.mahait.gov.in.entity.EmployeeIncrementEntity;
import com.mahait.gov.in.entity.MstEmployeeEntity;
import com.mahait.gov.in.entity.PaybillGenerationTrnDetails;
import com.mahait.gov.in.entity.PaybillGenerationTrnEntity;
import com.mahait.gov.in.entity.PaybillStatusEntity;

@Repository
public class PaybillGenerationTrnRepoImpl implements PaybillGenerationTrnRepo {
	// protected final Log logger = LogFactory.getLog(getClass());

	@PersistenceContext
	EntityManager entityManager;

	/*
	 * @Autowired SessionFactory sessionFactory;
	 */

	@Override
	public Long savePaybillHeadMpg(PaybillGenerationTrnEntity objEntity) {

		// logger.info("inside the saved method- ");

		Session currentSession = entityManager.unwrap(Session.class);
		Serializable saveId = currentSession.save(objEntity);
		return (Long) saveId;
	}

	@Override
	public Long saveHrPayPaybill(PaybillGenerationTrnDetails paybillGenerationTrnDetails) {

		// logger.info(" inside the saved saveHrPayPaybill- ");
		Session currentSession = entityManager.unwrap(Session.class);
		Serializable saveId = currentSession.save(paybillGenerationTrnDetails);
		return (Long) saveId;
	}

	@Override
	public Long getPaybillGenerationTrnId() {
		Session currentSession = entityManager.unwrap(Session.class);
		String hql = " SELECT coalesce(max(ch.paybillGenerationTrnId), 0) FROM PaybillGenerationTrnEntity ch";
		Query query = currentSession.createQuery(hql);
		return (Long) query.list().get(0);
	}

	@Override
	public PaybillGenerationTrnEntity findForwardChangeStatementById(Long paybillGenerationTrnId) {
		PaybillGenerationTrnEntity objCadre = null;
		Session currentSession = entityManager.unwrap(Session.class);
		objCadre = currentSession.get(PaybillGenerationTrnEntity.class, paybillGenerationTrnId);
		return objCadre;
	}

	@Override
	public void updateForwardChangeStatementStatus(PaybillGenerationTrnEntity paybillGenerationTrnEntity) {
		// TODO Auto-generated method stub
		Session currentSession = entityManager.unwrap(Session.class);
		currentSession.update(paybillGenerationTrnEntity);
	}

	@Override
	public PaybillGenerationTrnEntity forwardPayBillToLevel2(Long paybillGenerationTrnId) {
		PaybillGenerationTrnEntity objCadre = null;
		Session currentSession = entityManager.unwrap(Session.class);
		objCadre = currentSession.get(PaybillGenerationTrnEntity.class, paybillGenerationTrnId);
		return objCadre;
	}

	@SuppressWarnings("unchecked")
	@Override
	public int isPaybillExists(BigInteger billGroup, int paybillMonth, int paybillYear) {
		Session currentSession = entityManager.unwrap(Session.class);
		String sql = "select * from paybill_generation_trn_details where paybillMonth ='" + paybillMonth
				+ "' and paybillYear ='" + paybillYear + "'";
		// logger.info("inside isPaybillExists query*************"+sql);
		Query query = currentSession.createSQLQuery(sql);
		// logger.info("Result return**********"+query.getMaxResults());
		/* return query.getMaxResults(); */
		return (int) query.getMaxResults();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> findpaybill(BigInteger billGroup, int paybillMonth, int paybillYear, String ddo) {

		Session currentSession = entityManager.unwrap(Session.class);

		String HQL = " select a.paybill_generation_trn_id,a.scheme_billgroup_id,bb.bill_group_id  from paybill_generation_trn  a inner join mst_dcps_bill_group bb on a.scheme_billgroup_id = bb.bill_group_id  \r\n"
				+ " inner join org_ddo_mst dd on dd.ddo_code = a.ddo_code and a.ddo_code='" + ddo
				+ "' inner join rlt_zp_ddo_map ddd on dd.ddo_code = ddd.zp_ddo_code  \r\n"
				+ " where a.is_Active not in (14,8) and bb.bill_group_id='" + billGroup + "'";
		System.out.println("Findpaybill Quer +++++" + HQL);

		Query query = currentSession.createSQLQuery(HQL);
		return query.list();
	}

	// @SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getChangeStatementReport(String paybillGenerationTrnId) {
		Session currentSession = entityManager.unwrap(Session.class);
		String HQL = " select\r\n" + "				        temp.sevaarth_id,\r\n"
				+ "				        temp.employee_full_name_en,\r\n"
				+ "				        temp.designation_name,\r\n"
				+ "				        sum(temp.cur_year) as cur_year,\r\n"
				+ "				        sum(temp.cur_month) as cur_month,\r\n"
				+ "				        sum(temp.pre_year) as pre_year,\r\n"
				+ "				        sum(temp.pre_month) as pre_month,\r\n"
				+ "				        cast(sum(cur_basic_pay) as bigint)  as cur_basic,\r\n"
				+ "				        cast(sum(pre_basic_pay) as bigint) as pre_basic_pay,\r\n"
				+ "				        cast(sum(cur_da) as bigint) as cur_da,\r\n"
				+ "				        cast(sum(pre_da)as bigint) as pre_da,\r\n"
				+ "				        sum(cur_hra)as cur_hra,\r\n"
				+ "				        sum(pre_hra) as pre_hra,\r\n"
				+ "				        sum(cur_cla)as cur_cla,\r\n"
				+ "				        sum(pre_cla) as pre_cla,\r\n"
				+ "				        sum(cur_it)as cur_it,\r\n" + "				        sum(pre_it) as pre_it,\r\n"
				+ "				        sum(cur_hrr)as cur_hrr,\r\n"
				+ "				        sum(pre_hrr) as pre_hrr,\r\n"
				+ "				        sum(cur_pli)as cur_pli,\r\n"
				+ "				        sum(pre_pli) as pre_pli,\r\n"
				+ "				        cast(sum(cur_pt)as bigint) as cur_pt,\r\n"
				+ "				        cast(sum(pre_pt)as bigint)  as pre_pt,\r\n"
				+ "				        sum(cur_hba)as cur_hba,\r\n"
				+ "				        sum(pre_hba) as pre_hba,\r\n"
				+ "				        sum(cur_gpf_adv)as cur_gpf_adv,\r\n"
				+ "				        sum(pre_gpf_adv) as pre_gpf_adv,\r\n"
				+ "				        sum(cur_gpf_iv_adv)as cur_gpf_iv_adv,\r\n"
				+ "				        sum(pre_gpf_iv_adv) as pre_gpf_iv_adv,\r\n"
				+ "				        sum(cur_dcps)as cur_dcps,\r\n"
				+ "				        sum(pre_dcps) as pre_dcps,\r\n"
				+ "				        sum(cur_gpay)as cur_gpay,\r\n"
				+ "				        sum(pre_gpay) as pre_gpay,\r\n"
				+ "				        sum(cur_gpf_grp_abc)as cur_gpf_grp_abc,\r\n"
				+ "				        sum(pre_gpf_grp_abc) as pre_gpf_grp_abc,\r\n"
				+ "				        sum(cur_gpf_grp_d)as cur_gpf_grp_d,\r\n"
				+ "				        sum(pre_gpf_grp_d) as pre_gpf_grp_d,\r\n"
				+ "				        sum(cur_gpf_adv_grp_abc)as cur_gpf_adv_grp_abc,\r\n"
				+ "				        sum(pre_gpf_adv_grp_abc) as pre_gpf_adv_grp_abc,\r\n"
				+ "				        sum(cur_gpf_adv_grp_d)as cur_gpf_adv_grp_d,\r\n"
				+ "				        sum(pre_gpf_adv_grp_d) as pre_gpf_adv_grp_d,\r\n"
				+ "				        sum(cur_motor_cycle_adv)as cur_motor_cycle_adv,\r\n"
				+ "				        sum(pre_motor_cycle_adv) as pre_motor_cycle_adv,\r\n"
				+ "				        sum(cur_other_veh_adv)as cur_other_veh_adv,\r\n"
				+ "				        sum(pre_other_veh_adv) as pre_other_veh_adv,\r\n"
				+ "				        sum(cur_other_ded_try)as cur_other_ded_try,\r\n"
				+ "				        sum(pre_other_ded_try) as pre_other_ded_try,\r\n"
				+ "				        sum(cur_janjulgis)as cur_janjulgis,\r\n"
				+ "				        sum(pre_janjulgis) as pre_janjulgis,\r\n"
				+ "				        cast(sum(cur_ded_adjust)as bigint)as cur_ded_adjust,\r\n"
				+ "				        cast(sum(pre_ded_adjust)as bigint) as pre_ded_adjust,\r\n"
				+ "				        cast(sum(cur_gross_adjust)as bigint)as cur_gross_adjust,\r\n"
				+ "				        cast(sum(pre_gross_adjust)as bigint) as pre_gross_adjust,\r\n"
				+ "				        cast(sum(cur_gross_total_amt)as bigint)as cur_gross_total_amt,\r\n"
				+ "				        cast(sum(pre_gross_total_amt)as bigint) as pre_gross_total_amt,\r\n"
				+ "				        cast(sum(cur_total_net_amt)as bigint)as cur_total_net_amt,\r\n"
				+ "				        cast(sum(pre_total_net_amt)as bigint) as pre_total_net_amt,\r\n"
				+ "				        cast(sum(cur_gross_amt)as bigint)as cur_gross_amt,\r\n"
				+ "				        cast(sum(pre_gross_amt)as bigint) as pre_gross_amt,\r\n"
				+ "				        cast(sum(cur_total_deduction)as bigint)as cur_total_deduction,\r\n"
				+ "				        cast(sum(pre_total_deduction)as bigint) as pre_total_deduction,\r\n"
				+ "				        cast(sum(cur_adjust_dcps_employer)as bigint)as cur_adjust_dcps_employer,\r\n"
				+ "				        cast(sum(pre_adjust_dcps_employer)as bigint) as pre_adjust_dcps_employer,\r\n"
				+ "				        cast(sum(cur_dcps_arr)as bigint)as cur_dcps_arr,\r\n"
				+ "				        cast(sum(pre_dcps_arr)as bigint) as pre_dcps_arr,\r\n"
				+ "				        cast(sum(cur_dcps_da_arr)as bigint)as cur_dcps_da_arr,\r\n"
				+ "				        cast(sum(pre_dcps_da_arr)as bigint) as pre_dcps_da_arr,\r\n"
				+ "				        cast(sum(cur_dcps_delay)as bigint)as cur_dcps_delay,\r\n"
				+ "				        cast(sum(pre_dcps_delay)as bigint) as pre_dcps_delay,\r\n"
				+ "				        cast(sum(cur_dcps_employer)as bigint)as cur_dcps_employer,\r\n"
				+ "				        cast(sum(pre_dcps_employer)as bigint) as pre_dcps_employer,\r\n"
				+ "				        cast(sum(cur_dcps_pay_arr)as bigint)as cur_dcps_pay_arr,\r\n"
				+ "				        cast(sum(pre_dcps_pay_arr)as bigint) as pre_dcps_pay_arr,\r\n"
				+ "				        cast(sum(cur_dcps_regular_recovery)as bigint)as cur_dcps_regular_recovery,\r\n"
				+ "				        cast(sum(pre_dcps_regular_recovery)as bigint) as pre_dcps_regular_recovery,\r\n"
				+ "				        cast(sum(cur_gis)as bigint)as cur_gis,\r\n"
				+ "				        cast(sum(pre_gis)as bigint) as pre_gis,\r\n"
				+ "				        cast(sum(cur_ta)as bigint)as cur_ta,\r\n"
				+ "				        cast(sum(pre_ta)as bigint) as pre_ta,\r\n"
				+ "				        cast(sum(cur_nps_empr_deduct)as bigint) as cur_nps_empr_deduct,\r\n"
				+ "				        cast(sum( pre_nps_empr_deduct )as bigint)as   pre_nps_empr_deduct,\r\n"
				+ "				        cast(sum(  cur_nps_empr_allow)as bigint) as cur_nps_empr_allow,\r\n"
				+ "				        cast(sum(  pre_nps_empr_allow)as bigint)as pre_nps_empr_allow,\r\n"
				+ "				        cast(sum(  cur_emp_dcps_regular_recovery)as bigint) as   cur_emp_dcps_regular_recovery,\r\n"
				+ "				        cast(sum(  pre_emp_dcps_regular_recovery)as bigint)as   pre_emp_dcps_regular_recovery,\r\n"
				+ "				        cast(sum(  cur_emp_dcps_pay_arr)as bigint) as   cur_emp_dcps_pay_arr,\r\n"
				+ "				        cast(sum(  pre_emp_dcps_pay_arr)as bigint)as   pre_emp_dcps_pay_arr,\r\n"
				+ "				        cast(sum(  cur_emp_dcps_delay)as bigint) as   cur_emp_dcps_delay,\r\n"
				+ "				        cast(sum(  pre_emp_dcps_delay)as bigint)as   pre_emp_dcps_delay,\r\n"
				+ "				        cast(sum(  cur_emp_dcps_da_arr)as bigint) as   cur_emp_dcps_da_arr,\r\n"
				+ "				        cast(sum(  pre_emp_dcps_da_arr)as bigint)as   pre_emp_dcps_da_arr,\r\n"
				+ "						cast(sum(  pre_GPF_Subscription)as bigint)as   pre_GPF_Subscription,\r\n"
				+ "				        cast(sum(  cur_GPF_Subscription)as bigint) as   cur_GPF_Subscription,\r\n"
				+ "						cast(sum(  pre_GPF_Advance)as bigint)as   pre_GPF_Advance,\r\n"
				+ "				        cast(sum(  cur_GPF_Advance)as bigint) as   cur_GPF_Advance,\r\n"
				+ "						cast(sum(  pre_HRA5th)as bigint)as   pre_HRA5th,\r\n"
				+ "				        cast(sum(  cur_HRA5th)as bigint) as   cur_HRA5th,\r\n"
				+ "						cast(sum(  pre_HRA6th)as bigint)as   pre_HRA6th,\r\n"
				+ "				        cast(sum(  cur_HRA6th)as bigint) as   cur_HRA6th,\r\n"
				+ "						cast(sum(  pre_TA5th)as bigint)as   pre_TA5th,\r\n"
				+ "				        cast(sum(  cur_TA5th)as bigint) as   cur_TA5th,\r\n"
				+ "						cast(sum(  pre_TA6th)as bigint)as   pre_TA6th,\r\n"
				+ "				        cast(sum(  cur_TA6th)as bigint) as   cur_TA6th,\r\n"
				+ "						cast(sum(  pre_Society_Or_Bank_Loan)as bigint)as   pre_Society_Or_Bank_Loan,\r\n"
				+ "				        cast(sum(  cur_Society_Or_Bank_Loan)as bigint) as   cur_Society_Or_Bank_Loan,\r\n"
				+ "						cast(sum(  pre_BLWF)as bigint)as   pre_BLWF,\r\n"
				+ "				        cast(sum(  cur_BLWF)as bigint) as   cur_BLWF,\r\n"
				+ "						cast(sum(  pre_NDCPS_Subscription)as bigint)as   pre_NDCPS_Subscription,\r\n"
				+ "				        cast(sum(  cur_NDCPS_Subscription)as bigint) as   cur_NDCPS_Subscription,\r\n"
				+ "						cast(sum(pre_GPF_Arrears)as bigint)as   pre_GPF_Arrears,\r\n"
				+ "				        cast(sum(cur_GPF_Arrears)as bigint) as   cur_GPF_Arrears,\r\n"
				+ "						cast(sum(pre_GPF_Special_Arr)as bigint)as   pre_GPF_Special_Arr,\r\n"
				+ "				        cast(sum(cur_GPF_Special_Arr)as bigint) as   cur_GPF_Special_Arr,\r\n"
				+ "						cast(sum(pre_Arrears)as bigint)as   pre_Arrears,\r\n"
				+ "				        cast(sum(cur_Arrears)as bigint) as   cur_Arrears,\r\n"
				+ "						cast(sum(pre_Deputation_Allow)as bigint)as   pre_Deputation_Allow,\r\n"
				+ "				        cast(sum(cur_Deputation_Allow)as bigint) as   cur_Deputation_Allow,\r\n"
				+ "						cast(sum(pre_Tracer_Allow)as bigint)as   pre_Tracer_Allow,\r\n"
				+ "				        cast(sum(cur_Tracer_Allow)as bigint) as   cur_Tracer_Allow,\r\n"
				+ "						cast(sum(pre_Naksalied_Allow)as bigint)as   pre_Naksalied_Allow,\r\n"
				+ "				        cast(sum(cur_Naksalied_Allow)as bigint) as   cur_Naksalied_Allow,\r\n"
				+ "						cast(sum(pre_Hill_Station_Allow)as bigint)as   pre_Hill_Station_Allow,\r\n"
				+ "				        cast(sum(cur_Hill_Station_Allow)as bigint) as   cur_Hill_Station_Allow,\r\n"
				+ "						cast(sum(pre_wa)as bigint)as   pre_wa,\r\n"
				+ "				        cast(sum(cur_wa)as bigint) as   cur_wa,\r\n"
				+ "						cast(sum(pre_NAXAL_AREA_ALLOW)as bigint)as   pre_NAXAL_AREA_ALLOW,\r\n"
				+ "				        cast(sum(cur_NAXAL_AREA_ALLOW)as bigint) as   cur_NAXAL_AREA_ALLOW,\r\n"
				+ "						cast(sum(pre_BEGIS)as bigint)as   pre_BEGIS,\r\n"
				+ "				        cast(sum(cur_BEGIS)as bigint) as   cur_BEGIS,\r\n"
				+ "						cast(sum(pre_Leave_Pay)as bigint)as   pre_Leave_Pay,\r\n"
				+ "				        cast(sum(cur_Leave_Pay)as bigint) as   cur_Leave_Pay,\r\n"
				+ "						cast(sum(pre_rajashri_shahu)as bigint)as   pre_rajashri_shahu,\r\n"
				+ "				        cast(sum(cur_rajashri_shahu)as bigint) as   cur_rajashri_shahu,\r\n"
				+ "						cast(sum(pre_satara_society)as bigint)as   pre_satara_society,\r\n"
				+ "				        cast(sum(cur_satara_society)as bigint) as   cur_satara_society,\r\n"
				+ "						cast(sum(pre_mantralaya_soci)as bigint)as   pre_mantralaya_soci,\r\n"
				+ "				        cast(sum(cur_mantralaya_soci)as bigint) as   cur_mantralaya_soci,\r\n"
				+ "						cast(sum(pre_hba_house)as bigint)as   pre_hba_house,\r\n"
				+ "				        cast(sum(cur_hba_house)as bigint) as   cur_hba_house,\r\n"
				+ "						cast(sum(pre_panipurvtha_soc_cl3or4)as bigint)as   pre_panipurvtha_soc_cl3or4,\r\n"
				+ "				        cast(sum(cur_panipurvtha_soc_cl3or4)as bigint) as   cur_panipurvtha_soc_cl3or4,\r\n"
				+ "						cast(sum(pre_mlwf_onlymjp)as bigint)as   pre_mlwf_onlymjp,\r\n"
				+ "				        cast(sum(cur_mlwf_onlymjp)as bigint) as   cur_mlwf_onlymjp,\r\n"
				+ "						cast(sum(pre_mjp_soc_solapur)as bigint)as   pre_mjp_soc_solapur,\r\n"
				+ "				        cast(sum(cur_mjp_soc_solapur)as bigint) as   cur_mjp_soc_solapur,\r\n"
				+ "						cast(sum(pre_jal_bhavan_society)as bigint)as   pre_jal_bhavan_society,\r\n"
				+ "				        cast(sum(cur_jal_bhavan_society)as bigint) as   cur_jal_bhavan_society,\r\n"
				+ "						cast(sum(pre_mjp_soc_latur)as bigint)as   pre_mjp_soc_latur,\r\n"
				+ "				        cast(sum(cur_mjp_soc_latur)as bigint) as   cur_mjp_soc_latur,\r\n"
				+ "						cast(sum(pre_maha_lab_welfare_fund)as bigint)as   pre_maha_lab_welfare_fund,\r\n"
				+ "				        cast(sum(cur_maha_lab_welfare_fund)as bigint) as   cur_maha_lab_welfare_fund,\r\n"
				+ "						cast(sum(pre_society_latur)as bigint)as   pre_society_latur,\r\n"
				+ "				        cast(sum(cur_society_latur)as bigint) as   cur_society_latur,\r\n"
				+ "						cast(sum(pre_society_aurangabad)as bigint)as   pre_society_aurangabad,\r\n"
				+ "				        cast(sum(cur_society_aurangabad)as bigint) as   cur_society_aurangabad,\r\n"
				+ "						cast(sum(pre_society_nanded)as bigint)as   pre_society_nanded,\r\n"
				+ "				        cast(sum(cur_society_nanded)as bigint) as   cur_society_nanded,\r\n"
				+ "						cast(sum(pre_Jalseva_karm_saha_Path)as bigint)as   pre_Jalseva_karm_saha_Path,\r\n"
				+ "				        cast(sum(cur_Jalseva_karm_saha_Path) as bigint) as   cur_Jalseva_karm_saha_Path,\r\n"
				+ "						cast(sum(pre_da_arr)as bigint)as   pre_da_arr,\r\n"
				+ "				        cast(sum(cur_da_arr) as bigint) as   cur_da_arr\r\n" + " \r\n"
				+ "						\r\n" + "				         \r\n" + "				    from\r\n"
				+ "				        (select\r\n" + "				            c.sevaarth_id,\r\n"
				+ "				            c.employee_full_name_en,\r\n"
				+ "				            deg.designation_name,\r\n"
				+ "				            d.year_english as cur_year,\r\n"
				+ "				            e.month_id as cur_month ,\r\n"
				+ "				            0 as pre_year,\r\n" + "				            0 as pre_month ,\r\n"
				+ "				            b.basic_pay  as cur_basic_pay,\r\n"
				+ "				            0  as pre_basic_pay,\r\n"
				+ "				            b.da  as cur_da,\r\n" + "				            0  as pre_da,\r\n"
				+ "				            b.hra as cur_hra,\r\n" + "				            0 as pre_hra,\r\n"
				+ "				            b.cla as cur_cla,\r\n" + "				            0 as pre_cla,\r\n"
				+ "				            b.it as cur_it,\r\n" + "				            0 as pre_it,\r\n"
				+ "				            b.hrr as cur_hrr,\r\n" + "				            0 as pre_hrr,\r\n"
				+ "				            b.pli as cur_pli,\r\n" + "				            0 as pre_pli,\r\n"
				+ "				            b.pt as cur_pt,\r\n" + "				            0 as pre_pt,\r\n"
				+ "				            b.hba as cur_hba,\r\n" + "				            0 as pre_hba,\r\n"
				+ "				            b.gpf_adv as cur_gpf_adv,\r\n"
				+ "				            0 as pre_gpf_adv,\r\n"
				+ "				            b.gpf_iv_adv as cur_gpf_iv_adv,\r\n"
				+ "				            0 as pre_gpf_iv_adv,\r\n"
				+ "				            b.dcps as cur_dcps,\r\n" + "				            0 as pre_dcps,\r\n"
				+ "				            b.gpay as cur_gpay,\r\n" + "				            0 as pre_gpay,\r\n"
				+ "				            b.gpf_grp_abc as cur_gpf_grp_abc,\r\n"
				+ "				            0 as pre_gpf_grp_abc,\r\n"
				+ "				            b.gpf_grp_d as cur_gpf_grp_d,\r\n"
				+ "				            0 as pre_gpf_grp_d,\r\n"
				+ "				            b.gpf_adv_grp_abc as cur_gpf_adv_grp_abc,\r\n"
				+ "				            0 as pre_gpf_adv_grp_abc,\r\n"
				+ "				            b.gpf_adv_grp_d as cur_gpf_adv_grp_d,\r\n"
				+ "				            0 as pre_gpf_adv_grp_d,\r\n"
				+ "				            b.motor_cycle_adv as cur_motor_cycle_adv,\r\n"
				+ "				            0 as pre_motor_cycle_adv,\r\n"
				+ "				            b.other_veh_adv as cur_other_veh_adv,\r\n"
				+ "				            0 as pre_other_veh_adv,\r\n"
				+ "				            b.other_ded_try as cur_other_ded_try,\r\n"
				+ "				            0 as pre_other_ded_try,\r\n"
				+ "				            b.janjulgis as cur_janjulgis,\r\n"
				+ "				            0 as pre_janjulgis,\r\n"
				+ "				            b.ded_adjust as cur_ded_adjust,\r\n"
				+ "				            0 as pre_ded_adjust,\r\n"
				+ "				            b.gross_adjust as cur_gross_adjust,\r\n"
				+ "				            0 as pre_gross_adjust,\r\n"
				+ "				            b.gross_total_amt as cur_gross_total_amt,\r\n"
				+ "				            0 as pre_gross_total_amt,\r\n"
				+ "				            b.total_net_amt as cur_total_net_amt,\r\n"
				+ "				            0 as pre_total_net_amt,\r\n"
				+ "				            b.gross_amt as cur_gross_amt,\r\n"
				+ "				            0 as pre_gross_amt,\r\n"
				+ "				            b.total_deduction as cur_total_deduction,\r\n"
				+ "				            0 as pre_total_deduction,\r\n"
				+ "				            b.adjust_dcps_employer as cur_adjust_dcps_employer,\r\n"
				+ "				            0 as pre_adjust_dcps_employer,\r\n"
				+ "				            b.dcps_arr as cur_dcps_arr,\r\n"
				+ "				            0 as pre_dcps_arr,\r\n"
				+ "				            b.dcps_da_arr as cur_dcps_da_arr,\r\n"
				+ "				            0 as pre_dcps_da_arr,\r\n"
				+ "				            b.dcps_delay as cur_dcps_delay,\r\n"
				+ "				            0 as pre_dcps_delay,\r\n"
				+ "				            b.dcps_employer as cur_dcps_employer,\r\n"
				+ "				            0 as pre_dcps_employer,\r\n"
				+ "				            b.dcps_pay_arr as cur_dcps_pay_arr,\r\n"
				+ "				            0 as pre_dcps_pay_arr,\r\n"
				+ "				            b.dcps_regular_recovery as cur_dcps_regular_recovery,\r\n"
				+ "				            0 as pre_dcps_regular_recovery,\r\n"
				+ "				            b.gis as cur_gis,\r\n" + "				            0 as pre_gis,\r\n"
				+ "				            b.ta as cur_ta,\r\n" + "				            0 as pre_ta,\r\n"
				+ "				            b.nps_empr_deduct as cur_nps_empr_deduct,\r\n"
				+ "				            0 as pre_nps_empr_deduct,\r\n"
				+ "				            b.nps_empr_allow as cur_nps_empr_allow,\r\n"
				+ "				            0 as pre_nps_empr_allow,\r\n"
				+ "				            b.emp_dcps_regular_recovery as cur_emp_dcps_regular_recovery,\r\n"
				+ "				            0 as pre_emp_dcps_regular_recovery,\r\n"
				+ "				            b.emp_dcps_pay_arr as cur_emp_dcps_pay_arr,\r\n"
				+ "				            0 as pre_emp_dcps_pay_arr,\r\n"
				+ "				            b.emp_dcps_delay as cur_emp_dcps_delay,\r\n"
				+ "				            0 as pre_emp_dcps_delay,\r\n"
				+ "				            b.emp_dcps_da_arr as cur_emp_dcps_da_arr,\r\n"
				+ "				            0 as pre_emp_dcps_da_arr,\r\n"
				+ "				            a.created_date as created_date ,\r\n"
				+ "					        b.GPF_Subscription as cur_GPF_Subscription,\r\n"
				+ "				            0 as pre_GPF_Subscription,\r\n"
				+ "				            b.GPF_Advance as cur_GPF_Advance,\r\n"
				+ "				            0 as pre_GPF_Advance,\r\n"
				+ "				            b.HRA5th as cur_HRA5th,\r\n"
				+ "				            0 as pre_HRA5th,\r\n"
				+ "				            b.HRA6th as cur_HRA6th,\r\n"
				+ "				            0 as pre_HRA6th,\r\n"
				+ "				            b.TA5th as cur_TA5th,\r\n" + "				            0 as pre_TA5th,\r\n"
				+ "				            b.TA6th as cur_TA6th,\r\n" + "				            0 as pre_TA6th,\r\n"
				+ "				            b.Society_Or_Bank_Loan as cur_Society_Or_Bank_Loan,\r\n"
				+ "				            0 as pre_Society_Or_Bank_Loan,\r\n"
				+ "				            b.BLWF as cur_BLWF,\r\n" + "				            0 as pre_BLWF,\r\n"
				+ "				            b.NDCPS_Subscription as cur_NDCPS_Subscription,\r\n"
				+ "				            0 as pre_NDCPS_Subscription,\r\n"
				+ "				            b.GPF_Arrears as cur_GPF_Arrears,\r\n"
				+ "				            0 as pre_GPF_Arrears,\r\n"
				+ "				            b.GPF_Special_Arr as cur_GPF_Special_Arr,\r\n"
				+ "				            0 as pre_GPF_Special_Arr,\r\n"
				+ "				            b.Arrears as cur_Arrears,\r\n"
				+ "				            0 as pre_Arrears,\r\n"
				+ "				            b.Deputation_Allow as cur_Deputation_Allow,\r\n"
				+ "				            0 as pre_Deputation_Allow,\r\n"
				+ "				            b.Tracer_Allow as cur_Tracer_Allow,\r\n"
				+ "				            0 as pre_Tracer_Allow,\r\n"
				+ "				            b.Naksalied_Allow as cur_Naksalied_Allow,\r\n"
				+ "				            0 as pre_Naksalied_Allow,\r\n"
				+ "				            b.Hill_Station_Allow as cur_Hill_Station_Allow,\r\n"
				+ "				            0 as pre_Hill_Station_Allow,\r\n"
				+ "				            b.WA as cur_wa,\r\n" + "				            0 as pre_wa,\r\n"
				+ "				            b.NAXAL_AREA_ALLOW as cur_NAXAL_AREA_ALLOW,\r\n"
				+ "				            0 as pre_NAXAL_AREA_ALLOW,\r\n"
				+ "				            b.BEGIS as cur_BEGIS,\r\n" + "				            0 as pre_BEGIS,\r\n"
				+ "				            b.Leave_Pay as cur_Leave_Pay,\r\n"
				+ "				            0 as pre_Leave_Pay,  \r\n"
				+ "							b.rajashri_shahu as cur_rajashri_shahu,\r\n"
				+ "				            0 as pre_rajashri_shahu,    \r\n"
				+ "							b.satara_society as cur_satara_society, \r\n"
				+ "				            0 as pre_satara_society, \r\n"
				+ "							b.mantralaya_soci as cur_mantralaya_soci, \r\n"
				+ "				            0 as pre_mantralaya_soci,  \r\n"
				+ "							b.hba_house as cur_hba_house, \r\n"
				+ "				            0 as pre_hba_house,  \r\n"
				+ "							b.panipurvtha_soc_cl3or4 as cur_panipurvtha_soc_cl3or4, \r\n"
				+ "				            0 as pre_panipurvtha_soc_cl3or4,  \r\n"
				+ "							b.mlwf_onlymjp as cur_mlwf_onlymjp, \r\n"
				+ "				            0 as pre_mlwf_onlymjp,    \r\n"
				+ "							b.mjp_soc_solapur as cur_mjp_soc_solapur,  \r\n"
				+ "				            0 as pre_mjp_soc_solapur, \r\n"
				+ "							b.jal_bhavan_society as cur_jal_bhavan_society,  \r\n"
				+ "				            0 as pre_jal_bhavan_society,\r\n"
				+ "				            b.mjp_soc_latur as cur_mjp_soc_latur,   \r\n"
				+ "				            0 as pre_mjp_soc_latur,   \r\n"
				+ "							b.maha_lab_welfare_fund as cur_maha_lab_welfare_fund,   \r\n"
				+ "				            0 as pre_maha_lab_welfare_fund,   \r\n"
				+ "							b.society_latur as cur_society_latur,   \r\n"
				+ "				            0 as pre_society_latur,  \r\n"
				+ "							b.society_aurangabad as cur_society_aurangabad,   \r\n"
				+ "				            0 as pre_society_aurangabad,  \r\n"
				+ "							b.society_nanded as cur_society_nanded,   \r\n"
				+ "				            0 as pre_society_nanded,   \r\n"
				+ "							b.Jalseva_karm_saha_Path as cur_Jalseva_karm_saha_Path,   \r\n"
				+ "				            0 as pre_Jalseva_karm_saha_Path,   \r\n"
				+ "							b.da_arr as cur_da_arr,   \r\n"
				+ "				            0 as pre_da_arr\r\n" + "				          \r\n"
				+ "				\r\n" + "				            \r\n"
				+ "				                                                                     \r\n"
				+ "				        from\r\n"
				+ "				            paybill_generation_trn a                                 \r\n"
				+ "				        inner join\r\n"
				+ "				            paybill_generation_trn_details b                                                       \r\n"
				+ "				                on a.paybill_generation_trn_id = b.paybill_generation_trn_id                                 \r\n"
				+ "				        inner join\r\n"
				+ "				            employee_mst  c                                                       \r\n"
				+ "				                on b.sevaarth_id = c.sevaarth_id                                 \r\n"
				+ "				        inner join\r\n"
				+ "				            year_mst d                                                       \r\n"
				+ "				                on b.paybill_year = d.year_id                                 \r\n"
				+ "				        inner join\r\n"
				+ "				            month_mst e                                                       \r\n"
				+ "				                on b.paybill_month = e.month_id                                 \r\n"
				+ "				        inner join\r\n"
				+ "				            designation_mst as deg                                                       \r\n"
				+ "				                on deg.designation_code=c.designation_code                                \r\n"
				+ "				        where\r\n" + "				            a.is_active<>8               \r\n"
				+ "				            and               a.paybill_generation_trn_id = '" + paybillGenerationTrnId
				+ "'                                \r\n" + "				        union\r\n"
				+ "				        all   select\r\n" + "				            c.sevaarth_id,\r\n"
				+ "				            c.employee_full_name_en,\r\n"
				+ "				            deg.designation_name,\r\n" + "				            0 as cur_year,\r\n"
				+ "				            0 as cur_month ,\r\n"
				+ "				            d.year_english as pre_year,\r\n"
				+ "				            e.month_id as pre_month,\r\n"
				+ "				            0  as cur_basic_pay,\r\n"
				+ "				            be.basic_pay  as pre_basic_pay,\r\n"
				+ "				            0  as cur_da,\r\n" + "				            be.da  as pre_da,\r\n"
				+ "				            0 as cur_hra,\r\n" + "				            be.hra as pre_hra,\r\n"
				+ "				            0 as cur_cla,\r\n" + "				            be.cla as pre_cla,\r\n"
				+ "				            0 as cur_it,\r\n" + "				            be.it as pre_it,\r\n"
				+ "				            0 as cur_hrr,\r\n" + "				            be.hrr as pre_hrr,\r\n"
				+ "				            0 as cur_pli,\r\n" + "				            be.pli as pre_pli,\r\n"
				+ "				            0 as cur_pt,\r\n" + "				            be.pt as pre_pt,\r\n"
				+ "				            0 as cur_hba,\r\n" + "				            be.hba as pre_hba,\r\n"
				+ "				            0 as cur_gpf_adv,\r\n"
				+ "				            be.gpf_adv as pre_gpf_adv,\r\n"
				+ "				            0 as cur_gpf_iv_adv,\r\n"
				+ "				            be.gpf_iv_adv as pre_gpf_iv_adv,\r\n"
				+ "				            0 as cur_dcps,\r\n" + "				            be.dcps as pre_dcps,\r\n"
				+ "				            0 as cur_gpay,\r\n" + "				            be.gpay as pre_gpay,\r\n"
				+ "				            0 as cur_gpf_grp_abc,\r\n"
				+ "				            be.gpf_grp_abc as pre_gpf_grp_abc,\r\n"
				+ "				            0 as cur_gpf_grp_d,\r\n"
				+ "				            be.gpf_grp_d as pre_gpf_grp_d,\r\n"
				+ "				            0 as cur_gpf_adv_grp_abc,\r\n"
				+ "				            be.gpf_adv_grp_abc as pre_gpf_adv_grp_abc,\r\n"
				+ "				            0 as cur_gpf_adv_grp_d,\r\n"
				+ "				            be.gpf_adv_grp_d as pre_gpf_adv_grp_d,\r\n"
				+ "				            0 as cur_motor_cycle_adv,\r\n"
				+ "				            be.motor_cycle_adv as pre_motor_cycle_adv,\r\n"
				+ "				            0 as cur_other_veh_adv,\r\n"
				+ "				            be.other_veh_adv as pre_other_veh_adv,\r\n"
				+ "				            0 as cur_other_ded_try,\r\n"
				+ "				            be.other_ded_try as pre_other_ded_try,\r\n"
				+ "				            0 as cur_janjulgis,\r\n"
				+ "				            be.janjulgis as pre_janjulgis,\r\n"
				+ "				            0 as cur_ded_adjust,\r\n"
				+ "				            be.ded_adjust as pre_ded_adjust,\r\n"
				+ "				            0 as cur_gross_adjust,\r\n"
				+ "				            be.gross_adjust as pre_gross_adjust,\r\n"
				+ "				            0 as cur_gross_total_amt,\r\n"
				+ "				            be.gross_total_amt as pre_gross_total_amt,\r\n"
				+ "				            0 as cur_total_net_amt,\r\n"
				+ "				            be.total_net_amt as pre_total_net_amt,\r\n"
				+ "				            0 as cur_gross_amt,\r\n"
				+ "				            be.gross_amt as pre_gross_amt,\r\n"
				+ "				            0 as cur_total_deduction,\r\n"
				+ "				            be.total_deduction as pre_total_deduction,\r\n"
				+ "				            0 as cur_adjust_dcps_employer,\r\n"
				+ "				            be.adjust_dcps_employer as pre_adjust_dcps_employer,\r\n"
				+ "				            0 as cur_dcps_arr,\r\n"
				+ "				            be.dcps_arr as pre_dcps_arr,\r\n"
				+ "				            0 as cur_dcps_da_arr,\r\n"
				+ "				            be.dcps_da_arr as pre_dcps_da_arr,\r\n"
				+ "				            0 as cur_dcps_delay,\r\n"
				+ "				            be.dcps_delay as pre_dcps_delay,\r\n"
				+ "				            0 as cur_dcps_employer,\r\n"
				+ "				            be.dcps_employer as pre_dcps_employer,\r\n"
				+ "				            0 as cur_dcps_pay_arr,\r\n"
				+ "				            be.dcps_pay_arr as pre_dcps_pay_arr,\r\n"
				+ "				            0 as cur_dcps_regular_recovery,\r\n"
				+ "				            be.dcps_regular_recovery as pre_dcps_regular_recovery,\r\n"
				+ "				            0 as cur_gis,\r\n" + "				            be.gis as pre_gis,\r\n"
				+ "				            0 as cur_ta,\r\n" + "				            be.ta as pre_ta,\r\n"
				+ "				            0 as cur_nps_empr_deduct,\r\n"
				+ "				            be.nps_empr_deduct as pre_nps_empr_deduct,\r\n"
				+ "				            0 as cur_nps_empr_allow,\r\n"
				+ "				            be.nps_empr_allow as pre_nps_empr_allow,\r\n"
				+ "				            0 as cur_emp_dcps_regular_recovery,\r\n"
				+ "				            be.emp_dcps_regular_recovery as pre_emp_dcps_regular_recovery,\r\n"
				+ "				            0 as cur_emp_dcps_pay_arr,\r\n"
				+ "				            be.emp_dcps_pay_arr as pre_emp_dcps_pay_arr,\r\n"
				+ "				            0 as cur_emp_dcps_delay,\r\n"
				+ "				            be.emp_dcps_delay as pre_emp_dcps_delay,\r\n"
				+ "				            0 as cur_emp_dcps_da_arr,\r\n"
				+ "				            be.emp_dcps_da_arr as pre_emp_dcps_da_arr,\r\n"
				+ "				            a.created_date  AS created_date, \r\n"
				+ "				            0 as cur_GPF_Subscription,\r\n"
				+ "				            be.GPF_Subscription as pre_GPF_Subscription,\r\n"
				+ "				            0 as cur_GPF_Advance,\r\n"
				+ "				            be.GPF_Advance as pre_GPF_Advance,\r\n"
				+ "				            0 as cur_HRA5th,\r\n"
				+ "				            be.HRA5th as pre_HRA5th,\r\n"
				+ "				            0 as cur_HRA6th,\r\n"
				+ "				            be.HRA6th as pre_HRA6th,\r\n"
				+ "				            0 as cur_TA5th,\r\n"
				+ "				            be.TA5th as pre_TA5th,\r\n" + "				            0 as cur_TA6th,\r\n"
				+ "				            be.TA5th as pre_TA6th,\r\n"
				+ "				            0 as cur_Society_Or_Bank_Loan,\r\n"
				+ "				            be.Society_Or_Bank_Loan as pre_Society_Or_Bank_Loan,\r\n"
				+ "				            0 as cur_BLWF,\r\n" + "				            be.BLWF as pre_BLWF,\r\n"
				+ "				            0 as cur_NDCPS_Subscription,\r\n"
				+ "				            be.NDCPS_Subscription as pre_NDCPS_Subscription,\r\n"
				+ "				            0 as cur_GPF_Arrears,\r\n"
				+ "				            be.GPF_Arrears as pre_GPF_Arrears,\r\n"
				+ "				            0 as cur_GPF_Special_Arr,\r\n"
				+ "				            be.GPF_Special_Arr as pre_GPF_Special_Arr,\r\n"
				+ "				            0 as cur_Arrears,\r\n"
				+ "				            be.Arrears as pre_Arrears,\r\n"
				+ "				            0 as cur_Deputation_Allow,\r\n"
				+ "				            be.Deputation_Allow as pre_Deputation_Allow,\r\n"
				+ "				            0 as cur_Tracer_Allow,\r\n"
				+ "				            be.Tracer_Allow as pre_Tracer_Allow,\r\n"
				+ "				            0 as cur_Naksalied_Allow,\r\n"
				+ "				            be.Naksalied_Allow as pre_Naksalied_Allow,\r\n"
				+ "				            0 as cur_Hill_Station_Allow,\r\n"
				+ "				            be.Hill_Station_Allow as pre_Hill_Station_Allow,\r\n"
				+ "				            0 as cur_wa,\r\n" + "				            be.wa as pre_wa,\r\n"
				+ "				            0 as cur_NAXAL_AREA_ALLOW,\r\n"
				+ "				            be.NAXAL_AREA_ALLOW as pre_NAXAL_AREA_ALLOW,\r\n"
				+ "				            0 as cur_BEGIS,\r\n"
				+ "				            be.BEGIS as pre_BEGIS,\r\n"
				+ "				            0 as cur_Leave_Pay,\r\n"
				+ "				            be.Leave_Pay as pre_Leave_Pay,   \r\n"
				+ "							0 as cur_rajashri_shahu,\r\n"
				+ "				            be.rajashri_shahu as pre_rajashri_shahu,\r\n"
				+ "							0 as cur_satara_society,\r\n"
				+ "				            be.satara_society as pre_satara_society,   \r\n"
				+ "							0 as cur_mantralaya_soci,\r\n"
				+ "				            be.mantralaya_soci as pre_mantralaya_soci,   \r\n"
				+ "							0 as cur_hba_house,\r\n"
				+ "				            be.hba_house as pre_hba_house,   \r\n"
				+ "							0 as cur_panipurvtha_soc_cl3or4,  \r\n"
				+ "				            be.panipurvtha_soc_cl3or4 as pre_panipurvtha_soc_cl3or4,  \r\n"
				+ "							0 as cur_mlwf_onlymjp,  \r\n"
				+ "				            be.mlwf_onlymjp as pre_mlwf_onlymjp, \r\n"
				+ "							0 as cur_mjp_soc_solapur,  \r\n"
				+ "				            be.mjp_soc_solapur as pre_mjp_soc_solapurv,   \r\n"
				+ "							0 as cur_jal_bhavan_society,  \r\n"
				+ "				            be.jal_bhavan_society as pre_jal_bhavan_society,  \r\n"
				+ "							0 as cur_mjp_soc_latur,  \r\n"
				+ "				            be.mjp_soc_latur as pre_mjp_soc_latur,   \r\n"
				+ "							0 as cur_maha_lab_welfare_fund,  \r\n"
				+ "				            be.maha_lab_welfare_fund as pre_maha_lab_welfare_fund,   \r\n"
				+ "							0 as cur_society_latur,  \r\n"
				+ "				            be.society_latur as pre_society_latur, \r\n"
				+ "							0 as cur_society_aurangabad,  \r\n"
				+ "				            be.society_aurangabad as pre_society_aurangabad,   \r\n"
				+ "							0 as cur_society_nanded,   \r\n"
				+ "				            be.society_nanded as pre_society_nanded,   \r\n"
				+ "							0 as cur_Jalseva_karm_saha_Path,   \r\n"
				+ "				            be.Jalseva_karm_saha_Path as pre_Jalseva_karm_saha_Path,   \r\n"
				+ "							0 as cur_da_arr,   \r\n"
				+ "				            be.da_arr as pre_da_arr\r\n" + "				           \r\n"
				+ "				           \r\n" + "				                                           \r\n"
				+ "				        from\r\n"
				+ "				            paybill_generation_trn a                                  \r\n"
				+ "				        inner join\r\n"
				+ "				            paybill_generation_trn_details be                                                       \r\n"
				+ "				                on a.paybill_generation_trn_id = be.paybill_generation_trn_id                                  \r\n"
				+ "				        inner join\r\n"
				+ "				            employee_mst  c                                                       \r\n"
				+ "				                on be.sevaarth_id = c.sevaarth_id                                   \r\n"
				+ "				        inner join\r\n"
				+ "				            year_mst d                                                       \r\n"
				+ "				                on be.paybill_year = d.year_id                                   \r\n"
				+ "				        inner join\r\n"
				+ "				            month_mst e                                                       \r\n"
				+ "				                on be.paybill_month = e.month_id                                   \r\n"
				+ "				        inner join\r\n"
				+ "				            designation_mst as deg                                                       \r\n"
				+ "				                on deg.designation_code=c.designation_code                                  \r\n"
				+ "				        where\r\n" + "				            a.is_active<>8               \r\n"
				+ "				            and               cast (e.month_id as varchar)||d.year_english||be.sevaarth_id in (\r\n"
				+ "				                select\r\n"
				+ "				                    cast (case                                                                               \r\n"
				+ "				                        when (e.month_id= 1) then 12                                                                               \r\n"
				+ "				                        else (e.month_id -1)                                                                   \r\n"
				+ "				                    end as varchar)||case                                                                   \r\n"
				+ "				                    when (e.month_id= 1) then (d.year_english-1)                                                                   \r\n"
				+ "				                    else d.year_english  end||sevaarth_id                                                       \r\n"
				+ "				                from\r\n"
				+ "				                    paybill_generation_trn_details be                                                           \r\n"
				+ "				                inner join\r\n"
				+ "				                    year_mst d                                                                               \r\n"
				+ "				                        on be.paybill_year = d.year_id                                                          \r\n"
				+ "				                inner join\r\n"
				+ "				                    month_mst e                                                                               \r\n"
				+ "				                        on be.paybill_month = e.month_id                                                           \r\n"
				+ "				                where\r\n"
				+ "				                    be.paybill_generation_trn_id = '" + paybillGenerationTrnId
				+ "'                                         \r\n"
				+ "				            )                              \r\n"
				+ "				        ) as temp                   \r\n" + "				    group by\r\n"
				+ "				        temp.sevaarth_id,\r\n" + "				        temp.employee_full_name_en,\r\n"
				+ "				        temp.designation_name\r\n" + "				";
		Query query = currentSession.createSQLQuery(HQL);
		System.out.println("============change statement============" + HQL);
		// logger.info(" "+query.getQueryString());
		/*
		 * String HQL11 =
		 * "FROM MpgSchemeBillGroupEntity as t where t.isActive = '1' and t.ddoMapId = "
		 * +query.uniqueResult()+" ORDER BY t.billDescription ";
		 */
		return query.list();

	}

	// @SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getChangeStatementReportFromPreviousMonth(String ddoCode) {
		Session currentSession = entityManager.unwrap(Session.class);
		String HQL = "select concat((select employee_full_name_en from employee_mst where sevaarth_id=c.sevaarth_id),'(',(select designation_name from designation_mst where designation_code=(select designation_code from employee_mst where sevaarth_id=c.sevaarth_id)),')')"
				+ "								,d.year_english,e.month_id,be.*  from paybill_generation_trn a"
				+ "								inner join paybill_generation_trn_details be on a.paybill_generation_trn_id = be.paybill_generation_trn_id"
				+ "								inner join employee_mst  c on be.sevaarth_id = c.sevaarth_id"
				+ "								inner join year_mst d on be.paybill_year = d.year_id"
				+ "								inner join month_mst e on be.paybill_month = e.month_id"
				+ "								where cast (e.month_id as varchar)||d.year_english||be.sevaarth_id in ("
				+ "								select  cast (case when (e.month_id= 1) then 12 else (e.month_id -1) end as varchar)||case when (e.month_id= 1) then (d.year_english-1) else d.year_english  end||sevaarth_id from  paybill_generation_trn_details be"
				+ "								inner join year_mst d on be.paybill_year = d.year_id"
				+ "						        inner join month_mst e on be.paybill_month = e.month_id where  be.paybill_generation_trn_id = '"
				+ ddoCode + "')";
		Query query = currentSession.createSQLQuery(HQL);
		/*
		 * String HQL11 =
		 * "FROM MpgSchemeBillGroupEntity as t where t.isActive = '1' and t.ddoMapId = "
		 * +query.uniqueResult()+" ORDER BY t.billDescription ";
		 */
		return query.list();

	}

	@Override
	public PaybillGenerationTrnEntity consolidatedPaybill(Long paybillGenerationTrnId) {
		PaybillGenerationTrnEntity objCadre = null;
		Session currentSession = entityManager.unwrap(Session.class);
		objCadre = currentSession.get(PaybillGenerationTrnEntity.class, paybillGenerationTrnId);
		return objCadre;
	}

	@Override
	public List<Object[]> findDDOinfo(String userName) {
		Session currentSession = entityManager.unwrap(Session.class);
		String HQL = "select ddo_reg_id,ddo_name||' '||ddo_code as name,treasury_code from ddo_reg_mst where   ddo_code ='"
				+ userName + "' ";
		Query query = currentSession.createSQLQuery(HQL);
		return query.list();

	}

	@Override
	public List<Object[]> findregIdinfo(Long regid) {
		Session currentSession = entityManager.unwrap(Session.class);
		String HQL = "select * from org_inst_mst   where ddo_reg_id = " + regid;
		Query query = currentSession.createSQLQuery(HQL);
		return query.list();
	}

	@Override
	public List<Object[]> findmonthinfo(BigInteger currmonth) {
		Session currentSession = entityManager.unwrap(Session.class);
		String HQL = "select * from month_mst     where month_id   ='" + currmonth + "' ";
		Query query = currentSession.createSQLQuery(HQL);
		return query.list();
	}

	@Override
	public List<Object[]> findofcIdinfo(Long ofcid) {
		Session currentSession = entityManager.unwrap(Session.class);
		String HQL = "select * from treasury_office_mst   where treasury_office_id = " + ofcid;
		Query query = currentSession.createSQLQuery(HQL);
		return query.list();
	}

	@Override
	public List<Object[]> getAbstractReport(String paybillGenerationTrnId) {
		Session currentSession = entityManager.unwrap(Session.class);
		String HQL = "select ddo.office_name,ddo.account_no,month_english||' '||year_english as monyear,bill.bill_gross_amt as TOTAL_SALARY, sum(billdtl.festival_advance) as FA, 0 as Recovery,sum(billdtl.gross_total_amt) as Gross_salary,sum(billdtl.gpf_adv)+sum(billdtl.gpf_iv_adv)+sum(billdtl.gpf_grp_abc)+sum(billdtl.gpf_grp_d)+sum(billdtl.gpf_adv_grp_abc)+sum(billdtl.gpf_adv_grp_d) as GPF, sum (billdtl.revenue_stamp)as Revenue_stamp,"
				+ " sum(billdtl.dcps_delay) as DCPS_Delay,sum(billdtl.dcps) as dcps_reg, sum(billdtl.it) as IT,sum(billdtl.dcps_da) as DCPS_DA,  sum(billdtl.pt) as PT,sum (billdtl.computer_adv)as ComputerAdv,  sum(billdtl.other_deduct) as OTHER_DEDUCTION, SUM(billdtl.pli) AS PLI,SUM(billdtl.gis) AS GIS,sum(billdtl.total_deduction) as TOTAL_DED,  bill.bill_net_amount as NET_PAY,0 as rd,sum(billdtl.lic)as lic,0 as MISC,0 as GSLIS,"
				+ "  0 as othergpf,0 as othergis,0 as quarterrent, sum(billdtl.cop_bank)as cop_bank,sum(billdtl.credit_soc)as credit_soc,sum(billdtl.recurring_dep)as recurring_dep,sum(billdtl.total_net_amt) as salpay from paybill_generation_trn  bill   inner join paybill_generation_trn_details billdtl ON billdtl.paybill_generation_trn_id=bill.paybill_generation_trn_id inner join consolidate_paybill_trn_mpg conbillmpg ON "
				+ " conbillmpg.paybill_generation_trn_id=bill.paybill_generation_trn_id  inner join consolidate_paybill_trn conbill ON conbillmpg.consolidate_paybill_trn_id =conbill.consolidate_paybill_trn_id inner join bill_group_mst billgroup ON billgroup.bill_group_id =bill.scheme_billgroup_id  \r\n"
				+ " inner join ddo_reg_mst ddo ON ddo.ddo_code =bill.ddo_code  and conbill.consolidate_paybill_trn_id  ="
				+ paybillGenerationTrnId
				+ " inner join month_mst month ON month.month_id =bill.paybill_month  inner join year_mst year ON year.year_id =bill.paybill_year  GROUP BY billgroup.bill_group_id,billgroup.bill_description,month_english,year_english,ddo.ddo_code,ddo.account_no,ddo.office_name,bill.bill_gross_amt,bill.bill_net_amount     "; // conbill.consolidate_paybill_trn_id
		// =3
		Query query = currentSession.createSQLQuery(HQL);
		System.out.println("------HQL---" + HQL);
		return query.list();
	}

	@Override
	public List<Object[]> findyearinfo(BigInteger yearcurr) {
		Session currentSession = entityManager.unwrap(Session.class);
		String HQL = "select * from year_mst  where year_id  = '" + yearcurr + "' ";
		Query query = currentSession.createSQLQuery(HQL);
		return query.list();

	}

	// Created by Manikandan for Supplimentory Paybill
	@Override
	public int isBrokenPeriodEmpty(String sevaarthid, String monthid, String yearid) {
		Session currentSession = entityManager.unwrap(Session.class);
		String HQL = "select count(*)  from broken_period_pay_mst where sevaarth_id ='" + sevaarthid + "' and month_id="
				+ monthid + " and year_id=" + yearid;
		Query query = currentSession.createSQLQuery(HQL);
		// logger.info("query="+query.getQueryString());
		// logger.info("query.list().get(0)="+query.list().get(0));
		int result = ((BigInteger) query.list().get(0)).intValue();
		return result;

	}

	// Created by Manikandan for Supplimentory Paybill
	@Override
	public List<Object[]> getBrokenPeriodData(String sevaarthid, String monthid, String yearid, String Username) {
		Session currentSession = entityManager.unwrap(Session.class);
		StringBuffer sb = new StringBuffer();
		sb.append(
				"select bppay.emp_id,bppay.sevaarth_id,sum(bppay.basic_pay) basicpay,sum(bppay.net_pay) netpay,bpallded.allow_deduc_code, ");
		sb.append(
				"sum(bpallded.allow_deduc_amt) allow_ded_amt , deptallmst.department_allowdeduc_col_nm,deptallmst.broken_method_name,deptallmst.is_allowdeduc_type_sum ");
		sb.append(
				"from broken_period_pay_mst bppay inner join broken_period_allow_deduc_mst bpallded on bppay.broken_period_id=bpallded.broken_period_id ");
		sb.append(
				"inner join department_allowdeduc_mst deptallmst on deptallmst.department_allowdeduc_code=bpallded.allow_deduc_code ");
		sb.append("where bppay.sevaarth_id='" + sevaarthid + "' and bppay.month_id=" + monthid + " and bppay.year_id="
				+ yearid + " and bppay.ddo_code='" + Username + "' ");
		sb.append(
				" group by  bppay.emp_id,bppay.sevaarth_id,bpallded.allow_deduc_code ,deptallmst.department_allowdeduc_col_nm,deptallmst.broken_method_name,deptallmst.is_type order by bppay.emp_id");
		Query query = currentSession.createSQLQuery(sb.toString());
		return query.list();
	}

	@Override
	public List<Object[]> getViewDetialsReport(Integer consolidatedId) {
		Session currentSession = entityManager.unwrap(Session.class);
		String HQL = "select c.paybill_generation_trn_id,d.bill_description,office_name,c.bill_gross_amt as groamt,c.bill_gross_amt-c.bill_net_amount as ded,c.bill_net_amount from consolidate_paybill_trn a "
				+ " inner join consolidate_paybill_trn_mpg b on a.consolidate_paybill_trn_id = b.consolidate_paybill_trn_id inner join paybill_generation_trn c on b.paybill_generation_trn_id = c.paybill_generation_trn_id "
				+ "inner join bill_group_mst d on c.scheme_billgroup_id = d.bill_group_id inner join ddo_reg_mst e ON e.ddo_code =c.ddo_code where a.consolidate_paybill_trn_id  = "
				+ consolidatedId;
		Query query = currentSession.createSQLQuery(HQL);
		return query.list();
	}

	@Override
	public int getDaPercentageByMonthYear(String startDate, int commoncodePaycommission7pc) {
		Session currentSession = entityManager.unwrap(Session.class);
		String HQL = "FROM AllowanceDeductionMstEntity as t where t.payCommissionCode=" + commoncodePaycommission7pc
				+ "   and to_char(t.startDate,'YY-MM-DD')<='" + startDate + "' "
				+ " and (t.endDate is null OR to_char(t.endDate,'YY-MM-DD')>='" + startDate
				+ "')  ORDER BY t.startDate DESC";
		System.out.println("--------------------------------" + HQL);

		List<AllowanceDeductionMstEntity> lstAllowanceDeductionMstEntity = (List<AllowanceDeductionMstEntity>) entityManager
				.createQuery(HQL).getResultList();
		Integer percentage = lstAllowanceDeductionMstEntity.stream().map(m -> m.getPercentage()).findFirst().orElse(0);
		return percentage;
	}

	@Override
	public String getHRAPercentageByMonthYear(String startDate, int commoncodePaycommission7pc, String cityClass) {
		Session currentSession = entityManager.unwrap(Session.class);
		String HQL = "select t.payCommissionCode,t.cityClass" + cityClass + ""
				+ " FROM HRAAllowanceMstEntity as t where t.payCommissionCode=" + commoncodePaycommission7pc
				+ "   and to_char(t.startDate,'YY-MM-DD')<='" + startDate + "' "
				+ " and t.endDate is null OR to_char(t.endDate,'YY-MM-DD')>='" + startDate
				+ "'   ORDER BY t.startDate DESC";
		System.out.println("---------------getHRAPercentageByMonthYear-----------------" + HQL);

		String result = null;
		List<Object[]> lstAllowanceDeductionMstEntity = (List<Object[]>) entityManager.createQuery(HQL).getResultList();
		for (Object lst[] : lstAllowanceDeductionMstEntity) {
			result = lst[1].toString();
		}
		return result;
	}

	@Override
	public Integer isPaybillExistsForCurrentMonth(Long schemeBillgroupId, int paybillMonth, int paybillYear) {
		Session currentSession = entityManager.unwrap(Session.class);
		String HQL = "select count(*)  from paybill_generation_trn where  paybill_month ='" + paybillMonth
				+ "' and paybill_year ='" + paybillYear + "' and scheme_billgroup_id ='" + schemeBillgroupId
				+ "' and is_active in(1,2,3,4,5,6,7,9,10,11,12,13,14)";
		Query query = currentSession.createSQLQuery(HQL);
		// logger.info("query="+query.getQueryString());
		// logger.info("query.list().get(0)="+query.list().get(0));
		int result = ((BigInteger) query.list().get(0)).intValue();
		return result;

	}

	@Override
	public int getCheckIsBillInProcess(int monthName, int yearName, BigInteger schemeBillGroupId, int paybillType) {
		Session currentSession = entityManager.unwrap(Session.class);
		String HQL = "select count(*)  from paybill_generation_trn where scheme_billgroup_id ='" + schemeBillGroupId
				+ "' and bill_type_id =" + paybillType + " and is_active in(1,2,3,4,5,6,7,9,10,11,12,13)";
		System.out.println(HQL);
		Query query = currentSession.createSQLQuery(HQL);
		int result = ((BigInteger) query.list().get(0)).intValue();
		return result;
	}

	@Override
	public String getgradePay7PC(Long gradelevel) {
		Session currentSession = entityManager.unwrap(Session.class);
		String HQL = "select grade_pay from rlt_payband_gp_state_7pc where level = '" + gradelevel + "'";
		System.out.println("---------------getgradePay7PC-----------------" + HQL);
		Query query = currentSession.createSQLQuery(HQL);
		String result = (String) query.list().get(0);
		return result;
	}

	@Override
	public String isEmpRetired(int monthName, int yearName, BigInteger schemeBillGroupId, int paybillType,
			String ddoCode) {
		// TODO Auto-generated method stub
		yearName = 2000 + yearName - 1;
		String month1 = null;
		int numDays = 0;
		int numDays1 = 0;
		int numDays2 = 0;
		int totalnumDays2 = 0;
		if (monthName < 10) {
			month1 = "0" + monthName;
		} else {
			month1 = String.valueOf(monthName);
		}
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, yearName);
		calendar.set(Calendar.MONTH, monthName - 1);
		numDays = calendar.getActualMaximum(Calendar.DATE);

		Calendar cal = Calendar.getInstance();
		cal.setTime(calendar.getTime());
		cal.set(Calendar.MONTH, monthName);
		numDays1 = cal.getActualMaximum(Calendar.DATE);

		Calendar calendar2 = Calendar.getInstance();
		calendar2.setTime(calendar.getTime());
		calendar2.set(Calendar.MONTH, monthName + 1);
		numDays2 = calendar2.getActualMaximum(Calendar.DATE);

		totalnumDays2 = numDays + numDays1 + numDays2;

		Session currentSession = entityManager.unwrap(Session.class);
		String Hql = "select emst.employee_id,emst.sevaarth_id,emst.super_ann_date,emst.emp_service_end_date from employee_mst emst "
				+ " where emst.is_active = 1 and  emst.ddo_code = '" + ddoCode + "' and emst.billgroup_id = '"
				+ schemeBillGroupId + "' " + " and to_char(emst.doj,'YYYY-MM')<='" + yearName + "-" + month1
				+ "'  and  (to_char(emst.super_ann_date,'YYYY-MM')>='" + yearName + "-" + month1 + "' "
				+ " and   to_char(emst.emp_service_end_date,'YYYY-MM')>='" + yearName + "-" + month1 + "') "
				+ "and (super_ann_date-cast('" + yearName + "-" + monthName + "-" + 01 + "' as date) < " + totalnumDays2
				+ " or  emp_service_end_date-cast('" + yearName + "-" + monthName + "-" + 01 + "' as date) < "
				+ totalnumDays2 + ")"; //

		Query query = currentSession.createSQLQuery(Hql);
		System.out.println("isEmpRetired" + query);
		List<Object[]> lstemp = query.list();
		String msg = "";
		if (lstemp.size() > 0) {
			for (Object lst[] : lstemp) {
				if (msg.equals(""))
					msg = msg + isEmpRetiredBySevaarthId(lst[1].toString(), StringHelperUtils.isNullDate(lst[2]));
				else
					msg = "\n" + msg
							+ isEmpRetiredBySevaarthId(lst[1].toString(), StringHelperUtils.isNullDate(lst[2]));
			}
		}
		return msg;
	}

	@Override
	public String isEmpRetiredBySevaarthId(String sevaarthId, Date suppAnnDate) {
		// TODO Auto-generated method stub
		String msg = "";
		Session currentSession = entityManager.unwrap(Session.class);
		String sqlQuery = "select e.department_allowdeduc_code,c.department_allowdeduc_name,e.sevaarth_id,e.with_effective_date from employee_mst d"
				+ " inner join employee_allowdeduc_mpg e on d.sevaarth_id = e.sevaarth_id  and e.is_active = '1' inner join department_allowdeduc_mst c "
				+ "on e.department_allowdeduc_code = c.department_allowdeduc_code and c.is_active = '1' "
				+ "where c.department_allowdeduc_code  in(37,38,89,90,102,103,111,112) and e.sevaarth_id =  '"
				+ sevaarthId + "' ";
		Query query = currentSession.createSQLQuery(sqlQuery);
		List<Object[]> lstemp = query.list();
		if (lstemp.size() > 0) {
			msg = sevaarthId + " Employee Retirement date is " + suppAnnDate
					+ " please update employee eligibility !!!";
		}
		return msg;
	}

	@Override
	public PaybillGenerationTrnEntity findPaybillById(Long paybillGenerationTrnId) {
		PaybillGenerationTrnEntity objCadre = null;
		Session currentSession = entityManager.unwrap(Session.class);
		objCadre = currentSession.get(PaybillGenerationTrnEntity.class, paybillGenerationTrnId);
		return objCadre;
	}

	@Override
	public void updateVoucherEntry(PaybillGenerationTrnEntity objPaybillGeberationTrnEntity) {
		Session currentSession = entityManager.unwrap(Session.class);
		currentSession.update(objPaybillGeberationTrnEntity);

	}

	@Override
	public List<MstEmployeeEntity> checkedBgisAndGisCatNull(String schemeBillGroupId, String userName) {
		// TODO Auto-generated method stub
		String HQL = "FROM MstEmployeeEntity as  t  where t.ddoCode = '" + userName.trim() + "' and t.billGroupId = "
				+ schemeBillGroupId
				+ " AND t.isActive='1' and t.sevaarthId!=null and ((t.begisCatg is null or t.begisCatg='0') or  (t.giscatagory is null or t.giscatagory=0)) "
				+ " ORDER BY t.employeeFullNameEn";

		HQL = "FROM MstEmployeeEntity as  t  where t.ddoCode = '" + userName.trim() + "' and t.billGroupId = "
				+ schemeBillGroupId + " AND t.isActive='1'   ORDER BY t.employeeFullNameEn";
		return (List<MstEmployeeEntity>) entityManager.createQuery(HQL).getResultList();
	}

	@Override
	public Integer getannualincment(String sevaarthId, String startDate) {
		Session currentSession = entityManager.unwrap(Session.class);
		String HQL = "FROM EmployeeIncrementEntity  where sevaarthId='" + sevaarthId
				+ "'   and to_char(effective_from_date,'YY-MM-DD')<='" + startDate + "' "
				+ " and (to_increment_date is null OR to_char(to_increment_date,'YY-MM-DD')>='" + startDate
				+ "')  ORDER BY basic_pay_increment_id DESC";
		System.out.println("--------------------------------" + HQL);

		List<EmployeeIncrementEntity> lstAllowanceDeductionMstEntity = (List<EmployeeIncrementEntity>) entityManager
				.createQuery(HQL).getResultList();
		Double percentage = lstAllowanceDeductionMstEntity.stream().map(m -> m.getIncrementBasicPaySal()).findFirst()
				.orElse((double) 0);
		return percentage.intValue();
	}

	@Override
	public Integer getamtbeforeannualincment(String sevaarthId, String startDate) {
		Session currentSession = entityManager.unwrap(Session.class);
		String HQL = "FROM EmployeeIncrementEntity  where sevaarthId='" + sevaarthId
				+ "'   and to_char(effective_from_date,'YY-MM-DD')<='" + startDate + "' "
				+ " and (to_increment_date is null OR to_char(to_increment_date,'YY-MM-DD')>='" + startDate
				+ "')  ORDER BY basic_pay_increment_id DESC";
		System.out.println("--------------------------------" + HQL);

		List<EmployeeIncrementEntity> lstAllowanceDeductionMstEntity = (List<EmployeeIncrementEntity>) entityManager
				.createQuery(HQL).getResultList();
		Double percentage = lstAllowanceDeductionMstEntity.stream().map(m -> m.getPreBasicPay()).findFirst()
				.orElse((double) 0);
		return percentage.intValue();
	}

	@Override
	public int getDaCentralPercentageByMonthYear(String startDate, int commoncodePaycommission7pc) {
		Session currentSession = entityManager.unwrap(Session.class);

		String HQL1 = "FROM CentralGovtDAMasterEntity as t where t.payCommissionCode=" + commoncodePaycommission7pc
				+ "   and to_char(t.startDate,'YY-MM-DD')>='" + startDate + "' " + "  ORDER BY t.startDate DESC";

		List<CentralGovtDAMasterEntity> lstAllowanceDeductionMstEntity1 = (List<CentralGovtDAMasterEntity>) entityManager
				.createQuery(HQL1).getResultList();

		if (lstAllowanceDeductionMstEntity1.size() > 0 && lstAllowanceDeductionMstEntity1 != null) {
			Integer percentage = lstAllowanceDeductionMstEntity1.stream().map(m -> m.getPercentage()).findFirst()
					.orElse(0);
			return percentage;
		} else {
			String HQL = "FROM CentralGovtDAMasterEntity as t where t.payCommissionCode=" + commoncodePaycommission7pc
					+ "   and to_char(t.startDate,'YY-MM-DD')<='" + startDate + "' "
					+ " and (t.endDate is null OR to_char(t.endDate,'YY-MM-DD')>='" + startDate
					+ "')  ORDER BY t.startDate DESC";

			System.out.println("--------------------------------" + HQL);

			List<CentralGovtDAMasterEntity> lstAllowanceDeductionMstEntity = (List<CentralGovtDAMasterEntity>) entityManager
					.createQuery(HQL).getResultList();
			Integer percentage = lstAllowanceDeductionMstEntity.stream().map(m -> m.getPercentage()).findFirst()
					.orElse(0);
			return percentage;
		}
	}

	@Override
	public int savePaybillStatus(PaybillStatusEntity paybillStatusEntity) {

		Session currentSession = entityManager.unwrap(Session.class);
		Serializable saveId = currentSession.save(paybillStatusEntity);
		return (Integer) saveId;
	}

	@Override
	public List<AllowanceDeductionRuleMstEntity> fetchComponentDtlsByCompoId(int CompoId) {
		Session currentSession = entityManager.unwrap(Session.class);

		String HQL1 = "FROM AllowanceDeductionRuleMstEntity as t where t.departmentAllowdeducCode=" + CompoId + " ";

		List<AllowanceDeductionRuleMstEntity> lstAllowanceDeductionMstEntity1 = (List<AllowanceDeductionRuleMstEntity>) entityManager
				.createQuery(HQL1).getResultList();

		return lstAllowanceDeductionMstEntity1;
	}

	@Override
	public List<CLAMstEntity> getClaAmaountDtls(Long sevenPcLevel, Double basic, String citygroup,
			Long payCommissionCode) {
		Session currentSession = entityManager.unwrap(Session.class);

		String HQL1 = "FROM CLAMstEntity as t where t.minBasic<=" + basic + " and (maxBasic is null or maxBasic >= "
				+ basic + ") and cityGrp='" + citygroup + "'";

		List<CLAMstEntity> claMstLst = (List<CLAMstEntity>) entityManager.createQuery(HQL1).getResultList();

		return claMstLst;
	}

	@Override
	public List<AllowanceDeductionRuleMstEntity> fetchhraDtls(int allowDeducCode, String startDate, String citygroup,
			Double basic, int payComm) {
		Session currentSession = entityManager.unwrap(Session.class);

		String HQL1 = "FROM AllowanceDeductionRuleMstEntity as t and to_char(t.startDate,'YY-MM-DD')<='" + startDate
				+ "' and t.endDate is null OR to_char(t.endDate,'YY-MM-DD')>='" + startDate + "'  and departmentAllowdeducCode ="+allowDeducCode
				+ " and t.cityClass ='" + citygroup + "' and "+basic+">=22500 and t.payCommissionCode=" + payComm
				+ " ORDER BY t.startDate DESC";

		
		

		
		List<AllowanceDeductionRuleMstEntity> hraLst = (List<AllowanceDeductionRuleMstEntity>) entityManager
				.createQuery(HQL1).getResultList();

		return hraLst;
	}

	@Override
	public Double findGisComponentValue(String gisgroup, Date doj, String startDate) {
		Session currentSession = entityManager.unwrap(Session.class);

		String sql = "select  CASE WHEN DATE_PART('month',cast(:doj as date)) = 1 AND DATE_PART('day',cast( :doj as date))=1 THEN p.gis_amount "
				+ "WHEN EXTRACT(YEAR FROM AGE(cast(:startDate as date), cast(:doj as date))) <1 THEN p.premium_gis_amount  else p.gis_amount "
				+ " END AS selected_amount from cadre_group_mst p WHERE group_name_en = :groupName";

		NativeQuery<Double> query = currentSession.createNativeQuery(sql);
		query.setParameter("doj", doj);
		query.setParameter("groupName", gisgroup);
		query.setParameter("startDate", startDate);
		
		query.addScalar("selected_amount", StandardBasicTypes.DOUBLE);

		return (Double) query.uniqueResult();
	}

	@Override
	public Double fetchHraDtls(Double basic, String startDate, String cityClass,int allowDeducCode) {
		Session currentSession = entityManager.unwrap(Session.class);

		String sql = "SELECT CASE WHEN :basic < min_amount THEN min_amount ELSE :basic * multiplier / 100 END AS amount "
				+ "FROM hra_mst " + "WHERE city_type = :cityClass "
				+ "AND to_char(start_date, 'YY-MM-DD') <= :startDate "
				+ "AND (end_date IS NULL OR to_char(end_date, 'YY-MM-DD') >= :startDate)";

		NativeQuery<Double> query = currentSession.createNativeQuery(sql);
		query.setParameter("basic", basic);
		query.setParameter("cityClass", cityClass);
		query.setParameter("startDate", startDate);

		query.addScalar("amount", StandardBasicTypes.DOUBLE);

		return (Double) query.uniqueResult();
	}

	@Override
	public Double fetchAccidentialPilocyDtls(String startDate, String citygroup,int allowDeducCode) {
	    Session currentSession = entityManager.unwrap(Session.class);
	    
	    
	       
	    

	    String sql = "SELECT amount FROM allowance_deduction_wise_rule_mst " +
	                 "WHERE department_allowdeduc_code=:allowDeducCode AND city_group = :citygroup " +
	                 "AND to_char(start_date, 'YY-MM-DD') <= :startDate " +
	                 "AND (end_date IS NULL OR to_char(end_date, 'YY-MM-DD') >= :startDate)";

	    NativeQuery<Double> query = currentSession.createNativeQuery(sql);

	    query.setParameter("citygroup", citygroup);
	    query.setParameter("startDate", startDate);
	    query.setParameter("allowDeducCode", allowDeducCode);

	    query.addScalar("amount", StandardBasicTypes.DOUBLE);

	    return (Double) query.uniqueResult();
	}


	@Override
	public Double calculatePt(Double basic, int paybillMonth) {
		Session currentSession = entityManager.unwrap(Session.class);

		String sql = "SELECT calculate_pt(:basic, :paybillMonth) AS pt";

		NativeQuery<Double> query = currentSession.createNativeQuery(sql);

		query.setParameter("basic", basic);
		query.setParameter("paybillMonth", paybillMonth);

		query.addScalar("pt", StandardBasicTypes.DOUBLE);

		return (Double) query.uniqueResult();
	}

	@Override
	public Double calculateFamilyAllow(Long payCommission, Long sevenPcLevel, int allowDeducCode) {
		Session currentSession = entityManager.unwrap(Session.class);

		String sql = " select ALLOWANCE_DEDUCTION_WISE_RULE_ID,amount from allowance_deduction_wise_rule_mst where  department_allowdeduc_code = :allowDeducCode and "
				+ " :sevenPcLevel BETWEEN grade_pay_lower AND COALESCE(grade_pay_higher, :sevenPcLevel) and pay_commission_code = :payCommission ";

		Query query = currentSession.createNativeQuery(sql);
		query.setParameter("payCommission", payCommission);
		query.setParameter("sevenPcLevel", sevenPcLevel);
		query.setParameter("allowDeducCode", allowDeducCode);

		query.list();

		List<Object[]> lstemp = query.list();
		if (lstemp.size() > 1) {
			for (Object[] objects : lstemp) {
				if (sevenPcLevel >= StringHelperUtils.isNullDouble(objects[1])) {
					return StringHelperUtils.isNullDouble(objects[1]);
				}
			}
		} else if (lstemp.size() == 1) {
			for (Object[] objects : lstemp) {
				return StringHelperUtils.isNullDouble(objects[1]);
			}

		}

		return 0d;
	}

	@Override
	public Double fetchtaDtls(Double basic, Long payCommission, int allowDeducCode, Long gradelevel, String cityClass,
			String physicallyHandicapped) {
		Session currentSession = entityManager.unwrap(Session.class);

		String sql = "SELECT calculate_ta(" + basic + "," + payCommission + "," + allowDeducCode + "," + gradelevel
				+ ",'" + cityClass + "','" + physicallyHandicapped + "') AS ta";

		NativeQuery<Double> query = currentSession.createNativeQuery(sql);
		/*
		 * query.setParameter("basic", basic); query.setParameter("payCommission",
		 * payCommission); query.setParameter("allowDeducCode", allowDeducCode);
		 * query.setParameter("gradelevel", gradelevel); query.setParameter("cityClass",
		 * cityClass); query.setParameter("physicallyHandicapped",
		 * physicallyHandicapped);
		 */

		query.addScalar("ta", StandardBasicTypes.DOUBLE);

		return (Double) query.uniqueResult();
	}

}
