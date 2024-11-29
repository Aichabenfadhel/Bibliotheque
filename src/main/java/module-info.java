module com.example.demo1 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.demo1 to javafx.fxml;
    exports com.example.demo1;
    opens com.example.demo1.Controllers to javafx.fxml;
    exports com.example.demo1.Controllers;
    opens com.example.demo1.Services to javafx.fxml;
    exports com.example.demo1.Services;
    opens com.example.demo1.Models;
    exports com.example.demo1.Models;
    exports com.example.demo1.Util;
    opens com.example.demo1.Util to javafx.fxml;
    opens com.example.demo1.Images to javafx.fxml;
}