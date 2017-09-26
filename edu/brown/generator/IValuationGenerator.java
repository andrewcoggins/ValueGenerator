package brown.generator; 

import java.util.Set;

import brown.valuable.library.Tradeable;

public interface IValuationGenerator {
  
  public double makeValuation(Tradeable good);
  
  public double makeValuation(Set<Tradeable> goods);
  
  
}