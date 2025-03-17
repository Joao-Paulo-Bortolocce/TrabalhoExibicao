package com.example.trabalhofx;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MenuController implements Initializable {

    @FXML
    private Label lb_maior;

    @FXML
    private Label lb_menor;

    @FXML
    private Label lb_qtd;

    @FXML
    private Slider sl_qtd;

    @FXML
    private Spinner<Integer> sp_maior;

    @FXML
    private Spinner<Integer> sp_menor;

    public static int menorRange,maiorRange,qtd;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        sp_menor.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 30, 0));
        sp_maior.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 30, 30));
        sl_qtd.setValue(30);
        onAlterar(null);
        menorRange=0;
        maiorRange=30;
        qtd=15;
    }

    @FXML
    void onAlterar(MouseEvent event) {
        lb_qtd.setText(""+(int)sl_qtd.getValue());
    }


    public void onAlterarRangeMenor(MouseEvent mouseEvent) {
        int menor=sp_menor.getValue();
        int maior= sp_maior.getValue();
        if(menor>maior)
            sp_maior.getValueFactory().setValue(sp_menor.getValue());
    }

    public void onAlterarRangeMaior(MouseEvent mouseEvent)  {
        int menor=sp_menor.getValue();
        int maior= sp_maior.getValue();
        if(maior<menor)
            sp_menor.getValueFactory().setValue(sp_maior.getValue());
    }

    @FXML
    void onIniciar(ActionEvent event) throws IOException {
        menorRange=sp_menor.getValue();
        maiorRange=sp_maior.getValue();
        qtd=(int)sl_qtd.getValue();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("ordenacao-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setTitle("Ordenando");
        stage.setMaximized(true);
        stage.setScene(scene);
        stage.show();
        sp_menor.getScene().getWindow().hide();
    }

}
