package com.mahait.gov.in.service;

import java.util.List;

import javax.validation.Valid;

import com.mahait.gov.in.entity.MstBankBranchEntity;

public interface BranchMasterService {

	List<MstBankBranchEntity> listOfBranch();

	int saveBankBranch(@Valid MstBankBranchEntity mstBankBranchEntity);

	MstBankBranchEntity findBankBranchById(int bankBranchId);

	String updateBankBranch(@Valid MstBankBranchEntity mstBankBranchEntity);

}
