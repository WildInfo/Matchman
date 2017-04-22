package com.wild.utils;

import org.apache.logging.log4j.LogManager;

import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest;
import com.taobao.api.response.AlibabaAliqinFcSmsNumSendResponse;

public class WatchmanMessage {
	/*public static void main(String[] args) {
		CouldMessageContent("15197462033", getCharAndNumr(4));
	}*/

	public boolean CouldMessageContent(String tel, String num) {
		// 申请的端口 公共参数 TOP分配给应用的AppKey。
		TaobaoClient client = new DefaultTaobaoClient("http://gw.api.taobao.com/router/rest", "23693002",
				"d9e0fd532d40aa7e7cb13512ecbd3f79");
		AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
		req.setExtend("123456");// 使用用户类型
		req.setSmsType("normal");// 必须是normal
		req.setSmsFreeSignName("登录验证");// 短信模版签名
		req.setSmsParamString("{\"code\":\"：" + num + "\",\"product\":\"GC\"}");// 发送内容
		req.setRecNum(tel);// 接收手机号码,可以是多个手机号码，用,隔开
		req.setSmsTemplateCode("SMS_53840152");// 申请的短信模版
		AlibabaAliqinFcSmsNumSendResponse rsp ;
		try {
			rsp = client.execute(req);
			LogManager.getLogger().debug("短信发送情况为：" + rsp.getBody());
			if(rsp.isSuccess()){
				return true;
			}
		} catch (ApiException e) {
			e.printStackTrace();
		}
		
		return false;
	}

	/**
	 * java生成随机数字和字母组合
	 * 
	 * @param length[生成随机数的长度]
	 * @return
	 */
	/*public static String getCharAndNumr(int length) {
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
	}*/
}
