import java.sql.*;

void main() {
  var url = "jdbc:postgresql://localhost:5501/bdia";
  var props = new Properties();
  props.setProperty("user", "bdia_owner");
  props.setProperty("password", "12345");
  // props.setProperty("ssl", "true");

  try (Connection conn = DriverManager.getConnection(url, props)) {
    conn.setAutoCommit(false);
    conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);

    try {
      // INSERT
      try (PreparedStatement pstmt = conn
           .prepareStatement("insert into bdia.people (name, age) values (?, ?)")) {
        pstmt.setString(1, "Ben");
        pstmt.setInt(2, 35);
        int inserted = pstmt.executeUpdate();
        IO.println("Inserted rows: " + inserted);
      }

      // DELETE
      try (PreparedStatement pstmt = conn
           .prepareStatement("delete from bdia.people where name = ?")) {
        pstmt.setString(1, "Ben");
        int deleted = pstmt.executeUpdate();
        IO.println("Deleted rows: " + deleted);
      }

      // ----------------------------------------------------------------
      // PRZYKŁAD SQL INJECTION - NIGDY TAK NIE ROBIC!
      // Napastnik podaje jako name:  Anna' OR '1'='1
      // Powstaje zapytanie:
      //   update bdia.people set age = 99 where name = 'Anna' OR '1'='1'
      // Warunek OR '1'='1' jest zawsze prawdziwy - aktualizuje WSZYSTKIE wiersze!
      // ----------------------------------------------------------------
      String nameFromUser = "Anna' OR '1'='1";  // złośliwy input
      int ageFromUser = 99;
      String sqlInjectable =
        "update bdia.people set age = " + ageFromUser +
        " where name = '" + nameFromUser + "'";
      IO.println("NIEBEZPIECZNE zapytanie: " + sqlInjectable);
      // conn.createStatement().executeUpdate(sqlInjectable); // <- NIE odkomentowywac!

      // ----------------------------------------------------------------
      // BEZPIECZNA WERSJA - PreparedStatement z parametrami
      // Driver traktuje '?' jako dane, nie jako fragment SQL.
      // Cudzysłowy i znaki specjalne są automatycznie eskejpowane.
      // ----------------------------------------------------------------
      // UPDATE
      int age = 26;
      String name = "Anna";

      try (PreparedStatement pstmt = conn
           .prepareStatement("update bdia.people set age = ? where name = ?")) {
        pstmt.setInt(1, age);
        pstmt.setString(2, name);
        int updated = pstmt.executeUpdate();
        IO.println("Updated rows: " + updated);
      }

      // SELECT
      try (Statement stmt = conn.createStatement();
           ResultSet rs = stmt.executeQuery("select * from bdia.people")) {

        ResultSetMetaData meta = rs.getMetaData();
        int colCount = meta.getColumnCount();

        while (rs.next()) {
          for (int i = 1; i <= colCount; i++) {
            if (i > 1) IO.print("\t");
            var value = rs.getObject(i);
            IO.print(value + ":" + value.getClass());
          }
          IO.println();
        }
      }
      conn.commit();
    }
    catch (SQLException e) {
      conn.rollback();
      throw e;
    }
    finally {
      conn.setAutoCommit(true);
    }
  }
  catch(SQLException e) {
    e.printStackTrace();
  }
}
