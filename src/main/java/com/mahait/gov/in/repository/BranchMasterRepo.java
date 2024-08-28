package com.mahait.gov.in.repository;

import java.io.Serializable;
import java.util.List;

import javax.validation.Valid;

import com.mahait.gov.in.entity.MstBankBranchEntity;

public interface BranchMasterRepo {

	List<MstBankBranchEntity> listOfBranch();

	int saveBankBranch(@Valid MstBankBranchEntity mstBankBranchEntity);

	MstBankBranchEntity findBankBranchById(int bankBranchId);

	Serializable updateBankBranch(MstBankBranchEntity brachobject);

	List<Long> validateIFSCCode(Integer bankcode, String ifscCode);

}
