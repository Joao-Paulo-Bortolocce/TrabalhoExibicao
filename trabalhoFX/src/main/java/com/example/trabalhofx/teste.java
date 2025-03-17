package com.example.trabalhofx;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;



public class teste extends Application {
    AnchorPane pane;
    Button botao_inicio;
    private Button vet[];
    public static void main(String[] args)
    {
        launch(args);
    }
    @Override
    public void start(Stage stage) throws Exception
    {
        stage.setTitle("Pesquisa e Ordenacao");
        pane = new AnchorPane();
        botao_inicio = new Button();
        botao_inicio.setLayoutX(10); botao_inicio.setLayoutY(100);
        botao_inicio.setText("Inicia...");
        botao_inicio.setOnAction(e->{ move_botoes();});
        pane.getChildren().add(botao_inicio);
        vet = new Button[2];
        vet[0] = new Button("10");
        vet[0].setLayoutX(100);
        vet[0].setLayoutY(200);
        vet[0].setMinHeight(40); vet[0].setMinWidth(40);
        vet[0].setFont(new Font(14));
        pane.getChildren().add(vet[0]);
        vet[1] = new Button("20");
        vet[1].setLayoutX(180);
        vet[1].setLayoutY(200);
        vet[1].setMinHeight(40); vet[1].setMinWidth(40);
        vet[1].setFont(new Font(14));
        pane.getChildren().add(vet[1]);
        Scene scene = new Scene(pane, 800, 600);
        stage.setScene(scene);
        stage.show();
    }
    public void move_botoes() {
        // Movimento vertical
        Timeline moveDown = new Timeline(
                new KeyFrame(Duration.millis(500),
                        new KeyValue(vet[0].layoutYProperty(), vet[0].getLayoutY() + 50),
                        new KeyValue(vet[1].layoutYProperty(), vet[1].getLayoutY() - 50)
                )
        );

        // Movimento horizontal
        Timeline moveSide = new Timeline(
                new KeyFrame(Duration.millis(800),
                        new KeyValue(vet[0].layoutXProperty(), vet[0].getLayoutX() + 80),
                        new KeyValue(vet[1].layoutXProperty(), vet[1].getLayoutX() - 80)
                )
        );

        // Movimento de volta vertical
        Timeline moveUp = new Timeline(
                new KeyFrame(Duration.millis(500),
                        new KeyValue(vet[0].layoutYProperty(), vet[0].getLayoutY() - 50),
                        new KeyValue(vet[1].layoutYProperty(), vet[1].getLayoutY() + 50)
                )
        );

        // Executar as animações em sequência
        SequentialTransition seq = new SequentialTransition(moveDown, moveSide, moveUp);
        seq.setOnFinished(event -> {
            // Permutação na memória após a animação
            Button aux = vet[0];
            vet[0] = vet[1];
            vet[1] = aux;
        });

        seq.play();
    }
}
