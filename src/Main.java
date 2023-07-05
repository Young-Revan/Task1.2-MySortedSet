import java.util.Iterator;
import java.util.Random;

public class Main
{
    public static void main(String[] args)
    {
        MySortedSet<Integer> set = new MySortedSet<Integer>();

        /*Random random = new Random();
        for (int i = 0; i < 10; i++)
        {
            set.add(random.nextInt(-10000, 10000));
        }*/

        set.add(7);
        set.add(4);
        set.add(9);
        set.add(6);
        set.add(8);

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

        set.delete(9);

        set.print();
        System.out.print("\n\n");
        set.printAsTree();
    }
}