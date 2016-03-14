package com.stock.controller;

//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;

//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.ResponseBody;

//import com.stock.cache.CacheBankCard;
//import com.stock.common.mybatis.Page;
//import com.stock.common.util.response.IResult;
import com.stock.common.util.response.ServiceAction;
//import com.stock.dao.batch.BookDao;
//import com.stock.pojo.BankCard;

@Controller
@RequestMapping(value="/at")
public class TestController extends ServiceAction{
	
//	@Autowired private	BookDao bookDao;
//
//	@ResponseBody
//	@RequestMapping(value="/b")
//	public Map<String, Object> getBook(){
//		
//		Map<String, Object> params =new HashMap<String, Object>();
//		params.put("id", 8);
//		List<Map<String, Object>> list =bookDao.findById(params);
//		Page<Map<String, Object>> page =new Page<Map<String,Object>>();
//		page.setPageNo(1);
//		page.setPageSize(1);
//		page.setParams(params);
//		List<Map<String, Object>>	list2 =bookDao.findBy(page);
//		page.setResults(list2);
//		params.put("list", list);
//		params.put("list2", page);
//		
//		
//		return params;
//	} 
//	
//	@ResponseBody
//	@RequestMapping(value="/c")
//	public IResult getBank(){
//		
//		List<BankCard> list =bookDao.findBank();
//		if(isNotEmpty(list))
//			return makerSusResults("查询成功", list);
//		return makerErrResults("查询失败");
//	}
//	
//	@ResponseBody
//	@RequestMapping(value="/d")
//	public IResult getBankID(){
//		
//		BankCard bankCard=CacheBankCard.getInstance().getBankcardByCode("b07");
//		if(isNull(bankCard))	
//			return makerErrResults("查询失败");
//		return makerSusResults("查询成功", bankCard);
//	}
//	
//	@ResponseBody
//	@RequestMapping(value="/e",method=RequestMethod.PUT)
//	public IResult getBankIDput(){
//		
//		BankCard bankCard=CacheBankCard.getInstance().getBankcardByCode("b07");
//		if(isNull(bankCard))	
//			return makerErrResults("查询失败");
//		return makerSusResults("查询成功", bankCard);
//	}
	
}
