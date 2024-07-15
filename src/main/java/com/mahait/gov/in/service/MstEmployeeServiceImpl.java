package com.mahait.gov.in.service;

import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URLDecoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.mahait.gov.in.common.StringHelperUtils;
import com.mahait.gov.in.entity.AppoinmentEntity;
import com.mahait.gov.in.entity.MstCadreGroupEntity;
import com.mahait.gov.in.entity.MstDcpsDesignation;
import com.mahait.gov.in.entity.MstDesignationEntity;
import com.mahait.gov.in.model.DDOScreenModel;
import com.mahait.gov.in.model.MstCadreModel;
import com.mahait.gov.in.model.MstEmployeeModel;
import com.mahait.gov.in.repository.MstEmployeeRepo;


@Service
@Transactional
@PropertySource(value = { "classpath:application.properties" })
public class MstEmployeeServiceImpl implements MstEmployeeService {

	@Autowired
	private MstEmployeeRepo mstEmployeeRepo;

	@Autowired
	private Environment environment;

	@PersistenceContext
	EntityManager entityManager;

	@Override
	public List<DDOScreenModel> findDDOScreenDataTable(String locale, long loc_id) {
		List<Object[]> lstprop = null;
		try {
			lstprop = mstEmployeeRepo.findDDOScreenDataTable(loc_id);
		} catch (Exception e) {
			e.printStackTrace();
		}

		List<DDOScreenModel> lstObj = new ArrayList<>();
		if (!lstprop.isEmpty()) {
			for (Object[] objLst : lstprop) {
				DDOScreenModel obj = new DDOScreenModel();
				obj.setSubDepartmentId(StringHelperUtils.isNullInt(objLst[0]));
                obj.setSubDeptName(StringHelperUtils.isNullString(objLst[1]));
				
				lstObj.add(obj);
			}
		}
		return lstObj;
	}
	@Override
	public List<Object[]> getInstitueDtls(String ddocode) {
		List<Object[]> lstprop = mstEmployeeRepo.getInstitueDtls(ddocode);
		return lstprop;
	}


	@Override
	public List<MstCadreModel> getCadreMstData(String locale,long loc_id) {
		long fielddeptId = mstEmployeeRepo.getFieldDeptId(loc_id);
		List<Object[]> lstprop = mstEmployeeRepo.getCadreMstData(fielddeptId);
		List<MstCadreModel> lstObj = new ArrayList<>();
		if (!lstprop.isEmpty()) {
			for (Object[] objLst : lstprop) {
				MstCadreModel obj = new MstCadreModel();
				obj.setCadreId(StringHelperUtils.isNullBigInteger(objLst[0]).longValue());
				obj.setCadreDescription(StringHelperUtils.isNullString(objLst[1]));
				lstObj.add(obj);
			}
		}
		return lstObj;
	}
	@Override
	public List<Object[]> getAccountMaintainby() {
		List<Object[]> result = new ArrayList<Object[]>();
		Object[] object = new Object[2];
		object[0] = "1";
		object[1] = "A.G Mumbai";
		result.add(object);
		Object[] object1 = new Object[2];
		object1[0] = "2";
		object1[1] = "A.G  Nagpur";
		result.add(object1);
		
		
		/*Object[] object2 = new Object[2];
		object2[0] = "3";
		object2[1] = "Zilla Parishad";
		result.add(object2);*/
		
		
		
		Object[] object3 = new Object[2];
		object3[0] = "3";
		object3[1] = "MJP";
		result.add(object3);
		Object[] object4 = new Object[2];
		object4[0] = "4";
		object4[1] = "Not Applicable";
		result.add(object4);
		
		Object[] object5 = new Object[2];
		object5[0] = "5";
		object5[1] = "Others";
		result.add(object5);
		return result;
	}
	// protected final Log logger = LogFactory.getLog(getClass());
	@Override
	public List<Object[]> getDcpsAccnMaintainby() {
		List<Object[]> result = new ArrayList<Object[]>();
		
		
		/*Object[] object = new Object[2];
		object[0] = "1";
		object[1] = "a/C Maintained by Zilla Parishad";
		result.add(object);
		*/
		Object[] object2 = new Object[2];
		object2[0] = "1";
		object2[1] = "a/C Maintained by MJP";
		result.add(object2);
		
		Object[] object1 = new Object[2];
		object1[0] = "2";
		object1[1] = "a/C Maintained by Others";
		result.add(object1);
		return result;
	}
	
	@Override
	public List<MstCadreGroupEntity> getGISGroup() {
		return mstEmployeeRepo.getGISGroup();
	}
	
	@Override
	public List<Object[]> getGISApplicable() {
		List<Object[]> result = new ArrayList<Object[]>();

		Object[] temp = { "test", "NA", "Central Govt (CGEGIS)", "I.A.S (GIS)", "I.F.S(GIS)", "I.P.S (GIS)", "ZP(GIS)",
				"MJP(GIS)", "State Govt (GIS)", "Other" };
		for (int i = 1; i < temp.length; i++) {
			Object[] object2 = new Object[2];
			object2[0] = i;
			object2[1] = temp[i];
			result.add(object2);
		}
		return result;
	}
	
	@Override
	public List<Object[]> getRelation() {
		List<Object[]> result = new ArrayList<Object[]>();
		Object[] temp = { "Husband", "Wife", "Son", "Daughter", "Other", "Father", "Mother", "Brother" };
		for (int i = 0; i < temp.length; i++) {
			Object[] object2 = new Object[2];
			object2[0] = temp[i];
			object2[1] = temp[i];
			result.add(object2);
		}
		return result;
	}
	@Override
	public List<Object[]> findEmployeeConfigurationGetSixPayScale(int payCommission) {
		// TODO Auto-generated method stub
		return mstEmployeeRepo.findEmployeeConfigurationGetSixPayScale(payCommission);
	}
	@Override
	public List<Object[]> findEmployeeConfigurationGetpayscale(int payCommission) {
		// TODO Auto-generated method stub
		List<Object[]> deptEligibilityForAllowAndDeductEntity = mstEmployeeRepo.getSvnPayscale();
		return deptEligibilityForAllowAndDeductEntity;
	}
	@Override
	public List<Object[]> findEmployeeConfigurationGetCurrentPost(int designationId, String userName, Object object) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public List<Object[]> getCadreGroupMstData(String locale, String strCadreId) {
		List<Object[]> lstprop = mstEmployeeRepo.getgroupname(strCadreId);
		return lstprop;
	}
	@Override
	public List<MstEmployeeModel> getCadreGroupMstDataNew(String cadreid, String dob) {
		List<MstEmployeeModel> lstlstDeptEligibilityForAllowAndDeductEntity = new ArrayList<>();
		List<Object[]> lstObj = mstEmployeeRepo.getCadreGroupMstDataNew(cadreid);
		Integer ag = null;
		int age = 0;
		for (Object obj[] : lstObj) {
			MstEmployeeModel mstEmployeeModel = new MstEmployeeModel();
			ag = (Integer) obj[1];

			age = ag.intValue();
			SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
			Date birthdt = null;
			try {
				birthdt = sd.parse(dob);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Calendar cal = Calendar.getInstance();
			cal.setTime(birthdt);
			int yer = cal.getTime().getYear();
			yer = yer + 1900;
			Date dobt = cal.getTime();
			Date enhFamPensDate = null;
			if (dobt.getDate() == 1 && dobt.getMonth() == 0) {
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(birthdt);
				calendar.set(Calendar.YEAR, yer + age - 1);
				calendar.set(Calendar.MONTH, 11);
				calendar.set(Calendar.DATE, 31);
				// Calendar calendar1 = Calendar.getInstance();
				enhFamPensDate = calendar.getTime();
			} else if (dobt.getDate() == 1 && dobt.getMonth() != 0) {
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(birthdt);
				int reage = yer + age;
				calendar.set(Calendar.YEAR, yer + age);
				if (dobt.getMonth() == 2) {
					if (reage % 4 == 0) {
						calendar.set(Calendar.MONTH, 0);
						calendar.set(Calendar.DATE, 29);
					} else if (reage % 4 != 0) {
						calendar.set(Calendar.MONTH, 0);
						calendar.set(Calendar.DATE, 28);
					}
				} else {
					calendar.set(Calendar.MONTH, dobt.getMonth()-1);
					int day = calendar.getActualMaximum(Calendar.DATE);
					calendar.set(Calendar.DATE, day);
				}

				enhFamPensDate = calendar.getTime();
			} else {
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(birthdt);
				calendar.set(Calendar.YEAR, yer + age);
				if (dobt.getMonth() == 1) {
					int reage = yer + age;
					calendar.set(Calendar.YEAR, yer + age);
					if (reage % 4 == 0) {
						calendar.set(Calendar.MONTH, 1);
						calendar.set(Calendar.DATE, 29);
					} else {
						calendar.set(Calendar.MONTH, 1);
						calendar.set(Calendar.DATE, 28);
					}
				} else {
					calendar.set(Calendar.MONTH, dobt.getMonth());
					int day = calendar.getActualMaximum(Calendar.DATE);
					calendar.set(Calendar.DATE, day);
				}

				enhFamPensDate = calendar.getTime();
			}

			mstEmployeeModel.setSuperAnnDate(enhFamPensDate);
			mstEmployeeModel.setEmpServiceEndDate(enhFamPensDate);
			lstlstDeptEligibilityForAllowAndDeductEntity.add(mstEmployeeModel);
		}
		return lstlstDeptEligibilityForAllowAndDeductEntity;
	}
	@Override
	public List<Object[]> findEmployeeConfigurationpayScaleSeven(int payScaleSeven) {
		return mstEmployeeRepo.findEmployeeConfigurationpayScaleSeven(payScaleSeven);
	}
	@Override
	public List<AppoinmentEntity> getAppoitnment(String language) {
		// TODO Auto-generated method stub
		return mstEmployeeRepo.getAppoitnment(language);
	}
	@Override
	public List<Object[]> findEmployeeConfigurationGetsvnbasicpay(String payscale) {
		List<Object[]> deptEligibilityForAllowAndDeductEntity = mstEmployeeRepo.getSvnPcData(payscale);
		return deptEligibilityForAllowAndDeductEntity;
	}
	@Override
	public List<MstDesignationEntity> getDesignationMstData(String locale, long locId) {
		// TODO Auto-generated method stub
		long fielddeptId = mstEmployeeRepo.getFieldDeptId(locId);
		List<Object[]> lstprop = mstEmployeeRepo.getDesignationMstData(fielddeptId);
		List<MstDesignationEntity> lstObj = new ArrayList<>();
		if (!lstprop.isEmpty()) {
			for (Object[] objLst : lstprop) {
				MstDesignationEntity obj = new MstDesignationEntity();
				obj.setDesginationId(StringHelperUtils.isNullBigInteger(objLst[0]).longValue());
				obj.setDesgination(StringHelperUtils.isNullString(objLst[1]));
				lstObj.add(obj);
			}
		}
		return lstObj;
	}
	


}