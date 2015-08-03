/**
 *
 */
package com.psc.vote.app;

import android.os.StrictMode;
import android.util.Log;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.ArrayList;
import java.util.Map;

public class SimpleHttpClient {
    /** The time it takes for our client to timeout */
    public static final int HTTP_TIMEOUT = 30 * 1000; // milliseconds
    private static String serverURL = "http://52.74.54.79:8080/vote";

    /** Single instance of our HttpClient */
    private static HttpClient mHttpClient;

    /**
     * Get our single instance of our HttpClient object.
     *
     * @return an HttpClient object with connection parameters set
     */
    private static HttpClient getHttpClient() {
        if (mHttpClient == null) {
            mHttpClient = new DefaultHttpClient();
            final HttpParams params = mHttpClient.getParams();
            HttpConnectionParams.setConnectionTimeout(params, HTTP_TIMEOUT);
            HttpConnectionParams.setSoTimeout(params, HTTP_TIMEOUT);
            ConnManagerParams.setTimeout(params, HTTP_TIMEOUT);
        }
        return mHttpClient;
    }

    /**
     * Performs an HTTP Post request to the specified url with the
     * specified parameters.
     *
     * @param postParameters The parameters to send via the request
     * @return The result of the request
     * @throws Exception
     */
    public static String executeHttpPost(ArrayList<NameValuePair> postParameters) throws Exception {
        Log.i("SimpleHttpClient", "inside");
        String url = serverURL + "/vote/registerpost";
        System.out.println("url::" + url);
        System.out.println("postParameters::" + postParameters);

        BufferedReader in = null;
        try {
            HttpClient client = getHttpClient();
            System.out.println("client::" + client);
            Log.i("SimpleHttpClient::2", client.toString());
            HttpPost request = new HttpPost(url);
            Log.i("SimpleHttpClient::3", request.toString());

            UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(postParameters);
            Log.i("SimpleHttpClient::4", formEntity.toString());
            System.out.println("request::" + request);
            System.out.println("formEntity::" + formEntity);
            request.setEntity(formEntity);
            System.out.println("request:2:" + request);
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            System.out.println("request:3:" + "Strict Mode assigned");
            HttpResponse response = client.execute(request);
            System.out.println("response::" + response.toString());
            Log.i("SimpleHttpClient::5", response.toString());
            in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            Log.i("SimpleHttpClient::6", in.toString());

            StringBuffer sb = new StringBuffer("");
            Log.i("SimpleHttpClient::7", sb.toString());

            String line = "";
            String NL = System.getProperty("line.separator");
            Log.i("SimpleHttpClient::8 ", NL.toString());

            while ((line = in.readLine()) != null) {
                Log.i("SimpleHttpClient::9 ", "");
                sb.append(line + NL);
            }
            in.close();

            Log.i("SimpleHttpClient", "last");
            String result = sb.toString();
            Log.i("SimpleHttpClient ::", result);
            return result;
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e("SimpleHttpClient", String.valueOf(e));
                }
            }
        }
    }

    public static String executeHttpPost2() throws Exception {
        Log.i("executeHttpPost2", "inside");
        String url = serverURL + "/vote/register?username=%22sandeep%22&password=%22info%22";
        System.out.println("url::" + url);

        BufferedReader in = null;
        try {
            HttpClient client = getHttpClient();
            HttpPost request = new HttpPost(url);
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            HttpResponse response = client.execute(request);
            System.out.println("response::" + response);
            return response.toString();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    Log.e("SimpleHttpClient", String.valueOf(e));
                }
            }
        }
    }

    /**
     * Performs an HTTP GET request to the specified url.
     *
     * @param url The web address to post the request to
     * @return The result of the request
     * @throws Exception
     */
    public static String executeHttpGet(String url) throws Exception {
        BufferedReader in = null;
        try {
            HttpClient client = getHttpClient();
            HttpGet request = new HttpGet();
            request.setURI(new URI(url));
            HttpResponse response = client.execute(request);
            in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

            StringBuffer sb = new StringBuffer("");
            String line = "";
            String NL = System.getProperty("line.separator");
            while ((line = in.readLine()) != null) {
                sb.append(line + NL);
            }
            in.close();

            String result = sb.toString();
            return result;
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static Map<String, String> executeHttpGetMap(String url) throws Exception {
        BufferedReader in = null;
        try {
            HttpClient client = getHttpClient();
            HttpGet request = new HttpGet();
            request.setURI(new URI(url));
            HttpResponse response = client.execute(request);
            Map<String, String> responseInfo = (Map<String, String>) response.getEntity().getContent();
            return responseInfo;
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}