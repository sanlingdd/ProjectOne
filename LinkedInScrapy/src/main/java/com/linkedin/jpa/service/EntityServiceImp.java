package com.linkedin.jpa.service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Service;
@Service
public class EntityServiceImp<T> implements EntityService<T> {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public T getByBusinessKey(Class<T> entityClass, Object fieldName, Object filedValue) {
		String str = "select %s from %s entity where entity.%s = :%s";
		String.format(str, entityClass.getName(),entityClass.getName(),fieldName.toString(),fieldName.toString());
		TypedQuery<T> query = entityManager.createQuery(str, entityClass);
		query.setParameter(fieldName.toString(), filedValue);
		return query.getSingleResult();
	}

}
