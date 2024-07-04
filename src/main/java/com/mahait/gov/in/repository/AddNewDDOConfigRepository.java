package com.mahait.gov.in.repository;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.mahait.gov.in.entity.CmnDatabaseMst;
import com.mahait.gov.in.entity.CmnLanguageMst;
import com.mahait.gov.in.entity.CmnLocationMst;
import com.mahait.gov.in.entity.CmnLookupMst;
import com.mahait.gov.in.entity.OrgDdoMst;
import com.mahait.gov.in.entity.OrgDesignationMst;
import com.mahait.gov.in.entity.OrgEmpMst;
import com.mahait.gov.in.entity.OrgGradeMst;
import com.mahait.gov.in.entity.OrgPostDetailsRlt;
import com.mahait.gov.in.entity.OrgPostMst;
import com.mahait.gov.in.entity.OrgUserMst;
import com.mahait.gov.in.entity.OrgUserpostRlt;

@Repository
public class AddNewDDOConfigRepository {
	Session ghibSession = null;
	
	@PersistenceContext
	EntityManager entityManager;

	public void AddNewDDOConfigDAOImpl() 
	{
		ghibSession = entityManager.unwrap(Session.class);
	}

	public List getAllAdminDepartment(){
			StringBuilder lSBQuery = new StringBuilder();
			lSBQuery.append("SELECT locId, locName FROM CmnLocationMst \n");
			lSBQuery.append("WHERE departmentId = 100001");
			Query lQuery = ghibSession.createQuery(lSBQuery.toString());
			return  lQuery.list();
	}

	public List getAllFieldDepartment() {
		
			StringBuilder lSBQuery = new StringBuilder();
			lSBQuery.append("SELECT locId, locName FROM CmnLocationMst \n");
			lSBQuery.append("WHERE departmentId = 100011");
			Query lQuery = ghibSession.createQuery(lSBQuery.toString());
			return lQuery.list();
		
	}

	public List getAllTreasury() {
			StringBuilder lSBQuery = new StringBuilder();
			lSBQuery.append("SELECT locId, locName FROM CmnLocationMst \n");
			lSBQuery.append("WHERE departmentId in (100003,100006) ORDER BY locName ");
			Query lQuery = ghibSession.createQuery(lSBQuery.toString());
			return lQuery.list();

	}

	public String chkDdoCode(String lStrDdoCode)  {
		String lStrResData = "";
		List lLstResData = null;

			StringBuilder lSBQuery = new StringBuilder();
			lSBQuery.append("FROM OrgDdoMst WHERE ddoCode=:ddoCode \n");
			Query lQuery = ghibSession.createQuery(lSBQuery.toString());
			lQuery.setParameter("ddoCode", lStrDdoCode);
			lLstResData = lQuery.list();

			if (lLstResData.size() > 0) {
				lStrResData = "Y";
			} else {
				lStrResData = "N";
			}
		
		return lStrResData;
	}

	
	public String insertLocation(String lStrLocationName, Long lLngUserIdCrtd, Long lLngPostIdCrtd,
			Long lLngFieldDeptId, String lStrLocPin, Map inputMap, String strDistCode) throws Exception {
		logger.info("insertLocation 1");
		Long lLngLocId = null;

		try {
			CmnLocationMstDaoImpl lObjCmnLocationMstDao = new CmnLocationMstDaoImpl(CmnLocationMst.class,
					this.getSessionFactory());
			CmnLocationMst lObjCmnLocationMst = new CmnLocationMst();

			CmnLanguageMstDao lObjCmnLanguageDao = new CmnLanguageMstDaoImpl(CmnLanguageMst.class,
					this.getSessionFactory());
			CmnLanguageMst lObjCmnLanguageMst = lObjCmnLanguageDao.read(1l);

			CmnLookupMstDAO lObjCmnLookUpMstDao = new CmnLookupMstDAOImpl(CmnLookupMst.class, this.getSessionFactory());
			CmnLookupMst lObjCmnLookupMst = lObjCmnLookUpMstDao.read(1l);
			logger.info("insertLocation 2");
			// commented by vaibhav tyagi
			// lLngLocId = IFMSCommonServiceImpl.getNextSeqNum("cmn_location_mst",
			// inputMap);

			// added by vaibhav tyagi: start
			lLngLocId = getNextSeqNoLoc();
		
			lObjCmnLocationMst.setLocId(lLngLocId);
			logger.info("lLngLocId****" + lLngLocId);

			lObjCmnLocationMst.setLocName(lStrLocationName);
			logger.info("lStrLocationName****" + lStrLocationName);

			if (lStrLocationName.length() >= 15) {
				lObjCmnLocationMst.setLocShortName(lStrLocationName.substring(0, 15));
			} 
			else {
				lObjCmnLocationMst.setLocShortName(".");
			}
			lObjCmnLocationMst.setCmnLanguageMst(lObjCmnLanguageMst);
			lObjCmnLocationMst.setDepartmentId(100007l);
			lObjCmnLocationMst.setParentLocId(lLngFieldDeptId);
			lObjCmnLocationMst.setLocPin("1");
			lObjCmnLocationMst.setCmnLookupMst(lObjCmnLookupMst);
			lObjCmnLocationMst.setStartDate(new Date());
			lObjCmnLocationMst.setActivateFlag(1l);
			lObjCmnLocationMst.setCreatedBy(lLngUserIdCrtd);
			lObjCmnLocationMst.setCreatedByPost(lLngPostIdCrtd);
			lObjCmnLocationMst.setCreatedDate(DBUtility.getCurrentDateFromDB());
			lObjCmnLocationMst.setLocationCode(lLngLocId.toString());
			// code added by vivek on 04/03/2013
			Long distCode = (strDistCode != null && Long.parseLong(strDistCode) > 0) ? Long.parseLong(strDistCode) : -1;
			lObjCmnLocationMst.setLocDistrictId(distCode);
			// lObjCmnLocationMst.setLocStateId(Long.parseLong(gObjRsrcBndle.getString("LOC.MAHARASHTRA")));
			lObjCmnLocationMst.setLocStateId(15L);
			// code ends
			ghibSession.save(lObjCmnLocationMst);
			ghibSession.flush();

		
		return lLngLocId.toString();
	}

	public Long insertUserMst(String lStrDdoCode, Long lLngUserIdCrtd, Long lLngPostIdCrtd, Map inputMap)
			throws Exception {
		String ddoc = lStrDdoCode;
		Long lLngUserId = null;
		try {
			logger.info("inside isert userr mst is***********" + lStrDdoCode);
			CmnLookupMstDAO lObjCmnLookUpMstDao = new CmnLookupMstDAOImpl(CmnLookupMst.class, this.getSessionFactory());
			CmnLookupMst lObjCmnLookupMst = lObjCmnLookUpMstDao.read(1l);

			OrgUserMstDao lObjOrgUserMstDao = new OrgUserMstDaoImpl(OrgUserMst.class, this.getSessionFactory());
			OrgUserMst lObjOrgUserMstCrtdUsr = lObjOrgUserMstDao.read(lLngUserIdCrtd);
			logger.info("inside isert userr mst is*******post is is ****" + lLngPostIdCrtd);
			logger.info("inside isert userr mst is*******lLngUserIdCrtd is is ****" + lLngUserIdCrtd);
			OrgPostMstDaoImpl orgPostId = new OrgPostMstDaoImpl(OrgPostMst.class, this.getSessionFactory());
			OrgPostMst postId = orgPostId.read(lLngPostIdCrtd);

			OrgUserMst lObjUserMst = new OrgUserMst();
			lLngUserId = getNextSeqNoLocForUserMst();

			logger.info("lLngUserId is***********" + lLngUserId);
			lObjUserMst.setUserId(lLngUserId);

			lObjUserMst.setUserName(ddoc);
			logger.info("ddoc is***********" + ddoc);

			lObjUserMst.setPassword("0b76f0f411f6944f9d192da0fcbfb292");

			lObjUserMst.setCmnLookupMst(lObjCmnLookupMst);
			logger.info("lObjCmnLookupMst is***********" + lObjCmnLookupMst);

			lObjUserMst.setStartDate(DBUtility.getCurrentDateFromDB());
			lObjUserMst.setActivateFlag(0l);
			lObjUserMst.setCreatedDate(DBUtility.getCurrentDateFromDB());

			lObjUserMst.setOrgUserMstByCreatedBy(lObjOrgUserMstCrtdUsr);
			logger.info("lObjOrgUserMstCrtdUsr is***********" + lObjOrgUserMstCrtdUsr);

			lObjUserMst.setOrgPostMstByCreatedByPost(postId);
			logger.info("postId is***********" + postId);

			lObjUserMst.setSecretQueCode("Secret_Other");
			lObjUserMst.setSecretQueOther("Secret_Other");// TODO -- Needs to Change
			lObjUserMst.setSecretAnswer("ifms");
			ghibSession.save(lObjUserMst);
			ghibSession.flush();
		} catch (Exception e) {
			logger.error(" Error is : " + e, e);
			throw e;
		}

		return lLngUserId;
	}

	public void insertEmpMst(Long lLngUserId, String lStrFname, Long lLngUserIdCrtd, Long lLngPostIdCrtd,
			String lStrGendr, Map inputMap) throws Exception {
		Long lLngEmpId = null;
		try {
			SimpleDateFormat lObjDateFormate = new SimpleDateFormat("dd/MM/yyyy");
			Date lObjEmpDob = new Date("01/01/9999");

			OrgGradeMstDao lObjOrgGradeMstDao = new OrgGradeMstDaoImpl(OrgGradeMst.class, this.getSessionFactory());
			OrgGradeMst lObjOrgGradeMst = lObjOrgGradeMstDao.read(100064l);

			CmnLanguageMstDao lObjCmnLanguageDao = new CmnLanguageMstDaoImpl(CmnLanguageMst.class,
					this.getSessionFactory());
			CmnLanguageMst lObjCmnLanguageMst = lObjCmnLanguageDao.read(1l);

			OrgUserMstDao lObjOrgUserMstDao = new OrgUserMstDaoImpl(OrgUserMst.class, this.getSessionFactory());
			OrgUserMst lObjOrgUserMst = lObjOrgUserMstDao.read(lLngUserId);
			OrgUserMst lObjOrgUserMstCrtd = lObjOrgUserMstDao.read(lLngUserIdCrtd);

			OrgPostMstDaoImpl orgPostId = new OrgPostMstDaoImpl(OrgPostMst.class, this.getSessionFactory());
			OrgPostMst postId = orgPostId.read(lLngPostIdCrtd);

			OrgEmpMst lObjEmpMst = new OrgEmpMst();
			// lLngEmpId = IFMSCommonServiceImpl.getNextSeqNum("org_emp_mst", inputMap);
			lLngEmpId = getNextSeqNoLocForEmpMst();
			lObjEmpMst.setEmpId(lLngEmpId);
			logger.info("lLngEmpId is***********" + lLngEmpId);

			lObjEmpMst.setEmpFname(lStrFname);
			logger.info("lStrFname is***********" + lStrFname);

			lObjEmpMst.setEmpLname(" ");
			lObjEmpMst.setEmpMname(" ");

			lObjEmpMst.setEmpDob(lObjEmpDob);
			logger.info("lObjEmpDob is***********" + lObjEmpDob);

			lObjEmpMst.setEmpDoj(lObjEmpDob);
			logger.info("lObjEmpDob is***********" + lObjEmpDob);
			lObjEmpMst.setEmpSrvcFlag(1l);
			lObjEmpMst.setOrgGradeMst(lObjOrgGradeMst);
			logger.info("lObjOrgGradeMst is***********" + lObjOrgGradeMst);

			lObjEmpMst.setCmnLanguageMst(lObjCmnLanguageMst);
			logger.info("lObjCmnLanguageMst is***********" + lObjCmnLanguageMst);

			lObjEmpMst.setOrgUserMst(lObjOrgUserMst);
			logger.info("lObjOrgUserMst is***********" + lObjOrgUserMst);

			lObjEmpMst.setStartDate(DBUtility.getCurrentDateFromDB());
			lObjEmpMst.setActivateFlag(1l);
			lObjEmpMst.setOrgUserMstByCreatedBy(lObjOrgUserMstCrtd);
			logger.info("lObjOrgUserMstCrtd is***********" + lObjOrgUserMstCrtd);
			lObjEmpMst.setOrgPostMstByCreatedByPost(postId);
			lObjEmpMst.setCreatedDate(DBUtility.getCurrentDateFromDB());
			if (lStrGendr.equals("M")) {
				lObjEmpMst.setEmpPrefix("Mr");
			} else if (lStrGendr.equals("F")) {
				lObjEmpMst.setEmpPrefix("Ms");
			}

			ghibSession.save(lObjEmpMst);
			ghibSession.flush();
		} catch (Exception e) {
			logger.error(" Error is : " + e, e);
			throw e;
		}
	}

	public void insertOrgPostMst(Long lLngPostId, String lStrLocationCode, Long lLngUserIdCrtd, Long lLngPostIdCrtd,
			String lStrDsgnCode, Map inputMap) throws Exception {
		try {
			CmnLookupMstDAO lObjCmnLookUpMstDao = new CmnLookupMstDAOImpl(CmnLookupMst.class, this.getSessionFactory());
			CmnLookupMst lObjCmnLookupMst = lObjCmnLookUpMstDao.read(13l);

			OrgUserMstDao lObjOrgUserMstDao = new OrgUserMstDaoImpl(OrgUserMst.class, this.getSessionFactory());
			OrgUserMst lObjOrgUserMstCrtdUsr = lObjOrgUserMstDao.read(lLngUserIdCrtd);

			OrgPostMstDaoImpl orgPostId = new OrgPostMstDaoImpl(OrgPostMst.class, this.getSessionFactory());
			OrgPostMst postId = orgPostId.read(lLngPostIdCrtd);

			OrgPostMst lObjOrgPostMst = new OrgPostMst();
			// lLngPostId = IFMSCommonServiceImpl.getNextSeqNum("org_post_mst", inputMap);
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
		} catch (Exception e) {
			logger.error(" Error is : " + e, e);
			throw e;
		}
	}

	public void insertPostDtlsRlt(String lstrLocCode, Long lLngPostId, String lStrDesignName, Long lLngDsgnId,
			Long lLngUserIdCrtd, Long lLngPostIdCrtd, Map inputMap) throws Exception {
		Long lLngPostDtlsId = null;
		try {
			OrgPostMstDaoImpl orgPostId = new OrgPostMstDaoImpl(OrgPostMst.class, this.getSessionFactory());
			OrgPostMst postId = orgPostId.read(lLngPostId);
			OrgPostMst postIdCrtd = orgPostId.read(lLngPostIdCrtd);

			CmnLocationMstDaoImpl lObjCmnLocationMstDao = new CmnLocationMstDaoImpl(CmnLocationMst.class,
					this.getSessionFactory());
			CmnLocationMst lObjCmnLocationMst = lObjCmnLocationMstDao.read(Long.parseLong(lstrLocCode));

			OrgDesignationMstDao lObjDesgnMstDao = new OrgDesignationMstDaoImpl(OrgDesignationMst.class,
					this.getSessionFactory());
			OrgDesignationMst lObjOrgDesigmMst = lObjDesgnMstDao.read(lLngDsgnId);

			CmnLanguageMstDao lObjCmnLanguageDao = new CmnLanguageMstDaoImpl(CmnLanguageMst.class,
					this.getSessionFactory());
			CmnLanguageMst lObjCmnLanguageMst = lObjCmnLanguageDao.read(1l);

			OrgUserMstDao lObjOrgUserMstDao = new OrgUserMstDaoImpl(OrgUserMst.class, this.getSessionFactory());
			OrgUserMst lObjOrgUserMstCrtd = lObjOrgUserMstDao.read(lLngUserIdCrtd);

			OrgPostDetailsRlt lObjPostDtldRlt = new OrgPostDetailsRlt();
			// lLngPostDtlsId = IFMSCommonServiceImpl.getNextSeqNum("org_post_details_rlt",
			// inputMap);
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
		} catch (Exception e) {
			logger.error(" Error is : " + e, e);
			throw e;
		}
	}

	public void insertPostRoleRlt(Long lLngPostId, Long lLngUserIdCrtd, Long lLngPostIdCrtd, Map inputMap,
			String lStrUserType) throws Exception {
		Long lLngPostRoleId = null;
		AclRoleMst lObjAclRoleMst = null;

		try {
			OrgPostMstDaoImpl orgPostId = new OrgPostMstDaoImpl(OrgPostMst.class, this.getSessionFactory());
			OrgPostMst postId = orgPostId.read(lLngPostId);
			OrgPostMst postIdCrtd = orgPostId.read(lLngPostIdCrtd);

			DdoOutsideSevaarthDAOImpl lObjDdoOutSideSevaarth = new DdoOutsideSevaarthDAOImpl(AclRoleMst.class,
					this.getSessionFactory());

			if (lStrUserType.trim().equals("DDO")) {
				lObjAclRoleMst = (AclRoleMst) lObjDdoOutSideSevaarth.read(700002l);
			} else if (lStrUserType.trim().equals("ASST")) {
				lObjAclRoleMst = (AclRoleMst) lObjDdoOutSideSevaarth.read(700001l);
			}

			CmnLookupMstDAOImpl actFlag = new CmnLookupMstDAOImpl(CmnLookupMst.class, this.getSessionFactory());
			CmnLookupMst activeFlag = actFlag.read(Long.parseLong("1"));

			OrgUserMstDao lObjOrgUserMstDao = new OrgUserMstDaoImpl(OrgUserMst.class, this.getSessionFactory());
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
		} catch (Exception e) {
			logger.error(" Error is : " + e, e);
			throw e;
		}
	}

	public void insertUserPostRlt(Long lLngPostId, Long lLngUserId, Long lLngUserIdCrtd, Long lLngPostIdCrtd,
			Map inputMap) throws Exception {
		Long lLngEmpPostId = null;

		try {
			OrgPostMstDaoImpl orgPostId = new OrgPostMstDaoImpl(OrgPostMst.class, this.getSessionFactory());
			OrgPostMst postId = orgPostId.read(lLngPostId);
			OrgPostMst postIdCrtd = orgPostId.read(lLngPostIdCrtd);

			OrgUserMstDao lObjOrgUserMstDao = new OrgUserMstDaoImpl(OrgUserMst.class, this.getSessionFactory());
			OrgUserMst lObjOrgUserMstCrtd = lObjOrgUserMstDao.read(lLngUserIdCrtd);
			OrgUserMst lObjOrgUserMst = lObjOrgUserMstDao.read(lLngUserId);

			CmnLookupMstDAO lObjCmnLookUpMstDao = new CmnLookupMstDAOImpl(CmnLookupMst.class, this.getSessionFactory());
			CmnLookupMst lObjCmnLookupMst = lObjCmnLookUpMstDao.read(13l);

			OrgUserpostRlt lObjOrgUserpostRlt = new OrgUserpostRlt();
			// lLngEmpPostId = IFMSCommonServiceImpl.getNextSeqNum("ORG_USERPOST_RLT",
			// inputMap);
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
		} catch (Exception e) {
			logger.error(" Error is : " + e, e);
			throw e;
		}
	}

	public void insertOrgDdoMst(String lStrDdoCode, String lStrDdoName, String lStrDdoPrsnlName, Long lLngPostId,
			Long lLngUserIdCrtd, String lStrLocationCode, Long lLngPostIdCrtd, String lstrDeptLocCode, Map inputMap)
			throws Exception {
		Long lLndDdoId = null;
		try {
			OrgDdoMst lObjOrgDdoMst = new OrgDdoMst();
			lLndDdoId = IFMSCommonServiceImpl.getNextSeqNum("org_ddo_mst", inputMap);
			logger.info("lLndDdoId******************" + lLndDdoId);
			logger.info("lStrDdoCode******************" + lStrDdoCode);
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
		} catch (Exception e) {
			logger.error(" Error is : " + e, e);
			throw e;
		}
	}

	public void insertMstDcpsDdoOffice(String lStrDdoCode, String lStrDdoOffice, String lStrDistCode, Long lLngLocId,
			Long lLngUserIdCrtd, Long lLngPostIdCrtd, Map inputMap, String uniqeInstituteId) throws Exception {
		Long lLngMstOfficeDdoId = null;
		try {
			DdoOffice lObjDdoOffice = new DdoOffice();
			lLngMstOfficeDdoId = IFMSCommonServiceImpl.getNextSeqNum("MST_DCPS_DDO_OFFICE", inputMap);
			// lLngMstOfficeDdoId = getNextSeqNoLocForDdoOffice();
			logger.info("lLngMstOfficeDdoId******************" + lLngMstOfficeDdoId);
			logger.info("lStrDdoCode******************" + lStrDdoCode);
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
			logger.info("uniqeInstituteId******************" + uniqeInstituteId);
			lObjDdoOffice.setUniqueInstituteNo(uniqeInstituteId);
			ghibSession.save(lObjDdoOffice);
			ghibSession.flush();
		} catch (Exception e) {
			logger.error(" Error is : " + e, e);
			throw e;
		}
	}

	public void insertRltDdoOrg(Long lLngUserIdCrtd, Long lLngPostIdCrtd, String lStrDdoCode, String lStrTrsryCode,
			Map inputMap) throws Exception {
		Long lLngDdoOrgId = null;
		try {
			RltDdoOrg lObjRltDdoOrg = new RltDdoOrg();
			// lLngDdoOrgId = IFMSCommonServiceImpl.getNextSeqNum("rlt_ddo_org", inputMap);
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
		} catch (Exception e) {
			logger.error(" Error is : " + e, e);
			throw e;
		}
	}

	public void insertWfOrgPost(String Pid) {
		Session hibSession = getSession();
		Integer lIntProjId = 101;
		try {
			CmnProjectMstDao lObjCmnProjectMstDao = new CmnProjectMstDaoImpl(CmnProjectMst.class,
					this.getSessionFactory());
			CmnProjectMst lObjCmnProjectMst = lObjCmnProjectMstDao.read(lIntProjId);

			CmnDatabaseMstDaoImpl dbDAO = new CmnDatabaseMstDaoImpl(CmnDatabaseMst.class, this.getSessionFactory());
			CmnDatabaseMst dbId = dbDAO.read(Long.parseLong("99"));

			WfOrgPostMpgMst lObjWfOrgPostMpgMst = new WfOrgPostMpgMst();

			lObjWfOrgPostMpgMst.setPostId(Pid);
			lObjWfOrgPostMpgMst.setCmnProjectMst(lObjCmnProjectMst);
			lObjWfOrgPostMpgMst.setCmnDatabaseMst(dbId);
			hibSession.save(lObjWfOrgPostMpgMst);
			hibSession.flush();
		} catch (Exception e) {
			logger.error(" Error is : " + e, e);
		}
	}

	public void insertWfOrgLoc(String LocCode) {
		Session hibSession = getSession();
		Integer lIntProjId = 101;
		try {
			CmnProjectMstDao lObjCmnProjectMstDao = new CmnProjectMstDaoImpl(CmnProjectMst.class,
					this.getSessionFactory());
			CmnProjectMst lObjCmnProjectMst = lObjCmnProjectMstDao.read(lIntProjId);

			CmnDatabaseMstDaoImpl dbDAO = new CmnDatabaseMstDaoImpl(CmnDatabaseMst.class, this.getSessionFactory());
			CmnDatabaseMst dbId = dbDAO.read(Long.parseLong("99"));

			WfOrgLocMpgMst lObjWfOrgLocMpgMst = new WfOrgLocMpgMst();

			lObjWfOrgLocMpgMst.setLocId(LocCode);
			lObjWfOrgLocMpgMst.setCmnProjectMst(lObjCmnProjectMst);
			hibSession.save(lObjWfOrgLocMpgMst);
			hibSession.flush();
		} catch (Exception e) {
			logger.error(" Error is : " + e, e);
		}
	}

	public void insertWfOrgUser(Long lLngUserId) {
		Session hibSession = getSession();
		Integer lIntProjId = 101;

		try {
			CmnProjectMstDao lObjCmnProjectMstDao = new CmnProjectMstDaoImpl(CmnProjectMst.class,
					this.getSessionFactory());
			CmnProjectMst lObjCmnProjectMst = lObjCmnProjectMstDao.read(lIntProjId);

			CmnDatabaseMstDaoImpl dbDAO = new CmnDatabaseMstDaoImpl(CmnDatabaseMst.class, this.getSessionFactory());
			CmnDatabaseMst dbId = dbDAO.read(Long.parseLong("99"));

			WfOrgUsrMpgMst lObjWfUsrMpgMst = new WfOrgUsrMpgMst();
			lObjWfUsrMpgMst.setUserId(lLngUserId.toString());
			lObjWfUsrMpgMst.setCmnProjectMst(lObjCmnProjectMst);
			lObjWfUsrMpgMst.setCmnDatabaseMst(dbId);

			hibSession.save(lObjWfUsrMpgMst);
			hibSession.flush();
		} catch (Exception e) {
			logger.error(" Error is : " + e, e);
		}
	}

	public void insertWorkFlow(Long lLngPostIdAsst, Long lLongDDOPostId, Long lLongTOAsstPostId, Long lLongTOPostId,
			Long lLongCreatedByUserId, String lStrLocCode, Map inputMap) throws Exception {

		Long lLongHierarchyRefId = null;
		Query lQuery = null;
		StringBuilder lSBQuery = null;

		String lStrDocNameArr[] = { gObjRsrcBndle.getString("DCPS.RegistrationForm").trim(),
				gObjRsrcBndle.getString("DCPS.ChangesForm").trim(),
				gObjRsrcBndle.getString("DCPS.SixthPCArrears").trim(),
				gObjRsrcBndle.getString("DCPS.OnlineContribution").trim() };

		String lStrDocIdArr[] = { gObjRsrcBndle.getString("DCPS.RegistrationFormID").trim(),
				gObjRsrcBndle.getString("DCPS.ChangesFormID").trim(),
				gObjRsrcBndle.getString("DCPS.SixthPCArrearsID").trim(),
				gObjRsrcBndle.getString("DCPS.OnlineContributionID").trim() };

		try {

			for (Integer lInt = 0; lInt < 4; lInt++) {
				lLongHierarchyRefId = IFMSCommonServiceImpl.getNextSeqNum("wf_hierarchy_reference_mst", inputMap);

				lSBQuery = new StringBuilder();
				lSBQuery.append("INSERT INTO WF_HIERARCHY_REFERENCE_MST VALUES \n");
				lSBQuery.append(
						"(:hierachyRefId,:refName,:description,:docId,:crtUser,:createdDate,:lstUpdUser,:lstUpdDate,:startDate,:endDate,:activeFlag,:parentId,:dbId,:locCode,:langId,:parentHierarchyRefId,:branchcode,:hierarchySeqId) \n");
				lQuery = ghibSession.createSQLQuery(lSBQuery.toString());

				lQuery.setParameter("hierachyRefId", lLongHierarchyRefId);
				lQuery.setParameter("refName", lStrDocNameArr[lInt]);
				lQuery.setParameter("description", lStrDocNameArr[lInt]);
				lQuery.setParameter("docId", Long.valueOf(lStrDocIdArr[lInt]));
				lQuery.setParameter("crtUser", "1");
				lQuery.setParameter("createdDate", DBUtility.getCurrentDateFromDB());
				lQuery.setParameter("lstUpdUser", null);
				lQuery.setParameter("lstUpdDate", null);
				lQuery.setParameter("startDate", DBUtility.getCurrentDateFromDB());
				lQuery.setParameter("endDate", null);
				lQuery.setParameter("activeFlag", 1);
				lQuery.setParameter("parentId", null);
				lQuery.setParameter("dbId", 99);
				lQuery.setParameter("locCode", lStrLocCode);
				lQuery.setParameter("langId", "1");
				lQuery.setParameter("parentHierarchyRefId", null);
				lQuery.setParameter("branchcode", null);
				lQuery.setParameter("hierarchySeqId", lLongHierarchyRefId);
				lQuery.executeUpdate();

				if (lStrDocIdArr[lInt].equals("700001")) {
					insertWfHierachyPostMpgNewRegForm(lLongHierarchyRefId, lLngPostIdAsst, lLongDDOPostId,
							lLongTOAsstPostId, lLongTOPostId, lStrLocCode, inputMap);
				}
				if (lStrDocIdArr[lInt].equals("700005")) {
					insertWfHierachyPostMpgChanges(lLongHierarchyRefId, lLngPostIdAsst, lLongDDOPostId, lStrLocCode,
							inputMap);
				}
				if (lStrDocIdArr[lInt].equals("700002")) {
					insertWfHierachyPostMpgSixPCArrears(lLongHierarchyRefId, lLngPostIdAsst, lLongDDOPostId,
							lLongTOPostId, lStrLocCode, inputMap);
				}
				if (lStrDocIdArr[lInt].equals("700006")) {
					insertWfHierachyPostMpgOnlineContri(lLongHierarchyRefId, lLngPostIdAsst, lLongDDOPostId,
							lLongTOPostId, lStrLocCode, inputMap);
				}
			}

			insertWfHierarchyTableSeqMst(lStrLocCode, inputMap);

		} catch (Exception e) {
			logger.error("Error is : " + e, e);
			throw (e);
		}
	}

	public void insertWfHierachyPostMpgNewRegForm(Long lLongHierarchyRefId, Long lLngPostIdAsst, Long lLongDDOPostId,
			Long lLongTOAsstPostId, Long lLongTOPostId, String lStrLocCode, Map inputMap) throws Exception {

		StringBuilder lSBQueryInner = null;
		Query lQueryInner = null;
		Long lLongHierarchySeqId = null;

		Integer[] lArrIntLevelId = { 10, 20, 25, 30 };
		Long lLongArrPostId[] = { lLngPostIdAsst, lLongDDOPostId, lLongTOAsstPostId, lLongTOPostId };

		try {

			for (Integer lInt = 0; lInt < 4; lInt++) {
				lLongHierarchySeqId = IFMSCommonServiceImpl.getNextSeqNum("wf_hierachy_post_mpg", inputMap);

				lSBQueryInner = new StringBuilder();

				lSBQueryInner.append("INSERT INTO WF_HIERACHY_POST_MPG VALUES \n");
				lSBQueryInner.append(
						"(:hierachySeqId,:parentHierachy,:postId,:levelId,:hierachyRefId,:crtUser,:createdDate,:lstUpdUser,:lstUpdDate,:startDate,:endDate,:activeFlag,:locId,:langId,:dueDays) \n");

				lQueryInner = ghibSession.createSQLQuery(lSBQueryInner.toString());
				lQueryInner.setParameter("hierachySeqId", lLongHierarchySeqId);
				lQueryInner.setParameter("parentHierachy", null);
				lQueryInner.setParameter("postId", lLongArrPostId[lInt]);
				lQueryInner.setParameter("levelId", lArrIntLevelId[lInt]);
				lQueryInner.setParameter("hierachyRefId", lLongHierarchyRefId);
				lQueryInner.setParameter("crtUser", "1");
				lQueryInner.setParameter("createdDate", DBUtility.getCurrentDateFromDB());
				lQueryInner.setParameter("lstUpdUser", null);
				lQueryInner.setParameter("lstUpdDate", null);
				lQueryInner.setParameter("startDate", DBUtility.getCurrentDateFromDB());
				lQueryInner.setParameter("endDate", null);
				lQueryInner.setParameter("activeFlag", 1);
				lQueryInner.setParameter("locId", lStrLocCode);
				lQueryInner.setParameter("langId", 1);
				lQueryInner.setParameter("dueDays", null);

				lQueryInner.executeUpdate();
			}

		} catch (Exception e) {
			logger.error("Error is : " + e, e);
			throw (e);
		}
	}

	public void insertWfHierachyPostMpgChanges(Long lLongHierarchyRefId, Long lLngPostIdAsst, Long lLongDDOPostId,
			String lStrLocCode, Map inputMap) throws Exception {

		StringBuilder lSBQueryInner = null;
		Query lQueryInner = null;
		Long lLongHierarchySeqId = null;

		Integer[] lArrIntLevelId = { 10, 20 };
		Long lLongArrPostId[] = { lLngPostIdAsst, lLongDDOPostId };

		try {

			for (Integer lInt = 0; lInt < 1; lInt++) {
				lLongHierarchySeqId = IFMSCommonServiceImpl.getNextSeqNum("wf_hierachy_post_mpg", inputMap);

				lSBQueryInner = new StringBuilder();

				lSBQueryInner.append("INSERT INTO WF_HIERACHY_POST_MPG VALUES \n");
				lSBQueryInner.append(
						"(:hierachySeqId,:parentHierachy,:postId,:levelId,:hierachyRefId,:crtUser,:createdDate,:lstUpdUser,:lstUpdDate,:startDate,:endDate,:activeFlag,:locId,:langId,:dueDays) \n");

				lQueryInner = ghibSession.createSQLQuery(lSBQueryInner.toString());
				lQueryInner.setParameter("hierachySeqId", lLongHierarchySeqId);
				lQueryInner.setParameter("parentHierachy", null);
				lQueryInner.setParameter("postId", lLongArrPostId[lInt]);
				lQueryInner.setParameter("levelId", lArrIntLevelId[lInt]);
				lQueryInner.setParameter("hierachyRefId", lLongHierarchyRefId);
				lQueryInner.setParameter("crtUser", "1");
				lQueryInner.setParameter("createdDate", DBUtility.getCurrentDateFromDB());
				lQueryInner.setParameter("lstUpdUser", null);
				lQueryInner.setParameter("lstUpdDate", null);
				lQueryInner.setParameter("startDate", DBUtility.getCurrentDateFromDB());
				lQueryInner.setParameter("endDate", null);
				lQueryInner.setParameter("activeFlag", 1);
				lQueryInner.setParameter("locId", lStrLocCode);
				lQueryInner.setParameter("langId", 1);
				lQueryInner.setParameter("dueDays", null);

				lQueryInner.executeUpdate();
			}

		} catch (Exception e) {
			logger.error("Error is : " + e, e);
			throw (e);
		}
	}

	public void insertWfHierachyPostMpgSixPCArrears(Long lLongHierarchyRefId, Long lLngPostIdAsst, Long lLongDDOPostId,
			Long lLongTOPostId, String lStrLocCode, Map inputMap) throws Exception {

		StringBuilder lSBQueryInner = null;
		Query lQueryInner = null;
		Long lLongHierarchySeqId = null;

		Integer[] lArrIntLevelId = { 10, 20, 30 };
		Long lLongArrPostId[] = { lLngPostIdAsst, lLongDDOPostId, lLongTOPostId };

		try {

			for (Integer lInt = 0; lInt < 2; lInt++) {
				lLongHierarchySeqId = IFMSCommonServiceImpl.getNextSeqNum("wf_hierachy_post_mpg", inputMap);

				lSBQueryInner = new StringBuilder();

				lSBQueryInner.append("INSERT INTO WF_HIERACHY_POST_MPG VALUES \n");
				lSBQueryInner.append(
						"(:hierachySeqId,:parentHierachy,:postId,:levelId,:hierachyRefId,:crtUser,:createdDate,:lstUpdUser,:lstUpdDate,:startDate,:endDate,:activeFlag,:locId,:langId,:dueDays) \n");

				lQueryInner = ghibSession.createSQLQuery(lSBQueryInner.toString());
				lQueryInner.setParameter("hierachySeqId", lLongHierarchySeqId);
				lQueryInner.setParameter("parentHierachy", null);
				lQueryInner.setParameter("postId", lLongArrPostId[lInt]);
				lQueryInner.setParameter("levelId", lArrIntLevelId[lInt]);
				lQueryInner.setParameter("hierachyRefId", lLongHierarchyRefId);
				lQueryInner.setParameter("crtUser", "1");
				lQueryInner.setParameter("createdDate", DBUtility.getCurrentDateFromDB());
				lQueryInner.setParameter("lstUpdUser", null);
				lQueryInner.setParameter("lstUpdDate", null);
				lQueryInner.setParameter("startDate", DBUtility.getCurrentDateFromDB());
				lQueryInner.setParameter("endDate", null);
				lQueryInner.setParameter("activeFlag", 1);
				lQueryInner.setParameter("locId", lStrLocCode);
				lQueryInner.setParameter("langId", 1);
				lQueryInner.setParameter("dueDays", null);

				lQueryInner.executeUpdate();
			}

		} catch (Exception e) {
			logger.error("Error is : " + e, e);
			throw (e);
		}
	}

	public void insertWfHierachyPostMpgOnlineContri(Long lLongHierarchyRefId, Long lLngPostIdAsst, Long lLongDDOPostId,
			Long lLongTOPostId, String lStrLocCode, Map inputMap) throws Exception {

		StringBuilder lSBQueryInner = null;
		Query lQueryInner = null;
		Long lLongHierarchySeqId = null;

		Integer[] lArrIntLevelId = { 10, 20, 30 };
		Long lLongArrPostId[] = { lLngPostIdAsst, lLongDDOPostId, lLongTOPostId };

		try {

			for (Integer lInt = 0; lInt < 2; lInt++) {
				lLongHierarchySeqId = IFMSCommonServiceImpl.getNextSeqNum("wf_hierachy_post_mpg", inputMap);

				lSBQueryInner = new StringBuilder();

				lSBQueryInner.append("INSERT INTO WF_HIERACHY_POST_MPG VALUES \n");
				lSBQueryInner.append(
						"(:hierachySeqId,:parentHierachy,:postId,:levelId,:hierachyRefId,:crtUser,:createdDate,:lstUpdUser,:lstUpdDate,:startDate,:endDate,:activeFlag,:locId,:langId,:dueDays) \n");

				lQueryInner = ghibSession.createSQLQuery(lSBQueryInner.toString());
				lQueryInner.setParameter("hierachySeqId", lLongHierarchySeqId);
				lQueryInner.setParameter("parentHierachy", null);
				lQueryInner.setParameter("postId", lLongArrPostId[lInt]);
				lQueryInner.setParameter("levelId", lArrIntLevelId[lInt]);
				lQueryInner.setParameter("hierachyRefId", lLongHierarchyRefId);
				lQueryInner.setParameter("crtUser", "1");
				lQueryInner.setParameter("createdDate", DBUtility.getCurrentDateFromDB());
				lQueryInner.setParameter("lstUpdUser", null);
				lQueryInner.setParameter("lstUpdDate", null);
				lQueryInner.setParameter("startDate", DBUtility.getCurrentDateFromDB());
				lQueryInner.setParameter("endDate", null);
				lQueryInner.setParameter("activeFlag", 1);
				lQueryInner.setParameter("locId", lStrLocCode);
				lQueryInner.setParameter("langId", 1);
				lQueryInner.setParameter("dueDays", null);

				lQueryInner.executeUpdate();
			}

		} catch (Exception e) {
			logger.error("Error is : " + e, e);
			throw (e);
		}
	}

	public Long getPostIdOfTOAsstForTreasuryCode(String lStrTreasuryCode) {

		StringBuilder lSBQuery = new StringBuilder();
		List tempList = new ArrayList();
		Long lLongTOAsstPostId = null;

		lSBQuery.append(
				" SELECT post_id from org_post_mst where post_id in (select post_id from ORG_USERPOST_RLT where user_id in (select user_id from org_user_mst where trim(user_name)='"
						+ lStrTreasuryCode.trim() + "_TO_ASST' ))");

		Query lQuery = ghibSession.createSQLQuery(lSBQuery.toString());

		tempList = lQuery.list();
		if (tempList != null && tempList.size() != 0) {
			if (tempList.get(0) != null) {
				if (!"".equals(tempList.get(0).toString())) {
					lLongTOAsstPostId = Long.valueOf(tempList.get(0).toString());
				}
			}

		}
		return lLongTOAsstPostId;

	}

	public Long getPostIdOfTOForTreasuryCode(String lStrTreasuryCode) {

		StringBuilder lSBQuery = new StringBuilder();
		List tempList = new ArrayList();
		Long lLongTOPostId = null;

		lSBQuery.append(
				" SELECT post_id from org_post_mst where post_id in (select post_id from ORG_USERPOST_RLT where user_id in (select user_id from org_user_mst where trim(user_name)='"
						+ lStrTreasuryCode.trim() + "_TO' ))");

		Query lQuery = ghibSession.createSQLQuery(lSBQuery.toString());

		tempList = lQuery.list();
		if (tempList != null && tempList.size() != 0) {
			if (tempList.get(0) != null) {
				if (!"".equals(tempList.get(0).toString())) {
					lLongTOPostId = Long.valueOf(tempList.get(0).toString());
				}
			}

		}
		return lLongTOPostId;

	}

	public void insertWfHierarchyTableSeqMst(String lStrLocCode, Map inputMap) throws Exception {
		StringBuilder lSBQueryInner = null;
		Query lQueryInner = null;
		Long lLongTableSeqMstId = null;

		String[] lArrStrTableName = { "wf_job_mst", "wf_job_mvmnt_mst", "wf_audit_trail_mst", "wf_marked_hierachy_mst",
				"wf_notification", "wf_job_attribute", "wf_job_grp_mst", "wf_table_seq_mst", "wf_doc_mvmnt_mst" };
		Long[] lArrLongPrivMaxId = { 1l, 1l, 1l, 1l, 54004l, 909l, 927196l, 89l, 1l };

		try {

			for (Integer lInt = 0; lInt < 9; lInt++) {
				lLongTableSeqMstId = IFMSCommonServiceImpl.getNextSeqNum("wf_table_seq_mst", inputMap);

				lSBQueryInner = new StringBuilder();

				lSBQueryInner.append("INSERT INTO WF_TABLE_SEQ_MST VALUES \n");
				lSBQueryInner.append(
						"(:seqMstId,:tableName,:privMaxId,:crtdUser,:crtdPost,:createdDate,:lstUpdUser,:lstUpdPost,:lstUpdDate,:dbId,:locId,:pkLength) \n");

				lQueryInner = ghibSession.createSQLQuery(lSBQueryInner.toString());
				lQueryInner.setParameter("seqMstId", lLongTableSeqMstId);
				lQueryInner.setParameter("tableName", lArrStrTableName[lInt]);
				lQueryInner.setParameter("privMaxId", lArrLongPrivMaxId[lInt]);
				lQueryInner.setParameter("crtdUser", "1");
				lQueryInner.setParameter("crtdPost", "1");
				lQueryInner.setParameter("createdDate", DBUtility.getCurrentDateFromDB());
				lQueryInner.setParameter("lstUpdUser", null);
				lQueryInner.setParameter("lstUpdPost", null);
				lQueryInner.setParameter("lstUpdDate", null);
				lQueryInner.setParameter("dbId", 99);
				lQueryInner.setParameter("locId", lStrLocCode);
				lQueryInner.setParameter("pkLength", 20);

				lQueryInner.executeUpdate();
			}

		} catch (Exception e) {
			logger.error("Error is : " + e, e);
			throw e;
		}
	}

	public void insertUserMstAsst(Long lLngUserId, String lStrDdoCodeAsst, Long lLngUserIdCrtd, Long lLngPostIdCrtd,
			Map inputMap) throws Exception {
		try {
			CmnLookupMstDAO lObjCmnLookUpMstDao = new CmnLookupMstDAOImpl(CmnLookupMst.class, this.getSessionFactory());
			CmnLookupMst lObjCmnLookupMst = lObjCmnLookUpMstDao.read(1l);

			OrgUserMstDao lObjOrgUserMstDao = new OrgUserMstDaoImpl(OrgUserMst.class, this.getSessionFactory());
			OrgUserMst lObjOrgUserMstCrtdUsr = lObjOrgUserMstDao.read(lLngUserIdCrtd);

			OrgPostMstDaoImpl orgPostId = new OrgPostMstDaoImpl(OrgPostMst.class, this.getSessionFactory());
			OrgPostMst postId = orgPostId.read(lLngPostIdCrtd);

			OrgUserMst lObjUserMst = new OrgUserMst();
			// lLngUserId = IFMSCommonServiceImpl.getNextSeqNum("org_user_mst", inputMap);
			lObjUserMst.setUserId(lLngUserId);
			lObjUserMst.setUserName(lStrDdoCodeAsst);
			lObjUserMst.setPassword("0b76f0f411f6944f9d192da0fcbfb292");
			lObjUserMst.setCmnLookupMst(lObjCmnLookupMst);
			lObjUserMst.setStartDate(DBUtility.getCurrentDateFromDB());
			lObjUserMst.setActivateFlag(0l);
			lObjUserMst.setCreatedDate(DBUtility.getCurrentDateFromDB());
			lObjUserMst.setOrgUserMstByCreatedBy(lObjOrgUserMstCrtdUsr);
			lObjUserMst.setOrgPostMstByCreatedByPost(postId);
			lObjUserMst.setSecretQueCode("Secret_Other");
			lObjUserMst.setSecretQueOther("Secret_Other");
			lObjUserMst.setSecretAnswer("ifms");
			ghibSession.save(lObjUserMst);
			ghibSession.flush();
		} catch (Exception e) {
			logger.error(" Error is : " + e, e);
			throw e;
		}
	}

	// added by vaibhav tyagi: start
	public synchronized Long getNextSeqNoLoc() {

		Long seqId = 0l;
		Long nextSeqId = 0l;
		StringBuilder lSBQuery = new StringBuilder();
		lSBQuery.append(
				"select GENERATED_ID from  CMN_TABLE_SEQ_MST where LOCATION_CODE='10000' and upper(TABLE_NAME)= 'CMN_LOCATION_MST'");
		Query lQuery = ghibSession.createSQLQuery(lSBQuery.toString());
		seqId = Long.valueOf(lQuery.uniqueResult().toString());
		nextSeqId = seqId + 1;
		StringBuilder sb = new StringBuilder();
		sb.append("UPDATE CMN_TABLE_SEQ_MST SET GENERATED_ID=" + nextSeqId
				+ " where LOCATION_CODE='10000' and upper(TABLE_NAME)= 'CMN_LOCATION_MST'");
		Query query = ghibSession.createSQLQuery(sb.toString());
		query.executeUpdate();
		ghibSession.flush();
		return seqId;
	}
	// added by vaibhav tyagi: end

	public synchronized Long getNextSeqNoLocForUserMst() {

		Long seqId = 0l;
		Long nextSeqId = 0l;
		StringBuilder lSBQuery = new StringBuilder();
		lSBQuery.append(
				"select GENERATED_ID+5 from  CMN_TABLE_SEQ_MST where LOCATION_CODE='10000' and upper(TABLE_NAME)= 'ORG_USER_MST'");
		Query lQuery = ghibSession.createSQLQuery(lSBQuery.toString());
		seqId = Long.valueOf(lQuery.uniqueResult().toString());
		nextSeqId = seqId + 5;
		StringBuilder sb = new StringBuilder();
		sb.append("UPDATE CMN_TABLE_SEQ_MST SET GENERATED_ID=" + nextSeqId
				+ " where LOCATION_CODE='10000' and upper(TABLE_NAME)= 'ORG_USER_MST'");
		Query query = ghibSession.createSQLQuery(sb.toString());
		query.executeUpdate();
		ghibSession.flush();
		return seqId;
	}

	public synchronized Long getNextSeqNoLocForEmpMst() {

		Long seqId = 0l;
		Long nextSeqId = 0l;
		StringBuilder lSBQuery = new StringBuilder();
		lSBQuery.append(
				"select GENERATED_ID+5 from  CMN_TABLE_SEQ_MST where LOCATION_CODE='10000' and upper(TABLE_NAME)= 'ORG_EMP_MST'");
		Query lQuery = ghibSession.createSQLQuery(lSBQuery.toString());
		seqId = Long.valueOf(lQuery.uniqueResult().toString());
		nextSeqId = seqId + 6;
		StringBuilder sb = new StringBuilder();
		sb.append("UPDATE CMN_TABLE_SEQ_MST SET GENERATED_ID=" + nextSeqId
				+ " where LOCATION_CODE='10000' and upper(TABLE_NAME)= 'ORG_EMP_MST'");
		Query query = ghibSession.createSQLQuery(sb.toString());
		query.executeUpdate();
		ghibSession.flush();
		return seqId;
	}

	public synchronized Long getNextSeqNoLocForPostDtlsRlt() {

		Long seqId = 0l;
		Long nextSeqId = 0l;
		StringBuilder lSBQuery = new StringBuilder();
		lSBQuery.append(
				"select GENERATED_ID+5 from  CMN_TABLE_SEQ_MST where LOCATION_CODE='10000' and upper(TABLE_NAME)= 'ORG_POST_DETAILS_RLT'");
		Query lQuery = ghibSession.createSQLQuery(lSBQuery.toString());
		seqId = Long.valueOf(lQuery.uniqueResult().toString());
		nextSeqId = seqId + 6;
		StringBuilder sb = new StringBuilder();
		sb.append("UPDATE CMN_TABLE_SEQ_MST SET GENERATED_ID=" + nextSeqId
				+ " where LOCATION_CODE='10000' and upper(TABLE_NAME)= 'ORG_POST_DETAILS_RLT'");
		Query query = ghibSession.createSQLQuery(sb.toString());
		query.executeUpdate();
		ghibSession.flush();
		return seqId;
	}

	public synchronized Long getNextSeqNoLocForOrgUserpostRlt() {

		Long seqId = 0l;
		Long nextSeqId = 0l;
		StringBuilder lSBQuery = new StringBuilder();
		lSBQuery.append(
				"select GENERATED_ID+5 from  CMN_TABLE_SEQ_MST where LOCATION_CODE='10000' and upper(TABLE_NAME)= 'ORG_USERPOST_RLT'");
		Query lQuery = ghibSession.createSQLQuery(lSBQuery.toString());
		seqId = Long.valueOf(lQuery.uniqueResult().toString());
		nextSeqId = seqId + 6;
		StringBuilder sb = new StringBuilder();
		sb.append("UPDATE CMN_TABLE_SEQ_MST SET GENERATED_ID=" + nextSeqId
				+ " where LOCATION_CODE='10000' and upper(TABLE_NAME)= 'ORG_USERPOST_RLT'");
		Query query = ghibSession.createSQLQuery(sb.toString());
		query.executeUpdate();
		ghibSession.flush();
		return seqId;
	}

	public synchronized Long getNextSeqNoLocForDdoOffice() {

		Long seqId = 0l;
		Long nextSeqId = 0l;
		StringBuilder lSBQuery = new StringBuilder();
		lSBQuery.append(
				"select GENERATED_ID from  CMN_TABLE_SEQ_MST where LOCATION_CODE='10000' and upper(TABLE_NAME)= 'MST_DCPS_DDO_OFFICE'");
		Query lQuery = ghibSession.createSQLQuery(lSBQuery.toString());
		seqId = Long.valueOf(lQuery.uniqueResult().toString());
		nextSeqId = seqId + 1;
		StringBuilder sb = new StringBuilder();
		sb.append("UPDATE CMN_TABLE_SEQ_MST SET GENERATED_ID=" + nextSeqId
				+ " where LOCATION_CODE='10000' and upper(TABLE_NAME)= 'MST_DCPS_DDO_OFFICE'");
		Query query = ghibSession.createSQLQuery(sb.toString());
		query.executeUpdate();
		ghibSession.flush();
		return seqId;
	}

	public synchronized Long getNextSeqNoLocForRltDdoOrg() {

		Long seqId = 0l;
		Long nextSeqId = 0l;
		StringBuilder lSBQuery = new StringBuilder();
		lSBQuery.append(
				"select GENERATED_ID from  CMN_TABLE_SEQ_MST where LOCATION_CODE='10000' and upper(TABLE_NAME)= 'RLT_DDO_ORG'");
		Query lQuery = ghibSession.createSQLQuery(lSBQuery.toString());
		seqId = Long.valueOf(lQuery.uniqueResult().toString());
		nextSeqId = seqId + 1;
		StringBuilder sb = new StringBuilder();
		sb.append("UPDATE CMN_TABLE_SEQ_MST SET GENERATED_ID=" + nextSeqId
				+ " where LOCATION_CODE='10000' and upper(TABLE_NAME)= 'RLT_DDO_ORG'");
		Query query = ghibSession.createSQLQuery(sb.toString());
		query.executeUpdate();
		ghibSession.flush();
		return seqId;
	}

	public synchronized Long getNextSeqNoLocForRltDcpsDdoAsst() {

		Long seqId = 0l;
		Long nextSeqId = 0l;
		StringBuilder lSBQuery = new StringBuilder();
		lSBQuery.append(
				"select GENERATED_ID from  CMN_TABLE_SEQ_MST where LOCATION_CODE='10000' and upper(TABLE_NAME)= 'RLT_DCPS_DDO_ASST'");
		Query lQuery = ghibSession.createSQLQuery(lSBQuery.toString());
		seqId = Long.valueOf(lQuery.uniqueResult().toString());
		nextSeqId = seqId + 1;
		StringBuilder sb = new StringBuilder();
		sb.append("UPDATE CMN_TABLE_SEQ_MST SET GENERATED_ID=" + nextSeqId
				+ " where LOCATION_CODE='10000' and upper(TABLE_NAME)= 'RLT_DCPS_DDO_ASST'");
		Query query = ghibSession.createSQLQuery(sb.toString());
		query.executeUpdate();
		ghibSession.flush();
		return seqId;
	}
}
