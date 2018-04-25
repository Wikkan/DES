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

object TDESUI
{
  def main(args: Array[String])
  {
    Application.launch(classOf[TDESUI], args: _*)
  }
}

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
    grid.setStyle("-fx-background-color: #D3D3D3;")

    val sceneTitle = new Text("Bienvenid@")
    sceneTitle.setFont(Font.font("Menlo", FontWeight.LIGHT, 16))
    grid.add(sceneTitle, 0, 0, 2, 1)

    val mode = new Label("Modo: ")
    grid.add(mode, 0, 1)
    val modes = FXCollections.observableArrayList("Encriptar", "Desencriptar")
    val modesComboBox = new ComboBox[String](modes)
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
    val textField = new TextField

    val cipherTextLabel = new Label("Texto cifrado: ")
    val buttonLoadCipherText = new Button("Cargar criptograma")

    val runBtn = new Button("Ejecutar")
    val runHBBtn = new HBox(10)
    runHBBtn.setAlignment(Pos.BOTTOM_CENTER)
    runHBBtn.getChildren.add(runBtn)
    grid.add(runHBBtn, 1, 7)

    val tDESResult = new Text()
    grid.add(tDESResult, 0, 8)
    GridPane.setColumnSpan(tDESResult, 2)
    GridPane.setHalignment(tDESResult, LEFT)
    tDESResult.setId("tDESResult")

    buttonLoadCipherText.setOnAction((e: ActionEvent) => {
      def foo(e: ActionEvent): Unit = {
        val fileChooser: FileChooser = new FileChooser()
        val extFilter: FileChooser.ExtensionFilter = new FileChooser.ExtensionFilter(
          "Properties files (*.properties)", "*.properties"
        )
        fileChooser.getExtensionFilters().add(extFilter)
        val file: File = fileChooser.showOpenDialog(primaryStage)
      }
      foo(e)
    })

    modesComboBox.setOnAction((e: ActionEvent) => {
      def foo(e: ActionEvent): Unit = {
        tDESResult.setText("")
        if (modesComboBox.getValue == "Encriptar") {
          if (grid.getChildren.contains(cipherTextLabel)) {
            grid.getChildren.remove(cipherTextLabel)
          }
          if (grid.getChildren.contains(buttonLoadCipherText)) {
            grid.getChildren.remove(buttonLoadCipherText)
          }
          grid.add(textLabel, 0, 5)
          grid.add(textField, 1, 5)
        } else if (modesComboBox.getValue == "Desencriptar") {
          if (grid.getChildren.contains(textLabel)) {
            grid.getChildren.remove(textLabel)
          }
          if (grid.getChildren.contains(textField)) {
            grid.getChildren.remove(textField)
          }
          grid.add(cipherTextLabel, 0, 5)
          grid.add(buttonLoadCipherText, 1, 5)
        }
      }
      foo(e)
    })

    runBtn.setOnAction((e: ActionEvent) => {
      def foo(e: ActionEvent): Unit = {
        tDESResult.setText("")
        val anyFieldIncomplete: Boolean = (modesComboBox.getSelectionModel.isEmpty
          || key1TextField.getText.trim.isEmpty
          || key2TextField.getText.trim.isEmpty
          || key3TextField.getText.trim.isEmpty)
        if (anyFieldIncomplete) showAlert("Advertencia", "¡Hay campos sin completar!")
        else if (key1TextField.getText.trim == key2TextField.getText.trim
            || key1TextField.getText.trim == key3TextField.getText.trim
            || key2TextField.getText.trim == key3TextField.getText.trim)
          showAlert("Advertencia", "¡Las llaves deben ser diferentes!")
        // TODO Check Hex Len
        else if (key1TextField.getText.trim.length < 8
          || key2TextField.getText.trim.length < 8
          || key3TextField.getText.trim.length < 8)
          showAlert("Advertencia", "¡Las llaves deben ser de 8 caracteres!")
        else if (modesComboBox.getValue == "Encriptar") {
          if (textField.getText.trim.isEmpty) showAlert("Advertencia", "¡Debe indicar el texto en claro!")
          else {
            val eK1: DES = new DES(key1TextField.getText.trim, textField.getText.trim)
            val dK2: DES = new DES(key2TextField.getText.trim, eK1.run(), false)
            val eK3: DES = new DES(key3TextField.getText.trim, dK2.run())
            val cipherText = eK3.run()
            // TODO Show Text and Hex Text
            tDESResult.setText("Resultado: " + cipherText)
          }
        }
        else if (modesComboBox.getValue == "Desencriptar") {
          // TODO replace textField.getText.trim
          val dK3: DES = new DES(key3TextField.getText.trim, textField.getText.trim, false)
          val eK2: DES = new DES(key2TextField.getText.trim, dK3.run())
          val dK1: DES = new DES(key1TextField.getText.trim, eK2.run(), false, true)
          val text = dK1.run()
          // Show Text
          tDESResult.setText("Resultado: " + text)
        }
      }
      foo(e)
    })

    val scene = new Scene(grid, 400, 300)
    primaryStage.setScene(scene)
    primaryStage.show()

  }

}
