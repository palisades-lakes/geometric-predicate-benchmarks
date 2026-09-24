package gep.java.test.accumulators;

import java.util.List;

/** Shared data/code for accumulator tests.
 * Not instantiable. Class slots/methods only.
 *
 * @author mcdonald dot john dot alan at gmail dot com
 * @version 2026-08-21
 */
public final class Shared {

  public static final int TEST_DIM = 256; //(1 * 64 * 1024) - 1;
  //1 << 14;

  public static final List<String> accumulators () {
    return
      List.of(
        //,
        "gep.java.accumulators.IFastAccumulator",
        "gep.java.accumulators.ZhuHayesGCAccumulator",
        "gep.java.accumulators.ZhuHayesGCBranch",
        "gep.java.accumulators.ZhuHayesBranch"
        // "gep.java.accumulators.RationalFloatAccumulator"
        // ,
        // // Same as non-strict, just slower
        // "gep.java.accumulators.DoubleAccumulator",
        // "gep.java.accumulators.StrictDoubleFmaAccumulator",
        // // overflow unless values more limited
        // "gep.java.accumulators.FloatAccumulator",
        // "gep.java.accumulators.FloatFmaAccumulator",
        // // Too slow to keep testing
        // "gep.java.accumulators.BigDecimalAccumulator",
        // "gep.java.accumulators.BigFractionAccumulator",
        // "gep.java.accumulators.DoubleFmaAccumulator",
        // "gep.java.accumulators.KahanFmaAccumulator",
        // //,
        // // Broken in many ways.
        // // Doesn't overflow to infinity, or accumulate extreme
        // // values correctly.
        // // Slow as well.
        // //"gep.java.accumulators.RatioAccumulator"
        ); }

  //--------------------------------------------------------------
  // disable constructor
  //--------------------------------------------------------------

  private Shared () {
    throw new UnsupportedOperationException(
      "can't instantiate " + getClass()); }

  //--------------------------------------------------------------
}
//--------------------------------------------------------------
