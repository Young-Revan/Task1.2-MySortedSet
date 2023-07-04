import java.util.Iterator;
import java.util.Random;

public class Main
{
    public static void main(String[] args)
    {
        MySortedSet<Integer> set = new MySortedSet<Integer>();

        Random random = new Random();
        for (int i = 0; i < 10; i++)
        {
            set.add(random.nextInt(-10000, 10000));
        }

        set.print();

        System.out.print('\n');
        Iterator<Integer> iterator = set.iterator();
        while (iterator.hasNext())
        {
            System.out.print(iterator.next());
            System.out.print('\t');
        }
        System.out.print("\n\n");
        set.printAsTree();
    }
}