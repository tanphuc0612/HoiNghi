/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication5;

import database.NewHibernateUtil;
import entity.MaHoa;
import entity.Nguoidung;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 * FXML Controller class
 *
 * @author Hp
 */
public class ChinhSuaHoSoController implements Initializable {
    @FXML
    private TextField ten;
    @FXML
    private TextField pass;
    @FXML
    private Label username;
    @FXML
    private TextField email;    
    @FXML
    private Button home;   
    @FXML
    private Button chinh_sua;  
    @FXML
    private Label ten_cu;
    @FXML
    private Label pass_cu;
    @FXML
    private Label email_cu; 
    public void Init(String str){
        username.setText(str);
        Nguoidung n = new Nguoidung();
        List<Nguoidung> l = n.excuteQuery("From Nguoidung where userName = '"+str+"'");
        pass_cu.setText(l.get(0).getPass());
        ten_cu.setText(l.get(0).getTenUser());
        email_cu.setText(l.get(0).getEmail());
    }
    public void ChangeHome(String string) throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(string));
        Parent View = loader.load();
        Scene scene2;
        scene2 = new Scene(View,790,650); 
        Stage s = JavaFXApplication5.getPrimaryStage();
        UserHomeController controll = loader.getController();
        controll.Init(username.getText());
        s.setScene(scene2);
    }
    private void HomeButton(){
        home.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                try {
                    ChangeHome("UserHome.fxml");
                } catch (IOException ex) {
                    Logger.getLogger(ChinhSuaHoSoController.class.getName()).log(Level.SEVERE, null, ex);
                }
           }
        });
    }
    private void ChinhSuaButton(){
        chinh_sua.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                Query query;
                MaHoa m = new MaHoa();
                if(ten.getText().length()==0)
                {
                    ten.setText(ten_cu.getText());
                }
                
                if(pass.getText().length()==0)
                {
                    pass.setText(pass_cu.getText());
                }
                else{
                    pass.setText(m.getMD5(pass.getText()));
                }
                
                if(email.getText().length()==0)
                {
                    email.setText(email_cu.getText());
                }
                String hql = "UPDATE Nguoidung set tenUser = '"+ten.getText()+"', pass = '"+pass.getText()+"', email = '" + email.getText() + "' WHERE userName = '"+username.getText()+"'";
                Session s = NewHibernateUtil.getSessionFactory().openSession();
                s.beginTransaction();
                query = s.createQuery(hql);
                int result = query.executeUpdate();
                if(result == 0){
                    alert.setContentText("sửa không thành công");
                    alert.show();
                    s.getTransaction().commit();
                    s.close();
                }
                else{
                    alert.setContentText("Sửa thành công");
                    alert.show();
                    s.getTransaction().commit();
                    s.close();
                    Init(username.getText());
                }
                pass.clear();
                ten.clear();
                email.clear();
            }
        });
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.HomeButton();
        this.ChinhSuaButton();
    }    
    
}
