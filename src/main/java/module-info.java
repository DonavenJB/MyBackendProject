module com.bruce.backend {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.sql;
    requires java.logging;
    requires org.slf4j;

    opens com.bruce.backend to javafx.fxml;
    exports com.bruce.backend;
}
