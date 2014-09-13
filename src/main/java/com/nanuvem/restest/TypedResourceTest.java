package com.nanuvem.restest;

import static org.junit.Assert.assertEquals;

import java.util.List;

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
	public void testingRestMethods() {
		assertEquals(0, resource.get().size());

		T t = createObjectResource1();

		assertEquals(201, resource.post(t));

		List<T> ts = resource.get();

		Object ID = getIdResource(ts.get(0));
		
		T newT = resource.get(ID.toString());
		assertEquals(t, newT);

		T editedT = editObjectResoucer(newT);
		assertEquals(200, resource.put(ID.toString(), editedT));

		T editedT2 = resource.get(ID.toString());
		assertEquals(editedT, editedT2);

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
		assertEquals(201, resource.post(t1));

		T t2 = this.createObjectResource2();
		assertEquals(201, resource.post(t2));

		T t3 = this.createObjectResource3();
		assertEquals(201, resource.post(t3));

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
