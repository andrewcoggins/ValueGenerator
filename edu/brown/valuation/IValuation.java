package brown.valuation; 

import brown.valuable.IValuable;

/**
 * interface for 
 * @author acoggins
 *
 */
public interface IValuation {
  
  public IValuable getValuable();
  
  public Double getPrice();
  
  public void setPrice(Double newPrice);
  
  
}