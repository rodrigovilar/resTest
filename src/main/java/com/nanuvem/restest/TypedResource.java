package com.nanuvem.restest;

import java.util.List;

public abstract class TypedResource<T> {

	
	private Resource resource;
	
	public TypedResource(String url, String searchParameter) {
		this.resource = new Resource(url, searchParameter);
	}
	
	public TypedResource(String url) {
		this.resource = new Resource(url);
	}
	
	public List<T> get() {
		return toList(resource.get());
	}
	
	protected abstract List<T> toList(String jsonArray);

	public T get(String id) {
		return toObject(resource.get(id));
	}
	
	public String search(String query){
		return  resource.search(query);
	}
	
	protected abstract T toObject(String json);
	
	public int post(T t) {
		return resource.post(toJson(t));
	}

	protected abstract String toJson(T t);
	
	public int put(String id, T t) {
		return resource.put(id, toJson(t));
	}
	
	public int delete(String id) {
		return resource.delete(id);
	}

	public boolean containsSearchParameter() {
		return resource.containsSearchParameter();
	}
	
	
}
