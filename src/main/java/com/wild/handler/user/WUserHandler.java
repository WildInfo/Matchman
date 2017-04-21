package com.wild.handler.user;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.google.gson.Gson;
import com.wild.entity.user.WDetails;
import com.wild.entity.user.WUser;
import com.wild.entity.user.WUserDetailsRelation;
import com.wild.enums.StatusEnum;
import com.wild.enums.user.UserVersioniEnum;
import com.wild.service.user.WUserService;
import com.wild.utils.SessionAttribute;
import com.wild.utils.UUIDUtil;
import com.wild.utils.WatchmanMessage;
import com.wild.utils.others.CheckCodeSer;
import com.wild.utils.others.QRCodeOP;
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
		WUserDetailsRelation detailsRelation = new WUserDetailsRelation();
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
				&& StringUtils.isNotBlank(validateCode) && StringUtils.isNotBlank(age) && StringUtils.isNotBlank(sex)
				&& StringUtils.isNotBlank(NickName)) {
			user.setWUserNum(loginName);
			user.setWPassWord(password);
			String telForOnly = userService.telForOnly(loginName);
			if (StringUtils.isBlank(telForOnly)) {// 如果电话重复
				CheckCodeSer checkCodeSer = (CheckCodeSer) session.getAttribute("checkCode");
				if (null != checkCodeSer && !SerAndDeser.isTimeOut(checkCodeSer)) {// 判断验证码是否超时
					if (validateCode.equals(checkCodeSer.getCheckCode())) {
						int resultRegister = userService.register(new WUser(UUIDUtil.createUUID(), NickName, sex,
								loginName, password, age, new Date(), StatusEnum.normal, UserVersioniEnum.common));
						if (resultRegister > 0) {
							List<WUser> users = userService.login(user);// 查询
							uploadQR(users.get(0).getWGCNum());
							detailsRelation.setWID(UUIDUtil.createUUID());
							detailsRelation.setWKUserID(users.get(0).getWID());
							detailsRelation.setWKDetailsID(UUIDUtil.createUUID());
							int relations = userService.userDetailsDelation(detailsRelation);// 插入用户详情与用户数据表
							int details = userService.insertDtails(new WDetails(detailsRelation.getWKDetailsID(), null,
									null, null, 0, null, null, null, null, null));// 插入用户详情数据
							if ((relations & details) > 0) {
								out.println(gson.toJson("{\"result\": 1," + " \"desc\": \"注册成功！\","
										+ "\"data\":{\"userinfo\":{\"gcid\":" + users.get(0).getWGCNum()
										+ ",\"loginName\":" + users.get(0).getWUserNum() + ",\"sex\":\" "
										+ users.get(0).getWSex() + "\",\"age\":" + users.get(0).getWAge()
										+ ",\"nickName\":\" " + users.get(0).getWNickName() + "\",\"createTime\":"
										+ users.get(0).getWDate().getTime() + "},\"tokenId\":\" "
										+ users.get(0).getWID() + "\"}}"));
							} else {
								out.println(gson.toJson("{\"result\": 0," + " \"desc\": \"系统错误，请重试！\"}"));
								out.flush();
								out.close();
							}
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
	public void Login(WUser userLogin, HttpServletRequest request, HttpServletResponse response, PrintWriter out,HttpSession session) {
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
				session.setAttribute(SessionAttribute.USERLOGIN, users.get(0));
				out.println(gson.toJson("{\"result\": 1," + " \"desc\": \"登录成功！\","
						+ "\"data\":{\"userinfo\":{\"gcid\":" + users.get(0).getWGCNum() + ",\"loginName\":"
						+ users.get(0).getWUserNum() + ",\"sex\":\" " + users.get(0).getWSex() + "\",\"age\":"
						+ users.get(0).getWAge() + ",\"nickName\":\" " + users.get(0).getWNickName()
						+ "\",\"createTime\":" + users.get(0).getWDate().getTime() + "},\"tokenId\":\" "
						+ users.get(0).getWID() + "\"}}"));
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
		out.println(gson.toJson("{\"result\": 1," + " \"desc\": \"发送验证码成功！\", " + "\"data\": {"
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
		if (StringUtils.isNotBlank(password) && StringUtils.isNotBlank(loginName)
				&& StringUtils.isNotBlank(validateCode)) {
			CheckCodeSer checkCodeSer = (CheckCodeSer) session.getAttribute("checkCode");
			if (null != checkCodeSer && !SerAndDeser.isTimeOut(checkCodeSer)) {// 判断验证码是否超时
				if (validateCode.equals(checkCodeSer.getCheckCode())) {
					userLogin.setWPassWord(password);
					userLogin.setWUserNum(loginName);

					int usersResult = userService.lostPassWord(userLogin);// 修改

					if (usersResult > 0) {
						List<WUser> users = userService.login(userLogin);// 查询
						out.println(gson.toJson("{\"result\": 1," + " \"desc\": \"修改成功！\","
								+ "\"data\":{\"userinfo\":{\"gcid\":" + users.get(0).getWGCNum() + ",\"loginName\":"
								+ users.get(0).getWUserNum() + ",\"sex\":\" " + users.get(0).getWSex() + "\",\"age\":"
								+ users.get(0).getWAge() + ",\"nickName\":\" " + users.get(0).getWNickName()
								+ "\",\"createTime\":" + users.get(0).getWDate().getTime() + "},\"tokenId\":\" "
								+ users.get(0).getWID() + "\"}}"));
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
	 * 用户详情更新
	 * 
	 * @param userLogin
	 * @param request
	 * @param session
	 * @param out
	 */
	@RequestMapping(value = "/detailsResult", method = RequestMethod.GET)
	public void DetailsResult(WDetails details, HttpServletRequest request, HttpSession session, PrintWriter out) {
		Gson gson = new Gson();
		WUserDetailsRelation detailsRelation=new WUserDetailsRelation();
		String tokenId = request.getParameter("tokenId");// 用户id
		String signature = request.getParameter("signature");// 用户签名
		String interest = request.getParameter("interest");// 用户兴趣
		String introduce = request.getParameter("introduce");// 自我介绍
		String headImage = request.getParameter("headImage");// 头像地址

		// 数据不为空
		if (StringUtils.isNotBlank(tokenId)) {
			detailsRelation.setWKUserID(tokenId);
			if (StringUtils.isNotBlank(headImage)) {
				details.setWHeadImage(headImage);
			}
			if (StringUtils.isNotBlank(interest)) {
				details.setWHobbies(interest);
			}
			if (StringUtils.isNotBlank(signature)) {
				details.setWPersonalized(signature);
			}
			if (StringUtils.isNotBlank(introduce)) {
				details.setWIntroduce(introduce);
			}
			List<WUserDetailsRelation> relation = userService.userDetilsById(detailsRelation);//根据用户id查询出详情
			if (relation.size() > 0) {
				details.setWID(relation.get(0).getWKDetailsID());// 用户详细id

				int updetails = userService.updateUserDetails(details);
				if (updetails > 0) {
					out.println(gson.toJson("{\"result\": 1," + " \"desc\": \"保存成功！\"}"));
					out.flush();
					out.close();
				} else {
					out.println(gson.toJson("{\"result\": 0," + " \"desc\": \"保存失败！\"}"));
					out.flush();
					out.close();
				}
			} else {
				out.println(gson.toJson("{\"result\": 0," + " \"desc\": \"保存失败！\"}"));
				out.flush();
				out.close();
			}
		} else {
			out.println(gson.toJson("{\"result\": 0," + " \"desc\": \"用户信息验证失败！\"}"));
			out.flush();
			out.close();
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
	
	/**
	 * 修改头像
	 * @param iconPath
	 * @param request
	 * @param map
	 * @param session
	 * @param out
	 */
	@RequestMapping(value = "/uploadIcon",method = RequestMethod.GET)
	public void uploadHeadIcon(@RequestParam("iconPath")String iconPath,HttpServletRequest request,ModelMap map,HttpSession session,PrintWriter out){
		WUser wUser = (WUser) session.getAttribute(SessionAttribute.USERLOGIN);
		String relativelyPath = "";   
		relativelyPath = WUserHandler.class.getClassLoader().getResource("/").getPath(); // 项目的根目录
        relativelyPath = relativelyPath.substring(1, relativelyPath.indexOf("webapps"));
        String path = relativelyPath + "webapps/iconHead";  //头像存储路径
        File fi = new File(path);
        if(!fi.exists() && !fi.isFile()){
        	fi.mkdir();
        }
        path = path+"/"+wUser.getWGCNum()+iconPath.substring(iconPath.lastIndexOf(".")); 
        FileInputStream in = null;
        FileOutputStream output = null;
        try {
			in = new FileInputStream(new File(iconPath));
			output = new FileOutputStream(new File(path));
			int b = 0;
			while((b = in.read()) != -1){
				output.write(b);
			}
			output.flush();
			output.close();
			in.close();
			out.print("修改成功...");
			out.flush();
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 解析二维码
	 * @param map
	 */
	@RequestMapping("/decodeQR")
	public void deQR(@RequestParam("gcNum") String gcNum,ModelMap map,PrintWriter out){
		String relativelyPath = "";   
		relativelyPath = WUserHandler.class.getClassLoader().getResource("/").getPath(); // 项目的根目录
        relativelyPath = relativelyPath.substring(1, relativelyPath.indexOf("webapps"));
        String path = relativelyPath + "webapps/iconHead/QR"+gcNum+".png";  //头像存储路径
        QRCodeOP qr = new QRCodeOP();
        String content = qr.decoderQRCode(path);
        if(null != content && content.length()>0){
        	out.print("{\"content\":\""+content+"\"}");
        }else{
        	out.print("{\"error\":\"解析失败\"}");
        }
        out.flush();
        out.close();
	}
	
	/**
	 * 上传二维码
	 * @param gcNum：用户GC号
	 */
	public void uploadQR(String gcNum){
		String relativelyPath = "";   
		relativelyPath = WUserHandler.class.getClassLoader().getResource("/").getPath(); // 项目的根目录
        relativelyPath = relativelyPath.substring(1, relativelyPath.indexOf("webapps"));
        String path = relativelyPath + "webapps/iconHead";  //头像存储路径
        File fi = new File(path);
        if(!fi.exists() && !fi.isFile()){
        	fi.mkdir();
        }
        String imgPath = path+"/QR"+gcNum+".png";
        String encoderContent = "Welcome to Watchman!" + gcNum ;
        QRCodeOP handler = new QRCodeOP();  
        handler.encoderQRCode(encoderContent, imgPath, "png"); 
	}
	
}
