package DES

object Main extends App {

  override def main(args: Array[String]): Unit = {

    val k1: String = "-21zwPzG"
    val k2: String = "xF41kL0U"
    val k3: String = "yAV8ni67"

    /*// Simple Encryption
    val cipherTextHex = Tools.stringToHex(TDES.encrypt(k1, k2, k3, "supersecret!"))
    Tools.saveFileContentInProperties("/Users/adrian/Desktop/", "c", cipherTextHex)
    println(cipherTextHex)*/

    /*// Simple Decryption
    val cipherTextProperties = Tools.loadProperties("/Users/adrian/Desktop/c1525143804478.properties")
    val plainText = Tools.removePadding(
      TDES.decrypt(k1, k2, k3, Tools.hexToString(cipherTextProperties.getProperty("content")))
    )
    println(plainText)*/

    /* File Encryption */
    val imgBase64 = Tools.encoder("/Users/adrian/Pictures/Motos/CRF.jpg")
    Tools.saveFileContentInProperties(
      "/Users/adrian/Desktop/",
      "c",
      Tools.stringToHex(TDES.encrypt(k1, k2, k3, imgBase64)),
      ".jpg"
    )

    /* File Decryption */
    //Tools.decoder(str, "/Users/adrian/Desktop/CRF1.jpg")

  }

}
