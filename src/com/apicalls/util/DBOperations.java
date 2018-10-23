package com.apicalls.util;

import java.io.File;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;
import java.util.logging.Logger;


public class DBOperations {

    private static String dt = new SimpleDateFormat("yyyyMMdd").format(new Date());
    private static String filePath = new File("").getAbsolutePath();
    private static String logfile = filePath + "/logFiles/" + dt + "_" + "Database.log";
    private static Logger logger = LoggerMain.getLogger(logfile, false);

    public static Connection getDataEngineDBConnection(Map<String, String> Configuration){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://" +
                            Configuration.get("Host") + ":" +
                            Configuration.get("Port") + "/" +
                            Configuration.get("Schema"),
                            Configuration.get("User"),
                            Configuration.get("Password"));
            connection.setAutoCommit(false);
            return connection;

        } catch (Exception e){
            logger.severe("Error in connection with SQL. Error message: " + e);
            return null;
        }
    }

    public static boolean insertData(Connection connection, String insertQuery){
        try {
            assert connection != null;
            Statement stmt = connection.createStatement();
            stmt.executeUpdate(insertQuery);
            connection.commit();
            stmt.close();
            return true;

        } catch (Exception e){
            logger.severe("Error in insertData operation. Error message: " + e);
            return false;
        }
    }

    public static List<String> getASIN(Connection connection){
        try {
            assert connection != null;
            Statement stmt = connection.createStatement();

            String selectQuery  = "SELECT `ASIN` FROM `DataEngine`.`IsbnAll` " +
                                  "WHERE ASIN IS NOT NULL ORDER BY US_MWS_LastCalled ASC LIMIT 10";
            ResultSet rs = stmt.executeQuery(selectQuery);

            List<String> asinList = new ArrayList<>();
            int columns = (rs.getMetaData()).getColumnCount();

            while (rs.next()){
                for(int i=1; i<=columns; ++i)
                    asinList.add((String) rs.getObject(i));
            }
            rs.close();
            stmt.close();

            return asinList;

        } catch (Exception e){
            logger.severe("Error in getASIN operation. Error message: " + e);
            return null;
        }
    }

    public static void updateTracker(Connection connection, String updateQuery){
        try {
            assert connection != null;
            Statement stmt = connection.createStatement();
            stmt.executeUpdate(updateQuery);
            connection.commit();
            stmt.close();

        } catch (Exception e){
            logger.severe("Error in updateTracker operation. Error message: " + e);
        }
    }

    public static Map<String, String> readTracker(Connection connection, String selectQuery){
        try {
            assert connection != null;

            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(selectQuery);
            ResultSetMetaData md = rs.getMetaData();
            int columns = md.getColumnCount();

            Map<String, String> DBConfiguration = new LinkedHashMap<>();
            while (rs.next()){
                for(int i=1; i<=columns; ++i)
                    DBConfiguration.put(md.getColumnName(i), "" + rs.getObject(i));
            }
            rs.close();
            stmt.close();

            return DBConfiguration;

        } catch (Exception e){
            logger.severe("Error in readTracker operation. Error message: " + e);
            return null;
        }
    }

}
