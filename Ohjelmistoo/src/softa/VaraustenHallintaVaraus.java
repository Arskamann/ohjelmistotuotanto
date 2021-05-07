package softa;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

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

public class VaraustenHallintaVaraus extends VaraustenHallinta implements Initializable {
    //<editor-fold desc="FXML oliot">
    @FXML
    TextField varausID;
    @FXML
    TextField asiakasID;
    @FXML
    TextField mokkiID;
    @FXML
    Pane varaus;
    @FXML
    Label hakuTulos;
    @FXML
    Button tallenna;
    @FXML
    private ListView<Button> lista;
    @FXML
    Label d;
    @FXML
    Button poista;
    @FXML
    ListView palveluList;
    @FXML
    DatePicker vahvistusPVM;
    @FXML
    DatePicker varattuPVM;
    @FXML
    DatePicker varauksenAlkuPVM;
    @FXML
    DatePicker varauksenLoppuPVM;
    //</editor-fold>

    public LocalDate muunnaLocalDateksi (Date dateToConvert) { //Muuntaa Daten sopivaan muotoon DatePickeri‰ varten
        return Instant.ofEpochMilli(dateToConvert.getTime())
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }
    public void paivita() throws SQLException, ParseException {

        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+kanta, nimi, salis);
        System.out.println("Tiedot saatu!");

        PreparedStatement preparedStatement=connection.prepareStatement("select * from varaus where varaus_id="+iddd);
        ResultSet resultSet=preparedStatement.executeQuery();
        resultSet.next();

        //Hakee mˆkkiID:n mokki taulusta edellisen tietokantahaun perusteella
        preparedStatement = connection.prepareStatement("select mokkinimi from mokki where mokki_id = '"+resultSet.getString("mokki_mokki_id")+"'");
        ResultSet mokkinimi =  preparedStatement.executeQuery();
        mokkinimi.next();

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        String sVarattuPVM = resultSet.getString("varattu_pvm");
        String sVahvistusPVM = resultSet.getString("vahvistus_pvm");
        String sVarauksenAlkuPVM = resultSet.getString("varattu_alkupvm");
        String sVarauksenLoppuPVM = resultSet.getString("varattu_loppupvm");

        //p‰iv‰m‰‰rien formatointi
        Date dVarattuPVM = formatter.parse(sVarattuPVM);
        Date dVahvistusPVM = formatter.parse(sVahvistusPVM);
        Date dVarauksenAlkuPVM = formatter.parse(sVarauksenAlkuPVM);
        Date dVarauksenLoppuPVM = formatter.parse(sVarauksenLoppuPVM);

        System.out.println(sVarattuPVM);
        System.out.println(dVarattuPVM);
        varausID.setText(resultSet.getString("varaus_ID"));
        asiakasID.setText(resultSet.getString("asiakas_id"));
        mokkiID.setText(mokkinimi.getString("mokkinimi"));

        //kutsuu muunnaLocalDateksi metodia ett‰ saadaan p‰iv‰m‰‰rist‰ yhteensopivat
        varattuPVM.setValue(muunnaLocalDateksi(dVarattuPVM));
        vahvistusPVM.setValue(muunnaLocalDateksi(dVahvistusPVM));
        varauksenAlkuPVM.setValue(muunnaLocalDateksi(dVarauksenAlkuPVM));
        varauksenLoppuPVM.setValue(muunnaLocalDateksi(dVarauksenLoppuPVM));

    }
    public void palveluListapaivitys() throws SQLException { //Kutsutaan heti t‰h‰n ikkunaan siirrytt‰ess‰. N‰ytt‰‰ kaikki varauksen palvelut

        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+kanta, nimi, salis);
        PreparedStatement preparedStatement1=connection.prepareStatement(

                "select palvelu_id, lkm from varauksen_palvelut where varaus_id  = "+" '"+iddd+"'"
        );
        ResultSet varauksenPalvelut = preparedStatement1.executeQuery();

        while(varauksenPalvelut.next()) {
            preparedStatement1 = connection.prepareStatement(

                    "select palvelu_id, nimi from palvelu where palvelu_id = " + " '" + varauksenPalvelut.getString("palvelu_id") + "'"
            );
            ResultSet palveluNimet = preparedStatement1.executeQuery();
            palveluNimet.next();



            Text x = new Text(palveluNimet.getString("nimi") + " x " + varauksenPalvelut.getString("lkm"));
            x.setAccessibleText(palveluNimet.getString("palvelu_id"));
            palveluList.getItems().add(x);

        }
    }
    public void poista() {
        //Poistetaan ensin varauksesta palvelut
        try {
            PreparedStatement preparedStatement=connection.prepareStatement("delete from varauksen_palvelut where varaus_id="+iddd);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("Huomio");
            a.setContentText("Palveluiden poistaminen ei onnistunut");
            a.show();
        }
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+kanta, nimi, salis);

            //Seuraavana itse varaus
            PreparedStatement preparedStatement=connection.prepareStatement("delete from varaus where varaus_id="+iddd);
            preparedStatement.executeUpdate();
            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setContentText("Varaus poistettu");
            a.setTitle("Huomio");
            a.show();
            //P‰ivitt‰‰ listan uudestaan poiston j‰lkeen
            listapaivitys();
        }catch(SQLException e) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("Huomio");
            a.setContentText("Poistaminen ei onnistunut");
            a.show();
            e.printStackTrace();
        }
    }
    public void takaisin () throws IOException {
        changeScene("VaraustenHallinta.fxml");
    }
    public void tallenna() throws SQLException {

        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+kanta, nimi, salis);

        //Otetaan datepickereist‰ valitut p‰iv‰m‰‰r‰t
        String sVarattuPVM = varattuPVM.getValue().toString();
        String sVahvistusPVM = vahvistusPVM.getValue().toString();
        String sVarauksenAlkuPVM = varauksenAlkuPVM.getValue().toString();
        String sVarauksenLoppuPVM = varauksenLoppuPVM.getValue().toString();

        PreparedStatement preparedStatement = connection.prepareStatement("select mokki_id from mokki where mokkinimi = '"+mokkiID.getText()+"'");
        ResultSet mokkinimi =  preparedStatement.executeQuery();
        try {

            mokkinimi.next();
            //P‰ivitt‰‰ valitun varauksen tiedot tietokannassa
            preparedStatement = connection.prepareStatement(
                    "update varaus set mokki_mokki_id='"+mokkinimi.getString("mokki_id")+"',varattu_pvm='"+sVarattuPVM+"', vahvistus_pvm='"+sVahvistusPVM+"', varattu_alkupvm='"+sVarauksenAlkuPVM+"', varattu_loppupvm ='"+sVarauksenLoppuPVM+"' where varaus_id="+iddd);
            preparedStatement.executeUpdate();

            tallenna.setText("Tallennettu");
            tallenna.setStyle("-fx-background-color: #00ff00");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.println("Feilas");
        }
    }
    public void palveluIkkuna() throws IOException {
        changeScene("VaraustenHallinta_Palvelut.fxml");
    }
    public void poistaVarauksenPalvelu () throws SQLException { //Poistaa varauksesta valitun palvelun
        Text id = (Text) palveluList.getSelectionModel().getSelectedItem();
        String idd=id.getAccessibleText();
        System.out.println(idd);

        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+kanta, nimi, salis);

        try {
            PreparedStatement preparedStatement=connection.prepareStatement("delete from varauksen_palvelut where palvelu_id ="+ Integer.parseInt(idd));
            preparedStatement.executeUpdate();
            palveluList.getItems().remove(palveluList.getSelectionModel().getSelectedItem());
        } catch (SQLIntegrityConstraintViolationException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            palveluListapaivitys();
            paivita();
        } catch (SQLException | ParseException throwables) {
            throwables.printStackTrace();
        }
    }
}
