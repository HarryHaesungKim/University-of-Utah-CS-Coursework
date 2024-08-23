clc

%hello

% Question #1: Pixel-wise operations on images --------------------------

% (a)

% toycars1.png and toycars2.png

toycars1 = imread('./Assignment-3-Images/color/toycars1.png');
toycars2 = imread('./Assignment-3-Images/color/toycars2.png');

combinedImage1 = CS4640_blendTwoImages(toycars1, toycars2, 0.5);
combinedImage2 = CS4640_blendTwoImages(toycars1, toycars2, 0.75);
combinedImage3 = CS4640_blendTwoImages(toycars1, toycars2, 0.25);
combinedImage4 = CS4640_blendTwoImages(toycars1, toycars2, 0.9);

subplot(2,3,1), imshow(toycars1); title('toycars1.png');
subplot(2,3,2), imshow(toycars2); title('toycars2.png');
subplot(2,3,3), imshow(combinedImage1); title('Blended weight 0.5');
subplot(2,3,4), imshow(combinedImage2); title('Blended weight 0.75');
subplot(2,3,5), imshow(combinedImage3); title('Blended weight 0.25');
subplot(2,3,6), imshow(combinedImage4); title('Blended weight 0.9');

% Saving images to output_images

saveName = 'toycars_blended_weight_50_percent.png';
path = append('./output_images/', saveName);
imwrite(combinedImage1,path);

saveName = 'toycars_blended_weight_75_percent.png';
path = append('./output_images/', saveName);
imwrite(combinedImage2,path);

saveName = 'toycars_blended_weight_25_percent.png';
path = append('./output_images/', saveName);
imwrite(combinedImage3,path);

saveName = 'toycars_blended_weight_90_percent.png';
path = append('./output_images/', saveName);
imwrite(combinedImage4,path);
figure();

% subject01.centerlight.png and subject05.noglasses.png

subject01centerlight = imread('./Assignment-3-Images/grayscale/subject01.centerlight.png');
subject01noglasses = imread('./Assignment-3-Images/grayscale/subject05.noglasses.png');

combinedImage1 = CS4640_blendTwoImages(subject01centerlight, subject01noglasses, 0.5);
combinedImage2 = CS4640_blendTwoImages(subject01centerlight, subject01noglasses, 0.75);
combinedImage3 = CS4640_blendTwoImages(subject01centerlight, subject01noglasses, 0.25);
combinedImage4 = CS4640_blendTwoImages(subject01centerlight, subject01noglasses, 0.9);

subplot(2,3,1), imshow(subject01centerlight); title('subject01.centerlight.png');
subplot(2,3,2), imshow(subject01noglasses); title('subject05.noglasses.png');
subplot(2,3,3), imshow(combinedImage1); title('Blended weight 0.5');
subplot(2,3,4), imshow(combinedImage2); title('Blended weight 0.75');
subplot(2,3,5), imshow(combinedImage3); title('Blended weight 0.25');
subplot(2,3,6), imshow(combinedImage4); title('Blended weight 0.9');

% Saving images to output_images

saveName = 'subject_blended_weight_50_percent.png';
path = append('./output_images/', saveName);
imwrite(combinedImage1,path);

saveName = 'subject_blended_weight_75_percent.png';
path = append('./output_images/', saveName);
imwrite(combinedImage2,path);

saveName = 'subject_blended_weight_25_percent.png';
path = append('./output_images/', saveName);
imwrite(combinedImage3,path);

saveName = 'subject_blended_weight_90_percent.png';
path = append('./output_images/', saveName);
imwrite(combinedImage4,path);
figure();

% (b)

carpark = imread('./Assignment-3-Images/color/carpark.png');
onlyRedPixels = CS4640_separatePixelsOfRedCar(carpark, 100);

subplot(1,2,1), imshow(carpark); title('Original image');
subplot(1,2,2), imshow(onlyRedPixels); title('Red pixels threshold: 100');
figure();

saveName = 'carpark_red_pixels_only.png';
path = append('./output_images/', saveName);
imwrite(onlyRedPixels,path);

%(c)

subject05_rightlight = imread('./Assignment-3-Images/grayscale/subject05.rightlight.png');

transformedImage1 = CS4640_logarithmicTransformation(subject05_rightlight, 0.01);
transformedImage2 = CS4640_logarithmicTransformation(subject05_rightlight, 0.03);
transformedImage3 = CS4640_logarithmicTransformation(subject05_rightlight, 0.05);

subplot(2,2,1), imshow(subject05_rightlight); title('Original Image');
subplot(2,2,2), imshow(transformedImage1); title('Log transformed Image 0.01');
subplot(2,2,3), imshow(transformedImage2); title('Log transformed Image 0.03');
subplot(2,2,4), imshow(transformedImage3); title('Log transformed Image 0.05');
figure();

saveName = 'subject05_rightlight_logarithmic_Transformation1.png';
path = append('./output_images/', saveName);
imwrite(transformedImage1,path);
saveName = 'subject05_rightlight_logarithmic_Transformation2.png';
path = append('./output_images/', saveName);
imwrite(transformedImage2,path);
saveName = 'subject05_rightlight_logarithmic_Transformation3.png';
path = append('./output_images/', saveName);
imwrite(transformedImage3,path);

transformedImageExp1 = CS4640_exponentialTransformation(subject05_rightlight, 0.01);
transformedImageExp2 = CS4640_exponentialTransformation(subject05_rightlight, 0.03);
transformedImageExp3 = CS4640_exponentialTransformation(subject05_rightlight, 0.05);

subplot(2,2,1), imshow(subject05_rightlight); title('Original Image');
subplot(2,2,2), imshow(transformedImageExp1); title('Log transformed Image 0.01');
subplot(2,2,3), imshow(transformedImageExp2); title('Log transformed Image 0.03');
subplot(2,2,4), imshow(transformedImageExp3); title('Log transformed Image 0.05');
figure();

saveName = 'subject05_rightlight_exponential_Transformation1.png';
path = append('./output_images/', saveName);
imwrite(transformedImageExp1,path);
saveName = 'subject05_rightlight_exponential_Transformation2.png';
path = append('./output_images/', saveName);
imwrite(transformedImageExp2,path);
saveName = 'subject05_rightlight_exponential_Transformation3.png';
path = append('./output_images/', saveName);
imwrite(transformedImageExp3,path);

% Question #2: Histograms --------------------------

% (a)

cameraman = imread('./Assignment-3-Images/grayscale/cameraman.tif');
[filepath,name,ext] = fileparts('./Assignment-3-Images/grayscale/cameraman.tif');

% My histograms

CS4640_displayMyHistogram(cameraman, 10);
saveName = append('./output_images/', name, '_my_histogram_10_bins.png');
saveas(gcf,saveName)
figure();
CS4640_displayMyHistogram(cameraman, 20);
saveName = append('./output_images/', name, '_my_histogram_20_bins.png');
saveas(gcf,saveName)
figure();
CS4640_displayMyHistogram(cameraman, 40);
saveName = append('./output_images/', name, '_my_histogram_40_bins.png');
saveas(gcf,saveName)
figure();
CS4640_displayMyHistogram(cameraman, 80);
saveName = append('./output_images/', name, '_my_histogram_80_bins.png');
saveas(gcf,saveName)
figure();
CS4640_displayMyHistogram(cameraman, 100);
saveName = append('./output_images/', name, '_my_histogram_100_bins.png');
saveas(gcf,saveName)
figure();

% matlab histograms
CS4640_displayMatlabHistogram(cameraman, 10);
saveName = append('./output_images/', name, '_histogram_10_bins.png');
saveas(gcf,saveName)
figure();
CS4640_displayMatlabHistogram(cameraman, 20);
saveName = append('./output_images/', name, '_histogram_20_bins.png');
saveas(gcf,saveName)
figure();
CS4640_displayMatlabHistogram(cameraman, 40);
saveName = append('./output_images/', name, '_histogram_40_bins.png');
saveas(gcf,saveName)
figure();
CS4640_displayMatlabHistogram(cameraman, 80);
saveName = append('./output_images/', name, '_histogram_80_bins.png');
saveas(gcf,saveName)
figure();
CS4640_displayMatlabHistogram(cameraman, 100);
saveName = append('./output_images/', name, '_histogram_100_bins.png');
saveas(gcf,saveName)
figure();

%(b)

% rice
rice = imread('./Assignment-3-Images/grayscale/rice.png');
[filepath,name,ext] = fileparts('./Assignment-3-Images/grayscale/rice.png');

CS4640_displayMatlabHistogram(rice, 255);
saveName = append('./output_images/', name, '_histogram_255_bins.png');
saveas(gcf,saveName);

thresholdedImageRice = CS4640_separateForgroundBackgroundImage(rice, 130);
figure();

subplot(2,1,1), imshow(rice); title('Original Image');
subplot(2,1,2), imshow(thresholdedImageRice); title('Threshold: 130');
figure();

saveName = 'thresholdedImageRice_Threshold_130.png';
path = append('./output_images/', saveName);
imwrite(thresholdedImageRice,path);

% coins
coins = imread('./Assignment-3-Images/grayscale/coins.png');
[filepath,name,ext] = fileparts('./Assignment-3-Images/grayscale/coins.png');

CS4640_displayMatlabHistogram(coins, 255);
saveName = append('./output_images/', name, '_histogram_255_bins.png');
saveas(gcf,saveName);

thresholdedImageCoins = CS4640_separateForgroundBackgroundImage(coins, 80);
figure();

subplot(2,1,1), imshow(coins); title('Original Image');
subplot(2,1,2), imshow(thresholdedImageCoins); title('Threshold: 80');
figure();

saveName = 'thresholdedImageCoins_Threshold_80.png';
path = append('./output_images/', saveName);
imwrite(thresholdedImageCoins,path);

%(c)

% pout.tif

pout = imread('./Assignment-3-Images/grayscale/pout.tif');

CS4640_displayMatlabHistogram(pout, 50);
saveName = append('./output_images/', 'pout', '_histogram_50_bins.png');
saveas(gcf,saveName);
figure();

contrastStretchPout = CS4640_stretchContrast(pout);
CS4640_displayMatlabHistogram(contrastStretchPout, 50);
saveName = append('./output_images/', 'pout_contrast_stretch', '_histogram_50_bins.png');
saveas(gcf,saveName);
figure();

subplot(2,1,1), imshow(pout); title('Original Image');
subplot(2,1,2), imshow(contrastStretchPout); title('Contrast Stretched Image');
figure();



saveName = 'contrast_stretch_pout.png';
path = append('./output_images/', saveName);
imwrite(contrastStretchPout,path);

% subject01.glasses.png

sub01glasses = imread('./Assignment-3-Images/grayscale/subject01.glasses.png');

CS4640_displayMatlabHistogram(sub01glasses, 50);
saveName = append('./output_images/', 'sub01glasses', '_histogram_50_bins.png');
saveas(gcf,saveName);
figure();

contrastStretchSub01glasses = CS4640_stretchContrast(sub01glasses);
CS4640_displayMatlabHistogram(contrastStretchSub01glasses, 50);
saveName = append('./output_images/', 'sub01glasses_contrast_stretch', '_histogram_50_bins.png');
saveas(gcf,saveName);

subplot(2,1,1), imshow(sub01glasses); title('Original Image');
subplot(2,1,2), imshow(contrastStretchSub01glasses); title('Contrast Stretched Image');
figure();

saveName = 'contrast_stretch_sub01glasses.png';
path = append('./output_images/', saveName);
imwrite(contrastStretchSub01glasses,path);

%(d)

% pout.tif

pout = imread('./Assignment-3-Images/grayscale/pout.tif');

poutEq = CS4640_equalizeHistogram(pout);

subplot(2,2,1), imshow(pout); title('Original Image');   %Display image
subplot(2,2,2), imshow(poutEq); title('Equalized Image');	%Display result
subplot(2,2,3), imhist(pout); title('Original Histogram');	%Display histogram of image
subplot(2,2,4), imhist(poutEq); title('Equalized Histogram');	%Display histogram of result

saveName = append('./output_images/', 'pout_equalized', '_histogram&images.png');
saveas(gcf,saveName);

figure();

saveName = 'equalized_histogram_pout.png';
path = append('./output_images/', saveName);
imwrite(poutEq,path);

% subject01.glasses.png

sub01glasses = imread('./Assignment-3-Images/grayscale/subject01.glasses.png');

sub01glassesEq = CS4640_equalizeHistogram(sub01glasses);

subplot(2,2,1), imshow(sub01glasses); title('Original Image');   %Display image
subplot(2,2,2), imshow(sub01glassesEq); title('Equalized Image');	%Display result
subplot(2,2,3), imhist(sub01glasses); title('Original Histogram');	%Display histogram of image
subplot(2,2,4), imhist(sub01glassesEq); title('Equalized Histogram');	%Display histogram of result

saveName = append('./output_images/', 'sub01glasses_equalized', '_histogram&images.png');
saveas(gcf,saveName);

saveName = 'equalized_histogram_sub01glassesEq.png';
path = append('./output_images/', saveName);
imwrite(sub01glassesEq,path);

% Functions -----------------------------------------------------

function [combinedImage] = CS4640_blendTwoImages(image1, image2, weight)
%
% CS4640_1DConvolution - This function blends two given images using their
% weighted combination.
%
% Input:-
%     <input variable image1> (image): An image used to be blended with.
%     <input variable image2> (image): An image used to be blended with.
%     <input variable weight> (int): The weight used by the first image
%     with the second image's weight set to 1 minuse the given weight.
% Output:-
%     <output variable combinedImage> (image): The combined image.
%  Example usage:
%		combinedImage1 = CS4640_blendTwoImages(image1, image2, 0.5);
% Author:
%     Harry Kim
%		Fall 2022
%

combinedImage = imlincomb(weight, image1, 1-weight, image2);

end

function[redPixelregionedImages] = CS4640_separatePixelsOfRedCar(image, threshold)
%
% CS4640_separatePixelsOfRedCar - This function separates red pixels of an
% image given a threshold.
%
% Input:-
%     <input variable image> (image): An input image.
%     <input variable threshold> (int): A threshold to determine what color
%     counts as "red" in a pixel.
% Output:-
%     <output variable redPixelregionedImages> (image): An image that
%     contains only red pixels.
%  Example usage:
%		carpark = imread('./Assignment-3-Images/color/carpark.png');
%       onlyRedPixels = CS4640_separatePixelsOfRedCar(carpark, 100);
% Author:
%     Harry Kim
%		Fall 2022
%

R = image(:,:,1);
G = image(:,:,2);
B = image(:,:,3);

% Any pixel that has a red channel value above the threshold while the blue
% and green channel values of said pixel are below the threshold.
mask = R>threshold & G<threshold & B<threshold;
R(~mask) = 0;
G(~mask) = 0;
B(~mask) = 0;

redPixelregionedImages = cat(3,R,G,B);

end

function [newImage] = CS4640_logarithmicTransformation(image, sigma)
%
% CS4640_logarithmicTransformation - This function performs a logarithmic
% transformation on an image to adjust the contrast/brightness.
%
% Input:-
%     <input variable image> (image): An input image.
%     <input variable sigma> (int): The scaling factor that controls the
%     input range to the logarithmic function.
% Output:-
%     <output variable redPixelregionedImages> (image): The transformed
%     image.
%  Example usage:
%		subject05_rightlight = imread('./Assignment-3-Images/grayscale/subject05.rightlight.png');
%       transformedImage1 = CS4640_logarithmicTransformation(subject05_rightlight, 0.01);
% Author:
%     Harry Kim
%		Fall 2022
%

newImage = image;

maxx = max(image(:));

%maxx = 255;

for r = 1:size(image, 1)    % for number of rows of the image
    for c = 1:size(image, 2)    % for number of columns of the image

        constant = 255/(log10(double(1+maxx)));

        newImage(r,c) = constant * log(double(1 + (exp(sigma) - 1) * image(r,c)));

    end
end



end

function [newImage] = CS4640_exponentialTransformation(image, alpha)
%
% CS4640_exponentialTransformation - This function performs an exponential
% transformation on an image to adjust the contrast/brightness.
%
% Input:-
%     <input variable image> (image): An input image.
%     <input variable alpha> (int): A variable used to calculate the base
%     of the function.
% Output:-
%     <output variable redPixelregionedImages> (image): The transformed
%     image.
%  Example usage:
%		subject05_rightlight = imread('./Assignment-3-Images/grayscale/subject05.rightlight.png');
%       transformedImageExp1 = CS4640_exponentialTransformation(subject05_rightlight, 0.01);
% Author:
%     Harry Kim
%		Fall 2022
%

newImage = image;

maxx = max(image(:));

%maxx = 255;

for r = 1:size(image, 1)    % for number of rows of the image
    for c = 1:size(image, 2)    % for number of columns of the image

        constant = 255/(log10(double(1+maxx)));

        newImage(r,c) = constant * ((1 + alpha)^(image(r,c)) - 1);

    end
end

end

function [] = CS4640_displayMyHistogram(image, numBins)
%
% CS4640_displayMyHistogram - This function computes the histogram of a given
% image given the number of bins.
%
% Input:-
%     <input variable image> (image): An input image.
%     <input variable numBins> (int): The number of bins to be used.
% Output:-
%     None. This function displays the histogram in a separate subplot.
%  Example usage:
%		cameraman = imread('./Assignment-3-Images/grayscale/cameraman.tif');
%       CS4640_displayMyHistogram(cameraman, 10);
% Author:
%     Harry Kim
%		Fall 2022
%

bins = linspace(0, 255, numBins);

binsize = size(bins);

array = zeros(1, numBins);

for r = 1:size(image, 1)    % for number of rows of the image
    for c = 1:size(image, 2)    % for number of columns of the image
        currentPixel = image(r, c);
        correctBin = round(double(currentPixel)/255.0 * binsize(2));
        if correctBin < 1
            correctBin = 1;
        end
        array(correctBin) = array(correctBin)+1;
    end
end

subplot(1,2,1), imshow(image); title("Original Image");
subplot(1,2,2),
Number = linspace(1, numBins, length(array));
bar(Number, array);
xlabel('Bin');
ylabel('Frequency (count)');
title("My histogram. Bins: " + numBins);

end

function [] = CS4640_displayMatlabHistogram(image, numBins)
%
% CS4640_displayHistogram - This function computes the histogram of a given
% image given the number of bins.
%
% Input:-
%     <input variable image> (image): An input image.
%     <input variable numBins> (int): The number of bins to be used.
% Output:-
%     None. This function displays the histogram in a separate subplot.
%  Example usage:
%		cameraman = imread('./Assignment-3-Images/grayscale/cameraman.tif');
%       CS4640_displayMatlabHistogram(cameraman, 10);
% Author:
%     Harry Kim
%		Fall 2022
%

subplot(1,2,1), imshow(image); title("Original Image");
subplot(1,2,2), histogram(image, numBins); title("Bins: " + numBins);

end

function [newImage] = CS4640_separateForgroundBackgroundImage(image, threshold)
%
% CS4640_thresholdImage - This function separates foreground objects from
% the background with a given threshold.
%
% Input:-
%     <input variable image> (image): An input image.
%     <input variable threshold> (int): The threshold to separate the
%     forground from the background.
% Output:-
%     <output variable redPixelregionedImages> (image): A new image that
%     highlights forground objects.
%  Example usage:
%		rice = imread('./Assignment-3-Images/grayscale/rice.png');
%       CS4640_separateForgroundBackgroundImage = CS4640_thresholdImage(rice, 130);
% Author:
%     Harry Kim
%		Fall 2022
%

newImage = image;

for r = 1:size(image, 1)    % for number of rows of the image
    for c = 1:size(image, 2)    % for number of columns of the image
        if image(r,c) > threshold
            newImage(r,c) = 255;
        else
            newImage(r,c) = 0;
        end
    end
end

end

function [newImage] = CS4640_stretchContrast(image)
%
% CS4640_stretchContrast - This function stretchs the contrast of a given
% image.
%
% Input:-
%     <input variable image> (image): An input image.
% Output:-
%     <output variable newImage> (image): The image with the contrast
%     stretched.
%  Example usage:
%		pout = imread('./Assignment-3-Images/grayscale/pout.tif');
%       contrastStretchPout = CS4640_stretchContrast(pout);
% Author:
%     Harry Kim
%		Fall 2022
%

newImage = image;

maxx = max(image(:));

minn = min(image(:));

% [counts,binLocations] = imhist(image,255);
% 
% maxx = prctile(counts,95);
% 
% minn = prctile(counts,5);

for r = 1:size(image, 1)    % for number of rows of the image
    for c = 1:size(image, 2)    % for number of columns of the image

        num1 = double(image(r,c)) - double(maxx);

        num2 = num1 * double((255-0)/(maxx - minn));

        newImage(r,c) = num2 + 255.0;

    end
end

end

function [newImage] = CS4640_equalizeHistogram(image)
%
% CS4640_equalizeHistogram - This function equalizes the histogram of a
% given image using built-in matlab functions.
%
% Input:-
%     <input variable image> (image): An input image.
% Output:-
%     <output variable newImage> (image): The image with its histogram
%     equalized.
%  Example usage:
%		sub01glasses = imread('./Assignment-3-Images/grayscale/subject01.glasses.png');
%       sub01glassesEq = CS4640_equalizeHistogram(sub01glasses);
% Author:
%     Harry Kim
%		Fall 2022
%

newImage=histeq(image);

end
