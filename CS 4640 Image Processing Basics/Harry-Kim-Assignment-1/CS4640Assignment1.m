% Question #1: Image pixels and color --------------------------

% (a)
% cameraman.tif
fprintf("Info for cameraman.tif:\n\n")
CS4640_getImageInfoGrayscale('cameraman.tif');

% cell.tif
fprintf("Info for cell.tif:\n\n")
CS4640_getImageInfoGrayscale('cell.tif');

%(b)
CS4640_addScalarToGrayscaleImage('cameraman.tif', 100);
figure();

CS4640_addScalarToGrayscaleImage('cell.tif', -100);
figure();

CS4640_addScalarToPixelOfGrayscaleImage('cameraman.tif', -150, 1, 1);
figure();

%(c)
fprintf("Info for bowl_fruit.png:\n\n")
CS4640_getImageInfoColor('bowl_fruit.png');

fprintf("Info for edin_castle.png:\n\n")
CS4640_getImageInfoColor('edin_castle.png');

%(d)
CS4640_addScalarToColorImage('bowl_fruit.png', 100, 100, 100);
figure();
CS4640_addScalarToColorImage('bowl_fruit.png', 50, 50, 50);
figure();
CS4640_addScalarToColorImage('bowl_fruit.png', -200, -100, -50);
figure();
CS4640_addScalarToColorImage('bowl_fruit.png', 100, 20, -50);
figure();

%(e)
CS4640_falseColorMapGrayscaleImage('spine.tif');
figure();

% Question #2: Image resolution -----------------------------------

%(a)
CS4640_imageResizeAndReverse('onion.png');
figure();
CS4640_imageResizeAndReverse('cameraman.tif');
figure();

%(b)
CS4640_extractBitPlanesOfGrayscaleImage('cameraman.tif');
figure();
CS4640_extractBitPlanesOfGrayscaleImage('cell.tif');
figure();

% Question #3: Image formats -----------------------------------

CS4640_saveImageToGivenFormat('bowl_fruit.png', 'png');
figure();

CS4640_saveImageToGivenFormat('bowl_fruit.png', 'jpeg');
figure();

CS4640_saveImageToGivenFormat('bowl_fruit.png', 'tiff');
figure();

CS4640_saveImageToGivenFormat('bigben.png', 'png');
figure();

CS4640_saveImageToGivenFormat('bigben.png', 'jpeg');
figure();

CS4640_saveImageToGivenFormat('bigben.png', 'tiff');
figure();

% Question #4: Color spaces -----------------------------------

%(a)
CS4640_convertColoredImageToHSVColorSpace('bowl_fruit.png');
figure();
CS4640_convertColoredImageToHSVColorSpace('edin_castle.png');
figure();

%(b)
CS4640_computeWeightingFactorsRGB2Grayscale('bowl_fruit.png');

% Question #5: Extended projects -----------------------------------

%(a)
bins = CS4640_divideRangeOfIntensitiesIntoBins(0, 40, 5);

%fprintf(bins);

%(b)

CS4640_quantizePixelsOfImage('./project-indy/image_004.jpg',3);
figure();
CS4640_quantizePixelsOfImage('./project-indy/image_004.jpg',5);
figure();
CS4640_quantizePixelsOfImage('./project-indy/image_004.jpg',7);
figure();
% quantizePixelsOfImage('./project-indy/image_004.jpg',20);

%(c)
CS4640_distinguisBackgroundAndForground('./project-indy/image_004.jpg',5);


% Functions -----------------------------------------------------

function [] = CS4640_getImageInfoGrayscale(image)
%
% CS4640_getImageInfoGrayscale - This function prints out the min,
% max, mean, and standard deviation of the pixel values in a grayscale
% image.
%
% Input:-
%     <input variable image> (character vector): The name of the image to be used.
% Output:-
%     No outputs. The function prints the information to the console.
%  Example usage:
%		CS4640_getImageInfoGrayscale('cameraman.tif');
% Author:
%     Harry Kim
%		Fall 2022
%
theImage = imread(image);
minPixelVal = theImage(1, 1);
maxPixelVal = theImage(1, 1);
average1 = double(theImage(1, 1));

% Calculating the min, max, and mean of cameraman.tif.
k = 1;
for r = 1:size(theImage, 1)    % for number of rows of the image
    for c = 1:size(theImage, 2)    % for number of columns of the image

        currentPixel = theImage(r, c);

        if minPixelVal > currentPixel
            minPixelVal = currentPixel;
        end

        if maxPixelVal < currentPixel
            maxPixelVal = currentPixel;
        end

        average1 = double(average1) + double(currentPixel);

        k = k+1; % increment counter loop
    end
end

average1 = average1 / k;

% Calculating the standard deviation of cameraman.tif.
sum = double(0);
k = 1;
for r = 1:size(theImage, 1)    % for number of rows of the image
    for c = 1:size(theImage, 2)    % for number of columns of the image
        % Val(k) = pdf(cameraman(r, c),mu,sigma);

        currentPixel = theImage(r, c);

        distance = (double(currentPixel) - average1)^(2);

        sum = sum + double(distance);

        k = k+1; % increment counter loop
    end
end

standardDeviation = sqrt(sum / k-1);

% My answers:
fprintf("My answers:\n")
fprintf('Min: %d\n', minPixelVal);
fprintf('Max: %d\n', maxPixelVal);
fprintf('Mean: %f\n', average1);
fprintf('Standard Deviation: %f\n\n', standardDeviation);

% Matlab answers:
MLminPixelVal = min(theImage(:));
MLmaxPixelVal = max(theImage(:));
MLaverage = mean(theImage(:));
MLsd = std(double(theImage(:)));
fprintf("Matlab answers:\n")
fprintf('Min: %d\n', MLminPixelVal);
fprintf('Max: %d\n', MLmaxPixelVal);
fprintf('Mean: %f\n', MLaverage);
fprintf('Standard Deviation: %f\n\n', MLsd);
end

function [] = CS4640_addScalarToGrayscaleImage(image, value)
%
% CS4640_addScalarToGrayscaleImage - This function adds a scalar to the
% entirety of the image.
%
% Input:-
%     <input variable image> (character vector): The name of the image to be used.
% Output:-
%     No outputs. The function saves the new image to the output_images
%     folder and displays the original image and the new image in a
%     subplot.
%  Example usage:
%		CS4640_addScalarToGrayscaleImage('cameraman.tif', 100);
% Author:
%     Harry Kim
%		Fall 2022
%
theImage = imread(image);

adding_value = imadd(theImage, value);

saveName = append(CS4640_extractNameOfImageWithoutFileType(image), '_with_added_scalar_of_', num2str(value));
path = append('./output_images/', saveName, CS4640_getImageFileType(image));
imwrite(theImage,path);

subplot(1,2,1), imshow(theImage); title('Original Image');
subplot(1,2,2), imshow(adding_value); title('After adding ', value);

end

function [] = CS4640_addScalarToPixelOfGrayscaleImage(image, value, x, y)
%
% CS4640_addScalarToPixelOfGrayscaleImage - This function adds a scalar to a
% single pixel on an image.
%
% Input:-
%     <input variable image> (character vector): The name of the image to be used.
%     <input variable value> (integer): The value to add to the pixel.
%     <input variable x> (integer): The x coordinate of the pixel.
%     <input variable y> (integer): The y coordinate of the pixel.
% Output:-
%     No outputs. The function saves the new image to the output_images
%     folder and displays the original image and the new image in a
%     subplot.
%  Example usage:
%		CS4640_addScalarToPixelOfGrayscaleImage('cameraman.tif', -150, 1, 1);
% Author:
%     Harry Kim
%		Fall 2022
%
original = imread(image);

theImage = imread(image);

currentPixel = theImage(x, y);

theImage(x,y) = currentPixel + value;

saveName = append(CS4640_extractNameOfImageWithoutFileType(image), '_single_pixel_change_');
path = append('./output_images/', saveName, CS4640_getImageFileType(image));
imwrite(theImage,path);

subplot(1,2,1), imshow(original); title('Original Image');
subplot(1,2,2), imshow(theImage); title('Single pixel changed');

end

function [] = CS4640_getImageInfoColor(image)
%
% CS4640_getImageInfoColor - This function prints out the min,
% max, mean, and standard deviation of the pixel values of the RGB channels
% of a colored image.
%
% Input:-
%     <input variable image> (character vector): The name of the image to be used.
% Output:-
%     No outputs. The function prints the information to the console.
%  Example usage:
%		CS4640_getImageInfoColor('bowl_fruit.png');
% Author:
%     Harry Kim
%		Fall 2022
%
theImage = imread(image);

minPixelValR = theImage(1, 1, 1);
minPixelValG = theImage(1, 1, 2);
minPixelValB = theImage(1, 1, 3);

maxPixelValR = theImage(1, 1, 1);
maxPixelValG = theImage(1, 1, 2);
maxPixelValB = theImage(1, 1, 3);

average1R = double(theImage(1, 1, 1));
average1G = double(theImage(1, 1, 2));
average1B = double(theImage(1, 1, 3));


% Calculating the min, max, and mean of each RGB value of theImage.
k = 1;
for r = 1:size(theImage, 1)    % for number of rows of the image
    for c = 1:size(theImage, 2)    % for number of columns of the image

        % R
        currentPixelR = theImage(r, c, 1);
        if minPixelValR > currentPixelR
            minPixelValR = currentPixelR;
        end
        if maxPixelValR < currentPixelR
            maxPixelValR = currentPixelR;
        end
        average1R = double(average1R) + double(currentPixelR);

        %G
        currentPixelG = theImage(r, c, 2);
        if minPixelValG > currentPixelG
            minPixelValG = currentPixelG;
        end
        if maxPixelValG < currentPixelG
            maxPixelValG = currentPixelG;
        end
        average1G = double(average1G) + double(currentPixelG);

        %B
        currentPixelB = theImage(r, c, 3);
        if minPixelValB > currentPixelB
            minPixelValB = currentPixelB;
        end
        if maxPixelValB < currentPixelB
            maxPixelValB = currentPixelB;
        end
        average1B = double(average1B) + double(currentPixelB);

        k = k+1; % increment counter loop
    end
end

average1R = average1R / k;
average1G = average1G / k;
average1B = average1B / k;

% Calculating the standard deviation of cameraman.tif.
sumR = double(0);
sumG = double(0);
sumB = double(0);
k = 1;
for r = 1:size(theImage, 1)    % for number of rows of the image
    for c = 1:size(theImage, 2)    % for number of columns of the image
        % Val(k) = pdf(cameraman(r, c),mu,sigma);

        %R
        currentPixelR = theImage(r, c, 1);
        distance = (double(currentPixelR) - average1R)^(2);
        sumR = sumR + double(distance);

        %R
        currentPixelG = theImage(r, c, 2);
        distance = (double(currentPixelG) - average1G)^(2);
        sumG = sumG + double(distance);

        %R
        currentPixelB = theImage(r, c, 3);
        distance = (double(currentPixelB) - average1B)^(2);
        sumB = sumB + double(distance);

        k = k+1; % increment counter loop
    end
end

standardDeviationR = sqrt(sumR / k-1);
standardDeviationG = sqrt(sumG / k-1);
standardDeviationB = sqrt(sumB / k-1);

% My answers:
fprintf("My answers:\n")
fprintf('Min: R:%d', minPixelValR);
fprintf(' G:%d', minPixelValG);
fprintf(' B:%d \n', minPixelValB);

fprintf('Max: R:%d', maxPixelValR);
fprintf(' G:%d', maxPixelValG);
fprintf(' B:%d \n', maxPixelValB);

fprintf('Mean: R: %f', average1R);
fprintf(' G: %f', average1G);
fprintf(' B: %f \n', average1B);

fprintf('Standard Deviation: R: %f', standardDeviationR);
fprintf(' G: %f', standardDeviationG);
fprintf(' B: %f \n\n', standardDeviationB);

% Matlab Answers:
MLminPixelValR = min(min(theImage(:, :, 1)));
MLminPixelValG = min(min(theImage(:, :, 2)));
MLminPixelValB = min(min(theImage(:, :, 3)));

MLmaxPixelValR = max(max(theImage(:, :, 1)));
MLmaxPixelValG = max(max(theImage(:, :, 2)));
MLmaxPixelValB = max(max(theImage(:, :, 3)));

MLaverageR = mean2(theImage(:, :, 1));
MLaverageG = mean2(theImage(:, :, 2));
MLaverageB = mean2(theImage(:, :, 3));

MLsdR = std2(theImage(:, :, 1));
MLsdG = std2(theImage(:, :, 2));
MLsdB = std2(theImage(:, :, 3));

fprintf("Matlab answers:\n")
fprintf('Min: R:%d', MLminPixelValR);
fprintf(' G:%d', MLminPixelValG);
fprintf(' B:%d \n', MLminPixelValB);

fprintf('Max: R:%d', MLmaxPixelValR);
fprintf(' G:%d', MLmaxPixelValG);
fprintf(' B:%d \n', MLmaxPixelValB);

fprintf('Mean: R:%f', MLaverageR);
fprintf(' G:%f', MLaverageG);
fprintf(' B:%f \n', MLaverageB);

fprintf('Standard Deviation: R:%f', MLsdR);
fprintf(' G:%f', MLsdG);
fprintf(' B:%f \n', MLsdB);

end

function [] = CS4640_addScalarToColorImage(image, R, G, B)
%
% CS4640_addScalarToColorImage - This function adds a scalar to each of the
% channels of a colored image.
%
% Input:-
%     <input variable image> (character vector): The name of the image to be used.
%     <input variable R> (integer): The value to add to the red channel.
%     <input variable G> (integer): The value to add to the green channel.
%     <input variable B> (integer): The value to add to the blue channel.
% Output:-
%     No outputs. The function saves the new image to the output_images
%     folder and displays the original image and the new image in a
%     subplot.
%  Example usage:
%		CS4640_addScalarToColorImage('bowl_fruit.png', 100, 100, 100);
% Author:
%     Harry Kim
%		Fall 2022
%
theImage = imread(image);

redChannel = theImage(:, :, 1);
blueChannel = theImage(:, :, 2);
greenChannel = theImage(:, :, 3);

%adding_value = imadd(theImage, value);
adding_valueR = imadd(redChannel, R);
adding_valueG = imadd(blueChannel, G);
adding_valueB = imadd(greenChannel, B);

newImage = cat(3, adding_valueR, adding_valueG, adding_valueB);

saveName = append(CS4640_extractNameOfImageWithoutFileType(image), '_with_added_scalar_of_R', num2str(R), '_G', num2str(G), '_B', num2str(B));
path = append('./output_images/', saveName, CS4640_getImageFileType(image));
imwrite(newImage,path);

subplot(1,2,1), imshow(theImage); title('Original Image');
subplot(1,2,2), imshow(newImage); title('After adding new values');

end

function [] = CS4640_falseColorMapGrayscaleImage(image)
%
% CS4640_falseColorMapGrayscaleImage - This function puts a false color map
% onto a grayscale image.
%
% Input:-
%     <input variable image> (character vector): The name of the image to be used.
% Output:-
%     No outputs. The function saves the new image to the output_images
%     folder and displays the original image and the new images in a
%     subplot.
%  Example usage:
%		CS4640_imageResizeAndReverse('onion.png');
% Author:
%     Harry Kim
%		Fall 2022
%
theImage = imread(image);

minVal = min(theImage(:));
maxVal = max(theImage(:));

clim([minVal, maxVal * 2]);

falseColorImage = ind2rgb(theImage, parula);

subplot(2,2,1), imshow(theImage); title('Original Image');
subplot(2,2,2), imshow(theImage, [minVal, maxVal], Colormap=parula); title('After false color map [minVal, maxVal]');
subplot(2,2,3), imshow(theImage, [minVal/2, 2*maxVal], Colormap=parula); title('After false color map [minVal/2, 2*maxVal]');
subplot(2,2,4), imshow(theImage, [minVal*2, maxVal/2], Colormap=parula); title('After false color map [minVal*2, maxVal/2]');

end

function [] = CS4640_imageResizeAndReverse(image)
%
% CS4640_imageResizeAndReverse - This function resizes a given image to a
% smaller size and then reverses the resizing by resizing the image to its
% original size. The function does this by 1/2, 1/4, and 1/8 of the
% original size.
%
% Input:-
%     <input variable image> (character vector): The name of the image to be used.
% Output:-
%     No outputs. The function saves the new image to the output_images
%     folder and displays the original image and the new images in a
%     subplot.
%  Example usage:
%		CS4640_imageResizeAndReverse('onion.png');
% Author:
%     Harry Kim
%		Fall 2022
%
theImage = imread(image);

resizeImageHalf1 = imresize(theImage, 0.5, "nearest", "Antialiasing",true);
resizeImageHalf2 = imresize(resizeImageHalf1, 2, "nearest", "Antialiasing",true);

saveName = append(CS4640_extractNameOfImageWithoutFileType(image), '_half_resize');
path = append('./output_images/', saveName, CS4640_getImageFileType(image));
imwrite(resizeImageHalf2,path);

resizeImageQuarter1 = imresize(theImage, 0.25, "nearest", "Antialiasing",true);
resizeImageQuarter2 = imresize(resizeImageQuarter1, 4, "nearest", "Antialiasing",true);

saveName = append(CS4640_extractNameOfImageWithoutFileType(image), '_quarter_resize');
path = append('./output_images/', saveName, CS4640_getImageFileType(image));
imwrite(resizeImageQuarter2,path);

resizeImageEighth1 = imresize(theImage, 0.125, "nearest", "Antialiasing",true);
resizeImageEighth2 = imresize(resizeImageEighth1, 8, "nearest", "Antialiasing",true);

saveName = append(CS4640_extractNameOfImageWithoutFileType(image), '_eighth_resize');
path = append('./output_images/', saveName, CS4640_getImageFileType(image));
imwrite(resizeImageEighth2,path);

subplot(2,2,1), imshow(theImage); title('Original Image');
subplot(2,2,2), imshow(resizeImageHalf2); title('1/2');
subplot(2,2,3), imshow(resizeImageQuarter2); title('1/4');
subplot(2,2,4), imshow(resizeImageEighth2); title('1/8');

end

function [] = CS4640_extractBitPlanesOfGrayscaleImage(image)
%
% CS4640_extractBitPlanesOfGrayscaleImage - This function performs bit
% plane slicing of all the bits of a grayscale image and displays them.
%
% Input:-
%     <input variable image> (character vector): The name of the image to be used.
% Output:-
%     No outputs. The function saves the new image to the output_images
%     folder and displays the original image and the new images in a
%     subplot.
%  Example usage:
%		CS4640_extractBitPlanesOfGrayscaleImage('cameraman.tif');
% Author:
%     Harry Kim
%		Fall 2022
%
theImage = imread(image);

firstBitImage = theImage;
secondBitImage = theImage;
thirdBitImage = theImage;
fourthBitImage = theImage;
fifthBitImage = theImage;
sixthBitImage = theImage;
seventhBitImage = theImage;
eighthBitImage = theImage;

for r = 1:size(theImage, 1)    % for number of rows of the image
    for c = 1:size(theImage, 2)    % for number of columns of the image
        currentPixel = theImage(r, c);
        binStr = dec2bin(currentPixel, 8);

        % First Bit
        if strcmp('0', binStr(1:1))
           firstBitImage(r, c) = 0;
        else
           firstBitImage(r, c) = 255;
        end

        % Second Bit
        if strcmp('0', binStr(2:2))
           secondBitImage(r, c) = 0;
        else
           secondBitImage(r, c) = 255;
        end

        % Third Bit
        if strcmp('0', binStr(3:3))
           thirdBitImage(r, c) = 0;
        else
           thirdBitImage(r, c) = 255;
        end

        % Fourth Bit
        if strcmp('0', binStr(4:4))
           fourthBitImage(r, c) = 0;
        else
           fourthBitImage(r, c) = 255;
        end

        % Fifth Bit
        if strcmp('0', binStr(5:5))
           fifthBitImage(r, c) = 0;
        else
           fifthBitImage(r, c) = 255;
        end

        % Sixth Bit
        if strcmp('0', binStr(6:6))
           sixthBitImage(r, c) = 0;
        else
           sixthBitImage(r, c) = 255;
        end

        % Seventh Bit
        if strcmp('0', binStr(7:7))
           seventhBitImage(r, c) = 0;
        else
           seventhBitImage(r, c) = 255;
        end

        % Eighth Bit
        if strcmp('0', binStr(8:8))
           eighthBitImage(r, c) = 0;
        else
           eighthBitImage(r, c) = 255;
        end
    end
end

saveName = append(CS4640_extractNameOfImageWithoutFileType(image), '_bit_plane_8');
path = append('./output_images/', saveName, CS4640_getImageFileType(image));
imwrite(firstBitImage,path);

saveName = append(CS4640_extractNameOfImageWithoutFileType(image), '_bit_plane_7');
path = append('./output_images/', saveName, CS4640_getImageFileType(image));
imwrite(secondBitImage,path);

saveName = append(CS4640_extractNameOfImageWithoutFileType(image), '_bit_plane_6');
path = append('./output_images/', saveName, CS4640_getImageFileType(image));
imwrite(thirdBitImage,path);

saveName = append(CS4640_extractNameOfImageWithoutFileType(image), '_bit_plane_5');
path = append('./output_images/', saveName, CS4640_getImageFileType(image));
imwrite(fourthBitImage,path);

saveName = append(CS4640_extractNameOfImageWithoutFileType(image), '_bit_plane_4');
path = append('./output_images/', saveName, CS4640_getImageFileType(image));
imwrite(fifthBitImage,path);

saveName = append(CS4640_extractNameOfImageWithoutFileType(image), '_bit_plane_3');
path = append('./output_images/', saveName, CS4640_getImageFileType(image));
imwrite(sixthBitImage,path);

saveName = append(CS4640_extractNameOfImageWithoutFileType(image), '_bit_plane_2');
path = append('./output_images/', saveName, CS4640_getImageFileType(image));
imwrite(seventhBitImage,path);

saveName = append(CS4640_extractNameOfImageWithoutFileType(image), '_bit_plane_1');
path = append('./output_images/', saveName, CS4640_getImageFileType(image));
imwrite(eighthBitImage,path);

subplot(2,5,1), imshow(theImage); title('Original Image');
subplot(2,5,2), imshow(firstBitImage); title('Bit Plane 8');
subplot(2,5,3), imshow(secondBitImage); title('Bit Plane 7');
subplot(2,5,4), imshow(thirdBitImage); title('Bit Plane 6');
subplot(2,5,5), imshow(fourthBitImage); title('Bit Plane 5');
subplot(2,5,6), imshow(fifthBitImage); title('Bit Plane 4');
subplot(2,5,7), imshow(sixthBitImage); title('Bit Plane 3');
subplot(2,5,8), imshow(seventhBitImage); title('Bit Plane 2');
subplot(2,5,9), imshow(eighthBitImage); title('Bit Plane 1');

end

function [] = CS4640_saveImageToGivenFormat(image, format)
%
% CS4640_extractBitPlanesOfGrayscaleImage - This function changes the
% format of a given image and saves it.
%
% Input:-
%     <input variable image> (character vector): The name of the image to be used.
%     <input variable format> (character vector): The format of the image to be saved in.
% Output:-
%     No outputs. The function saves the new image to the output_images
%     folder and displays the original image and the new images in a
%     subplot.
%  Example usage:
%		CS4640_saveImageToGivenFormat('bowl_fruit.png', 'jpeg');
% Author:
%     Harry Kim
%		Fall 2022
%
theImage = imread(image);

saveName = append(CS4640_extractNameOfImageWithoutFileType(image), '_in_', format, '_format.', format);
path = append('./output_images/', saveName, CS4640_getImageFileType(image));
imwrite(theImage,path);

newImage = imread(path);

subplot(1,3,1), imshow(theImage); title('Original image');
subplot(1,3,2), imshow(newImage); title('New format image');

absdiffImage = imabsdiff(theImage, newImage);

subplot(1,3,3), imshow(absdiffImage); title('abs diff image');

fprintf("\nInfo of the absolute difference of the same image with different formats: \n\n")

CS4640_saveImageToGivenFormatHelper(absdiffImage);

end

function [] = CS4640_saveImageToGivenFormatHelper(theImage)
%
% CS4640_saveImageToGivenFormatHelper - This function calculates min, max,
% mean, and std of the absolute difference image.
%
% Input:-
%     <input variable theImage> (array): The image data contained within the array.
% Output:-
%     No outputs. The function saves the new image to the output_images
%     folder and displays the original image and the new image in a
%     subplot.
%  Example usage:
%		CS4640_saveImageToGivenFormatHelper(absdiffImage);
% Author:
%     Harry Kim
%		Fall 2022
%
minPixelValR = theImage(1, 1, 1);
minPixelValG = theImage(1, 1, 2);
minPixelValB = theImage(1, 1, 3);

maxPixelValR = theImage(1, 1, 1);
maxPixelValG = theImage(1, 1, 2);
maxPixelValB = theImage(1, 1, 3);

average1R = double(theImage(1, 1, 1));
average1G = double(theImage(1, 1, 2));
average1B = double(theImage(1, 1, 3));


% Calculating the min, max, and mean of each RGB value of theImage.
k = 1;
for r = 1:size(theImage, 1)    % for number of rows of the image
    for c = 1:size(theImage, 2)    % for number of columns of the image

        % R
        currentPixelR = theImage(r, c, 1);
        if minPixelValR > currentPixelR
            minPixelValR = currentPixelR;
        end
        if maxPixelValR < currentPixelR
            maxPixelValR = currentPixelR;
        end
        average1R = double(average1R) + double(currentPixelR);

        %G
        currentPixelG = theImage(r, c, 2);
        if minPixelValG > currentPixelG
            minPixelValG = currentPixelG;
        end
        if maxPixelValG < currentPixelG
            maxPixelValG = currentPixelG;
        end
        average1G = double(average1G) + double(currentPixelG);

        %B
        currentPixelB = theImage(r, c, 3);
        if minPixelValB > currentPixelB
            minPixelValB = currentPixelB;
        end
        if maxPixelValB < currentPixelB
            maxPixelValB = currentPixelB;
        end
        average1B = double(average1B) + double(currentPixelB);

        k = k+1; % increment counter loop
    end
end

average1R = average1R / k;
average1G = average1G / k;
average1B = average1B / k;

sumR = double(0);
sumG = double(0);
sumB = double(0);
k = 1;
for r = 1:size(theImage, 1)    % for number of rows of the image
    for c = 1:size(theImage, 2)    % for number of columns of the image
        % Val(k) = pdf(cameraman(r, c),mu,sigma);

        %R
        currentPixelR = theImage(r, c, 1);
        distance = (double(currentPixelR) - average1R)^(2);
        sumR = sumR + double(distance);

        %R
        currentPixelG = theImage(r, c, 2);
        distance = (double(currentPixelG) - average1G)^(2);
        sumG = sumG + double(distance);

        %R
        currentPixelB = theImage(r, c, 3);
        distance = (double(currentPixelB) - average1B)^(2);
        sumB = sumB + double(distance);

        k = k+1; % increment counter loop
    end
end

standardDeviationR = sqrt(sumR / k-1);
standardDeviationG = sqrt(sumG / k-1);
standardDeviationB = sqrt(sumB / k-1);

% My answers:
fprintf("My answers:\n")
fprintf('Min: R:%d', minPixelValR);
fprintf(' G:%d', minPixelValG);
fprintf(' B:%d \n', minPixelValB);

fprintf('Max: R:%d', maxPixelValR);
fprintf(' G:%d', maxPixelValG);
fprintf(' B:%d \n', maxPixelValB);

fprintf('Mean: R: %f', average1R);
fprintf(' G: %f', average1G);
fprintf(' B: %f \n', average1B);

fprintf('Standard Deviation: R: %f', standardDeviationR);
fprintf(' G: %f', standardDeviationG);
fprintf(' B: %f \n\n', standardDeviationB);

% Matlab Answers:
MLminPixelValR = min(min(theImage(:, :, 1)));
MLminPixelValG = min(min(theImage(:, :, 2)));
MLminPixelValB = min(min(theImage(:, :, 3)));

MLmaxPixelValR = max(max(theImage(:, :, 1)));
MLmaxPixelValG = max(max(theImage(:, :, 2)));
MLmaxPixelValB = max(max(theImage(:, :, 3)));

MLaverageR = mean2(theImage(:, :, 1));
MLaverageG = mean2(theImage(:, :, 2));
MLaverageB = mean2(theImage(:, :, 3));

MLsdR = std2(theImage(:, :, 1));
MLsdG = std2(theImage(:, :, 2));
MLsdB = std2(theImage(:, :, 3));

fprintf("Matlab answers:\n")
fprintf('Min: R:%d', MLminPixelValR);
fprintf(' G:%d', MLminPixelValG);
fprintf(' B:%d \n', MLminPixelValB);

fprintf('Max: R:%d', MLmaxPixelValR);
fprintf(' G:%d', MLmaxPixelValG);
fprintf(' B:%d \n', MLmaxPixelValB);

fprintf('Mean: R:%f', MLaverageR);
fprintf(' G:%f', MLaverageG);
fprintf(' B:%f \n', MLaverageB);

fprintf('Standard Deviation: R:%f', MLsdR);
fprintf(' G:%f', MLsdG);
fprintf(' B:%f \n\n', MLsdB);

end

function [] = CS4640_convertColoredImageToHSVColorSpace(image)
%
% CS4640_convertColoredImageToHSVColorSpace - This function converts a
% colored image to the HSV color space, displays the red, green, and
% blue channels of the original image, and the H, S, and V channels of the
% converted color image.
%
% Input:-
%     <input variable imageInput> (character vector): The name of the image to be used.
% Output:-
%     No outputs. The function saves the new image to the output_images
%     folder and displays the original image and the new images in a
%     subplot.
%  Example usage:
%		CS4640_convertColoredImageToHSVColorSpace('bowl_fruit.png');
% Author:
%     Harry Kim
%		Fall 2022
%
theImage = imread(image);

HSVImage = rgb2hsv(theImage);

[r,g,b] = imsplit(theImage);

[h,s,v] = imsplit(HSVImage);

saveName = append(CS4640_extractNameOfImageWithoutFileType(image), '_r_channel');
path = append('./output_images/', saveName, CS4640_getImageFileType(image));
imwrite(r,path);

saveName = append(CS4640_extractNameOfImageWithoutFileType(image), '_g_channel');
path = append('./output_images/', saveName, CS4640_getImageFileType(image));
imwrite(g,path);

saveName = append(CS4640_extractNameOfImageWithoutFileType(image), '_b_channel');
path = append('./output_images/', saveName, CS4640_getImageFileType(image));
imwrite(b,path);

subplot(2,4,1), imshow(theImage); title('Original RGB');
subplot(2,4,2), imshow(r); title('RGB R Channel');
subplot(2,4,3), imshow(g); title('RGB G Channel');
subplot(2,4,4), imshow(b); title('RGB B Channel');

saveName = append(CS4640_extractNameOfImageWithoutFileType(image), '_HSV_image');
path = append('./output_images/', saveName, CS4640_getImageFileType(image));
imwrite(HSVImage,path);

saveName = append(CS4640_extractNameOfImageWithoutFileType(image), '_h_channel');
path = append('./output_images/', saveName, CS4640_getImageFileType(image));
imwrite(h,path);

saveName = append(CS4640_extractNameOfImageWithoutFileType(image), '_s_channel');
path = append('./output_images/', saveName, CS4640_getImageFileType(image));
imwrite(s,path);

saveName = append(CS4640_extractNameOfImageWithoutFileType(image), '_v_channel');
path = append('./output_images/', saveName, CS4640_getImageFileType(image));
imwrite(v,path);

subplot(2,4,5), imshow(HSVImage); title('Original HSV');
subplot(2,4,6), imshow(h); title('HSV H Channel');
subplot(2,4,7), imshow(s); title('HSV S Channel');
subplot(2,4,8), imshow(v); title('HSV V Channel');

end

function [] = CS4640_computeWeightingFactorsRGB2Grayscale(imageInput)
%
% CS4640_computeWeightingFactorsRGB2Grayscale - This function computes the
% weighting factors used by matlab's rgb2gray function.
%
% Input:-
%     <input variable imageInput> (character vector): The name of the image to be used.
% Output:-
%     No outputs. The function saves the new image to the output_images
%     folder and displays the original image and the new images in a
%     subplot.
%  Example usage:
%		CS4640_computeWeightingFactorsRGB2Grayscale('bowl_fruit.png');
% Author:
%     Harry Kim
%		Fall 2022
%
theImage = imread(imageInput);

[r,g,b] = imsplit(theImage);

grayscaleImage = rgb2gray(theImage);

imageSize = size(theImage);

sizeX = imageSize(1);

sizeY = imageSize(2);

pixelOneX = randi(sizeX);
pixelOneY = randi(sizeY);

while (grayscaleImage(pixelOneX, pixelOneY) == 0)
    pixelOneX = randi(sizeX);
    pixelOneY = randi(sizeY);
end

pixelTwoX = randi(sizeX);
pixelTwoY = randi(sizeY);

while (grayscaleImage(pixelOneX, pixelOneY) == 0 && (pixelTwoX ~= pixelOneX && pixelTwoY ~= pixelOneY))
    pixelTwoX = randi(sizeX);
    pixelTwoY = randi(sizeY);
end

pixelThreeX = randi(sizeX);
pixelThreeY = randi(sizeY);

while (grayscaleImage(pixelOneX, pixelOneY) == 0 && (pixelThreeX ~= pixelOneX && pixelThreeY ~= pixelOneY) && (pixelThreeX ~= pixelTwoX && pixelThreeY ~= pixelTwoY))
    pixelThreeX = randi(sizeX);
    pixelThreeY = randi(sizeY);
end

pixelOneR = r(pixelOneX,pixelOneY);
pixelOneG = g(pixelOneX,pixelOneY);
pixelOneB = b(pixelOneX,pixelOneY);

pixelTwoR = r(pixelTwoX,pixelTwoY);
pixelTwoG = g(pixelTwoX,pixelTwoY);
pixelTwoB = b(pixelTwoX,pixelTwoY);

pixelThreeR = r(pixelThreeX,pixelThreeY);
pixelThreeG = g(pixelThreeX,pixelThreeY);
pixelThreeB = b(pixelThreeX,pixelThreeY);


X = [double(pixelOneR), double(pixelOneG), double(pixelOneB);
     double(pixelTwoR), double(pixelTwoG), double(pixelTwoB);
     double(pixelThreeR), double(pixelThreeG), double(pixelThreeB)];

Y = [double(grayscaleImage(pixelOneX, pixelOneY)); double(grayscaleImage(pixelTwoX, pixelTwoY)); double(grayscaleImage(pixelThreeX, pixelThreeY))] ;
coefficients = X \ Y;

%doubleChecka = double(pixelOneR) * coefficients(1) + double(pixelOneG) * coefficients(2) + double(pixelOneB) * coefficients(3)
%doubleCheckb = double(pixelTwoR) * coefficients(1) + double(pixelTwoG) * coefficients(2) + double(pixelTwoB) * coefficients(3)
%doubleCheckc = double(pixelThreeR) * coefficients(1) + double(pixelThreeG) * coefficients(2) + double(pixelThreeB) * coefficients(3)

fprintf("Weights: %f", coefficients(1));
fprintf(" %f", coefficients(2));
fprintf(" %f", coefficients(3));

end

function [bins] = CS4640_divideRangeOfIntensitiesIntoBins(min, max, n)
%
% CS4640_divideRangeOfIntensitiesIntoBins - This function  divides a given
% range of intensities into a given number of evenly spaced bins.
%
% Input:-
%     <input variable min> (integer): The lowest number in the range.
%     <input variable max> (integer): The highest number in the range.
%     <input variable n> (integer): The number of bins to be used.
% Output:-
%     <output variable bins> (2D vector): the bins generated.
%  Example usage:
%		bins = CS4640_divideRangeOfIntensitiesIntoBins(0, 40, 5);
% Author:
%     Harry Kim
%		Fall 2022
%
bins = linspace(min, max, n);

end

function [grayscaleImage] = CS4640_quantizePixelsOfImage(image, numBins)
%
% CS4640_quantizePixelsOfImage - This function converts the given colored
% image to grayscale, quantizes each pixel of a given image the index of
% the bin that its intensity lies in.
%
% Input:-
%     <input variable imageInput> (character vector): The name of the image to be used.
%     <input variable numBins> (integer): The number of bins to be used.
% Output:-
%     <output variable grayscaleImage> (array): The new image with quantized pixels.
%  Example usage:
%		newCoolImage = CS4640_quantizePixelsOfImage(image, numBins);
% Author:
%     Harry Kim
%		Fall 2022
%
theImage = imread(image);

imageInfo = size(theImage);

grayscaleImage = rgb2gray(theImage);

bins = CS4640_divideRangeOfIntensitiesIntoBins(0, 255, numBins);

for r = 1:size(theImage, 1)    % for number of rows of the image
    for c = 1:size(theImage, 2)    % for number of columns of the image
        currentPixel = grayscaleImage(r, c);

        binsize = size(bins);

        binsizeAlpha = binsize(2);

        math = double(currentPixel)/255.0;

        math2 = double(currentPixel)/255.0 * binsize(2);

        math3 = round(double(currentPixel)/255.0 * binsize(2));
        
        correctBin = round(double(currentPixel)/255.0 * binsize(2));

        if correctBin < 1
            correctBin = 1;
        end

        a = bins(correctBin);

        grayscaleImage(r,c) = bins(correctBin);
    end
end

saveName = append('indy', '_quantized');
path = append('./output_images/', saveName, CS4640_getImageFileType(image));
imwrite(grayscaleImage,path);

subplot(1,2,1), imshow(theImage); title('Original Image');
subplot(1,2,2), imshow(grayscaleImage); title('Quantized Image');

end

function [] = CS4640_distinguisBackgroundAndForground(image, numBins)
%
% CS4640_distinguisBackgroundAndForground - This function distinguishes the
% background and the forground of an image and an object of interest within
% the image.
%
% Input:-
%     <input variable imageInput> (character vector): The name of the image to be used.
%     <input variable numBins> (integer): The number of bins to be used.
% Output:-
%     No outputs. The function saves the new image to the output_images
%     folder and displays the original image and the new images in a
%     subplot.
%  Example usage:
%		CS4640_distinguisBackgroundAndForground('./project-indy/image_004.jpg',5);
% Author:
%     Harry Kim
%		Fall 2022
%
theImage = imread(image);

binaryImage = imbinarize(CS4640_quantizePixelsOfImage(image, numBins), 0.5);

saveName = append('indy', '_distinguished');
path = append('./output_images/', saveName, CS4640_getImageFileType(image));
imwrite(binaryImage,path);

subplot(1,2,1), imshow(theImage); title('Original Image');
subplot(1,2,2), imshow(binaryImage); title('Distinguished Image');

end

function [name] = CS4640_extractNameOfImageWithoutFileType(image)
%
% CS4640_extractNameOfImageWithoutFileType - This helper function extracts
% the name of a given image and returns it without the file type.
%
% Input:-
%     <input variable imageInput> (character vector): The name of the image to be used.
% Output:-
%     <output variable name> (character vector): The name of the image without the file type.
%  Example usage:
%		CS4640_extractNameOfImageWithoutFileType('onion.png');
% Author:
%     Harry Kim
%		Fall 2022
%
if strcmp(image((strlength(image) - 3) : (strlength(image) - 3)), '.')
    name = image(1: (strlength(image) - 4));
else
    name = image(1: (strlength(image) - 5));
end

end

function [fileType] = CS4640_getImageFileType(image)
%
% CS4640_getImageFileType - This helper function extracts the file type of
% a given image name.
%
% Input:-
%     <input variable imageInput> (character vector): The name of the image to be used.
% Output:-
%     <output variable name> (character vector): The name of the image file type.
%  Example usage:
%		CS4640_extractNameOfImageWithoutFileType('onion.png');
% Author:
%     Harry Kim
%		Fall 2022
%
if strcmp(image((strlength(image) - 3) : (strlength(image) - 3)), '.')
    fileType = image((strlength(image) - 3): strlength(image));
else
    fileType = image((strlength(image) - 4): strlength(image));
end

end