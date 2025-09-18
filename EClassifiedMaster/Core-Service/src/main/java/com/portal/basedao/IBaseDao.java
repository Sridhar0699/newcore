package com.portal.basedao;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;

/**
 * The Interface IBaseDao.
 */
public interface IBaseDao {

	/**
	 * Refresh the entity.
	 * 
	 * @param BaseEntity
	 *            the BaseEntity
	 * @return void
	 */
	public void refreshEntity(final BaseEntity valueObject);

	/**
	 * Save the entity object
	 * 
	 * @param valueObject
	 *            the value object
	 */
	public void save(final BaseEntity valueObject);


	/**
	 * Update the entity object
	 * 
	 * @param valueObject
	 *            the value object
	 */
	public void update(final BaseEntity valueObject);

	/**
	 * Delete the entity object
	 * 
	 * @param valueObject
	 *            the value object
	 */
	public void delete(final BaseEntity valueObject);

	/**
	 * Save or update the entity object
	 * 
	 * @param valueObject
	 *            the value object
	 */
	public void saveOrUpdate(final BaseEntity valueObject);

	/**
	 * Save or update the entity objects
	 * 
	 * @param entities
	 *            the entities
	 */
	public void saveOrUpdateAll(final Collection<? extends BaseEntity> entities);

	/**
	 * Delete all the entity objects
	 * 
	 * @param entities
	 *            the entities
	 */
	public void deleteAll(final Collection<? extends BaseEntity> entities);

	/**
	 * Find by entity using primary key
	 * 
	 * @param clazz
	 *            the clazz
	 * @param pkID
	 *            the pk id
	 * @return the base entity
	 */
	@SuppressWarnings("rawtypes")
	public BaseEntity findByPK(Class clazz, Object pkID);

	/**
	 * Merge the entity object
	 * 
	 * @param BaseEntity
	 * 
	 */
	public void merge(final BaseEntity valueObject);

	/**
	 * Find the row count of given query
	 * 
	 * @param queryString
	 *            the query string
	 * @param values
	 *            the values
	 * @return the int
	 */
	public int findRowCount(String queryString, Object[] values);

	/**
	 * Find by HQL query with indexed parameters.
	 * 
	 * @param String,Object[]
	 * 
	 * @return the list<? extends object>
	 */
	public List<? extends Object> findByHQLQueryWithIndexedParams(String hqlQuery, Object[] params);

	/**
	 * Find by HQL query with indexed parameters and limits.
	 * 
	 * @param String,Object[]
	 * 
	 * @return the list<? extends object>
	 */
	public List<? extends Object> findByHQLQueryWithIndexedParamsAndLimits(String hqlQuery, Object[] params, int pgSize,
			int pgNum);

	/**
	 * Find by SQL query with indexed params
	 * 
	 * @param String,Object[]
	 * 
	 * @return the list<? extends object>
	 */
	public List<? extends Object> findBySQLQueryWithIndexedParams(String sqlQuery, Object[] params);

	/**
	 * Find by SQL query with indexed params and limits
	 * 
	 * @param String,Object[]
	 * 
	 * @return the list<? extends object>
	 */
	public List<? extends Object> findBySQLQueryWithIndexedParamsAndLimits(String sqlQuery, Object[] params, int pgSize,
			int pgNum);

	/**
	 * Find by HQL query with named parameters
	 * 
	 * @param String,Map<String,
	 *            Object>
	 * 
	 * @return the list<? extends object>
	 */
	public List<? extends Object> findByHQLQueryWithNamedParams(String hqlQuery, Map<String, Object> params);

	/**
	 * Find by HQL query with named parameters and limits
	 * 
	 * @param String,Map<String,
	 *            Object>
	 * 
	 * @return the list<? extends object>
	 */
	public List<? extends Object> findByHQLQueryWithNamedParamsAndLimits(String hqlQuery, Map<String, Object> params,
			Integer pgSize, Integer pgNum);

	/**
	 * Find by SQL query with named parameters
	 * 
	 * @param String,Map<String,
	 *            Object>
	 * 
	 * @return the list<? extends object>
	 */
	public List<? extends Object> findBySQLQueryWithNamedParams(String sqlQuery, Map<String, Object> params);

	/**
	 * Find by SQL query with named parameters and limits
	 * 
	 * @param String,Map<String,
	 *            Object>
	 * 
	 * @return the list<? extends object>
	 */
	public List<? extends Object> findBySQLQueryWithNamedParamsAndLimits(String sqlQuery, Map<String, Object> params,
			int pgSize, int pgNum);

	/**
	 * Find by HQL query without parameters.
	 * 
	 * @param String
	 * 
	 * @return the list<? extends object>
	 */
	public List<? extends Object> findByHQLQueryWithoutParams(String hqlQuery);

	/**
	 * Find by HQL query without parameters and limits.
	 * 
	 * @param String
	 * 
	 * @return the list<? extends object>
	 */
	public List<? extends Object> findByHQLQueryWithoutParamsAndLimits(String hqlQuery, int pgSize, int pgNum);

	/**
	 * Find by SQL query without parameters.
	 * 
	 * @param String
	 * 
	 * @return the list<? extends object>
	 */
	public List<? extends Object> findBySQLQueryWithoutParams(String sqlQuery);

	/**
	 * Find by SQL query without parameters and limits
	 * 
	 * @param String
	 * 
	 * @return the list<? extends object>
	 */
	public List<? extends Object> findBySQLQueryWithoutParamsAndLimits(String sqlQuery, int pgSize, int pgNum);

	/**
	 * Update by HQL query
	 * 
	 * @param String,Object[]
	 * 
	 * @return the Integer
	 */
	public int updateByHQLQuery(String hqlQuery, Object[] params);

	/**
	 * Update by SQL query
	 * 
	 * @param String,Object[]
	 * 
	 * @return the list<? extends object>
	 */
	public int updateBySQLQuery(String sqlQuery, Object[] params);

	/**
	 * Update by HQL query with named parameters
	 * 
	 * @param String,Map<String,
	 *            Object>
	 * 
	 * @return the list<? extends object>
	 */
	public int updateByHQLQueryWithNamedParams(String hqlQuery, Map<String, Object> params);

	/**
	 * Update by SQL query with named parameters
	 * 
	 * @param String,Map<String,
	 *            Object>
	 * 
	 * @return the list<? extends object>
	 */
	public int updateBySQLQueryWithNamedParams(String sqlQuery, Map<String, Object> params);

	public List<? extends Object> findByHQLQueryWithIndexedParams(String hqlQuery, Object[] params, Integer fetchSize);
	
	public void updateAll(final Collection<? extends BaseEntity> entities);
	
	public List<Map<String, Object>> findByNativeSQLQueryByMap(String nativeQuery, Object[] params);
	
	public List<Map<String, Object>> findByNativeSQLQueryByMap(String nativeQuery, Object[] params, Integer fetchSize);
}
