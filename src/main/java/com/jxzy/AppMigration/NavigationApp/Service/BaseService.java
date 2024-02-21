package com.jxzy.AppMigration.NavigationApp.Service;

public interface BaseService {
    /**
     * 根据id获取对象
     * @param id
     * @param clazz
     * @return
     * @throws Exception
     */
    Object getObjById(Long id, Class clazz) throws Exception;
    /**
     * 插入对象
     * @param obj
     * @throws Exception
     */

    void insertObj(Object obj) throws Exception;

    /**
     * 根据id删除对象
     * @param id
     * @param clazz
     * @throws Exception
     */
    void delObjById(Long id, Class clazz) throws Exception;
    /**
     * 更新对象信息
     * @param obj
     * @throws Exception
     */
    void updateObj(Object obj)throws Exception;
}
