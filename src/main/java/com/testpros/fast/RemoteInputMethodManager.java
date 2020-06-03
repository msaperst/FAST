package com.testpros.fast;

import com.testpros.fast.reporter.Reporter;
import com.testpros.fast.reporter.Step;
import org.openqa.selenium.WebDriver.ImeHandler;

import java.util.List;

public class RemoteInputMethodManager implements ImeHandler {

    private final Reporter reporter;
    private final ImeHandler imeHandler;

    protected RemoteInputMethodManager(ImeHandler imeHandler, Reporter reporter) {
        this.imeHandler = imeHandler;
        this.reporter = reporter;
    }

    /**
     * All available engines on the machine. To use an engine, it has to be activated.
     *
     * @return list of available IME engines.
     */
    @Override
    public List<String> getAvailableEngines() {
        // not doing any logging, as this is just a check, nothing to log
        return imeHandler.getAvailableEngines();
    }

    /**
     * Get the name of the active IME engine. The name string is platform-specific.
     *
     * @return name of the active IME engine.
     */
    @Override
    public String getActiveEngine() {
        // not doing any logging, as this is just a check, nothing to log
        return imeHandler.getActiveEngine();
    }

    /**
     * Indicates whether IME input active at the moment (not if it's available).
     *
     * @return true if IME input is available and currently active, false otherwise.
     */
    @Override
    public boolean isActivated() {
        // not doing any logging, as this is just a check, nothing to log
        return imeHandler.isActivated();
    }

    /**
     * De-activate IME input (turns off the currently activated engine). Note that getActiveEngine
     * may still return the name of the engine but isActivated will return false.
     * Additionally, this will log the activity into the FAST reporter. If any errors
     * are encountered it is considered a failure, and the error will be recorded, otherwise it
     * will be considered a pass.
     */
    @Override
    public void deactivate() {
        Step step = new Step("Deactivating IME input",
                "IME deactivated");
        try {
            imeHandler.deactivate();
            step.setActual("IME status is '" + imeHandler.isActivated() + "'");
            if (imeHandler.isActivated()) {
                step.setFailed();
            } else {
                step.setPassed();
            }
        } catch (Exception e) {
            step.setFailed("Unable to deactivate IME input: " + e);
        } finally {
            reporter.addStep(step);
        }
    }

    /**
     * Make an engines that is available (appears on the list returned by getAvailableEngines)
     * active. After this call, the only loaded engine on the IME daemon will be this one and the
     * input sent using sendKeys will be converted by the engine. Note that this is a
     * platform-independent method of activating IME (the platform-specific way being using keyboard
     * shortcuts).
     * Additionally, this will log the activity into the FAST reporter. If any errors
     * are encountered it is considered a failure, and the error will be recorded, otherwise it
     * will be considered a pass.
     *
     * @param engine name of engine to activate.
     */
    @Override
    public void activateEngine(String engine) {
        Step step = new Step("Activating IME input",
                "IME activated");
        try {
            imeHandler.deactivate();
            step.setActual("IME status is '" + imeHandler.isActivated() + "'");
            if (imeHandler.isActivated()) {
                step.setPassed();
            } else {
                step.setFailed();
            }
        } catch (Exception e) {
            step.setFailed("Unable to activate IME input: " + e);
        } finally {
            reporter.addStep(step);
        }
    }
}
