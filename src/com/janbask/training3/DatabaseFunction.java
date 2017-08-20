package com.janbask.training3;

import java.sql.*;

public class DatabaseFunction {
    private static final String CATEGORY_COUNT_FUNCTION_CALL = "{ ? = call uf_get_categories_count()}";
    public static void main(String[] args) {
        callDatabaseFuntion();
    }

    static void callDatabaseFuntion(){
        Connection connection = null;
        try {
            //1. Creating a connection
            connection = DriverManager.getConnection(Config.DATABASE_URL, Config.DATABASE_USERNAME, Config.DATABASE_PASSWORD);
            //2. Creating a statement
            CallableStatement statement = connection.prepareCall(CATEGORY_COUNT_FUNCTION_CALL);
            //3. Preparing and executing a statement
            statement.registerOutParameter(1, Types.INTEGER);
            statement.execute();
            //4. Retrieving values from result
            int count = statement.getInt(1);
            System.out.printf("\nTotal Categories: %s", count);
            //5. Cleanup
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
