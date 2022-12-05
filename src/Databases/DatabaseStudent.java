package Databases;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;


public class DatabaseStudent {

    // Returns a list of teacher info
    public ArrayList<String> getUniqueTeacher(ResultSet rs){

        ArrayList<String> teacherInfo = new ArrayList<>();

        try{
            while(rs.next()){
                teacherInfo.add(rs.getString("name"));
                teacherInfo.add(rs.getString("id"));
                teacherInfo.add(rs.getString("module_code"));
            }
            return teacherInfo;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Returns a list of module code from the given result set
    public ArrayList<String> getModuleCodes(ResultSet rs){

        ArrayList<String> moduleCodes = new ArrayList<>();
        try {
            while (rs.next()) {
                moduleCodes.add(rs.getString("code"));
            }
            return moduleCodes;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Returns a list of module names from the given result set
    public ArrayList<String> getModuleNames(ResultSet rs){

        ArrayList<String> moduleNames = new ArrayList<>();
        try {
            while (rs.next()) {
                moduleNames.add(rs.getString("name"));
            }
            return moduleNames;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Returns a list of module teachers from the given result set
    public ArrayList<String> getModuleTeachers(ResultSet rs){

        ArrayList<String> moduleTeachers = new ArrayList<>();
        try {
            while (rs.next()) {
                moduleTeachers.add(rs.getString("teachers"));
            }
            return moduleTeachers;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void updateStudentCourse(Connection connection, String studentId, String courseName){
        try{
            PreparedStatement statement = connection.prepareStatement("UPDATE STUDENTS SET course_name = ? WHERE id = ?");
            statement.setString(1, courseName);
            statement.setString(2, studentId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateStudentModules(Connection connection, String moduleCode, String newModuleCode, String id){

        if(moduleCode == null){
            moduleCode = "";
            moduleCode += (newModuleCode+",");
        }else{
            String[] moduleCodesArray = moduleCode.split(",",-1);

            for(int i = 0; i < moduleCodesArray.length; i++){
                if(Objects.equals(newModuleCode, moduleCodesArray[i])){
                    return;
                }
            }
            moduleCode += (newModuleCode+",");
        }

        try{
            PreparedStatement statement = connection.prepareStatement("UPDATE STUDENTS SET module_code = ? WHERE id = ?");
            statement.setString(1, moduleCode);
            statement.setString(2, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
