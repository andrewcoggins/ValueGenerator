package brown.valuation.library; 

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import brown.valuable.IValuable;
import brown.valuable.library.Bundle;
import brown.valuation.IValuation;
import brown.valuation.IValuationSet;

public class BundleValuationSet implements IValuationSet,
Iterable<IValuation> {

  private Map<Bundle, Double> valMap;
  
  public BundleValuationSet() {
    this.valMap = new HashMap<Bundle, Double>();
  }
  
  public BundleValuationSet(Map<Bundle, Double> aMap) {
    this.valMap = aMap;
  }
  
  public BundleValuationSet(BundleValuationSet a) {
    this.valMap = new HashMap<Bundle, Double>(); 
    this.addAll(a);
  }
  
  @Override
  public void add(IValuation val) {
   valMap.put((Bundle) val.getValuable(), val.getPrice());
  }

  @Override
  public void add(IValuable item, Double price) {
   valMap.put((Bundle) item, price);
    
  }

  @Override
  public void clear() {
    this.valMap = new HashMap<Bundle, Double>();
    
  }

  @Override
  public Boolean contains(IValuable item) {
    return this.valMap.containsKey((Bundle) item);
  }

  @Override
  public IValuation getValuation(IValuable item) {
    return new BundleValuation((Bundle) item, valMap.get(item));
  }

  @Override
  public Double getOrDefault(IValuable item, Double defVal) {
    if(valMap.containsKey(item)) {
      return valMap.get(item);
    }
    else {
      return defVal;
    }
  }

  @Override
  public Boolean isEmpty() {
    return (valMap.isEmpty());
  }

  @Override
  public void addAll(IValuationSet vals) {
    for(IValuation v : (BundleValuationSet) vals) {
      this.add(v);
    }
  }

  @Override
  public void remove(IValuation val) {
    valMap.remove((Bundle) val.getValuable());  
  }

  @Override
  public void remove(IValuable item) {
    valMap.remove((Bundle) item);
    
  }

  @Override
  public Integer size() {
   return this.valMap.size();
  }

  @Override
  public IValuation[] toArray() {
    BundleValuation[] valArray = new BundleValuation[this.size()];
    int i = 0; 
    for(Bundle Bundle : valMap.keySet()) {
      valArray[i] = (BundleValuation) this.getValuation(Bundle);
      i++;
    }
    return (BundleValuation[]) valArray;
  }

  @Override
  public Iterator<IValuation> iterator() {
   ValuationIterator v = new ValuationIterator(this);
    return v;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((valMap == null) ? 0 : valMap.hashCode());
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
    BundleValuationSet other = (BundleValuationSet) obj;
    if (valMap == null) {
      if (other.valMap != null)
        return false;
    } else if (!valMap.equals(other.valMap))
      return false;
    return true;
  }

  @Override
  public String toString() {
    return "BundleValuationSet [valMap=" + valMap + "]";
  }
  
  
}