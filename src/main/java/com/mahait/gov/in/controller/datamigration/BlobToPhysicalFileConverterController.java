package com.mahait.gov.in.controller.datamigration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/master")
@Controller	
public class BlobToPhysicalFileConverterController {
	
	
	@Autowired
	BlobToPhysicalFileConverterService blobToPhysicalFileConverterService;
	
	
	
	//employeeconfig 1 //gr 2
	@RequestMapping("/convertBlogToFile/{typeOp}")
	public String convertBlogToFile(@PathVariable Integer typeOp) {
		blobToPhysicalFileConverterService.convertBlogToFile(typeOp);
	    return "Processing complete.";
	}
	
	
	

}
