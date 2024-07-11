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
import com.mahait.gov.in.entity.MstCadreGroupEntity;
import com.mahait.gov.in.model.DDOScreenModel;
import com.mahait.gov.in.model.MstCadreModel;
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
	public List<DDOScreenModel> findDDOScreenDataTable(String locale, String ddoCode) {
		List<Object[]> lstprop = null;
		try {
			lstprop = mstEmployeeRepo.findDDOScreenDataTable(ddoCode);
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
	public List<MstCadreModel> getCadreMstData(String locale) {
		List<Object[]> lstprop = mstEmployeeRepo.getCadreMstData();
		List<MstCadreModel> lstObj = new ArrayList<>();
		if (!lstprop.isEmpty()) {
			for (Object[] objLst : lstprop) {
				MstCadreModel obj = new MstCadreModel();
				obj.setCadreId(StringHelperUtils.isNullInt(objLst[0]));
				if (locale.equals("en")) {
					/* obj.setFieldDepartmrnt(StringHelperUtils.isNullString(objLst[1])); */
					/* obj.setCadreGroup(StringHelperUtils.isNullString(objLst[3])); */
					obj.setCadreGroup(StringHelperUtils.isNullString(objLst[1]));
				} else {
					/* obj.setFieldDepartmrnt(StringHelperUtils.isNullString(objLst[2])); */
					/* obj.setCadreGroup(StringHelperUtils.isNullString(objLst[4])); */
					obj.setCadreGroup(StringHelperUtils.isNullString(objLst[2]));
				}
				obj.setCadreCode(StringHelperUtils.isNullInt(objLst[3]));
				obj.setCadreDescription(StringHelperUtils.isNullString(objLst[4]));
				if (objLst[7] != null && objLst[7].equals('2')) {
					obj.setWhetherMinisterial("Yes");
				} else if (objLst[7] != null && objLst[7].equals('3')) {
					obj.setWhetherMinisterial("No");
				}
				obj.setSuperAnnuationAge(StringHelperUtils.isNullBigDecimal(objLst[6]));
				obj.setIsActive(StringHelperUtils.isNullInt(Integer.parseInt(String.valueOf(objLst[7]))));

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


}