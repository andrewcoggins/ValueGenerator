package brown.valuation.library; 

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import brown.valuable.library.Bundle;
import brown.valuable.library.Good;

public class BundleValuationTest {
  
  private Integer GOODS = 10; 
  
  @Test 
  public void testBundleValuation() {
    
    Set<Good> goodSet = new HashSet<Good>();
    for(int i = 0; i < GOODS; i++) 
      goodSet.add(new Good(i));
    
    Bundle bundleOne = new Bundle();
    Bundle bundleTwo = new Bundle(goodSet);
    
    BundleValuation bv = new BundleValuation(bundleOne, 0.0);
    BundleValuation bvTwo = new BundleValuation(bundleTwo, 10.0);
    
    assertEquals(bv.getValuable(), new Bundle());
    assertTrue(bv.getPrice() == 0.0);
    bv.setPrice(1.0);
    assertTrue(bv.getPrice() == 1.0);
    assertTrue(bv.size() == 0);
    
    assertEquals(bvTwo.getValuable(), bundleTwo);
    assertTrue(bvTwo.getPrice() == 10.0);
    for(int i = 0; i < GOODS; i++)
      assertTrue(bvTwo.contains(new Good(i)));
    assertTrue(!bvTwo.contains(new Good(11)));
    assertTrue(bvTwo.size() == 10);
    Bundle valBundle = (Bundle) bvTwo.getValuable(); 
    
    assertEquals(valBundle.bundle, goodSet);
  }
}