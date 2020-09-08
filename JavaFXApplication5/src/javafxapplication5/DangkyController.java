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
import java.util.regex.Pattern;
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
public class DangkyController implements Initializable {


    @FXML
    private Button home;
    @FXML
    private TextField username;
    @FXML
    private TextField password;
    @FXML
    private TextField ten;
    @FXML
    private TextField email;    
    @FXML
    private Button dang_nhap;
    @FXML
    private Button dang_ky;    
    private void HomeButton(){
        home.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                try {
                    Change("FXMLDocument.fxml");
                } catch (IOException ex) {
                    Logger.getLogger(DangkyController.class.getName()).log(Level.SEVERE, null, ex);
                }
           }
        });
    }
    private void DangNhapButton(){
        dang_nhap.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                try {
                    Change("DangNhap.fxml");
                } catch (IOException ex) {
                    Logger.getLogger(DangkyController.class.getName()).log(Level.SEVERE, null, ex);
                }
           }
        });
    }
    private void DangKyButton(){
        dang_ky.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                if((username.getText().length() == 0)||(password.getText().length()==0)||(ten.getText().length()==0)||(email.getText().length()==0))
                {
                    alert.setContentText("Bạn cần điền đủ thông tin");
                    alert.show();
                }
                else{
                    Nguoidung user = new Nguoidung();
                    List<Nguoidung> list = user.excuteQuery("From Nguoidung where UserName = '" + username.getText()+"'");
                    List<Ad> list2 = Ad.excuteQuery("From Ad where UserName = '" + username.getText()+"'");
                    if(!list.isEmpty()||!list2.isEmpty())
                    {
                        alert.setContentText("User đã tồn tại");   
                        alert.show();
                    }else if(!isValid(email.getText())){
                        alert.setContentText("Email nhập không đúng định dạng");
                        alert.show();
                    }
                    else{
                        MaHoa m = new MaHoa();
                        user.setUserName(username.getText());
                        user.setPass(m.getMD5(password.getText()));
                        user.setTenUser(ten.getText());
                        user.setEmail(email.getText());
                        user.setTinhTrang(true);
                        user.ThemNguoiDung();
                        alert.setContentText("Thêm thành công");   
                        username.clear();
                        password.clear();
                        email.clear();
                        ten.clear();                        
                        alert.show();
                    }
                }
           }
        });
    }  
    public boolean isValid(String email) 
    { 
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+ 
                            "[a-zA-Z0-9_+&*-]+)*@" + 
                            "(?:[a-zA-Z0-9-]+\\.)+[a-z" + 
                            "A-Z]{2,7}$"; 
                              
        Pattern pat = Pattern.compile(emailRegex); 
        if (email == null) 
            return false; 
        return pat.matcher(email).matches(); 
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
        this.DangNhapButton();
        this.HomeButton();
        this.DangKyButton();
    }    
    
}
