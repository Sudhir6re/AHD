package com.mahait.gov.in.service;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.web.multipart.MultipartFile;

import com.mahait.gov.in.entity.AppoinmentEntity;
import com.mahait.gov.in.entity.MstDcpsBillGroup;
import com.mahait.gov.in.entity.MstDcpsDesignation;
import com.mahait.gov.in.entity.MstDesignationEntity;
import com.mahait.gov.in.model.DDOScreenModel;
import com.mahait.gov.in.model.MstCadreModel;
import com.mahait.gov.in.model.MstEmployeeModel;


public interface MstEmployeeService {

	public List<DDOScreenModel> findDDOScreenDataTable(String locale,long locId);

	public List<Object[]> getInstitueDtls(String userName);

	public List<MstCadreModel> getCadreMstData(String locale, long locId);

	public Object getDcpsAccnMaintainby();

	public Object getAccountMaintainby();

	public Object getGISGroup();

	public Object getGISApplicable();

	public Object getRelation();

	public List<Object[]> findEmployeeConfigurationGetSixPayScale(int i);

	public List<Object[]> findEmployeeConfigurationGetpayscale(int payCommission);

	public List<Object[]> findEmployeeConfigurationGetCurrentPost(int designationId, String userName, Object object);

	public List<Object[]> getCadreGroupMstData(String language, String cadreid);

	public List<MstEmployeeModel> getCadreGroupMstDataNew(String cadreid, String dob);
;
	public List<Object[]> findEmployeeConfigurationpayScaleSeven(int payScaleSeven);


	public List<AppoinmentEntity> getAppoitnment(String language);

	public List<Object[]> findEmployeeConfigurationGetsvnbasicpay(String payscale);

	
	public List<MstDesignationEntity> getDesignationMstData(String locale, long locId);



	
	
}
