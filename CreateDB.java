import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateDB {

    public static void main(String[] args) {
        final String DBURL = "jdbc:sqlite:PenjualanPulsa.db";

        try (Connection connection = DriverManager.getConnection(DBURL)) {
            Statement statement = connection.createStatement();

            if (!tableExists(connection, "tabelCustomer")) {
                String createTabelCustomer = "CREATE TABLE IF NOT EXISTS tabelCustomer (" +
                        "idCustomer INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "namaCustomer VARCHAR(255) NOT NULL," +
                        "nomorHpCustomer VARCHAR(12) NOT NULL UNIQUE," +
                        "passwordCustomer VARCHAR(255) NOT NULL," +
                        "emailCustomer VARCHAR(255) UNIQUE," +
                        "statusAktif INTEGER NOT NULL CHECK (statusAktif IN (0,1))" +
                        ");";
                statement.executeUpdate(createTabelCustomer);
                System.out.println("Tabel tabelCustomer Berhasil dibuat");
            } else {
                System.out.println("Tabel tabelCustomer telah dibuat");
            }

            if (!tableExists(connection, "tabelMitra")) {
                String createTabelMitra = "CREATE TABLE IF NOT EXISTS tabelMitra (" +
                        "idMitra INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "namaMitra VARCHAR(255) NOT NULL," +
                        "emailMitra VARCHAR(255) NOT NULL UNIQUE," +
                        "passwordMitra VARCHAR(255) NOT NULL," +
                        "statusVerifikasi INTEGER NOT NULL CHECK (statusVerifikasi IN (0,1))," +
                        "statusAktif INTEGER NOT NULL CHECK (statusAktif IN (0,1))" +
                        ");";
                statement.executeUpdate(createTabelMitra);
                System.out.println("Tabel tabelMitra Berhasil dibuat");
            } else {
                System.out.println("Tabel tabelMitra telah dibuat");
            }

            if (!tableExists(connection, "tabelPaket")) {
                String createTabelPaket = "CREATE TABLE IF NOT EXISTS tabelPaket (" +
                        "idPaket INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "namaPaket VARCHAR(255) NOT NULL," +
                        "deskripsiPaket TEXT NOT NULL," +
                        "kuota INT NOT NULL," +
                        "hargaPaket INT NOT NULL," +
                        "statusAktif INTEGER NOT NULL CHECK (statusAktif IN (0,1))" +
                        ");";
                statement.executeUpdate(createTabelPaket);
                System.out.println("Tabel tabelPaket Berhasil dibuat");
            } else {
                System.out.println("Tabel tabelPaket telah dibuat");
            }

            if (!tableExists(connection, "tabelPulsaKuotaCustomer")) {
                String createtabelPulsaKuotaCustomer = "CREATE TABLE IF NOT EXISTS tabelPulsaKuotaCustomer(" +
                        "idPulsaKuotaCustomer INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "idCustomer INT NOT NULL," +
                        "pulsaCustomer INT NOT NULL," +
                        "kuotaCustomer INT NOT NULL," +
                        "FOREIGN KEY (idCustomer) REFERENCES tabelCustomer(idCustomer) ON DELETE CASCADE ON UPDATE CASCADE"
                        +
                        ");";
                statement.executeUpdate(createtabelPulsaKuotaCustomer);
                System.out.println("Tabel tabelPulsaKuotaCustomer berhasil dibuat");
            } else {
                System.out.println("Tabel tabelPulsaKuotaCustomer telah dibuat");
            }

            if (!tableExists(connection, "tabelSaldoMitra")) {
                String createTabelSaldoMitra = "CREATE TABLE IF NOT EXISTS tabelSaldoMitra(" +
                        "idSaldoMitra INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "idMitra INT NOT NULL," +
                        "saldoMitra INT NOT NULL," +
                        "FOREIGN KEY (idMitra) REFERENCES tabelMitra(idMitra) ON DELETE CASCADE ON UPDATE CASCADE" +
                        ");";
                statement.executeUpdate(createTabelSaldoMitra);
                System.out.println("Tabel tabelSaldoMitra berhasil dibuat");
            } else {
                System.out.println("Tabel tabelSaldoMitra telah dibuat");
            }

            if (!tableExists(connection, "tabelTransaksiPaket")) {
                String createTabelTransaksiPaket = "CREATE TABLE IF NOT EXISTS tabelTransaksiPaket (" +
                        "idTransaksiPaket INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "idCustomer INT NOT NULL," +
                        "idPaket INTEGER NOT NULL," +
                        "waktuTransaksi TIMESTAMP NOT NULL," +
                        "statusTransaksi TEXT NOT NULL CHECK (statusTransaksi IN ('diproses', 'selesai'))," +
                        "statusAktif INTEGER NOT NULL CHECK (statusAktif IN (0,1))," +
                        "FOREIGN KEY (idCustomer) REFERENCES tabelCustomer(idCustomer)," +
                        "FOREIGN KEY (idPaket) REFERENCES tabelPaket(idPaket) " +
                        ");";
                statement.executeUpdate(createTabelTransaksiPaket);
                System.out.println("Tabel tabelTransaksiPaket berhasil dibuat");
            } else {
                System.out.println("Tabel tabelTransaksiPaket telah dibuat");
            }

            if (!tableExists(connection, "tabelTransaksiSaldo")) {
                String createTabelTransaksiSaldo = "CREATE TABLE IF NOT EXISTS tabelTransaksiSaldo (" +
                        "idTransaksiSaldo INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "idMitra INT NOT NULL," +
                        "jumlahSaldo INT NOT NULL," +
                        "waktuTransaksi TIMESTAMP NOT NULL," +
                        "statusTransaksi TEXT NOT NULL CHECK (statusTransaksi IN ('diproses', 'selesai'))," +
                        "statusAktif INTEGER NOT NULL CHECK (statusAktif IN (0, 1))," +
                        "FOREIGN KEY (idMitra) REFERENCES tabelMitra(idMitra)" +
                        ");";

                statement.executeUpdate(createTabelTransaksiSaldo);
                System.out.println("Tabel tabelTransaksiSaldo berhasil dibuat");
            } else {
                System.out.println("Tabel tabelTransaksiSaldo telah dibuat");
            }

            if (!tableExists(connection, "tabelTransaksiPulsa")) {
                String createTabelTransaksiPulsa = "CREATE TABLE IF NOT EXISTS tabelTransaksiPulsa (" +
                        "idTransaksiPulsa INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "idCustomer INTEGER NOT NULL, " +
                        "jumlahPulsa INTEGER NOT NULL, " +
                        "idMitra INTEGER, " +
                        "waktuTransaksi TIMESTAMP NOT NULL, " +
                        "statusTransaksi TEXT NOT NULL CHECK (statusTransaksi IN ('diproses','selesai')), " +
                        "statusAktif INTEGER NOT NULL CHECK (statusAktif IN (0,1)), " +
                        "FOREIGN KEY (idCustomer) REFERENCES tabelCustomer(idCustomer), " +
                        "FOREIGN KEY (idMitra) REFERENCES tabelMitra(idMitra)" +
                        ");";
                statement.executeUpdate(createTabelTransaksiPulsa);
                System.out.println("Tabel tabelTransaksiPulsa berhasil dibuat");
            } else {
                System.out.println("Tabel tabelTransaksiPulsa telah dibuat");
            }
            statement.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Gagal membuat tabel: " + e.getMessage());
        }
    }

    private static boolean tableExists(Connection connection, String tableName) throws SQLException {
        try (ResultSet rs = connection.getMetaData().getTables(null, null, tableName, null)) {
            return rs.next(); // Mengembalikan true jika tabel ada
        }
    }
}
