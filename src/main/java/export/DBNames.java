package export;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Author: Tibeque
 * Date: 2019/12/31 15:48
 */
public class DBNames {
    public static List<String> getDBNames(String ip, String userName, String password) throws SQLException {
        String url = "jdbc:mysql://" + ip + ":3306";
        String sql = "show databases";

        Connection con = DriverManager.getConnection(url, userName, password);
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        List<String> list = new ArrayList<>();
        while (rs.next()) {
            list.add(rs.getString(1));

        }
        con.close();
        return list;
    }
}