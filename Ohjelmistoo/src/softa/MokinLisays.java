package softa;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

public class MokinLisays extends MokkienHallinta {

    @FXML
    private Pane hakutulos;

    @FXML
    private TextField mokkiID;

    @FXML
    private TextField mokkinimi;

    @FXML
    private TextField osoite;

    @FXML
    private TextField postinro;

    @FXML
    private TextField toimipaikka;

    @FXML
    private TextField hlo;

    @FXML
    private TextField kuvaus;

    @FXML
    private Label id;

    @FXML
    private Button takaisin;

    @FXML
    private ChoiceBox<?> toimialue;

    @FXML
    private ChoiceBox<?> mokkivarustelu;

    public void takaisinMokkeihin(ActionEvent event) throws IOException
    {
        changeScene("MokkienHallinta.fxml");
    }

    public void tallenna() {
        System.out.println("Tästä pääsee tallentamaan");
    }

    
    }



