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

  @Override
  public String toString() {
    return "AdditiveValuation [entry=" + entry + "]";
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((entry == null) ? 0 : entry.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    AdditiveValuation other = (AdditiveValuation) obj;
    if (entry == null) {
      if (other.entry != null)
        return false;
    } else if (!entry.equals(other.entry))
      return false;
    return true;
  }
  
  

}