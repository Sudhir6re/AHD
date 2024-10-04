package com.mahait.gov.in.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.type.LongType;
import org.springframework.stereotype.Repository;

import com.mahait.gov.in.entity.MstTalukaEntity;
import com.mahait.gov.in.model.MstTalukaModel;

@Repository
public class MstTalukaRepoImpl implements MstTalukaRepo{
	
//	protected final Log logger = LogFactory.getLog(getClass());
	
	@PersistenceContext
	EntityManager entityManager;
	
	/*@Autowired
	SessionFactory sessionFactory;*/
	
	@SuppressWarnings("unchecked")
	@Override
	public List<MstTalukaEntity> findTalukaAll() {
		String HQL = "FROM MstTalukaEntity as t where t.stateCode = 27 and t.isActive = '1'";
		return  entityManager.createQuery(HQL).getResultList();
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> findAllTaluka(int distId) {
		Session currentSession = entityManager.unwrap(Session.class);
		String hql = "select * from taluka_mst where district_code = "+distId;;
		Query query = currentSession.createSQLQuery(hql);
		return (List<Object[]>) query.list();
	}
	
	
	//Added for state list
		@SuppressWarnings("unchecked")
		@Override
		public List<MstTalukaModel> fetchDistrictByState(int stateCode) {
			Session currentSession = entityManager.unwrap(Session.class);

			String HQL = "SELECT new com.mahait.gov.in.model.MstTalukaModel(e.id, e.districtNameEn, e.districtNameMr,d.stateNameEn, d.stateNameMr,d.stateCode, e.districtCode, e.isActive) FROM MstDistrictEntity e INNER JOIN e.mstStateEntity d where e.stateCode ="+stateCode;		
			
			
			return (List<MstTalukaModel>) entityManager.createQuery(HQL).getResultList();
			
		
		}


		@SuppressWarnings("unchecked")
			@Override
			public List<MstTalukaEntity> findByTaluka(Integer subDepartmentCode) {
				String HQL = "FROM MstTalukaEntity as t "; //where t.talukaCode = " +subDepartmentCode
				return  entityManager.createQuery(HQL).getResultList();
			}


		@Override
		public List<Long> validateTalukaName(String talukaname, Integer districtcode) {
			Session currentSession =  entityManager.unwrap(Session.class);

			String hql = "select count(*) as count from taluka_mst  where taluka_name_en  ='"+ talukaname+"' and district_code ='"+districtcode+"'";
			
			Query query = currentSession.createSQLQuery(hql).addScalar("count", LongType.INSTANCE);
			
			List<Long> lstresult = query.list();
			return lstresult;
		}


	
}
