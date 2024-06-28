/**
 * 
 */
package com.mahait.gov.in.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mahait.gov.in.common.CommonConstants;
import com.mahait.gov.in.common.CommonConstants.STATUS;
import com.mahait.gov.in.common.CommonUtils;
import com.mahait.gov.in.entity.MstMenuRoleMappingEntity;
import com.mahait.gov.in.entity.MstSubMenuEntity;
import com.mahait.gov.in.entity.UserInfo;
import com.mahait.gov.in.model.MstMenuRoleMappingModel;
import com.mahait.gov.in.model.TopicModel;
import com.mahait.gov.in.service.CommonHomeMethodsService;
import com.mahait.gov.in.service.MstMenuRoleMappingService;

/**
 * @author Parvez
 *
 */
@Controller
@RequestMapping("/master")
public class MstMenuRoleMappingController {

	@Autowired
	MstMenuRoleMappingService mstMenuRoleMappingService;
	
	@Autowired
	CommonHomeMethodsService commonHomeMethodsService;
	
	@GetMapping("/mstMenuRoleMapping")
	public String mstCadreMst(@ModelAttribute("mstMenuRoleMappingModel") MstMenuRoleMappingModel mstMenuRoleMappingModel,
										Model model,Locale locale,HttpSession session) {
		
		String message = (String)model.asMap().get("message");
		model.addAttribute("mstMenuRoleMappingModel", mstMenuRoleMappingModel);
		
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
		if(message != null && message.equals("UPDATED")) {
			if(locale != null && locale.getLanguage().equalsIgnoreCase("en")) {
				model = CommonUtils.initModel(CommonConstants.Message.UPDATED_ENGLSH, STATUS.SUCCESS, model);
			} else {
				model = CommonUtils.initModel(CommonConstants.Message.UPDATED_MARATHI, STATUS.SUCCESS, model);
			}
		}
		if(message != null && message.equals("ALLREADYEXISTS")) {
			model.addAttribute("alreadyPresent", "ALLREADYEXISTS");
			if(locale != null && locale.getLanguage().equalsIgnoreCase("en")) {
				model = CommonUtils.initModel(CommonConstants.Message.ALLREADYEXISTS_ENGLISH, STATUS.WARNING, model);
			} else {
				model = CommonUtils.initModel(CommonConstants.Message.ALLREADYEXISTS_MARATHI, STATUS.WARNING, model);
			}
		}
		model.addAttribute("language", locale.getLanguage());
		model.addAttribute("lstMenu", commonHomeMethodsService.findAllMenu(locale.getLanguage()));
		model.addAttribute("lstRole", commonHomeMethodsService.findAllRole(locale.getLanguage()));
		model.addAttribute("lstMenuRoleMapping", commonHomeMethodsService.findAllMenuRoleMapping(locale.getLanguage()));
		return "/views/mst-menu-role-mapping";
    }
	
	@PostMapping("/saveMenuRoleMapping")
	public String saveMenuRoleMapping(@ModelAttribute("mstMenuRoleMappingModel") @Valid MstMenuRoleMappingModel mstMenuRoleMappingModel,
									BindingResult bindingResult,RedirectAttributes redirectAttributes, Model model, Locale locale, HttpSession session) {
		if(bindingResult.hasErrors()) {
			model.addAttribute("language", locale.getLanguage());
			return "/views/mst-menu-role-mapping"; /*Return to HTML Page*/
		} 
		UserInfo messages  = (UserInfo) session.getAttribute("MY_SESSION_MESSAGES");
		
		int afterSaveId = mstMenuRoleMappingService.saveMenuRoleMapping(mstMenuRoleMappingModel,messages);
		if(afterSaveId > 0) {
			redirectAttributes.addFlashAttribute("message","SUCCESS");
		}
		model.addAttribute("language", locale.getLanguage());
		return "redirect:/master/mstMenuRoleMapping"; /*redirects to controller URL*/
	}
	
	@RequestMapping(value="/editMenuRoleMapping/{key}")	// , method = RequestMethod.POST
    public String editMenuRoleMapping(@PathVariable int key, RedirectAttributes redirectAttributes, Model model,Locale locale,HttpSession session) {
		
		model.addAttribute("mstMenuRoleMappingEntity", mstMenuRoleMappingService.findMenuAndRoleMappingByKey(key));
		
		MstMenuRoleMappingEntity objMenuRole = mstMenuRoleMappingService.findMenuAndRoleMappingByKey(key);
		
		model.addAttribute("menu_key", objMenuRole.getMenuCode());
		model.addAttribute("role_key", objMenuRole.getRoleId());
		model.addAttribute("language", locale.getLanguage());
		model.addAttribute("lstMenu", commonHomeMethodsService.findAllMenu(locale.getLanguage()));
		model.addAttribute("lstRole", commonHomeMethodsService.findAllRole(locale.getLanguage()));
		
		UserInfo messages  = (UserInfo) session.getAttribute("MY_SESSION_MESSAGES");
		
		List<TopicModel> menuList = new ArrayList<>();
		List<TopicModel> subMenuList = new ArrayList<>();
		
		menuList = commonHomeMethodsService.findMenuNameByRoleID(messages.getRole_id(),locale.getLanguage());
		subMenuList = commonHomeMethodsService.findSubMenuByRoleID(messages.getRole_id(),locale.getLanguage());
		
		model.addAttribute("menuList", menuList);
		model.addAttribute("subMenuList", subMenuList);
		
		return "/views/edit-menu-role-mapping";
    }
	
	@PostMapping("/editMenuRoleMappingSave")
	public String editMenuRoleMappingSave(@ModelAttribute("mstMenuRoleMappingEntity") @Valid MstMenuRoleMappingEntity mstMenuRoleMappingEntity,
									BindingResult bindingResult,RedirectAttributes redirectAttributes,Model model,Locale locale,HttpSession session) {
		UserInfo messages  = (UserInfo) session.getAttribute("MY_SESSION_MESSAGES");
		if(bindingResult.hasErrors()) {
			model.addAttribute("language", locale.getLanguage());
			return "/views/edit-menu-role-mapping"; 
		} 
		String message = mstMenuRoleMappingService.saveEditMenuRoleMapping(mstMenuRoleMappingEntity,messages.getUser_id());
		if(message.equals("UPDATED")) {
			redirectAttributes.addFlashAttribute("message","UPDATED");
		} else if(message.equals("ALLREADYEXISTS")) {
			redirectAttributes.addFlashAttribute("message","ALLREADYEXISTS");
		}
		return "redirect:/master/mstMenuRoleMapping"; 
	}
	
	@RequestMapping(value = "/checkSubMenuExistsByMenuAndRoleKey/{menuKey}/{roleKey}", produces = "application/json;charset=UTF-8", headers = "Accept=application/json")
    public ResponseEntity<String> checkSubMenuExistsByMenuAndRoleKey(@RequestHeader HttpHeaders headers,@PathVariable int menuKey,@PathVariable int roleKey) {
		List<MstSubMenuEntity> subMenuList = mstMenuRoleMappingService.findSubMenuByMenuKeyAndRoleKey(menuKey,roleKey);
		Integer subMenuListSize = subMenuList.size();
		String resJson = subMenuListSize.toString();
		return ResponseEntity.ok(resJson);
    }
	
	@RequestMapping(value = "/deleteMenuAndRoleMapping/{menuKey}/{roleKey}") 
	public String deleteMenuAndRoleMapping(@PathVariable int menuKey,@PathVariable int roleKey, Model model, Locale locale, 
												RedirectAttributes redirectAttributes,HttpSession session) {
		UserInfo messages  = (UserInfo) session.getAttribute("MY_SESSION_MESSAGES");
		MstMenuRoleMappingEntity mstMenuRoleMappingEntity = mstMenuRoleMappingService.findMenuRoleMappingByMenuKeyAndRoleKey(menuKey,roleKey,messages.getRole_id());
		if (mstMenuRoleMappingEntity != null) {
			model.addAttribute("mstMenuRoleMappingModel", new MstMenuRoleMappingModel());
			model.addAttribute("language", locale.getLanguage());
		}
		return "views/mst-menu-role-mapping";
	}
}
