package com.elifbesli.loancalculator;



public class LoanUtils {
    
    public static double monthlyPayment(double loanAmount,
                                        double annualInterestRateInPercent, 
                                        long loanPeriodInMonths) {
        if (annualInterestRateInPercent <= 0) {
            annualInterestRateInPercent = 0.0000001;
        }
        double monthlyInterestRate = annualInterestRateInPercent / 1200.0;
        double numerator = loanAmount * monthlyInterestRate;
        double denominator = 
                1 - Math.pow(1 + monthlyInterestRate, -1 * loanPeriodInMonths);
        return (numerator / denominator);
    }
}
