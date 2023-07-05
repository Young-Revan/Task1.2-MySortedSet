import java.util.Iterator;
import java.util.Random;

public class Main
{
    public static void main(String[] args)
    {
        FileReadableSortedSet<String> set = new FileReadableSortedSet<String>();

        set.loadFromFile("F://test1.txt");

        Iterator<String> iterator = set.iterator();

        while (iterator.hasNext())
        {
            System.out.print(iterator.next() + '\t');
        }

        System.out.print("\n\n\n");
        set.printAsTree();
    }
}