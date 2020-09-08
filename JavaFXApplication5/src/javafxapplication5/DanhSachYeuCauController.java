/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication5;

import database.NewHibernateUtil;
import entity.DanhSach;
import entity.Hoinghi;
import entity.Lichsudangky;
import entity.LichsudangkyId;
import entity.Tochuc;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 * FXML Controller class
 *
 * @author Hp
 */
public class DanhSachYeuCauController implements Initializable {

    @FXML
    private TableView<DanhSach>table;  
    @FXML
    private TableColumn<DanhSach,Integer> table_ma;
    @FXML
    private TableColumn<DanhSach,String> table_ten;
    @FXML
    private TableColumn<DanhSach,String> table_ngay;
    @FXML
    private TableColumn<DanhSach,String> table_dia_diem;
    @FXML
    private TableColumn<DanhSach,String> table_cho;
    @FXML
    private TableColumn<DanhSach,String> table_username;
    @FXML 
    private Button yes;
    @FXML
    private Button no;
    @FXML
    private Button home;
    
    private void YesButton(){
        yes.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
            DanhSach a = table.selectionModelProperty().get().getSelectedItem();
            String[] arr = a.getTable_cho().split("/");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            Hoinghi h = new Hoinghi();
            if(Integer.valueOf(arr[0])<Integer.valueOf(arr[1])){                
                String hql1 = "UPDATE Lichsudangky set trangThai = 'Thành công' where maHoiNghi = " + a.getTable_ma() + " and userName = '" + a.getTable_username() + "'";
                List<Hoinghi> list = h.excuteQuery("from Hoinghi where maHoiNghi = " + a.getTable_ma());
                String hql2 = "UPDATE Hoinghi set nguoiThamDu = " + String.valueOf(list.get(0).getNguoiThamDu()+1) + "where maHoiNghi = " + a.getTable_ma();                
                Session s = NewHibernateUtil.getSessionFactory().openSession();
                s.beginTransaction();
                Query query1 = s.createQuery(hql1);
                Query query2 = s.createQuery(hql2);
                query1.executeUpdate();
                query2.executeUpdate();
                s.getTransaction().commit();
                s.close();
                danh_sach.remove(a);
                table.setItems(danh_sach);
            }else{
                alert.setContentText("Hội nghị đã đủ người tham gia");
                alert.show();
            }
        });
    }
    private void NoButton(){
        no.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
            DanhSach a = table.selectionModelProperty().get().getSelectedItem();
            Hoinghi h = new Hoinghi();               
            String hql1 = "UPDATE Lichsudangky set trangThai = 'Thất bại' where maHoiNghi = " + a.getTable_ma() + " and userName = '" + a.getTable_username() + "'";              
            Session s = NewHibernateUtil.getSessionFactory().openSession();
            s.beginTransaction();
            Query query1 = s.createQuery(hql1);
            query1.executeUpdate();
            s.getTransaction().commit();
            s.close();
            danh_sach.remove(a);
            table.setItems(danh_sach);
        });
    }
    private final ObservableList<DanhSach> danh_sach = FXCollections.observableArrayList();
    private void HomeButton(){
        home.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
            try {
                ChangeHome("AdminHome.fxml");
            } catch (IOException ex) {
                Logger.getLogger(DanhSachYeuCauController.class.getName()).log(Level.SEVERE, null, ex);
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
    @FXML
    private void TableView(){
        int i = 0;
        Hoinghi h = new Hoinghi();
        Lichsudangky l = new Lichsudangky();
        Tochuc t = new Tochuc();
        List<Lichsudangky> list1 = l.excuteQuery("FROM Lichsudangky where trangThai = 'Chờ duyệt'");
        for(Lichsudangky o : list1){
            List<Hoinghi> list2 = h.excuteQuery("FROM Hoinghi where maHoiNghi = "+list1.get(i).getId().getMaHoiNghi());
            List<Tochuc> list3 = t.excuteQuery("FROM Tochuc where maHoiNghi = "+list1.get(i).getId().getMaHoiNghi());            
            danh_sach.add(new DanhSach(o.getId().getMaHoiNghi(),list2.get(0).getTen(),String.valueOf(o.getNgayDangKy()),list3.get(0).getDiadiem().getTen(),o.getId().getUserName(),String.valueOf(list2.get(0).getNguoiThamDu()) + "/" + String.valueOf(list3.get(0).getDiadiem().getSucChua())));
            i++;
        }
        table_ma.setCellValueFactory(new PropertyValueFactory<>("table_ma"));
        table_ten.setCellValueFactory(new PropertyValueFactory<>("table_ten"));
        table_ngay.setCellValueFactory(new PropertyValueFactory<>("table_ngay"));
        table_dia_diem.setCellValueFactory(new PropertyValueFactory<>("table_dia_diem"));
        table_cho.setCellValueFactory(new PropertyValueFactory<>("table_cho"));
        table_username.setCellValueFactory(new PropertyValueFactory<>("table_username"));
        //add your data to the table here.
        table.setItems(danh_sach);
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        TableView();
        this.HomeButton();
        this.YesButton();
        this.NoButton();
    }    
    
}
