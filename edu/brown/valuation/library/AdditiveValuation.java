package brown.valuation.library; 

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import brown.generator.IValuationGenerator;
import brown.generator.library.ValRandGenerator;
import brown.valuable.IValuable;
import brown.valuable.library.Tradeable;
import brown.valuable.library.Value;
import brown.valuation.IIndependentValuation;

/**
 * A Valuation where the values of each good are independent.
 * @author andrew
 *
 */
public class AdditiveValuation implements IIndependentValuation {

  private Map<Tradeable, Value> valMap;
  
  /**
   * default constructor.
   * @param goods
   */
  public AdditiveValuation(Set<Tradeable> goods) {
    ValRandGenerator rg = new ValRandGenerator();
    for(Tradeable item : goods) {
      valMap.put(item, rg.makeValuation(item));
    }
  }
  
  /**
   * constructor with an input IValuationGenerator and its associated parameters.
   * @param valGenerator
   * @param goods
   */
  public AdditiveValuation(IValuationGenerator valGenerator, Set<Tradeable> goods) {
    this.valMap = new HashMap<Tradeable, Value>();
    for(Tradeable item : goods) {
      Value value = valGenerator.makeValuation(item);
      valMap.put(item, value);
    }
  }
  
  @Override
  public Value getValuation(Tradeable good) {
    return valMap.get(good);
  }

  @Override
  public Map<Tradeable, Value> getValuation(Set<Tradeable> goods) {
    Map<Tradeable, Value> valuation = new HashMap<Tradeable, Value>();
    for(IValuable item : goods) {
      valuation.put((Tradeable) item, valMap.get(item));
    }
    return valuation; 
  }
  
}