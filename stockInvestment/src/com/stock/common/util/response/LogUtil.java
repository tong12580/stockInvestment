package com.stock.common.util.response;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;


public class LogUtil
{protected static Logger wsLog = Logger.getLogger(LogUtil.class);

private static Logger getParentLog() {
  try {
    throw new Exception();
  }
  catch (Exception e) {
    StackTraceElement[] sts = e.getStackTrace();
    for (int i = 0; i < sts.length; i++) {
      try {
        if (Class.forName(sts[i].getClassName()) != LogUtil.class)
          return Logger.getLogger(Class.forName(sts[i].getClassName()));
      }
      catch (ClassNotFoundException e1)
      {
      }
    }
  }
  return wsLog;
}

protected static Object getLogText(Object o) {
  if (null == o) {
    return "日志为空！";
  }
  if ((o instanceof Exception)) {
    Exception e = (Exception)o;
    StringBuffer sb = new StringBuffer();
    sb.append(e.getMessage() + "\r\n");
    StackTraceElement[] st = e.getStackTrace();
    for (int i = 0; i < st.length; i++) {
      sb.append("\t在：" + st[i].getClassName() + "." + st[i].getMethodName() + " 第 " + st[i].getLineNumber() + " 行\r\n");
    }

    return sb.toString();
  }
  if ((o instanceof String)) {
    return (String)o;
  }
  if ((o instanceof byte[])) {
    return o;
  }

  return String.valueOf(o);
}

public static void info(Logger log, Object msg)
{
  if (log.isInfoEnabled())
    log.info(getLogText(msg));
}

public static void info(Logger log, Object msg, Throwable exception)
{
  if (log.isInfoEnabled())
    log.info(getLogText(msg), exception);
}

public static void debug(Logger log, Object msg)
{
  if (log.isDebugEnabled())
    log.debug(getLogText(msg));
}

public static void debug(Logger log, Object msg, Throwable exception)
{
  if (log.isDebugEnabled())
    log.debug(getLogText(msg), exception);
}

public static void error(Logger log, Object msg)
{
  log.error(getLogText(msg));
}

public static void error(Logger log, Object msg, Throwable exception) {
  log.error(getLogText(msg), exception);
}

public static void fatal(Log log, Object msg) {
  if (log.isFatalEnabled())
    log.fatal(getLogText(msg));
}

public static void fatal(Logger log, Object msg, Throwable exception)
{
  log.fatal(getLogText(msg), exception);
}

public static void warn(Logger log, Object msg) {
  log.warn(getLogText(msg));
}

public static void warn(Logger log, Object msg, Throwable exception) {
  log.warn(getLogText(msg), exception);
}

public static void info(Class<?> c, Object msg) {
  info(Logger.getLogger(c), msg);
}

public static void info(Class<?> c, Object msg, Throwable exception) {
  info(Logger.getLogger(c), msg, exception);
}

public static void debug(Class<?> c, Object msg) {
  debug(Logger.getLogger(c), msg);
}

public static void debug(Class<?> c, Object msg, Throwable exception) {
  debug(Logger.getLogger(c), msg, exception);
}

public static void error(Class<?> c, Object msg, Throwable exception) {
  error(Logger.getLogger(c), msg, exception);
}

public static void fatal(Class<?> c, Object msg) {
  fatal(LogFactory.getLog(c), msg);
}

public static void fatal(Class<?> c, Object msg, Throwable exception) {
  fatal(Logger.getLogger(c), msg, exception);
}

public static void warn(Class<?> c, Object msg) {
  warn(Logger.getLogger(c), msg);
}

public static void warn(Class<?> c, Object msg, Throwable exception) {
  warn(Logger.getLogger(c), msg, exception);
}

public static void info(Object msg) {
  info(getParentLog(), msg);
}

public static void info(Object msg, Throwable exception) {
  info(getParentLog(), msg, exception);
}

public static void debug(Object msg) {
  debug(getParentLog(), msg);
}

public static void debug(Object msg, Throwable exception) {
  debug(getParentLog(), msg, exception);
}

public static void error(Object msg) {
  error(getParentLog(), msg);
}

public static void error(Object msg, Throwable exception) {
  error(getParentLog(), msg, exception);
}

public static void fatal(Object msg) {
  fatal(getParentLog(), msg, null);
}

public static void fatal(Object msg, Throwable exception) {
  fatal(getParentLog(), msg, exception);
}

public static void warn(Object msg) {
  warn(getParentLog(), msg);
}

public static void warn(Object msg, Throwable exception) {
  warn(getParentLog(), msg, exception);
}

public static void trace(Object pm_objLogInfo) {
  getParentLog().log(new SeriousLevel(70000, "SERIOUS", 7, null), pm_objLogInfo);
}

public static void watch(Object pm_objLogInfo) {
  getParentLog().log(new SeriousLevel(60000, "SERIOUS", 7, null), pm_objLogInfo);
}

private static class SeriousLevel extends Level {
  private static final long serialVersionUID = 1L;

  private SeriousLevel(int level, String name, int sysLogLevel, Object object) {
    super(sysLogLevel, name, sysLogLevel);
  }
}
}