package ma.ychakir.rz.launchermaker.Utils;

import com.jfoenix.validation.base.ValidatorBase;
import javafx.scene.control.TextInputControl;

import java.io.File;

/**
 * @author Yassine
 */
public class ExecutableValidator extends ValidatorBase {
    @Override
    protected void eval() {
        if (this.srcControl.get() instanceof TextInputControl) {
            this.evalExecutable();
        }
    }

    private void evalExecutable() {
        TextInputControl textField = (TextInputControl) this.srcControl.get();
        File executable = new File(textField.getText());

        if (executable.getName().endsWith(".exe"))
            this.hasErrors.set(false);
        else
            this.hasErrors.set(true);
    }
}
