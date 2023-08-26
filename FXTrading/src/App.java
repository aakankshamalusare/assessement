import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * The main entry point for the trade management application.
 * Displays a menu to the user and allows them to interactively book trades, print trade information, and exit the program.
 */

public class App {
    public static void main(String[] args) throws IOException {
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        char ch;

        do{

            System.out.println("----------------------");
            System.out.println("1.Book Trade");
            System.out.println("2.Print Trades");
            System.out.println("3.Exit");
            System.out.println("----------------------");

            int choice;
            
            try{
                choice = Integer.parseInt(br.readLine());
            
            }catch(NumberFormatException ne){

                choice=-1;
            }

            Trade trade = new Trade();
           
            switch(choice){
                   
                
                case 1:trade.bookTrade();
                       break;
                    
                
                case 2:trade.printTrade();
                        break;
                
                case 3: break;

                default:
                        System.out.println("Invalid Choice");
                 
            }


            System.out.println("Do you really want to exit(Y/N)");
            ch = br.readLine().charAt(0);


        }while(ch=='n'||ch=='N');

        System.out.println("Bye - have a good day!");
    }
}
