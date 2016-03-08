package com.stock.common;

import java.io.Serializable;
import java.util.Date;
/**
 * @ClassName: SmsResultVo 
 * @Description: 梦网短信通道上行VO （"2014-12-12,13:48:41,15016711234,106571203838761,*,Ghana"）
 * @author lcj
 * @date 2014-12-12 下午02:24:57
 *
 */
public class SmsResultVo implements Serializable {
	private static final long serialVersionUID = -29058347976404808L;
	//
	/**用户回复时间**/
	private Date time;
	/**手机号码**/
	private String mobilePhone;
	/**公司序列**/
	private String companyId;
	/**短信内容**/
	private String content;
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	public String getMobilePhone() {
		return mobilePhone;
	}
	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}
	public String getCompanyId() {
		return companyId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	 
	
}


