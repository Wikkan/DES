package DES

import javafx.application.Application
import javafx.event.ActionEvent
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.layout.StackPane
import javafx.stage.Stage

object UI
{
  def main(args: Array[String]): Unit = {
    Application.launch(classOf[UI], args: _*)
  }
}

class UI extends Application
{
  override def start(primaryStage: Stage)
  {
    primaryStage.setTitle("Triple DES")
    val btn = new Button
    btn.setText("Say 'Hello World'")
    btn.setOnAction((_: ActionEvent) => {
      println("Hello World!")
    })

    val root = new StackPane
    root.getChildren.add(btn)
    primaryStage.setScene(new Scene(root, 300, 250))
    primaryStage.show()
  }

}
