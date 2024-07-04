package com.mahait.gov.in.repository;

import java.util.List;

import org.hibernate.Query;

public class AddNewDDOConfigRepository {

	public String insertLocation(String lStrLocationName, Long lLngUserIdCrtd, Long lLngPostIdCrtd, Long lLngFieldDeptId, String lStrLocPin,  Map inputMap,String strDistCode) throws Exception
	{
		logger.info("insertLocation 1");
		Long lLngLocId = null;
		
		try{
			CmnLocationMstDaoImpl lObjCmnLocationMstDao = new CmnLocationMstDaoImpl(CmnLocationMst.class,this.getSessionFactory());
			CmnLocationMst lObjCmnLocationMst = new CmnLocationMst();
			
			CmnLanguageMstDao lObjCmnLanguageDao = new CmnLanguageMstDaoImpl(CmnLanguageMst.class,this.getSessionFactory());
			CmnLanguageMst lObjCmnLanguageMst = lObjCmnLanguageDao.read(1l);
			
			CmnLookupMstDAO lObjCmnLookUpMstDao = new CmnLookupMstDAOImpl(CmnLookupMst.class,this.getSessionFactory());
			CmnLookupMst lObjCmnLookupMst = lObjCmnLookUpMstDao.read(1l);
			logger.info("insertLocation 2");
			//commented by vaibhav tyagi
			//lLngLocId = IFMSCommonServiceImpl.getNextSeqNum("cmn_location_mst", inputMap);
			
			//added by vaibhav tyagi: start
			lLngLocId = getNextSeqNoLoc();
			
			
			logger.info("Long.parseLong(gObjRsrcBndle.getString(LOC.MAHARASHTRA))****"+Long.parseLong(gObjRsrcBndle.getString("LOC.MAHARASHTRA")));
			lObjCmnLocationMst.setLocId(lLngLocId);
			logger.info("lLngLocId****"+lLngLocId);
			
			lObjCmnLocationMst.setLocName(lStrLocationName);
			logger.info("lStrLocationName****"+lStrLocationName);
			
			if(lStrLocationName.length() >= 15){
				lObjCmnLocationMst.setLocShortName(lStrLocationName.substring(0,15));
			}/*else if(lStrLocationName!=null&&lStrLocationName!=""){
				lObjCmnLocationMst.setLocShortName(lStrLocationName);
			}*/
			else{
				lObjCmnLocationMst.setLocShortName(".");
			}
			lObjCmnLocationMst.setCmnLanguageMst(lObjCmnLanguageMst);
			lObjCmnLocationMst.setDepartmentId(100007l);
			lObjCmnLocationMst.setParentLocId(lLngFieldDeptId);
			lObjCmnLocationMst.setLocPin("1");
			lObjCmnLocationMst.setCmnLookupMst(lObjCmnLookupMst);
			lObjCmnLocationMst.setStartDate(DBUtility.getCurrentDateFromDB());
			lObjCmnLocationMst.setActivateFlag(1l);
			lObjCmnLocationMst.setCreatedBy(lLngUserIdCrtd);
			lObjCmnLocationMst.setCreatedByPost(lLngPostIdCrtd);
			lObjCmnLocationMst.setCreatedDate(DBUtility.getCurrentDateFromDB());
			lObjCmnLocationMst.setLocationCode(lLngLocId.toString());
			//code added by vivek on 04/03/2013
			Long distCode=(strDistCode!=null && Long.parseLong(strDistCode)>0)?Long.parseLong(strDistCode):-1;
			lObjCmnLocationMst.setLocDistrictId(distCode);
			logger.info("distCode****"+distCode);
			//lObjCmnLocationMst.setLocStateId(Long.parseLong(gObjRsrcBndle.getString("LOC.MAHARASHTRA")));
			lObjCmnLocationMst.setLocStateId(15L);
			//code ends
			ghibSession.save(lObjCmnLocationMst);
			ghibSession.flush();
			
		}catch(Exception e){
			  logger.error(" Error is : " + e, e);
			  throw e;
		}
		return lLngLocId.toString();
	}
	
	public Long insertUserMst(String lStrDdoCode, Long lLngUserIdCrtd, Long lLngPostIdCrtd, Map inputMap) throws Exception
	{
		String ddoc= lStrDdoCode;
		Long lLngUserId = null;
		try{
			logger.info("inside isert userr mst is***********"+lStrDdoCode);
			CmnLookupMstDAO lObjCmnLookUpMstDao = new CmnLookupMstDAOImpl(CmnLookupMst.class,this.getSessionFactory());
			CmnLookupMst lObjCmnLookupMst = lObjCmnLookUpMstDao.read(1l);
			
			OrgUserMstDao lObjOrgUserMstDao = new OrgUserMstDaoImpl(OrgUserMst.class,this.getSessionFactory());
			OrgUserMst lObjOrgUserMstCrtdUsr = lObjOrgUserMstDao.read(lLngUserIdCrtd);
			logger.info("inside isert userr mst is*******post is is ****"+lLngPostIdCrtd);
			logger.info("inside isert userr mst is*******lLngUserIdCrtd is is ****"+lLngUserIdCrtd);
			OrgPostMstDaoImpl orgPostId = new OrgPostMstDaoImpl(OrgPostMst.class,this.getSessionFactory());
			OrgPostMst postId = orgPostId.read(lLngPostIdCrtd);
			
			OrgUserMst lObjUserMst = new OrgUserMst();
			lLngUserId = getNextSeqNoLocForUserMst();
			
			logger.info("lLngUserId is***********"+lLngUserId);
			lObjUserMst.setUserId(lLngUserId);
			
			lObjUserMst.setUserName(ddoc);
			logger.info("ddoc is***********"+ddoc);
			
			lObjUserMst.setPassword("0b76f0f411f6944f9d192da0fcbfb292");
			
			lObjUserMst.setCmnLookupMst(lObjCmnLookupMst);
			logger.info("lObjCmnLookupMst is***********"+lObjCmnLookupMst);
			
			lObjUserMst.setStartDate(DBUtility.getCurrentDateFromDB());
			lObjUserMst.setActivateFlag(0l);
			lObjUserMst.setCreatedDate(DBUtility.getCurrentDateFromDB());
			
			lObjUserMst.setOrgUserMstByCreatedBy(lObjOrgUserMstCrtdUsr);
			logger.info("lObjOrgUserMstCrtdUsr is***********"+lObjOrgUserMstCrtdUsr);
			
			lObjUserMst.setOrgPostMstByCreatedByPost(postId);
			logger.info("postId is***********"+postId);
			
			lObjUserMst.setSecretQueCode("Secret_Other");
			lObjUserMst.setSecretQueOther("Secret_Other");// TODO -- Needs to Change
			lObjUserMst.setSecretAnswer("ifms");
			ghibSession.save(lObjUserMst);
			ghibSession.flush();
		}catch(Exception e){
			  logger.error(" Error is : " + e, e);
			  throw e;
		}
		
		return lLngUserId;
	}
	
	public void insertEmpMst(Long lLngUserId, String lStrFname, Long lLngUserIdCrtd, Long lLngPostIdCrtd, String lStrGendr, Map inputMap) throws Exception
	{
		Long lLngEmpId = null;
		try{
			SimpleDateFormat lObjDateFormate = new SimpleDateFormat("dd/MM/yyyy");			
			Date lObjEmpDob = new Date("01/01/9999");
			
			OrgGradeMstDao lObjOrgGradeMstDao = new OrgGradeMstDaoImpl(OrgGradeMst.class,this.getSessionFactory());
			OrgGradeMst lObjOrgGradeMst = lObjOrgGradeMstDao.read(100064l);
			
			CmnLanguageMstDao lObjCmnLanguageDao = new CmnLanguageMstDaoImpl(CmnLanguageMst.class,this.getSessionFactory());
			CmnLanguageMst lObjCmnLanguageMst = lObjCmnLanguageDao.read(1l);
			
			OrgUserMstDao lObjOrgUserMstDao = new OrgUserMstDaoImpl(OrgUserMst.class,this.getSessionFactory());
			OrgUserMst lObjOrgUserMst = lObjOrgUserMstDao.read(lLngUserId);			
			OrgUserMst lObjOrgUserMstCrtd = lObjOrgUserMstDao.read(lLngUserIdCrtd);
			
			OrgPostMstDaoImpl orgPostId = new OrgPostMstDaoImpl(OrgPostMst.class,this.getSessionFactory());
			OrgPostMst postId = orgPostId.read(lLngPostIdCrtd);
			
			OrgEmpMst lObjEmpMst = new OrgEmpMst();
			//lLngEmpId = IFMSCommonServiceImpl.getNextSeqNum("org_emp_mst", inputMap);
			lLngEmpId = getNextSeqNoLocForEmpMst();
			lObjEmpMst.setEmpId(lLngEmpId);
			logger.info("lLngEmpId is***********"+lLngEmpId);
			
			lObjEmpMst.setEmpFname(lStrFname);
			logger.info("lStrFname is***********"+lStrFname);
			
			lObjEmpMst.setEmpLname(" ");
			lObjEmpMst.setEmpMname(" ");
			
			lObjEmpMst.setEmpDob(lObjEmpDob);
			logger.info("lObjEmpDob is***********"+lObjEmpDob);
			
			lObjEmpMst.setEmpDoj(lObjEmpDob);
			logger.info("lObjEmpDob is***********"+lObjEmpDob);
			lObjEmpMst.setEmpSrvcFlag(1l);
			lObjEmpMst.setOrgGradeMst(lObjOrgGradeMst);
			logger.info("lObjOrgGradeMst is***********"+lObjOrgGradeMst);
			
			lObjEmpMst.setCmnLanguageMst(lObjCmnLanguageMst);	
			logger.info("lObjCmnLanguageMst is***********"+lObjCmnLanguageMst);
			
			lObjEmpMst.setOrgUserMst(lObjOrgUserMst);
			logger.info("lObjOrgUserMst is***********"+lObjOrgUserMst);
			
			lObjEmpMst.setStartDate(DBUtility.getCurrentDateFromDB());
			lObjEmpMst.setActivateFlag(1l);
			lObjEmpMst.setOrgUserMstByCreatedBy(lObjOrgUserMstCrtd);
			logger.info("lObjOrgUserMstCrtd is***********"+lObjOrgUserMstCrtd);
			lObjEmpMst.setOrgPostMstByCreatedByPost(postId);
			lObjEmpMst.setCreatedDate(DBUtility.getCurrentDateFromDB());
			if(lStrGendr.equals("M")){
				lObjEmpMst.setEmpPrefix("Mr");
			}else if(lStrGendr.equals("F")){
				lObjEmpMst.setEmpPrefix("Ms");
			}
			
			ghibSession.save(lObjEmpMst);
			ghibSession.flush();
		}catch(Exception e){
			  logger.error(" Error is : " + e, e);
			  throw e;
		}
	}
	
	public void insertOrgPostMst(Long lLngPostId, String lStrLocationCode, Long lLngUserIdCrtd, Long lLngPostIdCrtd, String lStrDsgnCode, Map inputMap)throws Exception
	{			
		try{
			CmnLookupMstDAO lObjCmnLookUpMstDao = new CmnLookupMstDAOImpl(CmnLookupMst.class,this.getSessionFactory());
			CmnLookupMst lObjCmnLookupMst = lObjCmnLookUpMstDao.read(13l);
			
			OrgUserMstDao lObjOrgUserMstDao = new OrgUserMstDaoImpl(OrgUserMst.class,this.getSessionFactory());
			OrgUserMst lObjOrgUserMstCrtdUsr = lObjOrgUserMstDao.read(lLngUserIdCrtd);
			
			OrgPostMstDaoImpl orgPostId = new OrgPostMstDaoImpl(OrgPostMst.class,this.getSessionFactory());
			OrgPostMst postId = orgPostId.read(lLngPostIdCrtd);
			
			OrgPostMst lObjOrgPostMst = new OrgPostMst();
			//lLngPostId = IFMSCommonServiceImpl.getNextSeqNum("org_post_mst", inputMap);
			lObjOrgPostMst.setPostId(lLngPostId);
			lObjOrgPostMst.setParentPostId(-1l);
			lObjOrgPostMst.setPostLevelId(1l);
			lObjOrgPostMst.setCmnLookupMst(lObjCmnLookupMst);
			lObjOrgPostMst.setActivateFlag(1l);
			lObjOrgPostMst.setOrgUserMstByCreatedBy(lObjOrgUserMstCrtdUsr);
			lObjOrgPostMst.setOrgPostMstByCreatedByPost(postId);
			lObjOrgPostMst.setCreatedDate(DBUtility.getCurrentDateFromDB());
			lObjOrgPostMst.setLocationCode(lStrLocationCode);
			lObjOrgPostMst.setDsgnCode(lStrDsgnCode);
			ghibSession.save(lObjOrgPostMst);
			ghibSession.flush();
		}catch(Exception e){
			  logger.error(" Error is : " + e, e);
			  throw e;
		}		
	}
	
	public void insertPostDtlsRlt(String lstrLocCode, Long lLngPostId, String lStrDesignName, Long lLngDsgnId, Long lLngUserIdCrtd, Long lLngPostIdCrtd, Map inputMap)throws Exception
	{
		Long lLngPostDtlsId = null;
		try{
			OrgPostMstDaoImpl orgPostId = new OrgPostMstDaoImpl(OrgPostMst.class,this.getSessionFactory());
			OrgPostMst postId = orgPostId.read(lLngPostId);
			OrgPostMst postIdCrtd = orgPostId.read(lLngPostIdCrtd);
			
			CmnLocationMstDaoImpl lObjCmnLocationMstDao = new CmnLocationMstDaoImpl(CmnLocationMst.class,this.getSessionFactory());
			CmnLocationMst lObjCmnLocationMst = lObjCmnLocationMstDao.read(Long.parseLong(lstrLocCode));
			
			OrgDesignationMstDao lObjDesgnMstDao = new OrgDesignationMstDaoImpl(OrgDesignationMst.class,this.getSessionFactory());
			OrgDesignationMst lObjOrgDesigmMst = lObjDesgnMstDao.read(lLngDsgnId);
			
			CmnLanguageMstDao lObjCmnLanguageDao = new CmnLanguageMstDaoImpl(CmnLanguageMst.class,this.getSessionFactory());
			CmnLanguageMst lObjCmnLanguageMst = lObjCmnLanguageDao.read(1l);
			
			OrgUserMstDao lObjOrgUserMstDao = new OrgUserMstDaoImpl(OrgUserMst.class,this.getSessionFactory());
			OrgUserMst lObjOrgUserMstCrtd = lObjOrgUserMstDao.read(lLngUserIdCrtd);			
			
			OrgPostDetailsRlt lObjPostDtldRlt = new OrgPostDetailsRlt();
			//lLngPostDtlsId = IFMSCommonServiceImpl.getNextSeqNum("org_post_details_rlt", inputMap);
			lLngPostDtlsId = getNextSeqNoLocForPostDtlsRlt();
			lObjPostDtldRlt.setPostDetailId(lLngPostDtlsId);
			lObjPostDtldRlt.setOrgPostMst(postId);
			lObjPostDtldRlt.setPostName(lStrDesignName);
			lObjPostDtldRlt.setPostShortName(lStrDesignName);
			lObjPostDtldRlt.setCmnLocationMst(lObjCmnLocationMst);
			lObjPostDtldRlt.setOrgDesignationMst(lObjOrgDesigmMst);
			lObjPostDtldRlt.setCmnLanguageMst(lObjCmnLanguageMst);
			lObjPostDtldRlt.setOrgUserMstByCreatedBy(lObjOrgUserMstCrtd);
			lObjPostDtldRlt.setOrgPostMstByCreatedByPost(postIdCrtd);
			lObjPostDtldRlt.setCreatedDate(DBUtility.getCurrentDateFromDB());
			ghibSession.save(lObjPostDtldRlt);
			ghibSession.flush();
		}catch(Exception e){
			  logger.error(" Error is : " + e, e);
			  throw e;
		}
	}
	
	public void insertPostRoleRlt(Long lLngPostId, Long lLngUserIdCrtd, Long lLngPostIdCrtd, Map inputMap,String lStrUserType)throws Exception
	{
		Long lLngPostRoleId = null;
		AclRoleMst lObjAclRoleMst = null;
		
		try{
			OrgPostMstDaoImpl orgPostId = new OrgPostMstDaoImpl(OrgPostMst.class,this.getSessionFactory());
			OrgPostMst postId = orgPostId.read(lLngPostId);
			OrgPostMst postIdCrtd = orgPostId.read(lLngPostIdCrtd);
			
			DdoOutsideSevaarthDAOImpl lObjDdoOutSideSevaarth = new DdoOutsideSevaarthDAOImpl(AclRoleMst.class,this.getSessionFactory());
			
			if(lStrUserType.trim().equals("DDO"))
			{
				lObjAclRoleMst = (AclRoleMst) lObjDdoOutSideSevaarth.read(700002l);
			}
			else if(lStrUserType.trim().equals("ASST"))
			{
				lObjAclRoleMst = (AclRoleMst) lObjDdoOutSideSevaarth.read(700001l);
			}
			
			CmnLookupMstDAOImpl actFlag = new CmnLookupMstDAOImpl(CmnLookupMst.class,this.getSessionFactory()); 
			CmnLookupMst activeFlag = actFlag.read(Long.parseLong("1"));
			
			OrgUserMstDao lObjOrgUserMstDao = new OrgUserMstDaoImpl(OrgUserMst.class,this.getSessionFactory());
			OrgUserMst lObjOrgUserMstCrtd = lObjOrgUserMstDao.read(lLngUserIdCrtd);
			
			AclPostroleRlt lObjAclPostRoleRlt = new AclPostroleRlt();
			lLngPostRoleId = IFMSCommonServiceImpl.getNextSeqNum("acl_postrole_rlt", inputMap);
			lObjAclPostRoleRlt.setPostRoleId(lLngPostRoleId);
			lObjAclPostRoleRlt.setOrgPostMst(postId);
			lObjAclPostRoleRlt.setAclRoleMst(lObjAclRoleMst);
			lObjAclPostRoleRlt.setStartDate(DBUtility.getCurrentDateFromDB());
			lObjAclPostRoleRlt.setCmnLookupMstByActivate(activeFlag);
			lObjAclPostRoleRlt.setOrgPostMstByCreatedByPost(postIdCrtd);
			lObjAclPostRoleRlt.setOrgUserMstByCreatedBy(lObjOrgUserMstCrtd);
			lObjAclPostRoleRlt.setCreatedDate(DBUtility.getCurrentDateFromDB());
			ghibSession.save(lObjAclPostRoleRlt);
			ghibSession.flush();
		}catch(Exception e){
			  logger.error(" Error is : " + e, e);
			  throw e;
		}
	}
	
	public void insertUserPostRlt(Long lLngPostId, Long lLngUserId, Long lLngUserIdCrtd, Long lLngPostIdCrtd, Map inputMap)throws Exception
	{
		Long lLngEmpPostId = null;
		
		try{
			OrgPostMstDaoImpl orgPostId = new OrgPostMstDaoImpl(OrgPostMst.class,this.getSessionFactory());
			OrgPostMst postId = orgPostId.read(lLngPostId);
			OrgPostMst postIdCrtd = orgPostId.read(lLngPostIdCrtd);
			
			OrgUserMstDao lObjOrgUserMstDao = new OrgUserMstDaoImpl(OrgUserMst.class,this.getSessionFactory());
			OrgUserMst lObjOrgUserMstCrtd = lObjOrgUserMstDao.read(lLngUserIdCrtd);
			OrgUserMst lObjOrgUserMst = lObjOrgUserMstDao.read(lLngUserId);
			
			CmnLookupMstDAO lObjCmnLookUpMstDao = new CmnLookupMstDAOImpl(CmnLookupMst.class,this.getSessionFactory());
			CmnLookupMst lObjCmnLookupMst = lObjCmnLookUpMstDao.read(13l);
			
			OrgUserpostRlt lObjOrgUserpostRlt = new OrgUserpostRlt();
			//lLngEmpPostId = IFMSCommonServiceImpl.getNextSeqNum("ORG_USERPOST_RLT", inputMap);
			lLngEmpPostId = getNextSeqNoLocForOrgUserpostRlt();
			lObjOrgUserpostRlt.setEmpPostId(lLngEmpPostId);
			lObjOrgUserpostRlt.setOrgUserMst(lObjOrgUserMst);
			lObjOrgUserpostRlt.setOrgPostMstByPostId(postId);
			lObjOrgUserpostRlt.setStartDate(DBUtility.getCurrentDateFromDB());
			lObjOrgUserpostRlt.setActivateFlag(1l);
			lObjOrgUserpostRlt.setOrgPostMstByCreatedByPost(postIdCrtd);
			lObjOrgUserpostRlt.setOrgUserMstByCreatedBy(lObjOrgUserMstCrtd);
			lObjOrgUserpostRlt.setCreatedDate(DBUtility.getCurrentDateFromDB());
			lObjOrgUserpostRlt.setCmnLookupUserPostType(lObjCmnLookupMst);
			ghibSession.save(lObjOrgUserpostRlt);
			ghibSession.flush();
		}catch(Exception e){
			  logger.error(" Error is : " + e, e);
			  throw e;
		}
	}
	
	public void insertOrgDdoMst(String lStrDdoCode,String lStrDdoName,String lStrDdoPrsnlName,Long lLngPostId,Long lLngUserIdCrtd,
			String lStrLocationCode,Long lLngPostIdCrtd,String lstrDeptLocCode,Map inputMap)throws Exception
	{
		Long lLndDdoId = null;
		try{
			OrgDdoMst lObjOrgDdoMst = new OrgDdoMst();
			lLndDdoId = IFMSCommonServiceImpl.getNextSeqNum("org_ddo_mst", inputMap);
			logger.info("lLndDdoId******************"+lLndDdoId);
			logger.info("lStrDdoCode******************"+lStrDdoCode);
			lObjOrgDdoMst.setDdoId(lLndDdoId);
			lObjOrgDdoMst.setDdoCode(lStrDdoCode);
			lObjOrgDdoMst.setDdoName(lStrDdoName);
			lObjOrgDdoMst.setDdoPersonalName(lStrDdoPrsnlName);
			lObjOrgDdoMst.setPostId(lLngPostId);
			lObjOrgDdoMst.setLangId(1l);
			lObjOrgDdoMst.setDeptLocCode(lstrDeptLocCode);
			lObjOrgDdoMst.setCreatedBy(lLngUserIdCrtd);
			lObjOrgDdoMst.setCreatedByPost(lLngPostIdCrtd);
			lObjOrgDdoMst.setCreatedDate(DBUtility.getCurrentDateFromDB());
			lObjOrgDdoMst.setDbId(99l);
			lObjOrgDdoMst.setLocationCode(lStrLocationCode);
			ghibSession.save(lObjOrgDdoMst);
			ghibSession.flush();
		}catch(Exception e){
			  logger.error(" Error is : " + e, e);
			  throw e;
		}
	}
	
	public void insertMstDcpsDdoOffice(String lStrDdoCode,String lStrDdoOffice,String lStrDistCode,Long lLngLocId,
			Long lLngUserIdCrtd, Long lLngPostIdCrtd,Map inputMap,String uniqeInstituteId)throws Exception
	{
		Long lLngMstOfficeDdoId = null;
		try{
			DdoOffice lObjDdoOffice = new DdoOffice();
			lLngMstOfficeDdoId = IFMSCommonServiceImpl.getNextSeqNum("MST_DCPS_DDO_OFFICE", inputMap);
			//lLngMstOfficeDdoId = getNextSeqNoLocForDdoOffice();
			logger.info("lLngMstOfficeDdoId******************"+lLngMstOfficeDdoId);
			logger.info("lStrDdoCode******************"+lStrDdoCode);
			lObjDdoOffice.setDcpsDdoOfficeIdPk(lLngMstOfficeDdoId);
			lObjDdoOffice.setDcpsDdoCode(lStrDdoCode);
			lObjDdoOffice.setDcpsDdoOfficeName(lStrDdoOffice);
			lObjDdoOffice.setDcpsDdoOfficeDdoFlag("Yes");
			lObjDdoOffice.setDcpsDdoOfficeState("15");
			lObjDdoOffice.setDcpsDdoOfficeDistrict(lStrDistCode);
			lObjDdoOffice.setLangId(1l);
			lObjDdoOffice.setLocId(lLngLocId);
			lObjDdoOffice.setDbId(99l);			
			lObjDdoOffice.setPostId(lLngPostIdCrtd);
			lObjDdoOffice.setUserId(lLngUserIdCrtd);
			lObjDdoOffice.setCreatedDate(DBUtility.getCurrentDateFromDB());
			lObjDdoOffice.setStatusFlag(0l);
			logger.info("uniqeInstituteId******************"+uniqeInstituteId);
			lObjDdoOffice.setUniqueInstituteNo(uniqeInstituteId);
			ghibSession.save(lObjDdoOffice);
			ghibSession.flush();
		}catch(Exception e){
			  logger.error(" Error is : " + e, e);
			  throw e;
		}
	}
	
	public void insertRltDdoOrg(Long lLngUserIdCrtd, Long lLngPostIdCrtd,String lStrDdoCode,String lStrTrsryCode,Map inputMap)throws Exception
	{
		Long lLngDdoOrgId = null;
		try{
			RltDdoOrg lObjRltDdoOrg = new RltDdoOrg();
			lLngDdoOrgId = getNextSeqNoLocForRltDdoOrg();
			lObjRltDdoOrg.setDdoOrgId(lLngDdoOrgId);
			lObjRltDdoOrg.setActivateFlag(1l);
			lObjRltDdoOrg.setCreatedDate(DBUtility.getCurrentDateFromDB());
			lObjRltDdoOrg.setCreatedPostId(lLngPostIdCrtd);
			lObjRltDdoOrg.setCreatedUserId(lLngUserIdCrtd);
			lObjRltDdoOrg.setDdoCode(lStrDdoCode);
			lObjRltDdoOrg.setLocationCode(lStrTrsryCode);
			lObjRltDdoOrg.setTrnCounter(1);
			ghibSession.save(lObjRltDdoOrg);
			ghibSession.flush();
		}catch(Exception e){
			  logger.error(" Error is : " + e, e);
			  throw e;
		}
	}

	public List RetirveParentdtlfrmReptCode(String strRepoDDOCode) {
		List temp=null;
		try
		{		
			String branchQuery = "SELECT DEPT_LOC_CODE||'##'||HOD_LOC_CODE FROM ORG_DDO_MST where DDO_CODE like '%"+strRepoDDOCode+"%'";
			Query sqlQuery= hibSession.createSQLQuery(branchQuery);
			temp= sqlQuery.list();


			logger.error("List Size"+temp.size());

		}
		catch(Exception e){
			logger.error("Error in ZpAdminOfficeMstDAOImpl \n " + e);
			e.printStackTrace();
		}
		return temp;
	}
	
	
}
