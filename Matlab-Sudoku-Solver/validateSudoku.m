% Function validateSudoku(sudoku);
%
%  Input: A binary coded (transformed) sudoku
% Output: A boolean value regarding the validity of the input sudoku
%

function isValid = validateSudoku(inS)
    isValid = true;
    for i = 1:9
        if (sum(inS(i,:)) ~= 511 || sum(inS(:,i)) ~= 511)
            isValid = false;
            break;
        end
    end
end