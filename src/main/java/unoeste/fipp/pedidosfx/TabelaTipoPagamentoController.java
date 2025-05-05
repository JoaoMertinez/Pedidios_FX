package unoeste.fipp.pedidosfx;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import unoeste.fipp.pedidosfx.db.dal.CategoriaDAL;
import unoeste.fipp.pedidosfx.db.dal.TipoPagamentoDAL;
import unoeste.fipp.pedidosfx.db.entidade.Categoria;
import unoeste.fipp.pedidosfx.db.entidade.TipoPagamento;
import unoeste.fipp.pedidosfx.db.util.SingletonDB;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class TabelaTipoPagamentoController implements Initializable {

    public static TipoPagamento tipoPagamento = null;

    @FXML
    private TableColumn<TipoPagamento, Integer> coId;

    @FXML
    private TableColumn<TipoPagamento, String> coNome;

    @FXML
    private TableView<TipoPagamento> tableView;

    @FXML
    private TextField tfPesquisar;

    @FXML
    void onAlterar(ActionEvent event) throws IOException {
        if (tableView.getSelectionModel().getSelectedIndex() > -1) {
            tipoPagamento = tableView.getSelectionModel().getSelectedItem();
            Stage stage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(PedidosFX.class.getResource("form-tipoPagamento-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setTitle("Tipos de pagamento");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.showAndWait();
            tfPesquisar.setText("");
            carregarTabela("");
            tipoPagamento = null;
        }
    }

    @FXML
    void onApagar(ActionEvent event) {
        if (tableView.getSelectionModel().getSelectedIndex() > -1) {
            TipoPagamento tipoPagamento = tableView.getSelectionModel().getSelectedItem();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Confirma a exclusão do tipo de pagamento?");
            if (alert.showAndWait().get() == ButtonType.OK) {
                if (!new TipoPagamentoDAL().apagar(tipoPagamento)) {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Erro ao apagar \n" + SingletonDB.getConexao().getMensagemErro());
                    alert.showAndWait();
                } else
                    carregarTabela("");
            }
        }
    }

    @FXML
    void onNovoTipoPagamento(ActionEvent event) throws IOException {
        tfPesquisar.getScene().getWindow().setOpacity(0.2);
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(PedidosFX.class.getResource("form-tipoPagamento-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Tipos de pagamento");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.showAndWait();
        tfPesquisar.getScene().getWindow().setOpacity(1);
        tfPesquisar.setText("");
        carregarTabela("");
    }

    @FXML
    void onPesquisar(KeyEvent event) {
        carregarTabela(tfPesquisar.getText());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        coId.setCellValueFactory(new PropertyValueFactory<>("id"));
        coNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        carregarTabela("");
    }

    private void carregarTabela(String filtro) {
        if (!filtro.isEmpty())
            filtro = " upper(tpg_nome) LIKE '%" + filtro.toUpperCase() + "%'";
        TipoPagamentoDAL dal = new TipoPagamentoDAL();
        List<TipoPagamento> tipoPagamentoList = dal.get(filtro);
        tableView.setItems(FXCollections.observableArrayList(tipoPagamentoList));


    }
}
