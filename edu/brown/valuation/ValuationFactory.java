package brown.valuation;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import brown.generator.IValuationGenerator;
import brown.valuable.library.Tradeable;
import brown.valuation.library.AdditiveValuation;
import brown.valuation.library.BundleValuation;

public class ValuationFactory {

  public List<AdditiveValuation> getAdditiveValuations(IValuationGenerator valGenerator,
      Set<Tradeable> goods, Integer numValuations) {
    List<AdditiveValuation> valuations = new ArrayList<AdditiveValuation>();
    for(int i = 0; i < numValuations; i++) {
      valuations.add(new AdditiveValuation(valGenerator, goods));
    }
    return valuations; 
  }
  
  public List<BundleValuation> getBundleValuations(IValuationGenerator valGenerator, 
      Set<Tradeable> goods, Boolean monotonic, Integer numValuations) {
    List<BundleValuation> valuations = new ArrayList<BundleValuation>();
    for(int i = 0; i < numValuations; i++) {
      valuations.add(new BundleValuation(valGenerator, monotonic, goods));
    }
    return valuations;
  }
}