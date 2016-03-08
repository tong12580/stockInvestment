
package com.stock.common;

import java.math.BigDecimal;
import java.util.Random;

/**
 * 
 * @ClassName: RoundUtil 
 * @Description: Double类型的四舍五入算法
 * @author xiao.he
 * @date 2014-9-10 下午10:22:04
 *
 */
public class RoundUtil {
	
	/**
	 * 
	 * @Title: trunc
	 * @Description: 截取算法
	 * @author xiao.he
	 * @date 2015-7-27 下午05:24:40
	 * @param @param value
	 * @param @param decimalPlaces
	 * @param @return
	 * @return Double
	 */
	public static Double trunc(BigDecimal value, int decimalPlaces){
		if(value==null ) return null;
		if(decimalPlaces<=0) decimalPlaces=0;
		BigDecimal bd = value;   
		bd = bd.setScale(decimalPlaces,BigDecimal.ROUND_DOWN);
		return bd.doubleValue();
	}
	

	
	/**
	 * 
	 * @Title: trunc
	 * @Description: 截取算法
	 * @author xiao.he
	 * @date 2014-9-10 下午10:15:59
	 * @param @param value
	 * @param @param decimalPlaces
	 * @param @return
	 * @return Double
	 */
	public static Double trunc(Double value, int decimalPlaces){
		if(value==null ) return null;
		if(decimalPlaces<=0) decimalPlaces=0;
		BigDecimal bd = new BigDecimal(value.toString());   
		bd = bd.setScale(decimalPlaces,BigDecimal.ROUND_DOWN);
		return bd.doubleValue();
	}
	
	/**
	 * 
	 * @Title: trunc
	 * @Description: 返回包含两位小数点的数字字符串(截取类型)
	 * @author ganyufei
	 * @date 2014-12-10 下午09:36:43
	 * @param @param value
	 * @param @return
	 * @return String
	 */
	public static String trunc(Double value){
		if(value==null ) return null;
		BigDecimal bd = new BigDecimal(value.toString());   
		bd = bd.setScale(2,BigDecimal.ROUND_DOWN);
		return String.format("%.2f", bd.doubleValue());
	}
	/**
	 * 
	 * @Title: trunc
	 * @Description: 返回包含两位小数点的数字字符串(截取类型)
	 * @author ganyufei
	 * @date 2014-12-10 下午09:36:43
	 * @param @param value
	 * @param @return
	 * @return String
	 */
	public static String truncStr(BigDecimal value){
		if(value==null ) return null;
		value = value.setScale(2,BigDecimal.ROUND_DOWN);
		return String.format("%.2f", value.doubleValue());
	}
	/**
	 * 
	 * @Title: trunc
	 * @Description: 截取
	 * @author xiao.he
	 * @date 2015-9-16 下午10:52:00
	 * @param @param value
	 * @param @param len
	 * @param @return
	 * @return String
	 */
	public static String truncStr(BigDecimal value,Integer len){
		if(value==null ) return null;
		value = value.setScale(len,BigDecimal.ROUND_DOWN);
		return value.toString();
	}
	
	/**
	 * 
	 * @Title: round
	 * @Description: 四舍五入算法
	 * @author xiao.he
	 * @date 2014-9-10 下午10:15:30
	 * @param @param value
	 * @param @param decimalPlaces
	 * @param @return
	 * @return Double
	 */
	public static Double round(Double value, int decimalPlaces){
		
		if(value==null ) return null;
		if(decimalPlaces<=0) decimalPlaces=0;
		BigDecimal bd = new BigDecimal(value.toString());   
		bd = bd.setScale(decimalPlaces,BigDecimal.ROUND_HALF_UP);
		return bd.doubleValue();
		
		
		
	}
	
	/**
	 * 
	 * @Title: round
	 * @Description: 四舍五入算法
	 * @author xiao.he
	 * @date 2014-9-10 下午10:15:30
	 * @param @param value
	 * @param @param decimalPlaces
	 * @param @return
	 * @return Double
	 */
	public static BigDecimal round(BigDecimal value, int decimalPlaces){
		
		if(value==null ) return null;
		if(decimalPlaces<=0) decimalPlaces=0;
		value = value.setScale(decimalPlaces,BigDecimal.ROUND_HALF_UP);
		return value;
		
		
		
	}
	
	
	/**
	 * 
	 * @Title: getCode
	 * @Description: TODO 获取四位随机数
	 * @author xiao.he
	 * @date 2014-5-8 上午11:55:55
	 * @param @return
	 * @return int
	 */
	public static int getCode(){
		int intCount = (new Random()).nextInt(9999);// 最大值位9999
		if (intCount < 1000)
			intCount += 1000; // 最小值位1001
		return intCount;
	}
	
	/**
	 * 
	 * @Title: getCode8
	 * @Description: 获取八位随机数
	 * @author xiao.he
	 * @date 2014-9-2 下午07:15:07
	 * @param @return
	 * @return int
	 */
	public static int getCode8(){
		int intCount = (new Random()).nextInt(99999999);// 最大值位9999
		if (intCount < 10000000)
			intCount += 10000000; // 最小值位10000001
		return intCount;
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
	public static int getCode6(){
		int intCount = (new Random()).nextInt(999999);// 最大值位9999
		if (intCount < 100000)
			intCount += 100000; // 最小值位10000001
		return intCount;
	}
	
	
	/**
	 * 
	 * @Title: add
	 * @Description: 加法运算
	 * @author xiao.he
	 * @date 2014-9-10 下午10:14:08
	 * @param @param d1
	 * @param @param String.valueOf(d2
	 * @param @return
	 * @return double
	 */
	public static double add(double d1, double d2){
		// 进行加法运算
         BigDecimal b1 = new BigDecimal(String.valueOf(d1));
         BigDecimal b2 = new BigDecimal(String.valueOf(d2));
         return b1.add(b2).doubleValue();
	 }
	/**
	 * 
	 * @Title: sub减法运算
	 * @Description: 减法运算
	 * @author xiao.he
	 * @date 2014-9-10 下午10:14:32
	 * @param @param d1
	 * @param @param String.valueOf(d2
	 * @param @return
	 * @return double
	 */
	public static double sub(double d1, double d2){ 
		// 进行减法运算
         BigDecimal b1 = new BigDecimal(String.valueOf(d1));
         BigDecimal b2 = new BigDecimal(String.valueOf(d2));
        return b1.subtract(b2).doubleValue();
     }
	/**
	 * 
	 * @Title: mul
	 * @Description: 乘法运算
	 * @author xiao.he
	 * @date 2014-9-10 下午10:14:41
	 * @param @param d1
	 * @param @param String.valueOf(d2
	 * @param @return
	 * @return double
	 */
    public static double mul(double d1, double d2){
        // 进行乘法运算
         BigDecimal b1 = new BigDecimal(String.valueOf(d1));
         BigDecimal b2 = new BigDecimal(String.valueOf(d2));
        return b1.multiply(b2).doubleValue();
     }
    
    
    
    /**
     * 
     * @Title: div
     * @Description: 除法运算
     * @author xiao.he
     * @date 2014-9-10 下午10:14:50
     * @param @param String.valueOf(d1
     * @param @param String.valueOf(d2
     * @param @param len
     * @param @return
     * @return double
     */
    public static double div(double d1,double d2,int len) {
    	// 进行除法运算
         BigDecimal b1 = new BigDecimal(String.valueOf(d1));
         BigDecimal b2 = new BigDecimal(String.valueOf(d2));
        return b1.divide(b2,len,BigDecimal.ROUND_HALF_UP).doubleValue();
     }
    
    /**
     * @Title： div
     * @Description： TODO  两数相除不四舍五入
     * @author：kangxh
     * @date：2015-1-28 上午09:32:02 
     * @return double
     * @param d1
     * @param d2
     * @param len
     */
    public static double divRoundDown(double d1,double d2,int len) {
    	// 进行除法运算
         BigDecimal b1 = new BigDecimal(String.valueOf(d1));
         BigDecimal b2 = new BigDecimal(String.valueOf(d2));
        return b1.divide(b2,len,BigDecimal.ROUND_DOWN).doubleValue();
     }
    
    
    /**
     * 
     * @Title: roundUP
     * @Description: 含小数点的进一位
     * @author xiao.he
     * @date 2014-9-10 下午10:17:39
     * @param @param d
     * @param @param len
     * @param @return
     * @return double
     */
    public static double roundUP(double d,int len) {  
         BigDecimal b1 = new BigDecimal(String.valueOf(d));
         BigDecimal b2 = new BigDecimal("1");
        // 任何一个数字除以1都是原数字
        return b1.divide(b2, len,BigDecimal.ROUND_UP).doubleValue();
     }
    
    

	
	
	
	public static void main(String[] args) {
		
		//double a = RoundUtil.round(1234.3434, 2);
		System.out.println(Double.valueOf("1,212.2"));
		
		
		
		/*
		RoundUtil au = new RoundUtil();
		Double aa= new Double(376022.792);

	    System.out.println(aa.longValue());
		BigDecimal bd = new BigDecimal("3.149999");   
		bd = bd.setScale(2,BigDecimal.ROUND_DOWN);
		System.out.println(bd.toString() );
		aa =au.trunc(5.589,2);
		System.out.println(aa.toString() );
		*/
		
	}

}
