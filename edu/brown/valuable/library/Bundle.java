package brown.valuable.library;

import java.util.HashSet;
import java.util.Set;

import brown.valuable.IValuable;

public class Bundle implements IValuable {
  
  public Set<Good> bundle;
  
  public Bundle() {
    this.bundle = new HashSet<Good>();
  }
  
  public Bundle(Set<Good> bundle) {
    this.bundle = bundle; 
  }
  
}