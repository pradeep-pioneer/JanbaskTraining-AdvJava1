package com.janbask.training3;

import java.sql.*;
import java.util.Scanner;

public class StoredProcedures {

    private static final String CATEGORY_LIST_PROCEDURE_CALL = "call usp_get_all_categories();";
    private static final String CATEGORY_DETAIL_PROCEDURE_CALL = "call usp_get_category_details(?);";

    public static void main(String[] args) {
        callStoredProcedure();
        System.out.println("\n\nEnter the category id for which you want to see the details");
        Scanner scanner = new Scanner(System.in);
        int categoryId = Integer.parseInt(scanner.nextLine());
        callStoredProcedureDetails(categoryId);
    }

    static void callStoredProcedure(){
        Connection connection = null;
        try{
            connection = DriverManager.getConnection(Config.DATABASE_URL, Config.DATABASE_USERNAME, Config.DATABASE_PASSWORD);
            CallableStatement statement = connection.prepareCall(CATEGORY_LIST_PROCEDURE_CALL);
            ResultSet categories = statement.executeQuery();
            while(categories.next()){
                int categoryId = categories.getInt(1);
                String categoryName = categories.getString(2);
                String categoryDescription = categories.getString(3);
                System.out.printf("\nId: %s\tCategory Name: %s\t\t\tDescription: %s", categoryId, categoryName, categoryDescription);
            }
            categories.close();
            statement.close();
            connection.close();
        }catch (SQLException exception){
            exception.printStackTrace();
        }finally {
            try{
                if(connection!=null)
                    connection.close();
            }
            catch (SQLException exception){
                exception.printStackTrace();
            }
        }
    }

    static void callStoredProcedureDetails(int id){
        Connection connection = null;
        try{
            connection = DriverManager.getConnection(Config.DATABASE_URL, Config.DATABASE_USERNAME, Config.DATABASE_PASSWORD);
            CallableStatement statement = connection.prepareCall(CATEGORY_DETAIL_PROCEDURE_CALL);
            statement.setInt("categoryId", id);
            ResultSet category = statement.executeQuery();
            while(category.next()){
                String categoryName = category.getString(1);
                String categoryDescription = category.getString(2);
                System.out.printf("\nCategory Name: %s\t\t\tDescription: %s\n", categoryName, categoryDescription);
            }
            category.close();
            statement.close();
            connection.close();
        }catch (SQLException exception){
            exception.printStackTrace();
        }finally {
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
