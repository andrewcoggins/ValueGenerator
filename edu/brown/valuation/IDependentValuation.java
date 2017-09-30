package brown.valuation;

import java.util.Map;
import java.util.Set;

import brown.valuable.library.Tradeable;
import brown.valuable.library.Value;

public interface IDependentValuation extends IValuation {
  
  public Map<Set<Tradeable>, Value> getAllValuations();
  
  public Map<Set<Tradeable>, Value> getSomeValuations(Integer numValuations, 
      Integer bundleSizeMean, Double bundleSizeStdDev);
  
}