package com.mahait.gov.in.service;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.web.multipart.MultipartFile;

import com.mahait.gov.in.model.DDOScreenModel;
import com.mahait.gov.in.model.MstCadreModel;


public interface MstEmployeeService {

	public List<DDOScreenModel> findDDOScreenDataTable(String locale,String ddoCode);

	public List<Object[]> getInstitueDtls(String userName);

	public List<MstCadreModel> getCadreMstData(String locale);

	public Object getDcpsAccnMaintainby();

	public Object getAccountMaintainby();

	public Object getGISGroup();

	public Object getGISApplicable();

	public Object getRelation();

	
	
}
