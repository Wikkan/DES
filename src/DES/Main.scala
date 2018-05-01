package DES

import org.apache.commons.io.FilenameUtils

object Main extends App {

  override def main(args: Array[String]): Unit = {

    val k1: String = "-21zwPzG"
    val k2: String = "xF41kL0U"
    val k3: String = "yAV8ni67"

    /*// Simple Encryption
    val message = "supersecret!"
    val cipherTextHex = Tools.stringToHex(TDES.encrypt(k1, k2, k3, message))
    val destinationPath = "/Users/adrian/Desktop/"
    val propertiesName = "cryp"
    Tools.saveFileContentInProperties(destinationPath, propertiesName, cipherTextHex)*/

    /*// Simple Decryption
    val originPath = "/Users/adrian/Desktop/c1525143804478.properties"
    val cryptogram = Tools.loadProperties(originPath)
    val plainText = Tools.removePadding(
      TDES.decrypt(k1, k2, k3, Tools.hexToString(cryptogram.getProperty("content")))
    )*/

    /* File Encryption
    val originPath = "/Users/adrian/Pictures/Motos/CRF.jpg"
    val destinationPath = "/Users/adrian/Desktop/"
    val propertiesName = "cryp"
    val fileBase64 = Tools.encoder(originPath)
    val cryptogram = TDES.encrypt(k1, k2, k3, fileBase64)
    Tools.saveFileContentInProperties(
      destinationPath,
      propertiesName,
      cryptogram,
      FilenameUtils.getExtension(originPath)
    )*/

    /* File Decryption */
    val originPath = "/Users/adrian/Desktop/cryp1525215575627.properties"
    val cryptogram = Tools.loadProperties(originPath)
    val plainText = Tools.removePadding(TDES.decrypt(k1, k2, k3, cryptogram.getProperty("content")))
    val  destinationPath = "/Users/adrian/Desktop/" + "file." + cryptogram.getProperty("extension")
    Tools.decoder(plainText, destinationPath)

  }

}
