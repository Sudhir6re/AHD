package com.mahait.gov.in.controller.datamigration;

import java.math.BigInteger;
import java.util.List;

import com.mahait.gov.in.entity.GROrderDocumentEntity;
import com.mahait.gov.in.entity.MstEmployeeEntity;

public interface BlobToPhysicalFileConverterRepo {

	List<Object[]> convertBlogToFile(Integer typeOp);

	MstEmployeeEntity findEmpByEmpId(BigInteger id);

	void updatePhotoPath(MstEmployeeEntity mstEmployeeEntity);

	GROrderDocumentEntity findDocumentId(BigInteger grDocumentId);

	void updateGrPath(GROrderDocumentEntity gROrderDocumentEntity);


}
