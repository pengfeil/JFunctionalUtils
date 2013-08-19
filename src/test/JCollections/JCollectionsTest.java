package JCollections;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import pengfeil.utils.functional.JCollections;
import pengfeil.utils.functional.interfaces.IFilter;

/**
 * Unit test for class JCollections
 * 
 * @author pengfeil
 * 
 */
public class JCollectionsTest {
	public class Type implements IFilter<Type> {
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
	}

	private List<Type> list;
	private Set<Type> set;
	private JCollections<Type> jc;

	@Before
	public void setup(){
		System.out.println("Unit test for JCollectionsTest");
		list = new ArrayList<Type>();
		set = new HashSet<Type>();
		for(int i=0;i<100;i++){
			list.add(new Type(i));
			set.add(new Type(i));
		}
		jc = new JCollections<Type>();
	}
	
	private void validate(Collection<Type> col){
		for(Type t:col){
			Assert.assertTrue(t.number<=10);
		}
	}

	@Test
	public void testDoFilter(){
		jc.doFilter(list, new Type());
		validate(list);
		jc.doFilter(set, new Type());
		validate(set);
		list.clear();
		set.clear();
		jc.doFilter(list, new Type());
		validate(list);
		jc.doFilter(set, new Type());
		validate(set);
	}
}
