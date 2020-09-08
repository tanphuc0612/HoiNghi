/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Hp
 */
public class DanhSach {
        private  SimpleIntegerProperty  table_ma;
        private  SimpleStringProperty  table_ten;
        private  SimpleStringProperty  table_ngay;
        private  SimpleStringProperty  table_dia_diem;
        private  SimpleStringProperty  table_username;
        private  SimpleStringProperty  table_cho;
        
        public DanhSach(Integer table_ma, String table_ten, String table_ngay,String table_dia_diem, String table_username, String table_cho) {
            this.table_ma = new SimpleIntegerProperty(table_ma);
            this.table_ten = new SimpleStringProperty(table_ten);
            this.table_ngay = new SimpleStringProperty(table_ngay);
            this.table_dia_diem = new SimpleStringProperty(table_dia_diem);
            this.table_username = new SimpleStringProperty(table_username);
            this.table_cho = new SimpleStringProperty(table_cho);          
        }
        
    public int getTable_ma() {
        return table_ma.get();
    }

    public void setTable_ma(int table_ma) {
        this.table_ma = new SimpleIntegerProperty(table_ma);
    }
    
    public String getTable_ten() {
        return table_ten.get();
    }

    public void setTable_ten(String table_ten) {
        this.table_ten = new SimpleStringProperty(table_ten);
    }
    
    public String getTable_ngay() {
        return table_ngay.get();
    }

    public void setTable_ngay(String table_ngay) {
        this.table_ngay = new SimpleStringProperty(table_ngay);
    }
    
    public String getTable_dia_diem() {
        return table_dia_diem.get();
    }

    public void setTable_dia_diem(String table_dia_diem) {
        this.table_dia_diem = new SimpleStringProperty(table_dia_diem);
    }
    public String getTable_cho() {
        return table_cho.get();
    }

    public void setTable_cho(String table_cho) {
        this.table_cho = new SimpleStringProperty(table_cho);
    }
    public String getTable_username() {
        return table_username.get();
    }

    public void setTable_username(String table_username) {
        this.table_username = new SimpleStringProperty(table_username);
    }
}
