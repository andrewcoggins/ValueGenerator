package brown.valuationrepresentation;

import java.util.Map;
import java.util.Set;

import brown.valuable.library.Tradeable;
import brown.valuable.library.Value;

public class ComplexValuation extends AValuationRepresentation {
  
  public final Map<Set<Tradeable>, Value> vals; 
  
  public ComplexValuation(Map<Set<Tradeable> , Value> bundle) { 
    this.vals = bundle; 
  }
  
  
}