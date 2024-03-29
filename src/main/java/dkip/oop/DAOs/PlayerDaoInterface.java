package dkip.oop.DAOs;

/** OOP Feb 2022
 * UserDaoInterface
 *
 * Declares the methods that all UserDAO types must implement,
 * be they MySql User DAOs or Oracle User DAOs etc...
 *
 * Classes from the Business Layer (users of this DAO interface)
 * should use reference variables of this interface type to avoid
 * dependencies on the underlying concrete classes (e.g. MySqlUserDao).
 *
 * More sophisticated implementations will use a factory
 * method to instantiate the appropriate DAO concrete classes
 * by reading database configuration information from a
 * configuration file (that can be changed without altering source code)
 *
 * Interfaces are also useful when testing, as concrete classes
 * can be replaced by mock DAO objects.
 */


import dkip.oop.DTOs.team;
import dkip.oop.Exceptions.DaoException;
import org.json.JSONObject;

import java.util.List;

public interface PlayerDaoInterface
{
    public List<dkip.oop.DTOs.team> findAllPlayers() throws DaoException;

    public List<team> findPlayerBySalary(int salary) throws DaoException;

   public List<team> deletePlayerBySalary( int salary ) throws DaoException;

    public void addPlayer(int salary,String playerName,String teamName, String manager, String coach, String city) throws DaoException;

    public List<JSONObject> FindAllPlayersJsonForamt() throws DaoException;

    public List<JSONObject> FindAllPlayersUsingSalaryJsonForamt(int salary)  throws DaoException;
}

