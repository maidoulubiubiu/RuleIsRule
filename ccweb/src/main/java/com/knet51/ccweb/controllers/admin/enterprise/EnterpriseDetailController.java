package com.knet51.ccweb.controllers.admin.enterprise;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.knet51.ccweb.beans.UserInfo;
import com.knet51.ccweb.controllers.admin.user.WithdrawsApplyForm;
import com.knet51.ccweb.controllers.common.defs.GlobalDefs;
import com.knet51.ccweb.jpa.entities.Enterprise;
import com.knet51.ccweb.jpa.entities.Recharge;
import com.knet51.ccweb.jpa.entities.RechargeHistory;
import com.knet51.ccweb.jpa.entities.User;
import com.knet51.ccweb.jpa.entities.WithdrawsApply;
import com.knet51.ccweb.jpa.services.EnterpriseService;
import com.knet51.ccweb.jpa.services.RechargeHistoryService;
import com.knet51.ccweb.jpa.services.RechargeService;
import com.knet51.ccweb.jpa.services.UserService;
import com.knet51.ccweb.jpa.services.WithdrawsApplyService;

@Controller
public class EnterpriseDetailController {
	private static final Logger logger = LoggerFactory
			.getLogger(EnterpriseDetailController.class);

	@Autowired
	private UserService userService;
	@Autowired
	private RechargeHistoryService rechargeHistoryService;
	@Autowired
	private RechargeService rechargeService;
	@Autowired
	private WithdrawsApplyService withdrawsApplyService;
	@Autowired
	private EnterpriseService enterpriseService;
	
	@Transactional
	@RequestMapping(value = "/admin/enterprisepersonalInfo", method = RequestMethod.POST)
	public String enterprisepersonalInfo(@Valid EnterprisePersonalInfoForm personalInfoForm,
			BindingResult validResult, HttpSession session,RedirectAttributes redirectAttr, HttpServletRequest request, HttpServletResponse response) {
		logger.info("#### enterprise Personal InfoController ####");
		
		if (validResult.hasErrors()) {
			logger.info("detailInfoForm Validation Failed " + validResult);
			
		} else {
			logger.info("### detailInfoForm Validation passed. ###");
			
			UserInfo userInfo = (UserInfo) session.getAttribute(GlobalDefs.SESSION_USER_INFO);
			User user = userService.findOne(userInfo.getId());
			user.setName(personalInfoForm.getName());
			user = userService.updateUser(user);
			Enterprise enterprise = new Enterprise(user);
			enterprise.setIsEnterprise("1");
			enterprise = enterpriseService.updateEnterprise(enterprise);
			userInfo.setUser(user);
			userInfo.setEnterprise(enterprise);
			session.setAttribute(GlobalDefs.SESSION_USER_INFO, userInfo);
			
			String message = "个人信息保存成功";
			redirectAttr.addFlashAttribute("message", message);
		}
		return "redirect:/admin/resume?active=personal";
	}
	
	
	/**
	 * create recharge card
	 * @param cardForm
	 * @param validResult
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/admin/recharge/new", method = RequestMethod.POST)
	public String createRechargeHistory(@Valid EnterpriseRechargeCardForm cardForm,BindingResult validResult,HttpSession session){
		logger.info("=== into create rechargeHistory controller ===");
		UserInfo userInfo = (UserInfo) session.getAttribute(GlobalDefs.SESSION_USER_INFO);
		if(validResult.hasErrors()){
			logger.info("RechargeCardForm Validation Failed " + validResult);
			return "redirect:/admin/recharge/create";
		}else{
			Recharge recharge = rechargeService.findOneByCardid(cardForm.getCardid());
			if(recharge!=null){
				RechargeHistory rechargeHistory = new RechargeHistory();
				rechargeHistory.setCardid(recharge.getCardid());
				rechargeHistory.setPrice(recharge.getPrice());
				rechargeHistory.setUser(userInfo.getUser());
				rechargeHistory.setDate(new Date());
				rechargeHistoryService.createRechargeHistory(rechargeHistory);
				rechargeService.deleteRechargeById(recharge.getId());
				return "redirect:/admin/account/list";
			}else{
				return "redirect:/admin/recharge/create";
			}
		}
	}
	
	@RequestMapping(value="/admin/withdraws/create" ,method = RequestMethod.POST)
	public String createWithdrawsApply(@Valid WithdrawsApplyForm withdrawsApplyForm, BindingResult validResult, HttpSession session){
		logger.info("=== into create rechargeHistory controller ===");
		UserInfo userInfo = (UserInfo) session.getAttribute(GlobalDefs.SESSION_USER_INFO);
		if(validResult.hasErrors()){
			logger.info("withdrawsApplyForm Validation Failed " + validResult);
		}else{
			WithdrawsApply withdrawsApply = new WithdrawsApply();
			withdrawsApply.setContent(withdrawsApplyForm.getContent());
			withdrawsApply.setSum(Double.parseDouble(withdrawsApplyForm.getSum()));
			withdrawsApply.setDate(new Date());
			withdrawsApply.setUser(userInfo.getUser());
			withdrawsApplyService.createWithdrawsApply(withdrawsApply);
		}
		return "redirect:/admin/withdraws/list";
	}
	
}
