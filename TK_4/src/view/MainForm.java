package view;

import dao.MahasiswaDaoImpl;
import entity.Mahasiswa;
import util.CustomField;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MainForm {
    private JTextField fieldName;
    private JTextField fieldNim;
    private JTextField fieldTugas;
    private JTextField fieldKuis;
    private JTextField fieldUts;
    private JTextField fieldUas;
    private JTable table1;
    private JSplitPane rootPanel;
    private JButton hitungButton;
    private JButton resetButton;
    private JButton simpanButton;
    private JLabel lblNama;
    private JLabel lblNim;
    private JLabel lblRerata;
    private JLabel lblGrade;
    private JLabel lblKeterangan;
    private MahasiswaDaoImpl mahasiswaDao;
    private List<Mahasiswa> mahasiswas;
    private MahasiswaTableModel mahasiswaTableModel;
    private Mahasiswa selectedMahasiswa;

    CustomField.TextField fieldNamaFilter = new CustomField.TextField(20, "^[a-zA-Z0-9 ]+$");
    CustomField.TextField fieldNIMFilter = new CustomField.TextField(15, "^[0-9]+$");
    CustomField.TextField fieldDecimalFilter1 = new CustomField.TextField(10, "^-?\\d{0,10}(\\.\\d{0,2})?$");
    CustomField.TextField fieldDecimalFilter2 = new CustomField.TextField(10, "^-?\\d{0,10}(\\.\\d{0,2})?$");
    CustomField.TextField fieldDecimalFilter3 = new CustomField.TextField(10, "^-?\\d{0,10}(\\.\\d{0,2})?$");
    CustomField.TextField fieldDecimalFilter4 = new CustomField.TextField(10, "^-?\\d{0,10}(\\.\\d{0,2})?$");

    public MainForm() {
        fieldName.setDocument(fieldNamaFilter.getDocument());
        fieldNim.setDocument(fieldNIMFilter.getDocument());
        fieldTugas.setDocument(fieldDecimalFilter1.getDocument());
        fieldKuis.setDocument(fieldDecimalFilter2.getDocument());
        fieldUts.setDocument(fieldDecimalFilter3.getDocument());
        fieldUas.setDocument(fieldDecimalFilter4.getDocument());

        lblNama.setText("");
        lblNim.setText("");
        lblRerata.setText("");
        lblGrade.setText("");
        lblKeterangan.setText("");

        mahasiswaDao = new MahasiswaDaoImpl();
        mahasiswas = new ArrayList<>();
        try {
            mahasiswas.addAll(mahasiswaDao.fetchAll());
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        mahasiswaTableModel = new MahasiswaTableModel(mahasiswas);
        table1.setModel(mahasiswaTableModel);
        table1.setAutoCreateRowSorter(true);

        hitungButton.addActionListener(e -> {
         if (fieldName.getText().trim().isEmpty() || fieldNim.getText().trim().isEmpty() || fieldTugas.getText().trim().isEmpty() || fieldKuis.getText().trim().isEmpty() || fieldUts.getText().trim().isEmpty() || fieldUas.getText().trim().isEmpty()) {
             JOptionPane.showMessageDialog(rootPanel, "Harap Masukkan Seluruh Field", "Warning", JOptionPane.ERROR_MESSAGE);
         } else {
             BigDecimal rerata = new BigDecimal(fieldTugas.getText()).add(
                     new BigDecimal(fieldKuis.getText())).add(
                             new BigDecimal(fieldUts.getText()).add(
                                     new BigDecimal(fieldUas.getText())
                             )).divide(BigDecimal.valueOf(4));

             lblNama.setText(fieldName.getText());
             lblNim.setText(fieldNim.getText());
             lblRerata.setText(String.valueOf(rerata));
             lblGrade.setText(getGrade(rerata));
             lblKeterangan.setText(getKeteranganLulus(getGrade(rerata)));
         }
        });

        simpanButton.addActionListener(e -> {
            if (fieldName.getText().trim().isEmpty() || fieldNim.getText().trim().isEmpty() || fieldTugas.getText().trim().isEmpty() || fieldKuis.getText().trim().isEmpty() || fieldUts.getText().trim().isEmpty() || fieldUas.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(rootPanel, "Harap Masukkan Seluruh Input", "Warning", JOptionPane.ERROR_MESSAGE);
            } else {
                Mahasiswa mahasiswa = new Mahasiswa();
                mahasiswa.setId(selectedMahasiswa != null ? selectedMahasiswa.getId() : mahasiswas.size());
                mahasiswa.setNama(fieldName.getText());
                mahasiswa.setNim(fieldNim.getText());
                mahasiswa.setNilaiTugas(new BigDecimal(fieldTugas.getText()));
                mahasiswa.setNilaiKuis(new BigDecimal(fieldKuis.getText()));
                mahasiswa.setNilaiUts(new BigDecimal(fieldUts.getText()));
                mahasiswa.setNilaiUas(new BigDecimal(fieldUas.getText()));

                try {
                    if (selectedMahasiswa == null && mahasiswaDao.addData(mahasiswa) == 1 ) {
                        mahasiswas.clear();
                        mahasiswas.addAll(mahasiswaDao.fetchAll());
                        mahasiswaTableModel.fireTableDataChanged();
                        clearAndReset();
                    }

                    if (selectedMahasiswa != null && mahasiswaDao.updateData(mahasiswa) == 1) {
                        mahasiswas.clear();
                        mahasiswas.addAll(mahasiswaDao.fetchAll());
                        mahasiswaTableModel.fireTableDataChanged();
                        clearAndReset();
                    }
                } catch (SQLException | ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        resetButton.addActionListener(e -> {
            clearAndReset();
        });

        table1.getSelectionModel().addListSelectionListener(e -> {
            if (!table1.getSelectionModel().isSelectionEmpty()) {
                int selectedIndex = table1.convertRowIndexToModel(table1.getSelectedRow());

                selectedMahasiswa = mahasiswas.get(selectedIndex);
                if (selectedMahasiswa != null) {
                    fieldName.setText(selectedMahasiswa.getNama());
                    fieldNim.setText(selectedMahasiswa.getNim());
                    fieldTugas.setText(String.valueOf(selectedMahasiswa.getNilaiTugas()));
                    fieldKuis.setText(String.valueOf(selectedMahasiswa.getNilaiKuis()));
                    fieldUts.setText(String.valueOf(selectedMahasiswa.getNilaiUts()));
                    fieldUas.setText(String.valueOf(selectedMahasiswa.getNilaiUas()));
                }
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("MainForm");
        frame.setContentPane(new MainForm().rootPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void clearAndReset(){
        fieldNamaFilter.setText("");
        fieldNIMFilter.setText("");
        fieldDecimalFilter1.setText("");
        fieldDecimalFilter2.setText("");
        fieldDecimalFilter3.setText("");
        fieldDecimalFilter4.setText("");

        lblNama.setText("");
        lblNim.setText("");
        lblRerata.setText("");
        lblGrade.setText("");
        lblKeterangan.setText("");

        table1.clearSelection();
        selectedMahasiswa = null;
    }


    public static String getGrade(BigDecimal nilai) {
        if (nilai.compareTo(BigDecimal.valueOf(90)) >= 0 && nilai.compareTo(BigDecimal.valueOf(100)) <= 0) {
            return "A";
        } else if (nilai.compareTo(BigDecimal.valueOf(80)) >= 0 && nilai.compareTo(BigDecimal.valueOf(90)) < 0) {
            return "B+";
        } else if (nilai.compareTo(BigDecimal.valueOf(70)) >= 0 && nilai.compareTo(BigDecimal.valueOf(80)) < 0) {
            return "B";
        } else if (nilai.compareTo(BigDecimal.valueOf(60)) >= 0 && nilai.compareTo(BigDecimal.valueOf(70)) < 0) {
            return "C+";
        } else if (nilai.compareTo(BigDecimal.valueOf(50)) >= 0 && nilai.compareTo(BigDecimal.valueOf(60)) < 0) {
            return "C";
        } else if (nilai.compareTo(BigDecimal.valueOf(40)) >= 0 && nilai.compareTo(BigDecimal.valueOf(50)) < 0) {
            return "D";
        } else {
            return "E";
        }
    }

    public static String getKeteranganLulus(String grade) {
        if (grade.contains("A") || grade.contains("B+") || grade.contains("B") || grade.contains("C+") || grade.contains("C")) {
            return "Dinyatakan Lulus";
        } else {
            return  "Dinyatakan Tidak Lulus";
        }
    }


    private static class MahasiswaTableModel extends AbstractTableModel {
        private final String[] COLUMNS = {"ID", "NAMA", "NIM", "NILAI TUGAS", "NILAI KUIS", "NILAI UTS", "NILAI UAS"};
        private List<Mahasiswa> mahasiswas;

        private MahasiswaTableModel(List<Mahasiswa> mahasiswas) {
            this.mahasiswas = mahasiswas ;
        }

        @Override
        public int getRowCount() {
            return mahasiswas.size();
        }

        @Override
        public int getColumnCount() {
            return COLUMNS.length;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            if (mahasiswas.size() > 0) {
                return switch (columnIndex) {
                    case 0 -> mahasiswas.get(rowIndex).getId();
                    case 1 -> mahasiswas.get(rowIndex).getNama();
                    case 2 -> mahasiswas.get(rowIndex).getNim();
                    case 3 -> mahasiswas.get(rowIndex).getNilaiTugas();
                    case 4 -> mahasiswas.get(rowIndex).getNilaiKuis();
                    case 5 -> mahasiswas.get(rowIndex).getNilaiUts();
                    case 6 -> mahasiswas.get(rowIndex).getNilaiUas();
                    default -> "-";
                };
            } else {
                return  null;
            }
        }

        @Override
        public String getColumnName(int column) {
            return COLUMNS[column];
        }

        @Override
        public Class<?> getColumnClass(int columnIndex) {
            if (getValueAt(0, columnIndex) != null) {
                return getValueAt(0, columnIndex).getClass();
            }else {
                return Object.class;
            }
        }
    }
}

