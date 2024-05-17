module com.progtech.etelrendelesapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires java.sql;
    requires spring.security.crypto;
    requires java.desktop;

    opens com.progtech.etelrendelesapp to javafx.fxml;
    exports com.progtech.etelrendelesapp;
    exports com.progtech.etelrendelesapp.controller;
    opens com.progtech.etelrendelesapp.controller to javafx.fxml;

    opens com.progtech.etelrendelesapp.model to java.base;
    exports com.progtech.etelrendelesapp.model;
}