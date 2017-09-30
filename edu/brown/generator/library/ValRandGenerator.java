package brown.generator.library;

import java.util.Set;

import brown.generator.IValuationGenerator;
import brown.valuable.library.Tradeable;
import brown.valuable.library.Value;

/**
 * generates a random value between zero and one.
 * @author andrew
 *
 */
public class ValRandGenerator implements IValuationGenerator {
  
  private Double minVal; 
  private Double maxVal; 
  
  public ValRandGenerator() {
    this.minVal = 0.0; 
    this.maxVal = 1.0;
  }
  
  public ValRandGenerator(double maxVal, double minVal) { 
    this.minVal = minVal; 
    this.maxVal = maxVal; 
  }

  @Override
  public Value makeValuation(Tradeable good) {
    return new Value((Math.random() * this.maxVal) + this.minVal);
  }

  @Override
  public Value makeValuation(Set<Tradeable> goods) {
    Double setMin = this.minVal * goods.size();
    Double setMax = this.maxVal * goods.size();
    return new Value((Math.random() * setMax) + setMin);
  }
  
  
  
}