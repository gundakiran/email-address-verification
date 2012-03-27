package com.llt.email.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.llt.email.dao.EmailRequestDao;
import com.llt.email.dao.helper.BeanHandler;
import com.llt.email.model.EmailRequest;
import com.llt.email.util.Status;

@Repository("emailRequestDao")
public class EmailRequestDaoImpl extends BaseDaoImpl implements EmailRequestDao {
	@Autowired
	@Qualifier("jndiDataSource")
	protected DataSource dataSource;

	@Autowired
	@Qualifier("beanHandler")
	protected BeanHandler<EmailRequest> resultBeanHandler;

	@Override
	public void insert(EmailRequest request) {
		NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(
				dataSource);

		String sqlText = getSQL(SQL_INSERT_REQUEST);

		jdbcTemplate.update(sqlText,
				new BeanPropertySqlParameterSource(request));
	}

	@Override
	public void update(Integer requestId, Status status, String validEmailAddress) {
		NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(
				dataSource);
		String sqlText = getSQL(SQL_UPDATE_REQUEST);
		
		MapSqlParameterSource namedParameters = new MapSqlParameterSource(
				"requestId", requestId);
		namedParameters.addValue("status", status.getCode());
		namedParameters.addValue("updatedDate", new Date());
		namedParameters.addValue("validEmailAddress", validEmailAddress);
	
		jdbcTemplate.update(sqlText, namedParameters);
	}

	@Override
	public List<EmailRequest> findAll() {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String sqlText = getSQL(SQL_GET_ALL_REQUESTS);

		ResulRowHandler rowHandler = new ResulRowHandler();
		jdbcTemplate.query(sqlText, rowHandler);

		return rowHandler.getResults();
	}

	@Override
	public EmailRequest findById(Integer requestId) {
		NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(
				dataSource);
		String sqlText = getSQL(SQL_FIND_BY_REQUEST_ID);

		SqlParameterSource namedParameters = new MapSqlParameterSource(
				"requestId", requestId);

		ResulRowHandler rowHandler = new ResulRowHandler();
		jdbcTemplate.query(sqlText, namedParameters, rowHandler);

		if (rowHandler.getResults().size() > 0) {
			return rowHandler.getResults().get(0);
		} else {
			return null;
		}
	}
	
	@Override
	public List<EmailRequest> findInvalidEmailRequests(Date cutOffDate) {
		NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
		String sqlText = getSQL(SQL_GET_INVALID_REQUESTS);

		MapSqlParameterSource namedParameters = new MapSqlParameterSource(
				"cutOffDate", cutOffDate);
		namedParameters.addValue("requestStatus",Status.IN_PROGRESS.getCode());
		namedParameters.addValue("responseStatus",Status.INVALID.getCode());

		ResulRowHandler rowHandler = new ResulRowHandler();
		jdbcTemplate.query(sqlText, namedParameters,rowHandler);

		return rowHandler.getResults();
	}

	@Override
	public List<EmailRequest> findValidEmailRequests(Date cutOffDate) {
		NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
		String sqlText = getSQL(SQL_GET_VALID_REQUESTS);

		MapSqlParameterSource namedParameters = new MapSqlParameterSource(
				"cutOffDate", cutOffDate);
		
		namedParameters.addValue("requestStatus",Status.IN_PROGRESS.getCode());
		namedParameters.addValue("responseStatus",Status.INVALID.getCode());

		ResulRowHandler rowHandler = new ResulRowHandler();
		jdbcTemplate.query(sqlText, namedParameters,rowHandler);

		return rowHandler.getResults();
	}
	
	/**
	 * Class to handle the search results
	 * 
	 */
	private class ResulRowHandler implements RowCallbackHandler {
		List<EmailRequest> results = new ArrayList<EmailRequest>();

		public List<EmailRequest> getResults() {
			return results;
		}

		@Override
		public void processRow(ResultSet rs) throws SQLException {
			EmailRequest request = resultBeanHandler.handle(rs,
					EmailRequest.class);
			results.add(request);
		}
	}

}
