package ma.ychakir.rz.launchermaker.Utils;

import com.jfoenix.validation.base.ValidatorBase;
import javafx.scene.control.TextInputControl;

import java.io.File;

/**
 * @author Yassine
 */
public class IconValidator extends ValidatorBase {
    @Override
    protected void eval() {
        if (this.srcControl.get() instanceof TextInputControl) {
            this.evalIcon();
        }
    }

    private void evalIcon() {
        TextInputControl textField = (TextInputControl) this.srcControl.get();
        File icon = new File(textField.getText());

        if (icon.exists() && icon.getName().endsWith(".ico"))
            this.hasErrors.set(false);
        else
            this.hasErrors.set(true);
    }
}
