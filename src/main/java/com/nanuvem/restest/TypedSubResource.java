package com.nanuvem.restest;

import java.io.IOException;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.util.EntityUtils;

public abstract class TypedSubResource<S> {

	private SubResource subResource;
	
	public TypedSubResource(String rootUrl, String subResourceUrl) {
		this.subResource = new SubResource (rootUrl, subResourceUrl);
	}

	public List<S> get(String iD) {
		return toList(subResource.get(iD));
	}

	protected abstract List<S> toList(String jsonArray);

	public S get(String idResource, String idSubResource) {
		return toObject(subResource.get(idResource, idSubResource));
	}

	protected abstract S toObject(String json);

	public S toObject(HttpResponse response) throws ParseException, IOException {
		return toObject(EntityUtils.toString(response.getEntity()));
	}
	
	public HttpResponse post(String resourceId, S s) {
		return subResource.post(resourceId, toJson(s));
	}

	protected abstract String toJson(S s);

	public HttpResponse put(String idResource, String idSubResource, S s) {
		return this.subResource.put(idResource, idSubResource, toJson(s));
	}

	public int delete(String idResource, String idSubResource) {
		return this.subResource.delete(idResource, idSubResource);
	}

}
