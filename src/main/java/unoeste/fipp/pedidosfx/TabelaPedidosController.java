package unoeste.fipp.pedidosfx;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import unoeste.fipp.pedidosfx.db.dal.PedidoDAL;
import unoeste.fipp.pedidosfx.db.dal.ProdutoDAL;
import unoeste.fipp.pedidosfx.db.entidade.Pedido;
import unoeste.fipp.pedidosfx.db.entidade.Produto;
import unoeste.fipp.pedidosfx.db.entidade.TipoPagamento;
import unoeste.fipp.pedidosfx.db.util.SingletonDB;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class TabelaPedidosController implements Initializable {

    public static Pedido pedido = null;
    @FXML
    private TableColumn<Pedido, String> coCliente;

    @FXML
    private TableColumn<Pedido, LocalDate> coData;

    @FXML
    private TableColumn<Pedido, Integer> coId;

    @FXML
    private TableColumn<Pedido, String> coTelefone;

    @FXML
    private TableColumn<Pedido, TipoPagamento> coTipoPagamento;

    @FXML
    private TableColumn<Pedido, Double> coTotal;

    @FXML
    private TableColumn<Pedido, Character> coViagem;

    @FXML
    private TableView<Pedido> tableView;

    @FXML
    void onAlterar(ActionEvent event) throws IOException {
        if(tableView.getSelectionModel().getSelectedIndex()>-1) {
            pedido=tableView.getSelectionModel().getSelectedItem();
            Stage stage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(PedidosFX.class.getResource("form-pedidos-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setTitle("Pedidos");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.showAndWait();
            carregarTabela();
            pedido=null;
        }
    }

    @FXML
    void onApagar(ActionEvent event) {
        if(tableView.getSelectionModel().getSelectedIndex()>-1) {
            Pedido pedido = tableView.getSelectionModel().getSelectedItem();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Confirma a exclusão do pedido?");
            if (alert.showAndWait().get() == ButtonType.OK) {
                if(!new PedidoDAL().apagar(pedido)){
                    alert=new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Erro ao apagar \n"+ SingletonDB.getConexao().getMensagemErro());
                    alert.showAndWait();
                }
                else
                    carregarTabela();
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        coId.setCellValueFactory(new PropertyValueFactory<>("id"));
        coData.setCellValueFactory(new PropertyValueFactory<>("data"));
        coCliente.setCellValueFactory(new PropertyValueFactory<>("nomeCliente"));
        coTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        coViagem.setCellValueFactory(new PropertyValueFactory<>("viagem"));
        coTipoPagamento.setCellValueFactory(new PropertyValueFactory<>("tipoPagamento"));
        coTelefone.setCellValueFactory(new PropertyValueFactory<>("foneCliente"));
        carregarTabela();
    }

    private void carregarTabela() {

        PedidoDAL dal=new PedidoDAL();
        List<Pedido> pedidoList=dal.get("");
        for(Pedido p: pedidoList) {
            p.setViagem(Character.toUpperCase(p.getViagem()));
        }
        tableView.setItems(FXCollections.observableArrayList(pedidoList));
    }
}
