package com.mahait.gov.in.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.itextpdf.text.DocumentException;
import com.mahait.gov.in.common.CommonConstants;
import com.mahait.gov.in.common.CommonConstants.STATUS;
import com.mahait.gov.in.common.CommonUtils;
import com.mahait.gov.in.entity.OrgUserMst;
import com.mahait.gov.in.model.EmployeeStatisticsModel;
import com.mahait.gov.in.model.TopicModel;
import com.mahait.gov.in.service.CommonHomeMethodsService;
import com.mahait.gov.in.service.EmployeeStatisticsService;
@Controller
@RequestMapping("/paybill")
public class EmployeeStatisticsController {
	
//	protected final Log logger = LogFactory.getLog(getClass());
	List<EmployeeStatisticsModel> lstempstcBillObj = new ArrayList<>();
	
	@Autowired
	CommonHomeMethodsService commonHomeMethodsService;
	
	@Autowired
	EmployeeStatisticsService employeeStatisticsService;
	
	
//	
//	@Autowired
//	PdfGenaratorUtil pdfGenaratorUtil;

	
	SimpleDateFormat sdf =new SimpleDateFormat("dd/MM/yyyy");
	
	
	@GetMapping("/employeeStatisticsReport")
	public String employeeStatisticsReport(
			@ModelAttribute("employeeStatisticsModel") EmployeeStatisticsModel employeeStatisticsModel,
			 Model model, Locale locale, HttpSession session)
			throws FileNotFoundException, DocumentException {
		
		String message = (String)model.asMap().get("message");
		model.addAttribute("employeeStatisticsModel", employeeStatisticsModel);

		OrgUserMst messages = (OrgUserMst) session.getAttribute("MY_SESSION_MESSAGES");
		
		model.addAttribute("gotMonthName", session.getAttribute("PAY_BILL_MONTH"));
		
		model.addAttribute("language", locale.getLanguage());

		SimpleDateFormat sdf =new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		lstempstcBillObj.clear();
				String empname=null;
				String savaarthid=null;
				String dob=null;
				String empType=null;
				String cadre=null;
				String postname=null;
				String postType=null;
				String poststartdate=null;
				String postenddate=null;
				String dateofjoining=null;
				String dateofexipry=null;
				String scaledec=null;
				String basicpay=null;
				String sevenbasicpay=null;
				String pfserives=null;
				String gisgroup=null;
				String gisvalue=null;
				String gpfdcps=null;
				String handicap=null;
				String bankname=null;
				String bankbarname=null;
				String ifsccode=null;
				String accno=null;
				String pranno=null;
				String pranstatus=null;
				String pranremarks=null;
				String aadharno=null;
				String panno=null;
		List<Object[]> listA = employeeStatisticsService.findEmployeeStatistics(messages.getDdoCode());
		for (Object[] objLst : listA) {
			if (objLst[0] != null) {
				empname=objLst[0].toString();
			}
			if (objLst[1] != null) {
				savaarthid=objLst[1].toString();
			}else {
				savaarthid="";	
			}
			if (objLst[2] != null) {
				dob=objLst[2].toString();
			}
			if (objLst[3] != null) {
				pranno=objLst[3].toString();
			}
			if (objLst[4] != null) {
				pranstatus=objLst[4].toString();
			}
			if (objLst[5] != null) {
				pranremarks=objLst[5].toString();
			}
			if (objLst[6] != null) {
				if(objLst[6].toString()=="P") {
					empType="Permanent";
				}else if(objLst[6].toString()=="B") {
					empType="Both";
				}else {
					empType="Temporary";
				}
				empType="Permanent";
			}
			if (objLst[7] != null) {
				cadre=objLst[7].toString();
			}
			if (objLst[8] != null) {
				postname=objLst[8].toString();
			}
		
			if (objLst[9] != null) {
				if(objLst[9].toString()=="P") {
					postType="Permanent";
				}else if(objLst[9].toString()=="B") {
					postType="Both";
				}else {
					postType="Temporary";
				}
				postType="Permanent";
				
			}
			if (objLst[10] != null) {
				poststartdate=objLst[10].toString();
			}
			if (objLst[11] != null) {
				postenddate=objLst[11].toString();
			}
			if (objLst[12] != null) {
				dateofjoining=objLst[12].toString();
			}
			if (objLst[13] != null) {
				dateofexipry=objLst[13].toString();
			}
			if (objLst[14] != null) {
				scaledec=objLst[14].toString();
			}
			
			if (objLst[15] != null) {
				basicpay=objLst[15].toString();
				
			}
			if (objLst[16] != null) {
				sevenbasicpay=objLst[16].toString();
			}else {
				sevenbasicpay="";
			}
			if (objLst[17] != null) {
				pfserives=objLst[17].toString();
			}else {
				pfserives="";
			}
			if (objLst[18] != null) {
				gpfdcps=objLst[18].toString();
			}
			if (objLst[19] != null) {
				gisgroup=objLst[19].toString();
			}
			if (objLst[20] != null) {
				gisvalue=objLst[20].toString();
			}
			if (objLst[21] != null) {
				handicap=objLst[21].toString();
			}else {
				handicap="-";
			}
			if (objLst[22] != null) {
				bankname=(objLst[22].toString());
			}
			if (objLst[23] != null) {
				bankbarname=(objLst[23].toString());
			}
			if (objLst[24] != null) {
				ifsccode=objLst[24].toString();
			}
			if (objLst[25] != null) {
				accno=objLst[25].toString();
			}
			if (objLst[26] != null) {
				aadharno=objLst[26].toString();
				}else {
					aadharno="";
			}
			if (objLst[27] != null) {
				panno=objLst[27].toString();
			}else {
				panno="";
			}
		
			
			employeeststicReportModel(empname,savaarthid,dob,empType,cadre,postname,postType,poststartdate,postenddate,dateofjoining,
					 dateofexipry,scaledec,basicpay,sevenbasicpay,pfserives,gpfdcps,gisgroup,gisvalue,handicap,bankname,bankbarname,
					 ifsccode,accno,pranno,pranstatus,pranremarks,aadharno,panno);
		}
		
		model.addAttribute("createddate", sdf.format(new Date()));
		model.addAttribute("systemdate", sdf.format(new Date()));
		  model.addAttribute("lstempstcBillObj",lstempstcBillObj);
		  return "/views/reports/employeeStatistics";
		
	}





	@GetMapping("/employeeStatisticsExcelReport")
	public Map<String,Object> employeeStatisticsExcelReport(@ModelAttribute("employeeStatisticsModel") EmployeeStatisticsModel employeeStatisticsModel,
			 Model model, Locale locale, HttpSession session,HttpServletResponse response)throws FileNotFoundException, DocumentException {
		Map<String,Object> map = new HashMap<String,Object>();
		try{

			String message = (String)model.asMap().get("message");
			model.addAttribute("employeeStatisticsModel", employeeStatisticsModel);

			OrgUserMst messages = (OrgUserMst) session.getAttribute("MY_SESSION_MESSAGES");
			
			model.addAttribute("gotMonthName", session.getAttribute("PAY_BILL_MONTH"));
			
			model.addAttribute("language", locale.getLanguage());

			SimpleDateFormat sdf =new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
	XSSFWorkbook workbook = new XSSFWorkbook();
	XSSFSheet sheet = workbook.createSheet("Data");
	
	List<Object[]> lstResult = employeeStatisticsService.findEmployeeStatistics(messages.getUserName());
	
	if(lstResult!=null && lstResult.size()>0){
		 Row headerRow = sheet.createRow(0);
		 Cell headerCell = headerRow.createCell(0);
		 headerCell.setCellValue("Employee Name");
		 headerCell = headerRow.createCell(1);
		 headerCell.setCellValue("Sevarth Id");
		 headerCell = headerRow.createCell(2);
		 headerCell.setCellValue("Date of Birth");
		 headerCell = headerRow.createCell(3);
		 headerCell.setCellValue("Pran No");
		 headerCell = headerRow.createCell(4);
		 headerCell.setCellValue("Pran Status");
		 headerCell = headerRow.createCell(5);
		 headerCell.setCellValue("Pran Remarks");
		 headerCell = headerRow.createCell(6);
		 headerCell.setCellValue("Employee Type");
		 headerCell = headerRow.createCell(7);
		 headerCell.setCellValue("Cadre");
		 headerCell = headerRow.createCell(8);
		 headerCell.setCellValue("Post Name");
		 headerCell = headerRow.createCell(9);
		 headerCell.setCellValue("Post Type");
		 headerCell = headerRow.createCell(10);
		 headerCell.setCellValue("Post Start Date");
		 headerCell = headerRow.createCell(11);
		 headerCell.setCellValue("Post End Date");
		 headerCell = headerRow.createCell(12);
		 headerCell.setCellValue("Date of Joining");
		 headerCell = headerRow.createCell(13);
		 headerCell.setCellValue("Date of Service Expiry");
		 headerCell = headerRow.createCell(14);
		 headerCell.setCellValue("Scale Description");
		 headerCell = headerRow.createCell(15);
		 headerCell.setCellValue("Basic Salary");
		 headerCell = headerRow.createCell(16);
		 headerCell.setCellValue("Seven PC Basic");
		 headerCell = headerRow.createCell(17);
		 headerCell.setCellValue("PF Series");
		 headerCell = headerRow.createCell(18);
		 headerCell.setCellValue("GPF/DCPS Account No");
		 headerCell = headerRow.createCell(19);
		 headerCell.setCellValue("GIS Group");
		 headerCell = headerRow.createCell(20);
		 headerCell.setCellValue("GIS Value");
		 headerCell = headerRow.createCell(21);
		 headerCell.setCellValue("Physically Handicapped");
		 headerCell = headerRow.createCell(22);
		 headerCell.setCellValue("Bank Name");
		 headerCell = headerRow.createCell(23);
		 headerCell.setCellValue("Bank Branch Name");
		 headerCell = headerRow.createCell(24);
		 headerCell.setCellValue("IFSC Code");
		 headerCell = headerRow.createCell(25);
		 headerCell.setCellValue("Account No");
//		 headerCell = headerRow.createCell(26);
		 int i=1;
		 int  srNo=1;
		 
		for (Iterator iterator = lstResult.iterator(); iterator.hasNext();) {
			int rowNo = 0;
			Object obj[] = (Object[]) iterator.next();
			Row row = sheet.createRow(i++);
			 
//			Cell cell = row.createCell(rowNo++);
			Cell cell;
//			cell.setCellValue(srNo++);

			 for (int j = 0; j < obj.length; j++) {//Read Column from index 1
				 if(j==0){
					 cell = row.createCell(rowNo++);//For Employee Name
					 if(obj[j]!=null)
					 cell.setCellValue(obj[j].toString());
				 }
				 if(j==1){
					 cell = row.createCell(rowNo++);//For Sevarth Id
					 if(obj[j]!=null)
					 cell.setCellValue(obj[j].toString());
				 }
				 if(j==2){
					 cell = row.createCell(rowNo++);//For Date of Birth
					 if(obj[j]!=null)
					 cell.setCellValue(obj[j].toString());
				 }
				 if(j==3){
					 cell = row.createCell(rowNo++);//For Pran No
					 if(obj[j]!=null)
					 cell.setCellValue(obj[j].toString());
				 }
				 if(j==4){
					 cell = row.createCell(rowNo++);//For Pran Status
					 if(obj[j]!=null)
					 cell.setCellValue(obj[j].toString());
				 }
				 if(j==5){
					 cell = row.createCell(rowNo++);//For Pran Remarks
					 if(obj[j]!=null)
					 cell.setCellValue(obj[j].toString());
				 }
				 if(j==6){
					 cell = row.createCell(rowNo++);//For Employee Type
					 if(obj[j]!=null)
						 if(obj[j].toString()=="P") {
								 cell.setCellValue("Permanent");
							}else if(obj[j].toString()=="B") {
								 cell.setCellValue("Both");
							}else {
								cell.setCellValue("Temporary");
							}
						 
				 }
				 if(j==7){
					 cell = row.createCell(rowNo++);//For Cadre
					 if(obj[j]!=null)
					 cell.setCellValue(obj[j].toString());
				 }
				
				 if(j==8){
					 cell = row.createCell(rowNo++);//For Post Name
					 if(obj[j]!=null)
						 if(obj[j]!=null)
							 cell.setCellValue(obj[j].toString());
				 }
				 if(j==9){
					 cell = row.createCell(rowNo++);//For Post Type
					 if(obj[j]!=null)
						 if(obj[j].toString()=="P") {
							 cell.setCellValue("Permanent");
						}else if(obj[j].toString()=="B") {
							 cell.setCellValue("Both");
						}else {
							cell.setCellValue("Temporary");
						}
				 }
				 if(j==10){
					 cell = row.createCell(rowNo++);//For Post Start Date
					 if(obj[j]!=null)
					 cell.setCellValue(obj[j].toString());
				 }
				 if(j==11){
					 cell = row.createCell(rowNo++);//For Post End Date
					 if(obj[j]!=null)
					 cell.setCellValue(obj[j].toString());
				 }
				 if(j==12){
					 cell = row.createCell(rowNo++);//For Date of Joining
					 if(obj[j]!=null)
					 cell.setCellValue(obj[j].toString());
				 }
				 if(j==13){
					 cell = row.createCell(rowNo++);//For Date of Service Expiry
					 if(obj[j]!=null)
					 cell.setCellValue(obj[j].toString());
				 }
				 if(j==14){
					 cell = row.createCell(rowNo++);//For Scale Description
					 if(obj[j]!=null)
					 cell.setCellValue(obj[j].toString());
				 }
				 if(j==15){
					 cell = row.createCell(rowNo++);//For Basic Salary
					 if(obj[j]!=null)
					 cell.setCellValue(obj[j].toString());
				 }
				 if(j==16){
					 cell = row.createCell(rowNo++);//For Seven PC Basic
					 if(obj[j]!=null)
					 cell.setCellValue(Math.round(Double.parseDouble(obj[j].toString())));
				 }
				 if(j==17){
					 cell = row.createCell(rowNo++);//For PF Series
					 if(obj[j]!=null)
					 cell.setCellValue(obj[j].toString());
				 }
				 if(j==18){
					 cell = row.createCell(rowNo++);//For GPF/DCPS Account No
					 if(obj[j]!=null)
					 cell.setCellValue(obj[j].toString());
				 }
				 if(j==19){
					 cell = row.createCell(rowNo++);//For GIS Group
					 if(obj[j]!=null)
					 cell.setCellValue(obj[j].toString());
				 }
				 if(j==20){
					 cell = row.createCell(rowNo++);//For GIS Value
					 if(obj[j]!=null)
					 cell.setCellValue(obj[j].toString());
				 }
				 if(j==21){
					 cell = row.createCell(rowNo++);//For Physically Handicapped
					 if(obj[j]!=null) {
						 cell.setCellValue(obj[j].toString());
					 }else {
						 cell.setCellValue("-");
					 }
				 }
				
				 if(j==22){
					 cell = row.createCell(rowNo++);//For Bank Name
					 if(obj[j]!=null)
					 cell.setCellValue(obj[j].toString());
				 }
				 if(j==23){
					 cell = row.createCell(rowNo++);//For Bank Branch Name
					 if(obj[j]!=null)
					 cell.setCellValue(obj[j].toString());
				 }
				 if(j==24){
					 cell = row.createCell(rowNo++);//For IFSC Code
					 if(obj[j]!=null)
						 cell.setCellValue(obj[j].toString());
				 }
				 if(j==25){
					 cell = row.createCell(rowNo++);//For Account No
					 if(obj[j]!=null)
						 cell.setCellValue(obj[j].toString());
				 }
				
				
			}
		}
	}
	try{
		String osName = System.getProperty( "os.name" );
		File F =null;// new File("D:\\EmployeeStatistics.xlsx");
		String filePath = "";
		if(osName.toLowerCase().contains("windows")) {
//			filePath = PropertyFetcher.getPropertyValue(FileName.APPLICATION_CONFIG,"EXCEL_REPORT");
			 F = new File("D:\\EmployeeStatistics.xlsx");
			filePath = F.getAbsolutePath();
		}else {
//			filePath = PropertyFetcher.getPropertyValue(FileName.APPLICATION_CONFIG,"OTHER_EXCEL_REPORT");
			 F = new File("/home/fileUpload/EmployeeStatistics.xlsx");
			filePath = F.getPath();
		}
    	if (!new File(filePath).exists()) {
    		new File(filePath).mkdirs();
    	}
		FileOutputStream out = new FileOutputStream(new File(F,""));
	    workbook.write(out);
	    out.close();
		try {
			String fileName ="EmployeeStatistics.xlsx";
			// set content type 
			response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
			// add response header
			response.addHeader("Content-Disposition", "attachment; filename=EmployeeStatistics.xlsx");
			Path file = Paths.get(filePath, "");
			//copies all bytes from a file to an output stream
			Files.copy(file, response.getOutputStream());
			//flushes output stream
			response.getOutputStream().flush();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}catch (Exception e){
	    e.printStackTrace();
	}
}catch(Exception e){
	e.printStackTrace();
}
return map;
}





	private void employeeststicReportModel(String empname, String savaarthid, String dob, String empType, String cadre,
			String postname, String postType, String poststartdate, String postenddate, String dateofjoining,
			String dateofexipry, String scaledec, String basicpay, String sevenbasicpay, String pfserives,
			String gpfdcps, String gisgroup, String gisvalue, String handicap, String bankname, String bankbarname,
			String ifsccode, String accno,String pranno,String pranstatus,String pranremarks,String aadharno,String panno) {
		EmployeeStatisticsModel employeeStatisticsModel = new EmployeeStatisticsModel();
		employeeStatisticsModel.setEmpname(empname);
		employeeStatisticsModel.setSavaarthid(savaarthid);
		employeeStatisticsModel.setDob(dob);
		employeeStatisticsModel.setEmpType(empType);
		employeeStatisticsModel.setCadre(cadre);
		employeeStatisticsModel.setPostname(postname);
		employeeStatisticsModel.setPostType(postType);
		employeeStatisticsModel.setPoststartdate(poststartdate);
		employeeStatisticsModel.setPostenddate(postenddate);
		employeeStatisticsModel.setDateofjoining(dateofjoining);
		employeeStatisticsModel.setDateofexipry(dateofexipry);
		employeeStatisticsModel.setScaledec(scaledec);
		employeeStatisticsModel.setBasicpay(basicpay);
		if(!sevenbasicpay.isEmpty() &&  sevenbasicpay!=null) {
			employeeStatisticsModel.setSevenbasicpay(Math.round(Double.parseDouble(sevenbasicpay)));
		}else {
			employeeStatisticsModel.setSevenbasicpay(null);
		}
		employeeStatisticsModel.setPfserives(pfserives);
		employeeStatisticsModel.setGpfdcps(gpfdcps);
		employeeStatisticsModel.setGisgroup(gisgroup);
		employeeStatisticsModel.setGisvalue(gisvalue);
		employeeStatisticsModel.setHandicap(handicap);
		employeeStatisticsModel.setBankname(bankname);
		employeeStatisticsModel.setBankbarname(bankbarname);
		employeeStatisticsModel.setIfsccode(ifsccode);
		employeeStatisticsModel.setAccno(accno);
		employeeStatisticsModel.setPrenno(pranno);
		employeeStatisticsModel.setPrenstatus(pranstatus);
		employeeStatisticsModel.setPrenremarks(pranremarks);
		employeeStatisticsModel.setAadharno(aadharno);
		employeeStatisticsModel.setPanno(panno);
		lstempstcBillObj.add(employeeStatisticsModel);
		
	}

	

}
