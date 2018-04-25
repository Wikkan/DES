package DES

/**
  *
  * Created by E. Adrián Garro Sánchez on 24/04/18.
  * Instituto Tecnológico de Costa Rica.
  *
  */

object Tools {

  /*
   * Split an array into sub arrays of size "n".
   */
  def nSplit(l: Array[Int], n: Int = 8): Array[Array[Int]] = {
    l.grouped(n).toArray
  }

  /*
   * Recreate the string from the bit array.
   */
  def bitsToString(bits: Array[Int]): String = {
    val bytes = nSplit(bits)
    val bytesFixed = for (l <- bytes) yield {
      l.mkString
    }
    val chars: Array[Char] = for (byte <- bytesFixed) yield {
      Integer.parseInt(byte, 2).toChar
    }
    chars.mkString
  }

  /*
   * Convert a string into a array of bits.
   */
  def stringToBits(text: String): Array[Int] = {
    var bits: Array[Int] = Array()
    for (char <- text) {
      val byte: String = toBinary(char)
      val fixedBits = for (digit <- byte) yield {
        digit.asDigit
      }
      bits = bits ++ fixedBits
    }
    bits
  }

  /*
   * Return the binary value as a string of the given size.
   */
  def toBinary(c: Char, digits: Int = 8): String =
    String.format("%" + digits + "s", c.toInt.toBinaryString).replace(' ', '0')

  /*
   * Return the binary value as a string of the given size.
   */
  def intToBinary(i: Int, digits: Int = 8): String =
    String.format("%" + digits + "s", i.toBinaryString).replace(' ', '0')

}
