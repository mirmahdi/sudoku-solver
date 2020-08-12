% ************************************************************************
% Function: placeHiddenUniques(puzzleCompute, candidVals);
%
%  Inputs:
%           puzzle - The sudoku puzzle to be solved
% Outputs:
%           puzzle - (Partially) filled sudoku.
%           newVal - Boolean to indicate if an assignment was made in the
%                    function call.
% ************************************************************************

function [newVal, puzzle] = placeHiddenUniques(puzzle)

    % Compute the constraints on all cells to account for the known cell values.
    allCases = findAllCases(puzzle);

    newVal = false;

    % Find if within a row, column, or box, there is a value that is
    % only permissible in one and only one of the cells of that row
    % or column.
    casesDistRow(1:9) = 0;
    casesDistCol(1:9) = 0;
    casesDistBox(1:9) = 0;

    % Compute a probability distribution function for the possible
    % candidate numbers in each row, column, and box.
    for i = 1:9
        % Calculate and assign row and column values.
        for j = 1:9
            casesDistRow(i) = casesDistRow(i) + str2double(dec2bin(sum(allCases(i,j))));
            casesDistCol(i) = casesDistCol(i) + str2double(dec2bin(sum(allCases(j,i))));
        end

        % Calculate and assign box staring index values.
		% Box logic:    1   2   3
		%
		%               4   5   6
		%
		%               7   8   9

		% Aim of mapping: 1:3 --> 1; 4:6 --> 4; 7:9 --> 7
        boxrowidx = floor((i-1)/3) * 3 + 1;
		
		% Aim of mapping: 1,4,7 --> 1; 2,5,8 --> 4; 3,6,9 --> 7
        boxcolidx = mod(i-1,3) * 3 + 1;
        for j = boxrowidx:boxrowidx+2
            for k = boxcolidx:boxcolidx+2
                casesDistBox(i) = casesDistBox(i) + str2double(dec2bin(sum(allCases(j,k))));
            end
        end
    end

    % Check to find if any of the candid distribution contains a '1'.
    % (Indicating that there is only '1' possible value.)
    % Do so in different loops for rows, columns, and boxes
    % respectively

    foundNum = 0;
    for i = 1:9

        % Here: rows
        % Find a '1' in the possibility distributions of all rows
        unityInRow = length(num2str(casesDistRow(i))) - strfind(num2str(casesDistRow(i)), '1') + 1;
        a = 2;
        % In case unity found, use the information to set the value in
        % the corresponding cell
        if unityInRow > 0
            foundNum = 2^(unityInRow(1)-1);
            for j = 1:9
                if bitand(allCases(i,j),foundNum) == foundNum
                    puzzle(i,j) = foundNum;
                    allCases(i,j) = 0;
                    newVal = true;
                    break;
                end
            end
        end

        % Here: columns 
        % Find a '1' in the possibility distributions of all columns
        unityInCol = length(num2str(casesDistCol(i))) - strfind(num2str(casesDistCol(i)), '1') + 1;
        % In case unity found, use the information to set the value in
        % the corresponding cell
        if unityInCol > 0
            foundNum = 2^(unityInCol(1)-1);
            for j = 1:9
                if bitand(allCases(j,i),foundNum) == foundNum
                    puzzle(j,i) = foundNum;
                    allCases(j,i) = 0;
                    newVal = true;
                    break;
                end
            end
        end
        
        % Here: boxes
        % Find a '1' in the possibility distributions of all boxes
        unityInBox = length(num2str(casesDistBox(i))) - strfind(num2str(casesDistBox(i)), '1') + 1;
        % In case unity found, use the information to set the value in
        % the corresponding cell
        if unityInBox > 0
            foundNum = 2^(unityInBox(1)-1);
            boxrowidx = floor((i-1)/3) * 3 + 1;
            boxcolidx = mod(i-1,3) * 3 + 1;
            for j = boxrowidx:boxrowidx+2
                for k = boxcolidx:boxcolidx+2
                    if bitand(allCases(j,k),foundNum) == foundNum
                        puzzle(j,k) = foundNum;
                        allCases(j,k) = 0;
                        newVal = true;
                        break;
                    end
                end
            end
        end
    end
end