package brown.generator.library; 

import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;

import brown.valuable.library.Bundle;
import brown.valuable.library.Good;

public class SizeDependentGeneratorLazyTest {
  
  private Integer GOODS = 4;
  private Integer GOODSTWO = 100; 
  
  public void testSDP() {
    Function<Integer, Double> testFunction = x -> (double) x * x + 1;
    SizeDependentGenerator sdp = new SizeDependentGenerator(testFunction, 2.0);
    
    Set<Good> goodSet = new HashSet<Good>();
    for(int i = 0; i < GOODS; i++)
      goodSet.add(new Good(i));
    Bundle goodBundle = new Bundle(goodSet);
    System.out.println(sdp.getAllBundleValuations(goodBundle));
    for(int i = 1; i < GOODS + 1; i++) {
      for(int j = 1; j < GOODS; j++) {
        System.out.println(sdp.getSomeBundleValuations(goodBundle, i,
            j, 0.1));
      }
    }
      

  }
  
  public void testSDPTwo() {
    Set<Good> goodSet = new HashSet<>();
    for(int i = 0; i < GOODSTWO; i++)
      goodSet.add(new Good(i));
    Bundle goodBundle = new Bundle(goodSet);
    Function<Integer, Double> testFunction = x -> (double) 2 * x;
    SizeDependentGenerator sdp = new SizeDependentGenerator(testFunction, 1.0);
    System.out.println(sdp.getSomeBundleValuations(goodBundle, 90, 30, 7.0));

  }
  
  public static void main(String[] args) {
    
    SizeDependentGeneratorLazyTest lt = new SizeDependentGeneratorLazyTest();
    lt.testSDP();
    lt.testSDPTwo();
  }
}