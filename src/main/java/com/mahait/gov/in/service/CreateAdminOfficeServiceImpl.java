package com.mahait.gov.in.service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.bouncycastle.asn1.ocsp.ServiceLocator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mahait.gov.in.entity.CmnDistrictMst;
import com.mahait.gov.in.entity.CmnLocationMst;
import com.mahait.gov.in.entity.CmnTalukaMst;
import com.mahait.gov.in.entity.OrgDdoMst;
import com.mahait.gov.in.entity.OrgDesignationMst;
import com.mahait.gov.in.entity.OrgUserMst;
import com.mahait.gov.in.entity.ZpAdminNameMst;
import com.mahait.gov.in.entity.ZpAdminOfficeMst;
import com.mahait.gov.in.entity.ZpRltDdoMap;
import com.mahait.gov.in.mapper.ZpRltDdoMapMapper;
import com.mahait.gov.in.model.ZpRltDdoMapModel;
import com.mahait.gov.in.repository.AddNewDDOConfigRepository;
import com.mahait.gov.in.repository.CmnDistrictMstRepository;
import com.mahait.gov.in.repository.CmnLocationMstRepository;
import com.mahait.gov.in.repository.CmnTalukaMstRepository;
import com.mahait.gov.in.repository.CreateAdminOfficeRepo;
import com.mahait.gov.in.repository.OrgDdoMstRepository;
import com.mahait.gov.in.repository.OrgDesignationMstRepository;
import com.mahait.gov.in.repository.UserInfoRepo;
import com.mahait.gov.in.repository.ZpAdminNameMstRepository;
import com.mahait.gov.in.repository.ZpRltDdoMapRepository;

@Transactional
@Service
public class CreateAdminOfficeServiceImpl implements CreateAdminOfficeService {

	@Autowired
	CmnTalukaMstRepository cmnTalukaMstRepository;

	@Autowired
	CmnDistrictMstRepository cmnDistrictMstRepository;

	@Autowired
	ZpRltDdoMapRepository zpRltDdoMapRepository;

	@Autowired
	ZpAdminNameMstRepository zpAdminNameMstRepository;

	@Autowired
	ZpRltDdoMapMapper zpRltDdoMapMapper;

	@Autowired
	CreateAdminOfficeRepo createAdminOfficeRepo;

	@Autowired
	OrgDdoMstRepository orgDdoMstRepository;

	@Autowired
	CmnLocationMstRepository cmnLocationMstRepository;

	@Autowired
	UserInfoRepo userInfoRepo;
	
	@Autowired
    private PasswordEncoder passwordEncoder;
	

	@Autowired
	OrgDesignationMstRepository orgDesignationMstRepository;
	
	
	@Autowired 
	AddNewDDOConfigRepository objZpDDOOfficeMstDAOImpl;
	
	
	
	

	@Override
	public List<ZpRltDdoMapModel> findAllDdoMappedlist(OrgUserMst messages) {
		// TODO Auto-generated method stub
		return zpRltDdoMapMapper.convertEntityListToModelList(zpRltDdoMapRepository.findAll());
	}

	@Override
	public List<ZpAdminNameMst> fetchAllOfficeList(OrgUserMst messages) {
		List<String> adminCodes = Arrays.asList("06", "07", "19", "20", "31");
		return zpAdminNameMstRepository.findByAdminCodeInOrderByAdminCode(adminCodes);
	}

	@Override
	public List<CmnTalukaMst> findAllTalukaList(OrgUserMst messages) {
		// TODO Auto-generated method stub
		return cmnTalukaMstRepository.findAll();
	}

	@Override
	public List<CmnDistrictMst> findAllDistrictList(OrgUserMst messages) {
		// TODO Auto-generated method stub
		return cmnDistrictMstRepository.findByLangIdAndStateIdOrderByDistrictName(1, 15l);
	}

	@Override
	public List<Object[]> findZpRltDtls(OrgUserMst messages, String districtName, String talukaNametName,
			String adminType) {
		return createAdminOfficeRepo.getAllDDOOfficeDtlsData(districtName, talukaNametName, adminType);
	}

	@Override
	public void saveCreateAdminOffice(ZpRltDdoMapModel zpRltDdoMapModel, OrgUserMst messages) {

		Long gLngPostId = messages.getCreatedByPost().getPostId();
		Long gLngUserId = messages.getUserId();
		String strAdminOfc=zpRltDdoMapModel.getCmbAdminOffice();

		String lLngFieldDept=null;

		String strDistCode=zpRltDdoMapModel.getCmbDistOffice();
		String strDeptCode=zpRltDdoMapModel.getCmbAdminOffice();
		String strRepoDDOCode=zpRltDdoMapModel.getTxtRepDDOCode();
		String lStrDdoName=zpRltDdoMapModel.getTxtDDOName();
		String lstrFinalDDOCode=zpRltDdoMapModel.getTxtFinalDDOCode();
		String lstrSpecialDDOCode=zpRltDdoMapModel.getTxtSpecialDDOCode();
		String lstrLevel=zpRltDdoMapModel.getRadioFinalLevel();
		String lStrDdoPersonalName=zpRltDdoMapModel.getTxtDDOName();

		Long lLngDesignID =1l; //Long.parseLong(StringUtility.getParameter("1", request).trim());// TODO -- It will Change in future
		Long lLngAdminDept=0l;
	
		/*if(StringUtility.getParameter("cmbDept", request).trim()!=null){
		lLngAdminDept = -1l;//Long.parseLong(((StringUtility.getParameter("cmbDept", request).trim()!=null || !StringUtility.getParameter("cmbDept", request).trim().equals(""))?StringUtility.getParameter("cmbDept", request).trim():-1) );// TODO -- It will Change in future
		}*/
		String lLngDistrictCode=zpRltDdoMapModel.getCmbDistOffice();

		String lStrGender=zpRltDdoMapModel.getRadioGender();
		String strTOName=zpRltDdoMapModel.getTxtTreasuryName();
		String lLngTreasuryCode=zpRltDdoMapModel.getTxtTreasuryCode();
		String strSubTOName=zpRltDdoMapModel.getCmbSubTreasury();
		String lStrDesgnName=zpRltDdoMapModel.getTxtDDODsgn();
		String lStrDdoOfficeName=zpRltDdoMapModel.getTxtOfficeName();
		lStrDdoName=lStrDdoOfficeName;
		String lStrDdoCode=zpRltDdoMapModel.getTxtDDOCode();
		
		Long lLngLocPin = 1l;//Long.parseLong(StringUtility.getParameter("1", request).trim());// TODO -- Need Change Temporary "1" is added.  

		

		List<OrgDdoMst> ParentDtls1=orgDdoMstRepository.findAllByDdoCode(strRepoDDOCode);
		
		
		String lstrHOD_Code1=ParentDtls1.get(0).getDeptLocCode();
		lLngFieldDept=ParentDtls1.get(0).getHodLocCode();
		
		
		String lStrLocCode = objZpDDOOfficeMstDAOImpl.insertLocation(lStrDdoOfficeName, gLngUserId, gLngPostId, Long.valueOf(lLngFieldDept), String.valueOf(lLngLocPin), messages,strDistCode);
		Long lLngUserId = objZpDDOOfficeMstDAOImpl.insertUserMst(lStrDdoCode, gLngUserId, gLngPostId, messages);
		Long lLngPostId = new Long(lLngUserId);

		objZpDDOOfficeMstDAOImpl.insertEmpMst(lLngUserId, lStrDdoPersonalName, gLngUserId, gLngPostId, lStrGender, messages);

		objZpDDOOfficeMstDAOImpl.insertOrgPostMst(lLngPostId, lStrLocCode, gLngUserId, gLngPostId, lLngDesignID.toString(), messages);

		objZpDDOOfficeMstDAOImpl.insertPostDtlsRlt(lStrLocCode, lLngPostId, lStrDesgnName, lLngDesignID, gLngUserId, gLngPostId, messages);

		//lObjAddNewDdoConfig.insertPostRoleRlt(lLngPostId, gLngUserId, gLngPostId, objectArgs,"DDO");
		objZpDDOOfficeMstDAOImpl.insertPostRoleRlt(lLngPostId, gLngUserId, gLngPostId, objectArgs,"DDO");

		lObjAddNewDdoConfig.insertUserPostRlt(lLngPostId, lLngUserId, gLngUserId, gLngPostId, objectArgs);

		String lstrDdoType="";
		List ofcCode=objZpDDOOfficeMstDAOImpl.retirveDdoType(strAdminOfc);
		lstrDdoType=ofcCode.get(0).toString();//Note : Column Added in ORG_DDO_MST-DDOType

		List ParentDtls=objZpDDOOfficeMstDAOImpl.RetirveParentdtlfrmReptCode(strRepoDDOCode);
		
		
		String tmp1[] = ParentDtls.get(0).toString().split("##");
		String lstrDept_Code=tmp[0].toString();
		String lstrHOD_Code=tmp[1].toString();
		
		
		List DeptCode=objZpDDOOfficeMstDAOImpl.RetirveAdminDeptType(strDeptCode);
		String lstrDeptType=null;
		if(DeptCode!=null && DeptCode.size()>0)
			lstrDeptType=DeptCode.get(0).toString();

		String lstrAdminDeptType=null;
		objZpDDOOfficeMstDAOImpl.insertOrgDdoMst(lStrDdoCode, lStrDdoName, lStrDdoPersonalName, lLngPostId, gLngUserId, lStrLocCode, gLngPostId, lLngAdminDept.toString(),lstrDdoType,lstrDept_Code,lstrHOD_Code,lstrDeptType,objectArgs);

		String uniqeInstituteId=generateUniqeInstituteId(lStrDdoCode,lLngDistrictCode.toString(), objectArgs);
		objZpDDOOfficeMstDAOImpl.insertMstDcpsDdoOffice(lStrDdoCode, lStrDdoOfficeName, lLngDistrictCode.toString(), Long.parseLong(lStrLocCode), gLngUserId, gLngPostId, objectArgs,uniqeInstituteId);
/*
		lObjAddNewDdoConfig.insertRltDdoOrg(gLngUserId, gLngPostId, lStrDdoCode, lLngTreasuryCode.toString(), objectArgs);
		lObjAddNewDdoConfig.insertWfOrgPost(lLngPostId.toString()); 
		lObjAddNewDdoConfig.insertWfOrgLoc(lStrLocCode);
		lObjAddNewDdoConfig.insertWfOrgUser(lLngUserId);
		logger.info(".........10................Inserting insertRltDdoOrg............................");
		logger.info(".........................Entries Done in above tables............................");

*/

/*

		// Entries for DDO Assistant as user
		logger.info(".........................Entries for Asst Start............................");
		Long lLngUserIdAsst = lLngUserId + 1;
		String lStrDDOAsstUserName = lStrDdoCode.trim() + "_AST";
		lObjAddNewDdoConfig.insertUserMstAsst(lLngUserIdAsst, lStrDDOAsstUserName, gLngUserId, gLngPostId, objectArgs);

		Long lLngPostIdAsst = lLngPostId + 1;
		String lStrDdoPersonalNameAsst =  lStrDdoPersonalName + "_AST";

		String lStrDesgnNameAsst = lStrDesgnName + "_AST";
		logger.info(".........................Entries for Asst Start1............................");
		lObjAddNewDdoConfig.insertEmpMst(lLngUserIdAsst, lStrDdoPersonalNameAsst, gLngUserId, gLngPostId, lStrGender, objectArgs);
		logger.info(".........................Entries for Asst Start2............................");
		lObjAddNewDdoConfig.insertOrgPostMst(lLngPostIdAsst, lStrLocCode, gLngUserId, gLngPostId, lLngDesignID.toString(), objectArgs);
		logger.info(".........................Entries for Asst Start3............................");
		lObjAddNewDdoConfig.insertPostDtlsRlt(lStrLocCode, lLngPostIdAsst, lStrDesgnNameAsst, lLngDesignID, gLngUserId, gLngPostId, objectArgs);
		logger.info(".........................Entries for Asst Start4............................");
		objZpDDOOfficeMstDAOImpl.insertPostRoleRlt(lLngPostIdAsst, gLngUserId, gLngPostId, objectArgs,"ASST");
		logger.info(".........................Entries for Asst Start5............................");
		lObjAddNewDdoConfig.insertUserPostRlt(lLngPostIdAsst, lLngUserIdAsst, gLngUserId, gLngPostId, objectArgs);
		logger.info(".........................Entries for Asst Start6............................");
		lObjAddNewDdoConfig.insertWfOrgPost(lLngPostIdAsst.toString());
		logger.info(".........................Entries for Asst Start7............................");
		lObjAddNewDdoConfig.insertWfOrgUser(lLngUserIdAsst);
		logger.info(".........................Entries for Asst Start8............................");
		RltDdoAsst lObjRltDdoAsst = new RltDdoAsst();
		lObjRltDdoAsst.setAsstPostId(lLngPostIdAsst);
		lObjRltDdoAsst.setDdoPostId(lLngPostId);
		logger.info(".........................Entries for Asst Start9............................");
		//Long lLongRltDDOAsstId = IFMSCommonServiceImpl.getNextSeqNum("rlt_dcps_ddo_asst",objectArgs);
		Long lLongRltDDOAsstId = lObjAddNewDdoConfig.getNextSeqNoLocForRltDcpsDdoAsst();
		lObjRltDdoAsst.setRltDdoAsstId(lLongRltDDOAsstId);
		logger.info(".........................Entries for Asst Start10............................");
		lObjAddNewDdoConfig.create(lObjRltDdoAsst);
		logger.info(".........................Entries for Asst End............................");
		// Entries for DDO Assistant as a user overs
*/
/*

		Long ZP_DDO_POST_ID=lLngPostId;
		List RepoDDO=objZpDDOOfficeMstDAOImpl.retirveRepoDDOPostId((strRepoDDOCode));
		List FinalDDO=null;
		Long FINAL_DDO_POST_ID=null;
		List SpecDDO=null;
		Long SPECIAL_DDO_POST_ID=null;
		Long REPT_DDO_POST_ID=Long.valueOf(RepoDDO.get(0).toString());
		if(lstrFinalDDOCode!=null && !lstrFinalDDOCode.equals("") )
		{
			FinalDDO=objZpDDOOfficeMstDAOImpl.retirveFinalDDOPostId(Long.valueOf(lstrFinalDDOCode));
			FINAL_DDO_POST_ID=Long.valueOf(FinalDDO.get(0).toString());
		}


		if(lstrSpecialDDOCode!=null && !lstrSpecialDDOCode.equals("") )
		{
			logger.info("lstrSpecialDDOCode-----sunitha"+lstrSpecialDDOCode);
			SpecDDO=objZpDDOOfficeMstDAOImpl.retirveFinalDDOPostId(Long.valueOf(lstrSpecialDDOCode));
			logger.info("SpecDDO-----sunitha"+SpecDDO);
			SPECIAL_DDO_POST_ID=Long.valueOf(SpecDDO.get(0).toString());
		}

		if(lstrLevel.equalsIgnoreCase("radioFinalLevel2")){
			lstrLevel="2";
			Long lstrReporole=700019L; 
			List checkRole=objZpDDOOfficeMstDAOImpl.checkRepopostrole(REPT_DDO_POST_ID,lstrReporole);
			if(checkRole.size()==0){
				objZpDDOOfficeMstDAOImpl.insertPostRoleRlt(REPT_DDO_POST_ID, gLngUserId, gLngPostId, objectArgs,"ReportingDDO");
			}
		}
		if(lstrLevel.equalsIgnoreCase("radioFinalLevel3")){
			lstrLevel="3";
			Long lstrReporole=700019L; 
			List checkRepoRole=objZpDDOOfficeMstDAOImpl.checkRepopostrole(REPT_DDO_POST_ID,lstrReporole);
			if(checkRepoRole.size()==0){
				objZpDDOOfficeMstDAOImpl.insertPostRoleRlt(REPT_DDO_POST_ID, gLngUserId, gLngPostId, objectArgs,"ReportingDDO");
			}
			Long lstrFinalrole=700020L; 
			List checkFinalRole=objZpDDOOfficeMstDAOImpl.checkRepopostrole(FINAL_DDO_POST_ID,lstrFinalrole);
			if(checkFinalRole.size()==0){
				objZpDDOOfficeMstDAOImpl.insertPostRoleRlt(FINAL_DDO_POST_ID, gLngUserId, gLngPostId, objectArgs,"FinalDDO");
			}
		}
		if(lstrLevel.equalsIgnoreCase("radioFinalLevel4")){
			lstrLevel="4";
			Long lstrReporole=700019L; 
			List checkRepoRole=objZpDDOOfficeMstDAOImpl.checkRepopostrole(REPT_DDO_POST_ID,lstrReporole);
			if(checkRepoRole.size()==0){
				objZpDDOOfficeMstDAOImpl.insertPostRoleRlt(REPT_DDO_POST_ID, gLngUserId, gLngPostId, objectArgs,"ReportingDDO");
			}
			Long lstrFinalrole=700020L; 
			List checkFinalRole=objZpDDOOfficeMstDAOImpl.checkRepopostrole(FINAL_DDO_POST_ID,lstrFinalrole);
			if(checkFinalRole.size()==0){
				objZpDDOOfficeMstDAOImpl.insertPostRoleRlt(FINAL_DDO_POST_ID, gLngUserId, gLngPostId, objectArgs,"FinalDDO");
			}
			Long lstrSpecialrole=700021L; 
			List checkSpecialRole=objZpDDOOfficeMstDAOImpl.checkRepopostrole(SPECIAL_DDO_POST_ID,lstrSpecialrole);
			if(checkSpecialRole.size()==0){
				objZpDDOOfficeMstDAOImpl.insertPostRoleRlt(SPECIAL_DDO_POST_ID, gLngUserId, gLngPostId, objectArgs,"SpecialDDO");
			}
		}
		logger.info(">>>>>>>>>>>>>>>>REPT_DDO_POST_ID>>>>>>>>>>>>>>>>>>>>>"+REPT_DDO_POST_ID);
		logger.info(">>>>>>>>>>>>>>>>FINAL_DDO_POST_ID>>>>>>>>>>>>>>>>>>>>>"+FINAL_DDO_POST_ID);
		logger.info(">>>>>>>>>>>>>>>>SPECIAL_DDO_POST_ID>>>>>>>>>>>>>>>>>>>>>"+SPECIAL_DDO_POST_ID);
		objZpDDOOfficeMstDAOImpl.insertRltZpDdoMap(ZP_DDO_POST_ID, REPT_DDO_POST_ID, FINAL_DDO_POST_ID, SPECIAL_DDO_POST_ID, lStrDdoCode, strRepoDDOCode, lstrFinalDDOCode,lstrSpecialDDOCode, lstrLevel,gLngUserId, gLngPostId, objectArgs);
		logger.info("######################................sunitha................Inserted into insertRltZpDdoMap");





		//  Workflow Tables TODO -- Need to Check By Vihan 
		logger.info("...................inserting Worflow Tables...............");
		objZpDDOOfficeMstDAOImpl.insertWorkFlow(lLngPostIdAsst,lLngPostId,REPT_DDO_POST_ID, FINAL_DDO_POST_ID,SPECIAL_DDO_POST_ID, gLngUserId, lStrLocCode, objectArgs,lstrLevel);
		// insertWorkFlow(Long lLngPostIdAsst,Long lLongDDOPostId,Long lLongTOAsstPostId,Long lLongTOPostId,Long lLongSpecialPostId,Long lLongCreatedByUserId,String lStrLocCode,Map inputMap,String lstrLevel) throws Exception {
		//lObjAddNewDdoConfig.insertWfHierarchyTableSeqMst(lStrLocCode, objectArgs);
		//lObjAddNewDdoConfig.insertWfHierachyPostMpgChanges(REPT_DDO_POST_ID, lLngPostIdAsst, REPT_DDO_POST_ID, lStrLocCode, objectArgs);
		logger.info("...................inserting Worflow Tables...............");
		//  Workflow Tables 

		logger.info("....................Inserting in CMN TABLE MST...................");
		ReportingDDODaoImpl rpt=new ReportingDDODaoImpl(null,serviceLocator.getSessionFactory()); 
		Long seqId= rpt.getNextSeqNum();
		rpt.getSeqTable(lStrLocCode,objectArgs,seqId);
		rpt.updateUserMstSeq();
		logger.info("....................Inserting in CMN TABLE MST...................");


		ZpDDOOfficeMstDAOImpl  zpDDOOfficeMstDAO = new ZpDDOOfficeMstDAOImpl(ZpAdminOfficeMst.class,serviceLocator.getSessionFactory());
		//commented by vaibhav tyagi
		//List<ZpRltDdoMap> zpDDOOfficelst = zpDDOOfficeMstDAO.getAllDDOOfficeDtlsData();
		
		//added by vaibhav tyagi:start
		String districtSelected =null;
		String talukaSelected=null;
		String adminTypeSelected=null;
		List<ZpRltDdoMap> zpDDOOfficelst = zpDDOOfficeMstDAO.getAllDDOOfficeDtlsData(districtSelected,talukaSelected,adminTypeSelected);
		//added by vaibhav tyagi:end
		logger.info("zpdistrictOfficelst::"+zpDDOOfficelst.size());
		
		List districtList= zpDDOOfficeMstDAO.getAllDistrict();
		List adminTypeList= zpDDOOfficeMstDAO.getAllAdminType();
		List taluka = zpDDOOfficeMstDAO.getAllTalukaByDistrict(strDistCode);
		objectArgs.put("zpDDOOfficelst",zpDDOOfficelst);
		
		objectArgs.put("districtList",districtList);
		objectArgs.put("adminTypeList",adminTypeList);
		objectArgs.put("zpDDOOfficelst",zpDDOOfficelst);
		objectArgs.put("talukaList",taluka);
		
		
		objectArgs.put("lStrDdoCode",lStrDdoCode);
		objectArgs.put("uniqeInstituteId",uniqeInstituteId);
		districtList=null;
		adminTypeList=null;
		zpDDOOfficelst=null;
		objRes.setResultCode(ErrorConstants.SUCCESS);
		objRes.setResultValue(objectArgs);
		objRes.setViewName("zpDDOOfficeView");
		return objRes;*/
	}

	@Override
	public List<CmnTalukaMst> getAllTalukaByDistrictId(Long districtId) {
		return cmnTalukaMstRepository.findByDistrictIdOrderByTalukaName(districtId);
	}

	@Override
	public List<Object[]> retriveDisctOfcList(OrgUserMst messages, String ofcId) {
		// TODO Auto-generated method stub
		return createAdminOfficeRepo.retriveDisctOfcList(messages, ofcId);
	}

	@Override
	public List<Object[]> fetchDdoDetails(OrgUserMst messages, Long ddoCode) {
		// TODO Auto-generated method stub
		return createAdminOfficeRepo.findPostLocationByDdoCode(ddoCode);
	}

	public String generateDDOCode(String AdminOfc, String SubTOCode) {

	//	List getOfcCode = createAdminOfficeRepo.getAdminOfcCode(AdminOfc);
		//String AOfcCode = getOfcCode.get(0).toString();
		String CreatedDDOCode = AdminOfc;
		CreatedDDOCode += SubTOCode;
		List getCountCode = createAdminOfficeRepo.getCountofDDOCode(CreatedDDOCode);
		String FinalpreFixed = "";
		String suffix = "";
		String midfix = "";
		if (getCountCode.get(0) != null) {
			Long temp = Long.parseLong(getCountCode.get(0).toString()) + 1;
			suffix = temp.toString();
		}
		if (suffix.length() == 1)
			midfix = "0000";
		else if (suffix.length() == 2)
			midfix = "000";
		else if (suffix.length() == 3)
			midfix = "00";
		else if (suffix.length() == 4)
			midfix = "0";

		FinalpreFixed = CreatedDDOCode + midfix + suffix;
		return FinalpreFixed;

	}

	@Override
	public Map<String, Object> findTrasuryDetails(String DDOCode) {

		Long TDDOCode = 0l;
		if (!DDOCode.equalsIgnoreCase("")) {
			TDDOCode = Long.valueOf(DDOCode);
		}
		List RepoDDO = orgDdoMstRepository.findByDdoCodeLike(DDOCode);
		List TODetail = createAdminOfficeRepo.getTreasuryName(TDDOCode);
		
		if(TODetail.size()>0 && TODetail!=null) {
			Object[] o = (Object[]) TODetail.get(0);
			String TOName = o[1] != null ? o[1].toString() : "";
			String TOCode = o[0] != null ? o[0].toString() : "";

			List lstSubTO = createAdminOfficeRepo.getSubTreasury(Long.valueOf(TOCode));
			
			Map<String, Object> response = new HashMap<>();
			
			response.put("trasuryDetails", TODetail);
			response.put("subTreasuryList", lstSubTO);
			return response;
		}else {
			Map<String, Object> response = new HashMap<>();
			return response;
		}

	}
	
	@Override
	public int getUniqeInstituteIdCount(String DDOCode) {
		return createAdminOfficeRepo.getUniqeInstituteIdCount(DDOCode);
	}

	@Override
	public List<OrgDesignationMst> findDesignation(String desgn) {
		// TODO Auto-generated method stub
		return orgDesignationMstRepository.findByDsgnNameIgnoreCaseContaining(desgn);
	}
	
	
	

}
