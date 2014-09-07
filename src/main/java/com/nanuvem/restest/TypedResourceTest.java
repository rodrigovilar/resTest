package com.nanuvem.restest;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

public abstract class TypedResourceTest<T,S> {

	private TypedResource<T> resource;
	private String atributo_consulta;
	private TypedSubResource<S> subResource;

	@Before
	public void init() {
		resource = createTypedResource();
	}

	protected abstract TypedResource<T> createTypedResource();
	
	protected abstract TypedSubResource<S> createTypedSubResource();

	@Test
	public void testingRestMethods() {
		assertEquals(0, resource.get().size());

		T t = createObjectResource();

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

	}

	@Test
	public void testingRestSearch() throws Exception {
		assertEquals(0, resource.get().size());

		T t1 = this.createObjectResource();
		assertEquals(201, resource.post(t1));

		T t2 = this.createObjectResource();
		assertEquals(201, resource.post(t2));

		T t3 = this.createObjectResource();
		assertEquals(201, resource.post(t3));

		List<T> tList = resource.get();

		assertEquals(3, tList.size());

		List<T> tListResultado = resource.toList(resource
				.getConsulta(atributo_consulta));

		// como fazer esse assert da consulta?????????

		Object t1Id = getIdResource(tListResultado.get(0));
		assertEquals(200, resource.delete(t1Id.toString()));

		Object t2Id = getIdResource(tListResultado.get(0));
		assertEquals(200, resource.delete(t2Id.toString()));

		Object t3Id = getIdResource(tListResultado.get(0));
		assertEquals(200, resource.delete(t3Id.toString()));

		assertEquals(0, resource.get().size());
	}
	
	@Test
	public void testingRestSubResource() throws Exception {
		
		assertEquals(0, resource.get().size());
		
		T t = createObjectResource();
		assertEquals(201, resource.post(t));
		List<T> ts = resource.get();

		String ID = (String) getIdResource(ts.get(0));
		
		assertEquals(0, subResource.get(ID).size()); 
		
		S s = createObjectSubResource();
		assertEquals(201, subResource.post(ID, s));			
		List<S> sList = subResource.get(ID);
		String IDSubResource1 =  (String) getIdSubResource(sList.get(0));
		
		S sPesquisado = subResource.get(ID, IDSubResource1);
		
		assertEquals(s, sPesquisado);
		
		S sEditado = editObjectSubResource(sPesquisado);
		
		assertEquals(200, subResource.put(ID, IDSubResource1, sEditado));
		String IDSubResource2 =  (String) getIdSubResource(sList.get(0));
		
		S sPesquisado2 = subResource.get(ID, IDSubResource2);
		String IDSubResource3 =  (String) getIdSubResource(sList.get(0));
		
		assertEquals( sPesquisado, sPesquisado2 );
		
		assertEquals(200,subResource.delete(ID, IDSubResource3));
		assertEquals(0, subResource.get(ID).size()); 
		
		assertEquals(200, resource.delete(ID.toString()));
		assertEquals(0, resource.get().size());
		
		
	}
	
	
	protected abstract T editObjectResoucer(T t);

	protected abstract T createObjectResource();

	protected abstract Object getIdResource(T t);
	
	
	protected abstract Object getIdSubResource(S s);
	
	protected abstract S createObjectSubResource();
	
	protected abstract S editObjectSubResource(S s);

}
