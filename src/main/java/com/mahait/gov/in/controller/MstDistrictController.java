package com.mahait.gov.in.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/master")
public class MstDistrictController {/*
//	protected final Log logger = LogFactory.getLog(getClass());
	@Autowired
	CommonHomeMethodsService commonHomeMethodsService;
	
	@Autowired
	MstDistrictService mstDistrictService;
	
	@Autowired
	MstCountryService mstCountryService;
	
	@GetMapping("/mstDistrict")
	public String adminOfficeMaster(@ModelAttribute("mstDistrictEntity") MstDistrictEntity mstDistrictEntity,
										Model model,Locale locale,HttpSession session) {
		
		String message = (String)model.asMap().get("message");

		mstDistrictEntity.setDistrictCode(Integer.valueOf(commonHomeMethodsService.findCodeSeq("district_code","district_mst"))); 
		model.addAttribute("mstDistrictEntity", mstDistrictEntity);

		
		UserInfo messages  = (UserInfo) session.getAttribute("MY_SESSION_MESSAGES");
		
		List<TopicModel> menuList = new ArrayList<>();
		List<TopicModel> subMenuList = new ArrayList<>();
		
		menuList = commonHomeMethodsService.findMenuNameByRoleID(messages.getRole_id(),locale.getLanguage());
		subMenuList = commonHomeMethodsService.findSubMenuByRoleID(messages.getRole_id(),locale.getLanguage());
		
		model.addAttribute("menuList", menuList);
		model.addAttribute("subMenuList", subMenuList);
		
		if(message != null && message.equals("SUCCESS")) {
			if(locale != null && locale.getLanguage().equalsIgnoreCase("en")) {
				model = CommonUtils.initModel(CommonConstants.Message.ADDED_ENGLSH, STATUS.SUCCESS, model);
			} else {
				model = CommonUtils.initModel(CommonConstants.Message.ADDED_MARATHI, STATUS.SUCCESS, model);
			}
		}
		model.addAttribute("lstDistrictTable",mstDistrictService.findAllDistrict());
	
		model.addAttribute("lstCountryTable", mstCountryService.findAllCountry().stream()
				.filter(p -> p.getIsActive() == '1').collect(Collectors.toList()));
		
		
		model.addAttribute("language", locale.getLanguage());
		return "/views/mst-district";
    }
	
	@GetMapping(value="/fetchStateByCountry/{countryId}")
	public   ResponseEntity<List<MstStateModel>>  fetchStateByCountry(@PathVariable int countryId,Model model,Locale locale) {
		return new ResponseEntity<List<MstStateModel>>( mstDistrictService.fetchStateByCountry(countryId), HttpStatus.OK);
	}
	
	
	@PostMapping("/saveDistrictMst")
	public String saveDistrict(@ModelAttribute("mstDistrictEntity") @Valid MstDistrictEntity mstDistrictEntity,
									BindingResult bindingResult,RedirectAttributes redirectAttributes,HttpSession session) {
		UserInfo messages  = (UserInfo) session.getAttribute("MY_SESSION_MESSAGES");
		ModelAndView modelAndView = new ModelAndView();
		if(bindingResult.hasErrors()) {
			return "/views/mst-district"; Return to HTML Page
		} 
		mstDistrictEntity.setCreatedUserId(messages.getUser_id());
		MstDistrictEntity MstDistrictEntity12 = mstDistrictService.saveDistrict(mstDistrictEntity);
		
		if(MstDistrictEntity12 != null) {
			redirectAttributes.addFlashAttribute("message","SUCCESS");
		}
		return "redirect:/admin/master/mstDistrict"; redirects to controller URL
	}
	
	@RequestMapping("/validateDistrictName/{districtname}")
	public @ResponseBody List<Long> validateDistrictName(@PathVariable String districtname, Model model, Locale locale) {

		List<Long> status = mstDistrictService.validateDistrictname(districtname);
		return status;
	}

*/}
