package unoeste.fipp.pedidosfx;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import unoeste.fipp.pedidosfx.db.dal.CategoriaDAL;
import unoeste.fipp.pedidosfx.db.dal.ProdutoDAL;
import unoeste.fipp.pedidosfx.db.entidade.Categoria;
import unoeste.fipp.pedidosfx.db.entidade.Produto;
import unoeste.fipp.pedidosfx.db.util.SingletonDB;
import unoeste.fipp.pedidosfx.util.MaskFieldUtil;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class FormCategoriasController implements Initializable {

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
        Categoria categoria = new Categoria(tfNome.getText());
        if (tfNome.getText().trim().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("O campo \"nome\" não pode ser vazio!");
            alert.showAndWait();
        } else {


            if (TabelaCategoriasController.categoria == null) {


                if (!new CategoriaDAL().gravar(categoria)) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Erro ao gravar \n" + SingletonDB.getConexao().getMensagemErro());
                    alert.showAndWait();
                }

            } else {
                categoria.setId(Integer.parseInt(tfId.getText()));
                if (!new CategoriaDAL().alterar(categoria)) {

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

        if (TabelaCategoriasController.categoria != null) {
            //PREENCHER OS INPUTS
            Categoria categoria = TabelaCategoriasController.categoria;
            tfId.setText("" + categoria.getId());
            tfNome.setText(categoria.getNome());

        }
    }
}
