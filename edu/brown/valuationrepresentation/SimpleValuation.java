package brown.valuationrepresentation;

import java.util.Map;

import brown.valuable.library.Tradeable;
import brown.valuable.library.Value;

public class SimpleValuation extends AValuationRepresentation {
  
  public final Map<Tradeable, Value> vals;
  
  public SimpleValuation(Map<Tradeable, Value> vals) {
    this.vals = vals; 
  }
  
}