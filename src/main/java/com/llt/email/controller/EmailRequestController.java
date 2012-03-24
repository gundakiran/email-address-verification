package com.llt.email.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.llt.email.model.EmailRequest;

@Controller
public class EmailRequestController {
	
	private static final Logger logger = LoggerFactory.getLogger(EmailRequestController.class);
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Model model) {
		
		List<EmailRequest> emailRequests = createDummyEmailRequests(5);
		model.addAttribute(emailRequests);
		
		return "requestlist";
	}

	@RequestMapping(value = "/cancel", method = RequestMethod.GET)
	public String cancelEmailRequest(Model model) {
		return "redirect:/";
	}

	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public String createNewemailRequest(Model model) {
		logger.info("emailRequestPresent called ");
		
		model.addAttribute(new EmailRequest());
		
		return "emailrequest";
	}

	@RequestMapping(value = "/", method = RequestMethod.POST)
	public String emailRequestHandle(@Valid EmailRequest emailRequest, BindingResult bindingResult, Model model) {
		logger.info("emailRequestHandle Called");
		if(bindingResult.hasErrors()){
			logger.info("----------------EmaiRequest is In-Valid ----------");
			return "emailrequest";
		}
		logger.info("----------------EmaiRequest is Valid ----------");
		logger.info(emailRequest.toString());
		
		model.addAttribute("success", "Request saved successfully!!!!");
		
		return "emailrequest";
	}

	// --------- Replace these with actual DB Service Layer ------------
	private List<EmailRequest> createDummyEmailRequests(int numberOfRequestsToCreate) {
		List<EmailRequest> emailRequests = new ArrayList<EmailRequest>();
		for(int i=0; i<numberOfRequestsToCreate; i++){
			EmailRequest emailRequest = new EmailRequest();
			emailRequest.setDomain("domain-" + i);
			emailRequest.setFirstName("firstName-" + i);
			emailRequest.setLastName("lastName-" + i);
			
			emailRequests.add(emailRequest);
		}
		return emailRequests;
	}
	
}


///**
//* Simply selects the home view to render by returning its name.
//*/
//@RequestMapping(value = "/", method = RequestMethod.GET)
//public String home(Locale locale, Model model) {
//	logger.info("Welcome home! the client locale is "+ locale.toString());
//	
//	Date date = new Date();
//	DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
//	
//	String formattedDate = dateFormat.format(date);
//	
//	model.addAttribute("serverTime", formattedDate );
//	
//	return "home";
//}
