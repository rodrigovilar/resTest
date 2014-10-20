package com.nanuvem.restest;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.apache.http.HttpResponse;
import org.junit.Before;
import org.junit.Test;

public abstract class TypedSubResourceTest<T,S> {

	private TypedResource<T> resource;
	private TypedSubResource<S> subResource;

	@Before
	public void init() {
		resource = createTypedResource();
		subResource = createTypedSubResource();
	}

	protected abstract TypedResource<T> createTypedResource();
	
	protected abstract TypedSubResource<S> createTypedSubResource();

	@Test
	public void testingRestSubResource() throws Exception {
		
		assertEquals(0, resource.get().size());
		
		T t = createObjectResource();
		HttpResponse response = resource.post(t);
		assertEquals(201, response.getStatusLine().getStatusCode());
		T t2 = resource.toObject(response);
		assertEquals(t, t2);
		
		List<T> ts = resource.get();
		assertEquals(1, ts.size());
		assertEquals(t, ts.get(0));
		
		Object ID = getIdResource(t2);
		
		assertEquals(0, subResource.get(ID.toString()).size());
		
		S s = createObjectSubResource();
		response = subResource.post(ID.toString(), s);
		assertEquals(201, response.getStatusLine().getStatusCode());
		S s2 = subResource.toObject(response);
		assertEquals(s, s2);
		
		List<S> sList = subResource.get(ID.toString());
		assertEquals(1, sList.size());
		assertEquals(s, sList.get(0));
		
		Object IDSubResource1 =  getIdSubResource(s2);
		
		S sPesquisado = subResource.get(ID.toString(), IDSubResource1.toString());
		assertEquals(s, sPesquisado);
		
		S sEditado = editObjectSubResource(sPesquisado);
		
		response = subResource.put(ID.toString(), IDSubResource1.toString(), sEditado);
		assertEquals(200, response.getStatusLine().getStatusCode());
		S s3 = subResource.toObject(response);
		assertEquals(s3, sEditado);
		
		Object IDSubResource2 =  getIdSubResource(s2);
		
		S sPesquisado2 = subResource.get(ID.toString(), IDSubResource2.toString());
		
		Object IDSubResource3 =  getIdSubResource(s2);
		
		assertEquals( sPesquisado, sPesquisado2 );
		
		assertEquals(200,subResource.delete(ID.toString(), IDSubResource3.toString()));
		assertEquals(0, subResource.get(ID.toString()).size()); 
		
		assertEquals(200, resource.delete(ID.toString()));
		assertEquals(0, resource.get().size());
		
		
	}
	
	
	protected abstract T createObjectResource();

	protected abstract Object getIdResource(T t);
	
	protected abstract Object getIdSubResource(S s);
	
	protected abstract S createObjectSubResource();
	
	protected abstract S editObjectSubResource( S s);

}
