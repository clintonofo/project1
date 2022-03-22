package dkip.oop;
import java.util.Comparator;

public class ComparatorPlayerAge implements Comparator<Players>{
    @Override
    public int compare(Players p1, Players p2)
    {
        return p1.getAge() - p2.getAge();
    }
}





