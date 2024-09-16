package com.mahait.gov.in.controller.datamigration;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

@Repository
public class BlobToPhysicalFileConverterRepoImpl implements BlobToPhysicalFileConverterRepo {

	@PersistenceContext
	EntityManager entityManager;

	@Override
	public List<Object[]> convertBlogToFile(Integer typeOp) {
		String queryStr = "SELECT a.attachment_id AS fileName, " + "       b.final_attachment AS byteData "
				+ "FROM cmn_attachment_mst a " + " INNER JOIN cmn_attachment_mpg b ON a.attachment_id = b.attachment_id "
				+ "INNER JOIN cmn_attdoc_mst c ON b.sr_no = c.sr_no " + "WHERE a.activate_flag = 1";
		
		if(typeOp==1) {
			queryStr = "SELECT a.attachment_id AS fileName, " + "       b.final_attachment AS byteData "
					+ "FROM cmn_attachment_mst a " + " INNER JOIN cmn_attachment_mpg b ON a.attachment_id = b.attachment_id "
					+ "INNER JOIN cmn_attdoc_mst c ON b.sr_no = c.sr_no " + "WHERE a.activate_flag = 1";
			
		}else if(typeOp==2) {
			queryStr = "SELECT a.attachment_id AS fileName, " + "       b.final_attachment AS byteData "
					+ "FROM cmn_attachment_mst a " + " INNER JOIN cmn_attachment_mpg b ON a.attachment_id = b.attachment_id "
					+ "INNER JOIN cmn_attdoc_mst c ON b.sr_no = c.sr_no " + "WHERE a.activate_flag = 1";
			
		}else {
			queryStr = "SELECT a.attachment_id AS fileName, " + "       b.final_attachment AS byteData "
					+ "FROM cmn_attachment_mst a " + " INNER JOIN cmn_attachment_mpg b ON a.attachment_id = b.attachment_id "
					+ "INNER JOIN cmn_attdoc_mst c ON b.sr_no = c.sr_no " + "WHERE a.activate_flag = 1";
			
		}

		Query query = entityManager.createNativeQuery(queryStr);
		List<Object[]> results = query.getResultList();
		return results;
	}



}
