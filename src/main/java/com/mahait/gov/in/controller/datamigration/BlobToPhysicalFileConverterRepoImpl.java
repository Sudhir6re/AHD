package com.mahait.gov.in.controller.datamigration;

import java.math.BigInteger;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.mahait.gov.in.entity.GROrderDocumentEntity;
import com.mahait.gov.in.entity.MstEmployeeEntity;

@Repository
public class BlobToPhysicalFileConverterRepoImpl implements BlobToPhysicalFileConverterRepo {

	@PersistenceContext
	EntityManager entityManager;

	@Override
	public List<Object[]> convertBlogToFile(Integer typeOp) {
		String queryStr = "SELECT a.attachment_id AS fileName, " + "       c.final_attachment AS byteData  "
				+ "FROM cmn_attachment_mst a "
				+ " INNER JOIN cmn_attachment_mpg b ON a.attachment_id = b.attachment_id "
				+ "INNER JOIN cmn_attdoc_mst c ON b.sr_no = c.sr_no ";

		if (typeOp == 1) {
			queryStr = "SELECT a.attachment_id AS fileName, " + "       c.final_attachment AS byteData,d.employee_id  "
					+ "FROM cmn_attachment_mst a "
					+ " INNER JOIN cmn_attachment_mpg b ON a.attachment_id = b.attachment_id "
					+ "INNER JOIN cmn_attdoc_mst c ON b.sr_no = c.sr_no "
					+ "INNER JOIN employee_mst d on d.PHOTO_ATTACHMENT_ID=a.attachment_id "
					+ "WHERE a.activate_flag = 1";

		} else if (typeOp == 2) {
			queryStr = "SELECT a.attachment_id AS fileName, " + "    c.final_attachment AS byteData,d.employee_id "
					+ "FROM cmn_attachment_mst a "
					+ " INNER JOIN cmn_attachment_mpg b ON a.attachment_id = b.attachment_id "
					+ "INNER JOIN cmn_attdoc_mst c ON b.sr_no = c.sr_no "
					+ "INNER JOIN employee_mst d on d.SIGNATURE_ATTACHMENT_ID=a.attachment_id ";
					//+ "WHERE a.activate_flag = 1";

		} else {
			queryStr = "SELECT a.attachment_id AS fileName, "
					+ "       c.final_attachment,e.gr_document_id AS byteData,e.grOrder_id "
					+ "FROM cmn_attachment_mst a "
					+ " INNER JOIN cmn_attachment_mpg b ON a.attachment_id = b.attachment_id "
					+ "INNER JOIN cmn_attdoc_mst c ON b.sr_no = c.sr_no "
					+ "HR_PAY_ORDER_MST d on d.ATTACHMENT_ID=a.attachment_id "
					+ "gr_order_documents e on e.grOrder_id=d.ORDER_ID ";
					//+ "WHERE a.activate_flag = 1";

		}

		Query query = entityManager.createNativeQuery(queryStr);
		List<Object[]> results = query.getResultList();
		return results;
	}

	@Override
	public MstEmployeeEntity findEmpByEmpId(BigInteger id) {
		Session session = entityManager.unwrap(Session.class);
		return session.find(MstEmployeeEntity.class, id.longValue());
	}

	@Override
	public void updatePhotoPath(MstEmployeeEntity mstEmployeeEntity) {
		Session session = entityManager.unwrap(Session.class);
		session.update(mstEmployeeEntity);
	}

	@Override
	public GROrderDocumentEntity findDocumentId(BigInteger grDocumentId) {
		Session session = entityManager.unwrap(Session.class);
		return session.find(GROrderDocumentEntity.class, grDocumentId.longValue());
	}

	@Override
	public void updateGrPath(GROrderDocumentEntity gROrderDocumentEntity) {
		Session session = entityManager.unwrap(Session.class);
		session.update(gROrderDocumentEntity);
	}

}
