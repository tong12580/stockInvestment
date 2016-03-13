package com.stock.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import com.stock.common.util.SpringContextUtil;
import com.stock.dao.batch.BookDao;
import com.stock.pojo.BankCard;

public class CacheBankCard extends AbstractCacheBase {
	
	
	private static CacheBankCard instance = new CacheBankCard();
	
	private static Map<String, BankCard> bankcardMap;
	private static Map<Integer, BankCard> bankcardIdMap;
	private static List<BankCard> bankcardList;
	

	public List<BankCard> getBankcardList(){
		return bankcardList;
	}
	
	
	public BankCard getBankcardByCode(String bankCode){
		return bankcardMap.get(bankCode);
	}
	
	public BankCard getBankcardById(Integer id){
		return bankcardIdMap.get(id);
	}
	

	private CacheBankCard(){
		init();
	}
	
	public static CacheBankCard getInstance() {
		return instance;
	}
	
	@Override
	public void init() {
		
		bankcardMap = new HashMap<String, BankCard>();
		bankcardIdMap = new HashMap<Integer, BankCard>();
		bankcardList = new ArrayList<BankCard>();
		BookDao bookDao = (BookDao) SpringContextUtil.getBean("bookDao");
		bankcardList  = bookDao.findBank();
		
		for (BankCard bankcard:bankcardList) {
			bankcardMap.put(bankcard.getBankcode(), bankcard);
			bankcardIdMap.put(bankcard.getId(), bankcard);
		}
		
		info("CacheBankCard init complete:" + bankcardIdMap.size());
	}

	@Override
	public void refresh() {
		init();
	}

}
