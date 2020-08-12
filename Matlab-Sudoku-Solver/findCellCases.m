%*************************************************************************
% Function casesSequence = findCellCases(inS, idxrow, idxcol)
%
% Compute the possible values of an unassigned cell in a given
% 3 x 3 sudoku (9 x 9 matrix)
%
%  Input:
%              S - A 9x9 sudoku
%         idxrow - row index
%         idxcol - col index
% Output:
%  casesSequence - A binary array (sequence) of possible values
%
%*************************************************************************

function casesSequence = findCellCases(inS, idxrow, idxcol)

    row = 0;
    col = 0;
    box = 0;
    
    if inS(idxrow,idxcol) == 0

        % Calculate and assign row and columns values.
        for i = 1:9
            row = row + inS(idxrow,i);
            col = col + inS(i,idxcol);
        end
        
        % Calculate and assign box values.
        boxrowidx = floor((idxrow-1)/3) * 3 + 1;
        boxcolidx = floor((idxcol-1)/3) * 3 + 1;
        for i = boxrowidx:boxrowidx+2
            for j = boxcolidx:boxcolidx+2
                box = box + inS(i,j);
            end
        end

        % Then, apply all the constraints (row, column, and box)
        % collectively. The binary logic allow this arithmetic.
        casesSequence = 511 - bitor(box,bitor(row,col));        
    else
        % If the value is already known, there are no alternatives.
        casesSequence = 0;
    end
end