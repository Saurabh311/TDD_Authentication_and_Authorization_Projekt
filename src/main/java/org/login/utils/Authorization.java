package org.login.utils;

import org.login.enums.Resource;
import org.login.enums.Rights;
import java.util.HashMap;
import java.util.List;

public class Authorization {
    private HashMap<String, List<String>> authorizations = new HashMap();

    public Authorization() {
        authorizations.put(Resource.ACCOUNT.toString(), List.of(Rights.READ.toString()));
        authorizations.put(Resource.ACCOUNT.toString(), List.of(Rights.READ.toString(), Rights.WRITE.toString()));
        authorizations.put(Resource.PROVISION_CALC.toString(), List.of(Rights.EXECUTE.toString()));
    }
}
