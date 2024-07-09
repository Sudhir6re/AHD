package com.mahait.gov.in.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mahait.gov.in.service.CommonHomeMethodsService;

//@Component
public class UserSessionObject {

	@Autowired
	private CommonHomeMethodsService commonHomeMethodsService;

	public  void setSession(Long userId, HttpSession session) {
		HashMap<String, Object> baseLoginMap = new HashMap<String, Object>();
		
		Map<String, Object> objectArgs = new HashMap<>();

		List<Object[]> retriveUserdetails = commonHomeMethodsService.retriveUserdetails(userId);
		if (retriveUserdetails.size() > 0) {
			for (Object[] obj : retriveUserdetails) {
				baseLoginMap.put("ddoCode", obj[0]);
				baseLoginMap.put("locationId", obj[1]);
			//	baseLoginMap.put("loggedInPost", obj[2]);
			//	baseLoginMap.put("primaryPostId", obj[2]);
				//baseLoginMap.put("postDetailCode", obj[3]);
			}
			
			session.setAttribute("baseLoginMap",baseLoginMap);
			
			//objectArgs.put("baseLoginMap", baseLoginMap);
		}
	}

}
