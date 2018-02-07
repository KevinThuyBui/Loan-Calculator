//  Kevin Bui
//  CarLoan.java
//  An class that represents the values of a car loan

import java.util.Date;

class CarLoan {
    private Date currentDate;
    private Person creditor, creditorSpouse;
    private double costOfCar;
    private double tradeInValue;
    private double downPayment;
    private int numberOfYears;
    
    /**
     * Constructor for CarLoan with all fields
     * @param currentDate    The starting date of the loan, not null
     * @param creditor       The primary loan recipient
     * @param creditorSpouse The secondary loan recipient
     * @param costOfCar      The cost of the car for the loan, greater than 0
     * @param tradeInValue   The value of the car being traded in
     * @param downPayment    The inital amount paid
     * @param numberOfYears  The length of loan, positive non-zero integer
     * @throws IllegalArgumentException thrown if currentDate == null, creditor's monthly income = creditorSpouse's
     *          monthly income is zero, the cost of the car is not greater than zero, the number of years is zero
     * @throws InvalidCreditException   thrown if average combined credit is lower than or equal to 500
     */
    
    CarLoan(Date currentDate, Person creditor, Person creditorSpouse, double costOfCar,
            double tradeInValue, double downPayment, int numberOfYears)
            throws IllegalArgumentException, InvalidCreditException
    {
        if (combinedCreditScoreIsAcceptable(creditor, creditorSpouse)) {
            if (currentDate == null)
            {
                throw new IllegalArgumentException("Current Date");
            }
            else if (costOfCar <= 0)
            {
                throw new IllegalArgumentException("Cost Of Car");
            }
            else if (numberOfYears == 0)
            {
                throw new IllegalArgumentException("Number Of Years");
            }
            else if (creditor.getMonthlyIncome() + creditorSpouse.getMonthlyIncome() == 0)
            {
                throw new IllegalArgumentException("Income is zero");
            }
            else {
                this.currentDate = currentDate;
                this.creditor = creditor;
                this.creditorSpouse = creditorSpouse;
                this.costOfCar = costOfCar;
                this.tradeInValue = tradeInValue;
                this.downPayment = downPayment;
                this.numberOfYears = numberOfYears;

            }
        }
        else
        {
            throw new InvalidCreditException("Your credit is too low to qualify for a loan.");
        }
    }
    
    /**
     * Checks if both Person have an average credit score > 500
     * @param creditor          The primary creditor
     * @param creditorSpouse    Secondary creditor
     * @return  true if average credit score > 500 and false otherwise
     */
    private boolean combinedCreditScoreIsAcceptable(Person creditor, Person creditorSpouse)
    {
        return (creditor.getCreditScore() + creditorSpouse.getCreditScore()) / 2 > 500;
    }

    public Date getCurrentDate() {
        return currentDate;
    }

    public Person getCreditor() {
        return creditor;
    }

    public Person getCreditorSpouse() {
        return creditorSpouse;
    }

    public double getCostOfCar() {
        return costOfCar;
    }

    public double getTradeInValue() {
        return tradeInValue;
    }

    public double getDownPayment() {
        return downPayment;
    }

    public int getNumberOfYears() {
        return numberOfYears;
    }
    
    /**
     * A custom exception to stop low credit score loans
     */
    class InvalidCreditException extends Exception {
        InvalidCreditException(String message){
            super (message);
        }
    }
}
