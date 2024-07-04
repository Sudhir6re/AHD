package com.mahait.gov.in.repository;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.mahait.gov.in.entity.CmnTalukaMst;
import com.mahait.gov.in.entity.GROrderDocumentEntity;
import com.mahait.gov.in.entity.HrPayOrderMst;

public interface OrderMasterRepo {

	List<Object[]> findAllEmployee(String userName);

	List<Object[]> getSubGRType(long parentGrpId);

	String getDistrictId(String ddoCode);

	List<CmnTalukaMst> gettalukalst(String districtId);

	List<Long> findUsertype(String ddoCode);

	Integer saveGrOrder(HrPayOrderMst payOrderMst, MultipartFile[] files);

	Integer saveAdvanceDocuments(GROrderDocumentEntity grOrderDocumentEntity);

}
