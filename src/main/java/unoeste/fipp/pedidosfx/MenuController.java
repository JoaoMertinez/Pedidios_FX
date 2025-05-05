package unoeste.fipp.pedidosfx;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MenuController implements Initializable {

    @FXML
    void onAbrirPedido(ActionEvent event) throws IOException {
        Stage stage=new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(PedidosFX.class.getResource("tabela-pedidos-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Pedidos");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.showAndWait();
    }

    @FXML
    void onCadCategoria(ActionEvent event) throws IOException {
        Stage stage=new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(PedidosFX.class.getResource("tabela-categorias-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Cadastro de categorias");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.showAndWait();

    }

    @FXML
    void onCadProduto(ActionEvent event) throws Exception{
        Stage stage=new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(PedidosFX.class.getResource("tabela-produtos-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Cadastro de Produtos");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.showAndWait();
    }

    @FXML
    void onCadEmpresa(ActionEvent actionEvent) throws Exception {
        Stage stage=new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(PedidosFX.class.getResource("form-empresa-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Empresa");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.showAndWait();
    }

    @FXML
    void onNovoPedido(ActionEvent event) throws Exception {
        Stage stage=new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(PedidosFX.class.getResource("form-pedidos-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Cadastro de Produtos");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.showAndWait();
    }

    @FXML
    void onRelCardapio(ActionEvent event) {

    }

    @FXML
    void onRelPedidosPeriodo(ActionEvent event) {

    }

    @FXML
    void onRelProduto(ActionEvent event) {

    }

    @FXML
    void onSair(ActionEvent event) {
        Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Deseja realmente finalizar?");
        if(alert.showAndWait().get()== ButtonType.OK)
            Platform.exit();
    }

    @FXML
    void onSobre(ActionEvent event) {
        Alert alert=new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Faiska Burger System");
        alert.setContentText("Desenvolvido, a partir dos códigos do professor Silvio, por:\n\tJoão Claudio Martinez da Costa (RA: 262319640)\n\tTiago Pantaroto Mena (RA: 262318580)");
        alert.show();
    }

    @FXML
    void onTipoPagamento(ActionEvent event) throws IOException {
        Stage stage=new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(PedidosFX.class.getResource("tabela-tipoPagamento-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Tipo de pagamento");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.showAndWait();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
