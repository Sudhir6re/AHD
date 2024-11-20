package com.mahait.gov.in.nps.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mahait.gov.in.common.StringHelperUtils;
import com.mahait.gov.in.entity.OrgUserMst;
import com.mahait.gov.in.nps.model.DcpsLegacyModel;
import com.mahait.gov.in.nps.repository.LegacyValidationRepo;
import com.mahait.gov.in.nps.repository.VerifyDcpsLegacyRepo;

@Transactional
@Service
public class LegacyValidationServiceImpl implements LegacyValidationService {

	@Autowired
	LegacyValidationRepo legacyValidationRepo;

	@Autowired
	VerifyDcpsLegacyRepo verifyDcpsLegacyRepo;
	


	@Override
	public List<DcpsLegacyModel> findNsdlLegacyList(DcpsLegacyModel dcpsLegacyModel, OrgUserMst messages) {
		Long locId = verifyDcpsLegacyRepo.findLocId(messages.getLocId().toString());
		List<Object[]> lstObject = legacyValidationRepo.findNsdlLegacyList(dcpsLegacyModel, messages, locId);
		List<DcpsLegacyModel> lstDcpsLegacyModel = new ArrayList<>();
		if (lstObject.size() > 0) {
			for (Object[] object : lstObject) {
				DcpsLegacyModel dcpsLegacyModel1 = new DcpsLegacyModel();
				dcpsLegacyModel1.setFileName(StringHelperUtils.isNullString(object[0]));
				dcpsLegacyModel1.setBhEmpAmt(StringHelperUtils.isNullDouble(object[1]));
				dcpsLegacyModel1.setBhEmplrAmt(StringHelperUtils.isNullDouble(object[2]));
				dcpsLegacyModel1.setTransactionId(StringHelperUtils.isNullString(object[3]));
				dcpsLegacyModel1.setFileStatus(StringHelperUtils.isNullString(object[4]));
				dcpsLegacyModel1.setStatus(StringHelperUtils.isNullString(object[5]));
				dcpsLegacyModel1.setBhBatchFixId(StringHelperUtils.isNullString(object[6]));
				dcpsLegacyModel1.setVoucherNo(StringHelperUtils.isNullBigInteger(object[7]).longValue());
				
				//dcpsLegacyModel1.setVoucherDate(StringHelperUtils.isNull
				dcpsLegacyModel1.setBdsNo(StringHelperUtils.isNullString(object[9]));
				dcpsLegacyModel1.setBankRefno(StringHelperUtils.isNullString(object[10]));
				dcpsLegacyModel1.setLocName(StringHelperUtils.isNullString(object[11]));
				lstDcpsLegacyModel.add(dcpsLegacyModel1);
				
			}
		}
		return lstDcpsLegacyModel;
	}

}
