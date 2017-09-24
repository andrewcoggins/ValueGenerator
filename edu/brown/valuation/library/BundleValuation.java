package brown.valuation.library;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.math3.distribution.NormalDistribution;
import org.apache.commons.math3.exception.NotStrictlyPositiveException;
import org.apache.commons.math3.random.ISAACRandom;
import org.apache.commons.math3.random.RandomGenerator;

import brown.generator.IValuationGenerator;
import brown.generator.library.Bundle;
import brown.generator.library.BundleValuationSet;
import brown.generator.library.ValRandGenerator;
import brown.valuable.library.Good;
import brown.valuation.IDependentValuation;
import brown.valuation.IValuation;

/**
 * a bundle valuation is more complicated. I gave it the tools implicit in the
 * creation of values without initially creating those values.
 * 
 * @author andrew
 *
 */
public class BundleValuation implements IDependentValuation {

  private Map<Set<Good>, Double> valMap;
  private IValuationGenerator generator;
  private Set<Good> goods;
  private Boolean monotonic;

  public BundleValuation(Set<Good> goods) {
    this.generator = new ValRandGenerator();
    this.goods = goods;
    this.valMap = new HashMap<Set<Good>, Double>();
    this.monotonic = false;
  }

  // if not monotonic, don't set the constraint. There's gonna have to be some
  // uniformity to the way that these
  // functions operate.
  public BundleValuation(IValuationGenerator valGenerator, Boolean isMonotonic,
      Set<Good> goods) {
    this.generator = valGenerator;
    this.goods = goods;
    this.valMap = new HashMap<Set<Good>, Double>();
    this.monotonic = isMonotonic;
  }

  @Override
  public Map<Set<Good>, Double> getAllValuations() {
    Map<Map<Integer, Good>, Double> existingSetsID =
        new HashMap<Map<Integer, Good>, Double>();
    // map to cater to necessary iteration structure for monotonicity
    Map<Map<Integer, Good>, Double> previousSize =
        new HashMap<Map<Integer, Good>, Double>();
    Map<Integer, Good> numberGoods = new HashMap<Integer, Good>();
    int count = 0;
    for (Good good : this.goods) {
      numberGoods.put(count, good);
      count++;
    }
    // give maps starting values
    existingSetsID.put(new HashMap<Integer, Good>(), 0.0);
    previousSize.put(new HashMap<Integer, Good>(), 0.0);
    for (int i = 0; i < numberGoods.size(); i++) {
      // hashmap populated with every subset of size i;
      Map<Map<Integer, Good>, Double> temp =
          new HashMap<Map<Integer, Good>, Double>();
      // for each good in the previous size subset
      for (Map<Integer, Good> e : previousSize.keySet()) {
        // for each good, create a new bundle, as
        for (Integer id : numberGoods.keySet()) {
          Map<Integer, Good> eCopy = new HashMap<Integer, Good>(e);
          if (!e.keySet().contains(id)) {
            eCopy.put(id, numberGoods.get(id));
            if (!temp.containsKey(eCopy)) {
              // Double totalVariance = 0.0;
              // for(int anId : eCopy.keySet()) {
              // for(int secondId : eCopy.keySet()) {
              // totalVariance += varCoVar[anId][secondId];
              // }
              // }
              if (!monotonic) {
                Double bundleValue = -0.1;
                while (bundleValue < 0)
                  bundleValue = generator.makeValuation((Set) eCopy.values());
                temp.put(eCopy, bundleValue);
              } else {
                // apply monotonic constraints.
                Double highestValSubSet = 0.0;
                for (Integer anId : eCopy.keySet()) {
                  Map<Integer, Good> eCopyCopy =
                      new HashMap<Integer, Good>(eCopy);
                  eCopyCopy.remove(anId);
                  if (existingSetsID.containsKey(eCopyCopy)) {
                    if (existingSetsID.get(eCopyCopy) > highestValSubSet) {
                      highestValSubSet = existingSetsID.get(eCopyCopy);
                    }
                  }
                }
                Double sampledValue = -0.1;
                while (sampledValue < highestValSubSet) {
                  sampledValue = generator.makeValuation((Set) eCopy.values());
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
    for (Map<Integer, Good> idGood : existingSetsID.keySet()) {
      Set<Good> goodsSet = new HashSet<Good>(idGood.values());
      valMap.put(goodsSet, existingSetsID.get(idGood));
    }
    return valMap;
  }

  @Override
  public Map<Set<Good>, Double> getSomeValuations(Integer numValuations,
      Integer bundleSizeMean, Double bundleSizeStdDev) {
    if (bundleSizeMean > 0 && bundleSizeStdDev > 0) {
      populateVarCoVarMatrix(bundle);
      RandomGenerator rng = new ISAACRandom();
      NormalDistribution sizeDist =
          new NormalDistribution(rng, bundleSizeMean, bundleSizeStdDev);
      BundleValuationSet valuations = new BundleValuationSet();
      for (int i = 0; i < numberOfValuations; i++) {
        Boolean reSample = true;
        while (reSample) {
          int size = -1;
          // repeatedly sample bundle size until a valid size is picked.
          while (size < 1 || size > bundle.bundle.size()) {
            size = (int) sizeDist.sample();
          }
          Map<Integer, Good> theGoods = new HashMap<>();
          Bundle goodsSet = new Bundle();
          // list of goods to uniformly sample from
          List<Good> goodList = new ArrayList<Good>(bundle.bundle);
          // sample without replacement goods to add to the bundle size times.
          for (int j = 0; j < size; j++) {
            Integer rand = (int) (Math.random() * goodList.size());
            Good aGood = goodList.get(rand);
            theGoods.put(rand, aGood);
            goodsSet.bundle.add(aGood);
            goodList.remove(aGood);
          }
          if (!valuations.contains(goodsSet)) {
            reSample = false;
            Double variance = 0.0;
            for (Integer id : theGoods.keySet()) {
              for (Integer idTwo : theGoods.keySet()) {
                variance += varCoVar[id][idTwo];
              }
            }
            NormalDistribution bundleDist = new NormalDistribution(rng,
                valFunction.apply(theGoods.size()) * valueScale,
                variance * valueScale);
            if (!isMonotonic) {
              valuations.add(new BundleValuation(new Bundle(goodsSet.bundle),
                  bundleDist.sample()));
            }
            // if not monotonic, make sure that the added bundle's value is
            // greater than
            // all subsets and less than all supersets.
            else {
              Double minimumPrice = 0.0;
              Double maximumPrice = Double.MAX_VALUE;
              for (IValuation v : valuations) {
                if (goodsSet.bundle
                    .containsAll(((Bundle) v.getValuable()).bundle)
                    && v.getPrice() > minimumPrice) {
                  minimumPrice = v.getPrice();
                }
                if (((Bundle) v.getValuable()).bundle.containsAll(
                    goodsSet.bundle) && v.getPrice() < maximumPrice) {
                  maximumPrice = v.getPrice();
                }
              }
              Double bundlePrice = -0.1;
              while (bundlePrice < minimumPrice && bundlePrice > maximumPrice) {
                bundlePrice = bundleDist.sample();
              }
              valuations.add(new BundleValuation(new Bundle(goodsSet.bundle),
                  bundlePrice));
            }
          }
        }
      }
      return valuations;
    } else {
      System.out.println("ERROR: bundle size parameters not positive");
      throw new NotStrictlyPositiveException(bundleSizeMean);
    }
    return null;
  }

}
