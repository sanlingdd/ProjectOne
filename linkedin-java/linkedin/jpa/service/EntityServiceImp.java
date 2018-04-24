package com.linkedin.jpa.service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

@Service

public class EntityServiceImp<T> implements EntityService<T> {

	@PersistenceContext
	protected EntityManager entityManager;
	private final TransactionTemplate transactionTemplatee;
	@Autowired
	private PlatformTransactionManager transactionManager;

	public EntityServiceImp() {
		transactionTemplatee = new TransactionTemplate(transactionManager);
	}

	@Override
	public T getByBusinessKey(Class<T> entityClass, Object fieldName, Object filedValue) {
		try {
			String str = "select entity from %s entity where entity.%s = :%s";
			str = String.format(str, entityClass.getSimpleName(), fieldName.toString(), fieldName.toString());
			TypedQuery<T> query = entityManager.createQuery(str, entityClass);
			query.setParameter(fieldName.toString(), filedValue);
			T object = query.getSingleResult();
			return object;
		} catch (Exception e) {
			return (T) null;
		}
	}

	public EntityManager getEntityManager() {
		return entityManager;
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public TransactionTemplate getTransactionTemplatee() {
		return transactionTemplatee;
	}

}
