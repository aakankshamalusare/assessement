/**
 * 
 * The USDINRConversion class provides currency conversion methods for USD to INR conversion.
 * 
 */
public class USDINRConversion {

    private static double rate = 66.66;

    public static double getRate() {
        return rate;
    }

    public static void setRate(double rate) {
        USDINRConversion.rate = rate;
    }


    /**
     * Converts the given amount using the specified rate.
     *  
     * @param amount The amount to be converted
     * @return The converted amount
     */
   
     public static double currencyConversion(double amount) {

            return amount*rate;
    }

}
