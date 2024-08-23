clc
close all
clear

% Watershed segmentation--------------------------------------------------

% inputImage = imread("Fishes/Fish_Dataset/Black Sea Sprat/Black Sea Sprat/00001.png");
% 
% outputImage = CS4640_waterSegmentation(inputImage);
% figure();
% imshow(outputImage);


% "Training"
% Now with everything in a for-loop
% For-loop for each type of fish

myFolders = dir(fullfile("Fishes/Fish_Dataset/",'*'));

totalAccuracy = 0;

for h = 3:length(myFolders)

    % disp(myFolders(h).name);

    currentFish = myFolders(h).name;

    myFiles = dir(fullfile("Fishes/Fish_Dataset/" + currentFish + "/" + currentFish + "/",'*.png'));
    accuracySum = 0;
    
    % figure();
    for k = 1:length(myFiles)
    
        inputImage = imread("Fishes/Fish_Dataset/" + currentFish + "/" + currentFish + "/" + myFiles(k).name);
        % imshow(inputImage);
    
        predictedArea = CS4640_waterSegmentation(inputImage);
        imwrite(predictedArea,"./output_Images/algorithm2/Fish_Dataset/" + currentFish + "/Prediction_" + myFiles(k).name);
    
%         montage({inputImage,predictedArea},"Size",[1 2]);
%         title("Original Image and Grayscale Morphological Processed Image");
    
        groundTruthSegMaskImage = imread("Fishes/Fish_Dataset/" + currentFish + "/" + currentFish + " GT/" + myFiles(k).name);
        predictedAreaAccuracy = CS4640_computeAccuracySingleImage(predictedArea, groundTruthSegMaskImage);
        accuracySum = accuracySum + predictedAreaAccuracy;
    
    end

    accuracy = accuracySum / length(myFiles);
    fprintf("Accuracy for %s is: %d\n", currentFish, accuracy);

    totalAccuracy = totalAccuracy + accuracy;

end

fprintf("Overall accuracy score: %d\n", (totalAccuracy/9));

% "Testing"

myFolders = dir(fullfile("Fishes/NA_Fish_Dataset/",'*'));

for h = 3:length(myFolders)

    % disp(myFolders(h).name);

    currentFish = myFolders(h).name;

    myFiles = dir(fullfile("Fishes/NA_Fish_Dataset/" + currentFish + "/",'*.png'));
    accuracySum = 0;
    
    % figure();
    for k = 1:length(myFiles)
    
        inputImage = imread("Fishes/NA_Fish_Dataset/" + currentFish + "/" + myFiles(k).name);
        % imshow(inputImage);
    
        predictedArea = CS4640_waterSegmentation(inputImage);
        imwrite(predictedArea,"./output_Images/algorithm2/NA_Fish_Dataset/" + currentFish + "/Prediction_" + myFiles(k).name);
    
%         montage({inputImage,predictedArea},"Size",[1 2]);
%         title("Original Image and Grayscale Morphological Processed Image");
    
    end

end

% For .JPG images. Why? Why not keep it all png?
myFolders = dir(fullfile("Fishes/NA_Fish_Dataset/",'*'));

for h = 3:length(myFolders)

    % disp(myFolders(h).name);

    currentFish = myFolders(h).name;

    myFiles = dir(fullfile("Fishes/NA_Fish_Dataset/" + currentFish + "/",'*.JPG'));
    accuracySum = 0;
    
    % figure();
    for k = 1:length(myFiles)
    
        inputImage = imread("Fishes/NA_Fish_Dataset/" + currentFish + "/" + myFiles(k).name);
        % imshow(inputImage);
    
        predictedArea = CS4640_waterSegmentation(inputImage);
        imwrite(predictedArea,"./output_Images/algorithm2/NA_Fish_Dataset/" + currentFish + "/Prediction_" + myFiles(k).name);
    
%         montage({inputImage,predictedArea},"Size",[1 2]);
%         title("Original Image and Grayscale Morphological Processed Image");
    
    end

end



















% Edge detection segmentation.--------------------------------------------

% "Training"
% Now with everything in a for-loop
% For-loop for each type of fish

myFolders = dir(fullfile("Fishes/Fish_Dataset/",'*'));

for h = 3:length(myFolders)

    % disp(myFolders(h).name);

    currentFish = myFolders(h).name;

    myFiles = dir(fullfile("Fishes/Fish_Dataset/" + currentFish + "/" + currentFish + "/",'*.png'));
    accuracySum = 0;
    
    % figure();
    for k = 1:length(myFiles)
    
        inputImage = imread("Fishes/Fish_Dataset/" + currentFish + "/" + currentFish + "/" + myFiles(k).name);
        % imshow(inputImage);
    
        predictedArea = CS4640_computeEdgeDetectionSegmentationMask(inputImage);
        imwrite(predictedArea,"./output_Images/algorithm1/Fish_Dataset/" + currentFish + "/Prediction_" + myFiles(k).name);
    
%         montage({inputImage,predictedArea},"Size",[1 2]);
%         title("Original Image and Grayscale Morphological Processed Image");
    
        groundTruthSegMaskImage = imread("Fishes/Fish_Dataset/" + currentFish + "/" + currentFish + " GT/" + myFiles(k).name);
        predictedAreaAccuracy = CS4640_computeAccuracySingleImage(predictedArea, groundTruthSegMaskImage);
        accuracySum = accuracySum + predictedAreaAccuracy;
    
    end

    accuracy = accuracySum / length(myFiles);
    fprintf("Your accuracy for %s is: %d\n", currentFish, accuracy);

end

% "Testing"

myFolders = dir(fullfile("Fishes/NA_Fish_Dataset/",'*'));

for h = 3:length(myFolders)

    % disp(myFolders(h).name);

    currentFish = myFolders(h).name;

    myFiles = dir(fullfile("Fishes/NA_Fish_Dataset/" + currentFish + "/",'*.png'));
    accuracySum = 0;
    
    % figure();
    for k = 1:length(myFiles)
    
        inputImage = imread("Fishes/NA_Fish_Dataset/" + currentFish + "/" + myFiles(k).name);
        % imshow(inputImage);
    
        predictedArea = CS4640_computeEdgeDetectionSegmentationMask(inputImage);
        imwrite(predictedArea,"./output_Images/algorithm1/NA_Fish_Dataset/" + currentFish + "/Prediction_" + myFiles(k).name);
    
%         montage({inputImage,predictedArea},"Size",[1 2]);
%         title("Original Image and Grayscale Morphological Processed Image");
    
    end

end

% For .JPG images. Why? Why not keep it all png?
myFolders = dir(fullfile("Fishes/NA_Fish_Dataset/",'*'));

for h = 3:length(myFolders)

    % disp(myFolders(h).name);

    currentFish = myFolders(h).name;

    myFiles = dir(fullfile("Fishes/NA_Fish_Dataset/" + currentFish + "/",'*.JPG'));
    accuracySum = 0;
    
    % figure();
    for k = 1:length(myFiles)
    
        inputImage = imread("Fishes/NA_Fish_Dataset/" + currentFish + "/" + myFiles(k).name);
        % imshow(inputImage);
    
        predictedArea = CS4640_computeEdgeDetectionSegmentationMask(inputImage);
        imwrite(predictedArea,"./output_Images/algorithm1/NA_Fish_Dataset/" + currentFish + "/Prediction_" + myFiles(k).name);
    
%         montage({inputImage,predictedArea},"Size",[1 2]);
%         title("Original Image and Grayscale Morphological Processed Image");
    
    end

end

% Funcitons -------------------------------------------------------------

function [enhancedImage] = CS4640_contrastEnhancement(inputImage)
%
% CS4640_contrastEnhancement - This function enhances the contrast of an image
% using the built in adapthisteq function from matlab.
%
% Input:-
%     <input variable inputImage> (image): The input image.
% Output:-
%     <output variable enhancedImage> (image): The enhanced Image
%  Example usage:
%		outputImage = CS4640_contrastEnhancement(inputImage);
% Author:
%     Harry Kim
%		Fall 2022
%

enhancedImage = adapthisteq(inputImage);

end

function [filteredImage] = CS4640_noiseRemoval(inputImage, filterSize)
%
% CS4640_noiseRemoval - This function uses the gaussian filter to perform
% some noise removal.
%
% Input:-
%     <input variable inputImage> (image): The input image.
% Output:-
%     <output variable filteredImage> (image): The filtered Image
%  Example usage:
%		outputImage = CS4640_noiseRemoval(inputImage);
% Author:
%     Harry Kim
%		Fall 2022
%

kernel = fspecial('gaussian', [filterSize filterSize], 20);

filteredImage = imfilter(inputImage, kernel);

end

function [enhancedImage] = CS4640_enhanceEgde(inputImage)
%
% CS4640_enhanceEgde - This function enhances an edge of a given image.
%
% Input:-
%     <input variable inputImage> (image): The input image.
% Output:-
%     <output variable enhancedImage> (image): The enhanced Image
%  Example usage:
%		outputImage = CS4640_enhanceEgde(inputImage);
% Author:
%     Harry Kim
%		Fall 2022
%

kernel = -1*ones(3);
kernel(2,2) = 15;
enhancedImage = imfilter(inputImage, kernel);

end

function [BWfinal] = CS4640_edgeDetectionSegmentation(inputImage)
%
% CS4640_edgeDetectionSegmentation - This function performs image
% segmentation using a combination of techniques and built in matlab
% functions.
%
% Input:-
%     <input variable inputImage> (image): The input image.
% Output:-
%     <output variable BWfinal> (image): The segmented image.
%  Example usage:
%		outputImage = CS4640_segmentation(inputImage);
% Author:
%     Harry Kim
%		Fall 2022
%

[~,threshold] = edge(inputImage,'sobel');
fudgeFactor = 0.5;
BWs = edge(inputImage,'sobel',threshold * fudgeFactor);

se90 = strel('disk',3);
se0 = strel('line',3,0);

BWsdil = imdilate(BWs,[se90 se0]);

BWdfill = imfill(BWsdil,'holes');

se = strel('diamond',5);
BWerode = imerode(BWdfill, se);

seD = strel('disk',5);
BWfinal = imdilate(BWerode,seD);

se = strel('diamond', 2);
BWerode = imerode(BWfinal, se);

% BWnobord = imclearborder(BWerode,8);

seD = strel('diamond',5);
BWfinal = imerode(BWerode,seD);
BWfinal = imerode(BWfinal,seD);

BWfinal = bwareaopen(BWfinal, 9000);

end

function [accuracy] = CS4640_computeAccuracySingleImage(myImage, groundTruthSegMaskImage)
%
% CS4640_computeAccuracySingleImage - This function computes the accuracy
% of the found segmented image by comparing it to the ground truth
% segmented mask image.
%
% Input:-
%     <input variable myImage> (image): The segmented image.
%     <input variable groundTruthSegMaskImage> (image): The ground truth
%     segmented mask image.
% Output:-
%     <output variable accuracy> (double): The accuracy score.
%  Example usage:
%		accuracy = CS4640_computeAccuracySingleImage(myImage, groundTruthSegMaskImage);
% Author:
%     Harry Kim
%		Fall 2022
%

numerator = 0;
denominator = 0;

for r = 1:size(myImage, 1)    % for number of rows of the image
    for c = 1:size(myImage, 2)    % for number of columns of the image

        if myImage(r,c) == groundTruthSegMaskImage(r,c)
            numerator = numerator + 1;

        end

        if groundTruthSegMaskImage(r,c) == 1
            denominator = denominator + 1;
        end

    end
end

accuracy = numerator / denominator;

end

function [predictedArea] = CS4640_computeEdgeDetectionSegmentationMask(inputImage)
%
% CS4640_computeEdgeDetectionSegmentationMask - This function computes the segmentation mask by
% using the functions listed above.
%
% Input:-
%     <input variable inputImage> (image): The input image.
% Output:-
%     <output variable predictedArea> (image): The predected area.
%  Example usage:
%		outputImage = CS4640_computeSegmentationMask(inputImage);
% Author:
%     Harry Kim
%		Fall 2022
%

inputImage = rgb2gray(inputImage);

% Enhancing the contrast of the image.
inputImage_2 = CS4640_contrastEnhancement(inputImage);
% montage({inputImage,inputImage_2},"Size",[1 2]);
% title("Original Image and Enhanced Image using Contrast Enhancement");

% Enhancing the edge of the image.
% figure();
inputImage_3 = CS4640_enhanceEgde(inputImage_2);
% montage({inputImage_2,inputImage_3},"Size",[1 2]);
% title("Original Image and Enhanced Image using sharpening");

% Add edge enhanced image to noise removal.
inputImage_4 = inputImage - imcomplement(inputImage_3);
% montage({inputImage,inputImage_4},"Size",[1 2]);
% title("Add edge enhanced image to noise removal.");

% Smoothing out origianl image with gaussian noise removal
% figure();
% inputImage_5 = CS4640_noiseRemoval(inputImage_4, 10);
% montage({inputImage,inputImage_5},"Size",[1 2]);
% title("Original Image and Enhanced Image using Noise Removal");

% Image segmentation phase and post-processing phase
% figure();
predictedArea = CS4640_edgeDetectionSegmentation(inputImage_4);
% montage({inputImage,inputImage_6},"Size",[1 2]);
% title("Original Image and Grayscale Morphological Processed Image");

end

function [final] = CS4640_waterSegmentation(inputImage)
%
% CS4640_computeEdgeDetectionSegmentationMask - This function computes the segmentation mask by
% using the functions listed above.
%
% Input:-
%     <input variable inputImage> (image): The input image.
% Output:-
%     <output variable final> (image): The predected area.
%  Example usage:
%		outputImage = CS4640_waterSegmentation(inputImage);
% Author:
%     Harry Kim
%		Fall 2022
%

% Basically doing the watershed segmentation example from the textbook
% without all the overlay stuff.

rgb = inputImage;         % Step 1. Read image and 
I = rgb2gray(rgb);                 % use the Gradient Magnitude as 
% subplot(1,3,1), imshow(I)		   % the basic segmentation Function
hy = fspecial('sobel');
hx = hy';
Iy = imfilter(double(I), hy, 'replicate');
Ix = imfilter(double(I), hx, 'replicate');
gradmag = sqrt(Ix.^2 + Iy.^2);

se = strel('disk', 20);
Ie = imerode(I, se);
Iobr = imreconstruct(Ie, I);
% subplot(1,3,2), imshow(Iobr),
Iobrd = imdilate(Iobr, se);			% Following the opening with a closing 
Iobrcbr = imreconstruct(imcomplement(Iobrd), imcomplement(Iobr));

Iobrcbr = imcomplement(Iobrcbr);    % to remove the dark spots and stem marks. 
% subplot(1,3,3); imshow(Iobrcbr);    % Notice you must complement the image 
									% inputs and output of imreconstruct.

fgm = imregionalmax(Iobrcbr);       % Calculate the regional maxima of Iobrcbr 
									% to obtain good foreground markers.
									
% I2 = I; I2(fgm) = 255;				% To help interpret the result, superimpose 
% figure; subplot(1,3,1); imshow(I2);	% these foreground marker image on the 
									% original image.

se2 = strel(ones(5,5));				% Some of the mostly-occluded and shadowed 
fgm2 = imclose(fgm, se2);			% objects are not marked, which means that 
fgm3 = imerode(fgm2, se2);			% these objects will not be segmented
									% properly in the end result.  Also, the 
									% foreground markers in some objects go 
									% right up to the objects' edge.  That means 
									% we must clean the edges of the marker 
									% blobs and then shrink them a bit. 
									 
fgm4 = bwareaopen(fgm3, 20);		% This procedure leaves some stray isolated  
% I3 = I; I3(fgm4) = 255;				% pixels that must be removed.  Do this 
% subplot(1,3,2), imshow(I3);			% using bwareaopen, which removes all blobs 
									% that have fewer than a certain number of 
									% pixels.
									
									

bw = im2bw(Iobrcbr, graythresh(Iobrcbr)); %Step 3: Compute Background Markers
% subplot(1,3,3), imshow(bw);			% Now we need to mark the background.  In 
									% the cleaned-up image, Iobrcbr, the 
									% dark pixels belong to the background, 
									% so you could start with a thresholding 
									% operation.
									
D = bwdist(bw);						% The background pixels are in black, 
DL = watershed(D);					% but ideally we don't want the background 
bgm = DL == 0;						% markers to be too close to the edges of 
% figure; subplot(1,3,1);imshow(bgm); % the objects we are trying to segment.  
									% We'll "thin" the background by computing 
									% the "skeleton by influence zones", or SKIZ, 
									% of the foreground of bw. This can be done 
									% by computing the watershed transform of the 
									% distance transform of bw, and 
									% then looking for the watershed ridge 
									% lines (DL == 0) of the result.



gradmag2 = imimposemin(gradmag, bgm | fgm4); % Step 4: Compute the modified  
									% segmentation function.
									% The function imimposemin is used to modify 
									% an image so that it has regional minima 
									% only in certain desired locations.  
									% Here you can use imimposemin to modify 
									% the gradient magnitude image so that its 
									% regional minima occur at foreground and 
									% background marker pixels.

L = watershed(gradmag2);   			% Step 5: Now compute the Watershed 
									% Transform of modified function

% Lrgb = label2rgb(L, 'jet', 'w', 'shuffle');
% subplot(1,3,2),imshow(Lrgb)
% subplot(1,3,3),imshow(I), hold on
% himage = imshow(Lrgb); set(himage, 'AlphaData', 0.3);

LB = 500;
UB = 50000;
Iout = xor(bwareaopen(L,LB),  bwareaopen(L,UB));
% figure, imshow(Iout);

% Iout = imclearborder(L);

seD = strel('disk',5);
final = imclose(Iout,seD);
% figure, imshow(final);

end
