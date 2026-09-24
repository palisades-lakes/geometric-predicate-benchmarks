package gep.java.benchmarks.accumulate;

import gep.java.accumulators.Accumulator;

/** <pre>
 * java -cp target\benchmarks.jar gep.java.benchmarks.accumulate.TotalSum
 * </pre>
 * @author palisades dot lakes at gmail dot com
 * @version 2026-08-21
 */

public class TotalSum extends Base {

  @Override
  public final double[] operation (final Accumulator ac,
                                   final double[] z0,
                                   final double[] z1) {
    return new double[]
      { ac.clear().addAll(z0).doubleValue() }; }

  @SuppressWarnings("unused")
  public static final void main (final String[] args)  {
    Defaults.run("TotalSum"); } }
