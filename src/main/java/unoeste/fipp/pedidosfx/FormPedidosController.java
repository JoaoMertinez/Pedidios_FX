package unoeste.fipp.pedidosfx;

import com.itextpdf.io.font.FontConstants;
import com.itextpdf.kernel.color.Color;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.IBlockElement;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Text;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import unoeste.fipp.pedidosfx.db.dal.EmpresaDAL;
import unoeste.fipp.pedidosfx.db.dal.PedidoDAL;
import unoeste.fipp.pedidosfx.db.dal.ProdutoDAL;
import unoeste.fipp.pedidosfx.db.dal.TipoPagamentoDAL;
import unoeste.fipp.pedidosfx.db.entidade.Pedido;
import unoeste.fipp.pedidosfx.db.entidade.Produto;
import unoeste.fipp.pedidosfx.db.entidade.TipoPagamento;
import unoeste.fipp.pedidosfx.db.util.SingletonDB;
import unoeste.fipp.pedidosfx.util.MaskFieldUtil;
import unoeste.fipp.pedidosfx.util.ModalTable;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLOutput;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class FormPedidosController implements Initializable {

    public static Pedido.Item itemQuantidade = null;
    public static TableView<Pedido.Item> tableViewItens = null;

    @FXML
    private Button btProduto;

    @FXML
    private ComboBox<TipoPagamento> cbTipoPagamento;

    @FXML
    private TableColumn<Pedido.Item, String> coProduto;

    @FXML
    private TableColumn<Pedido.Item, String> coQuantidade;

    @FXML
    private TableColumn<Pedido.Item, String> coValor;

    @FXML
    private DatePicker dpData;

    @FXML
    private Label lbTotal;

    @FXML
    private CheckBox rbViagem;

    @FXML
    private Spinner<Integer> spQuantidade;

    @FXML
    private TableView<Pedido.Item> tableView;

    @FXML
    private TextField tfCliente;

    @FXML
    private TextField tfID;

    @FXML
    private TextField tfTelefone;

    private Produto produto = null;

    @FXML
    void onGerarPDF(ActionEvent event) {

        String dest = System.getProperty("user.dir") + File.separator + "pedido.pdf";
        PdfWriter writer = null;

        try {
            writer = new PdfWriter(dest);

            // Criando o documento  Pdf
            PdfDocument pdf = new PdfDocument(writer);

            // Criando o Document
            Document doc = new Document(pdf);

            // Título
            PdfFont fonteTitulo = PdfFontFactory.createFont(FontConstants.HELVETICA_BOLD);
            Text titulo;


            if (tfID.getText().isEmpty())
                titulo = new Text("Pedido  |  Faiska Burguer");
            else
                titulo = new Text("Pedido nº " + TabelaPedidosController.pedido.getId() + "  |  Faiska Burguer");
            titulo.setFont(fonteTitulo);
            titulo.setFontSize(24);
            Paragraph paragrafo = new Paragraph(titulo);
            doc.add(paragrafo);

            paragrafo = new Paragraph("\n");
            doc.add(paragrafo);
            paragrafo = new Paragraph("Informações do pedido");
            paragrafo.setFont(fonteTitulo);
            paragrafo.setFontSize(16);
            doc.add(paragrafo);

            if (dpData.getValue() == null)
                paragrafo = new Paragraph("Data do pedido: ");
            else {
                DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                paragrafo = new Paragraph("Data do pedido: " + dpData.getValue().format(formatador).toString());
            }

            doc.add(paragrafo);

            paragrafo = new Paragraph("Cliente: " + tfCliente.getText());
            doc.add(paragrafo);

            paragrafo = new Paragraph("Telefone: " + tfTelefone.getText());
            doc.add(paragrafo);

            if (cbTipoPagamento.getSelectionModel().getSelectedItem() == null)
                paragrafo = new Paragraph("Tipo de pagamento: ");
            else
                paragrafo = new Paragraph("Tipo de pagamento: " + cbTipoPagamento.getSelectionModel().getSelectedItem().getNome());
            doc.add(paragrafo);

            paragrafo = new Paragraph("Viagem: " + (rbViagem.isSelected() ? "Sim" : "Não"));
            doc.add(paragrafo);

            if (tableView.getItems().size() != 0) {
                paragrafo = new Paragraph("\n\n");
                doc.add(paragrafo);
                paragrafo = new Paragraph("Itens do pedido");
                paragrafo.setFont(fonteTitulo);
                paragrafo.setFontSize(16);
                doc.add(paragrafo);

                float[] pointColumnWidths = {150F, 150F, 150F};
                Table table = new Table(pointColumnWidths);
                table.addCell(new com.itextpdf.layout.element.Cell().add("Produto").setFontSize(16).setBackgroundColor(Color.LIGHT_GRAY));
                table.addCell(new com.itextpdf.layout.element.Cell().add("Quantidade").setFontSize(16).setBackgroundColor(Color.LIGHT_GRAY));
                table.addCell(new com.itextpdf.layout.element.Cell().add("Valor").setFontSize(16).setBackgroundColor(Color.LIGHT_GRAY));

                for (int i = 0; i < tableView.getItems().size(); i++) {
                    Pedido.Item item = tableView.getItems().get(i);
                    table.addCell(new Cell().add(item.produto().getNome()));
                    table.addCell(new Cell().add("" + item.quant()));
                    table.addCell(new Cell().add("" + item.valor() * item.quant()));
                }


                doc.add(table);
            }

            paragrafo = new Paragraph("\n\n");
            doc.add(paragrafo);
            EmpresaDAL empresaDAL = new EmpresaDAL();
            double valorViagem = empresaDAL.get("").get(0).getValorDaEmbalagem();
            if (valorViagem != 0 && rbViagem.isSelected())
                paragrafo = new Paragraph("Valor final: " + lbTotal.getText() + "  | R$ " + valorViagem + " incluso de embalagem");
            else
                paragrafo = new Paragraph("Valor final: " + lbTotal.getText());
            paragrafo.setFont(fonteTitulo);
            paragrafo.setFontSize(16);
            doc.add(paragrafo);


            doc.close();
            System.out.println("Documento criado");
            Desktop.getDesktop().open(new File(dest));


        } catch (Exception e) {
            System.out.println(e.getMessage());
        }


    }


    @FXML
    void alterarLabelValorTotal() {
        double valorTotal = 0;
        for (int i = 0; i < tableView.getItems().size(); i++) {
            valorTotal += tableView.getItems().get(i).quant() * tableView.getItems().get(i).valor();
        }


        EmpresaDAL empresaDAL = new EmpresaDAL();
        double valorViagem = empresaDAL.get("").get(0).getValorDaEmbalagem();
        if (valorViagem != 0 && rbViagem.isSelected()) {
            valorTotal += valorViagem;
        }
        String valorTotalString = "" + valorTotal;
        valorTotalString = valorTotalString.replace(".", ",");
        if (valorTotalString.length() - valorTotalString.indexOf(",") == 2)
            valorTotalString += "0";
        lbTotal.setText("R$" + valorTotalString);
    }

    @FXML
    void onAdicionar(ActionEvent event) {
        // Adiciona o item mesmo que repetido
        Pedido.Item item = new Pedido.Item(produto, spQuantidade.getValue(), produto.getValor());


        // Verifica se há itens repetidos, adicionando os valores em quantidadeTotal
        int quantidadeTotal = 0;
        boolean existe = false;
        for (Pedido.Item itens : tableView.getItems()) {

            if (itens.produto().getId() == item.produto().getId()) {
                existe = true;
                quantidadeTotal = itens.quant();
                System.out.println("entrou");

            }

        }
        if (existe) System.out.println("Há itens repetidos");
        else System.out.println("Não há itens repetidos");
        if (existe) {
            for (int i = 0; i < tableView.getItems().size(); i++) {
                if (tableView.getItems().get(i).produto().getId() == item.produto().getId()) {
                    tableView.getItems().remove(i);
                }
            }
            item = new Pedido.Item(produto, quantidadeTotal + spQuantidade.getValue(), produto.getValor());
            tableView.getItems().add(item);
        } else {
            tableView.getItems().add(item);
        }
        btProduto.setText("Selecione um item");
        spQuantidade.getEditor().setText("1");
        spQuantidade.getValueFactory().setValue(1);
        alterarLabelValorTotal();
        System.out.println("spQuantidade = " + spQuantidade.getValue());
        //voltar o spinner para 1
    }


    @FXML
    void onApagarProduto(ActionEvent event) {
        if (tableView.getSelectionModel().getSelectedIndex() > -1) {
            Pedido.Item item = tableView.getSelectionModel().getSelectedItem();
            tableView.getItems().remove(item);
            tableView.setItems(tableView.getItems());
            alterarLabelValorTotal();
        }
    }

    @FXML
    void onAlterarQuantidade(ActionEvent event) throws IOException {

        if (tableView.getSelectionModel().getSelectedIndex() > -1) {
            Pedido.Item item = tableView.getSelectionModel().getSelectedItem();
            itemQuantidade = item;
            tableViewItens = tableView;
            Stage stage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(PedidosFX.class.getResource("form-pedidos-alterar-quantidade-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setTitle("Pedidos");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.showAndWait();

        }

        alterarLabelValorTotal();

    }

    @FXML
    void onCancelar(ActionEvent event) {
        lbTotal.getScene().getWindow().hide();
    }

    @FXML
    void onConfirmar(ActionEvent event) {

        Pedido pedido = new Pedido();
        pedido.setData(dpData.getValue());
        pedido.setFoneCliente(tfTelefone.getText());
        pedido.setNomeCliente(tfCliente.getText());
        pedido.setTipoPagamento(cbTipoPagamento.getSelectionModel().getSelectedItem());
        pedido.setViagem(rbViagem.isSelected() ? 'S' : 'N');
        for (int i = 0; i < tableView.getItems().size(); i++) {
            pedido.addItem(tableView.getItems().get(i));
        }
        pedido.totalizar();

        PedidoDAL pedidoDAL = new PedidoDAL();
        if (TabelaPedidosController.pedido == null) {
            pedidoDAL.gravar(pedido);
        } else {
            pedido.setId(Integer.parseInt(tfID.getText()));
            pedidoDAL.alterar(pedido);
        }


        btProduto.getScene().getWindow().hide();
    }

    @FXML
    void onSelProduto(ActionEvent event) {
        ModalTable mt = new ModalTable(new ProdutoDAL().get(""), new String[]{"id", "nome", "valor", "categoria"}, "nome");
        Stage stage = new Stage();
        stage.setScene(new Scene(mt));
        stage.setWidth(600);
        stage.setHeight(480);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();

        produto = (Produto) mt.getSelecionado();
        if (produto != null)
            btProduto.setText(produto.getNome());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        coProduto.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().produto().getNome()));
        coQuantidade.setCellValueFactory(cellData -> new SimpleStringProperty("" + cellData.getValue().quant()));
        coValor.setCellValueFactory(cellData -> new SimpleStringProperty("" + cellData.getValue().valor() * cellData.getValue().quant()));
        MaskFieldUtil.foneField(tfTelefone);
        spQuantidade.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 1));
        carregarTiposPagamento();

        if (TabelaPedidosController.pedido != null) {
            //PREENCHER OS INPUTS
            Pedido pedido = TabelaPedidosController.pedido;
            tfID.setText("" + pedido.getId());
            tfCliente.setText(pedido.getNomeCliente());
            tfTelefone.setText(pedido.getFoneCliente());
            dpData.setValue(pedido.getData());
            int indexDoTipoPagamento = 0;
            TipoPagamento teste = null;
            for (int i = 0; i < cbTipoPagamento.getItems().size(); i++) {

                TipoPagamento tipoPagamentoDoPedido = cbTipoPagamento.getItems().get(i);
                System.out.println(tipoPagamentoDoPedido.getNome());
                if (tipoPagamentoDoPedido.getId() == pedido.getTipoPagamento().getId()) {
                    System.out.println("comboBoxID = " + tipoPagamentoDoPedido.getId());
                    System.out.println("pedidoID = " + pedido.getTipoPagamento().getId());
                    indexDoTipoPagamento = i;
                    teste = tipoPagamentoDoPedido;
                }


            }
            cbTipoPagamento.getSelectionModel().select(pedido.getTipoPagamento());
            tableView.setItems(FXCollections.observableArrayList(pedido.getItens()));
            alterarLabelValorTotal();

        }
    }

    private void carregarTiposPagamento() {
        List<TipoPagamento> tipoPagamentoList = new TipoPagamentoDAL().get("");
        cbTipoPagamento.setItems(FXCollections.observableArrayList(tipoPagamentoList));
        //cbTipoPagamento.getSelectionModel().select(0);// marcar o primeiro da lista
    }

}

