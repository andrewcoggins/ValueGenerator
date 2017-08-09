package brown.valuation.library; 

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import brown.valuable.library.Bundle;
import brown.valuable.library.Good;
import brown.valuation.IValuation;


public class BundleValuationSetTest {
  
  private Integer GOODS = 5;
  private Integer BUNDLES = 10; 
  
  @Test
  public void tesBundleValuationSet() {
    
    BundleValuationSet setOne = new BundleValuationSet();
    Map<Bundle, Double> bundleMap = new HashMap<Bundle, Double>();
    for(int i = 0; i < BUNDLES; i++) {
      Bundle b = new Bundle();
      for(int j = 0; j < GOODS; j++)
        b.bundle.add(new Good(i + j));
      bundleMap.put(b, 5.0);
    }
    BundleValuationSet setTwo = new BundleValuationSet(bundleMap);
    BundleValuationSet setThree = new BundleValuationSet(setTwo);
    
    assertEquals(setOne, new BundleValuationSet());
    assertEquals(setTwo, setThree);
    for(int i = 0; i < BUNDLES; i++) {
      Bundle goods = new Bundle();
      for(int j = 0; j < GOODS; j++) {
        goods.bundle.add(new Good(i + j));
      }
      assertTrue(setTwo.contains(goods));
      BundleValuation b = new BundleValuation(goods, 5.0);
      assertEquals(setThree.getValuation(goods), b);
      assertTrue(setThree.getOrDefault(goods, 0.0) == 5.0);
      goods.bundle.add(new Good(22));
      assertTrue(setThree.getOrDefault(goods, 0.0) == 0);
    }
    
    assertTrue(setOne.isEmpty());
    assertTrue(!setTwo.isEmpty());
    Bundle newBundle = new Bundle();
    for(int i = 0; i < GOODS; i++)
      newBundle.bundle.add(new Good(i));
    setTwo.remove(newBundle);
    assertTrue(setTwo.size() == 9);
    assertTrue(setThree.size() == 10);
    setThree.remove(newBundle);
    assertTrue(setThree.size() == 9);
    
    BundleValuationSet newSet = new BundleValuationSet();
    for(IValuation v : setTwo)
      newSet.add(v);
    
    assertEquals(newSet, setTwo);
    setOne.addAll(setTwo);
    assertEquals(setOne, setTwo);
    setOne.clear();
    assertEquals(setOne, new BundleValuationSet());
    
    
  }
}