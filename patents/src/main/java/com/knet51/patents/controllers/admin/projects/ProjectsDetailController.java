package com.knet51.patents.controllers.admin.projects;

import java.io.File;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.knet51.ccweb.jpa.entities.projects.BizModul;
import com.knet51.ccweb.jpa.entities.projects.PlanInfo;
import com.knet51.ccweb.jpa.entities.projects.Projects;
import com.knet51.ccweb.jpa.entities.projects.TeamInfo;
import com.knet51.patents.beans.UserInfo;
import com.knet51.patents.controllers.common.defs.GlobalDefs;
import com.knet51.patents.jpa.services.UserService;
import com.knet51.patents.jpa.services.projects.BizModulService;
import com.knet51.patents.jpa.services.projects.PlanInfoService;
import com.knet51.patents.jpa.services.projects.ProjectsService;
import com.knet51.patents.jpa.services.projects.TeamInfoService;
import com.knet51.patents.util.fileUpLoad.FileUtil;

@Controller
public class ProjectsDetailController {
	private static final Logger logger = LoggerFactory.getLogger(ProjectsDetailController.class);
	private static final long MAX_FILE_SIZE_2M = 2*1024*1024;
	private static final Integer LOGO_WIDTH = 260;
	private static final Integer LOGO_HEIGHT = 190;
	@Autowired
	private ProjectsService projectsService;
	@Autowired
	private UserService userService;
	@Autowired
	private BizModulService bizModulService;
	@Autowired
	private TeamInfoService teamInfoService;
	@Autowired
	private PlanInfoService planInfoService;
	
	@Transactional
	@RequestMapping(value="/admin/projects/add", method = RequestMethod.POST)
	public String addProjects(@Valid ProjectsForm projectsForm, BindingResult validResult,@RequestParam("industry") String industry,
			HttpSession session,MultipartHttpServletRequest request,RedirectAttributes redirectAttributes) throws Exception{
		UserInfo userInfo = (UserInfo) session.getAttribute(GlobalDefs.SESSION_USER_INFO);
		logger.info("===== into projects=====");
		if(validResult.hasErrors()){
			logger.info("====="+validResult.toString());
			return "redirect:/admin/projects/new";
		}else{
			Projects projects = new Projects();
			projects.setCompanyName(projectsForm.getCompanyName());
			projects.setContent(projectsForm.getContent());
			projects.setEmpNumber(projectsForm.getEmpNumber());
			projects.setIndustry(industry);
			projects.setLocation(projectsForm.getLocation());
			projects.setProgress(projectsForm.getProgress());
			projects.setProjectName(projectsForm.getProjectName());
			projects.setTotalMoney(Long.parseLong(projectsForm.getTotalMoney()));
			projects.setBoss(projectsForm.getBoss());
			projects.setPhone(projectsForm.getPhone());
			projects.setComplete(GlobalDefs.WAITE);
			projects.setCurrentMoney(0L);
			Integer maxInvest = Integer.parseInt(projectsForm.getMaxInvestNum());
			maxInvest = maxInvest>=20?20:maxInvest;
			projects.setMaxInvestNum(maxInvest);
			Integer minMoney = Integer.parseInt(projectsForm.getMinMoney());
			minMoney = minMoney<0?1:minMoney;
			projects.setMinMoney(minMoney);
			projects.setDate(new Date());
			projects.setStatus(GlobalDefs.WAITE);
			projects.setUser(userInfo.getUser());
			projects = projectsService.create(projects);
			
			List<MultipartFile> files = request.getFiles("logoPath");
			for (int i = 0; i < files.size(); i++) {
				MultipartFile multipartFile = files.get(i);
				if(!multipartFile.isEmpty()){
					if(multipartFile.getSize()>MAX_FILE_SIZE_2M){
						redirectAttributes.addFlashAttribute("errorMsg", "图片不得大于2M");
						return "redirect:/admin/projects/new";
					}else{
						logger.info("Upload file name:"+multipartFile.getOriginalFilename()); 
						String fileName = multipartFile.getOriginalFilename();
						String fileExtension = fileName.substring(fileName.lastIndexOf(".")+1);
						String path = session.getServletContext().getRealPath("/")+"/resources/attached/"+userInfo.getId()+"/projects/"+projects.getId();
						logger.debug("Upload Path:"+path); 
						FileUtil.createRealPath(path, session);
						String previewFile = path+File.separator+"small"+"."+fileExtension;
						File saveDest = new File(path + File.separator + fileName);
						multipartFile.transferTo(saveDest);
						FileUtil.getPreviewImage(saveDest, new File(previewFile), fileExtension,LOGO_WIDTH,LOGO_HEIGHT);
						String savePath = FileUtil.getSavePath("projects", userInfo.getId(), projects.getId()+"", request)+"/small"+"."+fileExtension;
						projects.setLogoPath(savePath);
					}
				}
			}
			projectsService.update(projects);
			
			BizModul bizModul = new BizModul(projects);
			bizModul.setTargetUser(projectsForm.getTargetUser());
			bizModul.setCompetitorIntro(projectsForm.getCompetitorIntro());
			bizModul.setCoreValueIntro(projectsForm.getCoreValueIntro());
			bizModul.setProfitModul(projectsForm.getProfitModul());
			bizModul.setModulIntro(projectsForm.getModulIntro());
			bizModul.setTargetReq(projectsForm.getTargetReq());
			bizModulService.create(bizModul);
			
			TeamInfo teamInfo = teamInfoService.findByProjects(projects);
			teamInfo.setShareholderIntro(projectsForm.getShareholderIntro());
			teamInfo.setUnShareholderIntro(projectsForm.getUnShareholderIntro());
			teamInfoService.create(teamInfo);
			
			PlanInfo planInfo = planInfoService.findByProjects(projects);
			planInfo.setContext(projectsForm.getPlanContext());
			planInfoService.create(planInfo);
		}
		return "redirect:/admin/projects/list";
	}
	
	@Transactional
	@RequestMapping(value="/admin/projects/edit/edit", method = RequestMethod.POST)
	public String editProjects(@Valid ProjectsForm projectsForm, BindingResult validResult,@RequestParam("projects_id") Long projects_id,
			@RequestParam("industry") String industry,HttpSession session,MultipartHttpServletRequest request,RedirectAttributes redirectAttributes) throws Exception{
		UserInfo userInfo = (UserInfo) session.getAttribute(GlobalDefs.SESSION_USER_INFO);
		if(validResult.hasErrors()){
			logger.info("====="+validResult.toString());
			return "redirect:/admin/projects/edit/"+projects_id;
		}else{
			Projects projects = projectsService.findOne(projects_id);
			projects.setCompanyName(projectsForm.getCompanyName());
			projects.setContent(projectsForm.getContent());
			projects.setEmpNumber(projectsForm.getEmpNumber());
			projects.setIndustry(industry);
			projects.setLocation(projectsForm.getLocation());
			projects.setProgress(projectsForm.getProgress());
			projects.setProjectName(projectsForm.getProjectName());
			projects.setTotalMoney(Long.parseLong(projectsForm.getTotalMoney()));
			projects.setBoss(projectsForm.getBoss());
			projects.setPhone(projectsForm.getPhone());
			projects.setMaxInvestNum(Integer.parseInt(projectsForm.getMaxInvestNum()));
			projects.setMinMoney(Integer.parseInt(projectsForm.getMinMoney()));
			projects.setDate(new Date());
			projects.setStatus(GlobalDefs.WAITE);
			projects.setUser(userInfo.getUser());
			
			List<MultipartFile> files = request.getFiles("logoPath");
			for (int i = 0; i < files.size(); i++) {
				MultipartFile multipartFile = files.get(i);
				if(!multipartFile.isEmpty()){
					if(multipartFile.getSize()>MAX_FILE_SIZE_2M){
						redirectAttributes.addFlashAttribute("errorMsg", "图片不得大于2M");
						return "redirect:/admin/projects/edit/"+projects_id;
					}else{
						logger.info("Upload file name:"+multipartFile.getOriginalFilename()); 
						String fileName = multipartFile.getOriginalFilename();
						String fileExtension = fileName.substring(fileName.lastIndexOf(".")+1);
						String path = session.getServletContext().getRealPath("/")+"/resources/attached/"+userInfo.getId()+"/projects/"+projects.getId();
						logger.debug("Upload Path:"+path); 
						FileUtil.createRealPath(path, session);
						String previewFile = path+File.separator+"small"+"."+fileExtension;
						File saveDest = new File(path + File.separator + fileName);
						multipartFile.transferTo(saveDest);
						FileUtil.getPreviewImage(saveDest, new File(previewFile), fileExtension,LOGO_WIDTH,LOGO_HEIGHT);
						String savePath = FileUtil.getSavePath("projects", userInfo.getId(), projects.getId()+"", request)+"/small"+"."+fileExtension;
						projects.setLogoPath(savePath);
					}
				}
			}
			projectsService.update(projects);

			BizModul bizModul = bizModulService.findByProjects(projects);
			bizModul.setTargetUser(projectsForm.getTargetUser());
			bizModul.setCompetitorIntro(projectsForm.getCompetitorIntro());
			bizModul.setCoreValueIntro(projectsForm.getCoreValueIntro());
			bizModul.setProfitModul(projectsForm.getProfitModul());
			bizModul.setModulIntro(projectsForm.getModulIntro());
			bizModul.setTargetReq(projectsForm.getTargetReq());
			bizModulService.update(bizModul);
			
			TeamInfo teamInfo = teamInfoService.findByProjects(projects);
			teamInfo.setShareholderIntro(projectsForm.getShareholderIntro());
			teamInfo.setUnShareholderIntro(projectsForm.getUnShareholderIntro());
			teamInfoService.update(teamInfo);
			
			PlanInfo planInfo = planInfoService.findByProjects(projects);
			planInfo.setContext(projectsForm.getPlanContext());
			planInfoService.update(planInfo);
		}
		return "redirect:/admin/projects/list";
	}
	
	@RequestMapping(value="/admin/projects/delete",method = RequestMethod.POST)
	public String destoryProjects(@RequestParam("project_id") Long project_id){
		projectsService.dele(project_id);
		return "redirect:/admin/projects/list";
	}
	
}
