package com.llt.email.integration;

import java.util.List;

import com.llt.email.model.EmailRequest;
import com.llt.email.model.EmailResponse;

/**
 * Spring Integration Gateway which will take the
 * domain request and split the request into 10
 * different combinations and sends the email to
 * each of these combinations and aggregate the
 * results and return the 10 responses.
 *
 */
public interface EmailIntegrationGateway {
	
	//TODO: check with Sri and see if he wants to do it this way.
	public List<EmailResponse> process(EmailRequest request);

}
