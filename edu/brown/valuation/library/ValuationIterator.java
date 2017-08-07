package brown.valuation.library; 

import java.util.Iterator;

import brown.valuation.IValuation;
import brown.valuation.IValuationSet;


public class ValuationIterator implements Iterable<IValuation>,
Iterator<IValuation> {
  
  private IValuation[] valArray;
  private int index;
  
  /**
   * Valuation iterator constructor.
   * @param valSet
   * a ValuationBundle to be iterated over. 
   */
  public ValuationIterator(IValuationSet valSet) {
    this.valArray = valSet.toArray(); 
    this.index = 0; 
  }

  /**
   * determines whether the value array has a next element. 
   */
  public boolean hasNext() {
    return index < valArray.length;
  }
  
  /**
   * returns the next element in valArray.
   */
  public IValuation next() {
    return valArray[index++];
  }
  
  /**
   * throws an exception.(?)
   */
  public void remove() {
    throw new UnsupportedOperationException();
  }
  
  /**
   * calls the valuation iterator for use on ValuationBundle.
   */
  public Iterator<IValuation> iterator() {
    return this;
  }

}
