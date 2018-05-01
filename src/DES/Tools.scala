package DES

/**
  *
  * Created by E. Adrián Garro Sánchez on 24/04/18.
  * Instituto Tecnológico de Costa Rica.
  *
  */

import java.sql.Timestamp
import java.io.FileOutputStream
import java.io.FileInputStream
import java.util.Properties

object Tools {

  val timestamp = new Timestamp(System.currentTimeMillis)

  /*
   * Get text property from properties file.
   * Path example: /Users/adrian/Desktop/cx.properties"
   */
  def loadText(path: String, property: String): String = {
    val properties = new Properties()
    val input = new FileInputStream(path)
    // Load a properties file.
    properties.load(input)
    input.close()
    properties.getProperty(property)
  }

  /*
   * Create properties in order to save plainText/cipherText.
   */
  def saveText(path: String, property: String, text: String): Unit = {
    val properties = new Properties()
    val output = new FileOutputStream(path + property + timestamp.getTime + ".properties")
    // Set the properties value.
    properties.setProperty(property, text)
    // Save properties.
    properties.store(output, "")
    output.close()
  }

  /*
   * Convert normal string to hex bytes string.
   */
  def stringToHex(text: String): String = {
    text.toArray.map(_.toInt.toHexString).mkString
  }

  /*
   * Convert hex bytes string to normal string.
   */
  def hexToString(hexString: String): String = {
    hexString.sliding(2, 2).toArray.map(Integer.parseInt(_, 16).toChar).mkString
  }

  /*
   * Remove the padding of the plain text (it assume there is padding).
   */
  def removePadding(data: String): String = {
    val padLen: Int = data.charAt(data.length() - 1).toInt
    data.slice(0, data.length() - padLen)
  }

  /*
   * Split an array into sub arrays of size "n".
   */
  def nSplit(l: Array[Int], n: Int = 8): Array[Array[Int]] = {
    l.grouped(n).toArray
  }

  /*
   * Recreate string from bits array.
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
