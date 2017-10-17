package test.generator.library;

import static org.junit.Assert.assertEquals;

import java.util.function.Function;

import org.junit.Test;

import brown.generator.library.SizeDependentGenerator;
import brown.valuable.library.Tradeable;
import brown.valuable.library.Value;

public class SdgTest {
  
  @Test
  public void testSdg() {
    
    Function<Integer, Double> aFunction = x -> (double) x;
    SizeDependentGenerator test = new SizeDependentGenerator(aFunction, 1.0);
    Tradeable good = new Tradeable();
    assertEquals(new Value(1.0), test.makeValuation(good));
  }
}