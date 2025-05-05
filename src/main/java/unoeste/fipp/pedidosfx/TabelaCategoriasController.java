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
import unoeste.fipp.pedidosfx.db.dal.ProdutoDAL;
import unoeste.fipp.pedidosfx.db.entidade.Categoria;
import unoeste.fipp.pedidosfx.db.entidade.Produto;
import unoeste.fipp.pedidosfx.db.util.SingletonDB;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class TabelaCategoriasController implements Initializable {

    public static Categoria categoria = null;

    @FXML
    private TableColumn<Categoria, Integer> coId;

    @FXML
    private TableColumn<Categoria, String> coNome;

    @FXML
    private TableView<Categoria> tableView;

    @FXML
    private TextField tfPesquisar;

    @FXML
    void onAlterar(ActionEvent event) throws IOException {
        if(tableView.getSelectionModel().getSelectedIndex()>-1) {
            categoria=tableView.getSelectionModel().getSelectedItem();
            Stage stage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(PedidosFX.class.getResource("form-categorias-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setTitle("Categorias");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.showAndWait();
            tfPesquisar.setText("");
            carregarTabela("");
            categoria=null;
        }
    }

    @FXML
    void onApagar(ActionEvent event) {
        if(tableView.getSelectionModel().getSelectedIndex()>-1) {
            Categoria categoria = tableView.getSelectionModel().getSelectedItem();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Confirma a exclusão da categoria?");
            if (alert.showAndWait().get() == ButtonType.OK) {
                if(!new CategoriaDAL().apagar(categoria)){
                    alert=new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Erro ao apagar \n"+ SingletonDB.getConexao().getMensagemErro());
                    alert.showAndWait();
                }
                else
                    carregarTabela("");
            }
        }
    }

    @FXML
    void onPesquisar(KeyEvent event) {
        carregarTabela(tfPesquisar.getText());

    }

    @FXML
    void onNovaCategoria(ActionEvent event) throws IOException {
        tfPesquisar.getScene().getWindow().setOpacity(0.2);
        Stage stage=new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(PedidosFX.class.getResource("form-categorias-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Produtos...");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.showAndWait();
        tfPesquisar.getScene().getWindow().setOpacity(1);
        tfPesquisar.setText("");
        carregarTabela("");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        coId.setCellValueFactory(new PropertyValueFactory<>("id"));
        coNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        carregarTabela("");
    }

    private void carregarTabela(String filtro) {
        if(!filtro.isEmpty())
            filtro=" upper(cat_nome) LIKE '%"+filtro.toUpperCase()+"%'";
        CategoriaDAL dal=new CategoriaDAL();
        List<Categoria> categoriaList=dal.get(filtro);
        tableView.setItems(FXCollections.observableArrayList(categoriaList));


    }

}
