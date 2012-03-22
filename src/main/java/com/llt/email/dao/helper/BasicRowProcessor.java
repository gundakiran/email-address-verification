package com.llt.email.dao.helper;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

/**
 * Basic implementation of RowProcessor
 * @see RowProcessor
 */
@Component("rowProcessor")
public class BasicRowProcessor implements RowProcessor {
	
    /**
     * Bean processor provides various methods to convert
     * the resultset into multiple formats. By default it
     * converts to the Class<T> type that is passed.
     */
    private BeanProcessor beanProcessor;
    
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
	public <T> T toBean(ResultSet rs, Class<T> type) throws SQLException {
		return this.beanProcessor.toBean(rs, type);
	}

	@Resource(name="beanProcessor")
	public void setBeanProcessor(BeanProcessor beanProcessor) {
		this.beanProcessor = beanProcessor;
	}

}