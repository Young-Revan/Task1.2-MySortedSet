import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class FileReadableSortedSet<T extends Comparable> extends MySortedSet<T>
{
    public void loadFromFile(String filename)
    {
        try
        {
            Path path = Paths.get(filename);
            Scanner scanner = new Scanner(path);
            while (scanner.hasNextLine())
            {
                String line = scanner.nextLine();
                add((T)line);
            }
            scanner.close();
        }
        catch (FileNotFoundException e1)
        {
            System.out.println(e1.getMessage());
        }
        catch (IOException e2)
        {
            System.out.println(e2.getMessage());
        }
    }
}
