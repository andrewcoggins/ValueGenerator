package brown.valuation.library;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import brown.valuable.library.Good;
import brown.valuation.IValuation;

public class AdditiveValuationSetTest {
  
  private Integer GOODS = 10; 
  
  @Test
  public void testAdditiveValuationSet() {
    
    Map<Good, Double> creationMap = new HashMap<Good, Double>();
    for(int i = 0; i < GOODS; i++)
      creationMap.put(new Good(i), 1.0);
    
    AdditiveValuationSet setOne = new AdditiveValuationSet();
    AdditiveValuationSet setTwo = new AdditiveValuationSet(creationMap);
    AdditiveValuationSet setThree = new AdditiveValuationSet(setTwo);
    
    assertEquals(setOne, new AdditiveValuationSet());
    assertEquals(setTwo, setThree);
    for(int i = 0; i < GOODS; i++) {
      Good aGood = new Good(i);
      assertTrue(setTwo.contains(aGood));
      AdditiveValuation a = new AdditiveValuation(aGood, 1.0);
      assertEquals(setThree.getValuation(aGood), a);
      assertTrue(setThree.getOrDefault(aGood, 0.0) == 1.0);
      assertTrue(setOne.getOrDefault(aGood, 0.0) == 0.0);
    }
    
    assertTrue(setOne.isEmpty());
    assertTrue(!setTwo.isEmpty());
    
    setTwo.remove(new Good(0));
    assertTrue(setTwo.size() == 9);
    assertTrue(setThree.size() == 10);
    setThree.remove(new AdditiveValuation(new Good(0), 1.0));
    assertTrue(setThree.size() == 9);
    
    AdditiveValuationSet newSet = new AdditiveValuationSet();
    for(IValuation v : setTwo)
      newSet.add(v);
    
    assertEquals(newSet, setTwo);
    setOne.addAll(setTwo);
    assertEquals(setOne, setTwo);
    setOne.clear();
    assertEquals(setOne, new AdditiveValuationSet());
  }
}