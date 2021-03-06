package com.knet51.ccweb.controllers.admin.teacher.sendReceMsg;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.knet51.ccweb.beans.UserInfo;
import com.knet51.ccweb.controllers.common.defs.GlobalDefs;
import com.knet51.ccweb.jpa.entities.SendMsg;
import com.knet51.ccweb.jpa.entities.User;
import com.knet51.ccweb.jpa.services.UserService;
import com.knet51.ccweb.jpa.services.msg.SendMsgService;
import com.knet51.ccweb.util.ajax.AjaxValidationEngine;
import com.knet51.ccweb.util.ajax.ValidationResponse;

@Controller
public class SendMsgDetailInfoController {
	private static final Logger logger = LoggerFactory.getLogger(SendMsgDetailInfoController.class);
	
	@Autowired
	private SendMsgService sendMsgService;
	
	@Autowired
	private UserService userService;
	
	@Transactional	
	@RequestMapping(value="/admin/message/sendMsg",method = RequestMethod.POST)
	public String sendMsg(@RequestParam("receiveId") Long receiveId, @RequestParam("sendMsgId") Long sendMsgId , @RequestParam("urmId") Long urmId ,
			@Valid SendMsgInfoForm sendMsgInfoForm,
			BindingResult validResult, HttpSession session,Model model){
		
		logger.info("#### Into SendMsgInfoPageControllerPage ####");
		if(validResult.hasErrors()){
			return "redirect:/admin/message/detailOne?mid=" + sendMsgId+"&urmid="+urmId;
		}else{
			//System.out.println(userId);
			SendMsg sendMsg = new SendMsg();
			//User sender = userService.findOne(senderId);
			UserInfo userInfo = (UserInfo) session.getAttribute(GlobalDefs.SESSION_USER_INFO);
			User sender = userService.findOne(userInfo.getId());
			String title = sendMsgInfoForm.getTitle();
			String content = sendMsgInfoForm.getContent();
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String date = format.format(new Date());
			sendMsg.setTitle(title);
			sendMsg.setIsDelete(1);
			sendMsg.setContent(content);
			sendMsg.setDate(date);
			sendMsg.setUser(sender);
			sendMsgService.add(sendMsg, receiveId, userInfo.getId());
			return "redirect:/admin/message/isRead";
		}	
	}
	
	/* front page  */
	
	@Transactional
	@RequestMapping(value="/message/sendMsgInfo",method = RequestMethod.POST)
	public String frontMessageList(@RequestParam("uid") Long receiverId,@Valid SendMsgInfoForm sendMsgInfoForm,
			BindingResult validResult, HttpSession session,Model model){
		
		logger.info("#### Into SendMsgInfoPageControllerPage ####");
		if(validResult.hasErrors()){
			return "redirect:/id/" + receiverId;
		}else{
			//System.out.println(userId);
			SendMsg sendMsg = new SendMsg();
			UserInfo userInfo = (UserInfo) session.getAttribute(GlobalDefs.SESSION_USER_INFO);
			User user = userService.findOne(userInfo.getId());
			String title = sendMsgInfoForm.getTitle();
			String content = sendMsgInfoForm.getContent();
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String date = format.format(new Date());
			sendMsg.setTitle(title);
			sendMsg.setIsDelete(1);
			sendMsg.setContent(content);
			sendMsg.setDate(date);
			sendMsg.setUser(user);
			sendMsgService.add(sendMsg, receiverId, userInfo.getId());
			return "redirect:/id/" + receiverId;
		}	
	}
	@RequestMapping(value = "/teacher/sendMsgInfoAJAX", method = RequestMethod.POST)
	public @ResponseBody ValidationResponse sendMsgInfoFormAjaxJson(@Valid SendMsgInfoForm sendMsgInfoForm, BindingResult result) {
		return AjaxValidationEngine.process(result);
	}
	
	@RequestMapping(value = "/user/sendMsgInfoAJAX", method = RequestMethod.POST)
	public @ResponseBody ValidationResponse userSendMsgInfoFormAjaxJson(@Valid SendMsgInfoForm sendMsgInfoForm, BindingResult result) {
		return AjaxValidationEngine.process(result);
	}
	
	

}
