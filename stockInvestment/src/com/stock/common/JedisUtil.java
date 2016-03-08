package com.stock.common;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import org.apache.commons.pool.impl.GenericObjectPool;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.ShardedJedis;

/** 
 * @ClassName: JedisUtil 
 * @Description: redis
 * @author guosheng.zhu
 * @date 2013-5-3 下午06:37:07 
 *  
 */

public class JedisUtil {

	 /** 非切片客户端链接 */
    private static Jedis jedis;

    /** 非切片链接池 */
    private static JedisPool jedisPool;

    /** 切片客户端链接 */
    private static ShardedJedis shardedJedis;

    private static int MAX_ACTIVE = 1024;
    private static int MAX_IDLE = 200;
    private static int MAX_WAIT = 10000;
    private static boolean TEST_ON_BORROW = true;
    private static boolean TEST_ON_RETURN = true;
    
    
    public JedisUtil(){
    	
    }
    
    public static void Initializer(String ip, int port){
    	initialPool(ip, port);
    	//initialShardedPool(ip, port);
    	//shardedJedis = shardedJedisPool.getResource();
    	jedis = jedisPool.getResource();
    }
	
    private static void initialPool(String ip, int port) {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxActive(MAX_ACTIVE);
        config.setMaxIdle(MAX_IDLE);
        config.setMaxWait(MAX_WAIT);
        config.setTestOnBorrow(TEST_ON_BORROW);
        config.setTestOnReturn(TEST_ON_RETURN);
        config.setWhenExhaustedAction(GenericObjectPool.WHEN_EXHAUSTED_GROW);
        jedisPool = new JedisPool(config, ip, port, 10000);
    }
    
    public static Pipeline getPipeline(){
    	return jedis.pipelined();
    }
    
    public static Jedis getJedis(){
    	jedis = jedisPool.getResource();
    	return jedis;
    }
    
    public static JedisPool getJedisPool(){
    	return jedisPool;
    }
    
    public static ShardedJedis getShardedJedis(){
    	return shardedJedis;
    }
    
    public void setSharded(final String key, final String value){
    	shardedJedis.set(key, value);
    }
    
    public void setSharded(final byte[] key, final byte[] value){
    	shardedJedis.set(key, value);
    }
    
    public void setexSharded(final String key, final String value, final int expireTime){
    	shardedJedis.setex(key, expireTime, value);
    }
    
    public void setexSharded(final byte[] key, final byte[] value, final int expireTime){
    	shardedJedis.setex(key, expireTime, value);
    }
    
    public String getSharded(final String key){
    	return shardedJedis.get(key);
    }
    
    public byte[] getSharded(final byte[] key){
    	return shardedJedis.get(key);
    }
    
    
    public void delSharded(final String key){
    	shardedJedis.del(key);
    }
    
    public void set(final String key, final String value){
    	jedis.set(key, value);
    }
    
    public void set(final byte[] key, final byte[] value){
    	jedis.set(key, value);
    }
    
    public void setObject(final String key, final Object value){
    	try {
			jedis.set(key.getBytes(), getBytesFromObject(value));
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    public void setex(final String key, final String value, final int expireTime){
    	jedis.setex(key, expireTime, value);
    }
    
    public void setex(final byte[] key, final byte[] value, final int expireTime){
    	jedis.setex(key, expireTime, value);
    }
    
    public String get(final String key){
    	return jedis.get(key);
    }
    
    public byte[] get(final byte[] key){
    	return jedis.get(key);
    }
    
    public void del(final byte[] key){
    	jedis.del(key);
    }
    
    public void del(final String key){
    	jedis.del(key);
    }
    
    public void flashDb(){
    	jedis.flushDB();
    }
    
    public void flashMasterDb(){
    	shardedJedis.getShard("master").flushDB();
    }
    
    public void flashSlaveDb(){
    	shardedJedis.getShard("slave").flushDB();
    }
    
    public static Object getObjectFromBytes(byte[] objBytes) throws Exception{ 
        if (objBytes == null || objBytes.length == 0){ 
            return null; 
        } 
        ByteArrayInputStream bi = new ByteArrayInputStream(objBytes); 
        ObjectInputStream oi = new ObjectInputStream(bi); 
        return oi.readObject(); 
    } 
    
    public static byte[] getBytesFromObject(Object obj) throws Exception{ 
        if (obj == null){ 
            return null; 
        } 
        ByteArrayOutputStream bo = new ByteArrayOutputStream(); 
        ObjectOutputStream oo = new ObjectOutputStream(bo); 
        oo.writeObject(obj); 
        return bo.toByteArray(); 
    } 
    
}
