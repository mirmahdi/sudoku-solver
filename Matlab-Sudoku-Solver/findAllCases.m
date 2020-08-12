%*************************************************************************
% Function allCases = findAllCases(S)
%
% Computes the possible values of unassigned cells in a given
% 3 x 3 sudoku (9 x 9 matrix)
%
%  Input: A 9x9 array in which missing values are replaced with zeros
% Output: A 9x9 array of binary coded candid values for unknown cells
%*************************************************************************

function allCases = findAllCases(S)

    % Initialize the three constraint variable arrays.
    row(1:9) = 0; col(1:9) = 0; box(1:9) = 0;

    % Box logic:    1   2   3
    %
    %               4   5   6
    %
    %               7   8   9
    %

    % Compute the constraints on each of the row, column, and box based on
    % the known sudoku values. Each row, column, or box, will have constraints
    % based on the current values already known in the row, column, or box.
    for i = 1:9
        for j = 1:9

            % Compute the box index, based on row and column indices
            % (See the box logic above)
            boxidx = ceil(j/3) + 3 * floor((i-1)/3);

            % If a cell value is known, add it to the current values of its
            % corresponding row, column, and box. Binary logic allows simple
            % additions to work in this case, because each set of
            % represented valuse will only contain one unique instance of '1'
            % per known number value in each row, cell, and box.
            if (floor(log2(S(i,j))) - log2(S(i,j))) == 0
                row(i) = row(i) + S(i,j);
                col(j) = col(j) + S(i,j);
                box(boxidx) = box(boxidx) + S(i,j);
            end
        end
    end

    % Initialize possible values for unknown cells based on the commulative
    % constraints imposed on each cell, from its row, its column, and its box.
    % It finishes with the possible values (binary position coded) for each
    % cell.

    allCases(1:9,1:9) = 0;

    for i = 1:9
        for j = 1:9
            if (floor(log2(S(i,j))) - log2(S(i,j))) == 0

                % If the value is already known, just assign zero to the
                % corresponding candidVal cell, as no further computation is
                % needed.
                allCases(i,j) = 0;

            else

                % If the value is unknown, first, find which 3x3 box it
                % belongs to.
                boxidx = ceil(j/3) + 3 * floor((i-1)/3);

                % Then, apply all the constraints (row, column, and box)
                % collectively. Again, the binary logic allow this arithmetic
                % to work fine. The "511 - ..." makes the number 
                % representations reverse, in the binary logic; i.e. the
                % assigned numbers now represent a set of 'possible' values,
                % instead of a set of 'impossible' values that were computed
                % based on the values already in the rows, columns, and boxes.
                allCases(i,j) = 511 - bitor(box(boxidx),bitor(row(i),col(j)));
            end
        end
    end
    % To be added
    % Function to identify and eliminate mutually exclusive pairs from
    % other unknown cells of each row, column, and box
    %
end