package com.wild.handler.user;

import java.io.PrintWriter;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.google.gson.Gson;
import com.wild.entity.user.WUser;
import com.wild.enums.user.UserStatusEnum;
import com.wild.enums.user.UserVersioniEnum;
import com.wild.service.user.WUserService;
import com.wild.utils.SessionAttribute;
import com.wild.utils.UUIDUtil;
import com.wild.utils.WatchmanMessage;
import com.wild.utils.others.CheckCodeSer;
import com.wild.utils.others.SerAndDeser;

@Controller
@RequestMapping("/wuser")
@SessionAttributes(SessionAttribute.USERLOGIN)
public class WUserHandler implements Serializable {

	private static final long serialVersionUID = -2250657581544309429L;
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");

	@Autowired
	private WUserService userService;

	/**
	 * 注册
	 * 
	 * @param user：用户
	 * @return
	 */
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public void register(@Valid PrintWriter out, WUser user, HttpSession session, HttpServletRequest request,
			ModelMap map) {
		Gson gson = new Gson();
		String loginName = request.getParameter("loginName");// 用户名（手机号码）
		String password = request.getParameter("password");
		String validateCode = request.getParameter("validateCode");// 验证码
		String sex = request.getParameter("Sex");// 性别
		String age = request.getParameter("Age");// 年龄
		String NickName = "";

		try {
			NickName = new String(request.getParameter("NickName").getBytes("ISO-8859-1"), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		// 数据不为空
		if (StringUtils.isNotBlank(loginName) && StringUtils.isNotBlank(password)
				&& StringUtils.isNotBlank(validateCode)) {
			String telForOnly = userService.telForOnly(loginName);
			if (StringUtils.isBlank(telForOnly)) {// 如果电话重复
				CheckCodeSer checkCodeSer = (CheckCodeSer) session.getAttribute("checkCode");
				if (!SerAndDeser.isTimeOut(checkCodeSer)) {// 判断验证码是否超时
					if (validateCode.equals(checkCodeSer.getCheckCode())) {
						int resultRegister = userService.register(new WUser(UUIDUtil.createUUID(), NickName, sex,
								loginName, password, age, new Date(), UserStatusEnum.normal, UserVersioniEnum.common));
						if (resultRegister > 0) {
							out.println(gson.toJson("{\"result\": 1," + " \"desc\": \"注册成功！\"}"));
							out.flush();
							out.close();
						} else {
							out.println(gson.toJson("{\"result\": 0," + " \"desc\": \"系统错误，请重试！\"}"));
							out.flush();
							out.close();
						}
					} else {
						out.println(gson.toJson("{\"result\": 0," + " \"desc\": \"验证码错误！\"}"));
						out.flush();
						out.close();
					}
				} else {
					session.removeAttribute("checkCode");
					out.println(gson.toJson("{\"result\": 0," + " \"desc\": \"验证码失效！\"}"));
					out.flush();
					out.close();
				}
			} else {
				out.println(gson.toJson("{\"result\": 0," + " \"desc\": \"该电话号码已经被注册了！\"}"));
				out.flush();
				out.close();
			}
		} else {
			out.println(gson.toJson("{\"result\": 0," + " \"desc\": \"数据不能为空！\"}"));
			out.flush();
			out.close();
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
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public void Login(WUser userLogin, HttpServletRequest request, HttpServletResponse response, PrintWriter out) {
		Gson gson = new Gson();
		String loginName = request.getParameter("loginName");// 用户名（手机号码）
		String password = request.getParameter("password");

		// 数据不为空
		if (StringUtils.isNotBlank(password) && StringUtils.isNotBlank(loginName)) {
			userLogin.setWPassWord(password);
			if (StringUtils.isNotBlank(loginName)) {
				userLogin.setWUserNum(loginName);
			}
			List<WUser> users = userService.login(userLogin);// 登录

			if (users.size() > 0) {
				out.println(gson.toJson("{\"result\": 1," + " \"desc\": \"登录成功！\"}"));
				out.flush();
				out.close();
			} else {
				out.println(gson.toJson("{\"result\": 0," + " \"desc\": \"登录失败！\"}"));
				out.flush();
				out.close();
			}
		} else {
			out.println(gson.toJson("{\"result\": 0," + " \"desc\": \"用户名或密码错误！\"}"));
			out.flush();
			out.close();
		}
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
		Gson gson = new Gson();
		WatchmanMessage cl = new WatchmanMessage();
		String tel = request.getParameter("loginName");// 获取短信验证码
		String num = getCharAndNumr();
		session.setAttribute(SessionAttribute.TELRLOGIN, num);
		cl.CouldMessageContent(tel, num);
		long date = System.currentTimeMillis();
		String time = sdf.format(date);
		CheckCodeSer checkCodeSer = new CheckCodeSer(num, time, tel);
		session.setAttribute("checkCode", checkCodeSer);
		out.println(gson.toJson("{\"result\": 0," + " \"desc\": \"发送验证码成功！\", " + "\"data\": {"
				+ "\"verificationCode\":" + num + "}}"));
		out.flush();
		out.close();
	}

	/**
	 * 忘记密码(修改)
	 * 
	 * @param userLogin：用户手机号码
	 */
	@RequestMapping(value = "/lostPassWord", method = RequestMethod.GET)
	public void LostPassWord(WUser userLogin, HttpServletRequest request, HttpSession session, PrintWriter out) {
		Gson gson = new Gson();
		String loginName = request.getParameter("loginName");// 用户名（手机号码）
		String password = request.getParameter("password");
		String validateCode = request.getParameter("validateCode");// 验证码

		// 数据不为空
		if (StringUtils.isNotBlank(password) && StringUtils.isNotBlank(loginName)&& StringUtils.isNotBlank(validateCode)) {
			CheckCodeSer checkCodeSer = (CheckCodeSer) session.getAttribute("checkCode");
			if (!SerAndDeser.isTimeOut(checkCodeSer)) {// 判断验证码是否超时
				if (validateCode.equals(checkCodeSer.getCheckCode())) {
					userLogin.setWPassWord(password);
					userLogin.setWUserNum(loginName);

					int users = userService.lostPassWord(userLogin);// 修改

					if (users > 0) {
						out.println(gson.toJson("{\"result\": 1," + " \"desc\": \"修改成功！\"}"));
						out.flush();
						out.close();
					} else {
						out.println(gson.toJson("{\"result\": 0," + " \"desc\": \"修改失败！\"}"));
						out.flush();
						out.close();
					}
				} else {
					out.println(gson.toJson("{\"result\": 0," + " \"desc\": \"验证码错误！\"}"));
					out.flush();
					out.close();
				}
			} else {
				session.removeAttribute("checkCode");
				out.println(gson.toJson("{\"result\": 0," + " \"desc\": \"验证码失效！\"}"));
				out.flush();
				out.close();
			}
		}
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
