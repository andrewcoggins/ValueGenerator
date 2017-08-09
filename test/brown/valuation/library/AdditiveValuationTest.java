package brown.valuation.library; 

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import brown.valuable.library.Good;

public class AdditiveValuationTest {
  
  
  @Test
  public void testAdditiveValuation() {
    
    Good goodOne = new Good(0);
    AdditiveValuation aValuation = new AdditiveValuation(goodOne, 1.5);
    
    
    assertEquals(aValuation.getValuable(), goodOne);
    assertTrue(aValuation.getPrice() == 1.5);
    
    aValuation.setPrice(0.0);
    
    assertTrue(aValuation.getPrice() == 0.0);
    
    AdditiveValuation aValuationTwo = new AdditiveValuation(goodOne, 0.0);
    
    assertEquals(aValuation, aValuationTwo);
  }
  
}