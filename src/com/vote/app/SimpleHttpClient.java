package com.vote.app;

import android.os.StrictMode;
import android.util.Log;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class SimpleHttpClient {
	/**
	 * The time it takes for our client to timeout
	 */
	public static final int HTTP_TIMEOUT = 30 * 1000; // milliseconds
	private static String serverURL = "http://52.76.83.72:8080/vote";

	/**
	 * Single instance of our HttpClient
	 */
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
	 * Performs an HTTP Post request to the specified url with the specified
	 * parameters.
	 * 
	 * @param submitRequest
	 * @param postParameters
	 *            The parameters to send via the request
	 * @return The result of the request
	 * @throws Exception
	 */
	public static String executeHttpPost(String submitRequest,
			ArrayList<NameValuePair> postParameters) throws Exception {

		String url = serverURL + submitRequest;

		BufferedReader in = null;
		try {
			HttpClient client = getHttpClient();

			HttpPost request = new HttpPost(url);

			UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(
					postParameters);

			request.setEntity(formEntity);

			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
					.permitAll().build();
			StrictMode.setThreadPolicy(policy);

			HttpResponse response = client.execute(request);

			in = new BufferedReader(new InputStreamReader(response.getEntity()
					.getContent()));

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
					Log.e("SimpleHttpClient", String.valueOf(e));
				}
			}
		}
	}
}