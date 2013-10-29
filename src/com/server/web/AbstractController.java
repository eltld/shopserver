
package com.server.web;

import java.io.OutputStream;

import org.springframework.ui.ModelMap;

import com.server.service.core.ServiceResponse;


public class AbstractController {

	protected static final String AJAX = "_AJAX";
	
	
	protected static final String RESULT = "_RESULT";
	
	
	protected static final String ID = "_ID";

	protected static final String AJAX_RESULT = "/common/ajax";
	
	
	protected static final String RESULT_PAGE = "/common/result";

	
	protected String ajax(Object result, ModelMap model) throws Exception {
		model.addAttribute(AJAX, result);
		return AJAX_RESULT;
	}
	
	
	protected String result(ModelMap model) throws Exception {
		model.addAttribute("success", true);
		return RESULT_PAGE;
	}
	
	
	protected void response(OutputStream out,ServiceResponse response) throws Exception {
		byte[] b = response.toString().getBytes("UTF-8");
		out.write(b);
	}
	
	protected String getView(String jsp) throws Exception {
		return jsp.replaceAll("(\\.jsp)$", "");
	}
}