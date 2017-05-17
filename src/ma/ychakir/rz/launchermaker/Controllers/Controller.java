package ma.ychakir.rz.launchermaker.Controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import ma.ychakir.rz.launchermaker.Models.AwesomeIcons;
import ma.ychakir.rz.launchermaker.Utils.*;
import org.apache.commons.io.FileUtils;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

/**
 * @author Yassine
 */
public class Controller implements Initializable {
    private static Stage stage;
    public JFXButton btnUrl;
    public JFXButton btnIP;
    public JFXButton btnPort;
    public JFXTextField txtUrl;
    public JFXTextField txtIP;
    public JFXButton btnMake;
    public JFXTextField txtPort;
    public JFXButton btnIcon;
    public JFXTextField txtIcon;
    public JFXButton btnSFrame;
    public JFXTextField txtSFrame;
    public AnchorPane anchor;
    public JFXButton btnAbout;

    public static void setStage(Stage stage) {
        Controller.stage = stage;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        DialogUtil.setPane(anchor);
        RequiredFieldValidator required = new RequiredFieldValidator();
        PortValidator portValidator = new PortValidator();
        IPValidator ipValidator = new IPValidator();
        IconValidator iconValidator = new IconValidator();
        ExecutableValidator executableValidator = new ExecutableValidator();

        required.setMessage("This field is required.");
        portValidator.setMessage("This must be a valid port number.");
        ipValidator.setMessage("This must be a valid IP address.");
        iconValidator.setMessage("This must be a valid path to an existing icon.");
        executableValidator.setMessage("This must be a valid executable name.");

        txtUrl.getValidators().add(required);
        txtIP.getValidators().add(ipValidator);
        txtPort.getValidators().add(portValidator);
        txtIcon.getValidators().add(iconValidator);
        txtSFrame.getValidators().add(executableValidator);

        btnUrl.setOnAction(e -> focusAndSelect(txtUrl));
        btnIP.setOnAction(e -> focusAndSelect(txtIP));
        btnPort.setOnAction(e -> focusAndSelect(txtPort));
        btnSFrame.setOnAction(e -> focusAndSelect(txtSFrame));
        btnAbout.setOnAction(e -> about());
        btnIcon.setOnAction(e -> {
            FileChooser chooser = new FileChooser();
            FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("ICON files (*.ico) ", "*.ico");
            chooser.getExtensionFilters().add(filter);
            chooser.setTitle("Select icon file");
            File file = chooser.showOpenDialog(stage);

            if (file != null) txtIcon.setText(file.getAbsolutePath());
        });
        btnMake.setOnAction(e -> {
            boolean flag = true;
            if (!txtUrl.validate()) flag = false;
            if (!txtIP.validate()) flag = false;
            if (!txtPort.validate()) flag = false;
            if (!txtIcon.validate()) flag = false;
            if (!txtSFrame.validate()) flag = false;

            if (flag)
                make(txtUrl.getText(),
                        txtIcon.getText(),
                        txtSFrame.getText(),
                        txtIP.getText(),
                        Integer.parseInt(txtPort.getText())
                );
        });

        AwesomeDude.addIconButton(btnMake, AwesomeIcons.ICON_SAVE);
        AwesomeDude.addIconButton(btnAbout, AwesomeIcons.ICON_INFO_SIGN);
    }

    private void make(String url, String icon, String sframe, String ip, int port) {
        File launcher = getExecutable();
        if (launcher != null && launcher.getName().endsWith(".exe")) {
            String currentDirectory = System.getProperty("user.dir");
            String config = String.format("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                            "<launch4jConfig>\n" +
                            "  <dontWrapJar>false</dontWrapJar>\n" +
                            "  <headerType>gui</headerType>\n" +
                            "  <jar>%s</jar>\n" +
                            "  <outfile>%s</outfile>\n" +
                            "  <errTitle></errTitle>\n" +
                            "  <cmdLine>%s %s %s %d</cmdLine>\n" +
                            "  <chdir>.</chdir>\n" +
                            "  <priority>normal</priority>\n" +
                            "  <downloadUrl>http://java.com/download</downloadUrl>\n" +
                            "  <supportUrl></supportUrl>\n" +
                            "  <stayAlive>false</stayAlive>\n" +
                            "  <restartOnCrash>false</restartOnCrash>\n" +
                            "  <manifest>%s</manifest>\n" +
                            "  <icon>%s</icon>\n" +
                            "  <singleInstance>\n" +
                            "    <mutexName>%s</mutexName>\n" +
                            "    <windowTitle></windowTitle>\n" +
                            "  </singleInstance>" +
                            "  <classPath>\n" +
                            "    <mainClass>ma.ychakir.rz.vlauncher.Launcher</mainClass>\n" +
                            "    <cp>libs/commons-io-2.5.jar</cp>\n" +
                            "    <cp>libs/gson-2.8.0.jar</cp>\n" +
                            "    <cp>libs/jna-4.0.0.jar</cp>\n" +
                            "    <cp>libs/jna-platform-4.0.0.jar</cp>\n" +
                            "    <cp>libs/log4j-1.2.17.jar</cp>\n" +
                            "  </classPath>\n" +
                            "  <jre>\n" +
                            "    <path>jre</path>\n" +
                            "    <bundledJre64Bit>false</bundledJre64Bit>\n" +
                            "    <bundledJreAsFallback>false</bundledJreAsFallback>\n" +
                            "    <minVersion></minVersion>\n" +
                            "    <maxVersion></maxVersion>\n" +
                            "    <jdkPreference>preferJre</jdkPreference>\n" +
                            "    <runtimeBits>32/64</runtimeBits>\n" +
                            "  </jre>\n" +
                            "  <versionInfo>\n" +
                            "    <fileVersion>1.0.0.0</fileVersion>\n" +
                            "    <txtFileVersion>1.0.0.0</txtFileVersion>\n" +
                            "    <fileDescription>Rappelz game launcher, Coded by Volon</fileDescription>\n" +
                            "    <copyright>Volon</copyright>\n" +
                            "    <productVersion>1.0.0.0</productVersion>\n" +
                            "    <txtProductVersion>1.0.0.0</txtProductVersion>\n" +
                            "    <productName>vLauncher</productName>\n" +
                            "    <companyName>Volon</companyName>\n" +
                            "    <internalName>vLauncher</internalName>\n" +
                            "    <originalFilename>Launcher.exe</originalFilename>\n" +
                            "    <trademarks></trademarks>\n" +
                            "    <language>ARABIC</language>\n" +
                            "  </versionInfo>\n" +
                            "</launch4jConfig>",
                    currentDirectory + "/launcher/Launcher.jar",
                    launcher.getAbsolutePath(),
                    url,
                    sframe,
                    ip,
                    port,
                    currentDirectory + "/launch4j/manifest/vLauncher.manifest",
                    icon,
                    launcher.getName().substring(0, launcher.getName().length() - 4)
            );

            try {
                //save config
                FileUtils.writeStringToFile(new File("./launch4j/config.xml"),
                        config,
                        "UTF-8");

                //build executable launcher
                if (build(launcher)) {
                    FileUtils.copyDirectory(new File("./launcher/libs"), new File(launcher.getParent() + "\\libs"));
                    FileUtils.copyDirectory(new File("./jre"), new File(launcher.getParent() + "\\jre"));
                    DialogUtil.toast("Launcher created successfully.", 5000);
                } else {
                    throw new Exception();
                }
            } catch (Exception e) {
                DialogUtil.toast("Failed to create launcher, please try again !", 5000);
            }
        }
    }

    private void focusAndSelect(JFXTextField textField) {
        textField.requestFocus();
        textField.selectAll();
    }

    private boolean build(File launcher) throws IOException, InterruptedException {
        //start build process
        ProcessBuilder builder = new ProcessBuilder();
        builder.command().add(".\\launch4j\\launch4jc");
        builder.command().add(".\\launch4j\\config.xml");
        builder.start().waitFor(5, TimeUnit.SECONDS);
        return launcher.exists();
    }

    private File getExecutable() {
        FileChooser chooser = new FileChooser();
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("EXE file (*.exe)", "*.exe");
        chooser.getExtensionFilters().add(filter);
        chooser.setTitle("Save Launcher");

        return chooser.showSaveDialog(stage);
    }

    private void about() {
        JFXSnackbar snackbar = new JFXSnackbar(anchor);
        snackbar.show("Launcher Maker For vLauncher, Coded by Volon.", "Github", 6000, e -> {
            try {
                Desktop.getDesktop().browse(new URI("https://github.com/yChakir"));
            } catch (IOException | URISyntaxException ex) {
                ex.printStackTrace();
            } finally {
                snackbar.close();
            }
        });
    }
}
