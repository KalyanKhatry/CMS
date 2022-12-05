package Databases;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DatabaseTeacher {

    // Gets Module Name from Module Code
    public String getModuleName(Connection connection, String moduleCode) {

        try {
            Statement statement = connection.createStatement();
            String getModuleNameQuery = "SELECT * FROM MODULES WHERE CODE = " + "'" + moduleCode + "'";

            ResultSet rs = statement.executeQuery(getModuleNameQuery);

            if(rs.next()) {
                String moduleName = rs.getString("name");
                statement.close();
                return moduleName;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Gets list of students enrolled in a specific module
    public ArrayList<String> getStudents(Connection connection, String moduleCode) {
        ArrayList<String> studentsArray = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String getStudentsQuery = "SELECT NAME FROM STUDENTS WHERE module_code LIKE " + "'%" + moduleCode + "%'";

            ResultSet rs = statement.executeQuery(getStudentsQuery);

            while (rs.next()) {
                String studentName = rs.getString("name");
                studentsArray.add(studentName);
            }
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return studentsArray;
    }

    // Gets list of studentIds enrolled in a specific module
    public ArrayList<String> getStudentId(Connection connection, String moduleCode) {
        ArrayList<String> studentIdArray = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String getStudentsQuery = "SELECT ID FROM STUDENTS WHERE module_code LIKE " + "'%" + moduleCode + "%'";

            ResultSet rs = statement.executeQuery(getStudentsQuery);

            while (rs.next()) {
                String studentName = rs.getString("id");
                studentIdArray.add(studentName);
            }
            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return studentIdArray;
    }
}
