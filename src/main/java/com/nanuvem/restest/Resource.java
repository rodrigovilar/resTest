package com.nanuvem.restest;

import java.net.URI;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class Resource {

	private CloseableHttpClient httpclient;

	private HttpGet getAll;
	private HttpGet getUnique;
	private HttpGet search;
	private HttpPost post;
	private HttpPut put;
	private HttpDelete delete;

	private String url;
	private String searchParameter;

	public Resource(String url, String searchParameter) {
		this.url = url;
		this.searchParameter = searchParameter;
		getAll = new HttpGet(url);
		post = new HttpPost(url);
		put = new HttpPut(url);
		delete = new HttpDelete(url);
	}

	public Resource(String url) {
		this(url, null);
	}

	public String get() {
		try {
			httpclient = HttpClients.createDefault();
			HttpResponse response = httpclient.execute(getAll);
			return EntityUtils.toString(response.getEntity());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public String get(String id) {
		try {
			getUnique = new HttpGet(url + "/" + id);
			httpclient = HttpClients.createDefault();
			HttpResponse response = httpclient.execute(getUnique);
			return EntityUtils.toString(response.getEntity());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public String search(String query) {

		try {

			search = new HttpGet(url + "?" + searchParameter + "="
					+ query);
			httpclient = HttpClients.createDefault();
			HttpResponse response = httpclient.execute(search);
			return EntityUtils.toString(response.getEntity());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	public HttpResponse post(String json) {
		try {
			post.setEntity(new StringEntity(json));
			httpclient = HttpClients.createDefault();
			HttpResponse response = httpclient.execute(post);
			return response;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	public HttpResponse put(String id, String json) {
		try {
			put.setURI(new URI(url + "/" + id));
			put.setEntity(new StringEntity(json));
			httpclient = HttpClients.createDefault();
			HttpResponse response = httpclient.execute(put);
			return response;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public int delete(String id) {
		try {
			delete.setURI(new URI(url + "/" + id));
			httpclient = HttpClients.createDefault();
			HttpResponse response = httpclient.execute(delete);
			return response.getStatusLine().getStatusCode();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public boolean containsSearchParameter() {
		return searchParameter != null;
	}
}
