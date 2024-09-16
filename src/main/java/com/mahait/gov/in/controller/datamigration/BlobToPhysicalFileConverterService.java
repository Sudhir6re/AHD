package com.mahait.gov.in.controller.datamigration;

import org.springframework.web.multipart.MultipartFile;

public interface BlobToPhysicalFileConverterService {

	void convertBlogToFile(Integer typeOp);

	String[] savePhotoSignature(MultipartFile[] files, String DeptNm, Long empid);

}
