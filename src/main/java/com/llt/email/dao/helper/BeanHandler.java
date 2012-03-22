package com.llt.email.dao.helper;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;


/**
 * <code>ResultSetHandler</code> implementation that converts the first
 * <code>ResultSet</code> row into a JavaBean.
 * 
 * @see com.bcbsa.ca.dao.util.ResultSetHandler
 */
@Component("beanHandler")
public class BeanHandler<T> implements ResultSetHandler<T> {

	/**
	 * The RowProcessor implementation to use when converting rows into beans.
	 */
	private RowProcessor rowProcessor;

	/**
	 * Convert the first row of the <code>ResultSet</code> into a bean with the
	 * <code>Class</code> given in the constructor.
	 * 
	 * @param rs
	 *            <code>ResultSet</code> to process.
	 * @return An initialized JavaBean or <code>null</code> if there were no
	 *         rows in the <code>ResultSet</code>.
	 * 
	 * @throws SQLException
	 *             if a database access error occurs
	 * @see com.bcbsa.ca.dao.util.ResultSetHandler#handle(java.sql.ResultSet)
	 */
	public T handle(ResultSet rs, Class<T> type) throws SQLException {
		T ret = this.rowProcessor.toBean(rs, type);
		return ret;
	}

	@Resource(name = "rowProcessor")
	public void setRowProcessor(RowProcessor rowProcessor) {
		this.rowProcessor = rowProcessor;
	}
}
