package com.mahait.gov.in.repository;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import com.mahait.gov.in.entity.MstCadreGroupEntity;
import com.mahait.gov.in.entity.MstRoleEntity;



public interface MstEmployeeRepo {

	public List<Object[]> findDDOScreenDataTable(String ddoCode);

	public List<Object[]> getInstitueDtls(String ddocode);

	public List<Object[]> getCadreMstData();

	public List<MstCadreGroupEntity> getGISGroup();

	public List<MstRoleEntity> findAll();
	
	

}
