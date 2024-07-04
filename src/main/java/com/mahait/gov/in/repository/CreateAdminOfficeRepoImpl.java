package com.mahait.gov.in.repository;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.mahait.gov.in.entity.CmnLanguageMst;
import com.mahait.gov.in.entity.OrgEmpMst;
import com.mahait.gov.in.entity.OrgGradeMst;
import com.mahait.gov.in.entity.OrgPostMst;
import com.mahait.gov.in.entity.OrgUserMst;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Repository
public class CreateAdminOfficeRepoImpl implements CreateAdminOfficeRepo {

	@PersistenceContext
	EntityManager entityManager;
	
	   private static final Logger logger = LoggerFactory.getLogger(CreateAdminOfficeRepoImpl.class);

	@Override
	public List<Object[]> getAllDDOOfficeDtlsData(String districtName, String talukaNametName, String adminType) {
		List list = null;

		Session hibSession = entityManager.unwrap(Session.class);

		StringBuffer strQuery = new StringBuffer();
		strQuery.append(
				"SELECT zp.ZP_DDO_CODE,zp.REPT_DDO_CODE,zp.FINAL_DDO_CODE,zp.SPECIAL_DDO_CODE,zp.ZPLEVEL,zp.STATUS FROM  RLT_ZP_DDO_MAP zp ");
		strQuery.append(" inner join mst_dcps_ddo_office office on zp.zp_ddo_code=office.ddo_code ");
		strQuery.append(" where zp.LANG_ID =1 and zp.status is not null ");
		if ((districtName != null) && (districtName != "") && (Long.parseLong(districtName) != -1)) {
			strQuery.append(" and office.district='" + districtName + "'");
		}
		if ((talukaNametName != null) && (talukaNametName != "") && (Long.parseLong(talukaNametName) != -1)) {
			strQuery.append(" and office.taluka='" + talukaNametName + "'");
		}

		if ((adminType != null) && (adminType != "") && (Long.parseLong(adminType) != -1)) {
			strQuery.append(" and zp.ZP_DDO_CODE like '" + adminType + "%'");
		}
		strQuery.append(" order by ZP_MAP_ID desc");

		// logger.info("Details Query :"+strQuery.toString());
		Query query = hibSession.createSQLQuery(strQuery.toString());
		list = query.list();
		return list;
	}

	@Override
	public List<Object[]> findPostLocationByDdoCode(Long ddoCode) {
		String sql = "WITH ddo_info AS (" + "    SELECT post_id, LOCATION_CODE " + "    FROM org_ddo_mst "
				+ "    WHERE DDO_CODE = :ddoCode " + ") " + "SELECT opd.POST_SHORT_NAME, clm.loc_name "
				+ "FROM ORG_POST_DETAILS_RLT opd " + "JOIN ddo_info di ON opd.post_ID = di.post_id "
				+ "JOIN CMN_LOCATION_MST clm ON clm.LOC_ID = di.LOCATION_CODE";

		Query query = (Query) entityManager.createNativeQuery(sql);
		query.setParameter("ddoCode", ddoCode);

		return query.getResultList();
	}

	public List getTreasuryName(Long TOCode) {
		Session hibSession = entityManager.unwrap(Session.class);
		List temp = null;
		try {
			String branchQuery = "select CM.loc_id,CM.loc_name from rlt_ddo_org RL join cmn_location_mst CM on cast(RL.location_code as bigint) = CM.loc_id  where RL.ddo_code = '"
					+ String.valueOf(TOCode)+"'";
			Query sqlQuery = hibSession.createSQLQuery(branchQuery);
			temp = sqlQuery.list();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return temp;
	}
	
	
	
	public List getAdminOfcCode(String AdminOfcID)
	{
		Session hibSession = entityManager.unwrap(Session.class);
		List temp=null;
		try
		{		
			String branchQuery = "select aa.OFFICE_CODE from ZP_ADMIN_OFFICE_MST aa where OFC_ID='"+AdminOfcID+"'";
			Query sqlQuery= hibSession.createSQLQuery(branchQuery);
			temp= sqlQuery.list();

		}
		catch(Exception e){
			e.printStackTrace();
		}
		return temp;
	}
	
	public List getCountofDDOCode(String CountCode)
	{
		Session hibSession = entityManager.unwrap(Session.class);
		List temp=null;
		try
		{		
			String branchQuery = "select count(qq.DDO_CODE) from ORG_DDO_MST qq where DDO_CODE like '"+ CountCode +"%'";
			Query sqlQuery= hibSession.createSQLQuery(branchQuery);
			temp= sqlQuery.list();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return temp;
	}

	@Override
	public List getSubTreasury(Long valueOf) {
		List temp=null;
		Session hibSession = entityManager.unwrap(Session.class);
		try
		{		
			String branchQuery = "SELECT loc_id,loc_name from CMN_LOCATION_MST where DEPARTMENT_ID= 100006 and PARENT_LOC_ID="+valueOf;
			Query sqlQuery= hibSession.createSQLQuery(branchQuery);
			temp= sqlQuery.list();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return temp;
	}
	
	
	@Override
	public int getUniqeInstituteIdCount(String strDdoCode) 
	{
		Session hibSession = entityManager.unwrap(Session.class);
		int uniqeInstituteIdCount=0;
		StringBuilder sb = new StringBuilder();
		sb.append(" select count(1) from MST_DCPS_DDO_OFFICE where UNIQUE_INSTITUTE_NO is not null ");
		Query selectQuery = hibSession.createSQLQuery(sb.toString());
		uniqeInstituteIdCount = Integer.parseInt(selectQuery.uniqueResult().toString());
		return uniqeInstituteIdCount;
	}



	@Override
	public List<Object[]> retriveDisctOfcList(OrgUserMst messages, String ofcId) {
		if(ofcId==null || ofcId.equals("") ) {
			String nativeQuery = "select DIST_MST_OFC_NAME, DIST_CODE from ZP_ADMIN_OFFICE_DISTRICT_MPG";
	        Query query = (Query) entityManager.createNativeQuery(nativeQuery);
	      //  query.setParameter("ofcId",ofcId);
	        List<Object[]> resultList = query.getResultList();
	        return resultList;
		}else {
			String nativeQuery = "select DIST_MST_OFC_NAME, DIST_CODE from ZP_ADMIN_OFFICE_DISTRICT_MPG where admin_OFFICE_CODE = :ofcId";
	        Query query = (Query) entityManager.createNativeQuery(nativeQuery);
	        query.setParameter("ofcId",ofcId);
	        List<Object[]> resultList = query.getResultList();
	        return resultList;
		}
    }

	@Override
	public List<Object[]> retrieveDistrictOfficeList(OrgUserMst messages, Long ofcId) {
		  String nativeQuery = "select DIST_MST_OFC_NAME, DIST_CODE from ZP_ADMIN_OFFICE_DISTRICT_MPG where admin_OFFICE_CODE = :ofcId";
	        Query query = (Query) entityManager.createNativeQuery(nativeQuery);
	        query.setParameter("ofcId", ofcId);
	        List<Object[]> resultList = query.getResultList();
	        return resultList;
	}
	
	public void insertEmpMst(Long lLngUserId, String lStrFname, Long lLngUserIdCrtd, Long lLngPostIdCrtd, String lStrGendr,OrgUserMst OrgUsermst) throws Exception
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
			
			lObjEmpMst.setEmpLname(" ");
			lObjEmpMst.setEmpMname(" ");
			
			lObjEmpMst.setEmpDob(lObjEmpDob);
			
			lObjEmpMst.setEmpDoj(lObjEmpDob);
			lObjEmpMst.setEmpSrvcFlag(1l);
			lObjEmpMst.setOrgGradeMst(lObjOrgGradeMst);
			
			lObjEmpMst.setCmnLanguageMst(lObjCmnLanguageMst);	
			
			lObjEmpMst.setOrgUserMst(lObjOrgUserMst);
			logger.info("lObjOrgUserMst is***********"+lObjOrgUserMst);
			
			lObjEmpMst.setStartDate(DBUtility.getCurrentDateFromDB());
			lObjEmpMst.setActivateFlag(1l);
			lObjEmpMst.setOrgUserMstByCreatedBy(lObjOrgUserMstCrtd);
			logger.info("lObjOrgUserMstCrtd is***********"+lObjOrgUserMstCrtd);
			lObjEmpMst.setOrgPostMstByCreatedByPost(postId);
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
			//lLngDdoOrgId = IFMSCommonServiceImpl.getNextSeqNum("rlt_ddo_org", inputMap);
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
	
	public void insertWfOrgPost(String Pid)
	{
		Session hibSession = getSession();
		Integer lIntProjId = 101;
		try{
			CmnProjectMstDao lObjCmnProjectMstDao = new CmnProjectMstDaoImpl(CmnProjectMst.class,this.getSessionFactory());
			CmnProjectMst lObjCmnProjectMst = lObjCmnProjectMstDao.read(lIntProjId);
			
			CmnDatabaseMstDaoImpl dbDAO = new CmnDatabaseMstDaoImpl(CmnDatabaseMst.class,this.getSessionFactory());
			CmnDatabaseMst  dbId = dbDAO.read(Long.parseLong("99"));
			
			WfOrgPostMpgMst lObjWfOrgPostMpgMst = new WfOrgPostMpgMst();
			
			lObjWfOrgPostMpgMst.setPostId(Pid);
			lObjWfOrgPostMpgMst.setCmnProjectMst(lObjCmnProjectMst);
			lObjWfOrgPostMpgMst.setCmnDatabaseMst(dbId);
			hibSession.save(lObjWfOrgPostMpgMst);
			hibSession.flush();
		}catch(Exception e){
			logger.error(" Error is : " + e, e);
		}
	}
	
	public void insertWfOrgLoc(String LocCode)
	{
		Session hibSession = getSession();
		Integer lIntProjId = 101;
		try{
			CmnProjectMstDao lObjCmnProjectMstDao = new CmnProjectMstDaoImpl(CmnProjectMst.class,this.getSessionFactory());
			CmnProjectMst lObjCmnProjectMst = lObjCmnProjectMstDao.read(lIntProjId);
			
			CmnDatabaseMstDaoImpl dbDAO = new CmnDatabaseMstDaoImpl(CmnDatabaseMst.class,this.getSessionFactory());
			CmnDatabaseMst dbId = dbDAO.read(Long.parseLong("99"));
			
			WfOrgLocMpgMst lObjWfOrgLocMpgMst = new WfOrgLocMpgMst();
			
			lObjWfOrgLocMpgMst.setLocId(LocCode);
			lObjWfOrgLocMpgMst.setCmnProjectMst(lObjCmnProjectMst);			
			hibSession.save(lObjWfOrgLocMpgMst);
			hibSession.flush();
		}catch(Exception e){
			logger.error(" Error is : " + e, e);
		}
	}
	
	
	
}
