package dao;

import entity.Mahasiswa;
import util.DaoService;
import util.MySQLConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MahasiswaDaoImpl implements DaoService<Mahasiswa> {

    @Override
    public List<Mahasiswa> fetchAll() throws SQLException, ClassNotFoundException {

        List<Mahasiswa> mahasiswas = new ArrayList<>();
        String query = "SELECT * FROM MAHASISWA";

        try(Connection connection = MySQLConnection.createConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(query)) {
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        Mahasiswa mahasiswa = new Mahasiswa();
                        mahasiswa.setId(rs.getInt("id"));
                        mahasiswa.setNama(rs.getString("nama"));
                        mahasiswa.setNim(rs.getString("nim"));
                        mahasiswa.setNilaiTugas(rs.getBigDecimal("nilai_tugas"));
                        mahasiswa.setNilaiKuis(rs.getBigDecimal("nilai_kuis"));
                        mahasiswa.setNilaiUts(rs.getBigDecimal("nilai_uts"));
                        mahasiswa.setNilaiUas(rs.getBigDecimal("nilai_uas"));
                        mahasiswas.add(mahasiswa);
                    }
                }
            }
        }


        return mahasiswas;
    }

    @Override
    public int addData(Mahasiswa mahasiswa) throws SQLException, ClassNotFoundException {
        int result = 0;
        String query = "INSERT INTO mahasiswa(nama,nim,nilai_tugas,nilai_kuis,nilai_uts,nilai_uas) VALUES(?,?,?,?,?,?)";
        try (Connection connection = MySQLConnection.createConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(query)) {
                ps.setString(1, mahasiswa.getNama());
                ps.setString(2, mahasiswa.getNim());
                ps.setBigDecimal(3, mahasiswa.getNilaiTugas());
                ps.setBigDecimal(4, mahasiswa.getNilaiKuis());
                ps.setBigDecimal(5, mahasiswa.getNilaiUts());
                ps.setBigDecimal(6, mahasiswa.getNilaiUas());
                if (ps.executeUpdate() != 0) {
                    connection.commit();
                    result = 1;
                } else{
                    connection.rollback();
                }
            }
        }

        return result;
    }

    @Override
    public int updateData(Mahasiswa mahasiswa) throws SQLException, ClassNotFoundException {
        int result = 0;
        String query = "UPDATE mahasiswa SET nama = ?, nim = ?, nilai_tugas = ?, nilai_kuis = ?, nilai_uts = ?, nilai_uas = ? WHERE id = ?";
        try (Connection connection = MySQLConnection.createConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(query)) {
                ps.setString(1, mahasiswa.getNama());
                ps.setString(2, mahasiswa.getNim());
                ps.setBigDecimal(3, mahasiswa.getNilaiTugas());
                ps.setBigDecimal(4, mahasiswa.getNilaiKuis());
                ps.setBigDecimal(5, mahasiswa.getNilaiUts());
                ps.setBigDecimal(6, mahasiswa.getNilaiUas());
                ps.setInt(7, mahasiswa.getId());
                if (ps.executeUpdate() != 0) {
                    connection.commit();
                    result = 1;
                } else {
                    connection.rollback();
                }
            }
        }

        return result;
    }
}
