package com.example.trabalhofx;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

public class OrdenacaoController implements Initializable {


    String estiloVerde,estiloAzul,estiloCinza,estiloLaranja;
    public Button bt_iniciar;
    public Label lb_vet;
    public Label lb_cont;
    public Label lb_linhaFonte;
    private int qtd, menor, maior, x, y, tamanho,tempoFonte=800;
    private Button botoes[];

    private Label fonte[];
    private boolean autorizado = true;
    @FXML
    AnchorPane exibicao;

    private int range;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        menor = MenuController.menorRange;
        maior = MenuController.maiorRange;
        qtd = MenuController.qtd;
        tamanho = 40;
        x = 60;
        y = 100;
//        exibicao.getChildren().clear();
        gerarFonte();
        lb_vet.setLayoutY(y + 10);
        lb_vet.setLayoutX(x - 35);
        lb_cont.setVisible(false);
        botoes = gerarVetorBotoes(x, y, qtd);
        exibicao.getChildren().addAll(botoes);
        estiloVerde="-fx-background-color: linear-gradient(to bottom, #8BC34A, #4CAF50);-fx-text-fill: white;-fx-font-size: 14px;-fx-font-weight: bold;-fx-border-color: black;-fx-border-width: 2px;";
        estiloAzul="-fx-background-color: linear-gradient(to bottom, #42F5E8, #4257F5);-fx-text-fill: white;-fx-font-size: 14px;-fx-font-weight: bold;-fx-border-color: black;-fx-border-width: 2px;";
        estiloCinza="-fx-background-color: linear-gradient(to bottom, #f2f2f2, #dcdcdc);-fx-text-fill: black;-fx-font-size: 14px;-fx-font-weight: bold;-fx-border-color: black;-fx-border-width: 2px;";
        estiloLaranja="-fx-background-color: linear-gradient(to bottom, #F58B1B, #F51B21);-fx-text-fill: white;-fx-font-size: 14px;-fx-font-weight: bold;-fx-border-color: black;-fx-border-width: 2px;";
        colocaPosicoesSemValor(x, y, qtd);
    }

    public void onIniciar(ActionEvent actionEvent) {
        if (autorizado) {
            autorizado = false;
            range = maior - menor + 1;
            y += 100;
            Button cont[] = gerarVetorBotoes(x, y, range);
            setarValorUnico("0", cont, range);
            for (int i = 0; i < range; i++) {
                cont[i].setVisible(false);
            }
            exibicao.getChildren().addAll(cont);
            realizarContagem(cont);
        }
    }

    private void realizarContagem(Button[] cont) {
        int tempo = 200;
        Task<Void> task = new Task<>() {
            @Override
            protected Void call() throws Exception {
                exibeLinha(1,tempoFonte);
                for (int i = 0; i < range; i++) {
                    cont[i].setVisible(true);
                }

                lb_cont.setVisible(true);
                exibeLinha(2,tempoFonte);
                lb_cont.setLayoutY(y + 10);
                lb_cont.setLayoutX(x - 45);
                espera(500);
                Platform.runLater(()->
                        colocaPosicoes(x, y, range)
                        );
                int pos, valor;
                exibeLinha(3,500);
                for (int i = 0; i < qtd; i++) {
                    int finalI = i;
                    addEstilo(botoes[finalI],estiloVerde);
                    pos = Integer.parseInt(botoes[i].getText());
                    pos -= menor;
                    espera(tempo);
                    int finalPos = pos;
                    exibeLinha(4,tempoFonte);
                    linhaAtual(fonte[5]);
                    addEstilo(cont[finalPos],estiloVerde);
                    valor = Integer.parseInt(cont[pos].getText());
                    valor++;
                    espera(tempo);
                    int finalValor = valor;
                    Platform.runLater(() -> cont[finalPos].setText("" + finalValor));
                    espera(tempo);
                    linhaNormal(fonte[5]);
                    addEstilo(botoes[finalI],estiloCinza);
                    addEstilo(cont[finalPos],estiloCinza);
                    espera(tempo);
                    exibeLinha(6,300);
                    exibeLinha(3,500);
                }
                return null;
            }
        };
        task.setOnSucceeded(event -> somarPosicoes(cont));
        Thread thread = new Thread(task);
        thread.setDaemon(true); // Faz a thread fechar junto com a aplicação
        thread.start();
    }

    private void somarPosicoes(Button[] cont) {
        int tempo = 500;
        Task<Void> soma = new Task<>() {
            @Override
            protected Void call() throws Exception {

                exibeLinha(7,500);
                for (int i = 1; i < range; i++) {
                    int valorI = i;
                    linhaAtual(fonte[8]);
                    addEstilo(cont[valorI-1],estiloVerde);
                    addEstilo(cont[valorI],estiloAzul);
                    int soma = Integer.parseInt(cont[valorI - 1].getText()) + Integer.parseInt(cont[valorI].getText());
                    espera(tempo);
                    Platform.runLater(() ->
                            cont[valorI].setText("" + soma)
                    );
                    espera(tempo);
                    linhaNormal(fonte[8]);
                    addEstilo(cont[valorI-1],estiloCinza);
                    addEstilo(cont[valorI],estiloCinza);
                    exibeLinha(9,300);
                    exibeLinha(7,500);
                }
                return null;
            }
        };
        soma.setOnSucceeded(event -> ordenar(cont));
        Thread thread = new Thread(soma);
        thread.setDaemon(true);
        thread.start();
    }

    private void ordenar(Button[] cont) {

        y += 100;

        Button ordenado[] = gerarVetorBotoes(x, y, qtd);
        setarValorUnico("0", ordenado, qtd);
        colocaPosicoesSemValor(x, y, qtd);
        exibeLinha(10,1000);
        exibicao.getChildren().addAll(ordenado);
        Task<Void> ordenar = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                exibeLinha(11,500);
                for (int i = qtd - 1; i >= 0; i--) {
                    Button aux= new Button();
                    aux.setVisible(false);
                    int posCont = Integer.parseInt(botoes[i].getText()) - menor;
                    int pos = Integer.parseInt(cont[posCont].getText()) - 1;
                    int posI = i, posOrdenado = pos;
                    aux.setLayoutX(botoes[posI].getLayoutX());
                    aux.setLayoutY(botoes[posI].getLayoutY());
                    aux.setText(botoes[posI].getText());
                    aux.setMinHeight(botoes[posI].getPrefHeight());
                    aux.setMinWidth(botoes[posI].getPrefWidth());

                    exibeLinha(12,tempoFonte);
                    exibeLinha(13,tempoFonte);
                    addEstilo(botoes[posI],estiloVerde);
                    addEstilo(aux,estiloVerde);
                    addEstilo(cont[posCont],estiloAzul);
                    addEstilo(ordenado[posOrdenado],estiloLaranja);
                    Platform.runLater(() -> {
                        exibicao.getChildren().add(aux);
                        aux.setVisible(true);
                    });

                    linhaAtual(fonte[14]);
                    for (int j = 0; j < 10; j++) {
                        Platform.runLater(() -> aux.setLayoutY(aux.getLayoutY() + 5));
                        espera(50);
                    }
                    int qtdX = (int) (aux.getLayoutX() - ordenado[posOrdenado].getLayoutX());
                    int movX = qtdX / 60;
                    for (int j = 0; j < 60 && movX != 0; j++) {
                        Platform.runLater(() -> aux.setLayoutX(aux.getLayoutX() - movX));
                        espera(50);
                    }
                    for (int j = 0; j < 15; j++) {
                        Platform.runLater(() -> aux.setLayoutY(aux.getLayoutY() + 10));
                        espera(50);
                    }
                    Platform.runLater(() -> {
                        ordenado[posOrdenado].setText(aux.getText());
                        aux.setVisible(false);
                    });
                    addEstilo(botoes[posI],estiloCinza);
                    addEstilo(ordenado[posOrdenado],estiloCinza);
                    addEstilo(aux,estiloCinza);
                    espera(500);
                    cont[posCont].setText("" + posOrdenado);
                    addEstilo(cont[posCont],estiloCinza);
                    linhaNormal(fonte[14]);
                    exibeLinha(15,200);
                    exibeLinha(11,500);
                }
                return null;
            }
        };
        ordenar.setOnSucceeded(event -> finalizar(cont,ordenado));
        Thread thread = new Thread(ordenar);
        thread.setDaemon(true);
        thread.start();
    }

    private void finalizar(Button[] cont,Button[] ordenado) {

        int tempo = 100;
        Task<Void> finalizar = new Task<>() {
            @Override
            protected Void call() throws Exception {
                linhaNormal(fonte[16]);
                Platform.runLater(()->{

                    for (int i = 0; i < qtd; i++) {
                        botoes[i].setVisible(false);
                    }
                    limpaPosicoes(x,y,qtd);

                });
                int caminho=200/10;
                for (int j = 0; j < caminho; j++) {
                    Platform.runLater(()->{

                        for (int i = 0; i < qtd; i++) {
                            ordenado[i].setLayoutY(ordenado[i].getLayoutY()-10);
                        }

                    });
                    espera(200);
                }
                linhaNormal(fonte[16]);
                exibeLinha(17,500);
                Platform.runLater(()->{
                    for (int i = 0; i < range; i++) {
                        cont[i].setVisible(false);
                    }
                    limpaPosicoes(x,y-100,qtd);
                    lb_cont.setVisible(false);


                });
                for (int i = 0; i < qtd; i++) {
                    addEstilo(ordenado[i],estiloAzul);
                    espera(100);
                }
                return null;
            }
        };
        Thread thread = new Thread(finalizar);
        thread.setDaemon(true);
        thread.start();
    }

    public void gerarFonte() {
        fonte = new Label[18];
        for (int i = 0; i < 18; i++) {
            fonte[i]= new Label();
        }
        fonte[0].setText("public void counting_sort() {");
        fonte[1].setText("      int range = maior - menor + 1;");
        fonte[2].setText("      int cont[] = new int[range];");
        fonte[3].setText("      for (int i = 0; i < tl; i++) {");
        fonte[4].setText("            posCont=vet[i]-menor;");
        fonte[5].setText("            cont[posCont]++;");
        fonte[6].setText("      }");
        fonte[7].setText("      for (int i = 1; i < tl; i++) {");
        fonte[8].setText("            vet[i]+=vet[i-1];");
        fonte[9].setText("      }");
        fonte[10].setText("      int ordenado[]=new int[tl];");
        fonte[11].setText("      for (int i = tl-1; i >0; i--) {");
        fonte[12].setText("            posCont=vet[i]-menor;");
        fonte[13].setText("            posOrdenado=cont[posCont]-1;");
        fonte[14].setText("            ordenado[posOrdenado]=vet[i];");
        fonte[15].setText("      }");
        fonte[16].setText("      vet=ordenado");
        fonte[17].setText("}");
        int base = 450;
        for (int i = 0; i < 18; i++) {
            fonte[i].setStyle("-fx-font-size: 20px;-fx-font-weight: bold;");
            fonte[i].setLayoutY(base + i * 25);
            fonte[i].setLayoutX(500);
            fonte[i].setVisible(true);
        }
        exibicao.getChildren().addAll(fonte);
    }

    public void setarValorUnico(String valor, Button botoes[], int tl) {
        for (int i = 0; i < tl; i++) {
            botoes[i].setText(valor);
        }
    }

    private void colocaPosicoes(int x, int y, int qtd) {
        int posY = y + tamanho + 5;
        x += 10;
        for (int i = 0; i < qtd; i++, x += 60) {
            Label lb = new Label();
            lb.setText("" + i + "(" + (i + menor) + ")");
            lb.setLayoutX(x);
            lb.setLayoutY(posY);
            exibicao.getChildren().add(lb);
        }
    }

    private void limpaPosicoes(int x, int y, int qtd) {
        int posY = y + tamanho + 5;
        x += 10;
        for (int i = 0; i < 20; i++, x += 60) {
            Label lb = new Label();
            lb.setStyle("-fx-background-color: #ffffff; -fx-text-fill: #ffffff;");
            lb.setText("jjjjjjjjjjjjjjjj");
            lb.setLayoutX(x);
            lb.setLayoutY(posY);
            exibicao.getChildren().add(lb);
        }
    }


    private void colocaPosicoesSemValor(int x, int y, int qtd) {
        int posY = y + tamanho + 5;
        x += 10;
        for (int i = 0; i < qtd; i++, x += 60) {
            Label lb = new Label();
            lb.setText("" + i);
            lb.setLayoutX(x);
            lb.setLayoutY(posY);
            exibicao.getChildren().add(lb);
        }
    }

    private int geraValor() {
        return new Random().nextInt(menor, maior + 1);
    }

    public Button[] gerarVetorBotoes(int x, int y, int qtd) {
        Button botoes[] = new Button[qtd];
        for (int i = 0; i < qtd; i++, x += 60) {
            botoes[i] = new Button("" + geraValor());
            botoes[i].setLayoutX(x);
            botoes[i].setLayoutY(y);
            botoes[i].setMinHeight(tamanho);
            botoes[i].setMinWidth(tamanho);
            botoes[i].setStyle("-fx-background-color: linear-gradient(to bottom, #f2f2f2, #dcdcdc);-fx-text-fill: black;-fx-font-size: 14px;-fx-font-weight: bold;-fx-border-color: black;-fx-border-width: 2px;");
            botoes[i].setFont(new Font(14));
        }
        return botoes;
    }

    private void addEstilo(Button bt,String estilo){
        Platform.runLater(() ->
                bt.setStyle(estilo)
        );
    }

    private void linhaAtual(Label lb){
        Platform.runLater(()->lb.setStyle("-fx-font-size: 20px;-fx-font-weight: bold;-fx-background-color: #8BC34A; -fx-text-fill: #ffffff;"));
    }

    private void linhaNormal(Label lb){
        Platform.runLater(()->lb.setStyle("-fx-font-size: 20px;-fx-font-weight: bold;-fx-background-color: #white; -fx-text-fill: black;"));
    }

    private void exibeLinha(int pos,int tempoFonte){
        linhaAtual(fonte[pos]);
        espera(tempoFonte);
        linhaNormal(fonte[pos]);
    }

    private void espera(int tempo){
        try {
            Thread.sleep(tempo);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
