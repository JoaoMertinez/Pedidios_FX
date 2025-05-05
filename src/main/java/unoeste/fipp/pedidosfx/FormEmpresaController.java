package unoeste.fipp.pedidosfx;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import unoeste.fipp.pedidosfx.db.dal.EmpresaDAL;
import unoeste.fipp.pedidosfx.db.entidade.Categoria;
import unoeste.fipp.pedidosfx.db.entidade.Empresa;

import java.net.URL;
import java.util.ResourceBundle;

public class FormEmpresaController implements Initializable {


    @FXML
    private TextField tfBairro;

    @FXML
    private TextField tfCep;

    @FXML
    private TextField tfCidade;

    @FXML
    private TextField tfCnpj;

    @FXML
    private TextField tfEmail;

    @FXML
    private TextField tfNomeFantasia;

    @FXML
    private TextField tfRazaoSocial;

    @FXML
    private TextField tfRua;

    @FXML
    private TextField tfTelefone;

    @FXML
    private TextField tfUf;

    @FXML
    private TextField tfVlrEmbalagem;

    @FXML
    private TextField tfNumeroDaRua;

    @FXML
    void onCancelar(ActionEvent event) {
        tfRua.getScene().getWindow().hide();
    }

    @FXML
    void onAlterar(ActionEvent event) {

        EmpresaDAL empresaDAL = new EmpresaDAL();
        Empresa empresa = null;

        if (!empresaDAL.get("").isEmpty())
            empresa = empresaDAL.get("").get(0);
        boolean empresaJaExiste = (empresa == null) ? false : true;
        if (empresa == null)
            empresa = new Empresa();

        empresa.setRazaoSocial(tfRazaoSocial.getText());
        empresa.setBairro(tfBairro.getText());
        empresa.setCep(tfCep.getText());
        empresa.setCnpj(tfCnpj.getText());
        empresa.setCidade(tfCidade.getText());
        empresa.setEmail(tfEmail.getText());
        empresa.setNomeFantasia(tfNomeFantasia.getText());
        empresa.setNumeroDaRua(tfNumeroDaRua.getText());
        empresa.setRua(tfRua.getText());
        empresa.setTelefone(tfTelefone.getText());
        empresa.setUf(tfUf.getText());
        empresa.setValorDaEmbalagem(Double.parseDouble(tfVlrEmbalagem.getText()));

        if (empresaJaExiste)
            empresaDAL.alterar(empresa);
        else
            empresaDAL.gravar(empresa);

        tfRua.getScene().getWindow().hide();

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        EmpresaDAL empresaDAL = new EmpresaDAL();
        Empresa empresa = null;

        if (!empresaDAL.get("").isEmpty())
            empresa = empresaDAL.get("").get(0);

        if (empresa != null) {

            //PREENCHER OS INPUTS
            tfRazaoSocial.setText(empresa.getRazaoSocial());
            tfBairro.setText(empresa.getBairro());
            tfCep.setText(empresa.getCep());
            tfCnpj.setText(empresa.getCnpj());
            tfCidade.setText(empresa.getCidade());
            tfEmail.setText(empresa.getEmail());
            tfNomeFantasia.setText(empresa.getNomeFantasia());
            tfNumeroDaRua.setText(empresa.getNumeroDaRua());
            tfRua.setText(empresa.getRua());
            tfTelefone.setText(empresa.getTelefone());
            tfUf.setText(empresa.getUf());
            tfVlrEmbalagem.setText(String.valueOf(empresa.getValorDaEmbalagem()));



        } else {
            tfVlrEmbalagem.setText("0");
        }
    }
}
