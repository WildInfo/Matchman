package com.wild.handler.user;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.google.gson.Gson;
import com.wild.entity.user.FriendList;
import com.wild.entity.user.HotComment;
import com.wild.entity.user.HotFriend;
import com.wild.entity.user.HotPraise;
import com.wild.entity.user.WDetails;
import com.wild.entity.user.WUser;
import com.wild.entity.user.WUserDetailsRelation;
import com.wild.enums.message.StatusEnum;
import com.wild.enums.user.AgeEnum;
import com.wild.enums.user.SexEnum;
import com.wild.enums.user.UserVersioniEnum;
import com.wild.service.user.FriendShipService;
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
	DateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@Autowired
	private WUserService userService;

	@Autowired
	private FriendShipService friendShipService;

	/**
	 * 注册
	 * 
	 * @param user：用户
	 * @return
	 */
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public void register(PrintWriter out, WUser user, HttpSession session, HttpServletRequest request) {
		Gson gson = new Gson();
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> map2 = new HashMap<String, Object>();
		WUserDetailsRelation detailsRelation = new WUserDetailsRelation();
		if (StringUtils.isNotBlank(user.getLoginName())) {
			// 数据不为空
			if (StringUtils.isNotBlank(user.getLoginName()) && StringUtils.isNotBlank(user.getPassword())
					&& StringUtils.isNotBlank(user.getValidateCode()) && StringUtils.isNotBlank(user.getNickName())) {
				user.setTokenId(UUIDUtil.createUUID());
				// SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd
				// hh:mm:ss");
				user.setWDate(new Date());
				user.setWStatus(StatusEnum.normal);
				user.setWSuperManager(UserVersioniEnum.common);// 普通权限
				// 判断性别
				if (0 == (Integer.valueOf(user.getSex()))) {
					user.setSex(SexEnum.man.getDesc());
				} else {
					user.setSex(SexEnum.woman.getDesc());
				}
				// 判断年龄
				switch (Integer.valueOf(user.getAge())) {
				case 1:
					user.setAge(AgeEnum.seventy.getDesc());
					break;
				case 2:
					user.setAge(AgeEnum.eighty.getDesc());
					break;
				case 3:
					user.setAge(AgeEnum.ninety.getDesc());
					break;
				case 4:
					user.setAge(AgeEnum.zero.getDesc());
					break;
				}

				String telForOnly = userService.telForOnly(user.getLoginName());
				if (StringUtils.isBlank(telForOnly)) {// 如果电话重复
					CheckCodeSer checkCodeSer = (CheckCodeSer) session.getAttribute("checkCode");
					if (null != checkCodeSer && !SerAndDeser.isTimeOut(checkCodeSer)) {// 判断验证码是否超时
						if (user.getValidateCode().equals(checkCodeSer.getCheckCode())) {
							int resultRegister = userService.register(user);
							if (resultRegister > 0) {
								List<WUser> users = userService.login(user);// 查询
								uploadQR(users.get(0).getWGCNum());
								detailsRelation.setWID(UUIDUtil.createUUID());
								detailsRelation.setWKUserID(users.get(0).getTokenId());// 存放tokenId号
								detailsRelation.setWKDetailsID(UUIDUtil.createUUID());
								int relations = userService.userDetailsDelation(detailsRelation);// 插入用户详情与用户数据表
								int details = userService.insertDtails(new WDetails(detailsRelation.getWKDetailsID(),
										null, null, null, 0, null, null, null, null, null));// 插入用户详情数据
								if ((relations & details) > 0) {
									WUser userJson = new WUser();

									userJson.setWGCNum(users.get(0).getWGCNum());
									userJson.setNickName(users.get(0).getNickName());
									userJson.setSex(users.get(0).getSex());
									userJson.setLoginName((users.get(0).getLoginName()));
									userJson.setAge(users.get(0).getAge());
									userJson.setWDate(users.get(0).getWDate());

									map2.put("userInfo", userJson);
									map2.put("tokenId", users.get(0).getTokenId());

									map.put("result", "1");
									map.put("desc", "注册成功！");
									map.put("data", map2);

									out.println(gson.toJson(map));
									out.flush();
									out.close();
								} else {
									map.put("result", "0");
									map.put("desc", "系统错误，请重试！");
									map.put("data", map2);

									out.println(gson.toJson(map));
									out.flush();
									out.close();
								}
							} else {
								map.put("result", "0");
								map.put("desc", "系统错误，请重试！");
								map.put("data", map2);

								out.println(gson.toJson(map));
								out.flush();
								out.close();
							}
						} else {
							map.put("result", "2");
							map.put("desc", "验证码错误！");
							map.put("data", map2);

							out.println(gson.toJson(map));
							out.flush();
							out.close();
						}
					} else {
						session.removeAttribute("checkCode");
						map.put("result", "3");
						map.put("desc", "验证码失效！");
						map.put("data", map2);

						out.println(gson.toJson(map));
						out.flush();
						out.close();
					}
				} else {
					map.put("result", "4");
					map.put("desc", "该电话号码已经被注册了！");
					map.put("data", map2);

					out.println(gson.toJson(map));
					out.flush();
					out.close();
				}
			} else {
				map.put("result", "5");
				map.put("desc", "数据不能为空！");
				map.put("data", map2);

				out.println(gson.toJson(map));
				out.flush();
				out.close();
			}
		} else {
			map.put("result", "5");
			map.put("desc", "数据不能为空！");
			map.put("data", map2);

			out.println(gson.toJson(map));
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
	 * @throws IOException
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public void Login(PrintWriter out, WUser userLogin, ModelMap modelMap, HttpSession session) throws IOException {
		Gson gson = new Gson();
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> map2 = new HashMap<String, Object>();
		// 数据不为空
		if (null != userLogin) {
			List<WUser> users = userService.login(userLogin);// 登录
			if (users.size() > 0) {
				session.setAttribute(SessionAttribute.USERLOGIN, users.get(0));
				modelMap.addAttribute(SessionAttribute.USERLOGIN, users.get(0));
				WUser userJson = new WUser();

				userJson.setWGCNum(users.get(0).getWGCNum());
				userJson.setNickName(users.get(0).getNickName());
				userJson.setSex(users.get(0).getSex());
				userJson.setLoginName(users.get(0).getLoginName());
				userJson.setAge(users.get(0).getAge());
				userJson.setWDate(users.get(0).getWDate());

				map2.put("userInfo", userJson);
				map2.put("tokenId", users.get(0).getTokenId());

				map.put("result", "1");
				map.put("desc", "登录成功！");
				map.put("data", map2);

				out.println(gson.toJson(map));
				out.flush();
				out.close();
			} else {
				map.put("result", "0");
				map.put("desc", "登录失败！");
				map.put("data", map2);

				out.println(gson.toJson(map));
				out.flush();
				out.close();
			}
		} else {
			map.put("result", "2");
			map.put("desc", "用户名或密码错误！");
			map.put("data", map2);

			out.println(gson.toJson(map));
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
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> map2 = new HashMap<String, Object>();
		WatchmanMessage cl = new WatchmanMessage();
		String tel = request.getParameter("loginName");// 获取短信验证码
		// String verificationCode = getCharAndNumr();
		String verificationCode = "1111";
		session.setAttribute(SessionAttribute.TELRLOGIN, verificationCode);
		// boolean result = cl.CouldMessageContent(tel, verificationCode);
		boolean result = true;
		long date = System.currentTimeMillis();
		String time = sdf.format(date);
		CheckCodeSer checkCodeSer = new CheckCodeSer(verificationCode, time, tel);
		session.setAttribute("checkCode", checkCodeSer);
		if (result) {// 如果短信发送成功

			map2.put("verificationCode", verificationCode);

			map.put("result", "1");
			map.put("desc", "验证码发送成功！");
			map.put("data", map2);

			out.println(gson.toJson(map));
			out.flush();
			out.close();
		} else {
			map.put("result", "0");
			map.put("desc", "验证码发送失败！");

			map.put("data", map2);

			out.println(gson.toJson(map));
			out.flush();
			out.close();
		}

	}

	/**
	 * 忘记密码(修改)
	 * 
	 * @param userLogin：用户手机号码
	 */
	@RequestMapping(value = "/lostPassWord", method = RequestMethod.POST)
	public void LostPassWord(WUser userLogin, HttpServletRequest request, HttpSession session, PrintWriter out) {
		Gson gson = new Gson();
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> map2 = new HashMap<String, Object>();
		String loginName = userLogin.getLoginName();// 用户名（手机号码）
		String password = userLogin.getPassword();
		String validateCode = userLogin.getValidateCode();// 验证码

		// 数据不为空
		if (StringUtils.isNotBlank(password) && StringUtils.isNotBlank(loginName)
				&& StringUtils.isNotBlank(validateCode)) {
			CheckCodeSer checkCodeSer = (CheckCodeSer) session.getAttribute("checkCode");
			if (null != checkCodeSer && !SerAndDeser.isTimeOut(checkCodeSer)) {// 判断验证码是否超时
				if (validateCode.equals(checkCodeSer.getCheckCode())) {
					int usersResult = userService.lostPassWord(userLogin);// 修改
					if (usersResult > 0) {
						List<WUser> users = userService.login(userLogin);// 查询
						WUser userJson = new WUser();

						userJson.setWGCNum(users.get(0).getWGCNum());
						userJson.setNickName(users.get(0).getNickName());
						userJson.setSex(users.get(0).getSex());
						userJson.setLoginName(users.get(0).getLoginName());
						userJson.setAge(users.get(0).getAge());
						userJson.setWDate(users.get(0).getWDate());

						map2.put("userInfo", userJson);
						map2.put("tokenId", users.get(0).getTokenId());

						map.put("result", "1");
						map.put("desc", "修改成功！");
						map.put("data", map2);

						out.println(gson.toJson(map));
						out.flush();
						out.close();
					} else {
						map.put("result", "0");
						map.put("desc", "修改失败！");
						map.put("data", map2);

						out.println(gson.toJson(map));
						out.flush();
						out.close();
					}
				} else {
					map.put("result", "2");
					map.put("desc", "验证码错误！");
					map.put("data", map2);

					out.println(gson.toJson(map));
					out.flush();
					out.close();
				}
			} else {
				session.removeAttribute("checkCode");
				map.put("result", "3");
				map.put("desc", "验证码失效！");
				map.put("data", map2);

				out.println(gson.toJson(map));
				out.flush();
				out.close();
			}
		}
	}

	/**
	 * 根据用户ID查询用户详情
	 * 
	 * @param user
	 * @param details
	 * @param request
	 * @param session
	 * @param out
	 */
	@RequestMapping(value = "/DetailsUser", method = RequestMethod.POST)
	public void DetailsUser(WUser user, WDetails details, HttpServletRequest request, HttpSession session,
			PrintWriter out) {
		Gson gson = new Gson();
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> map2 = new HashMap<String, Object>();
		WUserDetailsRelation detailsRelation = new WUserDetailsRelation();

		if (StringUtils.isNotBlank(user.getTokenId())) {
			detailsRelation.setWKUserID(user.getTokenId());
			List<WUserDetailsRelation> relation = userService.userDetilsById(detailsRelation);// 根据用户id查询出详情
			if (relation.size() > 0) {
				details.setWID(relation.get(0).getWKDetailsID());// 用户详细id
				List<WDetails> detailsUser = userService.selectDetils(details);
				if (detailsUser.size() > 0) {

					map.put("result", "1");
					map.put("desc", "查看成功！");
					map2.put("detailsInfo", detailsUser);
					map.put("data", map2);

					out.println(gson.toJson(map));
					out.flush();
					out.close();
				} else {
					map.put("result", "0");
					map.put("desc", "查看失败！");
					map.put("data", map2);

					out.println(gson.toJson(map));
					out.flush();
					out.close();
				}
			} else {
				map.put("result", "0");
				map.put("desc", "查看失败！");
				map.put("data", map2);

				out.println(gson.toJson(map));
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
	@RequestMapping(value = "/detailsResult", method = RequestMethod.POST)
	public void DetailsResult(WUser user, WDetails details, HttpServletRequest request, HttpSession session,
			PrintWriter out) {
		Gson gson = new Gson();
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> map2 = new HashMap<String, Object>();

		WUserDetailsRelation detailsRelation = new WUserDetailsRelation();
		// 数据不为空
		if (StringUtils.isNotBlank(user.getTokenId())) {
			detailsRelation.setWKUserID(user.getTokenId());
			List<WUserDetailsRelation> relation = userService.userDetilsById(detailsRelation);// 根据用户id查询出详情
			if (relation.size() > 0) {
				details.setWID(relation.get(0).getWKDetailsID());// 用户详细id

				int updetails = userService.updateUserDetails(details);
				if (updetails > 0) {

					map.put("result", "1");
					map.put("desc", "保存成功！");

					map.put("data", map2);

					out.println(gson.toJson(map));
					out.flush();
					out.close();
				} else {
					map.put("result", "0");
					map.put("desc", "保存失败！");
					map.put("data", map2);

					out.println(gson.toJson(map));
					out.flush();
					out.close();
				}
			} else {
				map.put("result", "0");
				map.put("desc", "保存失败！");
				map.put("data", map2);

				out.println(gson.toJson(map));
				out.flush();
				out.close();
			}
		} else {
			map.put("result", "2");
			map.put("desc", "用户信息验证失败！");
			map.put("data", map2);

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
	 * 
	 * @param iconPath
	 * @param request
	 * @param map
	 * @param session
	 * @param out
	 */
	@RequestMapping(value = "/uploadIcon", method = RequestMethod.POST)
	public void uploadHeadIcon(@RequestParam("iconPath") String iconPath, WUser user, HttpServletRequest request,
			HttpSession session, PrintWriter out) {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> map2 = new HashMap<String, Object>();
		Gson gson = new Gson();

		String relativelyPath = "";
		relativelyPath = WUserHandler.class.getClassLoader().getResource("/").getPath(); // 项目的根目录
		relativelyPath = relativelyPath.substring(1, relativelyPath.indexOf("webapps"));
		String path = relativelyPath + "webapps/iconHead"; // 头像存储路径
		File fi = new File(path);
		if (!fi.exists() && !fi.isFile()) {
			fi.mkdir();
		}
		path = path + "/" + user.getTokenId() + iconPath.substring(iconPath.lastIndexOf("."));
		FileInputStream in = null;
		FileOutputStream output = null;
		try {
			in = new FileInputStream(new File(iconPath));
			output = new FileOutputStream(new File(path));
			int b = 0;
			while ((b = in.read()) != -1) {
				output.write(b);
			}
			output.flush();
			output.close();
			in.close();
			map.put("result", "1");
			map.put("desc", "保存成功！");
			// map2.put("", "");

			map.put("data", map2);

			out.println(gson.toJson(map));
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
	 * 
	 * @param map
	 */
	@RequestMapping(value = "/decodeQR", method = RequestMethod.POST)
	public void deQR(WUser user, PrintWriter out) {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> map2 = new HashMap<String, Object>();
		Gson gson = new Gson();
		String relativelyPath = "";
		relativelyPath = WUserHandler.class.getClassLoader().getResource("/").getPath(); // 项目的根目录
		relativelyPath = relativelyPath.substring(1, relativelyPath.indexOf("webapps"));
		String path = relativelyPath + "webapps/iconHead/QR" + user.getTokenId() + ".png"; // 头像存储路径
		QRCodeOP qr = new QRCodeOP();
		String content = qr.decoderQRCode(path);
		if (null != content && content.length() > 0) {
			map.put("result", "1");
			map.put("desc", "保存成功！");
			map2.put("content", content);

			map.put("data", map2);

			out.println(gson.toJson(map));
		} else {
			map.put("result", "0");
			map.put("desc", "解析失败！");
			map.put("data", map2);

			out.println(gson.toJson(map));
		}
		out.flush();
		out.close();
	}

	/**
	 * 上传二维码
	 * 
	 * @param gcNum：用户GC号
	 */
	public void uploadQR(String gcNum) {
		String relativelyPath = "";
		relativelyPath = WUserHandler.class.getClassLoader().getResource("/").getPath(); // 项目的根目录
		relativelyPath = relativelyPath.substring(1, relativelyPath.indexOf("webapps"));
		String path = relativelyPath + "webapps/iconHead"; // 头像存储路径
		File fi = new File(path);
		if (!fi.exists() && !fi.isFile()) {
			fi.mkdir();
		}
		String imgPath = path + "/QR" + gcNum + ".png";
		String encoderContent = "Welcome to Watchman!" + gcNum;
		QRCodeOP handler = new QRCodeOP();
		handler.encoderQRCode(encoderContent, imgPath, "png");
	}

	/**
	 * 添加好友
	 * 
	 * @param out
	 * @throws ParseException
	 */
	@RequestMapping(value = "addFriend", method = RequestMethod.POST)
	public void addFriend(@ModelAttribute(SessionAttribute.USERLOGIN) WUser user,
			@RequestParam("friendId") String friendId, PrintWriter out) throws ParseException {
		String fid = UUIDUtil.createUUID();
		String userId = user.getWGCNum();
		int result = friendShipService.addFriend(fid, userId, friendId, sdf.parse(sdf.format(new Date())));
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		jsonMap.put("result", result);
		if (result > 0) {
			jsonMap.put("desc", "添加好友成功");
		} else {
			jsonMap.put("desc", "添加好友失败");
		}
		Gson gson = new Gson();
		out.print(gson.toJson(jsonMap));
		out.flush();
		out.close();
	}

	/**
	 * 获取用户好友列表
	 * 
	 * @param user：当前登陆用户
	 */
	@RequestMapping("/getFriendList")
	public void getFriendList(@ModelAttribute(SessionAttribute.USERLOGIN) WUser user, PrintWriter out) {
		String userid = user.getWGCNum();
		List<FriendList> list = friendShipService.getFriendList(user.getWGCNum());
		Map<String, Object> jsonMap = new HashMap<String, Object>();

		// 最热好友
		HotFriend hotFriend = friendShipService.getHotFriend(userid);
		// 好友最近点赞--公开消息
		HotPraise hotNew = friendShipService.hotPraiseNew(userid);
		// 好友最近点赞--热点消息
		HotPraise hotMessage = friendShipService.hotPraiseNew(userid);
		// 好友最近评论--公开消息
		HotComment hotCommentNew = friendShipService.hotCommentMessage(userid);
		// 好友最近评论--热点消息
		HotComment hotCommentMessage = friendShipService.hotCommentMessage(userid);
		List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();
		Map<String, Object> mapData = new HashMap<String, Object>();
		Date hotPoint = friendShipService.hotFriendMessage(userid);
		mapData.put("hotFriend", hotFriend);// 最热好友
		mapData.put("friendList", list);// 好友列表
		mapData.put("hotPoint", hotPoint);
		Date praiseDate, commentDate;
		if (hotNew != null && hotMessage != null && hotCommentNew != null && hotCommentMessage != null) {
			if (hotNew.getMpraise1().getTime() - hotMessage.getMpraise1().getTime() > 0) {
				praiseDate = hotNew.getMpraise1();
			} else {
				praiseDate = hotMessage.getMpraise1();
			}
			if (hotCommentNew.getMcreatedat().getTime() - hotCommentMessage.getMcreatedat().getTime() > 0) {
				commentDate = hotCommentNew.getMcreatedat();
			} else {
				commentDate = hotCommentMessage.getMcreatedat();
			}
			if (praiseDate.getTime() - commentDate.getTime() > 0) {
				mapData.put("closePraise", praiseDate);// 最近一次赞你
			} else {
				mapData.put("closeComment", praiseDate);// 最近一次评论了你
			}
		}
		if (hotNew == null && hotMessage != null) {
			mapData.put("closePraise", hotMessage.getMpraise1());// 最近一次赞你
		} else if (hotNew != null && hotMessage == null) {
			mapData.put("closePraise", hotNew.getMpraise1());// 最近一次赞你
		} else {
			mapData.put("closePraise", new HotPraise());// 最近一次赞你
		}
		if (hotCommentMessage == null && hotCommentNew != null) {
			mapData.put("closeComment", hotCommentNew.getMcreatedat());// 最近一次评论你
		} else if (hotCommentMessage != null && hotCommentNew == null) {
			mapData.put("closeComment", hotCommentMessage.getMcreatedat());// 最近一次评论你
		} else {
			mapData.put("closeComment", new HotComment());// 最近一次评论你
		}
		listMap.add(mapData);
		Gson gson = new Gson();
		if (null != list && list.size() > 0) {
			jsonMap.put("result", "1");
		} else {
			jsonMap.put("result", "0");
		}
		jsonMap.put("data", listMap);
		out.print(gson.toJson(jsonMap));
		out.flush();
		out.close();
	}
}
