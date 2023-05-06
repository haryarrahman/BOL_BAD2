package entity;

import java.math.BigDecimal;

public class Mahasiswa {

    private int id;
    private String nama, nim;
    private BigDecimal nilaiTugas, nilaiKuis, nilaiUts, nilaiUas;

    public Mahasiswa() {

    }

    public Mahasiswa(int id, String nama, BigDecimal nilaiTugas, BigDecimal nilaiKuis, BigDecimal nilaiUts, BigDecimal nilaiUas) {
        this.id = id;
        this.nama = nama;
        this.nilaiTugas = nilaiTugas;
        this.nilaiKuis = nilaiKuis;
        this.nilaiUts = nilaiUts;
        this.nilaiUas = nilaiUas;
    }

    public int getId() {
        return id;
    }

    public String getNim() {
        return nim;
    }

    public void setNim(String nim) {
        this.nim = nim;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public BigDecimal getNilaiTugas() {
        return nilaiTugas;
    }

    public void setNilaiTugas(BigDecimal nilaiTugas) {
        this.nilaiTugas = nilaiTugas;
    }

    public BigDecimal getNilaiKuis() {
        return nilaiKuis;
    }

    public void setNilaiKuis(BigDecimal nilaiKuis) {
        this.nilaiKuis = nilaiKuis;
    }

    public BigDecimal getNilaiUts() {
        return nilaiUts;
    }

    public void setNilaiUts(BigDecimal nilaiUts) {
        this.nilaiUts = nilaiUts;
    }

    public BigDecimal getNilaiUas() {
        return nilaiUas;
    }

    public void setNilaiUas(BigDecimal nilaiUas) {
        this.nilaiUas = nilaiUas;
    }
}
