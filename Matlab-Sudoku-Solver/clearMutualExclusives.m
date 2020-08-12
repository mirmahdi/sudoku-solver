%*************************************************************************
% Function clearedCases = clearMutualExclusives(allCases)
%
%   Input:     allCases - Matrix of all possibilities per sudoku cell,
%                         binary coded
%  Output: clearedCases - The input matrix, with the mutually exclusive
%                         possibilities cleared from other cells in the
%                         same row, column, and box.
%*************************************************************************

function clearedCases = clearMutualExclusives(allCases)
    
    clearedCases = allCases;
    % Start a counter to go through all nine rows, columns, and boxes
    for i = 1:9

        % Start traversing the row/col from its first element
        for j = 1:9

            % Check if allCases(i,j) has only 2 bits set (2 possibilities)
            currCases = allCases(i,j);
            onesCnt = 0;
            for k = 1:length(currCases)
                onesCnt = onesCnt + str2double(currCases(k));
            end
            if onesCnt == 2
                for k = j:9
                    if allCases(i,k) == currCases

                        %** Clear allCases(i,j) from the rest of the row
                        for l = 1:9
                            if l ~= i && l ~= k
                                %* CLEAR
                            end
                        end
                        break;
                    end
                    if allCases(k,j) == currCases
                        
                        %** Clear allCases(i,j) from the rest of the col
                        break;
                    end
                end
                %** {Do the same for boxes}
            end
        end
    end
end