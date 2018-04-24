package com.linkedin.jpa.service;

public interface EntityService<T> {
	public T getByBusinessKey(Class<T> entityClass,Object fieldName, Object filedValue);
}
