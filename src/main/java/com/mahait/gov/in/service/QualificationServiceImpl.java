package com.mahait.gov.in.service;

import java.util.List;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mahait.gov.in.entity.OrgUserMst;
import com.mahait.gov.in.entity.QualificationEntity;
import com.mahait.gov.in.repository.QualificationRepository;

@Service
@Transactional
public class QualificationServiceImpl implements QualificationService{

	@Autowired
	QualificationRepository qualificationRepository;
	


	@Override
	public String findQualificationByIdForDelete(long id) {
		QualificationEntity objMenu = qualificationRepository.findQualificationByidForDelete(id);
		if(objMenu != null) {	
			qualificationRepository.deleteQualification(objMenu);
		}
		return "DELETED";
	}

	@Override
	public Object findQualificationbyidForEdit(long id) {
		QualificationEntity objMenu = qualificationRepository.findQualificationByIdForEdit(id);
		return objMenu;
	}

	@Override
	public String saveEditQualification(@Valid QualificationEntity qualificationEntity, OrgUserMst orgUserMst) {
		QualificationEntity objMenu = qualificationRepository.findQualificationByIdForEdit(qualificationEntity.getQId());
		if(objMenu != null) {
			objMenu.setQId(qualificationEntity.getQId());
			objMenu.setQualification(qualificationEntity.getQualification());
			qualificationRepository.updateQualification(objMenu);
		}
		return "UPDATED";
	}

	@Override
	public Object saveQualification(QualificationEntity qualificationEntity) {
		 return qualificationRepository.save(qualificationEntity);
	}

	@Override
	public List<QualificationEntity> lstAllQualification() {
		return qualificationRepository.lstAllQualification();
	}

}
