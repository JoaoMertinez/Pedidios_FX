package unoeste.fipp.pedidosfx;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import unoeste.fipp.pedidosfx.db.dal.CategoriaDAL;
import unoeste.fipp.pedidosfx.db.dal.TipoPagamentoDAL;
import unoeste.fipp.pedidosfx.db.entidade.Categoria;
import unoeste.fipp.pedidosfx.db.entidade.TipoPagamento;
import unoeste.fipp.pedidosfx.db.util.SingletonDB;

import java.net.URL;
import java.util.ResourceBundle;

public class FormTipoPagamentoController implements Initializable {

    @FXML
    private TextField tfId;

    @FXML
    private TextField tfNome;

    @FXML
    void onCancelar(ActionEvent event) {
        tfNome.getScene().getWindow().hide();
    }

    @FXML
    void onConfirmar(ActionEvent event) {
        TipoPagamento tipoPagamento = new TipoPagamento(tfNome.getText());
        if (tfNome.getText().trim().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("O campo \"nome\" não pode ser vazio!");
            alert.showAndWait();
        } else {


            if (TabelaTipoPagamentoController.tipoPagamento == null) {


                if (!new TipoPagamentoDAL().gravar(tipoPagamento)) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Erro ao gravar \n" + SingletonDB.getConexao().getMensagemErro());
                    alert.showAndWait();
                }

            } else {
                tipoPagamento.setId(Integer.parseInt(tfId.getText()));
                if (!new TipoPagamentoDAL().alterar(tipoPagamento)) {

                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Erro ao alterar \n" + SingletonDB.getConexao().getMensagemErro());
                    alert.showAndWait();
                }
            }
            tfNome.getScene().getWindow().hide(); //fechando a janela
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(() -> {
            tfNome.requestFocus();
        });

        if (TabelaTipoPagamentoController.tipoPagamento != null) {
            //PREENCHER OS INPUTS
            TipoPagamento tipoPagamento = TabelaTipoPagamentoController.tipoPagamento;
            tfId.setText("" + tipoPagamento.getId());
            tfNome.setText(tipoPagamento.getNome());

        }
    }
}
