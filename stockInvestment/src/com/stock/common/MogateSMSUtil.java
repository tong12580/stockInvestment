package com.stock.common;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * 短信接口，对短信接口地址进行拼接，提供公用
 * 
 * @author Administrator
 * 
 */
public class MogateSMSUtil {
	
	public static final Map<String, String> mongateRetMap = new HashMap<String, String>(){
        /**
		 * 
		 */
		private static final long serialVersionUID = 8334532411908286554L;

		{
            put("-1", "参数为空。信息、电话号码等有空指针，登陆失败");
            put("-2", "电话号码个数超过100");
            put("-10", "申请缓存空间失败");
            put("-11", "电话号码中有非数字字符");
            put("-12", "有异常电话号码");
            put("-13", "电话号码个数与实际个数不相等");
            put("-14", "实际号码个数超过100");
            put("-101", "发送消息等待超时");
            put("-102", "发送或接收消息失败");
            put("-103", "接收消息超时");
            put("-200", "其他错误");
            put("-999", "web服务器内部错误");
            put("-10001", "用户登陆不成功");
            put("-10002", "提交格式不正确");
            put("-10003", "用户余额不足");
            put("-10004", "手机号码不正确");
            put("-10005", "计费用户帐号错误");
            put("-10006", "计费用户密码错");
            put("-10007", "账号已经被停用");
            put("-10008", "账号类型不支持该功能");
            put("-10009", "其它错误");
            put("-10010", "企业代码不正确");
            put("-10011", "信息内容超长");
            put("-10012", "不能发送联通号码");
            put("-10013", "操作员权限不够");
            put("-10014", "费率代码不正确");
            put("-10015", "服务器繁忙");
            put("-10016", "企业权限不够");
            put("-10017", "此时间段不允许发送");
            put("-10018", "经销商用户名或密码错");
            put("-10019", "手机列表或规则错误");
            put("-10021", "没有开停户权限");
            put("-10022", "没有转换用户类型的权限");
            put("-10023", "没有修改用户所属经销商的权限、可能是定时时间格式错误");
            put("-10024", "经销商用户名或密码错");
            put("-10025", "操作员登陆名或密码错误");
            put("-10026", "操作员所充值的用户不存在");
            put("-10027", "操作员没有充值商务版的权限");
            put("-10028", "该用户没有转正不能充值");
            put("-10029", "此用户没有权限从此通道发送信息(如果是EMP接入，可能是没加相应号码的号段导致的、或者是绑定通道路由有误，注意号码是不是合法的位数对不对)");
            put("-10030", "不能发送移动号码");
            put("-10031", "手机号码(段)非法");
            put("-10032", "用户使用的费率代码错误");
            put("-10033", "非法关键词");
            put("-10057", "网关有IP限制配置");
            
        }
    };

	/**
	 * 获取短信接口地址
	 * 
	 * @param url
	 *            短信接口地址 url
	 * @param UserID
	 *            参数 用户名或账号
	 * @param Account
	 *            参数 子账号
	 * @param code
	 *            验证码
	 * @param password
	 *            参数 密码
	 * @param content
	 *            内容
	 * @param phones
	 *            参数 电话号码，多个用；隔开
	 * @param suffix
	 *            参数 参数 后缀参数
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public static String getSMSPort(String url, String UserID, String Account,
			 String password,String code, String content, String phones,
			String suffix) throws UnsupportedEncodingException {
		content = URLEncoder.encode(content, "UTF-8");
		StringBuffer buffer = new StringBuffer();
		buffer.append(url);
		buffer.append("?UserID=");
		buffer.append(UserID);
		buffer.append("&Account=");
		buffer.append(Account);
		buffer.append("&Password=");
		buffer.append(password);
		buffer.append("&Phones=");
		buffer.append(phones);
		buffer.append("&Content=");
		buffer.append(content);
		if (suffix == null) {
			buffer.append("&SendTime=&SendType=1&PostFixNumber=1");
		} else {
			buffer.append(suffix);
		}

		return buffer.toString();
	}

	/**
	 * 发送短信
	 * 
	 * @param url
	 *            地址 总地址
	 * @return  成功为Sucess。  失败为null
	 * @throws DocumentException
	 */
	public static String sendSMS(String url) {
		String retCode=null;
		try {
			URL send = new  URL(url);
			InputStream is = send.openStream();
			SAXReader reader = new SAXReader();
			Document doc = reader.read(is);
			Element root = doc.getRootElement();
			retCode = root.elementText("RetCode");
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return retCode;

	}
	
	public static Map<String,String> commMessage(String url){
		Map<String,String> map = new HashMap<String, String>();
		map.put("code", "-1");
		map.put("desc", "发送失败");
		
		try {
			URL send = new  URL(url);
			InputStream is = send.openStream();
			SAXReader reader = new SAXReader();
			Document doc = reader.read(is);
			Element root = doc.getRootElement();
			map.put("code", root.elementText("code")+"");
			map.put("desc", root.elementText("msg")+"");
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return map;
		
	}
	
	/**
	 * 深圳梦网短信通道发送短信
	 * 
	 * @param url 总地址
	 * @return 返回结果中string节点的值
	 * @author tao.qin
	 * @throws DocumentException
	 */
	public static Map<String,String> sendMongateSMS(String url) {
		Map<String,String> map = new HashMap<String, String>();
		try {
			URL send = new  URL(url);
			InputStream is = send.openStream();
			SAXReader reader = new SAXReader();
			Document doc = reader.read(is);
			Element root = doc.getRootElement();
			String retCode = root.getTextTrim();
			map.put("retCode", retCode);
			//如果返回码大于10位小于25位为提交成功
			if(retCode.length()>10&&retCode.length()<25){
				map.put("code", "success");
				map.put("desc", "提交成功");
			}else{
				map.put("code", retCode);
				map.put("desc", mongateRetMap.get(retCode));
			}
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;

	}
	
	/**
	 * @Title: sendUrl
	 * @Description: 梦网返回上行
	 * @author lcj
	 * @date 2014-12-12 上午11:36:52
	 * @param @param url
	 * @param @return
	 * @return String[]
	 * @throws  Exception 
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List<SmsResultVo> sendUrl(String url) throws  Exception{

		URL send = new URL(url);
		InputStream is = send.openStream();
		SAXReader reader = new SAXReader();
		Document doc = reader.read(is);
		List<String> Lists = new ArrayList<String>();
		String[] result = null;/* {
				"2014-12-12,13:48:41,15016718007,106571203838761,*,yes",
				"2014-12-12,13:48:41,15016718007,106571203838761,*,Bnmc" };*/ 
		Element el = doc.getRootElement();
		Iterator it = el.elementIterator();
		while (it.hasNext()) // 获得所有用户回复信息
		{
			Element elm = (Element) it.next();
			Lists.add(elm.getText());
		}
		if (Lists != null && Lists.size() > 0) {
			result = new String[Lists.size()];
			for (int i = 0; i < Lists.size(); i++) {
				result[i] = Lists.get(i);
			}
		}
		
		List<SmsResultVo> list = new ArrayList();
		if (result == null || result.length == 0)
			return null;//内容为空返回NULL
		for (int i = 0; i < result.length; i++) { // 把返回结果封装成对象
			String arr[] = result[i].split(",");
			SmsResultVo smsResultVo = new SmsResultVo();
			smsResultVo.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
					.parse(arr[0] + " " + arr[1]));
			smsResultVo.setMobilePhone(arr[2]);
			smsResultVo.setCompanyId(arr[3]);
			smsResultVo.setContent(arr[5]);
			list.add(smsResultVo);
		}
        
		return list;
	}
	
	/**
	 * 
	 * Title: sendCassSMS
	 * Description:单元科技发送短信
	 * @param @param url
	 * @param @return 设定文件 
	 * @return  Map<String,String> 返回类型 
	 * @throws
	 */
	public static Map<String,String> sendCassSMS(String url) {
		Map<String,String> map = new HashMap<String, String>();
		try {
			URL send = new  URL(url);
			InputStream is = send.openStream();
			SAXReader reader = new SAXReader();
			Document doc = reader.read(is);
			Element root = doc.getRootElement();
			String result = root.attributeValue("return");
			String info = root.attributeValue("info");
			String msgid = root.attributeValue("msgid");
			String numbers = root.attributeValue("numbers");
			String messages = root.attributeValue("messages");
			map.put("result", result);	
			map.put("info", info);
			map.put("msgid", msgid);	
			map.put("numbers", numbers);	
			map.put("messages", messages);
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	
	@SuppressWarnings("unused")
	public static void main(String[] args) throws IOException {
		String mobile = "1868875735";
		String smsContent="zhaolong";
		String uri = "http://183.232.132.142/msg/HttpSendSM";//应用地址
		String account = "RRJC-xj09588";//账号
		String pswd = "WaFj199nxg";//密码	
		StringBuffer sb = new StringBuffer();
		boolean needstatus = true;//是否需要状态报告，需要true，不需要false
		 smsContent = URLEncoder.encode(smsContent, "utf-8");
		String URL = uri+"&account="+account+"&pswd="+pswd+"&msg="+smsContent+"&mobile="+mobile+"&needstatus=true";
		
		try {
			URL send = new  URL(URL);
			InputStream is = send.openStream();
			SAXReader reader = new SAXReader();
			Document doc = reader.read(is);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
