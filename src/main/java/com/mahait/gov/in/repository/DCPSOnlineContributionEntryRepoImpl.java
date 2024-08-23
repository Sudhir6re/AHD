package com.mahait.gov.in.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.mahait.gov.in.entity.CmnLocationMst;
import com.mahait.gov.in.entity.MstDcpsBillGroup;
import com.mahait.gov.in.entity.OrgUserMst;

@Repository
public class DCPSOnlineContributionEntryRepoImpl implements DCPSOnlineContributionEntryRepo {

	@PersistenceContext
	EntityManager manager;

	@Override
	public List<MstDcpsBillGroup> findAllBillGroup(OrgUserMst messages) {
		String HQL = "FROM MstDcpsBillGroup as t ORDER BY t.dcpsDdoBillGroupId DESC"; //dcpsDdoCode ='" + dDOCode
		return (List<MstDcpsBillGroup>) manager.createQuery(HQL).getResultList();
	}

	@Override
	public List<CmnLocationMst> findTreasuryByDdoCode(OrgUserMst messages) {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT LM.locationCode, LM.locName FROM RltDdoOrg RO, CmnLocationMst LM ");
		sb.append("WHERE RO.ddoCode = :ddoCode AND	LM.locationCode = RO.locationCode");
		return (List<CmnLocationMst>) manager.createQuery(sb.toString()).getResultList();
	}

}
