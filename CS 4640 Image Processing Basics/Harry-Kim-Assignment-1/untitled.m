clc

CS4640_falseColorMapGrayscaleImage('spine.tif');

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

newImage = imagesc(clim);

newNewImage = ind2rgb(newImage, jet(255));

% saveName = append(CS4640_extractNameOfImageWithoutFileType(image), '_v_channel');
% path = append('./output_images/', saveName, CS4640_getImageFileType(image));
% imwrite(v,path);

subplot(2,2,1), imshow(newNewImage); title('Original Image');
subplot(2,2,2), imageMod1 = imshow(theImage, [minVal, maxVal], Colormap=parula); title('After false color map [minVal, maxVal]');
subplot(2,2,3), imageMod2 = imshow(theImage, [minVal/2, 2*maxVal], Colormap=parula); title('After false color map [minVal/2, 2*maxVal]');
subplot(2,2,4), imageMod3 = imshow(theImage, [minVal*2, maxVal/2], Colormap=parula); title('After false color map [minVal*2, maxVal/2]');

saveName = append(CS4640_extractNameOfImageWithoutFileType(image), '_false_color_map1');
path = append('./output_images/', saveName, CS4640_getImageFileType(image));

i1 = uint8(imageMod1);

imwrite(i1,path);

saveName = append(CS4640_extractNameOfImageWithoutFileType(image), '_false_color_map2');
path = append('./output_images/', saveName, CS4640_getImageFileType(image));
imwrite(imageMod2,path);

saveName = append(CS4640_extractNameOfImageWithoutFileType(image), '_false_color_map3');
path = append('./output_images/', saveName, CS4640_getImageFileType(image));
imwrite(imageMod3,path);

saveName = append(CS4640_extractNameOfImageWithoutFileType(image), '_false_color_map4');
path = append('./output_images/', saveName, CS4640_getImageFileType(image));
imwrite(imageMod4,path);

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