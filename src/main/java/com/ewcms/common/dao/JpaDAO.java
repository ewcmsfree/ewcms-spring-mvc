/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.common.dao;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author wangwei
 * @author 吴智俊
 */
public abstract class JpaDAO<K, E> implements JpaDAOable<K,E> {

	@PersistenceContext
    private EntityManager entityManager;
	
    public EntityManager getEntityManager() {
        return this.entityManager;
    }
    
    protected void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }    
    
    protected Class<E> entityClass;

    @SuppressWarnings("unchecked")
	public JpaDAO() {
        ParameterizedType genericSuperclass =
                (ParameterizedType) getClass().getGenericSuperclass();
        this.entityClass =
                (Class<E>) genericSuperclass.getActualTypeArguments()[1];
    }

    @Override
    public void persist(E entity) {
        this.getEntityManager().persist(entity);
    }

    @Override
    public void remove(E entity) {
        this.getEntityManager().remove(entity);
    }

    @Override
    public void removeByPK(K pk) {
        E entity = get(pk);
        if (entity != null) {
            remove(entity);
        }
    }
    
    @Override
    public E merge(E entity) {
        return this.getEntityManager().merge(entity);
    }

    @Override
    public void refresh(E entity){
        this.getEntityManager().refresh(entity);
    }

    @Override
    public E get(K pk) {
        return this.getEntityManager().find(entityClass, pk);
    }

    @Override
    public E getRefresh(K pk){
        return this.getEntityManager().getReference(entityClass, pk);
    }

    @Override
    public E flush(E entity) {
        this.getEntityManager().flush();
        return entity;
    }

    @Override
    public List<E> findAll() {
    	String hql = String.format("Select h From %s h", entityClass.getName());
    	return getEntityManager().createQuery(hql, entityClass).getResultList();
    }

    @Override
    public Integer removeAll() {
    	String hql = String.format("Delete From %s", entityClass.getName());
    	return getEntityManager().createQuery(hql).executeUpdate();
    }
}
