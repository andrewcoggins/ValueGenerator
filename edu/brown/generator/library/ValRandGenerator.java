package brown.generator.library;

import java.util.Set;

import brown.generator.IValuationGenerator;
import brown.valuable.library.Good;

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
  public double makeValuation(Good good) {
    return (Math.random() * this.maxVal) + this.minVal;
  }

  @Override
  public double makeValuation(Set<Good> goods) {
    Double setMin = this.minVal * goods.size();
    Double setMax = this.maxVal * goods.size();
    return (Math.random() * setMax) + setMin;
  }
  
  
  
}