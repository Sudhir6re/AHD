package com.mahait.gov.in.service;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.web.multipart.MultipartFile;

public interface MstEmployeeService {/*
	
	public List<MstEmployeeEntity> findAllEmployeeByDDOCode(String ddoCode);
	
	public String saveEmployeeBillGroup(String object,int billGroupId);
	
	public List<MstEmployeeModel> findAllEmployees();
	
	public List<MstEmployeeModel> findAllEmployeesByDDOName(String ddoCode);
	
	public MstEmployeeEntity findMstEmpByBillGroupIdForReject(int id);
	
	public List<Object[]> findAllGroupAndSuperAnnuationAge(int id);
	
	public List<Object[]> findPayCommissionAgaintPayScaleList(int id);
	
	public List<Object[]> findEmployeeConfigurationGetCurrentPost(int id,String ddocode,String currpostcode);

	public List<Object[]> findEmployeeConfigurationGetGradePay(int id);
	
	public int saveEmployeeConfiguration(MstEmployeeModel mstEmployeeModel,MultipartFile[] files);
	
	public List<Object[]> findLevelsAgainstSeventhPayCommission();
	
	public List<MstEmployeeEntity> findAllEmployeeByddoCode(String ddoCode);
	
	public List<MstEmployeeEntity> firstgetfindAllEmployeeByddoCode(String ddoCode);
	
	public List<Object[]> findAttachedEmployee(String ddocode,String scmebillgroupid);
	
	public List<Object[]> findDettachEmployee(String ddocode,String scmebillgroupid);
	
	public String saveAttachDettachEmployee(MpgSchemeBillGroupModel mpgSchemeBillGroupModel);
	
	public List<Object[]> findDeptAllowanceDeductionReport(String ddoCode);
	
	public List<MstEmployeeEntity> findAllEmployeeByDDOCodeAndBillGroup(String ddoCode,int billGroupId);
	
	public List<Object[]> employeeAllowDeduction(String sevaarthId);
	
	public String getCityClassAgainstDDO(String ddoCode);
	
	public int getpayCommissionAgainstEmployee(String sevaarthId);
	public List<Object[]> findEmployeeConfigurationGetpayscale(int payCommissionid);
	
	public List<Object[]> findEmployeeConfigurationGetsvnbasicpay(String payscale);
	public List<MstEmployeeModel> getEmployeeDetails(String strddo,String locale);
	public MstEmployeeModel getEmployeeinfo(Integer emp_id);
	public int updateEmployeeConfiguration(MstEmployeeModel mstEmployeeModel,MultipartFile[] files) ;
	public int deleteEmployeeConfiguration(Integer empid);
	public List<Long> validateUIDUniq(String uid,String employeeid);
	public List<MstEmployeeModel> getApproveEmployeeDetails(String strddo, String locale);
	public List<Long> approveEmployeeConfiguration(String empid,String sevaarthid,String dcpsgpfflg);
	public List<Long> rejectEmployeeConfiguration(String empid);
	public List<Object[]> getBankBranch(String strbankid);
	public List<Object[]> getDcpsAccnMaintainby();
	public List<Object[]> getAccountMaintainby();
	public List<Object[]> getPfSeries(String accmainby);
	public List<MstCadreGroupEntity> getGISGroup();
	public List<Object[]> getGISApplicable();
	public List<Object[]> getRelation();
	public  String[] savePhotoSignature(MultipartFile[] files,String DeptNm,Integer empid,String existphotpath,String existsignpath);
	public  String[] savePhotoSignature(MultipartFile[] files,String DeptNm,Integer empid);
	public String findEmployeeConfigurationUploadImage(String strmagepath,String outputNm);
	public List<MstEmployeeEntity> findAllEmployeesNotMap();
	public List<MstEmployeeEntity> findAllEmployeesNotMap1();
	public String saveEmployeeddoCode(String sevaarthId,String ddoCode);
	public MstEmployeeEntity findMstEmpByDDOCodeForReject(int id);
	public List<DDOScreenModel> findDDOScreenDataTable(String locale,String ddoCode);
	public List<MstCadreModel> getCadreMstData(String locale);
	public List<Object[]> getCadreGroupMstData(String locale,String strCadreId);
	public List<Object[]> getInstitueDtls(String ddocode);
	public long getCount(String tempSevarthEmpCode);
	public List<MstNomineeDetailsEntity> getNominees(String empId);
	public List<MstEmployeeModel> getDcpsEmployeeDetails(String strddo,String locale);
	public  Character getLastDigit(String lStrDCPSId);
	public  Map getMappingData();
	public List<Long> forwardLvlThreeEmployeeConfiguration(String empid,String sevaarthid,String dcpsgpfflg) ;
	public List<Long> approveDcpsEmployeeConfiguration(String empid,String Dcpsnumber,String sevaarthid,String dcpsgpfflg);
	public List<Object[]> GetCurrentPostByLvlTwo(int designationId, String ddocode);
	public List<Object[]> GetCurrentPostByLvlThree(int designationId, String ddocode) ;
	public List<Long> validateTelephone(String strTelPhone,String employeeid);
	public List<Long> validateMobileno(long mobileno,String employeeid);
	public List<Long> validateEmail(String strEmail,String employeeid);
	public List<Long> validatePancard(String strPancard,String employeeid);
	public String getOfficeNameAddress(String strddo,String deptCode);
	 //check is paybill in process
	 public List<Object[]> isPaybillIsInProcessForAttach(String sevaarthId);
	 
	 //Added for paybill validation for emp data not null
	 public List<MstEmployeeEntity> findAllWorkingEmployeeByDDOCodeAndBillGroup(String ddoCode,int billGroupId,int month,int year);
	 public EmployeeAllowDeducComponentAmtEntity findGRPComponentsData(String sevaarthId,int allowDedCode);
	 public LoanEmployeeDtlsEntity findLoanDetails(int empId,int allowDedCode);
	 public List<Object[]> supEmployeeAllowDeduction(String sevaarthId);

	public int getSevenPcLevel(String sevaarthId);

	public int updateEmpLoanAmt(Integer employeeId, BigInteger gpfabc);

	public PaybillLoanRecoverDtlsEntity findInstallmentNoForCurrMont(String sevaarthId, int mon, int curryear);

	public int saveEmpLoanData(String sevaarthId, Double gpfAdvD, int mon, int curryear);

	public List<Object[]> findEmployeeConfigurationpayScaleSeven(int payScaleSeven);

	public List<Object[]> findEmployeeConfigurationGetSixPayScale(int payCommission);

	public List<MstEmployeeModel> lstAllBankBranchList();
	
	public List<MstEmployeeModel> lstCurrentPost();

	public List<MstEmployeeModel> lstCadreMst();
	
	public int saveChangeDetails(@Valid MstEmployeeModel mstEmployeeModel, MultipartFile[] files);
	
	public String checkIsSevaarth(int uniqid);

	public EmployeeSuspensionEntity getSuspensionPercentage(String sevaarthId, String fromDate);


	public String getDtoRegNumber(String ddoCode);


	public LNAHBAEmployeeRequestMstEntity findHBADetails(int employeeId,
			int allowDedCode);

	public LNACAEmployeeRequestMstEntity findCADetails(Integer employeeId, int allowDedCode);

	public LNAVAEmployeeRequestMstEntity findVADetails(Integer employeeId, int allowDedCode);

	public LNAFAEmployeeRequestMstEntity findFADetails(Integer employeeId, int allowDedCode);

	public int sevaarthIdAlreadyExists(String sevaarthId);

	public void updatemstEmployeeEntity(@Valid MstEmployeeEntity mstEmployeeEntity);

	public List<Object[]> findEmployeeConfigurationGetsvnbasicpayChangedetails(String string,Double svnbasic);

	public NSDLBHDtlsEntity findBHEntityById(Integer bhId);

	public void updateBhEntity(NSDLBHDtlsEntity nSDLBHDtlsEntity);

	public String getDdoRegNumber(String userName);

	public LoanEmployeeDtlsEntity findGPFADetails(String sevaarthid, int commoncodeComponentGpfaCode);

	public List<MstSubDepartmentModel> findfycorparationname(Integer corno);

	public DDOScreenEntity findAllGroup(String ddoCode);

	public List<Object[]> employeehraandta(String sevaarthId);

	public OtherAllowanceEntity findBEGISAmt(Integer giscatagory,String cadreCode);

	public GPFEmpWiseDeduPerTrnEntity finfGPFPercente(String sevaarthId, String startDate);

	public MstGPFRegularDeduPerTrnEntity findbyCommonPercante(String startDate);

	public OtherAllowanceEntity findBEGISAmtwith(Integer giscatagory);

	public int getSevaarthid(String sevaarthid);

	public int checkSevaarthIdExistInGpfDetailMst(String sevaarthid);

	public void saveGpfDetails(MstGpfDetailsEntity mstGpfDetailsEntity);

	public MstEmployeeEntity emplfindMstEmpByEmpId(int parseInt);

	public List<MstEmployeeModel> getCadreGroupMstDataNew(String cadreid,String dob);

	public List<Long> validateAccountNum(String accountNum,String employeeid);

	public PayfixationAdvEntity findPayfixDetails(String sevaarthId, int commoncodeComponentPayFixDiffCode);

	public ExcessPayRecoveryEntity findExcPayRec(String sevaarthId);

	public LoanEmployeeDtlsEntity findGPFAdvDetails(String sevaarthId, int commoncodeComponentGpfaCode);

	public BigInteger findEmpSuspend(String sevaarthId);

	public List<MstEmployeeEntity> findAllRetiredEmployeeByDDOCodeAndBillGroup(String ddoCode, int schemeBillgroupId,
			int paybillMonth, int paybillYear);

	public List<MstCommonEntity> getServicesList(String string);

	public LNAMotorVehicleAdvEmployeeRequestMstEntity findmotorCycleAdvDetails(Integer employeeId,
			int allowDedCode);
	
	public List<MstCommonEntity> getDettachReasonsList(String string);

	public int gettrano(String userName);
	
*/}
