package com.mahait.gov.in.nps.controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.common.net.HttpHeaders;
import com.mahait.gov.in.controller.BaseController;
import com.mahait.gov.in.entity.OrgUserMst;
import com.mahait.gov.in.nps.model.DcpsLegacyModel;
import com.mahait.gov.in.nps.model.NSDLDetailsModel;
import com.mahait.gov.in.nps.service.LegacyValidationService;
import com.mahait.gov.in.nps.service.NSDLDetailsService;
import com.mahait.gov.in.service.CommonHomeMethodsService;

@RequestMapping("/ddo")
@Controller
public class LegacyValidationController extends BaseController {

	@Autowired
	LegacyValidationService legacyValidationService;

	@Autowired
	CommonHomeMethodsService commonHomeMethodsService;

	@Autowired
	NSDLDetailsService nsdlDetailsService;

	@GetMapping("/legacyValidation")
	public String legacyValidation(Model model, Locale locale, HttpSession session,
			@ModelAttribute("dcpsLegacyModel") DcpsLegacyModel dcpsLegacyModel) {
		OrgUserMst messages = (OrgUserMst) session.getAttribute("MY_SESSION_MESSAGES");
		addMenuAndSubMenu(model, messages);

		Calendar cal = Calendar.getInstance();
		Integer currmonth = new Integer(cal.get(2) + 1);
		Integer curryear = new Integer(cal.get(1));

		dcpsLegacyModel.setMonth(currmonth);
		dcpsLegacyModel.setYear(curryear);

		List<DcpsLegacyModel> lstDcpsLegacyModel = legacyValidationService.findNsdlLegacyList(dcpsLegacyModel,
				messages);
		dcpsLegacyModel.setLstDcpsLegacyModel(lstDcpsLegacyModel);


		model.addAttribute("lstGetAllMonths", commonHomeMethodsService.lstGetAllMonths());
		model.addAttribute("lstGetAllYear", commonHomeMethodsService.lstGetAllYears());
		return "/views/nps/legacy-validation";
	}

	@PostMapping("/searchLegacyDataByYearAndMonth")
	public ResponseEntity<List<DcpsLegacyModel>> searchLegacyDataByYearAndMonth(Model model, Locale locale,
			HttpSession session, @RequestBody DcpsLegacyModel dcpsLegacyModel) {
		OrgUserMst messages = (OrgUserMst) session.getAttribute("MY_SESSION_MESSAGES");
		List<DcpsLegacyModel> lstDcpsLegacyModel = legacyValidationService.findNsdlLegacyList(dcpsLegacyModel,
				messages);
		return ResponseEntity.ok(lstDcpsLegacyModel);
	}
	
	@GetMapping("/viewAndSaveLegacyFile/{month}/{year}/{fileId}")
	public ResponseEntity<InputStreamResource> viewAndSaveLegacyFile(@PathVariable Integer month,@PathVariable Integer year,@PathVariable String fileId,
			Locale locale, HttpSession session, HttpServletRequest request)  {
		NSDLDetailsModel nsdlDetailsModel=new NSDLDetailsModel();
		
		nsdlDetailsModel.setYear(year);
		nsdlDetailsModel.setMonth(month);
		nsdlDetailsModel.setFileId(fileId);
		nsdlDetailsModel.setTreasuryId(treasuryId);
		
		String lStrTempFromDate = null;
		String lStrTempToDate = null;
		String lStrFromDate = null;
		String lStrToDate = null;
		Date lDateFromDate = null;
		Date lDateToDate = null;
		List lListTotalDdowiseEntries = null;
		Long yearId = null;
		Long lLongEmployeeAmt = 0L;
		Long lLongEmployerAmt = 0L;
		Long TotalAmt = 0L;
		String dhDtls = "";
		String ddoRegNo = "";
		StringBuilder sb11 = new StringBuilder();
		String errorData = " ";
		String ext = "";
		
		try {
			String Fileno =nsdlDetailsModel.getFileId();
			File f4 = null;
			f4 = new File(Fileno.concat(".txt"));
			f4.delete();
			
			f4.createNewFile();
			
			
			OrgUserMst messages = (OrgUserMst) session.getAttribute("MY_SESSION_MESSAGES");
			String key = "";
			String rootPath = "";
				String strOSName = System.getProperty("os.name");
				boolean test = strOSName.contains("Windows");
				if (strOSName.contains("Windows")) {
				//	key = "serverempconfigimagepath";
				} else {
					key = "serverContributionFolderPath";
					OUTPUT_ZIP_Contri_FILE = environment.getRequiredProperty(key);
					
					  File directory = new File(OUTPUT_ZIP_Contri_FILE);	
				 		if (!directory.exists()) {	
				 			directory.mkdir();	
				 		}	
					
				}
					
				String Path = OUTPUT_ZIP_Contri_FILE;
				String directoryName = Path.concat(messages.getUserName());
				File directory = new File(directoryName);
				if (!directory.exists()) {
					directory.mkdir();
				}
				directoryName=directoryName.concat("/"+Fileno.toString());
				 directory = new File(directoryName);
				if (!directory.exists()) {
					directory.mkdir();
				}
			
			
		
			String filePath =directoryName+"/"+ Fileno.concat(".txt");
			
			
		
			String dtoRegNo=csrfFormService.getDtoRegNumber(messages.getUserName()); 
			FileWriter fw = new FileWriter(filePath);
			BufferedWriter br = new BufferedWriter(fw);
			
			
			Calendar cal = Calendar.getInstance(TimeZone.getDefault());
			String date = "ddMMyyyy";
			SimpleDateFormat sdf = new SimpleDateFormat(date);
			sdf.setTimeZone(TimeZone.getDefault());
			String currentdate = sdf.format(cal.getTime());
			
			
			br.write("1" + "^");
			br.write("FH" + "^");
			br.write("P" + "^");
			br.write(dtoRegNo + "^");
			br.write("1" + "^");
			br.write(currentdate);
			br.write("^");
			br.write("A");
			br.write("^");
			br.write("^");
			br.write("^");
			br.write("^");
			br.write("^");
			br.write("\r\n");
			
			String bhData = nsdlDetailsService.getBatchData(Fileno);
			br.write(bhData);
			br.write("\r\n");
			
			List dhData = nsdlDetailsService.getDHData(Fileno);
			
			if (dhData != null && dhData.size() > 0) {
				Iterator IT = dhData.iterator();
				
				Object[] lObj = null;
				while (IT.hasNext()) {
					lObj = (Object[]) IT.next();
					if(lObj[0]!=null)
					dhDtls = lObj[0].toString();
					if(lObj[1]!=null)
					ddoRegNo = lObj[1].toString();
					br.write(dhDtls);
					
					br.write("\r\n");
					
					List sdDtls = nsdlDetailsService.getSDDtls(Fileno, messages.getUserName());
					for (int i = 0; i < sdDtls.size(); i++) {
						br.write(sdDtls.get(i).toString());
						br.write("\r\n");
						
					}
					
				}
				
			}
			
			br.close();
			String fvuFilePtah = filePath.replace("txt", "fvu");
			String errFilePtah = filePath.replace("txt", "err");
			
			
			String[] args = { filePath, errFilePtah, fvuFilePtah, "0", "1.72" };
			
			//	nsdlDetailsService.updateFileStatus(fileStatus, Fileno, errorData);
			File serverFile = new File(filePath);
			// Download file with InputStreamResource
			FileInputStream fileInputStream = null;
			try {
				fileInputStream = new FileInputStream(serverFile);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			InputStreamResource inputStreamResource = new InputStreamResource(fileInputStream);
			
			return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + serverFile)
					.contentType(MediaType.TEXT_PLAIN).contentLength(serverFile.length()).body(inputStreamResource);
			
		} 
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}

}
