package DES

import java.io.File
import javafx.application.Application
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.Scene
import javafx.scene.control.Label
import javafx.scene.control.TextField
import javafx.scene.text.Font
import javafx.scene.text.FontWeight
import javafx.scene.text.Text
import javafx.stage.Stage
import javafx.stage.FileChooser
import javafx.scene.control._
import javafx.collections.FXCollections
import javafx.scene.layout.HBox
import javafx.scene.control.Alert.AlertType
import javafx.event.ActionEvent
import javafx.scene.layout.GridPane
import javafx.geometry.HPos.LEFT

object UI {
  def main(args: Array[String]) {
    Application.launch(classOf[UI], args: _*)
  }
}

class UI extends Application {
  /*
   * Show Alert Without Header Text.
   */
  private def showAlert(title: String, message: String): Unit = {
    val alert = new Alert(AlertType.INFORMATION)
    alert.setTitle(title)
    // Header Text: null
    alert.setHeaderText(null)
    alert.setContentText(message)
    alert.showAndWait
  }

  override def start(primaryStage: Stage): Unit = {

    primaryStage.setTitle("Triple DES")
    val grid = new GridPane
    grid.setAlignment(Pos.CENTER)
    grid.setHgap(10)
    grid.setVgap(10)
    grid.setPadding(new Insets(25, 25, 25, 25))
    grid.setStyle("-fx-background-color: #D3D3D3;")

    val sceneTitle = new Text("Bienvenid@")
    sceneTitle.setFont(Font.font("Menlo", FontWeight.LIGHT, 16))
    grid.add(sceneTitle, 0, 0, 2, 1)

    val scene = new Scene(grid, 400, 300)
    primaryStage.setScene(scene)
    primaryStage.show()

  }

}
