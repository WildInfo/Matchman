package com.wild.handler.message;

import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.wild.enums.StatusEnum;
import com.wild.service.message.InformationService;
import com.wild.service.message.MCommentService;

@Controller
@RequestMapping("/infomation")
public class InformationHandler {
	@Autowired
	private InformationService informationService;
	
	@Autowired
	private MCommentService mCommentService;
	
	private SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
	
	/**
	 * 查询公开信息
	 * @param map
	 * @param out
	 * @param request
	 * @param response
	 */
	@RequestMapping("/getInfos")
	public void getPublicNews(ModelMap map,PrintWriter out,HttpServletRequest request,HttpServletResponse response){
		List<IInformation> infos = informationService.getPublicNews("湖南工学院");
		Gson gson = new Gson();
		out.print(gson.toJson(infos));
		out.flush();
		out.close();
	}
	
	/**
	 * 插入公开信息
	 */
	@RequestMapping("/insertInfo")
	public void insertPublicNews(){
		
	}
	
	/**
	 * 查询评论
	 */
	@RequestMapping(value="/getComments",method=RequestMethod.GET)
	public void getComments(@RequestParam("iid")String iid, ModelMap map,PrintWriter out,HttpServletRequest request){
		IInformation information = new IInformation();
		information.setIID(iid);
		List<MComment> infos = mCommentService.getComments(information);
		out.print(infos);
		out.flush();
		out.close();
	}
	
	/**
	 * 插入评论
	 * @param map
	 * @param out
	 */
	@RequestMapping(value="/insertComment",method=RequestMethod.GET)
	public void insertComment(ModelMap map,PrintWriter out,HttpServletRequest request){
		String publishUser = request.getParameter("publishUser");
		String targetUser = request.getParameter("targetUser");
		String content = request.getParameter("content");
		String parent = request.getParameter("parent");
		String parentType = request.getParameter("parentType");
		try {
			MComment comment = new MComment("", publishUser, targetUser, content, 0, 
						sdf.parse(sdf.format(new Date())), parent, parentType, "", "", "");
			int result = mCommentService.insertComment(comment);
			MMessageCommentRelation mcr = new MMessageCommentRelation("", parent, "", comment.getMOwnerUser(), comment.getMID());
			Map<String, Object> json = new HashMap<String, Object>();
			Gson gson = new Gson();
			json.put("result", result);
			if(result>0){
				int r = mCommentService.insertIMC(mcr);
				if(r>0)
					json.put("desc", "插入评论成功");
				else
					json.put("desc", "插入评论失败");
			}else{
				json.put("desc", "插入评论失败");
			}
			out.println(gson.toJson(json));
			out.flush();
			out.close();
		} catch (ParseException e) {
			e.printStackTrace();
		}	
	}
}
