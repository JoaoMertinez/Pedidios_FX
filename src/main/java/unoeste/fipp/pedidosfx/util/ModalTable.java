package unoeste.fipp.pedidosfx.util;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

import java.lang.reflect.Field;
import java.util.Base64;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

public class ModalTable extends BorderPane {
    private TableView tabela;
    private Button btConfirmar, btCancelar;
    private TextField tffiltro;
    private Object selecionado=null;
    private String filtro="";
    private Object object;
    private List<?> dados;
    public ModalTable(List<?> dados,  String[] colunas, String filtro) {
        super();
        this.filtro=filtro;
        object=dados.get(0);
        this.dados=dados;
        //this.setStyle("-fx-border-color:black;-fx-background-color:lightgray");

        this.getStylesheets().add("data:text/css;base64," + Base64.getEncoder().encodeToString(estilo.getBytes()));
        int i=0;
        tabela=new TableView(FXCollections.observableArrayList(dados));
        //tabela.setStyle("-fx-border-color:black;-fx-background-color:lightgray");
        for (String col : colunas) {
            TableColumn column = new TableColumn(col);
            column.setCellValueFactory(new PropertyValueFactory<>(col));
            tabela.getColumns().add(column);
        }
        tabela.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        carregarTabela(dados);
        this.setCenter(tabela);
        btConfirmar=new Button("Confirmar");
        btCancelar=new Button("Cancelar");
        btConfirmar.setOnAction(e->{confirmar();});
        btCancelar.setOnAction(e->{cancelar();});
        HBox hbox=new HBox(btConfirmar,btCancelar);
        hbox.setAlignment(Pos.CENTER); hbox.setSpacing(20); hbox.setMinHeight(40);
        this.setBottom(hbox);
        if(filtro!=null && !filtro.isEmpty())
        {   tffiltro=new TextField();
            tffiltro.setPromptText("Digite para pesquisar");
            HBox hboxf=new HBox(tffiltro);
            hboxf.setAlignment(Pos.CENTER); hboxf.setMinHeight(40);
            this.setTop(hboxf);
            tffiltro.setOnKeyTyped(e->{filtrar();});
        }
        this.setPadding(new Insets(10,10,10,10));
        this.setEffect(new DropShadow(20, Color.BLACK));
    }

    private void filtrar() {
        try {
            tabela.setItems(FXCollections.observableArrayList(dados));
            Field field = object.getClass().getDeclaredField(filtro);
            field.setAccessible(true);
            //Object value = field.get(object); // recupera o valor do atributo de filtro
            tabela.setItems(tabela.getItems().filtered(new Predicate() {
                @Override
                public boolean test(Object o) {
                    try {
                        Field field = o.getClass().getDeclaredField(filtro);
                        field.setAccessible(true);
                        return ((String)field.get(o)).toUpperCase().contains(tffiltro.getText().toUpperCase());
                    }
                    catch(Exception e){
                        System.out.println(e);
                        return false;}
                }
            }));
        }catch(Exception e){
            System.out.println(e);
        }
    }

    private void cancelar() {
        selecionado=null;
        btConfirmar.getScene().getWindow().hide();
    }

    private void confirmar() {
        if (tabela.getSelectionModel().getSelectedItem()!=null)
            selecionado=tabela.getSelectionModel().getSelectedItem();
        btConfirmar.getScene().getWindow().hide();
    }

    private void carregarTabela(List dados) {
        tabela.setItems(FXCollections.observableArrayList(dados));
    }
    public Object getSelecionado() {
        return selecionado;
    }
    private String estilo= """
            /*
              *
              *   Author: Antonio Pelusi
              *   Website: https://www.antoniopelusi.com
              *   Source code: https://github.com/antoniopelusi/JavaFX-Dark-Theme
              *
              */
              
              .root{
                  -fx-background-color: #2D2D30;
                  -fx-border-radius: 10 10 10 10;
                  -fx-background-radius: 10 10 10 10;
                  -fx-background-insets:10 10 10 10;
                  -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.8), 10, 0, 0, 0);
              }
              
              
              /* Label */
              .label{
                  -fx-text-fill: #AFB1B3;
              }
              
              
              /* Pane */
              .pane-grid{
              	-fx-background-color: #2D2D30;
                  background-color: #2D2D30;
              }
              
              
              /* GridPane */
              .tab-pane-grid{
                  /*
                   * GridPane gridPane = new GridPane();
                   * gridPane.getStyleClass().add("tab-pane-grid");
                   * gridPane.setPadding(new Insets(3,0,0,0));
                  */
                  -fx-background-color: #2D2D30;
                  background-color: #2D2D30;
                  -fx-background-insets: 3 0 0 0;
              }
              
              
              /* TextField */
              .text-field{
                  -fx-background-radius: 0;
                  -fx-border-width: 1;
                  border-width: 1;
                  -fx-border-color: #222222;
                  border-color: #222222;
                  -fx-background-color: #1A1A1A;
                  background-color: #1A1A1A;
                  -fx-text-fill: #AFB1B3;
                  -fx-highlight-fill: #4e4e4e;
                  -fx-min-height: 25;
                  min-height: 25;
                  -fx-pref-height: 25;
              }
              .text-field:hover{
                   -fx-border-color: #222222;
                   border-color: #222222;
                   -fx-background-color: #3E3E40;
                   background-color: #3E3E40;
              }
              .text-field:focused{
                  -fx-border-color: #4e4e4e;
                  border-color: #4e4e4e;
                  -fx-background-color: #1A1A1A;
                  background-color: #1A1A1A;
              }
              
              
              /* ComboBox */
              .combo-box{
                  -fx-background-radius: 0;
                  -fx-border-insets: 0 0 -1 0;
                  -fx-border-width: 1;
                  border-width: 1;
                  -fx-border-color: #222222;
                  border-color: #222222;
                  -fx-background-color: #1A1A1A;
                  background-color: #1A1A1A;
                  -fx-min-height: 25;
                  min-height: 25;
                  -fx-pref-height: 25;
              }
              .combo-box .list-cell:selected{
                  -fx-background-color: #1A1A1A;
                  background-color: #1A1A1A;
              }
              .combo-box .list-cell:hover{
                  -fx-background-color:  #3E3E40;
                  background-color:  #3E3E40;
              }
              .combo-box:hover{
                  -fx-border-color: #222222;
                  border-color: #222222;
                  -fx-background-color: #3E3E40;
                  background-color: #3E3E40;
                  -fx-cursor: HAND;
                  cursor: HAND;
              }
              .combo-box:focused{
                  -fx-border-color: #4e4e4e;
                  border-color: #4e4e4e;
                  -fx-background-color: #1A1A1A;
                  background-color: #1A1A1A;
              }
              .combo-box-base > .arrow-button {
                  -fx-padding: 0 6;
                  padding: 0 6;
              }
              .combo-box-base > .arrow-button > .arrow {
                  -fx-background-color: #555555;
                  background-color: #555555;
                  -fx-background-insets: 0;
                  -fx-padding: 2 4;
                  padding: 2 4;
                  -fx-shape: "M 0 0 H 7 L 3.5 4 z";
              }
              .combo-box .list-view{
                  -fx-background-radius: 0;
                  -fx-background-color: #1A1A1A;
                  background-color: #1A1A1A;
                  -fx-border-width: 0;
                  border-width: 0;
                  -fx-padding: 1;
                  padding: 1;
              }
              
              
              /* ListCell */
              .list-cell{
                  -fx-background-radius: 0;
                  -fx-border-width: 0;
                  border-width: 0;
                  -fx-text-fill: #AFB1B3;
                  -fx-background-color: #1A1A1A;
                  background-color: #1A1A1A;
                  -fx-min-height: 18;
                  min-height: 18;
                  -fx-pref-height: 18;
                  -fx-padding: 0 0 0 3;
                  padding: 0 0 0 3;
              }
              .list-cell:hover{
                  -fx-background-color: #3E3E40;
                  background-color: #3E3E40;
                  -fx-cursor: HAND;
                  cursor: HAND;
              }
              .list-cell:pressed{
                  -fx-background-color: #3E3E40;
                  background-color: #3E3E40;
              }
              .list-cell:selected{
                  -fx-background-color: #3E3E40;
                  background-color: #3E3E40;
              }
              
              
              /* ListView */
              .list-view{
                  -fx-background-radius: 0;
                  -fx-background-color: #1A1A1A;
                  background-color: #1A1A1A;
                  -fx-border-width: 1;
                  border-width: 1;
                  -fx-padding: 1;
                  padding: 1;
              }
              .list-view .scroll-bar{
                  -fx-background-insets: 0 -1 0 0;
              }
              .list-view .scroll-bar .thumb{
                  -fx-background-insets: 0 3 0 2;
              }
              .list-view:hover{
                  -fx-border-color: #3E3E40;
                  border-color: #3E3E40;
                  -fx-padding: 0;
                  padding: 0;
                  -fx-background-insets: 1 1 0 1;
              }
              .list-view:focused{
                  -fx-background-color: #1A1A1A;
                  background-color: #1A1A1A;
                  -fx-border-color: #4e4e4e;
                  border-color: #4e4e4e;
                  -fx-padding: 0 0 1 0;
                  padding: 0 0 1 0;
                  -fx-background-insets: 1;
              }
              
              
              /* Button */
              .button{
                  -fx-background-radius: 0;
                  -fx-border-insets: 0 0 -1 0;
                  -fx-border-width: 1;
                  border-width: 1;
                  -fx-border-color: #222222;
                  border-color: #222222;
                  -fx-background-color: #1A1A1A;
                  background-color: #1A1A1A;
                  -fx-text-fill: #AFB1B3;
                  -fx-min-height: 25;
                  min-height: 25;
                  -fx-pref-height: 25;
              }
              .button:focused{
                  -fx-border-color: #4e4e4e;
                  border-color: #4e4e4e;
                  -fx-background-color: #1A1A1A;
                  background-color: #1A1A1A;
              }
              .button:hover{
                  -fx-border-color: #222222;
                  border-color: #222222;
                  -fx-background-color: #3E3E40;
                  background-color: #3E3E40;
                  -fx-cursor: HAND;
                  cursor: HAND;
               }
              .button:pressed{
                  -fx-border-color: #4e4e4e;
                  border-color: #4e4e4e;
                  -fx-background-color: #4e4e4e;
                  background-color: #4e4e4e;
                  -fx-cursor: HAND;
                  cursor: HAND;
              }
              
              
              /* CheckBox */
              .check-box{
                  -fx-background-radius: 0;
                  -fx-border-width: 0;
                  border-width: 0;
                  -fx-insets: 0 0 0 -1;
                  -fx-background-color: transparent;
                  background-color: transparent;
                  -fx-text-fill: #AFB1B3;
                  -fx-min-height: 25;
                  min-height: 25;
                  -fx-pref-height: 25;
              }
              .check-box > .box{
                  -fx-background-radius: 0;
                  -fx-border-width: 1;
                  border-width: 1;
                  -fx-border-color: #222222;
                  border-color: #222222;
                  -fx-insets: 0 0 -1 0;
                  -fx-background-color: #1A1A1A;
                  background-color: #1A1A1A;
              }
              .check-box:focused > .box{
                  -fx-border-color: #4e4e4e;
                  border-color: #4e4e4e;
              }
              .check-box:hover > .box{
                  -fx-background-color: #3E3E40;
                  background-color: #3E3E40;
                  -fx-background-insets: 0 0 1 0;
                  -fx-cursor: HAND;
                  cursor: HAND;
              }
              .check-box > .box > .mark{
                  -fx-background-color: transparent;
                  background-color: transparent;
                  -fx-shape: "M 9.97498 1.22334L 4.6983 9.09834 L 4.52164 9.09834 L 0 5.19331 L 1.27664 3.52165 L 4.255 6.08833 L 8.33331 1.52588e-005 L 9.97498 1.22334 Z " ;
              }
              .check-box:selected > .box > .mark{
                   -fx-background-color: #AFB1B3;
                   background-color: #AFB1B3;
              }
              .check-box:pressed > .box > .mark{
                   -fx-background-color: #4e4e4e;
                   background-color: #4e4e4e;
              }
              
              
              /* TextArea */
              .text-area{
                  -fx-background-radius: 0;
                  -fx-border-width: 1;
                  border-width: 1;
                  -fx-border-color: #222222;
                  border-color: #222222;
                  -fx-text-fill: #AFB1B3;
                  -fx-highlight-fill: #4e4e4e;
                  -fx-background-color: #1A1A1A;
                  background-color: #1A1A1A;
              }
              .text-area:hover{
                  -fx-border-color: #3E3E40;
                  border-color: #3E3E40;
              }
              .text-area:focused{
                  -fx-border-color: #4e4e4e;
                  border-color: #4e4e4e;
                  -fx-background-color: #1A1A1A;
                  background-color: #1A1A1A;
              }
              .text-area .content{
                  -fx-background-radius: 0;
                  -fx-border-width: 0;
                  border-width: 0;
                  -fx-background-radius: 0;
                  -fx-background-color: #1A1A1A;
                  background-color: #1A1A1A;
              }
              .text-area .scroll-bar{
                  -fx-background-insets: 0 -0.5 0.5 0;
              }
              .text-area .scroll-bar .thumb{
                  -fx-background-insets: 0 2 0 2;
              }
              
              
              /* ScrollBar */
              .scroll-bar{
                  -fx-background-radius: 0;
                  -fx-background-color: #2A2A2C;
                  background-color: #2A2A2C;
              }
              .scroll-bar:vertical{
                  -fx-background-radius: 0;
                  -fx-background-color: #2A2A2C;
                  background-color: #2A2A2C;
              }
              .scroll-bar > .decrement-button{
                  -fx-background-color: #2A2A2C;
                  background-color: #2A2A2C;
                  -fx-background-radius: 0;
                  -fx-border-width: 0;
                  border-width: 0;
                  -fx-cursor: HAND;
                  cursor: HAND;
              }
              .scroll-bar .decrement-button,
              .scroll-bar .increment-button{
                  -fx-cursor: HAND;
                  cursor: HAND;
                  -fx-border-width: 0;
                  border-width: 0;
                  -fx-background-radius: 0;
              }
              .scroll-bar .decrement-button:hover,
              .scroll-bar .increment-button:hover{
                  -fx-background-color: #4e4e4e;
                  background-color: #4e4e4e;
              }
              .scroll-bar:vertical  .increment-arrow,
              .scroll-bar:vertical  .decrement-arrow,
              .scroll-bar:horizontal .increment-arrow,
              .scroll-bar:horizontal .decrement-arrow{
                  -fx-shape : " ";
              }
              .scroll-bar:horizontal .thumb,
              .scroll-bar:vertical .thumb {
                  -fx-background-color: #3D3D3D;
                  background-color: #3D3D3D;
                  -fx-background-radius : 0;
                  -fx-cursor: HAND;
                  cursor: HAND;
              }
              
              
              /* Menu */
              .menu-bar {
                  -fx-background-color: #2D2D30;
                  background-color: #2D2D30;
                  -fx-border-width: 0;
                  border-width: 0;
              }
              .menu-bar .menu-button{
                  -fx-background: #4e4e4e;
                  background: #4e4e4e;
                  -fx-text-fill: #AFB1B3;
                  -fx-min-height: 20;
                  min-height: 20;
                  -fx-pref-height: 20;
                  -fx-padding: 0 5 0 5;
                  padding: 0 5 0 5;
                  -fx-cursor: HAND;
                  cursor: HAND;
              }
              .menu-item{
                  -fx-background-radius: 0;
                  -fx-border-width: 0;
                  border-width: 0;
                  -fx-text-fill: #AFB1B3;
                  -fx-background-color: #1A1A1A;
                  background-color: #1A1A1A;
              }
              .menu-item:hover {
                  -fx-background-color: #3E3E40;
                  background-color: #3E3E40;
                  -fx-cursor: HAND;
                  cursor: HAND;
              }
              .menu-item:pressed {
                  -fx-background-color: #4e4e4e;
                  background-color: #4e4e4e;
                  -fx-cursor: HAND;
                  cursor: HAND;
              }
              .context-menu {
                  -fx-background-color: #1A1A1A;
                  background-color: #1A1A1A;
                  -fx-border-width: 0;
                  border-width: 0;
                  -fx-padding: 0 0 -1 0;
                  padding: 0 0 -1 0;
               }
              
              
              /* ProgressBar */
              .progress-bar{
                  -fx-background-color: transparent;
                  background-color: transparent;
              }
              .progress-bar .bar{
                  -fx-background-color: #4e4e4e;
                  background-color: #4e4e4e;
                  -fx-background-radius: 0;
                  -fx-padding: 3;
                  padding: 3;
                  -fx-background-insets: 0;
              }
              .progress-bar .track {
                  -fx-background-color: transparent;
                  background-color: transparent;
                  -fx-background-radius: 0;
              }
              
              
              /* Slider */
              .slider{
                  -fx-text-fill: #AFB1B3;
              }
              .slider .track{
                  -fx-background-color: #1A1A1A;
                  background-color: #1A1A1A;
                  -fx-background-radius: 0;
                  -fx-background-insets: 2 -2 1 0;
              }
              .slider .thumb{
                  -fx-background-color: #B1B1B1;
                  background-color: #B1B1B1;
                  -fx-background-radius: 6;
                  -fx-background-insets: 1 0 1 2;
                  -fx-border-radius: 6;
                  border-radius: 6;
                  -fx-border-insets: 1 0 1 2;
                  -fx-pref-width: 12;
                  -fx-pref-height: 12;
                  -fx-cursor: HAND;
                  cursor: HAND;
              }
              .slider:focused .thumb{
                  -fx-border-color: #4e4e4e;
                  border-color: #4e4e4e;
              }
              .slider .thumb:hover{
                  -fx-background-color: #3E3E40;
                  background-color: #3E3E40;
              }
              .slider .thumb:pressed{
                  -fx-background-color: #4e4e4e;
                  background-color: #4e4e4e;
              }
              
              
              /* TreeView */
              .tree-view .scroll-bar{
                  -fx-background-insets: 0 -1 0 0;
              }
              .tree-view .scroll-bar .thumb{
                  -fx-background-insets: 0 3 0 2;
              }
              .tree-view {
                  -fx-background-insets: 2;
                  -fx-background-color: #1A1A1A;
                  background-color: #1A1A1A;
                  -fx-border-width: 1;
                  border-width: 1;
                  -fx-border-color: #222222;
                  border-color: #222222;
                  -fx-padding: 0 0 1 0;
                  padding: 0 0 1 0;
              }
              .tree-view:hover{
                  -fx-border-color: #3E3E40;
                  border-color: #3E3E40;
              }
              .tree-view:focused {
                  -fx-border-color: #4e4e4e;
                  border-color: #4e4e4e;
              }
              .tree-cell{
                  -fx-background-color: #1A1A1A;
                  background-color: #1A1A1A;
                  -fx-text-fill: #AFB1B3;
                  -fx-border-width: 0;
                  border-width: 0;
                  -fx-pref-width: 20;
                  -fx-pref-height: 20;
                  -fx-padding: 1;
                  padding: 1;
                  -fx-cursor: HAND;
                  cursor: HAND;
              }
              .tree-cell:hover{
                  -fx-background-color: #3E3E40;
                  background-color: #3E3E40;
              }
              .tree-cell:selected{
                  -fx-background-color: #3E3E40;
                  background-color: #3E3E40;
              }
              .tree-cell:pressed{
                  -fx-background-color: #4e4e4e;
                  background-color: #4e4e4e;
              }
              .tree-cell > .tree-disclosure-node > .arrow
              {
                  -fx-background-color: #555555;
                  background-color: #555555;
              }
              .tree-cell:hover > .tree-disclosure-node > .arrow
              {
                  -fx-background-color: #4e4e4e;
                  background-color: #4e4e4e;
              }
              
              
              /* Tab */
              .tab-pane{
                  -fx-background-color: #4e4e4e;
                  background-color: #4e4e4e;
                  -fx-border-width: 1;
                  border-width: 1;
                  -fx-border-color: transparent;
                  border-color: transparent;
                  -fx-background-insets: 25.5 0 0 0;
              }
              .tab{
                  -fx-background-radius: 0;
                  -fx-border-width: 0;
                  border-width: 0;
                  -fx-background-color: #3E3E40;
                  background-color: #3E3E40;
                  -fx-text-fill: #AFB1B3;
                  -fx-min-height: 20;
                  min-height: 20;
                  -fx-pref-height: 20;
              }
              .tab:selected{
                  -fx-background-color: #4e4e4e;
                  background-color: #4e4e4e;
              }
              .tab-pane:focused > .tab-header-area > .headers-region > .tab:selected .focus-indicator {
                  -fx-border-color: transparent;
                  border-color: transparent;
              }
              .tab .tab-close-button {
                  -fx-shape: "M19,6.41L17.59,5 12,10.59 6.41,5 5,6.41 10.59,12 5,17.59 6.41,19 12,13.41 17.59,19 19,17.59 13.41,12z";
                  -fx-background-color: #AFB1B3;
                  background-color: #AFB1B3;
                  -fx-background-insets: 2;
                  -fx-margin: 0 0 -5 2;
                  margin: 0 0 -5 2;
                  -fx-cursor: HAND;
                  cursor: HAND;
              }
              .tab .tab-label {
                  -fx-text-fill: #AFB1B3;
              }
              .tab-pane .tab-header-area .tab-header-background {
                  -fx-opacity: 0.0;
                  opacity: 0.0;
              }
              
              
              /* TitledPane */
              .titled-pane{
                  -fx-text-fill: #AFB1B3;
                  -fx-label-padding: 0 0 -7 0;
                  -fx-background-color: transparent;
                  background-color: transparent;
              }
              .titled-pane .title{
                  -fx-background-color: #3E3E42;
                  background-color: #3E3E42;
                  -fx-background-radius: 0;
                  -fx-border-width: 0;
                  border-width: 0;
                  -fx-pref-height: 14;
              }
              .titled-pane .content{
                  -fx-background-color: #2D2D30;
                  background-color: #2D2D30;
                  -fx-border-color: #3E3E42;
                  border-color: #3E3E42;
                  -fx-border-radius: 0;
                  border-radius: 0;
                  -fx-border-width: 3 2 2 2;
                  border-width: 3 2 2 2;
              }
              
              
              /* TableView */
              .table-view{
                  -fx-border-width: 1;
                  border-width: 1;
                  -fx-border-radius: 0;
                  border-radius: 0;
                  -fx-border-color: #222222;
                  border-color: #222222;
                  -fx-background-radius: 0;
                  -fx-background-color: #1A1A1A;
                  background-color: #1A1A1A;
                  -fx-table-cell-border-color: #222222;
              }
              .table-view:focused{
                  -fx-border-width: 1;
                  border-width: 1;
                  -fx-border-radius: 0;
                  border-radius: 0;
                  -fx-border-color: #4e4e4e;
                  border-color: #4e4e4e;
                  -fx-background-insets: 1;
                  -fx-background-radius: 0;
                  -fx-background-color: #1A1A1A;
                  background-color: #1A1A1A;
              }
              .table-view .column-header-background{
                  -fx-border-width: 0 0 1 0;
                  border-width: 0 0 1 0;
                  -fx-border-color: #222222;
                  border-color: #222222;
                  -fx-background-radius: 0;
                  -fx-background-color: #1A1A1A;
                  background-color: #1A1A1A;
                  -fx-text-fill: #AFB1B3;
              }
              .table-view .column-header {
                  -fx-background-color: transparent;
                  background-color: transparent;
                  -fx-border-color: #222222;
                  border-color: #222222;
              }
              .table-view .column-resize-line {
                  -fx-background-color: #4e4e4e;
                  background-color: #4e4e4e;
                  -fx-pref-width: 1;
              }
              .table-row-cell{
                  -fx-background-color: #1A1A1A;
                  background-color: #1A1A1A;
                  -fx-text-background-color: #AFB1B3;
                  -fx-margin: 1;
                  margin: 1;
              }
              .table-row-cell:hover {
                  -fx-background-color: #3E3E40;
                  background-color: #3E3E40;
                  -fx-background-insets: 0;
                  -fx-background-radius: 0;
              }
              .table-row-cell:selected {
                  -fx-table-cell-border-color: #222222;
                  -fx-background-color: #3E3E40;
                  background-color: #3E3E40;
                  -fx-background-insets: 0;
                  -fx-background-radius: 0;
              }
              .table-row-cell:pressed {
                  -fx-background-color: #4e4e4e;
                  background-color: #4e4e4e;
                  -fx-background-insets: 0;
                  -fx-background-radius: 0;
              }
              .table-view .column-header .label {
                  -fx-alignment: center-left;
                  -fx-cursor: HAND;
                  cursor: HAND;
              }
              .table-view .filler {
                  -fx-background-color: transparent;
                  background-color: transparent;
              }
              .table-view .column-drag-header{
                  -fx-background-color: #4e4e4e;
                  background-color: #4e4e4e;
              }
              
              
              /* Tooltip */
              .tooltip{
                  -fx-background-radius: 0;
                  -fx-border-radius: 0;
                  border-radius: 0;
                  -fx-text-fill: #AFB1B3;
                  -fx-padding: 5;
                  padding: 5;
              }
              
            """;
}
