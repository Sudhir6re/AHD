package com.mahait.gov.in.controller;
import java.math.BigInteger;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.mahait.gov.in.CashWordConverter;
import com.mahait.gov.in.entity.OrgDdoMst;
import com.mahait.gov.in.entity.OrgUserMst;
import com.mahait.gov.in.model.RegularReportModel;
import com.mahait.gov.in.model.TopicModel;
import com.mahait.gov.in.service.CommonHomeMethodsService;
import com.mahait.gov.in.service.PaybillGenerationTrnService;
import com.mahait.gov.in.service.RegularReportService;
@RequestMapping("/ddoast")
@Controller
public class Form2RegularController  extends BaseController{
	
	
	@Autowired
	CommonHomeMethodsService commonHomeMethodsService;
	
	@Autowired
	RegularReportService regularReportService;
	
	@Autowired
	PaybillGenerationTrnService paybillGenerationTrnService;
	
	@RequestMapping(value = "/form2Regular", method = { RequestMethod.GET , RequestMethod.POST})
	public String form2Regular(@ModelAttribute("regularReportModel") RegularReportModel regularReportModel,
			Model model, Locale locale, HttpSession session) {
		
			String message = (String) model.asMap().get("message");
		
		OrgUserMst messages = (OrgUserMst) session.getAttribute("MY_SESSION_MESSAGES"); 
		model.addAttribute("lstMonths", commonHomeMethodsService.lstGetAllMonths());
		model.addAttribute("lstYears", commonHomeMethodsService.lstGetAllYears());
		model.addAttribute("lstBillDesc", regularReportService.lstBillDesc(messages.getDdoCode()));
		addMenuAndSubMenu(model,messages);
		return "/views/form2-regular";
	}
	
	@RequestMapping("/form2RegularReport")
	public String form2RegularReport(@ModelAttribute("regularReportModel") RegularReportModel regularReportModel, Model model, Locale locale,
			HttpSession session) {
		SimpleDateFormat sdf =new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		SimpleDateFormat sdf1 =new SimpleDateFormat("dd/MM/yyyy");
		OrgUserMst messages = (OrgUserMst) session.getAttribute("MY_SESSION_MESSAGES"); 
		String curryear=null;
		
		List<OrgDdoMst> ddoDtls=regularReportService.getDDOName(messages.getDdoCode());
		String ddoName=null;
		String ddoCode=null;
		String offcName=null;
		for (OrgDdoMst ddoScreenEntity : ddoDtls) {
			ddoName= ddoScreenEntity.getDdoName();
			ddoCode= ddoScreenEntity.getDdoCode();
			offcName=  ddoScreenEntity.getDdoOffice();
		}
		
		Integer month=regularReportModel.getMonthId();
		Integer year=regularReportModel.getYearId();
		
		List<Object[]>  yearinfo = commonHomeMethodsService.findyearinfo(BigInteger.valueOf(year));
		for (Object[] yearLst : yearinfo) {
			curryear = yearLst[9].toString();
		}
		
		
		String startDate=null;
		Date fromDate=null;
		Date toDate=null;
		try {
			if (month < 10) {
				startDate = String.valueOf(curryear) + '-' + String.valueOf("0" + month) + "-01";
			} else {
				startDate = String.valueOf(curryear) + '-' + String.valueOf(month) + "-01";
			}
			 fromDate=new SimpleDateFormat("yyyy-mm-dd").parse(startDate);  
			Calendar c = Calendar.getInstance();
			c.setTime(fromDate);
			c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
			 toDate= c.getTime();
		}catch(Exception e) {
			
		}
		

		
		String Name = null;
		Double basicpay = null;
		Double svnpcda = null;
		String pfacc = null;
		Integer billnumber = 0;
		Double totalNpsEmpContri=0d;
		Double totalNpsEmprContri=0d;
		Double npsEmpContri=0d;
		Double npsEmployerDedu=0d;
		Double totalContri=0d;
		Double grandtotalContri=0d;
		String dcpsNo=null;
		
		List<RegularReportModel>  entryinfo = regularReportService.findentry(regularReportModel.getYearId(),regularReportModel.getMonthId(),regularReportModel.getBillGroup(),messages.getUserName());
	/*	for (Object[] entryLst : entryinfo) {
			 
			
			
			if(entryLst[0]!=null)
			Name = entryLst[0].toString();
			if(entryLst[1]!=null)
			basicpay = Double.parseDouble(entryLst[1].toString());
			if(entryLst[2]!=null)
			svnpcda = Double.parseDouble(entryLst[2].toString());
			if(entryLst[3]!=null)
			pfacc = entryLst[3].toString();
			billnumber=(int) entryLst[4];
			if(entryLst[5]!=null)
			npsEmpContri= Double.parseDouble(entryLst[5].toString());
			if(entryLst[6]!=null)
			npsEmployerDedu=Double.parseDouble(entryLst[6].toString());
			if(entryLst[8]!=null) {
				dcpsNo=entryLst[8].toString();
			}else {
				dcpsNo="";
			}
			
			
			totalContri= npsEmpContri + npsEmployerDedu;
			grandtotalContri = grandtotalContri+totalContri;
			
			
			
			totalNpsEmpContri = totalNpsEmpContri + npsEmpContri ;
			totalNpsEmprContri=totalNpsEmprContri+npsEmployerDedu;
			
			RegularReportModel(Name,basicpay,svnpcda,pfacc,billnumber,fromDate,toDate,npsEmpContri,npsEmployerDedu,totalContri,dcpsNo);
		}*/
		
		
	/*	String empContrinword=CashWordConverter.doubleConvert(totalNpsEmpContri);
		String emprContrinword=CashWordConverter.doubleConvert(totalNpsEmprContri);
		String totalContrinword=CashWordConverter.doubleConvert(grandtotalContri);*/
        BigInteger bigInteger = BigInteger.valueOf(regularReportModel.getMonthId());
		
		String monname="";
		List<Object[]>  monthinfo = paybillGenerationTrnService.findmonthinfo(bigInteger);
		for (Object[] monthLst : monthinfo) {
			// String empName;
			monname = monthLst[1].toString();
			
		}
		BigInteger yearcurr = BigInteger.valueOf(regularReportModel.getYearId());
		BigInteger nextMonth = BigInteger.valueOf(regularReportModel.getMonthId()+1);
		String NextMon="";
		String NextYear="";
		BigInteger neyer = null;
		BigInteger nemon;
		
		if(bigInteger.equals(new BigInteger("12"))) {
			nextMonth=new BigInteger("1");
			BigInteger nextyer =new BigInteger("1");
			neyer=yearcurr.add(nextyer);
			NextYear=neyer.toString();
		}else {
			nextMonth=nextMonth;
			neyer=yearcurr;
		}
		
		List<Object[]>  nextmonthinfo = paybillGenerationTrnService.findmonthinfo(nextMonth);
		for (Object[] monthLst : nextmonthinfo) {
			// String empName;
			NextMon = monthLst[1].toString();
			
		}
		
		List<Object[]>  nextyearinfo = paybillGenerationTrnService.findyearinfo(neyer);
		for (Object[] yearLst : nextyearinfo) {
			// String empName;
			NextYear = yearLst[1].toString();
			
		}

		String billno = "";
		///billno = regularReportModel.getBillGroup();
		String billgrpname="";
		List<Object[]>  billinfo = regularReportService.findbillgrp(billno);
		
		for (Object[] billst :  billinfo) {
			// String empName;
			billgrpname = billst[10].toString();
			
		}
		
		String monthString = new DateFormatSymbols().getMonths()[month-1];
    	model.addAttribute("systemdate", sdf.format(new Date()));
    	model.addAttribute("date", sdf1.format(new Date()));
    	String billGroup=regularReportService.getbillGroup(billnumber);
		String officename =commonHomeMethodsService.getOffice(messages.getUserName());
		/*String treasury =commonHomeMethodsService.getTreasury(messages.getUserName());
		String treasuryCode =commonHomeMethodsService.getTreasuryCode(messages.getUserName());*/
		
    	model.addAttribute("systemdate", sdf.format(new Date()));
    	model.addAttribute("date", sdf1.format(new Date()));
		model.addAttribute("officename",officename);
        model.addAttribute("createddate", sdf.format(new Date()));
        model.addAttribute("systemdate", sdf.format(new Date()));
		//String word=CashWordConverter.doubleConvert(Totalamt);
	/*model.addAttribute("empContrinword", empContrinword);
		model.addAttribute("emprContrinword", emprContrinword);
		model.addAttribute("totalContrinword", totalContrinword);*/
		
		model.addAttribute("totalNpsEmpContri", totalNpsEmpContri);
		model.addAttribute("totalNpsEmprContri", totalNpsEmprContri);
		model.addAttribute("grandtotalContri", grandtotalContri);
		
		model.addAttribute("currmonyer", monname+" "+curryear);
	///	String officeName = commonHomeMethodsService.getOfficeName(messages.getUserName());
		model.addAttribute("offcName", offcName);
		model.addAttribute("billGroup", billGroup);
		model.addAttribute("ddoname", messages.getUserName());
		//model.addAttribute("word", word);
		model.addAttribute("yearName",curryear);
		model.addAttribute("monthName",monname);
		model.addAttribute("ddoCode",ddoCode);
		model.addAttribute("regularReportModel", regularReportModel);
	///	model.addAttribute("modellstObj",modellstObj);
		model.addAttribute("language", locale.getLanguage());
		model.addAttribute("systemdate", sdf.format(new Date()));
    	model.addAttribute("date", sdf1.format(new Date()));
		model.addAttribute("ddoName", ddoName);
		model.addAttribute("monthString", monthString);
		/*model.addAttribute("treasury", treasury);
		model.addAttribute("treasuryCode",treasuryCode);*/
		model.addAttribute("nextMonth",nextMonth);
		NextYear="Paybill of Month "+monname+" - " +curryear+" Paid In Month "+NextMon+"-"+NextYear;
		model.addAttribute("NextYear",NextYear);
		addMenuAndSubMenu(model,messages);
	
		return "/views/reports/form2-regular-report";
	}
	
	/*@GetMapping("/form2RegularReport")
	public String form2RegularReport( Model model, Locale locale,
			HttpSession session) {
		return "/views/reports/form2-regular-report";
	}*/

}
