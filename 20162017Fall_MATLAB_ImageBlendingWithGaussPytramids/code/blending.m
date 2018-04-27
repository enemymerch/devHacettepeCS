clear
clc

%% Read the input images
imA = im2double(imread('w5.jpg'));
imB = im2double(imread('w6.jpg'));

imA = imresize(imA,[size(imB,1) size(imB,2)]);


%% Now we're going to create the mask and the mask pyramid
[h,w,~] = size(imA);
[yim,xim]=meshgrid(1:w,1:h); % pixel coordinates

figure, imshow(imA);
h = imfreehand;    % select a region from A image
maskA = createMask(h);%Region Mask
maskA3=repmat(maskA,[1,1,3]);
ImA=imdilate(maskA3,ones(30)).*imA; % Region Image

centY=sum(sum(maskA.*xim))./sum(maskA(:)); % Center coordinates of region
centX=sum(sum(maskA.*yim))./sum(maskA(:));

hold on
plot(centX,centY,'r+');
figure,imshow(imB) 
[x,y,button]=ginput(1); % Where will you paste selected region in Image B?

hold on
plot(x,y,'r+');
shiftx=round(x)-round(centX);
shifty=round(y)-round(centY);

shiftImA=circshift(imA,[shifty,shiftx]);
shiftMask=circshift(maskA3,[shifty,shiftx]);


%% Now we're going to create "gauss pyramids" for region mask , imA, imB
level = 9;

GaussPyramidA = cell(1,level);
GaussPyramidA{1} = im2double(shiftImA);

GaussPyramidRegionMask  = cell(1,level);
GaussPyramidRegionMask{1}  = im2double(shiftMask);

GaussPyramidB = cell(1,level);
GaussPyramidB{1} = im2double(imB);

for lvl = 2:level
	GaussPyramidA{lvl} = impyramid(GaussPyramidA{lvl-1}, 'reduce');
    GaussPyramidB{lvl} = impyramid(GaussPyramidB{lvl-1}, 'reduce');
    GaussPyramidRegionMask{lvl} = impyramid(GaussPyramidRegionMask{lvl-1}, 'reduce');
end
%% Now we're going to create "lab pyramids" for imA, imB

% Adjust the image sizes
for lvl = level-1:-1:1
	a = size(GaussPyramidA{lvl+1})*2-1;
	GaussPyramidA{lvl} = GaussPyramidA{lvl}(1:a(1),1:a(2),:);
    
    b = size(GaussPyramidB{lvl+1})*2-1;
	GaussPyramidB{lvl} = GaussPyramidB{lvl}(1:b(1),1:b(2),:);
    
    c = size(GaussPyramidRegionMask{lvl+1})*2-1;
    GaussPyramidRegionMask{lvl} = GaussPyramidRegionMask{lvl}(1:c(1), 1:c(2),:);
end

LabPyramidA = GaussPyramidA;
LabPyramidB = GaussPyramidB;

% Creating the lab pyramids
for lvl = 1:level-1
	LabPyramidA{lvl} = GaussPyramidA{lvl}-impyramid(GaussPyramidA{lvl+1}, 'expand');
	LabPyramidB{lvl} = GaussPyramidB{lvl}-impyramid(GaussPyramidB{lvl+1}, 'expand');
end

%% Blending the Pyramids
BlendedPyramid = cell(1,level);
for lvl = 1:level
    BlendedPyramid{lvl} = LabPyramidA{lvl}.*GaussPyramidRegionMask{lvl} + LabPyramidB{lvl}.*(1-GaussPyramidRegionMask{lvl});
end

%% Now It's time to collapse the blended pyramid
for lvl = level-1:-1:1
	BlendedPyramid{lvl} = BlendedPyramid{lvl}+impyramid(BlendedPyramid{lvl+1},'expand');
end


outputIm = BlendedPyramid{1};
figure, imshow(outputIm);

imwrite(outputIm,'resultImage.jpg');

