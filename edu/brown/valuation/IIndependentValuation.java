package brown.valuation;

import java.util.Map;
import java.util.Set;

import brown.valuable.library.Tradeable;

public interface IIndependentValuation extends IValuation{
  
  public Double getValuation(Tradeable good);
  
  public Map<Tradeable, Double> getValuation(Set<Tradeable> goods);
  
}