
package com.edusys.utils;



/**
 *
 * @author Admin
 */
public class Sanpham {
    private Integer id ;
    private String tenSp ;
    private String linkHinhanh;

    public Sanpham() {
    }

    public Sanpham(int id, String tenSp, String linkHinhanh) {
        this.id = id;
        this.tenSp = tenSp;
        this.linkHinhanh = linkHinhanh;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTenSp() {
        return tenSp;
    }

    public void setTenSp(String tenSp) {
        this.tenSp = tenSp;
    }

    public String getLinkHinhanh() {
        return linkHinhanh;
    }

    public void setLinkHinhanh(String linkHinhanh) {
        this.linkHinhanh = linkHinhanh;
    }

    
    
}
