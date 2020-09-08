/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication5;

import database.NewHibernateUtil;
import entity.Hoinghi;
import entity.Lichsudangky;
import entity.LichsudangkyId;
import entity.ThongKeLichSu;
import entity.Tochuc;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.hibernate.Session;

/**
 * FXML Controller class
 *
 * @author Hp
 */
public class LichSuDangKyController implements Initializable {

    @FXML
    private Button home;
    @FXML
    private Label username;
    @FXML
    private TableView<ThongKeLichSu>table;  
    @FXML
    private TableColumn<ThongKeLichSu,Integer> table_ma;
    @FXML
    private TableColumn<ThongKeLichSu,String> table_ten;
    @FXML
    private TableColumn<ThongKeLichSu,String> table_ngay;
    @FXML
    private TableColumn<ThongKeLichSu,String> table_dia_diem;
    @FXML
    private TableColumn<ThongKeLichSu,String> table_tt;
    @FXML
    private TextField search_field;  
    @FXML
    private DatePicker date_picker;
    @FXML
    private ComboBox<String> combobox;
    @FXML
    private Button search;
    @FXML
    private TextField search_field_2;   
    @FXML
    private ComboBox<String> combobox_2;
    @FXML
    private Button chi_tiet;
    @FXML
    private Button bo_tham_gia;
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
        home.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
            try {
                ChangeHome("UserHome.fxml");
            } catch (IOException ex) {
                Logger.getLogger(LichSuDangKyController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }
    
    public void Init(String str){
        username.setText(str);
        TableView();
        combobox.setItems(combo);
        combobox_2.setItems(combo);
    }

    private final ObservableList<ThongKeLichSu> thong_ke = FXCollections.observableArrayList();
    private ObservableList<String> combo = FXCollections.observableArrayList("Tất cả","Tên hội nghị","Mã hội nghị","Địa điểm");
    @FXML
    private void TableView(){
        int i = 0;
        Hoinghi h = new Hoinghi();
        Lichsudangky l = new Lichsudangky();
        Tochuc t = new Tochuc();
        List<Lichsudangky> list1 = l.excuteQuery("FROM Lichsudangky where userName = '"+username.getText()+"'");
        for(Lichsudangky o : list1){
            List<Hoinghi> list2 = h.excuteQuery("FROM Hoinghi where maHoiNghi = "+list1.get(i).getId().getMaHoiNghi());
            List<Tochuc> list3 = t.excuteQuery("FROM Tochuc where maHoiNghi = "+list1.get(i).getId().getMaHoiNghi());            
            thong_ke.add(new ThongKeLichSu(o.getId().getMaHoiNghi(),list2.get(0).getTen(),String.valueOf(o.getNgayDangKy()),list3.get(0).getDiadiem().getDiaChi(),o.getTrangThai()));
            i++;
        }
        table_ma.setCellValueFactory(new PropertyValueFactory<>("table_ma"));
        table_ten.setCellValueFactory(new PropertyValueFactory<>("table_ten"));
        table_ngay.setCellValueFactory(new PropertyValueFactory<>("table_ngay"));
        table_dia_diem.setCellValueFactory(new PropertyValueFactory<>("table_dia_diem"));
        table_tt.setCellValueFactory(new PropertyValueFactory<>("table_tt"));
        //add your data to the table here.
        table.setItems(thong_ke);
    }
    
    public void SearchButton(){
        search.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                String kieu = combobox.getValue();
                ObservableList<ThongKeLichSu> thong_ke_1 = FXCollections.observableArrayList();
                ObservableList<ThongKeLichSu> thong_ke_2 = FXCollections.observableArrayList();
                ObservableList<ThongKeLichSu> thong_ke_3 = FXCollections.observableArrayList();
                if(kieu==null||kieu.equals("Tất cả"))
                {
                    thong_ke_1 = thong_ke;
                }else if(kieu.equals("Tên hội nghị"))
                {
                    for(ThongKeLichSu o : thong_ke){
                        if(o.getTable_ten().contains(search_field.getText())){
                            thong_ke_1.add(o);
                        }
                    }
                }else if(kieu.equals("Mã hội nghị")){
                    for(ThongKeLichSu o : thong_ke){
                        if(String.valueOf(o.getTable_ma()).contains(search_field.getText())){
                            thong_ke_1.add(o);
                        }
                    } 
                }else{
                    for(ThongKeLichSu o : thong_ke){
                        if(o.getTable_dia_diem().contains(search_field.getText())){
                            thong_ke_1.add(o);
                        }
                    }
                }
                
                kieu = combobox_2.getValue();
                if(kieu == null || kieu.equals("Tất cả")){
                    thong_ke_2 = thong_ke_1;
                }else{
                    if(kieu.equals("Tên hội nghị"))
                    {
                        for(ThongKeLichSu o : thong_ke_1){
                            if(o.getTable_ten().contains(search_field_2.getText())){
                                thong_ke_2.add(o);
                            }
                        }   
                    }else if(kieu.equals("Mã hội nghị")){
                        for(ThongKeLichSu o : thong_ke_1){
                            if(String.valueOf(o.getTable_ma()).contains(search_field_2.getText())){
                                thong_ke_2.add(o);
                            }
                        }
                    }else{
                        for(ThongKeLichSu o : thong_ke_1){
                            if(o.getTable_dia_diem().contains(search_field_2.getText())){
                                thong_ke_2.add(o);
                            }
                        }
                    }
                }
                if(date_picker.getValue() != null){
                    for(ThongKeLichSu o : thong_ke_2){
                        if(o.getTable_ngay().contains(String.valueOf(date_picker.getValue()))){
                            thong_ke_3.add(o);
                        }
                    }
                }else{
                    thong_ke_3 = thong_ke_2;
                }
                table.setItems(thong_ke_3);
           }
        });
    }
    public void ChangeLichSu(String string) throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(string));
        Parent View = loader.load();
        Scene scene2 = new Scene(View,800,650); 
        Stage s = JavaFXApplication5.getPrimaryStage();
        LichSuDangKyController controll = loader.getController();
        controll.Init(username.getText());
        s.setScene(scene2);
    }
    public void ChangeChiTiet() throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("UserHoiNghiChiTiet.fxml"));
        Parent HoiNghiChiTietView = loader.load();
        Scene scene2 = new Scene(HoiNghiChiTietView,800,800); 
        Stage s = JavaFXApplication5.getPrimaryStage();
        ThongKeLichSu m =  table.getSelectionModel().getSelectedItem();
        UserHoiNghiChiTietController controll = loader.getController();
        controll.Init(m.getTable_ma(),username.getText());
        s.setScene(scene2);
    }
    @FXML
    private void ChiTietButton(){
        chi_tiet.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                if(table.getSelectionModel().getSelectedItem()!= null)
                {
                    try {
                        ChangeChiTiet();
                    } catch (IOException ex) {
                        Logger.getLogger(LichSuDangKyController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
           }
        });
    }
    @FXML
    private void BoThamGiaButton(){
        bo_tham_gia.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                if(table.getSelectionModel().getSelectedItem()!= null)
                {
                    ThongKeLichSu m = table.getSelectionModel().getSelectedItem();
                    Tochuc t = new Tochuc();
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);                    
                    List<Tochuc> list = t.excuteQuery("From Tochuc where maHoiNghi = "+m.getTable_ma());
                    if(list.get(0).getId().getThoiGianToChuc().after(Date.valueOf(java.time.LocalDate.now()))){
                        LichsudangkyId id = new LichsudangkyId(username.getText(),m.getTable_ma());
                        Session s = NewHibernateUtil.getSessionFactory().openSession();   
                        s.beginTransaction();
                        Lichsudangky l = (Lichsudangky) s.get(Lichsudangky.class, id);
                        if (l != null) {
                            s.delete(l);
                            if(l.getTrangThai().equals("Thành công")){
                                Hoinghi h = (Hoinghi) s.get(Hoinghi.class, l.getId().getMaHoiNghi());
                                h.setNguoiThamDu(h.getNguoiThamDu()-1);
                                s.update(h);
                            }
                        }
                        s.getTransaction().commit();
                        s.close();
                        alert.setContentText("Xóa thành công");
                        alert.show();
                        try {
                            ChangeLichSu("LichSuDangKy.fxml");
                        } catch (IOException ex) {
                            Logger.getLogger(LichSuDangKyController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    else{
                        alert.setContentText("Không thể bỏ tham gia hội nghị đã diễn ra");
                        alert.show();
                    }
                }
           }
        });
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.HomeButton();
        this.SearchButton();
        this.ChiTietButton();
        this.BoThamGiaButton();
    }    
    
}
