import java.io.File
import java.math.BigInteger as BigInt

// modulos must be primes
fun crt(residues: List<Pair<BigInt, BigInt>>): Pair<BigInt,BigInt> { 
  return residues.reduce{ acc, r ->
    println(acc)
    crt2(acc, r)
  }
}

fun crt2(r1: Pair<BigInt, BigInt>, r2: Pair<BigInt,BigInt>): Pair<BigInt,BigInt> {
  val (x, m) = r1
  val (y, n) = r2
  val (m_dash, n_dash, _) = extendedGcd(m, n)
  val result = n*n_dash*x + m*m_dash*y

  return Pair((result%(m*n) + m*n)%(m*n), m*n)
}

// returns two numbers m' and n' such that mm' + nn' = gcd(m, n)
fun extendedGcd(m: BigInt, n: BigInt): Triple<BigInt, BigInt, BigInt> {
  if (m > n) {
    val (n_dash, m_dash, gcd) = extendedGcd(n, m)
    return Triple(m_dash, n_dash, gcd)
  }
  if (m == 0.toBigInteger()) return Triple(0.toBigInteger(), 1.toBigInteger(), n)

  val (r_bar, m_bar, gcd) = extendedGcd(n % m, m)
  return Triple(m_bar - r_bar*(n/m), r_bar, gcd)
}

val filename = "input"
val lines = File(filename).readLines()

val arrivalTime = lines[0].toInt()
val buses = lines[1].split(',').withIndex().filter{ it.value != "x" }.map { (i, n) ->
  Pair((n.toInt()-i).toBigInteger(), n.toBigInteger())
}

println(crt(buses))

