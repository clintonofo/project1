package dkip.oop.DAOs;

/** OOP Feb 2022
 *
 * Data Access Object (DAO) for User table with MySQL-specific code
 * This 'concrete' class implements the 'UserDaoInterface'.
 *
 * The DAO will contain the SQL query code to interact with the database,
 * so, the code here is specific to a particular database (e.g. MySQL or Oracle etc...)
 * No SQL queries will be used in the Business logic layer of code, thus, it
 * will be independent of the database specifics.
 *
 * The Business Logic layer is only permitted to access the database by calling
 * methods provided in the Data Access Layer - i.e. by callimng the DAO methods.
 *
 */


import dkip.oop.DTOs.team;
import dkip.oop.Exceptions.DaoException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class MySqlPlayerDao extends MySqlDao implements PlayerDaoInterface {

    @Override
    public List<team> findAllPlayers() throws DaoException {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        List<team> playersList = new ArrayList<>();

        try {
            //Get connection object using the methods in the super class (MySqlDao.java)...
            connection = this.getConnection();

            String query = "SELECT * FROM user";
            ps = connection.prepareStatement(query);

            //Using a PreparedStatement to execute SQL...
            resultSet = ps.executeQuery();
            while (resultSet.next()) {
                String playerName = resultSet.getString("player_name");
                String teamName = resultSet.getString("team_name");
                int salary = resultSet.getInt("salary");
                String manager = resultSet.getString("manager_name");
                String coach = resultSet.getString("coach_name");
                String city = resultSet.getString("city");
                team p = new team(playerName, teamName, salary, manager, coach, city);
                playersList.add(p);
            }
        } catch (SQLException e) {
            throw new DaoException("findAllPlayeresultSet() " + e.getMessage());
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (connection != null) {
                    freeConnection(connection);
                }
            } catch (SQLException e) {
                throw new DaoException("findAllPlayers() " + e.getMessage());
            }
        }
        return playersList;
    }


    @Override
    public List<team> findPlayerBySalary(int salary) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<team> teams = new ArrayList<>();
        try {
            connection = this.getConnection();

            String query = "SELECT * FROM user WHERE salary = ? ";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, String.valueOf(salary));

            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String playerName = resultSet.getString("player_name");
                String teamName = resultSet.getString("team_name");
                salary = resultSet.getInt("salary");
                String manager = resultSet.getString("manager_name");
                String coach = resultSet.getString("coach_name");
                String city = resultSet.getString("city");
                team p = new team(playerName, teamName, salary, manager, coach, city);
                teams.add(p);

            }
        } catch (SQLException e) {
            throw new DaoException("findPlayerById() " + e.getMessage());
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    freeConnection(connection);
                }
            } catch (SQLException e) {
                throw new DaoException("findPlayerById() " + e.getMessage());
            }
        }
        return teams;
    }

    //
    @Override
    public List<team> deletePlayerBySalary( int salary ) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<team> t = new ArrayList<>();
        try{
            connection = this.getConnection();
            String query="DELETE FROM user WHERE salary = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,salary);
            preparedStatement.executeUpdate();
            Statement statement = connection.createStatement();
            resultSet = preparedStatement.executeQuery("SELECT * FROM user");
            if (resultSet.next())
            {
                 salary = resultSet.getInt("salary");
                String playerName = resultSet.getString("player_name");
                String teamName = resultSet.getString("team_name");
                String manager = resultSet.getString("manager_name");
                String coach = resultSet.getString("coach_name");
                String city = resultSet.getString("city");

                team z = new team(playerName, teamName, salary, manager, coach, city);
                t.add(z);

            }
        }catch(SQLException e){
            throw new DaoException("deletePlayerBySalary: " + e.getMessage());
        }finally
        {
            try
            {
                if (resultSet != null)
                {
                    resultSet.close();
                }
                if (preparedStatement != null)
                {
                    preparedStatement.close();
                }
                if (connection != null)
                {
                    freeConnection(connection);
                }
            } catch (SQLException e)
            {
                throw new DaoException("deletePlayerBySalary() " + e.getMessage());
            }
        }
        return t;

    }

    @Override
    public void addPlayer(int salary,String playerName,String teamName, String manager, String coach, String city) throws DaoException{
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = this.getConnection();
            // Execute a query
            System.out.println("Inserting records into the table...");
            String query = "INSERT INTO user VALUES (null, ?,?,?,?,?,?)";
            preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, playerName);
            preparedStatement.setString(2, teamName);
            preparedStatement.setString(3, manager);
            preparedStatement.setString(4, coach);
            preparedStatement.setString(5, city);
            preparedStatement.setInt(6, salary);
            System.out.println("Inserted records into the table...");
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new DaoException("addPlayer() " + e.getMessage());
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    freeConnection(connection);
                }
            } catch (SQLException e) {


            }
        }
    }
}

