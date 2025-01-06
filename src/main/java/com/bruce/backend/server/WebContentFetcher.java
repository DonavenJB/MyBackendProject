package com.bruce.backend.server;

import com.bruce.backend.dataconversion.DataConversion;
import com.bruce.backend.stringmanipulation.StringManipulation;

import com.bruce.backend.common.LogSanity;
import com.bruce.backend.common.Request;
import com.bruce.backend.common.StringStack;

import java.net.HttpURLConnection;
import java.net.URL;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;

import com.bruce.backend.ssl.TrustEventListener;
import com.bruce.backend.ssl.SecureSocket;

public class WebContentFetcher implements Runnable, HostnameVerifier, TrustEventListener {
    
    protected Request userRequest;
    protected ManageUser userClient;

    public WebContentFetcher(Request userRequest, ManageUser userClient) {
        this.userRequest = userRequest;
        this.userClient = userClient;
        (new Thread(this, "Fetch: " + userRequest.arg(0))).start();
    }

    @Override
    public boolean trust(String hostname) {
        return true;
    }

    @Override
    public boolean verify(String hostname, SSLSession sslSession) {
        return true;
    }

    private String fetchContent(String urlString) throws Exception {
        String resolvedUrl;
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        if (connection instanceof HttpsURLConnection) {
            HttpsURLConnection secureConnection = (HttpsURLConnection) connection;
            secureConnection.setHostnameVerifier(this);
            secureConnection.setSSLSocketFactory(SecureSocket.getMyFactory(this));
        }

        connection.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1)");
        connection.setInstanceFollowRedirects(true);

        byte[] contentBytes = DataConversion.readAll(connection.getInputStream());
        if (connection.getResponseCode() == 302 || connection.getResponseCode() == 301) {
            return fetchContent(connection.getHeaderField("location"));
        }

        String contentString = DataConversion.bString(contentBytes);

        if (!url.getFile().endsWith("/")) {
            StringStack urlPathStack = new StringStack(url.getFile(), "/");
            // urlPathStack.pop();
            resolvedUrl = StringManipulation.strrep(urlString, url.getFile(), urlPathStack.toString() + "/");
        } else {
            resolvedUrl = urlString;
        }

        if (!contentString.toLowerCase().contains("shortcut icon") && !contentString.toLowerCase().contains("rel=\"icon\"")) {
            contentString = contentString.replaceFirst("(?i:<head.*?>)", "$0\n<link rel=\"shortcut icon\" type=\"image/x-icon\" href=\"/favicon.ico\">");
        }

        if (!contentString.toLowerCase().contains("<base href=")) {
            contentString = contentString.replaceFirst("(?i:<head.*?>)", "$0\n<base href=\"" + resolvedUrl + "\">");
        }

        return contentString;
    }

    @Override
    public void run() {
        String initialUrl = this.userRequest.arg(0) + "";
        try {
            String fetchedContent = fetchContent(initialUrl);
            this.userClient.write(this.userRequest.reply(fetchedContent));
        } catch (Exception exception) {
            LogSanity.logException("fetch: " + initialUrl, exception, false);
            this.userClient.write(this.userRequest.reply("error: " + exception.getMessage()));
        }
    }
}
