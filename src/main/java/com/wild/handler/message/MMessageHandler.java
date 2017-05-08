package com.wild.handler.message;

import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.gson.Gson;
import com.wild.entity.message.IInformation;
import com.wild.entity.message.MComment;
import com.wild.entity.message.MMessage;
import com.wild.entity.message.MMessageCommentRelation;
import com.wild.entity.user.LastOccur;
import com.wild.entity.user.WDetails;
import com.wild.entity.user.WUser;
import com.wild.entity.user.WUserDetailsRelation;
import com.wild.enums.message.GetStatusEnum;
import com.wild.enums.message.MessageTypeEnum;
import com.wild.enums.message.StatusEnum;
import com.wild.service.message.MCommentService;
import com.wild.service.user.FriendShipService;
import com.wild.service.user.LastOccurService;
import com.wild.service.user.WUserService;
import com.wild.utils.SessionAttribute;
import com.wild.utils.UUIDUtil;

/**
 * 热点消息
 * 
 * @author Wild
 *
 */
@Controller
@RequestMapping("/mmessage")
public class MMessageHandler {
	@Autowired
	private MCommentService commentService;

	@Autowired
	private WUserService userService;

	@Autowired
	private LastOccurService lastOccurService;
	
	private SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");

	@Autowired
	private MCommentService mCommentService;

	@Autowired
	private FriendShipService friendShipService;

	/**
	 * 发布热点
	 * 
	 * @param out
	 * @param session
	 * @param request
	 */
	@RequestMapping(value="/insertMessage", method = RequestMethod.POST)
	public void insertMessage(PrintWriter out, HttpSession session, HttpServletRequest request) {
		Gson gson = new Gson();
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> map2 = new HashMap<String, Object>();
		String tokenId = request.getParameter("tokenId");// 用户id
		String currency = request.getParameter("currency");// 游戏币数量
		String adress = request.getParameter("adress");// 热点地址
		String contents = "";// 热点内容
		try {
			contents = new String(request.getParameter("contents").getBytes("ISO-8859-1"), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		if (StringUtils.isNotBlank(tokenId) && StringUtils.isNotBlank(currency) && StringUtils.isNotBlank(contents)
				&& StringUtils.isNotBlank(adress)) {
			List<WUser> userDetils = userService.userDetils(tokenId);// 根据用户id查询用户详细
			MMessage message = new MMessage();

			message.setMID(UUIDUtil.createUUID());
			message.setMContent(contents);
			message.setMGrade(Integer.parseInt(currency));
			message.setMDate(new Date());
			message.setMAdress(adress);
			message.setMGetStatus(GetStatusEnum.unreceived);
			message.setMStatus(StatusEnum.normal);
			message.setMUserGC(userDetils.get(0).getWGCNum());// 存入用户GC号

			int messageResult = commentService.insertMessage(message);// 插入热点
			MMessageCommentRelation commentRelation = new MMessageCommentRelation(UUIDUtil.createUUID(), null,
					message.getMID(), tokenId, null, null);
			int commentRelationResult = commentService.insertIMC(commentRelation);// 添加热点与用户关系
			if ((messageResult > 0) && (commentRelationResult > 0)) {
				MMessage messageJson = commentService.selectMessage(message);// 查询热点消息

				map2.put("messageinfo", messageJson);
				map2.put("tokenId", message.getMID());
				map.put("result", "1");
				map.put("desc", "添加成功!");
				map.put("data", map2);

				// 更新用户最近出现的动态begin:
				LastOccur lo = lastOccurService.selectLastOccur(userDetils.get(0).getWGCNum());
				try {
					if(null==lo){//如果lastoccur中不存在该用户最近动态，则添加
						lo = new LastOccur(UUIDUtil.createUUID(), userDetils.get(0).getWGCNum(), 
								sdf.parse(sdf.format(new Date())), adress);
						lastOccurService.insertLastOccur(lo);
					}else{//否则更新该用户的最新动态
						lo = new LastOccur(UUIDUtil.createUUID(), userDetils.get(0).getWGCNum(), 
								sdf.parse(sdf.format(new Date())), adress);
						lastOccurService.updateLastOccur(lo);
					}
				}catch (ParseException e) {
					e.printStackTrace();
					out.println(gson.toJson(map));
					out.flush();
					out.close();
				}
			} else {
				map.put("result", "0");
				map.put("desc", "添加失败!");
				map.put("data", map2);

				out.println(gson.toJson(map));
				out.flush();
				out.close();
			}
		}
	}

	/**
	 * 查询游戏币数量
	 * 
	 * @param out
	 * @param session
	 * @param request
	 */
	@RequestMapping(value="/selectCurrey", method = RequestMethod.POST)
	public void selectCurrey(PrintWriter out, HttpSession session, HttpServletRequest request) {
		Gson gson = new Gson();
		WUserDetailsRelation detailsRelation = new WUserDetailsRelation();
		WDetails details = new WDetails();
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> map2 = new HashMap<String, Object>();
		String tokenId = request.getParameter("tokenId");// 用户id
		if (StringUtils.isNotBlank(tokenId)) {
			detailsRelation.setWKUserID(tokenId);
		}

		List<WUserDetailsRelation> relation = userService.userDetilsById(detailsRelation);// 根据用户id查询出详情
		if (relation.size() > 0) {
			details.setWID(relation.get(0).getWKDetailsID());// 用户详细id

			List<WDetails> updetails = userService.selectDetils(details);
			if (updetails.size() > 0) {

				map2.put("currency", updetails.get(0).getWCurrency());

				map.put("result", "1");
				map.put("desc", "查询成功!");
				map.put("data", map2);

				out.println(gson.toJson(map));
				out.flush();
				out.close();
			} else {
				map.put("result", "0");
				map.put("desc", "查询失败!");
				map.put("data", map2);

				out.println(gson.toJson(map));
				out.flush();
				out.close();
			}
		} else {
			map.put("result", "0");
			map.put("desc", "查询失败!");
			map.put("data", map2);

			out.println(gson.toJson(map));
			out.flush();
			out.close();
		}
	}

	/**
	 * 根据用户查看消息列表
	 * 
	 * @param out
	 * @param session
	 * @param request
	 */
	@RequestMapping(value="/messageUser", method = RequestMethod.POST)
	public void messageRelation(PrintWriter out, HttpSession session, HttpServletRequest request) {
		Gson gson = new Gson();
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> map2 = new HashMap<String, Object>();
		MMessageCommentRelation messageRelation = new MMessageCommentRelation();
		String tokenId = request.getParameter("tokenId");// 用户id
		if (StringUtils.isNotBlank(tokenId)) {
			messageRelation.setMKUserID(tokenId);
			List<MMessageCommentRelation> messageUser = commentService.messageRelation(messageRelation);// 添加热点与用户关系
			if ((messageUser.size() > 0)) {
				for (int i = 0; i < messageUser.size(); i++) {
					MMessage message = new MMessage();
					message.setMID(messageUser.get(i).getMKMessageID());
					MMessage messageDetails = commentService.selectMessage(message);// 根据热点id查询热点
					map2.put("message" + i + "", messageDetails);
				}
				map.put("result", "1");
				map.put("desc", "查询成功!");
				map.put("data", map2);

				out.println(gson.toJson(map));
				out.flush();
				out.close();
			} else {
				map.put("result", "0");
				map.put("desc", "查询失败!");
				map.put("data", map2);

				out.println(gson.toJson(map));
				out.flush();
				out.close();
			}
		}
	}

	/**
	 * 显示热点详情
	 * 
	 * @param out
	 * @param session
	 * @param request
	 */
	@RequestMapping(value="/deDailsMessage", method = RequestMethod.POST)
	public void deDailsMessage(PrintWriter out, HttpSession session, HttpServletRequest request) {
		Gson gson = new Gson();
		MMessage message = new MMessage();
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> map2 = new HashMap<String, Object>();
		String tokenId = request.getParameter("tokenId");// 热点id
		if (StringUtils.isNotBlank(tokenId)) {
			message.setMID(tokenId);
			MMessage messageResult = commentService.selectMessage(message);
			if (null != messageResult) {
				MMessage messageJson = commentService.selectMessage(message);// 查询热点消息

				map2.put("messageinfo", messageJson);
				map2.put("tokenId", message.getMID());

				map.put("result", "1");
				map.put("desc", "查询成功!");
				map.put("data", map2);

				out.println(gson.toJson(map));
				out.flush();
				out.close();
			} else {
				map.put("result", "0");
				map.put("desc", "查询失败!");
				map.put("data", map2);

				out.println(gson.toJson(map));
				out.flush();
				out.close();
			}
		}
	}

	/**
	 * 领取热点
	 * 
	 * @param out
	 * @param session
	 * @param request
	 */
	@RequestMapping(value="/recviedMessage", method = RequestMethod.POST)
	public void recviedMessage(PrintWriter out, HttpSession session, HttpServletRequest request) {
		Gson gson = new Gson();
		MMessage message = new MMessage();
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> map2 = new HashMap<String, Object>();
		String tokenId = request.getParameter("tokenId");// 用户id
		String messageid = request.getParameter("messageid");// 热点id
		if (StringUtils.isNotBlank(tokenId) && StringUtils.isNotBlank(messageid)) {
			List<WUser> userDetils = userService.userDetils(tokenId);// 根据用户id查询用户详细
			message.setMID(messageid);
			message.setMGetUser(userDetils.get(0).getWGCNum());
			message.setMGetStatus(GetStatusEnum.received);// 将热点状态改为收到
			int messageResult = commentService.updateMessageRev(message);
			if (messageResult > 0) {
				MMessage messageJson = commentService.selectMessage(message);// 查询热点消息

				map2.put("messageinfo", messageJson);
				map2.put("tokenId", message.getMID());

				map.put("result", "1");
				map.put("desc", "更新成功!");
				map.put("data", map2);

				out.println(gson.toJson(map));
				out.flush();
				out.close();
			} else {
				map.put("result", "0");
				map.put("desc", "更新失败!");
				map.put("data", map2);

				out.println(gson.toJson(map));
				out.flush();
				out.close();
			}
		}
	}

	/**
	 * 查询评论
	 */
	@RequestMapping(value = "/getMessageComments", method = RequestMethod.POST)
	public void getMessageComments(@RequestParam("mid") String mid, PrintWriter out, HttpServletRequest request) {
		MMessage message = new MMessage();
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> map2 = new HashMap<String, Object>();
		Gson gson = new Gson();
		if (StringUtils.isNotBlank(mid)) {
			message.setMID(mid);
			List<MComment> infos = mCommentService.getMessageComments(message);
			if (infos.size() > 0) {
				map.put("result", "1");
				map.put("desc", "查看成功");
				map2.put("commentinfo", infos);
				map.put("data", map2);
			} else
				map.put("result", "0");
			map.put("desc", "查看失败!");
			map.put("data", map2);
			out.println(gson.toJson(map));
		} else {
			map.put("result", "0");
			map.put("desc", "查看失败!");
			map.put("data", map2);
			out.println(gson.toJson(map));
		}
		out.flush();
		out.close();
	}

	/**
	 * 插入评论
	 * 
	 * @param map
	 * @param out
	 */
	@RequestMapping(value = "/insertMessageComment", method = RequestMethod.POST)
	public void insertMessageComment(PrintWriter out, HttpServletRequest request) {
		String publishUser = request.getParameter("publishUser");// 发布评论的那个用户的gc号
		String targetUser = request.getParameter("targetUser");// 被评论的那个用户的gc号
		String parent = request.getParameter("parent");// 被评论的热点ID
		String content = "";// 评论内容
		try {
			content = new String(request.getParameter("content").getBytes("ISO-8859-1"), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		MComment comment = new MComment(UUIDUtil.createUUID(), publishUser, targetUser, content, 0, new Date(), parent,
				MessageTypeEnum.message, "", "", "");
		int result = mCommentService.insertComment(comment);
		MMessageCommentRelation mcr = new MMessageCommentRelation(UUIDUtil.createUUID(), "", parent,
				comment.getMOwnerUser(), targetUser, comment.getMID());
		Map<String, Object> json = new HashMap<String, Object>();
		Map<String, Object> map2 = new HashMap<String, Object>();
		Gson gson = new Gson();
		json.put("result", result + "");
		if (result > 0) {// 说明评论成功
			friendShipService.updateHotNum(targetUser, publishUser);// 更新该好友的热度
			int r = mCommentService.insertIMC(mcr);// 插入消息评论关系表
			if (r > 0) {
				json.put("desc", "评论成功");
				json.put("data", map2);
			} else
				json.put("desc", "评论失败");
			json.put("data", map2);

		} else {
			json.put("desc", "评论失败");
			json.put("data", map2);
		}
		out.println(gson.toJson(json));
		out.flush();
		out.close();
	}
	@RequestMapping("/getNAndM")
	public void getNAndM(@ModelAttribute(SessionAttribute.USERLOGIN)WUser user,PrintWriter out){
		String userid = user.getWGCNum();//当前用户GC号
		List<IInformation> infos = mCommentService.getUserNews(userid);//当前用户发表的公开信息
		List<MMessage> mess = mCommentService.getUserMessages(userid);//当前用户发表的热点信息
		Map<String,Object> mapData = new HashMap<String,Object>();
		Map<String,Object> jsonMap = new HashMap<String,Object>();
		Gson gson = new Gson();
		jsonMap.put("result", 1);
		if(null != infos && infos.size()>0){
			mapData.put("infoNum", infos.size());//发表的公开消息数量
			mapData.put("infos", infos);
		}else{
			mapData.put("infoNum", 0);//发表的公开消息数量
			mapData.put("infos", new IInformation());
		}
		if(null != mess && mess.size()>0){
			mapData.put("messNum", mess.size());//发布的热点数量
			mapData.put("mess", mess);
		}else{
			mapData.put("messNum", 0);//发布的热点数量
			mapData.put("mess", new MMessage());
		}
		jsonMap.put("data", mapData);
		out.print(gson.toJson(jsonMap));
		out.flush();
		out.close();
		
	}

	/**
	 * 与我相关的热点任务
	 * 
	 * @param out
	 * @param request
	 */
	@RequestMapping(value = "/selectWithMessage", method = RequestMethod.POST)
	public void selectWithMessage(PrintWriter out, HttpServletRequest request) {
		String tokenId = request.getParameter("tokenId");// 用户id
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> map2 = new HashMap<String, Object>();
		Gson gson = new Gson();
		if (StringUtils.isNotBlank(tokenId)) {
			List<MMessage> result = mCommentService.selectWithMessage(tokenId);
			if (result.size() > 0) {// 说明评论成功
				map.put("result", "1");
				map2.put("messageinfo", result);
				map.put("desc", "查看成功");
				map.put("data", map2);
			} else {
				map.put("result", "0");
				map.put("desc", "查看失败");
				map.put("data", map2);
			}
			out.println(gson.toJson(map));
			out.flush();
			out.close();
		}
	}

	/**
	 * 查看与我相关的热点任务数量
	 * 
	 * @param out
	 * @param request
	 */
	@RequestMapping(value = "/selectCountMessage", method = RequestMethod.POST)
	public void selectCountMessage(PrintWriter out, HttpServletRequest request) {
		String tokenId = request.getParameter("tokenId");// 用户id
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> map2 = new HashMap<String, Object>();
		Gson gson = new Gson();
		if (StringUtils.isNotBlank(tokenId)) {
			int result = mCommentService.selectCountMessage(tokenId);
			if (result > 0) {// 说明评论成功
				map.put("result", "1");
				map2.put("messageinfo", result);
				map.put("desc", "查看成功");
				map.put("data", map2);
			} else {
				map.put("result", "0");
				map.put("desc", "查看失败");
				map.put("data", map2);
			}
			out.println(gson.toJson(map));
			out.flush();
			out.close();
		}
	}

	/**
	 * 查看前8条与我相关的有效消息
	 * 
	 * @param out
	 * @param request
	 */
	@RequestMapping(value = "/limitMessage", method = RequestMethod.POST)
	public void limitMessage(PrintWriter out, HttpServletRequest request) {
		String tokenId = request.getParameter("tokenId");// 用户id
		WUserDetailsRelation detailsRelation = new WUserDetailsRelation();
		WDetails details = new WDetails();
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> map2 = new HashMap<String, Object>();
		Gson gson = new Gson();
		if (StringUtils.isNotBlank(tokenId)) {
			detailsRelation.setWKUserID(tokenId);
			List<WUserDetailsRelation> detailsRelationList = userService.userDetilsById(detailsRelation);
			if (detailsRelationList.size() > 0) {
				details.setWID(detailsRelationList.get(0).getWKDetailsID());
				List<WDetails> detailsList = userService.selectDetils(details);

				if (detailsList.size() > 0) {
					List<MMessage> result = mCommentService.limitMessage(tokenId);
					if (result.size() > 0) {// 说明评论成功
						map.put("result", "1");
						map2.put("messageinfo", result);
						map2.put("images", detailsList.get(0).getWHeadImage());
						map.put("desc", "查看成功");
						map.put("data", map2);
					} else {
						map.put("result", "0");
						map.put("desc", "查看失败");
						map.put("data", map2);
					}
				} else {
					map.put("result", "0");
					map.put("desc", "查看失败");
					map.put("data", map2);
				}
			} else {
				map.put("result", "0");
				map.put("desc", "查看失败");
				map.put("data", map2);
			}
		} else {
			map.put("result", "0");
			map.put("desc", "查看失败");
			map.put("data", map2);
		}
		out.println(gson.toJson(map));
		out.flush();
		out.close();
	}
}
