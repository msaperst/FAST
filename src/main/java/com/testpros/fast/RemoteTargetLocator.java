package com.testpros.fast;

import com.testpros.fast.reporter.Reporter;
import com.testpros.fast.reporter.Step;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriver.TargetLocator;

import static com.testpros.fast.utilities.Constants.*;

public class RemoteTargetLocator implements TargetLocator {

    public static final String UNABLE_TO_SWITCH_TO_FRAME = "Unable to switch to frame: ";
    private final Reporter reporter;
    private final com.testpros.fast.RemoteWebDriver driver;
    private final TargetLocator targetLocator;

    protected RemoteTargetLocator(com.testpros.fast.RemoteWebDriver driver, TargetLocator targetLocator, Reporter reporter) {
        this.driver = driver;
        this.targetLocator = targetLocator;
        this.reporter = reporter;
    }

    /**
     * Select a frame by its (zero-based) index. Selecting a frame by index is equivalent to the
     * JS expression window.frames[index] where "window" is the DOM window represented by the
     * current context. Once the frame has been selected, all subsequent calls on the WebDriver
     * interface are made to that frame.
     * Additionally, this will log the activity into the FAST reporter. If the frame is successfully
     * selected, it is considered a pass. If the frame doesn't exist, the NoSuchFrameException
     * will cause a failure and be recorded.
     *
     * @param index (zero-based) index
     * @return This driver focused on the given frame
     */
    @Override
    public WebDriver frame(int index) {
        Step step = new Step("Switching to frame with index '" + index + "'",
                FRAME_SELECTED);
        try {
            WebDriver frameDriver = targetLocator.frame(index);
            step.setPassed(SWITCHED_TO_FRAME);
            return frameDriver;
        } catch (Exception e) {
            step.setFailed(UNABLE_TO_SWITCH_TO_FRAME + e);
        } finally {
            reporter.addStep(step);
        }
        return null;
    }

    /**
     * Select a frame by its name or ID. Frames located by matching name attributes are always given
     * precedence over those matched by ID.
     * Additionally, this will log the activity into the FAST reporter. If the frame is successfully
     * selected, it is considered a pass. If the frame doesn't exist, the NoSuchFrameException
     * will cause a failure and be recorded.
     *
     * @param nameOrId the name of the frame window, the id of the &lt;frame&gt; or &lt;iframe&gt;
     *                 element, or the (zero-based) index
     * @return This driver focused on the given frame
     */
    @Override
    public WebDriver frame(String nameOrId) {
        Step step = new Step("Switching to frame with name or id '" + nameOrId + "'",
                FRAME_SELECTED);
        try {
            WebDriver frameDriver = targetLocator.frame(nameOrId);
            step.setPassed(SWITCHED_TO_FRAME);
            return frameDriver;
        } catch (Exception e) {
            step.setFailed(UNABLE_TO_SWITCH_TO_FRAME + e);
        } finally {
            reporter.addStep(step);
        }
        return null;
    }

    /**
     * Select a frame using its previously located {@link WebElement}. Note that the
     * FAST WebElement must be passed to this method.
     * Additionally, this will log the activity into the FAST reporter. If the frame is successfully
     * selected, it is considered a pass. If the frame doesn't exist, the NoSuchFrameException
     * or StaleElementException will cause a failure and be recorded.
     *
     * @param frameElement The frame element to switch to.
     * @return This driver focused on the given frame.
     * @see org.openqa.selenium.WebDriver#findElement(By)
     */
    @Override
    public WebDriver frame(org.openqa.selenium.WebElement frameElement) {
        WebElement fastFrameElement = (WebElement) frameElement;
        Step step = new Step("Switching to frame with element '" + fastFrameElement.getElementName() + "'",
                FRAME_SELECTED);
        try {
            WebDriver frameDriver = targetLocator.frame(fastFrameElement.getWebElement());
            step.setPassed(SWITCHED_TO_FRAME);
            return frameDriver;
        } catch (Exception e) {
            step.setFailed(UNABLE_TO_SWITCH_TO_FRAME + e);
        } finally {
            reporter.addStep(step);
        }
        return null;
    }

    /**
     * Change focus to the parent context. If the current context is the top level browsing context,
     * the context remains unchanged.
     * Additionally, this will log the activity into the FAST reporter. If the parent frame is successfully
     * selected, it is considered a pass, otherwise it will cause a failure and the reason for the
     * failure will be recorded.
     *
     * @return This driver focused on the parent frame
     */
    @Override
    public WebDriver parentFrame() {
        Step step = new Step("Switching to parent frame", "Parent frame selected");
        try {
            WebDriver parentDriver = targetLocator.parentFrame();
            step.setPassed("Switched to parent frame");
            return parentDriver;
        } catch (Exception e) {
            step.setFailed("Unable to switch to parent frame: " + e);
        } finally {
            reporter.addStep(step);
        }
        return null;
    }

    /**
     * Switch the focus of future commands for this driver to the window with the given name/handle.
     * Additionally, this will log the activity into the FAST reporter. If the window is successfully
     * selected, it is considered a pass. If the frame doesn't exist, the NoSuchWindowException
     * will cause a failure and be recorded.
     *
     * @param nameOrHandle The name of the window or the handle as returned by
     *                     {@link WebDriver#getWindowHandle()}
     * @return This driver focused on the given window
     */
    @Override
    public WebDriver window(String nameOrHandle) {
        Step step = new Step("Switching to window with name or handle '" + nameOrHandle + "'",
                "Window selected");
        try {
            WebDriver windowDriver = targetLocator.window(nameOrHandle);
            step.setActual("Switched to window with handle '" + windowDriver.getWindowHandle() + "'");
            if( nameOrHandle.equals(windowDriver.getWindowHandle())) {
                step.setPassed();
            } else {
                step.setFailed();
            }
            return windowDriver;
        } catch (Exception e) {
            step.setFailed("Unable to switch to window: " + e);
        } finally {
            reporter.addStep(step);
        }
        return null;
    }

    /**
     * Selects either the first frame on the page, or the main document when a page contains
     * iframes.
     * Additionally, this will log the activity into the FAST reporter. If the default content
     * is successfully selected, it is considered a pass, otherwise it will cause a failure and
     * the reason for the failure will be recorded.
     *
     * @return This driver focused on the top window/first frame.
     */
    @Override
    public WebDriver defaultContent() {
        Step step = new Step("Switching to default content",
                "Default content selected");
        try {
            WebDriver contentDriver = targetLocator.defaultContent();
            step.setPassed("Switched to default content");
            return contentDriver;
        } catch (Exception e) {
            step.setFailed("Unable to switch to default content: " + e);
        } finally {
            reporter.addStep(step);
        }
        return null;
    }

    /**
     * Switches to the element that currently has focus within the document currently "switched to",
     * or the body element if this cannot be detected. This matches the semantics of calling
     * "document.activeElement" in Javascript.
     * Additionally, this will log the activity into the FAST reporter. If the active element
     * is successfully selected, it is considered a pass, otherwise it will cause a failure and
     * the reason for the failure will be recorded.
     *
     * @return The WebElement with focus, or the body element if no element with focus can be
     * detected.
     */
    @Override
    public WebElement activeElement() {
        Step step = new Step("Switching to active element",
                "Active element selected");
        try {
            org.openqa.selenium.WebElement activeElement = targetLocator.activeElement();
            WebElement webElement = new WebElement(driver, activeElement, 1);
            step.setPassed("Switched to active element '" + webElement.getElementName() + "'");
            return webElement;
        } catch (Exception e) {
            step.setFailed("Unable to switch to active element: " + e);
        } finally {
            reporter.addStep(step);
        }
        return null;
    }

    /**
     * Switches to the currently active modal dialog for this particular driver instance.
     * Additionally, this will log the activity into the FAST reporter. If the alert
     * is successfully located, it is considered a pass, otherwise the NoAlertPresentException
     * will cause a failure and be recorded.
     *
     * @return A handle to the dialog.
     */
    @Override
    public Alert alert() {
        Step step = new Step("Switching to active modal dialog",
                "Active modal dialog selected");
        try {
            Alert alert = targetLocator.alert();
            step.setPassed("Switched to active modal dialog with text '" + alert.getText() + "'");
            return alert;
        } catch (Exception e) {
            step.setFailed("Unable to switch to active modal dialog: " + e);
        } finally {
            reporter.addStep(step);
        }
        return null;
    }
}
