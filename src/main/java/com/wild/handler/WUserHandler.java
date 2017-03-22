package com.wild.handler;

import java.io.PrintWriter;
import java.io.Serializable;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

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
import com.wild.service.WUserService;
import com.wild.utils.PublicKeyMap;
import com.wild.utils.RSAUtils;
import com.wild.utils.SessionAttribute;
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
	public String register(@Valid @ModelAttribute("user") WUser user,HttpSession session,
			HttpServletRequest request, ModelMap map) {
		/*
		 * if (session.getAttribute(SessionAttribute.TELRLOGIN) == null) {
		 * map.put("regErrorMsg", "验证码已失效"); return "register"; } if
		 * (user.getCode().equalsIgnoreCase((String)
		 * session.getAttribute(SessionAttribute.TELRLOGIN))) { if
		 * (service.register(user) > 0) { return "login"; } } else {
		 * map.put("regErrorMsg", "验证码不正确"); return "register"; } //
		 * 如果有错误的话，那么将返回注册页面 if (result.hasErrors()) { map.put("regErrorMsg",
		 * "注册失败"); return "register"; }
		 */
		return "register";
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
		System.out.println(pwd);
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
	 * @param out
	 * @param request
	 * @param session
	 */
	@RequestMapping(value = "/message", method = RequestMethod.POST)
	public void MessageResiter(PrintWriter out, HttpServletRequest request, HttpSession session) {
		WatchmanMessage cl = new WatchmanMessage();
		String tel = request.getParameter("tel");
		String num = getCharAndNumr(4);
		session.setAttribute(SessionAttribute.TELRLOGIN, num);
		cl.CouldMessageContent(tel, num);
		Gson gson = new Gson();
		out.println(gson.toJson(num));
		out.flush();
		out.close();
	}

	/**
	 * 
	 * @param length[生成随机数的长度]
	 * @return
	 */
	public String getCharAndNumr(int length) {
		String val = "";
		Random random = new Random();
		for (int i = 0; i < length; i++) {
			// 输出字母还是数字
			String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";
			// 字符串
			if ("char".equalsIgnoreCase(charOrNum)) {
				// 取得大写字母还是小写字母
				int choice = random.nextInt(2) % 2 == 0 ? 65 : 97;
				val += (char) (choice + random.nextInt(26));
			} else if ("num".equalsIgnoreCase(charOrNum)) { // 数字
				val += String.valueOf(random.nextInt(10));
			}
		}
		return val;
	}
}
