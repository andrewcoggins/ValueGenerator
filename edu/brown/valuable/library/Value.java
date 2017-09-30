package brown.valuable.library;
import brown.valuable.IValue;

public class Value implements IValue {
  
  public Double value = null; 
  
  public Value(double val) {
      this.value = val;
  }

  @Override
  public String toString() {
    return "Value [value=" + value + "]";
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((value == null) ? 0 : value.hashCode());
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
    Value other = (Value) obj;
    if (value == null) {
      if (other.value != null)
        return false;
    } else if (!value.equals(other.value))
      return false;
    return true;
  }

}
