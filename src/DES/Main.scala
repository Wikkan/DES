package DES

object Main extends App {

  override def main(args: Array[String]): Unit = {
    val des = new DES("20182017", "hello")
    println(des.run())
  }

}
