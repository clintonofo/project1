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


import dkip.oop.ComparePlayerSalary;
import dkip.oop.DTOs.team;
import dkip.oop.Exceptions.DaoException;
import dkip.oop.SortType;
import dkip.oop.UserSalaryComparator;
import org.json.JSONObject;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;


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
                throw new DaoException("addPlayer() " + e.getMessage());

            }
        }
    }

    public List<JSONObject> FindAllPlayersJsonForamt() throws DaoException
    {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        List<team> playersList = new ArrayList<>();
        List<JSONObject> js=new ArrayList<JSONObject>();
        List<String> Names=new ArrayList<String>();
        try
        {
            connection = this.getConnection();

            String query = "SELECT * FROM user";
            ps = connection.prepareStatement(query);


            resultSet = ps.executeQuery();
            ResultSetMetaData md=resultSet.getMetaData();
            int columncount=md.getColumnCount();
            for(int i=1;i<columncount;i++)
            {
                Names.add(md.getColumnName(i).toUpperCase());
            }

            while (resultSet.next())
            {
                try
                {
                    JSONObject obj=new JSONObject();



                    for(int i=1;i<columncount;i++)
                    {
                        String Name=Names.get(i-1);
                        String Value=resultSet.getString(i);
                        obj.put(Name,Value);

                    }

                    js.add(obj);

                }
                catch(Exception e2)
                {

                }
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
        return js;


    }

    public List<JSONObject> FindAllPlayersUsingSalaryJsonForamt(int salary)  throws DaoException
    {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        List<team> playersList = new ArrayList<>();
        List<JSONObject> js=new ArrayList<JSONObject>();
        List<String> Names=new ArrayList<String>();
        try
        {
            connection = this.getConnection();

            String query = "SELECT * FROM user WHERE salary='"+salary+"'";
            ps = connection.prepareStatement(query);

            resultSet = ps.executeQuery();
            ResultSetMetaData md=resultSet.getMetaData();
            int columncount=md.getColumnCount();
            for(int i=1;i<columncount;i++)
            {
                Names.add(md.getColumnName(i).toUpperCase());
            }

            while (resultSet.next())
            {
                try
                {
                    JSONObject obj=new JSONObject();



                    for(int i=1;i<columncount;i++)
                    {
                        String Name=Names.get(i-1);
                        String Value=resultSet.getString(i);
                        obj.put(Name,Value);

                    }

                    js.add(obj);

                }
                catch(Exception e2)
                {

                }
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
        return js;


    }
    public List<JSONObject> FindAllPlayersUsingIdJsonForamt(int Id)  throws DaoException
    {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        List<team> playersList = new ArrayList<>();
        List<JSONObject> js=new ArrayList<JSONObject>();
        List<String> Names=new ArrayList<String>();
        try
        {
            connection = this.getConnection();

            String query = "SELECT * FROM user WHERE user_id='"+Id+"'";
            ps = connection.prepareStatement(query);

            resultSet = ps.executeQuery();
            ResultSetMetaData md=resultSet.getMetaData();
            int columncount=md.getColumnCount();
            for(int i=1;i<columncount;i++)
            {
                Names.add(md.getColumnName(i).toUpperCase());
            }

            while (resultSet.next())
            {
                try
                {
                    JSONObject obj=new JSONObject();



                    for(int i=1;i<columncount;i++)
                    {
                        String Name=Names.get(i-1);
                        String Value=resultSet.getString(i);
                        obj.put(Name,Value);

                    }

                    js.add(obj);

                }
                catch(Exception e2)
                {

                }
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
        return js;


    }

    public List<team> findPlayerUsingFilter(ComparePlayerSalary filter) throws DaoException {
        List<team> filterPlayers = new ArrayList<>();
        for(team player: findAllPlayers()){
            if(filter.compare(player, new team()) <= 0){
                filterPlayers.add(player);
                System.out.println(filterPlayers);
            }
        }
        return filterPlayers;
    }

    public static void filterPlayer() throws DaoException {
        PlayerDaoInterface IPlayerDao = new MySqlPlayerDao();
        PriorityQueue<team> filterPlayers = new PriorityQueue<>(new ComparePlayerSalary(SortType.Ascending));
        filterPlayers.addAll(((MySqlPlayerDao) IPlayerDao).findPlayerUsingFilter(new ComparePlayerSalary(SortType.Ascending)));
        System.out.println("\n Filter Players by Salary");
        if (filterPlayers.isEmpty())
            System.out.println("There are no Players");
        else {
            for (team Player : filterPlayers)
                System.out.println("Player: " + Player.toString());
        }
    }



    public List<team> deletePlayerBySalaryy( int salary )  throws DaoException
    {
        Connection connection = null;
        ResultSet resultSet = null;
        List<team> teams = new ArrayList<>();
        try {
            connection = this.getConnection();
            Statement stmt=connection.createStatement();
            String query = "delete  from user where salary='"+salary+"'";

            stmt.executeUpdate(query);
            System.out.print("Data is Successfully delete");
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
    public static void main(String[] args)
            throws DaoException   {

        boolean ver=true;
        while(ver)
        {
            List<team> Value = new ArrayList<>();
            MySqlPlayerDao dao=new MySqlPlayerDao();
            Scanner sc=new Scanner(System.in);
            System.out.println("1) Find All Entities");
            System.out.println("2) Find and display  All Entities by Using Salary");
            System.out.println("3) Delete The Player");
            System.out.println("4) Add New Player");
            System.out.println("5) List All Entities Using Filter");
            System.out.println("6) List All Entity in JSON Foramte");
            System.out.println("7) List All Player Using Their Salary in JSON Foramte");
            System.out.println("8) Listing  All Record Using Id in JSON Foramte");
            System.out.println("9) Display All Enitiy in JSON Formate");
            System.out.println("11) Delete Entity by Salary");
            System.out.println("12) Back");
            int a=sc.nextInt();
            if(a==1)
            {
                Value=dao.findAllPlayers();


                for(int i=0;i<Value.size();i++)
                {
                    System.out.println("Player Name  "+Value.get(i).getPlayerName()+"\t    "+"Team Name  "+Value.get(i).getTeamName()+"\t   "+"Salary "+Value.get(i).getSalary()+"\t  "+"Manager Name  "+Value.get(i).getManager()+"\t "+"Coach Name  "+Value.get(i).getCoach()+"\t  "+"City  "+Value.get(i).getCity());
                }

            }
            else if(a==2)
            {

                System.out.print("Enter The Player Salary");
                int Salay=sc.nextInt();
                Value=dao.findPlayerBySalary(Salay);
                for(int i=0;i<Value.size();i++)
                    System.out.println("Player Name  "+Value.get(i).getPlayerName()+"\t    "+"Team Name  "+Value.get(i).getTeamName()+"\t   "+"Salary "+Value.get(i).getSalary()+"\t  "+"Manager Name  "+Value.get(i).getManager()+"\t "+"Coach Name  "+Value.get(i).getCoach()+"\t  "+"City  "+Value.get(i).getCity());


            }
            else if(a==3)
            {

                System.out.print("Enter Player Salary That you want to delete ");
                int delete1=sc.nextInt();
                dao.deletePlayerBySalaryy(delete1);


            }
            else if(a==4)
            {

                System.out.println("Enter The Player Name");
                String playerName=sc.next();
                System.out.println("Enter The Team Name");
                String teamName=sc.next();
                System.out.println("Enter The Manager Name ");
                String managerName=sc.next();
                System.out.println("Enter The Coach Name");
                String coachName=sc.next();
                System.out.println("Enter The City ");
                String city=sc.next();
                System.out.println("Enter The Salary ");
                int salary=sc.nextInt();
                dao.addPlayer(salary,teamName,managerName,coachName,city,playerName);

                for(int i=0;i<Value.size();i++)
                {
                    System.out.println("Player Name  "+Value.get(i).getPlayerName()+"\t    "+"Team Name  "+Value.get(i).getTeamName()+"\t   "+"Salary "+Value.get(i).getSalary()+"\t  "+"Manager Name  "+Value.get(i).getManager()+"\t "+"Coach Name  "+Value.get(i).getCoach()+"\t  "+"City  "+Value.get(i).getCity());
                }
            }
            else if(a==5)
            {System.out.println("List entities using a filter");
                filterPlayer();

            }
            else if(a==6)
            {
                System.out.println("Data in JSON Formate");
                for(int i=0;i<dao.FindAllPlayersJsonForamt().size();i++)
                {
                    System.out.println(i+" "+dao.FindAllPlayersJsonForamt().get(i));
                }

            }
            else if(a==7)
            {

                System.out.println("Data in JSON ");
                System.out.println("Enter Player Salary");
                int salary=sc.nextInt();

                for(int i=0;i<dao.FindAllPlayersUsingSalaryJsonForamt(salary).size();i++)
                {
                    System.out.println(dao.FindAllPlayersUsingSalaryJsonForamt(salary).get(i));
                }

            }
            else if(a==8)
            {
                System.out.print("Enter The Player Id");
                int id=sc.nextInt();

                for(int i=0;i<dao.FindAllPlayersUsingIdJsonForamt(id).size();i++)
                {
                    System.out.println(dao.FindAllPlayersUsingIdJsonForamt(id).get(i));
                }

            }
            else if(a==9)
            {
                System.out.println("Data in JSON Formate");
                for(int i=0;i<dao.FindAllPlayersJsonForamt().size();i++)
                {
                    System.out.println(i+" "+dao.FindAllPlayersJsonForamt().get(i));
                }
            }

            else if(a==11)
            {

                System.out.print("Enter Salary ");
                int delete1=sc.nextInt();
                dao.deletePlayerBySalaryy(delete1);


            }
            else if(a==12)
            {

                ver=false;
                System.out.print("Program is terminated");
                System.exit(0);

            }
        }




    }














}

