package dkip.oop;

import java.util.Comparator;

public class ComparatorPlayerPosition  implements Comparator<Players> {
    @Override
    public int compare(Players p1, Players p2)
    {
        return p1.getPosition().compareToIgnoreCase(p2.getPosition());
    }
}
