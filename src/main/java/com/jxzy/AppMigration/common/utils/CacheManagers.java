package com.jxzy.AppMigration.common.utils;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
/**
 *
 * 版权所有：北京九星智元科技有限公司
 * 作　　者：曲绍备
 * 版　　本：1.0
 * 创建时间：2018年6月13日
 * 功能描述：缓存
 *
 */
public class CacheManagers {

    private static Map<String,CacheData> CACHE_DATA = new ConcurrentHashMap<>();
    public static <T> T getData(String key,Load<T> load,int expire){
        T data = getData(key);
        if(data == null && load != null){
            data = load.load();
            if(data != null){
                setData(key,data,expire);
            }
        }
        return data;
    }
    public static <T> T getData(String key){
        CacheData<T> data = CACHE_DATA.get(key);
        if(data != null && (data.getExpire() <= 0 || data.getSaveTime() >= new Date().getTime())){
            return data.getData();
        }
        return null;
    }
    public static <T> void setData(String key,T data,int expire){
        CACHE_DATA.put(key,new CacheData(data,expire));
    }
    public static void clear(String key){
        CACHE_DATA.remove(key);
    }
    public static void clearAll(){
        CACHE_DATA.clear();
    }
    public interface Load<T>{
        T load();
    }
    private static class CacheData<T>{
        CacheData(T t,int expire){
            this.data = t;
            this.expire = expire <= 0 ? 0 : expire * 1000;
            this.saveTime = new Date().getTime() + this.expire;
        }
        private T data;
        private long saveTime; // 存活时间
        private long expire;   // 过期时间 小于等于0标识永久存活
        public T getData() {
            return data;
        }
        public long getExpire() {
            return expire;
        }
        public long getSaveTime() {
            return saveTime;
        }
    }
}
