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

import com.llt.email.dao.EmailJobDao;
import com.llt.email.dao.helper.BeanHandler;
import com.llt.email.model.EmailJob;

@Repository("emailJobDao")
public class EmailJobDaoImpl extends BaseDaoImpl implements EmailJobDao {
	@Autowired
	@Qualifier("jndiDataSource")
	protected DataSource dataSource;
	
	@Autowired
	@Qualifier("beanHandler")
	protected BeanHandler<EmailJob> resultBeanHandler;


	@Override
	public void updateJob(EmailJob job) {
		NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(
				dataSource);

		String sqlText = getSQL(SQL_UPDATE_JOB);

		jdbcTemplate.update(sqlText,
				new BeanPropertySqlParameterSource(job));
	}
	
	@Override
	public void insertJob(EmailJob job) {
		NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(
				dataSource);

		String sqlText = getSQL(SQL_INSERT_JOB);

		jdbcTemplate.update(sqlText,
				new BeanPropertySqlParameterSource(job));
	}

	@Override
	public EmailJob getJob(String jobName) {
		NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(
				dataSource);
		String sqlText = getSQL(SQL_GET_JOB);

		SqlParameterSource namedParameters = new MapSqlParameterSource(
				"jobName", jobName);

		ResulRowHandler rowHandler = new ResulRowHandler();
		jdbcTemplate.query(sqlText, namedParameters, rowHandler);

		if (rowHandler.getResults().size() > 0) {
			return rowHandler.getResults().get(0);
		} else {
			return null;
		}
	}
	
	private class ResulRowHandler implements RowCallbackHandler {
		List<EmailJob> results = new ArrayList<EmailJob>();

		public List<EmailJob> getResults() {
			return results;
		}

		@Override
		public void processRow(ResultSet rs) throws SQLException {
			EmailJob request = resultBeanHandler.handle(rs,
					EmailJob.class);
			results.add(request);
		}
	}

}
