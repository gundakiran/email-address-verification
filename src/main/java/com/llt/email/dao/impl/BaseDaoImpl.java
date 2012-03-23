package com.llt.email.dao.impl;

import java.util.Map;

import javax.annotation.Resource;

public abstract class BaseDaoImpl {
	protected Map<String, String> sqlMap;
	
	/**
	 * This method reads the query (SQL) definition that is defined in
	 * configuration file (email-sql-beans.xml) and returns as string.
	 * 
	 * If it doesn't find then it will throw a Runtime Exception
	 */
	protected String getSQL(String sqlName) {
		String sqlText = null;

		if (sqlMap.containsKey(sqlName)) {
			sqlText = sqlMap.get(sqlName).trim();
		}

		return sqlText;
	}

	@Resource(name="sqlMap")
	public void setSqlMap(Map<String, String> sqlMap) {
		this.sqlMap = sqlMap;
	}
}