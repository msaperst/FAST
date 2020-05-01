package com.testpros.fast.reporter;

import org.apache.http.NameValuePair;
import org.apache.http.auth.UsernamePasswordCredentials;

import java.util.ArrayList;
import java.util.List;

public class RestRequest {

    private UsernamePasswordCredentials usernamePasswordCredentials;
    private List<NameValuePair> headerParams = new ArrayList<>();
    private List<NameValuePair> params;
    private String jsonBody;

    public RestRequest() {

    }

    public List<NameValuePair> getParams() {
        return params;
    }

    public void setParams(List<NameValuePair> params) {
        this.params = params;
    }

    public String getJsonBody() {
        return jsonBody;
    }

    public void setJsonBody(String jsonBody) {
        this.jsonBody = jsonBody;
    }

    public UsernamePasswordCredentials getUsernamePasswordCredentials() {
        return usernamePasswordCredentials;
    }

    public void setUsernamePasswordCredentials(UsernamePasswordCredentials usernamePasswordCredentials) {
        this.usernamePasswordCredentials = usernamePasswordCredentials;
    }

    public List<NameValuePair> getHeaderParams() {
        return headerParams;
    }

    public void setHeaderParams(List<NameValuePair> headerParams) {
        this.headerParams = headerParams;
    }

}
