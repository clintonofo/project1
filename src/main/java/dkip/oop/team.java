package dkip.oop;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class team implements Comparable<team> {

    private String playerName;
    private String teamName;
    private int salary;
    private String manager;
    private String coach;
    private String city;



    public team(String playerName, String teamName, int salary, String manager, String coach, String city) {
        super();
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

    @Override
    public String toString() {
        return "team{" +
                "playerName='" + playerName + '\'' +
                ", teamName='" + teamName + '\'' +
                ", salary=" + salary +
                ", manager='" + manager + '\'' +
                ", coach='" + coach + '\'' +
                ", city='" + city + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        team team = (team) o;
        return salary == team.salary && Objects.equals(playerName, team.playerName) && Objects.equals(teamName, team.teamName) && Objects.equals(manager, team.manager) && Objects.equals(coach, team.coach) && Objects.equals(city, team.city);
    }

    @Override
    public int hashCode() {
        return Objects.hash(playerName, teamName, salary, manager, coach, city);
    }

    @Override
    public int compareTo(team o) {
        return 0;
    }
}


