package DES

import java.io.File
import javafx.application.Application
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.{Group, Scene}
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
import javafx.scene.control.TabPane
import javafx.scene.control.TabPane.TabClosingPolicy
import javafx.geometry.Pos
import javafx.scene.layout.BorderPane
import javafx.scene.control.Tab

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

    primaryStage.setTitle("TRIPLE DES")
    val root = new Group()
    val scene = new Scene(root, 450, 300)

    val tabPane = new TabPane
    tabPane.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE)
    val lowerLabel = new Label("  Criptografía, TEC 2018  ")

    val mainBorderPane = new BorderPane
    mainBorderPane.setTop(tabPane)
    mainBorderPane.setBottom(lowerLabel)
    BorderPane.setAlignment(lowerLabel, Pos.BOTTOM_RIGHT)
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
    encKeysTypeComboBox.getSelectionModel().select(0)
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
    encInputTypeComboBox.getSelectionModel().select(0)
    encryptionGrid.add(encInputTypeComboBox, 1, 4)

    encryptionTab.setContent(encryptionGrid)
    tabPane.getTabs.add(encryptionTab)

    /* DECRYPTION TAB */

    val decryptionTab = new Tab
    decryptionTab.setText("Decriptar")
    tabPane.getTabs.add(decryptionTab)

    root.getChildren.add(mainBorderPane)
    primaryStage.setScene(scene)
    primaryStage.show()

  }

}
