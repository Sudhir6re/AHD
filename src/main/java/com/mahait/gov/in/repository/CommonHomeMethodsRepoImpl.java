package com.mahait.gov.in.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.mahait.gov.in.entity.CmnLookupMst;
import com.mahait.gov.in.entity.MstBankPay;
import com.mahait.gov.in.entity.MstCommonEntity;
import com.mahait.gov.in.entity.MstRoleEntity;
import com.mahait.gov.in.entity.ReligionMstEntity;
import com.mahait.gov.in.model.MstDesnModel;

@SuppressWarnings("unchecked")
@Repository
public class CommonHomeMethodsRepoImpl implements CommonHomeMethodsRepo {
	// protected final Log logger = LogFactory.getLog(getClass());
	@PersistenceContext
	EntityManager manager;



	@Override
	public List<Object[]> findRoleLevelMstList() {

		Session currentSession = manager.unwrap(Session.class);
		String hql = "SELECT " + "a.id, " + "a.role_id, " + "a.role_name, " + "a.role_description " + "FROM "
				+ "role_mst a where a.role_id in('1','2','5','6','8','9','10','11','12','13','14','15','16','17','18','19','20','21','22','23','24','25','26','27','29','30','31','32','33','34') "
				+ "ORDER BY " + "a.id ";
		Query query = currentSession.createSQLQuery(hql);
		return (List<Object[]>) query.list();
	}

	@Override
	public List<Object[]> findMenuNameByRoleID(int levelRoleVal) {

		Session currentSession = manager.unwrap(Session.class);
		String hql = "SELECT a.menu_code, " + "a.menu_name_english, " + "a.menu_name_marathi " + "FROM   menu_mst a, "
				+ "menu_role_mapping b " + "WHERE  a.menu_code = b.menu_code " + "AND b.role_Id = '" + levelRoleVal
				+ "' " + "AND b.is_active = '1' " + "GROUP  BY a.menu_code, " + "a.menu_name_english, "
				+ "a.menu_name_marathi " + "ORDER  BY a.menu_code, " + "a.menu_name_english, "
				+ "a.menu_name_marathi; ";
		Query query = currentSession.createSQLQuery(hql);
		return (List<Object[]>) query.list();

	}

	@Override
	public List<Object[]> findSubMenuByRoleID(int levelRoleVal) {

		Session currentSession = manager.unwrap(Session.class);
		String hql = "select sub_menu_id,menu_code,role_id,sub_menu_name_english,sub_menu_name_marathi,	controller_name,link_name  from sub_menu_mst where role_Id='"
				+ levelRoleVal + "' and is_active='" + 1 + "' order by sub_menu_id ";
		Query query = currentSession.createSQLQuery(hql);
		return (List<Object[]>) query.list();

	}

	@Override
	public List<Object[]> findAllMenu() {
		Session currentSession = manager.unwrap(Session.class);
		String hql = "select a.menu_id,a.menu_code, a.menu_name_english,a.menu_name_marathi,a.is_active from menu_mst a order by a.menu_id";
		Query query = currentSession.createSQLQuery(hql);
		return (List<Object[]>) query.list();

	}

	@Override
	public List<Object[]> findAllRole() {
		Session currentSession = manager.unwrap(Session.class);
		String hql = "select a.role_id as id, a.role_id,a.role_name, a.role_description,a.is_active from role_mst a order by a.role_id";
		Query query = currentSession.createSQLQuery(hql);
		return (List<Object[]>) query.list();

	}

	@Override
	public List<Object[]> findAllSubMenu() {
		Session currentSession = manager.unwrap(Session.class);
		String hql = "select a.sub_menu_id, d.role_name, b.menu_name_english, a.sub_menu_name_english,a.sub_menu_name_marathi,a.controller_name,a.link_name,a.is_active, "
				+ "b.menu_code as menu_code, d.role_id  " + "FROM sub_menu_mst a, " + "menu_mst b, "
				+ "menu_role_mapping c, " + "role_mst d " + "WHERE a.menu_code  = b.menu_code AND "
				+ "a.role_id = c.role_id AND " + "a.role_id = d.role_id AND " + "b.menu_code = c.menu_code AND "
				+ "c.role_id = d.role_id AND  a.is_active='1' " + "ORDER BY a.sub_menu_id, d.role_name ";
		Query query = currentSession.createSQLQuery(hql);
		return (List<Object[]>) query.list();
	}

	@Override
	public List<Object[]> findAllMenuRoleMapping() {
		Session currentSession = manager.unwrap(Session.class);
		String hql = "select c.menu_map_id,a.menu_name_english,a.menu_name_marathi,b.role_name,c.is_active, a.menu_code as menu_code, b.role_id FROM "
				+ "menu_mst a , role_mst b, menu_role_mapping c "
				+ "WHERE a.menu_code = c.menu_code AND b.role_id = c.role_id ORDER BY c.menu_map_id";
		Query query = currentSession.createSQLQuery(hql);
		return (List<Object[]>) query.list();
	}


	@Override
	public MstRoleEntity findMstRoleId(int roleId) {
		MstRoleEntity objDept = null;
		Session currentSession = manager.unwrap(Session.class);
		objDept = currentSession.get(MstRoleEntity.class, roleId);
		return objDept;
	}

	@Override
	public void updateMstRoleStatus(MstRoleEntity objDeptForReject) {
		Session currentSession = manager.unwrap(Session.class);
		currentSession.update(objDeptForReject);

	}

	@Override
	public MstRoleEntity findroleById(Integer roleId) {
		// TODO Auto-generated method stub

		MstRoleEntity objrole = null;
		Session currentSession = manager.unwrap(Session.class);
		objrole = currentSession.get(MstRoleEntity.class, roleId);
		return objrole;

	}

	@Override
	public void updaterole(MstRoleEntity objrole) {
		// TODO Auto-generated method stub
		Session currentSession = manager.unwrap(Session.class);
		currentSession.update(objrole);

	}

	@Override
	public int saveMstRole(MstRoleEntity mstRoleEntity) {
		Session currentSession = manager.unwrap(Session.class);
		return (int) currentSession.save(mstRoleEntity);
	}

	@Override
	public List<CmnLookupMst> findCommonMstByCommonCode(String commoncodeStatus) {
		String HQL = "FROM CmnLookupMst as t  WHERE t.lookupName='" + commoncodeStatus + "' ORDER BY t.lookupId ";
		return (List<CmnLookupMst>) manager.createQuery(HQL).getResultList();
	}

	@Override
	public List<MstBankPay> findBankName() {
		String HQL = "FROM MstBankPay as t";
		return (List<MstBankPay>) manager.createQuery(HQL).getResultList();
	}

	@Override
	public List<MstDesnModel> findDesignation(String userName) {
		Session currentSession = manager.unwrap(Session.class);
		String hql = "select * from ORG_DESIGNATION_MST";
		Query query = currentSession.createSQLQuery(hql);
		return (List<MstDesnModel>) query.list();
	}

	@Override
	public List<Object[]> retriveUserdetails(Long userId) {
		Session currentSession = manager.unwrap(Session.class);
		String hql = "select a.ddo_code,a.location_code,a.post_id,d.post_detail_id \r\n" + 
				" from org_ddo_mst a inner join org_post_mst b on a.location_code=b.location_code \r\n" + 
				" inner join org_user_mst c on c.user_name=a.ddo_code left join org_post_details_rlt d \r\n" + 
				"  on d.post_id=b.post_id where c.user_id="+userId;
		Query query = currentSession.createSQLQuery(hql);
		return (List<Object[]>) query.list();
	}


	public List<ReligionMstEntity> fetchAllReligions() {
		// TODO Auto-generated method stub
		String HQL = "FROM ReligionMstEntity as t ORDER BY t.religionId ";
		return (List<ReligionMstEntity>) manager.createQuery(HQL).getResultList();
	}

	@Override
	public List<Object[]> findLookUpNameDesc(String commoncodeSalutations) {
		// TODO Auto-generated method stub
	Session currentSession = manager.unwrap(Session.class);
		
		String hql = "SELECT O1.LOOKUP_ID,O1.lookup_name FROM CMN_LOOKUP_MST O1, CMN_LOOKUP_MST O2 WHERE O1.PARENT_LOOKUP_ID = O2.LOOKUP_ID \r\n"
				+ " AND O2.LOOKUP_NAME = '"+commoncodeSalutations+"' ORDER BY O1.ORDER_NO desc,O1.LOOKUP_ID";
		Query query = currentSession.createSQLQuery(hql);
		return (List<Object[]>) query.list();
	}



}
