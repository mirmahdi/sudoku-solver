% ************************************************************************
% Function convSudokuBin2Dec(S)
%
% Convert binary sudoku entries to their original decimal equivalents
%
%  Input: A 9x9 sudoku matrix
% 
% Output: A decimal sudoku
%
% ************************************************************************

function conv = convSudokuBin2Dec(S)
    for i = 1:9
        for j = 1:9
            conv(i,j) = log2(S(i,j)) + 1;
            if (conv(i,j) - floor(conv(i,j)))
                % If the binary value was anything other than a product of 2.
                % (i.e. if the matrix cell contained an array of possibilities
                % and not a distincts value, as coded in this solution.)
                conv(i,j) = 0;
            end
        end
    end
end