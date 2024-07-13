package com.mahait.gov.in.service;

import java.math.BigInteger;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mahait.gov.in.entity.DdoOffice;
import com.mahait.gov.in.entity.EmployeeAllowDeducComponentAmtEntity;
import com.mahait.gov.in.entity.LoanEmployeeDtlsEntity;
import com.mahait.gov.in.entity.MstEmployeeEntity;
import com.mahait.gov.in.repository.MstEmployeeRepo;

@Service
@Transactional
@PropertySource(value = { "classpath:application.properties" })
public class MstEmployeeServiceImpl implements MstEmployeeService {
	@PersistenceContext
	EntityManager entityManager;
	
	@Autowired
	private MstEmployeeRepo mstEmployeeRepo;
	

	@Override
	public DdoOffice findAllGroup(String ddoCode) {
		return mstEmployeeRepo.findAllGroup(ddoCode);
	}

	public List<MstEmployeeEntity> findAllWorkingEmployeeByDDOCodeAndBillGroup(String ddoCode, BigInteger billGroupId,
			int month, int year) {
		return mstEmployeeRepo.findAllWorkingEmployeeByDDOCodeAndBillGroup(ddoCode, billGroupId, month, year);
	}

	@Override
	public int getpayCommissionAgainstEmployee(String sevaarthId) {
		return mstEmployeeRepo.getpayCommissionAgainstEmployee(sevaarthId);
	}

	@Override
	public List<Object[]> employeeAllowDeduction(String sevaarthId) {
		return mstEmployeeRepo.findEmployeeAllowanceDeduction(sevaarthId);

	}

	public EmployeeAllowDeducComponentAmtEntity findGRPComponentsData(String sevaarthId, int allowDedCode) {
		return mstEmployeeRepo.findGRPComponentsData(sevaarthId, allowDedCode);
	}
	
	@Override
	public LoanEmployeeDtlsEntity findGPFADetails(String sevaarthid, int commoncodeComponentGpfaCode) {
		return mstEmployeeRepo.findGPFADetails(sevaarthid, commoncodeComponentGpfaCode);
	}

	@Override
	public LoanEmployeeDtlsEntity findGPFAdvDetails(String sevaarthid, int commoncodeComponentGpfaCode) {
		return mstEmployeeRepo.findGPFAdvDetails(sevaarthid, commoncodeComponentGpfaCode);
	}
	
	/*

	

	@Autowired
	private Environment environment;


	@Autowired
	private AnnualIncrementService annualIncrementService;

	// protected final Log logger = LogFactory.getLog(getClass());

	@Override
	public List<MstEmployeeEntity> findAllEmployeeByDDOCode(String ddoCode) {
		return mstEmployeeRepo.findAllEmployeeByDDOCode(ddoCode);
	}

	@Override
	public String saveEmployeeBillGroup(String sevaarthId, int billGroupId) {
		String saveId = mstEmployeeRepo.saveEmployeeBillGroup(sevaarthId, billGroupId);
		return saveId;
	}

	@Override
	public List<MstEmployeeModel> findAllEmployees() {
		List<Object[]> lstprop = mstEmployeeRepo.findAllEmployees();
		List<MstEmployeeModel> lstObj = new ArrayList<>();
		if (!lstprop.isEmpty()) {
			for (Object[] objLst : lstprop) {
				MstEmployeeModel obj = new MstEmployeeModel();
				obj.setSevaarthId(StringHelperUtils.isNullString(objLst[0]));
				obj.setEmployeeFullName(StringHelperUtils.isNullString(objLst[1]));
				obj.setDesignationName(StringHelperUtils.isNullString(objLst[2]));
				obj.setDepartmentNameEn(StringHelperUtils.isNullString(objLst[3]));
				obj.setEmployeeId(StringHelperUtils.isNullInt(objLst[4]));

				lstObj.add(obj);
			}
		}
		return lstObj;
	}

	@Override
	public List<MstEmployeeModel> findAllEmployeesByDDOName(String ddoCode) {
		List<Object[]> lstprop = mstEmployeeRepo.findAllEmployeesByDDOName(ddoCode);
		List<MstEmployeeModel> lstObj = new ArrayList<>();
		if (!lstprop.isEmpty()) {
			for (Object[] objLst : lstprop) {
				MstEmployeeModel obj = new MstEmployeeModel();
				obj.setSevaarthId(StringHelperUtils.isNullString(objLst[0]));
				obj.setEmployeeFullName(StringHelperUtils.isNullString(objLst[1]));
				obj.setDesignationName(StringHelperUtils.isNullString(objLst[2]));
				obj.setDepartmentNameEn(StringHelperUtils.isNullString(objLst[3]));
				obj.setEmployeeId(StringHelperUtils.isNullInt(objLst[4]));
				obj.setPayCommissionCode(StringHelperUtils.isNullInt(objLst[5]));
				obj.setPayCommissionName(StringHelperUtils.isNullString(objLst[6]));
				obj.setEmpServiceEndDate(StringHelperUtils.isNullDate(objLst[8]));
				obj.setBillDesc(StringHelperUtils.isNullString(objLst[9]));
				if (objLst[7] != null && !objLst[7].equals("")) {
					if (objLst[7].equals('Y')) {
						obj.setDcpsgpfflag("DCPS");
					} else {
						obj.setDcpsgpfflag("GPF");
					}
				}
				lstObj.add(obj);
			}
		}
		return lstObj;
	}

	@Override
	public MstEmployeeEntity findMstEmpByBillGroupIdForReject(int id) {
		MstEmployeeEntity objDeptForReject = mstEmployeeRepo.findMstEmpByBillGroupId(id);
		if (objDeptForReject != null) {
			objDeptForReject.setBillGroupId(null);
			mstEmployeeRepo.updateEmpBillStatusStatus(objDeptForReject);
		}
		return objDeptForReject;
	}

	@Override
	public List<Object[]> findAllGroupAndSuperAnnuationAge(int cadreId) {
		List<Object[]> deptEligibilityForAllowAndDeductEntity = mstEmployeeRepo
				.findAllGroupAndSuperAnnuationAge(cadreId);
		return deptEligibilityForAllowAndDeductEntity;
	}

	public List<Object[]> findPayCommissionAgaintPayScaleList(int cadreId) {
		List<Object[]> deptEligibilityForAllowAndDeductEntity = mstEmployeeRepo
				.findPayCommissionAgaintPayScaleList(cadreId);
		return deptEligibilityForAllowAndDeductEntity;
	}

	public List<Object[]> findEmployeeConfigurationGetCurrentPost(int designationId, String ddocode,
			String currpostcode) {
		List<Object[]> deptEligibilityForAllowAndDeductEntity = mstEmployeeRepo
				.findEmployeeConfigurationGetCurrentPost(designationId, ddocode, currpostcode);
		return deptEligibilityForAllowAndDeductEntity;
	}

	public List<Object[]> findEmployeeConfigurationGetGradePay(int payScaleId) {
		List<Object[]> deptEligibilityForAllowAndDeductEntity = mstEmployeeRepo
				.findEmployeeConfigurationGetGradePay(payScaleId);
		return deptEligibilityForAllowAndDeductEntity;
	}

	@Override
	public int saveEmployeeConfiguration(MstEmployeeModel mstEmployeeModel, MultipartFile[] files) {
		MstEmployeeEntity objEntity = new MstEmployeeEntity();

		// objEntity.setEmployeeId(mstEmployeeModel.getEmployeeId());
		// objEntity.setSevaarthId(mstEmployeeModel.getSevaarthId());
		if (mstEmployeeModel.getSevaarthId() == null || mstEmployeeModel.getSevaarthId().equalsIgnoreCase(""))
			if (mstEmployeeModel.getSevaarthId() == null) {
				String sevaarthGenration = getCrearteSevaartIh(mstEmployeeModel);
				objEntity.setSevaarthId(sevaarthGenration);
			} else {
				objEntity.setSevaarthId(mstEmployeeModel.getSevaarthId());
			}
		// Employee Details Start
		objEntity.setUidNo(mstEmployeeModel.getUidNo());
		objEntity.setEidNo(mstEmployeeModel.getEidNo());
		objEntity.setSalutation(mstEmployeeModel.getSalutation());
		objEntity.setEmployeeFullNameEn(mstEmployeeModel.getEmployeeFullNameEn().toUpperCase());
		objEntity.setEmployeeFNameEn(mstEmployeeModel.getEmployeeFNameEn().toUpperCase());
		objEntity.setEmployeeMNameEn(mstEmployeeModel.getEmployeeMNameEn().toUpperCase());
		objEntity.setEmployeeLNameEn(mstEmployeeModel.getEmployeeLNameEn().toUpperCase());
		objEntity.setEmployeeFullNameMr(mstEmployeeModel.getEmployeeFullNameMr());
		objEntity.setEmployeeFNameMr(mstEmployeeModel.getEmployeeFNameMr());
		objEntity.setEmployeeLNameMr(mstEmployeeModel.getEmployeeLNameMr());
		objEntity.setEmployeeMotherName(mstEmployeeModel.getEmployeeMotherName());
		objEntity.setBuckleNo(mstEmployeeModel.getBuckleNo());
		if (mstEmployeeModel.getGender() == '1') {
			objEntity.setGender('M');
		} else if (mstEmployeeModel.getGender() == '2') {
			objEntity.setGender('F');
		} else {
			objEntity.setGender('T');
		}
		objEntity.setReligionCode(mstEmployeeModel.getReligionCode());
		objEntity.setMaritalStatus(mstEmployeeModel.getMaritalStatus());
		objEntity.setEmployeeMNameMr(mstEmployeeModel.getEmployeeMNameMr());
		objEntity.setDob(mstEmployeeModel.getDob());
		objEntity.setDoj(mstEmployeeModel.getDoj());
		objEntity.setAddress1(mstEmployeeModel.getAddress1().toUpperCase());
		objEntity.setAddress2(mstEmployeeModel.getAddress2().toUpperCase());
		// objEntity.setAddress3(mstEmployeeModel.getAddress3().toUpperCase());
		// objEntity.setLocality(mstEmployeeModel.getLocality());
		objEntity.setStateCode(mstEmployeeModel.getStateCode());
		objEntity.setDistrictCode(mstEmployeeModel.getDistrictCode());
		// objEntity.setVillageName(mstEmployeeModel.getVillageName().toUpperCase());
		objEntity.setPinCode(mstEmployeeModel.getPinCode());
		objEntity.setPhysicallyHandicapped(mstEmployeeModel.getPhysicallyHandicapped());
		objEntity.setMobileNo1(mstEmployeeModel.getMobileNo1());
		objEntity.setEmailId(mstEmployeeModel.getEmailId());
		objEntity.setPanNo(mstEmployeeModel.getPanNo().toUpperCase());

		// Employee Details End

		// Department Details Start
		objEntity.setParentAdminDepartmentId(mstEmployeeModel.getParentAdminDepartmentId());
		objEntity.setParentFieldDepartmentId(mstEmployeeModel.getParentFieldDepartmentId());
		objEntity.setSubDeptId(mstEmployeeModel.getParentFieldDepartmentId().intValue());
		objEntity.setSubCorporationId(mstEmployeeModel.getSubCorporationId());
		objEntity.setAdminDepartmentCode(mstEmployeeModel.getParentAdminDepartmentId());
		objEntity.setFieldDepartmentCode(mstEmployeeModel.getFieldDepartmentId());
		objEntity.setIsChangeParentDepartment(mstEmployeeModel.getIsChangeParentDepartment());
		objEntity.setReasonForChngParentFieldDept(mstEmployeeModel.getReasonForChngParentFieldDept());
		objEntity.setCadreCode(mstEmployeeModel.getCadreId());
		objEntity.setEmpClass(mstEmployeeModel.getEmpClass());
		if (mstEmployeeModel.getSuperannuationage() != null)
			objEntity.setSuperAnnAge(Integer.valueOf(mstEmployeeModel.getSuperannuationage()));
		objEntity.setEmpServiceEndDate(mstEmployeeModel.getSuperAnnDate()); // by default set to retirement date added
																			// by sudhir
		objEntity.setSuperAnnDate(mstEmployeeModel.getSuperAnnDate());
		objEntity.setPayCommissionCode(mstEmployeeModel.getPayCommissionCode());
		objEntity.setFirstDesignationCode(mstEmployeeModel.getFirstDesignationId());
		objEntity.setDesignationCode(mstEmployeeModel.getDesignationId());
		objEntity.setPayscalelevelId(mstEmployeeModel.getPayscalelevelId());
		if (mstEmployeeModel.getPayscalelevelId() != null)
			objEntity.setSevenPcLevel(Integer.parseInt(mstEmployeeModel.getPayscalelevelId()));
		else
			objEntity.setSevenPcLevel(0);

		objEntity.setSvnthpaybasic(mstEmployeeModel.getSvnthpaybasic());

		objEntity.setPayScaleCode(mstEmployeeModel.getPayScaleCode());
		objEntity.setPayInPayBand(mstEmployeeModel.getPayInPayBand());
		objEntity.setGradePay(mstEmployeeModel.getGradePay());

		// objEntity.setBasicPay(mstEmployeeModel.getBasicPay() == null ? 0 :
		// mstEmployeeModel.getBasicPay().doubleValue());

		if (objEntity.getPayCommissionCode() == 2) {
			objEntity.setBasicPay(
					mstEmployeeModel.getBasicPay() == null ? 0 : mstEmployeeModel.getBasicPay().doubleValue());
		} else {
			objEntity.setSevenPcBasic(
					mstEmployeeModel.getSevenPcBasic() == null ? 0 : mstEmployeeModel.getSevenPcBasic().doubleValue());
		}

		objEntity.setPostdetailid(mstEmployeeModel.getPostdetailid());
		objEntity.setDepartmentNameEn(mstEmployeeModel.getDepartmentNameEn());
		objEntity.setDtInitialAppointmentParentInst(mstEmployeeModel.getDtInitialAppointmentParentInst());
		objEntity.setInstituteAdd(mstEmployeeModel.getInstituteAdd());
		objEntity.setInstName(mstEmployeeModel.getInstName());
		objEntity.setMobileNo2(mstEmployeeModel.getMobileNo2());
		objEntity.setInstemail(mstEmployeeModel.getInstemail());
		objEntity.setCityClass(mstEmployeeModel.getCityClass());
		objEntity.setDtJoinCurrentPost(mstEmployeeModel.getDtJoinCurrentPost());
		objEntity.setRemark(mstEmployeeModel.getRemark());
		objEntity.setIndiApproveOrderNo(mstEmployeeModel.getIndiApproveOrderNo());
		objEntity.setApprovalByDdoDate(mstEmployeeModel.getApprovalByDdoDate());
		objEntity.setHraBasic(mstEmployeeModel.getHraBasic());
		// Department Details End

		// Bank/DCPS/NPS/GPF Details Start
		objEntity.setBankCode(mstEmployeeModel.getBankId());
		objEntity.setIfscCode(mstEmployeeModel.getIfscCode());
		objEntity.setBankAcntNo(mstEmployeeModel.getBankAcntNo());
		objEntity.setBankBranchCode(mstEmployeeModel.getBankBranchId());
		objEntity.setDcpsgpfflag(mstEmployeeModel.getDcpsgpfflag());
		objEntity.setDcpsaccountmaintainby(mstEmployeeModel.getDcpsaccountmaintainby());
		objEntity.setPranNo(mstEmployeeModel.getPranNo());
		objEntity.setAccountmaintainby(mstEmployeeModel.getAccountmaintainby());
		objEntity.setPfseries(mstEmployeeModel.getPfseries());
		objEntity.setPfacno(mstEmployeeModel.getPfacno());
		objEntity.setPfdescription(mstEmployeeModel.getPfdescription());

		// Bank/DCPS/NPS/GPF Details End

		// GIS Details Start
		objEntity.setGisapplicable(mstEmployeeModel.getGisapplicable());
		objEntity.setGisgroup(mstEmployeeModel.getGisgroup());
		objEntity.setMembership_date(mstEmployeeModel.getMembership_date());
		objEntity.setGisRemark(mstEmployeeModel.getGisRemark());
		objEntity.setGiscatagory(mstEmployeeModel.getGiscatagory());
		objEntity.setBegisCatg(mstEmployeeModel.getBegisCatg());

		// GIS Details End

		// DCPS/NPS Nominee Details Start

		String[] lArrNomName = mstEmployeeModel.getStrArrNomineeName().split("~");
		String[] lArrAddress1 = mstEmployeeModel.getStrArrAddress().split("~");
		String[] lArrDateOfBirth = mstEmployeeModel.getStrArrDob().split("~");
		String[] lArrPercentShare = mstEmployeeModel.getStrArrPercentShare().split("~");
		String[] lArrRelationship = mstEmployeeModel.getStrArrRelationship().split("~");

		MstNomineeDetailsEntity[] lArrNomineeDtls = new MstNomineeDetailsEntity[lArrNomName.length];

		for (int i = 0; i < lArrNomName.length; i++) {
			if (!lArrNomName[i].equals("")) {
				MstNomineeDetailsEntity lObjNomineeDtls = new MstNomineeDetailsEntity();

				// lObjNomineeDtls.setDcpsEmpId(lObjEmpData);
				lObjNomineeDtls.setNomineename(lArrNomName[i]);
				lObjNomineeDtls.setNomineeaddress(lArrAddress1[i]);
				Date dtBirthDate = null;

				if (mstEmployeeModel.getStrArrDob() != null && !"".equals(mstEmployeeModel.getStrArrDob().trim())) {
					MstEmployeeModel mstEmployeeModel1 = new MstEmployeeModel();
					// String pattern = "yyyy-MM-dd";
					SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
					try {
						Date date = formatter.parse(lArrDateOfBirth[i]);
						mstEmployeeModel1.setRdob(date);
						dtBirthDate = mstEmployeeModel1.getRdob();
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
					}

				}

				lObjNomineeDtls.setDob(dtBirthDate);
				long lLngPercentShare = Long.parseLong(lArrPercentShare[i]);
				lObjNomineeDtls.setPercent_share(Integer.parseInt(lArrPercentShare[i]));
				lObjNomineeDtls.setRelation(lArrRelationship[i]);
				lObjNomineeDtls.setCreateddate(new Date());
				lObjNomineeDtls.setCreatedid(mstEmployeeModel.getCreatedUserId());
				lObjNomineeDtls.setIsactive("Y");
				lObjNomineeDtls.setUpdatedate(mstEmployeeModel.getUpdatedDate());
				lObjNomineeDtls.setUpdateid(mstEmployeeModel.getUpdatedUserId());
				// lObjNomineeDtls.setEmployeeId(objEntity.getEmployeeId());

				lArrNomineeDtls[i] = lObjNomineeDtls;
			}

		}

		// DCPS/NPS Nominee Details End

		objEntity.setEmpType("1");
		objEntity.setIsMappedWithNps('0');
		objEntity.setDdoCode(mstEmployeeModel.getDdoCode());
		objEntity.setBillGroupId(mstEmployeeModel.getBillgroupId());
		objEntity.setIsActive(3);
		objEntity.setSignatureAttachmentId(mstEmployeeModel.getSignatureAttachmentId());
		objEntity.setCreatedUserId(mstEmployeeModel.getCreatedUserId());
		objEntity.setCreatedDate(new Date());

		
		 * objEntity.setAccMaintainedByOther(mstEmployeeModel.getAccMaintainedByOther())
		 * ; objEntity.setBloodGroup(mstEmployeeModel.getBloodGroup());
		 * objEntity.setLandlineNo(mstEmployeeModel.getLandlineNo());
		 * objEntity.setVillageCode(mstEmployeeModel.getVillageCode());
		 * objEntity.setTalukaCode(mstEmployeeModel.getVillageCode());
		 * objEntity.setCountryCode(mstEmployeeModel.getCountryCode());
		 * objEntity.setCityClass(mstEmployeeModel.getCityClass()); //
		 * objEntity.setAppointmentDate(mstEmployeeModel.getAppointmentDate());
		 * objEntity.setUserId(mstEmployeeModel.getUserId());
		 * objEntity.setCurrentOfficeCode(mstEmployeeModel.getCurrentOfficeId());
		 * objEntity.setDdoCode(mstEmployeeModel.getDdoCode());
		 * objEntity.setBillGroupId(mstEmployeeModel.getBillgroupId());
		 * objEntity.setPercentageOfBasic(mstEmployeeModel.getPercentageOfBasic());
		 * objEntity.setHeadActCode(mstEmployeeModel.getHeadActCode());
		 * objEntity.setEmployeeType(mstEmployeeModel.getEmployeeType());
		 * objEntity.setIsActive(3);
		 * //objEntity.setEmpServiceEndDate(mstEmployeeModel.getEmpServiceEndDate());
		 * objEntity.setWithEffectFromDate(mstEmployeeModel.getWithEffectFromDate());
		 * objEntity.setGradeId(mstEmployeeModel.getGradeId()); //
		 * objEntity.setPhotoAttachmentId(mstEmployeeModel.getPhotoAttachmentId());
		 * objEntity.setSevenPcBasic(mstEmployeeModel.getSevenPcBasic());
		 * objEntity.setAdminDepartmentCode(BigInteger.valueOf(51l));
		 * objEntity.setEmpClass(mstEmployeeModel.getEmpClass());
		 

		// Extra
		
		 * private String employeeFullName; private String designationName; private
		 * String departmentNameEn;
		 

		// Start 1st Dec 2020 : Nominee Dtls Logic

		// End 1st Dec 2020 : Nominee Dtls Logic
		List reuslt = mstEmployeeRepo.saveEmployeeConfiguration(objEntity, mstEmployeeModel, lArrNomineeDtls);

		String[] saveimage = savePhotoSignature(files, mstEmployeeModel.getDeptNm(), (Integer) reuslt.get(1));
		String imgres = mstEmployeeRepo.updateImagePath(saveimage[0].toString(), saveimage[1].toString(),
				(Integer) reuslt.get(1));

		Serializable id = (Integer) reuslt.get(0);

		// Annual Increment entry for employee Start
		EmployeeIncrementEntity employeeIncrementEntity = new EmployeeIncrementEntity();
		employeeIncrementEntity.setSevaarthId(objEntity.getSevaarthId());
		employeeIncrementEntity.setEmployeeId(objEntity.getEmployeeId());
		employeeIncrementEntity.setCurrentBasicPay(0);
		employeeIncrementEntity.setCurrentBasicLevelId(0);
		employeeIncrementEntity.setPreBasicPay(0);
		employeeIncrementEntity.setIncrementOrderNo("0");
		employeeIncrementEntity.setIncrementOrderDate(new Date());
		if (objEntity.getPayCommissionCode() == 8) {
			employeeIncrementEntity.setIncrementBasicPaySal(objEntity.getSevenPcBasic().intValue());
		} else {
			employeeIncrementEntity.setIncrementBasicPaySal(objEntity.getBasicPay().intValue());
		}
		employeeIncrementEntity.setIncrementBasicLevelId(mstEmployeeModel.getSvnthpaybasic());
		employeeIncrementEntity.setEffective_from_date(new Date());
		employeeIncrementEntity.setTo_increment_date(new Date());
		employeeIncrementEntity.setCreatedDate(new Date());
		employeeIncrementEntity.setUpdatedDate(new Date());
		employeeIncrementEntity.setRemark(null);
		employeeIncrementEntity.setIsActive('1');
		employeeIncrementEntity.setMonth(1);
		employeeIncrementEntity.setCreatedUserId(1);
		employeeIncrementEntity.setUpdatedUserId(1);
		employeeIncrementEntity.setDdoCode(objEntity.getDdoCode());
		// Annual Increment entry for employee End
		mstEmployeeRepo.saveEmployeeIncrement(employeeIncrementEntity);

		return (int) id;
	}

	@Override
	public int updateEmployeeConfiguration(MstEmployeeModel mstEmployeeModel, MultipartFile[] files) {
		MstEmployeeEntity objEntity = mstEmployeeRepo.findbyemplid(mstEmployeeModel.getEmployeeId());
		Session currentSession = entityManager.unwrap(Session.class);
		// objEntity.setEmployeeId(mstEmployeeModel.getEmployeeId());
		// objEntity.setSevaarthId(mstEmployeeModel.getSevaarthId());
		// objEntity.setSevaarthId("0");
		MstNomineeDetailsEntity lObjNomineeDtls = null;
		MstNomineeDetailsEntity[] lArrNomineeDtls = null;
		if (objEntity != null) {
			objEntity.setUidNo(mstEmployeeModel.getUidNo());
			objEntity.setEidNo(mstEmployeeModel.getEidNo());
			objEntity.setSalutation(mstEmployeeModel.getSalutation());
			objEntity.setEmployeeFullNameEn(mstEmployeeModel.getEmployeeFullNameEn().toUpperCase());
			objEntity.setEmployeeFNameEn(mstEmployeeModel.getEmployeeFNameEn().toUpperCase());
			objEntity.setEmployeeMNameEn(mstEmployeeModel.getEmployeeMNameEn().toUpperCase());
			objEntity.setEmployeeLNameEn(mstEmployeeModel.getEmployeeLNameEn().toUpperCase());
			objEntity.setEmployeeFullNameMr(mstEmployeeModel.getEmployeeFullNameMr());
			objEntity.setEmployeeFNameMr(mstEmployeeModel.getEmployeeFNameMr());
			objEntity.setEmployeeLNameMr(mstEmployeeModel.getEmployeeLNameMr());
			objEntity.setEmployeeMotherName(mstEmployeeModel.getEmployeeMotherName());
			objEntity.setBuckleNo(mstEmployeeModel.getBuckleNo());
			if (mstEmployeeModel.getGender() == '1') {
				objEntity.setGender('M');
			} else if (mstEmployeeModel.getGender() == '2') {
				objEntity.setGender('F');
			} else {
				objEntity.setGender('T');
			}
			objEntity.setReligionCode(mstEmployeeModel.getReligionCode());
			objEntity.setMaritalStatus(mstEmployeeModel.getMaritalStatus());
			objEntity.setEmployeeMNameMr(mstEmployeeModel.getEmployeeMNameMr());
			objEntity.setDob(mstEmployeeModel.getDob());
			objEntity.setDoj(mstEmployeeModel.getDoj());
			objEntity.setAddress1(mstEmployeeModel.getAddress1().toUpperCase());
			objEntity.setAddress2(mstEmployeeModel.getAddress2().toUpperCase());
			
			 * objEntity.setAddress3(mstEmployeeModel.getAddress3().toUpperCase());
			 * objEntity.setLocality(mstEmployeeModel.getLocality());
			 
			objEntity.setStateCode(mstEmployeeModel.getStateCode());
			objEntity.setDistrictCode(mstEmployeeModel.getDistrictCode());
			// objEntity.setVillageName(mstEmployeeModel.getVillageName().toUpperCase());
			objEntity.setPinCode(mstEmployeeModel.getPinCode());
			objEntity.setPhysicallyHandicapped(mstEmployeeModel.getPhysicallyHandicapped());
			objEntity.setMobileNo1(mstEmployeeModel.getMobileNo1());
			objEntity.setEmailId(mstEmployeeModel.getEmailId());
			objEntity.setPanNo(mstEmployeeModel.getPanNo().toUpperCase());

			// Employee Details End

			// Department Details Start
			objEntity.setParentAdminDepartmentId(mstEmployeeModel.getParentAdminDepartmentId());
			objEntity.setParentFieldDepartmentId(mstEmployeeModel.getParentFieldDepartmentId());
			objEntity.setSubDeptId(mstEmployeeModel.getParentFieldDepartmentId().intValue());
			objEntity.setSubCorporationId(mstEmployeeModel.getSubCorporationId());
			objEntity.setAdminDepartmentCode(mstEmployeeModel.getParentAdminDepartmentId());
			objEntity.setFieldDepartmentCode(mstEmployeeModel.getFieldDepartmentId());
			objEntity.setIsChangeParentDepartment(mstEmployeeModel.getIsChangeParentDepartment());
			objEntity.setReasonForChngParentFieldDept(mstEmployeeModel.getReasonForChngParentFieldDept());
			objEntity.setCadreCode(mstEmployeeModel.getCadreId());
			objEntity.setEmpClass(mstEmployeeModel.getEmpClass());
			objEntity.setSuperAnnAge(Integer.valueOf(mstEmployeeModel.getSuperannuationage()));
			objEntity.setEmpServiceEndDate(mstEmployeeModel.getSuperAnnDate()); // by default set to retirement date
																				// added by sudhir
			objEntity.setSuperAnnDate(mstEmployeeModel.getSuperAnnDate());
			objEntity.setPayCommissionCode(mstEmployeeModel.getPayCommissionCode());
			objEntity.setFirstDesignationCode(mstEmployeeModel.getFirstDesignationId());
			objEntity.setDesignationCode(mstEmployeeModel.getDesignationId());
			objEntity.setPayscalelevelId(mstEmployeeModel.getPayscalelevelId());
			if (mstEmployeeModel.getPayscalelevelId() != null)
				objEntity.setSevenPcLevel(Integer.parseInt(mstEmployeeModel.getPayscalelevelId()));
			else
				objEntity.setSevenPcLevel(0);
			objEntity.setSvnthpaybasic(mstEmployeeModel.getSvnthpaybasic());
			objEntity.setPayScaleCode(mstEmployeeModel.getPayScaleCode());
			objEntity.setPayInPayBand(mstEmployeeModel.getPayInPayBand());
			objEntity.setGradePay(mstEmployeeModel.getGradePay());

			if (objEntity.getPayCommissionCode() == 2) {
				objEntity.setBasicPay(
						mstEmployeeModel.getBasicPay() == null ? 0 : mstEmployeeModel.getBasicPay().doubleValue());
			} else {
				objEntity.setSevenPcBasic(mstEmployeeModel.getSevenPcBasic() == null ? 0
						: mstEmployeeModel.getSevenPcBasic().doubleValue());
			}

			objEntity.setPostdetailid(mstEmployeeModel.getPostdetailid());
			objEntity.setDepartmentNameEn(mstEmployeeModel.getDepartmentNameEn());
			objEntity.setDtInitialAppointmentParentInst(mstEmployeeModel.getDtInitialAppointmentParentInst());
			objEntity.setInstituteAdd(mstEmployeeModel.getInstituteAdd());
			objEntity.setInstName(mstEmployeeModel.getInstName());
			objEntity.setMobileNo2(mstEmployeeModel.getMobileNo2());
			objEntity.setInstemail(mstEmployeeModel.getInstemail());
			objEntity.setDtJoinCurrentPost(mstEmployeeModel.getDtJoinCurrentPost());
			objEntity.setRemark(mstEmployeeModel.getRemark());
			objEntity.setCityClass(mstEmployeeModel.getCityClass());
			objEntity.setIndiApproveOrderNo(mstEmployeeModel.getIndiApproveOrderNo());
			objEntity.setApprovalByDdoDate(mstEmployeeModel.getApprovalByDdoDate());
			objEntity.setHraBasic(mstEmployeeModel.getHraBasic());
			// Department Details End

			// Bank/DCPS/NPS/GPF Details Start
			objEntity.setBankCode(mstEmployeeModel.getBankId());
			objEntity.setIfscCode(mstEmployeeModel.getIfscCode());
			objEntity.setBankAcntNo(mstEmployeeModel.getBankAcntNo());
			objEntity.setBankBranchCode(mstEmployeeModel.getBankBranchId());
			objEntity.setDcpsgpfflag(mstEmployeeModel.getDcpsgpfflag());
			objEntity.setDcpsaccountmaintainby(mstEmployeeModel.getDcpsaccountmaintainby());
			objEntity.setPranNo(mstEmployeeModel.getPranNo());
			objEntity.setAccountmaintainby(mstEmployeeModel.getAccountmaintainby());
			objEntity.setPfseries(mstEmployeeModel.getPfseries());
			objEntity.setPfacno(mstEmployeeModel.getPfacno());
			System.out.println("pfdescription---------"+mstEmployeeModel.getPfdescription());
			objEntity.setPfdescription(mstEmployeeModel.getPfdescription());

			// Bank/DCPS/NPS/GPF Details End

			// GIS Details Start
			objEntity.setGisapplicable(mstEmployeeModel.getGisapplicable());
			objEntity.setGisgroup(mstEmployeeModel.getGisgroup());
			objEntity.setMembership_date(mstEmployeeModel.getMembership_date());
			objEntity.setGisRemark(mstEmployeeModel.getGisRemark());
			objEntity.setGiscatagory(mstEmployeeModel.getGiscatagory());
			objEntity.setBegisCatg(mstEmployeeModel.getBegisCatg());
			// GIS Details End

			// DCPS/NPS Nominee Details Start

			String[] lArrNomName = mstEmployeeModel.getStrArrNomineeName().split("~");
			String[] lArrAddress1 = mstEmployeeModel.getStrArrAddress().split("~");
			String[] lArrDateOfBirth = mstEmployeeModel.getStrArrDob().split("~");
			String[] lArrPercentShare = mstEmployeeModel.getStrArrPercentShare().split("~");
			String[] lArrRelationship = mstEmployeeModel.getStrArrRelationship().split("~");

			lArrNomineeDtls = new MstNomineeDetailsEntity[lArrNomName.length];

			for (int i = 0; i < lArrNomName.length; i++) {
				if (!lArrNomName[i].equals("")) {
					lObjNomineeDtls = new MstNomineeDetailsEntity();

					// lObjNomineeDtls.setDcpsEmpId(lObjEmpData);
					lObjNomineeDtls.setNomineename(lArrNomName[i]);
					lObjNomineeDtls.setNomineeaddress(lArrAddress1[i]);
					Date dtBirthDate = null;

					if (mstEmployeeModel.getStrArrDob() != null && !"".equals(mstEmployeeModel.getStrArrDob().trim())) {
						MstEmployeeModel mstEmployeeModel1 = new MstEmployeeModel();
						// String pattern = "yyyy-MM-dd";
						SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
						try {
							Date date = formatter.parse(lArrDateOfBirth[i]);
							mstEmployeeModel1.setRdob(date);
							dtBirthDate = mstEmployeeModel1.getRdob();
						} catch (Exception e) {
							// TODO: handle exception
							e.printStackTrace();
						}

					}

					lObjNomineeDtls.setDob(dtBirthDate);
					long lLngPercentShare = Long.parseLong(lArrPercentShare[i]);
					lObjNomineeDtls.setPercent_share(Integer.parseInt(lArrPercentShare[i]));
					lObjNomineeDtls.setRelation(lArrRelationship[i]);
					lObjNomineeDtls.setCreateddate(new Date());
					lObjNomineeDtls.setCreatedid(mstEmployeeModel.getCreatedUserId());
					lObjNomineeDtls.setIsactive("Y");
					lObjNomineeDtls.setUpdatedate(mstEmployeeModel.getUpdatedDate());
					lObjNomineeDtls.setUpdateid(mstEmployeeModel.getUpdatedUserId());
					// lObjNomineeDtls.setEmployeeId(objEntity.getEmployeeId());

					lArrNomineeDtls[i] = lObjNomineeDtls;
				}

			} 

			// DCPS/NPS Nominee Details End

			objEntity.setEmpType("1");
			objEntity.setIsMappedWithNps('0');
			// objEntity.setDdoCode(mstEmployeeModel.getDdoCode());
			objEntity.setBillGroupId(mstEmployeeModel.getBillgroupId());
			objEntity.setIsActive(3);
			mstEmployeeModel.setIsActive(3);
			objEntity.setSignatureAttachmentId(mstEmployeeModel.getSignatureAttachmentId());
			objEntity.setCreatedUserId(mstEmployeeModel.getCreatedUserId());
			objEntity.setCreatedDate(new Date());

			if (mstEmployeeModel.getSevaarthId() == null || mstEmployeeModel.getSevaarthId().equals("")) {

				String sevaarthGenration = getCrearteSevaartIh(mstEmployeeModel);
				objEntity.setSevaarthId(sevaarthGenration);

			}

		}

		if (mstEmployeeModel.getDcpsid() != null) {
			MstDcpsDetailsEntity objEntity1 = mstEmployeeRepo.findbyDcpsid(mstEmployeeModel.getDcpsid());
			// objEntity1.setDcpsid(mstEmployeeModel.getDcpsid());
			objEntity1.setIsactive(mstEmployeeModel.getDcpsgpfflag());
			objEntity1.setAccountmaintainby(mstEmployeeModel.getDcpsaccountmaintainby());
			objEntity1.setCreateddate(new Date());
			objEntity1.setCreatedid(BigInteger.valueOf(1));
			objEntity1.setPfacno(mstEmployeeModel.getPfacno());
			// objEntity1.setUpdatedate(mstEmployeeModel.getUpdatedDate());
			// objEntity1.setUpdateid(mstEmployeeModel.getUpdatedUserId());
			objEntity1.setEmployeeId(objEntity.getEmployeeId());
			currentSession.update(objEntity1);
		} else if (mstEmployeeModel.getDcpsaccountmaintainby() != null)
			if (!mstEmployeeModel.getDcpsaccountmaintainby().equals("0")) {
				MstDcpsDetailsEntity objEntity1 = new MstDcpsDetailsEntity();
				objEntity1.setIsactive(mstEmployeeModel.getDcpsgpfflag());
				objEntity1.setAccountmaintainby(mstEmployeeModel.getDcpsaccountmaintainby());
				objEntity1.setCreateddate(new Date());
				objEntity1.setCreatedid(BigInteger.valueOf(1));
				objEntity1.setPfacno(mstEmployeeModel.getPfacno());
				// objEntity1.setUpdatedate(mstEmployeeModel.getUpdatedDate());
				// objEntity1.setUpdateid(mstEmployeeModel.getUpdatedUserId());
				objEntity1.setEmployeeId(objEntity.getEmployeeId());
				currentSession.save(objEntity1);
			}

		if (mstEmployeeModel.getGpf_id() != null) {
			MstGpfDetailsEntity objEntity2 = mstEmployeeRepo.findbyGPFid(mstEmployeeModel.getGpf_id());
			// objEntity2.setGpf_id(mstEmployeeModel.getGpf_id());
			objEntity2.setAccountmaintainby(mstEmployeeModel.getAccountmaintainby());
			objEntity2.setCreateddate(new Date());
			objEntity2.setCreatedid(BigInteger.valueOf(1l));
			objEntity2.setIsactive(mstEmployeeModel.getDcpsgpfflag());
			objEntity2.setPfacno(mstEmployeeModel.getPfacno());
			objEntity2.setPfdescription(mstEmployeeModel.getPfdescription());
			// objEntity2.setUpdatedate(mstEmployeeModel.getUpdatedDate());
			objEntity2.setCreatedid(mstEmployeeModel.getUpdatedUserId());
			objEntity2.setCreateddate(new Date());
			// objEntity2.setUpdateid(mstEmployeeModel.getUpdatedUserId());
			objEntity2.setEmployeeId(objEntity.getEmployeeId());
			currentSession.update(objEntity2);
		} else if (mstEmployeeModel.getAccountmaintainby() != null && mstEmployeeModel.getPfacno() != null
				&& mstEmployeeModel.getPfseries() != null)
			if (!mstEmployeeModel.getAccountmaintainby().equals("0") && !mstEmployeeModel.getPfacno().equals("")
					&& !mstEmployeeModel.getPfseries().equals("0")) {
				MstGpfDetailsEntity objEntity2 = new MstGpfDetailsEntity();
				objEntity2.setAccountmaintainby(mstEmployeeModel.getAccountmaintainby());
				objEntity2.setCreateddate(new Date());
				objEntity2.setCreatedid(BigInteger.valueOf(1l));
				objEntity2.setIsactive(mstEmployeeModel.getDcpsgpfflag());
				objEntity2.setPfacno(mstEmployeeModel.getPfacno());
				objEntity2.setPfdescription(mstEmployeeModel.getPfseries());
				objEntity2.setUpdatedate(mstEmployeeModel.getUpdatedDate());
				objEntity2.setUpdateid(mstEmployeeModel.getUpdatedUserId());
				objEntity2.setEmployeeId(objEntity.getEmployeeId());
				currentSession.save(objEntity2);
			}
		if (mstEmployeeModel.getGisid() != null) {
			MstGisdetailsEntity objEntity3 = mstEmployeeRepo.findbyGisid(mstEmployeeModel.getGisid());
			// objEntity3.setGisid(mstEmployeeModel.getGisid());
			objEntity3.setCreateddate(new Date());
			objEntity3.setCreatedid(BigInteger.valueOf(1));
			objEntity3.setGisapplicable(mstEmployeeModel.getGisapplicable());
			objEntity3.setGisgroup(mstEmployeeModel.getGisgroup());
			objEntity3.setIsactive("Y");
			objEntity3.setMembership_date(mstEmployeeModel.getMembership_date());
			// objEntity3.setUpdatedate(mstEmployeeModel.getUpdatedDate());
			// objEntity3.setUpdateid(mstEmployeeModel.getUpdatedUserId());
			objEntity3.setEmployeeId(objEntity.getEmployeeId());
			currentSession.update(objEntity3);
		} else if (mstEmployeeModel.getGisapplicable() != null && mstEmployeeModel.getGisgroup() != null
				&& mstEmployeeModel.getMembership_date() != null)
			if (!mstEmployeeModel.getGisapplicable().equals("0") && !mstEmployeeModel.getGisgroup().equals("0")
					&& !mstEmployeeModel.getMembership_date().equals("0")) {
				MstGisdetailsEntity objEntity3 = new MstGisdetailsEntity();
				objEntity3.setCreateddate(new Date());
				objEntity3.setCreatedid(mstEmployeeModel.getCreatedUserId());
				objEntity3.setGisapplicable(mstEmployeeModel.getGisapplicable());
				objEntity3.setGisgroup(mstEmployeeModel.getGisgroup());
				objEntity3.setIsactive("Y");
				objEntity3.setMembership_date(mstEmployeeModel.getMembership_date());
				objEntity3.setUpdatedate(mstEmployeeModel.getUpdatedDate());
				objEntity3.setUpdateid(mstEmployeeModel.getUpdatedUserId());
				objEntity3.setEmployeeId(objEntity.getEmployeeId());
				currentSession.save(objEntity3);
			}

		// Extra
		
		 * private String employeeFullName; private String designationName; private
		 * String departmentNameEn;
		 

		String[] saveimage = savePhotoSignature(files, mstEmployeeModel.getDeptNm(), mstEmployeeModel.getEmployeeId(),
				mstEmployeeModel.getPhotoAttachmentId(), mstEmployeeModel.getSignatureAttachmentId());
		objEntity.setPhotoAttachmentId(saveimage[0].toString());
		objEntity.setSignatureAttachmentId(saveimage[1]);

		// Serializable id=(Integer)reuslt.get(0);

		Serializable id = mstEmployeeRepo.updateEmployeeConfiguration(objEntity, mstEmployeeModel, lArrNomineeDtls);

		EmployeeIncrementEntity employeeIncrementEntity = new EmployeeIncrementEntity();

		boolean incrementOrderExists = mstEmployeeRepo.annualIncrementExists(objEntity.getEmployeeId());

		if (!incrementOrderExists) {
			// Annual Increment entry for employee Start
			employeeIncrementEntity.setSevaarthId(objEntity.getSevaarthId());
			employeeIncrementEntity.setEmployeeId(objEntity.getEmployeeId());
			employeeIncrementEntity.setCurrentBasicPay(0);
			employeeIncrementEntity.setCurrentBasicLevelId(0);
			employeeIncrementEntity.setPreBasicPay(0);
			employeeIncrementEntity.setIncrementOrderNo("0");
			employeeIncrementEntity.setIncrementOrderDate(new Date());
			if (objEntity.getPayCommissionCode() == 8) {
				employeeIncrementEntity.setIncrementBasicPaySal(objEntity.getSevenPcBasic().intValue());
			} else {
				employeeIncrementEntity.setIncrementBasicPaySal(objEntity.getBasicPay().intValue());
			}
			employeeIncrementEntity.setIncrementBasicLevelId(mstEmployeeModel.getSvnthpaybasic());
			employeeIncrementEntity.setEffective_from_date(new Date());
			employeeIncrementEntity.setTo_increment_date(new Date());
			employeeIncrementEntity.setCreatedDate(new Date());
			employeeIncrementEntity.setUpdatedDate(new Date());
			employeeIncrementEntity.setRemark(null);
			employeeIncrementEntity.setIsActive('1');
			employeeIncrementEntity.setMonth(1);
			employeeIncrementEntity.setCreatedUserId(1);
			employeeIncrementEntity.setUpdatedUserId(1);
			employeeIncrementEntity.setDdoCode(objEntity.getDdoCode());
			// Annual Increment entry for employee End

			mstEmployeeRepo.saveEmployeeIncrement(employeeIncrementEntity);
		}

		return (int) id;
	}

	private String getCrearteSevaartIh(MstEmployeeModel mstEmployeeModel) {
		SimpleDateFormat sdf = new SimpleDateFormat("yy");
		String dept = "MJP";
		char fname;
		char mname;
		char lname;
		if (mstEmployeeModel.getEmployeeMNameEn() != null) {
			fname = mstEmployeeModel.getEmployeeFNameEn().trim().charAt(0);
			mname = mstEmployeeModel.getEmployeeMNameEn().trim().charAt(0);
			lname = mstEmployeeModel.getEmployeeLNameEn().trim().charAt(0);
		} else {
			fname = mstEmployeeModel.getEmployeeFNameEn().trim().charAt(0);
			mname = mstEmployeeModel.getEmployeeFNameEn().trim().charAt(1);
			lname = mstEmployeeModel.getEmployeeLNameEn().trim().charAt(0);
		}
		String name = String.valueOf(fname) + String.valueOf(mname) + String.valueOf(lname);
		String gender = mstEmployeeModel.getGender().toString();
		if (gender.equals("1")) {
			gender = "M";
		} else if (gender.equals("2")) {
			gender = "F";
		} else if (gender.equals("3")) {
			gender = "T";
		} else {
			gender = mstEmployeeModel.getGender().toString();
		}
		String year = sdf.format(mstEmployeeModel.getDob());
		String index = "01";
		String sevaarth = dept + name.toUpperCase() + gender + year;

		//String sevaarth = dept + name.toUpperCase() + gender + year + index;
		BigInteger indexnew = null;
		if (sevaarth != null) {
			indexnew = mstEmployeeRepo.findbySevaarthCount(sevaarth);
		}

		if (!indexnew.equals(BigInteger.valueOf(0))) {
			if (String.valueOf(indexnew).length() == 1) {
				index = "0" + indexnew.add(BigInteger.valueOf(1));
			} else {
				index = indexnew.add(BigInteger.valueOf(1)).toString();
			}
			sevaarth = dept + name.toUpperCase() + gender + year + index;
		}else if(indexnew.equals(BigInteger.valueOf(0))) {
			sevaarth=sevaarth+index;
		}
		return sevaarth;
	}

	public List<Object[]> findLevelsAgainstSeventhPayCommission() {
		List<Object[]> deptEligibilityForAllowAndDeductEntity = mstEmployeeRepo.findLevelsAgainstSeventhPayCommission();
		return deptEligibilityForAllowAndDeductEntity;
	}

	@Override
	public List<MstEmployeeEntity> findAllEmployeeByddoCode(String ddoCode) {
		return mstEmployeeRepo.findAllEmployeeByddoCode(ddoCode);

	}

	@Override
	public List<MstEmployeeEntity> firstgetfindAllEmployeeByddoCode(String ddoCode) {
		return mstEmployeeRepo.firstgetfindAllEmployeeByddoCode(ddoCode);
	}

	@Override
	public List<Object[]> findAttachedEmployee(String ddocode, String scmebillgroupid) {
		return mstEmployeeRepo.findAttachedEmployee(ddocode, scmebillgroupid);
	}

	@Override
	public List<Object[]> findDettachEmployee(String ddocode, String scmebillgroupid) {
		return mstEmployeeRepo.findDettachEmployee(ddocode, scmebillgroupid);
	}

	@Override
	public String saveAttachDettachEmployee(MpgSchemeBillGroupModel mpgSchemeBillGroupModel) {
		String result = "N";

		// public String saveAttachDettachEmployeeBillGroup(String sevaarthId,int empid,
		// int billGroupId,String status)
		String lStrDcpsEmpIdstoBeDetached = mpgSchemeBillGroupModel.getDcpsEmpIdstoBeDetached();// StringUtility.getParameter("dcpsEmpIdstoBeDetached",
																								// request);
		String[] lStrArrDcpsEmpIdstoBeDetached = lStrDcpsEmpIdstoBeDetached.split("~");
		Long[] lLongArrDcpsEmpIdstoBeDetached = new Long[lStrArrDcpsEmpIdstoBeDetached.length];
		for (Integer lInt = 0; lInt < lStrArrDcpsEmpIdstoBeDetached.length; lInt++) {
			if (lStrArrDcpsEmpIdstoBeDetached[lInt] != "" && !lStrArrDcpsEmpIdstoBeDetached[lInt].equals("")) {
				lLongArrDcpsEmpIdstoBeDetached[lInt] = Long.valueOf(lStrArrDcpsEmpIdstoBeDetached[lInt]);
				result = mstEmployeeRepo.saveAttachDettachEmployeeBillGroup(mpgSchemeBillGroupModel.getSevaarthId(),
						lLongArrDcpsEmpIdstoBeDetached[lInt].intValue(), mpgSchemeBillGroupModel.getSchemebillGroupId(),
						"Detach");// updateBillNoInPayroll(lLongArrDcpsEmpIdstoBeDetached[lInt], null, "Detach");
			}
		}

		String lStrDcpsEmpIdstoBeAttached = mpgSchemeBillGroupModel.getDcpsEmpIdstoBeAttached(); // StringUtility.getParameter("dcpsEmpIdstoBeAttached",
																									// request);
		String[] lStrArrDcpsEmpIdstoBeAttached = lStrDcpsEmpIdstoBeAttached.split("~");
		Long[] lLongArrDcpsEmpIdstoBeAttached = new Long[lStrArrDcpsEmpIdstoBeAttached.length];
		for (Integer lInt = 0; lInt < lStrArrDcpsEmpIdstoBeAttached.length; lInt++) {
			if (lStrArrDcpsEmpIdstoBeAttached[lInt] != "" && !lStrArrDcpsEmpIdstoBeAttached[lInt].equals("")) {
				lLongArrDcpsEmpIdstoBeAttached[lInt] = Long.valueOf(lStrArrDcpsEmpIdstoBeAttached[lInt]);
				// lObjDdoBillGroupDAO.updateBillNoInPayroll(lLongArrDcpsEmpIdstoBeAttached[lInt],
				// lLongbillGroupId, "Attach");
				result = mstEmployeeRepo.saveAttachDettachEmployeeBillGroup(mpgSchemeBillGroupModel.getSevaarthId(),
						lLongArrDcpsEmpIdstoBeAttached[lInt].intValue(), mpgSchemeBillGroupModel.getSchemebillGroupId(),
						"Attach");
			}
		}

		// // Does the function of Detachment
		// for (Integer lInt = 0; lInt < lLongArrDcpsEmpIdstoBeDetached.length; lInt++)
		// {
		//
		// if (lLongArrDcpsEmpIdstoBeDetached[lInt] != null) {
		//
		// lObjDcpsEmpMst = (MstEmp)
		// dcpsNewRegistrationDao.read(Long.valueOf(lLongArrDcpsEmpIdstoBeDetached[lInt]));
		// lObjDcpsEmpMst.setBillGroupId(null);
		// }
		// }
		//
		// // Does the function of Attachment
		//
		// for (Integer lInt = 0; lInt < lLongArrDcpsEmpIdstoBeAttached.length; lInt++)
		// {
		//
		// if (lLongArrDcpsEmpIdstoBeAttached[lInt] != null) {
		//
		// lObjDcpsEmpMst = (MstEmp)
		// dcpsNewRegistrationDao.read(Long.valueOf(lLongArrDcpsEmpIdstoBeAttached[lInt]));
		// lObjDcpsEmpMst.setBillGroupId(lLongbillGroupId);
		// }
		// }
		//
		return result;

	}

	@Override
	public List<Object[]> findDeptAllowanceDeductionReport(String ddoCode) {
		return mstEmployeeRepo.findDeptAllowanceDeductionReport(ddoCode);

	}

	@Override
	public List<MstEmployeeEntity> findAllEmployeeByDDOCodeAndBillGroup(String ddoCode, int billGroupId) {
		return mstEmployeeRepo.findAllEmployeeByDDOCodeAndBillGroup(ddoCode, billGroupId);

	}

	@Override
	public String getCityClassAgainstDDO(String ddoCode) {
		return mstEmployeeRepo.getCityClassAgainstDDO(ddoCode);
	}



	public List<Object[]> findEmployeeConfigurationGetpayscale(int payCommissionid) {
		List<Object[]> deptEligibilityForAllowAndDeductEntity = mstEmployeeRepo.getSvnPayscale();
		return deptEligibilityForAllowAndDeductEntity;
	}

	public List<Object[]> findEmployeeConfigurationGetsvnbasicpay(String payscale) {
		List<Object[]> deptEligibilityForAllowAndDeductEntity = mstEmployeeRepo.getSvnPcData(payscale);
		return deptEligibilityForAllowAndDeductEntity;
	}

	@Override
	public List<MstEmployeeModel> getEmployeeDetails(String strddo, String locale) {
		List<MstEmployeeEntity> listempentity = mstEmployeeRepo.getEmployeeDetails(strddo);
		List<MstEmployeeModel> result = new ArrayList<MstEmployeeModel>();
		String DeptName = "";
		List<DDOScreenModel> lstDepartment = findDDOScreenDataTable(locale, strddo);
		for (Iterator iterator = lstDepartment.iterator(); iterator.hasNext();) {
			DDOScreenModel ddoScreenModel = (DDOScreenModel) iterator.next();
			DeptName = ddoScreenModel.getDeptName();
		}
		for (Iterator iterator = listempentity.iterator(); iterator.hasNext();) {
			MstEmployeeEntity mstEmployeeEntity = (MstEmployeeEntity) iterator.next();
			MstEmployeeModel mstEmployeeModel = new MstEmployeeModel();
			mstEmployeeModel.setEmployeeId(mstEmployeeEntity.getEmployeeId());
			mstEmployeeModel.setEmployeeFullName(mstEmployeeEntity.getEmployeeFullNameEn().toUpperCase());
			mstEmployeeModel.setSevaarthId(mstEmployeeEntity.getSevaarthId());
			mstEmployeeModel.setDesignationName(mstEmployeeRepo.getDesignationName(
					mstEmployeeEntity.getDesignationCode() != null ? mstEmployeeEntity.getDesignationCode().toString()
							: "0".toString())
					.toUpperCase());
			mstEmployeeModel.setDepartmentNameEn(DeptName);
			// List<MstDcpsDetailsEntity> listempdcpstity = mstEmployeeRepo
			// .getEmpDcpsDetails(mstEmployeeEntity.getEmployeeId());
			// for (Iterator iterator1 = listempdcpstity.iterator(); iterator1.hasNext();) {
			// MstDcpsDetailsEntity mstDcpsDetailsEntity = (MstDcpsDetailsEntity)
			// iterator1.next();
			// mstEmployeeModel.setDcpsid(mstDcpsDetailsEntity.getDcpsid());
			// }
			// List<MstGpfDetailsEntity> listempgpfstity = mstEmployeeRepo
			// .getEmployeeGpfDetails(mstEmployeeEntity.getEmployeeId());
			// for (Iterator iterator2 = listempgpfstity.iterator(); iterator2.hasNext();) {
			// MstGpfDetailsEntity mstGpfDetailsEntity = (MstGpfDetailsEntity)
			// iterator2.next();
			// mstEmployeeModel.setGpf_id(mstGpfDetailsEntity.getGpf_id());
			// }
			// List<MstGisdetailsEntity> listempGistity = mstEmployeeRepo
			// .getEmployeeGisDetails(mstEmployeeEntity.getEmployeeId());
			// for (Iterator iterator3 = listempGistity.iterator(); iterator3.hasNext();) {
			// MstGisdetailsEntity mstGisdetailsEntity = (MstGisdetailsEntity)
			// iterator3.next();
			// mstEmployeeModel.setGisid(mstGisdetailsEntity.getGisid());
			// }
			// List<MstNomineeDetailsEntity> listempNominetity = mstEmployeeRepo
			// .getEmployeeNomineeDetails(mstEmployeeEntity.getEmployeeId());
			// for (Iterator iterator4 = listempNominetity.iterator(); iterator4.hasNext();)
			// {
			// MstNomineeDetailsEntity mstNomineeDetailsEntity = (MstNomineeDetailsEntity)
			// iterator4.next();
			// mstEmployeeModel.setNomineeid(mstNomineeDetailsEntity.getNomineeid());
			// }
			result.add(mstEmployeeModel);
		}
		// List<MstEmployeeEntity>
		// listempentity=mstEmployeeRepo.getEmployeeDetails(strddo);

		return result;
	}

	@Override
	public MstEmployeeModel getEmployeeinfo(Integer emp_id) {
		return mstEmployeeRepo.getEmployeeinfo(emp_id);
	}

	@Override
	public int deleteEmployeeConfiguration(Integer empid) {
		return mstEmployeeRepo.deleteEmployeeConfiguration(empid);
	}

	@Override
	public List<Long> validateUIDUniq(String uid, String employeeid) {
		return mstEmployeeRepo.validateUIDUniq(uid, employeeid);
	}

	@Override
	public List<MstEmployeeModel> getApproveEmployeeDetails(String strddo, String locale) {
		List<MstEmployeeEntity> listempentity = mstEmployeeRepo.getApproveEmployeeDetails(strddo);
		List<MstEmployeeModel> result = new ArrayList<MstEmployeeModel>();
		String DeptName = "";
		String SubDeptName = "";
		List<DDOScreenModel> lstDepartment = findDDOScreenDataTable(locale, strddo);
		for (Iterator iterator = lstDepartment.iterator(); iterator.hasNext();) {
			DDOScreenModel ddoScreenModel = (DDOScreenModel) iterator.next();
			DeptName = ddoScreenModel.getDeptName();
			SubDeptName = ddoScreenModel.getSubDeptName();
		}
		for (Iterator iterator = listempentity.iterator(); iterator.hasNext();) {
			MstEmployeeEntity mstEmployeeEntity = (MstEmployeeEntity) iterator.next();
			MstEmployeeModel mstEmployeeModel = new MstEmployeeModel();
			mstEmployeeModel.setEmployeeId(mstEmployeeEntity.getEmployeeId());
			mstEmployeeModel.setEmployeeFullName(mstEmployeeEntity.getEmployeeFullNameEn().toUpperCase());
			mstEmployeeModel.setSevaarthId(mstEmployeeEntity.getSevaarthId());
			mstEmployeeModel.setDesignationName(mstEmployeeRepo.getDesignationName(
					mstEmployeeEntity.getDesignationCode() != null ? mstEmployeeEntity.getDesignationCode().toString()
							: "0".toString())
					.toUpperCase());
			mstEmployeeModel.setDepartmentNameEn(DeptName);
			mstEmployeeModel.setSubdeptNm(SubDeptName);
			result.add(mstEmployeeModel);
		}

		return result;

	}

	@Override
	public List<Long> approveEmployeeConfiguration(String empid, String sevaarthid, String dcpsgpfflg) {
		return mstEmployeeRepo.approveEmployeeConfiguration(empid, sevaarthid, dcpsgpfflg);
	}

	@Override
	public List<Long> rejectEmployeeConfiguration(String empid) {
		return mstEmployeeRepo.rejectEmployeeConfiguration(empid);
	}

	@Override
	public List<Object[]> getBankBranch(String strbankid) {
		List<Object[]> lstbranchname = mstEmployeeRepo.getBankBranch(strbankid);
		return lstbranchname;
	}

	@Override
	public List<Object[]> getDcpsAccnMaintainby() {
		List<Object[]> result = new ArrayList<Object[]>();
		
		
		Object[] object = new Object[2];
		object[0] = "1";
		object[1] = "a/C Maintained by Zilla Parishad";
		result.add(object);
		
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
		
		
		Object[] object2 = new Object[2];
		object2[0] = "3";
		object2[1] = "Zilla Parishad";
		result.add(object2);
		
		
		
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

	@Override
	public List<Object[]> getPfSeries(String accmainby) {
		List<Object[]> result = new ArrayList<Object[]>();
		Object[] temp1 = { "", "AG/MAH", "AG/MH", "AGC/MAH", "AGC/MH", "AGV/MAH", "AJ/HCAS", "AJ/MAH", "AJMH", "ED/MAH",
				"EDMH", "EXC/MH", "EXE/MAH", "FMAH", "GA/MAH", "GA/MH", "GA/PAO", "GN/BN20114", "IAS/MAH", "IFS/BN",
				"IFS/MH", "IND/MAH", "IND/MH", "IPS/MAH", "J/MAH", "JMH", "M/MAH", "MIS/MH", "MISC", "MISC/MAH",
				"MISC/MH", "MMH", "OT/MAH", "OTMH", "PB/MAH", "PB/MH", "PC/MAH", "PC/MH", "PH/MH", "PN/MAH", "PN/MH",
				"POL", "PS/MAH", "PS/MH", "PW/MAH", "PW/MH", "R/MAH", "S/MH", "SCI/MAH", "SMH", "STY/MAH", "STY/MH", "IAS/MH" };
		Object[] temp2 = { "ABN", "AJBN", "BRBN", "COBN", "CPBN", "EDBN", "EXBN", "FBN", "FMAH", "GABN", "IASBN",
				"IFSBN", "IFSMH", "INDBN", "IPSBN", "JBN", "LRBN", "MBN", "MDBN", "MHPW", "MSBN", "MVBN", "OTBN",
				"PHBN", "POBN", "RBN", "SCBN", "SPBN", "VBN", "MJP"};
		if (accmainby.equals("1")) {
			for (int i = 1; i < temp1.length; i++) {
				Object[] object2 = new Object[2];
				object2[0] = i;
				object2[1] = temp1[i];
				result.add(object2);
			}
		} else if (accmainby.equals("2")) {
			for (int i = 1; i < temp2.length; i++) {
				Object[] object2 = new Object[2];
				object2[0] = i;
				object2[1] = temp2[i];
				result.add(object2);
			}
		} else if (accmainby.equals("3")) {
			Object[] object2 = new Object[2];
			object2[0] = "3";
			object2[1] = "MJP";
			result.add(object2);
		} else if (accmainby.equals("4")) {
			Object[] object2 = new Object[2];
			object2[0] = "NA";
			object2[1] = "NA";
			result.add(object2);
		} else if (accmainby.equals("5")) {
			Object[] object2 = new Object[2];
			object2[0] = "1";
			object2[1] = "Other";
			result.add(object2);
		}
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
	public String[] savePhotoSignature(MultipartFile[] files, String DeptNm, Integer empid, String existphotpath,
			String existsignpath) {
		// department name/photo/employee_id/photo.jpg
		String[] res = new String[2];
		if (files.length != 0) {
			int width = 963;
			int height = 640;

			try {
				byte[] bytes = files[0].getBytes();

				if (bytes.length != 0) {
					BufferedImage image = null;
					File f = null;
					InputStream in = new ByteArrayInputStream(bytes);
					//    //read image
					////    try{
					////      f = new File(strmagepath); //image file path
					image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
					image = ImageIO.read(in);
					////      logger.info("Reading complete.");
					////    }catch(IOException e){
					////      logger.info("Error: "+e);
					////    }
					//  //write image
					// String stroutputimagepath="D:\\Image\\Output.jpg";
					//
					// f = new File(stroutputimagepath);
					// ImageIO.write(image, "jpg", f);
					// logger.info("Writing complete.");

					// Creating the directory to store file
					// String rootPath = System.getProperty("catalina.home");
					String key = "";
					String rootPath = "";
					String strOSName = System.getProperty("os.name");
					boolean test = strOSName.contains("Windows");
					if (strOSName.contains("Windows")) {
						key = "serverempconfigimagepath";
					} else {
						key = "serverempconfigimagepathLinuxOS";
					}
					rootPath = environment.getRequiredProperty(key);
					rootPath += DeptNm + File.separator + empid;
					File dir = new File(rootPath);
					if (!dir.exists())
						dir.mkdirs();

					String name = "photo.jpg";
					// Create the file on server
					File serverFile = new File(dir.getAbsolutePath() + File.separator + name);
					BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
					stream.write(bytes);
					stream.close();

					res[0] = dir.getAbsolutePath() + File.separator + name;

				} else {
					res[0] = existphotpath;
				}
			} catch (Exception e) {
				e.printStackTrace();
				res[0] = "";
			}
		}

		// signature image code started
		if (files.length != 0) {
			try {
				byte[] bytes = files[1].getBytes();
				boolean var = bytes.length != 0;

				if (bytes.length != 0) {
					BufferedImage image = null;
					File f = null;
					int width = 963;
					int height = 640;
					InputStream in = new ByteArrayInputStream(bytes);
					//    //read image
					////    try{
					////      f = new File(strmagepath); //image file path
					image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
					image = ImageIO.read(in);
					// BufferedImage scaleimg = Scalr
					////      logger.info("Reading complete.");
					////    }catch(IOException e){
					////      logger.info("Error: "+e);
					////    }
					//  //write image
					// String stroutputimagepath="D:\\Image\\Signature.jpg";

					// f = new File(stroutputimagepath);
					// ImageIO.write(image, "jpg", f);
					// logger.info("Writing complete.");
					// Creating the directory to store file
					// String rootPath = System.getProperty("catalina.home");
					String key = "";
					String rootPath = "";
					String strOSName = System.getProperty("os.name");
					boolean test = strOSName.contains("Windows");
					if (strOSName.contains("Windows")) {
						key = "serverempconfigimagepath";
					} else {
						key = "serverempconfigimagepathLinuxOS";
					}
					rootPath = environment.getRequiredProperty(key);
					rootPath += DeptNm + File.separator + empid;
					// String rootPath ="C:\\Users\\jjman\\OneDrive\\Pictures\\server";
					File dir = new File(rootPath);
					if (!dir.exists())
						dir.mkdirs();
					String name = "signature.jpg";
					// Create the file on server
					File serverFile = new File(dir.getAbsolutePath() + File.separator + name);
					BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
					stream.write(bytes);
					stream.close();

					res[1] = dir.getAbsolutePath() + File.separator + name;
				} else {
					res[1] = existsignpath;
				}
			} catch (Exception e) {
				e.printStackTrace();
				res[0] = "";
			}
		}
		// signature code ended
		return res;
	}

	@Override
	public String[] savePhotoSignature(MultipartFile[] files, String DeptNm, Integer empid) {
		// department name/photo/employee_id/photo.jpg
		String[] res = new String[2];
		if (files.length != 0) {
			int width = 963;
			int height = 640;

			try {
				byte[] bytes = files[0].getBytes();
				if (bytes.length != 0) {
					BufferedImage image = null;
					File f = null;
					InputStream in = new ByteArrayInputStream(bytes);
					//    //read image
					////    try{
					////      f = new File(strmagepath); //image file path
					image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
					image = ImageIO.read(in);
					////      logger.info("Reading complete.");
					////    }catch(IOException e){
					////      logger.info("Error: "+e);
					////    }
					//  //write image
					// String stroutputimagepath="D:\\Image\\Output.jpg";
					//
					// f = new File(stroutputimagepath);
					// ImageIO.write(image, "jpg", f);
					// logger.info("Writing complete.");

					// Creating the directory to store file
					// String rootPath = System.getProperty("catalina.home");
					String key = "";
					String rootPath = "";
					String strOSName = System.getProperty("os.name");
					boolean test = strOSName.contains("Windows");
					if (strOSName.contains("Windows")) {
						key = "serverempconfigimagepath";
					} else {
						key = "serverempconfigimagepathLinuxOS";
					}
					rootPath = environment.getRequiredProperty(key);
					rootPath += DeptNm + File.separator + empid;
					File dir = new File(rootPath);
					if (!dir.exists())
						dir.mkdirs();

					String name = "photo.jpg";
					// Create the file on server
					File serverFile = new File(dir.getAbsolutePath() + File.separator + name);
					BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
					stream.write(bytes);
					stream.close();

					res[0] = dir.getAbsolutePath() + File.separator + name;

				} else {
					res[0] = "";
				}
			} catch (Exception e) {
				e.printStackTrace();
				res[0] = "";
			}
		}

		// signature image code started
		if (files.length != 0) {
			try {
				byte[] bytes = files[1].getBytes();
				boolean var = bytes.length != 0;

				if (bytes.length != 0) {
					BufferedImage image = null;
					File f = null;
					int width = 963;
					int height = 640;
					InputStream in = new ByteArrayInputStream(bytes);
					//    //read image
					////    try{
					////      f = new File(strmagepath); //image file path
					image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
					image = ImageIO.read(in);
					// BufferedImage scaleimg = Scalr
					////      logger.info("Reading complete.");
					////    }catch(IOException e){
					////      logger.info("Error: "+e);
					////    }
					//  //write image
					// String stroutputimagepath="D:\\Image\\Signature.jpg";

					// f = new File(stroutputimagepath);
					// ImageIO.write(image, "jpg", f);
					// logger.info("Writing complete.");
					// Creating the directory to store file
					// String rootPath = System.getProperty("catalina.home");
					String key = "";
					String rootPath = "";
					String strOSName = System.getProperty("os.name");
					boolean test = strOSName.contains("Windows");
					if (strOSName.contains("Windows")) {
						key = "serverempconfigimagepath";
					} else {
						key = "serverempconfigimagepathLinuxOS";
					}
					rootPath = environment.getRequiredProperty(key);
					rootPath += DeptNm + File.separator + empid;
					// String rootPath ="C:\\Users\\jjman\\OneDrive\\Pictures\\server";
					File dir = new File(rootPath);
					if (!dir.exists())
						dir.mkdirs();
					String name = "signature.jpg";
					// Create the file on server
					File serverFile = new File(dir.getAbsolutePath() + File.separator + name);
					BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
					stream.write(bytes);
					stream.close();

					res[1] = dir.getAbsolutePath() + File.separator + name;
				} else {
					res[1] = "";
				}
			} catch (Exception e) {
				e.printStackTrace();
				res[1] = "";
			}
		}
		// signature code ended
		return res;
	}

	@Override
	public String findEmployeeConfigurationUploadImage(String strmagepath, String outputNm) {
		int width = 963; // width of the image
		int height = 640; // height of the image
		BufferedImage image = null;
		File f = null;

		// read image
		try {
			f = new File(strmagepath); // image file path
			image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
			image = ImageIO.read(f);
		} catch (IOException e) {
		}

		// write image
		String getpathstr = getPath();
		// Path getabsolutepath=Paths.get(".");
		// logger.info("getabsolutepath="+getabsolutepath.getRoot());
		// logger.info("getabsolutepath="+getabsolutepath);

		String stroutputimagepath = getpathstr + "/WEB-INF/classes/static/images/" + outputNm;
		try {
			f = new File(stroutputimagepath); // output file path
			ImageIO.write(image, "jpg", f);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return outputNm;
	}

	public String getPath() {
		String reponsePath = "";
		try {

			String path = this.getClass().getClassLoader().getResource("").getPath();
			String fullPath = URLDecoder.decode(path, "UTF-8");
			String pathArr[] = fullPath.split("/WEB-INF/classes/");
			fullPath = pathArr[0];

			// to read a file from webcontent
			// reponsePath = new File(fullPath).getPath() + File.separatorChar +
			// "newfile.txt";
			reponsePath = pathArr[0];
		} catch (UnsupportedEncodingException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return reponsePath;
	}

	@Override
	public List<MstEmployeeEntity> findAllEmployeesNotMap() {
		return mstEmployeeRepo.findAllEmployeesNotMap();
	}

	@Override
	public List<MstEmployeeEntity> findAllEmployeesNotMap1() {
		return mstEmployeeRepo.findAllEmployeesNotMap1();
	}

	@Override
	public String saveEmployeeddoCode(String sevaarthId, String ddoCode) {
		String saveId = mstEmployeeRepo.saveEmployeeddoCode(sevaarthId, ddoCode);
		return saveId;
	}

	@Override
	public MstEmployeeEntity findMstEmpByDDOCodeForReject(int id) {
		MstEmployeeEntity objDeptForReject = mstEmployeeRepo.findMstEmpByBillGroupId(id);
		if (objDeptForReject != null) {
			objDeptForReject.setDdoCode(null);
			mstEmployeeRepo.updateEmpBillStatusStatus(objDeptForReject);
		}
		return objDeptForReject;
	}

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
				// obj.setDdoRegID(StringHelperUtils.isNullInt(objLst[0]));
				obj.setDepartmentId(StringHelperUtils.isNullInt(objLst[0]));
				obj.setSubDepartmentId(StringHelperUtils.isNullInt(objLst[1]));
				;
				if (locale.equals("en")) {
					obj.setDeptName(StringHelperUtils.isNullString(objLst[2]));
					obj.setSubDeptName(StringHelperUtils.isNullString(objLst[4]));
				} else {
					obj.setDeptName(StringHelperUtils.isNullString(objLst[3]));
					obj.setSubDeptName(StringHelperUtils.isNullString(objLst[5]));
				}
				
				 * obj.setDdoName(StringHelperUtils.isNullString(objLst[7]));
				 * obj.setDdoCode(StringHelperUtils.isNullString(objLst[8]));
				 * obj.setRoleLevel(StringHelperUtils.isNullString(objLst[9]));
				 * obj.setIsActive(String0HelperUtils.isNullInt(Integer.parseInt(String.valueOf(
				 * objLst[10]))));
				 
				obj.setSubDeptShortName(StringHelperUtils.isNullString(objLst[6]));

				lstObj.add(obj);
			}
		}
		return lstObj;
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
					 obj.setFieldDepartmrnt(StringHelperUtils.isNullString(objLst[1])); 
					 obj.setCadreGroup(StringHelperUtils.isNullString(objLst[3])); 
					obj.setCadreGroup(StringHelperUtils.isNullString(objLst[1]));
				} else {
					 obj.setFieldDepartmrnt(StringHelperUtils.isNullString(objLst[2])); 
					 obj.setCadreGroup(StringHelperUtils.isNullString(objLst[4])); 
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
	public List<Object[]> getCadreGroupMstData(String locale, String strCadreId) {
		List<Object[]> lstprop = mstEmployeeRepo.getgroupname(strCadreId);
		return lstprop;
	}

	@Override
	public List<Object[]> getInstitueDtls(String ddocode) {
		List<Object[]> lstprop = mstEmployeeRepo.getInstitueDtls(ddocode);
		return lstprop;
	}

	@Override
	public long getCount(String tempSevarthEmpCode) {
		long res = mstEmployeeRepo.getCount(tempSevarthEmpCode);
		return res;
	}

	@Override
	public List<MstNomineeDetailsEntity> getNominees(String empId) {
		List<MstNomineeDetailsEntity> result = mstEmployeeRepo.getNominees(empId);
		return result;
	}

	@Override
	public List<MstEmployeeModel> getDcpsEmployeeDetails(String strddo, String locale) {
		List<MstEmployeeEntity> listempentity = mstEmployeeRepo.getDcpsEmployeeDetails(strddo);
		List<MstEmployeeModel> result = new ArrayList<MstEmployeeModel>();
		String DeptName = "";
		List<DDOScreenModel> lstDepartment = findDDOScreenDataTable(locale, strddo);
		for (Iterator iterator = lstDepartment.iterator(); iterator.hasNext();) {
			DDOScreenModel ddoScreenModel = (DDOScreenModel) iterator.next();
			DeptName = ddoScreenModel.getDeptName();
		}
		for (Iterator iterator = listempentity.iterator(); iterator.hasNext();) {
			MstEmployeeEntity mstEmployeeEntity = (MstEmployeeEntity) iterator.next();
			MstEmployeeModel mstEmployeeModel = new MstEmployeeModel();
			mstEmployeeModel.setEmployeeId(mstEmployeeEntity.getEmployeeId());
			mstEmployeeModel.setEmployeeFullName(mstEmployeeEntity.getEmployeeFullNameEn());
			mstEmployeeModel.setSevaarthId(mstEmployeeEntity.getSevaarthId());
			mstEmployeeModel.setDesignationName(mstEmployeeRepo.getDesignationName(
					mstEmployeeEntity.getDesignationCode() != null ? mstEmployeeEntity.getDesignationCode().toString()
							: "0".toString())
					.toUpperCase());
			mstEmployeeModel.setDepartmentNameEn(DeptName);
			// List<MstDcpsDetailsEntity> listempdcpstity = mstEmployeeRepo
			// .getEmpDcpsDetails(mstEmployeeEntity.getEmployeeId());
			// for (Iterator iterator1 = listempdcpstity.iterator(); iterator1.hasNext();) {
			// MstDcpsDetailsEntity mstDcpsDetailsEntity = (MstDcpsDetailsEntity)
			// iterator1.next();
			// mstEmployeeModel.setDcpsid(mstDcpsDetailsEntity.getDcpsid());
			// }
			// List<MstGpfDetailsEntity> listempgpfstity = mstEmployeeRepo
			// .getEmployeeGpfDetails(mstEmployeeEntity.getEmployeeId());
			// for (Iterator iterator2 = listempgpfstity.iterator(); iterator2.hasNext();) {
			// MstGpfDetailsEntity mstGpfDetailsEntity = (MstGpfDetailsEntity)
			// iterator2.next();
			// mstEmployeeModel.setGpf_id(mstGpfDetailsEntity.getGpf_id());
			// }
			// List<MstGisdetailsEntity> listempGistity = mstEmployeeRepo
			// .getEmployeeGisDetails(mstEmployeeEntity.getEmployeeId());
			// for (Iterator iterator3 = listempGistity.iterator(); iterator3.hasNext();) {
			// MstGisdetailsEntity mstGisdetailsEntity = (MstGisdetailsEntity)
			// iterator3.next();
			// mstEmployeeModel.setGisid(mstGisdetailsEntity.getGisid());
			// }
			// List<MstNomineeDetailsEntity> listempNominetity = mstEmployeeRepo
			// .getEmployeeNomineeDetails(mstEmployeeEntity.getEmployeeId());
			// for (Iterator iterator4 = listempNominetity.iterator(); iterator4.hasNext();)
			// {
			// MstNomineeDetailsEntity mstNomineeDetailsEntity = (MstNomineeDetailsEntity)
			// iterator4.next();
			// mstEmployeeModel.setNomineeid(mstNomineeDetailsEntity.getNomineeid());
			// }
			result.add(mstEmployeeModel);
		}
		// List<MstEmployeeEntity>
		// listempentity=mstEmployeeRepo.getEmployeeDetails(strddo);

		return result;

	}

	@Override
	public Character getLastDigit(String lStrDCPSId) {

		Character LastDigit = null;
		Map<String, Integer> MappingData = getMappingData();

		char[] lArrStrDcpsId = lStrDCPSId.toCharArray();

		Integer[] lArrIntMultiplication = { 1, 3, 5, 7, 1, 3, 5, 7, 1, 3, 5, 7, 1, 3, 5, 7, 1, 3, 5 };

		Integer lIntTotal = 0;

		for (Integer index = 0; index < lArrStrDcpsId.length - 1; index++) {
			String lStrMappedKey = Character.toString(lArrStrDcpsId[index]);
			Integer lIntMap = MappingData.get(lStrMappedKey);
			lIntTotal = lIntTotal + (lIntMap * lArrIntMultiplication[index]);
		}

		Integer lIntLastDigit = 26 - (lIntTotal % 26);
		LastDigit = (char) (64 + lIntLastDigit);

		return LastDigit;
	}

	@Override
	public Map getMappingData() {

		Map<String, Integer> MappingData = new HashMap<String, Integer>();

		MappingData.put("0", 0);
		MappingData.put("1", 1);
		MappingData.put("2", 2);
		MappingData.put("3", 3);
		MappingData.put("4", 4);
		MappingData.put("5", 5);
		MappingData.put("6", 6);
		MappingData.put("7", 7);
		MappingData.put("8", 8);
		MappingData.put("9", 9);
		MappingData.put("A", 10);
		MappingData.put("B", 11);
		MappingData.put("C", 12);
		MappingData.put("D", 13);
		MappingData.put("E", 14);
		MappingData.put("F", 15);
		MappingData.put("G", 16);
		MappingData.put("H", 17);
		MappingData.put("I", 18);
		MappingData.put("J", 19);
		MappingData.put("K", 20);
		MappingData.put("L", 21);
		MappingData.put("M", 22);
		MappingData.put("N", 23);
		MappingData.put("O", 24);
		MappingData.put("P", 25);
		MappingData.put("Q", 26);
		MappingData.put("R", 27);
		MappingData.put("S", 28);
		MappingData.put("T", 29);
		MappingData.put("U", 30);
		MappingData.put("V", 31);
		MappingData.put("W", 32);
		MappingData.put("X", 33);
		MappingData.put("Y", 34);
		MappingData.put("Z", 35);
		MappingData.put(".", 36);

		return MappingData;
	}

	;;

	@Override
	public List<Long> forwardLvlThreeEmployeeConfiguration(String empid, String sevaarthid, String dcpsgpfflg) {
		List<Long> result = mstEmployeeRepo.forwardLvlThreeEmployeeConfiguration(empid, sevaarthid, dcpsgpfflg);
		return result;
	}

	@Override
	public List<Long> approveDcpsEmployeeConfiguration(String empid, String Dcpsnumber, String sevaarthid,
			String dcpsgpfflg) {
		List<Long> result = mstEmployeeRepo.approveDcpsEmployeeConfiguration(empid, Dcpsnumber, sevaarthid, dcpsgpfflg);
		return result;
	}

	public List<Object[]> GetCurrentPostByLvlTwo(int designationId, String ddocode) {
		List<Object[]> result = mstEmployeeRepo.GetCurrentPostByLvlTwo(designationId, ddocode);
		return result;
	}

	public List<Object[]> GetCurrentPostByLvlThree(int designationId, String ddocode) {
		List<Object[]> result = mstEmployeeRepo.GetCurrentPostByLvlThree(designationId, ddocode);
		return result;
	}

	public List<Long> validateTelephone(String strTelPhone, String employeeid) {
		return mstEmployeeRepo.validateTelephone(strTelPhone, employeeid);
	}

	public List<Long> validateMobileno(long mobileno, String employeeid) {
		return mstEmployeeRepo.validateMobileno(mobileno, employeeid);
	}

	public List<Long> validateEmail(String strEmail, String employeeid) {
		return mstEmployeeRepo.validateEmail(strEmail, employeeid);
	}

	public List<Long> validatePancard(String strPancard, String employeeid) {
		return mstEmployeeRepo.validatePancard(strPancard, employeeid);
	}

	public String getOfficeNameAddress(String strddo, String deptCode) {
		return mstEmployeeRepo.getOfficeNameAddress(strddo, deptCode);
	}

	@Override
	public List<Object[]> isPaybillIsInProcessForAttach(String sevaarthId) {
		return mstEmployeeRepo.isPaybillIsInProcessForAttach(sevaarthId);
	}

	

	@Override
	public List<Object[]> supEmployeeAllowDeduction(String sevaarthId) {
		return mstEmployeeRepo.findSupEmployeeAllowanceDeduction(sevaarthId);

	}
	// for 7pc level id//

	public int getSevenPcLevel(String sevaarthId) {
		return mstEmployeeRepo.getSevenPcLevelID(sevaarthId);
	}

	@Override
	public LoanEmployeeDtlsEntity findLoanDetails(int empId, int allowDedCode) {
		return mstEmployeeRepo.findLoanDetails(empId, allowDedCode);
	}

	@Override
	public int updateEmpLoanAmt(Integer employeeId, BigInteger gpfabc) {
		return mstEmployeeRepo.updateEmpLoanAmt(employeeId, gpfabc);
	}

	@Override
	public PaybillLoanRecoverDtlsEntity findInstallmentNoForCurrMont(String sevaarthId, int mon, int curryear) {
		// TODO Auto-generated method stub
		return mstEmployeeRepo.findInstallmentNoForCurrMont(sevaarthId, mon, curryear);
	}

	@Override
	public int saveEmpLoanData(String sevaarthId, Double gpfAdvD, int mon, int curryear) {
		// TODO Auto-generated method stub
		// HttpSession session = null;
		int id = 0;
		PaybillLoanRecoverDtlsEntity paybillLoanRecoverDtlsEntity = new PaybillLoanRecoverDtlsEntity();
		// UserInfo messages = (UserInfo) session.getAttribute("MY_SESSION_MESSAGES");
		List<Object[]> lstprop = mstEmployeeRepo.findEmployeesloandata(sevaarthId);
		if (!lstprop.isEmpty()) {
			for (Object[] objLst : lstprop) {
				paybillLoanRecoverDtlsEntity.setSevaarthId(StringHelperUtils.isNullString(objLst[1]));
				paybillLoanRecoverDtlsEntity.setLoantypeId(StringHelperUtils.isNullBigInteger(objLst[2]));
				paybillLoanRecoverDtlsEntity.setLoanId(StringHelperUtils.isNullInt(objLst[3]));
				paybillLoanRecoverDtlsEntity.setDdoCode(StringHelperUtils.isNullString(objLst[5]));
				paybillLoanRecoverDtlsEntity.setPaybillMonth(mon);
				paybillLoanRecoverDtlsEntity.setPaybillYear(curryear);
				int loanint = StringHelperUtils.isNullBigInteger(objLst[9]).intValue();
				paybillLoanRecoverDtlsEntity.setTotalPrincipalLoanInstallment(loanint);
				paybillLoanRecoverDtlsEntity.setTotalPrincipalLoanAmount(StringHelperUtils.isNullBigInteger(objLst[8]));
				BigInteger amt = BigDecimal.valueOf(gpfAdvD).toBigInteger();
				paybillLoanRecoverDtlsEntity.setTotalPrincipalLoanRecoverAmount(amt);
				int recount = StringHelperUtils.isNullInt(objLst[11]) + 1;
				paybillLoanRecoverDtlsEntity.setTotalPrincipalLoanRecoverInstallment(recount);
				// paybillLoanRecoverDtlsEntity.setCreatedUserId(BigInteger.valueOf(messages.getUser_id()));
				paybillLoanRecoverDtlsEntity.setCreatedDate(new Date());

			}

		}
		Serializable saveattendanceEntry = mstEmployeeRepo.saveempLoandata(paybillLoanRecoverDtlsEntity);
		id = (int) saveattendanceEntry;
		return id;

	}

	@Override
	public List<Object[]> findEmployeeConfigurationpayScaleSeven(int payScaleSeven) {
		return mstEmployeeRepo.findEmployeeConfigurationpayScaleSeven(payScaleSeven);
	}

	@Override
	public List<Object[]> findEmployeeConfigurationGetSixPayScale(int payCommission) {
		return mstEmployeeRepo.findEmployeeConfigurationGetSixPayScale(payCommission);
	}

	@Override
	public List<MstEmployeeModel> lstAllBankBranchList() {
		List<Object[]> lstprop = mstEmployeeRepo.lstAllBankBranchList();
		List<MstEmployeeModel> lstObj = new ArrayList<>();
		if (!lstprop.isEmpty()) {
			for (Object[] objLst : lstprop) {
				MstEmployeeModel obj = new MstEmployeeModel();
				obj.setBankBranchId(StringHelperUtils.isNullBigInteger(objLst[0]));
				obj.setBankBranchName(StringHelperUtils.isNullString(objLst[1]));

				lstObj.add(obj);
			}
		}
		return lstObj;
	}

	@Override
	public List<MstEmployeeModel> lstCurrentPost() {
		List<Object[]> lstprop = mstEmployeeRepo.lstCurrentPost();
		List<MstEmployeeModel> lstObj = new ArrayList<>();
		if (!lstprop.isEmpty()) {
			for (Object[] objLst : lstprop) {
				MstEmployeeModel obj = new MstEmployeeModel();
				obj.setPostdetailid(StringHelperUtils.isNullInt(objLst[0]));
				obj.setPostName(StringHelperUtils.isNullString(objLst[1]));

				lstObj.add(obj);
			}
		}
		return lstObj;
	}

	@Override
	public List<MstEmployeeModel> lstCadreMst() {
		List<Object[]> lstprop = mstEmployeeRepo.lstCadreMst();
		List<MstEmployeeModel> lstObj = new ArrayList<>();
		if (!lstprop.isEmpty()) {
			for (Object[] objLst : lstprop) {
				MstEmployeeModel obj = new MstEmployeeModel();
				obj.setCadreId(StringHelperUtils.isNullBigInteger(objLst[0]));
				obj.setCadreName(StringHelperUtils.isNullString(objLst[1]));

				lstObj.add(obj);
			}
		}
		return lstObj;
	}

	@Override
	public int saveChangeDetails(@Valid MstEmployeeModel mstEmployeeModel, MultipartFile[] files) {

		// MstEmployeeEntity objEntity = new MstEmployeeEntity();
		System.out.println("mstEmployeeModel.getSevaarthId" + mstEmployeeModel.getSevaarthId());
		System.out.println("mstEmployeeModel.getUidNo" + mstEmployeeModel.getUidNo());
		MstEmployeeEntity objEntity = mstEmployeeRepo.getEmployeeData(mstEmployeeModel.getEmployeeId());

		objEntity.setEmployeeId(mstEmployeeModel.getEmployeeId());
		objEntity.setSevaarthId(mstEmployeeModel.getSevaarthId());

		objEntity.setGender(mstEmployeeModel.getGender());
		objEntity.setSalutation(mstEmployeeModel.getSalutation());
		objEntity.setEmployeeFullNameEn(mstEmployeeModel.getEmployeeFullNameEn().toUpperCase());
		objEntity.setEmployeeFNameEn(mstEmployeeModel.getEmployeeFNameEn().toUpperCase());
		objEntity.setEmployeeMNameEn(mstEmployeeModel.getEmployeeMNameEn().toUpperCase());
		objEntity.setEmployeeLNameEn(mstEmployeeModel.getEmployeeLNameEn().toUpperCase());
		objEntity.setEmployeeFullNameMr(mstEmployeeModel.getEmployeeFullNameMr());
		objEntity.setEmployeeFNameMr(mstEmployeeModel.getEmployeeFNameMr());
		objEntity.setEmployeeMNameMr(mstEmployeeModel.getEmployeeMNameMr());
		objEntity.setEmployeeLNameMr(mstEmployeeModel.getEmployeeLNameMr());
		objEntity.setEmployeeMotherName(mstEmployeeModel.getEmployeeMotherName());
		objEntity.setMaritalStatus(mstEmployeeModel.getMaritalStatus());
		objEntity.setDob(mstEmployeeModel.getDob());
		objEntity.setBloodGroup(mstEmployeeModel.getBloodGroup());
		objEntity.setMobileNo1(mstEmployeeModel.getMobileNo1());
		objEntity.setMobileNo2(mstEmployeeModel.getMobileNo2());
		objEntity.setLandlineNo(mstEmployeeModel.getLandlineNo());
		objEntity.setEmailId(mstEmployeeModel.getEmailId());
		objEntity.setReligionCode(mstEmployeeModel.getReligionCode());
		objEntity.setEidNo(mstEmployeeModel.getEidNo());

		objEntity.setUidNo(mstEmployeeModel.getUidNo1() + mstEmployeeModel.getUidNo2() + mstEmployeeModel.getUidNo3());

		objEntity.setPanNo(mstEmployeeModel.getPanNo().toUpperCase());
		objEntity.setAddress1(mstEmployeeModel.getAddress1().toUpperCase());
		objEntity.setAddress2(mstEmployeeModel.getAddress2().toUpperCase());
		objEntity.setAddress3(mstEmployeeModel.getAddress3().toUpperCase());
		objEntity.setPinCode(mstEmployeeModel.getPinCode());
		objEntity.setVillageCode(mstEmployeeModel.getVillageCode());
		objEntity.setVillageName(mstEmployeeModel.getVillageName().toUpperCase());
		objEntity.setTalukaCode(mstEmployeeModel.getVillageCode());
		objEntity.setDistrictCode(mstEmployeeModel.getDistrictCode());
		objEntity.setStateCode(mstEmployeeModel.getStateCode());
		objEntity.setCountryCode(mstEmployeeModel.getCountryCode());
		objEntity.setDtInitialAppointmentParentInst(mstEmployeeModel.getAppointmentDate());
		objEntity.setDoj(mstEmployeeModel.getDoj());
		objEntity.setUserId(mstEmployeeModel.getUserId());
		objEntity.setCadreCode(mstEmployeeModel.getCadreId());
		objEntity.setEmpClass(mstEmployeeModel.getEmpClass());
		objEntity.setFirstDesignationCode(mstEmployeeModel.getFirstDesignationId());
		objEntity.setDesignationCode(mstEmployeeModel.getDesignationId());
		objEntity.setParentAdminDepartmentId(mstEmployeeModel.getParentAdminDepartmentId());
		objEntity.setParentFieldDepartmentId(mstEmployeeModel.getParentFieldDepartmentId());
		objEntity.setAdminDepartmentCode(mstEmployeeModel.getAdminDepartmentId());
		objEntity.setFieldDepartmentCode(mstEmployeeModel.getFieldDepartmentId());
		objEntity.setCurrentOfficeCode(mstEmployeeModel.getCurrentOfficeId());
		objEntity.setPayCommissionCode(mstEmployeeModel.getPayCommissionCode());
		objEntity.setPayScaleCode(mstEmployeeModel.getPayScaleCode());
		objEntity.setPayInPayBand(mstEmployeeModel.getPayInPayBand());
		objEntity.setGradePay(mstEmployeeModel.getGradePay());
		objEntity.setBankCode(mstEmployeeModel.getBankId());
		objEntity.setIfscCode(mstEmployeeModel.getIfscCode());
		objEntity.setBankAcntNo(mstEmployeeModel.getBankAcntNo());
		objEntity.setBankBranchCode(mstEmployeeModel.getBankBranchId());
		objEntity.setDdoCode(mstEmployeeModel.getDdoCode());
		objEntity.setApprovalByDdoDate(mstEmployeeModel.getApprovalByDdoDate());
		objEntity.setBillGroupId(mstEmployeeModel.getBillgroupId());

		if (mstEmployeeModel.getPayCommissionCode() == 2) {
			objEntity.setBasicPay(mstEmployeeModel.getBasicPay().doubleValue());
		} else {
			objEntity.setSevenPcBasic(mstEmployeeModel.getSevenPcBasic().doubleValue());
		}

		objEntity.setPercentageOfBasic(mstEmployeeModel.getPercentageOfBasic());
		objEntity.setHeadActCode(mstEmployeeModel.getHeadActCode());
		objEntity.setEmployeeType(mstEmployeeModel.getEmployeeType());
		objEntity.setIsActive(5);
		objEntity.setEmpServiceEndDate(mstEmployeeModel.getEmpServiceEndDate());
		objEntity.setSuperAnnDate(mstEmployeeModel.getSuperAnnDate());
		objEntity.setWithEffectFromDate(mstEmployeeModel.getWithEffectFromDate());
		objEntity.setGradeId(mstEmployeeModel.getGradeId());
		objEntity.setPhotoAttachmentId(mstEmployeeModel.getPhotoAttachmentId());
		objEntity.setSignatureAttachmentId(mstEmployeeModel.getSignatureAttachmentId());
		objEntity.setCreatedUserId(mstEmployeeModel.getCreatedUserId());
		objEntity.setCreatedDate(new Date());
		objEntity.setUpdatedDate(mstEmployeeModel.getUpdatedDate());
		objEntity.setUpdatedUserId(mstEmployeeModel.getUpdatedUserId());
		objEntity.setSvnthpaybasic(mstEmployeeModel.getSvnthpaybasic());
		objEntity.setPayscalelevelId(mstEmployeeModel.getPayscalelevelId());
		objEntity.setSevenPcLevel(Integer.parseInt(mstEmployeeModel.getPayscalelevelId()));
		objEntity.setRemark(mstEmployeeModel.getRemark());
		objEntity.setPostdetailid(mstEmployeeModel.getPostdetailid());
		objEntity.setPhysicallyHandicapped(mstEmployeeModel.getPhysicallyHandicapped());
		objEntity.setDtInitialAppointmentParentInst(mstEmployeeModel.getDtInitialAppointmentParentInst());
		objEntity.setAcDcpsMaintedBy(mstEmployeeModel.getAcDcpsMaintainedBy());

		// Extra
		
		 * private String employeeFullName; private String designationName; private
		 * String departmentNameEn;
		 

		String[] saveimage = savePhotoSignature(files, mstEmployeeModel.getDeptNm(), mstEmployeeModel.getEmployeeId(),
				mstEmployeeModel.getPhotoAttachmentId(), mstEmployeeModel.getSignatureAttachmentId());
		objEntity.setPhotoAttachmentId(saveimage[0].toString());
		objEntity.setSignatureAttachmentId(saveimage[1]);

		// Serializable id=(Integer)reuslt.get(0);

		// Start 1st Dec 2020 : Nominee Dtls Logic
		String[] lArrNomName = mstEmployeeModel.getStrArrNomineeName().split("~");
		String[] lArrAddress1 = mstEmployeeModel.getStrArrAddress().split("~");
		String[] lArrDateOfBirth = mstEmployeeModel.getStrArrDob().split("~");
		String[] lArrPercentShare = mstEmployeeModel.getStrArrPercentShare().split("~");
		String[] lArrRelationship = mstEmployeeModel.getStrArrRelationship().split("~");

		MstNomineeDetailsEntity[] lArrNomineeDtls = new MstNomineeDetailsEntity[lArrNomName.length];
		for (int i = 0; i < lArrNomName.length; i++) {
			if (!lArrNomName[i].equals("")) {
				MstNomineeDetailsEntity lObjNomineeDtls = new MstNomineeDetailsEntity();

				// lObjNomineeDtls.setDcpsEmpId(lObjEmpData);
				lObjNomineeDtls.setNomineename(lArrNomName[i]);
				lObjNomineeDtls.setNomineeaddress(lArrAddress1[i]);
				Date dtBirthDate = null;

				if (mstEmployeeModel.getStrArrDob() != null && !"".equals(mstEmployeeModel.getStrArrDob().trim())) {
					MstEmployeeModel mstEmployeeModel1 = new MstEmployeeModel();
					// String pattern = "yyyy-MM-dd";
					SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
					try {
						Date date = formatter.parse(lArrDateOfBirth[i]);
						mstEmployeeModel1.setRdob(date);
						dtBirthDate = mstEmployeeModel1.getRdob();
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
					}

				}

				lObjNomineeDtls.setDob(dtBirthDate);
				long lLngPercentShare = Long.parseLong(lArrPercentShare[i].trim());
				lObjNomineeDtls.setPercent_share(Integer.parseInt(lArrPercentShare[i].trim()));
				lObjNomineeDtls.setRelation(lArrRelationship[i]);
				lObjNomineeDtls.setCreateddate(new Date());
				lObjNomineeDtls.setCreatedid(mstEmployeeModel.getCreatedUserId());
				lObjNomineeDtls.setIsactive("Y");
				lObjNomineeDtls.setUpdatedate(mstEmployeeModel.getUpdatedDate());
				lObjNomineeDtls.setUpdateid(mstEmployeeModel.getUpdatedUserId());
				// lObjNomineeDtls.setEmployeeId(objEntity.getEmployeeId());

				lArrNomineeDtls[i] = lObjNomineeDtls;
			}

		}

		// End 1st Dec 2020 : Nominee Dtls Logic

		Serializable id = mstEmployeeRepo.updateEmployeeConfiguration(objEntity, mstEmployeeModel, lArrNomineeDtls);

		return (int) id;
	}

	@Override
	public String checkIsSevaarth(int uniqid) {
		return mstEmployeeRepo.checkIsSevaarth(uniqid);
	}

	@Override
	public EmployeeSuspensionEntity getSuspensionPercentage(String sevaarthId,String fromDate) {
		// TODO Auto-generated method stub
		return mstEmployeeRepo.getSuspensionPercentage(sevaarthId,fromDate);
	}

	@Override
	public int sevaarthIdAlreadyExists(String sevaarthId) {
		// TODO Auto-generated method stub
		return mstEmployeeRepo.sevaarthIdAlreadyExists(sevaarthId);
	}

	@Override
	public String getDtoRegNumber(String ddoCode) {
		// TODO Auto-generated method stub
		return mstEmployeeRepo.getDtoRegNumber(ddoCode);
	}

	@Override
	public LNAHBAEmployeeRequestMstEntity findHBADetails(int employeeId, int allowDedCode) {
		return mstEmployeeRepo.findHBADetails(employeeId, allowDedCode);
	}

	@Override
	public LNACAEmployeeRequestMstEntity findCADetails(Integer employeeId, int allowDedCode) {
		// TODO Auto-generated method stub
		return mstEmployeeRepo.findCADetails(employeeId, allowDedCode);
	}

	@Override
	public LNAVAEmployeeRequestMstEntity findVADetails(Integer employeeId, int allowDedCode) {
		// TODO Auto-generated method stub
		return mstEmployeeRepo.findVADetails(employeeId, allowDedCode);
	}

	@Override
	public LNAFAEmployeeRequestMstEntity findFADetails(Integer employeeId, int allowDedCode) {
		// TODO Auto-generated method stub
		return mstEmployeeRepo.findFADetails(employeeId, allowDedCode);
	}

	@Override
	public void updatemstEmployeeEntity(MstEmployeeEntity mstEmployeeEntity) {
		// TODO Auto-generated method stub
		mstEmployeeRepo.updatemstEmployeeEntity(mstEmployeeEntity);
	}

	@Override
	public List<Object[]> findEmployeeConfigurationGetsvnbasicpayChangedetails(String string, Double svbasic) {
		return mstEmployeeRepo.findEmployeeConfigurationGetsvnbasicpayChangedetails(string, svbasic);
	}

	@Override
	public NSDLBHDtlsEntity findBHEntityById(Integer bhId) {
		// TODO Auto-generated method stub
		return mstEmployeeRepo.findBHEntityById(bhId);
	}

	@Override
	public void updateBhEntity(NSDLBHDtlsEntity nSDLBHDtlsEntity) {
		// TODO Auto-generated method stub
		mstEmployeeRepo.updateBhEntity(nSDLBHDtlsEntity);
	}

	@Override
	public String getDdoRegNumber(String userName) {
		return mstEmployeeRepo.getDdoRegNumber(userName);
	}

	
	@Override
	public List<MstSubDepartmentModel> findfycorparationname(Integer corno) {
		List<Object[]> lstprop = mstEmployeeRepo.findfycorparationname(corno);
		List<MstSubDepartmentModel> lstObj = new ArrayList<>();
		if (!lstprop.isEmpty()) {
			for (Object[] objLst : lstprop) {
				MstSubDepartmentModel obj = new MstSubDepartmentModel();
				obj.setSubDepartmentId(StringHelperUtils.isNullInt(objLst[0]));
				obj.setSubDepartmentNameEn(StringHelperUtils.isNullString(objLst[1]));
				lstObj.add(obj);
			}
		}

		return lstObj;
	}



	@Override
	public List<Object[]> employeehraandta(String sevaarthId) {
		return mstEmployeeRepo.employeehraandta(sevaarthId);
	}

	@Override
	public OtherAllowanceEntity findBEGISAmt(Integer giscatagory, String cadreCode) {
		// TODO Auto-generated method stub
		return mstEmployeeRepo.findBEGISAmt(giscatagory, cadreCode);
	}

	@Override
	public GPFEmpWiseDeduPerTrnEntity finfGPFPercente(String sevaarthId, String startDate) {
		return mstEmployeeRepo.finfGPFPercente(sevaarthId, startDate);
	}

	@Override
	public MstGPFRegularDeduPerTrnEntity findbyCommonPercante(String startDate) {
		return mstEmployeeRepo.findbyCommonPercante(startDate);
	}

	@Override
	public OtherAllowanceEntity findBEGISAmtwith(Integer giscatagory) {
		return mstEmployeeRepo.findBEGISAmtwith(giscatagory);
	}

	@Override
	public int getSevaarthid(String sevaarthid) {
		return mstEmployeeRepo.getSevaarthid(sevaarthid);
	}

	@Override
	public int checkSevaarthIdExistInGpfDetailMst(String sevaarthid) {
		// TODO Auto-generated method stub
		return mstEmployeeRepo.checkSevaarthIdExistInGpfDetailMst(sevaarthid);
	}

	@Override
	public void saveGpfDetails(MstGpfDetailsEntity mstGpfDetailsEntity) {
		// TODO Auto-generated method stub
		mstEmployeeRepo.saveGpfDetails(mstGpfDetailsEntity);
	}

	@Override
	public MstEmployeeEntity emplfindMstEmpByEmpId(int empId) {
		// TODO Auto-generated method stub
		return mstEmployeeRepo.findMstEmpByBillGroupId(empId);
	}

	@Override
	public List<MstEmployeeModel> getCadreGroupMstDataNew(String cadreid, String dob) {
		List<MstEmployeeModel> lstlstDeptEligibilityForAllowAndDeductEntity = new ArrayList<>();
		List<Object[]> lstObj = mstEmployeeRepo.getCadreGroupMstDataNew(cadreid);
		BigDecimal ag = null;
		int age = 0;
		for (Object obj[] : lstObj) {
			MstEmployeeModel mstEmployeeModel = new MstEmployeeModel();
			ag = (BigDecimal) obj[6];

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
	public List<Long> validateAccountNum(String accountNum, String employeeid) {
		// TODO Auto-generated method stub
		return mstEmployeeRepo.validateAccountNum(accountNum,employeeid);
	}

	@Override
	public PayfixationAdvEntity findPayfixDetails(String sevaarthId, int commoncodeComponentPayFixDiffCode) {
		return mstEmployeeRepo.findPayfixDetails(sevaarthId, commoncodeComponentPayFixDiffCode);
	}

	@Override
	public ExcessPayRecoveryEntity findExcPayRec(String sevaarthId) {
		// TODO Auto-generated method stub
		return  mstEmployeeRepo.findExcPayRec(sevaarthId);
	}

	@Override
	public BigInteger findEmpSuspend(String sevaarthId) {
		// TODO Auto-generated method stub
		return mstEmployeeRepo.findEmpSuspend(sevaarthId);
	}

	@Override
	public List<MstEmployeeEntity> findAllRetiredEmployeeByDDOCodeAndBillGroup(String ddoCode, int schemeBillgroupId,
			int paybillMonth, int paybillYear) {
		// TODO Auto-generated method stub
		return mstEmployeeRepo.findAllRetiredEmployeeByDDOCodeAndBillGroup(ddoCode, schemeBillgroupId, paybillMonth, paybillYear);
	}

	@Override
	public List<MstCommonEntity> getServicesList(String commonCodes) {
		return mstEmployeeRepo.getServicesList(commonCodes);
	}

	@Override
	public LNAMotorVehicleAdvEmployeeRequestMstEntity findmotorCycleAdvDetails(Integer employeeId,
			int allowDedCode) {
		// TODO Auto-generated method stub
		return mstEmployeeRepo.findmotorCycleAdvDetails(employeeId, allowDedCode);
	}

	@Override
	public List<MstCommonEntity> getDettachReasonsList(String commonCodes) {
		// TODO Auto-generated method stub
		return mstEmployeeRepo.getDettachReasonsList(commonCodes);
	}

	@Override
	public int gettrano(String userName) {
		return mstEmployeeRepo.gettrano(userName);
	}	
*/



	}
