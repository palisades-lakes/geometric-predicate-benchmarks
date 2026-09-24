package gep.java.scripts.triangles;

import gep.java.accumulators.ZhuHayesAccumulator;
import gep.java.geometry.Generators;
import gep.java.geometry.euclidean.VectorD2;
import gep.java.geometry.triangle.RoundingIntervalTriangle2D;
import gep.java.geometry.triangle.Triangle2D;
import gep.java.numbers.Doubles;
import gep.java.prng.Generator;
import gep.java.prng.PRNG;

/** <pre>
 * mvn clean install && jy src/scripts/java/mop/java/scripts/triangles/RandomInCircle.java
 * </pre>
 * @author palisades dot lakes at gmail dot com
 * @version 2026-09-11
 */

public final class InCircle {

  @SuppressWarnings("unused")
  public static final void main (final String[] args) {

    final Generator pointGenerator =
      Generators.vectorD2Generator(
        8192,
        Doubles.laplaceGenerator(
          PRNG.well44497b("seeds/Well44497b-2019-01-05.txt"),
          0.0, 1.0));
    final VectorD2[] points = (VectorD2[]) pointGenerator.next();

    final Generator triangleGenerator =
      Generators.triangleGenerator(
        8192,
        //ExactCache::from,
        //TriangleBF2::from,
        RoundingIntervalTriangle2D::from,
        //RoBfTriangle2D::from,
        Generators.vectorD2Generator(
          Doubles.laplaceGenerator(
            PRNG.well44497b("seeds/Well44497b-2019-01-07.txt"),
            0.0, 1.0)));
    final Triangle2D[] triangles =
      (Triangle2D[]) triangleGenerator.next();

    final double[] d = new double[points.length*triangles.length];
    System.out.println(points.length);
    System.out.println(triangles.length);
    System.out.println(Math.multiplyExact(points.length,triangles.length));
    System.out.println(d.length);

    final int nreps = 64;
    for (int i=0; i<nreps; i++) {
      int k=0;
      for (final Triangle2D t : triangles) {
        for (final VectorD2 p : points) {
          d[k++] = t.inCircleDistance(p); } }

      final ZhuHayesAccumulator zh = ZhuHayesAccumulator.make();
      zh.addAll(d);
      System.out.println(
        i + ": mean distance:" + (zh.doubleValue() / d.length)); } }


  //--------------------------------------------------------------------
  // disable construction
  //--------------------------------------------------------------------

  private InCircle () {
    throw new UnsupportedOperationException(); }

  //-------------------------------------------------------------------
} // end class
//-------------------------------------------------------------------
