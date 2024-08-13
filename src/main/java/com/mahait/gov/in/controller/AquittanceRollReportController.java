package com.mahait.gov.in.controller;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mahait.gov.in.CashWordConverter;
import com.mahait.gov.in.entity.OrgUserMst;
import com.mahait.gov.in.model.AquittanceRollReportModel;
import com.mahait.gov.in.service.AquittanceRollReportService;
import com.mahait.gov.in.service.PaybillGenerationTrnService;

@RequestMapping("/ddoast")
@Controller
public class AquittanceRollReportController  extends BaseController {
	
	@Autowired
	
	AquittanceRollReportService aquittanceRollReportService;
	@Autowired
	
	PaybillGenerationTrnService paybillGenerationTrnService;
	
	
	@GetMapping("/aquittancereport/{yearName}/{monthName}/{billNumber}/{ddoCode}")
	public String getAquittancereport(@ModelAttribute("aquittanceRollReportModel") AquittanceRollReportModel aquittanceRollReportModel,@PathVariable int monthName,@PathVariable int yearName, 
			@PathVariable Long billNumber,Model model, Locale locale, HttpSession session,@PathVariable String ddoCode) {
		String message = (String) model.asMap().get("message");
		OrgUserMst messages = (OrgUserMst) session.getAttribute("MY_SESSION_MESSAGES");

		if(ddoCode.equals("1")) {
			ddoCode=messages.getUserName();
		}else {
			ddoCode=messages.getDdoCode();
		}
		String monname = null;
		Double totalnetamt = 0d;
		 Double word1 = 0d;
		List<AquittanceRollReportModel>  aquittanceinfo = aquittanceRollReportService.findAquittanceReportDtls(monthName,yearName,ddoCode,billNumber);
		
		totalnetamt = aquittanceinfo.stream().mapToDouble(AquittanceRollReportModel ::getNetamt).sum();

		
		String curryear=null;
		BigInteger currMonth = BigInteger.valueOf(monthName);
		BigInteger yearcurr = BigInteger.valueOf(yearName);
		
		List<Object[]>  monthinfo = paybillGenerationTrnService.findmonthinfo(currMonth);
		for (Object[] monthLst : monthinfo) {
			monname = monthLst[4].toString();
			
		}
		
		List<Object[]>  yearinfo = paybillGenerationTrnService.findyearinfo(yearcurr);
		for (Object[] yearLst : yearinfo) {
			// String empName;
			curryear = yearLst[9].toString();
			
		}
		String orgname=null;		
		String ddoname=null;
		Long ofcid=null;
		List<Object[]>  ddoinfo = paybillGenerationTrnService.findDDOinfo(ddoCode);

		for (Object[] objddoLst : ddoinfo) {
			// String empName;
			 ddoname = objddoLst[0].toString();
			 if(objddoLst[1]!=null)
			 ofcid = Long.parseLong(objddoLst[1].toString());
			
		}
		String billGrpname = commonHomeMethodsService.findbillGrpname(billNumber);
		Date createdate = commonHomeMethodsService.findbillCreateDate(billNumber);
		String officename =commonHomeMethodsService.getOffice(ddoCode);
		/*String treasury =commonHomeMethodsService.getTreasuryCode(ddoCode);
		model.addAttribute("treasury",treasury);*/
		model.addAttribute("aquittanceinfo", aquittanceinfo);
		model.addAttribute("officename",officename);
		model.addAttribute("totalnetamt",totalnetamt);
		model.addAttribute("billGrpname",billGrpname);
		
		SimpleDateFormat sdf =new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		SimpleDateFormat sdf1 =new SimpleDateFormat("dd/MM/yyyy");
        model.addAttribute("createddate", sdf1.format(createdate));
        model.addAttribute("systemdate", sdf.format(new Date()));
		
		String word=CashWordConverter.doubleConvert(totalnetamt);
		model.addAttribute("currmonyer", monname+" "+curryear);
		model.addAttribute("orgname", orgname);
		model.addAttribute("ddoname", messages.getUserName());
		model.addAttribute("word", word);
		model.addAttribute("aquittanceRollReportModel", aquittanceRollReportModel);
		
		model.addAttribute("language", locale.getLanguage());
		return "/views/reports/aquittance-roll-report";
		
	}
	
	
	
	
	
	
	
/*	
	@GetMapping("/aquittancereportsearch")
	public String aquittancereportsearch(Model model, Locale locale, HttpSession session) {
		OrgUserMst messages = (OrgUserMst) session.getAttribute("MY_SESSION_MESSAGES");
		addMenuAndSubMenu(model,messages);	
		return "/views/reports/aquittance-roll-report";
	}

	@GetMapping("/aquittanceRoll")
	public String aquittanceRoll(Model model, Locale locale, HttpSession session) {
		OrgUserMst messages = (OrgUserMst) session.getAttribute("MY_SESSION_MESSAGES");
		addMenuAndSubMenu(model,messages);	
		return "/views/reports/aquittance-roll";
	}*/
}
