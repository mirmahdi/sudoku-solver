% ************************************************************************
% Function convSudokuDec2Bin(S)
%
% Converts decimal sudoku entries to their binary equivalent
%
%  Input: A 9x9 sudoku matrix, with missing values should be coded as 0s
%
% Output: A binary coded equivalent of sudoku
%           --> 2^(n-1) where n is the decimal entry from the input
%         Missing values will be coded as a zero.
%
% ************************************************************************

function conv = convSudokuDec2Bin(S)
    for i = 1:9
        for j = 1:9
            if S(i,j) > 0
                % If the number is known, map it to a unique binary string
                conv(i,j) = 2 ^ (S(i,j) - 1);
            else
                % If the number is missing, initiate it with a binary
                % string of nine 1s
                conv(i,j) = 0;
            end
        end
    end
end

% Coding pattern:
% 0 -->  0       = 000000000
% 1 -->  1 = 2^0 = 000000001
% 2 -->  2 = 2^1 = 000000010
% 3 -->  4 = 2^2 = 000000100
% 4 -->  8 = 2^3 = 000001000
% 5 --> 16 = 2^4 = 000010000
% 6 --> 32 = 2^5 = 000100000
% 7 --> 64 = 2^6 = 001000000
% 8 --> 128= 2^7 = 010000000
% 9 --> 256= 2^8 = 100000000