package ma.ychakir.rz.launchermaker.Utils;

import com.jfoenix.validation.base.ValidatorBase;
import javafx.scene.control.TextInputControl;

/**
 * @author Yassine
 */
public class PortValidator extends ValidatorBase {
    @Override
    protected void eval() {
        if (this.srcControl.get() instanceof TextInputControl) {
            this.evalTextInputField();
        }
    }

    private void evalTextInputField() {
        TextInputControl textField = (TextInputControl) this.srcControl.get();

        try {
            evalRange(Integer.parseInt(textField.getText()));
        } catch (Exception var3) {
            this.hasErrors.set(true);
        }
    }

    private void evalRange(int number) {
        if (number < 1 || number > 65535)
            this.hasErrors.set(true);
        else
            this.hasErrors.set(false);
    }
}
