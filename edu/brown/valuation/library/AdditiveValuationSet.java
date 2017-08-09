package brown.valuation.library; 

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import brown.valuable.IValuable;
import brown.valuable.library.Good;
import brown.valuation.IValuation;
import brown.valuation.IValuationSet;


public class AdditiveValuationSet implements IValuationSet, 
Iterable<IValuation> {

  private Map<Good, Double> valMap;
  
  public AdditiveValuationSet() {
    this.valMap = new HashMap<Good, Double>();
  }
  
  public AdditiveValuationSet(Map<Good, Double> aMap) {
    this.valMap = aMap;
  }
  
  public AdditiveValuationSet(AdditiveValuationSet a) {
    this.valMap = new HashMap<Good, Double>(); 
    this.addAll(a);
  }
  
  @Override
  public void add(IValuation val) {
   valMap.put((Good) val.getValuable(), val.getPrice());
  }

  @Override
  public void add(IValuable item, Double price) {
   valMap.put((Good) item, price);
    
  }

  @Override
  public void clear() {
    this.valMap = new HashMap<Good, Double>();
    
  }

  @Override
  public Boolean contains(IValuable item) {
    return this.valMap.containsKey((Good) item);
  }

  @Override
  public IValuation getValuation(IValuable item) {
    return new AdditiveValuation((Good) item, valMap.get(item));
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
    for(IValuation v : (AdditiveValuationSet) vals) {
      this.add(v);
    }
  }

  @Override
  public void remove(IValuation val) {
    valMap.remove((Good) val.getValuable());  
  }

  @Override
  public void remove(IValuable item) {
    valMap.remove((Good) item);
    
  }

  @Override
  public Integer size() {
   return this.valMap.size();
  }

  @Override
  public IValuation[] toArray() {
    AdditiveValuation[] valArray = new AdditiveValuation[this.size()];
    int i = 0; 
    for(Good good : valMap.keySet()) {
      valArray[i] = (AdditiveValuation) this.getValuation(good);
      i++;
    }
    return (AdditiveValuation[]) valArray;
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
    AdditiveValuationSet other = (AdditiveValuationSet) obj;
    if (valMap == null) {
      if (other.valMap != null)
        return false;
    } else if (!valMap.equals(other.valMap))
      return false;
    return true;
  }

  @Override
  public String toString() {
    return "AdditiveValuationSet [valMap=" + valMap + "]";
  }
  
  
  
}