package com.mahait.gov.in.controller;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mahait.gov.in.entity.DeptEligibilityForAllowAndDeductEntity;
import com.mahait.gov.in.service.EmplyeeComponentMappingloginService;

@Controller
@RequestMapping("/ddoast")
public class EmplyeeComponentMappingLogicController {
	Logger logger = LoggerFactory.getLogger(EmplyeeComponentMappingLogicController.class);
	@Autowired
	EmplyeeComponentMappingloginService emplyeeComponentMappingloginService;

	@GetMapping("/mappedComponent")
	public void getMapEmply() {

		List<DeptEligibilityForAllowAndDeductEntity> lstDeptEligibilityForAllowAndDeductEntity = emplyeeComponentMappingloginService
				.findAllComponentDetails();
          
		List<Map<String, Object>> map = emplyeeComponentMappingloginService.getEmployeeList();

		for (Map<String, Object> employeeMap : map) {
			for (Map.Entry<String, Object> entry : employeeMap.entrySet()) {
				String key = entry.getKey();
				Object value = entry.getValue();

				Integer deptAllowDeducCode = 0;
            
				Long empId = (Long) employeeMap.get("employee_id");
				
				empId=0l;
				System.out.println("aaaaaaaa1"+empId);
				String sevaarthId = (String) employeeMap.get("sevaarth_id");
				System.out.println("aaaaaaaa2"+sevaarthId);
                System.err.println("aaaaaaaa2"+sevaarthId);
				List<DeptEligibilityForAllowAndDeductEntity> duplicateList = lstDeptEligibilityForAllowAndDeductEntity.stream().filter(s -> s.getDepartmentAllowdeducColNm().toLowerCase().equals(key.toLowerCase())).collect(Collectors.toList());
				if (duplicateList.size() > 0) {

					deptAllowDeducCode = duplicateList.get(0).getDepartmentAllowdeducCode();
					System.out.println("aaaaaaaa"+deptAllowDeducCode);

					if (deptAllowDeducCode != null) {

						emplyeeComponentMappingloginService.mapComonent(empId, sevaarthId, deptAllowDeducCode);
					}
				}
			}
		}

	}

}
