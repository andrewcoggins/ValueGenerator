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
  
  public void setPrice();
  
  public Boolean contains(IValuable val);
  
}