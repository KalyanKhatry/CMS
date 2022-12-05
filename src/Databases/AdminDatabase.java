package Databases;

import java.sql.*;
import java.util.*;


public class AdminDatabase {

    public boolean authenticateAdmin(Connection connection, String id, String password) {
        System.out.println("ID" + id);
        System.out.println("PASSWORD" + password);

        try {
            Statement statement = connection.createStatement();
            String getStudentQuery = "SELECT * FROM ADMINISTRATOR WHERE ADMIN_ID = " + id + " AND PASSWORD =" + " '" + password + "'";

            ResultSet rs = statement.executeQuery(getStudentQuery);

            if (rs.next()) {
                String adminId = rs.getString("ADMIN_ID");

                if (Objects.equals(adminId, id)) {
                    statement.close();
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new NoSuchElementException("Not found error");
    }

    public Set<String> getAllCourses(Connection connection) {
        Set<String> coursesSet = new HashSet<String>();

        try {
            Statement statement = connection.createStatement();
            String getCoursesQuery = "SELECT COURSE FROM MODULES";

            ResultSet rs = statement.executeQuery(getCoursesQuery);

            while (rs.next()) {
                String courseName = rs.getString("course");
                coursesSet.add(courseName);
            }
            statement.close();
            return coursesSet;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new NoSuchElementException("Not found error");
    }

    public boolean addModule(Connection connection, String courseName, String moduleName, String code, String level, String semester) {
        try {
            String insertModule = "INSERT INTO MODULES (NAME, CODE, COURSE, LEVEL, SEMESTER) VALUES (?,?,?,?,?)";

            PreparedStatement statement = connection.prepareStatement(insertModule);
            statement.setString(1, moduleName);
            statement.setString(2, code);
            statement.setString(3, courseName);
            statement.setString(4, level);
            statement.setString(5, semester);

            statement.executeUpdate();
            System.out.println("Course has been added");
            statement.close();

            return true;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // Returns an arraylist of array of modules
    public ArrayList<String> getModuleCodes(Connection connection, String courseName) {
        ArrayList<String> moduleCodes = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            String getModulesQuery = "SELECT * FROM MODULES WHERE COURSE =" + " '" + courseName + "'";

            ResultSet rs = statement.executeQuery(getModulesQuery);

            while (rs.next()) {
                moduleCodes.add(rs.getString("code"));
            }
            statement.close();
            return moduleCodes;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<String> getModuleInfo(Connection connection, String moduleCode) {
        ArrayList<String> moduleInfo = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String getModuleQuery = "SELECT * FROM MODULES WHERE CODE = '" + moduleCode + "'";

            ResultSet rs = statement.executeQuery(getModuleQuery);

            while (rs.next()) {
                moduleInfo.add(rs.getString("name"));
                moduleInfo.add(rs.getString("course"));
                moduleInfo.add(rs.getString("level"));
                moduleInfo.add(rs.getString("semester"));
                moduleInfo.add(rs.getString("teachers"));
            }
            statement.close();
            return moduleInfo;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void changeModuleName(Connection connection, String moduleName, String moduleCode) {
        try {
            String changeModule = "UPDATE MODULES SET NAME = ? WHERE CODE = ?";

            PreparedStatement statement = connection.prepareStatement(changeModule);
            statement.setString(1, moduleName);
            statement.setString(2, moduleCode);
            statement.executeUpdate();

            System.out.println("Module Name updated");
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void changeCourseName(Connection connection, String courseName, String newCourseName) {
        try {
            String changeModule = "UPDATE MODULES SET COURSE = ? WHERE COURSE = ?";

            PreparedStatement statement = connection.prepareStatement(changeModule);
            statement.setString(1, newCourseName);
            statement.setString(2, courseName);
            statement.executeUpdate();

            System.out.println("Course Name updated");

            String changeStudentCourse = "UPDATE STUDENTS SET COURSE_NAME = ? WHERE COURSE_NAME = ?";
            PreparedStatement statement1 = connection.prepareStatement(changeStudentCourse);
            statement1.setString(1, newCourseName);
            statement1.setString(2, courseName);
            statement1.executeUpdate();

            statement.close();
            statement1.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteCourse(Connection connection, String courseName) {
        try {
            Statement statement = connection.createStatement();

            String deleteModuleQuery = "DELETE FROM MODULES WHERE COURSE = '" + courseName + "'";


            boolean isDeleted = statement.execute(deleteModuleQuery);

            if (isDeleted) {
                System.out.println("Course Successfully deleted");
            }
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void cancelCourse(Connection connection, boolean isCancelled, String courseName) {
        try {
            String changeModule = "UPDATE MODULES SET CANCELLED = ? WHERE COURSE = ?";

            PreparedStatement statement = connection.prepareStatement(changeModule);
            statement.setBoolean(1, isCancelled);
            statement.setString(2, courseName);
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean checkIfCancelled(Connection connection, String courseName) {
        try {
            Statement statement = connection.createStatement();
            String getModuleQuery = "SELECT CANCELLED FROM MODULES WHERE COURSE = '" + courseName + "'";

            ResultSet rs = statement.executeQuery(getModuleQuery);

            while (rs.next()) {
                boolean isCancelled = rs.getBoolean("cancelled");
                return isCancelled;
            }
            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    public void addTeacherIntoModule(Connection connection, String teacherName, String teachersString, String moduleCode) {
        if (teachersString == null) {
            teachersString = "";
        }

        if (teachersString.contains(teacherName)) {
            return;
        }

        teachersString = teachersString + teacherName + ",";

        String addTeacher = "UPDATE MODULES SET TEACHERS = ? WHERE CODE = ?";

        try {
            PreparedStatement statement = connection.prepareStatement(addTeacher);
            statement.setString(1, teachersString);
            statement.setString(2, moduleCode);

            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addModuleIntoTeacher(Connection connection, String teacherName, String moduleCodes, String moduleCode) {
        if (moduleCodes == null) {
            moduleCodes = "";
        }

        if (moduleCodes.contains(moduleCode)) {
            return;
        }

        moduleCodes = moduleCodes + moduleCode + ",";

        String addModule = "UPDATE TEACHER SET module_code = ? WHERE NAME = ?";

        try {
            PreparedStatement statement = connection.prepareStatement(addModule);
            statement.setString(1, moduleCodes);
            statement.setString(2, teacherName);

            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void removeTeacherFromModule(Connection connection, String teacherName, String teachersString, String moduleCode) {
        teachersString = teachersString.replace(teacherName + ",", "");

        try {
            String changeTeacher = "UPDATE MODULES SET TEACHERS = ? WHERE CODE = ?";

            PreparedStatement statement = connection.prepareStatement(changeTeacher);
            statement.setString(1, teachersString);
            statement.setString(2, moduleCode);
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeModuleFromTeacher(Connection connection, String teacherName, String moduleCode) {
        String moduleCodes = getModuleCodesFromTeacher(connection, teacherName);

        if (moduleCodes == null || Objects.equals(moduleCode, "")) {
            return;
        }

        moduleCodes = moduleCodes.replace(moduleCode + ",", "");

        try {
            String changeTeacher = "UPDATE TEACHER SET module_code = ? WHERE NAME = ?";

            PreparedStatement statement = connection.prepareStatement(changeTeacher);
            statement.setString(1, moduleCodes);
            statement.setString(2, teacherName);
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getModuleCodesFromTeacher(Connection connection, String teacherName) {
        try {
            Statement statement = connection.createStatement();
            String getModulesQuery = "SELECT module_code FROM TEACHER WHERE NAME = '" + teacherName + "'";

            ResultSet rs = statement.executeQuery(getModulesQuery);

            while (rs.next()) {
                String moduleCodes = rs.getString("module_code");
                return moduleCodes;
            }
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getModuleCodesFromStudent(Connection connection, String studentName) {
        try {
            Statement statement = connection.createStatement();
            String getModulesQuery = "SELECT module_code FROM STUDENTS WHERE NAME = '" + studentName + "'";

            ResultSet rs = statement.executeQuery(getModulesQuery);

            while (rs.next()) {
                String moduleCodes = rs.getString("module_code");
                return moduleCodes;
            }
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    // ------------DELETE A SPECIFIC MODULE FROM ALL TEACHERS------------------

    public void deleteModule(Connection connection, String moduleCode) {
        // Gets a list of all teachers present in the database
        // For each instructor deletes a specified module from the instructor table
        // Then remove the module from the modules table
        ArrayList<String> teacherNamesArr = getTeacherNames(connection);
        if (teacherNamesArr == null || teacherNamesArr.size() == 0) {
            return;
        }

        for (int i = 0; i < teacherNamesArr.size(); i++) {
            String teacherName = teacherNamesArr.get(i);
            removeModuleFromTeacher(connection, teacherName, moduleCode);
        }

        try {
            Statement statement = connection.createStatement();

            String deleteModuleQuery = "DELETE FROM MODULES WHERE CODE = '" + moduleCode + "'";
            statement.execute(deleteModuleQuery);
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<String> getTeacherNames(Connection connection) {
        ArrayList<String> teacherNamesArr = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String getModulesQuery = "SELECT name FROM TEACHER";

            ResultSet rs = statement.executeQuery(getModulesQuery);

            while (rs.next()) {
                teacherNamesArr.add(rs.getString("name"));
            }
            statement.close();
            return teacherNamesArr;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void deleteCourseFromTeachers(Connection connection, ArrayList<String> allModuleCodes) {

        for (int i = 0; i < allModuleCodes.size(); i++) {
            String moduleCode = allModuleCodes.get(i);
            System.out.println("Module Code: " + moduleCode);
            deleteModule(connection, moduleCode);
        }
    }


    // ------------DELETE A SPECIFIC MODULE FROM ALL STUDENTS------------------

    public void deleteModuleFromStudents(Connection connection, String moduleCode) {
        // Gets a list of all students present in the database
        // For each student deletes a specified module from the students table

        ArrayList<String> studentNamesArr = getStudentNames(connection);
        System.out.println(studentNamesArr);

        if (studentNamesArr == null || studentNamesArr.size() == 0) {
            return;
        }

        for (int i = 0; i < studentNamesArr.size(); i++) {
            String studentName = studentNamesArr.get(i);
            removeModuleFromStudent(connection, studentName, moduleCode);
        }
    }

    // Removes a specific module from a student
    public void removeModuleFromStudent(Connection connection, String studentName, String moduleCode) {
        String moduleCodes = getModuleCodesFromStudent(connection, studentName);

        if (moduleCodes == null || Objects.equals(moduleCode, "")) {
            return;
        }

        moduleCodes = moduleCodes.replace(moduleCode + ",", "");

        try {
            String changeTeacher = "UPDATE STUDENTS SET module_code = ? WHERE NAME = ?";

            PreparedStatement statement = connection.prepareStatement(changeTeacher);
            statement.setString(1, moduleCodes);
            statement.setString(2, studentName);
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Returns an array of all students in the student table
    public ArrayList<String> getStudentNames(Connection connection) {
        ArrayList<String> studentNamesArr = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String getStudentsQuery = "SELECT name FROM STUDENTS";

            ResultSet rs = statement.executeQuery(getStudentsQuery);

            while (rs.next()) {
                studentNamesArr.add(rs.getString("name"));
            }
            statement.close();
            return studentNamesArr;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Deletes a specific course from all students
    public void deleteCourseFromStudents(Connection connection, String courseName, ArrayList<String> allModuleCodes) {

        for (int i = 0; i < allModuleCodes.size(); i++) {
            String moduleCode = allModuleCodes.get(i);
            deleteModuleFromStudents(connection, moduleCode);
        }

        try {
            String deleteModuleQuery = "UPDATE STUDENTS SET course_name = '' WHERE course_name = ?";

            PreparedStatement statement = connection.prepareStatement(deleteModuleQuery);
            statement.setString(1, courseName);
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Checks a module limit for a level and a semester
    public boolean checkModuleLimit(Connection connection, String level, String semester, String courseName) {
        ArrayList<String> levelModules = new ArrayList<>();

        // They represent the number of semesters in a specific level
        int semesterCount = 0;

        try {
            Statement statement = connection.createStatement();
            String getModulesLevelQuery = "SELECT CODE FROM MODULES WHERE LEVEL = '" + level + "' AND course = '" + courseName + "'";
            String getSemestersQuery = "SELECT SEMESTER FROM MODULES WHERE LEVEL = '" + level + "' AND course = '" + courseName + "'";

            ResultSet rsLevel = statement.executeQuery(getModulesLevelQuery);

            while (rsLevel.next()) {
                levelModules.add(rsLevel.getString("code"));
            }

            ResultSet rsSem = statement.executeQuery(getSemestersQuery);

            while (rsSem.next()) {
                String sem = rsSem.getString("semester");
                // If the provided semester is the first semester
                if (Objects.equals(semester, "1")) {
                    // then check against only the 1st semesters and increase count
                    if (Objects.equals(sem, "1")) {
                        semesterCount++;
                    }

                } else if (Objects.equals(semester, "2")) {
                    if (Objects.equals(sem, "2")) {
                        semesterCount++;
                    }
                }
            }

            System.out.println("SEM COUNT" + semesterCount);
            System.out.println("LEVEL COUNT" + levelModules);
            statement.close();

            if (Objects.equals(level, "6") && levelModules.size() < 10 && semesterCount < 5) {
                return true;
            } else if (levelModules.size() < 7 && semesterCount < 3) {
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
