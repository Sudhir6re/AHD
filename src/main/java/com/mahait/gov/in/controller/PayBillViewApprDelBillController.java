package com.mahait.gov.in.controller;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.net.HttpHeaders;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.mahait.gov.in.HeaderFooterPageEvent;
import com.mahait.gov.in.UserExcelExporter;
import com.mahait.gov.in.common.CommonConstants;
import com.mahait.gov.in.common.CommonConstants.STATUS;
import com.mahait.gov.in.common.CommonUtils;
import com.mahait.gov.in.common.StringHelperUtils;
import com.mahait.gov.in.configuration.PdfGenaratorUtil;
import com.mahait.gov.in.entity.LoanEmployeeDtlsEntity;
import com.mahait.gov.in.entity.OrgUserMst;
import com.mahait.gov.in.entity.PaybillGenerationTrnDetails;
import com.mahait.gov.in.entity.PaybillGenerationTrnEntity;
import com.mahait.gov.in.entity.PaybillStatusEntity;
import com.mahait.gov.in.model.ChangeStatementModel;
import com.mahait.gov.in.model.PayBillViewApprDelBillModel;
import com.mahait.gov.in.model.TopicModel;
import com.mahait.gov.in.repository.PayBillViewApprDelBillRepo;
import com.mahait.gov.in.repository.PaybillGenerationTrnRepo;
import com.mahait.gov.in.service.CommonHomeMethodsService;
import com.mahait.gov.in.service.MstSchemeService;
import com.mahait.gov.in.service.PayBillViewApprDelBillService;
import com.mahait.gov.in.service.PaybillGenerationTrnService;

@Controller
@RequestMapping(value= {"/ddoast","/ddo"})
public class PayBillViewApprDelBillController {
//	protected final Log logger = LogFactory.getLog(getClass());
	 PdfPTable table = new PdfPTable(7);
	 private XSSFWorkbook workbook;
	 private XSSFSheet sheet;
	List<ChangeStatementModel> listChangeStatementModel=new ArrayList<ChangeStatementModel>();

	@Autowired
	CommonHomeMethodsService commonHomeMethodsService;
	
	@Autowired
	PayBillViewApprDelBillService payBillViewApprDelBill;
	
	
	List<PayBillViewApprDelBillModel> lstPayBillObj = new ArrayList<>();
	
	@Autowired
	PaybillGenerationTrnService paybillGenerationTrnService;
	
	/*@Autowired
	MpgSchemeBillGroupService mpgSchemeBillGroupService;*/
	
	@Autowired
	PdfGenaratorUtil pdfGenaratorUtil;
	
	@Autowired
	Environment  environment;
	
	/*@Autowired
	BDSIntegrationService bdsintegrationservice;*/
	
	
	@Autowired
	PayBillViewApprDelBillRepo payBillViewApprDelBillRepo;
	
	@Autowired
	PaybillGenerationTrnRepo paybillHeadMpgRepo;
	
	@Autowired
	MstSchemeService mstSchemeService;
	
	@GetMapping("/payBillViewApprDelBill")
	public String payBillViewApprDelBill(@ModelAttribute("payBillViewApprDelBillModel") PayBillViewApprDelBillModel payBillViewApprDelBillModel,
										Model model,Locale locale,HttpSession session) {
		Calendar now = Calendar.getInstance();
		//Calendar.getInstance().get(Calendar.MONTH);
		LocalDate currentdate = LocalDate.now(); 
		//logger.info("Current date: "+Calendar.getInstance().get(Calendar.MONTH));
		int currentYear = currentdate.getYear(); 
		
		String message = (String)model.asMap().get("message");
		model.addAttribute("payBillViewApprDelBillModel", payBillViewApprDelBillModel);

		OrgUserMst messages = (OrgUserMst) session.getAttribute("MY_SESSION_MESSAGES"); 
		
		model.addAttribute("gotMonthName", session.getAttribute("PAY_BILL_MONTH"));
		
		String splitddo[]=messages.getUserName().split("_");
		String ddo=null;
		ddo=splitddo[0];
		model.addAttribute("lstSchemeBillGroup", mstSchemeService
				.findAllMpgSchemeBillGroupByDDOCode(ddo, messages.getMstRoleEntity().getRoleId()));
		model.addAttribute("lstMonths", commonHomeMethodsService.lstGetAllMonths());
		model.addAttribute("lstYears", commonHomeMethodsService.lstGetAllYears());
		model.addAttribute("roleId", messages.getMstRoleEntity().getRoleId());
		model.addAttribute("userId", messages.getUserId());
		
		model.addAttribute("lstBillGroupLvl2", mstSchemeService
				.findAllMpgSchemeBillGroupBylvl2DDOCode(messages.getUserName()));
	   
//		model.addAttribute("lstBillType", commonHomeMethodsService.findAllScheme());
//		model.addAttribute("lstBillStatus", commonHomeMethodsService.findAllScheme());
		model.addAttribute("language", locale.getLanguage());

		if(messages.getMstRoleEntity().getRoleId() == 3) {
			model.addAttribute("lstGenerateBillDetails", payBillViewApprDelBill.findAlllstGenerateBillDetailsAgainstDDO(messages.getUserName(),messages.getMstRoleEntity().getRoleId(),(now.get(Calendar.MONTH) + 1)) );
			return "/views/paybill/paybill-view-approve-delete-bill";
				}
		
		else if(messages.getMstRoleEntity().getRoleId() != 3) {
			model.addAttribute("lstGenerateBillDetails", payBillViewApprDelBill.findAlllstGenerateBillDetailsAgainstDDO(messages.getUserName(),messages.getMstRoleEntity().getRoleId(),(now.get(Calendar.MONTH) + 1)) );
			return "/views/paybill/paybill-forward-change-statement-bill";
		}
		
		return null;
    } 
	
	@GetMapping("/payBillViewApprDelBill/{returnId}")
	public String payBillViewApprDelBill12(@ModelAttribute("payBillViewApprDelBillModel") PayBillViewApprDelBillModel payBillViewApprDelBillModel,
										Model model,Locale locale,HttpSession session) {
		
		String message = (String)model.asMap().get("message");
		model.addAttribute("payBillViewApprDelBillModel", payBillViewApprDelBillModel);

		OrgUserMst messages = (OrgUserMst) session.getAttribute("MY_SESSION_MESSAGES"); 
		
		model.addAttribute("lstMonths", commonHomeMethodsService.lstGetAllMonths());
		model.addAttribute("lstYears", commonHomeMethodsService.lstGetAllYears());

		return "/views/paybill/paybill-view-approve-delete-bill";
    } 
	
	
	
	@GetMapping("/forwardChangeStatement/{paybillGenerationTrnId}/{userId}")
	public String forwardChangeStatement(@ModelAttribute("payBillViewApprDelBillModel") PayBillViewApprDelBillModel payBillViewApprDelBillModel,@PathVariable int paybillGenerationTrnId,
			@PathVariable Long userId,Model model,Locale locale,HttpSession session,HttpServletRequest request) {
		
		
		String clientIP = request.getRemoteAddr();
		
		 String clientHostname = request.getRemoteHost();
		   
		 
		 String namePIp=clientHostname+"/"+clientIP;
		 
		 System.out.println(namePIp);

		 PaybillGenerationTrnEntity paybillGenerationTrnEntity = paybillGenerationTrnService.findForwardChangeStatementById(paybillGenerationTrnId,userId,namePIp);
	if(paybillGenerationTrnEntity!=null)
	{
		model.addAttribute("is_changed","1");
		model.addAttribute("changeStmtMonth",session.getAttribute("PAY_BILL_MONTH"));
		return "/views/paybill/paybill-view-approve-delete-bill";
	}
	else
	{
		model.addAttribute("is_changed","0");
		return "/views/paybill/paybill-view-approve-delete-bill";
	}
		
		
		//return "/views/paybill/paybill-view-approve-delete-bill";
		//return "/views/paybill/paybill-view-approve-delete-bill";
    } 
	
	
	@GetMapping("/approveChangeStatement/{paybillGenerationTrnId}/{userId}")
	public String approveChangeStatement(@ModelAttribute("payBillViewApprDelBillModel") PayBillViewApprDelBillModel payBillViewApprDelBillModel,@PathVariable int paybillGenerationTrnId,
			@PathVariable Long userId,Model model,Locale locale,HttpSession session,HttpServletRequest request) {
		
		String clientIP = request.getRemoteAddr();
		
	 String clientHostname = request.getRemoteHost();
	   
	 
	 String namePIp=clientHostname+"/"+clientIP;
	 
	 System.out.println(namePIp);
		
		PaybillGenerationTrnEntity paybillGenerationTrnEntity = paybillGenerationTrnService.findApproveChangeStatementById(paybillGenerationTrnId,userId,namePIp);
		
		if(paybillGenerationTrnEntity!=null)
		{
			model.addAttribute("is_changed","1");
			return "/views/paybill/paybill-view-approve-delete-bill";
		}
		else
		{
			model.addAttribute("is_changed","0");
			return "/views/paybill/paybill-view-approve-delete-bill";
		}
	} 
	
	//for reject change statement (3-12-2020)
	@GetMapping("/rejectChangeStatement/{paybillGenerationTrnId}/{userId}")
	public String rejectChangeStatement(@ModelAttribute("payBillViewApprDelBillModel") PayBillViewApprDelBillModel payBillViewApprDelBillModel,@PathVariable int paybillGenerationTrnId,
			@PathVariable Long userId,Model model,Locale locale,HttpSession session,HttpServletRequest request) {

		String clientIP = request.getRemoteAddr();
		
		 String clientHostname = request.getRemoteHost();
		   
		 
		 String namePIp=clientHostname+"/"+clientIP;
		 
		 System.out.println(namePIp);
		
		PaybillGenerationTrnEntity paybillGenerationTrnEntity = paybillGenerationTrnService.findRejectChangeStatementById(paybillGenerationTrnId,userId,namePIp);
		
		if(paybillGenerationTrnEntity!=null)
		{
			model.addAttribute("is_changed","1");
			return "/views/paybill/paybill-view-approve-delete-bill";
		}
		else
		{
			model.addAttribute("is_changed","0");
			return "/views/paybill/paybill-view-approve-delete-bill";
		}
	} 
	
	
	
	@GetMapping("/deleteBill/{paybillGenerationTrnId}/{userId}")
	public String deleteBill(@ModelAttribute("payBillViewApprDelBillModel") PayBillViewApprDelBillModel payBillViewApprDelBillModel,@PathVariable int paybillGenerationTrnId,
			@PathVariable Long userId,Model model,Locale locale,HttpSession session,HttpServletRequest request) {
		String clientIP = request.getRemoteAddr();
		
		 String clientHostname = request.getRemoteHost();
		   
		 
		 String namePIp=clientHostname+"/"+clientIP;
		 
		 System.out.println(namePIp);
		
		PaybillGenerationTrnEntity paybillGenerationTrnEntity = paybillGenerationTrnService.findDeleteBillById(paybillGenerationTrnId,userId,namePIp);
		
		if(paybillGenerationTrnEntity!=null)
		{
			model.addAttribute("is_changed","1");
			return "/views/paybill/paybill-view-approve-delete-bill";
		}
		else
		{
			model.addAttribute("is_changed","0");
			return "/views/paybill/paybill-view-approve-delete-bill";
		}
		
		
		//return "/views/paybill/paybill-view-approve-delete-bill";
		//return "/views/paybill/paybill-view-approve-delete-bill";
	}
	
	@GetMapping("/btnForwardBill/{paybillGenerationTrnId}/{userId}")
	public String btnForwardBill(@ModelAttribute("payBillViewApprDelBillModel") PayBillViewApprDelBillModel payBillViewApprDelBillModel,@PathVariable int paybillGenerationTrnId,
			@PathVariable Long userId,Model model,Locale locale,HttpSession session,HttpServletRequest request) {
		
		String clientIP = request.getRemoteAddr();
		
		 String clientHostname = request.getRemoteHost();
		   
		 
		 String namePIp=clientHostname+"/"+clientIP;
		 
		 System.out.println(namePIp);
		
		PaybillGenerationTrnEntity paybillGenerationTrnEntity = paybillGenerationTrnService.findForwardBillById(paybillGenerationTrnId,userId,namePIp);
		
		if(paybillGenerationTrnEntity!=null)
		{
			model.addAttribute("is_changed","1");
			return "/views/paybill/paybill-view-approve-delete-bill";
		}
		else
		{
			model.addAttribute("is_changed","0");
			return "/views/paybill/paybill-view-approve-delete-bill";
		}
		
		
		//return "/views/paybill/paybill-view-approve-delete-bill";
		//return "/views/paybill/paybill-view-approve-delete-bill";
	}
	
	
	/*@GetMapping("/forwardPayBillToLevel2/{paybillGenerationTrnId}")
	public String forwardPayBillToLevel2(@ModelAttribute("payBillViewApprDelBillModel") PayBillViewApprDelBillModel payBillViewApprDelBillModel,@PathVariable int paybillGenerationTrnId,
			Model model,Locale locale,HttpSession session) {
		
		logger.info( " PaybillGenerationTrnId For Bill Forward to Level 2 : " + paybillGenerationTrnId);
		PaybillGenerationTrnEntity paybillGenerationTrnEntity = paybillGenerationTrnService.forwardPayBillToLevel2(paybillGenerationTrnId);
		if(paybillGenerationTrnEntity!=null) {
			model.addAttribute("is_changed","1");
			return "/views/paybill/paybill-view-approve-delete-bill";
		} else {
			model.addAttribute("is_changed","0");
			return "/views/paybill/paybill-view-approve-delete-bill";
		}
	}*/ 
	
	@RequestMapping(value="/forwardPayBillToLevel2/{paybillGenerationTrnId}/{userId}")	// , method = RequestMethod.POST
	public String deleteCadre(@PathVariable int paybillGenerationTrnId,@PathVariable Long userId,Model model,Locale locale,HttpServletRequest request) {
		
		String clientIP = request.getRemoteAddr();
		
		 String clientHostname = request.getRemoteHost();
		   
		 
		 String namePIp=clientHostname+"/"+clientIP;
		 
		 System.out.println(namePIp);
		
		 
		PaybillGenerationTrnEntity paybillGenerationTrnEntity = paybillGenerationTrnService.forwardPayBillToLevel2(paybillGenerationTrnId,userId,namePIp);
		if(paybillGenerationTrnEntity != null) {
			model.addAttribute("payBillViewApprDelBillModel", new PayBillViewApprDelBillModel());
			model.addAttribute("language", locale.getLanguage());
		}
		return "views/paybill/paybill-view-approve-delete-bill";
	}
	
	/* Added by Brijoy 24-12-2020 for Generate Paybill*/
	@RequestMapping(value="/generatePaybill/{paybillGenerationTrnId}/{userId}")	// , method = RequestMethod.POST
	public String generatePaybill(@PathVariable int paybillGenerationTrnId,@PathVariable Long userId,Model model,Locale locale,HttpServletRequest request) {
		
		String clientIP = request.getRemoteAddr();
		
		 String clientHostname = request.getRemoteHost();
		   
		 
		 String namePIp=clientHostname+"/"+clientIP;
		 
		 System.out.println(namePIp);
		
		PaybillGenerationTrnEntity paybillGenerationTrnEntity = paybillGenerationTrnService.generatePaybill(paybillGenerationTrnId,userId,namePIp);
		if(paybillGenerationTrnEntity != null) {
			model.addAttribute("payBillViewApprDelBillModel", new PayBillViewApprDelBillModel());
			model.addAttribute("language", locale.getLanguage());
		}
		return "views/paybill/paybill-view-approve-delete-bill";
	}
	
	
	
	@GetMapping("/generateChangeStatementReport/{paybillGenerationTrnId}")
	 public String generateChangeStatementReport(@ModelAttribute("payBillViewApprDelBillModel") PayBillViewApprDelBillModel payBillViewApprDelBillModel, @PathVariable String paybillGenerationTrnId,
	  Model model, Locale locale, HttpSession session,HttpServletResponse response) throws DocumentException, IOException {
	  //logger.info( " PaybillGenerationTrnId : " + paybillGenerationTrnId);
	 table.flushContent();
		listChangeStatementModel.clear();
		 ByteArrayOutputStream out = new ByteArrayOutputStream();
		 OrgUserMst messages = (OrgUserMst) session.getAttribute("MY_SESSION_MESSAGES"); 
			SimpleDateFormat sdf =new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
			 List < Object[] > listA =null;
	   listA = paybillGenerationTrnService.getChangeStatementReport(paybillGenerationTrnId);
	  HeaderFooterPageEvent event = new HeaderFooterPageEvent();
	  // logger.info("controller called"+event.getTableHeight());
	  Document document = new Document(PageSize.A4, 36, 36, 170, 50);
	  String fileName ="change-statement-report.pdf";
	  File myFile = new File("change-statement-report.pdf");
	  FileOutputStream fileOut = new FileOutputStream(myFile);
	  String absolutePath = myFile.getAbsolutePath();
	  PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(myFile));
	  
	  // add header and footer
	  PdfWriter.getInstance(document, out);
	  writer.setPageEvent(event);
	  // write to document
	  document.open();
	  table.setWidthPercentage(100); //Width 100%
	  // table.setSpacingBefore(80000000f); //Space before table
	  //table.setSpacingAfter(400000f); //Space after table
	  //  table.setPaddingTop(100);

	  //Set Column widths
	  float[] columnWidths = { 0.8f, 4f, 2f,2f,2f, 2f,2f};
	  table.setWidths(columnWidths);
	  
	  String ddoname=null;
		String orgname=null;		
		String ofcname=null;
		String monname=null;
		String premonname=null;
		Long regid=null;
		Long ofcid=null;
		List<Object[]>  ddoinfo = paybillGenerationTrnService.findDDOinfo(messages.getUserName());

		for (Object[] objddoLst : ddoinfo) {
			// String empName;
			regid = Long.parseLong(objddoLst[0].toString());
			 ddoname = objddoLst[1].toString();
			 ofcid = Long.parseLong(objddoLst[2].toString());
			
		}
		List<Object[]>  regIdinfo = paybillGenerationTrnService.findregIdinfo(regid);
		for (Object[] objorgLst : regIdinfo) {
			// String empName;
			orgname = objorgLst[2].toString();
			
		}
		
		List<Object[]>  ofcIdinfo = paybillGenerationTrnService.findofcIdinfo(ofcid);
		for (Object[] ofcLst : ofcIdinfo) {
			// String empName;
			ofcname = ofcLst[2].toString();
			
		}
		

	  
	  PdfPCell cellnew1 = new PdfPCell(new Paragraph(""));
	  cellnew1.setColspan(2);
	  
	  PdfPCell cellnew2 = new PdfPCell(new Paragraph("Treasury:- " +ofcname));
	  cellnew2.setColspan(5);
	  cellnew2.setHorizontalAlignment(Element.ALIGN_RIGHT);
	  cellnew2.setVerticalAlignment(Element.ALIGN_RIGHT);
	  table.addCell(cellnew1);
	  table.addCell(cellnew2);
	  
	  PdfPCell cella1 = new PdfPCell(new Paragraph("Designation of Drawing and Disbursement Officer:-" + ddoname));
	  cella1.setColspan(5);
	   PdfPCell cella3 = new PdfPCell(new Paragraph("" ));
	   cella3.setColspan(2);
	   cella1.setHorizontalAlignment(Element.ALIGN_LEFT);
	   cella1.setVerticalAlignment(Element.ALIGN_LEFT);
	  table.addCell(cella1);
	  table.addCell(cella3);
	  
	  PdfPCell cellb1 = new PdfPCell(new Paragraph("Payment Name:-" + orgname));
	  cellb1.setColspan(5);
	   PdfPCell cellb3 = new PdfPCell(new Paragraph("" ));
	   cellb1.setHorizontalAlignment(Element.ALIGN_LEFT);
	   cellb1.setVerticalAlignment(Element.ALIGN_LEFT);
	   cellb3.setColspan(2);
	  table.addCell(cellb1);
	  table.addCell(cellb3);
	  
	  PdfPCell cell1 = new PdfPCell(new Paragraph("Sr no."));
	  cell1.setBorderColor(BaseColor.BLACK);
	  //cell1.setPaddingLeft(10);
	  cell1.setHorizontalAlignment(Element.ALIGN_LEFT);
	  cell1.setVerticalAlignment(Element.ALIGN_LEFT);

	  PdfPCell cell2 = new PdfPCell(new Paragraph("Employee Name and Designation"));
	  cell2.setBorderColor(BaseColor.BLACK);
	  // cell2.setPaddingLeft(10);
	  cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
	  cell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
	  PdfPCell cell3 = new PdfPCell(new Paragraph("Bill Type"));
	  cell3.setBorderColor(BaseColor.BLACK);
	  //   cell3.setPaddingLeft(10);
	  cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
	  cell3.setVerticalAlignment(Element.ALIGN_MIDDLE);
	  PdfPCell cell4 = new PdfPCell(new Paragraph("Previous Month Amount"));
	  cell4.setBorderColor(BaseColor.BLACK);
	  //   cell3.setPaddingLeft(10);
	  cell4.setHorizontalAlignment(Element.ALIGN_CENTER);
	  cell4.setVerticalAlignment(Element.ALIGN_MIDDLE);
	  PdfPCell cell5 = new PdfPCell(new Paragraph("Current Month Amount"));
	  cell5.setBorderColor(BaseColor.BLACK);
	  //   cell3.setPaddingLeft(10);
	  cell5.setHorizontalAlignment(Element.ALIGN_CENTER);
	  cell5.setVerticalAlignment(Element.ALIGN_MIDDLE);
	  PdfPCell cell6 = new PdfPCell(new Paragraph("Difference"));
	  cell6.setBorderColor(BaseColor.BLACK);
	  //   cell3.setPaddingLeft(10);
	  cell6.setHorizontalAlignment(Element.ALIGN_CENTER);
	  cell6.setVerticalAlignment(Element.ALIGN_MIDDLE);
	  PdfPCell cell7 = new PdfPCell(new Paragraph("Date and Time"));
	  cell7.setBorderColor(BaseColor.BLACK);
	  //   cell3.setPaddingLeft(10);
	  cell7.setHorizontalAlignment(Element.ALIGN_CENTER);
	  cell7.setVerticalAlignment(Element.ALIGN_MIDDLE);
	  table.addCell(cell1);
	  table.addCell(cell2);
	  table.addCell(cell3);
	  table.addCell(cell4);
	  table.addCell(cell5);
	  table.addCell(cell6);
	  table.addCell(cell7);
	   
	  int i = 0;
	  Double curAll = 0D;
		Double preAll = 0D;
		Double alldedtotal = 0D;
		Double allprededtotal = 0D;
		Double allcurnettotal = 0D;
		Double allprenettotal = 0D;
		BigInteger curryear = null;
		BigInteger currmonth = null;
		BigInteger preyear = null;
		BigInteger premonth = null;
		String createddate=null;
		Long cumo=0l;
	  for (Object[] objLst: listA) {
		  ChangeStatementModel changeStatementModel=new ChangeStatementModel();
	   //String empName;
	   String date ="";
	//   String date =StringHelperUtils.isNullString(objLst[79]);
	   createddate = StringHelperUtils.isNullString(objLst[79]);
	   String  empName = StringHelperUtils.isNullString(objLst[1]) + "(" + StringHelperUtils.isNullString(objLst[2]) + ")";
	   currmonth=StringHelperUtils.isNullBigInteger(objLst[4]);
		curryear=StringHelperUtils.isNullBigInteger(objLst[3]);
		premonth=StringHelperUtils.isNullBigInteger(objLst[6]);
		preyear=StringHelperUtils.isNullBigInteger(objLst[5]);
		 cumo =StringHelperUtils.isNullBigInteger(objLst[4]).longValue();
		Long netpay =StringHelperUtils.isNullBigInteger(objLst[56]).longValue();
		if(netpay!=null && netpay.equals(0l)) {
			 if (objLst[55] != null &&  objLst[56] != null) {
					i++;
					Double curGrossAmount =  (Double.parseDouble(objLst[55].toString()));
					Double preGrossAmount =  (Double.parseDouble(objLst[56].toString()));
					Double grossDiff = curGrossAmount-preGrossAmount;
					if (grossDiff  != 0) {
				     BaseColor br = BaseColor.RED;
				     drawTable(i, empName, "Net Pay Amount", preGrossAmount, curGrossAmount, grossDiff,date, br);
				     changeStatementModel.setEmpName(empName);
				     changeStatementModel.setComponentName("Net Pay Amount");
				     changeStatementModel.setPreBasic(curGrossAmount.toString());
				     changeStatementModel.setCurBasic(preGrossAmount.toString());
				     changeStatementModel.setBasicDiff(grossDiff.toString());
				     changeStatementModel.setDate(date);
				     changeStatementModel.setColor("red");
				     listChangeStatementModel.add(changeStatementModel);
				    } else {
				     BaseColor br = BaseColor.GREEN;
//				     drawTable(i, empName, "Net Pay Amount", preGrossAmount, curGrossAmount, grossDiff,date, br);
				    }
					curAll = curAll+(Double.parseDouble(objLst[55].toString()));
					 preAll =allprenettotal+( Double.parseDouble(objLst[56].toString()));
					 alldedtotal =alldedtotal+( Double.parseDouble(objLst[59].toString()));
					 allprededtotal =allprededtotal+( Double.parseDouble(objLst[60].toString()));
					 allcurnettotal =allcurnettotal+( Double.parseDouble(objLst[53].toString()));
					 allprenettotal =allprenettotal+(Double.parseDouble( objLst[54].toString()));
				   }
		}else {
			if (objLst[7] != null &&  objLst[8] != null) {
			i++;
			Double curBasic =  (Double.parseDouble(objLst[7].toString()));
			Double preBasic =  (Double.parseDouble(objLst[8].toString()));
			Double basicDiff = curBasic-preBasic;
			if (basicDiff  != 0) {
	     BaseColor br = BaseColor.RED;
	     drawTable(i, empName, "Basic Pay", preBasic, curBasic, basicDiff,date, br);
	     changeStatementModel.setEmpName(empName);
	     changeStatementModel.setComponentName("Basic Pay");
	     changeStatementModel.setPreBasic(preBasic.toString());
	     changeStatementModel.setCurBasic(curBasic.toString());
	     changeStatementModel.setBasicDiff(basicDiff.toString());
	     changeStatementModel.setDate(date);
	     changeStatementModel.setColor("red");
	     listChangeStatementModel.add(changeStatementModel);
	    } else {
	     BaseColor br = BaseColor.GREEN;
	    // drawTable(i, empName, "Basic Pay", preBasic, curBasic, basicDiff,date, br);
	    }
	   }
	   if (objLst[9] != null &&  objLst[10] != null) {
			i++;
			Double curDa =  (Double.parseDouble(objLst[9].toString()));
			Double preDa =  (Double.parseDouble(objLst[10].toString()));
			Double daDiff = curDa-preDa;
			if (daDiff  != 0) {
		     BaseColor br = BaseColor.RED;
		     drawTable(i, empName, "DA", preDa, curDa, daDiff,date, br);
		     changeStatementModel.setEmpName(empName);
		     changeStatementModel.setComponentName("Da");
		     changeStatementModel.setPreBasic(preDa.toString());
		     changeStatementModel.setCurBasic(curDa.toString());
		     changeStatementModel.setBasicDiff(daDiff.toString());
		     changeStatementModel.setDate(date);
		     changeStatementModel.setColor("red");
		     listChangeStatementModel.add(changeStatementModel);
		    } else {
		     BaseColor br = BaseColor.GREEN;
//		     drawTable(i, empName, "DA", preDa, curDa, daDiff,date, br);
		    }
		   }
	   if (objLst[11] != null &&  objLst[12] != null) {
			i++;
			Double curHra =  (Double.parseDouble(objLst[11].toString()));
			Double preHra =  (Double.parseDouble(objLst[12].toString()));
			Double sub = curHra-preHra;
			if (sub  != 0) {
	     BaseColor br = BaseColor.RED;
	     drawTable(i, empName, "HRA", preHra, curHra, sub,date, br);
	     changeStatementModel.setEmpName(empName);
	     changeStatementModel.setComponentName("HRA");
	     changeStatementModel.setPreBasic(preHra.toString());
	     changeStatementModel.setCurBasic(curHra.toString());
	     changeStatementModel.setBasicDiff(sub.toString());
	     changeStatementModel.setDate(date);
	     changeStatementModel.setColor("red");
	     listChangeStatementModel.add(changeStatementModel);
	    } else {
	     BaseColor br = BaseColor.GREEN;
//	     drawTable(i, empName, "HRA", preHra, curHra, sub,date, br);
	    }
	   }
	   if (objLst[13] != null &&  objLst[14] != null) {
			i++;
			Double curCla =  (Double.parseDouble(objLst[13].toString()));
			Double preCla =  (Double.parseDouble(objLst[14].toString()));
			Double claDiff = curCla-preCla;
			if (claDiff  != 0) {
		     BaseColor br = BaseColor.RED;
		     drawTable(i, empName, "CLA", preCla, curCla, claDiff,date, br);
		     changeStatementModel.setEmpName(empName);
		     changeStatementModel.setComponentName("CLA");
		     changeStatementModel.setPreBasic(preCla.toString());
		     changeStatementModel.setCurBasic(curCla.toString());
		     changeStatementModel.setBasicDiff(claDiff.toString());
		     changeStatementModel.setDate(date);
		     changeStatementModel.setColor("red");
		     listChangeStatementModel.add(changeStatementModel);
		    } else {
		     BaseColor br = BaseColor.GREEN;
//		     drawTable(i, empName, "CLA", preCla, curCla, claDiff,date, br);
		    }
		   }
	   if (objLst[15] != null &&  objLst[16] != null) {
			i++;
			Double curIt =  (Double.parseDouble(objLst[15].toString()));
			Double preIt =  (Double.parseDouble(objLst[16].toString()));
			Double ItDiff = curIt-preIt;
			if (ItDiff  != 0) {
		     BaseColor br = BaseColor.RED;
		     drawTable(i, empName, "IT", preIt, curIt, ItDiff,date, br);
		     changeStatementModel.setEmpName(empName);
		     changeStatementModel.setComponentName("IT");
		     changeStatementModel.setPreBasic(preIt.toString());
		     changeStatementModel.setCurBasic(curIt.toString());
		     changeStatementModel.setBasicDiff(ItDiff.toString());
		     changeStatementModel.setDate(date);
		     changeStatementModel.setColor("red");
		     listChangeStatementModel.add(changeStatementModel);
		    } else {
		     BaseColor br = BaseColor.GREEN;
//		     drawTable(i, empName, "IT", preIt, curIt, ItDiff,date, br);
		    }
		   }
	   if (objLst[17] != null &&  objLst[18] != null) {
			i++;
			Double curHrr =  (Double.parseDouble(objLst[17].toString()));
			Double preHrr =  (Double.parseDouble(objLst[18].toString()));
			Double hrrDiff = curHrr-preHrr;
			if (hrrDiff  != 0) {
		     BaseColor br = BaseColor.RED;
		     drawTable(i, empName, "HRR", preHrr, curHrr, hrrDiff,date, br);
		     changeStatementModel.setEmpName(empName);
		     changeStatementModel.setComponentName("HRR");
		     changeStatementModel.setPreBasic(preHrr.toString());
		     changeStatementModel.setCurBasic(curHrr.toString());
		     changeStatementModel.setBasicDiff(hrrDiff.toString());
		     changeStatementModel.setDate(date);
		     changeStatementModel.setColor("red");
		     listChangeStatementModel.add(changeStatementModel);
		    } else {
		     BaseColor br = BaseColor.GREEN;
//		     drawTable(i, empName, "HRR", preHrr, curHrr, hrrDiff,date, br);
		    }
		   }
	   if (objLst[19] != null &&  objLst[20] != null) {
			i++;
			Double curPli =  (Double.parseDouble(objLst[19].toString()));
			Double prepli =  (Double.parseDouble(objLst[20].toString()));
			Double pliDiff = curPli-prepli;
			if (pliDiff  != 0) {
	   	     BaseColor br = BaseColor.RED;
		     drawTable(i, empName, "PLI", prepli, curPli, pliDiff,date, br);
		     changeStatementModel.setEmpName(empName);
		     changeStatementModel.setComponentName("PLI");
		     changeStatementModel.setPreBasic(prepli.toString());
		     changeStatementModel.setCurBasic(curPli.toString());
		     changeStatementModel.setBasicDiff(pliDiff.toString());
		     changeStatementModel.setDate(date);
		     changeStatementModel.setColor("red");
		     listChangeStatementModel.add(changeStatementModel);
		    } else {
		     BaseColor br = BaseColor.GREEN;
//		     drawTable(i, empName, "PLI", prepli, curPli, pliDiff,date, br);
		    }
		   }
	   if (objLst[21] != null &&  objLst[22] != null) {
			i++;
			Double curPt =  (Double.parseDouble(objLst[21].toString()));
			Double prept =  (Double.parseDouble(objLst[22].toString()));
			Double ptDiff = curPt-prept;
			if (ptDiff  != 0) {
	  	     BaseColor br = BaseColor.RED;
		     drawTable(i, empName, "PT", prept, curPt, ptDiff,date, br);
		     changeStatementModel.setEmpName(empName);
		     changeStatementModel.setComponentName("PT");
		     changeStatementModel.setPreBasic(prept.toString());
		     changeStatementModel.setCurBasic(curPt.toString());
		     changeStatementModel.setBasicDiff(ptDiff.toString());
		     changeStatementModel.setDate(date);
		     changeStatementModel.setColor("red");
		     listChangeStatementModel.add(changeStatementModel);
		    } else {
		     BaseColor br = BaseColor.GREEN;
//		     drawTable(i, empName, "PT", prept, curPt, ptDiff,date, br);
		    }
		   }
	   if (objLst[23] != null &&  objLst[24] != null) {
			i++;
			Double curHba =  (Double.parseDouble(objLst[23].toString()));
			Double preHba =  (Double.parseDouble(objLst[24].toString()));
			Double hbaDiff = curHba-preHba;
			if (hbaDiff  != 0) {
	  	     BaseColor br = BaseColor.RED;
		     drawTable(i, empName, "HBA", preHba, curHba, hbaDiff,date, br);
		     changeStatementModel.setEmpName(empName);
		     changeStatementModel.setComponentName("HBA");
		     changeStatementModel.setPreBasic(preHba.toString());
		     changeStatementModel.setCurBasic(curHba.toString());
		     changeStatementModel.setBasicDiff(hbaDiff.toString());
		     changeStatementModel.setDate(date);
		     changeStatementModel.setColor("red");
		     listChangeStatementModel.add(changeStatementModel);
		    } else {
		     BaseColor br = BaseColor.GREEN;
//		     drawTable(i, empName, "HBA", preHba, curHba, hbaDiff,date, br);
		    }
		   }
	   if (objLst[25] != null &&  objLst[26] != null) {
			i++;
			Double curGpfAdv =  (Double.parseDouble(objLst[25].toString()));
			Double preGpfAdv =  (Double.parseDouble(objLst[26].toString()));
			Double gpfAdvDiff = curGpfAdv-preGpfAdv;
			if (gpfAdvDiff  != 0) {
	   	     BaseColor br = BaseColor.RED;
		     drawTable(i, empName, "GPF Advance", preGpfAdv, curGpfAdv, gpfAdvDiff,date, br);
		     changeStatementModel.setEmpName(empName);
		     changeStatementModel.setComponentName("GPF");
		     changeStatementModel.setPreBasic(preGpfAdv.toString());
		     changeStatementModel.setCurBasic(curGpfAdv.toString());
		     changeStatementModel.setBasicDiff(gpfAdvDiff.toString());
		     changeStatementModel.setDate(date);
		     changeStatementModel.setColor("red");
		     listChangeStatementModel.add(changeStatementModel);
		    } else {
		     BaseColor br = BaseColor.GREEN;
//		     drawTable(i, empName, "GPF Advance", preGpfAdv, curGpfAdv, gpfAdvDiff,date, br);
		    }
		   }
	   if (objLst[27] != null &&  objLst[28] != null) {
			i++;
			Double curGpfIvAdv =  (Double.parseDouble(objLst[27].toString()));
			Double preGpfIvAdv =  (Double.parseDouble(objLst[28].toString()));
			Double gpfIvAdvDiff = curGpfIvAdv-preGpfIvAdv;
			if (gpfIvAdvDiff  != 0) {
	   	     BaseColor br = BaseColor.RED;
		     drawTable(i, empName, "Gpf Iv Advance", preGpfIvAdv, curGpfIvAdv, gpfIvAdvDiff,date, br);
		     changeStatementModel.setEmpName(empName);
		     changeStatementModel.setComponentName("Gpf Iv Advance");
		     changeStatementModel.setPreBasic(preGpfIvAdv.toString());
		     changeStatementModel.setCurBasic(curGpfIvAdv.toString());
		     changeStatementModel.setBasicDiff(gpfIvAdvDiff.toString());
		     changeStatementModel.setDate(date);
		     changeStatementModel.setColor("red");
		     listChangeStatementModel.add(changeStatementModel);
		    } else {
		     BaseColor br = BaseColor.GREEN;
//		     drawTable(i, empName, "Gpf Iv Advance", preGpfIvAdv, curGpfIvAdv, gpfIvAdvDiff,date, br);
		    }
		   }
	   if (objLst[29] != null &&  objLst[30] != null) {
			i++;
			Double curDcps =  (Double.parseDouble(objLst[29].toString()));
			Double preDcps =  (Double.parseDouble(objLst[30].toString()));
			Double dcpsDiff = curDcps-preDcps;
			if (dcpsDiff  != 0) {
	  	     BaseColor br = BaseColor.RED;
		     drawTable(i, empName, "DCPS", preDcps, curDcps, dcpsDiff,date, br);
		     changeStatementModel.setEmpName(empName);
		     changeStatementModel.setComponentName("DCPS");
		     changeStatementModel.setPreBasic(preDcps.toString());
		     changeStatementModel.setCurBasic(curDcps.toString());
		     changeStatementModel.setBasicDiff(dcpsDiff.toString());
		     changeStatementModel.setDate(date);
		     changeStatementModel.setColor("red");
		     listChangeStatementModel.add(changeStatementModel);
		    } else {
		     BaseColor br = BaseColor.GREEN;
//		     drawTable(i, empName, "DCPS", preDcps, curDcps, dcpsDiff,date, br);
		    }
		   }
	   if (objLst[31] != null &&  objLst[32] != null) {
			i++;
			Double curGpay =  (Double.parseDouble(objLst[31].toString()));
			Double preGpay =  (Double.parseDouble(objLst[32].toString()));
			Double gpayDiff = curGpay-preGpay;
			if (gpayDiff  != 0) {
	  	     BaseColor br = BaseColor.RED;
		     drawTable(i, empName, "GPAY", preGpay, curGpay, gpayDiff,date, br);
		     changeStatementModel.setEmpName(empName);
		     changeStatementModel.setComponentName("GPAY");
		     changeStatementModel.setPreBasic(preGpay.toString());
		     changeStatementModel.setCurBasic(curGpay.toString());
		     changeStatementModel.setBasicDiff(gpayDiff.toString());
		     changeStatementModel.setDate(date);
		     changeStatementModel.setColor("red");
		     listChangeStatementModel.add(changeStatementModel);
		    } else {
		     BaseColor br = BaseColor.GREEN;
//		     drawTable(i, empName, "GPAY", preGpay, curGpay, gpayDiff,date, br);
		    }
		   }
	   if (objLst[33] != null &&  objLst[34] != null) {
			i++;
			Double curGpfGrpAbc =  (Double.parseDouble(objLst[33].toString()));
			Double preGpfGrpAbc =  (Double.parseDouble(objLst[34].toString()));
			Double gpfGrpAbcDiff = curGpfGrpAbc-preGpfGrpAbc;
			if (gpfGrpAbcDiff  != 0) {
	   	     BaseColor br = BaseColor.RED;
		     drawTable(i, empName, "Gpf Grp Abc", preGpfGrpAbc, curGpfGrpAbc, gpfGrpAbcDiff,date, br);
		     changeStatementModel.setEmpName(empName);
		     changeStatementModel.setComponentName("Gpf Grp Abc");
		     changeStatementModel.setPreBasic(preGpfGrpAbc.toString());
		     changeStatementModel.setCurBasic(curGpfGrpAbc.toString());
		     changeStatementModel.setBasicDiff(gpfGrpAbcDiff.toString());
		     changeStatementModel.setDate(date);
		     changeStatementModel.setColor("red");
		     listChangeStatementModel.add(changeStatementModel);
		    } else {
		     BaseColor br = BaseColor.GREEN;
//		     drawTable(i, empName, "Gpf Grp Abc", preGpfGrpAbc, curGpfGrpAbc, gpfGrpAbcDiff,date, br);
		    }
		   }
	   if (objLst[35] != null &&  objLst[36] != null) {
			i++;
			Double curGpfGrpD =  (Double.parseDouble(objLst[35].toString()));
			Double preGpfGrpD =  (Double.parseDouble(objLst[36].toString()));
			Double gpfGrpDDiff = curGpfGrpD-preGpfGrpD;
			if (gpfGrpDDiff  != 0) {
	  	     BaseColor br = BaseColor.RED;
		     drawTable(i, empName, "Gpf Grp D", preGpfGrpD, curGpfGrpD, gpfGrpDDiff,date, br);
		     changeStatementModel.setEmpName(empName);
		     changeStatementModel.setComponentName("Gpf Grp D");
		     changeStatementModel.setPreBasic(preGpfGrpD.toString());
		     changeStatementModel.setCurBasic(curGpfGrpD.toString());
		     changeStatementModel.setBasicDiff(gpfGrpDDiff.toString());
		     changeStatementModel.setDate(date);
		     changeStatementModel.setColor("red");
		     listChangeStatementModel.add(changeStatementModel);
		    } else {
		     BaseColor br = BaseColor.GREEN;
//		     drawTable(i, empName, "Gpf Grp D", preGpfGrpD, curGpfGrpD, gpfGrpDDiff,date, br);
		    }
		   }
	   if (objLst[37] != null &&  objLst[38] != null) {
			i++;
			Double curGpfAdvGrpAbc =  (Double.parseDouble(objLst[37].toString()));
			Double preGpfAdvGrpAbc =  (Double.parseDouble(objLst[38].toString()));
			Double gpfAdvGrpAbc = curGpfAdvGrpAbc-preGpfAdvGrpAbc;
			if (gpfAdvGrpAbc  != 0) {
	  	     BaseColor br = BaseColor.RED;
		     drawTable(i, empName, "Gpf Advance Grp Abc", preGpfAdvGrpAbc, curGpfAdvGrpAbc, gpfAdvGrpAbc,date, br);
		     changeStatementModel.setEmpName(empName);
		     changeStatementModel.setComponentName("Gpf Advance Grp Abc");
		     changeStatementModel.setPreBasic(preGpfAdvGrpAbc.toString());
		     changeStatementModel.setCurBasic(curGpfAdvGrpAbc.toString());
		     changeStatementModel.setBasicDiff(gpfAdvGrpAbc.toString());
		     changeStatementModel.setDate(date);
		     changeStatementModel.setColor("red");
		     listChangeStatementModel.add(changeStatementModel);
		    } else {
		     BaseColor br = BaseColor.GREEN;
//		     drawTable(i, empName, "Gpf Advance Grp Abc", preGpfAdvGrpAbc, curGpfAdvGrpAbc, gpfAdvGrpAbc,date, br);
		    }
		   }
	   if (objLst[39] != null &&  objLst[40] != null) {
			i++;
			Double curGpfAdvGrpD =  (Double.parseDouble(objLst[39].toString()));
			Double preGpfAdvGrpD =  (Double.parseDouble(objLst[40].toString()));
			Double gpfAdvGrpDDiff = curGpfAdvGrpD-preGpfAdvGrpD;
			if (gpfAdvGrpDDiff  != 0) {
	   	     BaseColor br = BaseColor.RED;
		     drawTable(i, empName, "Gpf Advance Grp D", preGpfAdvGrpD, curGpfAdvGrpD, gpfAdvGrpDDiff,date, br);
		     changeStatementModel.setEmpName(empName);
		     changeStatementModel.setComponentName("Gpf Advance Grp D");
		     changeStatementModel.setPreBasic(preGpfAdvGrpD.toString());
		     changeStatementModel.setCurBasic(curGpfAdvGrpD.toString());
		     changeStatementModel.setBasicDiff(gpfAdvGrpDDiff.toString());
		     changeStatementModel.setDate(date);
		     changeStatementModel.setColor("red");
		     listChangeStatementModel.add(changeStatementModel);
		    } else {
		     BaseColor br = BaseColor.GREEN;
//		     drawTable(i, empName, "Gpf Advance Grp D", preGpfAdvGrpD, curGpfAdvGrpD, gpfAdvGrpDDiff,date, br);
		    }
		   }
	   if (objLst[41] != null &&  objLst[42] != null) {
			i++;
			Double curMotorCycleAdv =  (Double.parseDouble(objLst[41].toString()));
			Double preMotorCycleAdv =  (Double.parseDouble(objLst[42].toString()));
			Double MotorCycleAdvDiff = curMotorCycleAdv-preMotorCycleAdv;
			if (MotorCycleAdvDiff  != 0) {
	  	     BaseColor br = BaseColor.RED;
		     drawTable(i, empName, "Motor Cycle Advance", preMotorCycleAdv, curMotorCycleAdv, MotorCycleAdvDiff,date, br);
		     changeStatementModel.setEmpName(empName);
		     changeStatementModel.setComponentName("Motor Cycle Advance");
		     changeStatementModel.setPreBasic(preMotorCycleAdv.toString());
		     changeStatementModel.setCurBasic(curMotorCycleAdv.toString());
		     changeStatementModel.setBasicDiff(MotorCycleAdvDiff.toString());
		     changeStatementModel.setDate(date);
		     changeStatementModel.setColor("red");
		     listChangeStatementModel.add(changeStatementModel);
		    } else {
		     BaseColor br = BaseColor.GREEN;
//		     drawTable(i, empName, "Motor Cycle Advance", preMotorCycleAdv, curMotorCycleAdv, MotorCycleAdvDiff,date, br);
		    }
		   }
	   if (objLst[43] != null &&  objLst[44] != null) {
			i++;
			Double curOtherDedTry =  (Double.parseDouble(objLst[43].toString()));
			Double preOtherDedTry =  (Double.parseDouble(objLst[44].toString()));
			Double otherDedTryDiff = curOtherDedTry-preOtherDedTry;
			if (otherDedTryDiff  != 0) {
	   	     BaseColor br = BaseColor.RED;
		     drawTable(i, empName, "Other ded try", preOtherDedTry, curOtherDedTry, otherDedTryDiff,date, br);
		     changeStatementModel.setEmpName(empName);
		     changeStatementModel.setComponentName("Other ded try");
		     changeStatementModel.setPreBasic(preOtherDedTry.toString());
		     changeStatementModel.setCurBasic(curOtherDedTry.toString());
		     changeStatementModel.setBasicDiff(otherDedTryDiff.toString());
		     changeStatementModel.setDate(date);
		     changeStatementModel.setColor("red");
		     listChangeStatementModel.add(changeStatementModel);
		    } else {
		     BaseColor br = BaseColor.GREEN;
//		     drawTable(i, empName, "Other ded try", preOtherDedTry, curOtherDedTry, otherDedTryDiff,date, br);
		    }
		   }
	   if (objLst[45] != null &&  objLst[46] != null) {
			i++;
			Double curJanjulgis =  (Double.parseDouble(objLst[45].toString()));
			Double preJanjulgis =  (Double.parseDouble(objLst[46].toString()));
			Double janjulgisDiff = curJanjulgis-preJanjulgis;
			if (janjulgisDiff  != 0) {
	  	     BaseColor br = BaseColor.RED;
		     drawTable(i, empName, "Janjulgis", preJanjulgis, curJanjulgis, janjulgisDiff,date, br);
		     changeStatementModel.setEmpName(empName);
		     changeStatementModel.setComponentName("Janjulgis");
		     changeStatementModel.setPreBasic(preJanjulgis.toString());
		     changeStatementModel.setCurBasic(curJanjulgis.toString());
		     changeStatementModel.setBasicDiff(janjulgisDiff.toString());
		     changeStatementModel.setDate(date);
		     changeStatementModel.setColor("red");
		     listChangeStatementModel.add(changeStatementModel);
		    } else {
		     BaseColor br = BaseColor.GREEN;
//		     drawTable(i, empName, "Janjulgis", preJanjulgis, curJanjulgis, janjulgisDiff,date, br);
		    }
		   }
	   
	   if (objLst[47] != null &&  objLst[48] != null) {
			i++;
			Double curJanjulgis =  (Double.parseDouble(objLst[47].toString()));
			Double preJanjulgis =  (Double.parseDouble(objLst[48].toString()));
			Double janjulgisDiff = curJanjulgis-preJanjulgis;
			if (janjulgisDiff  != 0) {
	   	     BaseColor br = BaseColor.RED;
		     drawTable(i, empName, "Janjulgis Diff", preJanjulgis, curJanjulgis, janjulgisDiff,date, br);
		     changeStatementModel.setEmpName(empName);
		     changeStatementModel.setComponentName("Janjulgis Diff");
		     changeStatementModel.setPreBasic(preJanjulgis.toString());
		     changeStatementModel.setCurBasic(curJanjulgis.toString());
		     changeStatementModel.setBasicDiff(janjulgisDiff.toString());
		     changeStatementModel.setDate(date);
		     changeStatementModel.setColor("red");
		     listChangeStatementModel.add(changeStatementModel);
		    } else {
		     BaseColor br = BaseColor.GREEN;
//		     drawTable(i, empName, "Janjulgis Diff", preJanjulgis, curJanjulgis, janjulgisDiff,date, br);
		    }
		   }
	   if (objLst[49] != null &&  objLst[50] != null) {
			i++;
			Double curGrossAdjust =  (Double.parseDouble(objLst[49].toString()));
			Double preGrossAdjust =  (Double.parseDouble(objLst[50].toString()));
			Double grossAdjustDiff = curGrossAdjust-preGrossAdjust;
			if (grossAdjustDiff  != 0) {
	   		     BaseColor br = BaseColor.RED;
		     drawTable(i, empName, "Janjulgis adjust", preGrossAdjust, curGrossAdjust, grossAdjustDiff,date, br);
		     changeStatementModel.setEmpName(empName);
		     changeStatementModel.setComponentName("Janjulgis adjust");
		     changeStatementModel.setPreBasic(preGrossAdjust.toString());
		     changeStatementModel.setCurBasic(curGrossAdjust.toString());
		     changeStatementModel.setBasicDiff(grossAdjustDiff.toString());
		     changeStatementModel.setDate(date);
		     changeStatementModel.setColor("red");
		     listChangeStatementModel.add(changeStatementModel);
		    } else {
		     BaseColor br = BaseColor.GREEN;
//		     drawTable(i, empName, "Janjulgis adjust", preGrossAdjust, curGrossAdjust, grossAdjustDiff,date, br);
		    }
		   }
	   
	   if (objLst[51] != null &&  objLst[52] != null) {
			i++;
			Double curGrossTotalAmount =  (Double.parseDouble(objLst[51].toString()));
			Double preGrossTotalAmount =  (Double.parseDouble(objLst[52].toString()));
			Double grossTotalAmountDiff = curGrossTotalAmount-preGrossTotalAmount;
			if (grossTotalAmountDiff  != 0) {
	 	     BaseColor br = BaseColor.RED;
		     drawTable(i, empName, "Gross Total Amount", preGrossTotalAmount, curGrossTotalAmount, grossTotalAmountDiff,date, br);
		     changeStatementModel.setEmpName(empName);
		     changeStatementModel.setComponentName("Gross Total Amount");
		     changeStatementModel.setPreBasic(preGrossTotalAmount.toString());
		     changeStatementModel.setCurBasic(curGrossTotalAmount.toString());
		     changeStatementModel.setBasicDiff(grossTotalAmountDiff.toString());
		     changeStatementModel.setDate(date);
		     changeStatementModel.setColor("red");
		     listChangeStatementModel.add(changeStatementModel);
		    } else {
		     BaseColor br = BaseColor.GREEN;
//		     drawTable(i, empName, "Gross Total Amount", preGrossTotalAmount, curGrossTotalAmount, grossTotalAmountDiff,date, br);
		    }
		   }
	   if (objLst[53] != null &&  objLst[54] != null) {
			i++;
			Double curGrossTotalNetAmount =  (Double.parseDouble(objLst[53].toString()));
			Double preGrossTotalNetAmount =  (Double.parseDouble(objLst[54].toString()));
			Double grossTotalNetAmountDiff = curGrossTotalNetAmount-preGrossTotalNetAmount;
			if (grossTotalNetAmountDiff  != 0) {
	   	     BaseColor br = BaseColor.RED;
		     drawTable(i, empName, "Gross Total Net Amount", preGrossTotalNetAmount, curGrossTotalNetAmount, grossTotalNetAmountDiff,date, br);
		     changeStatementModel.setEmpName(empName);
		     changeStatementModel.setComponentName("Gross Total Net Amount");
		     changeStatementModel.setPreBasic(curGrossTotalNetAmount.toString());
		     changeStatementModel.setCurBasic(preGrossTotalNetAmount.toString());
		     changeStatementModel.setBasicDiff(grossTotalNetAmountDiff.toString());
		     changeStatementModel.setDate(date);
		     changeStatementModel.setColor("red");
		     listChangeStatementModel.add(changeStatementModel);
		    } else {
		     BaseColor br = BaseColor.GREEN;
//		     drawTable(i, empName, "Gross Total Net Amount", preGrossTotalNetAmount, curGrossTotalNetAmount, grossTotalNetAmountDiff,date, br);
		    }
		   }
	  
	   if (objLst[57] != null &&  objLst[58] != null) {
			i++;
			Double curTotalDeduction =  (Double.parseDouble(objLst[57].toString()));
			Double preTotalDeduction =  (Double.parseDouble(objLst[58].toString()));
			Double totalDeductionDiff = curTotalDeduction-preTotalDeduction;
			if (totalDeductionDiff  != 0) {
		     BaseColor br = BaseColor.RED;
		     drawTable(i, empName, "Total Deduction", preTotalDeduction, curTotalDeduction, totalDeductionDiff,date, br);
		     changeStatementModel.setEmpName(empName);
		     changeStatementModel.setComponentName("Total Deduction");
		     changeStatementModel.setPreBasic(preTotalDeduction.toString());
		     changeStatementModel.setCurBasic(curTotalDeduction.toString());
		     changeStatementModel.setBasicDiff(totalDeductionDiff.toString());
		     changeStatementModel.setDate(date);
		     changeStatementModel.setColor("red");
		     listChangeStatementModel.add(changeStatementModel);
		    } else {
		     BaseColor br = BaseColor.GREEN;
//		     drawTable(i, empName, "Total Deduction", preTotalDeduction, curTotalDeduction, totalDeductionDiff,date, br);
		    }
		   }
	   if (objLst[59] != null &&  objLst[60] != null) {
			i++;
			Double curAdjustdcpsEmployer =  (Double.parseDouble(objLst[59].toString()));
			Double preAdjustdcpsEmployer =  (Double.parseDouble(objLst[60].toString()));
			Double adjustdcpsEmployerDiff = curAdjustdcpsEmployer-preAdjustdcpsEmployer;
			if (adjustdcpsEmployerDiff  != 0) {
	  	     BaseColor br = BaseColor.RED;
		     drawTable(i, empName, "Adjust Dcps Employer", preAdjustdcpsEmployer, curAdjustdcpsEmployer, adjustdcpsEmployerDiff,date, br);
		     changeStatementModel.setEmpName(empName);
		     changeStatementModel.setComponentName("Adjust Dcps Employer");
		     changeStatementModel.setPreBasic(preAdjustdcpsEmployer.toString());
		     changeStatementModel.setCurBasic(curAdjustdcpsEmployer.toString());
		     changeStatementModel.setBasicDiff(adjustdcpsEmployerDiff.toString());
		     changeStatementModel.setDate(date);
		     changeStatementModel.setColor("red");
		     listChangeStatementModel.add(changeStatementModel);
		    } else {
		     BaseColor br = BaseColor.GREEN;
//		     drawTable(i, empName, "Adjust Dcps Employer", preAdjustdcpsEmployer, curAdjustdcpsEmployer, adjustdcpsEmployerDiff,date, br);
		    }
		   }
	   if (objLst[61] != null &&  objLst[62] != null) {
			i++;
			Double curDcpsArr =  (Double.parseDouble(objLst[73].toString()));
			Double preDcpsArr =  (Double.parseDouble(objLst[74].toString()));
			Double dcpsArrDiff = curDcpsArr-preDcpsArr;
			if (dcpsArrDiff  != 0) {
	        BaseColor br = BaseColor.RED;
		     drawTable(i, empName, "Dcps Arr", preDcpsArr, curDcpsArr, dcpsArrDiff,date, br);
		     changeStatementModel.setEmpName(empName);
		     changeStatementModel.setComponentName("Dcps Arr");
		     changeStatementModel.setPreBasic(preDcpsArr.toString());
		     changeStatementModel.setCurBasic(curDcpsArr.toString());
		     changeStatementModel.setBasicDiff(dcpsArrDiff.toString());
		     changeStatementModel.setDate(date);
		     changeStatementModel.setColor("red");
		     listChangeStatementModel.add(changeStatementModel);
		    } else {
		     BaseColor br = BaseColor.GREEN;
//		     drawTable(i, empName, "Dcps Arr", preDcpsArr, curDcpsArr, dcpsArrDiff,date, br);
		    }
		   }
	   
	   if (objLst[63] != null &&  objLst[64] != null) {
			i++;
			Double curDcpsDaArr =  (Double.parseDouble(objLst[73].toString()));
			Double preDcpsDaArr =  (Double.parseDouble(objLst[74].toString()));
			Double dcpsDaArrDiff = curDcpsDaArr-preDcpsDaArr;
			if (dcpsDaArrDiff  != 0) {
	 	     BaseColor br = BaseColor.RED;
		     drawTable(i, empName, "Pre Dcps Da Arr", preDcpsDaArr, curDcpsDaArr, dcpsDaArrDiff,date, br);
		     changeStatementModel.setEmpName(empName);
		     changeStatementModel.setComponentName("Pre Dcps Da Arr");
		     changeStatementModel.setPreBasic(preDcpsDaArr.toString());
		     changeStatementModel.setCurBasic(curDcpsDaArr.toString());
		     changeStatementModel.setBasicDiff(dcpsDaArrDiff.toString());
		     changeStatementModel.setDate(date);
		     changeStatementModel.setColor("red");
		     listChangeStatementModel.add(changeStatementModel);
		    } else {
		     BaseColor br = BaseColor.GREEN;
//		     drawTable(i, empName, "Pre Dcps Da Arr", preDcpsDaArr, curDcpsDaArr, dcpsDaArrDiff,date, br);
		    }
		   }
	   if (objLst[65] != null &&  objLst[66] != null) {
			i++;
			Double curDcpsDelay =  (Double.parseDouble(objLst[73].toString()));
			Double preDcpsDelayt =  (Double.parseDouble(objLst[74].toString()));
			Double dcpsDelayDiff = curDcpsDelay-preDcpsDelayt;
			if (dcpsDelayDiff  != 0) {
	  	     BaseColor br = BaseColor.RED;
		     drawTable(i, empName, "Dcps Delay", preDcpsDelayt, curDcpsDelay, dcpsDelayDiff,date, br);
		     changeStatementModel.setEmpName(empName);
		     changeStatementModel.setComponentName("Dcps Delay");
		     changeStatementModel.setPreBasic(preDcpsDelayt.toString());
		     changeStatementModel.setCurBasic(curDcpsDelay.toString());
		     changeStatementModel.setBasicDiff(dcpsDelayDiff.toString());
		     changeStatementModel.setDate(date);
		     changeStatementModel.setColor("red");
		     listChangeStatementModel.add(changeStatementModel);
		    } else {
		     BaseColor br = BaseColor.GREEN;
//		     drawTable(i, empName, "Dcps Delay", preDcpsDelayt, curDcpsDelay, dcpsDelayDiff,date, br);
		    }
		   }
	   if (objLst[67] != null &&  objLst[68] != null) {
			i++;
			Double curDcpsEmployer =  (Double.parseDouble(objLst[73].toString()));
			Double preDcpsEmployer =  (Double.parseDouble(objLst[74].toString()));
			Double dcpsEmployerDiff = curDcpsEmployer-preDcpsEmployer;
			if (dcpsEmployerDiff  != 0) {
	  	     BaseColor br = BaseColor.RED;
		     drawTable(i, empName, "Dcps Employer", preDcpsEmployer, curDcpsEmployer, dcpsEmployerDiff,date, br);
		     changeStatementModel.setEmpName(empName);
		     changeStatementModel.setComponentName("Dcps Employer");
		     changeStatementModel.setPreBasic(preDcpsEmployer.toString());
		     changeStatementModel.setCurBasic(curDcpsEmployer.toString());
		     changeStatementModel.setBasicDiff(dcpsEmployerDiff.toString());
		     changeStatementModel.setDate(date);
		     changeStatementModel.setColor("red");
		     listChangeStatementModel.add(changeStatementModel);
		    } else {
		     BaseColor br = BaseColor.GREEN;
//		     drawTable(i, empName, "Dcps Employer", preDcpsEmployer, curDcpsEmployer, dcpsEmployerDiff,date, br);
		    }
		   }
	   
	   if (objLst[69] != null &&  objLst[70] != null) {
			i++;
			Double curDcpsPayArr =  (Double.parseDouble(objLst[73].toString()));
			Double preDcpsPayArr =  (Double.parseDouble(objLst[74].toString()));
			Double dcpsPayArrDiff = curDcpsPayArr-preDcpsPayArr;
			if (dcpsPayArrDiff  != 0) {
	 	     BaseColor br = BaseColor.RED;
		     drawTable(i, empName, "Dcps Pay Arr", preDcpsPayArr, curDcpsPayArr, dcpsPayArrDiff,date, br);
		     changeStatementModel.setEmpName(empName);
		     changeStatementModel.setComponentName("Dcps Pay Arr");
		     changeStatementModel.setPreBasic(preDcpsPayArr.toString());
		     changeStatementModel.setCurBasic(curDcpsPayArr.toString());
		     changeStatementModel.setBasicDiff(dcpsPayArrDiff.toString());
		     changeStatementModel.setDate(date);
		     changeStatementModel.setColor("red");
		     listChangeStatementModel.add(changeStatementModel);
		    } else {
		     BaseColor br = BaseColor.GREEN;
//		     drawTable(i, empName, "Dcps Pay Arr", preDcpsPayArr, curDcpsPayArr, dcpsPayArrDiff,date, br);
		    }
		   }
	   
	   if (objLst[71] != null &&  objLst[72] != null) {
			i++;
			Double curDcpsRegularRecovery =  (Double.parseDouble(objLst[73].toString()));
			Double preDcpsRegularRecovery =  (Double.parseDouble(objLst[74].toString()));
			Double dcpsRegularRecoveryDiff = curDcpsRegularRecovery-preDcpsRegularRecovery;
			if (dcpsRegularRecoveryDiff  != 0) {
	  	     BaseColor br = BaseColor.RED;
		     drawTable(i, empName, "Dcps Regular Recovery", preDcpsRegularRecovery, curDcpsRegularRecovery, dcpsRegularRecoveryDiff,date, br);
		     changeStatementModel.setEmpName(empName);
		     changeStatementModel.setComponentName("Dcps Regular Recovery");
		     changeStatementModel.setPreBasic(preDcpsRegularRecovery.toString());
		     changeStatementModel.setCurBasic(curDcpsRegularRecovery.toString());
		     changeStatementModel.setBasicDiff(dcpsRegularRecoveryDiff.toString());
		     changeStatementModel.setDate(date);
		     changeStatementModel.setColor("red");
		     listChangeStatementModel.add(changeStatementModel);
		    } else {
		     BaseColor br = BaseColor.GREEN;
//		     drawTable(i, empName, "Dcps Regular Recovery", preDcpsRegularRecovery, curDcpsRegularRecovery, dcpsRegularRecoveryDiff,date, br);
		    }
		   }
	   
	   if (objLst[73] != null
				&&  objLst[74] != null) {
			i++;
			Double curGis =  (Double.parseDouble(objLst[73].toString()));
			Double preGis =  (Double.parseDouble(objLst[74].toString()));
			Double gisDiff = curGis-preGis;
			if (gisDiff  != 0) {
		     BaseColor br = BaseColor.RED;
		     drawTable(i, empName, "GIS", preGis, curGis, gisDiff,date, br);
		     changeStatementModel.setEmpName(empName);
		     changeStatementModel.setComponentName("GIS");
		     changeStatementModel.setPreBasic(preGis.toString());
		     changeStatementModel.setCurBasic(curGis.toString());
		     changeStatementModel.setBasicDiff(gisDiff.toString());
		     changeStatementModel.setDate(date);
		     changeStatementModel.setColor("red");
		     listChangeStatementModel.add(changeStatementModel);
		    } else {
		     BaseColor br = BaseColor.GREEN;
//		     drawTable(i, empName, "GIS", preGis, curGis, gisDiff,date, br);
		    }
		   }
	   
	 
	   if (objLst[75] != null &&  objLst[76] != null) {
			i++;
			Double curTa =  (Double.parseDouble(objLst[75].toString()));
			Double preta =  (Double.parseDouble(objLst[76].toString()));
			Double taDiff = curTa-preta;
			if (taDiff  != 0) {
		     BaseColor br = BaseColor.RED;
		     drawTable(i, empName, "TA", preta, curTa, taDiff,date, br);
		     changeStatementModel.setEmpName(empName);
		     changeStatementModel.setComponentName("TA");
		     changeStatementModel.setPreBasic(preta.toString());
		     changeStatementModel.setCurBasic(curTa.toString());
		     changeStatementModel.setBasicDiff(taDiff.toString());
		     changeStatementModel.setDate(date);
		     changeStatementModel.setColor("red");
		     listChangeStatementModel.add(changeStatementModel);
		    } else {
		     BaseColor br = BaseColor.GREEN;
//		     drawTable(i, empName, "TA", preta, curTa, taDiff,date, br);
		    }
		   }
	   curAll = curAll+(Double.parseDouble(objLst[55].toString()));
		 preAll =allprenettotal+( Double.parseDouble(objLst[56].toString()));
		 alldedtotal =alldedtotal+( Double.parseDouble(objLst[59].toString()));
		 allprededtotal =allprededtotal+( Double.parseDouble(objLst[60].toString()));
		 allcurnettotal =allcurnettotal+( Double.parseDouble(objLst[53].toString()));
		 allprenettotal =allprenettotal+(Double.parseDouble( objLst[54].toString()));
	   }
	  }
	  
	  
	 		List<Object[]>  monthinfo = paybillGenerationTrnService.findmonthinfo(currmonth);
		for (Object[] monthLst : monthinfo) {
			// String empName;
			monname = monthLst[1].toString();
		}
		BigInteger monpre ;
		Long prmonth =0l;
		if(cumo.equals(1)) {
			 prmonth=12l;
			 BigInteger monsubyear=new BigInteger("1");
			 preyear=curryear.subtract(monsubyear);
			 monpre=BigInteger.valueOf(prmonth);
		}else {
			BigInteger monsub=new BigInteger("1");
			monpre=currmonth.subtract(monsub);
			preyear=curryear;
		}
		
		List<Object[]>  monthpreinfo = paybillGenerationTrnService.findmonthinfo(monpre);
		for (Object[] monthLst : monthpreinfo) {
			// String empName;
			premonname = monthLst[1].toString();
			
		}

		Double alltotal =curAll-preAll;
		
		Double alltotdedtotal =alldedtotal-allprededtotal;
		Double allnetdifftotal =allcurnettotal-allprededtotal;
	  
	   PdfPCell cell11 = new PdfPCell(new Paragraph(""));
	   cell11.setColspan(3);
//       cell11.setRowspan(3);
	  PdfPCell cell12 = new PdfPCell(new Paragraph("Difference Amount"));
	  cell12.setColspan(2);
//      cell12.setRowspan(2);
	  PdfPCell cell13 = new PdfPCell(new Paragraph("" + curAll));
	  PdfPCell cell14 = new PdfPCell(new Paragraph(""));
	  cell12.setHorizontalAlignment(Element.ALIGN_CENTER);
	  cell12.setVerticalAlignment(Element.ALIGN_CENTER);
	  cell13.setHorizontalAlignment(Element.ALIGN_RIGHT);
	  cell13.setVerticalAlignment(Element.ALIGN_MIDDLE);
	  table.addCell(cell11);
	  table.addCell(cell12);
	  table.addCell(cell13);
	  table.addCell(cell14);
	  
	  
	  PdfPCell cell21 = new PdfPCell(new Paragraph(""));
	  cell21.setColspan(3);
//	  cell21.setRowspan(3);
	  PdfPCell cell22 = new PdfPCell(new Paragraph("Current Net Pay Amount"));
	  cell22.setColspan(2);
//	  cell22.setRowspan(2);
	  PdfPCell cell23 = new PdfPCell(new Paragraph("" + curAll));
	  PdfPCell cell24 = new PdfPCell(new Paragraph(""));
	  cell22.setHorizontalAlignment(Element.ALIGN_CENTER);
	  cell22.setVerticalAlignment(Element.ALIGN_CENTER);
	  cell23.setHorizontalAlignment(Element.ALIGN_RIGHT);
	  cell23.setVerticalAlignment(Element.ALIGN_RIGHT);
	  table.addCell(cell21);
	  table.addCell(cell22);
	  table.addCell(cell23);
	  table.addCell(cell24);
	  
	  
	  PdfPCell cell31 = new PdfPCell(new Paragraph(""));
	  cell31.setColspan(3);
	  PdfPCell cell32 = new PdfPCell(new Paragraph("Last Net Pay Amount"));
	  cell32.setColspan(2);
	  PdfPCell cell33 = new PdfPCell(new Paragraph("" + preAll));
	  PdfPCell cell34 = new PdfPCell(new Paragraph(""));
	  cell32.setHorizontalAlignment(Element.ALIGN_CENTER);
	  cell32.setVerticalAlignment(Element.ALIGN_CENTER);
	  cell33.setHorizontalAlignment(Element.ALIGN_RIGHT);
	  cell33.setVerticalAlignment(Element.ALIGN_RIGHT);
	  table.addCell(cell31);
	  table.addCell(cell32);
	  table.addCell(cell33);
	  table.addCell(cell34);
	  
	  PdfPCell cell41 = new PdfPCell(new Paragraph(""));
	  cell41.setColspan(3);
	  PdfPCell cell42 = new PdfPCell(new Paragraph("Difference Total Amount"));
	  cell42.setColspan(2);
	  PdfPCell cell43 = new PdfPCell(new Paragraph("" + alltotal));
	  PdfPCell cell44 = new PdfPCell(new Paragraph(""));
	  cell42.setHorizontalAlignment(Element.ALIGN_CENTER);
	  cell42.setVerticalAlignment(Element.ALIGN_CENTER);
	  cell43.setHorizontalAlignment(Element.ALIGN_RIGHT);
	  cell43.setVerticalAlignment(Element.ALIGN_RIGHT);
	  table.addCell(cell41);
	  table.addCell(cell42);
	  table.addCell(cell43);
	  table.addCell(cell44);
	  
	
	 
	  
	  PdfPCell cell51 = new PdfPCell(new Paragraph("B)"));
	  cell51.setColspan(2);
	  PdfPCell cell52 = new PdfPCell(new Paragraph("Total Amount"));
	  cell52.setColspan(2);
	  PdfPCell cell53 = new PdfPCell(new Paragraph("Difference Amount"));
	  cell53.setColspan(2);
	  PdfPCell cell54 = new PdfPCell(new Paragraph("Net Amount"));
	  cell51.setHorizontalAlignment(Element.ALIGN_CENTER);
	  cell51.setVerticalAlignment(Element.ALIGN_CENTER);
	  cell52.setHorizontalAlignment(Element.ALIGN_RIGHT);
	  cell52.setVerticalAlignment(Element.ALIGN_RIGHT);
	  cell53.setHorizontalAlignment(Element.ALIGN_RIGHT);
	  cell53.setVerticalAlignment(Element.ALIGN_RIGHT);
	  cell54.setHorizontalAlignment(Element.ALIGN_RIGHT);
	  cell54.setVerticalAlignment(Element.ALIGN_RIGHT);
	  table.addCell(cell51);
	  table.addCell(cell52);
	  table.addCell(cell53);
	  table.addCell(cell54);
	
	  
	  
	  PdfPCell cell61 = new PdfPCell(new Paragraph("Current Month Pay Amount" ));
	  cell61.setColspan(2);
	  PdfPCell cell62 = new PdfPCell(new Paragraph("" + allcurnettotal));
	  cell62.setColspan(2);
	  PdfPCell cell63 = new PdfPCell(new Paragraph("" + alldedtotal));
	  cell63.setColspan(2);
	  PdfPCell cell64 = new PdfPCell(new Paragraph("" + curAll));
	  cell61.setHorizontalAlignment(Element.ALIGN_CENTER);
	  cell61.setVerticalAlignment(Element.ALIGN_CENTER);
	  cell62.setHorizontalAlignment(Element.ALIGN_RIGHT);
	  cell62.setVerticalAlignment(Element.ALIGN_RIGHT);
	  cell63.setHorizontalAlignment(Element.ALIGN_RIGHT);
	  cell63.setVerticalAlignment(Element.ALIGN_RIGHT);
	  cell64.setHorizontalAlignment(Element.ALIGN_RIGHT);
	  cell64.setVerticalAlignment(Element.ALIGN_RIGHT);
	  table.addCell(cell61);
	  table.addCell(cell62);
	  table.addCell(cell63);
	  table.addCell(cell64);
	  
	  
	  PdfPCell cell71 = new PdfPCell(new Paragraph("Last Month Pay Amount" ));
	  cell71.setColspan(2);
	  PdfPCell cell72 = new PdfPCell(new Paragraph("" + allprenettotal));
	  cell72.setColspan(2);
	  PdfPCell cell73 = new PdfPCell(new Paragraph("" + allprededtotal));
	  cell73.setColspan(2);
	  PdfPCell cell74 = new PdfPCell(new Paragraph("" + preAll));
	  cell71.setHorizontalAlignment(Element.ALIGN_CENTER);
	  cell71.setVerticalAlignment(Element.ALIGN_CENTER);
	  cell72.setHorizontalAlignment(Element.ALIGN_RIGHT);
	  cell72.setVerticalAlignment(Element.ALIGN_RIGHT);
	  cell73.setHorizontalAlignment(Element.ALIGN_RIGHT);
	  cell73.setVerticalAlignment(Element.ALIGN_RIGHT);
	  cell74.setHorizontalAlignment(Element.ALIGN_RIGHT);
	  cell74.setVerticalAlignment(Element.ALIGN_RIGHT);
	  table.addCell(cell71);
	  table.addCell(cell72);
	  table.addCell(cell73);
	  table.addCell(cell74);
	  
	  	  
	  PdfPCell cell81 = new PdfPCell(new Paragraph("Pay Difference Amount" ));
	  cell81.setColspan(2);
	  PdfPCell cell82 = new PdfPCell(new Paragraph("" + allnetdifftotal));
	  cell82.setColspan(2);
	  PdfPCell cell83 = new PdfPCell(new Paragraph("" + alltotdedtotal));
	  cell83.setColspan(2);
	  PdfPCell cell84 = new PdfPCell(new Paragraph("" + alltotal));
	  cell81.setHorizontalAlignment(Element.ALIGN_CENTER);
	  cell81.setVerticalAlignment(Element.ALIGN_CENTER);
	  cell82.setHorizontalAlignment(Element.ALIGN_RIGHT);
	  cell82.setVerticalAlignment(Element.ALIGN_RIGHT);
	  cell83.setHorizontalAlignment(Element.ALIGN_RIGHT);
	  cell83.setVerticalAlignment(Element.ALIGN_RIGHT);
	  cell84.setHorizontalAlignment(Element.ALIGN_RIGHT);
	  cell84.setVerticalAlignment(Element.ALIGN_RIGHT);
	  table.addCell(cell81);
	  table.addCell(cell82);
	  table.addCell(cell83);
	  table.addCell(cell84);
	  
	  
	  PdfPCell cell91 = new PdfPCell(new Paragraph("(*) Relevant Change Page Documents have been Added." ));
	  cell91.setColspan(7);
	  table.addCell(cell91);
	  
	  PdfPCell cell101 = new PdfPCell(new Paragraph("I Verify that I have checked the Payments Issued by the Treasury System for the Last Month of Bill and all the Staff-wise Recovery Amounts Shown in it and the Payments made Accordingly are Accurate." ));
	  cell101.setColspan(7);
	  table.addCell(cell101);
	  
	  PdfPCell cell141 = new PdfPCell(new Paragraph(""));
	  cell141.setColspan(7);
	  table.addCell(cell141);
	  
	  PdfPCell cell111 = new PdfPCell(new Paragraph(""));
	  cell111.setColspan(2);
	  PdfPCell cell112 = new PdfPCell(new Paragraph(""));
	  cell112.setColspan(2);
	  PdfPCell cell114 = new PdfPCell(new Paragraph("Signature Drawing and Disbursement Officer"));
	  cell114.setColspan(3);
	  cell4.setBackgroundColor(null);
	  table.addCell(cell111);
	  table.addCell(cell112);
	  table.addCell(cell114);
	  
	  PdfPCell cell121 = new PdfPCell(new Paragraph("Change Statement Verification Date Time:-" + createddate));
	  cell121.setColspan(3);
	  PdfPCell cell122 = new PdfPCell(new Paragraph(""));
	   PdfPCell cell123 = new PdfPCell(new Paragraph("Change Statement Printing Date Time:-" + sdf.format(new Date())));
	   cell123.setColspan(3);
	  cell4.setBackgroundColor(null);
	  table.addCell(cell121);
	  table.addCell(cell122);
	  table.addCell(cell123);
	  
	  PdfPCell cell131 = new PdfPCell(new Paragraph(""));
	  cell131.setColspan(2);
	  PdfPCell cell132 = new PdfPCell(new Paragraph("* Generated By PanchayatrajSevarth"));
	  cell132.setColspan(3);
	   PdfPCell cell133 = new PdfPCell(new Paragraph(""));
	   cell133.setColspan(2);
	  table.addCell(cell131);
	  table.addCell(cell132);
	  table.addCell(cell133);

	  document.add(table);
	  document.close();
	  FileInputStream inputStream = new FileInputStream(myFile);
		response.setContentType("application/pdf");
		response.setContentLength((int) myFile.length());
		response.setHeader("Content-Disposition", "application;filename=\"" + fileName + "\"");
//		response.setHeader("Content-Disposition", "inline;filename=\"" + fileName + "\"");
		FileCopyUtils.copy(inputStream, response.getOutputStream());
	  model.addAttribute("lstPayBillObj",lstPayBillObj);
	  return "/views/paybill/paybill-view-approve-delete-bill";
	  //return new ByteArrayInputStream(out.toByteArray());
	  
	  
	 }
	 

	//added by sudhir 
	@GetMapping("/viewChangeStatementReport/{paybillGenerationTrnId}")
	public String viewChangeStatementReport(
			@ModelAttribute("payBillViewApprDelBillModel") PayBillViewApprDelBillModel payBillViewApprDelBillModel,
			@PathVariable String paybillGenerationTrnId, Model model, Locale locale, HttpSession session)
			throws FileNotFoundException, DocumentException {
		lstPayBillObj.clear();
		OrgUserMst messages = (OrgUserMst) session.getAttribute("MY_SESSION_MESSAGES"); 
		SimpleDateFormat sdf =new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		List<Object[]> listA = paybillGenerationTrnService.getChangeStatementReport(paybillGenerationTrnId);
		int i = 0;
				Double curAll = 0D;
				Double preAll = 0D;
				Double alldedtotal = 0D;
				Double allprededtotal = 0D;
				Double allcurnettotal = 0D;
				Double allprenettotal = 0D;
				BigInteger curryear = null;
				BigInteger currmonth = null;
				BigInteger preyear = null;
				BigInteger premonth = null;
				String createddate=null;
				Long cumo=0l;
		for (Object[] objLst : listA) {
			// String empName;
			// createddate = StringHelperUtils.isNullString(objLst[91]);
			String date = null;
			String empName = StringHelperUtils.isNullString(objLst[1]) + "(" + StringHelperUtils.isNullString(objLst[2])
					+ ")";
			
			currmonth=StringHelperUtils.isNullBigInteger(objLst[4]);
			curryear=StringHelperUtils.isNullBigInteger(objLst[3]);
			premonth=StringHelperUtils.isNullBigInteger(objLst[6]);
			preyear=StringHelperUtils.isNullBigInteger(objLst[5]);
			 cumo =StringHelperUtils.isNullBigInteger(objLst[4]).longValue();
			
			Long netpay =StringHelperUtils.isNullBigInteger(objLst[56]).longValue();
			if(netpay!=null && netpay.equals(0l)) {
				if ( objLst[55] != null
						&&  objLst[56] != null) {
					i++;
					Double curGrossAmount =  (Double.parseDouble(objLst[55].toString()));
					Double preGrossAmount =  (Double.parseDouble(objLst[56].toString()));
					Double grossDiff = curGrossAmount-preGrossAmount;
					if (grossDiff  != 0) {
						String br = "color:red";
						addDataToModel(i, empName, "Net Pay Amount", preGrossAmount, curGrossAmount, grossDiff, date, br);
					} else {
						String br = "color:green";
					//	addDataToModel(i, empName, "Gross Amount", preGrossAmount, curGrossAmount, grossDiff, date, br);
					}
					curAll = curAll+(Double.parseDouble(objLst[55].toString()));
					 preAll =allprenettotal+( Double.parseDouble(objLst[56].toString()));
					 alldedtotal =alldedtotal+( Double.parseDouble(objLst[59].toString()));
					 allprededtotal =allprededtotal+( Double.parseDouble(objLst[60].toString()));
					 allcurnettotal =allcurnettotal+( Double.parseDouble(objLst[53].toString()));
					 allprenettotal =allprenettotal+(Double.parseDouble( objLst[54].toString()));
				}
			}else {
			if (objLst[7] != null
					&&  objLst[8] != null) {
				i++;
				Double basicDiff=0d;
			
				Double curBasic =   (Double.parseDouble(objLst[7].toString()));
				Double preBasic =   (Double.parseDouble(objLst[8].toString()));
				
				 basicDiff = curBasic-preBasic;
				if (basicDiff != 0) {
					String br = "color:red";
					addDataToModel(i, empName, "Basic Pay", preBasic, curBasic, basicDiff, date, br);
				} else {
					String br = "color:green";
					//addDataToModel(i, empName, "Basic Pay", preBasic, curBasic, basicDiff, date, br);
				}
			}
			if ( objLst[9] != null
					&&  objLst[10] != null) {
				i++;
				Double curDa =  (Double.parseDouble(objLst[9].toString()));
				Double preDa =  (Double.parseDouble(objLst[10].toString()));
				Double daDiff = curDa-preDa;
				if (daDiff != 0) {
					String br = "color:red";
					addDataToModel(i, empName, "DA", preDa, curDa, daDiff, date, br);
				} else {
					String br = "color:green";
					//addDataToModel(i, empName, "DA", preDa, curDa, daDiff, date, br);
				}
			}
			if ( objLst[11] != null
					&&  objLst[12] != null) {
				i++;
				Double curHra =  (Double.parseDouble(objLst[11].toString()));
				Double preHra =  (Double.parseDouble(objLst[12].toString()));
				Double sub = curHra-preHra;
				if (sub  != 0) {
					String br = "color:red";
					addDataToModel(i, empName, "HRA", preHra, curHra, sub, date, br);
				} else {
					String br = "color:green";
					//addDataToModel(i, empName, "HRA", preHra, curHra, sub, date, br);
				}
			}
			if ( objLst[13] != null
					&&  objLst[14] != null) {
				i++;
				Double curCla =  (Double.parseDouble(objLst[13].toString()));
				Double preCla =  (Double.parseDouble(objLst[14].toString()));
				Double claDiff = curCla-preCla;
				if (claDiff  != 0) {
					String br = "color:red";
					addDataToModel(i, empName, "CLA", preCla, curCla, claDiff, date, br);
				} else {
					String br = "color:green";
					//addDataToModel(i, empName, "CLA", preCla, curCla, claDiff, date, br);
				}
			}
			if ( objLst[15] != null
					&&  objLst[16] != null) {
				i++;
				Double curIt =  (Double.parseDouble(objLst[15].toString()));
				Double preIt =  (Double.parseDouble(objLst[16].toString()));
				Double ItDiff = curIt-preIt;
				if (ItDiff  != 0) {
					String br = "color:red";
					addDataToModel(i, empName, "IT", preIt, curIt, ItDiff, date, br);
				} else {
					String br = "color:green";
					//addDataToModel(i, empName, "IT", preIt, curIt, ItDiff, date, br);
				}
			}
			if ( objLst[17] != null
					&&  objLst[18] != null) {
				i++;
				Double curHrr =  (Double.parseDouble(objLst[17].toString()));
				Double preHrr =  (Double.parseDouble(objLst[18].toString()));
				Double hrrDiff = curHrr-preHrr;
				if (hrrDiff  != 0) {
					String br = "color:red";
					addDataToModel(i, empName, "HRR", preHrr, curHrr, hrrDiff, date, br);
				} else {
					String br = "color:green";
					//addDataToModel(i, empName, "HRR", preHrr, curHrr, hrrDiff, date, br);
				}
			}
			if ( objLst[19] != null
					&&  objLst[20] != null) {
				i++;
				Double curPli =  (Double.parseDouble(objLst[19].toString()));
				Double prepli =  (Double.parseDouble(objLst[20].toString()));
				Double pliDiff = curPli-prepli;
				if (pliDiff  != 0) {
					String br = "color:red";
					addDataToModel(i, empName, "PLI", prepli, curPli, pliDiff, date, br);
				} else {
					String br = "color:green";
					//addDataToModel(i, empName, "PLI", prepli, curPli, pliDiff, date, br);
				}
			}
			if ( objLst[21] != null
					&&  objLst[22] != null) {
				i++;
				Double curPt =  (Double.parseDouble(objLst[21].toString()));
				Double prept =  (Double.parseDouble(objLst[22].toString()));
				Double ptDiff = curPt-prept;
				if (ptDiff  != 0) {
					String br = "color:red";
					addDataToModel(i, empName, "PT", prept, curPt, ptDiff, date, br);
				} else {
					String br = "color:green";
					//addDataToModel(i, empName, "PT", prept, curPt, ptDiff, date, br);
				}
			}
			if ( objLst[23] != null
					&&  objLst[24] != null) {
				i++;
				Double curHba =  (Double.parseDouble(objLst[23].toString()));
				Double preHba =  (Double.parseDouble(objLst[24].toString()));
				Double hbaDiff = curHba-preHba;
				if (hbaDiff  != 0) {
					String br = "color:red";
					addDataToModel(i, empName, "HBA", preHba, curHba, hbaDiff, date, br);
				} else {
					String br = "color:green";
				//	addDataToModel(i, empName, "HBA", preHba, curHba, hbaDiff, date, br);
				}
			}
			/*if ( objLst[25]) != null
					&&  objLst[26]) != null) {
				i++;
				Double curGpfAdv =  (objLst[25]));
				Double preGpfAdv =  (objLst[26]));
				Double gpfAdvDiff = curGpfAdv-preGpfAdv;
				if (gpfAdvDiff  != 0) {
					String br = "color:red";
					addDataToModel(i, empName, "HBA", preGpfAdv, curGpfAdv, gpfAdvDiff, date, br);
				} else {
					String br = "color:green";
				//	addDataToModel(i, empName, "HBA", preGpfAdv, curGpfAdv, gpfAdvDiff, date, br);
				}
			}*/
			if ( objLst[25] != null
					&&  objLst[26] != null) {
				i++;
				Double curGpfAdv =  (Double.parseDouble(objLst[25].toString()));
				Double preGpfAdv =  (Double.parseDouble(objLst[26].toString()));
				Double gpfAdvDiff = curGpfAdv-preGpfAdv;
				if (gpfAdvDiff  != 0) {
					String br = "color:red";
					addDataToModel(i, empName, "Gpf Advance", preGpfAdv, curGpfAdv, gpfAdvDiff, date, br);
				} else {
					String br = "color:green";
					//addDataToModel(i, empName, "Gpf Advance", preGpfAdv, curGpfAdv, gpfAdvDiff, date, br);
				}
			}
			if ( objLst[27] != null
					&&  objLst[28] != null) {
				i++;
				Double curGpfIvAdv =  (Double.parseDouble(objLst[27].toString()));
				Double preGpfIvAdv =  (Double.parseDouble(objLst[28].toString()));
				Double gpfIvAdvDiff = curGpfIvAdv-preGpfIvAdv;
				if (gpfIvAdvDiff  != 0) {
					String br = "color:red";
					addDataToModel(i, empName, "Gpf Iv Advance", preGpfIvAdv, curGpfIvAdv, gpfIvAdvDiff, date, br);
				} else {
					String br = "color:green";
					//addDataToModel(i, empName, "Gpf Iv Advance", preGpfIvAdv, curGpfIvAdv, gpfIvAdvDiff, date, br);
				}
			}
			if ( objLst[29] != null
					&&  objLst[30] != null) {
				i++;
				Double curDcps =  (Double.parseDouble(objLst[29].toString()));
				Double preDcps =  (Double.parseDouble(objLst[30].toString()));
				Double dcpsDiff = curDcps-preDcps;
				if (dcpsDiff  != 0) {
					String br = "color:red";
					addDataToModel(i, empName, "DCPS", preDcps, curDcps, dcpsDiff, date, br);
				} else {
					String br = "color:green";
				//	addDataToModel(i, empName, "DCPS", preDcps, curDcps, dcpsDiff, date, br);
				}
			}
			if ( objLst[31] != null
					&&  objLst[32] != null) {
				i++;
				Double curGpay =  (Double.parseDouble(objLst[31].toString()));
				Double preGpay =  (Double.parseDouble(objLst[32].toString()));
				Double gpayDiff = curGpay-preGpay;
				if (gpayDiff  != 0) {
					String br = "color:red";
					addDataToModel(i, empName, "GPAY", preGpay, curGpay, gpayDiff, date, br);
				} else {
					String br = "color:green";
				//	addDataToModel(i, empName, "GPAY", preGpay, curGpay, gpayDiff, date, br);
				}
			}
			if ( objLst[33] != null
					&&  objLst[34] != null) {
				i++;
				Double curGpfGrpAbc =  (Double.parseDouble(objLst[33].toString()));
				Double preGpfGrpAbc =  (Double.parseDouble(objLst[34].toString()));
				Double gpfGrpAbcDiff = curGpfGrpAbc-preGpfGrpAbc;
				if (gpfGrpAbcDiff  != 0) {
					String br = "color:red";
					addDataToModel(i, empName, "Gpf Grp Abc", preGpfGrpAbc, curGpfGrpAbc, gpfGrpAbcDiff, date, br);
				} else {
					String br = "color:green";
				//	addDataToModel(i, empName, "Gpf Grp Abc", preGpfGrpAbc, curGpfGrpAbc, gpfGrpAbcDiff, date, br);
				}
			}
			if ( objLst[35] != null
					&&  objLst[36] != null) {
				i++;
				Double curGpfGrpD =  (Double.parseDouble(objLst[35].toString()));
				Double preGpfGrpD =  (Double.parseDouble(objLst[36].toString()));
				Double gpfGrpDDiff = curGpfGrpD-preGpfGrpD;
				if (gpfGrpDDiff  != 0) {
					String br = "color:red";
					addDataToModel(i, empName, "Gpf Grp D", preGpfGrpD, curGpfGrpD, gpfGrpDDiff, date, br);
				} else {
					String br = "color:green";
				//	addDataToModel(i, empName, "Gpf Grp D", preGpfGrpD, curGpfGrpD, gpfGrpDDiff, date, br);
				}
			}
			if ( objLst[37] != null
					&&  objLst[38] != null) {
				i++;
				Double curGpfAdvGrpAbc =  (Double.parseDouble(objLst[37].toString()));
				Double preGpfAdvGrpAbc =  (Double.parseDouble(objLst[38].toString()));
				Double gpfAdvGrpAbc = curGpfAdvGrpAbc-preGpfAdvGrpAbc;
				if (gpfAdvGrpAbc  != 0) {
					String br = "color:red";
					addDataToModel(i, empName, "Gpf Advance Grp Abc", preGpfAdvGrpAbc, curGpfAdvGrpAbc, gpfAdvGrpAbc,
							date, br);
				} else {
					String br = "color:green";
					/*addDataToModel(i, empName, "Gpf Advance Grp Abc", preGpfAdvGrpAbc, curGpfAdvGrpAbc, gpfAdvGrpAbc,
							date, br);*/
				}
			}
			if ( objLst[39] != null
					&&  objLst[40]!= null) {
				i++;
				Double curGpfAdvGrpD =  (Double.parseDouble(objLst[39].toString()));
				Double preGpfAdvGrpD =  (Double.parseDouble(objLst[40].toString()));
				Double gpfAdvGrpDDiff = curGpfAdvGrpD-preGpfAdvGrpD;
				if (gpfAdvGrpDDiff  != 0) {
					String br = "color:red";
					addDataToModel(i, empName, "Gpf Advance Grp D", preGpfAdvGrpD, curGpfAdvGrpD, gpfAdvGrpDDiff, date,
							br);
				} else {
					String br = "color:green";
					/*addDataToModel(i, empName, "Gpf Advance Grp D", preGpfAdvGrpD, curGpfAdvGrpD, gpfAdvGrpDDiff, date,
							br);*/
				}
			}
			if ( objLst[41] != null
					&&  objLst[42] != null) {
				i++;
				Double curMotorCycleAdv =  (Double.parseDouble(objLst[41].toString()));
				Double preMotorCycleAdv =  (Double.parseDouble(objLst[42].toString()));
				Double MotorCycleAdvDiff = curMotorCycleAdv-preMotorCycleAdv;
				if (MotorCycleAdvDiff  != 0) {
					String br = "color:red";
					addDataToModel(i, empName, "Motor Cycle Advance", preMotorCycleAdv, curMotorCycleAdv,
							MotorCycleAdvDiff, date, br);
				} else {
					String br = "color:green";
					/*addDataToModel(i, empName, "Motor Cycle Advance", preMotorCycleAdv, curMotorCycleAdv,
							MotorCycleAdvDiff, date, br);*/
				}
			}
			if ( objLst[43] != null
					&&  objLst[44] != null) {
				i++;
				Double curOtherDedTry =  (Double.parseDouble(objLst[43].toString()));
				Double preOtherDedTry =  (Double.parseDouble(objLst[44].toString()));
				Double otherDedTryDiff = curOtherDedTry-preOtherDedTry;
				if (otherDedTryDiff  != 0) {
					String br = "color:red";
					addDataToModel(i, empName, "Other ded try", preOtherDedTry, curOtherDedTry, otherDedTryDiff, date,
							br);
				} else {
					String br = "color:green";
				/*	addDataToModel(i, empName, "Other ded try", preOtherDedTry, curOtherDedTry, otherDedTryDiff, date,
							br);*/
				}
			}
			if ( objLst[45] != null
					&&  objLst[46] != null) {
				i++;
				Double curJanjulgis =  (Double.parseDouble(objLst[45].toString()));
				Double preJanjulgis =  (Double.parseDouble(objLst[46].toString()));
				Double janjulgisDiff = curJanjulgis-preJanjulgis;
				if (janjulgisDiff  != 0) {
					String br = "color:red";
					addDataToModel(i, empName, "Janjulgis", preJanjulgis, curJanjulgis, janjulgisDiff, date, br);
				} else {
					String br = "color:green";
				//	addDataToModel(i, empName, "Janjulgis", preJanjulgis, curJanjulgis, janjulgisDiff, date, br);
				}
			}
			if ( objLst[47] != null
					&&  objLst[48] != null) {
				i++;
				Double curJanjulgis =  (Double.parseDouble(objLst[47].toString()));
				Double preJanjulgis =  (Double.parseDouble(objLst[48].toString()));
				Double janjulgisDiff = curJanjulgis-preJanjulgis;
				if (janjulgisDiff  != 0) {
					String br = "color:red";
					addDataToModel(i, empName, "Ded Adjust", preJanjulgis, curJanjulgis, janjulgisDiff, date, br);
				} else {
					String br = "color:green";
					//addDataToModel(i, empName, "Ded Adjust", preJanjulgis, curJanjulgis, janjulgisDiff, date, br);
				}
			}
			if ( objLst[49] != null
					&&  objLst[50] != null) {
				i++;
				Double curGrossAdjust =  (Double.parseDouble(objLst[49].toString()));
				Double preGrossAdjust =  (Double.parseDouble(objLst[50].toString()));
				Double grossAdjustDiff = curGrossAdjust-preGrossAdjust;
				if (grossAdjustDiff  != 0) {
					String br = "color:red";
					addDataToModel(i, empName, "Gross Adjust", preGrossAdjust, curGrossAdjust, grossAdjustDiff, date,
							br);
				} else {
					String br = "color:green";
				/*	addDataToModel(i, empName, "Gross Adjust", preGrossAdjust, curGrossAdjust, grossAdjustDiff, date,
							br);*/
				}
			}
			if ( objLst[51] != null
					&&  objLst[52] != null) {
				i++;
				Double curGrossTotalAmount =  (Double.parseDouble(objLst[51].toString()));
				Double preGrossTotalAmount =  (Double.parseDouble(objLst[52].toString()));
				Double grossTotalAmountDiff = curGrossTotalAmount-preGrossTotalAmount;
				if (grossTotalAmountDiff  != 0) {
					String br = "color:red";
					addDataToModel(i, empName, "Gross Total Amount", preGrossTotalAmount, curGrossTotalAmount,
							grossTotalAmountDiff, date, br);
				} else {
					String br = "color:green";
					/*addDataToModel(i, empName, "Gross Total Amount", preGrossTotalAmount, curGrossTotalAmount,
							grossTotalAmountDiff, date, br);*/
				}
			}
		/*	if ( objLst[53] != null
					&&  objLst[54] != null) {
				i++;
				Double curGrossTotalNetAmount =  (objLst[53].toString()));
				Double preGrossTotalNetAmount =  (objLst[54].toString()));
				Double grossTotalNetAmountDiff = curGrossTotalNetAmount.subtract(preGrossTotalNetAmount);
				if (grossTotalNetAmountDiff  != 0) {
					String br = "color:red";
					addDataToModel(i, empName, "Gross Total Net Amount", preGrossTotalNetAmount, curGrossTotalNetAmount,
							grossTotalNetAmountDiff, date, br);
				} else {
					String br = "color:green";
					addDataToModel(i, empName, "Gross Total Net Amount", preGrossTotalNetAmount, curGrossTotalNetAmount,
							grossTotalNetAmountDiff, date, br);
				}
			}*/
			if ( objLst[55] != null
					&&  objLst[56] != null) {
				i++;
				Double curGrossAmount =  (Double.parseDouble(objLst[55].toString()));
				Double preGrossAmount =  (Double.parseDouble(objLst[56].toString()));
				Double grossDiff = curGrossAmount-preGrossAmount;
				if (grossDiff  != 0) {
					String br = "color:red";
					addDataToModel(i, empName, "Net Pay Amount", preGrossAmount, curGrossAmount, grossDiff, date, br);
				} else {
					String br = "color:green";
				//	addDataToModel(i, empName, "Gross Amount", preGrossAmount, curGrossAmount, grossDiff, date, br);
				}
				
			}
			if ( objLst[57] != null
					&&  objLst[58] != null) {
				i++;
				Double curGrossAmount =  (Double.parseDouble(objLst[57].toString()));
				Double preGrossAmount =  (Double.parseDouble(objLst[58].toString()));
				Double grossAmountDiff = curGrossAmount-preGrossAmount;
				if (grossAmountDiff  != 0) {
					String br = "color:red";
					addDataToModel(i, empName, "Gross Amount", preGrossAmount, curGrossAmount,
							grossAmountDiff, date, br);
				} else {
					String br = "color:green";
					/*addDataToModel(i, empName, "Total Deduction", preTotalDeduction, curTotalDeduction,
							totalDeductionDiff, date, br);*/
				}
			}
			/*if ( objLst[57] != null
					&&  objLst[58] != null) {
				i++;
				Double curTotalDeduction =  (Double.parseDouble(objLst[57].toString()));
				Double preTotalDeduction =  (Double.parseDouble(objLst[58].toString()));
				Double totalDeductionDiff = curTotalDeduction-preTotalDeduction;
				if (totalDeductionDiff  != 0) {
					String br = "color:red";
					addDataToModel(i, empName, "Total Deduction", preTotalDeduction, curTotalDeduction,
							totalDeductionDiff, date, br);
				} else {
					String br = "color:green";
					addDataToModel(i, empName, "Total Deduction", preTotalDeduction, curTotalDeduction,
							totalDeductionDiff, date, br);
				}
			}*/
			
			if ( objLst[59] != null
					&&  objLst[60] != null) {
				i++;
				Double curTotalDeduction =  (Double.parseDouble(objLst[59].toString()));
				Double preTotalDeduction =  (Double.parseDouble(objLst[60].toString()));
				Double totalDeductionDiff = curTotalDeduction-preTotalDeduction;
				if (totalDeductionDiff  != 0) {
					String br = "style='color:red";
					addDataToModel(i, empName, "Total Deduction", preTotalDeduction, curTotalDeduction,
							totalDeductionDiff, date, br);
				} else {
					String br = "color:green";
//					addDataToModel(i, empName, "Total Deduction", preTotalDeduction, curTotalDeduction,totalDeductionDiff, date, br);
				}
			}
			if ( objLst[61] != null
					&&  objLst[62] != null) {
				i++;
				Double curDcpsArr =  (Double.parseDouble(objLst[61].toString()));
				Double preDcpsArr =  (Double.parseDouble(objLst[62].toString()));
				Double dcpsArrDiff = curDcpsArr-preDcpsArr;
				if (dcpsArrDiff  != 0) {
					String br = "color:red";
					addDataToModel(i, empName, "Dcps Arr", preDcpsArr, curDcpsArr, dcpsArrDiff, date, br);
				} else {
					String br = "color:green";
					//addDataToModel(i, empName, "Dcps Arr", preDcpsArr, curDcpsArr, dcpsArrDiff, date, br);
				}
			}
			/*if ( objLst[63] != null
					&&  objLst[64] != null) {
				i++;
				Double curDcpsDaArr =  (objLst[53].toString()));
				Double preDcpsDaArr =  (objLst[54].toString()));
				Double dcpsDaArrDiff = curDcpsDaArr.subtract(preDcpsDaArr);
				if (dcpsDaArrDiff  != 0) {
					String br = "color:red";
					addDataToModel(i, empName, "Pre Dcps Da Arr", preDcpsDaArr, curDcpsDaArr, dcpsDaArrDiff, date, br);
				} else {
					String br = "color:green";
					//addDataToModel(i, empName, "Pre Dcps Da Arr", preDcpsDaArr, curDcpsDaArr, dcpsDaArrDiff, date, br);
				}
			}*/
			if ( objLst[65] != null
					&&  objLst[66] != null) {
				i++;
				Double curDcpsDelay =  (Double.parseDouble(objLst[65].toString()));
				Double preDcpsDelayt =  (Double.parseDouble(objLst[66].toString()));
				Double dcpsDelayDiff = curDcpsDelay-preDcpsDelayt;
				if (dcpsDelayDiff  != 0) {
					String br = "color:red";
					addDataToModel(i, empName, "Dcps Delay", preDcpsDelayt, curDcpsDelay, dcpsDelayDiff, date, br);
				} else {
					String br = "color:green";
					//addDataToModel(i, empName, "Dcps Delay", preDcpsDelayt, curDcpsDelay, dcpsDelayDiff, date, br);
				}
			}
			if ( objLst[67] != null
					&&  objLst[68] != null) {
				i++;
				Double curDcpsEmployer =  (Double.parseDouble(objLst[67].toString()));
				Double preDcpsEmployer =  (Double.parseDouble(objLst[68].toString()));
				Double dcpsEmployerDiff = curDcpsEmployer-preDcpsEmployer;
				if (dcpsEmployerDiff  != 0) {
					String br = "color:red";
					addDataToModel(i, empName, "Dcps Employer", preDcpsEmployer, curDcpsEmployer, dcpsEmployerDiff,
							date, br);
				} else {
					String br = "color:green";
				/*	addDataToModel(i, empName, "Dcps Employer", preDcpsEmployer, curDcpsEmployer, dcpsEmployerDiff,
							date, br);*/
				}
			}
			if ( objLst[69] != null
					&&  objLst[70] != null) {
				i++;
				Double curDcpsPayArr =  (Double.parseDouble(objLst[69].toString()));
				Double preDcpsPayArr =  (Double.parseDouble(objLst[70].toString()));
				Double dcpsPayArrDiff = curDcpsPayArr-preDcpsPayArr;
				if (dcpsPayArrDiff  != 0) {
					String br = "color:red";
					addDataToModel(i, empName, "Dcps Pay Arr", preDcpsPayArr, curDcpsPayArr, dcpsPayArrDiff, date, br);
				} else {
					String br = "color:green";
					//addDataToModel(i, empName, "Dcps Pay Arr", preDcpsPayArr, curDcpsPayArr, dcpsPayArrDiff, date, br);
				}
			}
			if ( objLst[71] != null
					&&  objLst[72] != null) {
				i++;
				Double curDcpsRegularRecovery =  (Double.parseDouble(objLst[71].toString()));
				Double preDcpsRegularRecovery =  (Double.parseDouble(objLst[72].toString()));
				Double dcpsRegularRecoveryDiff = curDcpsRegularRecovery-preDcpsRegularRecovery;
				if (dcpsRegularRecoveryDiff  != 0) {
					String br = "color:red";
					addDataToModel(i, empName, "Dcps Regular Recovery", preDcpsRegularRecovery, curDcpsRegularRecovery,
							dcpsRegularRecoveryDiff, date, br);
				} else {
					String br = "color:green";
				/*	addDataToModel(i, empName, "Dcps Regular Recovery", preDcpsRegularRecovery, curDcpsRegularRecovery,
							dcpsRegularRecoveryDiff, date, br);*/
				}
			}
			if ( objLst[73] != null
					&&  objLst[74] != null) {
				i++;
				Double curGis =  (Double.parseDouble(objLst[73].toString()));
				Double preGis =  (Double.parseDouble(objLst[74].toString()));
				Double gisDiff = curGis-preGis;
				if (gisDiff  != 0) {
					String br = "color:red";
					addDataToModel(i, empName, "GIS", preGis, curGis, gisDiff, date, br);
				} else {
					String br = "color:green";
					//addDataToModel(i, empName, "GIS", preGis, curGis, gisDiff, date, br);
				}
			}
			if ( objLst[75] != null
					&&  objLst[76] != null) {
				i++;
				Double curTa =  (Double.parseDouble(objLst[75].toString()));
				Double preta =  (Double.parseDouble(objLst[76].toString()));
				Double taDiff = curTa-preta;
				if (taDiff  != 0) {
					String br = "color:red";
					addDataToModel(i, empName, "TA", preta, curTa, taDiff, date, br);
				} else {
					String br = "color:green";
				//	addDataToModel(i, empName, "TA", preta, curTa, taDiff, date, br);
				}
				
			}
			if ( objLst[79] != null
					&&  objLst[80] != null) {
				i++;
				Double curNpsEmprDeduct =  (Double.parseDouble(objLst[79].toString()));
				Double preNpsEmprDeduct =  (Double.parseDouble(objLst[80].toString()));
				Double npsEmprDeductDiff = curNpsEmprDeduct-preNpsEmprDeduct;
				if (npsEmprDeductDiff  != 0) {
					String br = "color:red";
					addDataToModel(i, empName, "NPS_EMPR_DEDUCT", preNpsEmprDeduct, curNpsEmprDeduct, npsEmprDeductDiff, date, br);
				} else {
					String br = "color:green";
					//	addDataToModel(i, empName, "TA", preta, curTa, taDiff, date, br);
				}
				
			}
			if ( objLst[81] != null
					&&  objLst[82] != null) {
				i++;
				Double curNpsEmprAllow =  (Double.parseDouble(objLst[81].toString()));
				Double preNpsEmprAllow =  (Double.parseDouble(objLst[82].toString()));
				Double npsEmprAllowDiff = curNpsEmprAllow-preNpsEmprAllow;
				if (npsEmprAllowDiff  != 0) {
					String br = "color:red";
					addDataToModel(i, empName, "NPS_EMPR_ALLOW", preNpsEmprAllow, curNpsEmprAllow, npsEmprAllowDiff, date, br);
				} else {
					String br = "color:green";
					//	addDataToModel(i, empName, "TA", preta, curTa, taDiff, date, br);
				}
				
			}
			if ( objLst[83] != null
					&&  objLst[84] != null) {
				i++;
				Double curEmpDcpsRegularRecovery =  (Double.parseDouble(objLst[83].toString()));
				Double preEmpDcpsRegularRecovery =  (Double.parseDouble(objLst[84].toString()));
				Double empDcpsRegularRecoveryDiff = curEmpDcpsRegularRecovery-preEmpDcpsRegularRecovery;
				if (empDcpsRegularRecoveryDiff  != 0) {
					String br = "color:red";
					addDataToModel(i, empName, "EMP_DCPS_REGULAR_RECOVERY", preEmpDcpsRegularRecovery, curEmpDcpsRegularRecovery, empDcpsRegularRecoveryDiff, date, br);
				} else {
					String br = "color:green";
					//	addDataToModel(i, empName, "TA", preta, curTa, taDiff, date, br);
				}
				
			}
			if ( objLst[85] != null
					&&  objLst[86] != null) {
				i++;
				Double curEmpDcpsPayArr =  (Double.parseDouble(objLst[85].toString()));
				Double preEmpDcpsPayArr =  (Double.parseDouble(objLst[86].toString()));
				Double empDcpsPayArrDiff = curEmpDcpsPayArr-preEmpDcpsPayArr;
				if (empDcpsPayArrDiff  != 0) {
					String br = "color:red";
					addDataToModel(i, empName, "EMP_DCPS_PAY_ARR", preEmpDcpsPayArr, curEmpDcpsPayArr, empDcpsPayArrDiff, date, br);
				} else {
					String br = "color:green";
					//	addDataToModel(i, empName, "TA", preta, curTa, taDiff, date, br);
				}
				
			}
			if ( objLst[87] != null
					&&  objLst[88] != null) {
				i++;
				Double curEmpDcpsDelay =  (Double.parseDouble(objLst[87].toString()));
				Double preEmpDcpsDelay =  (Double.parseDouble(objLst[88].toString()));
				Double empDcpsDelayDiff = curEmpDcpsDelay-preEmpDcpsDelay;
				if (empDcpsDelayDiff  != 0) {
					String br = "color:red";
					addDataToModel(i, empName, "EMP_DCPS_DELAY", preEmpDcpsDelay, curEmpDcpsDelay, empDcpsDelayDiff, date, br);
				} else {
					String br = "color:green";
					//	addDataToModel(i, empName, "TA", preta, curTa, taDiff, date, br);
				}
				
			}
			if ( objLst[89] != null
					&&  objLst[90] != null) {
				i++;
				Double curEmpDcpsDaArr =  (Double.parseDouble(objLst[89].toString()));
				Double preEmpDcpsDaArr =  (Double.parseDouble(objLst[90].toString()));
				Double empDcpsDaArrDiff = curEmpDcpsDaArr-preEmpDcpsDaArr;
				if (empDcpsDaArrDiff  != 0) {
					String br = "color:red";
					addDataToModel(i, empName, "Emp_DCPS_DA_ARR", preEmpDcpsDaArr, curEmpDcpsDaArr, empDcpsDaArrDiff, date, br);
				} else {
					String br = "color:green";
					//	addDataToModel(i, empName, "TA", preta, curTa, taDiff, date, br);
				}
				
			}
			if ( objLst[91] != null
					&&  objLst[92] != null) {
				i++;
				Double curgpfSubscr =  (Double.parseDouble(objLst[91].toString()));
				Double pregpfSubscr =  (Double.parseDouble(objLst[92].toString()));
				Double gpfSubscrDiff = curgpfSubscr-pregpfSubscr;
				if (gpfSubscrDiff  != 0) {
					String br = "color:red";
					addDataToModel(i, empName, "GPF_Subscription", pregpfSubscr, curgpfSubscr, gpfSubscrDiff, date, br);
				} else {
					String br = "color:green";
					//	addDataToModel(i, empName, "TA", preta, curTa, taDiff, date, br);
				}
				
			}
			if ( objLst[93] != null
					&&  objLst[94] != null) {
				i++;
				Double curgpfAdv =  (Double.parseDouble(objLst[93].toString()));
				Double pregpfAdv =  (Double.parseDouble(objLst[94].toString()));
				Double gpfAdvDiff = curgpfAdv-pregpfAdv;
				if (gpfAdvDiff  != 0) {
					String br = "color:red";
					addDataToModel(i, empName, "GPF_Advance", pregpfAdv, curgpfAdv, gpfAdvDiff, date, br);
				} else {
					String br = "color:green";
					//	addDataToModel(i, empName, "TA", preta, curTa, taDiff, date, br);
				}
				
			}
			if ( objLst[95] != null
					&&  objLst[96] != null) {
				i++;
				Double curhra5th =  (Double.parseDouble(objLst[93].toString()));
				Double prehra5th =  (Double.parseDouble(objLst[94].toString()));
				Double hra5thDiff = curhra5th-prehra5th;
				if (hra5thDiff  != 0) {
					String br = "color:red";
					addDataToModel(i, empName, "HRA_5th_PC", prehra5th, curhra5th, hra5thDiff, date, br);
				} else {
					String br = "color:green";
					//	addDataToModel(i, empName, "TA", preta, curTa, taDiff, date, br);
				}
				
			}
			if ( objLst[97] != null
					&&  objLst[98] != null) {
				i++;
				Double curta5th =  (Double.parseDouble(objLst[97].toString()));
				Double preta5th =  (Double.parseDouble(objLst[98].toString()));
				Double ta5Diff = curta5th-preta5th;
				if (ta5Diff  != 0) {
					String br = "color:red";
					addDataToModel(i, empName, "TA_5th_PC", preta5th, curta5th, ta5Diff, date, br);
				} else {
					String br = "color:green";
					//	addDataToModel(i, empName, "TA", preta, curTa, taDiff, date, br);
				}
				
			}
			if ( objLst[99] != null
					&&  objLst[100] != null) {
				i++;
				Double curta6th =  (Double.parseDouble(objLst[97].toString()));
				Double preta6th =  (Double.parseDouble(objLst[98].toString()));
				Double ta6Diff = curta6th-preta6th;
				if (ta6Diff  != 0) {
					String br = "color:red";
					addDataToModel(i, empName, "TA_6th_PC", preta6th, curta6th, ta6Diff, date, br);
				} else {
					String br = "color:green";
					//	addDataToModel(i, empName, "TA", preta, curTa, taDiff, date, br);
				}
				
			}
			if ( objLst[101] != null
					&&  objLst[102] != null) {
				i++;
				Double cursocBankLoan =  (Double.parseDouble(objLst[101].toString()));
				Double presocBankLoan =  (Double.parseDouble(objLst[102].toString()));
				Double socBankLoanDiff = cursocBankLoan-presocBankLoan;
				if (socBankLoanDiff  != 0) {
					String br = "color:red";
					addDataToModel(i, empName, "Socierty_or_BankLoan", presocBankLoan, cursocBankLoan, socBankLoanDiff, date, br);
				} else {
					String br = "color:green";
					//	addDataToModel(i, empName, "TA", preta, curTa, taDiff, date, br);
				}
				
			}
			if ( objLst[103] != null
					&&  objLst[104] != null) {
				i++;
				Double curblwf =  (Double.parseDouble(objLst[103].toString()));
				Double preblwf =  (Double.parseDouble(objLst[104].toString()));
				Double blwfDiff = curblwf-preblwf;
				if (blwfDiff  != 0) {
					String br = "color:red";
					addDataToModel(i, empName, "BLWF", preblwf, curblwf, blwfDiff, date, br);
				} else {
					String br = "color:green";
					//	addDataToModel(i, empName, "TA", preta, curTa, taDiff, date, br);
				}
				
			}
			if ( objLst[105] != null
					&&  objLst[106] != null) {
				i++;
				Double curndcpsSubscr =  (Double.parseDouble(objLst[105].toString()));
				Double prendcpsSubscr =  (Double.parseDouble(objLst[106].toString()));
				Double ndcpsSubscrDiff = curndcpsSubscr-prendcpsSubscr;
				if (ndcpsSubscrDiff  != 0) {
					String br = "color:red";
					addDataToModel(i, empName, "NDCPS_Subscription", prendcpsSubscr, curndcpsSubscr, ndcpsSubscrDiff, date, br);
				} else {
					String br = "color:green";
					//	addDataToModel(i, empName, "TA", preta, curTa, taDiff, date, br);
				}
				
			}
			if ( objLst[107] != null
					&&  objLst[108] != null) {
				i++;
				Double curgpfArr =  (Double.parseDouble(objLst[107].toString()));
				Double pregpfArr =  (Double.parseDouble(objLst[108].toString()));
				Double gpfArrDiff = curgpfArr-pregpfArr;
				if (gpfArrDiff  != 0) {
					String br = "color:red";
					addDataToModel(i, empName, "GPF_Arr", pregpfArr, curgpfArr, gpfArrDiff, date, br);
				} else {
					String br = "color:green";
					//	addDataToModel(i, empName, "TA", preta, curTa, taDiff, date, br);
				}
				
			}
			if ( objLst[109] != null
					&&  objLst[110] != null) {
				i++;
				Double curgpfSpclArr =  (Double.parseDouble(objLst[109].toString()));
				Double pregpfSpclArr =  (Double.parseDouble(objLst[110].toString()));
				Double gpfSpclArrDiff = curgpfSpclArr-pregpfSpclArr;
				if (gpfSpclArrDiff  != 0) {
					String br = "color:red";
					addDataToModel(i, empName, "GPF_Special_Arr", pregpfSpclArr, curgpfSpclArr, gpfSpclArrDiff, date, br);
				} else {
					String br = "color:green";
					//	addDataToModel(i, empName, "TA", preta, curTa, taDiff, date, br);
				}
				
			}
			if ( objLst[111] != null
					&&  objLst[112] != null) {
				i++;
				Double curarrears =  (Double.parseDouble(objLst[111].toString()));
				Double prearrears =  (Double.parseDouble(objLst[112].toString()));
				Double arrearsDiff = curarrears-prearrears;
				if (arrearsDiff  != 0) {
					String br = "color:red";
					addDataToModel(i, empName, "Arrears", prearrears, curarrears, arrearsDiff, date, br);
				} else {
					String br = "color:green";
					//	addDataToModel(i, empName, "TA", preta, curTa, taDiff, date, br);
				}
				
			}
			if ( objLst[113] != null
					&&  objLst[114] != null) {
				i++;
				Double curdeputAllow =  (Double.parseDouble(objLst[113].toString()));
				Double predeputAllow =  (Double.parseDouble(objLst[114].toString()));
				Double deputAllowDiff = curdeputAllow-predeputAllow;
				if (deputAllowDiff  != 0) {
					String br = "color:red";
					addDataToModel(i, empName, "Deputation_Allow", predeputAllow, curdeputAllow, deputAllowDiff, date, br);
				} else {
					String br = "color:green";
					//	addDataToModel(i, empName, "TA", preta, curTa, taDiff, date, br);
				}
				
			}
			
			if ( objLst[115] != null
					&&  objLst[116] != null) {
				i++;
				Double curtracerAllow =  (Double.parseDouble(objLst[115].toString()));
				Double pretracerAllow =  (Double.parseDouble(objLst[116].toString()));
				Double tracerAllowDiff = curtracerAllow-pretracerAllow;
				if (tracerAllowDiff  != 0) {
					String br = "color:red";
					addDataToModel(i, empName, "Tracer_Allow", pretracerAllow, curtracerAllow, tracerAllowDiff, date, br);
				} else {
					String br = "color:green";
					//	addDataToModel(i, empName, "TA", preta, curTa, taDiff, date, br);
				}
				
			}
			if ( objLst[117] != null
					&&  objLst[118] != null) {
				i++;
				Double curnaksalisedAllow =  (Double.parseDouble(objLst[117].toString()));
				Double prenaksalisedAllow =  (Double.parseDouble(objLst[118].toString()));
				Double naksalisedAllowDiff = curnaksalisedAllow-prenaksalisedAllow;
				if (naksalisedAllowDiff  != 0) {
					String br = "color:red";
					addDataToModel(i, empName, "Naksalised_Allow", prenaksalisedAllow, curnaksalisedAllow, naksalisedAllowDiff, date, br);
				} else {
					String br = "color:green";
					//	addDataToModel(i, empName, "TA", preta, curTa, taDiff, date, br);
				}
				
			}
			if ( objLst[119] != null
					&&  objLst[120] != null) {
				i++;
				Double curhillStatAllow =  (Double.parseDouble(objLst[119].toString()));
				Double prehillStatAllow =  (Double.parseDouble(objLst[120].toString()));
				Double hillStatAllowDiff = curhillStatAllow-prehillStatAllow;
				if (hillStatAllowDiff  != 0) {
					String br = "color:red";
					addDataToModel(i, empName, "HillStation_Allow", prehillStatAllow, curhillStatAllow, hillStatAllowDiff, date, br);
				} else {
					String br = "color:green";
					//	addDataToModel(i, empName, "TA", preta, curTa, taDiff, date, br);
				}
				
			}
			if ( objLst[119] != null
					&&  objLst[120] != null) {
				i++;
				Double curwa =  (Double.parseDouble(objLst[119].toString()));
				Double prewa =  (Double.parseDouble(objLst[120].toString()));
				Double waDiff = curwa-prewa;
				if (waDiff  != 0) {
					String br = "color:red";
					addDataToModel(i, empName, "WA_Allow", prewa, curwa, waDiff, date, br);
				} else {
					String br = "color:green";
					//	addDataToModel(i, empName, "TA", preta, curTa, taDiff, date, br);
				}
				
			}
			if ( objLst[121] != null
					&&  objLst[122] != null) {
				i++;
				Double curnaxalAreaAllow =  (Double.parseDouble(objLst[121].toString()));
				Double prenaxalAreaAllow =  (Double.parseDouble(objLst[122].toString()));
				Double naxalAreaAllowDiff = curnaxalAreaAllow-prenaxalAreaAllow;
				if (naxalAreaAllowDiff  != 0) {
					String br = "color:red";
					addDataToModel(i, empName, "Naxal_Area_Allow", prenaxalAreaAllow, curnaxalAreaAllow, naxalAreaAllowDiff, date, br);
				} else {
					String br = "color:green";
					//	addDataToModel(i, empName, "TA", preta, curTa, taDiff, date, br);
				}
				
			}
			if ( objLst[123] != null
					&&  objLst[124] != null) {
				i++;
				Double curbegis =  (Double.parseDouble(objLst[123].toString()));
				Double prebegis =  (Double.parseDouble(objLst[124].toString()));
				Double begisDiff = curbegis-prebegis;
				if (begisDiff  != 0) {
					String br = "color:red";
					addDataToModel(i, empName, "BEGIS", prebegis, curbegis, begisDiff, date, br);
				} else {
					String br = "color:green";
					//	addDataToModel(i, empName, "TA", preta, curTa, taDiff, date, br);
				}
				
			}
			if ( objLst[125] != null
					&&  objLst[126] != null) {
				i++;
				Double curleavePay =  (Double.parseDouble(objLst[125].toString()));
				Double preleavePay =  (Double.parseDouble(objLst[126].toString()));
				Double leavePayDiff = curleavePay-preleavePay;
				if (leavePayDiff  != 0) {
					String br = "color:red";
					addDataToModel(i, empName, "Leave_Pay", preleavePay, curleavePay, leavePayDiff, date, br);
				} else {
					String br = "color:green";
					//	addDataToModel(i, empName, "TA", preta, curTa, taDiff, date, br);
				}
				if ( objLst[127] != null
						&&  objLst[128] != null) {
					i++;
					Double currajashriShahu =  (Double.parseDouble(objLst[127].toString()));
					Double prerajashriShahu =  (Double.parseDouble(objLst[128].toString()));
					Double rajashriShahuDiff = currajashriShahu-prerajashriShahu;
					if (rajashriShahuDiff  != 0) {
						String br = "color:red";
						addDataToModel(i, empName, "Rajashri_Shahu", prerajashriShahu, currajashriShahu, leavePayDiff, date, br);
					} else {
						String br = "color:green";
						//	addDataToModel(i, empName, "TA", preta, curTa, taDiff, date, br);
					}
				
			}
				if ( objLst[129] != null
						&&  objLst[130] != null) {
					i++;
					Double cursataraSoci =  (Double.parseDouble(objLst[129].toString()));
					Double presataraSoci =  (Double.parseDouble(objLst[130].toString()));
					Double sataraSociDiff = cursataraSoci-presataraSoci;
					if (sataraSociDiff  != 0) {
						String br = "color:red";
						addDataToModel(i, empName, "Satara_Society", presataraSoci, cursataraSoci, sataraSociDiff, date, br);
					} else {
						String br = "color:green";
						//	addDataToModel(i, empName, "TA", preta, curTa, taDiff, date, br);
					}
					
				}
				if ( objLst[131] != null
						&&  objLst[132] != null) {
					i++;
					Double curmantralayaSoci =  (Double.parseDouble(objLst[131].toString()));
					Double premantralayaSoci =  (Double.parseDouble(objLst[132].toString()));
					Double mantralayaSociDiff = curmantralayaSoci-premantralayaSoci;
					if (mantralayaSociDiff  != 0) {
						String br = "color:red";
						addDataToModel(i, empName, "Mantralaya_Society", premantralayaSoci, curmantralayaSoci, mantralayaSociDiff, date, br);
					} else {
						String br = "color:green";
						//	addDataToModel(i, empName, "TA", preta, curTa, taDiff, date, br);
					}
					
				}
				if ( objLst[133] != null
						&&  objLst[134] != null) {
					i++;
					Double curhbaHouse =  (Double.parseDouble(objLst[133].toString()));
					Double prehbaHouse =  (Double.parseDouble(objLst[134].toString()));
					Double hbaHouseDiff = curhbaHouse-prehbaHouse;
					if (hbaHouseDiff  != 0) {
						String br = "color:red";
						addDataToModel(i, empName, "HBA_House", prehbaHouse, curhbaHouse, hbaHouseDiff, date, br);
					} else {
						String br = "color:green";
						//	addDataToModel(i, empName, "TA", preta, curTa, taDiff, date, br);
					}
					
				}
				if ( objLst[135] != null
						&&  objLst[136] != null) {
					i++;
					Double curpanipuravthaCl3or4 =  (Double.parseDouble(objLst[135].toString()));
					Double prepanipuravthaCl3or4 =  (Double.parseDouble(objLst[134].toString()));
					Double panipuravthaCl3or4Diff = curpanipuravthaCl3or4-prepanipuravthaCl3or4;
					if (panipuravthaCl3or4Diff  != 0) {
						String br = "color:red";
						addDataToModel(i, empName, "Panipuravtha_Soc_CL3or4", prepanipuravthaCl3or4, curpanipuravthaCl3or4, panipuravthaCl3or4Diff, date, br);
					} else {
						String br = "color:green";
						//	addDataToModel(i, empName, "TA", preta, curTa, taDiff, date, br);
					}
					
				}
				if ( objLst[137] != null
						&&  objLst[138] != null) {
					i++;
					Double curmlwf =  (Double.parseDouble(objLst[137].toString()));
					Double premlwf =  (Double.parseDouble(objLst[138].toString()));
					Double mlwfDiff = curmlwf-premlwf;
					if (mlwfDiff  != 0) {
						String br = "color:red";
						addDataToModel(i, empName, "MLWF_onlyMJP", premlwf, curmlwf, mlwfDiff, date, br);
					} else {
						String br = "color:green";
						//	addDataToModel(i, empName, "TA", preta, curTa, taDiff, date, br);
					}
					
				}
				if ( objLst[139] != null
						&&  objLst[140] != null) {
					i++;
					Double curmjpSocSolapur =  (Double.parseDouble(objLst[139].toString()));
					Double premjpSocSolapur =  (Double.parseDouble(objLst[140].toString()));
					Double mjpSocSolapurDiff = curmjpSocSolapur-premjpSocSolapur;
					if (mjpSocSolapurDiff  != 0) {
						String br = "color:red";
						addDataToModel(i, empName, "MJP_Soc_Solapur", premjpSocSolapur, curmjpSocSolapur, mjpSocSolapurDiff, date, br);
					} else {
						String br = "color:green";
						//	addDataToModel(i, empName, "TA", preta, curTa, taDiff, date, br);
					}
					
				}
				if ( objLst[141] != null
						&&  objLst[142] != null) {
					i++;
					Double curjalbhavanSoci =  (Double.parseDouble(objLst[141].toString()));
					Double prejalbhavanSoci =  (Double.parseDouble(objLst[142].toString()));
					Double jalbhavanSociDiff = curjalbhavanSoci-prejalbhavanSoci;
					if (jalbhavanSociDiff  != 0) {
						String br = "color:red";
						addDataToModel(i, empName, "Jalbhavan_Soci", prejalbhavanSoci, curjalbhavanSoci, jalbhavanSociDiff, date, br);
					} else {
						String br = "color:green";
						//	addDataToModel(i, empName, "TA", preta, curTa, taDiff, date, br);
					}
					
				}
				if ( objLst[143] != null
						&&  objLst[144] != null) {
					i++;
					Double curmjpSocLatur =  (Double.parseDouble(objLst[143].toString()));
					Double premjpSocLatur =  (Double.parseDouble(objLst[144].toString()));
					Double mjpSocLaturDiff = curmjpSocLatur-premjpSocLatur;
					if (mjpSocLaturDiff  != 0) {
						String br = "color:red";
						addDataToModel(i, empName, "MJP_Soc_Latur", premjpSocLatur, curmjpSocLatur, mjpSocLaturDiff, date, br);
					} else {
						String br = "color:green";
						//	addDataToModel(i, empName, "TA", preta, curTa, taDiff, date, br);
					}
					
				}
				if ( objLst[145] != null
						&&  objLst[146] != null) {
					i++;
					Double curmahaLabwelfare =  (Double.parseDouble(objLst[145].toString()));
					Double premahaLabwelfare =  (Double.parseDouble(objLst[146].toString()));
					Double mahaLabwelfareDiff = curmahaLabwelfare-premahaLabwelfare;
					if (mahaLabwelfareDiff  != 0) {
						String br = "color:red";
						addDataToModel(i, empName, "MahaLab_Welfare_fund", premahaLabwelfare, curmahaLabwelfare, mahaLabwelfareDiff, date, br);
					} else {
						String br = "color:green";
						//	addDataToModel(i, empName, "TA", preta, curTa, taDiff, date, br);
					}
					
				}
				if ( objLst[147] != null
						&&  objLst[148] != null) {
					i++;
					Double cursociLatur =  (Double.parseDouble(objLst[147].toString()));
					Double presociLatur =  (Double.parseDouble(objLst[148].toString()));
					Double mahaLabwelfareDiff = cursociLatur-presociLatur;
					if (mahaLabwelfareDiff  != 0) {
						String br = "color:red";
						addDataToModel(i, empName, "Latur_Soci", presociLatur, cursociLatur, mahaLabwelfareDiff, date, br);
					} else {
						String br = "color:green";
						//	addDataToModel(i, empName, "TA", preta, curTa, taDiff, date, br);
					}
					
				}
				if ( objLst[149] != null
						&&  objLst[150] != null) {
					i++;
					Double cursociAurang =  (Double.parseDouble(objLst[149].toString()));
					Double presociAurang =  (Double.parseDouble(objLst[150].toString()));
					Double sociAurangDiff = cursociAurang-presociAurang;
					if (sociAurangDiff  != 0) {
						String br = "color:red";
						addDataToModel(i, empName, "Aurangabad_Soci", presociAurang, cursociAurang, sociAurangDiff, date, br);
					} else {
						String br = "color:green";
						//	addDataToModel(i, empName, "TA", preta, curTa, taDiff, date, br);
					}
					
				}
				if ( objLst[151] != null
						&&  objLst[152] != null) {
					i++;
					Double cursociNanded =  (Double.parseDouble(objLst[151].toString()));
					Double presociNanded =  (Double.parseDouble(objLst[152].toString()));
					Double sociNandedDiff = cursociNanded-presociNanded;
					if (sociNandedDiff  != 0) {
						String br = "color:red";
						addDataToModel(i, empName, "Nanded_Soci", presociNanded, cursociNanded, sociNandedDiff, date, br);
					} else {
						String br = "color:green";
						//	addDataToModel(i, empName, "TA", preta, curTa, taDiff, date, br);
					}
					
				}
				if ( objLst[153] != null
						&&  objLst[154] != null) {
					i++;
					Double curjalsevaKarmPath =  (Double.parseDouble(objLst[153].toString()));
					Double prejalsevaKarmPath =  (Double.parseDouble(objLst[154].toString()));
					Double jalsevaKarmPathDiff = curjalsevaKarmPath-prejalsevaKarmPath;
					if (jalsevaKarmPathDiff  != 0) {
						String br = "color:red";
						addDataToModel(i, empName, "Jalseva_karma_path", prejalsevaKarmPath, curjalsevaKarmPath, jalsevaKarmPathDiff, date, br);
					} else {
						String br = "color:green";
						//	addDataToModel(i, empName, "TA", preta, curTa, taDiff, date, br);
					}
					
				}
				if ( objLst[155] != null
						&&  objLst[156] != null) {
					i++;
					Double curdaArr =  (Double.parseDouble(objLst[155].toString()));
					Double predaArr =  (Double.parseDouble(objLst[156].toString()));
					Double daArrDiff = curdaArr-predaArr;
					if (daArrDiff  != 0) {
						String br = "color:red";
						addDataToModel(i, empName, "DA_ARR", predaArr, curdaArr, daArrDiff, date, br);
					} else {
						String br = "color:green";
						//	addDataToModel(i, empName, "TA", preta, curTa, taDiff, date, br);
					}
					
				}
				curAll = curAll+(Double.parseDouble(objLst[55].toString()));
				 preAll =allprenettotal+( Double.parseDouble(objLst[56].toString()));
				 alldedtotal =alldedtotal+( Double.parseDouble(objLst[59].toString()));
				 allprededtotal =allprededtotal+( Double.parseDouble(objLst[60].toString()));
				 allcurnettotal =allcurnettotal+( Double.parseDouble(objLst[53].toString()));
				 allprenettotal =allprenettotal+(Double.parseDouble( objLst[54].toString()));
				
			}
			
		}
		String ddoname=null;
		String orgname=null;		
		String ofcname=null;
		String monname=null;
		String premonname=null;
		Long regid=null;
		Long ofcid=null;
		List<Object[]>  ddoinfo = paybillGenerationTrnService.findDDOinfo(messages.getUserName());

		for (Object[] objddoLst : ddoinfo) {
			// String empName;
			regid = Long.parseLong(objddoLst[0].toString());
			 ddoname = objddoLst[1].toString();
			 if(objddoLst[2]!=null)
			 ofcid = Long.parseLong(objddoLst[2].toString());
			 else
				 ofcid=(long) 0;
			
		}
		List<Object[]>  regIdinfo = paybillGenerationTrnService.findregIdinfo(regid);
		for (Object[] objorgLst : regIdinfo) {
			// String empName;
			orgname = objorgLst[2].toString();
			
		}
		
		List<Object[]>  ofcIdinfo = paybillGenerationTrnService.findofcIdinfo(ofcid);
		for (Object[] ofcLst : ofcIdinfo) {
			// String empName;
			ofcname = ofcLst[2].toString();
			
		}
		
		List<Object[]>  monthinfo = paybillGenerationTrnService.findmonthinfo(currmonth);
		for (Object[] monthLst : monthinfo) {
			// String empName;
			monname = monthLst[1].toString();
			
		}
		BigInteger monpre ;
		Long prmonth =0l;
		if(cumo.equals(1l)) {
			 prmonth=12l;
			 BigInteger monsubyear=new BigInteger("1");
			 preyear=curryear.subtract(monsubyear);
			 monpre=BigInteger.valueOf(prmonth);
		}else {
			BigInteger monsub=new BigInteger("1");
			monpre=currmonth.subtract(monsub);
			preyear=curryear;
		}
		
		List<Object[]>  monthpreinfo = paybillGenerationTrnService.findmonthinfo(monpre);
		for (Object[] monthLst : monthpreinfo) {
			// String empName;
			premonname = monthLst[1].toString();
			
		}
		
		Date createdate = commonHomeMethodsService.findbillCreateDate(Integer.parseInt(paybillGenerationTrnId));
		
		model.addAttribute("currentMonthAmountAll", curAll);
		model.addAttribute("previousMonthAmountAll", preAll);
		Double alltotal =curAll-preAll;
		
		model.addAttribute("totalAmountAll", alltotal);
		model.addAttribute("currentdedMonthAmountAll", alldedtotal);
		model.addAttribute("previousdedMonthAmountAll", allprededtotal);
		Double alltotdedtotal =alldedtotal-allprededtotal;
		model.addAttribute("totaldedAmountAll", alltotdedtotal);
		model.addAttribute("currentnetAmountAll", allcurnettotal);
		model.addAttribute("previousnetAmountAll", allprenettotal);
		Double allnetdifftotal =allcurnettotal-allprenettotal;
		model.addAttribute("totalnetAmountAll", allnetdifftotal);
		model.addAttribute("payBillViewApprDelBillModel", payBillViewApprDelBillModel);
		model.addAttribute("lstPayBillObj", lstPayBillObj);
		model.addAttribute("ofcName", ddoname);
		model.addAttribute("officename", ofcname);
		model.addAttribute("orgname", orgname);
		model.addAttribute("currmonyer", monname+" "+curryear);
		model.addAttribute("premonyer", premonname+" "+preyear);
		model.addAttribute("currmonth", monname);
		model.addAttribute("premonth", premonname);
		model.addAttribute("createddate", sdf.format(createdate));
		model.addAttribute("systemdate", sdf.format(new Date()));
		model.addAttribute("paybillGenerationTrnId", paybillGenerationTrnId);
		}
		
		return "/views/report/changestatement";	
		}
	
	@GetMapping("/viewChangeStatementReportPDF/{paybillGenerationTrnId}")
	public String viewChangeStatementReportPDF(@ModelAttribute("payBillViewApprDelBillModel") PayBillViewApprDelBillModel payBillViewApprDelBillModel,
			@PathVariable String paybillGenerationTrnId, Model model, Locale locale, HttpSession session,HttpServletResponse response)
			throws FileNotFoundException, DocumentException {
		lstPayBillObj.clear();
		OrgUserMst messages = (OrgUserMst) session.getAttribute("MY_SESSION_MESSAGES"); 
		SimpleDateFormat sdf =new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		List<Object[]> listA = paybillGenerationTrnService.getChangeStatementReport(paybillGenerationTrnId);
		int i = 0;
				Double curAll = 0D;
				Double preAll = 0D;
				Double alldedtotal = 0D;
				Double allprededtotal = 0D;
				Double allcurnettotal = 0D;
				Double allprenettotal = 0D;
				BigInteger curryear = null;
				BigInteger currmonth = null;
				BigInteger preyear = null;
				BigInteger premonth = null;
				String createddate=null;
				Long cumo=0l;
		for (Object[] objLst : listA) {
			// String empName;
			 createddate = StringHelperUtils.isNullString(objLst[91]);
			String date = null;
			String empName = StringHelperUtils.isNullString(objLst[1]) + "(" + StringHelperUtils.isNullString(objLst[2])
					+ ")";
			
			currmonth=StringHelperUtils.isNullBigInteger(objLst[4]);
			curryear=StringHelperUtils.isNullBigInteger(objLst[3]);
			premonth=StringHelperUtils.isNullBigInteger(objLst[6]);
			preyear=StringHelperUtils.isNullBigInteger(objLst[5]);
			 cumo =StringHelperUtils.isNullBigInteger(objLst[4]).longValue();
			
			Long netpay =StringHelperUtils.isNullBigInteger(objLst[56]).longValue();
			if(netpay!=null && netpay.equals(0l)) {
				if ( objLst[55] != null
						&&  objLst[56] != null) {
					i++;
					Double curGrossAmount =  (Double.parseDouble(objLst[55].toString()));
					Double preGrossAmount =  (Double.parseDouble(objLst[56].toString()));
					Double grossDiff = curGrossAmount-preGrossAmount;
					if (grossDiff  != 0) {
						String br = "color:red";
						addDataToModel(i, empName, "Net Pay Amount", preGrossAmount, curGrossAmount, grossDiff, date, br);
					} else {
						String br = "color:green";
					//	addDataToModel(i, empName, "Gross Amount", preGrossAmount, curGrossAmount, grossDiff, date, br);
					}
					curAll = curAll+(Double.parseDouble(objLst[55].toString()));
					 preAll =allprenettotal+( Double.parseDouble(objLst[56].toString()));
					 alldedtotal =alldedtotal+( Double.parseDouble(objLst[59].toString()));
					 allprededtotal =allprededtotal+( Double.parseDouble(objLst[60].toString()));
					 allcurnettotal =allcurnettotal+( Double.parseDouble(objLst[53].toString()));
					 allprenettotal =allprenettotal+(Double.parseDouble( objLst[54].toString()));
				}
			}else {
			if (objLst[7] != null
					&&  objLst[8] != null) {
				i++;
				Double curBasic =   (Double.parseDouble(objLst[7].toString()));
				Double preBasic =   (Double.parseDouble(objLst[8].toString()));
				Double basicDiff = curBasic-preBasic;
				if (basicDiff != 0) {
					String br = "color:red";
					addDataToModel(i, empName, "Basic Pay", preBasic, curBasic, basicDiff, date, br);
				} else {
					String br = "color:green";
					//addDataToModel(i, empName, "Basic Pay", preBasic, curBasic, basicDiff, date, br);
				}
			}
			if ( objLst[9] != null
					&&  objLst[10] != null) {
				i++;
				Double curDa =  (Double.parseDouble(objLst[9].toString()));
				Double preDa =  (Double.parseDouble(objLst[10].toString()));
				Double daDiff = curDa-preDa;
				if (daDiff != 0) {
					String br = "color:red";
					addDataToModel(i, empName, "DA", preDa, curDa, daDiff, date, br);
				} else {
					String br = "color:green";
					//addDataToModel(i, empName, "DA", preDa, curDa, daDiff, date, br);
				}
			}
			if ( objLst[11] != null
					&&  objLst[12] != null) {
				i++;
				Double curHra =  (Double.parseDouble(objLst[11].toString()));
				Double preHra =  (Double.parseDouble(objLst[12].toString()));
				Double sub = curHra-preHra;
				if (sub  != 0) {
					String br = "color:red";
					addDataToModel(i, empName, "HRA", preHra, curHra, sub, date, br);
				} else {
					String br = "color:green";
					//addDataToModel(i, empName, "HRA", preHra, curHra, sub, date, br);
				}
			}
			if ( objLst[13] != null
					&&  objLst[14] != null) {
				i++;
				Double curCla =  (Double.parseDouble(objLst[13].toString()));
				Double preCla =  (Double.parseDouble(objLst[14].toString()));
				Double claDiff = curCla-preCla;
				if (claDiff  != 0) {
					String br = "color:red";
					addDataToModel(i, empName, "CLA", preCla, curCla, claDiff, date, br);
				} else {
					String br = "color:green";
					//addDataToModel(i, empName, "CLA", preCla, curCla, claDiff, date, br);
				}
			}
			if ( objLst[15] != null
					&&  objLst[16] != null) {
				i++;
				Double curIt =  (Double.parseDouble(objLst[15].toString()));
				Double preIt =  (Double.parseDouble(objLst[16].toString()));
				Double ItDiff = curIt-preIt;
				if (ItDiff  != 0) {
					String br = "color:red";
					addDataToModel(i, empName, "IT", preIt, curIt, ItDiff, date, br);
				} else {
					String br = "color:green";
					//addDataToModel(i, empName, "IT", preIt, curIt, ItDiff, date, br);
				}
			}
			if ( objLst[17] != null
					&&  objLst[18] != null) {
				i++;
				Double curHrr =  (Double.parseDouble(objLst[17].toString()));
				Double preHrr =  (Double.parseDouble(objLst[18].toString()));
				Double hrrDiff = curHrr-preHrr;
				if (hrrDiff  != 0) {
					String br = "color:red";
					addDataToModel(i, empName, "HRR", preHrr, curHrr, hrrDiff, date, br);
				} else {
					String br = "color:green";
					//addDataToModel(i, empName, "HRR", preHrr, curHrr, hrrDiff, date, br);
				}
			}
			if ( objLst[19] != null
					&&  objLst[20] != null) {
				i++;
				Double curPli =  (Double.parseDouble(objLst[19].toString()));
				Double prepli =  (Double.parseDouble(objLst[20].toString()));
				Double pliDiff = curPli-prepli;
				if (pliDiff  != 0) {
					String br = "color:red";
					addDataToModel(i, empName, "PLI", prepli, curPli, pliDiff, date, br);
				} else {
					String br = "color:green";
					//addDataToModel(i, empName, "PLI", prepli, curPli, pliDiff, date, br);
				}
			}
			if ( objLst[21] != null
					&&  objLst[22] != null) {
				i++;
				Double curPt =  (Double.parseDouble(objLst[21].toString()));
				Double prept =  (Double.parseDouble(objLst[22].toString()));
				Double ptDiff = curPt-prept;
				if (ptDiff  != 0) {
					String br = "color:red";
					addDataToModel(i, empName, "PT", prept, curPt, ptDiff, date, br);
				} else {
					String br = "color:green";
					//addDataToModel(i, empName, "PT", prept, curPt, ptDiff, date, br);
				}
			}
			if ( objLst[23] != null
					&&  objLst[24] != null) {
				i++;
				Double curHba =  (Double.parseDouble(objLst[23].toString()));
				Double preHba =  (Double.parseDouble(objLst[24].toString()));
				Double hbaDiff = curHba-preHba;
				if (hbaDiff  != 0) {
					String br = "color:red";
					addDataToModel(i, empName, "HBA", preHba, curHba, hbaDiff, date, br);
				} else {
					String br = "color:green";
				//	addDataToModel(i, empName, "HBA", preHba, curHba, hbaDiff, date, br);
				}
			}
			/*if ( objLst[25]) != null
					&&  objLst[26]) != null) {
				i++;
				Double curGpfAdv =  (objLst[25]));
				Double preGpfAdv =  (objLst[26]));
				Double gpfAdvDiff = curGpfAdv-preGpfAdv;
				if (gpfAdvDiff  != 0) {
					String br = "color:red";
					addDataToModel(i, empName, "HBA", preGpfAdv, curGpfAdv, gpfAdvDiff, date, br);
				} else {
					String br = "color:green";
				//	addDataToModel(i, empName, "HBA", preGpfAdv, curGpfAdv, gpfAdvDiff, date, br);
				}
			}*/
			if ( objLst[25] != null
					&&  objLst[26] != null) {
				i++;
				Double curGpfAdv =  (Double.parseDouble(objLst[25].toString()));
				Double preGpfAdv =  (Double.parseDouble(objLst[26].toString()));
				Double gpfAdvDiff = curGpfAdv-preGpfAdv;
				if (gpfAdvDiff  != 0) {
					String br = "color:red";
					addDataToModel(i, empName, "Gpf Advance", preGpfAdv, curGpfAdv, gpfAdvDiff, date, br);
				} else {
					String br = "color:green";
					//addDataToModel(i, empName, "Gpf Advance", preGpfAdv, curGpfAdv, gpfAdvDiff, date, br);
				}
			}
			if ( objLst[27] != null
					&&  objLst[28] != null) {
				i++;
				Double curGpfIvAdv =  (Double.parseDouble(objLst[27].toString()));
				Double preGpfIvAdv =  (Double.parseDouble(objLst[28].toString()));
				Double gpfIvAdvDiff = curGpfIvAdv-preGpfIvAdv;
				if (gpfIvAdvDiff  != 0) {
					String br = "color:red";
					addDataToModel(i, empName, "Gpf Iv Advance", preGpfIvAdv, curGpfIvAdv, gpfIvAdvDiff, date, br);
				} else {
					String br = "color:green";
					//addDataToModel(i, empName, "Gpf Iv Advance", preGpfIvAdv, curGpfIvAdv, gpfIvAdvDiff, date, br);
				}
			}
			if ( objLst[29] != null
					&&  objLst[30] != null) {
				i++;
				Double curDcps =  (Double.parseDouble(objLst[29].toString()));
				Double preDcps =  (Double.parseDouble(objLst[30].toString()));
				Double dcpsDiff = curDcps-preDcps;
				if (dcpsDiff  != 0) {
					String br = "color:red";
					addDataToModel(i, empName, "DCPS", preDcps, curDcps, dcpsDiff, date, br);
				} else {
					String br = "color:green";
				//	addDataToModel(i, empName, "DCPS", preDcps, curDcps, dcpsDiff, date, br);
				}
			}
			if ( objLst[31] != null
					&&  objLst[32] != null) {
				i++;
				Double curGpay =  (Double.parseDouble(objLst[31].toString()));
				Double preGpay =  (Double.parseDouble(objLst[32].toString()));
				Double gpayDiff = curGpay-preGpay;
				if (gpayDiff  != 0) {
					String br = "color:red";
					addDataToModel(i, empName, "GPAY", preGpay, curGpay, gpayDiff, date, br);
				} else {
					String br = "color:green";
				//	addDataToModel(i, empName, "GPAY", preGpay, curGpay, gpayDiff, date, br);
				}
			}
			if ( objLst[33] != null
					&&  objLst[34] != null) {
				i++;
				Double curGpfGrpAbc =  (Double.parseDouble(objLst[33].toString()));
				Double preGpfGrpAbc =  (Double.parseDouble(objLst[34].toString()));
				Double gpfGrpAbcDiff = curGpfGrpAbc-preGpfGrpAbc;
				if (gpfGrpAbcDiff  != 0) {
					String br = "color:red";
					addDataToModel(i, empName, "Gpf Grp Abc", preGpfGrpAbc, curGpfGrpAbc, gpfGrpAbcDiff, date, br);
				} else {
					String br = "color:green";
				//	addDataToModel(i, empName, "Gpf Grp Abc", preGpfGrpAbc, curGpfGrpAbc, gpfGrpAbcDiff, date, br);
				}
			}
			if ( objLst[35] != null
					&&  objLst[36] != null) {
				i++;
				Double curGpfGrpD =  (Double.parseDouble(objLst[35].toString()));
				Double preGpfGrpD =  (Double.parseDouble(objLst[36].toString()));
				Double gpfGrpDDiff = curGpfGrpD-preGpfGrpD;
				if (gpfGrpDDiff  != 0) {
					String br = "color:red";
					addDataToModel(i, empName, "Gpf Grp D", preGpfGrpD, curGpfGrpD, gpfGrpDDiff, date, br);
				} else {
					String br = "color:green";
				//	addDataToModel(i, empName, "Gpf Grp D", preGpfGrpD, curGpfGrpD, gpfGrpDDiff, date, br);
				}
			}
			if ( objLst[37] != null
					&&  objLst[38] != null) {
				i++;
				Double curGpfAdvGrpAbc =  (Double.parseDouble(objLst[37].toString()));
				Double preGpfAdvGrpAbc =  (Double.parseDouble(objLst[38].toString()));
				Double gpfAdvGrpAbc = curGpfAdvGrpAbc-preGpfAdvGrpAbc;
				if (gpfAdvGrpAbc  != 0) {
					String br = "color:red";
					addDataToModel(i, empName, "Gpf Advance Grp Abc", preGpfAdvGrpAbc, curGpfAdvGrpAbc, gpfAdvGrpAbc,
							date, br);
				} else {
					String br = "color:green";
					/*addDataToModel(i, empName, "Gpf Advance Grp Abc", preGpfAdvGrpAbc, curGpfAdvGrpAbc, gpfAdvGrpAbc,
							date, br);*/
				}
			}
			if ( objLst[39] != null
					&&  objLst[40]!= null) {
				i++;
				Double curGpfAdvGrpD =  (Double.parseDouble(objLst[39].toString()));
				Double preGpfAdvGrpD =  (Double.parseDouble(objLst[40].toString()));
				Double gpfAdvGrpDDiff = curGpfAdvGrpD-preGpfAdvGrpD;
				if (gpfAdvGrpDDiff  != 0) {
					String br = "color:red";
					addDataToModel(i, empName, "Gpf Advance Grp D", preGpfAdvGrpD, curGpfAdvGrpD, gpfAdvGrpDDiff, date,
							br);
				} else {
					String br = "color:green";
					/*addDataToModel(i, empName, "Gpf Advance Grp D", preGpfAdvGrpD, curGpfAdvGrpD, gpfAdvGrpDDiff, date,
							br);*/
				}
			}
			if ( objLst[41] != null
					&&  objLst[42] != null) {
				i++;
				Double curMotorCycleAdv =  (Double.parseDouble(objLst[41].toString()));
				Double preMotorCycleAdv =  (Double.parseDouble(objLst[42].toString()));
				Double MotorCycleAdvDiff = curMotorCycleAdv-preMotorCycleAdv;
				if (MotorCycleAdvDiff  != 0) {
					String br = "color:red";
					addDataToModel(i, empName, "Motor Cycle Advance", preMotorCycleAdv, curMotorCycleAdv,
							MotorCycleAdvDiff, date, br);
				} else {
					String br = "color:green";
					/*addDataToModel(i, empName, "Motor Cycle Advance", preMotorCycleAdv, curMotorCycleAdv,
							MotorCycleAdvDiff, date, br);*/
				}
			}
			if ( objLst[43] != null
					&&  objLst[44] != null) {
				i++;
				Double curOtherDedTry =  (Double.parseDouble(objLst[43].toString()));
				Double preOtherDedTry =  (Double.parseDouble(objLst[44].toString()));
				Double otherDedTryDiff = curOtherDedTry-preOtherDedTry;
				if (otherDedTryDiff  != 0) {
					String br = "color:red";
					addDataToModel(i, empName, "Other ded try", preOtherDedTry, curOtherDedTry, otherDedTryDiff, date,
							br);
				} else {
					String br = "color:green";
				/*	addDataToModel(i, empName, "Other ded try", preOtherDedTry, curOtherDedTry, otherDedTryDiff, date,
							br);*/
				}
			}
			if ( objLst[45] != null
					&&  objLst[46] != null) {
				i++;
				Double curJanjulgis =  (Double.parseDouble(objLst[45].toString()));
				Double preJanjulgis =  (Double.parseDouble(objLst[46].toString()));
				Double janjulgisDiff = curJanjulgis-preJanjulgis;
				if (janjulgisDiff  != 0) {
					String br = "color:red";
					addDataToModel(i, empName, "Janjulgis", preJanjulgis, curJanjulgis, janjulgisDiff, date, br);
				} else {
					String br = "color:green";
				//	addDataToModel(i, empName, "Janjulgis", preJanjulgis, curJanjulgis, janjulgisDiff, date, br);
				}
			}
			if ( objLst[47] != null
					&&  objLst[48] != null) {
				i++;
				Double curJanjulgis =  (Double.parseDouble(objLst[47].toString()));
				Double preJanjulgis =  (Double.parseDouble(objLst[48].toString()));
				Double janjulgisDiff = curJanjulgis-preJanjulgis;
				if (janjulgisDiff  != 0) {
					String br = "color:red";
					addDataToModel(i, empName, "Ded Adjust", preJanjulgis, curJanjulgis, janjulgisDiff, date, br);
				} else {
					String br = "color:green";
					//addDataToModel(i, empName, "Ded Adjust", preJanjulgis, curJanjulgis, janjulgisDiff, date, br);
				}
			}
			if ( objLst[49] != null
					&&  objLst[50] != null) {
				i++;
				Double curGrossAdjust =  (Double.parseDouble(objLst[49].toString()));
				Double preGrossAdjust =  (Double.parseDouble(objLst[50].toString()));
				Double grossAdjustDiff = curGrossAdjust-preGrossAdjust;
				if (grossAdjustDiff  != 0) {
					String br = "color:red";
					addDataToModel(i, empName, "Gross Adjust", preGrossAdjust, curGrossAdjust, grossAdjustDiff, date,
							br);
				} else {
					String br = "color:green";
				/*	addDataToModel(i, empName, "Gross Adjust", preGrossAdjust, curGrossAdjust, grossAdjustDiff, date,
							br);*/
				}
			}
			if ( objLst[51] != null
					&&  objLst[52] != null) {
				i++;
				Double curGrossTotalAmount =  (Double.parseDouble(objLst[51].toString()));
				Double preGrossTotalAmount =  (Double.parseDouble(objLst[52].toString()));
				Double grossTotalAmountDiff = curGrossTotalAmount-preGrossTotalAmount;
				if (grossTotalAmountDiff  != 0) {
					String br = "color:red";
					addDataToModel(i, empName, "Gross Total Amount", preGrossTotalAmount, curGrossTotalAmount,
							grossTotalAmountDiff, date, br);
				} else {
					String br = "color:green";
					/*addDataToModel(i, empName, "Gross Total Amount", preGrossTotalAmount, curGrossTotalAmount,
							grossTotalAmountDiff, date, br);*/
				}
			}
		/*	if ( objLst[53] != null
					&&  objLst[54] != null) {
				i++;
				Double curGrossTotalNetAmount =  (objLst[53].toString()));
				Double preGrossTotalNetAmount =  (objLst[54].toString()));
				Double grossTotalNetAmountDiff = curGrossTotalNetAmount.subtract(preGrossTotalNetAmount);
				if (grossTotalNetAmountDiff  != 0) {
					String br = "color:red";
					addDataToModel(i, empName, "Gross Total Net Amount", preGrossTotalNetAmount, curGrossTotalNetAmount,
							grossTotalNetAmountDiff, date, br);
				} else {
					String br = "color:green";
					addDataToModel(i, empName, "Gross Total Net Amount", preGrossTotalNetAmount, curGrossTotalNetAmount,
							grossTotalNetAmountDiff, date, br);
				}
			}*/
			/*if ( objLst[55] != null
					&&  objLst[56] != null) {
				i++;
				Double curGrossAmount =  (objLst[55].toString()));
				Double preGrossAmount =  (objLst[56].toString()));
				Double grossDiff = curGrossAmount.subtract(preGrossAmount);
				if (grossDiff  != 0) {
					String br = "color:red";
					addDataToModel(i, empName, "Gross Amount", preGrossAmount, curGrossAmount, grossDiff, date, br);
				} else {
					String br = "color:green";
				//	addDataToModel(i, empName, "Gross Amount", preGrossAmount, curGrossAmount, grossDiff, date, br);
				}
			}*/
			if ( objLst[57] != null
					&&  objLst[58] != null) {
				i++;
				Double curGrossAmount =  (Double.parseDouble(objLst[57].toString()));
				Double preGrossAmount =  (Double.parseDouble(objLst[58].toString()));
				Double grossAmountDiff = curGrossAmount-preGrossAmount;
				if (grossAmountDiff  != 0) {
					String br = "color:red";
					addDataToModel(i, empName, "Gross Amount", preGrossAmount, curGrossAmount,
							grossAmountDiff, date, br);
				} else {
					String br = "color:green";
					/*addDataToModel(i, empName, "Gross Amount", preTotalDeduction, curTotalDeduction,
							totalDeductionDiff, date, br);*/
				}
			}
		/*	if ( objLst[57] != null
					&&  objLst[58] != null) {
				i++;
				Double curTotalDeduction =  (Double.parseDouble(objLst[57].toString()));
				Double preTotalDeduction =  (Double.parseDouble(objLst[58].toString()));
				Double totalDeductionDiff = curTotalDeduction-preTotalDeduction;
				if (totalDeductionDiff  != 0) {
					String br = "color:red";
					addDataToModel(i, empName, "Total Deduction", preTotalDeduction, curTotalDeduction,
							totalDeductionDiff, date, br);
				} else {
					String br = "color:green";
					addDataToModel(i, empName, "Total Deduction", preTotalDeduction, curTotalDeduction,
							totalDeductionDiff, date, br);
				}
			}*/
			
			if ( objLst[59] != null
					&&  objLst[60] != null) {
				i++;
				Double curTotalDeduction =  (Double.parseDouble(objLst[59].toString()));
				Double preTotalDeduction =  (Double.parseDouble(objLst[60].toString()));
				Double totalDeductionDiff = curTotalDeduction-preTotalDeduction;
				if (totalDeductionDiff  != 0) {
					String br = "style='color:red";
					addDataToModel(i, empName, "Total Deduction", preTotalDeduction, curTotalDeduction,
							totalDeductionDiff, date, br);
				} else {
					String br = "color:green";
					addDataToModel(i, empName, "Total Deduction", preTotalDeduction, curTotalDeduction,
							totalDeductionDiff, date, br);
				}
			}
			
			/*if ( objLst[59] != null
					&&  objLst[60] != null) {
				i++;
				Double curAdjustdcpsEmployer =  (objLst[59].toString()));
				Double preAdjustdcpsEmployer =  (objLst[60].toString()));
				Double adjustdcpsEmployerDiff = curAdjustdcpsEmployer.subtract(preAdjustdcpsEmployer);
				if (adjustdcpsEmployerDiff  != 0) {
					String br = "style='color:red";
					addDataToModel(i, empName, "Adjust Dcps Employer", preAdjustdcpsEmployer, curAdjustdcpsEmployer,
							adjustdcpsEmployerDiff, date, br);
				} else {
					String br = "color:green";
					addDataToModel(i, empName, "Adjust Dcps Employer", preAdjustdcpsEmployer, curAdjustdcpsEmployer,
							adjustdcpsEmployerDiff, date, br);
				}
			}*/
			if ( objLst[61] != null
					&&  objLst[62] != null) {
				i++;
				Double curDcpsArr =  (Double.parseDouble(objLst[61].toString()));
				Double preDcpsArr =  (Double.parseDouble(objLst[62].toString()));
				Double dcpsArrDiff = curDcpsArr-preDcpsArr;
				if (dcpsArrDiff  != 0) {
					String br = "color:red";
					addDataToModel(i, empName, "Dcps Arr", preDcpsArr, curDcpsArr, dcpsArrDiff, date, br);
				} else {
					String br = "color:green";
					//addDataToModel(i, empName, "Dcps Arr", preDcpsArr, curDcpsArr, dcpsArrDiff, date, br);
				}
			}
			/*if ( objLst[63] != null
					&&  objLst[64] != null) {
				i++;
				Double curDcpsDaArr =  (objLst[53].toString()));
				Double preDcpsDaArr =  (objLst[54].toString()));
				Double dcpsDaArrDiff = curDcpsDaArr.subtract(preDcpsDaArr);
				if (dcpsDaArrDiff  != 0) {
					String br = "color:red";
					addDataToModel(i, empName, "Pre Dcps Da Arr", preDcpsDaArr, curDcpsDaArr, dcpsDaArrDiff, date, br);
				} else {
					String br = "color:green";
					//addDataToModel(i, empName, "Pre Dcps Da Arr", preDcpsDaArr, curDcpsDaArr, dcpsDaArrDiff, date, br);
				}
			}*/
			if ( objLst[65] != null
					&&  objLst[66] != null) {
				i++;
				Double curDcpsDelay =  (Double.parseDouble(objLst[65].toString()));
				Double preDcpsDelayt =  (Double.parseDouble(objLst[66].toString()));
				Double dcpsDelayDiff = curDcpsDelay-preDcpsDelayt;
				if (dcpsDelayDiff  != 0) {
					String br = "color:red";
					addDataToModel(i, empName, "Dcps Delay", preDcpsDelayt, curDcpsDelay, dcpsDelayDiff, date, br);
				} else {
					String br = "color:green";
					//addDataToModel(i, empName, "Dcps Delay", preDcpsDelayt, curDcpsDelay, dcpsDelayDiff, date, br);
				}
			}
			if ( objLst[67] != null
					&&  objLst[68] != null) {
				i++;
				Double curDcpsEmployer =  (Double.parseDouble(objLst[67].toString()));
				Double preDcpsEmployer =  (Double.parseDouble(objLst[68].toString()));
				Double dcpsEmployerDiff = curDcpsEmployer-preDcpsEmployer;
				if (dcpsEmployerDiff  != 0) {
					String br = "color:red";
					addDataToModel(i, empName, "Dcps Employer", preDcpsEmployer, curDcpsEmployer, dcpsEmployerDiff,
							date, br);
				} else {
					String br = "color:green";
				/*	addDataToModel(i, empName, "Dcps Employer", preDcpsEmployer, curDcpsEmployer, dcpsEmployerDiff,
							date, br);*/
				}
			}
			if ( objLst[69] != null
					&&  objLst[70] != null) {
				i++;
				Double curDcpsPayArr =  (Double.parseDouble(objLst[69].toString()));
				Double preDcpsPayArr =  (Double.parseDouble(objLst[70].toString()));
				Double dcpsPayArrDiff = curDcpsPayArr-preDcpsPayArr;
				if (dcpsPayArrDiff  != 0) {
					String br = "color:red";
					addDataToModel(i, empName, "Dcps Pay Arr", preDcpsPayArr, curDcpsPayArr, dcpsPayArrDiff, date, br);
				} else {
					String br = "color:green";
					//addDataToModel(i, empName, "Dcps Pay Arr", preDcpsPayArr, curDcpsPayArr, dcpsPayArrDiff, date, br);
				}
			}
			if ( objLst[71] != null
					&&  objLst[72] != null) {
				i++;
				Double curDcpsRegularRecovery =  (Double.parseDouble(objLst[71].toString()));
				Double preDcpsRegularRecovery =  (Double.parseDouble(objLst[72].toString()));
				Double dcpsRegularRecoveryDiff = curDcpsRegularRecovery-preDcpsRegularRecovery;
				if (dcpsRegularRecoveryDiff  != 0) {
					String br = "color:red";
					addDataToModel(i, empName, "Dcps Regular Recovery", preDcpsRegularRecovery, curDcpsRegularRecovery,
							dcpsRegularRecoveryDiff, date, br);
				} else {
					String br = "color:green";
				/*	addDataToModel(i, empName, "Dcps Regular Recovery", preDcpsRegularRecovery, curDcpsRegularRecovery,
							dcpsRegularRecoveryDiff, date, br);*/
				}
			}
			if ( objLst[73] != null
					&&  objLst[74] != null) {
				i++;
				Double curGis =  (Double.parseDouble(objLst[73].toString()));
				Double preGis =  (Double.parseDouble(objLst[74].toString()));
				Double gisDiff = curGis-preGis;
				if (gisDiff  != 0) {
					String br = "color:red";
					addDataToModel(i, empName, "GIS", preGis, curGis, gisDiff, date, br);
				} else {
					String br = "color:green";
					//addDataToModel(i, empName, "GIS", preGis, curGis, gisDiff, date, br);
				}
			}
			if ( objLst[75] != null
					&&  objLst[76] != null) {
				i++;
				Double curTa =  (Double.parseDouble(objLst[75].toString()));
				Double preta =  (Double.parseDouble(objLst[76].toString()));
				Double taDiff = curTa-preta;
				if (taDiff  != 0) {
					String br = "color:red";
					addDataToModel(i, empName, "TA", preta, curTa, taDiff, date, br);
				} else {
					String br = "color:green";
				//	addDataToModel(i, empName, "TA", preta, curTa, taDiff, date, br);
				}
				
			}
			if ( objLst[79] != null
					&&  objLst[80] != null) {
				i++;
				Double curNpsEmprDeduct =  (Double.parseDouble(objLst[79].toString()));
				Double preNpsEmprDeduct =  (Double.parseDouble(objLst[80].toString()));
				Double npsEmprDeductDiff = curNpsEmprDeduct-preNpsEmprDeduct;
				if (npsEmprDeductDiff  != 0) {
					String br = "color:red";
					addDataToModel(i, empName, "NPS_EMPR_DEDUCT", preNpsEmprDeduct, curNpsEmprDeduct, npsEmprDeductDiff, date, br);
				} else {
					String br = "color:green";
					//	addDataToModel(i, empName, "TA", preta, curTa, taDiff, date, br);
				}
				
			}
			if ( objLst[81] != null
					&&  objLst[82] != null) {
				i++;
				Double curNpsEmprAllow =  (Double.parseDouble(objLst[81].toString()));
				Double preNpsEmprAllow =  (Double.parseDouble(objLst[82].toString()));
				Double npsEmprAllowDiff = curNpsEmprAllow-preNpsEmprAllow;
				if (npsEmprAllowDiff  != 0) {
					String br = "color:red";
					addDataToModel(i, empName, "NPS_EMPR_ALLOW", preNpsEmprAllow, curNpsEmprAllow, npsEmprAllowDiff, date, br);
				} else {
					String br = "color:green";
					//	addDataToModel(i, empName, "TA", preta, curTa, taDiff, date, br);
				}
				
			}
			if ( objLst[83] != null
					&&  objLst[84] != null) {
				i++;
				Double curEmpDcpsRegularRecovery =  (Double.parseDouble(objLst[83].toString()));
				Double preEmpDcpsRegularRecovery =  (Double.parseDouble(objLst[84].toString()));
				Double empDcpsRegularRecoveryDiff = curEmpDcpsRegularRecovery-preEmpDcpsRegularRecovery;
				if (empDcpsRegularRecoveryDiff  != 0) {
					String br = "color:red";
					addDataToModel(i, empName, "EMP_DCPS_REGULAR_RECOVERY", preEmpDcpsRegularRecovery, curEmpDcpsRegularRecovery, empDcpsRegularRecoveryDiff, date, br);
				} else {
					String br = "color:green";
					//	addDataToModel(i, empName, "TA", preta, curTa, taDiff, date, br);
				}
				
			}
			if ( objLst[85] != null
					&&  objLst[86] != null) {
				i++;
				Double curEmpDcpsPayArr =  (Double.parseDouble(objLst[85].toString()));
				Double preEmpDcpsPayArr =  (Double.parseDouble(objLst[86].toString()));
				Double empDcpsPayArrDiff = curEmpDcpsPayArr-preEmpDcpsPayArr;
				if (empDcpsPayArrDiff  != 0) {
					String br = "color:red";
					addDataToModel(i, empName, "EMP_DCPS_PAY_ARR", preEmpDcpsPayArr, curEmpDcpsPayArr, empDcpsPayArrDiff, date, br);
				} else {
					String br = "color:green";
					//	addDataToModel(i, empName, "TA", preta, curTa, taDiff, date, br);
				}
				
			}
			if ( objLst[87] != null
					&&  objLst[88] != null) {
				i++;
				Double curEmpDcpsDelay =  (Double.parseDouble(objLst[87].toString()));
				Double preEmpDcpsDelay =  (Double.parseDouble(objLst[88].toString()));
				Double empDcpsDelayDiff = curEmpDcpsDelay-preEmpDcpsDelay;
				if (empDcpsDelayDiff  != 0) {
					String br = "color:red";
					addDataToModel(i, empName, "EMP_DCPS_DELAY", preEmpDcpsDelay, curEmpDcpsDelay, empDcpsDelayDiff, date, br);
				} else {
					String br = "color:green";
					//	addDataToModel(i, empName, "TA", preta, curTa, taDiff, date, br);
				}
				
			}
			if ( objLst[89] != null
					&&  objLst[90] != null) {
				i++;
				Double curEmpDcpsDaArr =  (Double.parseDouble(objLst[89].toString()));
				Double preEmpDcpsDaArr =  (Double.parseDouble(objLst[90].toString()));
				Double empDcpsDaArrDiff = curEmpDcpsDaArr-preEmpDcpsDaArr;
				if (empDcpsDaArrDiff  != 0) {
					String br = "color:red";
					addDataToModel(i, empName, "Emp_DCPS_DA_ARR", preEmpDcpsDaArr, curEmpDcpsDaArr, empDcpsDaArrDiff, date, br);
				} else {
					String br = "color:green";
					//	addDataToModel(i, empName, "TA", preta, curTa, taDiff, date, br);
				}
				
			}
			
				curAll = curAll+(Double.parseDouble(objLst[55].toString()));
				 preAll =allprenettotal+( Double.parseDouble(objLst[56].toString()));
				 alldedtotal =alldedtotal+( Double.parseDouble(objLst[59].toString()));
				 allprededtotal =allprededtotal+( Double.parseDouble(objLst[60].toString()));
				 allcurnettotal =allcurnettotal+( Double.parseDouble(objLst[53].toString()));
				 allprenettotal =allprenettotal+(Double.parseDouble( objLst[54].toString()));
				
			}
			
		}
		String ddoname=null;
		String orgname=null;		
		String ofcname=null;
		String monname=null;
		String premonname=null;
		Long regid=null;
		Long ofcid=null;
		List<Object[]>  ddoinfo = paybillGenerationTrnService.findDDOinfo(messages.getUserName());

		for (Object[] objddoLst : ddoinfo) {
			// String empName;
			regid = Long.parseLong(objddoLst[0].toString());
			 ddoname = objddoLst[1].toString();
			 ofcid = Long.parseLong(objddoLst[2].toString());
			
		}
		List<Object[]>  regIdinfo = paybillGenerationTrnService.findregIdinfo(regid);
		for (Object[] objorgLst : regIdinfo) {
			// String empName;
			orgname = objorgLst[2].toString();
			
		}
		
		List<Object[]>  ofcIdinfo = paybillGenerationTrnService.findofcIdinfo(ofcid);
		for (Object[] ofcLst : ofcIdinfo) {
			// String empName;
			ofcname = ofcLst[2].toString();
			
		}
		
		List<Object[]>  monthinfo = paybillGenerationTrnService.findmonthinfo(currmonth);
		for (Object[] monthLst : monthinfo) {
			// String empName;
			monname = monthLst[1].toString();
			
		}
		BigInteger monpre ;
		Long prmonth =0l;
		if(cumo.equals(1l)) {
			 prmonth=12l;
			 BigInteger monsubyear=new BigInteger("1");
			 preyear=curryear.subtract(monsubyear);
			 monpre=BigInteger.valueOf(prmonth);
		}else {
			BigInteger monsub=new BigInteger("1");
			monpre=currmonth.subtract(monsub);
			preyear=curryear;
		}
		
		List<Object[]>  monthpreinfo = paybillGenerationTrnService.findmonthinfo(monpre);
		for (Object[] monthLst : monthpreinfo) {
			// String empName;
			premonname = monthLst[1].toString();
			
		}
		Date createdate = commonHomeMethodsService.findbillCreateDate(Integer.parseInt(paybillGenerationTrnId));
		model.addAttribute("currentMonthAmountAll", curAll);
		model.addAttribute("previousMonthAmountAll", preAll);
		Double alltotal =curAll-preAll;
		
		model.addAttribute("totalAmountAll", alltotal);
		model.addAttribute("currentdedMonthAmountAll", alldedtotal);
		model.addAttribute("previousdedMonthAmountAll", allprededtotal);
		Double alltotdedtotal =alldedtotal-allprededtotal;
		model.addAttribute("totaldedAmountAll", alltotdedtotal);
		model.addAttribute("currentnetAmountAll", allcurnettotal);
		model.addAttribute("previousnetAmountAll", allprenettotal);
		Double allnetdifftotal =allcurnettotal-allprededtotal;
		model.addAttribute("totalnetAmountAll", allnetdifftotal);
		model.addAttribute("payBillViewApprDelBillModel", payBillViewApprDelBillModel);
		model.addAttribute("lstPayBillObj", lstPayBillObj);
		model.addAttribute("ofcName", ddoname);
		model.addAttribute("officename", ofcname);
		model.addAttribute("orgname", orgname);
		model.addAttribute("currmonyer", monname+" "+curryear);
		model.addAttribute("premonyer", premonname+" "+preyear);
		model.addAttribute("currmonth", monname);
		model.addAttribute("premonth", premonname);
		model.addAttribute("createddate", sdf.format(createdate));
		model.addAttribute("systemdate", sdf.format(new Date()));
		
		 Map<String,String> data = new HashMap<String,String>();
		    data.put("ddoname",ddoname);
		    try {
				pdfGenaratorUtil.createPdf("views/report/changestatement",model.asMap(),response);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		
		return "/views/report/changestatement";	
		}

	@GetMapping("/viewChangeStatementReportExcel/{paybillGenerationTrnId}")
	public String viewChangeStatementReportExcel(@ModelAttribute("payBillViewApprDelBillModel") PayBillViewApprDelBillModel payBillViewApprDelBillModel,
			@PathVariable String paybillGenerationTrnId, Model model, Locale locale, HttpSession session,HttpServletResponse response)
			throws DocumentException, IOException {
		response.setContentType("application/octet-stream");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());
		lstPayBillObj.clear();
		OrgUserMst messages = (OrgUserMst) session.getAttribute("MY_SESSION_MESSAGES"); 
		SimpleDateFormat sdf =new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		List<Object[]> listA = paybillGenerationTrnService.getChangeStatementReport(paybillGenerationTrnId);
		int i = 0;
				Double curAll = 0D;
				Double preAll = 0D;
				Double alldedtotal = 0D;
				Double allprededtotal = 0D;
				Double allcurnettotal = 0D;
				Double allprenettotal = 0D;
				BigInteger curryear = null;
				BigInteger currmonth = null;
				BigInteger preyear = null;
				BigInteger premonth = null;
				String createddate=null;
				Long cumo=0l;
		for (Object[] objLst : listA) {
			// String empName;
			 createddate = StringHelperUtils.isNullString(objLst[91]);
			String date = null;
			String empName = StringHelperUtils.isNullString(objLst[1]) + "(" + StringHelperUtils.isNullString(objLst[2])
					+ ")";
			
			currmonth=StringHelperUtils.isNullBigInteger(objLst[4]);
			curryear=StringHelperUtils.isNullBigInteger(objLst[3]);
			premonth=StringHelperUtils.isNullBigInteger(objLst[6]);
			preyear=StringHelperUtils.isNullBigInteger(objLst[5]);
			 cumo =StringHelperUtils.isNullBigInteger(objLst[4]).longValue();
			
			Long netpay =StringHelperUtils.isNullBigInteger(objLst[56]).longValue();
			if(netpay!=null && netpay.equals(0l)) {
				if ( objLst[55] != null
						&&  objLst[56] != null) {
					i++;
					Double curGrossAmount =  (Double.parseDouble(objLst[55].toString()));
					Double preGrossAmount =  (Double.parseDouble(objLst[56].toString()));
					Double grossDiff = curGrossAmount-preGrossAmount;
					if (grossDiff  != 0) {
						String br = "color:red";
						addDataToModel(i, empName, "Net Pay Amount", preGrossAmount, curGrossAmount, grossDiff, date, br);
					} else {
						String br = "color:green";
					//	addDataToModel(i, empName, "Gross Amount", preGrossAmount, curGrossAmount, grossDiff, date, br);
					}
					curAll = curAll+(Double.parseDouble(objLst[55].toString()));
					 preAll =allprenettotal+( Double.parseDouble(objLst[56].toString()));
					 alldedtotal =alldedtotal+( Double.parseDouble(objLst[59].toString()));
					 allprededtotal =allprededtotal+( Double.parseDouble(objLst[60].toString()));
					 allcurnettotal =allcurnettotal+( Double.parseDouble(objLst[53].toString()));
					 allprenettotal =allprenettotal+(Double.parseDouble( objLst[54].toString()));
				}
			}else {
			if (objLst[7] != null
					&&  objLst[8] != null) {
				i++;
				Double curBasic =   (Double.parseDouble(objLst[7].toString()));
				Double preBasic =   (Double.parseDouble(objLst[8].toString()));
				Double basicDiff = curBasic-preBasic;
				if (basicDiff != 0) {
					String br = "color:red";
					addDataToModel(i, empName, "Basic Pay", preBasic, curBasic, basicDiff, date, br);
				} else {
					String br = "color:green";
					//addDataToModel(i, empName, "Basic Pay", preBasic, curBasic, basicDiff, date, br);
				}
			}
			if ( objLst[9] != null
					&&  objLst[10] != null) {
				i++;
				Double curDa =  (Double.parseDouble(objLst[9].toString()));
				Double preDa =  (Double.parseDouble(objLst[10].toString()));
				Double daDiff = curDa-preDa;
				if (daDiff != 0) {
					String br = "color:red";
					addDataToModel(i, empName, "DA", preDa, curDa, daDiff, date, br);
				} else {
					String br = "color:green";
					//addDataToModel(i, empName, "DA", preDa, curDa, daDiff, date, br);
				}
			}
			if ( objLst[11] != null
					&&  objLst[12] != null) {
				i++;
				Double curHra =  (Double.parseDouble(objLst[11].toString()));
				Double preHra =  (Double.parseDouble(objLst[12].toString()));
				Double sub = curHra-preHra;
				if (sub  != 0) {
					String br = "color:red";
					addDataToModel(i, empName, "HRA", preHra, curHra, sub, date, br);
				} else {
					String br = "color:green";
					//addDataToModel(i, empName, "HRA", preHra, curHra, sub, date, br);
				}
			}
			if ( objLst[13] != null
					&&  objLst[14] != null) {
				i++;
				Double curCla =  (Double.parseDouble(objLst[13].toString()));
				Double preCla =  (Double.parseDouble(objLst[14].toString()));
				Double claDiff = curCla-preCla;
				if (claDiff  != 0) {
					String br = "color:red";
					addDataToModel(i, empName, "CLA", preCla, curCla, claDiff, date, br);
				} else {
					String br = "color:green";
					//addDataToModel(i, empName, "CLA", preCla, curCla, claDiff, date, br);
				}
			}
			if ( objLst[15] != null
					&&  objLst[16] != null) {
				i++;
				Double curIt =  (Double.parseDouble(objLst[15].toString()));
				Double preIt =  (Double.parseDouble(objLst[16].toString()));
				Double ItDiff = curIt-preIt;
				if (ItDiff  != 0) {
					String br = "color:red";
					addDataToModel(i, empName, "IT", preIt, curIt, ItDiff, date, br);
				} else {
					String br = "color:green";
					//addDataToModel(i, empName, "IT", preIt, curIt, ItDiff, date, br);
				}
			}
			if ( objLst[17] != null
					&&  objLst[18] != null) {
				i++;
				Double curHrr =  (Double.parseDouble(objLst[17].toString()));
				Double preHrr =  (Double.parseDouble(objLst[18].toString()));
				Double hrrDiff = curHrr-preHrr;
				if (hrrDiff  != 0) {
					String br = "color:red";
					addDataToModel(i, empName, "HRR", preHrr, curHrr, hrrDiff, date, br);
				} else {
					String br = "color:green";
					//addDataToModel(i, empName, "HRR", preHrr, curHrr, hrrDiff, date, br);
				}
			}
			if ( objLst[19] != null
					&&  objLst[20] != null) {
				i++;
				Double curPli =  (Double.parseDouble(objLst[19].toString()));
				Double prepli =  (Double.parseDouble(objLst[20].toString()));
				Double pliDiff = curPli-prepli;
				if (pliDiff  != 0) {
					String br = "color:red";
					addDataToModel(i, empName, "PLI", prepli, curPli, pliDiff, date, br);
				} else {
					String br = "color:green";
					//addDataToModel(i, empName, "PLI", prepli, curPli, pliDiff, date, br);
				}
			}
			if ( objLst[21] != null
					&&  objLst[22] != null) {
				i++;
				Double curPt =  (Double.parseDouble(objLst[21].toString()));
				Double prept =  (Double.parseDouble(objLst[22].toString()));
				Double ptDiff = curPt-prept;
				if (ptDiff  != 0) {
					String br = "color:red";
					addDataToModel(i, empName, "PT", prept, curPt, ptDiff, date, br);
				} else {
					String br = "color:green";
					//addDataToModel(i, empName, "PT", prept, curPt, ptDiff, date, br);
				}
			}
			if ( objLst[23] != null
					&&  objLst[24] != null) {
				i++;
				Double curHba =  (Double.parseDouble(objLst[23].toString()));
				Double preHba =  (Double.parseDouble(objLst[24].toString()));
				Double hbaDiff = curHba-preHba;
				if (hbaDiff  != 0) {
					String br = "color:red";
					addDataToModel(i, empName, "HBA", preHba, curHba, hbaDiff, date, br);
				} else {
					String br = "color:green";
				//	addDataToModel(i, empName, "HBA", preHba, curHba, hbaDiff, date, br);
				}
			}
			/*if ( objLst[25]) != null
					&&  objLst[26]) != null) {
				i++;
				Double curGpfAdv =  (objLst[25]));
				Double preGpfAdv =  (objLst[26]));
				Double gpfAdvDiff = curGpfAdv-preGpfAdv;
				if (gpfAdvDiff  != 0) {
					String br = "color:red";
					addDataToModel(i, empName, "HBA", preGpfAdv, curGpfAdv, gpfAdvDiff, date, br);
				} else {
					String br = "color:green";
				//	addDataToModel(i, empName, "HBA", preGpfAdv, curGpfAdv, gpfAdvDiff, date, br);
				}
			}*/
			if ( objLst[25] != null
					&&  objLst[26] != null) {
				i++;
				Double curGpfAdv =  (Double.parseDouble(objLst[25].toString()));
				Double preGpfAdv =  (Double.parseDouble(objLst[26].toString()));
				Double gpfAdvDiff = curGpfAdv-preGpfAdv;
				if (gpfAdvDiff  != 0) {
					String br = "color:red";
					addDataToModel(i, empName, "Gpf Advance", preGpfAdv, curGpfAdv, gpfAdvDiff, date, br);
				} else {
					String br = "color:green";
					//addDataToModel(i, empName, "Gpf Advance", preGpfAdv, curGpfAdv, gpfAdvDiff, date, br);
				}
			}
			if ( objLst[27] != null
					&&  objLst[28] != null) {
				i++;
				Double curGpfIvAdv =  (Double.parseDouble(objLst[27].toString()));
				Double preGpfIvAdv =  (Double.parseDouble(objLst[28].toString()));
				Double gpfIvAdvDiff = curGpfIvAdv-preGpfIvAdv;
				if (gpfIvAdvDiff  != 0) {
					String br = "color:red";
					addDataToModel(i, empName, "Gpf Iv Advance", preGpfIvAdv, curGpfIvAdv, gpfIvAdvDiff, date, br);
				} else {
					String br = "color:green";
					//addDataToModel(i, empName, "Gpf Iv Advance", preGpfIvAdv, curGpfIvAdv, gpfIvAdvDiff, date, br);
				}
			}
			if ( objLst[29] != null
					&&  objLst[30] != null) {
				i++;
				Double curDcps =  (Double.parseDouble(objLst[29].toString()));
				Double preDcps =  (Double.parseDouble(objLst[30].toString()));
				Double dcpsDiff = curDcps-preDcps;
				if (dcpsDiff  != 0) {
					String br = "color:red";
					addDataToModel(i, empName, "DCPS", preDcps, curDcps, dcpsDiff, date, br);
				} else {
					String br = "color:green";
				//	addDataToModel(i, empName, "DCPS", preDcps, curDcps, dcpsDiff, date, br);
				}
			}
			if ( objLst[31] != null
					&&  objLst[32] != null) {
				i++;
				Double curGpay =  (Double.parseDouble(objLst[31].toString()));
				Double preGpay =  (Double.parseDouble(objLst[32].toString()));
				Double gpayDiff = curGpay-preGpay;
				if (gpayDiff  != 0) {
					String br = "color:red";
					addDataToModel(i, empName, "GPAY", preGpay, curGpay, gpayDiff, date, br);
				} else {
					String br = "color:green";
				//	addDataToModel(i, empName, "GPAY", preGpay, curGpay, gpayDiff, date, br);
				}
			}
			if ( objLst[33] != null
					&&  objLst[34] != null) {
				i++;
				Double curGpfGrpAbc =  (Double.parseDouble(objLst[33].toString()));
				Double preGpfGrpAbc =  (Double.parseDouble(objLst[34].toString()));
				Double gpfGrpAbcDiff = curGpfGrpAbc-preGpfGrpAbc;
				if (gpfGrpAbcDiff  != 0) {
					String br = "color:red";
					addDataToModel(i, empName, "Gpf Grp Abc", preGpfGrpAbc, curGpfGrpAbc, gpfGrpAbcDiff, date, br);
				} else {
					String br = "color:green";
				//	addDataToModel(i, empName, "Gpf Grp Abc", preGpfGrpAbc, curGpfGrpAbc, gpfGrpAbcDiff, date, br);
				}
			}
			if ( objLst[35] != null
					&&  objLst[36] != null) {
				i++;
				Double curGpfGrpD =  (Double.parseDouble(objLst[35].toString()));
				Double preGpfGrpD =  (Double.parseDouble(objLst[36].toString()));
				Double gpfGrpDDiff = curGpfGrpD-preGpfGrpD;
				if (gpfGrpDDiff  != 0) {
					String br = "color:red";
					addDataToModel(i, empName, "Gpf Grp D", preGpfGrpD, curGpfGrpD, gpfGrpDDiff, date, br);
				} else {
					String br = "color:green";
				//	addDataToModel(i, empName, "Gpf Grp D", preGpfGrpD, curGpfGrpD, gpfGrpDDiff, date, br);
				}
			}
			if ( objLst[37] != null
					&&  objLst[38] != null) {
				i++;
				Double curGpfAdvGrpAbc =  (Double.parseDouble(objLst[37].toString()));
				Double preGpfAdvGrpAbc =  (Double.parseDouble(objLst[38].toString()));
				Double gpfAdvGrpAbc = curGpfAdvGrpAbc-preGpfAdvGrpAbc;
				if (gpfAdvGrpAbc  != 0) {
					String br = "color:red";
					addDataToModel(i, empName, "Gpf Advance Grp Abc", preGpfAdvGrpAbc, curGpfAdvGrpAbc, gpfAdvGrpAbc,
							date, br);
				} else {
					String br = "color:green";
					/*addDataToModel(i, empName, "Gpf Advance Grp Abc", preGpfAdvGrpAbc, curGpfAdvGrpAbc, gpfAdvGrpAbc,
							date, br);*/
				}
			}
			if ( objLst[39] != null
					&&  objLst[40]!= null) {
				i++;
				Double curGpfAdvGrpD =  (Double.parseDouble(objLst[39].toString()));
				Double preGpfAdvGrpD =  (Double.parseDouble(objLst[40].toString()));
				Double gpfAdvGrpDDiff = curGpfAdvGrpD-preGpfAdvGrpD;
				if (gpfAdvGrpDDiff  != 0) {
					String br = "color:red";
					addDataToModel(i, empName, "Gpf Advance Grp D", preGpfAdvGrpD, curGpfAdvGrpD, gpfAdvGrpDDiff, date,
							br);
				} else {
					String br = "color:green";
					/*addDataToModel(i, empName, "Gpf Advance Grp D", preGpfAdvGrpD, curGpfAdvGrpD, gpfAdvGrpDDiff, date,
							br);*/
				}
			}
			if ( objLst[41] != null
					&&  objLst[42] != null) {
				i++;
				Double curMotorCycleAdv =  (Double.parseDouble(objLst[41].toString()));
				Double preMotorCycleAdv =  (Double.parseDouble(objLst[42].toString()));
				Double MotorCycleAdvDiff = curMotorCycleAdv-preMotorCycleAdv;
				if (MotorCycleAdvDiff  != 0) {
					String br = "color:red";
					addDataToModel(i, empName, "Motor Cycle Advance", preMotorCycleAdv, curMotorCycleAdv,
							MotorCycleAdvDiff, date, br);
				} else {
					String br = "color:green";
					/*addDataToModel(i, empName, "Motor Cycle Advance", preMotorCycleAdv, curMotorCycleAdv,
							MotorCycleAdvDiff, date, br);*/
				}
			}
			if ( objLst[43] != null
					&&  objLst[44] != null) {
				i++;
				Double curOtherDedTry =  (Double.parseDouble(objLst[43].toString()));
				Double preOtherDedTry =  (Double.parseDouble(objLst[44].toString()));
				Double otherDedTryDiff = curOtherDedTry-preOtherDedTry;
				if (otherDedTryDiff  != 0) {
					String br = "color:red";
					addDataToModel(i, empName, "Other ded try", preOtherDedTry, curOtherDedTry, otherDedTryDiff, date,
							br);
				} else {
					String br = "color:green";
				/*	addDataToModel(i, empName, "Other ded try", preOtherDedTry, curOtherDedTry, otherDedTryDiff, date,
							br);*/
				}
			}
			if ( objLst[45] != null
					&&  objLst[46] != null) {
				i++;
				Double curJanjulgis =  (Double.parseDouble(objLst[45].toString()));
				Double preJanjulgis =  (Double.parseDouble(objLst[46].toString()));
				Double janjulgisDiff = curJanjulgis-preJanjulgis;
				if (janjulgisDiff  != 0) {
					String br = "color:red";
					addDataToModel(i, empName, "Janjulgis", preJanjulgis, curJanjulgis, janjulgisDiff, date, br);
				} else {
					String br = "color:green";
				//	addDataToModel(i, empName, "Janjulgis", preJanjulgis, curJanjulgis, janjulgisDiff, date, br);
				}
			}
			if ( objLst[47] != null
					&&  objLst[48] != null) {
				i++;
				Double curJanjulgis =  (Double.parseDouble(objLst[47].toString()));
				Double preJanjulgis =  (Double.parseDouble(objLst[48].toString()));
				Double janjulgisDiff = curJanjulgis-preJanjulgis;
				if (janjulgisDiff  != 0) {
					String br = "color:red";
					addDataToModel(i, empName, "Ded Adjust", preJanjulgis, curJanjulgis, janjulgisDiff, date, br);
				} else {
					String br = "color:green";
					//addDataToModel(i, empName, "Ded Adjust", preJanjulgis, curJanjulgis, janjulgisDiff, date, br);
				}
			}
			if ( objLst[49] != null
					&&  objLst[50] != null) {
				i++;
				Double curGrossAdjust =  (Double.parseDouble(objLst[49].toString()));
				Double preGrossAdjust =  (Double.parseDouble(objLst[50].toString()));
				Double grossAdjustDiff = curGrossAdjust-preGrossAdjust;
				if (grossAdjustDiff  != 0) {
					String br = "color:red";
					addDataToModel(i, empName, "Gross Adjust", preGrossAdjust, curGrossAdjust, grossAdjustDiff, date,
							br);
				} else {
					String br = "color:green";
				/*	addDataToModel(i, empName, "Gross Adjust", preGrossAdjust, curGrossAdjust, grossAdjustDiff, date,
							br);*/
				}
			}
			if ( objLst[51] != null
					&&  objLst[52] != null) {
				i++;
				Double curGrossTotalAmount =  (Double.parseDouble(objLst[51].toString()));
				Double preGrossTotalAmount =  (Double.parseDouble(objLst[52].toString()));
				Double grossTotalAmountDiff = curGrossTotalAmount-preGrossTotalAmount;
				if (grossTotalAmountDiff  != 0) {
					String br = "color:red";
					addDataToModel(i, empName, "Gross Total Amount", preGrossTotalAmount, curGrossTotalAmount,
							grossTotalAmountDiff, date, br);
				} else {
					String br = "color:green";
					/*addDataToModel(i, empName, "Gross Total Amount", preGrossTotalAmount, curGrossTotalAmount,
							grossTotalAmountDiff, date, br);*/
				}
			}
		/*	if ( objLst[53] != null
					&&  objLst[54] != null) {
				i++;
				Double curGrossTotalNetAmount =  (objLst[53].toString()));
				Double preGrossTotalNetAmount =  (objLst[54].toString()));
				Double grossTotalNetAmountDiff = curGrossTotalNetAmount.subtract(preGrossTotalNetAmount);
				if (grossTotalNetAmountDiff  != 0) {
					String br = "color:red";
					addDataToModel(i, empName, "Gross Total Net Amount", preGrossTotalNetAmount, curGrossTotalNetAmount,
							grossTotalNetAmountDiff, date, br);
				} else {
					String br = "color:green";
					addDataToModel(i, empName, "Gross Total Net Amount", preGrossTotalNetAmount, curGrossTotalNetAmount,
							grossTotalNetAmountDiff, date, br);
				}
			}*/
			/*if ( objLst[55] != null
					&&  objLst[56] != null) {
				i++;
				Double curGrossAmount =  (objLst[55].toString()));
				Double preGrossAmount =  (objLst[56].toString()));
				Double grossDiff = curGrossAmount.subtract(preGrossAmount);
				if (grossDiff  != 0) {
					String br = "color:red";
					addDataToModel(i, empName, "Gross Amount", preGrossAmount, curGrossAmount, grossDiff, date, br);
				} else {
					String br = "color:green";
				//	addDataToModel(i, empName, "Gross Amount", preGrossAmount, curGrossAmount, grossDiff, date, br);
				}
			}*/
			if ( objLst[57] != null
					&&  objLst[58] != null) {
				i++;
				Double curTotalDeduction =  (Double.parseDouble(objLst[57].toString()));
				Double preTotalDeduction =  (Double.parseDouble(objLst[58].toString()));
				Double totalDeductionDiff = curTotalDeduction-preTotalDeduction;
				if (totalDeductionDiff  != 0) {
					String br = "color:red";
					addDataToModel(i, empName, "Total Deduction", preTotalDeduction, curTotalDeduction,
							totalDeductionDiff, date, br);
				} else {
					String br = "color:green";
					/*addDataToModel(i, empName, "Total Deduction", preTotalDeduction, curTotalDeduction,
							totalDeductionDiff, date, br);*/
				}
			}
			/*if ( objLst[59] != null
					&&  objLst[60] != null) {
				i++;
				Double curAdjustdcpsEmployer =  (objLst[59].toString()));
				Double preAdjustdcpsEmployer =  (objLst[60].toString()));
				Double adjustdcpsEmployerDiff = curAdjustdcpsEmployer.subtract(preAdjustdcpsEmployer);
				if (adjustdcpsEmployerDiff  != 0) {
					String br = "style='color:red";
					addDataToModel(i, empName, "Adjust Dcps Employer", preAdjustdcpsEmployer, curAdjustdcpsEmployer,
							adjustdcpsEmployerDiff, date, br);
				} else {
					String br = "color:green";
					addDataToModel(i, empName, "Adjust Dcps Employer", preAdjustdcpsEmployer, curAdjustdcpsEmployer,
							adjustdcpsEmployerDiff, date, br);
				}
			}*/
			if ( objLst[61] != null
					&&  objLst[62] != null) {
				i++;
				Double curDcpsArr =  (Double.parseDouble(objLst[61].toString()));
				Double preDcpsArr =  (Double.parseDouble(objLst[62].toString()));
				Double dcpsArrDiff = curDcpsArr-preDcpsArr;
				if (dcpsArrDiff  != 0) {
					String br = "color:red";
					addDataToModel(i, empName, "Dcps Arr", preDcpsArr, curDcpsArr, dcpsArrDiff, date, br);
				} else {
					String br = "color:green";
					//addDataToModel(i, empName, "Dcps Arr", preDcpsArr, curDcpsArr, dcpsArrDiff, date, br);
				}
			}
			/*if ( objLst[63] != null
					&&  objLst[64] != null) {
				i++;
				Double curDcpsDaArr =  (objLst[53].toString()));
				Double preDcpsDaArr =  (objLst[54].toString()));
				Double dcpsDaArrDiff = curDcpsDaArr.subtract(preDcpsDaArr);
				if (dcpsDaArrDiff  != 0) {
					String br = "color:red";
					addDataToModel(i, empName, "Pre Dcps Da Arr", preDcpsDaArr, curDcpsDaArr, dcpsDaArrDiff, date, br);
				} else {
					String br = "color:green";
					//addDataToModel(i, empName, "Pre Dcps Da Arr", preDcpsDaArr, curDcpsDaArr, dcpsDaArrDiff, date, br);
				}
			}*/
			if ( objLst[65] != null
					&&  objLst[66] != null) {
				i++;
				Double curDcpsDelay =  (Double.parseDouble(objLst[65].toString()));
				Double preDcpsDelayt =  (Double.parseDouble(objLst[66].toString()));
				Double dcpsDelayDiff = curDcpsDelay-preDcpsDelayt;
				if (dcpsDelayDiff  != 0) {
					String br = "color:red";
					addDataToModel(i, empName, "Dcps Delay", preDcpsDelayt, curDcpsDelay, dcpsDelayDiff, date, br);
				} else {
					String br = "color:green";
					//addDataToModel(i, empName, "Dcps Delay", preDcpsDelayt, curDcpsDelay, dcpsDelayDiff, date, br);
				}
			}
			if ( objLst[67] != null
					&&  objLst[68] != null) {
				i++;
				Double curDcpsEmployer =  (Double.parseDouble(objLst[67].toString()));
				Double preDcpsEmployer =  (Double.parseDouble(objLst[68].toString()));
				Double dcpsEmployerDiff = curDcpsEmployer-preDcpsEmployer;
				if (dcpsEmployerDiff  != 0) {
					String br = "color:red";
					addDataToModel(i, empName, "Dcps Employer", preDcpsEmployer, curDcpsEmployer, dcpsEmployerDiff,
							date, br);
				} else {
					String br = "color:green";
				/*	addDataToModel(i, empName, "Dcps Employer", preDcpsEmployer, curDcpsEmployer, dcpsEmployerDiff,
							date, br);*/
				}
			}
			if ( objLst[69] != null
					&&  objLst[70] != null) {
				i++;
				Double curDcpsPayArr =  (Double.parseDouble(objLst[69].toString()));
				Double preDcpsPayArr =  (Double.parseDouble(objLst[70].toString()));
				Double dcpsPayArrDiff = curDcpsPayArr-preDcpsPayArr;
				if (dcpsPayArrDiff  != 0) {
					String br = "color:red";
					addDataToModel(i, empName, "Dcps Pay Arr", preDcpsPayArr, curDcpsPayArr, dcpsPayArrDiff, date, br);
				} else {
					String br = "color:green";
					//addDataToModel(i, empName, "Dcps Pay Arr", preDcpsPayArr, curDcpsPayArr, dcpsPayArrDiff, date, br);
				}
			}
			if ( objLst[71] != null
					&&  objLst[72] != null) {
				i++;
				Double curDcpsRegularRecovery =  (Double.parseDouble(objLst[71].toString()));
				Double preDcpsRegularRecovery =  (Double.parseDouble(objLst[72].toString()));
				Double dcpsRegularRecoveryDiff = curDcpsRegularRecovery-preDcpsRegularRecovery;
				if (dcpsRegularRecoveryDiff  != 0) {
					String br = "color:red";
					addDataToModel(i, empName, "Dcps Regular Recovery", preDcpsRegularRecovery, curDcpsRegularRecovery,
							dcpsRegularRecoveryDiff, date, br);
				} else {
					String br = "color:green";
				/*	addDataToModel(i, empName, "Dcps Regular Recovery", preDcpsRegularRecovery, curDcpsRegularRecovery,
							dcpsRegularRecoveryDiff, date, br);*/
				}
			}
			if ( objLst[73] != null
					&&  objLst[74] != null) {
				i++;
				Double curGis =  (Double.parseDouble(objLst[73].toString()));
				Double preGis =  (Double.parseDouble(objLst[74].toString()));
				Double gisDiff = curGis-preGis;
				if (gisDiff  != 0) {
					String br = "color:red";
					addDataToModel(i, empName, "GIS", preGis, curGis, gisDiff, date, br);
				} else {
					String br = "color:green";
					//addDataToModel(i, empName, "GIS", preGis, curGis, gisDiff, date, br);
				}
			}
			if ( objLst[75] != null
					&&  objLst[76] != null) {
				i++;
				Double curTa =  (Double.parseDouble(objLst[75].toString()));
				Double preta =  (Double.parseDouble(objLst[76].toString()));
				Double taDiff = curTa-preta;
				if (taDiff  != 0) {
					String br = "color:red";
					addDataToModel(i, empName, "TA", preta, curTa, taDiff, date, br);
				} else {
					String br = "color:green";
				//	addDataToModel(i, empName, "TA", preta, curTa, taDiff, date, br);
				}
				
			}
				curAll = curAll+(Double.parseDouble(objLst[55].toString()));
				 preAll =allprenettotal+( Double.parseDouble(objLst[56].toString()));
				 alldedtotal =alldedtotal+( Double.parseDouble(objLst[59].toString()));
				 allprededtotal =allprededtotal+( Double.parseDouble(objLst[60].toString()));
				 allcurnettotal =allcurnettotal+( Double.parseDouble(objLst[53].toString()));
				 allprenettotal =allprenettotal+(Double.parseDouble( objLst[54].toString()));
				
			}
			
		}
		
		 String headerKey = "Content-Disposition";
	        String headerValue = "attachment; filename=ChangeStatement_" + currentDateTime + " "+messages.getUserName()+".xlsx";
	        response.setHeader(headerKey, headerValue);
	         
	         
	         
		String ddoname=null;
		String orgname=null;		
		String ofcname=null;
		String monname=null;
		String premonname=null;
		Long regid=null;
		Long ofcid=null;
		List<Object[]>  ddoinfo = paybillGenerationTrnService.findDDOinfo(messages.getUserName());

		for (Object[] objddoLst : ddoinfo) {
			// String empName;
			regid = Long.parseLong(objddoLst[0].toString());
			 ddoname = objddoLst[1].toString();
			 ofcid = Long.parseLong(objddoLst[2].toString());
			
		}
		List<Object[]>  regIdinfo = paybillGenerationTrnService.findregIdinfo(regid);
		for (Object[] objorgLst : regIdinfo) {
			// String empName;
			orgname = objorgLst[2].toString();
			
		}
		
		List<Object[]>  ofcIdinfo = paybillGenerationTrnService.findofcIdinfo(ofcid);
		for (Object[] ofcLst : ofcIdinfo) {
			// String empName;
			ofcname = ofcLst[2].toString();
			
		}
		
		List<Object[]>  monthinfo = paybillGenerationTrnService.findmonthinfo(currmonth);
		for (Object[] monthLst : monthinfo) {
			// String empName;
			monname = monthLst[1].toString();
			
		}
		BigInteger monpre ;
		Long prmonth =0l;
		if(cumo.equals(1l)) {
			 prmonth=12l;
			 BigInteger monsubyear=new BigInteger("1");
			 preyear=curryear.subtract(monsubyear);
			 monpre=BigInteger.valueOf(prmonth);
		}else {
			BigInteger monsub=new BigInteger("1");
			monpre=currmonth.subtract(monsub);
			preyear=curryear;
		}
		
		List<Object[]>  monthpreinfo = paybillGenerationTrnService.findmonthinfo(monpre);
		for (Object[] monthLst : monthpreinfo) {
			// String empName;
			premonname = monthLst[1].toString();
			
		}
		Date createdate = commonHomeMethodsService.findbillCreateDate(Integer.parseInt(paybillGenerationTrnId));
		model.addAttribute("currentMonthAmountAll", curAll);
		model.addAttribute("previousMonthAmountAll", preAll);
		Double alltotal =curAll-preAll;
		
		model.addAttribute("totalAmountAll", alltotal);
		model.addAttribute("currentdedMonthAmountAll", alldedtotal);
		model.addAttribute("previousdedMonthAmountAll", allprededtotal);
		Double alltotdedtotal =alldedtotal-allprededtotal;
		model.addAttribute("totaldedAmountAll", alltotdedtotal);
		model.addAttribute("currentnetAmountAll", allcurnettotal);
		model.addAttribute("previousnetAmountAll", allprenettotal);
		Double allnetdifftotal =allcurnettotal-allprededtotal;
		model.addAttribute("totalnetAmountAll", allnetdifftotal);
		model.addAttribute("payBillViewApprDelBillModel", payBillViewApprDelBillModel);
		model.addAttribute("lstPayBillObj", lstPayBillObj);
		model.addAttribute("ofcName", ddoname);
		model.addAttribute("officename", ofcname);
		model.addAttribute("orgname", orgname);
		model.addAttribute("currmonyer", monname+" "+curryear);
		model.addAttribute("premonyer", premonname+" "+preyear);
		model.addAttribute("currmonth", monname);
		model.addAttribute("premonth", premonname);
		model.addAttribute("createddate", sdf.format(createdate));
		model.addAttribute("systemdate", sdf.format(new Date()));
		
         
	        UserExcelExporter excelExporter = new UserExcelExporter(lstPayBillObj);
	         
	        excelExporter.export(response); 
		return "/views/report/changestatement";	
		}


	     
	public void addDataToModel(int srNo, String empName, String componentName, Double previousAmount,
			Double currentAmount, Double difference, String date, String color) {
		PayBillViewApprDelBillModel payBillViewApprDelBillModel = new PayBillViewApprDelBillModel();
		payBillViewApprDelBillModel.setEmpName(empName);
		payBillViewApprDelBillModel.setComponentName(componentName);
		payBillViewApprDelBillModel.setPreviousMonthAmount(previousAmount);
		payBillViewApprDelBillModel.setCurrentMonthAmount(currentAmount);
		payBillViewApprDelBillModel.setDifference(difference);
		payBillViewApprDelBillModel.setDateAndTime(date);
		payBillViewApprDelBillModel.setColor(color);
		lstPayBillObj.add(payBillViewApprDelBillModel);
	}
	

	 public void drawTable(int srNo, String empName, String componentName, Double previousAmount, Double currentAmount, Double difference, String date, BaseColor color) {
	  PdfPCell cell1 = new PdfPCell(new Paragraph("" + srNo));
	  PdfPCell cell2 = new PdfPCell(new Paragraph("" + empName));
	  PdfPCell cell3 = new PdfPCell(new Paragraph("" + componentName));
	  PdfPCell cell4 = new PdfPCell(new Paragraph("" + previousAmount));
//	  cell4.setBackgroundColor(color);
	  PdfPCell cell5 = new PdfPCell(new Paragraph("" + currentAmount));
//	  cell5.setBackgroundColor(color);
	  PdfPCell cell6 = new PdfPCell(new Paragraph("" + difference));
//	  cell6.setBackgroundColor(color);
	  PdfPCell cell7 = new PdfPCell(new Paragraph("" + date));
	  cell4.setHorizontalAlignment(Element.ALIGN_RIGHT);
	  cell4.setVerticalAlignment(Element.ALIGN_MIDDLE);
	  cell5.setHorizontalAlignment(Element.ALIGN_RIGHT);
	  cell5.setVerticalAlignment(Element.ALIGN_MIDDLE);
	  cell6.setHorizontalAlignment(Element.ALIGN_RIGHT);
	  cell7.setVerticalAlignment(Element.ALIGN_MIDDLE);
	  table.addCell(cell1);
	  table.addCell(cell2);
	  table.addCell(cell3);
	  table.addCell(cell4);
	  table.addCell(cell5);
	  table.addCell(cell6);
	  table.addCell(cell7);
	 }
	
	@GetMapping(value="/findPayBillByBillNumber/{billNumber}/{yearName}/{monthName}")
	public @ResponseBody List<Object[]> findPayBillByBillNumber(@PathVariable String billNumber,@PathVariable int yearName,@PathVariable int monthName,Model model,Locale locale,HttpSession session) {
		OrgUserMst messages = (OrgUserMst) session.getAttribute("MY_SESSION_MESSAGES"); 
		List<Object[]> consolatedFilterList =  payBillViewApprDelBill.findPayBillByBillNumber(billNumber,monthName,yearName,messages.getMstRoleEntity().getRoleId());
		return consolatedFilterList;
    }
	
	//Added for search paybill for only  month and year//
	
	@GetMapping(value="/findPayBillByMonthYear/{yearName}/{monthName}")
	public @ResponseBody List<Object[]> findPayBillByMonthYear(@PathVariable int yearName,@PathVariable int monthName,Model model,Locale locale,HttpSession session) {
		OrgUserMst messages = (OrgUserMst) session.getAttribute("MY_SESSION_MESSAGES"); 
		List<Object[]> consolatedFilterList =  payBillViewApprDelBill.findPayBillByMonthYear(monthName,yearName,messages.getUserName(),messages.getMstRoleEntity().getRoleId());
		return consolatedFilterList;
    }
	
	@GetMapping("/downloadAuthPdf/{authno}") 
	public ResponseEntity<InputStreamResource> downloadAuthPdf(@PathVariable String  authno, HttpSession session){
		OrgUserMst messages = (OrgUserMst) session.getAttribute("MY_SESSION_MESSAGES"); 
		String ddoCode=messages.getUserName();
		
		String key = "";
		String rootPath = "";
		String strOSName = System.getProperty("os.name");
		boolean test = strOSName.contains("Windows");
		if (strOSName.contains("Windows")) {
			key = "serverempconfigimagepath";
		} else {
			key = "serverempconfigimagepathLinuxOS";
		}
		rootPath = environment.getRequiredProperty(key);
		rootPath += "MJP";

		String name = authno+".pdf";
		// Create the file on server
		File serverFile = new File(rootPath+"/"+name);
		System.out.println("------------serverFile");
		FileInputStream fileInputStream = null;
		try {
			fileInputStream = new FileInputStream(serverFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
		InputStreamResource inputStreamResource = new InputStreamResource(fileInputStream);
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "inline;filename=filename="+name)
				.contentType(MediaType.APPLICATION_PDF).contentLength(serverFile.length()).body(inputStreamResource);
	}
	@GetMapping("/updateVoucherDtls/{paybillGenerationTrnId}/{voucherNo}/{voucherDate}/{chqNo}/{chqDate}/{accNo}/{ifscCode}/{userId}")
	public String updateVoucherDtls(@ModelAttribute("payBillViewApprDelBillModel") PayBillViewApprDelBillModel payBillViewApprDelBillModel,@PathVariable int paybillGenerationTrnId,
			Model model,HttpServletRequest request,Locale locale,HttpSession session,@PathVariable String voucherNo,@PathVariable String voucherDate,@PathVariable String chqNo,@PathVariable String chqDate,@PathVariable String accNo,@PathVariable String ifscCode,@PathVariable Long userId) {
		Date vdate =null;
		Date chequeDate =null;
		OrgUserMst messages = (OrgUserMst) session.getAttribute("MY_SESSION_MESSAGES"); 
		SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd");
		try {
			vdate=sdf.parse(voucherDate);
			chequeDate=sdf.parse(chqDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace(); 
		}
		
		PaybillGenerationTrnEntity paybillGenerationTrnEntity = paybillGenerationTrnService.findPaybillById(paybillGenerationTrnId,voucherNo,vdate,chqNo,chequeDate,accNo,ifscCode,userId);
		
		PaybillStatusEntity paybillStatusEntity = new PaybillStatusEntity();
		
		 String clientIP = request.getRemoteAddr();
		
		 String clientHostname = request.getRemoteHost();
		   
		 
		 String namePIp=clientHostname+"/"+clientIP;
		 
		 System.out.println(namePIp);
		 
		
		//paybill status updation//
		InetAddress local=null;
		try {
			local = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		paybillStatusEntity.setBillNo(paybillGenerationTrnId);
		paybillStatusEntity.setCreatedDate(new Date());
		paybillStatusEntity.setIsActive(14);
		paybillStatusEntity.setUserId(userId);
		paybillStatusEntity.setMacId(namePIp);
		Serializable id3= paybillHeadMpgRepo.savePaybillStatus(paybillStatusEntity);
		
		/*List<PaybillGenerationTrnDetails> paybillGenerationTrnDetails = bdsintegrationservice.getPaybillSevaarthid(paybillGenerationTrnId);
		for (PaybillGenerationTrnDetails paybillGenerationTrnDetails2 : paybillGenerationTrnDetails) {
				if(paybillGenerationTrnDetails2.getSevaarthId()!=null && paybillGenerationTrnDetails2.getFestivalAdv()!=null) {
					int faid=bdsintegrationservice.getfaid(paybillGenerationTrnDetails2.getSevaarthId());
					if(faid>0) {
						
						LNAFAEmployeeRequestMstEntity lnafaEmployeeRequestMstEntity =bdsintegrationservice.getFADetails(faid);
						
						if(lnafaEmployeeRequestMstEntity.getNoOfInstallmentsPaid()==null) {
							lnafaEmployeeRequestMstEntity.setNoOfInstallmentsPaid(0);
						}
						
						if(lnafaEmployeeRequestMstEntity.getTotalAmtPaid()==null) {
							lnafaEmployeeRequestMstEntity.setTotalAmtPaid(0d);
						}
						
						
						int installment = lnafaEmployeeRequestMstEntity.getNoOfInstallmentsPaid()+1;
						lnafaEmployeeRequestMstEntity.setTotalAmtPaid(lnafaEmployeeRequestMstEntity.getTotalAmtPaid()+paybillGenerationTrnDetails2.getFestivalAdv());
						lnafaEmployeeRequestMstEntity.setNoOfInstallmentsPaid(installment);
						if(lnafaEmployeeRequestMstEntity.getSanctionedNoOfInstallment()==installment)
							lnafaEmployeeRequestMstEntity.setIsActive(0);
						bdsintegrationservice.updateFADetails(lnafaEmployeeRequestMstEntity);
						
						
	
						FaLoanDtlsTrnEntity faLoanDtlsTrnEntity=new FaLoanDtlsTrnEntity();
						faLoanDtlsTrnEntity.setLnaFaEmployeeRequestId(lnafaEmployeeRequestMstEntity.getLnaFAEmployeeRequestId());
						faLoanDtlsTrnEntity.setTotalAmtPaid(lnafaEmployeeRequestMstEntity.getTotalAmtPaid()+paybillGenerationTrnDetails2.getFestivalAdv());
						faLoanDtlsTrnEntity.setNoOfInstallmentsPaid(installment);
						faLoanDtlsTrnEntity.setEmiAmtPaid(paybillGenerationTrnDetails2.getFestivalAdv());
						faLoanDtlsTrnEntity.setCreateddate(new Date());
						faLoanDtlsTrnEntity.setCreatedUserId(messages.getUser_id());
						faLoanDtlsTrnEntity.setSanctionedAmount(lnafaEmployeeRequestMstEntity.getSanctionedAmount());
						faLoanDtlsTrnEntity.setEmiPaidDate(new Date());
						faLoanDtlsTrnEntity.setEmployeeId(lnafaEmployeeRequestMstEntity.getEmployeeId());
						faLoanDtlsTrnEntity.setSevaarthId(lnafaEmployeeRequestMstEntity.getSevaarthId());
						faLoanDtlsTrnEntity.setLoanSubTypeId(lnafaEmployeeRequestMstEntity.getLoanSubTypeId());
						faLoanDtlsTrnEntity.setRemark("Emi Amount paid="+paybillGenerationTrnDetails2.getFestivalAdv()+" of installment no"+installment);
						
						bdsintegrationservice.saveFaDtlsTrn(faLoanDtlsTrnEntity);
					}
				}
				if(paybillGenerationTrnDetails2.getSevaarthId()!=null && paybillGenerationTrnDetails2.getComputerAdv()!=null) {
					int caid=bdsintegrationservice.getcaid(paybillGenerationTrnDetails2.getSevaarthId());
					if(caid>0) {
						LNACAEmployeeRequestMstEntity lnacaEmployeeRequestMstEntity =bdsintegrationservice.getCADetails(caid);
						lnacaEmployeeRequestMstEntity.setTotalAmtPaid(lnacaEmployeeRequestMstEntity.getTotalAmtPaid()+paybillGenerationTrnDetails2.getComputerAdv());
						lnacaEmployeeRequestMstEntity.setNoOfInstallmentsPaid(lnacaEmployeeRequestMstEntity.getNoOfInstallmentsPaid()+1);
						bdsintegrationservice.updateCADetails(lnacaEmployeeRequestMstEntity);
						
						CaLoanDtlsTrnEntity caLoanDtlsTrnEntity=new CaLoanDtlsTrnEntity();
						caLoanDtlsTrnEntity.setLnaCaEmployeeRequestId(lnacaEmployeeRequestMstEntity.getLnaCaEmployeeRequestId());
						caLoanDtlsTrnEntity.setTotalAmtPaid(lnacaEmployeeRequestMstEntity.getTotalAmtPaid()+paybillGenerationTrnDetails2.getComputerAdv());
						caLoanDtlsTrnEntity.setNoOfInstallmentsPaid(lnacaEmployeeRequestMstEntity.getNoOfInstallmentsPaid()+1);
						caLoanDtlsTrnEntity.setEmiAmtPaid(paybillGenerationTrnDetails2.getComputerAdv());
						caLoanDtlsTrnEntity.setCreateddate(new Date());
						caLoanDtlsTrnEntity.setCreatedUserId(messages.getUser_id());
						caLoanDtlsTrnEntity.setSanctionedAmount(lnacaEmployeeRequestMstEntity.getSanctionedAmount());
						caLoanDtlsTrnEntity.setEmiPaidDate(new Date());
						caLoanDtlsTrnEntity.setEmployeeId(lnacaEmployeeRequestMstEntity.getEmployeeId());
						caLoanDtlsTrnEntity.setSevaarthId(lnacaEmployeeRequestMstEntity.getSevaarthId());
						caLoanDtlsTrnEntity.setLoanSubTypeId(lnacaEmployeeRequestMstEntity.getLoanSubTypeId());
						caLoanDtlsTrnEntity.setRemark("Emi Amount paid="+paybillGenerationTrnDetails2.getComputerAdv()+" of installment no"+lnacaEmployeeRequestMstEntity.getNoOfInstallmentsPaid()+1);
						
					    bdsintegrationservice.saveCaDtlsTrn(caLoanDtlsTrnEntity);
					}
				}
				if(paybillGenerationTrnDetails2.getSevaarthId()!=null && paybillGenerationTrnDetails2.getOtherVehAdv()!=null) {
					int vaid=bdsintegrationservice.getvaid(paybillGenerationTrnDetails2.getSevaarthId());
					if(vaid>0) {
						LNAVAEmployeeRequestMstEntity lnavaEmployeeRequestMstEntity =bdsintegrationservice.getVADetails(vaid);
						if(lnavaEmployeeRequestMstEntity.getTotalAmtPaid()!=null)
						lnavaEmployeeRequestMstEntity.setTotalAmtPaid(lnavaEmployeeRequestMstEntity.getTotalAmtPaid()+paybillGenerationTrnDetails2.getOtherVehAdv());
						lnavaEmployeeRequestMstEntity.setNoOfInstallmentsPaid(lnavaEmployeeRequestMstEntity.getNoOfInstallmentsPaid()+1);
						bdsintegrationservice.updateVADetails(lnavaEmployeeRequestMstEntity);
						
						
						VaLoanDtlsTrnEntity vaLoanDtlsTrnEntity=new VaLoanDtlsTrnEntity();
						vaLoanDtlsTrnEntity.setLnaVaEmployeeRequestId(lnavaEmployeeRequestMstEntity.getLnaVAEmployeeRequestId());
						if(lnavaEmployeeRequestMstEntity.getTotalAmtPaid()!=null && paybillGenerationTrnDetails2.getOtherVehAdv() !=null)
						vaLoanDtlsTrnEntity.setTotalAmtPaid(lnavaEmployeeRequestMstEntity.getTotalAmtPaid()+paybillGenerationTrnDetails2.getOtherVehAdv());
						vaLoanDtlsTrnEntity.setNoOfInstallmentsPaid(lnavaEmployeeRequestMstEntity.getNoOfInstallmentsPaid()+1);
						vaLoanDtlsTrnEntity.setEmiAmtPaid(paybillGenerationTrnDetails2.getOtherVehAdv());
						vaLoanDtlsTrnEntity.setCreateddate(new Date());
						vaLoanDtlsTrnEntity.setCreatedUserId(messages.getUser_id());
						vaLoanDtlsTrnEntity.setSanctionedAmount(lnavaEmployeeRequestMstEntity.getSanctionedAmount());
						vaLoanDtlsTrnEntity.setEmiPaidDate(new Date());
						vaLoanDtlsTrnEntity.setEmployeeId(lnavaEmployeeRequestMstEntity.getEmployeeId());
						vaLoanDtlsTrnEntity.setSevaarthId(lnavaEmployeeRequestMstEntity.getSevaarthId());
						vaLoanDtlsTrnEntity.setLoanSubTypeId(lnavaEmployeeRequestMstEntity.getCmbVehicleSubType());
						vaLoanDtlsTrnEntity.setRemark("Emi Amount paid="+paybillGenerationTrnDetails2.getOtherVehAdv()+" of installment no"+lnavaEmployeeRequestMstEntity.getNoOfInstallmentsPaid()+1);
						bdsintegrationservice.saveVaDtlsTrn(vaLoanDtlsTrnEntity);
						
						
					}
				}
				if(paybillGenerationTrnDetails2.getSevaarthId()!=null && paybillGenerationTrnDetails2.getHbaHouse()!=null) {
					int hbaid=bdsintegrationservice.gethbaid(paybillGenerationTrnDetails2.getSevaarthId());
					if(hbaid>0) {
						LNAHBAEmployeeRequestMstEntity lnahbaEmployeeRequestMstEntity =bdsintegrationservice.getHBADetails(hbaid);
						
						
						if(lnahbaEmployeeRequestMstEntity.getTotalAmtPaid()==null) {
							lnahbaEmployeeRequestMstEntity.setTotalAmtPaid(0d);
						}
						
						
						lnahbaEmployeeRequestMstEntity.setTotalAmtPaid(lnahbaEmployeeRequestMstEntity.getTotalAmtPaid()+paybillGenerationTrnDetails2.getHbaHouse());
						lnahbaEmployeeRequestMstEntity.setNoOfInstallmentsPaid(lnahbaEmployeeRequestMstEntity.getNoOfInstallmentsPaid()+1);
						bdsintegrationservice.updateVADetails(lnahbaEmployeeRequestMstEntity);
						
						HbaLoanDtlsTrnEntity hbaLoanDtlsTrnEntity=new HbaLoanDtlsTrnEntity();
						hbaLoanDtlsTrnEntity.setLnaHbaEmployeeRequestId(lnahbaEmployeeRequestMstEntity.getLnaHbaEmployeeRequestId());
						hbaLoanDtlsTrnEntity.setTotalAmtPaid(lnahbaEmployeeRequestMstEntity.getTotalAmtPaid()+paybillGenerationTrnDetails2.getHbaHouse());
						hbaLoanDtlsTrnEntity.setNoOfInstallmentsPaid(lnahbaEmployeeRequestMstEntity.getNoOfInstallmentsPaid()+1);
						hbaLoanDtlsTrnEntity.setEmiAmtPaid(paybillGenerationTrnDetails2.getHbaHouse());
						hbaLoanDtlsTrnEntity.setCreateddate(new Date());
						hbaLoanDtlsTrnEntity.setCreatedUserId(messages.getUser_id());
						hbaLoanDtlsTrnEntity.setSanctionedAmount(lnahbaEmployeeRequestMstEntity.getSanctionedAmount());
						hbaLoanDtlsTrnEntity.setEmiPaidDate(new Date());
						hbaLoanDtlsTrnEntity.setEmployeeId(lnahbaEmployeeRequestMstEntity.getEmployeeId());
						hbaLoanDtlsTrnEntity.setSevaarthId(lnahbaEmployeeRequestMstEntity.getSevaarthId());
						hbaLoanDtlsTrnEntity.setLoanSubTypeId(lnahbaEmployeeRequestMstEntity.getLoanSubTypeId());
						hbaLoanDtlsTrnEntity.setRemark("Emi Amount paid="+paybillGenerationTrnDetails2.getHbaHouse()+" of installment no"+lnahbaEmployeeRequestMstEntity.getNoOfInstallmentsPaid()+1);
						bdsintegrationservice.saveHbaDtlsTrn(hbaLoanDtlsTrnEntity);
						
					}
				}
				
				if(paybillGenerationTrnDetails2.getSevaarthId()!=null) {
					if(paybillGenerationTrnDetails2.getGpfAdvGrpAbc()!=null && !paybillGenerationTrnDetails2.getGpfAdvGrpAbc().equals(0d)
							|| paybillGenerationTrnDetails2.getGpfAdvGrpD()!=null && !paybillGenerationTrnDetails2.getGpfAdvGrpD().equals(0d)
							|| paybillGenerationTrnDetails2.getGpfGrpABC()!=null && !paybillGenerationTrnDetails2.getGpfGrpABC().equals(0d)
							|| paybillGenerationTrnDetails2.getGpfGrpD()!=null && !paybillGenerationTrnDetails2.getGpfGrpD().equals(0d)
							|| paybillGenerationTrnDetails2.getGpfSubscription()!=null && !paybillGenerationTrnDetails2.getGpfSubscription().equals(0d)) {
						TranGpfAmt tranGpfAmt=new TranGpfAmt();
						Double bal=advanceWithdrawalRepo.getCurrentOpeningBalanceWithdraw(paybillGenerationTrnDetails2.getSevaarthId());
						
						if(bal==null) {
							bal=0d;
						}
						
		                tranGpfAmt.setRemark("Regular Deductions");
		                if(paybillGenerationTrnDetails2.getGpfAdvGrpAbc()!=null && !paybillGenerationTrnDetails2.getGpfAdvGrpAbc().equals(0d)) {
		                	tranGpfAmt.setCrAmount(paybillGenerationTrnDetails2.getGpfAdvGrpAbc());
		                	if(bal!=null) {
								tranGpfAmt.setBalance(bal+paybillGenerationTrnDetails2.getGpfAdvGrpAbc());
							}
		                	
		                }else if(paybillGenerationTrnDetails2.getGpfAdvGrpD()!=null && !paybillGenerationTrnDetails2.getGpfAdvGrpD().equals(0d)) {
		                	tranGpfAmt.setCrAmount(paybillGenerationTrnDetails2.getGpfAdvGrpD());
		                	if(bal!=null) {
		                		tranGpfAmt.setBalance(bal+paybillGenerationTrnDetails2.getGpfAdvGrpD());
							}
		                }else if(paybillGenerationTrnDetails2.getGpfGrpABC()!=null && !paybillGenerationTrnDetails2.getGpfGrpABC().equals(0d)) {
		                	tranGpfAmt.setCrAmount(paybillGenerationTrnDetails2.getGpfGrpABC());
		                	if(bal!=null) {
		                		tranGpfAmt.setBalance(bal+paybillGenerationTrnDetails2.getGpfGrpABC());
							}
		                }else if(paybillGenerationTrnDetails2.getGpfSubscription()!=null && !paybillGenerationTrnDetails2.getGpfSubscription().equals(0d)) {
		                	tranGpfAmt.setCrAmount(paybillGenerationTrnDetails2.getGpfSubscription());
		                	if(bal!=null) {
		                		tranGpfAmt.setBalance(bal+paybillGenerationTrnDetails2.getGpfSubscription());
							}
		                }else {
		                	tranGpfAmt.setCrAmount(paybillGenerationTrnDetails2.getGpfGrpD());
		                	if(bal!=null) {
		                		tranGpfAmt.setBalance(bal+paybillGenerationTrnDetails2.getGpfGrpD());
							}
		                }
		                tranGpfAmt.setSevaarthId(paybillGenerationTrnDetails2.getSevaarthId());
		                tranGpfAmt.setCreated_by(BigInteger.valueOf(messages.getUser_id()));
		                tranGpfAmt.setStatus("1");
		                tranGpfAmt.setBillType("5");
		                tranGpfAmt.setCreated_date(new Date());
		                tranGpfAmt.setMonth(paybillGenerationTrnDetails2.getPaybillMonth());
		                tranGpfAmt.setPaybillMonth(paybillGenerationTrnDetails2.getPaybillMonth());
		                tranGpfAmt.setPaybillYear(paybillGenerationTrnDetails2.getPaybillYear());
		                tranGpfAmt.setYear(paybillGenerationTrnDetails2.getPaybillYear());
		                Integer saveId=gpfOpeningBalEntryRepo.saveGpfTranAmount(tranGpfAmt);
					}
				}
				/// NPS_EMPR_DEDUCT, DCPS_ARR,NPS_EMP_CONTRI deduction
				if(paybillGenerationTrnDetails2.getSevaarthId()!=null) {
					if(paybillGenerationTrnDetails2.getNpsEmployerDeduct()!=null && !paybillGenerationTrnDetails2.getNpsEmployerDeduct().equals(0d)
							|| paybillGenerationTrnDetails2.getDcpsArr()!=null && !paybillGenerationTrnDetails2.getDcpsArr().equals(0d)
							|| paybillGenerationTrnDetails2.getNpsEmployeeContri()!=null && !paybillGenerationTrnDetails2.getNpsEmployeeContri().equals(0d)) {
						DcpsCalculationEntity dcpsCalc=new DcpsCalculationEntity();
						
						dcpsCalc.setSevaarthId(paybillGenerationTrnDetails2.getSevaarthId());
						dcpsCalc.setMonthId(paybillGenerationTrnDetails2.getPaybillMonth());
						dcpsCalc.setYearId(paybillGenerationTrnDetails2.getPaybillYear());
						dcpsCalc.setDcpsArrear(paybillGenerationTrnDetails2.getDcpsArr());
						dcpsCalc.setEmpContri(paybillGenerationTrnDetails2.getNpsEmployeeContri());
						dcpsCalc.setEmprContri(paybillGenerationTrnDetails2.getNpsEmployerDeduct());
					
						Integer saveId=payBillViewApprDelBillRepo.saveDCPSNpsDeductions(dcpsCalc);
					}
				}
				
				
				
				
				
				// cut advance amount from paybill 
				
				List<LoanEmployeeDtlsEntity> lstLoanEmployeeDtlsEntity=gpfOpeningBalEntryRepo.findLoanInfo(paybillGenerationTrnDetails2.getSevaarthId());
				if(lstLoanEmployeeDtlsEntity!=null && lstLoanEmployeeDtlsEntity.size()>0) {
					
					LoanEmployeeDtlsEntity loanEmployeeDtlsEntity=lstLoanEmployeeDtlsEntity.get(0);
					loanEmployeeDtlsEntity.setTotalRecoveredInst(loanEmployeeDtlsEntity.getTotalRecoveredInst()+1);
					
					
					if(loanEmployeeDtlsEntity.getTotalRecoveredAmt()==null) {
						loanEmployeeDtlsEntity.setTotalRecoveredAmt(BigInteger.valueOf(0));
					}
					
					
					loanEmployeeDtlsEntity.setTotalRecoveredAmt((loanEmployeeDtlsEntity.getTotalRecoveredAmt().add(loanEmployeeDtlsEntity.getLoanprinemiamt())));
					loanEmployeeDtlsEntity.setTrncounter(loanEmployeeDtlsEntity.getTotalRecoveredInst()+1);
					loanEmployeeDtlsEntity.setUpdateddate(new Date());
					
					
					if(loanEmployeeDtlsEntity.getLoanprinamt()==loanEmployeeDtlsEntity.getTotalRecoveredAmt()) {
						loanEmployeeDtlsEntity.setLoanactivateflag(0);
					}
					
					advanceWithdrawalRepo.updateLoanDtls(loanEmployeeDtlsEntity);
					TranGpfAmt tranGpfAmt=new TranGpfAmt();
					Double bal=advanceWithdrawalRepo.getCurrentOpeningBalanceWithdraw(paybillGenerationTrnDetails2.getSevaarthId());
					if(bal==null) {
						bal=0d;
					}
					
					int instaNo=0;
	                tranGpfAmt.setRemark("Gpf Advance refund Amount  "+loanEmployeeDtlsEntity.getLoanprinemiamt()+" with installment No is"+loanEmployeeDtlsEntity.getTotalRecoveredInst()+1);
					tranGpfAmt.setBalance(bal+loanEmployeeDtlsEntity.getLoanprinemiamt().doubleValue());
	                tranGpfAmt.setSevaarthId(paybillGenerationTrnDetails2.getSevaarthId());
	                tranGpfAmt.setCreated_by(BigInteger.valueOf(messages.getUser_id()));
	                tranGpfAmt.setStatus("1");
	                tranGpfAmt.setCreated_date(new Date());
	                tranGpfAmt.setMonth(paybillGenerationTrnDetails2.getPaybillMonth());
	                tranGpfAmt.setPaybillMonth(paybillGenerationTrnDetails2.getPaybillMonth());
	                tranGpfAmt.setPaybillYear(paybillGenerationTrnDetails2.getPaybillYear());
	                tranGpfAmt.setYear(paybillGenerationTrnDetails2.getPaybillYear());
	                tranGpfAmt.setCrAmount(loanEmployeeDtlsEntity.getLoanprinemiamt().doubleValue());
	                tranGpfAmt.setDrAmount(0d);
	                tranGpfAmt.setBillType("1");
	                Integer saveId=gpfOpeningBalEntryRepo.saveGpfTranAmount(tranGpfAmt);
				}
				// Excess Pay Recovery
				
				if(paybillGenerationTrnDetails2.getSevaarthId()!=null && paybillGenerationTrnDetails2.getExcessPayrec()!=null) {
					int excessPayId=bdsintegrationservice.getExcessPayId(paybillGenerationTrnDetails2.getSevaarthId());
					if(excessPayId>0) {
						ExcessPayRecoveryEntity excessPayRecoveryEntity =bdsintegrationservice.getExcessPayDtls(excessPayId);
						
						excessPayRecoveryEntity.setLoanPrinInstNo(excessPayRecoveryEntity.getLoanPrinInstNo()+1);
						if(paybillGenerationTrnDetails2.getExcessPayrec()!=null && excessPayRecoveryEntity.getTotalPaidAmt() !=null)
						excessPayRecoveryEntity.setTotalPaidAmt(excessPayRecoveryEntity.getTotalPaidAmt()+paybillGenerationTrnDetails2.getExcessPayrec());
						
						bdsintegrationservice.updateExcessPayRec(excessPayRecoveryEntity);
					}
				}
				//end 
		}*/
		
		if(paybillGenerationTrnEntity!=null)
		{
			model.addAttribute("message","SUCCESS");
		}
		return "/views/paybill/paybill-view-approve-delete-bill";
	}
	
	
}
