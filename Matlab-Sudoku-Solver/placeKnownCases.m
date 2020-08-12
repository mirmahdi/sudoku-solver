% ************************************************************************
% Function: placeKnownCases(puzzleCompute, candidVals);
%
%  Inputs:
%           puzzle - The sudoku puzzle to be solved
% Outputs:
%           puzzle - (Partially) filled sudoku.
%           newVal - Boolean to indicate if an assignment was made in the
%                    function call.
% ************************************************************************

function [newVal, puzzle] = placeKnownCases(puzzle)
    % Compute the constraints on all cells to account for the known cell values.
    allCases = findAllCases(puzzle);
    newVal = false;
    for i = 1:9
        for j = 1:9
            if ((floor(log2(puzzle(i,j))) - log2(puzzle(i,j))) ~= 0) && ((floor(log2(allCases(i,j))) - log2(allCases(i,j))) == 0)
                
                % If the actual cell value is unknown AND there is only one
                % possible candid cell value, assign it.
                puzzle(i,j) = allCases(i,j);
                newVal = true;
            end
        end
    end
end