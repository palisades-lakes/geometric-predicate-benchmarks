package gep.java.scripts.accumulators;

import gep.java.accumulators.Accumulator;
import gep.java.prng.Generator;
import gep.java.prng.Generators;

/** Profile dot products.
 *
 * <pre>
 * jy --source 25 src/scripts/java/xfp/java/scripts/TotalDot.java
 * </pre>
 * @author palisades dot lakes at gmail dot com
 * @version 2026-08-21
 */
@SuppressWarnings("unused")
public final class TotalDot {

  public static final void main (final String[] args) {
    final int dim = (2*1024*1024);
    final int trys = 8 * 1024;
    final Generator g = Generators.make("exponential",dim);
    final Accumulator a =
      gep.java.accumulators.BigFloatAccumulator.make();
    assert a.isExact();
    //Debug.DEBUG = true;
    for (int i=0;i<trys;i++) {
      final double[] x0 = (double[]) g.next();
      final double[] x1 = (double[]) g.next();
      final double z = a.clear().addProducts(x0,x1).doubleValue();
      assert Double.isFinite(z); }
    //    System.out.println("SWAPS/CALLS="
    //      + ((double) BigFloat.SWAPS)/BigFloat.CALLS);
  }

  //--------------------------------------------------------------
}
//--------------------------------------------------------------
