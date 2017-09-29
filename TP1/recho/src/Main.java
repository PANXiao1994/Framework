public class Main {

    public static void main(String[] args) {
        // write your code here
        for(int i = args.length-1;i>=0;i--){
            System.out.print(args[i]);
            if(i>0)
                System.out.print(' ');
            else
                System.out.println();
        }
    }
}

