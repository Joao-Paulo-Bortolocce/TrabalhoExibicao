package com.example.trabalhofx;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;

import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

public class OrdenacaoController implements Initializable {

    public Button bt_iniciar;
    public Label lb_vet;
    public Label lb_cont;
    private int qtd, menor, maior, x, y, tamanho;
    //    private int vet[];
    private Button botoes[];

    @FXML
    AnchorPane exibicao;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        menor = MenuController.menorRange;
        maior = MenuController.maiorRange;
        qtd = MenuController.qtd;
        tamanho = 40;
        x = 50;
        y = 100;
        exibicao.getChildren().clear();
        lb_vet.setLayoutY(y + 10);
        lb_vet.setLayoutX(x - 35);
        lb_cont.setVisible(false);
//        vet= new int[qtd];
        botoes = gerarVetorBotoes(x, y, qtd);
        exibicao.getChildren().addAll(botoes);
        colocaPosicoes(x,y,qtd);
    }

    private int geraValor() {
        return new Random().nextInt(menor, maior + 1);
    }

    private void colocaPosicoes(int x, int y,int qtd) {
        int posY = y + tamanho + 5;
        x+=10;
        for (int i = 0; i < qtd; i++,x+=50) {
            Label lb= new Label();
            lb.setText(""+i+"("+(i+menor)+")");
            lb.setLayoutX(x);
            lb.setLayoutY(posY);
            exibicao.getChildren().add(lb);
        }
    }

    public void setarValorUnico(String valor,Button botoes[], int tl){
        for (int i = 0; i < tl; i++) {
            botoes[i].setText(valor);
        }
    }

    public void onIniciar(ActionEvent actionEvent) {
        int range = maior - menor + 1;
        y += 100;
        Button cont[] = gerarVetorBotoes(x, y, range);
        setarValorUnico("0",cont,range);
        colocaPosicoes(x,y,range);
        lb_cont.setVisible(true);
        lb_cont.setLayoutY(y + 10);
        lb_cont.setLayoutX(x - 45);
        exibicao.getChildren().addAll(cont);
        cont = realizarContagem(cont);
    }

    private Button[] realizarContagem(Button[] cont) {
        int tempo=300;
        Task<Button[]> task = new Task<>() {
            @Override
            protected Button[] call() throws Exception {
                int pos, valor;
                for (int i = 0; i < qtd; i++) {
                    int finalI = i;
                    Platform.runLater(() ->
                            botoes[finalI].setStyle("-fx-background-color: linear-gradient(to bottom, #8BC34A, #4CAF50);-fx-text-fill: white;-fx-font-size: 14px;-fx-font-weight: bold;")
                    );

                    pos = Integer.parseInt(botoes[i].getText());
                    pos -= menor;

                    Thread.sleep(tempo);

                    int finalPos = pos;
                    Platform.runLater(() ->
                            cont[finalPos].setStyle("-fx-background-color: linear-gradient(to bottom, #8BC34A, #4CAF50);-fx-text-fill: white;-fx-font-size: 14px;-fx-font-weight: bold;")
                    );

                    valor = Integer.parseInt(cont[pos].getText());
                    valor++;

                    Thread.sleep(tempo);

                    int finalValor = valor;
                    Platform.runLater(() -> cont[finalPos].setText("" + finalValor));

                    Thread.sleep(tempo);

                    Platform.runLater(() -> {
                        botoes[finalI].setStyle("-fx-background-color: linear-gradient(to bottom, #f2f2f2, #dcdcdc);-fx-text-fill: black;-fx-font-size: 14px;-fx-font-weight: bold;");
                        cont[finalPos].setStyle("-fx-background-color: linear-gradient(to bottom, #f2f2f2, #dcdcdc);-fx-text-fill: black;-fx-font-size: 14px;-fx-font-weight: bold;");
                    });

                    Thread.sleep(tempo);
                }
                return cont;
            }
        };

        // Cria e inicia a thread para rodar a contagem
        Thread thread = new Thread(task);
        thread.setDaemon(true); // Faz a thread fechar junto com a aplicação
        thread.start();

        return cont; // Retorna o array imediatamente (antes da animação terminar)
    }




//    public int[] gerarVetor(){
//        int vet[] = new int[qtd];
//        for (int i = 0; i < qtd; i++,x+=50)
//            vet[i]=geraValor();
//        return vet;
//    }

    public Button[] gerarVetorBotoes(int x, int y, int qtd) {
        Button botoes[] = new Button[qtd];
        for (int i = 0; i < qtd; i++, x += 50) {
            botoes[i] = new Button("" + geraValor());
            botoes[i].setLayoutX(x);
            botoes[i].setLayoutY(y);
            botoes[i].setMinHeight(tamanho);
            botoes[i].setMinWidth(tamanho);
            botoes[i].setStyle("-fx-background-color: linear-gradient(to bottom, #f2f2f2, #dcdcdc);-fx-text-fill: black;-fx-font-size: 14px;-fx-font-weight: bold;");
            botoes[i].setFont(new Font(14));
        }
        return botoes;
    }

//    public void move_botoes()
//    {
//        Task<Void> task = new Task<Void>(){
//            @Override
//            protected Void call() {
////permutação na tela
//                for (int i = 0; i < 10; i++) {
//                    Platform.runLater(() -> vet[0].setLayoutY(vet[0].getLayoutY() + 5));
//                    Platform.runLater(() -> vet[1].setLayoutY(vet[1].getLayoutY() - 5));
//                    try {
//                        Thread.sleep(50);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//                for (int i = 0; i < 16; i++) {
//                    Platform.runLater(() -> vet[0].setLayoutX(vet[0].getLayoutX() + 5));
//                    Platform.runLater(() -> vet[1].setLayoutX(vet[1].getLayoutX() - 5));
//                    try {
//                        Thread.sleep(50);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//                for (int i = 0; i < 10; i++) {
//                    Platform.runLater(() -> vet[0].setLayoutY(vet[0].getLayoutY() - 5));
//                    Platform.runLater(() -> vet[1].setLayoutY(vet[1].getLayoutY() + 5));
//                    try {
//                        Thread.sleep(50);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
////permutação na memória
//                Button aux = vet[0];
//                vet[0] = vet[1];
//                vet[1] = aux;
//                return null;
//            }
//        };
//        Thread thread = new Thread(task);
//        thread.start();
//    }
}
