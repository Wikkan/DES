package DES

/**
  *
  * Created by E. Adrián Garro Sánchez on 24/04/18.
  * Instituto Tecnológico de Costa Rica.
  *
  */

import java.io.File
import java.io.FileOutputStream
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.IOException
import java.sql.Timestamp
import java.util.Properties
import java.util.Base64

object Tools {

  val timestamp = new Timestamp(System.currentTimeMillis)

  def decoder(base64File: String, pathFile: String): Unit = {
    try {
      val contentOutFile = new FileOutputStream(pathFile)
      // Converting a Base64 String into file byte array.
      try {
        val fileByteArray = Base64.getDecoder.decode(base64File)
        contentOutFile.write(fileByteArray)
      } catch {
        case e: FileNotFoundException =>
          println("File not found" + e)
        case ioe: IOException =>
          println("Exception while reading the File " + ioe)
      } finally if (contentOutFile != null) contentOutFile.close()
    }
  }

  def encoder(filePath: String): String = {
    var base64File = ""
    val file = new File(filePath)
    try {
      val contentInFile = new FileInputStream(file)
      // Reading a file from file system.
      try {
        val fileData = new Array[Byte](file.length.asInstanceOf[Int])
        contentInFile.read(fileData)
        base64File = Base64.getEncoder.encodeToString(fileData)
      } catch {
        case e: FileNotFoundException =>
          println("File not found" + e)
        case ioe: IOException =>
          println("Exception while reading the File " + ioe)
      } finally if (contentInFile != null) contentInFile.close()
    }
    base64File
  }

  /*
   * Get properties file.
   * macOS Path example: /Users/adrian/Desktop/p.properties"
   */
  def loadProperties(path: String): Properties = {
    val properties = new Properties()
    val input = new FileInputStream(path)
    properties.load(input)
    input.close()
    properties
  }

  /*
   * Create properties in order to save file content.
   * * macOS Path example: /Users/adrian/Desktop/"
   */
  def saveFileContentInProperties(path: String, propertiesName: String, content: String, extension: String=""): Unit = {
    val properties = new Properties()
    val output = new FileOutputStream(path + propertiesName + timestamp.getTime + ".properties")
    properties.setProperty("content", content)
    if (extension != null && !extension.isEmpty) {
      properties.setProperty("extension", extension)
    }
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
