package cn.pfinfo.springbootshiro.dao.base;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

/**
 * Created by panfei on 2018/1/19.
 */
@Repository
@Transactional
public interface ICommonDao<T,ID extends Serializable> extends JpaRepository<T,ID>,JpaSpecificationExecutor<T>{
}
