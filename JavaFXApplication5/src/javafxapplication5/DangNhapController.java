/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication5;

import entity.Ad;
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
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Hp
 */
public class DangNhapController implements Initializable {

    @FXML
    private Button home;
    @FXML
    private Button dang_nhap;
    @FXML
    private Button dang_ky;
    @FXML
    private TextField username;    
    @FXML
    private TextField pass;     
    
    
    private void HomeButton(){
        home.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                try {
                    Change("FXMLDocument.fxml");
                } catch (IOException ex) {
                    Logger.getLogger(DangNhapController.class.getName()).log(Level.SEVERE, null, ex);
                }
           }
        });
    }
    private void DangKyButton(){
        dang_ky.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                try {
                    Change("Dangky.fxml");
                } catch (IOException ex) {
                    Logger.getLogger(DangNhapController.class.getName()).log(Level.SEVERE, null, ex);
                }
           }
        });
    }

    private void DangNhapButton(){
        dang_nhap.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                if((username.getText().length() == 0)||(pass.getText().length() == 0))
                {
                    alert.setContentText("bạn cần điền đủ thông tin");
                    alert.show();
                }
                else{
                    Nguoidung user = new Nguoidung();
                    Ad ad = new Ad();
                    List<Nguoidung> list1 = user.excuteQuery("From Nguoidung where UserName = '" + username.getText()+"'");
                    List<Ad> list2 = Ad.excuteQuery("From Ad where UserName = '" + username.getText()+"'");
                    MaHoa m = new MaHoa();
                    if(!list2.isEmpty())
                    {
                        if(list2.get(0).getPass().equals(m.getMD5(pass.getText()))){
                            try {
                                ChangeDangNhapAdmin("AdminHome.fxml");
                                        } catch (IOException ex) {
                                Logger.getLogger(DangNhapController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        else{
                            
                        }
                    }
                    else if(!list1.isEmpty()){
                        if(list1.get(0).getPass().equals(m.getMD5(pass.getText()))){
                            if(list1.get(0).getTinhTrang())
                            {
                                try {
                                ChangeDangNhapUser("UserHome.fxml");
                                        } catch (IOException ex) {
                                Logger.getLogger(DangNhapController.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }else{
                                alert.setContentText("Tài khoản này đã bị chặn");
                                alert.show();                                
                            }
                        }
                    }else{
                        alert.setContentText("không có user cần tìm hoặc pass sai");
                        alert.show();
                        username.clear();
                        pass.clear();
                    }  
                }
           }                    
        });
    } 
    public void ChangeDangNhapAdmin(String string) throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(string));
        Parent View = loader.load();
        Scene scene2;
        scene2 = new Scene(View,790,650); 
        Stage s = JavaFXApplication5.getPrimaryStage();
        s.setScene(scene2);
    }
    public void ChangeDangNhapUser(String string) throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(string));
        Parent View = loader.load();
        Scene scene2;
        scene2 = new Scene(View,790,650); 
        Stage s = JavaFXApplication5.getPrimaryStage();
        String str = username.getText();
        UserHomeController controll = loader.getController();
        controll.Init(str);
        s.setScene(scene2);
    }
    public void Change(String string) throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(string));
        Parent View = loader.load();
        Scene scene2;
        if(string.equals("FXMLDocument.fxml")){
            scene2 = new Scene(View,790,650); 
        }
        else{
            scene2 = new Scene(View,600,400);
        }
        Stage s = JavaFXApplication5.getPrimaryStage();
        s.setScene(scene2);
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.DangKyButton();
        this.HomeButton();
        this.DangNhapButton();
    }    
}
