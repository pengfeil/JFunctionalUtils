package com.amazonia.utils.functional;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.amazonia.utils.functional.JLists;


/**
 * Unit test for class JLists
 * 
 * @author pengfeil
 * 
 */
public class JListsTest {
	public class Type{
		public int number;

		public Type(int number) {
			this.number = number;
		}

		public Type() {
			this.number = -1;
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
	private JLists<Type> jl;

	@Before
	public void setup() {
		list = new ArrayList<Type>();
		for (int i = 0; i < 100; i++) {
			list.add(new Type(i));
		}
		jl = new JLists<Type>();
	}
	
	@Test
	public void testAt()
	{
		System.out.println("JListsTest:testAt");
		Assert.assertEquals(list.get(0).number, jl.at(list, 0).number);
		Assert.assertEquals(list.get(10).number, jl.at(list, 10).number);
		Assert.assertEquals(list.get(list.size()-1).number, jl.at(list, -1).number);
		Assert.assertEquals(list.get(list.size()-10).number, jl.at(list, -10).number);
	}
}
