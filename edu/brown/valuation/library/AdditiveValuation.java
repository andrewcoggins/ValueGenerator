package brown.valuation.library; 

import java.util.AbstractMap.SimpleEntry;

import brown.valuable.IValuable;
import brown.valuable.library.Good;
import brown.valuation.IValuation;

public class AdditiveValuation implements IValuation {

  private SimpleEntry<Good, Double> entry; 
  
  public AdditiveValuation(Good aGood, Double value) {
    this.entry = new SimpleEntry<Good, Double>(aGood, value);
  }
  
  @Override
  public IValuable getValuable() {
    return (Good) this.entry.getKey();
  }

  @Override
  public Double getPrice() {
    return this.entry.getValue();
  }

  @Override
  public void setPrice(Double newPrice) {
    entry.setValue(newPrice);
  }

}