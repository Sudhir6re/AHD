package com.mahait.gov.in.controller.datamigration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/ddoast")
public class EmployeeDetailController {

	@Autowired
	EmployeeDetailService employeeDetailService;
	
	
    @GetMapping("/detailspage")
    public @ResponseBody String readCSV() {
        return employeeDetailService.processExcelFile();
    }

	}

