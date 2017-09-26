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
import brown.generator.library.ValRandGenerator;
import brown.valuable.library.Tradeable;
import brown.valuation.IDependentValuation;

/**
 * a bundle valuation is more complicated. I gave it the tools implicit in the
 * creation of values without initially creating those values.
 * 
 * @author andrew
 *
 */
public class BundleValuation implements IDependentValuation {

  private Map<Set<Tradeable>, Double> valMap;
  private IValuationGenerator generator;
  private Set<Tradeable> goods;
  private Boolean monotonic;

  public BundleValuation(Set<Tradeable> goods) {
    this.generator = new ValRandGenerator();
    this.goods = goods;
    this.valMap = new HashMap<Set<Tradeable>, Double>();
    this.monotonic = false;
  }

  // if not monotonic, don't set the constraint. There's gonna have to be some
  // uniformity to the way that these
  // functions operate.
  public BundleValuation(IValuationGenerator valGenerator, Boolean isMonotonic,
      Set<Tradeable> goods) {
    this.generator = valGenerator;
    this.goods = goods;
    this.valMap = new HashMap<Set<Tradeable>, Double>();
    this.monotonic = isMonotonic;
  }

  @Override
  public Map<Set<Tradeable>, Double> getAllValuations() {
    Map<Map<Integer, Tradeable>, Double> existingSetsID =
        new HashMap<Map<Integer, Tradeable>, Double>();
    // map to cater to necessary iteration structure for monotonicity
    Map<Map<Integer, Tradeable>, Double> previousSize =
        new HashMap<Map<Integer, Tradeable>, Double>();
    Map<Integer, Tradeable> numberGoods = new HashMap<Integer, Tradeable>();
    int count = 0;
    for (Tradeable good : this.goods) {
      numberGoods.put(count, good);
      count++;
    }
    // give maps starting values
    existingSetsID.put(new HashMap<Integer, Tradeable>(), 0.0);
    previousSize.put(new HashMap<Integer, Tradeable>(), 0.0);
    for (int i = 0; i < numberGoods.size(); i++) {
      // hashmap populated with every subset of size i;
      Map<Map<Integer, Tradeable>, Double> temp =
          new HashMap<Map<Integer, Tradeable>, Double>();
      // for each good in the previous size subset
      for (Map<Integer, Tradeable> e : previousSize.keySet()) {
        // for each good, create a new bundle, as
        for (Integer id : numberGoods.keySet()) {
          Map<Integer, Tradeable> eCopy = new HashMap<Integer, Tradeable>(e);
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
                  Map<Integer, Tradeable> eCopyCopy =
                      new HashMap<Integer, Tradeable>(eCopy);
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
    for (Map<Integer, Tradeable> idGood : existingSetsID.keySet()) {
      Set<Tradeable> goodsSet = new HashSet<Tradeable>(idGood.values());
      valMap.put(goodsSet, existingSetsID.get(idGood));
    }
    return valMap;
  }

  @Override
  public Map<Set<Tradeable>, Double> getSomeValuations(Integer numValuations,
      Integer bundleSizeMean, Double bundleSizeStdDev) {
    if (bundleSizeMean > 0 && bundleSizeStdDev > 0) {
      Map<Set<Tradeable>, Double> returnMap = new HashMap<Set<Tradeable>, Double>();
      // populateVarCoVarMatrix(bundle);
      RandomGenerator rng = new ISAACRandom();
      NormalDistribution sizeDist =
          new NormalDistribution(rng, bundleSizeMean, bundleSizeStdDev);
      for (int i = 0; i < numValuations; i++) {
        Boolean reSample = true;
        while (reSample) {
          List<Tradeable> goodList = new ArrayList<Tradeable>(this.goods);
          Map<Integer, Tradeable> goodMap = new HashMap<>();
          int size = -1;
          // repeatedly sample bundle size until a valid size is picked.
          while (size < 1 || size > this.goods.size()) {
            size = (int) sizeDist.sample();
          }
          // list of goods to uniformly sample from
          // sample without replacement goods to add to the bundle size times.
          for (int j = 0; j < size; j++) {
            Integer rand = (int) (Math.random() * goodList.size());
            Tradeable aGood = goodList.get(rand);
            goodMap.put(rand, aGood);
            goodList.remove(aGood);
          }
          if (!valMap.containsKey(goodMap.values())) {
            reSample = false;
            Double variance = 0.0;
            Set<Tradeable> goodSet = new HashSet<Tradeable>(goodMap.values());
            // for (Integer id : goodMap.keySet()) {
            // for (Integer idTwo : goodMap.keySet()) {
            // variance += varCoVar[id][idTwo];
            // }
            // }
            if (!this.monotonic) {
              Double bundleValue = -0.1;
              while (bundleValue < 0)
                bundleValue = generator.makeValuation(goodSet);
              returnMap.put(goodSet, bundleValue);
            }
            // if not monotonic, make sure that the added bundle's value is
            // greater than
            // all subsets and less than all supersets.
            else {
              Double minimumPrice = 0.0;
              Double maximumPrice = Double.MAX_VALUE;
              for (Set<Tradeable> goods : this.valMap.keySet()) {
                if (goodSet.containsAll(goods)
                    && valMap.get(goods) > minimumPrice) {
                  minimumPrice = valMap.get(goods);
                }
                if (goods.containsAll(goodSet)
                    && valMap.get(goods) < maximumPrice) {
                  maximumPrice = valMap.get(goods);
                }
              }
              Double bundleValue = -0.1;
              while (bundleValue < minimumPrice && bundleValue > maximumPrice) {
                bundleValue = generator.makeValuation(goodSet);
              }
              returnMap.put(goodSet, bundleValue);
            }
          }
        }
      }
      return returnMap;
    } else {
      System.out.println("ERROR: bundle size parameters not positive");
      throw new NotStrictlyPositiveException(bundleSizeMean);
    }
  }

}
