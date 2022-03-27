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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class MySqlPlayerDao extends MySqlDao implements PlayerDaoInterface
{

    @Override
    public List<team> findAllPlayers() throws DaoException
    {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        List<team> playersList = new ArrayList<>();

        try
        {
            //Get connection object using the methods in the super class (MySqlDao.java)...
            connection = this.getConnection();

            String query = "SELECT * FROM users";
            ps = connection.prepareStatement(query);

            //Using a PreparedStatement to execute SQL...
            resultSet = ps.executeQuery();
            while (resultSet.next())
            {
                String playerName = resultSet.getString("playerName");
                String teamName = resultSet.getString("teamName");
                int salary = resultSet.getInt("salary");
                String manager = resultSet.getString("manager");
                String coach = resultSet.getString("coach");
                String city = resultSet.getString("city");
                team p = new team(playerName, teamName, salary, manager, coach, city);
                playersList.add(p);
            }
        } catch (SQLException e)
        {
            throw new DaoException("findAllPlayeresultSet() " + e.getMessage());
        } finally
        {
            try
            {
                if (resultSet != null)
                {
                    resultSet.close();
                }
                if (ps != null)
                {
                    ps.close();
                }
                if (connection != null)
                {
                    freeConnection(connection);
                }
            } catch (SQLException e)
            {
                throw new DaoException("findAllPlayers() " + e.getMessage());
            }
        }
        return playersList;
    }


    @Override
    public List<team> findPlayerBySalary(int salary) throws DaoException
    {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<team> teams = new ArrayList<>();
        try {
            connection = this.getConnection();

            String query = "SELECT * FROM users WHERE salary = ? ";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, String.valueOf(salary));

            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String playerName = resultSet.getString("playerName");
                String teamName = resultSet.getString("teamName");
                salary = resultSet.getInt("salary");
                String manager = resultSet.getString("manager");
                String coach = resultSet.getString("coach");
                String city = resultSet.getString("city");
                team p = new team(playerName, teamName, salary, manager, coach, city);
                teams.add(p);

            }
        } catch (SQLException e)
        {
            throw new DaoException("findPlayerById() " + e.getMessage());
        } finally
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
                throw new DaoException("findPlayerById() " + e.getMessage());
            }
        }
        return teams;
    }
//
//    @Override
//    public List<Player> deletePlayerById( int id ) throws DaoException
//    {
//        Connection connection = null;
//        PreparedStatement preparedStatement = null;
//        ResultSet resultSet = null;
//        List<Player> usersList = new ArrayList<>();
//
//        try
//        {
//            //Get connection object using the methods in the super class (MySqlDao.java)...
//            connection = this.getConnection();
//
//            String query = "SELECT * FROM USER where LAST_NAME LIKE ?";
//            preparedStatement = connection.prepareStatement(query);
//            preparedStatement.setString( 1, "%" + subString + "%" );
//
//
//            //Using a PreparedStatement to execute SQL...
//            resultSet = preparedStatement.executeQuery();
//            while (resultSet.next())
//            {
//                int userId = resultSet.getInt("USER_ID");
//                String username = resultSet.getString("USERNAME");
//                String password = resultSet.getString("PASSWORD");
//                String lastname = resultSet.getString("LAST_NAME");
//                String firstname = resultSet.getString("FIRST_NAME");
//                Player u = new Player(userId, firstname, lastname, username, password);
//                usersList.add(u);
//            }
//        } catch (SQLException e)
//        {
//            throw new DaoException("findAllUsersLastNameContains() " + e.getMessage());
//        } finally
//        {
//            try
//            {
//                if (resultSet != null)
//                {
//                    resultSet.close();
//                }
//                if (preparedStatement != null)
//                {
//                    preparedStatement.close();
//                }
//                if (connection != null)
//                {
//                    freeConnection(connection);
//                }
//            } catch (SQLException e)
//            {
//                throw new DaoException("findAllUsersLastNameContains() " + e.getMessage());
//            }
//        }
//        return usersList;     // may be empty
//    }
}

