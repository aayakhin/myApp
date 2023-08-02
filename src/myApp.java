
import models.Person;

import java.sql.*;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class myApp {
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String DB_NAME = "postgres";
    private static final String DB_PASSWORD = "";
    private static final String DB_DRIVER = "org.postgresql.Driver";

    public static void main(String[] args) {
        Connection connection = null;
        Statement statement = null;

        switch (args[0]) {
            case "1":
                connection = getConnection();
                try {
                    statement = connection.createStatement();
                    statement.executeUpdate("Create Table Test(name varchar, date_of_birth date, gender varchar)");
                    statement.close();
                    connection.close();
                } catch (Exception e) {
                    System.err.println(e.getClass().getName() + ": " + e.getMessage());
                    System.exit(0);
                }
                System.out.println("Table Created successfully");
                break;
            case "2":
                connection = getConnection();
                String name = args[1];
                String date = args[2];
                String gender = args[3];

                try {
                    PreparedStatement preparedStatement =
                            connection.prepareStatement("INSERT INTO Test (name, date_of_birth, gender) VALUES (?,?,?)");

                    preparedStatement.setString(1, name);
                    preparedStatement.setDate(2, Date.valueOf(date));
                    preparedStatement.setString(3, gender);
                    preparedStatement.executeUpdate();
                    System.out.println("Person added");
                    connection.close();
                } catch (SQLException throwables){
                    throwables.printStackTrace();
                }
                break;
            case "3":
                connection=getConnection();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                try {
                    statement = connection.createStatement();
                    String SQL = "SELECT DISTINCT ON (name, date_of_birth) * FROM test ORDER BY name";
                    ResultSet resultSet = statement.executeQuery(SQL);
                    while (resultSet.next()){
                        System.out.println(
                        resultSet.getString("name") + " " +
                        resultSet.getString("date_of_birth") + " " +
                        resultSet.getString("gender") + " " +
                                " Возраст: " + Period.between(LocalDate.parse(resultSet.getString("date_of_birth"), formatter), LocalDate.now()).getYears());

                    }
                    statement.close();
                    connection.close();
                } catch (SQLException throwables){
                    throwables.printStackTrace();
                }
                break;
            case "4":
                PeopleGenerator peopleGenerator = new PeopleGenerator();
                List<Person> people = new ArrayList<>();
                connection = getConnection();
                try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO Test (name, date_of_birth, gender) VALUES (?,?,?)")){
                    for (int i=0; i<1000000; i++) {
                        people.add(peopleGenerator.generate());
                        preparedStatement.setString(1, people.get(i).getName());
                        preparedStatement.setDate(2, Date.valueOf(people.get(i).getDate_of_birth()));
                        preparedStatement.setString(3, people.get(i).getGender());
                        preparedStatement.addBatch();
                    }
                    List<Person> hundredPeople = new ArrayList<>();
                    for (int i=0; i<100; i++) {
                        hundredPeople.add(peopleGenerator.generateHundred());
                        preparedStatement.setString(1, hundredPeople.get(i).getName());
                        preparedStatement.setDate(2, Date.valueOf(hundredPeople.get(i).getDate_of_birth()));
                        preparedStatement.setString(3, hundredPeople.get(i).getGender());
                        preparedStatement.addBatch();
                    }
                    preparedStatement.executeBatch();
                    preparedStatement.close();
                    connection.close();
                } catch (SQLException throwable){
                    throwable.printStackTrace();
                }

                break;
            case "5":
                long timeBefore = System.currentTimeMillis();
                connection=getConnection();
                try {
                    statement = connection.createStatement();
                    ResultSet resultSet= statement.executeQuery("SELECT * FROM test WHERE (name LIKE 'F%' and gender = 'Male')");
                    while (resultSet.next()){
                        System.out.println(
                        resultSet.getString("name") + " " +
                        resultSet.getString("date_of_birth") + " " +
                        resultSet.getString("gender"));
                    }
                    statement.close();
                    connection.close();
                } catch (SQLException throwable){
                    throwable.printStackTrace();
                }
                long timeAfter = System.currentTimeMillis();
                System.out.println("Время выполнения: " + (timeAfter-timeBefore));
                break;
            case "6":
                connection=getConnection();
                try {
                    statement = connection.createStatement();
                    statement.executeUpdate("create index on test (name, gender)");
                    statement.close();
                    connection.close();
                } catch (SQLException throwable){
                    throwable.printStackTrace();
                }
                break;
        }
    }
    static Connection getConnection(){
        Connection connection = null;
        try {
            Class.forName(DB_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_NAME, DB_PASSWORD);
            System.out.println("Database Connected ..");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }
}