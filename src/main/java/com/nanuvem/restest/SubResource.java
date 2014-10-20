package com.nanuvem.restest;



import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class SubResource {

	private CloseableHttpClient httpclient;

	private HttpGet getAll;
	private HttpGet getUnique;
	private HttpPost post;
	private HttpPut put;
	private HttpDelete delete;

	private String rootUrl;

	private String subResourceUrl;

	public SubResource(String rootUrl, String subResourceUrl) {
		this.rootUrl = rootUrl;
		this.subResourceUrl = subResourceUrl;
	}

	public String get(String resourceId) {
		try {
			getAll = new HttpGet(rootUrl + "/" + resourceId + "/"
					+ subResourceUrl);
			httpclient = HttpClients.createDefault();
			HttpResponse response = httpclient.execute(getAll);
			return EntityUtils.toString(response.getEntity());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public String get(String resourceId, String subResourceId) {
		try {
			getUnique = new HttpGet(rootUrl + "/" + resourceId + "/"
					+ subResourceUrl + "/" + subResourceId);
			httpclient = HttpClients.createDefault();
			HttpResponse response = httpclient.execute(getUnique);
			return EntityUtils.toString(response.getEntity());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public HttpResponse post(String resourceId, String json) {
		try {

			post = new HttpPost(rootUrl + "/" + resourceId + "/"
					+ subResourceUrl);
			post.setEntity(new StringEntity(json));
			httpclient = HttpClients.createDefault();
			HttpResponse response = httpclient.execute(post);
			return response;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	public HttpResponse put(String idResource, String idSubResource, String json) {
		try {
			put = new HttpPut(rootUrl + "/" + idResource + "/" + subResourceUrl
					+ "/" + idSubResource);
			put.setEntity(new StringEntity(json));
			httpclient = HttpClients.createDefault();
			HttpResponse response = httpclient.execute(put);
			return response;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public int delete(String idResource, String idSubresource) {
		try {
			delete = new HttpDelete(rootUrl + "/" + idResource + "/"
					+ subResourceUrl + idSubresource);
			httpclient = HttpClients.createDefault();
			HttpResponse response = httpclient.execute(delete);
			return response.getStatusLine().getStatusCode();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
