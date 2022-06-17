package sample.progalab8;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class Main {
    public static void main(String[] args) {
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager
                    .getConnection("jdbc:postgresql://localhost:1111/studs",
                            "s336424", "zml766");
            System.out.println("Opened database successfully");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
