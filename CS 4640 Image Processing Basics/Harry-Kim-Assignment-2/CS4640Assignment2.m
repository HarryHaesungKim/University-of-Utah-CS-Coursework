clc

% Question #1: 1D Convolution --------------------------

% (c)
f = [0 0 1 1 1 1 1 0.5 0];
h = [-2 -1.5 -1 -0.5 0 0.5 1 1.5 2];
g = CS4640_1DConvolution(f, h);

fprintf('My answer: \n');
fprintf('g: [%s]\n\n', join(string(g), ','));

fprintf('Matlab answer: \n');
fprintf('g: [%s]\n\n', join(string(conv(f, h)), ','));

% (d)
f = [0 0 2 2 2 4 4 4 0 0];
h = [-1 1];
g = CS4640_1DConvolution(f, h);

fprintf('My answer: \n');
fprintf('g: [%s]\n\n', join(string(g), ','));

fprintf('Matlab answer: \n');
fprintf('g: [%s]\n\n', join(string(conv(f, h)), ','));

f = [0 0 2 2 2 4 4 4 0 0];
h = [-1 2 1];
g = CS4640_1DConvolution(f, h);

fprintf('My answer: \n');
fprintf('g: [%s]\n\n', join(string(g), ','));

fprintf('Matlab answer: \n');
fprintf('g: [%s]\n\n', join(string(conv(f, h)), ','));

% Question #2: PSF & 2D convolution --------------------------

% (a)

newImage1 = CS4640_squareImageGaussianFunction(100, 10);
newImage2 = CS4640_squareImageGaussianFunction(100, 50);
newImage3 = CS4640_squareImageGaussianFunction(100, 300);
newImage4 = CS4640_squareImageGaussianFunction(100, 600);

subplot(2,2,1), imshow(newImage1); title('Gaussian Image sigma: 10');

subplot(2,2,2), imshow(newImage2); title('Gaussian Image sigma: 50');

subplot(2,2,3), imshow(newImage3); title('Gaussian Image sigma: 300');

subplot(2,2,4), imshow(newImage4); title('Gaussian Image sigma: 600');

figure();

% Saving images to output_images

saveName = append('squareImageGaussianFunction-100-10.jpg');
path = append('./output_images/', saveName);
imwrite(newImage1,path);

saveName = append('squareImageGaussianFunction-100-50.jpg');
path = append('./output_images/', saveName);
imwrite(newImage2,path);

saveName = append('squareImageGaussianFunction-100-300.jpg');
path = append('./output_images/', saveName);
imwrite(newImage3,path);

saveName = append('squareImageGaussianFunction-100-600.jpg');
path = append('./output_images/', saveName);
imwrite(newImage4,path);

% (b)

% cameraman.tif
inputImage = imread('./Assignment-1-Images/grayscale/cameraman.tif');

newImage1 = CS4640_convolutionWithPSF(inputImage, 10, 3);
newImage2 = CS4640_convolutionWithPSF(inputImage, 10, 50);
newImage3 = CS4640_convolutionWithPSF(inputImage, 50, 10);
newImage4 = CS4640_convolutionWithPSF(inputImage, 100, 40);

subplot(2,2,1), imshow(newImage1); title('Gaussian Image size: 10 sigma: 3');
subplot(2,2,2), imshow(newImage2); title('Gaussian Image size: 10 sigma: 50');
subplot(2,2,3), imshow(newImage3); title('Gaussian Image size: 50 sigma: 10');
subplot(2,2,4), imshow(newImage4); title('Gaussian Image size: 100 sigma: 40');

figure();

% Saving images to output_images

[filepath,name,ext] = fileparts('./Assignment-1-Images/grayscale/cameraman.tif');

saveName = append(name, '_convoPSF_10_3');
path = append('./output_images/', saveName, '.jpg');
imwrite(newImage1,path);

saveName = append(name, '_convoPSF_10_50');
path = append('./output_images/', saveName, '.jpg');
imwrite(newImage2,path);

saveName = append(name, '_convoPSF_50_10');
path = append('./output_images/', saveName, '.jpg');
imwrite(newImage3,path);

saveName = append(name, '_convoPSF_100_40');
path = append('./output_images/', saveName, '.jpg');
imwrite(newImage4,path);

% cell.tif
inputImage = imread('./Assignment-1-Images/grayscale/cell.tif');

newImage1 = CS4640_convolutionWithPSF(inputImage, 10, 3);
newImage2 = CS4640_convolutionWithPSF(inputImage, 10, 50);
newImage3 = CS4640_convolutionWithPSF(inputImage, 50, 10);
newImage4 = CS4640_convolutionWithPSF(inputImage, 100, 40);

subplot(2,2,1), imshow(newImage1); title('Gaussian Image size: 10 sigma: 3');
subplot(2,2,2), imshow(newImage2); title('Gaussian Image size: 10 sigma: 50');
subplot(2,2,3), imshow(newImage3); title('Gaussian Image size: 50 sigma: 10');
subplot(2,2,4), imshow(newImage4); title('Gaussian Image size: 100 sigma: 40');

figure();

% Saving images to output_images

[filepath,name,ext] = fileparts('./Assignment-1-Images/grayscale/cell.tif');

saveName = append(name, '_convoPSF_10_3');
path = append('./output_images/', saveName, '.jpg');
imwrite(newImage1,path);

saveName = append(name, '_convoPSF_10_50');
path = append('./output_images/', saveName, '.jpg');
imwrite(newImage2,path);

saveName = append(name, '_convoPSF_50_10');
path = append('./output_images/', saveName, '.jpg');
imwrite(newImage3,path);

saveName = append(name, '_convoPSF_100_40');
path = append('./output_images/', saveName, '.jpg');
imwrite(newImage4,path);

% (c)

% cameraman.tif
newImage1 = CS4640_convolutionWithRandomPTS('./Assignment-1-Images/grayscale/cameraman.tif', 10, 50, 0.90);

newImage2 = CS4640_convolutionWithRandomPTS('./Assignment-1-Images/grayscale/cameraman.tif', 10, 50, 0.50);

% Saving images to output_images

[filepath,name,ext] = fileparts('./Assignment-1-Images/grayscale/cameraman.tif');

saveName = append(name, '_convoPSFWithRandomPTS_90_percent');
path = append('./output_images/', saveName, '.jpg');
imwrite(newImage1,path);

saveName = append(name, '_convoPSFWithRandomPTS_50_percent');
path = append('./output_images/', saveName, '.jpg');
imwrite(newImage2,path);

% cell.tif
newImage1 = CS4640_convolutionWithRandomPTS('./Assignment-1-Images/grayscale/cell.tif', 10, 50, 0.90);

newImage2 = CS4640_convolutionWithRandomPTS('./Assignment-1-Images/grayscale/cell.tif', 10, 50, 0.50);

% Saving images to output_images

[filepath,name,ext] = fileparts('./Assignment-1-Images/grayscale/cell.tif');

saveName = append(name, '_convoPSFWithRandomPTS_90_percent');
path = append('./output_images/', saveName, '.jpg');
imwrite(newImage1,path);

saveName = append(name, '_convoPSFWithRandomPTS_50_percent');
path = append('./output_images/', saveName, '.jpg');
imwrite(newImage2,path);

% Question #3: Quantization --------------------------

%(a)

%cameraman.tif
quantizedImage1 = CS4640_quantizePixelsOfGrayscaleImage('./Assignment-1-Images/grayscale/cameraman.tif', 128);
quantizedImage2 = CS4640_quantizePixelsOfGrayscaleImage('./Assignment-1-Images/grayscale/cameraman.tif', 64);
quantizedImage3 = CS4640_quantizePixelsOfGrayscaleImage('./Assignment-1-Images/grayscale/cameraman.tif', 8);
quantizedImage4 = CS4640_quantizePixelsOfGrayscaleImage('./Assignment-1-Images/grayscale/cameraman.tif', 4);

subplot(2,3,1), imshow(imread('./Assignment-1-Images/grayscale/cameraman.tif')); title('Original Image');
subplot(2,3,2), imshow(quantizedImage1); title('Quantized Image 128 levels');
subplot(2,3,3), imshow(quantizedImage2); title('Quantized Image 64 levels');
subplot(2,3,4), imshow(quantizedImage3); title('Quantized Image 8 levels');
subplot(2,3,5), imshow(quantizedImage4); title('Quantized Image 4 levels');

figure();

% Saving images to output_images

[filepath,name,ext] = fileparts('./Assignment-1-Images/grayscale/cameraman.tif');

saveName = append(name, '_quantizePixelsOfGrayscaleImage_128');
path = append('./output_images/', saveName, '.jpg');
imwrite(quantizedImage1,path);

saveName = append(name, '_quantizePixelsOfGrayscaleImage_64');
path = append('./output_images/', saveName, '.jpg');
imwrite(quantizedImage2,path);

saveName = append(name, '_quantizePixelsOfGrayscaleImage_8');
path = append('./output_images/', saveName, '.jpg');
imwrite(quantizedImage3,path);

saveName = append(name, '_quantizePixelsOfGrayscaleImage_4');
path = append('./output_images/', saveName, '.jpg');
imwrite(quantizedImage4,path);

%cell.tif
quantizedImage1 = CS4640_quantizePixelsOfGrayscaleImage('./Assignment-1-Images/grayscale/cell.tif', 128);
quantizedImage2 = CS4640_quantizePixelsOfGrayscaleImage('./Assignment-1-Images/grayscale/cell.tif', 64);
quantizedImage3 = CS4640_quantizePixelsOfGrayscaleImage('./Assignment-1-Images/grayscale/cell.tif', 8);
quantizedImage4 = CS4640_quantizePixelsOfGrayscaleImage('./Assignment-1-Images/grayscale/cell.tif', 4);

subplot(2,3,1), imshow(imread('./Assignment-1-Images/grayscale/cell.tif')); title('Original Image');
subplot(2,3,2), imshow(quantizedImage1); title('Quantized Image 128 levels');
subplot(2,3,3), imshow(quantizedImage2); title('Quantized Image 64 levels');
subplot(2,3,4), imshow(quantizedImage3); title('Quantized Image 8 levels');
subplot(2,3,5), imshow(quantizedImage4); title('Quantized Image 4 levels');

figure();

% Saving images to output_images

[filepath,name,ext] = fileparts('./Assignment-1-Images/grayscale/cell.tif');

saveName = append(name, '_quantizePixelsOfGrayscaleImage_128');
path = append('./output_images/', saveName, '.jpg');
imwrite(quantizedImage1,path);

saveName = append(name, '_quantizePixelsOfGrayscaleImage_64');
path = append('./output_images/', saveName, '.jpg');
imwrite(quantizedImage2,path);

saveName = append(name, '_quantizePixelsOfGrayscaleImage_8');
path = append('./output_images/', saveName, '.jpg');
imwrite(quantizedImage3,path);

saveName = append(name, '_quantizePixelsOfGrayscaleImage_4');
path = append('./output_images/', saveName, '.jpg');
imwrite(quantizedImage4,path);

%(b)

% bowl_fruit.png
theImage = imread('./Assignment-1-Images/color/bowl_fruit.png');

quantizedImage = CS4640_quantizePixelsOfColorImage(theImage, 4);

subplot(1,2,1), imshow(theImage); title('Original Image');
subplot(1,2,2), imshow(quantizedImage); title('Quantized Image 4 levels');

% Saving images to output_images

[filepath,name,ext] = fileparts('./Assignment-1-Images/color/bowl_fruit.png');
saveName = append(name, '_quantizePixelsOfColorImage_4');
path = append('./output_images/', saveName, ext);
imwrite(quantizedImage,path);

figure();

HSVColorSpaceImage = rgb2hsv(imread('./Assignment-1-Images/color/bowl_fruit.png'));
quantizedHSVImage = CS4640_quantizePixelsOfColorImage(HSVColorSpaceImage, 4);

subplot(1,2,1), imshow(HSVColorSpaceImage); title('Original HSV Image');
subplot(1,2,2), imshow(quantizedHSVImage); title('Quantized HSV Image 255 levels');

figure();

% Saving images to output_images

[filepath,name,ext] = fileparts('./Assignment-1-Images/color/bowl_fruit.png');
saveName = append(name, '_HSVColorSpaceImage');
path = append('./output_images/', saveName, ext);
imwrite(HSVColorSpaceImage,path);

[filepath,name,ext] = fileparts('./Assignment-1-Images/color/bowl_fruit.png');
saveName = append(name, '_quantizePixelsOfColorImageHSV_4');
path = append('./output_images/', saveName, ext);
imwrite(quantizedHSVImage,path);

% onion.png
theImage = imread('./Assignment-1-Images/color/onion.png');

quantizedImage = CS4640_quantizePixelsOfColorImage(theImage, 4);

subplot(1,2,1), imshow(theImage); title('Original Image');
subplot(1,2,2), imshow(quantizedImage); title('Quantized Image 4 levels');

% Saving images to output_images

[filepath,name,ext] = fileparts('./Assignment-1-Images/color/onion.png');
saveName = append(name, '_quantizePixelsOfColorImage_4');
path = append('./output_images/', saveName, ext);
imwrite(quantizedImage,path);

figure();

HSVColorSpaceImage = rgb2hsv(imread('./Assignment-1-Images/color/onion.png'));
quantizedHSVImage = CS4640_quantizePixelsOfColorImage(HSVColorSpaceImage, 4);

subplot(1,2,1), imshow(HSVColorSpaceImage); title('Original HSV Image');
subplot(1,2,2), imshow(quantizedHSVImage); title('Quantized HSV Image 255 levels');

figure();

% Saving images to output_images

[filepath,name,ext] = fileparts('./Assignment-1-Images/color/onion.png');
saveName = append(name, '_HSVColorSpaceImage');
path = append('./output_images/', saveName, ext);
imwrite(HSVColorSpaceImage,path);

[filepath,name,ext] = fileparts('./Assignment-1-Images/color/onion.png');
saveName = append(name, '_quantizePixelsOfColorImageHSV_4');
path = append('./output_images/', saveName, ext);
imwrite(quantizedHSVImage,path);

% Question #4: Extended projects --------------------------

% (a)

h1 = [-1 0 1; -1 0 1; -1 0 1];
h2 = [-1 -1 -1; 0 0 0; 1 1 1];
h3 = [0 -1 0; -1 4 -1; 0 -1 0];

theImage1 = imread('./extended_projects_images/normal-image002.jpg');

newImage1_1 = convn(im2double(theImage1), im2double(h1), 'same');
newImage1_2 = convn(im2double(theImage1), im2double(h2), 'same');
newImage1_3 = convn(im2double(theImage1), im2double(h3), 'same');

subplot(2,2,1), imshow(theImage1); title('Original Image');
subplot(2,2,2), imshow(newImage1_1); title('Convolved with PSF1');
subplot(2,2,3), imshow(newImage1_2); title('Convolved with PSF2');
subplot(2,2,4), imshow(newImage1_3); title('Convolved with PSF3');

figure();

% Saving images to output_images

[filepath,name,ext] = fileparts('./extended_projects_images/normal-image002.jpg');

saveName = append(name, '_convolveWith2DPSF1');
path = append('./output_images/', saveName, ext);
imwrite(newImage1_1,path);

saveName = append(name, '_convolveWith2DPSF2');
path = append('./output_images/', saveName, ext);
imwrite(newImage1_2,path);

saveName = append(name, '_convolveWith2DPSF3');
path = append('./output_images/', saveName, ext);
imwrite(newImage1_3,path);

% (b)

theImage1 = imread('./extended_projects_images/image_004.jpg');
theImage2 = imread('./extended_projects_images/image_005.jpg');

combinedImage1 = imlincomb(0.5, theImage1, 0.5, theImage2);
gaussianCombinedImage1 = CS4640_convolutionWithPSF(combinedImage1, 50, 10);

subplot(2,2,1), imshow(theImage1); title('Original Image1');
subplot(2,2,2), imshow(theImage2); title('Original Image2');
subplot(2,2,3), imshow(combinedImage1); title('Combined Images');
subplot(2,2,4), imshow(gaussianCombinedImage1); title('Gaussian Combined Images');

% Saving images to output_images

saveName = append('indy_combinedThenGaussian');
path = append('./output_images/', saveName, '.jpg');
imwrite(gaussianCombinedImage1,path);

figure();

gaussianImage1 = CS4640_convolutionWithPSF(theImage1, 50, 10);
gaussianImage2 = CS4640_convolutionWithPSF(theImage2, 50, 10);

combinedGaussianImage1 = imlincomb(0.5, gaussianImage1, 0.5, gaussianImage2);

subplot(2,3,1), imshow(theImage1); title('Original Image1');
subplot(2,3,2), imshow(theImage2); title('Original Image2');
subplot(2,3,3), imshow(gaussianImage1); title('Gaussian Image1');
subplot(2,3,4), imshow(gaussianImage2); title('Gaussian Image2');
subplot(2,3,5), imshow(combinedGaussianImage1); title('Combined Gaussian Images');

%visually they appear the same. Numerically, all values of
%gaussianCombinedImage1 is 0.0003 more than all the values of
%combinedGaussianImage1 They are pretty much the same.

% Saving images to output_images

saveName = append('indy_gaussianThenCombined');
path = append('./output_images/', saveName, '.jpg');
imwrite(combinedGaussianImage1,path);

% functions ----------------------------------------------------

function [g] = CS4640_1DConvolution(f, h)
%
% CS4640_1DConvolution - This function calculates the convolution between
% two 1D matricies.
%
% Input:-
%     <input variable f> (number array): One of the arrays to be convoluted.
%     <input variable h> (number array): One of the arrays to be convoluted.
% Output:-
%     <output variable g> (number array): The calculated convoluted array.
%  Example usage:
%		f = [0 0 2 2 2 4 4 4 0 0];
%       h = [-1 2 1];
%       g = CS4640_1DConvolution(f, h);
% Author:
%     Harry Kim
%		Fall 2022
%
g = zeros(1, length(f) + length(h) - 1);

for i = 1:length(g)
    for j = 1: length(f)
        if(0 < i-j+1)
            if(i-j+1 > length(h))
                g(i) = g(i) + f(j) * 0;
            else
                g(i) = g(i) + f(j) * h(i-j+1);
            end
        end
    end
end

end

function [newImage] = CS4640_squareImageGaussianFunction(sideLength, sd)
%
% CS4640_squareImageGaussianFunction - This function constructs a square
% image/matrix with a Gaussian function in its center.
%
% Input:-
%     <input variable sideLength> (int): Number of pixels in the side.
%     <input variable sd> (double): Standard deviation of the function.
% Output:-
%     <output variable newImage> (image): The image created by the gaussian
%     function.
%  Example usage:
%		newImage1 = CS4640_squareImageGaussianFunction(100, 10);
% Author:
%     Harry Kim
%		Fall 2022
%
twoDArray = zeros(sideLength,sideLength);

for x = 1:sideLength
    for y = 1:sideLength
        twoDArray(x,y) = (1/(2*pi*sd^2))*exp(-((x-sideLength/2)^2+(y-sideLength/2)^2)/(2*sd^2));
    end
end

newImage = mat2gray(twoDArray);

end

function [newImage] = CS4640_convolutionWithPSF(image, sideLength, sd)
%
% CS4640_convolutionWithPSF - This function computes the convolution of a
% given 2D point spread function (PSF) with an object/scene function.
%
% Input:-
%     <input variable image> (image): The image to be modified.
%     <input variable sideLength> (int): Number of pixels in the side.
%     <input variable sd> (double): Standard deviation of the function.
% Output:-
%     <output variable newImage> (image): A new image with the gaussian
%     filter effect.
%  Example usage:
%		newImage1 = CS4640_convolutionWithPSF(inputImage, 10, 3);
% Author:
%     Harry Kim
%		Fall 2022
%
kernel = CS4640_squareImageGaussianFunction(sideLength, sd);

kernel = kernel/sum(kernel(:)); % normalize the filter

newImage = convn(im2double(image), im2double(kernel), 'same');

end

function [newImage] = CS4640_convolutionWithRandomPTS(image, sideLength, sd, percentage)
%
% CS4640_convolutionWithPSF - This function computes the PSF convolution
% with the object/scene function using a random percentage of the points in
% the object function.
%
% Input:-
%     <input variable image> (image): The image to be modified.
%     <input variable sideLength> (int): Number of pixels in the side.
%     <input variable sd> (double): Standard deviation of the function.
%     <input variable percentage> (double): percent of pixels to hold a
%     value of zero.
% Output:-
%     <output variable newImage> (image): A new image with random points to
%     be affected by the gaussian function.
%  Example usage:
%		CS4640_convolutionWithRandomPTS('./Assignment-1-Images/grayscale/cameraman.tif', 10, 50, 0.90);
% Author:
%     Harry Kim
%		Fall 2022
%
theImage = imread(image);
 
idx = randperm(numel(theImage),round(numel(theImage)*percentage)) ; % get 80% of indices randomly 
theImage(idx) = 0 ;  % replace with zero 

% theImage now has random black pixels. Now to add gaussian filter.

newImage = CS4640_convolutionWithPSF(theImage, sideLength, sd);

subplot(2,2,1), imshow(theImage); title('test1');
subplot(2,2,2), imshow(newImage); title('test1');

end

function [grayscaleImage] = CS4640_quantizePixelsOfGrayscaleImage(image, numBins)
%
% CS4640_quantizePixelsOfGrayscaleImage - This function quantizes each pixel
% of a given grayscale image the index of the bin that its intensity lies in.
%
% Input:-
%     <input variable imageInput> (character vector): The name of the image to be used.
%     <input variable numBins> (integer): The number of bins to be used.
% Output:-
%     <output variable grayscaleImage> (image): The new image with quantized pixels.
%  Example usage:
%		newCoolImage = CS4640_quantizePixelsOfGrayscaleImage(image, numBins);
% Author:
%     Harry Kim
%		Fall 2022
%
theImage = imread(image);

grayscaleImage = theImage;

bins = linspace(0, 255, numBins);

for r = 1:size(theImage, 1)    % for number of rows of the image
    for c = 1:size(theImage, 2)    % for number of columns of the image
        currentPixel = grayscaleImage(r, c);

        binsize = size(bins);
        
        correctBin = round(double(currentPixel)/255.0 * binsize(2));

        if correctBin < 1
            correctBin = 1;
        end

        grayscaleImage(r,c) = bins(correctBin);
    end
end
end

function [quantizedImage] = CS4640_quantizePixelsOfColorImage(image, numBins)
%
% CS4640_quantizePixelsOfColorImage - This function quantizes each pixel
% of a given color image the index of the bin that its intensity lies in.
%
% Input:-
%     <input variable image> (image): The image to be used.
%     <input variable numBins> (integer): The number of bins to be used.
% Output:-
%     <output variable quantizedImage> (image): The new image with quantized pixels.
%  Example usage:
%		newCoolImage = CS4640_quantizePixelsOfColorImage(image, numBins);
% Author:
%     Harry Kim
%		Fall 2022
%
theImage = image;

[redChannel,greenChannel,blueChannel] = imsplit(theImage);

bins = linspace(0, 255, numBins);

for r = 1:size(theImage, 1)    % for number of rows of the image
    for c = 1:size(theImage, 2)    % for number of columns of the image
        currentPixelR = redChannel(r, c);
        currentPixelG = greenChannel(r, c);
        currentPixelB = blueChannel(r, c);

        binsize = size(bins);
        
        correctBinR = round(double(currentPixelR)/255.0 * binsize(2));
        correctBinG = round(double(currentPixelG)/255.0 * binsize(2));
        correctBinB = round(double(currentPixelB)/255.0 * binsize(2));


        if correctBinR < 1
            correctBinR = 1;
        end

        if correctBinG < 1
            correctBinG = 1;
        end

        if correctBinB < 1
            correctBinB = 1;
        end

        %grayscaleImage(r,c) = bins(correctBin);
        redChannel(r,c) = bins(correctBinR);
        greenChannel(r,c) = bins(correctBinG);
        blueChannel(r,c) = bins(correctBinB);
    end
end

quantizedImage = cat(3, redChannel, greenChannel, blueChannel);

end


