package entity;
// Generated Jul 9, 2020 4:09:42 PM by Hibernate Tools 4.3.1


import java.util.Date;

/**
 * TochucId generated by hbm2java
 */
public class TochucId  implements java.io.Serializable {


     private int maDiaDiem;
     private Date thoiGianToChuc;

    public TochucId() {
    }

    public TochucId(int maDiaDiem, Date thoiGianToChuc) {
       this.maDiaDiem = maDiaDiem;
       this.thoiGianToChuc = thoiGianToChuc;
    }
   
    public int getMaDiaDiem() {
        return this.maDiaDiem;
    }
    
    public void setMaDiaDiem(int maDiaDiem) {
        this.maDiaDiem = maDiaDiem;
    }
    public Date getThoiGianToChuc() {
        return this.thoiGianToChuc;
    }
    
    public void setThoiGianToChuc(Date thoiGianToChuc) {
        this.thoiGianToChuc = thoiGianToChuc;
    }


   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof TochucId) ) return false;
		 TochucId castOther = ( TochucId ) other; 
         
		 return (this.getMaDiaDiem()==castOther.getMaDiaDiem())
 && ( (this.getThoiGianToChuc()==castOther.getThoiGianToChuc()) || ( this.getThoiGianToChuc()!=null && castOther.getThoiGianToChuc()!=null && this.getThoiGianToChuc().equals(castOther.getThoiGianToChuc()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + this.getMaDiaDiem();
         result = 37 * result + ( getThoiGianToChuc() == null ? 0 : this.getThoiGianToChuc().hashCode() );
         return result;
   }   


}


