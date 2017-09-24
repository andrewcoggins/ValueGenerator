package brown.valuation;

import java.util.Map;
import java.util.Set;

import brown.valuable.library.Good;

public interface IIndependentValuation extends IValuation{
  
  public Double getValuation(Good good);
  
  public Map<Good, Double> getValuation(Set<Good> goods);
  
}