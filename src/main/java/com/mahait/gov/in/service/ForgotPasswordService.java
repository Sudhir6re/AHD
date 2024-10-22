package com.mahait.gov.in.service;

import javax.servlet.http.HttpServletRequest;

import com.mahait.gov.in.entity.OrgUserMst;

public interface ForgotPasswordService {

	OrgUserMst initiatePasswordReset(String emailId, HttpServletRequest request);

	OrgUserMst checkTokenIsValid(String token);

	void updatePassword(OrgUserMst orgUserMst);

}
