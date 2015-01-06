package realrank.user;

import static org.junit.Assert.*;

import java.util.Iterator;
import java.util.List;

import org.junit.Test;

public class UserManagerTest {

	@Test
	public void getNoUser() {
		List result = UserManager.getUsersByKeyword("xxx");
		assertNull(result);
	}
	
	@Test
	public void likeSearchTest() {
		List result = UserManager.getUsersByKeyword("cha");
		Iterator ir = result.iterator();
		while(ir.hasNext()){
			System.out.println(ir.next());
		}
	}

}
