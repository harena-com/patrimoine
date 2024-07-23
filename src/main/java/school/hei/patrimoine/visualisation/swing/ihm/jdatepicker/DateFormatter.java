package school.hei.patrimoine.visualisation.swing.ihm.jdatepicker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.swing.*;

public class DateFormatter extends JFormattedTextField.AbstractFormatter {

  private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

  @Override
  public Object stringToValue(String text) throws ParseException {
    return dateFormat.parseObject(text);
  }

  @Override
  public String valueToString(Object value) {
    if (value == null) {
      return "";
    }
    Calendar cal = (Calendar) value;
    return dateFormat.format(cal.getTime());
  }
}
