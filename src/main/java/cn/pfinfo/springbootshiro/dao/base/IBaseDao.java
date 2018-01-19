package cn.pfinfo.springbootshiro.dao.base;

import java.io.Serializable;
import java.util.List;

/**
 * Created by panfei on 2018/1/19.
 */
public interface IBaseDao<T,ID extends Serializable> {
    /**
     * 保存一个实体
     * @param entity
     */
    void save(T entity);
    /**
     * 更新一个实体
     * @param entity
     */
    void update(T entity);
    /**
     * 通过主键删除一个实体
     * @param id 实体的主键
     */
    void delete(ID id);

    /**
     * 通过id获取一个实体
     * @param id 实体的id
     * @return 实体
     */
    T get(ID id);
    /**
     * 通过多个id查询并返回实体集合
     * @param ids
     */
    List<T> find(ID ... ids);
//    List<T> findByPage(ID id);
}
