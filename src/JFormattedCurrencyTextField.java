//  Kevin Bui
//  JFormattedCurrencyTextField.java
//  A custom JFormattedTextField with a US currency set as its display format. It uses a number formatter for editing

import javax.swing.*;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.text.NumberFormat;
import java.util.Locale;

class JFormattedCurrencyTextField extends JFormattedTextField
{
    /**
     * Constructor with a boolean to toggle the focus listener
     * @param setFocusAdapter flag to turn the Focus Adapter on or off
     */
    JFormattedCurrencyTextField(boolean setFocusAdapter)
    {
        NumberFormat newFormat = NumberFormat.getCurrencyInstance(Locale.US);
        newFormat.setMaximumFractionDigits(2);

        NumberFormat editFormat = NumberFormat.getNumberInstance();

        NumberFormatter newFormatter = new NumberFormatter(newFormat);

        NumberFormatter editFormatter = new NumberFormatter(editFormat);

        DefaultFormatterFactory defaultFormatterFactory = new DefaultFormatterFactory(
                        newFormatter,
                        newFormatter,
                        editFormatter
                );
        this.setFormatterFactory(defaultFormatterFactory);

        this.setValue(0);
        if (setFocusAdapter)
        {
            this.addFocusListener(new FocusAdapter()
            {
                @Override
                public void focusGained(FocusEvent e)
                {
                    JFormattedTextField currentField = (JFormattedTextField) e.getSource();
                    SwingUtilities.invokeLater(currentField::selectAll);
                }
            });
        }
    }
}
