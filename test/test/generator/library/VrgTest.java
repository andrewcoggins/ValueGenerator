package test.generator.library;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import brown.generator.library.ValRandGenerator;
import brown.valuable.library.Tradeable;

public class VrgTest {
  
  @Test
  public void testVrg() {
    int NUMTRIALS = 100;
    ValRandGenerator vrg = new ValRandGenerator();
    Tradeable good = new Tradeable(0);
    
    for(int i = 0; i < NUMTRIALS; i++) {
    assertTrue(vrg.makeValuation(good).value >= 0.0
        && vrg.makeValuation(good).value <= 1.0);
    }
  }
}