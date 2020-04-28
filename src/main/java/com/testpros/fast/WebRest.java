package com.testpros.fast;

import com.testpros.fast.reporter.Reporter;
import com.testpros.fast.reporter.Step;
import com.testpros.fast.reporter.Step.Status;
import org.apache.http.NameValuePair;
import org.apache.http.auth.AuthenticationException;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WebRest {

    WebDriver driver;
    Reporter reporter;
    UsernamePasswordCredentials usernamePasswordCredentials = null;
    List<NameValuePair> headerParams = new ArrayList<>();

    public WebRest(WebDriver driver) {
        this.driver = driver;
        this.reporter = driver.getReporter();
    }

    public WebRest(WebDriver driver, String username, String password) {
        this.driver = driver;
        this.reporter = driver.getReporter();
        usernamePasswordCredentials = new UsernamePasswordCredentials(username, password);
    }

    public WebRest(WebDriver driver, List<NameValuePair> headerParams) {
        this.driver = driver;
        this.reporter = driver.getReporter();
        this.headerParams = headerParams;
    }

    private void setupCall(HttpRequestBase request) throws AuthenticationException {
        if (usernamePasswordCredentials != null) {
            request.addHeader(new BasicScheme().authenticate(usernamePasswordCredentials, request, null));
        }
        for (NameValuePair headerParam : headerParams) {
            request.setHeader(headerParam.getName(), headerParam.getValue());
        }
    }

    private String getRequestOutput() {
        StringBuilder sb = new StringBuilder();
        if (usernamePasswordCredentials != null) {
            sb.append("\n    Credentials:").
                    append("\n        Username: " + usernamePasswordCredentials.getUserName()).
                    append("\n        Password: " + usernamePasswordCredentials.getPassword());
        }
        if (headerParams.size() > 0) {
            sb.append("\n    Headers:");
        }
        for (NameValuePair headerParam : headerParams) {
            sb.append("\n        ").append(headerParam.getName()).append(" : ").append(headerParam.getValue());
        }
        return sb.toString();
    }

    private String getResponseOutput(CloseableHttpResponse response) {
        return "\n    Headers: " + response.getAllHeaders().toString() +
                "\n    Status Code: " + response.getStatusLine().getStatusCode() +
                "\n    Raw Response: " + response.getEntity().toString();
    }

    private void copyOverCookies(HttpClientContext context) {
        CookieStore cookieStore = context.getCookieStore();
        List<Cookie> cookies = cookieStore.getCookies();
        for (Cookie cookie : cookies) {
            this.driver.manage().addCookie(new org.openqa.selenium.Cookie(cookie.getName(), cookie.getValue(), cookie.getDomain(),
                    cookie.getPath(), cookie.getExpiryDate(), cookie.isSecure()));
        }
    }

    public CloseableHttpResponse get(String url) {
        Step step = new Step("Making 'get' call to '" + url + "'",
                "'get' call to '" + url + "' successfully made with: " + getRequestOutput());
        try {
            CloseableHttpClient httpclient = HttpClients.createDefault();
            HttpClientContext context = HttpClientContext.create();
            HttpGet httpGet = new HttpGet(url);
            setupCall(httpGet);
            CloseableHttpResponse response = httpclient.execute(httpGet, context);
            copyOverCookies(context);
            httpclient.close();
            step.setActual("'get' call to '" + url + "' returned: " + getResponseOutput(response));
            step.setStatus(Status.PASS);
            return response;
        } catch (Exception e) {
            step.setActual("Unable to make get call: " + e);
            step.setStatus(Status.FAIL);
        } finally {
            reporter.addStep(step);
        }
        return null;
    }

    public CloseableHttpResponse post(String url, List<NameValuePair> params) throws IOException, AuthenticationException {
        Step step = new Step("Making 'post' call to '" + url + "'",
                "'post' call to '" + url + "' successfully made with: " + getRequestOutput() +
                        "\n    Parameters: " + params.toString());
        try {
            CloseableHttpClient httpclient = HttpClients.createDefault();
            HttpClientContext context = HttpClientContext.create();
            HttpPost httpPost = new HttpPost(url);
            setupCall(httpPost);
            httpPost.setEntity(new UrlEncodedFormEntity(params));
            CloseableHttpResponse response = httpclient.execute(httpPost, context);
            copyOverCookies(context);
            httpclient.close();
            step.setActual("'post' call to '" + url + "' returned: " + getResponseOutput(response));
            step.setStatus(Status.PASS);
            return response;
        } catch (Exception e) {
            step.setActual("Unable to make post call: " + e);
            step.setStatus(Status.FAIL);
        } finally {
            reporter.addStep(step);
        }
        return null;
    }

    public CloseableHttpResponse post(String url, String jsonBody) throws IOException, AuthenticationException {
        Step step = new Step("Making 'post' call to '" + url + "'",
                "'post' call to '" + url + "' successfully made with: " + getRequestOutput() +
                        "\n    Parameters: " + jsonBody);
        try {
            CloseableHttpClient httpclient = HttpClients.createDefault();
            HttpClientContext context = HttpClientContext.create();
            HttpPost httpPost = new HttpPost(url);
            setupCall(httpPost);
            httpPost.setEntity(new StringEntity(jsonBody));
            CloseableHttpResponse response = httpclient.execute(httpPost, context);
            copyOverCookies(context);
            httpclient.close();
            step.setActual("'post' call to '" + url + "' returned: " + getResponseOutput(response));
            step.setStatus(Status.PASS);
            return response;
        } catch (Exception e) {
            step.setActual("Unable to make post call: " + e);
            step.setStatus(Status.FAIL);
        } finally {
            reporter.addStep(step);
        }
        return null;
    }

    //TODO
    // - Add logging - might need to cleanup
    // - other methods
    // - other request types (multipart)
    // - file uploads
    // -
}
