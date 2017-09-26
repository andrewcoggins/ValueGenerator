package brown.valuation;

import java.util.Map;
import java.util.Set;

import brown.valuable.library.Tradeable;

public interface IDependentValuation extends IValuation {
  
  public Map<Set<Tradeable>, Double> getAllValuations();
  
  public Map<Set<Tradeable>, Double> getSomeValuations(Integer numValuations, 
      Integer bundleSizeMean, Double bundleSizeStdDev);
  
}