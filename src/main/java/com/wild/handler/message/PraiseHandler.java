package com.wild.handler.message;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.wild.entity.user.WUser;
import com.wild.service.message.PraiseService;
import com.wild.utils.SessionAttribute;
import com.wild.utils.UUIDUtil;

@Controller
@RequestMapping("/praise")
public class PraiseHandler {
	
	@Autowired
	private PraiseService praiseService;
	
	@RequestMapping("/addPraise")
	public void addPraise(String inId,PrintWriter out,HttpSession session){
		WUser userLogin = (WUser)session.getAttribute(SessionAttribute.USERLOGIN);
		String result = String.valueOf(praiseService.addPraise(UUIDUtil.createUUID(),userLogin,inId));
		Map<String,String> map = new HashMap<String,String>();
		map.put("result",result);
		map.put("desc", "点赞成功");
		out.print(map);
		out.flush();
		out.close();
	}
}
