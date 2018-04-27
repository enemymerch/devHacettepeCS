% BBM 415 - Fundamentals of Image Processing
% Problem Set 2, Second Assignment
% 18.11.2016 (Irem Hocaogullari)

%main function
function [IR] = asg2(source, target)

% read in the glass plate input images and convert to double matrises
S = imread(source);
T = imread(target);

source = im2double(S);
target = im2double(T);

ISource = rgb2lab(source);
ITarget = rgb2lab(target);

%compute mean and std for each channel,
%subtract means from data points for target image
%scale new data points according to the relative standard deviations of target and source images
%add the averages computed for the source to scaled data points

for ch = 1:3 
    meanSource = mean2(ISource(:,:,ch));
    meanTarget = mean2(ITarget(:,:,ch));
    stdSource = std2(ISource(:,:,ch));
    stdTarget = std2(ITarget(:,:,ch));
    
    trg = ITarget(:,:,ch) - meanTarget;
    
    sf = stdSource/stdTarget;
    res_lab(:,:,ch) = trg * sf + meanSource;
end



IR = lab2rgb(res_lab);

imwrite(IR, 'ColorChangeResult.jpg');
end

function lab = rgb2lab(im)
a = [0.3811 0.5783 0.0402;0.1967 0.7244 0.0782;0.0241 0.1288 0.8444];
b = [1/sqrt(3) 0 0;0 1/sqrt(6) 0;0 0 1/sqrt(2)];
c = [1 1 1;1 1 -2;1 -1 0];
img = reshape(im2double(im),[],3);

img = max(img,1/255);

% convert to LMS space
LMS = a*img';
LMS = log10(LMS);

% convert to lab space
lab = b*c*LMS;
lab=reshape(lab', size(im));
end

function rgb = lab2rgb(lab)
res_lab = reshape(lab,[],3);

b2 = [sqrt(3)/3 0 0;0 sqrt(6)/6 0;0 0 sqrt(2)/2];
c2 = [1 1 1;1 1 -1;1 -2 0];

% convert to LMS
LMS_res=c2*b2*res_lab';
for ch = 1:3
    LMS_res(ch,:) = 10.^LMS_res(ch,:);
end

% convert  to RGB
est_im = ([4.4679 -3.5873 0.1193;-1.2186 2.3809 -0.1624;0.0497 -0.2439 1.2045]*LMS_res)';
rgb = reshape(est_im,size(lab)); % reshape the image
end