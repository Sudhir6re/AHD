package com.mahait.gov.in.controller.datamigration;

import java.math.BigInteger;

public interface BlobToPhysicalFileConverterService {
	void convertBlogToFile(Integer typeOp);

	String uploadPhoto(byte[] files, String DeptNm, BigInteger empid);

	String uploadSignature(byte[] files, String DeptNm, BigInteger empid);
}
