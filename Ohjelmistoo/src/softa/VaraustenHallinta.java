package softa;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.io.IOException;
import java.sql.*;


public class VaraustenHallinta extends Menu {

    @FXML
    TextField varausID;
    @FXML
    TextField asiakasID;
    @FXML
    TextField mokkiID;
    @FXML
    TextField varattuPVM;
    @FXML
    TextField vahvistusPVM;
    @FXML
    TextField varauksenAlkuPVM;
    @FXML
    TextField varauksenLoppuPVM;
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

    static int iddd = 0;

    public void menu(ActionEvent event) throws IOException { //tällä  toiminnolla pääsee takasin päävalikkoon. Tätä kutsutaan uusivaraus.xml tiedostossa
        changeScene("Menu.fxml");
    }
    public void takaisin (ActionEvent event){
        varaus.setVisible(false);
    }
    public void listaHaku() {    //käytetään "Hae" nappulan painamisen yhteydessä
        String hakutext=haku.getText();
        String aID;
        String vID;
        String mID;
        int increment = 0;
        lista.getItems().clear();
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+kanta, nimi, salis);
            PreparedStatement preparedStatement=connection.prepareStatement(

                    "select asiakas_ID from asiakas where etunimi = "+"'"+hakutext+"'"

                    +"select asiakas_id from asiakas where etunimi  = "+" '"+hakutext+"' or sukunimi = "+" '"+hakutext+"'"

            );

            ResultSet aIDResult = preparedStatement.executeQuery();

            aIDResult.next();
            aID = aIDResult.getString("asiakas_id");

            preparedStatement = connection.prepareStatement("select * from varaus where asiakas_id = "+"'"+aID+"'");
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                increment++;
                vID = resultSet.getString("varaus_id");
                aID = resultSet.getString("asiakas_ID");
                mID = resultSet.getString("mokki_mokki_id");

                Button x=new Button(hakutext +  "n " + increment + ". varaus: " + "VarausID: " + vID + " " + "AsiakasID: " + aID + " " + "MokkiID: " + mID);
                x.setAccessibleText(vID);
                x.setOnAction((event) -> {
                    iddd = Integer.parseInt(x.getAccessibleText());

                    varaus.setVisible(true);
                });

                lista.getItems().add(x);

            }
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
                    } catch (SQLException e) {
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
            a.setContentText("Asiakas poistettu");
            a.setTitle("Huomio");
            a.show();
            listapaivitys();
        }catch(Exception e) {
            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setContentText("Asiakkaalla on aktiivinen varaus! Poista varaus ensin.");
            a.setTitle("Huomio");
            a.show();
        }
    }
    public void paivita() throws SQLException{

        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+kanta, nimi, salis);
        System.out.println("Tiedot saatu!");

        PreparedStatement preparedStatement=connection.prepareStatement("select * from varaus where varaus_id="+iddd);

        ResultSet resultSet=preparedStatement.executeQuery();
        while(resultSet.next()){
            varausID.setText(resultSet.getString("varaus_ID"));
            asiakasID.setText(resultSet.getString("asiakas_id"));
            mokkiID.setText(resultSet.getString("mokki_mokki_id"));
            varattuPVM.setText(resultSet.getString("varattu_pvm"));
            vahvistusPVM.setText(resultSet.getString("vahvistus_pvm"));
            varauksenAlkuPVM.setText(resultSet.getString("varattu_alkupvm"));
            varauksenLoppuPVM.setText(resultSet.getString("varattu_loppupvm"));

        }

    }
    public void tallenna() throws SQLException {

        System.out.println(iddd);
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+kanta, nimi, salis);
        System.out.println("Tiedot saatu!");

        String sVarausID = varausID.getText();
        String sAsiakasID= asiakasID.getText();
        String sMokkiID= mokkiID.getText();
        String sVarattuPVM= varattuPVM.getText();
        String sVahvistusPVM= vahvistusPVM.getText();
        String sVarauksenAlkuPVM= varauksenAlkuPVM.getText();
        String sVarauksenLoppuPVM=varauksenLoppuPVM.getText();



        PreparedStatement preparedStatement=connection.prepareStatement(
                "update varaus set varattu_pvm='"+sVarattuPVM+"', vahvistus_pvm='"+sVahvistusPVM+"', varattu_alkupvm='"+sVarauksenAlkuPVM+"', varattu_loppupvm ='"+sVarauksenLoppuPVM+"' where asiakas_id="+iddd);
        preparedStatement.executeUpdate();
        tallenna.setText("Tallennettu");
        tallenna.setStyle("-fx-background-color: #00ff00");
    }

}
