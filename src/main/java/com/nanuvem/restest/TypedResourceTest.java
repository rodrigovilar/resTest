package com.nanuvem.restest;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.junit.Before;
import org.junit.Test;

public abstract class TypedResourceTest<T> {

	private TypedResource<T> resource;
	private String queryThatReturns1and2;
	private String queryThatReturns3;

	@Before
	public void init() {
		resource = createTypedResource();
		queryThatReturns1and2 = getQueryThatReturns1and2();
		queryThatReturns3 = getQueryThatReturns3();
	}

	protected abstract TypedResource<T> createTypedResource();

	protected abstract String getQueryThatReturns1and2();

	protected abstract String getQueryThatReturns3();

	@Test
	public void testingRestMethods() throws ParseException, IOException {
		assertEquals(0, resource.get().size());

		T t = createObjectResource1();

		HttpResponse response = resource.post(t);
		assertEquals(201, response.getStatusLine().getStatusCode());
		T t2 = resource.toObject(response);
		assertEquals(t, t2);
		
		List<T> ts = resource.get();
		assertEquals(1, ts.size());
		assertEquals(t, ts.get(0));
		
		Object ID = getIdResource(t2);
		
		T newT = resource.get(ID.toString());
		assertEquals(t, newT);

		T editedT = editObjectResoucer(newT);
		assertEquals(200, resource.put(ID.toString(), editedT));
		response = resource.put(ID.toString(), editedT);
		assertEquals(200, response.getStatusLine().getStatusCode());
		T editedT2 = resource.toObject(response);
		assertEquals(editedT, editedT2);
		
		ts = resource.get();
		assertEquals(1, ts.size());
		assertEquals(editedT, ts.get(0));
		
		T editedT3 = resource.get(ID.toString());
		assertEquals(editedT, editedT3);
		

		assertEquals(200, resource.delete(ID.toString()));
		
		assertEquals(0, resource.get().size());

	}

	@Test
	public void testingRestSearch() throws Exception {

		if (!resource.containsSearchParameter()) {
			return;
		}

		assertEquals(0, resource.get().size());

		T t1 = this.createObjectResource1();
		HttpResponse response = resource.post(t1);
		assertEquals(201, response.getStatusLine().getStatusCode());
		T t1rs = resource.toObject(response);
		assertEquals(t1, t1rs);
		

		T t2 = this.createObjectResource2();
		response = resource.post(t2);
		assertEquals(201, response.getStatusLine().getStatusCode());
		T t2rs = resource.toObject(response);
		assertEquals(t2, t2rs);

		T t3 = this.createObjectResource3();
		response = resource.post(t3);
		assertEquals(201, response.getStatusLine().getStatusCode());
		T t3rs = resource.toObject(response);
		assertEquals(t3, t3rs);

		List<T> tList = resource.get();
		assertEquals(3, tList.size());

		tList = resource.toList(resource.search(queryThatReturns1and2));
		assertEquals(2, tList.size());
		
		assertEquals(t1, tList.get(0));
		assertEquals(t2, tList.get(1));

		tList = resource.toList(resource.search(queryThatReturns3));
		assertEquals(1, tList.size());
		assertEquals(t3, tList.get(0));

		Object t1Id = getIdResource(tList.get(0));
		assertEquals(200, resource.delete(t1Id.toString()));

		Object t2Id = getIdResource(tList.get(0));
		assertEquals(200, resource.delete(t2Id.toString()));

		Object t3Id = getIdResource(tList.get(0));
		assertEquals(200, resource.delete(t3Id.toString()));

		assertEquals(0, resource.get().size());
	}

	protected abstract T editObjectResoucer(T t);

	protected abstract T createObjectResource1();

	protected abstract T createObjectResource2();

	protected abstract T createObjectResource3();

	protected abstract Object getIdResource(T t);

}
