package main;

import java.sql.*;

public class UpdateSqlClass {
    public UpdateSqlClass() {
    }

    public void InsertIntoDatabase(Connection connection,Statement statement, String username, int points) throws SQLException {
        ResultSet resultSet=statement.executeQuery("select * from videogame");
        boolean existing=false;
        String sql;
        PreparedStatement stmt;
        while(resultSet.next())
        {
            if(resultSet.getString("User").equalsIgnoreCase(username))
            {
                if(resultSet.getInt("Score")<=points)
                {
                    existing=true;
                }
                else
                {
                    return;
                }

            }
        }
        if(existing)
        {
            sql="update videogame set score = ? where user = ?";
            stmt=  connection.prepareStatement(sql);
            stmt.setInt(1,points);
            stmt.setString(2,username);
        }
        else
        {
            sql="insert into videogame values(?,?)";
            stmt=  connection.prepareStatement(sql);
            stmt.setInt(2,points);
            stmt.setString(1,username);
        }

        stmt.executeUpdate();


    }
}
