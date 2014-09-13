package com.nanuvem.restest;

import java.util.List;

public abstract class TypedSubResource<S> {

	private SubResource subResource;
	private String rootUrl;
	private String subResourceUrl;
	
	public TypedSubResource(String rootUrl, String url2) {
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

	public int post(String resourceId, S s) {
		return subResource.post(resourceId, toJson(s));
	}

	protected abstract String toJson(S s);

	public int put(String idResource, String idSubResource, S s) {
		return this.subResource.put(idResource, idSubResource, toJson(s));
	}

	public int delete(String idResource, String idSubResource) {
		return this.subResource.delete(idResource, idSubResource);
	}

}
