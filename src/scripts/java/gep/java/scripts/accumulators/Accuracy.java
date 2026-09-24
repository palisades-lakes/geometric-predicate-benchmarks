package gep.java.scripts.accumulators;

import gep.java.accumulators.Accumulator;
import gep.java.accumulators.BigFloatAccumulator;
import gep.java.accumulators.KahanAccumulator;
import gep.java.prng.Generator;
import gep.java.benchmarks.accumulate.Common;

/** evaluate inexact accumulators for accuracy against
 * exact accumulator.
 * <pre>
 * jy --source 25 src/scripts/java/xfp/java/scripts/BFProfile.java
 * </pre>
 * @author palisades dot lakes at gmail dot com
 * @version 2019-07-29
 */
@SuppressWarnings("unused")
public final class Accuracy {

  public static final void main (final String[] args) {
    final int dim = (64*1024*1024) + 1;
    final int trys = 128;
    final Accumulator exact = BigFloatAccumulator.make();
    final Accumulator a = KahanAccumulator.make();
    for (final Generator g : Common.generators(dim)) {
      // TODO: write results to CSV
      for (int i=0;i<trys;i++) {
        final double[] x0 = (double[]) g.next();
        final double[] x1 = (double[]) g.next();
        final double truth = exact.clear().addProducts(x0,x1).doubleValue();
        final double pred = a.clear().addProducts(x0,x1).doubleValue();
        System.out.println(
          (2.0*Math.abs(truth-pred))
          / (Math.abs(truth)+Math.abs(pred))); } } }

  //--------------------------------------------------------------
}
//--------------------------------------------------------------
