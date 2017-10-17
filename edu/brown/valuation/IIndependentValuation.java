package brown.valuation;

import brown.valuable.library.Tradeable;
import brown.valuable.library.Value;

public interface IIndependentValuation extends IValuation {
  
  public Value getValuation(Tradeable good);
    
}