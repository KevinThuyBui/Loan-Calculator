//  Kevin
//  CalculatorResults.java
//  This is a JDialog that is configured to display the results of LoanCalculator as inputted. The output fields are
//      formatted by output type.

import javax.swing.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CalculatorResults extends JDialog {
    //The base JPanel
    private JPanel contentPane;
    //The various output fields
    private JFormattedTextField monthlyIncomeOutput;
    private JFormattedTextField loanPaymentIncomeRatioOutput;
    private JFormattedTextField interestRateOutput;
    private JFormattedTextField loanAmountOutput;
    private JFormattedTextField monthlyPaymentOutput;
    private JFormattedTextField dateOfLastPaymentOutput;
    private JFormattedTextField monthlyDebtPaymentOutput;
    
    /**
     * Constructor with a parameters
     * @param monthlyPayment            Amount to be paid in dollars monthly.
     * @param loanPaymentIncomeRatio    Ratio of loan payments to Income. May be zero
     * @param interestRate              Interest rate calculated from loan payment income ratio
     * @param loanAmount                The total amount of money in dollars of loan
     * @param monthlyIncome             Combined monthly income of both creditors in dollars
     * @param monthlyDebtPayment        Amount paid to existing loans per month in dollars
     * @param dateOfLastPayment         The Date of last payment
     */
    CalculatorResults(double monthlyPayment, double loanPaymentIncomeRatio, double interestRate, double loanAmount,
                      double monthlyIncome, double monthlyDebtPayment, Date dateOfLastPayment) {
        setContentPane(contentPane);
        setModal(false);
        setLocationRelativeTo(null);
        setVisible(true);

        setOutputFields(monthlyPayment,  loanPaymentIncomeRatio, interestRate, loanAmount, monthlyIncome,
                monthlyDebtPayment, dateOfLastPayment);
    }

    /**
     *  Sets the various output fields as given in constructor
     * @param monthlyPayment            Amount to be paid in dollars monthly.
     * @param loanPaymentIncomeRatio    Ratio of loan payments to Income. May be zero
     * @param interestRate              Interest rate calculated from loan payment income ratio
     * @param loanAmount                The total amount of money in dollars of loan
     * @param monthlyIncome             Combined monthly income of both creditors in dollars
     * @param monthlyDebtPayment        Amount paid to existing loans per month in dollars
     * @param dateOfLastPayment         The Date of last payment
     */

    private void setOutputFields(double monthlyPayment, double loanPaymentIncomeRatio, double interestRate, double loanAmount,
                                 double monthlyIncome, double monthlyDebtPayment, Date dateOfLastPayment)
    {
        monthlyIncomeOutput.setValue(monthlyIncome);
        loanPaymentIncomeRatioOutput.setValue(loanPaymentIncomeRatio);
        interestRateOutput.setValue(interestRate);
        loanAmountOutput.setValue(loanAmount);
        monthlyPaymentOutput.setValue(monthlyPayment);
        monthlyDebtPaymentOutput.setValue(monthlyDebtPayment);
        dateOfLastPaymentOutput.setValue(formatDateOutput(dateOfLastPayment));
    }

    /**
     * Initializes the output fields and prevents editing
     */

    private void createUIComponents()
    {
        monthlyIncomeOutput = new JFormattedCurrencyTextField(false);
        loanAmountOutput = new JFormattedCurrencyTextField(false);
        monthlyPaymentOutput = new JFormattedCurrencyTextField(false);
        monthlyDebtPaymentOutput = new JFormattedCurrencyTextField(false);

        dateOfLastPaymentOutput = new JFormattedTextField();
        interestRateOutput = new JFormattedTextField();
        loanPaymentIncomeRatioOutput = new JFormattedTextField();

        monthlyIncomeOutput.setEditable(false);
        loanAmountOutput.setEditable(false);
        monthlyPaymentOutput.setEditable(false);
        monthlyDebtPaymentOutput.setEditable(false);

        dateOfLastPaymentOutput.setEditable(false);
        interestRateOutput.setEditable(false);
        loanPaymentIncomeRatioOutput.setEditable(false);
    }

    /**
     * Takes the date object from loanCalculator.calculateDateOfLastPayment() and formats it into a string
     * @param calculatedDate date of last payment as Date
     * @return String Date represented as MM/dd/yyyy
     */
    private String formatDateOutput(Date calculatedDate)
    {
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        return dateFormat.format(calculatedDate);
    }

}
