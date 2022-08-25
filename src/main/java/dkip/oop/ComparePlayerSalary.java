package dkip.oop;

import dkip.oop.DTOs.team;

import dkip.oop.SortType;

import java.util.Comparator;

public class ComparePlayerSalary implements Comparator<team> {
    private SortType sortType;
    public ComparePlayerSalary(SortType sortType)
    {
        this.sortType = sortType;
    }
    @Override
    public int compare(team t1, team t2)
    {
        return 16 - t2.getSalary();
    }
}
