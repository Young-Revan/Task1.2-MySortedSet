import java.util.Iterator;
import java.util.Random;
import java.io.*;

public class Main
{
    public static void main(String[] args)
    {
        /*try(FileWriter writer = new FileWriter("F://test3.txt", false))
        {
            Random random = new Random();
            int n = 1000000;
            for (int i = 0; i < n; i++)
            {
                int length = random.nextInt(100);
                String str = "";
                for (int j = 0; j < length; j++)
                {
                    str += (char)random.nextInt(48, 122);
                }
                str += "\r\n";
                writer.write(str);
            }
            String text = "text";
            writer.flush();
        }
        catch(IOException ex){

            System.out.println(ex.getMessage());
        }*/

        FileReadableSortedSet<String> set = new FileReadableSortedSet<String>();
        // "You... struck... your... company commander?"
        // "Just this, Johnnie. Don't buy a farm. You know your job; do it. Just do it. Don't try to win a medal."
        set.loadFromFile("F://test4.txt");

        //System.out.print("\n\n\n");
        //set.printAsTree();

        System.out.print(set.contains("\"You... struck... your... company commander?\""));
        System.out.print(set.contains("\"Just this, Johnnie. Don't buy a farm. You know your job; do it. Just do it. Don't try to win a medal.\""));

        set.delete("\"You... struck... your... company commander?\"");
        set.delete("\"Just this, Johnnie. Don't buy a farm. You know your job; do it. Just do it. Don't try to win a medal.\"");

        System.out.print("\n\n\n");
        set.printAsTree();
    }
}