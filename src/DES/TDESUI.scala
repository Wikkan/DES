package DES

import javafx.application.Application
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.Scene
import javafx.scene.control.Label
import javafx.scene.control.TextField
import javafx.scene.layout.GridPane
import javafx.scene.text.Font
import javafx.scene.text.FontWeight
import javafx.scene.text.Text
import javafx.stage.Stage
import javafx.scene.control._
import javafx.collections.FXCollections
import javafx.scene.layout.HBox
import javafx.scene.control.Alert.AlertType
import javafx.event.ActionEvent

class TDESUI extends Application {
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
    val sceneTitle = new Text("Bienvenid@")
    sceneTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20))
    grid.add(sceneTitle, 0, 0, 2, 1)
    val mode = new Label("Modo: ")
    grid.add(mode, 0, 1)
    val modes = FXCollections.observableArrayList("Encriptar", "Desencriptar")
    val modesComboBox = new ComboBox[String](modes)
    modesComboBox.getSelectionModel.selectFirst()
    grid.add(modesComboBox, 1, 1)
    val key1Label = new Label("Llave 1:")
    grid.add(key1Label, 0, 2)
    val key1TextField = new TextField
    grid.add(key1TextField, 1, 2)
    val key2Label = new Label("Llave 2:")
    grid.add(key2Label, 0, 3)
    val key2TextField = new TextField
    grid.add(key2TextField, 1, 3)
    val key3Label = new Label("Llave 3:")
    grid.add(key3Label, 0, 4)
    val key3TextField = new TextField
    grid.add(key3TextField, 1, 4)
    val textLabel = new Label("Texto: ")
    grid.add(textLabel, 0, 5)
    val textField = new TextField
    grid.add(textField, 1, 5)
    val btn = new Button("Ejecutar TDES")
    val hbBtn = new HBox(10)
    hbBtn.setAlignment(Pos.BOTTOM_RIGHT)
    hbBtn.getChildren.add(btn)
    grid.add(hbBtn, 1, 6)
    btn.setOnAction((e: ActionEvent) => {
      def foo(e: ActionEvent) = {
        val anyFieldIncomplete = key1TextField.getText.trim.isEmpty || key2TextField.getText.trim.isEmpty || key3TextField.getText.trim.isEmpty || textField.getText.trim.isEmpty
        if (anyFieldIncomplete) showAlert("Advertencia", "¡Hay campos sin completar!")
        else if (modesComboBox.getValue == "Encriptar") {
          val eK1 = new DES(key1TextField.getText.trim, textField.getText.trim)
          val dK2 = new DES(key2TextField.getText.trim, eK1.run(), false)
          val eK3 = new DES(key3TextField.getText.trim, dK2.run())
          val cipherText = eK3.run()
          showAlert("Resultado", cipherText)
        }
        else if (modesComboBox.getValue == "Desencriptar") {
          println(textField.getText.trim)
          val dK3 = new DES(key3TextField.getText.trim, textField.getText.trim, false)
          val eK2 = new DES(key2TextField.getText.trim, dK3.run())
          val dK1 = new DES(key1TextField.getText.trim, eK2.run(), false, true)
          val text = dK1.run()
          showAlert("Resultado", text)
        }
      }
      foo(e)
    })
    val scene = new Scene(grid, 525, 300)
    primaryStage.setScene(scene)
    primaryStage.show()
  }
}
