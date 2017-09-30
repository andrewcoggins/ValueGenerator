package brown.valuation;

import java.util.Map;
import java.util.Set;

import brown.valuable.library.Tradeable;
import brown.valuable.library.Value;

public interface IIndependentValuation extends IValuation{
  
  public Value getValuation(Tradeable good);
  
  public Map<Tradeable, Value> getValuation(Set<Tradeable> goods);
  
}