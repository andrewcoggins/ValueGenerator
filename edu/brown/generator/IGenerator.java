package brown.generator; 

import brown.valuable.IValuable;
import brown.valuation.library.AdditiveValuationSet;
import brown.valuation.library.BundleValuationSet;

public interface IGenerator {
  
  public AdditiveValuationSet getAdditiveValuation(IValuable valuable);
  
  public BundleValuationSet getAllBundleValuations(IValuable valuable);
  
  public BundleValuationSet getSomeBundleValuations(IValuable valuable, 
      Integer numberOfValuations, Integer bundleSizeMean,
      Double bundleSizeStdDev);
  
}