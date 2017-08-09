package brown.generator.library;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.function.Function;

import org.apache.commons.math3.distribution.NormalDistribution;
import org.apache.commons.math3.exception.NotStrictlyPositiveException;
import org.apache.commons.math3.random.ISAACRandom;

import brown.generator.IGenerator;
import brown.valuable.library.Bundle;
import brown.valuable.library.Good;
import brown.valuation.IValuation;
import brown.valuation.library.AdditiveValuation;
import brown.valuation.library.AdditiveValuationSet;
import brown.valuation.library.BundleValuation;
import brown.valuation.library.BundleValuationSet;

public class SizeDependentGenerator implements IGenerator {

  private Function<Integer, Double> valFunction; 
  private Double valueScale;

  public SizeDependentGenerator(Function<Integer, Double> valFunction, Double valueScale) {
    this.valFunction = valFunction; 
    this.valueScale = valueScale; 
  }
  
  @Override
  public AdditiveValuation getSingleValuation(Good aGood) {
   Double value = valFunction.apply(1);
   return new AdditiveValuation(aGood, value);
  }
  
  @Override
  public AdditiveValuationSet getAdditiveValuation(Bundle bundle) { 
    AdditiveValuationSet returnSet = new AdditiveValuationSet(); 
    Double meanValue = valFunction.apply(1);
    for(Good g : bundle.bundle) {
      AdditiveValuation a = new AdditiveValuation(g, meanValue);
      returnSet.add(a);
    }
    return returnSet;
  }

  @Override
  public BundleValuationSet getAllBundleValuations(Bundle bundle) {
    //initialize a bundle of existing sets.
    BundleValuationSet existingSets = new BundleValuationSet();
    //add an empty bundle
    existingSets.add(new BundleValuation(new Bundle(), 0.0));
      //iterate bundle adding process over all goods
      for(Good good : bundle.bundle) {
        //create temporary bundle to be added later. 
        BundleValuationSet temp = new BundleValuationSet();
        //for each value in existingSets, create a new bundle containing that good
        //for every bundle that does not contain that good.
        for(IValuation e : existingSets) {
          BundleValuation eBundle = (BundleValuation) e;
          if (!eBundle.contains(good)) {
            System.out.println(good);
            //create a copy set of goods, and add the new good to it.
            Bundle goods = (Bundle) eBundle.getValuable();
            System.out.println(goods);
            Bundle eCopy = new Bundle(new HashSet<Good>(goods.bundle)); 
            System.out.println(eCopy);
            eCopy.bundle.add(good);
            System.out.println(goods);
            System.out.println(eCopy);
            //add it to temp with a price determined by the value function.
            temp.add(eCopy, valFunction.apply(eCopy.bundle.size()) * valueScale);
          }
        }
        //add temp to existing sets
        existingSets.addAll(temp);
        System.out.println(existingSets);
      }
    return existingSets;
  }

  @Override
  public BundleValuationSet getSomeBundleValuations(Bundle bundle,
      Integer numberOfValuations, Integer bundleSizeMean,
      Double bundleSizeStdDev) {
    //check valid inputs for bundle size mean and std. dev.
    if (bundleSizeMean > 0 && bundleSizeStdDev > 0) {
      //create a normal distribution to draw bundle sizes. 
      NormalDistribution sizeDist = new NormalDistribution(new ISAACRandom(), bundleSizeMean, 
        bundleSizeStdDev);
      //create a new bundle to add to.
      BundleValuationSet existingSets = new BundleValuationSet();
      //iterate over the number of valuations that are input
      for(int i = 0; i < numberOfValuations; i++) {
        Boolean reSample = true;
        //repeated sampling of bundles so no duplicate bundles appear in output.
        while(reSample) {
          int size = -1; 
          //repeatedly sample bundle size until a valid size is picked.
          while (size < 1 || size > bundle.bundle.size()) {
            size = (int) sizeDist.sample();}
          //bundle to be added to
            Bundle theGoods = new Bundle();
          //list of goods to uniformly sample from
            List<Good> goodList = new ArrayList<Good>(bundle.bundle); 
            //sample without replacement goods to add to the bundle size times.
            for(int j = 0; j < size; j++) {
              Integer rand = (int) (Math.random() * goodList.size());
              Good aGood = goodList.get(rand);
              theGoods.bundle.add(aGood);
              goodList.remove(aGood);
            }
            //if the resulting bundle is already in the existing bundles,
            //throw out and start over
            if(!existingSets.contains(theGoods)) {
              existingSets.add(theGoods, valFunction.apply(theGoods.bundle.size()) * valueScale);
              reSample = false;
            }
        }
      }
      return existingSets; 
  }
    //throw error if inputs for bundle size mean and standard deviation are not positive. 
    else {
      System.out.println("ERROR: bundle size parameters not positive");
      throw new NotStrictlyPositiveException(bundleSizeMean);
    }
  }
  

}