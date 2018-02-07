//  Kevin Bui
//  Person.java
//  A class representing a person with some invalid input handling
class Person {
    private String firstName, lastName;
    private int socialSecurityNumber;
    private double monthlyIncome;
    private double monthlyDebtPayment;
    private int creditScore;
    
    /**
     * Constructor with all parameters
     * @param firstName First name of person. Can not be null @throws IllegalArgumentException
     * @param lastName  Last name of person. Can not be null @throws IllegalArgumentException
     * @param socialSecurityNumber  Social security number. Can not equal zero @throws IllegalArgumentException
     * @param monthlyIncome Monthly income in dollars. Can be zero
     * @param monthlyDebtPayment    Monthly debt payment in dollars. Can be zero
     * @param creditScore   Credit score. May not be less than or equal to 280 or greater than or equal to 990. @throws
     *                      IllegalArgumentException
     */
    
    Person(String firstName, String lastName, int socialSecurityNumber, double monthlyIncome, double monthlyDebtPayment, int creditScore)
            throws IllegalArgumentException {

        if (!firstName.isEmpty())
            this.firstName = firstName;
        else {
            throw new IllegalArgumentException("First Name");
        }

        if (!lastName.isEmpty())
            this.lastName = lastName;
        else {
            throw new IllegalArgumentException("Last Name");
        }

        if (socialSecurityNumber > 0)
            this.socialSecurityNumber = socialSecurityNumber;
        else {
            throw new IllegalArgumentException("Social Security Number");
        }

        if (monthlyIncome >= 0)
            this.monthlyIncome = monthlyIncome;
        else {
            throw new IllegalArgumentException("Monthly Income");
        }

        if (monthlyDebtPayment >= 0)
            this.monthlyDebtPayment = monthlyDebtPayment;
        else {
            throw new IllegalArgumentException("Monthly Debt");
        }

        final int LOWEST_CREDIT_SCORE = 280;
        if (creditScore >= LOWEST_CREDIT_SCORE)
            this.creditScore = creditScore;
        else {
            throw new IllegalArgumentException("Credit Score < 300");
        }
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }


    public int getSocialSecurityNumber() {
        return socialSecurityNumber;
    }

    public double getMonthlyIncome() {
        return monthlyIncome;
    }

    public double getMonthlyDebtPayment() {
        return monthlyDebtPayment;
    }

    public int getCreditScore() {
        return creditScore;
    }
}
