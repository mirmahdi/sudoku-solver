% ************************************************************************
% Function: [outS, iter] = placeCasesRecursively(inS, i, j, iter);
%
%  Inputs:
%            inS - The sudoku puzzle to be solved
%              i - Row index of the first cell to be checked
%              j - Column index of the first cell to be checked
%           iter - Iterations counter
% Outputs:
%           outS - A fully solved sudoku
%
% Dependencies (to other functions):
%      getCandidSeq - Compute the possible cases for a specific referenced
%      (Exclusive)    cell, given a partially solved sudoku.
%    validateSudoku - Find if the solution found is a valid sudoku.
% ************************************************************************

function [outS, iter] = placeCasesRecursively(inS, i, j, iter)

    % Find the next unknown cell after or on the i and j indices
    while inS(i,j) ~= 0 && (i ~= 9 || j ~= 9)
        switch j
            case 9
                i = i + 1;
                j = 1;
            otherwise
                j = j + 1;
        end
    end
    
    outS = inS;
    cellCases = findCellCases(inS, i, j);
%    cellCases = 10 - findstr(dec2bin(findCellCases(inS, i, j)),'1');

    currentCasePwr = 0;
    while cellCases
        while ~mod(cellCases,2)
            cellCases = floor (cellCases / 2);
            currentCasePwr = currentCasePwr + 1;
        end
        currentCaseDigit = 2^(currentCasePwr);
%        currentCaseDigit = 2^(cellCases(1)-1);
         
        inS(i,j) = currentCaseDigit;
        [outS, iter] = placeCasesRecursively(inS, i, j, iter);
        % This line is sought only with a completed sudoku
        if validateSudoku(outS) % In case a solution was found
            break;
        else % If the returned sudoku is invalid
            % Reset the cell value, get ready for another iteration
            inS(i,j) = 0;
            % Remove the unsuccessful digit from candidVals
            cellCases = cellCases - 1;
        end
    end
    iter = iter + 1;
end