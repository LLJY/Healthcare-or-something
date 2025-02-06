package org.lucas.ui.framework.views;

import java.util.function.Predicate;

/**
 * Defines the user input for a view.
 * @param promptText A user friendly name.
 * @param lambda The function to be called when this input is selected. Usually called a lambda function.
 */
public record UserInput(String promptText, UserInputResult lambda) {
}