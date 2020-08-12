% ************************************************************************
% Sudoku solver main.
%
% Relied on different solution strategies implemented in different
% functions (placeXXXXX). However, using only the recursive function can
% eventually find the solution, but might take much more iterations. While
% using the other two strategies (known case and hidden uniques), although
% finds solutions and reduces iterations significantly, can end-up with
% some unsolved puzzles.
%
% To be added: Mutual exclusives
%
% ************************************************************************

% Import and draw a sudoku from file.
puzzleInput = importdata('input_b1.m');
% puzzleInput = importdata('blank.m');

drawSudoku(puzzleInput);

% Convert sudoku to binary values, make it ready for calculations.
binPuzzleCompute = convSudokuDec2Bin(puzzleInput);

newVal = true;
iterations = 0;

while newVal
    iterations = iterations + 1; % Track of the number of iterations

    % Place known values in the sudoku matrix  and return results
    [newVal, binPuzzleCompute] = placeKnownCases(binPuzzleCompute);

    % Place unique possibilities in the sudoku matrix and return results
    [newVal, binPuzzleCompute] = placeHiddenUniques(binPuzzleCompute);

end

if ~validateSudoku(binPuzzleCompute)
    [binPuzzleCompute, iterations] = placeCasesRecursively(binPuzzleCompute, 1, 1, iterations);
end

puzzleSolution = convSudokuBin2Dec(binPuzzleCompute);
drawSudoku(puzzleSolution);

        