import java.util.Scanner;
import java.util.Random;

public class Main {

    public static void main(String[] args) {
	// write your code here
        System.out.println("Random number between 1 and 1000000");
        Random rand = new Random();
        int  num = rand.nextInt(1000000) + 1;
        Scanner keyboard = new Scanner(System.in);
        int myint;
        do{
            System.out.print("Enter an integer:  ");
            myint = keyboard.nextInt();
            if(myint>num)
                System.out.println("The answer is smaller!");
            else if(myint<num)
                System.out.println("The answer is bigger!");
        }while(myint != num);
        System.out.println("Success!");
    }
}
