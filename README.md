# SPA - Student Personal Assistant

A comprehensive Java desktop application designed to serve as a personal assistant for students, featuring a scientific calculator and website management system.

## Features

### Authentication System
- Secure user registration and login
- SHA-512 password hashing with salt
- Email validation
- Persistent user session management

### Scientific Calculator
- Support for complex mathematical expressions
- Advanced trigonometric functions (sin, cos, tan, cot, sec, csc)
- Logarithmic calculations (log, ln)
- Support for special constants (π, e)
- Decimal precision handling
- Expression parsing with parentheses support
- Error handling for invalid expressions

### Website Management
- Quick access to frequently used educational websites
- Integrated web browser
- Customizable website shortcuts including:
  - Google
  - YouTube
  - ODUS (University Portal)
  - Blackboard

### User Interface
- Modern, intuitive design
- Smooth animations and transitions
- Fullscreen mode
- Draggable login/register windows
- Dynamic date and time display
- Clean and organized layout

## Technical Details

### Built With
- Java
- JavaFX for GUI
- Custom mathematical expression evaluator
- WebView for integrated browsing
- File-based data persistence

### Architecture
- MVC design pattern
- Object-oriented programming principles
- Modular component design
- Custom UI controls

### Security Features
- Email format validation
- Password confirmation
- Secure password storage
- Protected user sessions

## Requirements
- Java Runtime Environment (JRE)
- JavaFX compatible system
- Internet connection for web features

## Installation

1. Ensure you have Java installed on your system
2. Download the latest release
3. Run the application using:
   ```bash
   java -jar spa.jar
   ```

## Usage

### Login/Register
1. Launch the application
2. Register with your email and password
3. Log in using your credentials

### Calculator
- Access through the calculator icon on the home screen
- Enter mathematical expressions using the interface
- View results with high precision

### Websites
- Click the website icon on the home screen
- Select from predefined website shortcuts
- Browse within the integrated web view

## Contributing
Contributions are welcome. Please feel free to submit a Pull Request.

## License
This project is licensed under the MIT License - see the LICENSE file for details.

## Credits
Developed as a student project for Java programming course.
