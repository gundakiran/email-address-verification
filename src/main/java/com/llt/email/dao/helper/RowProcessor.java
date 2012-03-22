package com.llt.email.dao.helper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * <code>RowProcessor</code> implementations convert 
 * <code>ResultSet</code> rows into various other objects.  
 * 
 * @see BasicRowProcessor
 */
public interface RowProcessor {

    /**
     * Convert a <code>ResultSet</code> row into a JavaBean.  This 
     * implementation delegates to a BeanProcessor instance.
     * 
     * @see com.bcbsa.ca.dao.util.RowProcessor#toBean(java.sql.ResultSet, java.lang.Class)
     * 
     * @param rs ResultSet that supplies the bean data
     * @param <T> The type of bean to create type Class from which to create the bean instance
     *       
     * @throws SQLException if a database access error occurs
     * @return the newly created bean 
     */	
	public <T> T toBean(ResultSet rs, Class<T> type) throws SQLException;
}
