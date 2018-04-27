%% Clearing workspace
clear
clc

%%Reading input image
inputImage = rgb2gray(imread('nyc.jpg'));

%% Initializing Parameters
sigma = 0.3;
k = 1.6;

phi = 400;  
gamma = 1.0002;
epsilon = 6;

%% Creating XDoG Filter

GaussianFileteredImage1 = imgaussfilt(inputImage, sigma);
GaussianFileteredImage2 = imgaussfilt(inputImage, (k*sigma));

XDoG = GaussianFileteredImage1-(gamma*GaussianFileteredImage2);

subplot(1,2,1);
imshow(mat2gray(XDoG));
title('XDoG');
imwrite(mat2gray(XDoG),'XDoG.jpeg');


%% Thresholding XDoG
[x,y] = size(XDoG);
TXDoG = zeros(x,y);
for i = 1:x
    for j = 1:y
        if XDoG(i,j)<epsilon 
            XDoG(i,j) = 1;
        else
            t = tanh(phi*(double(XDoG(i,j))));
            XDoG(i,j) = 1 + t;
        end
    end
end

subplot(1,2,2);
imshow(mat2gray(XDoG));
title('Thresholded XDoG');
imwrite(mat2gray(XDoG),'Thresholded_XDoG.jpeg');
