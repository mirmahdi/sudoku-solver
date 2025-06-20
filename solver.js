const gridElement = document.getElementById("sudoku-grid");
const timerElement = document.getElementById("timer");
const movesElement = document.getElementById("moves");

let isPaused = false;
let manualStepping = false;
let delayMs = 200;
let originalPuzzle = [];
let timer = null;
let seconds = 0;
let moveCount = 0;
let solved = false;
let solvingInProgress = false;

// Build 9x9 grid with input listeners
for (let row = 0; row < 9; row++) {
  const tr = document.createElement("tr");
  for (let col = 0; col < 9; col++) {
    const td = document.createElement("td");
    td.className =
      (col % 3 === 0 ? "thick-border-left " : "") +
      (row % 3 === 0 ? "thick-border-top" : "");
    const input = document.createElement("input");
    input.type = "number";
    input.min = "1";
    input.max = "9";
    input.dataset.row = row;
    input.dataset.col = col;
		input.addEventListener("input", () => {
			if (solved) {
				moveCount++;
				updateMoves();
				checkConflicts();
			}
		});
    td.appendChild(input);
    tr.appendChild(td);
  }
  gridElement.appendChild(tr);
}

function updateMoves() {
  movesElement.textContent = moveCount;
}

function startTimer() {
  stopTimer();
  seconds = 0;
  timerElement.textContent = 0;
  timer = setInterval(() => {
    seconds++;
    timerElement.textContent = seconds;
  }, 1000);
}

function stopTimer() {
  clearInterval(timer);
}

function getGridAndMarkOriginals() {
  const grid = [];
  for (let i = 0; i < 9; i++) {
    const row = [];
    for (let j = 0; j < 9; j++) {
      const cell = getCell(i, j);
      const val = cell.value ? parseInt(cell.value) : 0;
      cell.classList.remove("original", "solved", "invalid");
      if (val !== 0) cell.classList.add("original");
      row.push(val);
    }
    grid.push(row);
  }
  originalPuzzle = JSON.parse(JSON.stringify(grid));
  return grid;
}

function getCell(row, col) {
  return gridElement.rows[row].cells[col].firstChild;
}

function setCell(row, col, value, isSolved = false) {
  const cell = getCell(row, col);
  cell.value = value || "";
  if (!cell.classList.contains("original")) {
    cell.classList.toggle("solved", isSolved && value !== 0);
  }
}

function highlightCell(row, col) {
  clearHighlights();
  gridElement.rows[row].cells[col].classList.add("active-cell");
}

function clearHighlights() {
  for (let r = 0; r < 9; r++)
    for (let c = 0; c < 9; c++)
      gridElement.rows[r].cells[c].classList.remove("active-cell");
}

function isValid(grid, row, col, num) {
  for (let i = 0; i < 9; i++) {
    if (grid[row][i] === num || grid[i][col] === num) return false;
  }
  const boxRow = Math.floor(row / 3) * 3;
  const boxCol = Math.floor(col / 3) * 3;
  for (let i = 0; i < 3; i++)
    for (let j = 0; j < 3; j++)
      if (grid[boxRow + i][boxCol + j] === num) return false;

  return true;
}

function wait(ms) {
  return new Promise(resolve => {
    const check = () => {
      if (!isPaused || manualStepping) {
        manualStepping = false;
        resolve();
      } else {
        requestAnimationFrame(check);
      }
    };
		const raw = parseInt(document.getElementById("speed").value);
		setTimeout(check, 1000 - raw);
  });
}

async function visualSolve(grid) {
  for (let row = 0; row < 9; row++) {
    for (let col = 0; col < 9; col++) {
      if (grid[row][col] === 0) {
        for (let num = 1; num <= 9; num++) {
          if (isValid(grid, row, col, num)) {
            grid[row][col] = num;
            highlightCell(row, col);
            setCell(row, col, num, true);
            await wait(delayMs);

            if (await visualSolve(grid)) return true;

            grid[row][col] = 0;
            setCell(row, col, "");
            await wait(delayMs);
          }
        }
        return false;
      }
    }
  }
  return true;
}

function instantSolve(grid) {
  for (let row = 0; row < 9; row++) {
    for (let col = 0; col < 9; col++) {
      if (grid[row][col] === 0) {
        for (let num = 1; num <= 9; num++) {
          if (isValid(grid, row, col, num)) {
            grid[row][col] = num;
            if (instantSolve(grid)) return true;
            grid[row][col] = 0;
          }
        }
        return false;
      }
    }
  }
  return true;
}

function pauseOrResume() {
  isPaused = !isPaused;
  document.getElementById("pause-btn").textContent = isPaused ? "Resume" : "Pause";
}

function manualStep() {
  if (isPaused) manualStepping = true;
}

function checkConflicts() {
  clearConflicts();
  const grid = [];
  for (let i = 0; i < 9; i++) {
    const row = [];
    for (let j = 0; j < 9; j++) {
      const val = getCell(i, j).valueAsNumber || 0;
      row.push(val);
    }
    grid.push(row);
  }

  for (let r = 0; r < 9; r++) {
    for (let c = 0; c < 9; c++) {
      const val = grid[r][c];
      if (val === 0) continue;
      grid[r][c] = 0;
      if (!isValid(grid, r, c, val)) {
        getCell(r, c).classList.add("invalid");
      }
      grid[r][c] = val;
    }
  }
}

function clearConflicts() {
  for (let r = 0; r < 9; r++)
    for (let c = 0; c < 9; c++)
      getCell(r, c).classList.remove("invalid");
}

async function startSolving() {
  const mode = document.querySelector('input[name="mode"]:checked').value;
  document.getElementById("step-controls").style.display = mode === "step" ? "block" : "none";
  delayMs = parseInt(document.getElementById("speed").value);

  solved = false;
	solvingInProgress = true;
  moveCount = 0;
  updateMoves();
  clearHighlights();
  const grid = getGridAndMarkOriginals();

  disableUI(true);
  startTimer();

  if (mode === "instant") {
    const success = instantSolve(grid);
    if (success) {
      for (let r = 0; r < 9; r++)
        for (let c = 0; c < 9; c++)
          setCell(r, c, grid[r][c], true);
      solved = true;
      //alert("Sudoku Solved Instantly!");
    } else alert("No solution found.");
  } else {
    isPaused = false;
    manualStepping = false;
    const success = await visualSolve(grid);
    if (success) {
      solved = true;
      //alert("Sudoku Solved with Visualization!");
    } else {
      alert("No solution found.");
    }
  }

  stopTimer();
  clearHighlights();
	solvingInProgress = false;
  disableUI(false);
}

function disableUI(solveInProgress) {
  document.getElementById("pause-btn").disabled = !solveInProgress;
  document.getElementById("step-btn").disabled = !solveInProgress;
  document.getElementById("start-btn").disabled = solveInProgress;
  document.getElementById("reset-btn").disabled = true;
}

function resetPuzzle() {
  for (let r = 0; r < 9; r++) {
    for (let c = 0; c < 9; c++) {
      const cell = getCell(r, c);
      const val = originalPuzzle[r][c];
      cell.value = val || "";
      cell.classList.remove("solved", "invalid");
      if (val !== 0) {
        cell.classList.add("original");
      } else {
        cell.classList.remove("original");
      }
    }
  }
  moveCount = 0;
  updateMoves();
  timerElement.textContent = "0";
  solved = false;
  clearHighlights();
  clearConflicts();
}

function generatePuzzle() {
  // For simplicity: a very easy fixed puzzle (can use API later)
  const puzzle = [
    [5, 3, 0, 0, 7, 0, 0, 0, 0],
    [6, 0, 0, 1, 9, 5, 0, 0, 0],
    [0, 9, 8, 0, 0, 0, 0, 6, 0],
    [8, 0, 0, 0, 6, 0, 0, 0, 3],
    [4, 0, 0, 8, 0, 3, 0, 0, 1],
    [7, 0, 0, 0, 2, 0, 0, 0, 6],
    [0, 6, 0, 0, 0, 0, 2, 8, 0],
    [0, 0, 0, 4, 1, 9, 0, 0, 5],
    [0, 0, 0, 0, 8, 0, 0, 7, 9]
  ];

  for (let r = 0; r < 9; r++)
    for (let c = 0; c < 9; c++) {
      const cell = getCell(r, c);
      const val = puzzle[r][c];
      cell.value = val || "";
      cell.classList.remove("solved", "invalid");
      if (val !== 0) {
        cell.classList.add("original");
      } else {
        cell.classList.remove("original");
      }
    }

  originalPuzzle = JSON.parse(JSON.stringify(puzzle));
  solved = false;
  moveCount = 0;
  updateMoves();
  timerElement.textContent = "0";
  clearHighlights();
}

function uploadPuzzle() {
  const fileInput = document.getElementById("fileInput");
  fileInput.click();
  fileInput.onchange = () => {
    const file = fileInput.files[0];
    if (!file) return;
    const reader = new FileReader();
    reader.onload = function (e) {
      try {
        const puzzle = JSON.parse(e.target.result);
        for (let r = 0; r < 9; r++)
          for (let c = 0; c < 9; c++) {
            const cell = getCell(r, c);
            const val = puzzle[r][c];
            cell.value = val || "";
            cell.classList.remove("solved", "invalid");
            if (val !== 0) {
              cell.classList.add("original");
            } else {
              cell.classList.remove("original");
            }
          }
        originalPuzzle = puzzle;
        solved = false;
        moveCount = 0;
        updateMoves();
        timerElement.textContent = "0";
        clearHighlights();
      } catch (err) {
        alert("Invalid file format. Must be a 9x9 JSON array.");
      }
    };
    reader.readAsText(file);
  };
}

function updateMode() {
  const mode = document.querySelector('input[name="mode"]:checked').value;
  document.getElementById("step-controls").style.display = mode === "step" ? "block" : "none";

  // If solving is ongoing and switched to instant, trigger instant solve
  if (solvingInProgress && mode === "instant" && !solved) {
    const grid = getGridFromDOM();
    if (instantSolve(grid)) {
      for (let r = 0; r < 9; r++)
        for (let c = 0; c < 9; c++)
          setCell(r, c, grid[r][c], true);
      solved = true;
      isPaused = false;
      clearHighlights();
    }
    stopTimer();
    disableUI(false);
  }
}
