import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.LinkedList;

/**
 * The Trade class facilitates trade operations including booking and printing trades.
 * It allows users to interactively input trade data, perform currency conversion, and manage a list of booked trades.
 */

public class Trade {

    private static LinkedList<BookTrade> tradesList = new LinkedList<BookTrade>();
    private BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    DecimalFormat decimalFormat = new DecimalFormat("###,###.###");
    
    /**
     * Validates customer name and currency pair.
     * 
     * @param custName The customer name to be validated
     * @param currencyPair The currency pair to be validated
     * @return An error message if data is invalid, otherwise null
     */
    
     private String isValid(String custName,String currencyPair){
       
             
        if(custName==null||custName.isEmpty()){

            return ("Invalid Customer Name!");
        }

        if(currencyPair==null||!currencyPair.equalsIgnoreCase("USDINR")){

            return("Invalid Currency Pair ");
        }

        return null;

    }


    /**
     * Retrieves trade data from user input and returns a BookTrade object if the data is valid, 
     * otherwise provides appropriate error messages.
     * 
     * @return BookTrade object if data is valid, otherwise null
     * @throws IOException if there's an issue with input/output operations
     */

    private BookTrade getData() throws IOException{
        
        System.out.println("Enter Customer Name:");
        String custName = br.readLine();
  

        System.out.println("Enter Currency Pair:(1->USDINR 2->INRUSD)");
        String currencyPair = br.readLine();

       
        double amount=-1 ; 
        
        if(isValid(custName, currencyPair)== null){

            System.out.println("Enter amount to transfer:");
           
            try{
                amount = Double.parseDouble(br.readLine());
            }catch(NumberFormatException e){

            }

            if(amount<0){
                System.out.println("Invalid amount");
                return null;
            }

            return new BookTrade(custName, currencyPair, amount);

        }else{

            System.out.println(isValid(custName, currencyPair));
        }

        return null;

        
    }


    /**
     * Handles the booking of a trade involving currency conversion.
     * Retrieves trade data, performs currency conversion, and allows user to view exchange rates and book/cancel trades.
     * 
     * @throws IOException if there's an issue with input/output operations
     */
    
     public void bookTrade() throws IOException{

        BookTrade bookTrade = getData();

        if(bookTrade!=null){
            
            bookTrade.currencyConversion(bookTrade.getCurrencyPair());

            System.out.println("Do you want to get Rate(Y/N):");
            String getRate = br.readLine();

            if(getRate.equalsIgnoreCase("Yes")|| getRate.equalsIgnoreCase("Y")){

                System.out.println("You are transferring INR "+decimalFormat.format(bookTrade.getAmount())+" to "+bookTrade.getCustName()+" (Assuming that rate was "+BookTrade.getRate()+")");
            }

            System.out.println("Book/Cancel this trade?(Press 1-->Book):");
            String booked = br.readLine();

            if(booked.equals("1")){

                tradesList.add(bookTrade);
                System.out.println("Trade for USDINR has been booked with rate "+BookTrade.getRate()+
                " The amount of Rs "+decimalFormat.format(bookTrade.getAmount())+" will be transferred in 2 working days to "+bookTrade.getCustName());

            }else{

                System.out.println("Trade is Canceled");
            }
        }else{

            System.out.println("Something went wrong...try with valid data!");
        }

        
    
    }

    
    /**
     * Prints trade information for the trades stored in the tradesList.
     * Displays trade details including TradeNo, CurrencyPair, CustomerName,Amount, and Rate for each trade in the list.
     */
     
    public void printTrade() {

        if(!tradesList.isEmpty()){
           
            System.out.println("TradeNo CurrencyPair CustomerName Amount Rate");

            for(BookTrade trade:tradesList)
            
                    System.out.println(trade);
        }else{

            System.out.println("No Trade Information available");
        }
    }
    
}


