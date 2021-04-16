package softa;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.*;
import java.util.Locale;

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
    Button tallenna;
    @FXML
    Button takaisin;
    @FXML
    private ListView<Button> lista;
    @FXML
    private TextField haku;
    @FXML
    Button päivitä;
    @FXML
    Label d;
    @FXML
    Button poista;
    static int iddd=1;

    public void menu(ActionEvent event) throws IOException { //tällä  toiminnolla pääsee takasin päävalikkoon. Tätä kutsutaan uusivaraus.xml tiedostossa
        changeScene("Menu.fxml");
    }

    public void takaisin (ActionEvent event) throws IOException {
        changeScene("VaraustenHallinta.fxml");
    }
    public void listaHaku(){            // tässä haetaan id:n ja nimen perusteella asiakkaita
        String hakutext=haku.getText();
        String aID = "";
        String vID;
        String mID;
        int increment = 0;
        lista.getItems().clear();
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+kanta, nimi, salis);
            PreparedStatement preparedStatement=connection.prepareStatement(
                    "select asiakas_ID from asiakas where etunimi = "+"'"+hakutext+"'"
                    //"select * from varaus where asiakas_id = "+"'"+hakutext+"'"
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
                    System.out.println(x.getText());
                    iddd = Integer.parseInt(x.getAccessibleText());

                    try {

                        changeScene("varaus.fxml");

                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                });

                lista.getItems().add(x);

            }
            System.out.println("Tiedot saatu!");

        } catch (SQLException e) {
            System.out.println("Error while connecting to the database");
        }


    }
    public void poista() throws SQLException, IOException {
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + kanta, nimi, salis);
        System.out.println("Tiedot saatu!");
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
                "update varaus set varaus_id ='"+sVarausID+"', asiakas_id='"+sAsiakasID+"',"+"mokki_mokki_id='"+sMokkiID+"'"
                        + ", varattu_pvm='"+sVarattuPVM+"', vahvistus_pvm='"+sVahvistusPVM+"', varattu_alkupvm='"+sVarauksenAlkuPVM+"', varattu_loppupvm ='"+sVarauksenLoppuPVM+"' where asiakas_id="+iddd);
        preparedStatement.executeUpdate();
        tallenna.setText("Tallennettu");
    }

}
