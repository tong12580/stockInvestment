package com.stock.common.util.response;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.stock.common.FastjsonUtil;
import com.stock.common.util.response.IDefineMsg;
import com.stock.common.util.response.IResult;
import com.stock.common.util.response.Result;
import com.stock.common.util.response.LogUtil;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

@SuppressWarnings({"rawtypes","unchecked"})
public class ServiceAction extends LogUtil{
	
	/**
	 * 判断对象是否为空
	 * @param obj
	 * @return boolean
	 */
  public static  boolean  isNull(Object obj)
  {
    if (obj == null) return true;

    if ((obj instanceof String)) {
      String strObj = valueOf(obj);
      return "".equals(strObj);
    }
    return false;
  }
  
  /**
	 * 字符是否为空或"null"
	 * @param str
	 * @return boolean
	 */
  public static boolean isEmpty(String str)
  {
    if (str == null) return true;
    if ("null".equalsIgnoreCase(str)) return true;
    if ("".equals(str)) return true;
    return false;
  }
  
  /**
	 * list是否为空
	 * @param list
	 * @return boolean
	 */
  public static boolean isEmpty(List<?> list)
  {
    if (list == null) return true;
    if (list.size() < 1) return true;
    if (list.isEmpty()) return true;
    return false;
  }

	/**
	 * map是否为空
	 * @param map
	 * @return boolean
	 */
  public static boolean isEmpty(Map<?,?> map)
  {
    if (map == null) return true;
    if (map.size() < 1) return true;
    if (map.isEmpty()) return true;
    return false;
  }
  
  /**
   * 遍历交易map中的空值
   * @param reqMap
   * @return IResult
   */
  public static IResult checkParamsEmpty(Map<String, Object> reqMap){
		
	  for(Map.Entry<String, Object> map : reqMap.entrySet()){
		  if(isNull(map.getValue()))
			  return makerErrResults(map.getKey()+IDefineMsg.LACK_PARAM);
	  }
	
		return makerSusResults(IDefineMsg.CHEACK_SUC);
  }  
  
  /**
   * json-->map并校验是否为空
   * @param json
   * @return {@link IResult} JosnMap<String,Object> 
   */
  public static IResult checkJsonEmpty(String json){
	  
	  Map<String, Object> reqMap	=FastjsonUtil.jsonChangeMap(json);
	  
	  for(Map.Entry<String, Object> map : reqMap.entrySet()){
		  if(isNull(map.getValue()))
			  return makerErrResults(map.getKey()+IDefineMsg.LACK_PARAM);
	  }
	
		return makerSusResults(IDefineMsg.CHEACK_SUC,reqMap);
  }
  
  
	/**
	 * 判断字符串是否只有数字及字母
	 * @param str
	 * @return
	 */
  public static boolean isNumChar(String str)
  {
    int len = str.length();
    for (int i = 0; i < len; i++) {
      char c = str.charAt(i);
      if (((c < 'a') || (c > 'z')) && ((c < 'A') || (c > 'Z')))
      {
        if (!Character.isDigit(c))
        {
          return false;
        }
      }
    }
    return true;
  }

	/**
	 * 判断是否为整数
	 * @param str
	 * @return
	 */
  public static boolean isInteger(String str)
  {
    try
    {
      Integer.parseInt(str);
      return true;
    }
    catch (NumberFormatException e) {
    }
    return false;
  }

	/**
	 * 判断是否为浮点数
	 * @param str
	 * @return
	 */
  public static boolean isDouble(String str)
  {
    try
    {
      Double.parseDouble(str);
      return true;
    }
    catch (NumberFormatException e) {
    }
    return false;
  }

	/**
	 * 判断是否为正确日期,
	 * @param str，格式为：yyyy-MM-dd HH:mm:ss
	 * @return
	 */
  public static boolean isDate(String str)
  {
    try
    {
      SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      Date d = f.parse(str);
      String s = f.format(d);
      return s.equals(str);
    }
    catch (Exception e) {
    }
    return false;
  }

	/**
	 * 转换对象为字符串类型
	 * @param obj
	 * @return 
	 */
  public  static String valueOf(Object obj)
  {
    if (obj == null) {
      return "";
    }
    if ((obj instanceof String)) {
      String strObj = obj.toString().trim();
      if ("null".equalsIgnoreCase(strObj)) {
        return "";
      }
      return strObj;
    }
    if ((obj instanceof BigDecimal)) {
      BigDecimal bigObj = (BigDecimal)obj;
      return bigObj.toString();
    }

    return obj.toString().trim();
  }

	/**
	 * 转换数字类型对象为字符串类型
	 * @param obj
	 * @return
	 */
  public static String numValueOf(Object obj)
  {
    String str = valueOf(obj);
    if (str.trim().length() <= 0) {
      return "0";
    }
    if (!isDouble(str)) {
      return "0";
    }
    return str;
  }

  /**
   * Map<String,Object>-><String,String>
   * @param paramsMap
   * @return
   */
  public static Map<String, String> object2StringMap(Map<String, Object> paramsMap)
  {
    Map<String, String> rsMap = new HashMap<String,String>();
    Iterator<?> it = paramsMap.entrySet().iterator();
    while (it.hasNext()) {
      Map.Entry<?, ?> entry = (Map.Entry<?, ?>)it.next();
      String mapKey = valueOf(entry.getKey());
      String mapValue = valueOf(entry.getValue());
      rsMap.put(mapKey, mapValue.trim());
    }
    return rsMap;
  }
  
  public static List<?> getSplitJsonParams(String channel)
  {
    List<Map<String, Object>> array = null;
    try {
      array = new ArrayList<Map<String, Object>>();
      String params = valueOf(channel);
      params = params.replace("{", "");
      params = params.replace("}", "");
      String[] paramsArray = params.split(",");
      for (String str : paramsArray) {
        String[] temp = str.split(":");
        Map<String, Object> channelMap = new HashMap<String, Object>();
        channelMap.put("channelno", temp[0]);
        channelMap.put("channelpay", temp[1]);
        String bankid = temp.length == 2 ? "" : temp[2];
        channelMap.put("bankid", bankid);
        String vouchercode = temp.length == 4 ? temp[3] : "";
        channelMap.put("vouchercode", vouchercode);
        array.add(channelMap);
      }
    } catch (Exception e) {
    	error("======= 拆分 chennal参数异常 =====");
    	error(e.getMessage());
      return null;
    }
    return array;
  }
  
  	/**
	 * 拆分传入的字符串，返回为键值对形式
	 * @param channel
	 * @return
	 */  
  public static List<Map<String, Object>> splitVoucher(String voucher)
  {
    List<Map<String, Object>> array = null;
    try {
      array = new ArrayList<Map<String, Object>>();
      String params = valueOf(voucher);
      params = params.replace("{", "");
      params = params.replace("}", "");
      String[] paramsArray = params.split(",");
      for (String str : paramsArray) {
        String[] temp = str.split(":");
        Map<String, Object> voucherMap = new HashMap<String, Object>();
        voucherMap.put("vouchercode", temp[0]);
        voucherMap.put("voucheramount", temp[1]);
        array.add(voucherMap);
      }
    } catch (Exception e) {
      error("======= 拆分 voucher 参数异常 =====");
      error(e.getMessage());
    }
    return array;
  }

	/**
	 * 取list 中 map的value值
	 * @param srcList
	 * @param mapKey
	 * @return
	 */  
  public static String getListMapValue(List<?> srcList, String mapKey)
  {
    if ((srcList == null) || (srcList.isEmpty())) return "";
    Map<?, ?> m = (Map<?, ?>)srcList.get(0);
    String value = valueOf(m.get(mapKey));
    return value;
  }

	/**
	 * 数值类对象转换为BigDecimal
	 * @param obj
	 * @return
	 */
  public static BigDecimal toBigDecimal(Object obj)
  {
    String strObj = valueOf(obj);
    BigDecimal decStrObj = new BigDecimal(0);
    decStrObj.setScale(2, RoundingMode.HALF_UP);
    try {
      decStrObj = new BigDecimal(strObj);
      decStrObj = decStrObj.setScale(2, RoundingMode.HALF_UP);
    } catch (Exception e) {
      error(" ===== 非数值转换Bigdecimal异常  ===== ");
      error(e.getMessage());
    }
    return decStrObj;
  }

  /**
   * 数值转换为BigDecimal
   * @param i
   * @return
   */
  public static BigDecimal toBigDecimal(int i)
  {
    BigDecimal bignumber = new BigDecimal(i);
    bignumber = bignumber.setScale(2, RoundingMode.HALF_UP);
    return bignumber;
  }

	/**
	 * 数值字符串转换为BigDecimal
	 * @param amount
	 * @return
	 */  
  public static BigDecimal toBigDecimal(String amount)
  {
    BigDecimal decimalAmount = new BigDecimal(0);
    try {
      decimalAmount = new BigDecimal(amount);
      decimalAmount = decimalAmount.setScale(2, RoundingMode.HALF_UP);
    } catch (Exception e) {
      error(" ===== 非数值转换Bigdecimal异常  ===== ");
      error(e.getMessage());
      return null;
    }
    return decimalAmount;
  }

	/**
	 * 数值字符串转换为BigInteger
	 * @param amount
	 * @return
	 */  
  public static BigInteger toBigInteger(String amount)
  {
    BigInteger decimalAmount = null;
    try {
      decimalAmount = new BigInteger(amount);
    }
    catch (Exception e) {
      error(" ===== 非数值转换Bigdecimal异常  ===== ");
      error(e.getMessage());
    }
    return decimalAmount;
  }
  
  	/**
	 * 请求返回响应失败或返回结果结果为空
	 * @param rs
	 * @return boolean
	 */
	public static boolean isFailOrEmpty(IResult rs){
		if (!rs.isSuccessful()) return true;
		List<?> rsList = rs.getResult(0);
		if (rsList.isEmpty()) return true;
		if (rsList.size() < 1) return true;
		return false;
	}

  public static String calculateBuyerPay(String smrjg, String charge)
  {
    BigDecimal decimalSmrjg = toBigDecimal(smrjg);
    decimalSmrjg = decimalSmrjg.setScale(2, RoundingMode.HALF_UP);
    BigDecimal decimalCharge = toBigDecimal(charge);
    decimalCharge = decimalCharge.setScale(2, RoundingMode.HALF_UP);
    BigDecimal decimalBuyerPay = decimalSmrjg.add(decimalCharge);
    return valueOf(decimalBuyerPay);
  }

  public static List<Map<String, Object>> jsonToList(String jsonString)
  {
    
	List list = new ArrayList();
    try {
      Gson gson = new Gson();
      list = (List)gson.fromJson(jsonString, new TypeToken() {  } .getType());
    }
    catch (Exception e)
    {
      error(e.getMessage());
    }
    return list;
  }

  public static Map<String, Object> jsonToMap(String jsonString)
  {
    Map map = new HashMap();
    try {
      Gson gson = new Gson();
      map = (Map)gson.fromJson(jsonString, new TypeToken() {  } .getType());
    }
    catch (Exception e)
    {
      error(e.getMessage());
    }
    return map;
  }

  public static String IResultToJson(IResult rs)
  {
    Map map = new HashMap();
    String Jsondate = null;
    try
    {
      Gson gson = new Gson();
      List list = rs.getResult(0);
      String message = rs.getMessage();
      String code = rs.getCode();
      map.put("message", message);
      map.put("code", code);
      list.add(map);

      Jsondate = gson.toJson(list);
    }
    catch (Exception e)
    {
    }
    return Jsondate;
  }

  /**
	 * 获取Result<List<Map>> 中 key对应的value值
	 * 多个Result,或多个List时用
	 * @param rs
	 * @param rsIndex
	 * @param listIndex
	 * @param mapKey
	 * @return
	 */
	public static String getResultMapValue(IResult rs,int rsIndex,int listIndex,String mapKey){
		String value = "";
		try {
			List rsList = rs.getResult(rsIndex);
			Map rsMap = (Map)rsList.get(listIndex);
			value = valueOf(rsMap.get(mapKey));
		} catch (Exception e) {
			//e.printStackTrace();
			error("error",e);
		}
		return value.trim();
	}
	/**
	 * get Result<List<Map>> 中 key对应的value值
	 * 一个Result只有一个List,list只有一个map时使用
	 * @param rs
	 * @param mapKey
	 * @return
	 */
	public static String getResultMapValue(IResult rs,String mapKey){
		String value = "";
		try {
			List rsList = rs.getResult(0);
			Map rsMap = (Map)rsList.get(0);
			value = valueOf(rsMap.get(mapKey));
		} catch (Exception e) {
			error("error",e);
		}
		return value.trim();
	}

	
	/**
	 * 获取Result中的map
	 * @param rs
	 * @param rsIndex
	 * @param listIndex
	 * @return
	 */
	public static Map getResultMap(IResult rs,int rsIndex,int listIndex){
		Map rsMap = null;
		try {
			List rsList = rs.getResult(rsIndex);
			rsMap = (Map)rsList.get(listIndex);
		} catch (Exception e) {
			error("error",e);
		}	
		return rsMap;
	}
	/**
	 * 获取Result中的map
	 * @param rs
	 * @param rsIndex
	 * @param listIndex
	 * @return
	 */
	public static Map getResultMap(IResult rs){
		Map rsMap = null;
		try {
			List rsList = rs.getResult(0);
			if (rsList !=null && !rsList.isEmpty()) {
				rsMap = (Map)rsList.get(0);
			}
		} catch (Exception e) {
			error("error",e);
		}	
		return rsMap;
	}
	
	/**
	 * 组装成一个Result 用于返回消息
	 * @param rsMap
	 * @return
	 */
	public static IResult makerResults(Map rsMap){
		IResult rs = new Result();
		rs.setResType(IDefineMsg.WS_TYPE_LISTMAP);
		List rsList = new ArrayList();
		rsList.add(rsMap);
		rs.setResult(rsList);
		return rs;
	}
	
	/**
	 * 组装成一个Result 用于返回消息
	 * @param rs
	 * @param rsMap
	 * @return
	 */
	public static IResult makerResults(IResult rs,Map rsMap){
		List rsList = new ArrayList();
		rs.setResType(IDefineMsg.WS_TYPE_LISTMAP);
		rsList.add(rsMap);
		rs.setResult(rsList);
		rs.setLengths(rsMap.size());
		return rs;
	}
	/**
	 * 组装成一个Result 用于只返回单一值消息
	 * @param rs
	 * @param key
	 * @param value
	 * @return
	 */
	public static IResult makerResults(IResult rs,String key,String value){
		List rsList = new ArrayList();
		HashMap rsMap = new HashMap();
		rsMap.put(key, value);
		rsList.add(rsMap);
		rs.setResult(rsList);
		rs.setLengths(IDefineMsg.WS_TYPE_INT);
		rs.setResType(IDefineMsg.WS_TYPE_LISTMAP);
		return rs;
	}
	
	public static IResult makerResults(String code,String msg){
		IResult rs = new Result();
		rs.setCode(code);
		rs.setMessage(msg);
		return rs;
	}
	
	public static IResult makerSusResults(String msg){
		IResult rs = new Result();
		rs.setCode(IDefineMsg.CODE_SUCCESS);
		rs.setMessage(msg);
		rs.setLengths(IDefineMsg.WS_TYPE_NULL);
		return rs;
	}
	
	public static IResult makerSusResults(String msg,List list){
		IResult rs = new Result();
		rs.setCode(IDefineMsg.CODE_SUCCESS);
		rs.setMessage(msg);
		rs.setResType(IDefineMsg.WS_TYPE_LISTMAP);
		rs.setResult(list);
		rs.setLengths(list.size());
		return rs;
	}
	
	public static IResult makerSusResults(String msg,Map rsMap){
		IResult rs = new Result();
		List list = new ArrayList();
		list.add(rsMap);
		rs.setCode(IDefineMsg.CODE_SUCCESS);
		rs.setMessage(msg);
		rs.setResType(IDefineMsg.WS_TYPE_LISTMAP);
		rs.setLengths(rsMap.size());
		rs.setResult(list);
		return rs;
	}
	
	public static IResult makerSusResults(String msg,Object object){
		IResult rs = new Result();
		
		rs.setCode(IDefineMsg.CODE_SUCCESS);
		rs.setMessage(msg);
		rs.setResType(IDefineMsg.WS_TYPE_OBJECT);
		rs.setResult(object);
		rs.setLengths(IDefineMsg.WS_TYPE_INT);
		return rs;
	}
	
	public static IResult makerErrResults(String msg,Map rsMap){
		IResult rs = new Result();
		List list = new ArrayList();
		list.add(rsMap);
		rs.setCode(IDefineMsg.CODE_ERROR);
		rs.setMessage(msg);
		rs.setResType(IDefineMsg.WS_TYPE_LISTMAP);
		rs.setLengths(rsMap.size());
		rs.setResult(list);
		return rs;
	}
	
	public static IResult makerErrResults(String msg){
		IResult rs = new Result();
		rs.setCode(IDefineMsg.CODE_ERROR);
		rs.setMessage(msg);
		return rs;
	}
	
	public static IResult makerResults(IResult rs,List rsList){
		rs.setResult(rsList);
		rs.setResType(IDefineMsg.WS_TYPE_LISTMAP);
		rs.setLengths(rsList.size());
		return rs;
	}


  public static String formatDate(String pattern)
  {
    Date date = new Date();
    return formatDate(date, pattern);
  }

  public static String formatDate()
  {
    String pattern = "yyyyMMdd";
    return formatDate(pattern);
  }


	/**
	 * 不大于0
	 * @param number
	 * @return
	 */
	public static boolean notGreaterZero(BigDecimal number){
		return number.compareTo(new BigDecimal(0)) < 1;
	}
	
	/**
	 * 是否大0
	 * @param number
	 * @return
	 */
	public static boolean isGreaterZero(BigDecimal number){
		return number.compareTo(new BigDecimal(0)) > 0;
	}
	
	/**
	 * 是否大0
	 * @param number
	 * @return
	 */
	public static boolean isGreaterZero(String number){
		BigDecimal bNumber = toBigDecimal(number);
		if(isNull(bNumber)){
			return false;
		}
		return isGreaterZero(bNumber);
	}

	
	/**
	 * 生成 pwdLength长度的随机码
	 * @param a
	 * @param pwdLength
	 * @return string
	 */	
  public static String getRandomCode(int pwdLength)
  {
    int randomNum = getRandomCode(0, pwdLength);
    return valueOf(Integer.valueOf(randomNum));
  }

	/**
	 * 生成 pwdLength长度的随机码
	 * @param a
	 * @param pwdLength
	 * @return int
	 */  
  public static int getRandomCode(int a, int pwdLength)
  {
    Random random = new Random();
    int b = random.nextInt(10);
    a = a * 10 + b;
    if (a < 100000) {
      return getRandomCode(a, pwdLength);
    }
    return a;
  }
  
	/**
	 * 
	 * @Title: getCode6
	 * @Description: 获取6位随机数
	 * @author xiao.he
	 * @date 2014-9-2 下午07:15:07
	 * @param @return
	 * @return int
	 */
	public static int getRandomCode6(){
		int intCount = (new Random()).nextInt(999999);// 最大值位9999
		if (intCount < 100000)
			intCount += 100000; // 最小值位10000001
		return intCount;
	}
	
	/**
	 * 随机数
	 * @param n
	 * @return
	 */
	 public static String getRandStr(int n)
	  {
	    Random random = new Random();
	    String sRand = "";
	    for (int i = 0; i < n; i++) {
	      String rand = String.valueOf(random.nextInt(10));
	      sRand = sRand + rand;
	    }
	    return sRand;
	  }
  
  /**
   * 字符转数值
   * @param intstr
   * @return int
   */
  public static int parseInt(String intstr)
  {
    return isEmpty(intstr) ? 0 : Integer.parseInt(intstr.trim());
  }
  
  /**
   * 字符转数值
   * @param intstr
   * @return float
   */
  public static float parseFloat(String intstr)
  {
    return isEmpty(intstr) ? 0.0F : Float.parseFloat(intstr.trim());
  }

  /**
   * 字符转数值
   * @param intstr
   * @return double
   */
  public static double parseDouble(String intstr)
  {
    return isEmpty(intstr) ? 0.0D : Double.parseDouble(intstr.trim());
  }
  
  /**
	* 检查一个数组中是否包含某个特定的值
	* @param arr
	* @param targetValue
	* @return boolean
	*/
  public static boolean useLoop(String[] arr, String targetValue)
  {
    for (String s : arr) {
      if (s.equals(targetValue)) {
        return true;
      }
    }
    return false;
  }
  
	/**
	 * 正则校验(入参 params 不能为 null)
	 * 
	 * @param params 入参
	 * @param macroDefine 宏定义（正则表达式）
	 * @return boolean
	 */  
	  public static boolean regExpCheck(String params, String macroDefine)
	  {
	    return params.matches(macroDefine);
	  }

  
  /**
   * 加一年
   * @param date
   * @param year
   * @return
   */
  public static Date addYear(Date date, int year) {
		GregorianCalendar gdate = new GregorianCalendar();
		gdate.setTime(date);
		gdate.add(GregorianCalendar.YEAR, year);
		return gdate.getTime();
	}
  
	/**
	 * 加一月
	 * @param date
	 * @param month
	 * @return
	 */
	public static Date addMonth(Date date, int month) {
		GregorianCalendar gdate = new GregorianCalendar();
		gdate.setTime(date);
		gdate.add(GregorianCalendar.MONTH, month);
		return gdate.getTime();
	}
	
	/**
	 * 加一日 
	 * @param date
	 * @param day
	 * @return
	 */
	public static Date addDay(Date date, int day) {
		GregorianCalendar gdate = new GregorianCalendar();
		gdate.setTimeInMillis(date.getTime());
		gdate.add(GregorianCalendar.DAY_OF_MONTH, day);
		return gdate.getTime();
	}
	
	/**
	 * 分钟
	 * @param date
	 * @param second
	 * @return
	 */
	public static Date addSecond(Date date, int second) {
		GregorianCalendar gdate = new GregorianCalendar();
		gdate.setTimeInMillis(date.getTime());
		gdate.add(GregorianCalendar.SECOND, second);
		return gdate.getTime();
	}

	/**
	 * @Title: formatDate
	 * @Description: 获取指定格式日期字符串
	 * @param  date
	 * @param  patter
	 * @param  
	 * @return String
	 */
	public static String formatDate(Date date, String patter) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(patter);
			return sdf.format(date);
		} catch (RuntimeException e) {
			return "";
		}
	}

	/**
	 * @Title: formatDate
	 * @Description: 按默认格式格式化时间
	 * @param  date
	 * @return String
	 */
	public static String formatDate(Date date) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			return sdf.format(date);
		} catch (RuntimeException e) {
			return "";
		}
	}

	/**
	 * 按照指定的格式来解析字符串成为时间
	 * @param date
	 * @param patter
	 * @return
	 */
	public static Date parseDate(String strDate, String patter) {
		Date date = null;
		SimpleDateFormat sdf = new SimpleDateFormat(patter);
		try {
			date = sdf.parse(strDate);
		} catch (ParseException e) {
			return null;
		}
		return date;
	}

	/**
	 * @Title: parseDate
	 * @Description: 按默认格式解析时间
	 * @param  strDate
	 * @return Date
	 */
	public static Date parseDate(String strDate) {
		Date date = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			date = sdf.parse(strDate);
		} catch (ParseException e) {
			return null;
		}
		return date;
	}

	/**
	 * 获得现在的时间(格式:yyyy-MM-dd)
	 * @return
	 */
	public static Date getNow() {
		Date now = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		try {
			return dateFormat.parse(dateFormat.format(now));
		} catch (ParseException e) {
			return null;
		}
	}
	
	public static Date getTimeDate() {
		Date now = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			return dateFormat.parse(dateFormat.format(now));
		} catch (ParseException e) {
			return null;
		}
	}

	/**
	 * 计算两个时间相差的天数
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static int daysBetween(Date date1, Date date2) {
		if (date1 == null || date2 == null) {
			return 0;
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(date1);
		long time1 = cal.getTimeInMillis();
		cal.setTime(date2);
		long time2 = cal.getTimeInMillis();
		long between_days = (time2 - time1) / (1000 * 3600 * 24);
		return Integer.parseInt(String.valueOf(between_days));
	}

	/**
	 * @Title: getTimestamp
	 * @Description: 获取1-2的时间差，单位为ms
	 * @param time1
	 * @param time2
	 * @return int
	 */
	public static int getTimestamp(Date time1, Date time2) {
		if (time1 == null || time2 == null) {
			return 0;
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(time1);
		long m1 = cal.getTimeInMillis();
		cal.setTime(time2);
		long m2 = cal.getTimeInMillis();
		long between_sec = (m2 - m1);
		return Integer.parseInt(String.valueOf(between_sec));
	}

	/**
	 * @Title: getWeekTimesBE
	 * @Description: 获取一周的起止时间
	 * @param offset 星期偏移量，0为本周，-1为上周，1为下周，如此类推
	 * @param  date[0]：开始时间，格式2012-03-04
	 *        00:00:00；date[1]：结束时间，格式2012-03-10 23:59:59；异常为null
	 * @return Date[]
	 */
	public static Date[] getWeekTimesBE(int offset) {
		try {
			Date[] dates = new Date[2];
			SimpleDateFormat f1 = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat f2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			// 得到当前日期
			Calendar cal = Calendar.getInstance();

			// 得到本周第一天日期
			int day_of_week = cal.get(Calendar.DAY_OF_WEEK);
			cal.add(Calendar.DATE, -day_of_week + 1 + (7 * offset));
			Date begin = cal.getTime();
			String weekFirstStr = f1.format(begin);
			begin = f2.parse(weekFirstStr + " 00:00:00");

			// 得到本周最后一天
			cal.add(Calendar.DATE, 6);
			Date end = cal.getTime();
			String weekLastStr = f1.format(end);
			end = f2.parse(weekLastStr + " 23:59:59");
			dates[0] = begin;
			dates[1] = end;
			return dates;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * @Title: getMonthTimesBE
	 * @Description: 获取一月的起止时间
	 * @param @param offset 月份偏移量，0为本月，-1为上月，1为下月，如此类推
	 * @param @return date[0]：开始时间，格式2012-03-01
	 *        00:00:00；date[1]：结束时间，格式2012-03-31 23:59:59；异常为null
	 * @return Date[]
	 */
	public static Date[] getMonthTimesBE(int offset) {
		try {
			Date[] dates = new Date[2];
			// 得到当前日期
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.MONTH, offset);

			int MaxDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
			// 按你的要求设置时间
			cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), MaxDay,
					23, 59, 59);
			Date end = cal.getTime();
			cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), 1, 00, 00,
					00);
			Date begin = cal.getTime();
			dates[0] = begin;
			dates[1] = end;
			return dates;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * @Title: getDayTimesBE
	 * @Description: 获取一天的开始结束时间
	 * @param @param date
	 * @param @return
	 * @return Date[]
	 */
	public static Date[] getDayTimesBE(Date date) {
		Date[] dates = new Date[2];
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);

		// 按你的要求设置时间
		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal
				.get(Calendar.DAY_OF_MONTH), 23, 59, 59);
		Date end = cal.getTime();
		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal
				.get(Calendar.DAY_OF_MONTH), 00, 00, 00);
		Date begin = cal.getTime();
		dates[0] = begin;
		dates[1] = end;
		return dates;
	}

	/**
	 * @Title: isNotEmpty
	 * @Description: 判断列表是否为空
	 * @param @param list
	 * @param @return
	 * @return boolean
	 */
	public static boolean isNotEmpty(List<?> list) {
		boolean returnBoolean = false;
		if (list != null && list.size() > 0) {
			returnBoolean = true;
		}

		return returnBoolean;
	}

	/**
	 * @Title: isNotEmpty
	 * @Description: 判断数组是否为空
	 * @param @param ObjectArray
	 * @param @return
	 * @return boolean
	 */
	public static boolean isNotEmpty(Object[] ObjectArray) {
		boolean returnBoolean = false;
		if (ObjectArray != null && ObjectArray.length > 0) {
			returnBoolean = true;
		}

		return returnBoolean;
	}

	/**
	 * @Title: isNotEmpty
	 * @Description: 判断字符串是否不为空
	 * @param @param strings
	 * @param @return
	 * @return boolean
	 */
	public static boolean isNotEmpty(String... strings) {
		boolean returnBoolean = true;

		if (strings != null && strings.length > 0) {
			for (String string : strings) {
				if (string == null || "".equals(string)) {
					returnBoolean = false;
					break;
				}
			}
		} else {
			returnBoolean = false;
		}

		return returnBoolean;
	}
	
	/**
	* @Title: isEmpty 
	* @Description: 判断字符串是否为空 
	* @param @param strings
	* @param @return  
	* @return boolean
	 */
	public static boolean isEmpty(String... strings) {
		if (strings == null || strings.length == 0) {
			return true;
		}
		for (String string : strings) {
			if (string == null || "".equals(string.trim().replace(" ", ""))) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @purpose:calculate the page count using the count and pagesize @params:
	 * int ,int @return: int
	 */
	public static int count2PageCount(int count, int pageSize) {
		int pageCount = count / pageSize;
		if (count % pageSize != 0) {
			pageCount++;
		}

		return pageCount;
	}

	/**
	 * @purpose:calculate the page count using the count and pagesize @params:
	 * int ,int @return: int
	 */
	public static int count2PageCount(long countLong, int pageSize) {
		int count = Long.valueOf(countLong).intValue();
		int pageCount = count / pageSize;
		if (count % pageSize != 0) {
			pageCount++;
		}

		return pageCount;
	}

	/**
	 * @purpose:put a List to String use the specified pattern ";" @params: List
	 * 
	 * @return: String
	 */
	public static String listToString(List<?> list) {
		StringBuffer stringBuffer = new StringBuffer();

		if (isNotEmpty(list)) {
			for (Object object : list) {
				if (object != null) {
					stringBuffer.append(object.toString());
					stringBuffer.append(";");
				}
			}
		}

		if (stringBuffer.length() == 0) {
			return null;
		} else {
			return stringBuffer.toString();
		}
	}

	/**
	 * @purpose:put a List to String use the specified pattern @params: List
	 * 
	 * @return: String
	 */
	public static String listToString(List<?> list, String pattern) {
		StringBuffer stringBuffer = new StringBuffer();

		if (isNotEmpty(list)) {
			for (Object object : list) {
				if (object != null) {
					stringBuffer.append(object.toString());
					stringBuffer.append(pattern);
				}
			}
		}

		if (stringBuffer.length() == 0) {
			return null;
		} else {
			return stringBuffer.toString();
		}
	}

	/**
	 * @purpose:put a array to a String using the specified pattern ";" @params:
	 * Object[] @return: String
	 */
	public static String array2String(Object[] objects) {
		String string = null;

		StringBuffer stringBuffer = new StringBuffer();
		if (isNotEmpty(objects)) {
			for (Object object : objects) {
				if (object != null) {
					stringBuffer.append(object.toString());
					stringBuffer.append(";");
				}
			}
		}

		if (stringBuffer.length() > 0) {
			string = stringBuffer.toString();
		}

		return string;
	}

	/**
	 * @purpose:put a Object Array to List @params: Object[] @return: List
	 */
	public static List arrayToList(Object[] objects) {
		List list = null;

		if (isNotEmpty(objects)) {
			list = new ArrayList();
			for (Object object : objects) {
				if (object != null) {
					list.add(object);
				}
			}
		}

		return list;
	}

	/**
	 * @purpose:put a String array to List<Integer> @params: String[] @return:
	 * List<Integer>
	 */
	public static List<Integer> stringArrayToListInteger(String[] strings) {
		List<Integer> list = null;

		try {
			if (isNotEmpty(strings)) {
				list = new ArrayList<Integer>();
				for (String string : strings) {
					if (string != null) {
						list.add(Integer.parseInt(string));
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			list = null;
		}

		return list;
	}

	/**
	 * @purpose:put a String to List<String> based on the specified pattern ";"
	 * 
	 * @params: String @return: List<String>
	 */
	public static List<String> stringToList(String string) {
		List<String> list = null;

		if (isNotEmpty(string)) {
			String[] stringArray = string.split(";");
			list = arrayToList(stringArray);
		}

		return list;
	}

	/**
	 *  解析输入流成byte数组
	 * @param inputstream
	 * @param length
	 * @return
	 * @throws Exception
	 */
	public static byte[] recvMsg(InputStream inputstream, int length)
			throws Exception {
		try {
			byte content[] = new byte[length];
			int readCount = 0; // 已经成功读取的字节的个数
			while (readCount < length) {
				int size = (length - readCount) > 1024 ? 1024
						: (length - readCount);
				readCount += inputstream.read(content, readCount, size);
			}
			return content;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * @Title: isNumeric
	 * @Description: 判断字符串是否为数字
	 * @Param @param str
	 * @Param @return
	 * @return boolean
	 */
	public static boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		return pattern.matcher(str).matches();
	}

	/**
	 * 判断手机号是否合法
	 * 
	 * @param phone
	 * @return
	 */
	public static boolean isPhone(String phone) {
		if (null == phone || "".equals(phone)) {
			return false;
		}
		String regExp =  "^[1][3,5,8][0-9]{9}$";
		Pattern p = Pattern.compile(regExp);
		Matcher m = p.matcher(phone);
		return m.find();
	}
	
	/**
	 * 判断邮箱号是否合法
	 * 
	 * @param phone
	 * @return
	 */
	public static boolean isEmail(String email) {
		if (null == email || "".equals(email)) {
			return false;
		}
		String regExp = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";
		Pattern p = Pattern.compile(regExp);
		Matcher m = p.matcher(email);
		return m.find();
	}

	/**
	 * @Title: parseListToString
	 * @Description: 列表转换为字符串
	 * @param  list 要转换的对象
	 * @param  split 分隔符
	 * @param 
	 * @return String
	 */
	public static String parseListToString(List<?> list, String split) {
		if (list != null && list.size() > 0) {
			String str = "";
			int len = list.size();
			for (int i = 0; i < len; i++) {
				if (i != (len - 1)) {
					str += (list.get(i) + split);
				} else {
					str += list.get(i);
				}
			}
			return str;
		}
		return null;
	}
	
	/**
	 * @Title: pasreArrayToString
	 * @Description: 数组转字符串
	 * @param   arr
	 * @param   split  
	 * @return String
	 */
	public static String pasreArrayToString(Object[] arr, String split) {
		if (arr != null && arr.length > 0) {
			String str = "";
			for (int i = 0; i < arr.length; i++) {
				if (i != (arr.length - 1)) {
					str += (arr[i] + split);
				} else {
					str += arr[i];
				}
			}
			return str;
		}
		return null;
	}

	/**
	 * @Title: getFileSuffix
	 * @Description: 获取文件后缀，返回如：.jpg
	 * @param   name
	 * @return String
	 */
	public static String getFileSuffix(String name) {
		int loc = name.lastIndexOf('.');
		if (loc != -1) {
			return name.substring(loc);
		}
		return null;
	}

	/**
	 * @Title: getTimeFileName
	 * @Description: 获取默认的以是时间命名的文件名
	 * @return String
	 */
	public static String getTimeFileName(String suffix) {
		return formatDate(new Date(), "yyyyMMddHHmmssSSS") + suffix;
	}

	/**
	 * @Title: getAgeByBirthday
	 * @Description: 计算年龄
	 * @param @param birthday
	 * @return String
	 */
	public static String getAgeByBirthday(Date birthday) {
		int days = daysBetween(birthday, new Date());
		int year = days / 365;
		int month = (days % 365) / 30;
		String age = "";
		if (year > 0) {
			age = year + "岁";
			if (month > 0) {
				age = age + month + "个月";
			}
		} else if (month > 0) {
			age = month + "个月";
		}
		return age;
	}

	/**
	 * @Title: deleteFile
	 * @Description: 删除文件或文件夹
	 * @param  realPath
	 * @return void
	 */
	public static void deleteFile(String realPath) {
		File file = new File(realPath);
		if (file.isFile() && file.exists()) {
			file.delete();
		} else {
			if (file.isDirectory()) {
				File[] files = file.listFiles();
				for (int i = 0; i < files.length; i++) {
					files[i].delete();
				}
			}
			file.delete();
		}
	}

	/**
	 * @Title: getFileSize
	 * @Description: 根据路径获取文件大小
	 * @param  path
	 * @return long
	 */
	public static long getFileSize(String path) {
		File file = new File(path);
		if (file.isFile() && file.exists()) {
			return file.length();
		}
		return 0;
	}

	/**
	 * @Title: makeDir
	 * @Description: 创建目录，如果存在则不创建
	 * @param  path
	 * @return void
	 */
	public static boolean makeDir(String path) {
		return new File(path).mkdirs();
	}
	
	
	public static byte[] getImageToBytes(String imgPath) {  
	    byte[] bytes = null;  
	    ByteArrayOutputStream out = new ByteArrayOutputStream();  
	  
	    try {  
	        //创建URL  
	        URL url = new URL(imgPath);  
	        //得到连接  
	        HttpURLConnection urlConn = (HttpURLConnection)url.openConnection();  
	        //得到连接地址的输入流  
	        InputStream in = urlConn.getInputStream();  
	  
	        int size;  
	        //缓冲值  
	        bytes = new byte[1024];  
	        if(in != null){  
	            //循环读输入流至read返回-1为止，并写到缓存中  
	            while((size=in.read(bytes)) != -1){  
	                out.write(bytes, 0, size);  
	            }  
	        }  
	        out.close();//关闭输出流  
	        in.close();//关闭输入流  
	        urlConn.disconnect();//断开连接  
	  
	        } catch (Exception e) {  
	            e.printStackTrace();  
	        }  
	  
	        return out.toByteArray();  
	}
	
	public static void bytesToImgSave(byte[] bytes, String imgFile) throws Exception{  
	    //UUID序列号作为保存图片的名称  
	    
	    File f = new File(imgFile);  
	  
	    try {
			DataOutputStream out = new DataOutputStream(new FileOutputStream(f));
			for (int i = 0; i < bytes.length; i++){
				out.write(bytes[i]);
			}
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * basePath路径
	 * @param request
	 * @return
	 */
	public static String getServerPath(HttpServletRequest request){
	  	String basePath = request.getSession().getServletContext().getRealPath("/");
	  	return basePath;
  }
	
	/**
	 * 保存远程图片
	 * @param urlStr
	 * @return
	 */
	public String putUrlPNG(String urlStr){
		
		String imgUrl=null;
		String name = formatDate(new Date(), "yyyyMMddHHmmssSSS");
		String path=/**Constants.getFileBasePath()+*/"opt/appleprofession/";
		
		@SuppressWarnings("unused")
		String path2="C://test//";	//本机测试路径
		try {
			URL url =new URL(urlStr);
			BufferedInputStream bis =new BufferedInputStream(url.openStream());
			 byte[] bytes = new byte[100];
			 imgUrl =path+name+".png";
			 OutputStream bos = new FileOutputStream(new File(imgUrl));
			
			 int len;  
		      while ( (len = bis.read(bytes)) > 0) {  
		        bos.write(bytes, 0, len);  
		      }  
		      bis.close();  
		      bos.flush();  
		      bos.close();
		} catch (MalformedURLException e) {
			error(e.getMessage());
			return null;
		} catch (IOException e) {
			error(e.getMessage());
			return null;
		}
		
		return imgUrl;
	}
	
	
	/**
	 * 当前时间
	 * @return
	 */
	public static String getCurDatetime(){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date curDate = new Date();
		String strDatetime 	= df.format(curDate);
		return strDatetime;
	}
	/**
	 * 当前时间
	 * @return
	 */
	public static String getCurDatetime(String pattern){
		SimpleDateFormat df = new SimpleDateFormat(pattern);
		Date curDate = new Date();
		String strDatetime 	= df.format(curDate);
		return strDatetime;
	}
	
	/**
	 * 隐藏号码
	 * @param param
	 * @return
	 */
	public String putConcealParam(String param){
		
		String str = param;
		str	= str.substring(0,3)+"****"+str.substring(str.length()-4,str.length());			
		return str;
	}
	
	/** UUid 字串*/
	private static String[] chars = new String[] { "a", "b", "c", "d", "e", "f",  
        "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s",  
        "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5",  
        "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I",  
        "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",  
        "W", "X", "Y", "Z" };  
	/**
	 * 获取短UUid
	 * @return
	 */
	public  String getShortUUid() {  
	    StringBuffer shortBuffer = new StringBuffer();  
	    String uuid = UUID.randomUUID().toString().replace("-", "");  
	    for (int i = 0; i < 8; i++) {  
	        String str = uuid.substring(i * 4, i * 4 + 4);  
	        int x = Integer.parseInt(str, 16);  
	        shortBuffer.append(chars[x % 0x3E]);  
	    }  
	    return shortBuffer.toString();  
	  
	} 
}