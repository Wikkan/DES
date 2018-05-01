package DES

object Main extends App {

  override def main(args: Array[String]): Unit = {

    val k1: String = "-21zwPzG"
    val k2: String = "xF41kL0U"
    val k3: String = "yAV8ni67"
    val message: String = "supersecret!"

    val cipherTextHex = Tools.stringToHex(TDES.encrypt(k1, k2, k3, message))
    //Tools.saveText("/Users/adrian/Desktop/", "c", cipherTextHex)
    println(cipherTextHex)

    val c = Tools.loadText("/Users/adrian/Desktop/c1525143804478.properties", "c")

    val plainText = Tools.removePadding(
      TDES.decrypt(k1, k2, k3, Tools.hexToString(c))
    )
    println(plainText)

  }

}
