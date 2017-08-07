package brown.valuable.library; 

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class GoodTest {
  
  private Good testGood = new Good();
  private Good testGoodTwo = new Good(0);
  
  @Test 
  public void testGood() {
    Good i = new Good();
    Good j = new Good(0);
    
    assertEquals(testGood, i);
    assertEquals(testGoodTwo, j);
    assertEquals(testGood.ID, i.ID);
    assertEquals(testGoodTwo.ID, j.ID);
  }
}