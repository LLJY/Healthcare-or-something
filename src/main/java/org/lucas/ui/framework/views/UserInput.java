package org.lucas.ui.framework.views;

import java.util.function.Predicate;

public record UserInput(String promptText, UserInputResult lambda) {
}