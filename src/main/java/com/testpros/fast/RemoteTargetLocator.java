package com.testpros.fast;

import com.testpros.fast.reporter.Reporter;
import com.testpros.fast.reporter.Step;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriver.TargetLocator;

public class RemoteTargetLocator implements TargetLocator {

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
                "Frame '" + index + "' successfully selected");
        try {
            WebDriver driver = targetLocator.frame(index);
            step.setPassed("Successfully switched to frame with index '" + index + "'");
            return driver;
        } catch (Exception e) {
            step.setFailed("Unable to select frame: " + e);
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
                "Frame '" + nameOrId + "' successfully selected");
        try {
            WebDriver driver = targetLocator.frame(nameOrId);
            step.setPassed("Successfully switched to frame with name or id '" + nameOrId + "'");
            return driver;
        } catch (Exception e) {
            step.setFailed("Unable to select frame: " + e);
        } finally {
            reporter.addStep(step);
        }
        return null;
    }

    /**
     * Select a frame using its previously located {@link WebElement}.
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
        Step step = new Step("Switching to frame with element '" + frameElement + "'",
                "Frame '" + frameElement + "' successfully selected");
        try {
            WebDriver driver = targetLocator.frame(frameElement);
            step.setPassed("Successfully switched to frame with element '" + frameElement + "'");
            return driver;
        } catch (Exception e) {
            step.setFailed("Unable to select frame: " + e);
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
        Step step = new Step("Switching to parent frame", "Parent frame successfully selected");
        try {
            WebDriver driver = targetLocator.parentFrame();
            step.setPassed("Successfully switched to parent frame");
            return driver;
        } catch (Exception e) {
            step.setFailed("Unable to select frame: " + e);
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
                "Window '" + nameOrHandle + "' successfully selected");
        try {
            WebDriver driver = targetLocator.window(nameOrHandle);
            step.setPassed("Successfully switched to window with name or handle '" + nameOrHandle + "'");
            return driver;
        } catch (Exception e) {
            step.setFailed("Unable to select window: " + e);
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
                "Default content successfully selected");
        try {
            WebDriver driver = targetLocator.defaultContent();
            step.setPassed("Successfully switched to default content");
            return driver;
        } catch (Exception e) {
            step.setFailed("Unable to select default content: " + e);
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
                "Active element successfully selected");
        try {
            WebElement element = new WebElement(driver, targetLocator.activeElement(), 1);
            step.setPassed("Successfully switched to active element '" + element);
            return element;
        } catch (Exception e) {
            step.setFailed("Unable to select active element: " + e);
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
                "Active element successfully selected");
        try {
            Alert alert = targetLocator.alert();
            step.setPassed("Successfully switched to active modal dialog '" + alert);
            return alert;
        } catch (Exception e) {
            step.setFailed("Unable to select active modal dialog: " + e);
        } finally {
            reporter.addStep(step);
        }
        return null;
    }
}
