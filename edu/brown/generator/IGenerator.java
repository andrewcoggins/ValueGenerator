package brown.generator; 

import brown.valuable.library.Bundle;
import brown.valuable.library.Good;
import brown.valuation.library.AdditiveValuation;
import brown.valuation.library.AdditiveValuationSet;
import brown.valuation.library.BundleValuationSet;

public interface IGenerator {
  
  public AdditiveValuation getSingleValuation(Good aGood);
  
  public AdditiveValuationSet getAdditiveValuation(Bundle valuable);
  
  public BundleValuationSet getAllBundleValuations(Bundle valuable);
  
  public BundleValuationSet getSomeBundleValuations(Bundle valuable, 
      Integer numberOfValuations, Integer bundleSizeMean,
      Double bundleSizeStdDev);
  
}