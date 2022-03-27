package dkip.oop;

import java.util.Comparator;

public class ComparatorPlayerName implements Comparator<team> {

    private SortType sortType;	// Ascending or Descending enum type
    public ComparatorPlayerName(SortType sortType)
    {
        this.sortType = sortType;
    }

    @Override
    public int compare(team t1, team t2) {
        return t1.getPlayerName().compareToIgnoreCase(t2.getPlayerName());
    }
}
