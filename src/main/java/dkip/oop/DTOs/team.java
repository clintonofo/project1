package dkip.oop.DTOs;


/**                                                     OOP Feb 2022
 *  Data Transfer Object (DTO)
 *
 * This POJO (Plain Old Java Object) is called the Data Transfer Object (DTO).
 * (or, alternatively, the Model Object or the Value Object).
 * It is used to transfer data between the DAO and the Business Objects.
 * Here, it represents a row of data from the User database table.
 * The DAO creates and populates a User object (DTO) with data retrieved from
 * the resultSet and passes the User object to the Business Layer.
 *
 * Collections of DTOs( e.g. ArrayList<User> ) may also be passed
 * between the Data Access Layer (DAOs) and the Business Layer objects.
 */

public class team
{

    private String playerName;
    private String teamName;
    private int salary;
    private String manager;
    private String coach;
    private String city;

    public team(String playerName, String teamName, int salary, String manager, String coach, String city)
    {
        this.playerName = playerName;
        this.teamName = teamName;
        this.salary = salary;
        this.manager = manager;
        this.coach = coach;
        this.city = city;
    }




    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public String getCoach() {
        return coach;
    }

    public void setCoach(String coach) {
        this.coach = coach;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @java.lang.Override
    public java.lang.String toString() {
        return "team{" +
                "playerName='" + playerName + '\'' +
                ", teamName='" + teamName + '\'' +
                ", salary=" + salary +
                ", manager='" + manager + '\'' +
                ", coach='" + coach + '\'' +
                ", city='" + city + '\'' +
                '}';
    }
}
