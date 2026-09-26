package gep.java.benchmarks.triangles.circle;

import gep.java.benchmarks.triangles.Defaults;
import gep.java.geometry.euclidean.VectorD2;
import gep.java.geometry.triangle.Triangle2D;

/** <pre>
 * mvn -q install && jmh gep.java.benchmarks.triangles.circle.All
 * </pre>
 * @author palisades dot lakes at gmail dot com
 * @version 2026-09-24
 */

public class All extends Base {

  @Override
  public final double operation (final Triangle2D t,
                                 final VectorD2 p) {
    return t.inCircle(p); }

  public static final void main (final String[] ignore)  {
    Defaults.run("CocircularInCircle");
    Defaults.run("InCircleDistance");
    Defaults.run("RandomInCircle"); } }
