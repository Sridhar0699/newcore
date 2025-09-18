package com.portal.basedao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.query.internal.NativeQueryImpl;
import org.hibernate.transform.AliasToEntityMapResultTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * The Class BaseHibernateDao.
 */
@Repository("baseDao")
public class BaseHibernateDao implements IBaseDao {

	private static final Logger logger = LogManager.getLogger(BaseHibernateDao.class);
	
	@Autowired
	private EntityManager entityManager;

	/**
	 * Refresh the entity.
	 * 
	 * @param BaseEntity
	 *            the BaseEntity
	 * @return void
	 */
	public void refreshEntity(final BaseEntity valueObject) {

		this.entityManager.refresh(valueObject);
	}

	/**
	 * Save the entity object
	 * 
	 * @param valueObject
	 *            the value object
	 */
	public void save(final BaseEntity valueObject) {

		this.entityManager.persist(valueObject);
	}
	

	/**
	 * Update the entity object
	 * 
	 * @param valueObject
	 *            the value object
	 */
	public void update(final BaseEntity valueObject) {

		this.entityManager.merge(valueObject);
	}

	/**
	 * Delete the entity object
	 * 
	 * @param valueObject
	 *            the value object
	 */
	public void delete(final BaseEntity valueObject) {

		this.entityManager.remove(valueObject);
	}

	/**
	 * Save or update the entity object
	 * 
	 * @param valueObject
	 *            the value object
	 */
	public void saveOrUpdate(final BaseEntity valueObject) {

		this.entityManager.merge(valueObject);
	}

	/**
	 * Save or update the entity objects
	 * 
	 * @param entities
	 *            the entities
	 */
	public void saveOrUpdateAll(final Collection<? extends BaseEntity> entities) {

		for (final BaseEntity entity : entities) {

			this.entityManager.persist(entity);
		}
	}
	
	/**
	 * Save or update the entity objects
	 * 
	 * @param entities
	 *            the entities
	 */
	public void updateAll(final Collection<? extends BaseEntity> entities) {

		for (final BaseEntity entity : entities) {

			this.entityManager.merge(entity);
		}
	}


	/**
	 * Delete all the entity objects
	 * 
	 * @param entities
	 *            the entities
	 */
	public void deleteAll(final Collection<? extends BaseEntity> entities) {

		for (final BaseEntity entity : entities) {

			this.entityManager.remove(entity);
		}
	}

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
	public BaseEntity findByPK(Class clazz, Object pkID) {

		return (BaseEntity) this.entityManager.find(clazz, (Serializable) pkID);
	}

	/**
	 * Merge the entity object
	 * 
	 * @param BaseEntity
	 * 
	 */

	public void merge(BaseEntity valueObject) {

		this.entityManager.merge(valueObject);
	}

	/**
	 * Find the row count of given query
	 * 
	 * @param hqlQuery
	 *            the query string
	 * @param values
	 *            the values
	 * @return the int
	 */
	public int findRowCount(String hqlQuery, Object[] values) {

		final Query query = this.entityManager.createQuery(hqlQuery);

		for (int i = 0; i < values.length; i++) {

			query.setParameter(i, values[i]);
		}

		return ((Long) query.getResultList().get(0)).intValue();
	}

	/**
	 * Find by HQL query with indexed parameters.
	 * 
	 * @param String,Object[]
	 * 
	 * @return the list<? extends object>
	 */
	public List<? extends Object> findByHQLQueryWithIndexedParams(String hqlQuery, Object[] params) {

		Query query = this.entityManager.createQuery(hqlQuery);

		return this.getQueryResultsWithIndexedParams(query, params, null, null);
	}

	/**
	 * Find by HQL query with indexed parameters and limits.
	 * 
	 * @param String,Object[]
	 * 
	 * @return the list<? extends object>
	 */
	public List<? extends Object> findByHQLQueryWithIndexedParamsAndLimits(String hqlQuery, Object[] params, int pgSize,
			int pgNum) {

		Query query = this.entityManager.createQuery(hqlQuery);

		return this.getQueryResultsWithIndexedParams(query, params, pgSize, pgNum);
	}

	/**
	 * Find by SQL query with indexed params
	 * 
	 * @param String,Object[]
	 * 
	 * @return the list<? extends object>
	 */
	public List<? extends Object> findBySQLQueryWithIndexedParams(String sqlQuery, Object[] params) {

		Query query = this.entityManager.createNativeQuery(sqlQuery);

		return this.getQueryResultsWithIndexedParams(query, params, null, null);
	}

	/**
	 * Find by SQL query with indexed params and limits
	 * 
	 * @param String,Object[]
	 * 
	 * @return the list<? extends object>
	 */
	public List<? extends Object> findBySQLQueryWithIndexedParamsAndLimits(String sqlQuery, Object[] params, int pgSize,
			int pgNum) {

		Query query = this.entityManager.createNativeQuery(sqlQuery);

		return this.getQueryResultsWithIndexedParams(query, params, pgSize, pgNum);
	}

	/**
	 * Get query results with indexed params
	 * 
	 * @param queryObj
	 * @param params
	 * @return
	 */

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private List<? extends Object> getQueryResultsWithIndexedParams(Query query, Object[] params, Integer pgSize,
			Integer pgNum) {

		List list = new ArrayList();

		try {

			if (pgSize != null && pgNum != null) {

				query.setFirstResult(pgSize * (pgNum - 1));
				query.setMaxResults(pgSize);
			}

			int index = 1;

			for (Object param : params) {

				query.setParameter(index, param);

				index++;
			}

			list = query.getResultList();

		} catch (Exception ex) {

			logger.error("Exception BaseDao:", ex);
		}

		return list;
	}

	/**
	 * Find by HQL query with named parameters
	 * 
	 * @param String,Map<String,
	 *            Object>
	 * 
	 * @return the list<? extends object>
	 */
	public List<? extends Object> findByHQLQueryWithNamedParams(String hqlQuery, Map<String, Object> params) {

		Query query = this.entityManager.createQuery(hqlQuery);

		return this.getQueryResultsWithNamedParams(query, params, null, null);
	}

	/**
	 * Find by HQL query with named parameters and limits
	 * 
	 * @param String,Map<String,
	 *            Object>
	 * 
	 * @return the list<? extends object>
	 */
	public List<? extends Object> findByHQLQueryWithNamedParamsAndLimits(String hqlQuery, Map<String, Object> params,
			Integer pgSize, Integer pgNum) {

		Query query = this.entityManager.createQuery(hqlQuery);

		return this.getQueryResultsWithNamedParams(query, params, pgSize, pgNum);
	}

	/**
	 * Find by SQL query with named parameters
	 * 
	 * @param String,Map<String,
	 *            Object>
	 * 
	 * @return the list<? extends object>
	 */
	public List<? extends Object> findBySQLQueryWithNamedParams(String sqlQuery, Map<String, Object> params) {

		Query query = this.entityManager.createNativeQuery(sqlQuery);

		return this.getQueryResultsWithNamedParams(query, params, null, null);
	}

	/**
	 * Find by SQL query with named parameters and limits
	 * 
	 * @param String,Map<String,
	 *            Object>
	 * 
	 * @return the list<? extends object>
	 */
	public List<? extends Object> findBySQLQueryWithNamedParamsAndLimits(String sqlQuery, Map<String, Object> params,
			int pgSize, int pgNum) {

		Query query = this.entityManager.createNativeQuery(sqlQuery);

		return this.getQueryResultsWithNamedParams(query, params, pgSize, pgNum);
	}

	/**
	 * Get query results with named params
	 * 
	 * @param query
	 * @param params
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private List<? extends Object> getQueryResultsWithNamedParams(Query query, Map<String, Object> params,
			Integer pgSize, Integer pgNum) {

		List list = new ArrayList();

		try {

			if (pgSize != null && pgNum != null) {

				query.setFirstResult(pgSize * (pgNum - 1));
				query.setMaxResults(pgSize);
			}

			for (Map.Entry<String, Object> param : params.entrySet()) {

				if (param.getValue() instanceof List) {

					List ids = (List) param.getValue();

					query.setParameter(param.getKey(), ids);

				} else {

					query.setParameter(param.getKey(), param.getValue());
				}
			}

			list = query.getResultList();

		} catch (Exception ex) {

			logger.error("Exception BaseDao:", ex);
		}

		return list;
	}

	/**
	 * Find by HQL query without parameters.
	 * 
	 * @param String
	 * 
	 * @return the list<? extends object>
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<? extends Object> findByHQLQueryWithoutParams(String hqlQuery) {

		List list = new ArrayList();

		try {

			Query query = this.entityManager.createQuery(hqlQuery);

			list = query.getResultList();

		} catch (Exception ex) {

			logger.error("Exception BaseDao:", ex);
		}

		return list;
	}

	/**
	 * Find by HQL query without parameters and limits.
	 * 
	 * @param String
	 * 
	 * @return the list<? extends object>
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<? extends Object> findByHQLQueryWithoutParamsAndLimits(String hqlQuery, int pgSize, int pgNum) {

		List list = new ArrayList();

		try {

			Query query = this.entityManager.createQuery(hqlQuery);

			query.setFirstResult(pgSize * (pgNum - 1));
			query.setMaxResults(pgSize);

			list = query.getResultList();

		} catch (Exception ex) {

			logger.error("Exception BaseDao:", ex);
		}

		return list;
	}

	/**
	 * Find by SQL query without parameters.
	 * 
	 * @param String
	 * 
	 * @return the list<? extends object>
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<? extends Object> findBySQLQueryWithoutParams(String sqlQuery) {

		List list = new ArrayList();

		try {

			Query query = this.entityManager.createNativeQuery(sqlQuery);

			list = query.getResultList();

		} catch (Exception ex) {

			logger.error("Exception BaseDao:", ex);
		}

		return list;
	}

	/**
	 * Find by SQL query without parameters and limits
	 * 
	 * @param String
	 * 
	 * @return the list<? extends object>
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<? extends Object> findBySQLQueryWithoutParamsAndLimits(String sqlQuery, int pgSize, int pgNum) {

		List list = new ArrayList();

		try {

			Query query = this.entityManager.createNativeQuery(sqlQuery);

			query.setFirstResult(pgSize * (pgNum - 1));
			query.setMaxResults(pgSize);

			list = query.getResultList();

		} catch (Exception ex) {

			logger.error("Exception BaseDao:", ex);
		}

		return list;
	}

	/**
	 * Update by HQL query
	 * 
	 * @param String,Object[]
	 * 
	 * @return the Integer
	 */
	public int updateByHQLQuery(String hqlQuery, Object[] params) {

		int result = 0;

		try {

			Query query = this.entityManager.createQuery(hqlQuery);

			int index = 1;

			for (Object param : params) {

				query.setParameter(index, param);
				index++;
			}

			result = query.executeUpdate();

		} catch (Exception ex) {

			logger.error("Exception BaseDao:", ex);
		}

		return result;
	}

	/**
	 * Update by SQL query
	 * 
	 * @param String,Object[]
	 * 
	 * @return the list<? extends object>
	 */
	public int updateBySQLQuery(String sqlQuery, Object[] params) {

		int result = 0;

		try {

			Query query = this.entityManager.createNativeQuery(sqlQuery);

			int index = 1;

			for (Object param : params) {

				query.setParameter(index, param);
				index++;
			}

			result = query.executeUpdate();

		} catch (Exception ex) {

			logger.error("Exception BaseDao:", ex);
		}

		return result;
	}

	/**
	 * Update by HQL query with named parameters
	 * 
	 * @param String,Map<String,
	 *            Object>
	 * 
	 * @return the list<? extends object>
	 */
	public int updateByHQLQueryWithNamedParams(String hqlQuery, Map<String, Object> params) {

		Query query = this.entityManager.createQuery(hqlQuery);

		return this.getUpdatedResultCount(query, params);
	}

	/**
	 * Update by SQL query with named parameters
	 * 
	 * @param String,Map<String,
	 *            Object>
	 * 
	 * @return the list<? extends object>
	 */
	public int updateBySQLQueryWithNamedParams(String sqlQuery, Map<String, Object> params) {

		Query query = this.entityManager.createNativeQuery(sqlQuery);

		return this.getUpdatedResultCount(query, params);
	}

	/**
	 * Get updated results count
	 * 
	 * @param query
	 * @param params
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	private int getUpdatedResultCount(Query query, Map<String, Object> params) {

		int result = 0;

		try {

			for (Map.Entry<String, Object> param : params.entrySet()) {

				if (param.getValue() instanceof List) {

					List ids = (List) param.getValue();
					query.setParameter(param.getKey(), ids);

				} else {

					query.setParameter(param.getKey(), param.getValue());
				}
			}

			result = query.executeUpdate();

		} catch (Exception ex) {

			logger.error("Exception BaseDao:", ex);
		}

		return result;
	}
	
	/**
	 * Find by native SQL query
	 * 
	 * @param String,Object[]
	 * 
	 * @return the list<? extends object>
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> findByNativeSQLQueryByMap(String nativeQuery, Object[] params) {

		Query query = entityManager.createNativeQuery(nativeQuery);
		int index = 1;

		for (Object param : params) {

			query.setParameter(index, param.toString());
			index++;
		}
		NativeQueryImpl nativeQueryImpl = (NativeQueryImpl) query;
		nativeQueryImpl.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
		return nativeQueryImpl.getResultList();
	}
	
	/**
	 * Find by native SQL query
	 * 
	 * @param String,Object[]
	 * 
	 * @return the list<? extends object>
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> findByNativeSQLQueryByMap(String nativeQuery, Object[] params, Integer fetchSize) {

		Query query = entityManager.createNativeQuery(nativeQuery);
		int index = 0;

		for (Object param : params) {

			query.setParameter(index, param.toString());
			index++;
		}
		NativeQueryImpl nativeQueryImpl = (NativeQueryImpl) query;
		nativeQueryImpl.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
		return nativeQueryImpl.getResultList();
	}
	
	/**
	 * Find by HQL query with indexed parameters.
	 * 
	 * @param String,Object[]
	 * 
	 * @return the list<? extends object>
	 */
	public List<? extends Object> findByHQLQueryWithIndexedParams(String hqlQuery, Object[] params, Integer fetchSize) {

		Query query = this.entityManager.createQuery(hqlQuery);
		query.setMaxResults(fetchSize);
		return this.getQueryResultsWithIndexedParams(query, params, null, null);
	}
}
