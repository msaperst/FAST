package com.testpros.fast;

import com.testpros.fast.reporter.Reporter;
import com.testpros.fast.reporter.Step;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver.Window;

public class RemoteWindow implements Window {

    private final Reporter reporter;
    private final Window window;

    protected RemoteWindow(Window window, Reporter reporter) {
        this.window = window;
        this.reporter = reporter;
    }

    /**
     * Set the size of the current window. This will change the outer window dimension,
     * not just the view port, synonymous to window.resizeTo() in JS.
     * Additionally, this will log the activity into the FAST reporter. If any errors
     * are encountered it is considered a failure, and the error will be recorded, otherwise it
     * will be considered a pass.
     *
     * @param targetSize The target size.
     */
    @Override
    public void setSize(Dimension targetSize) {
        Step step = new Step("Setting window size to '" + targetSize + "'",
                "Window size successfully set to '" + targetSize + "'");
        try {
            window.setSize(targetSize);
            step.setActual("Window resized to '" + getSize() + "'");
            if (getSize().equals(targetSize)) {
                step.setPassed();
            } else {
                step.setFailed();
            }
        } catch (Exception e) {
            step.setFailed("Unable to resize the window: " + e);
        } finally {
            reporter.addStep(step);
        }
    }

    /**
     * Set the position of the current window. This is relative to the upper left corner of the
     * screen, synonymous to window.moveTo() in JS.
     * Additionally, this will log the activity into the FAST reporter. If any errors
     * are encountered it is considered a failure, and the error will be recorded, otherwise it
     * will be considered a pass.
     *
     * @param targetPosition The target position of the window.
     */
    @Override
    public void setPosition(Point targetPosition) {
        Step step = new Step("Setting window position to '" + targetPosition + "'",
                "Window position successfully changed to '" + targetPosition + "'");
        try {
            window.setPosition(targetPosition);
            step.setActual("Window moved to '" + getPosition() + "'");
            if (getPosition().equals(targetPosition)) {
                step.setPassed();
            } else {
                step.setFailed();
            }
        } catch (Exception e) {
            step.setFailed("Unable to move the window position: " + e);
        } finally {
            reporter.addStep(step);
        }
    }

    /**
     * Get the size of the current window. This will return the outer window dimension, not just
     * the view port.
     *
     * @return The current window size.
     */
    @Override
    public Dimension getSize() {
        // not doing any logging, as this is just a check, nothing to log
        return window.getSize();
    }

    /**
     * Get the position of the current window, relative to the upper left corner of the screen.
     *
     * @return The current window position.
     */
    @Override
    public Point getPosition() {
        // not doing any logging, as this is just a check, nothing to log
        return window.getPosition();
    }

    /**
     * Maximizes the current window if it is not already maximized
     * Additionally, this will log the activity into the FAST reporter. If any errors
     * are encountered it is considered a failure, and the error will be recorded, otherwise it
     * will be considered a pass.
     */
    @Override
    public void maximize() {
        Step step = new Step("Maximizing the window",
                "Window successfully maximized");
        try {
            window.maximize();
            step.setPassed("Window maximized with new size of '" + getSize() + "'");
        } catch (Exception e) {
            step.setFailed("Unable to maximize the window: " + e);
        } finally {
            reporter.addStep(step);
        }
    }

    /**
     * Fullscreen the current window if it is not already fullscreen
     * Additionally, this will log the activity into the FAST reporter. If any errors
     * are encountered it is considered a failure, and the error will be recorded, otherwise it
     * will be considered a pass.
     */
    @Override
    public void fullscreen() {
        Step step = new Step("Setting the window to fullscreen",
                "Window successfully set to fullscreen");
        try {
            window.fullscreen();
            step.setPassed("Window at fullscreen with new size of '" + getSize() + "'");
        } catch (Exception e) {
            step.setFailed("Unable to set the window to fullscreen: " + e);
        } finally {
            reporter.addStep(step);
        }
    }
}
