package brown.valuation;

import java.util.Set;

import brown.valuable.library.Tradeable;
import brown.valuationrepresentation.AValuationRepresentation;

public interface IValuation {
  
  public AValuationRepresentation getValuation(Set<Tradeable> goods);
  
}