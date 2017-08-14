package brown.generator.library; 

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;

import org.junit.Test;

import brown.valuable.library.Bundle;
import brown.valuable.library.Good;
import brown.valuation.library.AdditiveValuation;
import brown.valuation.library.AdditiveValuationSet;
import brown.valuation.library.BundleValuation;
import brown.valuation.library.BundleValuationSet;

public class SizeDependentGeneratorTest {
  
  @Test
  public void testOne() {
    Double valueScale = 1.0; 
    Function<Integer, Double> basic = x -> 0.0;
    SizeDependentGenerator sdp = new SizeDependentGenerator(basic, valueScale);
    AdditiveValuation generated = sdp.getSingleValuation(new Good(0));
    AdditiveValuation comparison = new AdditiveValuation(new Good(0), 0.0);
    
    assertEquals(generated, comparison);
    assertTrue(!generated.equals(new AdditiveValuation(new Good(0), 1.0)));
    assertTrue(!generated.equals(new AdditiveValuation(new Good(1), 0.0)));
    
    Bundle aBundle = new Bundle();
    for(int i = 0; i < 5; i++)
      aBundle.bundle.add(new Good(i));
    
    AdditiveValuationSet generatedSet = sdp.getAdditiveValuation(aBundle);
    AdditiveValuationSet comparisonSet = new AdditiveValuationSet();
    for(int i = 0; i < 5; i++)
      comparisonSet.add(new AdditiveValuation(new Good(i), 0.0));
    assertEquals(generatedSet, comparisonSet);
    comparisonSet.add(new AdditiveValuation(new Good(4), 0.0));
    assertEquals(generatedSet, comparisonSet);
    comparisonSet.add(new AdditiveValuation(new Good(6), 0.0));
    assertTrue(!generatedSet.equals(comparisonSet));
    
    BundleValuationSet comparisonbSet = new BundleValuationSet();
    Bundle b1 = new Bundle();
    Bundle b2 = new Bundle();
    Bundle b3 = new Bundle();
    Bundle b4 = new Bundle();
    Bundle b5 = new Bundle();
    Bundle b6 = new Bundle();
    Bundle b7 = new Bundle();
    Bundle b8 = new Bundle();
    b2.bundle.add(new Good(0));
    b3.bundle.add(new Good(1));
    b4.bundle.add(new Good(2));
    b5.bundle.add(new Good(1));
    b5.bundle.add(new Good(2));
    b6.bundle.add(new Good(1));
    b6.bundle.add(new Good(3));
    b7.bundle.add(new Good(2));
    b7.bundle.add(new Good(3));
    b8.bundle.add(new Good(1));
    b8.bundle.add(new Good(2));
    b8.bundle.add(new Good(3));
    comparisonbSet.add(new BundleValuation(b1, 0.0));
    comparisonbSet.add(new BundleValuation(b2, 0.0));
    comparisonbSet.add(new BundleValuation(b3, 0.0));
    comparisonbSet.add(new BundleValuation(b4, 0.0));
    comparisonbSet.add(new BundleValuation(b5, 0.0));
    comparisonbSet.add(new BundleValuation(b6, 0.0));
    comparisonbSet.add(new BundleValuation(b7, 0.0));
    comparisonbSet.add(new BundleValuation(b8, 0.0));
    
    Set<Good> goodSet = new HashSet<Good>();
    goodSet.add(new Good(1));
    goodSet.add(new Good(2));
    goodSet.add(new Good(3));
    
    //does not pass jUnit but is correct. 
    assertEquals(sdp.getAllBundleValuations(new Bundle(goodSet)), comparisonbSet);
    
    goodSet.add(new Good(4));
    goodSet.add(new Good(5));
    
    System.out.println(sdp.getAllBundleValuations(new Bundle(goodSet)));
  }
}