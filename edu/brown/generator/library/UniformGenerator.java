package brown.generator.library; 

import java.util.Set;

import brown.generator.IValuationGenerator;
import brown.valuable.library.Tradeable;
import brown.valuable.library.Value;

public class UniformGenerator implements IValuationGenerator {

  @Override
  public Value makeValuation(Tradeable good) {
    // TODO Auto-generated method stub
    return new Value(0);
  }

  @Override
  public Value makeValuation(Set<Tradeable> goods) {
    // TODO Auto-generated method stub
    return new Value(0);
  }


  
}