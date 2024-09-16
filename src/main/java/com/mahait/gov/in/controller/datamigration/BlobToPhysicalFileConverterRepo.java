package com.mahait.gov.in.controller.datamigration;

import java.util.List;

public interface BlobToPhysicalFileConverterRepo {

	List<Object[]> convertBlogToFile(Integer typeOp);


}
