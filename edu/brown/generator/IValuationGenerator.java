package brown.generator; 

import java.util.Set;

import brown.valuable.library.Tradeable;
import brown.valuable.library.Value;

public interface IValuationGenerator {
  
  public Value makeValuation(Tradeable good);
  
  public Value makeValuation(Set<Tradeable> goods);
  
  
}