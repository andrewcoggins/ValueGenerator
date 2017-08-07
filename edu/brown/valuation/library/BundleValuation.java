package brown.valuation.library; 

import java.util.AbstractMap.SimpleEntry;
import java.util.Map.Entry;
import java.util.Set;

import brown.valuable.IValuable;
import brown.valuable.library.Bundle;
import brown.valuable.library.Good;
import brown.valuation.IValuation;

public class BundleValuation implements IValuation {

  private SimpleEntry<Bundle, Double> entry; 
  
  public BundleValuation(Bundle aBundle, Double price) {
    this.entry = new SimpleEntry<Bundle, Double>(aBundle, price);
  }
  
  @Override
  public IValuable getValuable() {
    return (Bundle) this.entry.getKey();
  }

  @Override
  public Double getPrice() {
    return this.entry.getValue();
  }

  @Override
  public void setPrice(Double newPrice) {
   this.entry.setValue(newPrice);
    
  }

  public Boolean contains(IValuable val) {
    Bundle b = (Bundle) this.getValuable();
    return b.bundle.contains(val);
  }
  
  public Integer size() {
    Bundle b = (Bundle) this.getValuable();
    return b.bundle.size();
  }
}