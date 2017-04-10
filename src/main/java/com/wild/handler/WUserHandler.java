package com.wild.handler;

import java.io.PrintWriter;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.google.gson.Gson;
import com.wild.entity.WUser;
import com.wild.enums.UserStatusEnum;
import com.wild.enums.UserVersioniEnum;
import com.wild.service.WUserService;
import com.wild.utils.PublicKeyMap;
import com.wild.utils.RSAUtils;
import com.wild.utils.SessionAttribute;
import com.wild.utils.UUIDUtil;
import com.wild.utils.WatchmanMessage;

@Controller
@RequestMapping("/wuser")
@SessionAttributes(SessionAttribute.USERLOGIN)
public class WUserHandler implements Serializable {
	private static final long serialVersionUID = -2250657581544309429L;

	@Autowired
	private WUserService userService;

	/**
	 * 注册
	 * 
	 * @param user：用户
	 * @return
	 */
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public void register(@Valid @ModelAttribute("user") PrintWriter out, WUser user, HttpSession session,
			HttpServletRequest request, ModelMap map) {
		Gson gson = new Gson();
		String loginName = request.getParameter("loginName");// 用户名（手机号码）
		String password = request.getParameter("password");
		String validateCode = request.getParameter("validateCode");// 验证码
		String NickName = request.getParameter("NickName");// 用户昵称

		// 数据不为空
		if (StringUtils.isNotBlank(loginName) && StringUtils.isNotBlank(password)
				&& StringUtils.isNotBlank(validateCode)) {
			if (!validateCode.equals(SessionAttribute.TELRLOGIN)) {
				int resultRegister = userService.register(new WUser(UUIDUtil.createUUID(), NickName, "", loginName,
						password, "", new Date(), UserStatusEnum.normal, UserVersioniEnum.common));
				if (resultRegister > 0) {
					out.println(gson.toJson("{\"result\": 1," + " \"desc\": \"注册成功！\"}"));
					out.flush();
					out.close();
				}
			} else {
				out.println(gson.toJson("{\"result\": 0," + " \"desc\": \"注册失败！\"}"));
				out.flush();
				out.close();
			}
		}

	}

	/**
	 * 登录
	 * 
	 * @param userLogin
	 * @param result
	 * @param request
	 * @param out
	 * @param map
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String Login(WUser userLogin, BindingResult result, HttpServletRequest request, PrintWriter out,
			ModelMap map, HttpSession session) {
		// 如果有错误的话，那么将返回注册页面
		String pwd = RSAUtils.decryptStringByJs(userLogin.getWPassWord());
		List<WUser> users = userService.login(userLogin);
		if (users.size() > 0) {
			map.put(SessionAttribute.USERLOGIN, users.get(0));
			// LogManager.getLogger().debug("user==>" + userLogin);
			return "index";
		} else {
			map.put(SessionAttribute.LOGERRORMSG, "用户名或密码输入不正确");
			return "login";
		}
	}

	public String keyPair() {
		PublicKeyMap publicKeyMap = RSAUtils.getPublicKeyMap();
		System.out.println(publicKeyMap);
		return "login";
	}

	/**
	 * 手机验证码
	 * 
	 * @param out
	 * @param request
	 * @param session
	 */
	@RequestMapping(value = "/smsVerificationCode", method = RequestMethod.GET)
	public void MessageResiter(PrintWriter out, HttpServletRequest request, HttpServletResponse response,
			HttpSession session) {
		WatchmanMessage cl = new WatchmanMessage();
		String tel = request.getParameter("loginName");// 获取短信验证码
		String num = getCharAndNumr();
		session.setAttribute(SessionAttribute.TELRLOGIN, num);
		session.setMaxInactiveInterval(300);// 设置验证码5分钟失效
		cl.CouldMessageContent(tel, num);
		Gson gson = new Gson();
		out.println(gson.toJson("{\"result\": 0," + " \"desc\": \"发送验证码成功！\", " + "\"data\": {"
				+ "\"verificationCode\":" + num + "}}"));
		out.flush();
		out.close();
	}

	/**
	 * 
	 * @param length[生成随机数的长度]
	 * @return
	 */
	public String getCharAndNumr() {
		String val = "";// 定义两变量
		Random ne = new Random();// 实例化一个random的对象ne
		val = ne.nextInt(9999 - 1000 + 1) + 1000 + "";// 为变量赋随机值1000-9999
		return val;
	}
}
