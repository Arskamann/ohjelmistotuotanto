package softa;

import com.mysql.cj.jdbc.exceptions.MysqlDataTruncation;
import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.util.Duration;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;


public class VaraustenHallinta extends Menu implements Initializable {

    //<editor-fold desc="FXML oliot">
    @FXML
    TextField varausID;
    @FXML
    TextField asiakasID;
    @FXML
    TextField mokkiID;
    @FXML
    Pane list;
    @FXML
    Pane varaus;
    @FXML
    Label hakuTulos;
    @FXML
    Button tallenna;
    @FXML
    private ListView<Button> lista;
    @FXML
    private TextField haku;
    @FXML
    Button paivita;
    @FXML
    ListView palveluList;
    @FXML
    Pane palvelu;
    @FXML
    TextField palveluIDText;
    @FXML
    TextField palvelunNimiText;
    @FXML
    TextField lkmText;
    @FXML
    TextField varausIDText;
    @FXML
    CheckBox naytaTulevat;
    @FXML
    CheckBox naytaVanhat;
    @FXML
    DatePicker vahvistusPVM;
    @FXML
    DatePicker varattuPVM;
    @FXML
    DatePicker varauksenAlkuPVM;
    @FXML
    DatePicker varauksenLoppuPVM;
    @FXML
    Label palveluStatus;
    //</editor-fold>

    static int iddd = 0;



    public void menu(ActionEvent event) throws IOException { //t‰ll‰  toiminnolla p‰‰see takasin p‰‰valikkoon. T‰t‰ kutsutaan uusivaraus.xml tiedostossa
        changeScene("Menu.fxml");
    }
    public void listaHaku() {    //k‰ytet‰‰n "Hae" nappulan painamisen yhteydess‰
        ResultSet resultSet = null;
        String hakutext=haku.getText();
        String kokonimi = "N/a";
        String aID;
        String vID;
        String mID;
        int increment = 0;
        PreparedStatement preparedStatement;
        lista.getItems().clear();
        if (!haku.getText().isBlank()) {
            try {
                connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+kanta, nimi, salis);

                //Tekee haun tietokantaan joka kerta kun kirjain kirjotetaan hakukentt‰‰n. Teen joskus pelk‰ll‰ resultsetill‰ ett‰ ei tarvitse hakea tietokannasta joka kerta
                preparedStatement = connection.prepareStatement(

                        "select asiakas_id from asiakas where etunimi like '"+'%' +hakutext+ '%' +"' or sukunimi like "+" '"+'%' +hakutext+ '%' +"'"
                );

                ResultSet aIDResult = preparedStatement.executeQuery(); //Pelkk‰ asiakasID resultset

                aIDResult.next();
                aID = aIDResult.getString("asiakas_id");

                //Hakuja valittujen kriteereiden perusteella. P‰ivittyy myˆs klikatessa filttereist‰
                if (naytaTulevat.isSelected() && naytaVanhat.isSelected()) {
                    preparedStatement = connection.prepareStatement("select * from varaus where asiakas_id = " + "'" + aID + "'");
                    resultSet = preparedStatement.executeQuery();
                }
                else if (naytaVanhat.isSelected()) {
                    preparedStatement = connection.prepareStatement("select * from varaus where asiakas_id = " + "'" + aID + "' and varattu_alkupvm <= current_date()");
                    resultSet = preparedStatement.executeQuery();
                }
                else if (naytaTulevat.isSelected()) {
                    preparedStatement = connection.prepareStatement("select * from varaus where asiakas_id = " + "'" + aID + "' and varattu_alkupvm >= current_date()");
                    resultSet = preparedStatement.executeQuery();
                }
                preparedStatement = connection.prepareStatement("select asiakas_id, etunimi, sukunimi from asiakas");
                ResultSet nimet = preparedStatement.executeQuery();
                try {
                    //tekee loopissa buttonit kaikille lˆydetyille tiedoille. Buttoneista p‰‰see k‰sittelem‰‰n kyseisi‰ tietoja.
                    while(resultSet.next()){
                        while (nimet.next()) {
                            if (aID.equals(nimet.getString("asiakas_id"))) {
                                kokonimi = nimet.getString("etunimi") + " " + nimet.getString("sukunimi");
                                System.out.println("nimi onnistu");
                                System.out.println(kokonimi);
                            }
                        }
                        increment++;
                        vID = resultSet.getString("varaus_id");
                        aID = resultSet.getString("asiakas_ID");
                        mID = resultSet.getString("mokki_mokki_id");

                        Button x=new Button(kokonimi + " Varaus: " + increment + " || " +  "VarausID: " + vID + " " + "AsiakasID: " + aID + " " + "MokkiID: " + mID);
                        x.setAccessibleText(vID);

                        x.setOnAction((event) -> {
                            iddd = Integer.parseInt(x.getAccessibleText());
                            try {
                                changeScene("VaraustenHallinta_Varaus.fxml");
                            } catch (IOException throwables) {
                                throwables.printStackTrace();
                            }
                        });

                        lista.getItems().add(x);

                    }
                } catch (NullPointerException e) {
                    lista.getItems().clear();
                }
                //Pieni animaatio hakutulokselle
                hakuTulos.setText("Haulla " + increment + " tulosta");
                hakuTulos.setVisible(true);
                Timeline timeline = new Timeline();
                timeline.getKeyFrames().add(
                        new KeyFrame(Duration.millis(3000),
                                new KeyValue(hakuTulos.visibleProperty(), false)));
                timeline.play();
                System.out.println("Tiedot saatu!");


            } catch (SQLException e) {
                if(e.getMessage().equals("Illegal operation on empty result set.")) {
                    hakuTulos.setText("Haulla ei tuloksia");
                    hakuTulos.setVisible(true);
                    Timeline timeline = new Timeline();
                    timeline.getKeyFrames().add(
                            new KeyFrame(Duration.millis(3000),
                                    new KeyValue(hakuTulos.visibleProperty(), false)));
                    timeline.play();
                }
                else {
                    System.out.println("Error while connecting to the database");
                    e.printStackTrace();
                }
            }
        } else if (naytaTulevat.isSelected() || naytaVanhat.isSelected()){
            listapaivitys();
        }


    }
    public void listapaivitys(){    //k‰ytet‰‰n "N‰yt‰ kaikki" nappulan painamisen yhteydess‰ ja kun siirrytt‰‰n varaustenhallinta ikkunaan
        String etunimi;
        String sukunimi;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+kanta, nimi, salis);
            lista.getItems().clear();
            PreparedStatement preparedStatement;
            ResultSet resultSet;
            String sql = "select * from varaus";

            if (naytaTulevat.isSelected() && naytaVanhat.isSelected()) {
                sql = "select * from varaus";
            }
            else if (naytaVanhat.isSelected()) {
                sql = "select * from varaus where varattu_alkupvm <= current_date()";
            }
            else if (naytaTulevat.isSelected()) {
                sql = "select * from varaus where varattu_alkupvm >= current_date()";
            }

            preparedStatement = connection.prepareStatement("select asiakas_id, etunimi, sukunimi from asiakas");

            ResultSet nimet = preparedStatement.executeQuery();
            nimet.next();

            preparedStatement=connection.prepareStatement(sql);

            resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){ //tekee nappulat tietokannasta haetuilla tiedoilla

                String id=resultSet.getString("varaus_id");
                String asiakasID =resultSet.getString("asiakas_id");
                String mokkiID =resultSet.getString("mokki_mokki_id");

                if(!asiakasID.equals(nimet.getString("asiakas_id"))) { //pidet‰‰n sama nimi nappuloissa jos varaus on samalta henkilˆlt‰
                    nimet.next();
                }

                etunimi = nimet.getString("etunimi");
                sukunimi = nimet.getString("sukunimi");

                Button x = new Button(etunimi +" "+ sukunimi);

                x.setAccessibleText(id);

                x.setOnAction((event) -> {
                    System.out.println(x.getText());

                    iddd=Integer.parseInt(x.getAccessibleText());

                    //Siirryt‰‰n Buttonin kautta seuraavaan ikkunaan
                    try {
                        changeScene("VaraustenHallinta_Varaus.fxml");
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                });






                lista.getItems().add(x);

            }

        } catch (SQLException e) {
            System.out.println("Error while connecting to the database");
        } catch (NullPointerException e) {
            if(connection == null) {
                hakuTulos.setText("Ei yhteytt‰ tietokantaan");
                hakuTulos.setVisible(true);
                Timeline timeline = new Timeline();
                timeline.getKeyFrames().add(
                        new KeyFrame(Duration.millis(3000),
                                new KeyValue(hakuTulos.visibleProperty(), false)));
                timeline.play();
            }
        }


    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        listapaivitys();
    }

}
