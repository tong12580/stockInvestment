package com.stock.common.mybatis;

import java.util.Map;
import java.util.Properties;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;
import org.apache.log4j.Logger;

@Intercepts( {
    @Signature(method = "prepare", type = StatementHandler.class, args = {Connection.class}) })
public class PageInterceptor implements Interceptor {
	
	private static Logger log = Logger.getLogger(PageInterceptor.class);
    private String databaseType;
    
    /**
     * 拦截后要执行的方法
     */
    public Object intercept(Invocation invocation) throws Throwable {
      
       RoutingStatementHandler handler = (RoutingStatementHandler) invocation.getTarget();
       StatementHandler delegate = (StatementHandler)ReflectUtil.getFieldValue(handler, "delegate");
       BoundSql boundSql = delegate.getBoundSql();
      
       Object obj = boundSql.getParameterObject();
       if (obj instanceof Page<?>) {
           Page<?> page = (Page<?>) obj;
          
           MappedStatement mappedStatement = (MappedStatement)ReflectUtil.getFieldValue(delegate, "mappedStatement");
           Connection connection = (Connection)invocation.getArgs()[0];
           String sql = boundSql.getSql();
        
           this.setTotalRecord(page,mappedStatement, connection);
           String pageSql = this.getPageSql(page, sql);
           ReflectUtil.setFieldValue(boundSql, "sql", pageSql);
       }
       return invocation.proceed();
    }
 
 
    /**
     * 拦截器对应的封装原始对象的方法
     */
    public Object plugin(Object target) {
       return Plugin.wrap(target, this);
    }
 
    /**
     * 设置注册拦截器时设定的属性
     */
    public void setProperties(Properties properties) {
       this.databaseType = properties.getProperty("databaseType");
    }
   
    /**
     * 根据page对象获取对应的分页查询Sql语句，这里只做了两种数据库类型，Mysql和Oracle
     * 其它的数据库都 没有进行分页
     *
     * @param page 分页对象
     * @param sql 原sql语句
     * @return
     */
    private String getPageSql(Page<?> page, String sql) {
       StringBuffer sqlBuffer = new StringBuffer(sql);
       if ("mysql".equalsIgnoreCase(databaseType)) {
           return getMysqlPageSql(page, sqlBuffer);
       } else if ("oracle".equalsIgnoreCase(databaseType)) {
           return getOraclePageSql(page, sqlBuffer);
       }
       return sqlBuffer.toString();
    }
   
    /**
     * 获取Mysql数据库的分页查询语句
     * @param page 分页对象
     * @param sqlBuffer 包含原sql语句的StringBuffer对象
     * @return Mysql数据库分页语句
     */
    private String getMysqlPageSql(Page<?> page, StringBuffer sqlBuffer) {
    	
       sqlBuffer =new StringBuffer(getParamSql(page, sqlBuffer));
       int offset = (page.getPageNo() - 1) * page.getPageSize();
       sqlBuffer.append(" limit ").append(offset).append(",").append(page.getPageSize());
       return sqlBuffer.toString();
    }
   
    /**
     * 获取Oracle数据库的分页查询语句
     * @param page 分页对象
     * @param sqlBuffer 包含原sql语句的StringBuffer对象
     * @return Oracle数据库的分页查询语句
     */
    private String getOraclePageSql(Page<?> page, StringBuffer sqlBuffer) {
       int offset = (page.getPageNo() - 1) * page.getPageSize() + 1;
       sqlBuffer.insert(0, "select u.*, rownum r from (").append(") u where rownum < ").append(offset + page.getPageSize());
       sqlBuffer.insert(0, "select * from (").append(") where r >= ").append(offset);
       return sqlBuffer.toString();
    }
   
    /**
     * 给当前的参数对象page设置总记录数
     *
     * @param page Mapper映射语句对应的参数对象
     * @param mappedStatement Mapper映射语句
     * @param connection 当前的数据库连接
     */
    private void setTotalRecord(Page<?> page,
           MappedStatement mappedStatement, Connection connection) {
     
       BoundSql boundSql = mappedStatement.getBoundSql(page);
       String sql = boundSql.getSql();
      
       String countSql = this.getCountSql(page,sql);
     
       List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
    
       BoundSql countBoundSql = new BoundSql(mappedStatement.getConfiguration(), countSql, parameterMappings, page);
     
       ParameterHandler parameterHandler = new DefaultParameterHandler(mappedStatement, page, countBoundSql);
  
       PreparedStatement pstmt = null;
       ResultSet rs = null;
       try {
           pstmt = connection.prepareStatement(countSql);
          
           parameterHandler.setParameters(pstmt);
         
           rs = pstmt.executeQuery();
           if (rs.next()) {
              int totalRecord = rs.getInt(1);
            
              page.setTotalRecord(totalRecord);
           }
       } catch (SQLException e) {
           e.printStackTrace();
       } finally {
           try {
              if (rs != null)
                  rs.close();
               if (pstmt != null)
                  pstmt.close();
           } catch (SQLException e) {
              e.printStackTrace();
           }
       }
    }
    
    /**
     * 拼接参数 
     * @param page
     * @param sql
     * @return
     */
   private String getParamSql(Page<?> page, StringBuffer sqlBuffer){
	   
	   Map<String, Object> params =page.getParams();
	   
	   	if(null!=params){
	   		boolean first= true; 
	   		for(Map.Entry<String, Object> entry :params.entrySet() ){
	   			if(first){
	   				sqlBuffer.append(" where ").append(entry.getKey()).append(" = ").append(entry.getValue());
	   				first=!first;
	   			}else{
	   				sqlBuffer.append(" and ").append(entry.getKey()).append(" = ").append(entry.getValue());
	   			}
	   		}
	   	}
	   
	   return sqlBuffer.toString();  
   }
    
    /**
     * 根据原Sql语句获取对应的查询总记录数的Sql语句
     * @param sql
     * @return
     */
    private String getCountSql(Page<?> page,String sql) {
    	
    	String countSql	=	null;
    	int index 		=	sql.indexOf("from");
    	countSql		=	"select count(*) " + sql.substring(index);
    	countSql		=	getParamSql(page, new StringBuffer(countSql));
    	
       return countSql;
    }
   
    /**
     * 利用反射进行操作的一个工具类
     *
     */
    private static class ReflectUtil {
       /**
        * 利用反射获取指定对象的指定属性
        * @param obj 目标对象
        * @param fieldName 目标属性
        * @return 目标属性的值
        */
       public static Object getFieldValue(Object obj, String fieldName) {
           Object result = null;
           Field field = ReflectUtil.getField(obj, fieldName);
           if (field != null) {
              field.setAccessible(true);
              try {
                  result = field.get(obj);
              } catch (IllegalArgumentException e) {
            	  log.error(e.getMessage());
                  e.printStackTrace();
              } catch (IllegalAccessException e) {
                  log.error(e.getMessage());
                  e.printStackTrace();
              }
           }
           return result;
       }
      
       /**
        * 利用反射获取指定对象里面的指定属性
        * @param obj 目标对象
        * @param fieldName 目标属性
        * @return 目标字段
        */
       private static Field getField(Object obj, String fieldName) {
           Field field = null;
          for (Class<?> clazz=obj.getClass(); clazz != Object.class; clazz=clazz.getSuperclass()) {
              try {
                  field = clazz.getDeclaredField(fieldName);
                  break;
              } catch (NoSuchFieldException e) {
                
              }
           }
           return field;
       }
 
       /**
        * 利用反射设置指定对象的指定属性为指定的值
        * @param obj 目标对象
        * @param fieldName 目标属性
         * @param fieldValue 目标值
        */
       public static void setFieldValue(Object obj, String fieldName,
              String fieldValue) {
           Field field = ReflectUtil.getField(obj, fieldName);
           if (field != null) {
              try {
                  field.setAccessible(true);
                  field.set(obj, fieldValue);
              } catch (IllegalArgumentException e) {
            	  log.error(e.getMessage());
                  e.printStackTrace();
              } catch (IllegalAccessException e) {
            	  log.error(e.getMessage());
                  e.printStackTrace();
              }
           }
        }
    }
}
