package DES

import java.io.File

import javafx.application.Application
import javafx.collections.FXCollections
import javafx.event.ActionEvent
import javafx.geometry.{Insets, Pos}
import javafx.scene.control.Alert.AlertType
import javafx.scene.control.TabPane.TabClosingPolicy
import javafx.scene.control.{Label, Tab, TabPane, TextField, _}
import javafx.scene.layout.{BorderPane, GridPane, HBox}
import javafx.scene.{Group, Scene}
import javafx.stage.{FileChooser, Stage}

object UI {
  def main(args: Array[String]) {
    Application.launch(classOf[UI], args: _*)
  }
}

class UI extends Application {

  var fileToEncryptPath = ""
  var fileToDecryptPath = ""

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

    primaryStage.setTitle("TRIPLE DES")
    val root = new Group()
    val scene = new Scene(root, 380, 330)

    val tabPane = new TabPane
    tabPane.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE)

    val mainBorderPane = new BorderPane
    mainBorderPane.setCenter(tabPane)
    mainBorderPane.prefHeightProperty.bind(scene.heightProperty)
    mainBorderPane.prefWidthProperty.bind(scene.widthProperty)

    /* ENCRYPTION TAB */

    val encryptionTab = new Tab
    encryptionTab.setText("Encriptar")

    val encryptionGrid = new GridPane
    encryptionGrid.setAlignment(Pos.CENTER)
    encryptionGrid.setHgap(10)
    encryptionGrid.setVgap(10)
    encryptionGrid.setPadding(new Insets(25, 25, 25, 25))

    val encKeysType = new Label("Tipo de llaves: ")
    encryptionGrid.add(encKeysType, 0, 0)
    val encKeysTypeColl = FXCollections.observableArrayList("Texto", "Hexadecimal")
    val encKeysTypeComboBox = new ComboBox[String](encKeysTypeColl)
    encryptionGrid.add(encKeysTypeComboBox, 1, 0)

    val encKey1Label = new Label("Llave 1:")
    encryptionGrid.add(encKey1Label, 0, 1)
    val encKey1TextField = new TextField
    encryptionGrid.add(encKey1TextField, 1, 1)

    val encKey2Label = new Label("Llave 2:")
    encryptionGrid.add(encKey2Label, 0, 2)
    val encKey2TextField = new TextField
    encryptionGrid.add(encKey2TextField, 1, 2)

    val encKey3Label = new Label("Llave 3:")
    encryptionGrid.add(encKey3Label, 0, 3)
    val encKey3TextField = new TextField
    encryptionGrid.add(encKey3TextField, 1, 3)

    val encInputType = new Label("Tipo de entrada: ")
    encryptionGrid.add(encInputType, 0, 4)
    val encInputTypeColl = FXCollections.observableArrayList("Texto", "Hexadecimal", "Archivo")
    val encInputTypeComboBox = new ComboBox[String](encInputTypeColl)
    encryptionGrid.add(encInputTypeComboBox, 1, 4)

    val textLabel = new Label("Texto: ")
    val textField = new TextField

    val loadFileLabel = new Label("Archivo: ")
    val buttonLoadFile = new Button("Cargar")
    buttonLoadFile.setOnAction((e: ActionEvent) => {
      def action(e: ActionEvent): Unit = {
        val fileChooser: FileChooser = new FileChooser()
        val extFilter: FileChooser.ExtensionFilter = new FileChooser.ExtensionFilter(
          "Properties files (*.properties)", "*.properties"
        )
        fileChooser.getExtensionFilters.add(extFilter)
        val file: File = fileChooser.showOpenDialog(primaryStage)
        fileToEncryptPath = file.getAbsolutePath
      }

      action(e)
    })

    encInputTypeComboBox.setOnAction((e: ActionEvent) => {
      def action(e: ActionEvent): Unit = {
        if (encInputTypeComboBox.getValue == "Texto" || encInputTypeComboBox.getValue == "Hexadecimal") {
          if (encryptionGrid.getChildren.contains(loadFileLabel)) {
            encryptionGrid.getChildren.remove(loadFileLabel)
          }
          if (encryptionGrid.getChildren.contains(buttonLoadFile)) {
            encryptionGrid.getChildren.remove(buttonLoadFile)
          }
          if (!encryptionGrid.getChildren.contains(textLabel)) {
            encryptionGrid.add(textLabel, 0, 5)
          }
          if (!encryptionGrid.getChildren.contains(textField)) {
            encryptionGrid.add(textField, 1, 5)
          }
        }
        if (encInputTypeComboBox.getValue == "Archivo") {
          if (encryptionGrid.getChildren.contains(textLabel)) {
            encryptionGrid.getChildren.remove(textLabel)
          }
          if (encryptionGrid.getChildren.contains(textField)) {
            encryptionGrid.getChildren.remove(textField)
          }
          if (!encryptionGrid.getChildren.contains(loadFileLabel)) {
            encryptionGrid.add(loadFileLabel, 0, 5)
          }
          if (!encryptionGrid.getChildren.contains(buttonLoadFile)) {
            encryptionGrid.add(buttonLoadFile, 1, 5)
          }
        }
      }

      action(e)
    })

    val encBtn = new Button("ENCRIPTAR")
    val encHBBtn = new HBox(5)
    encHBBtn.setAlignment(Pos.BOTTOM_LEFT)
    encHBBtn.getChildren.add(encBtn)
    encryptionGrid.add(encHBBtn, 0, 6)

    encBtn.setOnAction((e: ActionEvent) => {
      def action(e: ActionEvent): Unit = {

        var validFields: Boolean = true

        // Any Field Incomplete?
        if (encKeysTypeComboBox.getSelectionModel.isEmpty) {
          showAlert("Advertencia", "Debe indicar el tipo de las llaves.")
          validFields = false
        }
        if (encKey1TextField.getText.trim.isEmpty) {
          showAlert("Advertencia", "Debe indicar la llave 1.")
          validFields = false
        }
        if (encKey2TextField.getText.trim.isEmpty) {
          showAlert("Advertencia", "Debe indicar la llave 2.")
          validFields = false
        }
        if (encKey3TextField.getText.trim.isEmpty) {
          showAlert("Advertencia", "Debe indicar la llave 3.")
          validFields = false
        }
        if (encInputTypeComboBox.getSelectionModel.isEmpty) {
          showAlert("Advertencia", "Debe indicar el tipo de entrada.")
          validFields = false
        }
        if (encInputTypeComboBox.getValue == "Texto" || encInputTypeComboBox.getValue == "Hexadecimal") {
          if (textField.getText.trim.isEmpty) {
            showAlert("Advertencia", "Debe indicar un texto para encriptar.")
            validFields = false
          }
        }
        if (encInputTypeComboBox.getValue == "Archivo") {
          if (fileToEncryptPath == "") {
            showAlert("Advertencia", "Debe cargar un archivo para encriptar.")
            validFields = false
          }
        }

        // Check that keys are different.
        if (!encKey1TextField.getText.trim.isEmpty
          && !encKey2TextField.getText.trim.isEmpty
          && !encKey3TextField.getText.trim.isEmpty) {
          val keys = Array(encKey1TextField.getText.trim,
            encKey2TextField.getText.trim,
            encKey3TextField.getText.trim
          )
          if (keys.distinct.length != keys.length) {
            showAlert("Advertencia", "Todas las llaves deben ser diferentes.")
            validFields = false
          }
        }

        // Check if hex keys are really hex and have correct length.
        if (encKeysTypeComboBox.getValue == "Hexadecimal") {
          if (!Tools.isHexNumber(encKey1TextField.getText.trim)) {
            showAlert("Advertencia", "La llave 1 no es hexadecimal.")
            validFields = false
          }
          if (encKey1TextField.getText.trim.length < 16) {
            showAlert("Advertencia", "La llave 1 debe tener al menos 16 caracteres.")
            validFields = false
          }
          if (!Tools.isHexNumber(encKey2TextField.getText.trim)) {
            showAlert("Advertencia", "La llave 2 no es hexadecimal.")
            validFields = false
          }
          if (encKey2TextField.getText.trim.length < 16) {
            showAlert("Advertencia", "La llave 2 debe tener al menos 16 caracteres.")
            validFields = false
          }
          if (!Tools.isHexNumber(encKey3TextField.getText.trim)) {
            showAlert("Advertencia", "La llave 3 no es hexadecimal.")
            validFields = false
          }
          if (encKey3TextField.getText.trim.length < 16) {
            showAlert("Advertencia", "La llave 3 debe tener al menos 16 caracteres.")
            validFields = false
          }
        }

        // Check if text keys have correct length.
        if (encKeysTypeComboBox.getValue == "Texto") {
          if (encKey1TextField.getText.trim.length < 8) {
            showAlert("Advertencia", "La llave 1 debe tener al menos 8 caracteres.")
            validFields = false
          }
          if (encKey2TextField.getText.trim.length < 8) {
            showAlert("Advertencia", "La llave 2 debe tener al menos 8 caracteres.")
            validFields = false
          }
          if (encKey3TextField.getText.trim.length < 8) {
            showAlert("Advertencia", "La llave 3 debe tener al menos 8 caracteres.")
            validFields = false
          }
        }

        // Check if hex text input is really hex.
        if (encInputTypeComboBox.getValue == "Hexadecimal") {
          if (!textField.getText.trim.isEmpty) {
            if (!Tools.isHexNumber(textField.getText.trim)) {
              showAlert("Advertencia", "El texto a encriptar no es hexadecimal.")
              validFields = false
            }
          }
        }

        /** If all fields are OK. **/
        if (validFields) {

        }

      }

      action(e)
    })

    encryptionTab.setContent(encryptionGrid)
    tabPane.getTabs.add(encryptionTab)

    /* DECRYPTION TAB */

    val decryptionTab = new Tab
    decryptionTab.setText("Decriptar")
    tabPane.getTabs.add(decryptionTab)

    /* ABOUT TAB */

    val aboutTab = new Tab
    aboutTab.setText("Acerca de")
    val aboutHBox = new HBox
    aboutHBox.getChildren.add(new Label(
      "Instituto Tecnológico de Costa Rica\n"
        + "Criptografía\n"
        + "Algoritmo Triple DES\n"
        + "Prof. Jorge Vargas Calvo\n"
        + "Estudiantes: Adrián Garro, Josué Jiménez\n"
        + "I Semestre, 2018")
    )
    aboutHBox.setAlignment(Pos.CENTER)
    aboutTab.setContent(aboutHBox)
    tabPane.getTabs.add(aboutTab)

    root.getChildren.add(mainBorderPane)
    primaryStage.setScene(scene)
    primaryStage.show()

  }

}
