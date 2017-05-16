package com.wild.handler.message;

import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.gson.Gson;
import com.wild.entity.message.IInformation;
import com.wild.entity.message.MComment;
import com.wild.entity.message.MMessageCommentRelation;
import com.wild.entity.user.FriendShip;
import com.wild.entity.user.LastOccur;
import com.wild.entity.user.WUser;
import com.wild.enums.message.MessageTypeEnum;
import com.wild.service.message.InformationService;
import com.wild.service.message.MCommentService;
import com.wild.service.user.FriendShipService;
import com.wild.service.user.LastOccurService;
import com.wild.utils.SessionAttribute;
import com.wild.utils.UUIDUtil;

@Controller
@RequestMapping("/information")
public class InformationHandler {
	@Autowired
	private InformationService informationService;

	@Autowired
	private MCommentService mCommentService;

	@Autowired
	private FriendShipService friendShipService;
	
	@Autowired
	private LastOccurService lastOccurService;
	

	/**
	 * 查询公开信息
	 * 
	 * @param map
	 * @param out
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/getInfos", method = RequestMethod.POST)
	public void getPublicNews(ModelMap map,String address, PrintWriter out, HttpServletRequest request,
				HttpServletResponse response) {
		List<IInformation> infos = informationService.getPublicNews(address);
		System.out.println(infos.get(0));
		Gson gson = new Gson();
		if(null != infos && infos.size()>0){
			out.print(gson.toJson(infos));
		}else{
			out.print("22222222222");
		}
		out.flush();
		out.close();
	}

	/**
	 * 插入公开信息
	 */
	@RequestMapping(value="/insertInfo",method=RequestMethod.POST)
	public void insertInfo(ModelMap map,IInformation info,PrintWriter out,HttpServletRequest request,HttpSession session){
     	String id = UUIDUtil.createUUID();
		WUser user = (WUser) session.getAttribute(SessionAttribute.USERLOGIN);
		String userid = user.getWGCNum();
		info.setIID(id);
		info.setIUserId(userid);
		info.setIDate(new Date());
		int result = informationService.insertPublicNews(info);
		Map<String, Object> json = new HashMap<String, Object>();
		Gson gson = new Gson();
		json.put("result", result+"");
		if (result > 0) {
			json.put("desc", "发布消息成功");
			
			//更新用户最近出现的动态begin:
			/*LastOccur lo = lastOccurService.selectLastOccur(user.getWGCNum());
			if(null==lo){//如果lastoccur中不存在该用户最近动态，则添加
				lo = new LastOccur(UUIDUtil.createUUID(), user.getWGCNum(), 
						new Date(), info.getIAdress());
				lastOccurService.insertLastOccur(lo);
			}else{//否则更新该用户的最新动态
				lo = new LastOccur(UUIDUtil.createUUID(), user.getWGCNum(), 
						new Date(), info.getIAdress());
				lastOccurService.updateLastOccur(lo);
			}*/
			//end
		}else{
			json.put("desc", "发布消息失败");
		}
		out.print(gson.toJson(json));
		out.flush();
		out.close();
	}


	/**
	 * 查询评论
	 */
	@RequestMapping(value = "/getComments", method = RequestMethod.POST)
	public void getComments(@RequestParam("iid") String iid, PrintWriter out, HttpServletRequest request) {
		IInformation information = new IInformation();
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> map2 = new HashMap<String, Object>();
		Gson gson = new Gson();
		if (StringUtils.isNotBlank(iid)) {
			information.setIID(iid);
			List<MComment> infos = mCommentService.getComments(information);
			map.put("result", "1");
			map.put("desc", "查看成功");
			map2.put("commentinfo", infos);
			map.put("data", map2);
			out.print(gson.toJson(map));
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
	@RequestMapping(value = "/insertComment", method = RequestMethod.POST)
	public void insertComment(ModelMap map, PrintWriter out, HttpServletRequest request) {
		String publishUser = request.getParameter("publishUser");// 发布评论的那个用户的gc号
		String targetUser = request.getParameter("targetUser");// 被评论的那个用户的gc号
		String content = request.getParameter("content");// 评论内容
		String parent = request.getParameter("parent");// 被评论的消息ID
		MComment comment = new MComment(UUIDUtil.createUUID(), publishUser, targetUser, content, 0,
				new Date(), parent, MessageTypeEnum.infomation, "", "", "");
		int result = mCommentService.insertComment(comment);
		MMessageCommentRelation mcr = new MMessageCommentRelation(UUIDUtil.createUUID(), parent, "", comment.getMOwnerUser(), targetUser,
				comment.getMID());
		Map<String, Object> json = new HashMap<String, Object>();
		Map<String, Object> map2 = new HashMap<String, Object>();
		Gson gson = new Gson();
		json.put("result", result+"");
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

	/**
	 * 获取信息详情
	 * 
	 * @param map
	 * @param out
	 * @param request
	 */
	@RequestMapping(value = "/getInfoDetail", method = RequestMethod.POST)
	public void getInfoDetail(ModelMap map, PrintWriter out, HttpServletRequest request, HttpSession session) {
		WUser user = (WUser) session.getAttribute(SessionAttribute.USERLOGIN); // 当前登陆用户
		Map<String, Object> json = new HashMap<String, Object>();
		Gson gson = new Gson();
		String iid = request.getParameter("IID"); // 消息ID
		String userid = request.getParameter("IUserId"); // 发消息的用户ID
		List<FriendShip> friends = friendShipService.getFriends(userid); 
		IInformation information = new IInformation();
		information.setIID(iid);
		information.setIUserId(userid);
		// 判断当前用户是否是要查看的信息的用户的好友
		boolean isFriend = false;
		List<IInformation> infos;
		for (FriendShip fs : friends) {
			if (fs.getFKFriendID().equals(user.getWGCNum())) {
				isFriend = true;
				break;
			}
		}

		if (userid.equals(user.getWGCNum()) || isFriend) {// 该条信息是否是当前用户发送的或查看的用户是该条消息的好友
			infos = informationService.getInfoDetails(information);
			json.put("result", "1");
			json.put("desc", "查看成功");
			json.put("data", infos);
		} else {// 说明是陌生人
			infos = informationService.getInfoDetailsByStrenger(information);
			json.put("result", "1");
			json.put("desc", "查看成功");
			json.put("data", infos);
		}
		out.print(gson.toJson(json));
		out.flush();
		out.close();
	}

}
