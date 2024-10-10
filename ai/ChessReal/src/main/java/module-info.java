module com.example.chessreal {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.chess to javafx.fxml;
    exports com.chess;
}