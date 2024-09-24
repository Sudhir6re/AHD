package com.mahait.gov.in.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mahait.gov.in.common.StringHelperUtils;
import com.mahait.gov.in.model.LstGenerateBillDetailsModel;
import com.mahait.gov.in.model.PaybillViewApproveDeleteModel;
import com.mahait.gov.in.repository.PayBillViewApprDelBillRepo;

@Service
@Transactional
public class PayBillViewApprDelBillServiceImpl implements PayBillViewApprDelBillService {

//	protected final Log logger = LogFactory.getLog(getClass());
	@Autowired
	PayBillViewApprDelBillRepo payBillViewApprDelBill;
	
	@Override
	public List<LstGenerateBillDetailsModel> findAlllstGenerateBillDetailsAgainstDDO(String ddoCode,int roleId,int month) {
		List<LstGenerateBillDetailsModel> lstObj = new ArrayList<>();
		
		if (roleId == 3) {
			List<Object[]> lstGenerateBillDetails = payBillViewApprDelBill
					.findAlllstGenerateBillDetailsAgainstDDO(ddoCode,month);
			lstObj = new ArrayList<>();
			if (!lstGenerateBillDetails.isEmpty()) {
				for (Object[] objLst : lstGenerateBillDetails) {
					LstGenerateBillDetailsModel obj = new LstGenerateBillDetailsModel();
					obj.setPaybillGenerationTrnId(StringHelperUtils.isNullBigInteger(objLst[0]));
					obj.setBillDescription(StringHelperUtils.isNullString(objLst[1]));
					obj.setSchemeCode(StringHelperUtils.isNullString(objLst[2]));
					obj.setSchemeName(StringHelperUtils.isNullString(objLst[3]));
					obj.setBillGrossAmt(StringHelperUtils.isNullDouble(Double.parseDouble(objLst[4].toString())));
					obj.setBillNetAmt(StringHelperUtils.isNullDouble(Double.parseDouble(objLst[5].toString())));
					obj.setIsActive(StringHelperUtils.isNullInt(Integer.parseInt(String.valueOf(objLst[6]))));
					obj.setNoOfEmployee(StringHelperUtils.isNullInt(objLst[7]));
					obj.setAuthno(StringHelperUtils.isNullString(objLst[8]));

					lstObj.add(obj);
				}
			}
			return lstObj;
		}
		
		else if(roleId != 3)
		{
			List<String> ddoCode1 = payBillViewApprDelBill.findDdoNameAgainstGivenDdo(ddoCode,roleId);
			if(ddoCode1.isEmpty() || ddoCode1 == null ) {
				return null;
			}else {
			List<Object[]> lstGenerateBillDetails = payBillViewApprDelBill
					.findAlllstGenerateBillDetailsAgainstDDO2(ddoCode1);
			lstObj = new ArrayList<>();
			if (!lstGenerateBillDetails.isEmpty()) {
				for (Object[] objLst : lstGenerateBillDetails) {
					LstGenerateBillDetailsModel obj = new LstGenerateBillDetailsModel();
					obj.setPaybillGenerationTrnId(StringHelperUtils.isNullBigInteger(objLst[0]));
					obj.setBillDescription(StringHelperUtils.isNullString(objLst[1]));
					obj.setSchemeCode(StringHelperUtils.isNullString(objLst[2]));
					obj.setSchemeName(StringHelperUtils.isNullString(objLst[3]));
					obj.setBillGrossAmt(StringHelperUtils.isNullDouble(Double.parseDouble(objLst[4].toString())));
					obj.setBillNetAmt(StringHelperUtils.isNullDouble(Double.parseDouble(objLst[5].toString())));
					obj.setIsActive(StringHelperUtils.isNullInt(Integer.parseInt(String.valueOf(objLst[6]))));
					obj.setNoOfEmployee(StringHelperUtils.isNullInt(objLst[7]));
					lstObj.add(obj);
				}
			}
			return lstObj;
			}
		}
		
		return lstObj;
	}

	@Override
	public List<LstGenerateBillDetailsModel> findAllPayBillsForConsolidatedAgainstDDO(String ddoCode, int roleId) {
		List<LstGenerateBillDetailsModel> lstObj = new ArrayList<>();
		List<Object[]> lstGenerateBillDetails = payBillViewApprDelBill
				.findAllPayBillsForConsolidatedAgainstDDO(ddoCode,roleId);
		lstObj = new ArrayList<>();
		if (!lstGenerateBillDetails.isEmpty()) {
			for (Object[] objLst : lstGenerateBillDetails) {
				LstGenerateBillDetailsModel obj = new LstGenerateBillDetailsModel();
				obj.setPaybillGenerationTrnId(StringHelperUtils.isNullBigInteger(objLst[0]));
				obj.setBillDescription(StringHelperUtils.isNullString(objLst[1]));
				obj.setSchemeCode(StringHelperUtils.isNullString(objLst[2]));
				obj.setSchemeName(StringHelperUtils.isNullString(objLst[3]));
				obj.setBillGrossAmt(StringHelperUtils.isNullDouble(objLst[4]));
				obj.setBillNetAmt(StringHelperUtils.isNullDouble(objLst[5]));
				obj.setIsActive(StringHelperUtils.isNullInt(Integer.parseInt(String.valueOf(objLst[6]))));

				lstObj.add(obj);
			}
		}
		return lstObj;
	}
	public List<PaybillViewApproveDeleteModel> findPayBillByBillNumber(String billNumber, int paybillMonth, int paybillYear,int roleId) {
		

		// TODO Auto-generated method stub
		List<Object[]>  lstObject= payBillViewApprDelBill.findPayBillByBillNumber(billNumber,paybillMonth,paybillYear,roleId);
		List<PaybillViewApproveDeleteModel> lstPaybillViewApproveDeleteModel=new ArrayList<>();
		if (lstObject.size()>0) {
			for (Object[] objLst : lstObject) {
				PaybillViewApproveDeleteModel paybillViewApproveDeleteModel=new PaybillViewApproveDeleteModel();
				paybillViewApproveDeleteModel.setPaybillGenerationTrnId(StringHelperUtils.isNullBigInteger(objLst[0]).longValue());
				paybillViewApproveDeleteModel.setBillDescription(StringHelperUtils.isNullString(objLst[1]));
				paybillViewApproveDeleteModel.setSchemeCode(StringHelperUtils.isNullString(objLst[2]));
				paybillViewApproveDeleteModel.setSchemeName(StringHelperUtils.isNullString(objLst[3]));
				paybillViewApproveDeleteModel.setBillGrossAmt(StringHelperUtils.isNullBigInteger(objLst[4]).longValue());
				paybillViewApproveDeleteModel.setBillNetAmount(StringHelperUtils.isNullBigInteger(objLst[5]).longValue());
				paybillViewApproveDeleteModel.setIsActive(StringHelperUtils.isNullInt(objLst[6]));
				paybillViewApproveDeleteModel.setNoOfEmployee(StringHelperUtils.isNullInt(objLst[7]));
				paybillViewApproveDeleteModel.setAuthNo(StringHelperUtils.isNullString(objLst[8]));
				lstPaybillViewApproveDeleteModel.add(paybillViewApproveDeleteModel);
			}
		}
		return lstPaybillViewApproveDeleteModel;
	
		//return  payBillViewApprDelBill.findPayBillByBillNumber(billNumber,paybillMonth,paybillYear,roleId);
	}
	
	//
	public List<PaybillViewApproveDeleteModel> findPayBillByMonthYear(int paybillMonth, int paybillYear,String ddoCode,int roleId) {
		// TODO Auto-generated method stub
		List<Object[]>  lstObject= payBillViewApprDelBill.findPayBillByMonthYear(paybillMonth,paybillYear,ddoCode,roleId);
		List<PaybillViewApproveDeleteModel> lstPaybillViewApproveDeleteModel=new ArrayList<>();
		if (lstObject.size()>0) {
			for (Object[] objLst : lstObject) {
				PaybillViewApproveDeleteModel paybillViewApproveDeleteModel=new PaybillViewApproveDeleteModel();
				paybillViewApproveDeleteModel.setPaybillGenerationTrnId(StringHelperUtils.isNullBigInteger(objLst[0]).longValue());
				paybillViewApproveDeleteModel.setBillDescription(StringHelperUtils.isNullString(objLst[1]));
				paybillViewApproveDeleteModel.setSchemeCode(StringHelperUtils.isNullString(objLst[2]));
				paybillViewApproveDeleteModel.setSchemeName(StringHelperUtils.isNullString(objLst[3]));
				paybillViewApproveDeleteModel.setBillGrossAmt(StringHelperUtils.isNullBigInteger(objLst[4]).longValue());
				paybillViewApproveDeleteModel.setBillNetAmount(StringHelperUtils.isNullBigInteger(objLst[5]).longValue());
				paybillViewApproveDeleteModel.setIsActive(StringHelperUtils.isNullInt(objLst[6]));
				paybillViewApproveDeleteModel.setNoOfEmployee(StringHelperUtils.isNullInt(objLst[7]));
				paybillViewApproveDeleteModel.setAuthNo(StringHelperUtils.isNullString(objLst[8]));
				lstPaybillViewApproveDeleteModel.add(paybillViewApproveDeleteModel);
			}
		}
		return lstPaybillViewApproveDeleteModel;
	}
	

}
