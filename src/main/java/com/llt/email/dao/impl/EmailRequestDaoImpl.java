package com.llt.email.dao.impl;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.llt.email.dao.EmailRequestDao;
import com.llt.email.dao.helper.BeanHandler;
import com.llt.email.model.EmailRequest;

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

		jdbcTemplate.update(sqlText, new BeanPropertySqlParameterSource(request));
	}

	@Override
	public void update(Integer emailRequestId, String status) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<EmailRequest> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EmailRequest findById(Integer requestId) {
		// TODO Auto-generated method stub
		return null;
	}


}
