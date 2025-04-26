module com.example.trabalhofx {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.trabalhofx to javafx.fxml;
    exports com.example.trabalhofx;
}