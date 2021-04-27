package softa;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.io.IOException;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;


public class VaraustenHallinta extends Menu {

    //<editor-fold desc="FXML oliot">
    @FXML
    TextField varausID;
    @FXML
    TextField asiakasID;
    @FXML
    TextField mokkiID;
    /* Jätän siltä varalta että jotai hajoo mut kalenterin pitäs toimia
    @FXML
    TextField varattuPVM;
    @FXML
    TextField vahvistusPVM;
    @FXML
    TextField varauksenAlkuPVM;
    @FXML
    TextField varauksenLoppuPVM;
     */
    @FXML
    Pane list;
    @FXML
    Pane varaus;
    @FXML
    Label hakuTulos;
    @FXML
    Button tallenna;
    @FXML
    Button takaisin;
    @FXML
    private ListView<Button> lista;
    @FXML
    private TextField haku;
    @FXML
    Button paivita;
    @FXML
    Label d;
    @FXML
    Button poista;
    @FXML
    ListView palveluList;
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
    //</editor-fold>

    static int iddd = 0;



    public void menu(ActionEvent event) throws IOException { //tällä  toiminnolla pääsee takasin päävalikkoon. Tätä kutsutaan uusivaraus.xml tiedostossa
        changeScene("Menu.fxml");
    }
    public void takaisin (ActionEvent event){
        varaus.setVisible(false);
        palveluList.getItems().clear();
    }
    public void listaHaku() {    //käytetään "Hae" nappulan painamisen yhteydessä
        ResultSet resultSet = null;
        String hakutext=haku.getText();
        String kokonimi = "N/a";
        String aID;
        String vID;
        String mID;
        int increment = 0;
        PreparedStatement preparedStatement;
        lista.getItems().clear();
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+kanta, nimi, salis);
            preparedStatement = connection.prepareStatement(

                    "select asiakas_id from asiakas where etunimi  = "+" '"+hakutext+"' or sukunimi = "+" '"+hakutext+"'"
            );

            ResultSet aIDResult = preparedStatement.executeQuery();

            aIDResult.next();
            aID = aIDResult.getString("asiakas_id");

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
                            paivita();
                            palveluListapaivitys();
                        } catch (SQLException | ParseException throwables) {
                            throwables.printStackTrace();
                        }
                        varaus.setVisible(true);
                    });
    
                    lista.getItems().add(x);
    
                }
            } catch (NullPointerException e) {
                lista.getItems().clear();
            }
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
            }
        }


    }
    public void listapaivitys(){    //käytetään "Näytä kaikki" nappulan painamisen yhteydessä
        String etunimi;
        String sukunimi;
        try {
            lista.getItems().clear();

            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+kanta, nimi, salis);
            System.out.println("Tiedot saatu!");
            PreparedStatement preparedStatement = connection.prepareStatement("select asiakas_id, etunimi, sukunimi from asiakas");

            ResultSet nimet = preparedStatement.executeQuery();
            nimet.next();

            preparedStatement=connection.prepareStatement("select * from varaus");

            ResultSet resultSet=preparedStatement.executeQuery();

            while(resultSet.next()){ //tekee nappulat tietokannasta haetuilla tiedoilla

                String id=resultSet.getString("varaus_id");
                String asiakasID =resultSet.getString("asiakas_id");
                String mokkiID =resultSet.getString("mokki_mokki_id");

                if(!asiakasID.equals(nimet.getString("asiakas_id"))) { //pidetään sama nimi nappuloissa jos varaus on samalta henkilöltä
                    nimet.next();
                }

                etunimi = nimet.getString("etunimi");
                sukunimi = nimet.getString("sukunimi");

                Button x = new Button(etunimi +" "+ sukunimi);

                x.setAccessibleText(id);               //  näin saahaan se napin ID talteen ilman että sitä näytetään siinä

                x.setOnAction((event) -> {
                    System.out.println(x.getText());

                    iddd=Integer.parseInt(x.getAccessibleText()); // tälleen saahaan se id sieltä sit poimittua

                    varaus.setVisible(true);
                    try {
                        paivita();
                        palveluListapaivitys();
                    } catch (SQLException | ParseException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                });






                lista.getItems().add(x);

            }

        } catch (SQLException e) {
            System.out.println("Error while connecting to the database");
        }


    }
    public void poista() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+kanta, nimi, salis);
            System.out.println("Tiedot saatu!");



            PreparedStatement preparedStatement=connection.prepareStatement("delete from varaus where varaus_id="+iddd);
            preparedStatement.executeUpdate();
            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setContentText("Varaus poistettu");
            a.setTitle("Huomio");
            a.show();
            listapaivitys();
        }catch(Exception e) {
            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setContentText("Virhe");
            a.setTitle("Huomio");
            a.show();
        }
    }
    public void paivita() throws SQLException, ParseException {

        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+kanta, nimi, salis);
        System.out.println("Tiedot saatu!");

        PreparedStatement preparedStatement=connection.prepareStatement("select * from varaus where varaus_id="+iddd);

        ResultSet resultSet=preparedStatement.executeQuery();
        while(resultSet.next()){
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

            String sVarattuPVM = resultSet.getString("varattu_pvm");
            String sVahvistusPVM = resultSet.getString("vahvistus_pvm");
            String sVarauksenAlkuPVM = resultSet.getString("varattu_alkupvm");
            String sVarauksenLoppuPVM = resultSet.getString("varattu_loppupvm");
            Date dVarattuPVM = formatter.parse(sVarattuPVM);
            Date dVahvistusPVM = formatter.parse(sVahvistusPVM);
            Date dVarauksenAlkuPVM = formatter.parse(sVarauksenAlkuPVM);
            Date dVarauksenLoppuPVM = formatter.parse(sVarauksenLoppuPVM);

            System.out.println(sVarattuPVM);
            System.out.println(dVarattuPVM);
            varausID.setText(resultSet.getString("varaus_ID"));
            asiakasID.setText(resultSet.getString("asiakas_id"));
            mokkiID.setText(resultSet.getString("mokki_mokki_id"));
            /* Vanhojen textfieldien koodia
            varattuPVM.setText(sVarattuPVM);
            vahvistusPVM.setText(sVahvistusPVM);
            varauksenAlkuPVM.setText(sVarauksenAlkuPVM);
            varauksenLoppuPVM.setText(sVarauksenLoppuPVM);
             */

            varattuPVM.setValue(muunnaLocalDateksi(dVarattuPVM));
            vahvistusPVM.setValue(muunnaLocalDateksi(dVahvistusPVM));
            varauksenAlkuPVM.setValue(muunnaLocalDateksi(dVarauksenAlkuPVM));
            varauksenLoppuPVM.setValue(muunnaLocalDateksi(dVarauksenLoppuPVM));

            System.out.println(dVarattuPVM);
            System.out.println(muunnaLocalDateksi(dVarattuPVM));


        }

    }
    public LocalDate muunnaLocalDateksi (Date dateToConvert) {
        return Instant.ofEpochMilli(dateToConvert.getTime())
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }
    public void tallenna() throws SQLException {

        System.out.println(iddd);
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+kanta, nimi, salis);
        System.out.println("Tiedot saatu!");

        String sVarausID = varausID.getText();
        String sAsiakasID= asiakasID.getText();
        String sMokkiID= mokkiID.getText();
        System.out.println(varattuPVM.getValue());

        String sVarattuPVM = varattuPVM.getValue().toString();
        String sVahvistusPVM = vahvistusPVM.getValue().toString();
        String sVarauksenAlkuPVM = varauksenAlkuPVM.getValue().toString();
        String sVarauksenLoppuPVM = varauksenLoppuPVM.getValue().toString();




        PreparedStatement preparedStatement=connection.prepareStatement(
                "update varaus set varattu_pvm='"+sVarattuPVM+"', vahvistus_pvm='"+sVahvistusPVM+"', varattu_alkupvm='"+sVarauksenAlkuPVM+"', varattu_loppupvm ='"+sVarauksenLoppuPVM+"' where asiakas_id="+iddd);
        preparedStatement.executeUpdate();
        tallenna.setText("Tallennettu");
        tallenna.setStyle("-fx-background-color: #00ff00");
    }
    public void palveluListapaivitys() throws SQLException {

        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+kanta, nimi, salis);
        PreparedStatement preparedStatement1=connection.prepareStatement(

                "select palvelu_id, lkm from varauksen_palvelut where varaus_id  = "+" '"+Integer.parseInt(varausID.getText())+"'"
        );
        ResultSet varauksenPalvelut = preparedStatement1.executeQuery();

        while(varauksenPalvelut.next()) {
            preparedStatement1 = connection.prepareStatement(

                    "select palvelu_id, nimi from palvelu where palvelu_id order by palvelu_id = " + " '" + varauksenPalvelut.getString("palvelu_id") + "'"
            );
            ResultSet palveluNimet = preparedStatement1.executeQuery();
            palveluNimet.next();

            Text x = new Text(palveluNimet.getString("nimi") + " x " + varauksenPalvelut.getString("lkm"));
            x.setAccessibleText(palveluNimet.getString("palvelu_id"));
            palveluList.getItems().add(x);

        }
    }
    public void poistaVarauksenPalvelu () throws SQLException {
        Text id = (Text) palveluList.getSelectionModel().getSelectedItem();
        String idd=id.getAccessibleText().toString();
        System.out.println(idd);

        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+kanta, nimi, salis);

        PreparedStatement preparedStatement=connection.prepareStatement("delete from varauksen_palvelut where palvelu_id ="+ Integer.parseInt(idd));
        preparedStatement.executeUpdate();
    }
    public void palveluIkkuna() {
        varausIDText.setText(varausID.getText());
        list.setVisible(false);
        varaus.setVisible(false);
        palvelu.setVisible(true);
    }
    public void lisaaPalvelu() throws SQLException {
        int palvelu_id = -1;
        int lkm = -1;
        try {
            palvelu_id = Integer.parseInt(palveluIDText.getText());
            lkm = Integer.parseInt(lkmText.getText());
        } catch (IllegalArgumentException e) {

        } catch (NullPointerException e) {
            System.out.println("Tyhjiä kenttiä");
        }
        PreparedStatement preparedStatement2=connection.prepareStatement(
                "insert into varauksen_palvelut set varaus_id ='"+Integer.parseInt(varausIDText.getText())+"', palvelu_id='"+palvelu_id+"',"+"lkm='"+lkm+"'");
        preparedStatement2.executeUpdate();
        preparedStatement2 = connection.prepareStatement("Insert into palvelu set nimi = '"+palvelunNimiText.getText()+"'");
        System.out.println("Tiedot tallennetu");
    }
    public void paivitaPalvelut() {
            try{
                palveluIDText.clear();
                PreparedStatement ps = connection.prepareStatement("select palvelu_id from palvelu where nimi ='"+palvelunNimiText.getText()+"' ");
                ResultSet rs = ps.executeQuery();
                rs.next();
                palveluIDText.setText(rs.getString("palvelu_id"));
            } catch (SQLException e) {

            }
    }

}
