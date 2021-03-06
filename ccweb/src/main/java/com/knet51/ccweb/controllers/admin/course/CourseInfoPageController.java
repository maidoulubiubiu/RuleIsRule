package com.knet51.ccweb.controllers.admin.course;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
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
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.knet51.ccweb.beans.UserCourseBeans;
import com.knet51.ccweb.beans.UserInfo;
import com.knet51.ccweb.controllers.common.defs.GlobalDefs;
import com.knet51.ccweb.jpa.entities.User;
import com.knet51.ccweb.jpa.entities.courses.CourseLesson;
import com.knet51.ccweb.jpa.entities.courses.CourseResource;
import com.knet51.ccweb.jpa.entities.courses.CourseType;
import com.knet51.ccweb.jpa.entities.courses.Course;
import com.knet51.ccweb.jpa.entities.courses.UserCourse;
import com.knet51.ccweb.jpa.entities.timeline.Trends;
import com.knet51.ccweb.jpa.services.TeacherService;
import com.knet51.ccweb.jpa.services.TrendsService;
import com.knet51.ccweb.jpa.services.UserCourseService;
import com.knet51.ccweb.jpa.services.UserService;
import com.knet51.ccweb.jpa.services.course.CourseLessonService;
import com.knet51.ccweb.jpa.services.course.CourseResourceService;
import com.knet51.ccweb.jpa.services.course.CourseService;
import com.knet51.ccweb.jpa.services.course.CourseTypeService;
import com.knet51.ccweb.util.ajax.AjaxValidationEngine;
import com.knet51.ccweb.util.ajax.ValidationResponse;
import com.knet51.ccweb.util.fileUpLoad.FileUtil;

@Controller
public class CourseInfoPageController {
	private static final Logger logger = 
			LoggerFactory.getLogger(CourseInfoPageController.class);
	private static final long MAX_FILE_SIZE_2M = 2*1024*1024;
	@Autowired
	private  CourseService courseService;
	@Autowired
	private TeacherService teacherService;
	@Autowired
	private CourseResourceService courseResourceService;
	@Autowired
	private UserService userService;
	@Autowired
	private CourseLessonService courseLessonService;
	@Autowired
	private UserCourseService userCourseService;
	@Autowired
	private CourseTypeService courseTypeService;
	@Autowired
	private TrendsService trendsService;
	
	@RequestMapping(value="/admin/course/list")
	public String teacherCourseInfo(HttpSession session,Model model ,@RequestParam(value="pageNumber",defaultValue="0") 
	int pageNumber, @RequestParam(value="pageSize", defaultValue="10") int pageSize){
		logger.info("#####Into TeacherCourseInfoPageController#####");
		UserInfo userInfo = (UserInfo) session.getAttribute(GlobalDefs.SESSION_USER_INFO);
		if(userInfo.getRole().equals("user")){
			return "redirect:/admin";
		}
		
		Page<Course> onePage =courseService.findTeacherCourseByUserAndPublishGreaterThan(pageNumber, pageSize,userInfo.getUser(),GlobalDefs.PUBLISH_NUM_DELETE);
		//Page<TeacherCourse> page = teacherCourseService.findTeacherCourseByTeacherAndPublish(pageNumber, pageSize, teacher, publish)
		model.addAttribute("page", onePage);
		if (userInfo.getUser().getRole().equals("teacher")) {
			return "admin.teacher.course.list";
		} else if (userInfo.getUser().getRole().equals("enterprise")) {
			return "admin.enterprise.course.list";
		} else {
			return "404";
		}
	}
	
	@RequestMapping(value="/admin/course/list/{publish}")
	public String teacherCoursePublished(@PathVariable String publish,HttpSession session,Model model ,@RequestParam(value="pageNumber",defaultValue="0") 
	int pageNumber, @RequestParam(value="pageSize", defaultValue="10") int pageSize){
		logger.info("##### Into teacherCoursePublished #####");
		UserInfo userInfo = (UserInfo) session.getAttribute(GlobalDefs.SESSION_USER_INFO);
		if(userInfo.getRole().equals("user")){
			return "redirect:/admin";
		}else{
			Page<Course> onePage = null;
			if("publish".equals(publish)){
				onePage = courseService.findTeacherCourseByUserAndPublish(pageNumber, pageSize, userInfo.getUser(), GlobalDefs.PUBLISH_NUM_ADMIN_FRONT);
			}else if("unpub".equals(publish)){
				onePage = courseService.findTeacherCourseByUserAndPublish(pageNumber, pageSize, userInfo.getUser(), GlobalDefs.PUBLISH_NUM_ADMIN);
			}else if("recycle".equals(publish)){
				onePage = courseService.findTeacherCourseByUserAndPublish(pageNumber, pageSize, userInfo.getUser(), GlobalDefs.PUBLISH_NUM_RECYCLE);
			}
			model.addAttribute("page", onePage);
			if (userInfo.getUser().getRole().equals("teacher")) {
				return "admin.teacher.course.list";
			} else if (userInfo.getUser().getRole().equals("enterprise")) {
				return "admin.enterprise.course.list";
			} else {
				return "404";
			}
		}
		
	}
	
	@RequestMapping(value="/admin/course/new")
	public String courseAdd(){
		
		return "admin.teacher.course.new";
	}
	

	
	@RequestMapping(value="/admin/course/view/{course_id}")
	public String detailCourseInfo(@PathVariable Long course_id,Model model,HttpSession session){
		Course course = courseService.findOneById(course_id);
		UserInfo userInfo = (UserInfo) session.getAttribute(GlobalDefs.SESSION_USER_INFO);
		if(course == null){
			return "redirect:/admin";
		}else{
			Long teacherId=course.getUser().getId();
			if(!userInfo.getId().equals(teacherId)){
				return "redirect:/admin/course/list";
			}
		}
		model.addAttribute("course", course);

		List<CourseResource> listResource = courseResourceService.getAllCourseResourceByCourseIdAndStatus(course_id,GlobalDefs.STATUS_COURSE_RESOURCE);
		List<CourseResource> courseList;
		Map<Integer, List<CourseResource> > courseMap = new TreeMap<Integer, List<CourseResource> >();
		Integer lessonNum = 0;
		for (CourseResource courseResource : listResource) {
			courseList = new ArrayList<CourseResource>();
			lessonNum = courseResource.getLessonNum();
			courseList = courseResourceService
					.getResourceByLessonNumAndCourseId(lessonNum,course_id);
			courseMap.put(lessonNum, courseList);
		}
		model.addAttribute("resourceCount", listResource.size());
		model.addAttribute("courseMap", courseMap);
		if (userInfo.getUser().getRole().equals("teacher")) {
			return "admin.teacher.course.view";
		} else if (userInfo.getUser().getRole().equals("enterprise")) {
			return "admin.enterprise.course.view";
		} else {
			return "404";
		}
	}
	
	/*   new add course   */
	
	@RequestMapping(value="/admin/course/addcourse")
	public String addCoursePage(@RequestParam("active") String active,Model model,HttpSession session){
		UserInfo userInfo = (UserInfo) session.getAttribute(GlobalDefs.SESSION_USER_INFO);
		if(userInfo.getRole().equals("user")){
			return "redirect:/admin";
		}else{
			if (active == null || active.equals("")) {
				active = "first";
			}
			List<CourseType> cTypeList = courseTypeService.findAll();
			model.addAttribute("typeList", cTypeList);
			model.addAttribute("active", active);
			if (userInfo.getUser().getRole().equals("teacher")) {
				return "admin.teacher.course.add";
			} else if (userInfo.getUser().getRole().equals("enterprise")) {
				return "admin.enterprise.course.add";
			} else {
				return "404";
			}
		}
	}
	
	@Transactional
	@RequestMapping(value="/admin/course/new/firststep",method=RequestMethod.POST)
	public String TeacherCourseAddInfo(@Valid CourseInfoForm courseInfoForm,
			BindingResult validResult, HttpSession session,MultipartHttpServletRequest request,Model model,
			RedirectAttributes redirectAttributes) throws Exception{
		logger.info("#### Into TeacherCourseAdd Controller ####");
		if(validResult.hasErrors()){
			logger.info("detailInfoForm Validation Failed " + validResult);
			return "redirect:/admin/course/addcourse?active=first";
		}else{
			List<MultipartFile> files = request.getFiles("coverFile");
			UserInfo userInfo = (UserInfo) session.getAttribute(GlobalDefs.SESSION_USER_INFO);
			CourseType cType = courseTypeService.findOneById(courseInfoForm.getCourseType());
			Course course = new Course();
			String courseName = courseInfoForm.getCourseName();
			String courseDesc = courseInfoForm.getCourseDesc();
			course.setCourseName(courseName);
			course.setCourseDesc(courseDesc);
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			String date = format.format(new Date());
			course.setCourseDate(date);
			course.setStatus(GlobalDefs.STATUS_CCWEB);
			course.setPublish(GlobalDefs.PUBLISH_NUM_ADMIN);
			course.setUser(userInfo.getUser());
			course.setcType(cType);
			//course.setCourseType(cType.getTypeName());
			Course newCourse = courseService.createTeacherCourse(course);
			for(int i=0;i<files.size();i++){
				MultipartFile multipartFile = files.get(i);
				if(!multipartFile.isEmpty()){
					if(multipartFile.getSize()>MAX_FILE_SIZE_2M){
						redirectAttributes.addFlashAttribute("errorMsg", "图片不得大于2M");
						return "redirect:/admin/course/addcourse?active=first";
					}else{
						logger.info("Upload file name:"+multipartFile.getOriginalFilename()); 
						String fileName = multipartFile.getOriginalFilename();
						String fileExtension = fileName.substring(fileName.lastIndexOf(".")+1);
						String path = session.getServletContext().getRealPath("/")+"/resources/attached/"+userInfo.getId()+"/course/"+newCourse.getId();
						logger.debug("Upload Path:"+path); 
						FileUtil.createRealPath(path, session);
						String previewFile = path+File.separator+"small"+"."+fileExtension;
						File saveDest = new File(path + File.separator + fileName);
						multipartFile.transferTo(saveDest);
						FileUtil.getPreviewImage(saveDest, new File(previewFile), fileExtension,GlobalDefs.COURSE_COVER_WIDTH,GlobalDefs.COURSE_COVER_HEIGHT);
						String savePath = FileUtil.getSavePath("course", userInfo.getId(), newCourse.getId()+"", request)+"/small"+"."+fileExtension;
						newCourse.setCourseCover(savePath);
					}
				}
			}
			
			courseService.createTeacherCourse(newCourse);
			redirectAttributes.addFlashAttribute("courseId", newCourse.getId());
			return "redirect:/admin/course/addcourse?active=second";
		}
	
	}
	
	@Transactional
	@RequestMapping(value="/admin/course/new/secondstep",method=RequestMethod.POST)
	public String addCourseSecond(HttpSession session,Model model,
			MultipartHttpServletRequest request,@RequestParam("courseId") Long course_id,@RequestParam("pwd") String pwd, 
			@RequestParam("status") Integer status,@RequestParam("price") Long price,RedirectAttributes redirectAttributes)throws  Exception{
		if(course_id == null){
			return "redirect:/admin/course/list";
		}
		if(price ==null){
			price = (long) 0;
		}
		Course course = courseService.findOneById(Long.valueOf(course_id));
		course.setPwd(pwd.trim());
		course.setStatus(status);
		course.setPrice(price);
		courseService.updateTeacherCourse(course);
		redirectAttributes.addFlashAttribute("courseId", course_id);
//		UserInfo userInfo = (UserInfo) session.getAttribute(GlobalDefs.SESSION_USER_INFO);
		/*
		List<MultipartFile> files = request.getFiles("resourceFile");
		String resourceDesc = request.getParameter("resourceDesc");
		String lessonNum = request.getParameter("resourceOrder");
		for(int i=0;i<files.size();i++){
			if(!files.get(i).isEmpty()){
				MultipartFile multipartFile = files.get(i);
				CourseResource resource = new CourseResource();
				logger.info("Upload file name:"+files.get(i).getOriginalFilename()); 	
				String fileName = multipartFile.getOriginalFilename();
				String name = fileName.substring(0, fileName.indexOf("."));
				resource.setFileName(name);
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				String date = format.format(new Date());
				resource.setDate(date);
				TeacherCourse teacherCourse = teacherCourseService.findOneById(Long.valueOf(course_id));
				String path = session.getServletContext().getRealPath("/")+"/resources/attached/"+userInfo.getId()+"/course/"+teacherCourse.getCourseName()+"/"+lessonNum;
				FileUtil.createRealPath(path, session);
				File saveDest = new File(path + File.separator + fileName);
				multipartFile.transferTo(saveDest);
				String savePath = path+File.separator+fileName;
				resource.setSavePath(savePath);
				resource.setSaveName(fileName);
				resource.setResourceDesc(resourceDesc);
				resource.setLessonNum(lessonNum);
				resource.setCourse_id(Long.valueOf(course_id));
				courseResourceService.createCourseResource(resource);
			}
		}*/
		return "redirect:/admin/course/edit/"+Long.valueOf(course_id)+"/modifycourse";
	}
	/*
	@Transactional
	@RequestMapping(value="/admin/course/new/thirdstep",method=RequestMethod.POST)
	public String addCourseThird(HttpSession session,Model model,
			MultipartHttpServletRequest request,@RequestParam("courseId") Long course_id) throws  Exception{
		if(course_id == null){
			return "redirect:/admin/course/list";
		}
		UserInfo userInfo = (UserInfo) session.getAttribute(GlobalDefs.SESSION_USER_INFO);
		List<MultipartFile> files = request.getFiles("resourceFile");
		String resourceDesc = request.getParameter("resourceDesc");
		String lessonNum = request.getParameter("resourceOrder");
		for(int i=0;i<files.size();i++){
			if(!files.get(i).isEmpty()){
				MultipartFile multipartFile = files.get(i);
				CourseResource resource = new CourseResource();
				logger.info("Upload file name:"+files.get(i).getOriginalFilename()); 	
				String fileName = multipartFile.getOriginalFilename();
				String name = fileName.substring(0, fileName.indexOf("."));
				resource.setFileName(name);
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				String date = format.format(new Date());
				resource.setDate(date);
				TeacherCourse teacherCourse = teacherCourseService.findOneById(Long.valueOf(course_id));
				String path = session.getServletContext().getRealPath("/")+"/resources/attached/"+userInfo.getId()+"/course/"+teacherCourse.getId()+"/"+lessonNum;
				FileUtil.createRealPath(path, session);
				File saveDest = new File(path + File.separator + fileName);
				multipartFile.transferTo(saveDest);
				String savePath = path+File.separator+fileName;
				resource.setSavePath(savePath);
				resource.setSaveName(fileName);
				resource.setResourceDesc(resourceDesc);
				resource.setLessonNum(lessonNum);
				resource.setCourse_id(Long.valueOf(course_id));
				courseResourceService.createCourseResource(resource);
			}
		}
		return "redirect:/admin/course/edit/"+Long.valueOf(course_id)+"/modifycourse";
	}
	*/
	@Transactional
	@RequestMapping(value="/admin/course/edit/{course_id}/publish")
	public String publishCourse(@PathVariable Long course_id,HttpSession session){
		Course course= courseService.findOneById(Long.valueOf(course_id));
		UserInfo userInfo = (UserInfo) session.getAttribute(GlobalDefs.SESSION_USER_INFO);
		if(course == null){
			return "redirect:/admin/course/list";
		}else{
			
			Long teacherId=course.getUser().getId();
			if(!userInfo.getId().equals(teacherId)){
				return "redirect:/admin/course/list";
			}
		}
		course.setPublish(GlobalDefs.PUBLISH_NUM_ADMIN_FRONT);
		Course newCourse = courseService.updateTeacherCourse(course);
		Trends trends = new Trends();
		trends.setCoverUrl(newCourse.getCourseCover());
		trends.setUser(userInfo.getUser());

		trends.setTitle(newCourse.getCourseName());
		trends.setPublishDate(new Date());
		trends.setItemId(newCourse.getId());
		trends.setVariety("course");
		
		trendsService.createTrends(trends);
		return "redirect:/admin/course/list";
	}
	
	@Transactional
	@RequestMapping(value="/admin/course/edit/{course_id}/cancelpublish")
	public String cancelPublish(@PathVariable Long course_id,HttpSession session){
		Course course= courseService.findOneById(Long.valueOf(course_id));
		if(course == null){
			return "redirect:/admin/course/list";
		}else{
			UserInfo userInfo = (UserInfo) session.getAttribute(GlobalDefs.SESSION_USER_INFO);
			Long teacherId=course.getUser().getId();
			if(!userInfo.getId().equals(teacherId)){
				return "redirect:/admin/course/list";
			}
		}
		course.setPublish(GlobalDefs.PUBLISH_NUM_ADMIN);
		courseService.updateTeacherCourse(course);
		return "redirect:/admin/course/list";
	}
	
	/**
	 * show course preview page
	 * @param course_id
	 * @param model
	 * @param session
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(value="/admin/course/edit/{course_id}/preview")
	public String previewCourse(@PathVariable Long course_id,Model model,HttpSession session,
			@RequestParam(value = "pageNumber", defaultValue = "0") int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = "5") int pageSize){
		Course course= courseService.findOneById(Long.valueOf(course_id));
		UserInfo userInfo = (UserInfo) session.getAttribute(GlobalDefs.SESSION_USER_INFO);
		if(course == null){
			return "redirect:/admin/course/list";
		}else{
			Long teacherId=course.getUser().getId();
			if(!userInfo.getId().equals(teacherId)){
				return "redirect:/admin/course/list";
			}
		}
		
		List<CourseResource> listResource = courseResourceService.getAllCourseResourceByCourseIdAndStatus(course_id, GlobalDefs.STATUS_COURSE_RESOURCE);
		List<CourseResource> courseList;
		Map<Integer, List<CourseResource>> courseMap = new TreeMap<Integer, List<CourseResource>>();
		int LessonNum = 0;
		for (CourseResource courseResource : listResource) {
			LessonNum = courseResource.getLessonNum();
			courseList= new ArrayList<CourseResource>();
			courseList = courseResourceService
					.getResourceByLessonNumAndCourseId(LessonNum,Long.valueOf(course_id));
			courseMap.put(LessonNum, courseList);
		}
		List<UserCourseBeans> list = new ArrayList<UserCourseBeans>();
		Page<UserCourse> onePage = userCourseService
				.findUserCourseByTeachercourseid(pageNumber, pageSize, course_id);
		UserCourseBeans UserCourseUser;
		for (int i = 0; i < onePage.getContent().size(); i++) {
			UserCourseUser = new UserCourseBeans();
			long userid = onePage.getContent().get(i).getUserid();
			User user = userCourseService.findByUserId(userid);
			UserCourse comm = onePage.getContent().get(i);
			String userName = user.getName();
			String photoUrl = user.getPhoto_url();
			UserCourseUser.setUserCourse(comm);
			UserCourseUser.setPhotoUrl(photoUrl);
			UserCourseUser.setUserName(userName);
			list.add(UserCourseUser);
		}
		Integer sumPerson = 0;
		List<UserCourse> userCourseList = userCourseService
				.findByTeachercourseid(course_id);
		double courseMark = 0.0;
		for (UserCourse userCourse : userCourseList) {
			if (userCourse.getMark() != null) {
				sumPerson=sumPerson+1;
				courseMark = userCourseService.getMark(course_id);// 一个视频的评论平均分数
			}
		}
		// model.addAttribute("listCount", listUserCourse.size());
		model.addAttribute("listUserCourse", list);
		// model.addAttribute("id", id);
		model.addAttribute("page", onePage);
		model.addAttribute("sumPerson", sumPerson);
		model.addAttribute("courseMark", courseMark);
		model.addAttribute("resourceCount", listResource.size());
		model.addAttribute("courseMap", courseMap);
		model.addAttribute("course", course);
		return "admin.course.preview";
	}

	/**
	 * show the course in courses
	 * @param course_id
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/admin/course/edit/{course_id}/pubcourses")
	public String publishToCourses(@PathVariable Long course_id,Model model,HttpSession session){
		Course course = courseService.findOneById(Long.valueOf(course_id));
		if(course == null){
			return "redirect:/admin/course/list";
		}else{
			UserInfo userInfo = (UserInfo) session.getAttribute(GlobalDefs.SESSION_USER_INFO);
			Long teacherId=course.getUser().getId();
			if(!userInfo.getId().equals(teacherId)){
				return "redirect:/admin/course/list";
			}
		}
		course.setStatus(GlobalDefs.STATUS_CCWEB_COURSES);
		courseService.updateTeacherCourse(course);
		return "redirect:/admin/course/view/"+Long.valueOf(course_id);
	}

	
	
	/**
	 * show all course type list
	 * @param session
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/admin/course/type/list")
	public String showCourseType(HttpSession session,Model model){
		UserInfo userInfo = (UserInfo) session.getAttribute(GlobalDefs.SESSION_USER_INFO);
		if(!userInfo.getEmail().equals("tim@apple.com")){
			return "redirect:/admin/course/list";
		}else{
			List<CourseType> list = courseTypeService.findAll();
			model.addAttribute("list", list);
			if (userInfo.getUser().getRole().equals("teacher")) {
				return "admin.teacher.course.type.list";
			} else if (userInfo.getUser().getRole().equals("enterprise")) {
				return "admin.enterprise.course.type.list";
			} else {
				return "404";
			}
		}
	}
	
	
	
	/**
	 * 验证 课程 fileName是否为空
	 * author:lbx
	 * @param lesson_id
	 * @param course_id
	 * @return
	 */
	@RequestMapping(value="/admin/course/edit/courselesson/checkFileName", method = RequestMethod.POST)
	public void checkFileName(@RequestParam("lessonId") Long id,HttpServletResponse response,HttpSession session) throws Exception{
		PrintWriter out=response.getWriter();
		CourseLesson courseLesson=courseLessonService.findOne(id);
		List<CourseResource> courseResources=courseResourceService.getResourceByLessonNumAndCourseId(courseLesson.getLessonNum(), courseLesson.getCourseId());
		Integer number=0;
		for (CourseResource courseResource : courseResources) {
			if(courseResource.getFileName()!=null){
				number=1;
			}
		}
		String num=number.toString();
		out.write(num);
		out.flush();
		out.close();
	}

	@RequestMapping(value = "/admin/course/courseInfoAJAX", method = RequestMethod.POST)
	public @ResponseBody ValidationResponse courseFormAjaxJson(@Valid CourseInfoForm courseInfoForm, BindingResult result,HttpSession session) {
		return AjaxValidationEngine.process(result);
	}
	
	@RequestMapping(value="/admin/course/checkcoursename", method = RequestMethod.POST)
	public void checkCourseName(@RequestParam("courseName") String courseName,HttpServletResponse response,HttpSession session) throws Exception{
		PrintWriter out=response.getWriter();
		UserInfo userInfo = (UserInfo) session.getAttribute(GlobalDefs.SESSION_USER_INFO);
		Course course=courseService.getTeacherCourseByCourseName(courseName,userInfo.getId());
		Integer number=0;
		if(course!=null){
			number=1;
		}
		String num=number.toString();
		out.write(num);
		out.flush();
		out.close();
	}
	
	@RequestMapping(value="/admin/course/resource/edit/ajax")
	public String courseResourceEditAjax(@RequestParam("resourceId") Long resource_id,HttpServletRequest request,HttpServletResponse response ) throws IOException{
		logger.info("==== into thecourseResourceEditAjax controller ====");
		PrintWriter out = response.getWriter();
		CourseResource resource = courseResourceService.findOneById(Long.valueOf(resource_id));
		Gson g = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
		out.print(g.toJson(resource));
		out.flush();
		out.close();
		return null;
	}
	
	/**
	 * validate the course's pwd when someone wants to check a course which has the pwd in ccweb front page
	 * @param course_id
	 * @param pwd
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value="/checkCoursePwd", method = RequestMethod.POST)
	public String checkCoursePwd(@RequestParam("cid") Long course_id,@RequestParam("coursepwd") String pwd,HttpServletRequest request,HttpServletResponse response ) throws IOException{
		logger.info("==== into the ajax checkCoursePwd controller ====");
		PrintWriter out = response.getWriter();
		Course course = courseService.findOneById(course_id);
		String flag;
		if(!pwd.equals(course.getPwd())){
			flag = "false";
		}else{
			flag = "true";
		}
		out.print(flag);
		out.flush();
		out.close();
		return null;
	}
}
