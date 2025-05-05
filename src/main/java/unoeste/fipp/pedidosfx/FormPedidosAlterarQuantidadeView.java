package unoeste.fipp.pedidosfx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import unoeste.fipp.pedidosfx.db.entidade.Pedido;

import java.net.URL;
import java.util.ResourceBundle;

public class FormPedidosAlterarQuantidadeView implements Initializable {

    @FXML
    private TextField tfNomeProduto;

    @FXML
    private TextField tfQuantidade;

    @FXML
    void onCancelar(ActionEvent event) {
        tfNomeProduto.getScene().getWindow().hide();
    }

    @FXML
    void onConfirmar(ActionEvent event) {
        FormPedidosController.tableViewItens.getItems().remove(FormPedidosController.itemQuantidade);
        Pedido.Item item = new Pedido.Item(FormPedidosController.itemQuantidade.produto(), Integer.parseInt(tfQuantidade.getText()), FormPedidosController.itemQuantidade.valor());
        FormPedidosController.tableViewItens.getItems().add(item);
        tfNomeProduto.getScene().getWindow().hide();
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tfNomeProduto.setText(FormPedidosController.itemQuantidade.produto().getNome());

    }
}
