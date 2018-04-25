package DES

object Main extends App {

  override def main(args: Array[String]): Unit = {

    // Triple DES

    val k1: String = "-21zwPzG"
    val k2: String = "xF41kL0U"
    val k3: String = "yAV8ni67"
    val message: String = "supersecret!"

    val eK1 = new DES(k1, message)
    val dK2 = new DES(k2, Tools.bitsToString(eK1.run()), false)
    val eK3 = new DES(k3, Tools.bitsToString(dK2.run()))
    val cipherText = Tools.bitsToString(eK3.run())
    println(cipherText)

    val dK3 = new DES(k3, cipherText, false)
    val eK2 = new DES(k2, Tools.bitsToString(dK3.run()))
    val dK1 = new DES(k1, Tools.bitsToString(eK2.run()), false)
    val text = Tools.removePadding(Tools.bitsToString(dK1.run()))
    println(text)
  }

}
