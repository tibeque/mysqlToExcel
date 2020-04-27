package export;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import entity.Excel;
import util.ExcelUtil;


public class ExportDataFromMysql {

    public static String userName;
    public static String password;
    public static String ip;
    public static List<String> dbNames;
    public static String path;
    public static String fileName;

    public static String createTimeColName = "create_time";
    public static String lastUUpdateTimeColName = "update_time";


    public static List<Excel> excelList = new ArrayList<>();

    static {
        Properties prop = new Properties();
        try {
            prop.load(ExportDataFromMysql.class.getClassLoader().getResourceAsStream("init.properties"));
        } catch (IOException e) {
            System.out.println("fail to load file");
            e.printStackTrace();
        }
        ip = prop.getProperty("ip");
        userName = prop.getProperty("userName");
        password = prop.getProperty("password");
        path = prop.getProperty("path");
        fileName = prop.getProperty("fileName");

        try {
            dbNames = DBNames.getDBNames(ip, userName, password);
        } catch (SQLException e) {
            System.out.println("fail to get the name of database");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws SQLException, IOException {
        String tableName = "";
        String totalTmp = "";
        String crTimeTmp = "";
        String lastUpTimeTmp = "";
        for (String dbName : dbNames) {

            String url = "jdbc:mysql://" + ip + ":3306/" + dbName + "?useSSL=false&allowMultiQueries=true&useUnicode=true&characterEncoding=UTF-8&rewriteBatchedStatements=true";
            Connection conn = DriverManager.getConnection(url, userName, password);
            DatabaseMetaData metaDatas = conn.getMetaData();
            ResultSet rs = metaDatas.getTables(null, dbName, null, new String[]{"TABLE"});

            while (rs.next()) {
                Excel excel = new Excel();
                tableName = rs.getString("TABLE_NAME");
                Statement s = conn.createStatement();


                ResultSet qryRs = s.executeQuery("select count(1) cnt from `" + tableName + "`");
                if (qryRs.next()) {
                    totalTmp = String.valueOf(qryRs.getInt(1));
                    excel.setTotal(totalTmp);
                }

                //Get last created time
                try {
                    ResultSet colRs = metaDatas.getColumns(null, dbName, tableName, createTimeColName);
                    if (colRs.next()) {
                        qryRs = s.executeQuery("select max(DATE(" + createTimeColName + ")) cnt from `" + tableName + "`");
                        if (qryRs.next()) {
                            Object obj = qryRs.getObject(1);
                            if (obj != null) {
                                crTimeTmp = String.valueOf(obj.toString().substring(0, 10));
                                excel.setCreateTime(crTimeTmp);
                            }

                        }
                    }
                    //Get last update time

                    colRs = metaDatas.getColumns(null, dbName, tableName, lastUUpdateTimeColName);
                    if (colRs.next()) {
                        qryRs = s.executeQuery("select max(DATE(" + lastUUpdateTimeColName + ")) cnt from  `" + tableName + "`");
                        if (qryRs.next()) {
                            Object obj = qryRs.getObject(1);
                            if (obj != null) {
                                lastUpTimeTmp = String.valueOf(obj.toString().substring(0, 10));
                                excel.setLastUpdateTiem(lastUpTimeTmp);
                            }
                        }
                    }
                    excel.setIp(ip);
                    excel.setDbName(dbName);
                    excel.setTableName(tableName);
                    excelList.add(excel);
                    System.out.println(ip + "==" + dbName + "==" + tableName);
                } catch (Exception e) {
                    System.out.println(ip + ":" + dbName + ":" + tableName + "Error...");
                    continue;
                }
            }
            conn.close();
        }
        String context = ExcelUtil.singleGenExcel(excelList);
        ExcelUtil.writeExcel(path, fileName, context, true);
    }
}
