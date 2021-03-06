package com.knet51.ccweb.controllers.admin.course;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.knet51.ccweb.beans.UserInfo;
import com.knet51.ccweb.controllers.common.defs.GlobalDefs;
import com.knet51.ccweb.jpa.entities.courses.CourseLesson;
import com.knet51.ccweb.jpa.entities.courses.CourseResource;
import com.knet51.ccweb.jpa.entities.courses.CourseType;
import com.knet51.ccweb.jpa.entities.courses.Course;
import com.knet51.ccweb.jpa.entities.resource.ResourceType;
import com.knet51.ccweb.jpa.services.TeacherService;
import com.knet51.ccweb.jpa.services.course.CourseLessonService;
import com.knet51.ccweb.jpa.services.course.CourseResourceService;
import com.knet51.ccweb.jpa.services.course.CourseService;
import com.knet51.ccweb.jpa.services.course.CourseTypeService;
import com.knet51.ccweb.jpa.services.resources.ResourceTypeService;
import com.knet51.ccweb.util.fileUpLoad.FTPUtil;
import com.knet51.ccweb.util.fileUpLoad.FileUtil;


@Controller
public class CourseInfoDetailController {
	private static Logger logger = 
			LoggerFactory.getLogger(CourseInfoDetailController.class);
	public static final long MAX_RESOURCE_SIZE_200M = 200*1024*1024;
	public static final long MAX_COVER_SIZE_2M = 2*1024*1024;
	@Autowired
	private CourseService courseService;
	@Autowired
	private CourseResourceService courseResourceService; 
	@Autowired
	private TeacherService teacherService;
	@Autowired
	private CourseLessonService lessonService;
	@Autowired
	private ResourceTypeService resourceTypeService;
	@Autowired
	private CourseTypeService courseTypeService;
	@Autowired
	private CourseLessonService courseLessonService;
	
	/**
	 * update the teacher's course basic information
	 * @param courseInfoForm
	 * @param validResult
	 * @param session
	 * @param course_id
	 * @return
	 */
	@Transactional
	@RequestMapping(value="/admin/course/edit/edit",method=RequestMethod.POST)
	public String TeacherCourseUpdateInfo(@Valid CourseInfoForm courseInfoForm,
			BindingResult validResult, HttpSession session,@RequestParam("id") Long course_id){
		
		logger.info("#### Into TeacherCourseAdd Controller ####");
		if(validResult.hasErrors()){
			logger.info("detailInfoForm Validation Failed " + validResult);
			return "redirect:/admin/course/edit/"+Long.valueOf(course_id);
		}else{
			Course course = courseService.findOneById(Long.valueOf(course_id));
			String courseName = courseInfoForm.getCourseName();
			String courseDesc = courseInfoForm.getCourseDesc();
			course.setCourseName(courseName);
			course.setCourseDesc(courseDesc);
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			String date = format.format(new Date());
			course.setCourseDate(date);
			courseService.updateTeacherCourse(course);
			return "redirect:/admin/course/list";
		}
	
	}
	/**
	 * remove the course to the recycle bin
	 * @param session
	 * @param course_id
	 * @return
	 */
	@Transactional
	@RequestMapping(value="/admin/course/destory",method=RequestMethod.POST)
	public String TeacherCourseDele( HttpSession session,@RequestParam("cId") Long course_id){
		logger.info("#### Into TeacherCourseAdd Controller ####");
			//teacherCourseService.deleTeacherCourse(course_id);
			Course course = courseService.findOneById(Long.valueOf(course_id));
			course.setPublish(GlobalDefs.PUBLISH_NUM_RECYCLE);
			courseService.updateTeacherCourse(course);
			return "redirect:/admin/course/list";
	}
	/**
	 * delete the course but it will still save in the DB
	 * @param session
	 * @param course_id
	 * @return
	 */
	@Transactional
	@RequestMapping(value="/admin/course/deleted",method=RequestMethod.POST)
	public String deleFromCourseRecycle( HttpSession session,@RequestParam("cId") Long course_id){
		logger.info("#### Into TeacherCourseAdd Controller ####");
		Course course = courseService.findOneById(Long.valueOf(course_id));
		course.setPublish(GlobalDefs.PUBLISH_NUM_DELETE);
		courseService.updateTeacherCourse(course);
		return "redirect:/admin/course/list";
	}
	/**
	 * recover the course which is in recycle bin
	 * @param session
	 * @param course_id
	 * @return
	 */
	@Transactional
	@RequestMapping(value="/admin/course/recover",method=RequestMethod.POST)
	public String courseRecoverUnpublish( HttpSession session,@RequestParam("cId") Long course_id){
		logger.info("#### Into TeacherCourseAdd Controller ####");
			//teacherCourseService.deleTeacherCourse(course_id);
			Course course = courseService.findOneById(Long.valueOf(course_id));
			course.setPublish(GlobalDefs.PUBLISH_NUM_ADMIN);
			courseService.updateTeacherCourse(course);
			return "redirect:/admin/course/list";
	}
	
	/**
	 * create the course resource
	 * @param session
	 * @param model
	 * @param request
	 * @param course_id
	 * @return
	 * @throws Exception
	 */
	@Transactional
	@RequestMapping(value="/admin/course/resource/create",method=RequestMethod.POST)
	public String TeacherCourseResourceAdd(HttpSession session,Model model,RedirectAttributes redirectAttributes,
			MultipartHttpServletRequest request,@RequestParam("courseId") Long course_id,@RequestParam("lessonNum") Integer lessonNum) throws  Exception{
		UserInfo userInfo = (UserInfo) session.getAttribute(GlobalDefs.SESSION_USER_INFO);
		List<MultipartFile> files = request.getFiles("resourceFile");
		String resourceName = request.getParameter("resourceName");
		for(int i=0;i<files.size();i++){
			if(!files.get(i).isEmpty()){
				MultipartFile multipartFile = files.get(i);
				if(multipartFile.getSize()>MAX_RESOURCE_SIZE_200M){
					redirectAttributes.addFlashAttribute("fileMaxError", "上传文件不得大于200M");
					return "redirect:/admin/course/edit/"+Long.valueOf(course_id)+"/modifycourse";
				}
				Long type = Long.parseLong(request.getParameter("type"));
				ResourceType resourceType = resourceTypeService.findOneById(type);
				Long courseLessonId = Long.parseLong(request.getParameter("lessonId"));
				CourseResource resource = new CourseResource();
				logger.info("Upload file name:"+files.get(i).getOriginalFilename()); 	
				String fileName = multipartFile.getOriginalFilename();
				resource.setFileName(resourceName);
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				String date = format.format(new Date());
				resource.setDate(date);
				Course course = courseService.findOneById(Long.valueOf(course_id));
				
				//String path = session.getServletContext().getRealPath("/")+"resources/attached/"+userInfo.getId()+"/course/"+course.getId()+"/"+lessonNum;
				String relativePath ="/resources/attached/"+userInfo.getId()+"/course/"+course.getId()+"/"+lessonNum;
				String path = relativePath;
				InputStream fileInput = multipartFile.getInputStream();
				boolean flag =  FTPUtil.getInstance().uploadFile(path, fileName, fileInput);
				//FileUtil.createRealPath(path, session);
				//File saveDest = new File(path + File.separator + fileName);
				//multipartFile.transferTo(saveDest);
				if(flag){
					String savePath = path+"/"+fileName;
					resource.setSavePath(savePath);
					resource.setSaveName(fileName);
					resource.setLessonNum(lessonNum);
					resource.setCourse_id(Long.valueOf(course_id));
					resource.setCourseLessonId(courseLessonId);
					resource.setResourceType(resourceType);
					resource.setRelativePath(relativePath+"/"+fileName);
					resource.setStatus(GlobalDefs.STATUS_COURSE_RESOURCE);
					courseResourceService.createCourseResource(resource);
				}
				
			}
		}
		return "redirect:/admin/course/edit/"+Long.valueOf(course_id)+"/modifycourse";
	}
	
	/**
	 * update the course resource
	 * @param session
	 * @param model
	 * @param request
	 * @param resource_id
	 * @return
	 * @throws Exception
	 */
	@Transactional
	@RequestMapping(value="/admin/course/resource/update",method=RequestMethod.POST)
	public String ResourceEdit(HttpSession session,Model model,RedirectAttributes redirectAttributes,
			MultipartHttpServletRequest request,@RequestParam("resourceId") Long resource_id,@RequestParam("courseId") Long course_id) throws  Exception{
		UserInfo userInfo = (UserInfo) session.getAttribute(GlobalDefs.SESSION_USER_INFO);
		List<MultipartFile> files = request.getFiles("resourceFile");
		String resourceName = request.getParameter("resourceName");
		CourseResource resource = courseResourceService.findOneById(resource_id); 
		Long type = Long.parseLong(request.getParameter("type"));
		ResourceType resourceType = resourceTypeService.findOneById(type);
		for(int i=0;i<files.size();i++){
			if(!files.get(i).isEmpty()){
				MultipartFile multipartFile = files.get(i);
				if(multipartFile.getSize()>MAX_RESOURCE_SIZE_200M){
					redirectAttributes.addFlashAttribute("fileMaxError", "上传文件不得大于200M");
					return "redirect:/admin/course/edit/"+Long.valueOf(course_id)+"/modifycourse";
				}
				boolean success = FTPUtil.getInstance().deleFtpFile(resource.getSavePath());
				if(success){
					logger.info("Upload file name:"+files.get(i).getOriginalFilename()); 	
					String fileName = multipartFile.getOriginalFilename();
					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
					String date = format.format(new Date());
					resource.setDate(date);
					Course course = courseService.findOneById(resource.getCourse_id());
					
//					String path = session.getServletContext().getRealPath("/")+"/resources/attached/"+userInfo.getId()+"/course/"+course.getId()+File.separator+resource.getLessonNum();
//					String relativePath ="/resources/attached/"+userInfo.getId()+"/course/"+course.getId()+"/"+resource.getLessonNum();
//					FileUtil.createRealPath(path, session);
//					File saveDest = new File(path + File.separator + fileName);
//					multipartFile.transferTo(saveDest);
					
					String relativePath ="/resources/attached/"+userInfo.getId()+"/course/"+course.getId()+"/"+resource.getLessonNum();
					String path = relativePath;
					InputStream fileInput = multipartFile.getInputStream();
					boolean flag =  FTPUtil.getInstance().uploadFile(path, fileName, fileInput);
					if(flag){
						String savePath = path+"/"+fileName;
						resource.setSavePath(savePath);
						resource.setSaveName(fileName);
						resource.setRelativePath(relativePath+"/"+fileName);
					}
					
				}
			
			}
			resource.setFileName(resourceName);
			resource.setResourceType(resourceType);
			courseResourceService.createCourseResource(resource);
		}
		return "redirect:/admin/course/edit/"+Long.valueOf(course_id)+"/modifycourse";
	}
	/**
	 * destory the course resource
	 * @param session
	 * @param model
	 * @param resource_id
	 * @return
	 * @throws Exception
	 */
	@Transactional
	@RequestMapping(value="/admin/course/resource/destory",method=RequestMethod.POST)
	public String destoryTeacherCourseResource(HttpSession session,Model model,
			@RequestParam("resourceId") Long resource_id) throws  Exception{
		CourseResource resource = courseResourceService.findOneById(resource_id); 
		//File oldResource = new File(resource.getSavePath());
		boolean success = FTPUtil.getInstance().deleFtpFile(resource.getSavePath());
		if(success){
			courseResourceService.deleCourseResource(resource_id);
		}
		return "redirect:/admin/course/edit/"+resource.getCourse_id()+"/modifycourse";
	}
	
	/**
	 * download the course resource
	 * @param resource_id
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/course/resource/download/{resource_id}")
	public String resourceDownLoad(@PathVariable Long resource_id,HttpServletRequest request,HttpServletResponse response) throws Exception{
		logger.info("-------Into resource DownLoad controller------");
		CourseResource resource = courseResourceService.findOneById(resource_id);
		String savePath = resource.getSavePath();
		String fileName = resource.getSaveName();
		//FileUtil.downLoad(request, response, savePath, fileName);
		FTPUtil.getInstance().ftpDownLoad( response, savePath, fileName);
		return null;
	}
	

	/**
	 * 修改课程
	 * @return
	 */
	@Transactional
	@RequestMapping(value="/admin/course/edit/{course_id}/modifycourse")
	public String modifyCreateTeacherCourse(HttpSession session,@PathVariable Long course_id,Model model,HttpServletRequest request){
		Course course=courseService.findOneById(course_id);
		if(course == null){
			//logger.info("------------url_courseId"+course_id+"--------select_course_id"+course.getId());
			return "redirect:/admin/course/list";
		}else{
			UserInfo userInfo = (UserInfo) session.getAttribute(GlobalDefs.SESSION_USER_INFO);
			Long teacherId=course.getUser().getId();
			if(!userInfo.getId().equals(teacherId)){
				//logger.info("----------current_user_id="+userInfo.getId()+"----------course_teacher_id"+teacherId);
				return "redirect:/admin/course/list";
			}
		}
		List<CourseResource> listResource = courseResourceService.getAllCourseResourceByCourseIdAndStatus(course_id, GlobalDefs.STATUS_COURSE_RESOURCE);
		List<CourseResource> courseList;
		List<ResourceType> listType = resourceTypeService.getTypeByTypeStatus(GlobalDefs.STATUS_COURSE_RESOURCETYPE);
		model.addAttribute("type", listType);
		Map<Integer, List<CourseResource>> courseMap = new TreeMap<Integer, List<CourseResource>>();
		int LessonNum =0;
		for (CourseResource courseResource : listResource) {
			courseList = new ArrayList<CourseResource>();
			LessonNum = courseResource.getLessonNum();
			courseList = courseResourceService
					.getResourceByLessonNumAndCourseId(LessonNum,course_id);
			courseMap.put(LessonNum, courseList);
		}
		List<CourseLesson> lessonNumList = lessonService.getMaxLessonNumByCourseId(course_id);
		if(lessonNumList.size()>0){
			model.addAttribute("lesson", lessonNumList.get(0));
		}
		List<CourseLesson> lessonList = lessonService.findCourseLessonByCourseId(course_id);
		model.addAttribute("lessonList", lessonList);
		model.addAttribute("lessonListCount", lessonList.size());
		model.addAttribute("resourceCount", listResource.size());
		model.addAttribute("courseMap", courseMap);
		model.addAttribute("course", course);
		
		return "admin.teacher.course.edit.modifycourse";
	}
	/**
	 * 基本信息
	 * @return
	 */
	@Transactional
	@RequestMapping(value="/admin/course/edit/{id}/basicinfo")
	public String basicMessage(HttpSession session,@PathVariable Long id,Model model){
		Course course=courseService.findOneById(id);
		if(course == null){
			return "redirect:/admin/course/list";
		}else{
			UserInfo userInfo = (UserInfo) session.getAttribute(GlobalDefs.SESSION_USER_INFO);
			Long teacherId=course.getUser().getId();
			if(!userInfo.getId().equals(teacherId)){
				return "redirect:/admin/course/list";
			}
		}
		List<CourseType> cTypeList = courseTypeService.findAll();
		model.addAttribute("typeList", cTypeList);
		model.addAttribute("course", course);
		return "admin.teacher.course.edit.basicinfo";
	}
	/**
	 * 修改基本信息
	 * @return
	 */
	@Transactional
	@RequestMapping(value="/admin/course/edit/basicinfomodify",method=RequestMethod.POST)
	public String modifyBasicMessage(HttpSession session,@RequestParam("courseId") Long id,RedirectAttributes redirectAttr,Model model,
			HttpServletRequest request,@Valid CourseInfoForm teacherCourseInfoForm,BindingResult validResult){
		if (validResult.hasErrors()) {
			logger.info("detailInfoForm Validation Failed " + validResult);
			return "redirect:/admin/course/edit/{id}/basicinfo";
		}else{
		Course course=courseService.findOneById(id);
		String courseName=teacherCourseInfoForm.getCourseName();
		Long courseType=teacherCourseInfoForm.getCourseType();
		String courseDesc=teacherCourseInfoForm.getCourseDesc();
		if(courseName!=null||courseType!=null||courseDesc!=null){
			CourseType ctype = courseTypeService.findOneById(courseType);
			course.setCourseName(courseName);
			course.setcType(ctype);
			//course.setCourseType(ctype.getTypeName());
			course.setCourseDesc(courseDesc); 
			courseService.updateTeacherCourse(course);
			redirectAttr.addFlashAttribute("message", "保存成功");
		}
		model.addAttribute("course", course);
	}
		return "redirect:/admin/course/edit/"+id+"/basicinfo";
	}
	/***
	 * 详细信息
	 * @return
	 */
	@Transactional
	@RequestMapping(value="/admin/course/edit/{id}/detailinfo")
	public String detailMessage(HttpSession session,@PathVariable Long id,Model model){
		Course course=courseService.findOneById(id);
		if(course == null){
			return "redirect:/admin/course/list";
		}else{
			UserInfo userInfo = (UserInfo) session.getAttribute(GlobalDefs.SESSION_USER_INFO);
			Long teacherId=course.getUser().getId();
			if(!userInfo.getId().equals(teacherId)){
				return "redirect:/admin/course/list";
			}
		}
		model.addAttribute("course", course);
		return "admin.teacher.course.edit.detailinfo";
	}
	/***
	 * 修改详细信息
	 * @return
	 */
	@Transactional
	@RequestMapping(value="/admin/course/edit/detailinfomodify",method=RequestMethod.POST)
	public String modifyDetailMessage(HttpSession session,@RequestParam("courseId") Long id,Model model,RedirectAttributes redirectAttr,HttpServletRequest request){
		String character=request.getParameter("courseCharacter");
		String targetPerson=request.getParameter("targetPerson");
		Course course=courseService.findOneById(id);
		course.setCourseCharacter(character);
		course.setTargetPerson(targetPerson);
		model.addAttribute("course", course);
		redirectAttr.addFlashAttribute("message", "保存成功");
		return "redirect:/admin/course/edit/"+id+"/detailinfo";
	}
	/***
	 * 封面
	 * @return
	 */
	@Transactional
	@RequestMapping(value="/admin/course/edit/{id}/cover")
	public String CreateCover(HttpSession session,@PathVariable Long id,Model model){
		Course course=courseService.findOneById(id);
		if(course == null){
			return "redirect:/admin/course/list";
		}else{
			UserInfo userInfo = (UserInfo) session.getAttribute(GlobalDefs.SESSION_USER_INFO);
			Long teacherId=course.getUser().getId();
			if(!userInfo.getId().equals(teacherId)){
				return "redirect:/admin/course/list";
			}
		}
		model.addAttribute("course", course);
		return "admin.teacher.course.edit.cover";
	}
	/***
	 * 修改封面
	 * @return
	 * @throws Exception 
	 * @throws IOException 
	 */
	@Transactional
	@RequestMapping(value="/admin/course/edit/moidfycover",method=RequestMethod.POST)
	public String modifyCreateCover(HttpSession session,@RequestParam("courseId") Long id,MultipartHttpServletRequest request,
			Model model,RedirectAttributes redirectAttributes) throws Exception{
			List<MultipartFile> files = request.getFiles("coverFile");
		UserInfo userInfo = (UserInfo) session.getAttribute(GlobalDefs.SESSION_USER_INFO);
		Course course=courseService.findOneById(id);
		for(int i=0;i<files.size();i++){
			MultipartFile multipartFile = files.get(i);
			if(!files.get(i).isEmpty()){
				//logger.info("Upload file name:"+multipartFile.getOriginalFilename()); 
				if(multipartFile.getSize()>MAX_COVER_SIZE_2M){
					redirectAttributes.addFlashAttribute("errorMsg", "图片不得大于2M");
					return "redirect:/admin/course/addcourse?active=first";
				}else{
				String fileName = multipartFile.getOriginalFilename();
				String fileExtension = fileName.substring(fileName.lastIndexOf(".")+1);
				String path = session.getServletContext().getRealPath("/")+"/resources/attached/"+userInfo.getId()+"/course/"+course.getId();
				logger.debug("Upload Path:"+path); 
				FileUtil.createRealPath(path, session);
				String previewFile = path+File.separator+"small"+"."+fileExtension;
				File saveDest = new File(path + File.separator + fileName);
				multipartFile.transferTo(saveDest);
				FileUtil.getPreviewImage(saveDest, new File(previewFile), fileExtension,GlobalDefs.COURSE_COVER_WIDTH,GlobalDefs.COURSE_COVER_HEIGHT);
				String savePath = FileUtil.getSavePath("course", userInfo.getId(), course.getId()+"", request)+"/small"+"."+fileExtension;
				course.setCourseCover(savePath);
				}
			}      
		}
		Course fixedCourse = courseService.updateTeacherCourse(course);
		model.addAttribute("course", fixedCourse);
		return "redirect:/admin/course/edit/"+id+"/cover";
		}
	/**
	 * 修改视频
	 * @return
	 */
	@Transactional
	@RequestMapping(value="/admin/course/edit/{id}/watchvideo")
	public String modifyWatchVideo(HttpSession session,@PathVariable Long id,Model model){
		Course course=courseService.findOneById(id);
		if(course == null){
			return "redirect:/admin/course/list";
		}else{
			UserInfo userInfo = (UserInfo) session.getAttribute(GlobalDefs.SESSION_USER_INFO);
			Long teacherId=course.getUser().getId();
			if(!userInfo.getId().equals(teacherId)){
				return "redirect:/admin/course/list";
			}
		}
		model.addAttribute("course", course);
		return "admin.teacher.course.edit.watchvideo";
	}
	/**
	 * 权限设置
	 * @return
	 */
	@Transactional
	@RequestMapping(value="/admin/course/edit/{id}/powerprice")
	public String powerPrice(HttpSession session,@PathVariable Long id,Model model){
		Course course=courseService.findOneById(id);
		if(course == null){
			return "redirect:/admin/course/list";
		}else{
			UserInfo userInfo = (UserInfo) session.getAttribute(GlobalDefs.SESSION_USER_INFO);
			Long teacherId=course.getUser().getId();
			if(!userInfo.getId().equals(teacherId)){
				return "redirect:/admin/course/list";
			}
		}
		model.addAttribute("course", course);
		return "admin.teacher.course.edit.powerprice";
	}
	/**
	 * 修改权限设置
	 * @return
	 */
	@Transactional
	@RequestMapping(value="/admin/course/edit/powerpricemodify" ,method=RequestMethod.POST)
	public String modifyPowerPrice(HttpSession session,@RequestParam("courseId") Long id,@RequestParam("price") Long price,
			Model model,RedirectAttributes redirectAttr,HttpServletRequest request){
		Integer status=Integer.parseInt(request.getParameter("status"));
		String pwd=request.getParameter("pwd");
		Course course=courseService.findOneById(id);
		course.setStatus(status);
		course.setPrice(price);
		course.setPwd(pwd.trim());
		courseService.updateTeacherCourse(course);
		model.addAttribute("course", course);
		redirectAttr.addFlashAttribute("message", "保存成功");
		return "redirect:/admin/course/edit/"+id+"/powerprice";
	}
	/**
	 * 取消课程
	 * @returndeleLessonNum
	 */
	@Transactional
	@RequestMapping(value="/admin/course/edit/{id}/deletecourse")
	public String modifyDeleteMessage(HttpSession session,@PathVariable Long id,Model model){
		Course course=courseService.findOneById(id);
		if(course == null){
			return "redirect:/admin/course/list";
		}else{
			UserInfo userInfo = (UserInfo) session.getAttribute(GlobalDefs.SESSION_USER_INFO);
			Long teacherId=course.getUser().getId();
			if(!userInfo.getId().equals(teacherId)){
				return "redirect:/admin/course/list";
			}
		}
		model.addAttribute("course", course);
		return "admin.teacher.course.edit.deletecourse";
	}
	/**
	 * 删除课程
	 * @return
	 */
	@Transactional
	@RequestMapping(value="/admin/course/edit/deletecoursemodify")
	public String deleteMessage(HttpSession session,@RequestParam("courseId") Long id,Model model){
		Course course=courseService.findOneById(id);
		if(course == null){
			return "redirect:/admin/course/list";
		}else{
			UserInfo userInfo = (UserInfo) session.getAttribute(GlobalDefs.SESSION_USER_INFO);
			Long teacherId=course.getUser().getId();
			if(!userInfo.getId().equals(teacherId)){
				return "redirect:/admin/course/list";
			}
		}
		course.setPublish(GlobalDefs.PUBLISH_NUM_RECYCLE);
		courseService.createTeacherCourse(course);
		return "redirect:/admin/course/list";
	}
	
	/**
	 * create a course type
	 * @param typeName
	 * @return
	 */
	@RequestMapping(value="/admin/course/type/new/create",method=RequestMethod.POST)
	public String createCourseType(@RequestParam("typeName") String typeName){
		CourseType courseType = new CourseType();
		courseType.setTypeName(typeName);
		courseTypeService.createCourseType(courseType);
		return "redirect:/admin/course/type/list";
	}
	
	/**
	 * destory a course type
	 * @param id
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/admin/course/type/destory/{id}",method=RequestMethod.GET)
	public String destoryCourseType(@PathVariable Long id,HttpSession session){
		UserInfo userInfo = (UserInfo) session.getAttribute(GlobalDefs.SESSION_USER_INFO);
		if(!userInfo.getEmail().equals("tim@apple.com")){
			return "redirect:/admin/course/list";
		}else{
			courseTypeService.destryCourseType(id);
			return "redirect:/admin/course/type/list";
		}
	}
	
	/**
	 * add the lesson number
	 * @param course_id
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/admin/course/edit/addlessonnum",method=RequestMethod.POST)
	public String addNewLessonNum(@RequestParam("courseId") Long course_id,Model model){
		List<CourseLesson> lessonList = courseLessonService.getMaxLessonNumByCourseId(Long.valueOf(course_id));
		int lessonNum = 0;
		if(lessonList.size()>0){
			lessonNum = lessonList.get(0).getLessonNum();
			lessonList.get(0).setStatus(null);
			courseLessonService.createCourseLesson(lessonList.get(0));
		}
		int newLessonNum = 0;
		newLessonNum = lessonNum+1;
		CourseLesson courselesson = new CourseLesson();
		courselesson.setLessonNum(newLessonNum);
		courselesson.setCourseId(Long.valueOf(course_id));
		courselesson.setStatus("max");
		courseLessonService.createCourseLesson(courselesson);
		return "redirect:/admin/course/edit/"+Long.valueOf(course_id)+"/modifycourse";
	}

	/**
	 * destory the lessonNum
	 * @param lesson_id
	 * @param course_id
	 * @return
	 */
	@RequestMapping(value="/admin/course/edit/courselesson/destory",method=RequestMethod.POST)
	public String deleteCoourseLesson(@RequestParam("lessonId") Long lesson_id,@RequestParam("courseId") Long course_id){
		CourseLesson bigLesson = courseLessonService.findOne(lesson_id);
		List<CourseLesson> courseLessonList = courseLessonService.findCourseLessonByCourseId(course_id);
		if(bigLesson.getLessonNum()>=2 && courseLessonList.size()>=2){
			int smallLessonNum = bigLesson.getLessonNum()-1;
			List<CourseLesson> smallLessonList = courseLessonService.findCourseLessonByCourseIdAndLessonNum(course_id, smallLessonNum);
			if(smallLessonList.size()>0){
				smallLessonList.get(0).setStatus("max");
				courseLessonService.createCourseLesson(smallLessonList.get(0));
			}
		}
		courseLessonService.destory(Long.valueOf(lesson_id));
		return "redirect:/admin/course/edit/"+Long.valueOf(course_id)+"/modifycourse";
	}
	
}
