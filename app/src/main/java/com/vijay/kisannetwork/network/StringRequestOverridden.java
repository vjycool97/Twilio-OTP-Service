package com.vijay.kisannetwork.network;

import android.util.Base64;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class StringRequestOverridden extends StringRequest {

    private static final String TO = "To";
    //    private static final String FROM = "From";
//    private static final String TO = "To";
    private String username, password;
//    private String to;
//    private String from;

    public StringRequestOverridden(String url,
                                   String usernameParam,
                                   String passwordParam,
                                   Response.Listener<String> listener,
                                   Response.ErrorListener errorListener) {
        super(Method.POST, url, listener, errorListener);
        this.username = usernameParam;
        this.password = passwordParam;

    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return createBasicAuthHeader(this.username, this.password);
    }

    private Map<String, String> createBasicAuthHeader(String username, String password) {
        Map<String, String> headerMap = new HashMap<String, String>();

        String credentials = username + ":" + password;
        
        String encodedCredentials = Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
        headerMap.put("Authorization", "Basic " + encodedCredentials);
        return headerMap;
    }

}