package com.cloupia.feature.storage.wfInputTypeProvider;

import com.cloupia.service.cIM.inframgr.customactions.UserInputValidatorIf;

public class EmailIdValidator implements UserInputValidatorIf {

	@Override
	public String getValidatorDescription() {
		return "Validating email address..";
	}

	@Override
	public void validateUserInput(String emailId) throws Exception {
		
		String EMAIL_REGEX = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
		
		Boolean b = emailId.matches(EMAIL_REGEX);
		
		if(! b){
			throw new Exception("Enter a valid Email address.");
		}
	}

}
