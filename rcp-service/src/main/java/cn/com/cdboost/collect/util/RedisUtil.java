package cn.com.cdboost.collect.util;

import cn.com.cdboost.collect.cache.*;
import cn.com.cdboost.collect.constant.RedisKeyConstant;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.*;

import java.util.*;

@Component
public class RedisUtil {
    private static final Logger logger = LoggerFactory.getLogger(RedisUtil.class);
  
    @Autowired
    private ShardedJedisPool shardedJedisPool;
  
    /**  
     * 设置一个key的过期时间（单位：秒）  
     *   
     * @param key  
     *            key值  
     * @param seconds  
     *            多少秒后过期  
     * @return 1：设置了过期时间 0：没有设置过期时间/不能设置过期时间  
     */  
    public long expire(String key, int seconds) {  
        if (key == null || key.equals("")) {  
            return 0;  
        }  
  
        ShardedJedis shardedJedis = null;
        try {  
            shardedJedis = shardedJedisPool.getResource();  
            return shardedJedis.expire(key, seconds);  
        } catch (Exception ex) {  
            logger.error("EXPIRE error[key=" + key + " seconds=" + seconds + "]" + ex.getMessage(), ex);  
            returnBrokenResource(shardedJedis);  
        } finally {  
            returnResource(shardedJedis);  
        }  
        return 0;  
    }  
  
    /**  
     * 设置一个key在某个时间点过期  
     *   
     * @param key  
     *            key值  
     * @param unixTimestamp  
     *            unix时间戳，从1970-01-01 00:00:00开始到现在的秒数  
     * @return 1：设置了过期时间 0：没有设置过期时间/不能设置过期时间  
     */  
    public long expireAt(String key, long unixTimestamp) {  
        if (key == null || key.equals("")) {  
            return 0;  
        }  
  
        ShardedJedis shardedJedis = null;  
        try {  
            shardedJedis = shardedJedisPool.getResource();  
            return shardedJedis.expireAt(key, unixTimestamp);  
        } catch (Exception ex) {  
            logger.error("EXPIRE error[key=" + key + " unixTimestamp=" + unixTimestamp + "]" + ex.getMessage(), ex);  
            returnBrokenResource(shardedJedis);  
        } finally {  
            returnResource(shardedJedis);  
        }  
        return 0;  
    }  
  
    /**  
     * 截断一个List  
     *   
     * @param key  
     *            列表key  
     * @param start  
     *            开始位置 从0开始  
     * @param end  
     *            结束位置  
     * @return 状态码  
     */  
    public String trimList(String key, long start, long end) {  
        if (key == null || key.equals("")) {  
            return "-";  
        }  
        ShardedJedis shardedJedis = null;  
        try {  
            shardedJedis = shardedJedisPool.getResource();  
            return shardedJedis.ltrim(key, start, end);  
        } catch (Exception ex) {  
            logger.error("LTRIM 出错[key=" + key + " start=" + start + " end=" + end + "]" + ex.getMessage(), ex);  
            returnBrokenResource(shardedJedis);  
        } finally {  
            returnResource(shardedJedis);  
        }  
        return "-";  
    }  
  
    /**  
     * 检查Set长度  
     *   
     * @param key  
     * @return  
     */  
    public long countSet(String key) {  
        if (key == null) {  
            return 0;  
        }  
        ShardedJedis shardedJedis = null;  
        try {  
            shardedJedis = shardedJedisPool.getResource();  
            return shardedJedis.scard(key);  
        } catch (Exception ex) {  
            logger.error("countSet error.", ex);  
            returnBrokenResource(shardedJedis);  
        } finally {  
            returnResource(shardedJedis);  
        }  
        return 0;  
    }  
  
    /**  
     * 添加到Set中（同时设置过期时间）  
     *   
     * @param key  
     *            key值  
     * @param seconds  
     *            过期时间 单位s  
     * @param value  
     * @return 成功true  
     */  
    public boolean addSet(String key, int seconds, String... value) {  
        boolean result = addSet(key, value);  
        if (result) {  
            long i = expire(key, seconds);  
            return i == 1;  
        }  
        return false;  
    }  
  
    /**  
     * 添加到Set中  
     *   
     * @param key  
     * @param value  
     * @return  
     */  
    public boolean addSet(String key, String... value) {  
        if (key == null || value == null) {  
            return false;  
        }  
        ShardedJedis shardedJedis = null;  
        try {  
            shardedJedis = shardedJedisPool.getResource();  
            shardedJedis.sadd(key, value);  
            return true;  
        } catch (Exception ex) {  
            logger.error("setList error.", ex);  
            returnBrokenResource(shardedJedis);  
        } finally {  
            returnResource(shardedJedis);  
        }  
        return false;  
    }  
  
    /**  
     * @param key  
     * @param value  
     * @return 判断值是否包含在set中  
     */  
    public boolean containsInSet(String key, String value) {  
        if (key == null || value == null) {  
            return false;  
        }  
        ShardedJedis shardedJedis = null;  
        try {  
            shardedJedis = shardedJedisPool.getResource();  
            return shardedJedis.sismember(key, value);  
        } catch (Exception ex) {  
            logger.error("setList error.", ex);  
            returnBrokenResource(shardedJedis);  
        } finally {  
            returnResource(shardedJedis);  
        }  
        return false;  
    }  
  
    /**  
     * 获取Set  
     *   
     * @param key  
     * @return  
     */  
    public Set<String> getSet(String key) {
        ShardedJedis shardedJedis = null;  
        try {  
            shardedJedis = shardedJedisPool.getResource();  
            return shardedJedis.smembers(key);  
        } catch (Exception ex) {  
            logger.error("getList error.", ex);  
            returnBrokenResource(shardedJedis);  
        } finally {  
            returnResource(shardedJedis);  
        }  
        return null;  
    }  
  
    /**  
     * 从set中删除value  
     *   
     * @param key  
     * @return  
     */  
    public boolean removeSetValue(String key, String... value) {  
        ShardedJedis shardedJedis = null;  
        try {  
            shardedJedis = shardedJedisPool.getResource();  
            shardedJedis.srem(key, value);  
            return true;  
        } catch (Exception ex) {  
            logger.error("getList error.", ex);  
            returnBrokenResource(shardedJedis);  
        } finally {  
            returnResource(shardedJedis);  
        }  
        return false;  
    }  
  
    /**  
     * 从list中删除value 默认count 1  
     *   
     * @param key  
     * @param values  
     *            值list  
     * @return  
     */  
    public int removeListValue(String key, List<String> values) {  
        return removeListValue(key, 1, values);  
    }  
  
    /**  
     * 从list中删除value  
     *   
     * @param key  
     * @param count  
     * @param values  
     *            值list  
     * @return  
     */  
    public int removeListValue(String key, long count, List<String> values) {  
        int result = 0;  
        if (values != null && values.size() > 0) {  
            for (String value : values) {  
                if (removeListValue(key, count, value)) {  
                    result++;  
                }  
            }  
        }  
        return result;  
    }  
  
    /**  
     * 从list中删除value  
     *   
     * @param key  
     * @param count  
     *            要删除个数  
     * @param value  
     * @return  
     */  
    public boolean removeListValue(String key, long count, String value) {  
        ShardedJedis shardedJedis = null;  
        try {  
            shardedJedis = shardedJedisPool.getResource();  
            shardedJedis.lrem(key, count, value);  
            return true;  
        } catch (Exception ex) {  
            logger.error("getList error.", ex);  
            returnBrokenResource(shardedJedis);  
        } finally {  
            returnResource(shardedJedis);  
        }  
        return false;  
    }  
  
    /**  
     * 截取List  
     *   
     * @param key  
     * @param start  
     *            起始位置  
     * @param end  
     *            结束位置  
     * @return  
     */  
    public List<String> rangeList(String key, long start, long end) {  
        if (key == null || key.equals("")) {  
            return null;  
        }  
        ShardedJedis shardedJedis = null;  
        try {  
            shardedJedis = shardedJedisPool.getResource();  
            return shardedJedis.lrange(key, start, end);  
        } catch (Exception ex) {  
            logger.error("rangeList 出错[key=" + key + " start=" + start + " end=" + end + "]" + ex.getMessage(), ex);  
            returnBrokenResource(shardedJedis);  
        } finally {  
            returnResource(shardedJedis);  
        }  
        return null;  
    }  
  
    /**  
     * 检查List长度  
     *   
     * @param key  
     * @return  
     */  
    public long countList(String key) {  
        if (key == null) {  
            return 0;  
        }  
        ShardedJedis shardedJedis = null;  
        try {  
            shardedJedis = shardedJedisPool.getResource();  
            return shardedJedis.llen(key);  
        } catch (Exception ex) {  
            logger.error("countList error.", ex);  
            returnBrokenResource(shardedJedis);  
        } finally {  
            returnResource(shardedJedis);  
        }  
        return 0;  
    }  
  
    /**  
     * 添加到List中（同时设置过期时间）  
     *   
     * @param key  
     *            key值  
     * @param seconds  
     *            过期时间 单位s  
     * @param value  
     * @return  
     */  
    public boolean addList(String key, int seconds, String... value) {  
        boolean result = addList(key, value);  
        if (result) {  
            long i = expire(key, seconds);  
            return i == 1;  
        }  
        return false;  
    }  
  
    /**  
     * 添加到List  
     *   
     * @param key  
     * @param value  
     * @return  
     */  
    public boolean addList(String key, String... value) {  
        if (key == null || value == null) {  
            return false;  
        }  
        ShardedJedis shardedJedis = null;  
        try {  
            shardedJedis = shardedJedisPool.getResource();  
            shardedJedis.lpush(key, value);  
            return true;  
        } catch (Exception ex) {  
            logger.error("setList error.", ex);  
            returnBrokenResource(shardedJedis);  
        } finally {  
            returnResource(shardedJedis);  
        }  
        return false;  
    }  
  
    /**  
     * 添加到List(只新增)  
     *   
     * @param key  
     * @return
     */  
    public boolean addList(String key, List<String> list) {  
        if (key == null || list == null || list.size() == 0) {  
            return false;  
        }  
        for (String value : list) {  
            addList(key, value);  
        }  
        return true;  
    }  
  
//    /**
//     * 获取List
//     *
//     * @param key
//     * @return
//     */
//    public List<String> getList(String key) {
//        ShardedJedis shardedJedis = null;
//        try {
//            shardedJedis = shardedJedisPool.getResource();
//            return shardedJedis.lrange(key, 0, -1);
//        } catch (Exception ex) {
//            logger.error("getList error.", ex);
//            returnBrokenResource(shardedJedis);
//        } finally {
//            returnResource(shardedJedis);
//        }
//        return null;
//    }
  
    /**  
     * 设置HashSet对象  
     *  
     * @param domain  
     *            域名  
     * @param key  
     *            键值  
     * @param value  
     *            Json String or String value  
     * @return  
     */  
    public boolean setHSet(String domain, String key, String value) {  
        if (value == null)  
            return false;  
        ShardedJedis shardedJedis = null;  
        try {  
            shardedJedis = shardedJedisPool.getResource();  
            shardedJedis.hset(domain, key, value);  
            return true;  
        } catch (Exception ex) {  
            logger.error("setHSet error.", ex);  
            returnBrokenResource(shardedJedis);  
        } finally {  
            returnResource(shardedJedis);  
        }  
        return false;  
    }  
  
    /**  
     * 获得HashSet对象  
     *  
     * @param domain  
     *            域名  
     * @param key  
     *            键值  
     * @return Json String or String value  
     */  
    public String getHSet(String domain, String key) {  
        ShardedJedis shardedJedis = null;  
        try {  
            shardedJedis = shardedJedisPool.getResource();  
            return shardedJedis.hget(domain, key);  
        } catch (Exception ex) {  
            logger.error("getHSet error.", ex);  
            returnBrokenResource(shardedJedis);  
        } finally {  
            returnResource(shardedJedis);  
        }  
        return null;  
    }  
  
    /**  
     * 删除HashSet对象  
     *  
     * @param domain  
     *            域名  
     * @param key  
     *            键值  
     * @return 删除的记录数  
     */  
    public long delHSet(String domain, String key) {  
        ShardedJedis shardedJedis = null;  
        long count = 0;  
        try {  
            shardedJedis = shardedJedisPool.getResource();  
            count = shardedJedis.hdel(domain, key);  
        } catch (Exception ex) {  
            logger.error("delHSet error.", ex);  
            returnBrokenResource(shardedJedis);  
        } finally {  
            returnResource(shardedJedis);  
        }  
        return count;  
    }  
  
    /**  
     * 删除HashSet对象  
     *  
     * @param domain  
     *            域名  
     * @param key  
     *            键值  
     * @return 删除的记录数  
     */  
    public long delHSet(String domain, String... key) {  
        ShardedJedis shardedJedis = null;  
        long count = 0;  
        try {  
            shardedJedis = shardedJedisPool.getResource();  
            count = shardedJedis.hdel(domain, key);  
        } catch (Exception ex) {  
            logger.error("delHSet error.", ex);  
            returnBrokenResource(shardedJedis);  
        } finally {  
            returnResource(shardedJedis);  
        }  
        return count;  
    }  
  
    /**  
     * 判断key是否存在  
     *  
     * @param domain  
     *            域名  
     * @param key  
     *            键值  
     * @return  
     */  
    public boolean existsHSet(String domain, String key) {  
        ShardedJedis shardedJedis = null;  
        boolean isExist = false;  
        try {  
            shardedJedis = shardedJedisPool.getResource();  
            isExist = shardedJedis.hexists(domain, key);  
        } catch (Exception ex) {  
            logger.error("existsHSet error.", ex);  
            returnBrokenResource(shardedJedis);  
        } finally {  
            returnResource(shardedJedis);  
        }  
        return isExist;  
    }  
  
    /**  
     * 全局扫描hset  
     *  
     * @param match  
     *            field匹配模式  
     * @return  
     */  
    public List<Map.Entry<String, String>> scanHSet(String domain, String match) {
        ShardedJedis shardedJedis = null;  
        try {  
            int cursor = 0;  
            shardedJedis = shardedJedisPool.getResource();  
            ScanParams scanParams = new ScanParams();
            scanParams.match(match);  
            Jedis jedis = shardedJedis.getShard(domain);
            ScanResult<Map.Entry<String, String>> scanResult;
            List<Map.Entry<String, String>> list = new ArrayList<Map.Entry<String, String>>();
            do {  
                scanResult = jedis.hscan(domain, String.valueOf(cursor), scanParams);  
                list.addAll(scanResult.getResult());  
                cursor = Integer.parseInt(scanResult.getStringCursor());  
            } while (cursor > 0);  
            return list;  
        } catch (Exception ex) {  
            logger.error("scanHSet error.", ex);  
            returnBrokenResource(shardedJedis);  
        } finally {  
            returnResource(shardedJedis);  
        }  
        return null;  
    }  
  
    /**  
     * 返回 domain 指定的哈希集中所有字段的value值  
     *  
     * @param domain  
     * @return  
     */  
  
    public List<String> hvals(String domain) {  
        ShardedJedis shardedJedis = null;  
        List<String> retList = null;  
        try {  
            shardedJedis = shardedJedisPool.getResource();  
            retList = shardedJedis.hvals(domain);  
        } catch (Exception ex) {  
            logger.error("hvals error.", ex);  
            returnBrokenResource(shardedJedis);  
        } finally {  
            returnResource(shardedJedis);  
        }  
        return retList;  
    }  
  
    /**  
     * 返回 domain 指定的哈希集中所有字段的key值  
     *  
     * @param domain  
     * @return  
     */  
  
    public Set<String> hkeys(String domain) {  
        ShardedJedis shardedJedis = null;  
        Set<String> retList = null;  
        try {  
            shardedJedis = shardedJedisPool.getResource();  
            retList = shardedJedis.hkeys(domain);  
        } catch (Exception ex) {  
            logger.error("hkeys error.", ex);  
            returnBrokenResource(shardedJedis);  
        } finally {  
            returnResource(shardedJedis);  
        }  
        return retList;  
    }  
  
    /**  
     * 返回 domain 指定的哈希key值总数  
     *  
     * @param domain  
     * @return  
     */  
    public long lenHset(String domain) {  
        ShardedJedis shardedJedis = null;  
        long retList = 0;  
        try {  
            shardedJedis = shardedJedisPool.getResource();  
            retList = shardedJedis.hlen(domain);  
        } catch (Exception ex) {  
            logger.error("hkeys error.", ex);  
            returnBrokenResource(shardedJedis);  
        } finally {  
            returnResource(shardedJedis);  
        }  
        return retList;  
    }  
  
    /**  
     * 设置排序集合  
     *  
     * @param key  
     * @param score  
     * @param value  
     * @return  
     */  
    public boolean setSortedSet(String key, long score, String value) {  
        ShardedJedis shardedJedis = null;  
        try {  
            shardedJedis = shardedJedisPool.getResource();  
            shardedJedis.zadd(key, score, value);  
            return true;  
        } catch (Exception ex) {  
            logger.error("setSortedSet error.", ex);  
            returnBrokenResource(shardedJedis);  
        } finally {  
            returnResource(shardedJedis);  
        }  
        return false;  
    }  
  
    /**  
     * 获得排序集合  
     *  
     * @param key  
     * @param startScore  
     * @param endScore  
     * @param orderByDesc  
     * @return  
     */  
    public Set<String> getSoredSet(String key, long startScore, long endScore, boolean orderByDesc) {  
        ShardedJedis shardedJedis = null;  
        try {  
            shardedJedis = shardedJedisPool.getResource();  
            if (orderByDesc) {  
                return shardedJedis.zrevrangeByScore(key, endScore, startScore);  
            } else {  
                return shardedJedis.zrangeByScore(key, startScore, endScore);  
            }  
        } catch (Exception ex) {  
            logger.error("getSoredSet error.", ex);  
            returnBrokenResource(shardedJedis);  
        } finally {  
            returnResource(shardedJedis);  
        }  
        return null;  
    }  
  
    /**  
     * 计算排序长度  
     *  
     * @param key  
     * @param startScore  
     * @param endScore  
     * @return  
     */  
    public long countSoredSet(String key, long startScore, long endScore) {  
        ShardedJedis shardedJedis = null;  
        try {  
            shardedJedis = shardedJedisPool.getResource();  
            Long count = shardedJedis.zcount(key, startScore, endScore);  
            return count == null ? 0L : count;  
        } catch (Exception ex) {  
            logger.error("countSoredSet error.", ex);  
            returnBrokenResource(shardedJedis);  
        } finally {  
            returnResource(shardedJedis);  
        }  
        return 0L;  
    }  
  
    /**  
     * 删除排序集合  
     *  
     * @param key  
     * @param value  
     * @return  
     */  
    public boolean delSortedSet(String key, String value) {  
        ShardedJedis shardedJedis = null;  
        try {  
            shardedJedis = shardedJedisPool.getResource();  
            long count = shardedJedis.zrem(key, value);  
            return count > 0;  
        } catch (Exception ex) {  
            logger.error("delSortedSet error.", ex);  
            returnBrokenResource(shardedJedis);  
        } finally {  
            returnResource(shardedJedis);  
        }  
        return false;  
    }  
  
    /**  
     * 获得排序集合  
     *  
     * @param key  
     * @param startRange  
     * @param endRange  
     * @param orderByDesc  
     * @return  
     */  
    public Set<String> getSoredSetByRange(String key, int startRange, int endRange, boolean orderByDesc) {  
        ShardedJedis shardedJedis = null;  
        try {  
            shardedJedis = shardedJedisPool.getResource();  
            if (orderByDesc) {  
                return shardedJedis.zrevrange(key, startRange, endRange);  
            } else {  
                return shardedJedis.zrange(key, startRange, endRange);  
            }  
        } catch (Exception ex) {  
            logger.error("getSoredSetByRange error.", ex);  
            returnBrokenResource(shardedJedis);  
        } finally {  
            returnResource(shardedJedis);  
        }  
        return null;  
    }  
  
    /**  
     * 获得排序打分  
     *  
     * @param key  
     * @return  
     */  
    public Double getScore(String key, String member) {  
        ShardedJedis shardedJedis = null;  
        try {  
            shardedJedis = shardedJedisPool.getResource();  
            return shardedJedis.zscore(key, member);  
        } catch (Exception ex) {  
            logger.error("getSoredSet error.", ex);  
            returnBrokenResource(shardedJedis);  
        } finally {  
            returnResource(shardedJedis);  
        }  
        return null;  
    }  
  
    public boolean set(String key, String value, int second) {  
        ShardedJedis shardedJedis = null;  
        try {  
            shardedJedis = shardedJedisPool.getResource();  
            shardedJedis.setex(key, second, value);  
            return true;  
        } catch (Exception ex) {  
            logger.error("set error.", ex);  
            returnBrokenResource(shardedJedis);  
        } finally {  
            returnResource(shardedJedis);  
        }  
        return false;  
    }  
  
    public boolean set(String key, String value) {  
        ShardedJedis shardedJedis = null;  
        try {  
            shardedJedis = shardedJedisPool.getResource();  
            shardedJedis.set(key, value);  
            return true;  
        } catch (Exception ex) {  
            logger.error("set error.", ex);  
            returnBrokenResource(shardedJedis);  
        } finally {  
            returnResource(shardedJedis);  
        }  
        return false;  
    }

    public boolean set(byte[] key, byte[] value) {
        ShardedJedis shardedJedis = null;
        try {
            shardedJedis = shardedJedisPool.getResource();
            shardedJedis.set(key, value);
            return true;
        } catch (Exception ex) {
            logger.error("set error.", ex);
            returnBrokenResource(shardedJedis);
        } finally {
            returnResource(shardedJedis);
        }
        return false;
    }

    public Object getValue(String key) {  
        Object ret = null;  
        ShardedJedis jedis = shardedJedisPool.getResource();  
        try {  
  
            // 去redis中取回序列化后的对象  
            byte[] obj = jedis.get(key.getBytes());  
  
            // 取回的对象反序列化  
            if (obj != null) {  
//                ret = SerializeUtil2.unserialize(obj);
                ret = SerializeUtil.unserializeForList(obj);
            }  
  
        } catch (Exception e) {  
            jedis.close();  
        } finally {  
            jedis.close();  
        }  
        return ret;  
    }

    public String get(String key, String defaultValue) {  
        ShardedJedis shardedJedis = null;  
        try {  
            shardedJedis = shardedJedisPool.getResource();  
            return shardedJedis.get(key) == null ? defaultValue : shardedJedis.get(key);  
        } catch (Exception ex) {  
            logger.error("get error.", ex);  
            returnBrokenResource(shardedJedis);  
        } finally {  
            returnResource(shardedJedis);  
        }  
        return defaultValue;  
    }  
  
    public boolean del(String key) {  
        ShardedJedis shardedJedis = null;  
        try {  
            shardedJedis = shardedJedisPool.getResource();  
            shardedJedis.del(key);  
            return true;  
        } catch (Exception ex) {  
            logger.error("del error.", ex);  
            returnBrokenResource(shardedJedis);  
        } finally {  
            returnResource(shardedJedis);  
        }  
        return false;  
    }  
  
    public long incr(String key) {  
        ShardedJedis shardedJedis = null;  
        try {  
            shardedJedis = shardedJedisPool.getResource();  
            return shardedJedis.incr(key);  
        } catch (Exception ex) {  
            logger.error("incr error.", ex);  
            returnBrokenResource(shardedJedis);  
        } finally {  
            returnResource(shardedJedis);  
        }  
        return 0;  
    }  
  
    public long decr(String key) {  
        ShardedJedis shardedJedis = null;  
        try {  
            shardedJedis = shardedJedisPool.getResource();  
            return shardedJedis.decr(key);  
        } catch (Exception ex) {  
            logger.error("incr error.", ex);  
            returnBrokenResource(shardedJedis);  
        } finally {  
            returnResource(shardedJedis);  
        }  
        return 0;  
    }

    private void returnBrokenResource(ShardedJedis shardedJedis) {  
        if (shardedJedis == null) {  
            return;  
        }  
        try {  
            // 容错  
            shardedJedis.close();  
            //shardedJedisPool.returnBrokenResource(shardedJedis);  
        } catch (Exception e) {  
            logger.error("returnBrokenResource error.", e);  
        }  
    }  
  
    private void returnResource(ShardedJedis shardedJedis) {  
        try {  
             shardedJedis.close();  
            //shardedJedisPool.returnResource(shardedJedis);  
        } catch (Exception e) {  
            logger.error("returnResource error.", e);  
        }  
    }


    /**
     * 设置List集合
     * @param key
     * @param list
     */
    public boolean setList(String key ,List<?> list){
        ShardedJedis shardedJedis = null;
        try {
            shardedJedis = shardedJedisPool.getResource();
            shardedJedis.set(key.getBytes(), SerializeUtil3.serializeList(list));
            return true;
        } catch (Exception ex) {
            logger.error("set error.", ex);
            returnBrokenResource(shardedJedis);
        } finally {
            returnResource(shardedJedis);
        }
        return false;
    }

    /**
     * 获取List集合
     * @param key
     * @return
     */
    public List<?> getList(String key){
        ShardedJedis shardedJedis = null;
        try {
            shardedJedis = shardedJedisPool.getResource();
            return shardedJedis.get(key) == null ? null : SerializeUtil3.unserializeList(shardedJedis.get(key.getBytes()));
        } catch (Exception ex) {
            logger.error("get error.", ex);
            returnBrokenResource(shardedJedis);
        } finally {
            returnResource(shardedJedis);
        }
        return null;
    }

    public Map<String,DeviceRelationCacheVo> getDeviceRelationMap(Set<String> keys) {
        ShardedJedis shardedJedis = null;
        try {
            shardedJedis = shardedJedisPool.getResource();
            ShardedJedisPipeline pipelined = shardedJedis.pipelined();
            Map<String,Response<String>> result = new HashMap<>();
            for (String s : keys) {
                result.put(s,pipelined.get(s));
            }

            pipelined.sync();
            Map<String,DeviceRelationCacheVo> dataMap = new HashMap<>();
            for (String key : result.keySet()) {
                String value = result.get(key).get();
                DeviceRelationCacheVo relationCacheVo = JSON.parseObject(value, DeviceRelationCacheVo.class);
                String cno = key.replace(RedisKeyConstant.DEVICE_RELATION_PREFIX,"");
                dataMap.put(cno,relationCacheVo);
            }
            return dataMap;
        } catch (Exception ex) {
            logger.error("get error.", ex);
            returnBrokenResource(shardedJedis);
        } finally {
            returnResource(shardedJedis);
        }
        return null;
    }

    public Map<String,List<String>> queryDeviceRelationChildMap(Set<String> keys) {
        ShardedJedis shardedJedis = null;
        try {
            shardedJedis = shardedJedisPool.getResource();
            ShardedJedisPipeline pipelined = shardedJedis.pipelined();
            Map<String,Response<String>> result = new HashMap<>();
            for (String s : keys) {
                result.put(s,pipelined.get(s));
            }

            pipelined.sync();
            Map<String,List<String>> dataMap = new HashMap<>();
            for (String key : result.keySet()) {
                String value = result.get(key).get();
                List<String> list = JSON.parseArray(value, String.class);
                String cno = key.replace(RedisKeyConstant.DEVICE_RELATION_CHILD_PREFIX,"");
                dataMap.put(cno,list);
            }
            return dataMap;
        } catch (Exception ex) {
            logger.error("get error.", ex);
            returnBrokenResource(shardedJedis);
        } finally {
            returnResource(shardedJedis);
        }
        return null;
    }

    public Map<String,DeviceMapCacheVo> queryDeviceMapCacheMap(Set<String> keys) {
        ShardedJedis shardedJedis = null;
        try {
            shardedJedis = shardedJedisPool.getResource();
            ShardedJedisPipeline pipelined = shardedJedis.pipelined();
            Map<String,Response<String>> result = new HashMap<>();
            for (String s : keys) {
                result.put(s,pipelined.get(s));
            }

            pipelined.sync();
            Map<String,DeviceMapCacheVo> dataMap = new HashMap<>();
            for (String key : result.keySet()) {
                String value = result.get(key).get();
                DeviceMapCacheVo cacheVo = JSON.parseObject(value, DeviceMapCacheVo.class);
                String cno = key.replace(RedisKeyConstant.DEVICE_MAP_PREFIX,"");
                dataMap.put(cno,cacheVo);
            }
            return dataMap;
        } catch (Exception ex) {
            logger.error("get error.", ex);
            returnBrokenResource(shardedJedis);
        } finally {
            returnResource(shardedJedis);
        }
        return null;
    }

    public  Map<String,DeviceCacheVo> getDeviceCacheMap(Set<String> keys) {
        ShardedJedis shardedJedis = null;
        try {
            shardedJedis = shardedJedisPool.getResource();
            ShardedJedisPipeline pipelined = shardedJedis.pipelined();
            Map<String,Response<String>> result = new HashMap<>();
            for (String s : keys) {
                result.put(s,pipelined.get(s));
            }

            pipelined.sync();
            Map<String,DeviceCacheVo> dataMap = new HashMap<>();
            for (String key : result.keySet()) {
                String value = result.get(key).get();
                DeviceCacheVo cacheVo = JSON.parseObject(value, DeviceCacheVo.class);
                String cno = key.replace(RedisKeyConstant.DEVICE_PREFIX,"");
                dataMap.put(cno,cacheVo);
            }
            return dataMap;
        } catch (Exception ex) {
            logger.error("get error.", ex);
            returnBrokenResource(shardedJedis);
        } finally {
            returnResource(shardedJedis);
        }
        return null;
    }

    public Map<String,DeviceStateCacheVo> getDeviceStateMap(Set<String> keys) {
        ShardedJedis shardedJedis = null;
        try {
            shardedJedis = shardedJedisPool.getResource();
            ShardedJedisPipeline pipelined = shardedJedis.pipelined();
            Map<String,Response<String>> result = new HashMap<>();
            for (String s : keys) {
                result.put(s,pipelined.get(s));
            }

            pipelined.sync();
            Map<String,DeviceStateCacheVo> dataMap = new HashMap<>();
            for (String key : result.keySet()) {
                String value = result.get(key).get();
                DeviceStateCacheVo cacheVo = JSON.parseObject(value, DeviceStateCacheVo.class);
                String cno = key.replace(RedisKeyConstant.DEVICE_STATE_PREFIX,"");
                dataMap.put(cno,cacheVo);
            }
            return dataMap;
        } catch (Exception ex) {
            logger.error("get error.", ex);
            returnBrokenResource(shardedJedis);
        } finally {
            returnResource(shardedJedis);
        }
        return null;
    }

    public Map<String,CustomerCacheVo> queryCustomerCacheMap(Set<String> keys) {
        ShardedJedis shardedJedis = null;
        try {
            shardedJedis = shardedJedisPool.getResource();
            ShardedJedisPipeline pipelined = shardedJedis.pipelined();
            Map<String,Response<String>> result = new HashMap<>();
            for (String s : keys) {
                result.put(s,pipelined.get(s));
            }

            pipelined.sync();
            Map<String,CustomerCacheVo> dataMap = new HashMap<>();
            for (String key : result.keySet()) {
                String value = result.get(key).get();
                CustomerCacheVo cacheVo = JSON.parseObject(value, CustomerCacheVo.class);
                String customerNo = key.replace(RedisKeyConstant.CUSTOMER_PREFIX,"");
                dataMap.put(customerNo,cacheVo);
            }
            return dataMap;
        } catch (Exception ex) {
            logger.error("get error.", ex);
            returnBrokenResource(shardedJedis);
        } finally {
            returnResource(shardedJedis);
        }
        return null;
    }

    public Map<Long,OrgCacheVo> queryOrgCacheMap(Set<String> keys) {
        ShardedJedis shardedJedis = null;
        try {
            shardedJedis = shardedJedisPool.getResource();
            ShardedJedisPipeline pipelined = shardedJedis.pipelined();
            Map<String,Response<String>> result = new HashMap<>();
            for (String s : keys) {
                result.put(s,pipelined.get(s));
            }

            pipelined.sync();
            Map<Long,OrgCacheVo> dataMap = new HashMap<>();
            for (String key : result.keySet()) {
                String value = result.get(key).get();
                OrgCacheVo cacheVo = JSON.parseObject(value, OrgCacheVo.class);
                String orgNo = key.replace(RedisKeyConstant.ORG_PREFIX,"");
                dataMap.put(Long.valueOf(orgNo),cacheVo);
            }
            return dataMap;
        } catch (Exception ex) {
            logger.error("get error.", ex);
            returnBrokenResource(shardedJedis);
        } finally {
            returnResource(shardedJedis);
        }
        return null;
    }

    public Map<Long,MenuCacheVo> queryMenuCacheMap(Set<String> keys) {
        ShardedJedis shardedJedis = null;
        try {
            shardedJedis = shardedJedisPool.getResource();
            ShardedJedisPipeline pipelined = shardedJedis.pipelined();
            Map<String,Response<String>> result = new HashMap<>();
            for (String s : keys) {
                result.put(s,pipelined.get(s));
            }

            pipelined.sync();
            Map<Long,MenuCacheVo> dataMap = new HashMap<>();
            for (String key : result.keySet()) {
                String value = result.get(key).get();
                MenuCacheVo cacheVo = JSON.parseObject(value, MenuCacheVo.class);
                String menuId = key.replace(RedisKeyConstant.MENU_PREFIX,"");
                dataMap.put(Long.valueOf(menuId),cacheVo);
            }
            return dataMap;
        } catch (Exception ex) {
            logger.error("get error.", ex);
            returnBrokenResource(shardedJedis);
        } finally {
            returnResource(shardedJedis);
        }
        return null;
    }

    public Map<Long,List<Long>> queryParentOrgMap(Set<String> keys) {
        ShardedJedis shardedJedis = null;
        try {
            shardedJedis = shardedJedisPool.getResource();
            ShardedJedisPipeline pipelined = shardedJedis.pipelined();
            Map<String,Response<String>> result = new HashMap<>();
            for (String s : keys) {
                result.put(s,pipelined.get(s));
            }

            pipelined.sync();
            Map<Long,List<Long>> dataMap = new HashMap<>();
            for (String key : result.keySet()) {
                String value = result.get(key).get();
                List<Long> parentOrgList = JSON.parseArray(value, Long.class);
                String orgNo = key.replace(RedisKeyConstant.ORG_PARENT_PREFIX,"");
                dataMap.put(Long.valueOf(orgNo),parentOrgList);
            }
            return dataMap;
        } catch (Exception ex) {
            logger.error("get error.", ex);
            returnBrokenResource(shardedJedis);
        } finally {
            returnResource(shardedJedis);
        }
        return null;
    }

    public Map<String,Boolean> queryDeviceChildMap(Set<String> keys) {
        ShardedJedis shardedJedis = null;
        try {
            shardedJedis = shardedJedisPool.getResource();
            ShardedJedisPipeline pipelined = shardedJedis.pipelined();
            Map<String,Response<String>> result = new HashMap<>();
            for (String s : keys) {
                result.put(s,pipelined.get(s));
            }

            pipelined.sync();
            Map<String,Boolean> dataMap = new HashMap<>();
            for (String key : result.keySet()) {
                String value = result.get(key).get();
                Boolean flag = JSON.parseObject(value, Boolean.class);
                String cno = key.replace(RedisKeyConstant.DEVICE_CHILD_PREFIX,"");
                dataMap.put(cno,flag);
            }
            return dataMap;
        } catch (Exception ex) {
            logger.error("get error.", ex);
            returnBrokenResource(shardedJedis);
        } finally {
            returnResource(shardedJedis);
        }
        return null;
    }

    public Map<Long,Boolean> queryOrgChildMap(Set<String> keys) {
        ShardedJedis shardedJedis = null;
        try {
            shardedJedis = shardedJedisPool.getResource();
            ShardedJedisPipeline pipelined = shardedJedis.pipelined();
            Map<String,Response<String>> result = new HashMap<>();
            for (String s : keys) {
                result.put(s,pipelined.get(s));
            }

            pipelined.sync();
            Map<Long,Boolean> dataMap = new HashMap<>();
            for (String key : result.keySet()) {
                String value = result.get(key).get();
                Boolean flag = JSON.parseObject(value, Boolean.class);
                String orgNo = key.replace(RedisKeyConstant.ORG_EXIST_CHILD_PREFIX,"");
                dataMap.put(Long.valueOf(orgNo),flag);
            }
            return dataMap;
        } catch (Exception ex) {
            logger.error("get error.", ex);
            returnBrokenResource(shardedJedis);
        } finally {
            returnResource(shardedJedis);
        }
        return null;
    }

    public Map<Long,List<Long>> queryDataOrgMap(Set<String> keys) {
        ShardedJedis shardedJedis = null;
        try {
            shardedJedis = shardedJedisPool.getResource();
            ShardedJedisPipeline pipelined = shardedJedis.pipelined();
            Map<String,Response<String>> result = new HashMap<>();
            for (String s : keys) {
                result.put(s,pipelined.get(s));
            }

            pipelined.sync();
            Map<Long,List<Long>> dataMap = new HashMap<>();
            for (String key : result.keySet()) {
                String value = result.get(key).get();
                List<Long> list = JSON.parseArray(value, Long.class);
                String orgNo = key.replace(RedisKeyConstant.ORG_CHILD_PREFIX,"");
                dataMap.put(Long.valueOf(orgNo),list);
            }
            return dataMap;
        } catch (Exception ex) {
            logger.error("get error.", ex);
            returnBrokenResource(shardedJedis);
        } finally {
            returnResource(shardedJedis);
        }
        return null;
    }


    /**
     * 批量设置用户缓存信息
     * @param customerCacheVos
     * @return
     */
    public boolean setCustomerCacheList(List<CustomerCacheVo> customerCacheVos) {
        ShardedJedis shardedJedis = null;
        try {
            shardedJedis = shardedJedisPool.getResource();
            ShardedJedisPipeline pipelined = shardedJedis.pipelined();
            for (CustomerCacheVo cacheVo : customerCacheVos) {
                String key = RedisKeyConstant.CUSTOMER_PREFIX + cacheVo.getCustomerNo();
                pipelined.set(key,JSON.toJSONString(cacheVo));
            }
            pipelined.sync();
            return true;
        } catch (Exception ex) {
            logger.error("set error.", ex);
            returnBrokenResource(shardedJedis);
        } finally {
            returnResource(shardedJedis);
        }
        return false;
    }

    /**
     * 批量设置设备关联表缓存信息
     * @param lists
     * @return
     */
    public boolean setDeviceRelationList(List<DeviceRelationCacheVo> lists) {
        ShardedJedis shardedJedis = null;
        try {
            shardedJedis = shardedJedisPool.getResource();
            ShardedJedisPipeline pipelined = shardedJedis.pipelined();
            for (DeviceRelationCacheVo list : lists) {
                String key = RedisKeyConstant.DEVICE_RELATION_PREFIX + list.getMeterCno();
                pipelined.set(key,JSON.toJSONString(list));
            }
            pipelined.sync();
            return true;
        } catch (Exception ex) {
            logger.error("set error.", ex);
            returnBrokenResource(shardedJedis);
        } finally {
            returnResource(shardedJedis);
        }
        return false;
    }

    public boolean setDeviceRelationChildMap(Map<String,List<String>> map) {
        ShardedJedis shardedJedis = null;
        try {
            shardedJedis = shardedJedisPool.getResource();
            ShardedJedisPipeline pipelined = shardedJedis.pipelined();
            for (Map.Entry<String, List<String>> entry : map.entrySet()) {
                String cno = entry.getKey();
                String key = RedisKeyConstant.DEVICE_RELATION_CHILD_PREFIX + cno;
                List<String> list = map.get(cno);
                pipelined.set(key,JSON.toJSONString(list));
            }
            pipelined.sync();
            return true;
        } catch (Exception ex) {
            logger.error("set error.", ex);
            returnBrokenResource(shardedJedis);
        } finally {
            returnResource(shardedJedis);
        }
        return false;
    }

    /**
     * 批量设置设备缓存信息
     * @param lists
     * @return
     */
    public boolean setDeviceCacheList(List<DeviceCacheVo> lists) {
        ShardedJedis shardedJedis = null;
        try {
            shardedJedis = shardedJedisPool.getResource();
            ShardedJedisPipeline pipelined = shardedJedis.pipelined();
            for (DeviceCacheVo list : lists) {
                String key = RedisKeyConstant.DEVICE_PREFIX + list.getCno();
                pipelined.set(key,JSON.toJSONString(list));
            }
            pipelined.sync();
            return true;
        } catch (Exception ex) {
            logger.error("set error.", ex);
            returnBrokenResource(shardedJedis);
        } finally {
            returnResource(shardedJedis);
        }
        return false;
    }

    /**
     * 批量设置用户设备关系缓存
     * @param lists
     * @return
     */
    public boolean setDeviceMapCacheList(List<DeviceMapCacheVo> lists) {
        ShardedJedis shardedJedis = null;
        try {
            shardedJedis = shardedJedisPool.getResource();
            ShardedJedisPipeline pipelined = shardedJedis.pipelined();
            for (DeviceMapCacheVo list : lists) {
                String key = RedisKeyConstant.DEVICE_MAP_PREFIX + list.getCno();
                pipelined.set(key,JSON.toJSONString(list));
            }
            pipelined.sync();
            return true;
        } catch (Exception ex) {
            logger.error("set error.", ex);
            returnBrokenResource(shardedJedis);
        } finally {
            returnResource(shardedJedis);
        }
        return false;
    }

    /**
     * 批量设置设备状态缓存
     * @param collection
     * @return
     */
    public boolean setDeviceStateCollection(Collection<DeviceStateCacheVo> collection) {
        ShardedJedis shardedJedis = null;
        try {
            shardedJedis = shardedJedisPool.getResource();
            ShardedJedisPipeline pipelined = shardedJedis.pipelined();
            for (DeviceStateCacheVo cacheVo : collection) {
                String key = RedisKeyConstant.DEVICE_STATE_PREFIX + cacheVo.getCno();
                pipelined.set(key,JSON.toJSONString(cacheVo));
            }
            pipelined.sync();
            return true;
        } catch (Exception ex) {
            logger.error("set error.", ex);
            returnBrokenResource(shardedJedis);
        } finally {
            returnResource(shardedJedis);
        }
        return false;
    }

    /**
     * 批量设置组织缓存信息
     * @param lists
     * @return
     */
    public boolean setOrgCacheList(List<OrgCacheVo> lists) {
        ShardedJedis shardedJedis = null;
        try {
            shardedJedis = shardedJedisPool.getResource();
            ShardedJedisPipeline pipelined = shardedJedis.pipelined();
            for (OrgCacheVo list : lists) {
                String key = RedisKeyConstant.ORG_PREFIX + list.getOrgNo();
                pipelined.set(key,JSON.toJSONString(list));
            }
            pipelined.sync();
            return true;
        } catch (Exception ex) {
            logger.error("set error.", ex);
            returnBrokenResource(shardedJedis);
        } finally {
            returnResource(shardedJedis);
        }
        return false;
    }

    /**
     * 批量设置菜单缓存信息
     * @param lists
     * @return
     */
    public boolean setMenuCacheList(List<MenuCacheVo> lists) {
        ShardedJedis shardedJedis = null;
        try {
            shardedJedis = shardedJedisPool.getResource();
            ShardedJedisPipeline pipelined = shardedJedis.pipelined();
            for (MenuCacheVo list : lists) {
                String key = RedisKeyConstant.MENU_PREFIX + list.getMenuId();
                pipelined.set(key,JSON.toJSONString(list));
            }
            pipelined.sync();
            return true;
        } catch (Exception ex) {
            logger.error("set error.", ex);
            returnBrokenResource(shardedJedis);
        } finally {
            returnResource(shardedJedis);
        }
        return false;
    }


    /**
     * 批量设置某个组织的父组织列表
     * @param map
     * @return
     */
    public boolean setParentOrgList(Map<Long,List<Long>> map) {
        ShardedJedis shardedJedis = null;
        try {
            shardedJedis = shardedJedisPool.getResource();
            ShardedJedisPipeline pipelined = shardedJedis.pipelined();
            for (Map.Entry<Long, List<Long>> entry : map.entrySet()) {
                Long orgNo = entry.getKey();
                String key = RedisKeyConstant.ORG_PARENT_PREFIX + orgNo;
                List<Long> value = entry.getValue();
                pipelined.set(key,JSON.toJSONString(value));
            }
            pipelined.sync();
            return true;
        } catch (Exception ex) {
            logger.error("set error.", ex);
            returnBrokenResource(shardedJedis);
        } finally {
            returnResource(shardedJedis);
        }
        return false;
    }

    /**
     * 批量设置某个组织所在的整个树
     * @param map
     * @return
     */
    public boolean setOrgTreeMap(Map<Long, List<OrgCacheVo>> map) {
        ShardedJedis shardedJedis = null;
        try {
            shardedJedis = shardedJedisPool.getResource();
            ShardedJedisPipeline pipelined = shardedJedis.pipelined();
            for (Map.Entry<Long, List<OrgCacheVo>> entry : map.entrySet()) {
                Long orgNo = entry.getKey();
                String key = RedisKeyConstant.ORG_TREE_PREFIX + orgNo;
                List<OrgCacheVo> value = entry.getValue();
                pipelined.set(key,JSON.toJSONString(value));
            }

            pipelined.sync();
            return true;
        } catch (Exception ex) {
            logger.error("set error.", ex);
            returnBrokenResource(shardedJedis);
        } finally {
            returnResource(shardedJedis);
        }
        return false;
    }

    /**
     * 批量设置某个组织以及该组织的孩子节点缓存
     * @param map
     * @return
     */
    public boolean setOrgChildMap(Map<Long, List<OrgCacheVo>> map) {
        ShardedJedis shardedJedis = null;
        try {
            shardedJedis = shardedJedisPool.getResource();
            ShardedJedisPipeline pipelined = shardedJedis.pipelined();
            for (Map.Entry<Long, List<OrgCacheVo>> entry : map.entrySet()) {
                Long orgNo = entry.getKey();
                String key = RedisKeyConstant.ORG_CHILD_PREFIX + orgNo;
                List<OrgCacheVo> value = entry.getValue();
                pipelined.set(key,JSON.toJSONString(value));
            }

            pipelined.sync();
            return true;
        } catch (Exception ex) {
            logger.error("set error.", ex);
            returnBrokenResource(shardedJedis);
        } finally {
            returnResource(shardedJedis);
        }
        return false;
    }

    /**
     * 批量设置用户组织管辖权限缓存
     * @param map
     * @return
     */
    public boolean setUserOrgMap(Map<Integer, List<Long>> map) {
        ShardedJedis shardedJedis = null;
        try {
            shardedJedis = shardedJedisPool.getResource();
            ShardedJedisPipeline pipelined = shardedJedis.pipelined();

            for (Map.Entry<Integer, List<Long>> entry : map.entrySet()) {
                Integer userId = entry.getKey();
                String key = RedisKeyConstant.USER_ORGS_PREFIX + userId;
                List<Long> value = entry.getValue();
                pipelined.set(key,JSON.toJSONString(value));
            }

            pipelined.sync();
            return true;
        } catch (Exception ex) {
            logger.error("set error.", ex);
            returnBrokenResource(shardedJedis);
        } finally {
            returnResource(shardedJedis);
        }
        return false;
    }

    /**
     * 批量设置设备是否存在孩子节点
     * @param map
     * @return
     */
    public boolean setDeviceChild(Map<String,Boolean> map) {
        ShardedJedis shardedJedis = null;
        try {
            shardedJedis = shardedJedisPool.getResource();
            ShardedJedisPipeline pipelined = shardedJedis.pipelined();
            for (Map.Entry<String, Boolean> entry : map.entrySet()) {
                String cno = entry.getKey();
                String key = RedisKeyConstant.DEVICE_CHILD_PREFIX + cno;
                Boolean flag = map.get(cno);
                pipelined.set(key,String.valueOf(flag));
            }
            pipelined.sync();
            return true;
        } catch (Exception ex) {
            logger.error("set error.", ex);
            returnBrokenResource(shardedJedis);
        } finally {
            returnResource(shardedJedis);
        }
        return false;
    }

    /**
     * 批量设置组织是否存在孩子节点
     * @param map
     * @return
     */
    public boolean setOrgChild(Map<Long,Boolean> map) {
        ShardedJedis shardedJedis = null;
        try {
            shardedJedis = shardedJedisPool.getResource();
            ShardedJedisPipeline pipelined = shardedJedis.pipelined();
            for (Map.Entry<Long, Boolean> entry : map.entrySet()) {
                Long orgNo = entry.getKey();
                String key = RedisKeyConstant.ORG_EXIST_CHILD_PREFIX + orgNo;
                Boolean flag = map.get(orgNo);
                pipelined.set(key,String.valueOf(flag));
            }
            pipelined.sync();
            return true;
        } catch (Exception ex) {
            logger.error("set error.", ex);
            returnBrokenResource(shardedJedis);
        } finally {
            returnResource(shardedJedis);
        }
        return false;
    }

    /**
     * 批量删除key
     * @param keys
     * @return
     */
    public boolean batchDeleteKeys(Collection<String> keys) {
        ShardedJedis shardedJedis = null;
        try {
            shardedJedis = shardedJedisPool.getResource();
            ShardedJedisPipeline pipelined = shardedJedis.pipelined();
            for (String key : keys) {
                pipelined.del(key);
            }
            pipelined.sync();
            return true;
        } catch (Exception ex) {
            logger.error("set error.", ex);
            returnBrokenResource(shardedJedis);
        } finally {
            returnResource(shardedJedis);
        }
        return false;
    }

    /**
     * 根据pattern模糊查询key
     * @param pattern
     * @return
     */
    public Set<String> keys(String pattern) {
        ShardedJedis shardedJedis = null;
        try {
            Set<String> all = new HashSet<>();
            shardedJedis = shardedJedisPool.getResource();
            Collection<Jedis> allShards = shardedJedis.getAllShards();
            for (Jedis jedis : allShards) {
                Set<String> keys = jedis.keys(pattern);
                all.addAll(keys);
            }
            return all;
        } catch (Exception ex) {
            logger.error("set error.", ex);
            returnBrokenResource(shardedJedis);
        } finally {
            returnResource(shardedJedis);
        }
        return null;
    }
}  