package com.mahait.gov.in.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

<<<<<<< Updated upstream
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
@Transactional
public class MstDesignationServiceImpl implements MstDesignationService{/*
=======
>>>>>>> Stashed changes
=======
import com.mahait.gov.in.common.StringHelperUtils;
>>>>>>> Stashed changes
import com.mahait.gov.in.entity.MstPayCommissionEntity;
import com.mahait.gov.in.model.MstDesignationModel;
import com.mahait.gov.in.repository.MstDesignationRepo;
@Service
@Transactional
public class MstDesignationServiceImpl implements MstDesignationService{

	
	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	
	@Autowired
	private MstDesignationRepo  mstDesignationRepo;

	/*@Override
	public int saveDesignationMst(MstDesignationModel mstDesignationModel,int userId) {
		MstDesignationEntity mstDesignationEntity = new MstDesignationEntity();

		//mstDesignationEntity.setField_department(Integer.valueOf(mstDesignationModel.getFieldDepartment()));
		//mstDesignationEntity.setCadre(1);;
		mstDesignationEntity.setPayCommission(mstDesignationModel.getPayCommission());
		mstDesignationEntity.setField_department(Integer.valueOf(mstDesignationModel.getFieldDepartmrnt()));
		mstDesignationEntity.setDesgination_code(mstDesignationModel.getDesignationCode());
		mstDesignationEntity.setDesgination(mstDesignationModel.getDesignation().toUpperCase());
		mstDesignationEntity.setDesignationShortName(mstDesignationModel.getDesignationShortName().toUpperCase());
		mstDesignationEntity.setCadreCode(mstDesignationModel.getCadreCode());
		mstDesignationEntity.setCadre(Integer.valueOf(mstDesignationModel.getCadreDescDD()));
		mstDesignationEntity.setPayCommission(mstDesignationModel.getDdcPayCommission());
		mstDesignationEntity.setIsActive('1');
		mstDesignationEntity.setCreatedDate(new Date());
		mstDesignationEntity.setCreatedUserId(userId);
		mstDesignationEntity.setCadreGroup(mstDesignationModel.getCadreGroup());
		
		int saveId = mstDesignationRepo.saveDesignationMst(mstDesignationEntity);
		return saveId;
	}

	}*/

/*	@Override
>>>>>>> d62234043a6ef75399080141dbb407484af22d68
	public String getCadreDesc(Integer fieldDepartmrntID, String language) {
		CommonHelper helper = new CommonHelper();
		List<Object[] > lstprop = mstDesignationRepo.getCadreDesc(fieldDepartmrntID);
		Gson gson = new Gson();
		String str = gson.toJson(helper.getCadreDescDataHelper(lstprop,language));
		return JsonResponseHelper.getJSONResponseString(str);
	}

*/
	/*@Override
>>>>>>> Stashed changes
	public List<MstPayCommissionEntity> findAllPayCommission() {
		return mstDesignationRepo.findAllPayCommission();
	}*/

	@Override
	public List<MstDesignationModel> getDesignationMstData(String locale) {

		List<Object[]> lstprop = mstDesignationRepo.getDesignationMstData();
		List<MstDesignationModel> lstObj = new ArrayList<>();
        if (!lstprop.isEmpty()) {
            for (Object[] objLst : lstprop) {
            	MstDesignationModel obj = new MstDesignationModel();
                obj.setDesignationId(StringHelperUtils.isNullBigInteger(objLst[0]));
//                if(locale.equals("en")) {
//                	 obj.setDescFldDept(StringHelperUtils.isNullString(objLst[1]));
//                } else {
//                	 obj.setDescFldDept(StringHelperUtils.isNullString(objLst[2]));
//                }
                obj.setDesignationCode(StringHelperUtils.isNullBigInteger(objLst[1]));
                obj.setDesignation(StringHelperUtils.isNullString(objLst[2]));
                obj.setDesignationShortName(StringHelperUtils.isNullString(objLst[3]));
//                obj.setDescCadre(StringHelperUtils.isNullString(objLst[6]));
//                if(locale.equals("en")) {
//               	 obj.setDescPayCommission(StringHelperUtils.isNullString(objLst[7]));
//               } else {
//               	 obj.setDescPayCommission(StringHelperUtils.isNullString(objLst[8]));
//               }
                obj.setIsActive(StringHelperUtils.isNullInt(Integer.parseInt(String.valueOf(objLst[4]))));
                if(objLst[5]!=null)
                {
                	
                obj.setCadreName(StringHelperUtils.isNullString(objLst[5]));
                }
                lstObj.add(obj);
            }
        }
        return lstObj;
	}
	/*
	@Override
	public MstDesignationEntity findMstDesgByDesgId(int designationId) {
		return mstDesignationRepo.findMstDesgByDesgId(designationId);
	}

	@Override
	public List<MstCadreEntity> findCadreDescByFldDeptId(Integer fldDeptId) {
		return mstDesignationRepo.findCadreDescByFldDeptId(fldDeptId); 
	}

	@Override
	public String editDesgSave(MstDesignationEntity mstDesgEntity,int userId) {
		MstDesignationEntity objDesg = mstDesignationRepo.findMstDesgByDesgId(mstDesgEntity.getDesgination_id());
		if(objDesg != null) {
			objDesg.setField_department(mstDesgEntity.getField_department());
			objDesg.setDesgination_code(mstDesgEntity.getDesgination_code());
			objDesg.setDesgination(mstDesgEntity.getDesgination().toUpperCase());
			objDesg.setDesignationShortName(mstDesgEntity.getDesignationShortName().toUpperCase());
			objDesg.setCadreCode(mstDesgEntity.getCadreCode());
			objDesg.setIsActive(mstDesgEntity.getIsActive());	// UPDATED
			objDesg.setUpdatedDate(new Date());
			objDesg.setUpdatedUserId(userId);
			objDesg.setCadreGroup(mstDesgEntity.getCadreGroup());
			mstDesignationRepo.updateDesginationStatus(objDesg);
		}
		return "UPDATED";
	}

	@Override
	public MstDesignationEntity findMstDesgByIdForReject(int designationId,int userId) {
		MstDesignationEntity objDes = mstDesignationRepo.findMstDesgByDesgId(designationId);
		if(objDes != null) {
			objDes.setIsActive('0');	// REJECTED
			objDes.setUpdatedDate(new Date());
			objDes.setUpdatedUserId(userId);
			mstDesignationRepo.updateDesginationStatus(objDes);
		}
		return objDes;
	}

	@Override
	public List<Long> validateDesignationName(String desgname) {
		return mstDesignationRepo.validateDesignationName(desgname);
	}

	@Override
	public List<MstCadreEntity> getCadre() {
		return mstDesignationRepo.getCadre();
<<<<<<< HEAD
	}
<<<<<<< Updated upstream
<<<<<<< HEAD
	}*/
}
