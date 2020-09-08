/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication5;

import entity.Diadiem;
import entity.Hoinghi;
import entity.Tochuc;
import entity.TochucId;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;


public class ThemHoiNghiController implements Initializable {

    @FXML
    private Button them;
    @FXML
    private Button home;
    @FXML
    private TextField ten;
    @FXML
    private ComboBox ma;     
    @FXML
    private TextField ngan;
    @FXML
    private TextField chi_tiet;  
    @FXML
    private DatePicker time; 
    @FXML
    private Button anh;
    @FXML
    private Label link;
    
    private ObservableList<String> combo = FXCollections.observableArrayList();
    public void Init(){
        Diadiem d = new Diadiem();
        List<Diadiem> list = d.excuteQuery("From Diadiem");
        for(Diadiem o:list){
            combo.add(String.valueOf(o.getMaDiaDiem())+". "+o.getDiaChi());
        }
        ma.setItems(combo);
    }
    private void HomeButton(){
        home.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                try {
                    ChangeHome("AdminHome.fxml");
                } catch (IOException ex) {
                    Logger.getLogger(ThemHoiNghiController.class.getName()).log(Level.SEVERE, null, ex);
                }
           }
        });
    }
    public void CopyAnh()
    {
        String arr[] = link.getText().split("/");
        try {
            FileInputStream in = new FileInputStream(link.getText().replace("/", "\\"));
                    File file = new File("src/javafxapplication5/Image");
                    String file_name = file.getAbsolutePath() + "/" + arr[arr.length-1];
                    File file1 = new File(file_name);                  
                    try {
                        file1.createNewFile();
                    } catch (IOException ex) {
                        Logger.getLogger(ThemHoiNghiController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    FileOutputStream out = new FileOutputStream(file_name);
                    BufferedInputStream bin = new BufferedInputStream(in);
                    BufferedOutputStream bou = new BufferedOutputStream(out);
                    try{
                        int b = 0;
                        while(b!=-1){
                            b = bin.read();
                            bou.write(b);
                        } 
                    }catch (IOException ex) {
                        Logger.getLogger(ThemHoiNghiController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    try {
                        bin.close();
                        bou.close();
                    } catch (IOException ex) {
                        Logger.getLogger(ThemHoiNghiController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    link.setText(arr[arr.length-1]);
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(ThemHoiNghiController.class.getName()).log(Level.SEVERE, null, ex);
                }        
    }
    private void AnhButton(){
        anh.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                FileChooser fc = new FileChooser();
                fc.getExtensionFilters().addAll(new ExtensionFilter("Image Files", "*.jpg", "*.png"));
                File selected = fc.showOpenDialog(null);
                if(selected!=null)
                {
                    link.setText(selected.getAbsolutePath().replace("\\","/"));
                }
           }
        });
    }
    public void ThemButton(){
        them.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                Hoinghi h = new Hoinghi();
                Tochuc t = new Tochuc();
                Diadiem d = new Diadiem();
                TochucId id = new TochucId();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                if(ma.getValue() == null||ten.getText().isEmpty()||time.getValue()==null){
                    alert.setContentText("Bạn cần điền đủ thông tin");
                    alert.show();
                }
                else if(time.getValue().isBefore(java.time.LocalDate.now())){
                    alert.setContentText("Ngày bắt đầu hội nghị cần sau ngày hôm nay");
                    alert.show();
                }
                else{
                    String arr[] = String.valueOf(ma.getValue()).split(". ");
                    alert.setContentText(String.valueOf(ma.getValue()));
                    alert.show();
                    List<Tochuc> list = t.excuteQuery("From Tochuc where maDiaDiem = "+arr[0]+" and thoiGianToChuc = '" + String.valueOf(time.getValue()) + "'");       
                    List<Diadiem> list1 = d.excuteQuery("From Diadiem where maDiaDiem = " + arr[0]);    
                    if(!list.isEmpty()){
                        alert.setContentText("không được tổ chức hội nghị chung 1 ngày tại cùng 1 địa điểm");
                        alert.show();                       
                    }
                    else{
                        h.setMoTaNgan(ngan.getText());
                        h.setMoTaChiTiet(chi_tiet.getText());
                        h.setNguoiThamDu(0);
                        h.setTen(ten.getText());
                        if(!link.getText().isEmpty())
                        {
                            CopyAnh();
                        }
                        h.setAnh(link.getText());
                        id.setMaDiaDiem(Integer.valueOf(arr[0]));
                        id.setThoiGianToChuc(Date.valueOf(time.getValue()));
                        t.setId(id);
                        t.setHoinghi(h);
                        t.setDiadiem(list1.get(0));
                        h.Them();
                        t.Them();
                        alert.setContentText("Thêm thành công");
                        alert.show();
                        ten.clear();
                        ngan.clear();
                        chi_tiet.clear();
                    }
               }
           }
        });
    }       
    public void ChangeHome(String string) throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(string));
        Parent View = loader.load();
        Scene scene2;
        scene2 = new Scene(View,790,650); 
        Stage s = JavaFXApplication5.getPrimaryStage();
        s.setScene(scene2);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.HomeButton();
        this.ThemButton();
        this.Init();
        this.AnhButton();
    }    
    
}
