/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication5;

import database.NewHibernateUtil;
import entity.Diadiem;
import entity.Hoinghi;
import entity.Tochuc;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
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
import javafx.stage.Stage;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 * FXML Controller class
 *
 * @author Hp
 */
public class SuaHoiNghiController implements Initializable {

    @FXML 
    private Label ten_cu;
    @FXML 
    private Label ngan_cu;
    @FXML 
    private Label chi_tiet_cu;
    @FXML 
    private Label dia_diem_cu;
    @FXML 
    private Label time_cu;
    @FXML 
    private Label link;
    @FXML 
    private TextField ten;
    @FXML 
    private TextField ngan;
    @FXML 
    private TextField chi_tiet;
    @FXML 
    private ComboBox dia_diem;
    @FXML 
    private DatePicker time;
    @FXML 
    private Button home;   
    @FXML 
    private Button sua;  
    @FXML 
    private Button anh;  
    private int ma_hoi_nghi;
    private ObservableList<String> combo = FXCollections.observableArrayList();    
    public void Init(int ma){
        ma_hoi_nghi = ma;
        Tochuc t = new Tochuc();
        Hoinghi h = new Hoinghi();
        Diadiem d = new Diadiem();
        List<Hoinghi> list1 = h.excuteQuery("From Hoinghi where maHoiNghi = "+String.valueOf(ma));
        List<Tochuc> list2 = t.excuteQuery("From Tochuc where maHoiNghi = "+String.valueOf(ma));
        ten_cu.setText(list1.get(0).getTen());
        ngan_cu.setText(list1.get(0).getMoTaNgan());
        chi_tiet_cu.setText(list1.get(0).getMoTaChiTiet());
        dia_diem_cu.setText(String.valueOf(list2.get(0).getDiadiem().getDiaChi()));
        time_cu.setText(String.valueOf(list2.get(0).getId().getThoiGianToChuc()));
        List<Diadiem> list3 = d.excuteQuery("From Diadiem");
        list3.forEach((o) -> {
            combo.add(o.getDiaChi());
        });
        dia_diem.setItems(combo);
        File file = new File("src/javafxapplication5/Image"+list1.get(0).getAnh());
        link.setText(file.getAbsolutePath());
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
                fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png"));
                File selected = fc.showOpenDialog(null);
                if(selected!=null)
                {
                    link.setText(selected.getAbsolutePath().replace("\\","/"));
                }
           }
        });
    }
    private void SuaButton(){
        sua.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            Diadiem d = new Diadiem(); 
            Hoinghi h = new Hoinghi(); 
            Tochuc t = new Tochuc();
            Query query1,query2;
            if(dia_diem.getValue()==null)
            {
                dia_diem.setValue(dia_diem_cu.getText());
            }
            if(ten.getText().length()==0)
            {
                ten.setText(ten_cu.getText());
            }
            if(ngan.getText().length()==0)
            {
                ngan.setText(ngan_cu.getText());
            }
            if(chi_tiet.getText().length()==0)
            {
                chi_tiet.setText(chi_tiet_cu.getText());
            }
            if(time.getValue()==null)
            {
                time.setValue(LocalDate.parse(time_cu.getText()));
            }            
            List<Diadiem> list = d.excuteQuery("FRom Diadiem where diaChi = '"+dia_diem.getValue()+"'");
            List<Tochuc> list1 = t.excuteQuery("From Tochuc where maDiaDiem = "+list.get(0).getMaDiaDiem()+" and thoiGianToChuc = '" + String.valueOf(time.getValue()) + "'");  
            
            if(!list1.isEmpty() && list1.get(0).getHoinghi().getMaHoiNghi() != ma_hoi_nghi){
                alert.setContentText("Địa điểm đã có hội nghị khác tại thời gian này");
                alert.show();                      
            }else if(time.getValue().compareTo(java.time.LocalDate.now())<0){
                alert.setContentText("Không thể sửa thành ngày đã xảy ra");
                alert.show();                
            }
            else{
                if(link.getText()!=null){
                    CopyAnh();
                }
                String hql1 = "UPDATE Tochuc set thoiGianToChuc = '"+String.valueOf(time.getValue())+"', maDiaDiem = '"+list.get(0).getMaDiaDiem()+"' where maHoiNghi = "+String.valueOf(ma_hoi_nghi);
                String hql2 = "UPDATE Hoinghi set ten = '"+ten.getText()+ "',moTaNgan = '"+ngan.getText()+"',moTaChiTiet = '"+chi_tiet.getText()+"',anh = '" + link.getText() + "' where maHoiNghi = "+String.valueOf(ma_hoi_nghi);
                Session s = NewHibernateUtil.getSessionFactory().openSession();
                s.beginTransaction();
                query1 = s.createQuery(hql1);
                query2 = s.createQuery(hql2);
                int result1 = query1.executeUpdate();
                int result2 = query2.executeUpdate();
                if(result1 == 0 || result2 == 0){
                    alert.setContentText("Sửa không thành công");
                    alert.show();
                }
                else{
                    alert.setContentText("Sửa thành công");
                    alert.show();
                }
                s.getTransaction().commit();
                s.close();
                try {
                    ChangeHome("AdminHome.fxml");
                } catch (IOException ex) {
                    Logger.getLogger(SuaHoiNghiController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        });
    }
    
        private void HomeButton(){
        home.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
            try {
                ChangeHome("AdminHome.fxml");
            } catch (IOException ex) {
                Logger.getLogger(SuaHoiNghiController.class.getName()).log(Level.SEVERE, null, ex);
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
        this.SuaButton();
        this.AnhButton();
    }    
    
}
