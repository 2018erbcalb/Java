
/**
 * Write a description of class RecursionExample2 here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class RecursionExample2
{
    private static void p(int i){
        System.out.println("hello");
        if(i<4){
            p(i+1);
        }
    }
    
    public static void main(String[]args){
        int i = 0;
        p(i);
    }
}
