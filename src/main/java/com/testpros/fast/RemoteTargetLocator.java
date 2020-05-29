package com.testpros.fast;

import com.testpros.fast.reporter.Reporter;
import com.testpros.fast.reporter.Step;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class RemoteTargetLocator implements WebDriver.TargetLocator {

    private final Reporter reporter;
    private final com.testpros.fast.RemoteWebDriver driver;
    private final WebDriver.TargetLocator targetLocator;

    protected RemoteTargetLocator(com.testpros.fast.RemoteWebDriver driver, WebDriver.TargetLocator targetLocator, Reporter reporter) {
        this.driver = driver;
        this.targetLocator = targetLocator;
        this.reporter = reporter;
    }

    /**
     * Select a frame by its (zero-based) index. Selecting a frame by index is equivalent to the
     * JS expression window.frames[index] where "window" is the DOM window represented by the
     * current context. Once the frame has been selected, all subsequent calls on the WebDriver
     * interface are made to that frame.
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
            step.setActual("Successfully switched to frame with index '" + index + "'");
            step.setStatus(Step.Status.PASS);
            return driver;
        } catch (Exception e) {
            step.setActual("Unable to select frame: " + e);
            step.setStatus(Step.Status.FAIL);
        } finally {
            reporter.addStep(step);
        }
        return null;
    }

    /**
     * Select a frame by its name or ID. Frames located by matching name attributes are always given
     * precedence over those matched by ID.
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
            step.setActual("Successfully switched to frame with name or id '" + nameOrId + "'");
            step.setStatus(Step.Status.PASS);
            return driver;
        } catch (Exception e) {
            step.setActual("Unable to select frame: " + e);
            step.setStatus(Step.Status.FAIL);
        } finally {
            reporter.addStep(step);
        }
        return null;
    }

    /**
     * Select a frame using its previously located {@link WebElement}.
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
            step.setActual("Successfully switched to frame with element '" + frameElement + "'");
            step.setStatus(Step.Status.PASS);
            return driver;
        } catch (Exception e) {
            step.setActual("Unable to select frame: " + e);
            step.setStatus(Step.Status.FAIL);
        } finally {
            reporter.addStep(step);
        }
        return null;
    }

    /**
     * Change focus to the parent context. If the current context is the top level browsing context,
     * the context remains unchanged.
     *
     * @return This driver focused on the parent frame
     */
    @Override
    public WebDriver parentFrame() {
        Step step = new Step("Switching to parent frame", "Parent frame successfully selected");
        try {
            WebDriver driver = targetLocator.parentFrame();
            step.setActual("Successfully switched to parent frame");
            step.setStatus(Step.Status.PASS);
            return driver;
        } catch (Exception e) {
            step.setActual("Unable to select frame: " + e);
            step.setStatus(Step.Status.FAIL);
        } finally {
            reporter.addStep(step);
        }
        return null;
    }

    /**
     * Switch the focus of future commands for this driver to the window with the given name/handle.
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
            step.setActual("Successfully switched to window with name or handle '" + nameOrHandle + "'");
            step.setStatus(Step.Status.PASS);
            return driver;
        } catch (Exception e) {
            step.setActual("Unable to select window: " + e);
            step.setStatus(Step.Status.FAIL);
        } finally {
            reporter.addStep(step);
        }
        return null;
    }

    /**
     * Selects either the first frame on the page, or the main document when a page contains
     * iframes.
     *
     * @return This driver focused on the top window/first frame.
     */
    @Override
    public WebDriver defaultContent() {
        Step step = new Step("Switching to default content",
                "Default content successfully selected");
        try {
            WebDriver driver = targetLocator.defaultContent();
            step.setActual("Successfully switched to default content");
            step.setStatus(Step.Status.PASS);
            return driver;
        } catch (Exception e) {
            step.setActual("Unable to select default content: " + e);
            step.setStatus(Step.Status.FAIL);
        } finally {
            reporter.addStep(step);
        }
        return null;
    }

    /**
     * Switches to the element that currently has focus within the document currently "switched to",
     * or the body element if this cannot be detected. This matches the semantics of calling
     * "document.activeElement" in Javascript.
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
            step.setActual("Successfully switched to active element '" + element);
            step.setStatus(Step.Status.PASS);
            return element;
        } catch (Exception e) {
            step.setActual("Unable to select active element: " + e);
            step.setStatus(Step.Status.FAIL);
        } finally {
            reporter.addStep(step);
        }
        return null;
    }

    /**
     * Switches to the currently active modal dialog for this particular driver instance.
     *
     * @return A handle to the dialog.
     */
    @Override
    public Alert alert() {
        Step step = new Step("Switching to active modal dialog",
                "Active element successfully selected");
        try {
            Alert alert = targetLocator.alert();
            step.setActual("Successfully switched to active modal dialog '" + alert);
            step.setStatus(Step.Status.PASS);
            return alert;
        } catch (Exception e) {
            step.setActual("Unable to select active modal dialog: " + e);
            step.setStatus(Step.Status.FAIL);
        } finally {
            reporter.addStep(step);
        }
        return null;
    }
}
