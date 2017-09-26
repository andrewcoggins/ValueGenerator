package brown.valuable.library; 

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class GoodTest {
  
  private Tradeable testGood = new Tradeable();
  private Tradeable testGoodTwo = new Tradeable(0);
  
  @Test 
  public void testGood() {
    Tradeable i = new Tradeable();
    Tradeable j = new Tradeable(0);
    
    assertEquals(testGood, i);
    assertEquals(testGoodTwo, j);
    assertEquals(testGood.ID, i.ID);
    assertEquals(testGoodTwo.ID, j.ID);
  }
}