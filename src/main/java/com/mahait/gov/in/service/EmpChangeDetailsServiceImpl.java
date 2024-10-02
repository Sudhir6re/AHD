package com.mahait.gov.in.service;

import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.imageio.ImageIO;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.mahait.gov.in.model.EmpChangeDetailsModel;
import com.mahait.gov.in.model.MstEmployeeModel;
import com.mahait.gov.in.repository.EmpChangeDetailsRepo;

@Service
@Transactional
public class EmpChangeDetailsServiceImpl implements EmpChangeDetailsService {
	
	@Autowired
	EmpChangeDetailsRepo empChangeDetailsRepo;
	
	@Autowired
	private Environment environment;
	
	

	@Autowired
	EmployeeIncrementService annualIncrementService;


	@Override
	public List<EmpChangeDetailsModel> findEmpforChangeDtls(String userName) {
		// TODO Auto-generated method stub
		
		
		List<EmpChangeDetailsModel> lstObj = new ArrayList<>();
		List<Object[]> lstGenerateBillDetails = empChangeDetailsRepo.findEmpforChangeDtls(userName);
		lstObj = new ArrayList<>();

		
		//employee_full_name_en,sevaarth_id,seven_pc_basic
		if (!lstGenerateBillDetails.isEmpty()) {/*
			for (Object[] objLst : lstGenerateBillDetails) {
				EmpChangeDetailsModel obj = new EmpChangeDetailsModel();
				 if(objLst[0]!=null) {
					 obj.setEmpName(StringHelperUtils.isNullString(objLst[0]));
				 }
				 if(objLst[1]!=null) {
					 obj.setSevaarthId(StringHelperUtils.isNullString(objLst[1]));
				 }else {
					 obj.setSevaarthId("");
				 }
				SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
				String DOB  = dateFormat.format(objLst[2]);
				obj.setDOB(StringHelperUtils.isNullString(DOB));
				obj.setGender(StringHelperUtils.isNullChar(objLst[3]));
				obj.setEmpId(StringHelperUtils.isNullInt(objLst[4]));
				
				
			
				
				
				lstObj.add(obj);
			}
		*/}
		return lstObj;
	}

	

	@Override
	public MstEmployeeModel getEmpData(int empId) {
		// TODO Auto-generated method stub
		return empChangeDetailsRepo.getEmpData(empId);
	}



	@Override
	public List<Object[]> getEmpSignPhoto(Integer employeeId) {
		// TODO Auto-generated method stub
		return empChangeDetailsRepo.getEmpSignPhoto(employeeId);
	}



	/*@Override
	public List<MstSevenMatrixEntity> getsevenPCBasic(int payscaleId) {
		// TODO Auto-generated method stub
		return empChangeDetailsRepo.getsevenPCBasic(payscaleId);
	}



	@Override
	public List<MstEmployeeEntity> findEmpLst(String ddocode) {
		// TODO Auto-generated method stub
		return empChangeDetailsRepo.findEmpLst(ddocode);
	}



	@Override
	public MstEmployeeEntity getEmployeeData(int empId) {
		// TODO Auto-generated method stub
		return empChangeDetailsRepo.getEmployeeData(empId);
	}
	public List<Object[]> GetCurrentPost(int designationId, String ddocode){
		List<Object[]> result=empChangeDetailsRepo.GetCurrentPost(designationId, ddocode);
		return result;
	}*/



	@Override
	public int updateChangeEmpDtls(/*@Valid MstEmployeeEntity mstEmployeeEntity, MultipartFile[] files, int roleid,UserInfo userInfo*/) {
		return 0;/*
		
		if(files.length!=0)
		{
			String[] saveimage = savePhotoSignature(files, mstEmployeeEntity.getFieldDepartmentCode(), mstEmployeeEntity.getEmployeeId(),
					mstEmployeeEntity.getPhotoAttachmentId(), mstEmployeeEntity.getSignatureAttachmentId());
			
		mstEmployeeEntity.setPhotoAttachmentId(saveimage[0].toString());
		mstEmployeeEntity.setSignatureAttachmentId(saveimage[1]);
		}
		
		
		MstEmployeeEntity mstEmployeeEntity2 = empChangeDetailsRepo.findempid(mstEmployeeEntity.getEmployeeId());
		
		
		
		
		
		
		if(roleid==1) {
			//add entry into annual increment
			try {
				Calendar cal = Calendar.getInstance();
				Date today = cal.getTime();
				cal.add(Calendar.YEAR, 1); // to get previous year add -1
				cal.set(Calendar.DATE, 1);
				Date nextYear = cal.getTime();
			
				Date date1 =new Date();
				date1.setDate(1);
				
				Date toIncDate =nextYear;
				
				Integer oldBasic=0;
	            Integer newBasic=0;
				
				if(mstEmployeeEntity2.getPayCommissionCode()==2) {
					 oldBasic=mstEmployeeEntity2.getBasicPay().intValue();
		             newBasic=mstEmployeeEntity.getBasicPay().intValue();
				}else if(mstEmployeeEntity2.getPayCommissionCode()==8) {
					 oldBasic=mstEmployeeEntity2.getSevenPcBasic().intValue();
		             newBasic=mstEmployeeEntity.getSevenPcBasic().intValue();
				}
	            
				
				Integer currentBasicLevelId =Integer.parseInt(mstEmployeeEntity2.getPayscalelevelId());

				Integer incrementBasicLevelId = Integer.parseInt(mstEmployeeEntity.getPayscalelevelId());

				EmployeeIncrementEntity employeeIncrementEntity = new EmployeeIncrementEntity();
				

				// employeeIncrementEntity.setBasicPayIncrementId(basicPayIncrementId1);
				employeeIncrementEntity.setCurrentBasicPay(oldBasic);
				employeeIncrementEntity.setEffective_from_date(date1);
				employeeIncrementEntity.setEmployeeId(mstEmployeeEntity.getEmployeeId());
				employeeIncrementEntity.setIncrementBasicPaySal(newBasic);
				employeeIncrementEntity.setIncrementOrderDate(date1);
				employeeIncrementEntity.setIncrementOrderNo("");
				employeeIncrementEntity.setPreBasicPay(oldBasic);
				employeeIncrementEntity.setRemark("");
				employeeIncrementEntity.setSevaarthId(mstEmployeeEntity.getSevaarthId());
				employeeIncrementEntity.setCreatedDate(new Date());
				employeeIncrementEntity.setMonth(today.getMonth()+1);
				employeeIncrementEntity.setIsActive('3');
				employeeIncrementEntity.setDdoCode(mstEmployeeEntity.getDdoCode());
				employeeIncrementEntity.setTo_increment_date(nextYear);
				employeeIncrementEntity.setCurrentBasicLevelId(currentBasicLevelId);
				employeeIncrementEntity.setIncrementBasicLevelId(incrementBasicLevelId);

				EmployeeIncrementEntity previousIncrementDtlsObj = annualIncrementService.getPreIncDtsByempId(mstEmployeeEntity.getEmployeeId());
				if (previousIncrementDtlsObj != null) {
				 	previousIncrementDtlsObj.setTo_increment_date(nextYear);
					annualIncrementService.updateEmpIncrementToDate(previousIncrementDtlsObj,userInfo);
				}

				annualIncrementService.saveAnnualIncrement(employeeIncrementEntity);
				
				//annualIncrementService.saveAnnualIncrementBasicMst(mstEmployeeEntity);
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			//end annula increment entry
		}else if(roleid==2) {
			EmployeeIncrementEntity employeeIncrementEntity=annualIncrementService.findOldAnnualIncrement(mstEmployeeEntity.getEmployeeId());
			if(employeeIncrementEntity!=null) {
				employeeIncrementEntity.setIsActive('1');
				annualIncrementService.updateEmpIncrementToDate(employeeIncrementEntity,userInfo);
			}
		}
		
		
		ChangeDtlsHst changeDtlsHst = new ChangeDtlsHst();
		
		
		
		if(roleid==1)
		{
		if(mstEmployeeEntity2!=null) {
			
			
			
			
			//History maintainance start//
			changeDtlsHst.setEmployeeId(mstEmployeeEntity2.getEmployeeId());
			changeDtlsHst.setUidNo(mstEmployeeEntity2.getUidNo());
			changeDtlsHst.setEidNo(mstEmployeeEntity2.getEidNo());
			changeDtlsHst.setSalutation(mstEmployeeEntity2.getSalutation());
			changeDtlsHst.setSevaarthId(mstEmployeeEntity2.getSevaarthId());
			changeDtlsHst.setEmployeeFullNameEn(mstEmployeeEntity2.getEmployeeFullNameEn());
			changeDtlsHst.setEmployeeFNameEn(mstEmployeeEntity2.getEmployeeFNameEn().toUpperCase());
			changeDtlsHst.setEmployeeMNameEn(mstEmployeeEntity2.getEmployeeMNameEn().toUpperCase());
			changeDtlsHst.setEmployeeLNameEn(mstEmployeeEntity2.getEmployeeLNameEn().toUpperCase());
			changeDtlsHst.setEmployeeFullNameMr(mstEmployeeEntity2.getEmployeeFullNameMr());
			changeDtlsHst.setEmployeeFNameMr(mstEmployeeEntity2.getEmployeeFNameMr());
			changeDtlsHst.setEmployeeLNameMr(mstEmployeeEntity2.getEmployeeLNameMr());
			changeDtlsHst.setEmployeeMotherName(mstEmployeeEntity2.getEmployeeMotherName());
			changeDtlsHst.setBuckleNo(mstEmployeeEntity2.getBuckleNo());
			changeDtlsHst.setGender(mstEmployeeEntity2.getGender());
			changeDtlsHst.setReligionCode(mstEmployeeEntity2.getReligionCode());
			changeDtlsHst.setMaritalStatus(mstEmployeeEntity2.getMaritalStatus());
			changeDtlsHst.setEmployeeMNameMr(mstEmployeeEntity2.getEmployeeMNameMr());
			changeDtlsHst.setDob(mstEmployeeEntity2.getDob());
			changeDtlsHst.setDoj(mstEmployeeEntity2.getDoj());
			changeDtlsHst.setAddress1(mstEmployeeEntity2.getAddress1().toUpperCase());
			changeDtlsHst.setAddress2(mstEmployeeEntity2.getAddress2().toUpperCase());
//			changeDtlsHst.setAddress3(mstEmployeeEntity2.getAddress3().toUpperCase());
//			changeDtlsHst.setLocality(mstEmployeeEntity2.getLocality());
			changeDtlsHst.setStateCode(mstEmployeeEntity2.getStateCode());
			changeDtlsHst.setDistrictCode(mstEmployeeEntity2.getDistrictCode());
//			changeDtlsHst.setVillageName(mstEmployeeEntity2.getVillageName().toUpperCase());
			changeDtlsHst.setPinCode(mstEmployeeEntity2.getPinCode());
			changeDtlsHst.setPhysicallyHandicapped(mstEmployeeEntity2.getPhysicallyHandicapped());
			changeDtlsHst.setMobileNo1(mstEmployeeEntity2.getMobileNo1());
			changeDtlsHst.setEmailId(mstEmployeeEntity2.getEmailId());
			changeDtlsHst.setPanNo(mstEmployeeEntity2.getPanNo());
			changeDtlsHst.setCreatedDate(new Date());
			changeDtlsHst.setRemark(mstEmployeeEntity2.getRemark());
			
			//Employee Details End
			
			
			//Department Details Start
			changeDtlsHst.setParentAdminDepartmentId(mstEmployeeEntity2.getParentAdminDepartmentCode());
			changeDtlsHst.setParentFieldDepartmentId(mstEmployeeEntity2.getParentFieldDepartmentCode());
			changeDtlsHst.setSubDeptId(mstEmployeeEntity2.getSubDeptId());
			changeDtlsHst.setSubCorporationId(mstEmployeeEntity2.getSubCorporationId());
			changeDtlsHst.setAdminDepartmentCode(mstEmployeeEntity2.getParentAdminDepartmentCode());
			changeDtlsHst.setFieldDepartmentCode(mstEmployeeEntity2.getFieldDepartmentCode());
			changeDtlsHst.setIsChangeParentDepartment(mstEmployeeEntity2.getIsChangeParentDepartment());
			changeDtlsHst.setReasonForChngParentFieldDept(mstEmployeeEntity2.getReasonForChngParentFieldDept());
			changeDtlsHst.setCadreCode(mstEmployeeEntity2.getCadreCode());
			changeDtlsHst.setEmpClass(mstEmployeeEntity2.getEmpClass());
			if(mstEmployeeEntity2.getSuperAnnAge()!=null)
			changeDtlsHst.setSuperAnnAge(Integer.valueOf(mstEmployeeEntity2.getSuperAnnAge()));
			changeDtlsHst.setEmpServiceEndDate(mstEmployeeEntity2.getSuperAnnDate()); //by default set to retirement date added by sudhir
			changeDtlsHst.setSuperAnnDate(mstEmployeeEntity2.getSuperAnnDate());
			changeDtlsHst.setPayCommissionCode(mstEmployeeEntity2.getPayCommissionCode());
			changeDtlsHst.setFirstDesignationCode(mstEmployeeEntity2.getFirstDesignationCode());
			changeDtlsHst.setDesignationCode(mstEmployeeEntity2.getDesignationCode());
			changeDtlsHst.setPayscalelevelId(mstEmployeeEntity2.getPayscalelevelId());
			changeDtlsHst.setBegisCatg(mstEmployeeEntity2.getBegisCatg());
			
			if(mstEmployeeEntity2.getPayscalelevelId()!=null)
				changeDtlsHst.setSevenPcLevel(Integer.parseInt(mstEmployeeEntity2.getPayscalelevelId()));
			else
			changeDtlsHst.setSevenPcLevel(0);
			
			
			changeDtlsHst.setSvnthpaybasic(mstEmployeeEntity2.getSvnthpaybasic());
			
			if (mstEmployeeEntity2.getPayCommissionCode() == 2) {
				changeDtlsHst.setBasicPay(
						mstEmployeeEntity2.getBasicPay() == null ? 0 : mstEmployeeEntity2.getBasicPay().doubleValue());
				changeDtlsHst.setGradePay(mstEmployeeEntity2.getGradePay());
			} else {
				changeDtlsHst.setSevenPcBasic(mstEmployeeEntity2.getSevenPcBasic() == null ? 0
						: mstEmployeeEntity2.getSevenPcBasic().doubleValue());
			}

			changeDtlsHst.setPayScaleCode(mstEmployeeEntity2.getPayScaleCode());
			changeDtlsHst.setPayInPayBand(mstEmployeeEntity2.getPayInPayBand());
//			changeDtlsHst.setBasicPay(mstEmployeeEntity2.getBasicPay() == null ? 0 : mstEmployeeEntity2.getBasicPay().doubleValue());
			
			changeDtlsHst.setPostdetailid(mstEmployeeEntity2.getPostdetailid());
			changeDtlsHst.setDepartmentNameEn(mstEmployeeEntity2.getDepartmentNameEn());
			changeDtlsHst.setDtInitialAppointmentParentInst(mstEmployeeEntity2.getDtInitialAppointmentParentInst());
			changeDtlsHst.setInstituteAdd(mstEmployeeEntity2.getInstituteAdd());
			changeDtlsHst.setInstName(mstEmployeeEntity2.getInstName());
			changeDtlsHst.setMobileNo2(mstEmployeeEntity2.getMobileNo2());
			changeDtlsHst.setInstemail(mstEmployeeEntity2.getInstemail());
			changeDtlsHst.setDtJoinCurrentPost(mstEmployeeEntity2.getDtJoinCurrentPost());
			changeDtlsHst.setRemark(mstEmployeeEntity2.getRemark());
			changeDtlsHst.setCityClass(mstEmployeeEntity2.getCityClass());
			changeDtlsHst.setIndiApproveOrderNo(mstEmployeeEntity2.getIndiApproveOrderNo());
			changeDtlsHst.setApprovalByDdoDate(mstEmployeeEntity2.getApprovalByDdoDate());
			changeDtlsHst.setHraBasic(mstEmployeeEntity2.getHraBasic());
			//Department Details End
			
			
			
			//Bank/DCPS/NPS/GPF Details Start
			changeDtlsHst.setBankCode(mstEmployeeEntity2.getBankCode());
			changeDtlsHst.setIfscCode(mstEmployeeEntity2.getIfscCode());
			changeDtlsHst.setBankAcntNo(mstEmployeeEntity2.getBankAcntNo());
			changeDtlsHst.setBankBranchCode(mstEmployeeEntity2.getBankBranchCode());
			changeDtlsHst.setDcpsgpfflag(mstEmployeeEntity2.getDcpsgpfflag());
			changeDtlsHst.setDcpsaccountmaintainby(mstEmployeeEntity2.getDcpsaccountmaintainby());
			changeDtlsHst.setPranNo(mstEmployeeEntity2.getPranNo());
			changeDtlsHst.setAccountmaintainby(mstEmployeeEntity2.getAccountmaintainby());
			changeDtlsHst.setPfseries(mstEmployeeEntity2.getPfseries());
			changeDtlsHst.setPfacno(mstEmployeeEntity2.getPfacno());
			changeDtlsHst.setPfdescription(mstEmployeeEntity2.getPfdescription());
			
			//Bank/DCPS/NPS/GPF Details End
			
			
			//GIS Details Start
			changeDtlsHst.setGisapplicable(mstEmployeeEntity2.getGisapplicable());
			changeDtlsHst.setGisgroup(mstEmployeeEntity2.getGisgroup());
			changeDtlsHst.setMembership_date(mstEmployeeEntity2.getMembership_date());
			changeDtlsHst.setGisRemark(mstEmployeeEntity2.getGisRemark());
			changeDtlsHst.setGiscatagory(mstEmployeeEntity2.getGiscatagory());
			//GIS Details End
			
			
			///History maintainance end//
			
			
			
			
			mstEmployeeEntity2.setUidNo(mstEmployeeEntity.getUidNo());
			mstEmployeeEntity2.setEidNo(mstEmployeeEntity.getEidNo());
			mstEmployeeEntity2.setSalutation(mstEmployeeEntity.getSalutation());
			mstEmployeeEntity2.setEmployeeFullNameEn(mstEmployeeEntity.getEmployeeFullNameEn());
			mstEmployeeEntity2.setEmployeeFNameEn(mstEmployeeEntity.getEmployeeFNameEn().toUpperCase());
			mstEmployeeEntity2.setEmployeeMNameEn(mstEmployeeEntity.getEmployeeMNameEn().toUpperCase());
			mstEmployeeEntity2.setEmployeeLNameEn(mstEmployeeEntity.getEmployeeLNameEn().toUpperCase());
			mstEmployeeEntity2.setEmployeeFullNameMr(mstEmployeeEntity.getEmployeeFullNameMr());
			mstEmployeeEntity2.setEmployeeFNameMr(mstEmployeeEntity.getEmployeeFNameMr());
			mstEmployeeEntity2.setEmployeeLNameMr(mstEmployeeEntity.getEmployeeLNameMr());
			mstEmployeeEntity2.setEmployeeMotherName(mstEmployeeEntity.getEmployeeMotherName());
			mstEmployeeEntity2.setBuckleNo(mstEmployeeEntity.getBuckleNo());
			mstEmployeeEntity2.setGender(mstEmployeeEntity.getGender());
			mstEmployeeEntity2.setReligionCode(mstEmployeeEntity.getReligionCode());
			mstEmployeeEntity2.setMaritalStatus(mstEmployeeEntity.getMaritalStatus());
			mstEmployeeEntity2.setEmployeeMNameMr(mstEmployeeEntity.getEmployeeMNameMr());
			mstEmployeeEntity2.setDob(mstEmployeeEntity.getDob());
			mstEmployeeEntity2.setDoj(mstEmployeeEntity.getDoj());
			mstEmployeeEntity2.setAddress1(mstEmployeeEntity.getAddress1().toUpperCase());
			mstEmployeeEntity2.setAddress2(mstEmployeeEntity.getAddress2().toUpperCase());
//			mstEmployeeEntity2.setAddress3(mstEmployeeEntity.getAddress3().toUpperCase());
//			mstEmployeeEntity2.setLocality(mstEmployeeEntity.getLocality());
			mstEmployeeEntity2.setStateCode(mstEmployeeEntity.getStateCode());
			mstEmployeeEntity2.setDistrictCode(mstEmployeeEntity.getDistrictCode());
//			mstEmployeeEntity2.setVillageName(mstEmployeeEntity.getVillageName().toUpperCase());
			mstEmployeeEntity2.setPinCode(mstEmployeeEntity.getPinCode());
			mstEmployeeEntity2.setPhysicallyHandicapped(mstEmployeeEntity.getPhysicallyHandicapped());
			mstEmployeeEntity2.setMobileNo1(mstEmployeeEntity.getMobileNo1());
			mstEmployeeEntity2.setEmailId(mstEmployeeEntity.getEmailId());
			mstEmployeeEntity2.setPanNo(mstEmployeeEntity.getPanNo());
			
			//Employee Details End
			
			
			//Department Details Start
			mstEmployeeEntity2.setParentAdminDepartmentId(mstEmployeeEntity.getParentAdminDepartmentCode());
			mstEmployeeEntity2.setParentFieldDepartmentId(mstEmployeeEntity.getParentFieldDepartmentCode());
			mstEmployeeEntity2.setSubDeptId(mstEmployeeEntity.getSubDeptId());
			mstEmployeeEntity2.setSubCorporationId(mstEmployeeEntity.getSubCorporationId());
			mstEmployeeEntity2.setAdminDepartmentCode(mstEmployeeEntity.getParentAdminDepartmentCode());
			mstEmployeeEntity2.setFieldDepartmentCode(mstEmployeeEntity.getFieldDepartmentCode());
			mstEmployeeEntity2.setIsChangeParentDepartment(mstEmployeeEntity.getIsChangeParentDepartment());
			mstEmployeeEntity2.setReasonForChngParentFieldDept(mstEmployeeEntity.getReasonForChngParentFieldDept());
			mstEmployeeEntity2.setCadreCode(mstEmployeeEntity.getCadreCode());
			mstEmployeeEntity2.setEmpClass(mstEmployeeEntity.getEmpClass());
			mstEmployeeEntity2.setSuperAnnAge(Integer.valueOf(mstEmployeeEntity.getSuperAnnAge()));
			mstEmployeeEntity2.setEmpServiceEndDate(mstEmployeeEntity.getSuperAnnDate()); //by default set to retirement date added by sudhir
			mstEmployeeEntity2.setSuperAnnDate(mstEmployeeEntity.getSuperAnnDate());
			mstEmployeeEntity2.setPayCommissionCode(mstEmployeeEntity.getPayCommissionCode());
			mstEmployeeEntity2.setFirstDesignationCode(mstEmployeeEntity.getFirstDesignationCode());
			mstEmployeeEntity2.setDesignationCode(mstEmployeeEntity.getDesignationCode());
			mstEmployeeEntity2.setPayscalelevelId(mstEmployeeEntity.getPayscalelevelId());
			if(mstEmployeeEntity.getPayscalelevelId()!=null)
				mstEmployeeEntity2.setSevenPcLevel(Integer.parseInt(mstEmployeeEntity.getPayscalelevelId()));
			else
			mstEmployeeEntity2.setSevenPcLevel(0);
			
			
			mstEmployeeEntity2.setSvnthpaybasic(mstEmployeeEntity.getSvnthpaybasic());
			
			if (mstEmployeeEntity.getPayCommissionCode() == 2) {
				mstEmployeeEntity2.setBasicPay(
						mstEmployeeEntity.getBasicPay() == null ? 0 : mstEmployeeEntity.getBasicPay().doubleValue());
				mstEmployeeEntity2.setGradePay(mstEmployeeEntity.getGradePay());
			} else {
				mstEmployeeEntity2.setSevenPcBasic(mstEmployeeEntity.getSevenPcBasic() == null ? 0
						: mstEmployeeEntity.getSevenPcBasic().doubleValue());
			}

			mstEmployeeEntity2.setPayScaleCode(mstEmployeeEntity.getPayScaleCode());
			mstEmployeeEntity2.setPayInPayBand(mstEmployeeEntity.getPayInPayBand());
//			mstEmployeeEntity2.setBasicPay(mstEmployeeEntity.getBasicPay() == null ? 0 : mstEmployeeEntity.getBasicPay().doubleValue());
			
			mstEmployeeEntity2.setPostdetailid(mstEmployeeEntity.getPostdetailid());
			mstEmployeeEntity2.setDepartmentNameEn(mstEmployeeEntity.getDepartmentNameEn());
			mstEmployeeEntity2.setDtInitialAppointmentParentInst(mstEmployeeEntity.getDtInitialAppointmentParentInst());
			mstEmployeeEntity2.setInstituteAdd(mstEmployeeEntity.getInstituteAdd());
			mstEmployeeEntity2.setInstName(mstEmployeeEntity.getInstName());
			mstEmployeeEntity2.setMobileNo2(mstEmployeeEntity.getMobileNo2());
			mstEmployeeEntity2.setInstemail(mstEmployeeEntity.getInstemail());
			mstEmployeeEntity2.setDtJoinCurrentPost(mstEmployeeEntity.getDtJoinCurrentPost());
			mstEmployeeEntity2.setRemark(mstEmployeeEntity.getRemark());
			mstEmployeeEntity2.setCityClass(mstEmployeeEntity.getCityClass());
			mstEmployeeEntity2.setIndiApproveOrderNo(mstEmployeeEntity.getIndiApproveOrderNo());
			mstEmployeeEntity2.setApprovalByDdoDate(mstEmployeeEntity.getApprovalByDdoDate());
			mstEmployeeEntity2.setHraBasic(mstEmployeeEntity.getHraBasic());
			//Department Details End
			
			
			
			//Bank/DCPS/NPS/GPF Details Start
			mstEmployeeEntity2.setBankCode(mstEmployeeEntity.getBankCode());
			mstEmployeeEntity2.setIfscCode(mstEmployeeEntity.getIfscCode());
			mstEmployeeEntity2.setBankAcntNo(mstEmployeeEntity.getBankAcntNo());
			mstEmployeeEntity2.setBankBranchCode(mstEmployeeEntity.getBankBranchCode());
			mstEmployeeEntity2.setDcpsgpfflag(mstEmployeeEntity.getDcpsgpfflag());
			mstEmployeeEntity2.setDcpsaccountmaintainby(mstEmployeeEntity.getDcpsaccountmaintainby());
			mstEmployeeEntity2.setPranNo(mstEmployeeEntity.getPranNo());
			mstEmployeeEntity2.setAccountmaintainby(mstEmployeeEntity.getAccountmaintainby());
			mstEmployeeEntity2.setPfseries(mstEmployeeEntity.getPfseries());
			mstEmployeeEntity2.setPfacno(mstEmployeeEntity.getPfacno());
			mstEmployeeEntity2.setPfdescription(mstEmployeeEntity.getMstGpfDetailsEntity().getPfdescription());
			
			//Bank/DCPS/NPS/GPF Details End
			
			
			//GIS Details Start
			mstEmployeeEntity2.setGisapplicable(mstEmployeeEntity.getGisapplicable());
			mstEmployeeEntity2.setGisgroup(mstEmployeeEntity.getGisgroup());
			mstEmployeeEntity2.setMembership_date(mstEmployeeEntity.getMembership_date());
			mstEmployeeEntity2.setGisRemark(mstEmployeeEntity.getGisRemark());
			mstEmployeeEntity2.setGiscatagory(mstEmployeeEntity.getGiscatagory());
			//GIS Details End
		
			
			

			}
		
		
	}
		
		
		
		
		mstEmployeeEntity2.setDtInitialAppointmentParentInst(StringHelperUtils.isNullDate(mstEmployeeEntity.getDtInitialAppointmentParentInst()));
		mstEmployeeEntity2.setOrderDate(StringHelperUtils.isNullDate(mstEmployeeEntity.getOrderDate()));
		if(roleid==1)
		{
			mstEmployeeEntity2.setIsActive(5);
		}
		else
		{
			if(mstEmployeeEntity.getGender()=='1')
			{
				mstEmployeeEntity2.setGender('M');
			}
			else if(mstEmployeeEntity.getGender()=='2')
			{
				mstEmployeeEntity2.setGender('F');
			}
			else
			{
				mstEmployeeEntity2.setGender('T');
			}
			mstEmployeeEntity2.setIsActive(1);
		}
		mstEmployeeEntity2.setUpdatedDate(new Date());
		mstEmployeeEntity2.setParentFieldDepartmentCode(BigInteger.valueOf(51));
		int updated=0;
		if(mstEmployeeEntity!=null) {
			empChangeDetailsRepo.updateChangeEmpDtls(mstEmployeeEntity2);
			if(roleid==1)
			empChangeDetailsRepo.updateChangeEmpHstDtls(changeDtlsHst);
			updated=1;
		}
		
		
	
		
		
		
		
		return  updated;
	*/}

	public String[] savePhotoSignature(MultipartFile[] files, BigInteger bigInteger, Integer empid, String existphotpath,
			String existsignpath) {
		// department name/photo/employee_id/photo.jpg
		
		System.out.println("Image Uploading-----"+files.length);
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
					rootPath += bigInteger + File.separator + empid;
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
					rootPath += bigInteger + File.separator + empid;
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



	/*@Override
	public List<MstEmployeeEntity> findEmpLstforApprovChngDtls() {
		// TODO Auto-generated method stub
		return empChangeDetailsRepo.findEmpLstforApprovChngDtls();
	}
*/


	@Override
	public List<Object[]> GetCurrentPostDesigation(Integer postdetailid) {
		List<Object[]> result=empChangeDetailsRepo.GetCurrentPostDesigation(postdetailid);
		return result;
	}
	
	
}
