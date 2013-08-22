package com.amazonia.utils.functional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.amazonia.utils.functional.interfaces.IFilter;
import com.amazonia.utils.functional.interfaces.IMapper;
import com.amazonia.utils.functional.interfaces.IReducer;

/**
 * Unit test for class JCollections
 * 
 * @author pengfeil
 * 
 */
public class JCollectionsTest {
	public class Type implements IFilter<Type>, IMapper<Type>, IReducer<Type> {
		public int number;

		public Type(int number) {
			this.number = number;
		}

		public Type() {
			this.number = -1;
		}

		@Override
		public boolean isFiltered(Type o) {
			return o.number > 10;
		}

		@Override
		public Type map(Type o) {
			return new Type(o.number * 2);
		}

		@Override
		public Type reduce(Type o1, Type o2) {
			return new Type(o1.number + o2.number);
		}

		@Override
		public boolean equals(Object o) {
			if (o instanceof Type) {
				return ((Type) o).number == this.number;
			} else {
				return false;
			}
		}

		@Override
		public int hashCode() {
			return this.number;
		}

		@Override
		public String toString() {
			return "" + this.number;
		}

	}

	private List<Type> list;
	private Set<Type> set;
	private JCollections<Type> jc;

	@Before
	public void setup() {
		list = new ArrayList<Type>();
		set = new HashSet<Type>();
		for (int i = 1; i < 100; i++) {
			list.add(new Type(i));
			set.add(new Type(-i));
		}
		jc = new JCollections<Type>();
	}

	private void validateFilter(Collection<Type> col, IFilter<Type> filter) {
		for (Type t : col) {
			Assert.assertFalse(filter.isFiltered(t));
		}
	}

	@Test
	public void testDoFilter() {
		System.out.println("JCollectionsTest:testDoFilter");
		IFilter<Type> filter = new Type();
		jc.doFilter(list, filter);
		validateFilter(list, filter);
		jc.doFilter(set, filter);
		validateFilter(set, filter);
		list.clear();
		set.clear();
		jc.doFilter(list, filter);
		validateFilter(list, filter);
		jc.doFilter(set, new Type());
		validateFilter(set, filter);
	}

	private void validateMap(Collection<Type> from, Collection<Type> to,
			IMapper<Type> mapper) {
		for (Type t : from) {
			Type m = mapper.map(t);
			Assert.assertTrue(to.contains(m));
			to.remove(m);
		}
	}

	@Test
	public void testDoMap() throws InstantiationException,
			IllegalAccessException {
		System.out.println("JCollectionsTest:testDoMap");
		IMapper<Type> mapper = new Type();
		Collection<Type> c1 = jc.doMap(list, mapper);
		validateMap(list, c1, mapper);
		Collection<Type> c2 = jc.doMap(set, mapper);
		validateMap(set, c2, mapper);
		list.clear();
		set.clear();
		Collection<Type> c3 = jc.doMap(list, mapper);
		validateMap(list, c3, mapper);
		Collection<Type> c4 = jc.doMap(set, mapper);
		validateMap(set, c4, mapper);
	}

	@Test
	public void testDoReduce() {
		System.out.println("JCollectionsTest:testDoReduce");
		Type result = jc.doReducer(set, new Type());
		Type expected = new Type(0);
		for (Type t : set) {
			expected.number += t.number;
		}
		Assert.assertEquals(expected.number, result.number);
	}

	@Test
	public void testDoUnion() throws InstantiationException,
			IllegalAccessException {
		System.out.println("JCollectionsTest:testDoUnion");
		Collection<Type> result = jc.doUnion(set, list);
		validateUnion(result, list, set);
	}

	private void validateUnion(Collection<Type> result, Collection<Type> c1,
			Collection<Type> c2) {
		Assert.assertEquals(c1.size() + c2.size(), result.size());
		for (Type t : c1) {
			Assert.assertTrue(result.contains(t));
			result.remove(t);
		}
		for (Type t : c2) {
			Assert.assertTrue(result.contains(t));
			result.remove(t);
		}
		Assert.assertEquals(result.size(), 0);
	}

	@Test
	public void testMax() {
		System.out.println("JCollectionsTest:testMax");
		Type max = jc.max(set, new Comparator<Type>() {
			@Override
			public int compare(Type t1, Type t2) {
				if (t1.number > t2.number)
					return 1;
				else if (t1.number < t2.number)
					return -1;
				else
					return 0;
			}
		});
		// validate
		for (Type t : set) {
			Assert.assertTrue(t.number <= max.number);
		}
	}
}
