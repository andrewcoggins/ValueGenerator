package brown.valuation;

import java.util.Map;
import java.util.Set;

import brown.valuable.library.Good;

public interface IDependentValuation extends IValuation {
  
  public Map<Set<Good>, Double> getAllValuations();
  
  public Map<Set<Good>, Double> getSomeValuations(Integer numValuations, 
      Integer bundleSizeMean, Double bundleSizeStdDev);
  
}