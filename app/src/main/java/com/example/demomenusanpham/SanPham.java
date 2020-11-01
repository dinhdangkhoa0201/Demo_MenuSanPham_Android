package com.example.demomenusanpham;

import java.io.Serializable;
import java.util.Objects;

public class SanPham implements Serializable {
    private String ma;
    private String ten;
    private String dvt;

    @Override
    public String toString() {
        return "Mã: " + ma +
                "\nTên: " + ten +
                "\nĐVT: " + dvt;
    }

    public SanPham(String ma, String ten, String dvt) {
        this.ma = ma;
        this.ten = ten;
        this.dvt = dvt;
    }

    public String getMa() {
        return ma;
    }

    public void setMa(String ma) {
        this.ma = ma;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getDvt() {
        return dvt;
    }

    public void setDvt(String dvt) {
        this.dvt = dvt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SanPham sanPham = (SanPham) o;
        return ma.equals(sanPham.ma);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ma);
    }
}
