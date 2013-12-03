package br.ufpb.dce.bloomer.core.test;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

public abstract class TypedResourceTest<T>{
	
	private TypedResource<T> resource ;

	@Before
	public void init() {
		resource = createTypedResource();
	}

	protected abstract TypedResource<T> createTypedResource();
		

	@Test
	public void testingRestMethods() {
		assertEquals(0, resource.get().size());

		T t  = createObject();
		
		assertEquals(201, resource.post(t));

		List<T> ts = resource.get();

		Object ID = getId(ts.get(0));
		T newT = resource.get(ID.toString());
		assertEquals(t, newT);

		T editedT = editObject(newT);
		assertEquals(200, resource.put(editedT));

		List<T> ts2 = resource.get();
		T editedT2 = resource.get(ID.toString());
		assertEquals(editedT, editedT2);

		assertEquals(200, resource.delete(ID.toString()));
	}

	protected abstract T editObject (T t);
	
	protected abstract T createObject();
	
	protected abstract Object getId (T t);
	
}
