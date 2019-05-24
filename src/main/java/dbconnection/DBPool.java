package dbconnection;

import javax.naming.NamingException;
import javax.sql.DataSource;

public class DBPool {

    private static DataSource dataSource;

    private DBPool() {
    }

    public static synchronized DataSource getInstance() {
        if (dataSource == null) {
            try {
                javax.naming.Context initialContext = new javax.naming.InitialContext();

                dataSource = (DataSource) initialContext.lookup("java:comp/env/jdbc/web_lib");

            } catch (NamingException ex) {
                ex.printStackTrace();
            }
        }
        return dataSource;
    }
}
