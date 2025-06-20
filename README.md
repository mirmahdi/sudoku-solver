# Sudoku Solver

A web-based Sudoku solver with both instant and step-by-step visualization modes, built using HTML, CSS, and JavaScript.

## Features

- **Instant Solve**: Solve the loaded puzzle in a single click.
- **Step-by-Step Solve**: Visualize the backtracking algorithm as it fills in each cell.
- **Adjustable Speed**: Control the delay between steps with a slider.
- **Pause / Resume**: Temporarily halt the visualization and resume at any time.
- **Manual Step**: Advance the solver one step at a time when paused.
- **Generate Puzzle**: Load a predefined sample puzzle.
- **Upload Puzzle**: Import any 9×9 Sudoku puzzle from a JSON file.
- **Timer & Move Counter**: Track how long and how many moves it takes to solve.
- **Conflict Detection**: Invalid entries are highlighted in red.
- **Active Cell Highlighting**: The current cell under consideration is highlighted in yellow.

## Demo

*(Replace `demo.png` with a screenshot of the application.)*

## Getting Started

### Prerequisites

- A modern web browser (Chrome, Firefox, Edge, Safari).

### Installation

1. **Clone the repository**  
   ```bash
   git clone https://github.com/your-username/sudoku-solver.git
   cd sudoku-solver

Open in Browser

Double-click index.html, or

Serve the directory (e.g., with VS Code Live Server or python -m http.server) and navigate to http://localhost:8000.

Usage
Select Solve Mode

Instant Solve: Solves the current grid immediately.

Step-by-Step: Shows the solving process.

(Step-by-Step Only) Adjust the Speed slider to control visualization delay.

Click Pause to pause/resume or Manual Step to advance one move.

Use Generate Puzzle to load the default puzzle.

Use Upload Puzzle to import a 9×9 JSON file (array of arrays).

Click Start Solving to begin.

Click Reset to restore the original puzzle state.

Monitor the timer and move counter at the bottom of the control panel.

File Structure

├── index.html      # Main HTML page
├── styles.css      # Styles for grid, buttons, and highlights
├── solver.js       # Core solving logic and UI interactions
├── LICENSE         # Project license (e.g., MIT)
└── README.md       # Project documentation

Contributing
Contributions are welcome! To contribute:

Fork the repository.

Create a new branch (git checkout -b feature-name).

Commit your changes (git commit -m "Add new feature").

Push to the branch (git push origin feature-name).

Open a Pull Request.

Please ensure your code follows the existing style and includes comments where necessary.

License
This project is licensed under the terms of the LICENSE file.