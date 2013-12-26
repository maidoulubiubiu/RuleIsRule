package com.knet51.courses.controllers.patent;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.knet51.ccweb.beans.UserInfo;
import com.knet51.ccweb.jpa.entities.RequirType;
import com.knet51.ccweb.jpa.entities.Requirement;
import com.knet51.ccweb.jpa.entities.patent.Patent;
import com.knet51.ccweb.jpa.entities.patent.PatentField;
import com.knet51.ccweb.jpa.entities.patent.PatentType;
import com.knet51.courses.jpa.services.patent.PatentFieldService;
import com.knet51.courses.jpa.services.patent.PatentService;
import com.knet51.courses.jpa.services.patent.PatentTypeService;
import com.knet51.courses.jpa.services.requirement.RequirTypeService;
import com.knet51.courses.jpa.services.requirement.RequirementService;
import com.knet51.courses.util.MyUtil;

@Controller
public class PatentController {
	@Autowired
	private PatentService patentService;
	@Autowired
	private PatentFieldService patentFieldService;
	@Autowired
	private PatentTypeService patentTypeService;
	@Autowired
	private RequirementService requirementService;
	@Autowired
	private RequirTypeService requirTypeService;
	
	@RequestMapping(value="/search/{patent}", method = RequestMethod.GET)
	public String searchPatent(@PathVariable String patent,Model model,HttpSession session,@RequestParam(value = "pageNumber", defaultValue = "0") int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = "20") int pageSize,@RequestParam("patentType")Long patentType_id,@RequestParam("types") String searchType,@RequestParam("searchParam") String searchParam) throws Exception{
		searchParam = new String(searchParam.getBytes("iso-8859-1"), "utf-8").trim();
		String newsearchParam = MyUtil.replaceSpace(searchParam);
		List<PatentType> patentTypeList = patentTypeService.findAllPatentType();
		model.addAttribute("patentTypeList", patentTypeList);
		PatentType patentType = patentTypeService.findOne(patentType_id);
		Page<Patent> page = patentService.searchPatent(pageNumber, pageSize, patentType, searchType, newsearchParam);
		List<Patent> list = patentService.searchPatentList(patentType, searchType, newsearchParam);
		model.addAttribute("page", page);
		List<PatentField> fieldList = patentFieldService.findAll();
		model.addAttribute("fieldList", fieldList);
		model.addAttribute("searchParam", searchParam);
		model.addAttribute("searchTypes", searchType);
		model.addAttribute("searchpatentCount", list.size());
		model.addAttribute("active", patent);
		return "patent.search.list";
	}
	
	@RequestMapping(value="/search/{patent}/detail", method = RequestMethod.GET)
	public String searchPatentDetail(@PathVariable String patent,Model model){
		List<PatentType> patentTypeList = patentTypeService.findAllPatentType();
		model.addAttribute("patentTypeList", patentTypeList);
		List<PatentField> fieldList = patentFieldService.findAll();
		model.addAttribute("fieldList", fieldList);
		return "patent.search.detail";
	}
	
	@RequestMapping(value="/search/{patent}/detail/list", method = RequestMethod.POST)
	public String searchPatentDetailList(@PathVariable String patent,Model model,HttpSession session,@RequestParam(value = "pageNumber", defaultValue = "0") int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = "20") int pageSize,@Valid DetailSearchPatentForm searchPatentForm,@RequestParam("patentType") Long patentType_id) throws Exception{
		
		
		String applicant = MyUtil.replaceSpace(searchPatentForm.getApplicant());
		String classNum = MyUtil.replaceSpace(searchPatentForm.getClassNum());
		String inventer = MyUtil.replaceSpace(searchPatentForm.getInventer());
		String mainClassNum = MyUtil.replaceSpace(searchPatentForm.getMainClassNum());
		String patentField = MyUtil.replaceSpace(searchPatentForm.getPatentField());
		String patentName =  MyUtil.replaceSpace(searchPatentForm.getPatentName());
		String patentNum = MyUtil.replaceSpace(searchPatentForm.getPatentNum());
		String publishNum = MyUtil.replaceSpace(searchPatentForm.getPublishNum());
		PatentType patentType = null;
		if(patentType_id != null){
			patentType =  patentTypeService.findOne(patentType_id);
		}
	
		Map<String,String> formMap = null;
		searchPatentForm.setApplicant(applicant);
		searchPatentForm.setClassNum(classNum);
		searchPatentForm.setMainClassNum(mainClassNum);
		searchPatentForm.setInventer(inventer);
		searchPatentForm.setPatentField(patentField);
		searchPatentForm.setPatentName(patentName);
		searchPatentForm.setPatentNum(patentNum);
		searchPatentForm.setPublishNum(publishNum);
		try {
			formMap = MyUtil.findPostUnnullInput(searchPatentForm);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		formMap.put("patentType", patentType_id+"");
		model.addAttribute("formMap", formMap);
		
		
		Page<Patent> page = patentService.detailSearchPatent(pageNumber, pageSize, patentType, 
				patentNum, patentName, patentField, mainClassNum, classNum, applicant, inventer, publishNum);
		List<Patent> list = patentService.detailSearchPatentList(patentType, 
				patentNum, patentName, patentField, mainClassNum, classNum, applicant, inventer, publishNum);

		model.addAttribute("page", page);
		model.addAttribute("searchpatentCount", list.size());
		
		List<PatentType> patentTypeList = patentTypeService.findAllPatentType();
		model.addAttribute("patentTypeList", patentTypeList);
		List<PatentField> fieldList = patentFieldService.findAll();
		model.addAttribute("fieldList", fieldList);
		return "patent.search.detail.list";
	}
	
	@RequestMapping(value="/patent/list")
	public String patentList(Model model, HttpSession session,@RequestParam(value = "pageNumber", defaultValue = "0") int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = "20") int pageSize){
		List<Patent> patentList = patentService.findPatentList();
		Page<Patent> page = patentService.findAll(pageNumber, pageSize);
		List<PatentField> fieldList = patentFieldService.findAll();
		model.addAttribute("page", page);
		model.addAttribute("searchpatentCount", patentList.size());
		model.addAttribute("fieldList", fieldList);
		return "patent.list";
	}
	
	@RequestMapping(value="/patent/{patentField}/list")
	public String patentTypeFilter(@PathVariable String patentField,Model model, HttpSession session,@RequestParam(value = "pageNumber", defaultValue = "0") int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = "20") int pageSize) throws Exception{
		patentField = new String(patentField.getBytes("iso-8859-1"), "utf-8").trim();
		Page<Patent> page = null;
		List<Patent> patentList = null;
		if(patentField.equals("all")){
			page = patentService.findAll(pageNumber, pageSize);
			patentList = patentService.findPatentList();
		}else{
			page = patentService.findPatentByPatentField(pageNumber, pageSize, patentField);
			patentList = patentService.findPatentByPatentListField(patentField);
		}
		 
		List<PatentField> fieldList = patentFieldService.findAll();
		 
		model.addAttribute("fieldList", fieldList);
		model.addAttribute("page", page);
		model.addAttribute("searchpatentCount", patentList.size());
		model.addAttribute("patentField", patentField);
		return "patent.list";
	}
	
	@RequestMapping(value="/patent/view")
	public String showPatentDetail(Model model,HttpSession session, @RequestParam(value = "id") String patentNum){
		UserInfo userInfo = (UserInfo) session.getAttribute("userInfo");
//		if(userInfo == null){
//			return "redirect:/patent/list";
//		}
		Patent patent = patentService.findOne(patentNum);
		model.addAttribute("patent", patent);
		return "patent.view";
	}
	
	@RequestMapping(value="/requirement/{require_type}/list")
	public String showRequirementList(@PathVariable String require_type,@RequestParam(value = "pageNumber", defaultValue = "0") int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = "20") int pageSize,Model model){
		RequirType requirType = null;
		if(require_type.equals("patent")){
			requirType = requirTypeService.findOne(1L);
		}else if(require_type.equals("technology")){
			requirType = requirTypeService.findOne(2L);
		}
		Page<Requirement> page = requirementService.findRequireByRequireType(pageNumber, pageSize, requirType);
		model.addAttribute("page", page);
		model.addAttribute("active", require_type);
		return "requirement.list";
	}
	
	@RequestMapping(value="/requirement/{require_type}/view/{require_id}")
	public String showRequirementDetail(@PathVariable String require_type,@PathVariable Long require_id,Model model){
		Requirement requirement = requirementService.findOne(require_id);
		model.addAttribute("requirement", requirement);
		model.addAttribute("active", require_type);
		return "requirement.view";
	}
}
