package unoeste.fipp.pedidosfx;

/*
ALUNOS:
João Claudio Martinez da Costa RA: 262319640
Tiago Pantaroto Mena RA: 262318580
 */
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import unoeste.fipp.pedidosfx.db.util.SingletonDB;

import javax.swing.*;
import java.io.IOException;

public class PedidosFX extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(PedidosFX.class.getResource("menu-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Faiska Burguer Menu");

        stage.setScene(scene);
        //stage.setMaximized(true);
        stage.show();
    }

    public static void main(String[] args) {

        if(!SingletonDB.conectar())
        {
            JOptionPane.showMessageDialog(null,"Erro ao conectar: "+
                    SingletonDB.getConexao().getMensagemErro());
            Platform.exit();
        }
        launch();
    }
}