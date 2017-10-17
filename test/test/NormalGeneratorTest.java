package test; 

import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;

import brown.generator.library.NormalGenerator;
import brown.valuable.library.Tradeable;
import brown.valuation.IValuation;
import brown.valuation.library.BundleValuation;
import test.valuable.library.Bundle;
import test.valuation.library.BundleValuationSet;

public class NormalGeneratorTest {
  
  private Integer ITERATIONS = 1000;
  private Integer GOODS = 3;  
  private Integer GOODSTWO = 3; 
  private Integer GOODSTHREE = 5; 
  
  /**
   * basic test of the functionality of the normal generator.
   */
  public void testNormalGenerator() {
    
    Function<Integer, Double> basicFunction = x -> (double) x; 
    
    NormalGenerator ng = new NormalGenerator(basicFunction, false, 1.0);
    
    Tradeable aGood = new Tradeable(0);
    for(int i = 0; i < ITERATIONS; i++)
    System.out.println(ng.getSingleValuation(aGood));
    
    Set<Tradeable> goodSet = new HashSet<Tradeable>();
    for(int i = 0; i < GOODS; i++)
      goodSet.add(new Tradeable(i));
    Bundle goodBundle = new Bundle(goodSet);
    System.out.print('\n');
    System.out.println(ng.getAdditiveValuation(goodBundle));
    System.out.print('\n');
    System.out.println(ng.getAllBundleValuations(goodBundle));
    for(int i = 0; i < 100; i++)
      System.out.println(ng.getSomeBundleValuations(goodBundle, 4, 2, 2.0));
  }
  
  /**
   * tests for monotonicity in the get all valuations case. 
   */
  public void testNormalGeneratorTwo() {
    Function<Integer, Double> basicFunction = x -> (double) x; 
    NormalGenerator ng = new NormalGenerator(basicFunction, 2.0, 0.0, true, 1.0);
    
    Set<Tradeable> goodSet = new HashSet<Tradeable>();
    for(int i = 0; i < GOODSTWO; i++)
      goodSet.add(new Tradeable(i));
    Bundle goodBundle = new Bundle(goodSet);
    for(int i = 0; i < 15; i++) {
      BundleValuationSet b = ng.getAllBundleValuations(goodBundle);
      for(IValuation aBundle : b) {
        for(IValuation aBundleTwo : b) {
          if(((Bundle)aBundle.getValuable()).bundle
              .containsAll(((Bundle)aBundleTwo.getValuable()).bundle)) {
            if (((BundleValuation) aBundle).getPrice() >= ((BundleValuation)aBundleTwo).getPrice())
              System.out.println("Good");
            else {
              System.out.println("Bad");
            }
          }
        }
      }
    }
  }
  
  /**
   * test for monotonicity in the get some valuations case. 
   */
  public void testNormalGeneratorThree() {
    Function<Integer, Double> basicFunction = x -> (double) x; 
    NormalGenerator ng = new NormalGenerator(basicFunction, 2.0, 0.0, true, 1.0);
    Set<Tradeable> goodSet = new HashSet<Tradeable>();
    for(int i = 0; i < GOODSTHREE; i++)
      goodSet.add(new Tradeable(i));
    Bundle goodBundle = new Bundle(goodSet);
    for(int i = 0; i < 40; i++) {
      BundleValuationSet b = ng.getSomeBundleValuations(goodBundle, 7, 3, 1.0);
      Boolean flag = true;
      for(IValuation aBundle : b) {
        for(IValuation aBundleTwo : b) {
          //if every superset's value is greater than or equal to all its subsets, we're good.
          //otherwise, bad.
          if(((Bundle)aBundle.getValuable()).bundle
              .containsAll(((Bundle)aBundleTwo.getValuable()).bundle)) {
            if (!(((BundleValuation) aBundle).getPrice() >= ((BundleValuation)aBundleTwo).getPrice()))
              flag = false; 
          }
        }
      }
      String answer = flag ? "Good" : "Bad";
      System.out.println(answer);
      System.out.println(b);
    }
  }
  
  public static void main(String[] args) {
    NormalGeneratorTest genTest = new NormalGeneratorTest();
    //genTest.testNormalGenerator();
    //genTest.testNormalGeneratorTwo();
    genTest.testNormalGeneratorThree();
  }
}