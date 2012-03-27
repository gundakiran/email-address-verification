package com.llt.email.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.llt.email.dao.EmailResponseDao;
import com.llt.email.dao.helper.BeanHandler;
import com.llt.email.model.EmailResponse;
import com.llt.email.util.Status;

@Repository("emailResponseDao")
public class EmailResponseDaoImpl extends BaseDaoImpl implements
		EmailResponseDao {
	@Autowired
	@Qualifier("jndiDataSource")
	protected DataSource dataSource;

<<<<<<< HEAD
=======
@Repository("emailResponseDao")
public class EmailResponseDaoImpl extends BaseDaoImpl implements EmailResponseDao {
	@Autowired
	@Qualifier("jndiDataSource")
	protected DataSource dataSource;

>>>>>>> 218c1babb4f88c17bedcd22874948e3328d899c1
	@Autowired
	@Qualifier("beanHandler")
	protected BeanHandler<EmailResponse> resultBeanHandler;

	@Override
	public void insert(EmailResponse response) {
		NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(
				dataSource);

		String sqlText = getSQL(SQL_INSERT_RESPONSE);

<<<<<<< HEAD
		jdbcTemplate.update(sqlText, new BeanPropertySqlParameterSource(
				response));
=======
		jdbcTemplate.update(sqlText,
				new BeanPropertySqlParameterSource(response));
>>>>>>> 218c1babb4f88c17bedcd22874948e3328d899c1
	}

	@Override
	public List<EmailResponse> findByRequestId(Integer requestId) {
		NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(
				dataSource);
		String sqlText = getSQL(SQL_GET_RESPONSES_BY_REQUEST_ID);
<<<<<<< HEAD

		SqlParameterSource namedParameters = new MapSqlParameterSource(
				"requestId", requestId);

		ResulRowHandler rowHandler = new ResulRowHandler();
		jdbcTemplate.query(sqlText, namedParameters, rowHandler);

		return rowHandler.getResults();
	}

=======

		SqlParameterSource namedParameters = new MapSqlParameterSource(
				"requestId", requestId);

		ResulRowHandler rowHandler = new ResulRowHandler();
		jdbcTemplate.query(sqlText, namedParameters, rowHandler);

		return rowHandler.getResults();
	}
	
>>>>>>> 218c1babb4f88c17bedcd22874948e3328d899c1
	/**
	 * Class to handle the search results
	 * 
	 */
	private class ResulRowHandler implements RowCallbackHandler {
		List<EmailResponse> results = new ArrayList<EmailResponse>();

		public List<EmailResponse> getResults() {
			return results;
		}

		@Override
		public void processRow(ResultSet rs) throws SQLException {
			EmailResponse request = resultBeanHandler.handle(rs,
					EmailResponse.class);
			results.add(request);
		}
	}
<<<<<<< HEAD

	@Override
	public List<EmailResponse> findByStatus(Integer requestId, Status status) {
		NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(
				dataSource);
		String sqlText = getSQL(SQL_GET_RESPONSES_BY_REQUEST_ID_AND_STATUS);

		MapSqlParameterSource namedParameters = new MapSqlParameterSource(
				"requestId", requestId);
		namedParameters.addValue("status", status.getCode());

		ResulRowHandler rowHandler = new ResulRowHandler();
		jdbcTemplate.query(sqlText, namedParameters, rowHandler);

		return rowHandler.getResults();
	}
=======
>>>>>>> 218c1babb4f88c17bedcd22874948e3328d899c1
}