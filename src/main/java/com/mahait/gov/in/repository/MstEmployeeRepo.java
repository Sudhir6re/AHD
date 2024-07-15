package com.mahait.gov.in.repository;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import com.mahait.gov.in.entity.AppoinmentEntity;
import com.mahait.gov.in.entity.MstCadreGroupEntity;
import com.mahait.gov.in.entity.MstDcpsDesignation;
import com.mahait.gov.in.entity.MstRoleEntity;



public interface MstEmployeeRepo {

	public List<Object[]> findDDOScreenDataTable(long loc_id);

	public List<Object[]> getInstitueDtls(String ddocode);

	public List<Object[]> getCadreMstData(long fielddeptId);

	public List<MstCadreGroupEntity> getGISGroup();

	public List<MstRoleEntity> findAll();

	public long getFieldDeptId(long loc_id);

	public List<Object[]> findEmployeeConfigurationGetSixPayScale(int payCommission);

	public List<Object[]> findEmployeeConfigurationpayScaleSeven(int payCommission);

	public List<Object[]> getgroupname(String strCadreId);

	public List<Object[]> getCadreGroupMstDataNew(String cadreid);

	public List<AppoinmentEntity> getAppoitnment(String language);

	public List<Object[]> getSvnPayscale();

	public List<Object[]> getSvnPcData(String payscale);

	public List<Object[]> getDesignationMstData(long fielddeptId);


	

}
