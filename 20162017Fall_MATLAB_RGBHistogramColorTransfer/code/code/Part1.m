%Reading source image
SourceImDir = 'similar\\image2.jpg';
SourceIm = imread(SourceImDir);

%Reading target image
TargetImDir = 'images\\4.jpg';
TargetIm = imread(TargetImDir);

% Images to lab color space
SourceImLab = rgb2lab(SourceIm);
TargetImLab = rgb2lab(TargetIm);

%Extracting L channels of images
SourceImLChannel = SourceImLab(:,:,1);
TargetImLChannel = TargetImLab(:,:,1);

%Extracting A channels of images
SourceImAChannel = SourceImLab(:,:,2);
TargetImAChannel = TargetImLab(:,:,2);

%Extracting B channels of images
SourceImBChannel = SourceImLab(:,:,3);
TargetImBChannel = TargetImLab(:,:,3);

%Calculating mean values for source image's L A B channels
MeanSourceL = mean2(SourceImLChannel); 
MeanSourceA = mean2(SourceImAChannel);
MeanSourceB = mean2(SourceImBChannel);

%Calculating mean values for source image's L A B channels
MeanTargetL = mean2(TargetImLChannel); 
MeanTargetA = mean2(TargetImAChannel);
MeanTargetB = mean2(TargetImBChannel);

%Calculating standard deviation values for source image's L A B channels
StdSourceL = std2(SourceImLChannel);
StdSourceA = std2(SourceImAChannel);
StdSourceB = std2(SourceImBChannel);

%Calculating standard deviation values for target image's L A B channels
StdTargetL = std2(TargetImLChannel);
StdTargetA = std2(TargetImAChannel);
StdTargetB = std2(TargetImBChannel);

%Calculating Result L A B channels with std Values of L A B Channels of
%Source Image and Target Image
TargetImLChannel = (((TargetImLChannel-MeanTargetL))*(StdTargetL/StdSourceL));

TargetImAChannel = (((TargetImAChannel-MeanTargetA))*(StdTargetA/StdSourceA));

TargetImBChannel = (((TargetImBChannel-MeanTargetB))*(StdTargetB/StdSourceB));

%Assigning L A B Channels 
%ResultImLab(:,:,1) = (ResultLChannel+MeanSourceL);
%ResultImLab(:,:,2) = (ResultAChannel+MeanSourceA);
%ResultImLab(:,:,3) = (ResultBChannel+MeanSourceB);

TargetImLab(:,:,1) = TargetImLChannel+MeanSourceL;
TargetImLab(:,:,2) = TargetImAChannel+MeanSourceA;
TargetImLab(:,:,3) = TargetImBChannel+MeanSourceB;


%lab2rgb
ResultIm = lab2rgb(TargetImLab);


imwrite(ResultIm, 'result1.png');

