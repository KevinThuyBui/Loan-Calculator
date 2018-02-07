//  Kevin Bui
//  LoanCalculator.java
//  This is the object that contains all of the necessary calculations for the loan

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class LoanCalculator {
    private CarLoan carLoan;
    
    /**
     * Constructor that accepts a CarLoan
     * @param carLoan the car loan to be manipulated. Cannot be null.
     */
    
    LoanCalculator(CarLoan carLoan)
    {
        this.carLoan = carLoan;
    }
    
    /**
     * Calculates monthly payment using the formula: monthlyPayment = (Pr(1+r/12)^(12N-(D/365N))/(12(1+r/12)^12 - 12)
     * The term 1 + r/12 is saved as monthlyInterestMultiplier for the sake of readability
     * @return  the amount in dollars of the monthly payment for the loan
     */
    
    public double calculateMonthlyPayment()
    {
        double loanAmount = calculateLoanAmount();
        double interestRate = calculateInterestRate();
        int loanLength = carLoan.getNumberOfYears();
        int dayOfMonth = calculateDayOfMonth();

        double monthlyInterestMultiplier = interestRate / 12 + 1;
    
        return loanAmount * interestRate * (Math.pow(monthlyInterestMultiplier, (12 * loanLength) -
                (dayOfMonth / (365 * loanLength))))
                / (12 * Math.pow(monthlyInterestMultiplier, 12 * loanLength) - 12);
    }
    
    /**
     * A helper function that manipulates a Date object by setting it to the next month and adding the numberOfYears
     * @return the modified Date of last payment
     */
    
    public Date calculateDateOfLastPayment()
    {
        Calendar cal = new GregorianCalendar();
        cal.setTime(carLoan.getCurrentDate());
        cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) + 1);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.add(Calendar.YEAR, carLoan.getNumberOfYears());
        return cal.getTime();
    }

    private int calculateDayOfMonth() {
        Calendar cal = new GregorianCalendar();
        cal.setTime(carLoan.getCurrentDate());
        return cal.get(Calendar.DAY_OF_MONTH);
    }

    public double calculateCombinedMonthlyIncome()
    {
        return carLoan.getCreditor().getMonthlyIncome() + carLoan.getCreditorSpouse().getMonthlyIncome();
    }

    public double calculateCombinedMonthlyPayments()
    {
        return carLoan.getCreditor().getMonthlyDebtPayment() + carLoan.getCreditorSpouse().getMonthlyDebtPayment();
    }
    
    /**
     * Calculates the monthly loan payment to income ratio. May be zero
     * @return  the ratio to be used for the interest rate.
     */

    public double calculateMonthlyLoanPaymentsToIncomeRatio()
    {
        return calculateCombinedMonthlyPayments() / calculateCombinedMonthlyIncome();
    }
    
    /**
     * Calculates the interest rate from the monthly loan payments to income ratio. Sets to 0.02 if ratio is 0
     * @return  the calculated interest rate
     */
    
    public double calculateInterestRate()
    {
        double interestRate = calculateMonthlyLoanPaymentsToIncomeRatio()/10;
        
        if (interestRate == 0)
        {
            return  .02;
        }
        else
        {
            return interestRate;
        }
    }

    public double calculateLoanAmount()
    {
        return carLoan.getCostOfCar() - carLoan.getTradeInValue() - carLoan.getDownPayment();
    }

}
