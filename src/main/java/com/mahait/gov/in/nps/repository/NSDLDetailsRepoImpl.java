package com.mahait.gov.in.nps.repository;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.mahait.gov.in.entity.OrgUserMst;
import com.mahait.gov.in.nps.entity.NSDLBHDtlsEntity;
import com.mahait.gov.in.nps.entity.NSDLDHDtlsEntity;
import com.mahait.gov.in.nps.entity.NSDLSDDtlsEntity;

@Repository
public class NSDLDetailsRepoImpl implements NSDLDetailsRepo {

	@PersistenceContext
	EntityManager entityManager;

	@Override
	public List<Object[]> getNsdlEmpData(int month, int year, String ddoCode) {
		Session currentSession = entityManager.unwrap(Session.class);
		String HQL = "select DISTINCT a.file_name,bh_emp_amount,bh_emplr_amount,transaction_id,file_status,nsdl_bh_id,nsdl_status_code from nsdl_bh a inner join nsdl_sd b on a.file_name=b.file_name where month='"
				+ month + "' and year ='" + year + "'  and b.ddo_reg_no='"+ddoCode+"' ";
		Query query = currentSession.createSQLQuery(HQL);
		System.out.println(HQL);
		return query.list();
	}

	@Override
	public List<Object[]> getNSDLEmpDtlsForGenerate(int month, int year, String ddoCode) {
		Session currentSession = entityManager.unwrap(Session.class);
		String HQL = "select sum(a.gross_total_amt) as gross_amt,sum(a.total_net_amt) as net_amt,sum(a.nps_empr_allow) as emp_amt,  sum(nps_emp_contri) as empr_amt,"
				+ " c.ddo_reg_no,d.ddo_code_level2  from paybill_generation_trn_details a \r\n"
				+ " inner join employee_mst b on a.sevaarth_id=b.sevaarth_id\r\n"
				+ " inner join ddo_reg_mst c on b.ddo_code=c.ddo_code\r\n"
				+ " inner join ddo_map_rlt d on d.ddo_code_level1=c.ddo_code\r\n"
				+ " inner join paybill_generation_trn e on e.paybill_generation_trn_id=a.paybill_generation_trn_id\r\n"
				+ " where e.paybill_month='" + month + "' and e.paybill_year='" + year
				+ "' and b.dcps_gpf_flag='Y'  and d.ddo_code_level2='" + ddoCode
				+ "' and b.pran_no !='' and e.is_active='14'  and b.giscatahory=1 \r\n"
				+ "  and b.pran_no not in (select DISTINCT sd_pran_no  from nsdl_bh a inner join nsdl_sd b on "
				+ " a.file_name=b.file_name where month="+month+" and year ="+year+"  and b.ddo_reg_no='" + ddoCode+"') GROUP BY c.ddo_reg_no,d.ddo_code_level2";
		Query query = currentSession.createSQLQuery(HQL);
		System.out.println(HQL);
		return query.list();
	}

	@Override
	public List<Object[]> lstNsdlEmpDtls(String filename) {
		Session currentSession = entityManager.unwrap(Session.class);
		String HQL = "select a.sevaarth_id,a.employee_full_name_en,a.pran_no,a.ddo_code,sd.ddo_reg_no,sd.sd_emplr_amount,sd.sd_emp_amount,sd.sd_total_amt,case when super_ann_date > now() then 'Employee Not Retired' else 'Employee Retired' end from employee_mst a inner join nsdl_sd sd on sd.sd_pran_no = a.pran_no\r\n"
				+ "inner join nsdl_bh bh on bh.file_name = sd.file_name\r\n" + "AND bh.status <> '-1'\r\n"
				+ "AND bh.file_name = '" + filename + "'";
		Query query = currentSession.createSQLQuery(HQL);
		System.out.println(HQL);
		return query.list();
	}

	@Override
	public List<Object[]> getEmpDataByFileId(String userName, String fileId) {
		// TODO Auto-generated method stub
		Session currentSession = entityManager.unwrap(Session.class);
		String HQL = "select a.sevaarth_id,a.employee_full_name_en,a.pran_no,a.ddo_code,sd.ddo_reg_no,sd.sd_emplr_amount,sd.sd_emp_amount,sd.sd_total_amt,case when super_ann_date > now() then 'Employee Not Retired' else 'Employee Retired' end from employee_mst a inner join nsdl_sd sd on sd.sd_pran_no = a.pran_no\r\n"
				+ "inner join nsdl_bh bh on bh.file_name = sd.file_name\r\n" + "AND bh.status <> '-1'\r\n"
				+ "AND bh.file_name = '" + fileId + "'";
		Query query = currentSession.createSQLQuery(HQL);
		System.out.println(HQL);
		return query.list();
	}

	@Override
	public List<Object[]> lstNsdlDDOWiseDtls(String ddoCode) {
		Session currentSession = entityManager.unwrap(Session.class);
		String HQL = "select b.employee_full_name_en,b.ddo_code,b.sevaarth_id,c.office_name,sum(a.gross_total_amt) as gross_amt,sum(a.total_net_amt) as net_amt,sum(a.nps_empr_allow) as emp_amt,  sum(nps_emp_contri) as empr_amt,f.dcps_no,b.pran_no, \r\n"
				+ " case when b.super_ann_date > now() then 'Employee Not Retired' else 'Employee Retired' end as empStatus  from paybill_generation_trn_details a \r\n"
				+ " inner join employee_mst b on a.sevaarth_id=b.sevaarth_id\r\n"
				+ " inner join ddo_reg_mst c on b.ddo_code=c.ddo_code\r\n"
				+ " inner join ddo_map_rlt d on d.ddo_code_level1=c.ddo_code\r\n"
				+ " inner join paybill_generation_trn e on e.paybill_generation_trn_id=a.paybill_generation_trn_id\r\n"
				+ " inner join dcps_details_mst f on b.sevaarth_id=f.sevaarth_id\r\n"
				+ " where b.dcps_gpf_flag='Y' and d.ddo_code_level2='" + ddoCode
				+ "' and  b.pran_no !='' and e.is_active='14' and b.giscatahory=1 \r\n"
				+ "GROUP BY c.ddo_reg_no,d.ddo_code_level1,b.employee_full_name_en,b.ddo_code,b.sevaarth_id,c.office_name,f.dcps_no,b.pran_no,\r\n"
				+ "b.super_ann_date";
		Query query = currentSession.createSQLQuery(HQL);
		System.out.println(HQL);
		return query.list();
	}

	@Override
	public String getbatchIdCount(String batchIdPrefix) {
		// TODO Auto-generated method stub
		Session currentSession = entityManager.unwrap(Session.class);
		String lLstReturnList = "";
		StringBuilder sb = new StringBuilder();
		sb.append("select count(1)+1 from NSDL_BH where FILE_NAME like '" + batchIdPrefix + "%'");
		Query selectQuery = currentSession.createSQLQuery(sb.toString());
		lLstReturnList = selectQuery.uniqueResult().toString();
		return lLstReturnList;
	}

	@Override
	public List<Object[]> getEmployeeListNsdl(Integer yrCode, Integer month, Integer treasuryyno, String ddoName) {
		// TODO Auto-generated method stub
		List empLst = null;
		StringBuilder Strbld = new StringBuilder();
		Session currentSession = entityManager.unwrap(Session.class);
		try {
			Strbld.append(
					"select b.employee_full_name_en,b.ddo_code,b.sevaarth_id,c.office_name,sum(a.gross_total_amt) as gross_amt,sum(a.total_net_amt) as net_amt,"
					+ " sum(a.nps_empr_allow) as emp_amt,  sum(nps_emp_contri) as empr_amt, "
							+ " f.dcps_no,b.pran_no,  case when b.super_ann_date > now() then 'Employee Not Retired' else 'Employee Retired' end as empStatus  from paybill_generation_trn_details a\r\n"
							+ " inner join employee_mst b on a.sevaarth_id=b.sevaarth_id\r\n"
							+ " inner join ddo_reg_mst c on b.ddo_code=c.ddo_code\r\n"
							+ " inner join ddo_map_rlt d on d.ddo_code_level1=c.ddo_code\r\n"
							+ " inner join paybill_generation_trn e on e.paybill_generation_trn_id=a.paybill_generation_trn_id \r\n"
							+ " left join dcps_details_mst f on b.sevaarth_id=f.sevaarth_id\r\n"
							+ " where b.dcps_gpf_flag='Y' and d.ddo_code_level2='" + ddoName
							+ "' and  b.pran_no != '' and e.is_active='14' and b.giscatahory=1 and e.paybill_month=" + month
							+ " and e.paybill_year=" + yrCode +"   and b.pran_no not in (select DISTINCT sd_pran_no from nsdl_bh a inner join nsdl_sd b "
									+ " on a.file_name=b.file_name where month="+month+" and year ="+yrCode+"  and b.ddo_reg_no='"+ddoName+"') "
									+ "GROUP BY c.ddo_reg_no,d.ddo_code_level2,b.employee_full_name_en,b.ddo_code,b.sevaarth_id,c.office_name,f.dcps_no,b.pran_no,\r\n"
							+ "b.super_ann_date ");
			SQLQuery lQuery = currentSession.createSQLQuery(Strbld.toString());
			return lQuery.list();
		} catch (Exception e) {
			System.out.println("Error occer in  getEmployeeList ---------" + e);
		}
		return null;
	}

	@Override
	public Long getDDoRegCount(Integer yrCode, Integer month, Integer treasuryyno) {
		// TODO Auto-generated method stub

		// TODO Auto-generated method stub
		Session currentSession = entityManager.unwrap(Session.class);
		List temp = null;
		Long regCount = null;
		String HQL = "select b.employee_full_name_en,b.ddo_code,b.sevaarth_id,c.office_name,sum(a.gross_total_amt) as gross_amt,sum(a.total_net_amt) as net_amt, "
				+ "sum(a.nps_empr_allow) as emp_amt,  sum(nps_emp_contri) as empr_amt, "
				+ " f.dcps_no,b.pran_no,  case when b.super_ann_date > now() then 'Employee Not Retired' else 'Employee Retired' end as empStatus  from paybill_generation_trn_details a \r\n"
				+ " inner join employee_mst b on a.sevaarth_id=b.sevaarth_id\r\n"
				+ " inner join ddo_reg_mst c on b.ddo_code=c.ddo_code\r\n"
				+ " inner join ddo_map_rlt d on d.ddo_code_level1=c.ddo_code\r\n"
				+ " inner join paybill_generation_trn e on e.paybill_generation_trn_id=a.paybill_generation_trn_id\r\n"
				+ " inner join dcps_details_mst f on b.sevaarth_id=f.sevaarth_id\r\n"
				+ " where b.dcps_gpf_flag='Y'  and  b.pran_no !='' and e.is_active='14' and b.giscatahory=1 \r\n"
				+ "GROUP BY c.ddo_reg_no,d.ddo_code_level1,b.employee_full_name_en,b.ddo_code,b.sevaarth_id,c.office_name,f.dcps_no,b.pran_no,\r\n"
				+ "b.super_ann_date";
		Query query = currentSession.createSQLQuery(HQL);
		return regCount;
	}

	@Override
	public Integer saveDHDetail(NSDLDHDtlsEntity nSDLDHDtlsEntity) {
		// TODO Auto-generated method stub
		Session currentSession = entityManager.unwrap(Session.class);
		return (Integer) currentSession.save(nSDLDHDtlsEntity);
	}

	@Override
	public Integer saveSDDetail(NSDLSDDtlsEntity nSDLSDDtlsEntity) {
		// TODO Auto-generated method stub
		Session currentSession = entityManager.unwrap(Session.class);
		return (Integer) currentSession.save(nSDLSDDtlsEntity);
	}

	@Override
	public Integer saveBHDetail(NSDLBHDtlsEntity nSDLBHDtlsEntity) {
		// TODO Auto-generated method stub
		Session currentSession = entityManager.unwrap(Session.class);
		return (Integer) currentSession.save(nSDLBHDtlsEntity);
	}

	public String getBatchData(String fileNumber) {
		Session currentSession = entityManager.unwrap(Session.class);
		String lLstReturnList = "";
		StringBuilder sb = new StringBuilder();

		// sb.append(" select
		// SR_NO||'^'||HEADER_NAME||'^'||BH_NO||'^'||BH_COL2||'^'||BH_FIX_NO||'^'||BH_DATE||'^'||BH_BATCH_FIX_ID||'^^'||BH_DDO_COUNT||'^'||BH_PRAN_COUNT||'^'||BH_EMP_AMOUNT||'^'||BH_EMPLR_AMOUNT||'^^'||BH_TOTAL_AMT||'^'
		// from NSDL_BH_dtls ");
		sb.append(
				" select SR_NO||'^'||HEADER_NAME||'^'||BH_NO||'^'||BH_COL2||'^'||BH_FIX_NO||'^'||BH_DATE||'^'||BH_BATCH_FIX_ID||'^^'||BH_DDO_COUNT||'^'||BH_PRAN_COUNT||'^'||BH_EMPLR_AMOUNT||'^'||BH_EMP_AMOUNT||'^^'||BH_TOTAL_AMT||'^' from NSDL_BH");
		sb.append(" where FILE_NAME='" + fileNumber + "' ");
		
		System.out.println("query>>"+sb);
		Query selectQuery = currentSession.createSQLQuery(sb.toString());
		lLstReturnList = selectQuery.uniqueResult().toString();
		return lLstReturnList;
	}

	public List getDHData(String fileNumber) {
		Session currentSession = entityManager.unwrap(Session.class);
		List lLstReturnList = null;
		StringBuilder sb = new StringBuilder();
		// sb.append(" SELECT
		// SR_NO||'^'||HEADER_NAME||'^'||DH_NO||'^'||DH_COL2||'^'||DH_DDO_REG_NO||'^'||BH_SD_COUNT||'^'||DH_EMP_AMOUNT||'^'||DH_EMPLR_AMOUNT||'^^',DH_DDO_REG_NO
		// FROM NSDL_DH_dtls ");
		sb.append(
				" SELECT SR_NO||'^'||HEADER_NAME||'^'||DH_NO||'^'||DH_COL2||'^'||DH_DDO_REG_NO||'^'||BH_SD_COUNT||'^'||DH_EMPLR_AMOUNT||'^'||DH_EMP_AMOUNT||'^^',DH_DDO_REG_NO FROM NSDL_DH ");
		sb.append(" where FILE_NAME='" + fileNumber + "' order by SR_NO asc");
		Query selectQuery = currentSession.createSQLQuery(sb.toString());
		lLstReturnList = selectQuery.list();

		return lLstReturnList;

	}

	public List getSDDtls(String fileNumber, String ddoRegNo) {
		Session currentSession = entityManager.unwrap(Session.class);
		List lLstReturnList = null;
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT SR_NO||'^'||HEADER_NAME||'^'||SD_NO||'^'||SD_NO_2||'^'||SD_NO_3||'^'||SD_PRAN_NO||'^'||SD_EMPLR_AMOUNT||'^'||SD_EMP_AMOUNT||'^'||'^'||SD_TOTAL_AMT||'^'||SD_STATUS||'^'||SD_REMARK||'^' FROM NSDL_SD  ");
		sb.append(" where   FILE_NAME='" + fileNumber + "'and DDO_REG_NO='" + ddoRegNo + "' order by SR_NO asc ");
		System.out.println(sb);
		Query selectQuery = currentSession.createSQLQuery(sb.toString());
		lLstReturnList = selectQuery.list();
		return lLstReturnList;
	}
	
	public void updateFileStatus(int fileStatus, String fileno, String errorData) {
		Session currentSession = entityManager.unwrap(Session.class);
		StringBuilder sb = new StringBuilder();
		errorData=errorData.replace("'", "");
		sb.append("update NSDL_BH set file_status='"+fileStatus+"'  ");
		if(errorData!=null && !errorData.equals(""))
		sb.append(" , error_data='"+errorData+"' ");
		sb.append("   where FILE_NAME='"+fileno+"' ");
		final Query updateQuery = currentSession.createSQLQuery(sb.toString());
		updateQuery.executeUpdate();
	}

	@Override
	public void updateBatchId(String batchId, List dcpsLegacyId) {
		// TODO Auto-generated method stub
		Session currentSession = entityManager.unwrap(Session.class);
		StringBuffer str = new StringBuffer();
		str.append("update DCPS_LEGACY_DATA set batch_id='"
				+ batchId
				+ "', STATUS = '3' where 1=1 and batch_id is null and  NPS_ID in  ( :billIds ) ");
		Query query1 = currentSession.createSQLQuery(str.toString());
		query1.setParameterList("billIds", dcpsLegacyId);
		query1.executeUpdate();
	}

	
	
	
	@Override
	public List<Object[]> getNsdlEmpDataLevelwise(int month, int year, String userName) {
		Session currentSession = entityManager.unwrap(Session.class);
		
		String HQL = "select sum(a.GROSS_AMT) as gross_amt,sum(a.NET_TOTAL) as net_amt,sum(a.NPS_EMPLR) as emp_amt,  sum(dcps) as empr_amt,"
				+ " c.ddo_reg_no,d.ddo_code_level1  from paybill_generation_trn_details a \r\n"
				+ " inner join employee_mst b on a.sevaarth_id=b.sevaarth_id\r\n"
				+ " inner join ddo_reg_mst c on b.ddo_code=c.ddo_code\r\n"
				+ " inner join ddo_map_rlt d on d.ddo_code_level1=c.ddo_code\r\n"
				+ " inner join paybill_generation_trn e on e.paybill_generation_trn_id=a.paybill_generation_trn_id\r\n"
				+ " where e.paybill_month='" + month + "' and e.paybill_year='" + year
				+ "' and b.dcps_gpf_flag='Y'  and d.ddo_code_level2='" + userName
				+ "' and b.pran_no !='' and e.is_active='14'  and b.giscatahory=1 \r\n"
				+ "GROUP BY c.ddo_reg_no,d.ddo_code_level1";
		Query query = currentSession.createSQLQuery(HQL);
		System.out.println(HQL);
		return query.list();
	}

	@Override
	public List<Object[]> getEmployeeListNsdl1(Integer year, Integer month, String ddoCode) {
		List empLst = null;
		StringBuilder Strbld = new StringBuilder();
		Session currentSession = entityManager.unwrap(Session.class);
		try {
			Strbld.append(
					"select b.employee_full_name_en,b.ddo_code,b.sevaarth_id,c.office_name,sum(a.gross_total_amt) as gross_amt,sum(a.total_net_amt) as net_amt,"
					+ " sum(a.nps_empr_allow) as emp_amt,  sum(nps_emp_contri) as empr_amt, "
							+ " f.dcps_no,b.pran_no,  case when b.super_ann_date > now() then 'Employee Not Retired' else 'Employee Retired' end as empStatus  from paybill_generation_trn_details a\r\n"
							+ " inner join employee_mst b on a.sevaarth_id=b.sevaarth_id\r\n"
							+ " inner join ddo_reg_mst c on b.ddo_code=c.ddo_code\r\n"
							+ " inner join ddo_map_rlt d on d.ddo_code_level1=c.ddo_code\r\n"
							+ " inner join paybill_generation_trn e on e.paybill_generation_trn_id=a.paybill_generation_trn_id \r\n"
							+ " left join dcps_details_mst f on b.sevaarth_id=f.sevaarth_id\r\n"
							+ " where b.dcps_gpf_flag='Y' and d.ddo_code_level1='" + ddoCode
							+ "' and  b.pran_no != '' and e.is_active='14' and b.giscatahory=1 and e.paybill_month=" + month
							+ " and e.paybill_year=" + year + "\r\n"
							+ "GROUP BY c.ddo_reg_no,d.ddo_code_level2,b.employee_full_name_en,b.ddo_code,b.sevaarth_id,c.office_name,f.dcps_no,b.pran_no,\r\n"
							+ "b.super_ann_date ");
			SQLQuery lQuery = currentSession.createSQLQuery(Strbld.toString());
			return lQuery.list();
		} catch (Exception e) {
			System.out.println("Error occer in  getEmployeeList ---------" + e);
		}
		return null;
	}

	@Override
	public List<Object[]> searchDdoWiseContribution(int year, int month, OrgUserMst messages) {
		Session currentSession = entityManager.unwrap(Session.class);
		String strLocationCode = messages.getDdoCode().substring(0, 4);
		/*StringBuilder empNotPran = new StringBuilder();
		empNotPran.append("SELECT count(pay.EMP_ID) FROM paybill_generation_trn_details AS pay ")
		        .append(" INNER JOIN paybill_generation_trn AS billmpg ON pay.paybill_generation_trn_id = billmpg.paybill_generation_trn_id ")
		        .append(" INNER JOIN ORG_DDO_MST AS ddo ON billmpg.LOC_ID = CAST(ddo.location_code AS bigint) ")
		        .append(" INNER JOIN employee_mst AS mstemp ON mstemp.employee_id = pay.EMP_ID ")
		        .append(" WHERE billmpg.PAYBILL_YEAR ="+year)
		        .append(" AND billmpg.VOUCHER_NO IS NOT NULL AND billmpg.VOUCHER_DATE IS NOT NULL AND billmpg.PAYBILL_MONTH ="+month)
		        .append(" AND mstemp.PRAN_NO IS NULL AND ddo.DDO_CODE = temp.ZP_DDO_CODE AND (pay.dcps <> 0 AND pay.NPS_EMPLR_CONTRI_DED <> 0)");*/
		
		
		
		String empNotPran="select\r\n" + 
				"        count(pay.emp_id) \r\n" + 
				"    from\r\n" + 
				"        paybill_generation_trn_details as pay  \r\n" + 
				"    inner join\r\n" + 
				"        paybill_generation_trn as billmpg \r\n" + 
				"            on pay.paybill_generation_trn_id = billmpg.paybill_generation_trn_id  \r\n" + 
				"    inner join\r\n" + 
				"        org_ddo_mst as ddo \r\n" + 
				"            on billmpg.loc_id = cast(ddo.location_code as bigint)  \r\n" + 
				"    inner join\r\n" + 
				"        employee_mst as mstemp \r\n" + 
				"            on mstemp.employee_id = pay.emp_id  \r\n" + 
				"    where\r\n" + 
				"        billmpg.paybill_year = 24 \r\n" + 
				"        and billmpg.voucher_no is not null \r\n" + 
				"        and billmpg.voucher_date is not null \r\n" + 
				"        and billmpg.paybill_month = 7 \r\n" + 
				"        and mstemp.pran_no is null \r\n" + 
				"        and ddo.ddo_code = temp.zp_ddo_code \r\n" + 
				"        and (\r\n" + 
				"            pay.dcps <> 0 \r\n" + 
				"            and pay.nps_emplr_contri_ded <> 0\r\n" + 
				"        )";
		
		
		String empWithPran="select\r\n" + 
				"        count(pay.emp_id) \r\n" + 
				"    from\r\n" + 
				"        paybill_generation_trn_details as pay  \r\n" + 
				"    inner join\r\n" + 
				"        paybill_generation_trn as billmpg \r\n" + 
				"            on pay.paybill_generation_trn_id = billmpg.paybill_generation_trn_id  \r\n" + 
				"    inner join\r\n" + 
				"        org_ddo_mst as ddo \r\n" + 
				"            on billmpg.loc_id = cast(ddo.location_code as bigint)  \r\n" + 
				"    inner join\r\n" + 
				"        employee_mst as mstemp \r\n" + 
				"            on mstemp.employee_id = pay.emp_id  \r\n" + 
				"    where\r\n" + 
				"        billmpg.paybill_year = 24 \r\n" + 
				"        and billmpg.voucher_no is not null \r\n" + 
				"        and billmpg.voucher_date is not null \r\n" + 
				"        and billmpg.paybill_month = 7 \r\n" + 
				"        and mstemp.pran_no is not null \r\n" + 
				"        and ddo.ddo_code = temp.zp_ddo_code \r\n" + 
				"        and (\r\n" + 
				"            pay.dcps <> 0 \r\n" + 
				"            and pay.nps_emplr_contri_ded <> 0\r\n" + 
				"        )";
	/*	
		StringBuilder empWithPran = new StringBuilder();
		empWithPran.append("SELECT count(pay.EMP_ID) FROM paybill_generation_trn_details AS pay ")
		        .append(" INNER JOIN paybill_generation_trn AS billmpg ON pay.paybill_generation_trn_id = billmpg.paybill_generation_trn_id ")
		        .append(" INNER JOIN ORG_DDO_MST AS ddo ON billmpg.LOC_ID = CAST(ddo.location_code AS bigint) ")
		        .append(" INNER JOIN employee_mst AS mstemp ON mstemp.employee_id = pay.EMP_ID ")
		        .append(" WHERE billmpg.PAYBILL_YEAR ="+year)
		        .append(" AND billmpg.VOUCHER_NO IS NOT NULL AND billmpg.VOUCHER_DATE IS NOT NULL AND billmpg.PAYBILL_MONTH ="+month)
		        .append(" AND mstemp.PRAN_NO IS NOT NULL AND ddo.DDO_CODE = temp.ZP_DDO_CODE AND (pay.dcps <> 0 AND pay.NPS_EMPLR_CONTRI_DED <> 0)");*/

		
		StringBuilder sb2 = new StringBuilder();
		sb2.append(" select temp.ZP_DDO_CODE,(" + empNotPran.toString() + ") as empnotpran,(" + empWithPran.toString()+ ") as empWithPran ,sum(temp.CONTRIBUTION) as contribution,sum(temp.EmprContri) as EmprContri from ");
		sb2.append("\r\n" + 
				"    (select\r\n" + 
				"        rlt.zp_ddo_code,\r\n" + 
				"        sum(e.dcps_da) + sum(e.dcps) + sum(e.dcps_delay) + sum(e.dcps_pay) as contribution,\r\n" + 
				"        sum(e.nps_emplr_contri_ded) as emprcontri  \r\n" + 
				"    from\r\n" + 
				"        paybill_generation_trn b \r\n" + 
				"    left join\r\n" + 
				"        paybill_generation_trn_details e \r\n" + 
				"            on b.paybill_generation_trn_id = e.paybill_generation_trn_id \r\n" + 
				"    left join\r\n" + 
				"        org_ddo_mst ddo \r\n" + 
				"            on b.ddo_code = ddo.ddo_code  \r\n" + 
				"    left join\r\n" + 
				"        rlt_zp_ddo_map rlt \r\n" + 
				"            on rlt.zp_ddo_code = ddo.ddo_code \r\n" + 
				"    left join\r\n" + 
				"        employee_mst mstemp \r\n" + 
				"            on e.sevaarth_id = mstemp.sevaarth_id  \r\n" + 
				"    left outer join\r\n" + 
				"        (\r\n" + 
				"            select\r\n" + 
				"                sd.sd_pran_no,\r\n" + 
				"                cast(sum(coalesce(sd.sd_emp_amount, 0)) as double precision) as sd_amnt,\r\n" + 
				"                cast(sum(coalesce(sd.sd_emplr_amount, 0)) as double precision) as sd_amnt_emplr,\r\n" + 
				"                bh.year,\r\n" + 
				"                bh.month \r\n" + 
				"            from\r\n" + 
				"                nsdl_sd sd \r\n" + 
				"            inner join\r\n" + 
				"                nsdl_bh bh \r\n" + 
				"                    on bh.file_name = sd.file_name \r\n" + 
				"                    and bh.status != '-1'  \r\n" + 
				"                    and bh.year = 24    \r\n" + 
				"                    and bh.month = 7   \r\n" + 
				"                    and bh.file_name like '%2222%' \r\n" + 
				"                    and sd.is_legacy_data = 'N' \r\n" + 
				"            group by\r\n" + 
				"                sd.sd_pran_no,\r\n" + 
				"                bh.year,\r\n" + 
				"                bh.month \r\n" + 
				"        ) a \r\n" + 
				"            on a.sd_pran_no = mstemp.pran_no  \r\n" + 
				"    where\r\n" + 
				"        b.paybill_month = 7 \r\n" + 
				"        and b.paybill_year = 24 \r\n" + 
				"        and b.is_active = 14 \r\n" + 
				"        and e.dcps > 0 \r\n" + 
				"        and e.nps_emplr_contri_ded > 0 \r\n" + 
				"        and rlt.rept_ddo_code = '2222222222' \r\n" + 
				"        and mstemp.ddo_code is not null \r\n" + 
				"        and mstemp.pran_active = 1 \r\n" + 
				"        and mstemp.pran_no is not null \r\n" + 
				"        and mstemp.dcps_gpf_flag = 'Y' \r\n" + 
				"    group by\r\n" + 
				"        rlt.zp_ddo_code) as temp \r\n" + 
				"group by\r\n" + 
				"    temp.zp_ddo_code \r\n" + 
				"having\r\n" + 
				"    (\r\n" + 
				"        sum(temp.contribution) > 0  \r\n" + 
				"        and sum(temp.emprcontri) > 0\r\n" + 
				"    );\r\n" + 
				"");
		
	/*	sb2.append(
				" (select rlt.ZP_DDO_CODE,sum(e.DCPS_DA)+sum(e.DCPS)+sum(e.DCPS_DELAY)+sum(e.DCPS_PAY) as CONTRIBUTION,sum(e.NPS_EMPLR_CONTRI_DED) as EmprContri ");
		sb2.append(" FROM paybill_generation_trn b LEFT JOIN paybill_generation_trn_details e ON b.paybill_generation_trn_id = e.paybill_generation_trn_id LEFT JOIN ORG_DDO_MST ddo ON b.ddo_code = ddo.ddo_code ");
		sb2.append(" LEFT JOIN RLT_ZP_DDO_MAP rlt ON rlt.ZP_DDO_CODE = ddo.DDO_CODE LEFT JOIN employee_mst mstemp ON e.sevaarth_id = mstemp.sevaarth_id ");
		sb2.append(" left outer join (SELECT sd.SD_PRAN_NO,cast(sum(COALESCE(sd.SD_EMP_AMOUNT,0)) as double precision) as sd_amnt , cast(sum(COALESCE(sd.SD_EMPLR_AMOUNT,0)) as double precision) as sd_amnt_emplr, ");
		sb2.append(" bh.YEAR,bh.MONTH FROM NSDL_SD sd inner join NSDL_BH bh on bh.FILE_NAME=sd.FILE_NAME and bh.STATUS!='-1' ");
		sb2.append(" and bh.YEAR=" + year + "    and bh.MONTH="+ month +"   and bh.file_name like '%"+ strLocationCode+ "%' and sd.IS_LEGACY_DATA='N' group by sd.SD_PRAN_NO,bh.YEAR,bh.MONTH ) a on a.SD_PRAN_NO=mstemp.pran_no ");
		sb2.append(" WHERE b.PAYBILL_MONTH = " + month + " AND ");
		sb2.append(" b.PAYBILL_YEAR =" + year + " AND ");
		sb2.append(" b.is_active = 14 AND ");
		sb2.append(" e.DCPS > 0 AND ");
		sb2.append(" e.NPS_EMPLR_CONTRI_DED > 0 AND ");
		sb2.append(" rlt.REPT_DDO_CODE = '" + messages.getDdoCode() + "' AND ");
		sb2.append(" mstemp.DDO_CODE is not null AND ");
		sb2.append(" mstemp.PRAN_ACTIVE = 1 AND ");
		sb2.append(" mstemp.PRAN_NO is not null AND ");
		sb2.append(" mstemp.dcps_gpf_flag = 'Y' group by rlt.ZP_DDO_CODE ) as temp group by temp.ZP_DDO_CODE having (sum(temp.CONTRIBUTION)>0  and sum(temp.EmprContri)>0) ");

		*/
		System.out.println(sb2.toString());
		
		Query selectQuery2 = currentSession.createSQLQuery(sb2.toString());
		return selectQuery2.list();
	}

}
