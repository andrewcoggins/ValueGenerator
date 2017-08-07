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

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((bundle == null) ? 0 : bundle.hashCode());
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
    Bundle other = (Bundle) obj;
    if (bundle == null) {
      if (other.bundle != null)
        return false;
    } else if (!bundle.equals(other.bundle))
      return false;
    return true;
  }

  @Override
  public String toString() {
    return "Bundle [bundle=" + bundle + "]";
  }
  
}