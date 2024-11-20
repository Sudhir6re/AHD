package com.mahait.gov.in.nps.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.mahait.gov.in.entity.OrgUserMst;
import com.mahait.gov.in.nps.model.DcpsLegacyModel;

@Repository
public class LegacyValidationRepoImpl implements LegacyValidationRepo {

	@PersistenceContext
	EntityManager entityManager;

	@Override
	public List<Object[]> findNsdlLegacyList(DcpsLegacyModel dcpsLegacyModel, OrgUserMst messages, Long locId) {
		Session session = entityManager.unwrap(Session.class);
		List legacyList = null;
		Query lQuery = null;
	
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("Select distinct bh.file_name,bh.bh_emp_amount,bh.bh_emplr_amount,bh.transaction_id, ")
		            .append("Case When bh.file_status = '0' then cast('File not validated' as varchar(20)) ")
		            .append("When bh.file_status = '1' then cast('File is validated' as varchar(20)) ")
		            .append("When bh.file_status = '2' then cast('File is rejected' as varchar(20)) ")
		            .append("When bh.file_status = '5' then cast('Contribution file send' as varchar(25)) ")
		            .append("When bh.file_status = '11' then cast('Transaction id updated' as varchar(25)) ")
		            .append("When bh.file_status = '12' then cast('Bill locked' as varchar(25)) End, ")
		            .append("bh.file_status,bh.bh_batch_fix_id,bh.voucher_no,bh.voucher_date,bh.bds_no,bh.bank_refno, ")
		            .append("(Select lookup_name From cmn_lookup_mst Where cast(lookup_id as varchar) = ( ")
		            .append("Select distinct period From dcps_legacy_data Where batch_id = substr(bh.file_name, 2, length(bh.file_name)) )) ")
		            .append("From nsdl_bh bh ")
		            .append("Inner join rlt_zp_ddo_map as rlt ")
		            .append("On rlt.zp_ddo_code = bh.ddo_code ")
		            .append("Where bh.month = '"+dcpsLegacyModel.getMonth()+"' And bh.year = '"+dcpsLegacyModel.getYear()+"' ")
		            .append("And rlt.rept_ddo_code = '"+messages.getDdoCode()+"' And bh.file_name ilike 'R"+locId+"%' And bh.status <> '-1' And bh.is_legacy_data = 'Y'");
		
		lQuery = session.createSQLQuery(queryBuilder.toString());
		legacyList = lQuery.list();
		return legacyList;
	}

}
