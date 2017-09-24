package brown.generator; 

import java.util.Set;

import brown.valuable.library.Good;

public interface IValuationGenerator {
  
  public double makeValuation(Good good);
  
  public double makeValuation(Set<Good> goods);
  
  
}