package org.gozhe.android.core.db;

import java.io.Serializable;
import java.util.List;


/**
 * @author xiaox
 * ����DAO��
 * EntityDao��һ�����ͽӿڣ�������CRUD�ķ���
 * @param <T>
 * @param <PK>
 */
public interface EntityDao<T,PK extends Serializable>{
	
	
	/**
	 * ���ʵ��
	 * @param entity
	 */
	void save(final T entity);
	
	
	/**
	 * �Ƴ�ʵ�����
	 * @param ids
	 */
	void remove(final PK... ids);
	
	/**
	 * ����
	 * @param entity
	 */
	void upDate(final T entity);
	
	/**
	 * ����ID��ѯ����
	 * @param id
	 * @return
	 */
	T find(final PK id);
	
	
	/**
	 * ��ҳ��ѯ
	 * @param startResult
	 * @param maxResult
	 * @return
	 */
	List<T> getPageData(Integer startResult, Integer maxResult);
	
	
	/**
	 * ���ؼ�¼��
	 * @return
	 */
	public long getCount();
}

