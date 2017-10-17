package brown.valuation;


import brown.valuationrepresentation.AValuationRepresentation;

public interface IDependentValuation extends IValuation {
  
  public AValuationRepresentation getSomeValuations(Integer numValuations, 
      Integer bundleSizeMean, Double bundleSizeStdDev);
  
}