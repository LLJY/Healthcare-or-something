# Java Mini CLI Framework

This project provides a basic framework for building command-line interface (CLI) applications in Java. It offers a structured approach to creating interactive menus, handling user input, and managing different pages or views within your CLI application.

## Getting Started

### Prerequisites

*   Java Development Kit (JDK) installed [cite: 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25]
*   IntelliJ IDEA (or any other Java IDE) [cite: 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25]

### Project Setup in IntelliJ IDEA

1.  Clone the repository: `git clone <repository_url>`
2.  Open IntelliJ IDEA and select "Open or Import".
3.  Navigate to the cloned repository and select the `pom.xml` file.
4.  Click "Open" to import the project.

## Framework Overview

The framework consists of the following core components:

*   `UiBase`: An abstract class that serves as the base for creating UI pages.
*   `View`: An abstract class that represents a single view or page within the CLI application.
*   `Canvas`: A class responsible for rendering the UI and handling user interactions.
*   `Terminal`: A class that interacts with the console for input and output.

## Usage

1.  Create a new class that extends `UiBase`.
2.  Implement the `OnCreateView` method to define the layout and content of your view.
3.  Implement the `OnViewCreated` method to handle any initialization or setup tasks for your view.
4.  Use the `ToPage` method to navigate between different views.

## Example

```java
public class MyView extends UiBase {
    @Override
    public View OnCreateView() {
        // Create UI elements here, must return a view
        
        //i.e:
        return new ListView(
                // other listview shit goes here
        );
    }

    @Override
    public void OnViewCreated(View parentView) {
        // your other code here
    }
}
```

## Contributing

Feel free to contribute to this project by submitting pull requests or reporting issues.
Refer to the styleguide [**here**](https://docs.google.com/document/d/1oQZtgl1YLNz4zDecadRudJOaQCh9SY2zP-HBpRrb8YU/edit?usp=sharing
):
