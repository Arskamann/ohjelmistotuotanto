package softa;

import com.mysql.cj.jdbc.exceptions.MysqlDataTruncation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ResourceBundle;

public class VaraustenHallintaPalvelut extends VaraustenHallintaVaraus implements Initializable {
    //<editor-fold desc="FXML oliot">
    @FXML
    Pane palvelu;
    @FXML
    Button lisaaPalvelu;
    @FXML
    TextField palveluIDText;
    @FXML
    TextField palvelunNimiText;
    @FXML
    TextField lkmText;
    @FXML
    TextField varausIDText;
    @FXML
    Label palveluStatus;
    @FXML
    ComboBox palveluNimiDrop;
    //</editor-fold>
    public void lisaaPalvelu() throws SQLException {
        int palvelu_id = 0;
        int lkm = -1;
        try {
            palvelu_id = Integer.parseInt(palveluIDText.getText());
            if (lkmText.getText().isBlank()) {
                lkm = 1;
            }
            else lkm = Integer.parseInt(lkmText.getText());
        } catch (IllegalArgumentException e) {

        } catch (NullPointerException e) {
            System.out.println("Tyhji‰ kentti‰");
        }
        try {
            PreparedStatement preparedStatement2=connection.prepareStatement(
                    "insert into varauksen_palvelut set varaus_id ='"+Integer.parseInt(varausIDText.getText())+"', palvelu_id='"+palvelu_id+"',"+"lkm='"+lkm+"'");
            preparedStatement2.executeUpdate();
            preparedStatement2 = connection.prepareStatement("Insert into palvelu set nimi = '"+palvelunNimiText.getText()+"'");

            palveluStatus.setText("Lis‰ys onnistui");
            palveluStatus.setVisible(true);
            palveluStatus.setStyle("-fx-background-color: #00ff00");
            Timeline timeline = new Timeline();
            timeline.getKeyFrames().add(
                    new KeyFrame(Duration.millis(3000),
                            new KeyValue(palveluStatus.visibleProperty(), false)));
            timeline.play();

            System.out.println("Tiedot tallennettu");

        } catch (SQLIntegrityConstraintViolationException throwables) {
            String palveluTyhj‰ = "Cannot add or update a child row: a foreign key constraint fails (`vn`.`varauksen_palvelut`, CONSTRAINT `fk_palvelu` FOREIGN KEY (`palvelu_id`) REFERENCES `palvelu` (`palvelu_id`) ON DELETE RESTRICT ON UPDATE RESTRICT)";

            if(throwables.getMessage().equals(palveluTyhj‰)) {
                palveluStatus.setText("Ei valittua palvelua!");
                palveluStatus.setVisible(true);
                Timeline timeline = new Timeline();
                timeline.getKeyFrames().add(
                        new KeyFrame(Duration.millis(3000),
                                new KeyValue(palveluStatus.visibleProperty(), false)));
                timeline.play();
            } else {
                palveluStatus.setText("Palvelu on jo olemassa valitussa varauksessa!");
                palveluStatus.setVisible(true);
                Timeline timeline = new Timeline();
                timeline.getKeyFrames().add(
                        new KeyFrame(Duration.millis(3000),
                                new KeyValue(palveluStatus.visibleProperty(), false)));
                timeline.play();
            }
            throwables.printStackTrace();
        } catch (NumberFormatException e) {
            e.printStackTrace();
        } catch (MysqlDataTruncation e) {
            palveluStatus.setText("Palvelua ei lˆytynyt tietokannasta. Lis‰‰ palvelu ensin!");
            palveluStatus.setVisible(true);
            Timeline timeline = new Timeline();
            timeline.getKeyFrames().add(
                    new KeyFrame(Duration.millis(3000),
                            new KeyValue(palveluStatus.visibleProperty(), false)));
        }
    }
    public void paivitaPalvelut() throws SQLException {
        ResultSet rs = connection.prepareStatement("select nimi from palvelu").executeQuery();
        while (rs.next()) {
            palveluNimiDrop.getItems().add(rs.getString("nimi"));
        }
    }
    public void palveluIDOnAction() {
        try{
            palveluIDText.clear();
            PreparedStatement ps = connection.prepareStatement("select palvelu_id from palvelu where nimi ='"+palveluNimiDrop.getSelectionModel().getSelectedItem().toString()+"' ");
            ResultSet rs = ps.executeQuery();
            rs.next();
            palveluIDText.setText(rs.getString("palvelu_id"));
        } catch (SQLException e) {

        }
    }
    public void takaisin(ActionEvent event) throws IOException { //t‰ll‰  toiminnolla p‰‰see takasin p‰‰valikkoon. T‰t‰ kutsutaan uusivaraus.xml tiedostossa
        changeScene("VaraustenHallinta_Varaus.fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            paivitaPalvelut();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        varausIDText.setText(String.valueOf(iddd));
    }
}
