%Reading source image
SourceImDir = 'similar\\source.jpg';
SourceIm = imread(SourceImDir);

%Reading target image
TargetImDir = 'similar\\target2.jpg';
TargetIm = imread(TargetImDir);

% Images to lab color space
SourceImLab = rgb2lab(SourceIm);
TargetImLab = rgb2lab(TargetIm);

%Size's of source and target images
[row1, column1, dim1] = size(SourceImLab);
[row2, column2, dim2] = size(TargetImLab);


row_size = 1000;
column_size = 1000;
% Resize the source and target image 
SourceImLab = imresize(SourceImLab, [row_size column_size]);
TargetImLab = imresize(TargetImLab, [row_size column_size]);

%Extracting L A B channels of source image
SourceImLChannel = SourceImLab(:,:,1);
SourceImAChannel = SourceImLab(:,:,2);
SourceImBChannel = SourceImLab(:,:,3);

MeanSourceL = mean2(SourceImLChannel);
MeanSourceA = mean2(SourceImAChannel);
MeanSourceB = mean2(SourceImBChannel);

%Extracting L A B channels for target image
TargetImLChannel = TargetImLab(:,:,1);
TargetImAChannel = TargetImLab(:,:,2);
TargetImBChannel = TargetImLab(:,:,3);

MeanTargetL = mean2(TargetImLChannel);
MeanTargetA = mean2(TargetImAChannel);
MeanTargetB = mean2(TargetImBChannel);

row_partition_count = 10;
row_count = floor(row_size/row_partition_count);

column_partition_count = 10;
column_count = floor(column_size/column_partition_count);

rect_count = row_partition_count*column_partition_count;

%SourceRects(:,:,:,1) = TargetImLab(1:100,1:100,:);
TargetRects = rand(row_count, column_count, 3, rect_count);
SourceRects = rand(row_count, column_count, 3, rect_count);
ResultRects = rand(row_count, column_count, 3, rect_count);

i = 0;

% Slicing images into rect. areas 
index = 1;
while i+row_count<=row_size
    j = 0;
    while j+column_count<=column_size
        TargetRects(:,:,:,index) = TargetImLab(i+1:i+row_count,j+1:j+column_count,:);
        SourceRects(:,:,:,index) = SourceImLab(i+1:i+row_count,j+1:j+column_count,:);
        j = j+column_count;
        index = index + 1;
    end
    i = i+row_count;
end
index = index-1;


i = 0;
j = 0;
for i = 1:index
    TargetRect = TargetRects(:,:,:,i);
    k = 1;
    %ncc = normxcorr2(rgb2gray(lab2rgb(SourceRects(:,:,:,1))),rgb2gray(lab2rgb(TargetRect)));
    diff = sum(sum(sum((lab2rgb(TargetRect)-lab2rgb(SourceRects)).^2)));
    for j = 1:index
        tempSourceRect = SourceRects(:,:,:,j);
        tempDiff = sum(sum(sum((lab2rgb(TargetRect)-lab2rgb(tempSourceRect)).^2)));
        %tempNcc = normxcorr2(rgb2gray(lab2rgb(tempSourceRect)),rgb2gray(lab2rgb(TargetRect)));
        if tempDiff<diff

            k = j;
        end
    end
    %Now we found the needed SourceRect
    SourceRect = SourceRects(:,:,:,k);
    
    % L A B mean std for SourceRect
    SourceRectLChannel = SourceRect(:,:,1);
    SourceRectAChannel = SourceRect(:,:,2);
    SourceRectBChannel = SourceRect(:,:,3);
    

    
    StdSourceL = std2(SourceRectLChannel);
    StdSourceA = std2(SourceRectAChannel);
    StdSourceB = std2(SourceRectBChannel);
    
    % L A B mean std for TargetRect
    TargetRectLChannel = TargetRect(:,:,1);
    TargetRectAChannel = TargetRect(:,:,2);
    TargetRectBChannel = TargetRect(:,:,3);
    

    
    StdTargetL = std2(TargetRectLChannel);
    StdTargetA = std2(TargetRectAChannel);
    StdTargetB = std2(TargetRectBChannel);
    
    TargetRectLChannel = (((TargetRectLChannel-MeanTargetL))*(StdTargetL/StdSourceL));
    TargetRectAChannel = (((TargetRectAChannel-MeanTargetA))*(StdTargetA/StdSourceA));
    TargetRectBChannel = (((TargetRectBChannel-MeanTargetB))*(StdTargetB/StdSourceB));
    
    ResultRect(:,:,1) = TargetRectLChannel+MeanSourceL;
    ResultRect(:,:,2) = TargetRectAChannel+MeanSourceA;
    ResultRect(:,:,3) = TargetRectBChannel+MeanSourceB;
    
    ResultRects(:,:,:,i)= ResultRect;
end

ResultImLab = rand(row_size, column_size, 3);

i = 0;
j = 0;

k = 1;
% This is the part where result image is created with rectangular ares
while i+row_count<=row_size
    j = 0;
    while j+column_count<=column_size
        ResultImLab(i+1:i+row_count,j+1:j+column_count,:) = ResultRects(:,:,:,k);
        j = j+column_count;
        k = k + 1;
    end
    i = i+row_count;
end

imshow(lab2rgb(ResultImLab));

[x, y, z] = size(TargetIm);

ResultIm = lab2rgb(imresize(ResultImLab, [x y]));

imwrite(ResultIm, 'result.png');
imshow(ResultIm);
