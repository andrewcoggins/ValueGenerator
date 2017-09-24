package brown.valuation.library; 

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import brown.generator.IValuationGenerator;
import brown.generator.library.ValRandGenerator;
import brown.valuable.IValuable;
import brown.valuable.library.Good;
import brown.valuation.IIndependentValuation;

/**
 * A Valuation where the values of each good are independent.
 * @author andrew
 *
 */
public class AdditiveValuation implements IIndependentValuation {

  private Map<Good, Double> valMap;
  
  /**
   * default constructor.
   * @param goods
   */
  public AdditiveValuation(Set<Good> goods) {
    ValRandGenerator rg = new ValRandGenerator();
    for(Good item : goods) {
      valMap.put(item, rg.makeValuation(item));
    }
  }
  
  /**
   * constructor with an input IValuationGenerator and its associated parameters.
   * @param valGenerator
   * @param goods
   */
  public AdditiveValuation(IValuationGenerator valGenerator, Set<Good> goods) {
    this.valMap = new HashMap<Good, Double>();
    for(Good item : goods) {
      double value = valGenerator.makeValuation(item);
      valMap.put(item, value);
    }
  }
  
  @Override
  public Double getValuation(Good good) {
    return valMap.get(good);
  }

  @Override
  public Map<Good, Double> getValuation(Set<Good> goods) {
    Map<Good, Double> valuation = new HashMap<Good, Double>();
    for(IValuable item : goods) {
      valuation.put((Good) item, valMap.get(item));
    }
    return valuation; 
  }
  
}