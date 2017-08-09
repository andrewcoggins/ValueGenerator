package brown.valuation.library; 

import java.util.AbstractMap.SimpleEntry;

import brown.valuable.IValuable;
import brown.valuable.library.Bundle;
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

  @Override
  public String toString() {
    return "BundleValuation [entry=" + entry + "]";
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
    BundleValuation other = (BundleValuation) obj;
    if (entry == null) {
      if (other.entry != null)
        return false;
    } else if (!entry.equals(other.entry))
      return false;
    return true;
  }
  
  
}