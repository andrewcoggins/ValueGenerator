package brown.valuable.library; 

import static org.junit.Assert.assertEquals;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

/**
 * character-building exercise mostly.
 * @author acoggins
 *
 */
public class BundleTest {

  @Test
  public void testBundle() {
    Bundle testBundle = new Bundle();
    Bundle testBundleA = new Bundle();
    Set<Tradeable> goodSet = new HashSet<Tradeable>();
    for(int i = 0; i < 10; i++) {
      goodSet.add(new Tradeable(i));
    }
    Bundle testBundleTwo = new Bundle(goodSet);
    Bundle testBundleTwoA = new Bundle(goodSet);
    
    assertEquals(testBundle, testBundleA);
    assertEquals(testBundleTwo, testBundleTwoA);
    assertEquals(testBundle.bundle, new HashSet<Tradeable>());
    assertEquals(testBundleTwo.bundle, goodSet);
  }
}