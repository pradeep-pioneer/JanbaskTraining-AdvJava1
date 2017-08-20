package com.janbask.training3;

import java.sql.*;

public class SimpleConnection {

    public static void main(String[] args) {
        makeSimpleConnection();
    }

    static void makeSimpleConnection(){
        Connection connection = null;
        try {
            //1. Creating a connection
            connection = DriverManager.getConnection(Config.DATABASE_URL, Config.DATABASE_USERNAME, Config.DATABASE_PASSWORD);
            //2. Creating a statement
            String sql = "select * from ut_category";
            PreparedStatement statement = connection.prepareStatement(sql);
            //3. Executing a statement
            ResultSet resultSet = statement.executeQuery();
            //4. Retrieving values from ResultSet
            while(resultSet.next()){
                String categoryName = resultSet.getString(2);
                String categoryDescription = resultSet.getString(3);
                System.out.printf("\nCategory Name: %s\t\t\tDescription: %s", categoryName, categoryDescription);
            }
            //5. Cleanup
            resultSet.close();
            statement.close();
            connection.close();
        }//6. Exception handling and cleanup
        catch (SQLException exception){
            System.out.printf("\nError: %s\nSql State: %s", exception.getMessage(), exception.getSQLState());
            exception.printStackTrace();
        }
        finally {
            try{
                if(connection!=null)
                    connection.close();
            }
            catch (SQLException exception){
                exception.printStackTrace();
            }
        }
    }
}
