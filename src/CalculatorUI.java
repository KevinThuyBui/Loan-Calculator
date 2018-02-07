//  Kevin Bui
//  CalculatorUI.java
//  The GUI of the calculator program and the main. Handles input and catches the exceptions created when instantiating
//      the Person objects and CarLoan object.

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.event.ActionEvent;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CalculatorUI {
    private JFormattedTextField dateInput;
    private JFormattedTextField firstNameInput;
    private JFormattedTextField lastNameInput;
    private JFormattedTextField spouseFirstNameInput;
    private JFormattedTextField spouseLastNameInput;
    private JFormattedTextField socialSecurityNumberInput;
    private JFormattedTextField spouseSocialSecurityNumberInput;
    private JFormattedTextField monthlyIncomeInput;
    private JFormattedTextField spouseMonthlyIncome;
    private JFormattedTextField monthlyDebtPaymentInput;
    private JFormattedTextField spouseMonthlyDebtPaymentInput;
    private JFormattedTextField costOfCarInput;
    private JFormattedTextField valueOfTradeInInput;
    private JFormattedTextField downPaymentInput;
    private JFormattedTextField lengthOfCarPaymentInput;
    private JFormattedTextField creditScoreInput;
    private JFormattedTextField spouseCreditScoreInput;
    private JPanel UI;
    private JButton calculateButton;
    private Person creditor, creditorSpouse;
    private CarLoan carLoan;

    private CalculatorUI()
    {
        setDefaultValues();
    }

    public static void main(String[] args)
    {
        JFrame frame = new JFrame("Car Loan Calculator");
        frame.setContentPane(new CalculatorUI().UI);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);

    }
    
    /**
     * Initializes the input fields and the create button
     */
    
    private void createUIComponents()
    {
        dateInput = new JFormattedTextField(new SimpleDateFormat("dd-MM-yyyy"));
        dateInput.setValue(new Date());
    
        NumberFormat lengthOfCarPaymentFormat = getNumberFormatLengthOfCarPayment();
        lengthOfCarPaymentInput = new JFormattedTextField(lengthOfCarPaymentFormat);
        
        initializeCurrencyFields();

        initializeSocialSecurityFields();
    
        initializeCalculateButton();
        }
    
    /**
     * Initializes the calculate button and is where all the input verification occurs
     */
    private void initializeCalculateButton()
    {
        calculateButton = new JButton();
        calculateButton.setAction(new AbstractAction()
        {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    try
                    {
                        createPersons();
                        createCarLoan();
                        LoanCalculator loanCalculator = createLoanCalculator();
                        createResultDialog(loanCalculator);
                    }
                    catch (NumberFormatException | NullPointerException ex)
                    {
                        JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), "Verify all input fields are filled.");
                    }
                    catch (CarLoan.InvalidCreditException invalidCredit)
                    {
                        JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), invalidCredit.getMessage());
                    }
                    catch (IllegalArgumentException illegalArgument)
                    {
                        JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), "Invalid input: " + illegalArgument.getMessage());
                    }
                }});
    }
    
    private LoanCalculator createLoanCalculator()
    {
        return new LoanCalculator(carLoan);
    }
    
    /**
     * Helper function to generate a number format to limit the year input between 0 - 99
     * @return NumberFormat for lengthOfCarPaymentInput
     */
    
    private NumberFormat getNumberFormatLengthOfCarPayment()
    {
        NumberFormat lengthOfCarPaymentFormat = NumberFormat.getNumberInstance();
        lengthOfCarPaymentFormat.setMaximumIntegerDigits(1);
        lengthOfCarPaymentFormat.setMaximumIntegerDigits(2);
        lengthOfCarPaymentFormat.setMaximumFractionDigits(0);
        return lengthOfCarPaymentFormat;
    }
    
    private void createResultDialog(LoanCalculator loanCalculator)
    {
        if (loanCalculator != null)
        {
            CalculatorResults calculatorResults = createCalculatorResults(loanCalculator);
    
            calculatorResults.pack();
        }
        else
        {
            throw new NullPointerException();
        }
    }
    
    
    private CalculatorResults createCalculatorResults(LoanCalculator loanCalculator)
    {
        if (carLoan != null)
        {
            double monthlyPayment = loanCalculator.calculateMonthlyPayment();
            double loanPaymentIncomeRatio = loanCalculator.calculateMonthlyLoanPaymentsToIncomeRatio();
            double interestRate = loanCalculator.calculateInterestRate();
            double loanAmount = loanCalculator.calculateLoanAmount();
            double monthlyIncome = loanCalculator.calculateCombinedMonthlyIncome();
            double monthlyDebtPayment = loanCalculator.calculateCombinedMonthlyPayments();
            Date dateOfLastPayment = loanCalculator.calculateDateOfLastPayment();
    
            return new CalculatorResults(monthlyPayment,
                    loanPaymentIncomeRatio, interestRate, loanAmount, monthlyIncome, monthlyDebtPayment,
                    dateOfLastPayment);
        }
        return null;
    }
    
    
    private void setDefaultValues()
    {
        firstNameInput.setValue("Kevin");
        lastNameInput.setValue("Bui");

        spouseFirstNameInput.setValue("Kevin");
        spouseLastNameInput.setValue("Bui");

        socialSecurityNumberInput.setValue("123-23-1234");
        spouseSocialSecurityNumberInput.setValue("234-45-6789");

    }


    private void createCarLoan() throws CarLoan.InvalidCreditException
    {
        double costOfCar = ((Number)(costOfCarInput.getValue())).doubleValue();
        Date currentDate = (Date)dateInput.getValue();
        double downPayment = ((Number)(downPaymentInput.getValue())).doubleValue();
        int numberOfYears = Integer.parseInt(lengthOfCarPaymentInput.getText());
        double valueOfTradeIn = ((Number)(valueOfTradeInInput.getValue())).doubleValue();
        
        carLoan = new CarLoan(currentDate, creditor, creditorSpouse, costOfCar, valueOfTradeIn, downPayment, numberOfYears);
    }

    private void createPersons()
    {
        int creditScore = Integer.parseInt(creditScoreInput.getText());
        int creditScoreSpouse = Integer.parseInt(spouseCreditScoreInput.getText());

        String firstName = firstNameInput.getText();
        String lastName = lastNameInput.getText();
        double monthlyDebtPayment = ((Number) (monthlyDebtPaymentInput.getValue())).doubleValue();
        double monthlyIncome = ((Number) (monthlyIncomeInput.getValue())).doubleValue();

        String socialSecurityNumberInputString = socialSecurityNumberInput.getText();
        int socialSecurityNumber = parseSocialSecurityNumber(socialSecurityNumberInputString);

        creditor = new Person(firstName, lastName, socialSecurityNumber, monthlyIncome, monthlyDebtPayment, creditScore);

        String firstNameSpouse = spouseFirstNameInput.getText();
        String lastNameSpouse = spouseLastNameInput.getText();
        double monthlyDebtPaymentSpouse = ((Number) (spouseMonthlyDebtPaymentInput.getValue())).doubleValue();
        double monthlyIncomeSpouse = ((Number) (spouseMonthlyIncome.getValue())).doubleValue();

        String socialSecurityNumberSpouseInputString = socialSecurityNumberInput.getText();
        int socialSecurityNumberSpouse = parseSocialSecurityNumber(socialSecurityNumberSpouseInputString);


        creditorSpouse = new Person(firstNameSpouse, lastNameSpouse, socialSecurityNumberSpouse, monthlyIncomeSpouse,
                monthlyDebtPaymentSpouse, creditScoreSpouse);
        
    }
    
    private int parseSocialSecurityNumber(String socialSecurityNumberInputString)
    {
        String socialSecurityNumberInputStringFormatted = socialSecurityNumberInputString.replaceAll("-", "");
        return Integer.parseInt(socialSecurityNumberInputStringFormatted);
    }
    
    private MaskFormatter createMaskFormatter(String mask)
    {
        try{
            return new MaskFormatter(mask);
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
        return null;
    }


    private void initializeSocialSecurityFields()
    {
      
        MaskFormatter maskFormatterSocialSecurity = createMaskFormatter("###-##-####");

        socialSecurityNumberInput = new JFormattedTextField(maskFormatterSocialSecurity);
        spouseSocialSecurityNumberInput = new JFormattedTextField(maskFormatterSocialSecurity);

        creditScoreInput = new JFormattedTextField(createMaskFormatter("###"));
        spouseCreditScoreInput = new JFormattedTextField(createMaskFormatter("###"));

    }

    private void initializeCurrencyFields()
    {
        monthlyIncomeInput = new JFormattedCurrencyTextField(true);
        spouseMonthlyIncome = new JFormattedCurrencyTextField(true);
        valueOfTradeInInput = new JFormattedCurrencyTextField(true);
        downPaymentInput = new JFormattedCurrencyTextField(true);
        monthlyDebtPaymentInput = new JFormattedCurrencyTextField(true);
        spouseMonthlyDebtPaymentInput = new JFormattedCurrencyTextField(true);
        costOfCarInput = new JFormattedCurrencyTextField(true);
    }
}
