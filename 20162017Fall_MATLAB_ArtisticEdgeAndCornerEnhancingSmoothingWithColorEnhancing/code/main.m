
%% First, perform the Papari's filter on an image
PapariFilter('buch.jpg', 'PapariFilterResult.jpg', 10, 8, 5);


%% Now, perform the color change of an image
color('green_red_blue.png', 'PapariFilterResult.jpg');