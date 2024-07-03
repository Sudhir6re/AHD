package com.mahait.gov.in.service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mahait.gov.in.entity.CmnDistrictMst;
import com.mahait.gov.in.entity.CmnTalukaMst;
import com.mahait.gov.in.entity.OrgUserMst;
import com.mahait.gov.in.entity.ZpAdminNameMst;
import com.mahait.gov.in.entity.ZpAdminOfficeMst;
import com.mahait.gov.in.mapper.ZpRltDdoMapMapper;
import com.mahait.gov.in.model.ZpRltDdoMapModel;
import com.mahait.gov.in.repository.CmnDistrictMstRepository;
import com.mahait.gov.in.repository.CmnTalukaMstRepository;
import com.mahait.gov.in.repository.CreateAdminOfficeRepo;
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
		return cmnDistrictMstRepository.findAll();
	}

	@Override
	public List<Object[]> findZpRltDtls(OrgUserMst messages, String districtName, String talukaNametName,
			String adminType) {
		return createAdminOfficeRepo.getAllDDOOfficeDtlsData(districtName, talukaNametName, adminType);
	}

	@Override
	public void saveCreateAdminOffice(ZpRltDdoMapModel zpRltDdoMapModel, OrgUserMst messages) {/*
		logger.info("Entering into saveZpDDODtls of ZpDDOOfficeServiceImpl");
		ResultObject objRes = new ResultObject(ErrorConstants.ERROR);
		ServiceLocator serviceLocator = (ServiceLocator) objectArgs.get("serviceLocator");
		HttpServletRequest request = (HttpServletRequest) objectArgs.get("requestObj");

		//AddNewDDOConfigDAOImpl lObjAddNewDdoConfig = new AddNewDDOConfigDAOImpl(CmnLocationMst.class,serv.getSessionFactory());
		Long gLngPostId = SessionHelper.getPostId(objectArgs);
		Long gLngUserId = SessionHelper.getUserId(objectArgs);
		String strAdminOfc=StringUtility.getParameter("cmbAdminOffice", request).trim();

		String lLngFieldDept=null;//StringUtility.getParameter("cmbAdminOffice", request).trim(); // TODO -- It will Change in future

		String strDistCode=StringUtility.getParameter("cmbDistOffice", request).trim();
		logger.info("...........cmbDistOffice..............."+strDistCode);
		String strDeptCode=StringUtility.getParameter("cmbDept", request).trim();
		String strRepoDDOCode=StringUtility.getParameter("txtRepDDOCode", request).trim();
		String lStrDdoName=StringUtility.getParameter("txtDDOName", request).trim().trim();
		String lstrFinalDDOCode=StringUtility.getParameter("txtFinalDDOCode", request).trim();
		String lstrSpecialDDOCode=StringUtility.getParameter("txtSpecialDDOCode", request).trim();
		String lstrLevel=StringUtility.getParameter("radioFinalLevel", request).trim();
		logger.info("...........lLongLevel..............."+lstrLevel);
		String lStrDdoPersonalName=StringUtility.getParameter("txtDDOName", request).trim();// TODO -- It will Change in future
		logger.info("...........lStrDdoPersonalName..............."+lStrDdoPersonalName);

		Long lLngDesignID =1l; //Long.parseLong(StringUtility.getParameter("1", request).trim());// TODO -- It will Change in future
		Long lLngAdminDept=0l;
		if(StringUtility.getParameter("cmbDept", request).trim()!=null){
		lLngAdminDept = -1l;//Long.parseLong(((StringUtility.getParameter("cmbDept", request).trim()!=null || !StringUtility.getParameter("cmbDept", request).trim().equals(""))?StringUtility.getParameter("cmbDept", request).trim():-1) );// TODO -- It will Change in future
		}
		//String lLngDistrictCode=StringUtility.getParameter("cmbDistrict", request).trim();
		String lLngDistrictCode=StringUtility.getParameter("cmbDistOffice", request).trim();

		String lStrGender=StringUtility.getParameter("radioGender", request).trim().trim();
		String strTOName=StringUtility.getParameter("txtTreasuryName", request).trim();
		String lLngTreasuryCode=StringUtility.getParameter("txtTreasuryCode", request).trim();
		String strSubTOName=StringUtility.getParameter("txtSubTreasuryName", request).trim();
		String lStrDesgnName=StringUtility.getParameter("txtDDODsgn", request).trim();
		String lStrDdoOfficeName=StringUtility.getParameter("txtOfficeName", request).trim();
		lStrDdoName=lStrDdoOfficeName;
		String lStrDdoCode=StringUtility.getParameter("txtDDOCode", request).trim();
		
		logger.info(".............................lStrDdoCode............................."+lStrDdoCode);
		Long lLngLocPin = 1l;//Long.parseLong(StringUtility.getParameter("1", request).trim());// TODO -- Need Change Temporary "1" is added.  

		AddNewDDOConfigDAOImpl lObjAddNewDdoConfig = new AddNewDDOConfigDAOImpl(CmnLocationMst.class,serviceLocator.getSessionFactory());
		ZpDDOOfficeMstDAOImpl objZpDDOOfficeMstDAOImpl=new ZpDDOOfficeMstDAOImpl(ZpRltDdoMap.class,serviceLocator.getSessionFactory());

		logger.info("..............................Entries Start in Below tables............................");
		logger.info(".........01................Inserting insertLocation............................");
		
		logger.info("strRepoDDOCode............................"+strRepoDDOCode);
		
		List ParentDtls1=objZpDDOOfficeMstDAOImpl.RetirveParentdtlfrmReptCode(strRepoDDOCode);
		
		logger.info("ParentDtls1............................"+ParentDtls1);
		
		String tmp[] = ParentDtls1.get(0).toString().split("##");
		String lstrHOD_Code1=tmp[1].toString();
		lLngFieldDept=lstrHOD_Code1;
//String lStrLocCode = lObjAddNewDdoConfig.insertLocation(lStrDdoOfficeName, gLngUserId, gLngPostId, Long.valueOf(lLngFieldDept), String.valueOf(lLngLocPin), objectArgs);
		String lStrLocCode = lObjAddNewDdoConfig.insertLocation(lStrDdoOfficeName, gLngUserId, gLngPostId, Long.valueOf(lLngFieldDept), String.valueOf(lLngLocPin), objectArgs,strDistCode);
		logger.info(".........01................Inserted insertLocation............................");
		logger.info(".........02................Inserting insertUserMst............................");
		Long lLngUserId = lObjAddNewDdoConfig.insertUserMst(lStrDdoCode, gLngUserId, gLngPostId, objectArgs);
		logger.info(".........02................Inserted insertUserMst............................");
		Long lLngPostId = new Long(lLngUserId);
		logger.info("lLngPostId "+lLngPostId);

		logger.info(".........03................Inserting insertEmpMst............................");
		lObjAddNewDdoConfig.insertEmpMst(lLngUserId, lStrDdoPersonalName, gLngUserId, gLngPostId, lStrGender, objectArgs);
		logger.info(".........03................Inserting insertEmpMst............................");

		logger.info(".........04................Inserting insertOrgPostMst............................");
		lObjAddNewDdoConfig.insertOrgPostMst(lLngPostId, lStrLocCode, gLngUserId, gLngPostId, lLngDesignID.toString(), objectArgs);
		logger.info(".........04................Inserting insertOrgPostMst............................");

		logger.info(".........05................Inserting insertPostDtlsRlt............................");
		lObjAddNewDdoConfig.insertPostDtlsRlt(lStrLocCode, lLngPostId, lStrDesgnName, lLngDesignID, gLngUserId, gLngPostId, objectArgs);
		logger.info(".........05................Inserting insertPostDtlsRlt............................");

		logger.info(".........06................Inserting insertPostRoleRlt............................");
		//lObjAddNewDdoConfig.insertPostRoleRlt(lLngPostId, gLngUserId, gLngPostId, objectArgs,"DDO");
		objZpDDOOfficeMstDAOImpl.insertPostRoleRlt(lLngPostId, gLngUserId, gLngPostId, objectArgs,"DDO");
		logger.info(".........06................Inserting insertPostRoleRlt............................");

		logger.info(".........07................Inserting insertUserPostRlt............................");
		lObjAddNewDdoConfig.insertUserPostRlt(lLngPostId, lLngUserId, gLngUserId, gLngPostId, objectArgs);
		logger.info(".........07................Inserting insertUserPostRlt............................");

		logger.info(".........08................Inserting insertOrgDdoMst............................");
		String lstrDdoType="";
		List ofcCode=objZpDDOOfficeMstDAOImpl.retirveDdoType(strAdminOfc);
		lstrDdoType=ofcCode.get(0).toString();//Note : Column Added in ORG_DDO_MST-DDOType

		List ParentDtls=objZpDDOOfficeMstDAOImpl.RetirveParentdtlfrmReptCode(strRepoDDOCode);
		
		
		Object[] p =(Object[])ParentDtls.get(0);
		String lstrDept_Code=p[0].toString();
		String lstrHOD_Code=p[1].toString();
		
		String tmp1[] = ParentDtls.get(0).toString().split("##");
		String lstrDept_Code=tmp[0].toString();
		String lstrHOD_Code=tmp[1].toString();
		
		
		List DeptCode=objZpDDOOfficeMstDAOImpl.RetirveAdminDeptType(strDeptCode);
		String lstrDeptType=null;
		if(DeptCode!=null && DeptCode.size()>0)
			lstrDeptType=DeptCode.get(0).toString();

		String lstrAdminDeptType=null;
		objZpDDOOfficeMstDAOImpl.insertOrgDdoMst(lStrDdoCode, lStrDdoName, lStrDdoPersonalName, lLngPostId, gLngUserId, lStrLocCode, gLngPostId, lLngAdminDept.toString(),lstrDdoType,lstrDept_Code,lstrHOD_Code,lstrDeptType,objectArgs);
		logger.info(".........08................Inserting insertOrgDdoMst............................");

		logger.info(".........09................Inserting insertMstDcpsDdoOffice............................");
		//Edited by samadhan to save uniqe institute number generated by system
		String uniqeInstituteId=generateUniqeInstituteId(lStrDdoCode,lLngDistrictCode.toString(), objectArgs);
		//lObjAddNewDdoConfig.insertMstDcpsDdoOffice(lStrDdoCode, lStrDdoOfficeName, lLngDistrictCode.toString(), Long.parseLong(lStrLocCode), gLngUserId, gLngPostId, objectArgs);
		lObjAddNewDdoConfig.insertMstDcpsDdoOffice(lStrDdoCode, lStrDdoOfficeName, lLngDistrictCode.toString(), Long.parseLong(lStrLocCode), gLngUserId, gLngPostId, objectArgs,uniqeInstituteId);
		logger.info(".........09................Inserting insertMstDcpsDdoOffice............................");

		logger.info(".........10................Inserting insertRltDdoOrg............................");
		lObjAddNewDdoConfig.insertRltDdoOrg(gLngUserId, gLngPostId, lStrDdoCode, lLngTreasuryCode.toString(), objectArgs);
		lObjAddNewDdoConfig.insertWfOrgPost(lLngPostId.toString()); 
		lObjAddNewDdoConfig.insertWfOrgLoc(lStrLocCode);
		lObjAddNewDdoConfig.insertWfOrgUser(lLngUserId);
		logger.info(".........10................Inserting insertRltDdoOrg............................");
		logger.info(".........................Entries Done in above tables............................");





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
		return objRes;
	*/}

}
