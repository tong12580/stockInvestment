package com.stock.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.stock.common.mybatis.Page;
import com.stock.pojo.BankCard;

@Repository
public interface BookDao {
	
	List<Map<String, Object>> findById(Map<String, Object> params);
	List<Map<String, Object>> findBy(Page<Map<String, Object>> params);
	List<BankCard> findBank();
}
