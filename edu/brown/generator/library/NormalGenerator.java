package brown.generator.library; 

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList; 
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

import org.apache.commons.math3.distribution.NormalDistribution;
import org.apache.commons.math3.exception.NotStrictlyPositiveException;
import org.apache.commons.math3.random.ISAACRandom;
import org.apache.commons.math3.random.RandomGenerator;

import brown.generator.IGenerator;
import brown.valuable.IValuable;
import brown.valuable.library.Bundle;
import brown.valuable.library.Good;
import brown.valuation.IValuation;
import brown.valuation.IValuationSet;
import brown.valuation.library.AdditiveValuationSet;
import brown.valuation.library.BundleValuation;
import brown.valuation.library.BundleValuationSet;

public class NormalGenerator implements IGenerator {

  private Function<Integer, Double> valFunction; 
  private Double baseVariance; 
  private Double expectedCovariance;
  private Boolean isMonotonic; 
  private Double valueScale;
  private Double varCoVar[][];

  
  public NormalGenerator (Function<Integer, Double> valFunction, 
      Boolean isMonotonic, Double valueScale) {
   this.valFunction = valFunction; 
   this.baseVariance = 1.0;
   this.expectedCovariance = 0.0;
   this.isMonotonic = isMonotonic; 
   this.valueScale = valueScale; 
 }
  
  public NormalGenerator (Function<Integer, Double> valFunction, 
      Double baseVariance, Double expectedCovariance, Boolean isMonotonic, 
      Double valueScale) {
    this.valFunction = valFunction; 
    this.expectedCovariance = expectedCovariance; 
    this.baseVariance = baseVariance; 
    this.isMonotonic = isMonotonic; 
    this.valueScale = valueScale; 
  }
 
  @Override
  public AdditiveValuationSet getAdditiveValuation(IValuable valuable) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public BundleValuationSet getAllBundleValuations(IValuable valuable) {
    Bundle bundle = (Bundle) valuable; 
    populateVarCoVarMatrix(bundle);
    //random generator for all distributions in this method
    RandomGenerator rng = new ISAACRandom();
    Map<Map<Integer, Good>, Double> existingSetsID =
        new HashMap<Map<Integer, Good>, Double>();
    //map to cater to necessary iteration structure for monotonicity
    Map<Map<Integer, Good>, Double> previousSize =
        new HashMap<Map<Integer, Good>, Double>();
    Map<Integer, Good> numberGoods = new HashMap<Integer, Good>();
    int count = 0; 
    for (Good good : bundle.bundle) {
      numberGoods.put(count, good);
      count++;
    }
    //give maps starting values
    existingSetsID.put(new HashMap<Integer, Good>(), 0.0);
    previousSize.put(new HashMap<Integer, Good>(), 0.0);
    for(int i = 0; i < numberGoods.size(); i++) {
      //hashmap populated with every subset of size i;
      Map<Map<Integer, Good>, Double> temp =
          new HashMap<Map<Integer, Good>, Double>();
      //for each good in the previous size subset
      for(Map<Integer, Good> e : previousSize.keySet()) {
          //for each good, create a new bundle, as 
          for(Integer id : numberGoods.keySet()){
            Map<Integer, Good> eCopy = new HashMap<Integer, Good>(e);
            if(!e.keySet().contains(id)) {
              eCopy.put(id, numberGoods.get(id));
              if (!temp.containsKey(eCopy)) {
              Double totalVariance = 0.0; 
              for(int anId : eCopy.keySet()) {
                for(int secondId : eCopy.keySet()) {
                  totalVariance += varCoVar[anId][secondId];
                }
              }
              Double bundleMean = valFunction.apply(eCopy.size()) * valueScale; 
              NormalDistribution bundleDist = new NormalDistribution(rng, bundleMean,
                  totalVariance * valueScale);
              if (!isMonotonic) {
                temp.put(eCopy, bundleDist.sample());
              }
              else {
                //apply monotonic constraints. 
                Double highestValSubSet = 0.0;
                for(Integer anId : eCopy.keySet()) {
                  Map<Integer, Good> eCopyCopy = 
                      new HashMap<Integer, Good>(eCopy);
                  eCopyCopy.remove(anId);
                  if(existingSetsID.containsKey(eCopyCopy)) {
                    if(existingSetsID.get(eCopyCopy) > highestValSubSet) {
                      highestValSubSet = existingSetsID.get(eCopyCopy);
                    }
                  }
                }
                Double sampledValue = -0.1;
                while (sampledValue < highestValSubSet) {
                  sampledValue = bundleDist.sample();
                }
                temp.put(eCopy, sampledValue);
              }
            }
            }
          }
        }
      existingSetsID.putAll(temp);
      previousSize = temp;
    }
    //move the existing sets from an ID based to the structure in the type signature. 
    IValuationSet existingSets = new BundleValuationSet();
    for(Map<Integer, Good> idGood : existingSetsID.keySet()) {
     Bundle goodsToReturn = new Bundle((Set<Good>) idGood.values());
      existingSets.add(goodsToReturn, existingSetsID.get(idGood));
    }
    return (BundleValuationSet) existingSets;
  }

  @Override
  public BundleValuationSet getSomeBundleValuations(IValuable valuable,
      Integer numberOfValuations, Integer bundleSizeMean,
      Double bundleSizeStdDev) {
    Bundle bundle = (Bundle) valuable; 
    if (bundleSizeMean > 0 && bundleSizeStdDev > 0) {
      populateVarCoVarMatrix(bundle);
      RandomGenerator rng = new ISAACRandom();
      NormalDistribution sizeDist = new NormalDistribution(rng, bundleSizeMean, 
          bundleSizeStdDev);
    BundleValuationSet valuations = new BundleValuationSet();
    for(int i = 0; i < numberOfValuations; i++) {
        Boolean reSample = true;
        while(reSample) {
          int size = -1; 
          //repeatedly sample bundle size until a valid size is picked.
          while (size < 1 || size > bundle.bundle.size()) {
            size = (int) sizeDist.sample();}
          Map<Integer, Good> theGoods = new HashMap<>();
         Bundle goodsSet = new Bundle();
          //list of goods to uniformly sample from
            List<Good> goodList = new ArrayList<Good>(bundle.bundle); 
            //sample without replacement goods to add to the bundle size times.
            for(int j = 0; j < size; j++) {
              Integer rand = (int) (Math.random() * goodList.size());
              Good aGood = goodList.get(rand);
              theGoods.put(rand, aGood);
              goodsSet.bundle.add(aGood);
              goodList.remove(aGood);
            }
            Bundle goodsSetBundle = new Bundle(goodsSet.bundle);
            if(!valuations.contains(goodsSetBundle)) {
              reSample = false;
              Double variance = 0.0;
              for(Integer id : theGoods.keySet()) {
                for(Integer idTwo : theGoods.keySet()) {
                  variance += varCoVar[id][idTwo];
                }
              }
              NormalDistribution bundleDist = new NormalDistribution(rng,
                  valFunction.apply(theGoods.size()) * valueScale, 
                  variance * valueScale);
              if(!isMonotonic) {
                valuations.add(new BundleValuation(new Bundle(goodsSet.bundle), bundleDist.sample()));
              }
              else {
                Double minimumPrice = 0.0;
                for(IValuation v : valuations) {
                if(isSubset((Bundle) v.getValuable(), new Bundle(goodsSet.bundle)) &&
                    v.getPrice() > minimumPrice) {
                    minimumPrice = v.getPrice();
                  }
                }
                Double bundlePrice = -0.1;
                while(bundlePrice < minimumPrice) {
                  bundlePrice = bundleDist.sample();
                }
                valuations.add(new BundleValuation(new Bundle(goodsSet.bundle), bundlePrice));
              }
            }  
          }
        }
    return valuations; 
  }
  else {
    System.out.println("ERROR: bundle size parameters not positive");
    throw new NotStrictlyPositiveException(bundleSizeMean);
  }
  }
  
    

    
    
 
    /**
     * Helper function for populating the variance covariance matrix
     * for the above methods
     */
    private void populateVarCoVarMatrix(Bundle aBundle) {
      //distribution to draw covariance observations. Currently included a default value
      //to assure positive semidefiniance but hoping to later change this with a method 
      //that assures positive semidefinance as defined by Sylvester's Criterion.
      NormalDistribution varianceDist = new NormalDistribution(new ISAACRandom(),
      expectedCovariance, 0.1);
      Set<Good> goods = aBundle.bundle;
     varCoVar = new Double[goods.size()][goods.size()];
      for(int i = 0; i < goods.size(); i++) {
        for(int j = i; j < goods.size(); j++) {
          if (i == j) {
            varCoVar[i][j] = baseVariance; 
          }
          else {
            Double entry = Double.POSITIVE_INFINITY;
            while (Math.abs(entry) >= baseVariance) {
              entry = varianceDist.sample();
            }
            varCoVar[i][j] = entry;
            varCoVar[j][i] = entry;
          }
        }
      }
      }
    
    //Boolean isPositiveDefinite = false; 
    //check the positive definance of the matrix.
//    for(int i = 0; i < GOODS.size() - 1; i++) {
//      int offset = 1;
//      while (varCoVar[i][i] == 0 && (offset + i) < GOODS.size()) {
//        for(int j = 0; j < GOODS.size(); j++) {
//          Double tmp = varCoVar[i + offset][j];
//          varCoVar[i + offset][j] = varCoVar[i][j];
//          varCoVar[i][j] = tmp;
//        }
//        offset++;
//        if(isPositiveDefinite) {
//          isPositiveDefinite = false; 
//        }
//        else {
//          isPositiveDefinite = true;
//        }
//      }
//      if (varCoVar[i][i] < 0) {
//        for(int j = 0; j < GOODS.size(); j++) {
//          varCoVar[i][j] = varCoVar[i][j] * -1;
//        }
//        if(isPositiveDefinite) {
//          isPositiveDefinite = false; 
//        }
//        else {
//          isPositiveDefinite = true;
//        }
//      }
//      //now, we eliminate the rows below. 
//      for(int k = i + 1; k < GOODS.size(); k++) {
//        Double coefficient = varCoVar[k][i] / varCoVar[i][i];
//        for(int j = 0; j < GOODS.size(); j++) {
//          varCoVar[k][j] -= (varCoVar[i][j] * coefficient);
//        }
//      }
//      
//    }
//    Double product = 1.0;
//    for(int i = 0; i < GOODS.size(); i++) {
//      product = product * varCoVar[i][i];
//    }
//    if(product >= 0 && isPositiveDefinite) {
//    }
//    else if (product < 0 && !isPositiveDefinite) {
//      isPositiveDefinite = true;
//    }
//    else {
//      isPositiveDefinite = false; 
//    }
//    System.out.println("A");
//    }
    
    
    //give each good an ID
    
    /**
     * Helper function, determines is the first set is a subset of the second.
     * @param firstSet
     * the first set for which we determine if it is a subset of the second
     * @param secondSet
     * the set that the first is being compared against. 
     * @return
     * whether or not the first set is a subset of the second.
     */
    private Boolean isSubset(Bundle firstSet, Bundle secondSet) {
      for(Good f : firstSet.bundle) {
        if(!secondSet.bundle.contains(f)) {
          return false;
        }
      }
      return true;
    }


  
}