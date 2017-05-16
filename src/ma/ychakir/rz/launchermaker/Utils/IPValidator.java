package ma.ychakir.rz.launchermaker.Utils;

import com.jfoenix.validation.base.ValidatorBase;
import javafx.scene.control.TextInputControl;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Yassine
 */
public class IPValidator extends ValidatorBase {
    @Override
    protected void eval() {
        if (this.srcControl.get() instanceof TextInputControl) {
            this.evalIP();
        }
    }

    private void evalIP() {
        TextInputControl textField = (TextInputControl) this.srcControl.get();
        String ipString = textField.getText();

        String IP_ADDRESS_PATTERN = "(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)";

        Pattern pattern = Pattern.compile(IP_ADDRESS_PATTERN);
        Matcher matcher = pattern.matcher(ipString);

        if (matcher.find())
            this.hasErrors.set(false);
        else
            this.hasErrors.set(true);
    }
}
