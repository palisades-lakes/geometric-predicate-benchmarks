# geometric-predicate-benchmarks
tests of speed, accuracy, etc., informing implementation decisions for geometric predicates.

## Summary
This is rough code, archived from 
[mop-benchmarks](https://github.com/palisades-lakes/mop-benchmarks).

I have created rough Java implementations of Jonathan Shewchuk's
[expansions](https://www.cs.cmu.edu/afs/cs/project/quake/public/code/predicates.c)
and compared the performance to `BigFloat`, a simpler (to my mind)
implementation of high precision floating point numbers.
My implementation of expansion predicates take from 1-18 times as long 
as the `BigFloat` ones, with about the same runtimes for orientation tests
and expansion-based roughly 10-20 times longer for in circle.

I am archiving the Java expansion code here and 
proceeding with `BigFloat`.

It is possible that a better Java implementation of expansions might change this,
but I don't see obvious opportunities for optimization.

There are also issues in the original 
[C code](https://www.cs.cmu.edu/afs/cs/project/quake/public/code/predicates.c),
eg fetching elements off the end of arrays,
that I have 'fixed' in the Java implementations,
which seem to be working.
However I would need a better understanding what's assumed implicitly
(eg non-overlapping elements) in the code
before I would trust releasing it.

The other evaluation I've done is to compare `double` interval arithmetic with
Shewchuk's error bounds. Interval arithmetic gives more precise bounds but
is 10-30 times slower.

## Some details
The approximate nature of `double` arithmetic causes geometric predicates 
to give the wrong answer in some cases.

To deal with this we:
1. Assume input points ae specified with `double` coordinates. 
This should be precise enough for 'most' applications.
2. The geometric predicates are all moderate degree polynomials in the input 
coordinates. The possible exact values, starting from `double` values, using 
exact rational arithmetic, are a finite subset of the rational numbers, 
although not all values that can be represented by `double` or computed 
in `double` arithmetic.
3. There are a variety of implementations of subsets of the rationals that 
cover all possible predicate values resulting from 'double' inputs, 
and are reasonably efficient.
4. It is possible to estimate the error of `double` calculations, so that 
`double` calculation can be used when it is qualitatively correct, 
only reverting to higher precision in the hopefully rare cases when it is necessary.  
  
There are 2 predicates of interest, which can be defined for any euclidean 
space, though at present **R**<sup>2</sup> (actually **Q**<sup>2</sup>) 
is of primary interest.
1. Signed Volume/Orientation  
There are 3 equivalent ways of viewing this one:
   1. The signed volume (area) of an embedded simplex (eg triangle, tetrahedron, etc)
   2. The orientation (positive or negative) of an embedded simplex.
   3. On which side of a hyperplane (line) does a point lie.

    The second and third are determined by the sign of the signed volume.
There are reasonable bounds for the error in a `double` calculation of signed
volume, which means that it can be determined when the `double` calculation is
accurate enough to get orientation and 'which side' correct, which is true
'most' of the time.

2. Point in embedded simplex circumsphere (circumcircle).  
This is perhaps a less obvious predicate, It is a key part of constructing
Delaunay triangulations.  
It is a qualitative predicate, also implemented as the sign of a quantitative
predicate (signed distance inside/outside the circumsphere), 
so `double` error bounds can be used to determine when `double` calculation is
accurate enough, and when higher precision is necessary.


This code is heavily based on Jonathan Shewchuk's work:
- [Website: Adaptive Precision Floating-Point Arithmetic and Fast Robust Predicates for Computational Geometry](https://www.cs.cmu.edu/~quake/robust.html)
- [Long paper: 'Adaptive Precision Floating-Point Arithmetic ...'](https://people.eecs.berkeley.edu/~jrs/papers/robustr.pdf)
- [Short paper: 'Robust Adaptive Floating-Point Geometric Predicates'](https://people.eecs.berkeley.edu/~jrs/papers/robust-predicates.pdf)
- [Lecture Notes on Geometric Robustness](https://people.eecs.berkeley.edu/~jrs/papers/robnotes.pdf)
- [C source: predicates.c](https://www.cs.cmu.edu/afs/cs/project/quake/public/code/predicates.c)

There are 2 basic ideas here:
1. Expansions  
The key insight (which predates Shewchuk---see his papers for references)
is that the exact value of rational arithmetic ops, `+/-/*`, when the arguments are 
`double`, is the exact sum of 2 `double` values. That is:  
    a op b = r + e  
where 
   - `a` and `b` are `double` 
   - `op` is rational addition, subtraction, or multiplication
   - `r` is the value of `op` rounded to the nearest `double`
   - `+` is rational addition
   - `e` is a `double` and the exact difference between `r` 
   and the rational value of `a op b` 
     
   This means that the exact rational value of any polynomial evaluated over 
`double`inputs can be represented by the exact sum of a finite number `double`s.

2. Error bounds  
Shewchuk provides a series of error bounds, first for `double` arithmetic, and
then for partially computed expansions.
It doesn't appear to me that the extra complexity of partial expansion is 
worthwhile, though I haven't explicitly tested that.

I have compared Shewchuk's ideas (in my own implementation) to two alternatives
of my own:
1. `BigFloat`  
A high precision quasi-floating-point number: a sign flag, 
an unsigned `BigInteger` sized significand and an `int` exponent.
It is only 'quasi' floating point because the significand is treated 
as an integer, rather than as a fraction of one as in IEEE 754.
Because of this, the difference between two adjacent `BigFloat` is
2<sup>`Integer.MIN_VALUE`</sup> regardless of magnitude.  
I belive this covers all possible exact values of the relevant geometric 
predicates, but I still need to work out the details.
2. Interval arithmetic.
I have implemented 2 versions of `double` intervals:
`RoundingInterval` attempts to return the smallest `double` interval 
containing the exact value of a calculation.
`RelaxedInterval` sacrifices precision for possible performance gains.
In neither case is the performance good enough to encourage pursuing this further.


