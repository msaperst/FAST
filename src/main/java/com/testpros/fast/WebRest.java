package com.testpros.fast;

import com.testpros.fast.reporter.Reporter;
import com.testpros.fast.reporter.RestRequest;
import com.testpros.fast.reporter.Step;
import com.testpros.fast.reporter.Step.Status;
import org.apache.http.NameValuePair;
import org.apache.http.auth.AuthenticationException;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.UnsupportedEncodingException;
import java.util.List;

public class WebRest {

    WebDriver driver;
    Reporter reporter;
    RestRequest restRequest = new RestRequest();

    public WebRest(WebDriver driver) {
        this.driver = driver;
        this.reporter = driver.getReporter();
    }

    public WebRest(WebDriver driver, String username, String password) {
        this.driver = driver;
        this.reporter = driver.getReporter();
        restRequest.setUsernamePasswordCredentials(new UsernamePasswordCredentials(username, password));
    }

    public WebRest(WebDriver driver, List<NameValuePair> headerParams) {
        this.driver = driver;
        this.reporter = driver.getReporter();
        restRequest.setHeaderParams(headerParams);
    }

    public WebRest(WebDriver driver, String username, String password, List<NameValuePair> headerParams) {
        this.driver = driver;
        this.reporter = driver.getReporter();
        restRequest.setUsernamePasswordCredentials(new UsernamePasswordCredentials(username, password));
        restRequest.setHeaderParams(headerParams);
    }

    private void setupCall(HttpRequestBase request) throws AuthenticationException {
        if (restRequest.getUsernamePasswordCredentials() != null) {
            request.addHeader(new BasicScheme().authenticate(restRequest.getUsernamePasswordCredentials(), request, null));
        }
        for (NameValuePair headerParam : restRequest.getHeaderParams()) {
            request.setHeader(headerParam.getName(), headerParam.getValue());
        }
    }

//    private String getRequestOutput() {
//        StringBuilder sb = new StringBuilder();
//        if (usernamePasswordCredentials != null) {
//            sb.append("\n    Credentials:").
//                    append("\n        Username: " + usernamePasswordCredentials.getUserName()).
//                    append("\n        Password: " + usernamePasswordCredentials.getPassword());
//        }
//        if (headerParams.size() > 0) {
//            sb.append("\n    Headers:");
//        }
//        for (NameValuePair headerParam : headerParams) {
//            sb.append("\n        ").append(headerParam.getName()).append(" : ").append(headerParam.getValue());
//        }
//        return sb.toString();
//    }
//
//    private String getResponseOutput(CloseableHttpResponse response) {
//        return "\n    Headers: " + response.getAllHeaders().toString() +
//                "\n    Status Code: " + response.getStatusLine().getStatusCode() +
//                "\n    Raw Response: " + response.getEntity().toString();
//    }

    private void copyOverCookies(HttpClientContext context) {
        CookieStore cookieStore = context.getCookieStore();
        List<Cookie> cookies = cookieStore.getCookies();
        for (Cookie cookie : cookies) {
            this.driver.manage().addCookie(new org.openqa.selenium.Cookie(cookie.getName(), cookie.getValue(), cookie.getDomain(),
                    cookie.getPath(), cookie.getExpiryDate(), cookie.isSecure()));
        }
    }

    public CloseableHttpResponse get(String url) {
        HttpGet httpGet = new HttpGet(url);
        return httpMethod(httpGet, url);
    }

    public CloseableHttpResponse post(String url) {
        HttpPost httpPost = new HttpPost(url);
        return httpMethod(httpPost, url);
    }

    public CloseableHttpResponse post(String url, List<NameValuePair> params) throws UnsupportedEncodingException {
        restRequest.setParams(params);
        HttpPost httpPost = new HttpPost(url);
        httpPost.setEntity(new UrlEncodedFormEntity(params));
        return httpMethod(httpPost, url);
    }

    public CloseableHttpResponse post(String url, String jsonBody) throws UnsupportedEncodingException {
        restRequest.setJsonBody(jsonBody);
        HttpPost httpPost = new HttpPost(url);
        httpPost.setEntity(new StringEntity(jsonBody));
        return httpMethod(httpPost, url);
    }

    public CloseableHttpResponse put(String url) {
        HttpPut httpPut = new HttpPut(url);
        return httpMethod(httpPut, url);

    }

    public CloseableHttpResponse put(String url, List<NameValuePair> params) throws UnsupportedEncodingException {
        restRequest.setParams(params);
        HttpPut httpPut = new HttpPut(url);
        httpPut.setEntity(new UrlEncodedFormEntity(params));
        return httpMethod(httpPut, url);
    }

    public CloseableHttpResponse put(String url, String jsonBody) throws UnsupportedEncodingException {
        restRequest.setJsonBody(jsonBody);
        HttpPut httpPut = new HttpPut(url);
        httpPut.setEntity(new StringEntity(jsonBody));
        return httpMethod(httpPut, url);

    }

    public CloseableHttpResponse delete(String url) {
        HttpDelete httpDelete = new HttpDelete(url);
        return httpMethod(httpDelete, url);

    }

    public CloseableHttpResponse patch(String url) {
        HttpPatch httpPatch = new HttpPatch(url);
        return httpMethod(httpPatch, url);

    }

    public CloseableHttpResponse patch(String url, List<NameValuePair> params) throws UnsupportedEncodingException {
        restRequest.setParams(params);
        HttpPatch httpPatch = new HttpPatch(url);
        httpPatch.setEntity(new UrlEncodedFormEntity(params));
        return httpMethod(httpPatch, url);

    }

    public CloseableHttpResponse patch(String url, String jsonBody) throws UnsupportedEncodingException {
        restRequest.setJsonBody(jsonBody);
        HttpPatch httpPatch = new HttpPatch(url);
        httpPatch.setEntity(new StringEntity(jsonBody));
        return httpMethod(httpPatch, url);

    }

    private CloseableHttpResponse httpMethod(HttpRequestBase httpMethod, String url) {
        String methodName = httpMethod.getMethod();
        Step step = new Step("Making '" + methodName + "' call to '" + url + "'",
                "'" + methodName + "' call to '" + url + "' successfully made", restRequest);
        try {
            CloseableHttpClient httpclient = HttpClients.createDefault();
            HttpClientContext context = HttpClientContext.create();
            setupCall(httpMethod);
            CloseableHttpResponse response = httpclient.execute(httpMethod, context);
            copyOverCookies(context);
            httpclient.close();
            step.setActual("'" + methodName + "' call to '" + url + "' successfully returned");
            step.setResponse(response);
            step.setStatus(Status.PASS);
            return response;
        } catch (Exception e) {
            step.setActual("Unable to make '" + methodName + "' call: " + e);
            step.setStatus(Status.FAIL);
        } finally {
            reporter.addStep(step, false);
        }
        return null;
    }

    //TODO
    // - other request types (multipart)
    // - file uploads
}
