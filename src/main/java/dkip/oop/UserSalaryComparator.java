package dkip.oop;
import java.util.Comparator;

public class UserSalaryComparator
        implements Comparator<team>
{
    private SortType sortType;

    public UserSalaryComparator(SortType sortType)
    {
        this.sortType = sortType;
    }

    @Override
    public int compare(team u1, team u2)
    {
        int direction = sortType.getValue();
        return direction * (u1.getSalary() - u2.getSalary());
    }

}

