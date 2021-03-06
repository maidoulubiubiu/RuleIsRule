package com.knet51.ccweb.controllers.admin.enterprise.authentication;

import java.io.File;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.knet51.ccweb.beans.UserInfo;
import com.knet51.ccweb.controllers.common.defs.GlobalDefs;
import com.knet51.ccweb.jpa.entities.AuthenResource;
import com.knet51.ccweb.jpa.entities.Authentication;
import com.knet51.ccweb.jpa.entities.User;
import com.knet51.ccweb.jpa.services.authentication.AuthenResourceService;
import com.knet51.ccweb.jpa.services.authentication.AuthenticationService;
import com.knet51.ccweb.util.ajax.AjaxValidationEngine;
import com.knet51.ccweb.util.ajax.ValidationResponse;
import com.knet51.ccweb.util.fileUpLoad.FileUtil;

@Controller
public class EnterprsieAuthenticationController {
	private static final Logger logger = LoggerFactory
			.getLogger(EnterprsieAuthenticationController.class);
	public static final long MAX_FILE_SIZE_10M = 10*1024*1024;
	
	@Autowired
	private AuthenticationService authenticationService;
	@Autowired
	private AuthenResourceService authenResourceService;
	
	@RequestMapping(value="/admin/authentication/list")
	public String showEnterpriseAuthentication(HttpSession session, Model model,@RequestParam(value="pageNumber",defaultValue="0") 
	int pageNumber, @RequestParam(value="pageSize", defaultValue="20") int pageSize){
		logger.info("===== into showEnterpriseAuthentication page controller ");
		UserInfo userInfo = (UserInfo) session
				.getAttribute(GlobalDefs.SESSION_USER_INFO);
		Page<Authentication> page = authenticationService.findAllByUser(pageNumber, pageSize, userInfo.getUser());
		model.addAttribute("page", page);
		return "admin.enterprise.authentication.list";
	}
	
	@RequestMapping(value="/admin/authentication/new")
	public String addEnterpriseAuthentication(HttpSession session, Model model){
		logger.info("===== into addEnterpriseAuthentication page controller ");
		Authentication authentication = (Authentication) session.getAttribute("authentication");
		if(authentication != null && authentication.getStatus().equals("pass")){
			return "redirect:/admin";
		}else{
			return "admin.enterprise.authentication.new";
		}
	}
	
	@RequestMapping(value="/admin/authentication/new/create" ,method=RequestMethod.POST)
	public String addEnterpriseAuthenticationDetail(@Valid AuthenticationForm authenticationForm ,HttpSession session,
			Model model,MultipartHttpServletRequest request,BindingResult validResult,RedirectAttributes redirectAttributes) throws Exception{
		logger.info("===== into addAuthenticationDetailInfo page controller ");
		UserInfo userInfo = (UserInfo) session.getAttribute(GlobalDefs.SESSION_USER_INFO);
		User user = userInfo.getUser();
		if(validResult.hasErrors()){
			logger.info("annoDetailInfoForm Validation Failed " + validResult);
			return "redirect:/admin/authentication/new";
		}else{
			logger.info("####  TeacherAnnoDetailController passed.  ####");
			String title = authenticationForm.getTitle();
			String content = authenticationForm.getContent();
			Authentication authentication = new Authentication();
			authentication.setTitle(title);
			authentication.setEmail(authenticationForm.getEmail());
			authentication.setContent(content);
			authentication.setDate(new Date());
			authentication.setUser(user);
			authentication.setStatus("submit");
			Authentication auth = authenticationService.createAuthentication(authentication);
			auth.getUser().setName(authenticationForm.getName());
			auth.getUser().setFix_phone(authenticationForm.getPhone());
			authenticationService.updateAuthentication(auth);
			
			List<MultipartFile> files = request.getFiles("myFiles");
			for(int i=0;i<files.size();i++){
				MultipartFile multipartFile = files.get(i);
				logger.info("=====++++++"+multipartFile.getOriginalFilename());
				if(!files.get(i).isEmpty()){
					if(multipartFile.getSize()>MAX_FILE_SIZE_10M){
						redirectAttributes.addFlashAttribute("errorMsg", "上传资料不得大于10M");
						return "redirect:/admin/authentication/new";
					}else{
						AuthenResource authenResource = new AuthenResource();
						String fileName = files.get(i).getOriginalFilename();
						String path = session.getServletContext().getRealPath("/")+"resources/attached/"+userInfo.getId()+"/authentication/"+auth.getId();
						FileUtil.createRealPath(path, session);
						File saveDest = new File(path + File.separator + fileName);
						multipartFile.transferTo(saveDest);
						String savePath = path+"/"+fileName;
						authenResource.setAuthentication(auth);
						authenResource.setSaveName(fileName);
						authenResource.setSavePath(savePath);
						authenResourceService.createAuthenResource(authenResource);
					}
				}
			}
			
			return "redirect:/admin";
		}
		
	}
	
	/**
	 * download a resource
	 * @param resource_id
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/admin/authentication/download/{id}",method=RequestMethod.GET)
	public String resourceDownLoad(@PathVariable Long id,HttpServletRequest request,HttpServletResponse response) throws Exception{
		logger.info("-------Into authentication resource DownLoad controller------");
		AuthenResource authenResource = authenResourceService.findOneById(id);
		String savePath = authenResource.getSavePath();
		String fileName = authenResource.getSaveName();
		FileUtil.downLoad(request, response, savePath, fileName);
		return null;
	}
	
	@RequestMapping(value = "/admin/authentication/createAuthenticationAjax", method = RequestMethod.POST)
	public @ResponseBody ValidationResponse authenticationInfoFormAjaxJson(@Valid AuthenticationForm authenticationForm, BindingResult result) {
		logger.info("================= into authencation ajax controller");
		return AjaxValidationEngine.process(result);
	}
	
}
