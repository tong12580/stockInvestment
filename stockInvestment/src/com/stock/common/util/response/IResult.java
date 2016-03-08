package com.stock.common.util.response;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public abstract interface IResult extends Serializable
{
  public abstract Object getResult();

  public abstract void setResult(Object paramObject);

  public abstract String getMessage();

  public abstract void setCode(String paramString);

  public abstract void setMessage(String paramString);

  public abstract void copy(IResult paramIResult);
  
  public abstract void setLengths(int paramInt);

  public abstract int getLengths();
  
  public abstract boolean isSuccessful();

  public abstract void failure(String paramString);

  public abstract void successful(String paramString);

  public abstract int getResType();

  public abstract void setResType(int paramInt);

  public abstract void transfer(Map<?, ?> paramMap);

  public abstract List<?> getResult(int paramInt);
  
//  public abstract StringBuffer getListJson();

  public abstract StringBuffer toJson();
  
  public abstract String getCode();
}