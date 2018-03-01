package cn.pfinfo.springbootshiro.service;

/**
 * 声明式接口
 * @author panfei
 *
 * @param <T>
 */
public interface IBaseService<T> {
	/**
	 * 通过ID获取一个实例
	 * @param id
	 * @return
	 */
	T getOne(Long id);
}
