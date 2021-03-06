package com.cloupia.feature.storage.wfInputTypeProvider;

import com.cloupia.model.cIM.FormLOVPair;
import com.cloupia.service.cIM.inframgr.forms.wizard.LOVProviderIf;
import com.cloupia.service.cIM.inframgr.forms.wizard.WizardSession;

public class SampleLovProvider implements LOVProviderIf{
	public static final String SAMPLE_LOV_PROVIDER = "storageModule_sample_lov_provider";

	@Override
	public FormLOVPair[] getLOVs(WizardSession session) {
		FormLOVPair[] pairs = new FormLOVPair[2];
    	FormLOVPair pair1 = new FormLOVPair("name", "value");
    	FormLOVPair pair2 = new FormLOVPair("label", "value");
    	pairs[0] = pair1;
    	pairs[1] = pair2;
    	return pairs;
	}
}
