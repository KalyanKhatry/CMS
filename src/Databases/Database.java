package Databases;

import java.sql.*;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Objects;


public class Database {

    public Connection createConnection() {
        String url = "jdbc:mysql://localhost:3306/coursemanagementsystem";
        String username = "root";
        String password = "";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url, username, password);
            System.out.println("Database Connection Successful");
            return connection;

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public ArrayList<String> getUniqueStudent(Connection connection, int studentId, String password){

        try {
            Statement statement = connection.createStatement();
            String getStudentQuery = "SELECT * FROM STUDENTS WHERE ID = " + studentId + " AND PASSWORD =" + " '" + password + "'" ;

            ResultSet rs = statement.executeQuery(getStudentQuery);


            if(rs.next()){
                ArrayList<String> studentInfo = new ArrayList<>();

                studentInfo.add(rs.getString("name"));
                studentInfo.add(rs.getString("course_name"));
                studentInfo.add(rs.getString("module_code"));

                String studentLevel = rs.getString("level");
                studentInfo.add(Objects.equals(studentLevel, "0") ? "4" : studentLevel);
                studentInfo.add(rs.getString("id"));
                studentInfo.add(rs.getString("semester"));
                studentInfo.add(rs.getString("password"));

                return studentInfo;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        throw new NoSuchElementException("Not found error");
    }


    public ResultSet getUniqueTeacher(Connection connection, String teacherId, String password){
        try {
            Statement statement = connection.createStatement();
            String getTeacherQuery = "SELECT * FROM TEACHER WHERE ID = " + teacherId + " AND PASSWORD =" + " '" + password + "'" ;

            ResultSet rs = statement.executeQuery(getTeacherQuery);
            return rs;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new NoSuchElementException("Not found Error");
    }

    public void getStudents(Connection connection) {
        try{
            Statement statement = connection.createStatement();
            String getStudentsQuery = "SELECT * FROM STUDENT";

            ResultSet rs = statement.executeQuery(getStudentsQuery);

            while(rs.next()){
                String name = rs.getString("name");
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
    }


    public ResultSet getModules(Connection connection, String level, String semester, String courseName){
        try{
            String getModules = "SELECT * FROM MODULES WHERE LEVEL = ? AND SEMESTER = ? AND course = ?";

            PreparedStatement statement = connection.prepareStatement(getModules);
            statement.setString(1, level);
            statement.setString(2, semester);
            statement.setString(3, courseName);

            ResultSet rs = statement.executeQuery();

            return rs;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int checkIfCancelled(Connection connection, String courseName){
        try{
            String getModules = "SELECT CANCELLED FROM MODULES WHERE COURSE = ? ";

            PreparedStatement statement = connection.prepareStatement(getModules);
            statement.setString(1, courseName);

            ResultSet rs = statement.executeQuery();

            int cancelled = 0;
            if (rs.next()){
                cancelled = rs.getInt("cancelled");
            }
            return cancelled;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}

