package com.mahait.gov.in.repository;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import com.mahait.gov.in.entity.CmnDatabaseMst;
import com.mahait.gov.in.entity.CmnLanguageMst;
import com.mahait.gov.in.entity.CmnLocationMst;
import com.mahait.gov.in.entity.CmnLookupMst;
import com.mahait.gov.in.entity.DdoOffice;
import com.mahait.gov.in.entity.MstDesignationEntity;
import com.mahait.gov.in.entity.MstRoleEntity;
import com.mahait.gov.in.entity.OrgDdoMst;
import com.mahait.gov.in.entity.OrgEmpMst;
import com.mahait.gov.in.entity.OrgGradeMst;
import com.mahait.gov.in.entity.OrgPostDetailsRlt;
import com.mahait.gov.in.entity.OrgPostMst;
import com.mahait.gov.in.entity.OrgUserMst;
import com.mahait.gov.in.entity.OrgUserpostRlt;
import com.mahait.gov.in.entity.RltDdoOrg;
import com.mahait.gov.in.entity.ZpRltDdoMap;

@Repository
public class AddNewDDOConfigRepository {
	
	@PersistenceContext
	EntityManager entityManager;
	
	@Autowired
	CmnLanguageMstRepository cmnLanguageMstRepository;
	
	@Autowired
	CmnLookupMstRepository cmnLookupMstRepository;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	
	@Autowired
	CmnLocationMstRepository cmnLocationMstRepository;
	
	@Autowired
	OrgGradeMstRepository  orgGradeMstRepository;
	
	@Autowired
	UserInfoRepoImpl userInfoRepoImpl;
	
	
	@Autowired
	OrgPostMstRepository orgPostMstRepository;
	
	

	@Autowired
	MstDesignationRepository orgDesignationMstRepository;
	
	
	
	
	

	@Autowired
	AclRoleMstRepository aclRoleMstRepository;
	
	
	@Autowired
	ZpRltDdoMapRepository zpRltDdoMapRepository;
	
	
	

	@Autowired
	MstRoleRepo mstRoleRepo;

	private Serializable save;
	
	


	public List getAllAdminDepartment(){
		Session ghibSession = entityManager.unwrap(Session.class);         
			StringBuilder lSBQuery = new StringBuilder();
			lSBQuery.append("SELECT locId, locName FROM CmnLocationMst \n");
			lSBQuery.append("WHERE departmentId = 100001");
			Query lQuery = ghibSession.createQuery(lSBQuery.toString());
			return  lQuery.list();
	}

	public List getAllFieldDepartment() {
		Session hibSession = entityManager.unwrap(Session.class);      
			StringBuilder lSBQuery = new StringBuilder();
			lSBQuery.append("SELECT locId, locName FROM CmnLocationMst \n");
			lSBQuery.append("WHERE departmentId = 100011");
			Query lQuery = hibSession.createQuery(lSBQuery.toString());
			return lQuery.list();
		
	}

	public List getAllTreasury() {
		Session ghibSession = entityManager.unwrap(Session.class);     
			StringBuilder lSBQuery = new StringBuilder();
			lSBQuery.append("SELECT locId, locName FROM CmnLocationMst \n");
			lSBQuery.append("WHERE departmentId in (100003,100006) ORDER BY locName ");
			Query lQuery = ghibSession.createQuery(lSBQuery.toString());
			return lQuery.list();

	}

	public String chkDdoCode(String lStrDdoCode)  {
		Session hibSession = entityManager.unwrap(Session.class);      
		String lStrResData = "";
		List lLstResData = null;

			StringBuilder lSBQuery = new StringBuilder();
			lSBQuery.append("FROM OrgDdoMst WHERE ddoCode=:ddoCode \n");
			Query lQuery = hibSession.createQuery(lSBQuery.toString());
			lQuery.setParameter("ddoCode", lStrDdoCode);
			lLstResData = lQuery.list();

			if (lLstResData.size() > 0) {
				lStrResData = "Y";
			} else {
				lStrResData = "N";
			}
		
		return lStrResData;
	}

	
	public CmnLocationMst insertLocation(String lStrLocationName, Long lLngUserIdCrtd, Long lLngPostIdCrtd,
			Long lLngFieldDeptId, String lStrLocPin, OrgUserMst orgUserMst, String strDistCode)  {
		Long lLngLocId = null;

		Session ghibSession = entityManager.unwrap(Session.class);     
			CmnLocationMst lObjCmnLocationMst = new CmnLocationMst();

			CmnLanguageMst lObjCmnLanguageMst = cmnLanguageMstRepository.findByLangId(1l);

			CmnLookupMst lObjCmnLookupMst =cmnLookupMstRepository.findByLookupId(1l);
		
			lLngLocId = getNextSeqNoLoc();
		
			lObjCmnLocationMst.setLocId(lLngLocId);

			lObjCmnLocationMst.setLocName(lStrLocationName);

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
			lObjCmnLocationMst.setCreatedBy(orgUserMst);
			lObjCmnLocationMst.setCreatedByPost(orgUserMst.getCreatedByPost());
			lObjCmnLocationMst.setCreatedDate(new Date());
			lObjCmnLocationMst.setLocationCode(lLngLocId.toString());
			Long distCode = (strDistCode != null && Long.parseLong(strDistCode) > 0) ? Long.parseLong(strDistCode) : -1;
			lObjCmnLocationMst.setLocDistrictId(distCode);
			lObjCmnLocationMst.setLocStateId(15L);
			 Long id =(Long) ghibSession.save(lObjCmnLocationMst);
			
			
			
			 CmnLocationMst save = ghibSession.get(CmnLocationMst.class, id);
			
			return save;
		//	ghibSession.flush();
	//	return lLngLocId.toString();
	}

	public OrgUserMst insertUserMst(String lStrDdoCode, Long lLngUserIdCrtd, Long lLngPostIdCrtd, OrgUserMst orgUserMst)
		{
		Session ghibSession = entityManager.unwrap(Session.class);     
		String ddoc = lStrDdoCode;
		Long lLngUserId = null;
			CmnLocationMst lObjCmnLocationMst = new CmnLocationMst();
			CmnLanguageMst lObjCmnLanguageMst = cmnLanguageMstRepository.findByLangId(1l);
			CmnLookupMst lObjCmnLookupMst =cmnLookupMstRepository.findByLookupId(1l);
			
			
			
			OrgUserMst lObjUserMst = new OrgUserMst();
		//	lLngUserId = 0l;//getNextSeqNoLocForUserMst();
		//	lObjUserMst.setUserId(lLngUserId);

			lObjUserMst.setUserName(ddoc);

			lObjUserMst.setPassword(passwordEncoder.encode("ifms123"));

			Optional<MstRoleEntity> findById = mstRoleRepo.findById(3);
			lObjUserMst.setMstRoleEntity(findById.get());
			
			lObjUserMst.setCmnLookupMst(lObjCmnLookupMst);

			lObjUserMst.setStartDate(new Date());
			
			lObjUserMst.setActivateFlag(0l);
			lObjUserMst.setAppCode(1);
			lObjUserMst.setCreatedDate(new Date());

			lObjUserMst.setCreatedBy(orgUserMst);

			lObjUserMst.setCreatedByPost(orgUserMst.getCreatedByPost());

			lObjUserMst.setSecretQueCode("Secret_Other");
			lObjUserMst.setSecretQueOther("Secret_Other");// TODO -- Needs to Change
			lObjUserMst.setSecretAnswer("ifms");
			//Serializable save = ghibSession.save(lObjUserMst);
			
			
             Long id =(Long) ghibSession.save(lObjUserMst);
			
			
             OrgUserMst save = ghibSession.get(OrgUserMst.class, id);
			
			return save; 
			
			
		//	ghibSession.flush();
		

		//return (Long) save;
	}

	public void insertEmpMst(Long lLngUserId, String lStrFname, Long lLngUserIdCrtd, Long lLngPostIdCrtd,
			String lStrGendr, OrgUserMst orgUserMst) {
		Session ghibSession = entityManager.unwrap(Session.class);     
		Long lLngEmpId = null;
			SimpleDateFormat lObjDateFormate = new SimpleDateFormat("dd/MM/yyyy");
			Date lObjEmpDob = new Date("01/01/9999");

			OrgGradeMst lObjOrgGradeMst =orgGradeMstRepository.findByGradeId(100064l); 
			CmnLanguageMst lObjCmnLanguageMst = cmnLanguageMstRepository.findByLangId(1l);

			OrgUserMst lObjOrgUserMst = orgUserMst;
			OrgUserMst lObjOrgUserMstCrtd =userInfoRepoImpl.getUserByUserId(lLngUserId);

			OrgPostMst postId =orgUserMst.getCreatedByPost();

			OrgEmpMst lObjEmpMst = new OrgEmpMst();
			// lLngEmpId = IFMSCommonServiceImpl.getNextSeqNum("org_emp_mst", inputMap);
			//lLngEmpId = getNextSeqNoLocForEmpMst();
			//lObjEmpMst.setEmpId(lLngEmpId);

			lObjEmpMst.setEmpFname(lStrFname);

			lObjEmpMst.setEmpLname(" ");
			lObjEmpMst.setEmpMname(" ");
			

			lObjEmpMst.setEmpDob(new Timestamp(lObjEmpDob.getTime()));

			lObjEmpMst.setEmpDoj(new Timestamp(lObjEmpDob.getTime()));
			lObjEmpMst.setEmpSrvcFlag(1l);
			lObjEmpMst.setOrgGradeMst(lObjOrgGradeMst);

			lObjEmpMst.setCmnLanguageMst(lObjCmnLanguageMst);

			lObjEmpMst.setOrgUserMst(lObjOrgUserMst);

			lObjEmpMst.setStartDate(new Timestamp(new Date().getTime()));
			lObjEmpMst.setActivateFlag(1l);
			lObjEmpMst.setCreatedBy(orgUserMst);
			lObjEmpMst.setCreatedByPost(orgUserMst.getCreatedByPost());
			lObjEmpMst.setCreatedDate(new Timestamp(new Date().getTime()));
			if (lStrGendr.equals("M")) {
				lObjEmpMst.setEmpPrefix("Mr");
			} else if (lStrGendr.equals("F")) {
				lObjEmpMst.setEmpPrefix("Ms");
			}

			ghibSession.save(lObjEmpMst);
			ghibSession.flush();
		
	}

	public OrgPostMst insertOrgPostMst(Long lLngPostId, String lStrLocationCode, Long lLngUserIdCrtd, Long lLngPostIdCrtd,
			String lStrDsgnCode, OrgUserMst orgUserMst) {
		Session ghibSession = entityManager.unwrap(Session.class);     
			CmnLookupMst lObjCmnLookupMst = cmnLookupMstRepository.findByLookupId(13l);

			OrgUserMst lObjOrgUserMstCrtdUsr =orgUserMst;

			OrgPostMst postId = orgUserMst.getCreatedByPost();

			OrgPostMst lObjOrgPostMst = new OrgPostMst();
			lObjOrgPostMst.setPostId(lLngPostId);
			lObjOrgPostMst.setParentPostId(-1l);
			lObjOrgPostMst.setPostLevelId(1l);
			//lObjOrgPostMst.setCmnLookupMst(lObjCmnLookupMst);
			lObjOrgPostMst.setCmnLookupMst(lObjCmnLookupMst);
			lObjOrgPostMst.setActivateFlag(1l);
			lObjOrgPostMst.setCreatedBy(lObjOrgUserMstCrtdUsr);
			lObjOrgPostMst.setCreatedByPost(postId);
			lObjOrgPostMst.setCreatedDate(new Timestamp(new Date().getTime()));
			lObjOrgPostMst.setLocationCode(lStrLocationCode);
			lObjOrgPostMst.setDsgnCode(lStrDsgnCode);
			Long id =(Long) ghibSession.save(lObjOrgPostMst);
			
			
			OrgPostMst save = ghibSession.get(OrgPostMst.class, id);
			
			return save; 
		
	}

	public void insertPostDtlsRlt(String lstrLocCode, Long lLngPostId, String lStrDesignName, Long lLngDsgnId,
			Long lLngUserIdCrtd, Long lLngPostIdCrtd, OrgUserMst orgUserMst, OrgPostMst newOrgPostMst, CmnLocationMst cmnLocationMst) {
		Session ghibSession = entityManager.unwrap(Session.class);     
		Long lLngPostDtlsId = null;
			OrgPostMst postId = newOrgPostMst;
			OrgPostMst postIdCrtd = orgUserMst.getCreatedByPost();

			CmnLocationMst lObjCmnLocationMst =cmnLocationMst;// cmnLocationMstRepository.findByLocId(Long.parseLong(lstrLocCode));

			MstDesignationEntity lObjOrgDesigmMst = orgDesignationMstRepository.findByDesginationId(lLngDsgnId);
			CmnLanguageMst lObjCmnLanguageMst = cmnLanguageMstRepository.findByLangId(1l);

			OrgUserMst lObjOrgUserMstCrtd = orgUserMst;

			OrgPostDetailsRlt lObjPostDtldRlt = new OrgPostDetailsRlt();
			
			//lLngPostDtlsId = getNextSeqNoLocForPostDtlsRlt();
			//lObjPostDtldRlt.setPostDetailId(lLngPostDtlsId);
			lObjPostDtldRlt.setOrgPostMst(postId);
			lObjPostDtldRlt.setPostName(lStrDesignName);
			lObjPostDtldRlt.setPostShortName(lStrDesignName);
			lObjPostDtldRlt.setCmnLocationMst(lObjCmnLocationMst);
			lObjPostDtldRlt.setOrgDesignationMst(lObjOrgDesigmMst);
		//	lObjPostDtldRlt.setCmnLookupMst(newOrgPostMst.getCmnLookupMst());
			
			//lObjPostDtldRlt.setCmnLookupMst(postId.getCmnLookupMst());
			lObjPostDtldRlt.setCmnLanguageMst(lObjCmnLanguageMst);
			lObjPostDtldRlt.setCreatedBy(lObjOrgUserMstCrtd);
			lObjPostDtldRlt.setCreatedByPost(postIdCrtd);
			//lObjPostDtldRlt.set
			lObjPostDtldRlt.setCreatedDate(new Timestamp(new Date().getTime()));
			ghibSession.save(lObjPostDtldRlt);
			ghibSession.flush();
		
	}

	public void insertPostRoleRlt(Long lLngPostId, Long lLngUserIdCrtd, Long lLngPostIdCrtd, OrgUserMst orgUserMst,
			String lStrUserType, OrgPostMst newOrgPostMst){
		Session ghibSession = entityManager.unwrap(Session.class);     
		Long lLngPostRoleId = null;
		/*AclRoleMst lObjAclRoleMst = null;
           OrgPostMst postId = newOrgPostMst;//orgPostMstRepository.findByPostId(lLngPostId);
			OrgPostMst postIdCrtd = orgUserMst.getCreatedByPost();


			if (lStrUserType.trim().equals("DDO")) {
				lObjAclRoleMst = (AclRoleMst) aclRoleMstRepository.findByRoleId(700002l);
			} else if (lStrUserType.trim().equals("ASST")) {
				lObjAclRoleMst = (AclRoleMst) aclRoleMstRepository.findByRoleId(700001l);
			}

			CmnLookupMst activeFlag = cmnLookupMstRepository.findByLookupId(1l);

			OrgUserMst lObjOrgUserMstCrtd = orgUserMst;

			AclPostroleRlt lObjAclPostRoleRlt = new AclPostroleRlt();
			//lLngPostRoleId = IFMSCommonServiceImpl.getNextSeqNum("acl_postrole_rlt", inputMap);
			//lObjAclPostRoleRlt.setPostRoleId(lLngPostRoleId);
			lObjAclPostRoleRlt.setOrgPostMst(postId);
			lObjAclPostRoleRlt.setAclRoleMst(lObjAclRoleMst);
			lObjAclPostRoleRlt.setStartDate(new Timestamp(new Date().getTime()));
			lObjAclPostRoleRlt.setCmnLookupMstByActivate(activeFlag);
			lObjAclPostRoleRlt.setOrgPostMstByCreatedByPost(postIdCrtd);
			lObjAclPostRoleRlt.setOrgUserMstByCreatedBy(lObjOrgUserMstCrtd);
			lObjAclPostRoleRlt.setCreatedDate(new Timestamp(new Date().getTime()));
			ghibSession.save(lObjAclPostRoleRlt);
			ghibSession.flush();*/
		
	}

	public void insertUserPostRlt(Long lLngPostId, Long lLngUserId, Long lLngUserIdCrtd, Long lLngPostIdCrtd,
			OrgUserMst orgUserMst, OrgPostMst newOrgPostMst, OrgUserMst orgUserMst2) {
		Long lLngEmpPostId = null;
		Session ghibSession = entityManager.unwrap(Session.class);     
		try {

			OrgPostMst postId = newOrgPostMst;//orgPostMstRepository.findByPostId(lLngPostId);
			OrgPostMst postIdCrtd = orgUserMst.getCreatedByPost();	
			
			
			OrgUserMst lObjOrgUserMstCrtd = orgUserMst;
			OrgUserMst lObjOrgUserMst = orgUserMst2;//userInfoRepoImpl.getUserByUserId(lLngUserId);

			CmnLookupMst lObjCmnLookupMst = cmnLookupMstRepository.findByLookupId(13l);

			OrgUserpostRlt lObjOrgUserpostRlt = new OrgUserpostRlt();
			// lLngEmpPostId = IFMSCommonServiceImpl.getNextSeqNum("ORG_USERPOST_RLT",
			// inputMap);
			//lLngEmpPostId = getNextSeqNoLocForOrgUserpostRlt();
			//lObjOrgUserpostRlt.setEmpPostId(lLngEmpPostId);
			lObjOrgUserpostRlt.setOrgUserMst(lObjOrgUserMst);
			//lObjOrgUserpostRlt.setOrgPostMst(newOrgPostMst);
			lObjOrgUserpostRlt.setStartDate(new Timestamp(new Date().getTime()));
			lObjOrgUserpostRlt.setActivateFlag(1l);
			lObjOrgUserpostRlt.setOrgPostMstByPostId(newOrgPostMst);
			lObjOrgUserpostRlt.setCreatedByPost(postIdCrtd);
			lObjOrgUserpostRlt.setCreatedBy(orgUserMst);
			lObjOrgUserpostRlt.setCreatedDate(new Timestamp(new Date().getTime()));
			lObjOrgUserpostRlt.setCmnLookupUserPostType(lObjCmnLookupMst);
		//	lObjOrgUserpostRlt.setCmnLocationMst(postId.getLookupId());
			ghibSession.save(lObjOrgUserpostRlt);
			ghibSession.flush();
		} catch (Exception e) {
			throw e;
		}
	}

	
	
	
	public void insertOrgDdoMst(String lStrDdoCode, String lStrDdoName, String lStrDdoPrsnlName, Long lLngPostId,
			Long lLngUserIdCrtd, String lStrLocationCode, Long lLngPostIdCrtd, String lstrDeptLocCode, OrgUserMst orgUserMst)
			 {
		Session ghibSession = entityManager.unwrap(Session.class);     
		Long lLndDdoId = null;
		try {
			OrgDdoMst lObjOrgDdoMst = new OrgDdoMst();
			//lLndDdoId = IFMSCommonServiceImpl.getNextSeqNum("org_ddo_mst", inputMap);
			//lObjOrgDdoMst.setDdoId(lLndDdoId);
			lObjOrgDdoMst.setDdoCode(lStrDdoCode);
			lObjOrgDdoMst.setDdoName(lStrDdoName);
			lObjOrgDdoMst.setDdoPersonalName(lStrDdoPrsnlName);
			lObjOrgDdoMst.setPostId(lLngPostId);
			lObjOrgDdoMst.setLangId((short)1);
			lObjOrgDdoMst.setDeptLocCode(lstrDeptLocCode);
			lObjOrgDdoMst.setCreatedBy(lLngUserIdCrtd);
			lObjOrgDdoMst.setCreatedByPost(lLngPostIdCrtd);
			lObjOrgDdoMst.setCreatedDate(new Timestamp(new Date().getTime()));
			lObjOrgDdoMst.setDbId((short)99);
			lObjOrgDdoMst.setLocationCode(lStrLocationCode);
			ghibSession.save(lObjOrgDdoMst);
			ghibSession.flush();
		} catch (Exception e) {
			throw e;
		}
	}

	public void insertMstDcpsDdoOffice(String lStrDdoCode, String lStrDdoOffice, String lStrDistCode, Long lLngLocId,
			Long lLngUserIdCrtd, Long lLngPostIdCrtd, OrgUserMst orgUserMst, String uniqeInstituteId)  {
		Session ghibSession = entityManager.unwrap(Session.class);     
		Long lLngMstOfficeDdoId = null;
			DdoOffice lObjDdoOffice = new DdoOffice();
			//lLngMstOfficeDdoId = IFMSCommonServiceImpl.getNextSeqNum("MST_DCPS_DDO_OFFICE", inputMap);
			// lLngMstOfficeDdoId = getNextSeqNoLocForDdoOffice();
			//logger.info("lLngMstOfficeDdoId******************" + lLngMstOfficeDdoId);
			//logger.info("lStrDdoCode******************" + lStrDdoCode);
			//lObjDdoOffice.setDcpsDdoOfficeIdPk(lLngMstOfficeDdoId);
			///lObjDdoOffice.setDcpsDdoCode(lStrDdoCode);
			lObjDdoOffice.setDcpsDdoOfficeName(lStrDdoOffice);
			lObjDdoOffice.setDcpsDdoOfficeDdoFlag("Yes");
			lObjDdoOffice.setDcpsDdoOfficeState("15");
			lObjDdoOffice.setDcpsDdoOfficeDistrict(lStrDistCode);
			lObjDdoOffice.setLangId(1l);
			lObjDdoOffice.setLocId(lLngLocId);
			lObjDdoOffice.setDbId(99l);
			lObjDdoOffice.setPostId(lLngPostIdCrtd);
			lObjDdoOffice.setUserId(lLngUserIdCrtd);
			lObjDdoOffice.setCreatedDate(new Timestamp(new Date().getTime()));
			lObjDdoOffice.setStatusFlag(0l);
			lObjDdoOffice.setUniqueInstituteNo(uniqeInstituteId);
			ghibSession.save(lObjDdoOffice);
			ghibSession.flush();
	
	}

	public void insertRltDdoOrg(Long lLngUserIdCrtd, Long lLngPostIdCrtd, String lStrDdoCode, String lStrTrsryCode,
			OrgUserMst orgUserMst) throws Exception {
		Session ghibSession = entityManager.unwrap(Session.class);     
		Long lLngDdoOrgId = null;
			RltDdoOrg lObjRltDdoOrg = new RltDdoOrg();
			// lLngDdoOrgId = IFMSCommonServiceImpl.getNextSeqNum("rlt_ddo_org", inputMap);
			lLngDdoOrgId = getNextSeqNoLocForRltDdoOrg();
			lObjRltDdoOrg.setDdoOrgId(lLngDdoOrgId);
			lObjRltDdoOrg.setActivateFlag(1l);
			lObjRltDdoOrg.setCreatedDate(new Timestamp(new Date().getTime()));
			lObjRltDdoOrg.setCreatedPostId(lLngPostIdCrtd);
			lObjRltDdoOrg.setCreatedUserId(lLngUserIdCrtd);
			lObjRltDdoOrg.setDdoCode(lStrDdoCode);
			lObjRltDdoOrg.setLocationCode(lStrTrsryCode);
			lObjRltDdoOrg.setTrnCounter(1);
			ghibSession.save(lObjRltDdoOrg);
			ghibSession.flush();
		
	}

	/*public void insertWfOrgPost(String Pid) {
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
			Long lLongCreatedByUserId, String lStrLocCode, OrgUserMst orgUserMst) throws Exception {

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
			Long lLongTOAsstPostId, Long lLongTOPostId, String lStrLocCode, OrgUserMst orgUserMst) throws Exception {

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
			String lStrLocCode, OrgUserMst orgUserMst) throws Exception {

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
			Long lLongTOPostId, String lStrLocCode, OrgUserMst orgUserMst) throws Exception {

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
			Long lLongTOPostId, String lStrLocCode, OrgUserMst orgUserMst) throws Exception {

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
*/
	public Long getPostIdOfTOAsstForTreasuryCode(String lStrTreasuryCode) {
		Session ghibSession = entityManager.unwrap(Session.class);     
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
		Session ghibSession = entityManager.unwrap(Session.class);     
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

	public void insertWfHierarchyTableSeqMst(String lStrLocCode, OrgUserMst orgUserMst) throws Exception {
		StringBuilder lSBQueryInner = null;
		Query lQueryInner = null;
		Long lLongTableSeqMstId = null;
		Session ghibSession = entityManager.unwrap(Session.class);     
		String[] lArrStrTableName = { "wf_job_mst", "wf_job_mvmnt_mst", "wf_audit_trail_mst", "wf_marked_hierachy_mst",
				"wf_notification", "wf_job_attribute", "wf_job_grp_mst", "wf_table_seq_mst", "wf_doc_mvmnt_mst" };
		Long[] lArrLongPrivMaxId = { 1l, 1l, 1l, 1l, 54004l, 909l, 927196l, 89l, 1l };

		try {

			for (Integer lInt = 0; lInt < 9; lInt++) {
				lLongTableSeqMstId = 00000l;//IFMSCommonServiceImpl.getNextSeqNum("wf_table_seq_mst", inputMap);

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
				lQueryInner.setParameter("createdDate", new Timestamp(new Date().getTime()));
				lQueryInner.setParameter("lstUpdUser", null);
				lQueryInner.setParameter("lstUpdPost", null);
				lQueryInner.setParameter("lstUpdDate", null);
				lQueryInner.setParameter("dbId", 99);
				lQueryInner.setParameter("locId", lStrLocCode);
				lQueryInner.setParameter("pkLength", 20);

				lQueryInner.executeUpdate();
			}

		} catch (Exception e) {
			throw e;
		}
	}

	public void insertUserMstAsst(Long lLngUserId, String lStrDdoCodeAsst, Long lLngUserIdCrtd, Long lLngPostIdCrtd,
			OrgUserMst orgUserMst) throws Exception {
		try {
			Session ghibSession = entityManager.unwrap(Session.class);     
			CmnLookupMst lObjCmnLookupMst = cmnLookupMstRepository.findByLookupId(1l);

			OrgUserMst lObjOrgUserMstCrtdUsr = orgUserMst;

			OrgPostMst postId =orgUserMst.getCreatedByPost();

			
			
			OrgUserMst lObjUserMst = new OrgUserMst();
			// lLngUserId = IFMSCommonServiceImpl.getNextSeqNum("org_user_mst", inputMap);
			//lObjUserMst.setUserId(lLngUserId);
			lObjUserMst.setUserName(lStrDdoCodeAsst);
			lObjUserMst.setPassword(passwordEncoder.encode("ifms123"));
			lObjUserMst.setCmnLookupMst(lObjCmnLookupMst);
			lObjUserMst.setStartDate(new Timestamp(new Date().getTime()));
			lObjUserMst.setActivateFlag(0l);
			lObjUserMst.setCreatedDate(new Timestamp(new Date().getTime()));
			lObjUserMst.setCreatedBy(lObjOrgUserMstCrtdUsr);
			lObjUserMst.setCreatedByPost(postId);
			lObjUserMst.setSecretQueCode("Secret_Other");
			lObjUserMst.setSecretQueOther("Secret_Other");
			lObjUserMst.setSecretAnswer("ifms");
			lObjUserMst.setCreatedByPost(orgUserMst.getCreatedByPost());
			Optional<MstRoleEntity> findById = mstRoleRepo.findById(1);
			lObjUserMst.setMstRoleEntity(findById.get());
			
			ghibSession.save(lObjUserMst);
			ghibSession.flush();
		} catch (Exception e) {
			throw e;
		}
	}

	// added by vaibhav tyagi: start
	public synchronized Long getNextSeqNoLoc() {
		
		
	return 	cmnLocationMstRepository.findMaxLocId()+1;
		/*
	}

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
	*/}
	// added by vaibhav tyagi: end

	public synchronized Long getNextSeqNoLocForUserMst() {
		Session ghibSession = entityManager.unwrap(Session.class);     

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
		Session ghibSession = entityManager.unwrap(Session.class);     
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
		Session ghibSession = entityManager.unwrap(Session.class);     
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
		Session ghibSession = entityManager.unwrap(Session.class);     
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
		Session ghibSession = entityManager.unwrap(Session.class);     
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
		Session ghibSession = entityManager.unwrap(Session.class);     
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
		Session ghibSession = entityManager.unwrap(Session.class);     
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
	
	public List RetirveParentdtlfrmReptCode(String RptDDOCode)
	{
		Session ghibSession = entityManager.unwrap(Session.class);     
		List temp=null;
		try
		{		
			String branchQuery = "SELECT DEPT_LOC_CODE||'##'||HOD_LOC_CODE FROM ORG_DDO_MST where DDO_CODE like '%"+RptDDOCode+"%'";
			Query sqlQuery= ghibSession.createSQLQuery(branchQuery);
			temp= sqlQuery.list();



		}
		catch(Exception e){
			e.printStackTrace();
		}
		return temp;
	}
	public List RetirveAdminDeptType(String lstrAdminDeptType)
	{
		Session ghibSession = entityManager.unwrap(Session.class);     
		List temp=null;
		try
		{		
			String branchQuery = "SELECT b.DEPT_ID FROM MST_ZP_DEPT b where DEPT_ID like '%"+lstrAdminDeptType+"%'";
			Query sqlQuery= ghibSession.createSQLQuery(branchQuery);
			temp= sqlQuery.list();



		}
		catch(Exception e){
			e.printStackTrace();
		}
		return temp;
	}
	
	


public List retriveDepts(String OfcCode) 
	{
		List temp=null;
		//hibSession = getSession();
		try
		{		
			Session ghibSession = entityManager.unwrap(Session.class);   
			String branchQuery = "select aa.DEPT_ID,aa.DEPT_NAME,aa.DEPT_CODE FROM MST_ZP_DEPT aa where ADMIN_OFF_TYPE_CODE='"+OfcCode+"'";
			Query sqlQuery= ghibSession.createSQLQuery(branchQuery);
			temp= sqlQuery.list();
			
			
			
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return temp;
	}
	public List retirveDdoType(String adminOfcId)
	{
		Session ghibSession = entityManager.unwrap(Session.class);     
		List temp=null;
		try
		{		
			String branchQuery = "SELECT OFFICE_CODE FROM ZP_ADMIN_OFFICE_MST where OFC_ID like '%"+adminOfcId+"%'";
			Query sqlQuery= ghibSession.createSQLQuery(branchQuery);
			temp= sqlQuery.list();

		}
		catch(Exception e){
			e.printStackTrace();
		}
		return temp;
	}

	
	//added by samdahan
		public String generateUniqeInstituteId(String strDdoCode, String districtCode,OrgUserMst orgUserMst) 
		{
			String uniqeInstituteId="";
			int uniqeInstituteIdCount=0;
			String distName="";
			
			uniqeInstituteIdCount=getUniqeInstituteIdCount(strDdoCode);
			distName=getDistName(districtCode);
			
			String tmp = String.valueOf((uniqeInstituteIdCount+1));
			if(tmp.length()<8)
			{
				String prefix="";
				for(int i=0;i<(7-tmp.length());i++)
				{
					prefix = prefix+"0";
				}
				tmp=prefix+tmp;
			}
			
			uniqeInstituteId = distName.substring(0, 3) + tmp; 
			
			
			return uniqeInstituteId;
		}
		
		
		//added by samdahan
		public String getDistName(String districtCode) {
			Session ghibSession = entityManager.unwrap(Session.class);     
			String distName="";
			StringBuilder sb = new StringBuilder();

			sb.append(" SELECT upper(DISTRICT_NAME) FROM CMN_DISTRICT_MST where DISTRICT_ID = '"+districtCode+"' ");
			
			
			Query selectQuery = ghibSession.createSQLQuery(sb.toString());

			
			distName = selectQuery.uniqueResult().toString();
			return distName;
		
		}

		//added by samdahan
		public int getUniqeInstituteIdCount(String strDdoCode) 
		{
			Session ghibSession = entityManager.unwrap(Session.class);     
			int uniqeInstituteIdCount=0;
			StringBuilder sb = new StringBuilder();

			sb.append(" select count(1) from MST_DCPS_DDO_OFFICE where UNIQUE_INSTITUTE_NO is not null ");
			
			
			Query selectQuery = ghibSession.createSQLQuery(sb.toString());

			
			uniqeInstituteIdCount = Integer.parseInt(selectQuery.uniqueResult().toString());
			return uniqeInstituteIdCount;
		
		}

		public void insertOrgDdoMst(String lStrDdoCode, String lStrDdoName, String lStrDdoPersonalName, Long lLngPostId,
				Long gLngUserId, String lStrLocCode, Long gLngPostId, String string, String lstrDdoType,
				String lstrDept_Code, String lstrHOD_Code, String lstrDeptType, OrgUserMst messages) {
			Long lLndDdoId = null;
			try {
				Session ghibSession = entityManager.unwrap(Session.class);     
				OrgDdoMst lObjOrgDdoMst = new OrgDdoMst();
				//lLndDdoId = IFMSCommonServiceImpl.getNextSeqNum("org_ddo_mst", inputMap);
				//lObjOrgDdoMst.setDdoId(lLndDdoId);
				lObjOrgDdoMst.setDdoCode(lStrDdoCode);
				lObjOrgDdoMst.setDdoName(lStrDdoName);
				lObjOrgDdoMst.setDdoPersonalName(lStrDdoPersonalName);
				lObjOrgDdoMst.setPostId(lLngPostId);
				lObjOrgDdoMst.setLangId((short)1);
				lObjOrgDdoMst.setDeptLocCode(lstrDept_Code);
				lObjOrgDdoMst.setCreatedBy(messages.getUserId());
				lObjOrgDdoMst.setCreatedByPost(messages.getCreatedByPost().getPostId());
				lObjOrgDdoMst.setCreatedDate(new Timestamp(new Date().getTime()));
				lObjOrgDdoMst.setDbId((short)99);
				lObjOrgDdoMst.setLocationCode(lStrLocCode);
				ghibSession.save(lObjOrgDdoMst);
				//ghibSession.flush();
			} catch (Exception e) {
				throw e;
			}
		}

	
		
		public void insertRltZpDdoMap(Long ZP_DDO_POST_ID,Long REPT_DDO_POST_ID, Long FINAL_DDO_POST_ID, Long SPECIAL_DDO_POST_ID, String ZP_DDO_CODE,String REPT_DDO_CODE,String FINAL_DDO_CODE,String SPECIAL_DDO_CODE,String lstrLevel,Long CreatedUser,Long CreatedPost,OrgUserMst orgUserMst)
		{
			Long ZP_MAP_ID = null;
			try{
				CmnLanguageMst lObjCmnLanguageMst = cmnLanguageMstRepository.findByLangId(1l);
				Session ghibSession = entityManager.unwrap(Session.class);     
				ZpRltDdoMap objZpRltDdoMap=new ZpRltDdoMap();
				//ZP_MAP_ID = IFMSCommonServiceImpl.getNextSeqNum("RLT_ZP_DDO_MAP", inputMap);
			//	ZP_MAP_ID = getNextSeqNoLocForRltZpDdoMap();
				objZpRltDdoMap.setZpDdoPostId(ZP_DDO_POST_ID);
				//objZpRltDdoMap.setZP_MAP_ID(ZP_MAP_ID);
				objZpRltDdoMap.setReptDdoPostId(REPT_DDO_POST_ID);
				objZpRltDdoMap.setFinalDdoPostId(FINAL_DDO_POST_ID);
				objZpRltDdoMap.setSpecialDdoPostId(SPECIAL_DDO_POST_ID);
				objZpRltDdoMap.setZpDdoCode(ZP_DDO_CODE);
				objZpRltDdoMap.setReptDdoCode(REPT_DDO_CODE);
				objZpRltDdoMap.setFinalDdoCode(FINAL_DDO_CODE);
				objZpRltDdoMap.setSpecialDdoCode(SPECIAL_DDO_CODE);
				objZpRltDdoMap.setLangId(1l);;
				objZpRltDdoMap.setStatus(0l);
				objZpRltDdoMap.setCreatedDate(new Timestamp(new Date().getTime()));
				objZpRltDdoMap.setCreatedUserId(CreatedPost);
				objZpRltDdoMap.setCreatedPostId(CreatedUser);
				objZpRltDdoMap.setZplevel(Long.valueOf(lstrLevel));
				ghibSession.save(objZpRltDdoMap);
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
		
		public List retirveRepoDDOPostId(String DDOCode)
		{
			List temp=null;
			try
			{		
				Session ghibSession = entityManager.unwrap(Session.class);   
				String branchQuery = "SELECT POST_ID FROM ORG_DDO_MST where DDO_CODE="+DDOCode;
				Query sqlQuery= ghibSession.createSQLQuery(branchQuery);
				temp= sqlQuery.list();



			}
			catch(Exception e){
				e.printStackTrace();
			}
			return temp;
		}
		
		public List retirveFinalDDOPostId(Long DDOCode)
		{
			List temp=null;
			try
			{		
				Session ghibSession = entityManager.unwrap(Session.class);   
				String branchQuery = "SELECT POST_ID FROM ORG_DDO_MST where DDO_CODE="+DDOCode;
				Query sqlQuery= ghibSession.createSQLQuery(branchQuery);
				temp= sqlQuery.list();



			}
			catch(Exception e){
				e.printStackTrace();
			}
			return temp;
		}

		
		public List checkRepopostrole(Long postID,Long roleID)
		{
			Session ghibSession = entityManager.unwrap(Session.class);   
			List temp=null;
			try
			{		
				String branchQuery = "SELECT POST_ROLE_ID FROM ACL_POSTROLE_RLT where post_id="+postID+" and role_id="+roleID;
				Query sqlQuery= ghibSession.createSQLQuery(branchQuery);
				temp= sqlQuery.list();


			}
			catch(Exception e){
				e.printStackTrace();
			}
			return temp;
		}

		/*public void insertPostRoleRlt(Long rEPT_DDO_POST_ID, Long gLngUserId, Long gLngPostId, OrgUserMst messages,
				String string) {
			// TODO Auto-generated method stub
			
		}*/
	
}
