package com.mahait.gov.in.controller.datamigration;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

import javax.transaction.Transactional;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mahait.gov.in.entity.EmployeeDetailEntity;

@Transactional
@Service
public class EmployeeDetailServiceImpl implements EmployeeDetailService {

	@Autowired
	EmployeeDetailRepository employeeDetailRepository;

	private static final Logger logger = LoggerFactory.getLogger(EmployeeDetailServiceImpl.class);
	private static final String EXCEL_FILE_LOCATION1 = "C:\\Users\\Dipali.sonawane\\Desktop\\Book1.xlsx";
	private static final String EXCEL_FILE_LOCATION = "D:\\CSV Data\\mst_dcps_emp_1209204-1.xlsx";

	@Override
	public String processExcelFile() {
		Workbook workbook = null;
		try {
			workbook = WorkbookFactory.create(new File(EXCEL_FILE_LOCATION));
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
			logger.info("Number of sheets: {}", workbook.getNumberOfSheets());

			int index = 0;
			for (Sheet sheet : workbook) {
				DataFormatter dataFormatter = new DataFormatter();
				for (Row row : sheet) {
					if (index != 0) {
						EmployeeDetailEntity details = new EmployeeDetailEntity();

						details.setEmployeeId(Long.valueOf(dataFormatter.formatCellValue(row.getCell(0))));
						details.setSevaarthId(dataFormatter.formatCellValue(row.getCell(1)));
						details.setGender(dataFormatter.formatCellValue(row.getCell(2)).charAt(0));
						details.setSalutation(Long.valueOf(dataFormatter.formatCellValue(row.getCell(3))));
						details.setEmployeeFullNameEn(dataFormatter.formatCellValue(row.getCell(4)));
						details.setEmployeeFNameEn(dataFormatter.formatCellValue(row.getCell(5)));
						// details.setGisRemark(dataFormatter.formatCellValue(row.getCell(6)));
						details.setEmployeeMNameEn(dataFormatter.formatCellValue(row.getCell(6)));
						details.setEmployeeLNameEn(dataFormatter.formatCellValue(row.getCell(7)));
						// details.setEmployeeFullNameMr(dataFormatter.formatCellValue(row.getCell(8)));
						// details.setEmployeeFNameMr(dataFormatter.formatCellValue(row.getCell(9)));
						// details.setEmployeeMNameMr(dataFormatter.formatCellValue(row.getCell(10)));
						// details.setEmployeeLNameMr(dataFormatter.formatCellValue(row.getCell(11)));
						// details.setEmployeeMotherName(dataFormatter.formatCellValue(row.getCell(13)));
						// details.setMaritalStatus(dataFormatter.formatCellValue(row.getCell(14)).charAt(0));
						try {
							details.setDob(sdf.parse(dataFormatter.formatCellValue(row.getCell(8))));
						} catch (ParseException e) {
							e.printStackTrace();
						}
						try {
							details.setMembership_date(sdf.parse(dataFormatter.formatCellValue(row.getCell(85))));
						} catch (ParseException e) {
							e.printStackTrace();
						}
						try {
							details.setDtJoinCurrentPost(sdf.parse(dataFormatter.formatCellValue(row.getCell(78))));
						} catch (ParseException e) {

							e.printStackTrace();
						}
						// details.setBloodGroup(dataFormatter.formatCellValue(row.getCell(18)).charAt(0));
						details.setMobileNo1(Long.valueOf(dataFormatter.formatCellValue(row.getCell(9))));
						
//						if(row.getCell(10)))==null)
						details.setMobileNo2(Long.valueOf(dataFormatter.formatCellValue(row.getCell(10))));
						// details.setLandlineNo(dataFormatter.formatCellValue(row.getCell(21)));
						details.setEmailId(dataFormatter.formatCellValue(row.getCell(11)));
						// details.setInstemail(dataFormatter.formatCellValue(row.getCell(23)));
						// details.setReligionCode(Long.valueOf(dataFormatter.formatCellValue(row.getCell(24))));
						// details.setGiscatagory(Long.valueOf(dataFormatter.formatCellValue(row.getCell(25))));
						details.setEidNo(dataFormatter.formatCellValue(row.getCell(12)));
						details.setUidNo(dataFormatter.formatCellValue(row.getCell(13)));
						details.setPanNo(dataFormatter.formatCellValue(row.getCell(14)));
						details.setAddress1(dataFormatter.formatCellValue(row.getCell(15)));
						details.setAddress2(dataFormatter.formatCellValue(row.getCell(16)));
						details.setAddress3(dataFormatter.formatCellValue(row.getCell(17)));
						details.setPinCode(Long.valueOf(dataFormatter.formatCellValue(row.getCell(18))));
						// details.setVillageCode(Long.valueOf(dataFormatter.formatCellValue(row.getCell(33))));
						// details.setVillageName(dataFormatter.formatCellValue(row.getCell(34)));
						// details.setTalukaCode(Long.valueOf(dataFormatter.formatCellValue(row.getCell(35))));
						details.setDistrictCode(Long.valueOf(dataFormatter.formatCellValue(row.getCell(19))));
						// details.setSubDeptId(Long.valueOf(dataFormatter.formatCellValue(row.getCell(37))));
						details.setStateCode(Long.valueOf(dataFormatter.formatCellValue(row.getCell(20))));
						// details.setCountryCode(Long.valueOf(dataFormatter.formatCellValue(row.getCell(39))));
						try {
							details.setDoj(sdf.parse(dataFormatter.formatCellValue(row.getCell(22))));
						} catch (ParseException e) {

							e.printStackTrace();
						}
						details.setUserId(Long.valueOf(dataFormatter.formatCellValue(row.getCell(23))));
						details.setCadreCode(Long.valueOf(dataFormatter.formatCellValue(row.getCell(24))));
						// details.setEmpClass(Long.valueOf(dataFormatter.formatCellValue(row.getCell(43))));
						details.setFirstDesignationCode(Long.valueOf(dataFormatter.formatCellValue(row.getCell(25))));
						details.setDesignationCode(Long.valueOf(dataFormatter.formatCellValue(row.getCell(26))));
						// details.setParentAdminDepartmentCode(Long.valueOf(dataFormatter.formatCellValue(row.getCell(46))));
						// details.setParentFieldDepartmentCode(Long.valueOf(dataFormatter.formatCellValue(row.getCell(47))));
						// details.setAdminDepartmentCode(Long.valueOf(dataFormatter.formatCellValue(row.getCell(48))));
						// details.setSubCorporationId(Long.valueOf(dataFormatter.formatCellValue(row.getCell(49))));
						details.setFieldDepartmentCode(Long.valueOf(dataFormatter.formatCellValue(row.getCell(27))));
						details.setCurrentOfficeCode(Long.valueOf(dataFormatter.formatCellValue(row.getCell(62))));
						details.setPayCommissionCode(Long.valueOf(dataFormatter.formatCellValue(row.getCell(61))));
						details.setPayScaleCode(Long.valueOf(dataFormatter.formatCellValue(row.getCell(67))));
						// details.setIsApplicableforBeams(dataFormatter.formatCellValue(row.getCell(54)).charAt(0));
						details.setReasonForChangedtls(dataFormatter.formatCellValue(row.getCell(72)));
						// details.setInstituteAdd(dataFormatter.formatCellValue(row.getCell(56)));
						// details.setDepartmentNameEn(dataFormatter.formatCellValue(row.getCell(57)));
						// details.setInstName(dataFormatter.formatCellValue(row.getCell(75)));
						// details.setPfacno(dataFormatter.formatCellValue(row.getCell(86)));
						// details.setPfdescription(dataFormatter.formatCellValue(row.getCell(87)));
						// details.setGisapplicable(dataFormatter.formatCellValue(row.getCell(61)));
						// details.setGisgroup(dataFormatter.formatCellValue(row.getCell(62)));
						// details.setDcpsaccountmaintainby(dataFormatter.formatCellValue(row.getCell(81)));
						details.setAccountmaintainby(dataFormatter.formatCellValue(row.getCell(80)));
						// details.setIndiApproveOrderNo(dataFormatter.formatCellValue(row.getCell(84)));
						details.setPfseries(dataFormatter.formatCellValue(row.getCell(88)));
						// details.setDcpsNo(dataFormatter.formatCellValue(row.getCell(67)));
						// details.setIsgpfflag(Long.valueOf(dataFormatter.formatCellValue(row.getCell(68))));
						// details.setCrtId(Long.valueOf(dataFormatter.formatCellValue(row.getCell(69))));
						//

						// details.setEmployeeFatherHubandName(dataFormatter.formatCellValue(row.getCell(75)));
						// details.setEmployeeSpouseName(dataFormatter.formatCellValue(row.getCell(76)));
						// details.setEmployeeBankPinCode(dataFormatter.formatCellValue(row.getCell(77)));
						// details.setBuildingName(dataFormatter.formatCellValue(row.getCell(78)));
						// details.setEmpPermanentFlatUnitNo(dataFormatter.formatCellValue(row.getCell(79)));
						// details.setEmpPermanentBuildingName(dataFormatter.formatCellValue(row.getCell(80)));
						// details.setEmpPermanentLocality(dataFormatter.formatCellValue(row.getCell(81)));
						// details.setEmpPermanentDistrict(dataFormatter.formatCellValue(row.getCell(82)));
						// details.setEmpPermanentState(dataFormatter.formatCellValue(row.getCell(83)));
						// details.setEmpPermanentCountry(dataFormatter.formatCellValue(row.getCell(84)));
						// details.setEmpPermanentPinCode(dataFormatter.formatCellValue(row.getCell(85)));
						// details.setEmpNominee1GuardName(dataFormatter.formatCellValue(row.getCell(86)));
						// details.setEmpNominee1InvalidCondn(dataFormatter.formatCellValue(row.getCell(87)));
						// details.setEmpNominee2GuardName(dataFormatter.formatCellValue(row.getCell(88)));
						// details.setEmpNominee2InvalidCondn(dataFormatter.formatCellValue(row.getCell(89)));
						// details.setEmpNominee3GuardName(dataFormatter.formatCellValue(row.getCell(90)));
						// details.setEmpNominee3InvalidCondn(dataFormatter.formatCellValue(row.getCell(91)));
						// details.setNSDLStatus(dataFormatter.formatCellValue(row.getCell(92)));
						// details.setUSPerson(dataFormatter.formatCellValue(row.getCell(93)));
						// details.setCountryofTax(dataFormatter.formatCellValue(row.getCell(94)));
						// details.setAddressOfTax(dataFormatter.formatCellValue(row.getCell(95)));
						// details.setCityOfTax(dataFormatter.formatCellValue(row.getCell(96)));
						// details.setStateofTax(dataFormatter.formatCellValue(row.getCell(97)));
						// details.setPostCodeofTax(dataFormatter.formatCellValue(row.getCell(98)));
						// details.setTinOrPan(dataFormatter.formatCellValue(row.getCell(99)));
						// details.setState(dataFormatter.formatCellValue(row.getCell(100)));
						// details.setCountry(dataFormatter.formatCellValue(row.getCell(101)));
						// details.setDistrict(dataFormatter.formatCellValue(row.getCell(102)));
						// details.setEmpSTDCode(dataFormatter.formatCellValue(row.getCell(103)));
						// details.setEmpPhoneNo(dataFormatter.formatCellValue(row.getCell(104)));
						// details.setEmployeeBankName(dataFormatter.formatCellValue(row.getCell(105)));

						employeeDetailRepository.save(details);
					}
					index++;
				}
			}

			return "CSV processed successfully";

		} catch (IOException e) {
			logger.error(e.getMessage(), e);
			return "Error processing CSV file";
		} finally {
			try {
				if (workbook != null) {
					workbook.close();
				}
			} catch (IOException e) {
				logger.error(e.getMessage(), e);
			}
		}
	}
}