package com.wild.handler.message;

import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.gson.Gson;
import com.wild.entity.user.WUser;
import com.wild.service.message.PraiseService;
import com.wild.service.user.FriendShipService;
import com.wild.utils.SessionAttribute;
import com.wild.utils.UUIDUtil;

@Controller
@RequestMapping("/praise")
public class PraiseHandler {

	@Autowired
	private PraiseService praiseService;

	@Autowired
	private FriendShipService friendShipService;

	/**
	 * 点赞
	 * 
	 * @param inId：要点赞的信息的GC号
	 * @param infoUser：发表该条信息的用户的GC号
	 * @param out
	 * @param session
	 */
	@RequestMapping(value ="/addPraise", method = RequestMethod.POST)
	public void addPraise(String typeId, String userId, PrintWriter out, HttpSession session) {
		WUser userLogin = (WUser) session.getAttribute(SessionAttribute.USERLOGIN);
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> map2 = new HashMap<String, Object>();
		Gson gson = new Gson();
		if (StringUtils.isNotBlank(typeId) && StringUtils.isNotBlank(userId)) {
			String result = String.valueOf(praiseService.addPraise(UUIDUtil.createUUID(), userLogin, typeId,new Date()));
			if (StringUtils.isNotBlank(result)) {
				friendShipService.updateHotNum(userLogin.getWGCNum(), userId);// 更新该好友的热度
				map.put("result", result+"");
				map.put("desc", "点赞成功");
				map.put("data", map2);
				out.print(gson.toJson(map));
			} else {
				map.put("result", "0");
				map.put("desc", "点赞失败");
				map.put("data", map2);
				out.print(gson.toJson(map));
			}
		} else {
			map.put("result", "0");
			map.put("desc", "点赞失败");
			map.put("data", map2);
			out.print(gson.toJson(map));
		}
		out.flush();
		out.close();
	}
}
