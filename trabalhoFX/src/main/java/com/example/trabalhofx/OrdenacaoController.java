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

    public Button bt_iniciar;
    public Label lb_vet;
    public Label lb_cont;
    public Label lb_linhaFonte;
    public VBox vb_exec;
    private int qtd, menor, maior, x, y, tamanho;
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
        //gerarFonte();
        lb_vet.setLayoutY(y + 10);
        lb_vet.setLayoutX(x - 35);
        lb_cont.setVisible(false);
        botoes = gerarVetorBotoes(x, y, qtd);
        vb_exec.setLayoutX(800);
        vb_exec.setLayoutY(400);
        exibicao.getChildren().addAll(botoes);
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
                Platform.runLater(() -> {
                    fonte[1].setStyle("-fx-background-color: #3392ef");
                    lb_linhaFonte.setText(fonte[1].getText());
                });
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Platform.runLater(()->{
                    fonte[1].setStyle("-fx-background-color: none");
                    fonte[2].setStyle("-fx-background-color: #3392ef");
                    lb_linhaFonte.setText(fonte[2].getText());
                });
                for (int i = 0; i < range; i++) {
                    cont[i].setVisible(true);
                }
                lb_cont.setVisible(true);
                lb_cont.setLayoutY(y + 10);
                lb_cont.setLayoutX(x - 45);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Platform.runLater(()->
                        colocaPosicoes(x, y, range)
                        );

                int pos, valor;
                for (int i = 0; i < qtd; i++) {
                    int finalI = i;
                    Platform.runLater(() ->
                            botoes[finalI].setStyle("-fx-background-color: linear-gradient(to bottom, #8BC34A, #4CAF50);-fx-text-fill: white;-fx-font-size: 14px;-fx-font-weight: bold;-fx-border-color: black;-fx-border-width: 2px;")
                    );
                    pos = Integer.parseInt(botoes[i].getText());
                    pos -= menor;
                    Thread.sleep(tempo);
                    int finalPos = pos;
                    Platform.runLater(() ->
                            cont[finalPos].setStyle("-fx-background-color: linear-gradient(to bottom, #8BC34A, #4CAF50);-fx-text-fill: white;-fx-font-size: 14px;-fx-font-weight: bold;-fx-border-color: black;-fx-border-width: 2px;")
                    );
                    valor = Integer.parseInt(cont[pos].getText());
                    valor++;
                    Thread.sleep(tempo);
                    int finalValor = valor;
                    Platform.runLater(() -> cont[finalPos].setText("" + finalValor));
                    Thread.sleep(tempo);
                    Platform.runLater(() -> {
                        botoes[finalI].setStyle("-fx-background-color: linear-gradient(to bottom, #f2f2f2, #dcdcdc);-fx-text-fill: black;-fx-font-size: 14px;-fx-font-weight: bold;-fx-border-color: black;-fx-border-width: 2px;");
                        cont[finalPos].setStyle("-fx-background-color: linear-gradient(to bottom, #f2f2f2, #dcdcdc);-fx-text-fill: black;-fx-font-size: 14px;-fx-font-weight: bold;-fx-border-color: black;-fx-border-width: 2px;");
                    });
                    Thread.sleep(tempo);
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
        int tempo = 200;
        Task<Void> soma = new Task<>() {
            @Override
            protected Void call() throws Exception {
                for (int i = 1; i < range; i++) {
                    int valorI = i;
                    Platform.runLater(() -> {
                        cont[valorI - 1].setStyle("-fx-background-color: linear-gradient(to bottom, #8BC34A, #4CAF50);-fx-text-fill: white;-fx-font-size: 14px;-fx-font-weight: bold;-fx-border-color: black;-fx-border-width: 2px;");
                        cont[valorI].setStyle("-fx-background-color: linear-gradient(to bottom, #42F5E8, #4257F5);-fx-text-fill: white;-fx-font-size: 14px;-fx-font-weight: bold;-fx-border-color: black;-fx-border-width: 2px;");
                    });
                    int soma = Integer.parseInt(cont[valorI - 1].getText()) + Integer.parseInt(cont[valorI].getText());
                    Thread.sleep(tempo);
                    Platform.runLater(() ->
                            cont[valorI].setText("" + soma)
                    );
                    Thread.sleep(tempo);
                    Platform.runLater(() -> {
                        cont[valorI - 1].setStyle("-fx-background-color: linear-gradient(to bottom, #f2f2f2, #dcdcdc);-fx-text-fill: black;-fx-font-size: 14px;-fx-font-weight: bold;-fx-border-color: black;-fx-border-width: 2px;");
                        cont[valorI].setStyle("-fx-background-color: linear-gradient(to bottom, #f2f2f2, #dcdcdc);-fx-text-fill: black;-fx-font-size: 14px;-fx-font-weight: bold;-fx-border-color: black;-fx-border-width: 2px;");
                    });
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
        setarValorUnico("null", ordenado, qtd);
        colocaPosicoesSemValor(x, y, qtd);
        exibicao.getChildren().addAll(ordenado);
        Task<Void> ordenar = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                for (int i = qtd - 1; i >= 0; i--) {
                    int posCont = Integer.parseInt(botoes[i].getText()) - menor;
                    int pos = Integer.parseInt(cont[posCont].getText()) - 1;
                    int posI = i, posOrdenado = pos;
                    Platform.runLater(() -> {
                        botoes[posI].setStyle("-fx-background-color: linear-gradient(to bottom, #8BC34A, #4CAF50);-fx-text-fill: white;-fx-font-size: 14px;-fx-font-weight: bold;-fx-border-color: black;-fx-border-width: 2px;");
                        cont[posCont].setStyle("-fx-background-color: linear-gradient(to bottom, #42F5E8, #4257F5);-fx-text-fill: white;-fx-font-size: 14px;-fx-font-weight: bold;-fx-border-color: black;-fx-border-width: 2px;");
                        ordenado[posOrdenado].setStyle("-fx-background-color: linear-gradient(to bottom, #F58B1B, #F51B21);-fx-text-fill: white;-fx-font-size: 14px;-fx-font-weight: bold;-fx-border-color: black;-fx-border-width: 2px;");
                    });
                    for (int j = 0; j < 10; j++) {
                        Platform.runLater(() -> botoes[posI].setLayoutY(botoes[posI].getLayoutY() + 5));
                        Platform.runLater(() -> ordenado[posOrdenado].setLayoutY(ordenado[posOrdenado].getLayoutY() - 5));
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    int qtdX = (int) (botoes[posI].getLayoutX() - ordenado[posOrdenado].getLayoutX());
                    int movX = qtdX / 60;
                    for (int j = 0; j < 60 && movX != 0; j++) {
                        Platform.runLater(() -> botoes[posI].setLayoutX(botoes[posI].getLayoutX() - movX));
                        Platform.runLater(() -> ordenado[posOrdenado].setLayoutX(ordenado[posOrdenado].getLayoutX() + movX));
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    for (int j = 0; j < 15; j++) {
                        Platform.runLater(() -> ordenado[posOrdenado].setLayoutY(ordenado[posOrdenado].getLayoutY() - 10));
                        Platform.runLater(() -> botoes[posI].setLayoutY(botoes[posI].getLayoutY() + 10));
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    Platform.runLater(() -> {
                        botoes[posI].setStyle("-fx-background-color: linear-gradient(to bottom, #f2f2f2, #dcdcdc);-fx-text-fill: black;-fx-font-size: 14px;-fx-font-weight: bold;-fx-border-color: black;-fx-border-width: 2px;");
                        ordenado[posOrdenado].setStyle("-fx-background-color: linear-gradient(to bottom, #f2f2f2, #dcdcdc);-fx-text-fill: black;-fx-font-size: 14px;-fx-font-weight: bold;-fx-border-color: black;-fx-border-width: 2px;");
                    });
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    cont[posCont].setText("" + posOrdenado);
                    Platform.runLater(() -> cont[posCont].setStyle("-fx-background-color: linear-gradient(to bottom, #f2f2f2, #dcdcdc);-fx-text-fill: black;-fx-font-size: 14px;-fx-font-weight: bold;-fx-border-color: black;-fx-border-width: 2px;"));
                    //permutação na memória
                    Button aux = botoes[posI];
                    botoes[posI] = ordenado[posOrdenado];
                    ordenado[posOrdenado] = aux;
                }
                return null;
            }
        };
        Thread thread = new Thread(ordenar);
        thread.setDaemon(true);
        thread.start();
    }

    public void gerarFonte() {
        fonte = new Label[18];
        for (int i = 0; i < 18; i++) {
            //fonte[i]= new Label();
        }
        fonte[0].setText("public void counting_sort() {");
        fonte[1].setText("   int range = maior - menor + 1;");
        fonte[2].setText("   int cont[] = new int[range];");
        fonte[3].setText("   for (int i = 0; i < tl; i++) {");
        fonte[4].setText("      posCont=vet[i]-menor;");
        fonte[5].setText("      cont[posCont]++;");
        fonte[6].setText("   }");
        fonte[7].setText("   for (int i = 1; i < tl; i++) {");
        fonte[8].setText("      vet[i]+=vet[i-1];");
        fonte[9].setText("   }");
        fonte[10].setText("   int ordenado[]=new int[tl];");
        fonte[11].setText("   for (int i = tl-1; i >.0; i--) {");
        fonte[12].setText("      posCont=vet[i]-menor;");
        fonte[13].setText("      posOrdenado=cont[posCont]-1;");
        fonte[14].setText("   ordenado[posOrdenado]=vet[i];");
        fonte[15].setText("   }");
        fonte[16].setText("   vet=ordenado");
        fonte[17].setText("}");
        int base = 370;
        for (int i = 0; i < 18; i++) {
            fonte[i].setStyle("-fx-font-size: 12px;-fx-font-weight: bold;");
            fonte[i].setLayoutY(base + i * 17);
            fonte[i].setLayoutX(400);
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
}
