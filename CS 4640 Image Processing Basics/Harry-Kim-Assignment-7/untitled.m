he = imread("Fishes\Fish_Dataset\Black Sea Sprat\Black Sea Sprat\00001.png");

mask = rgb2gray(he) > 200; % whatever value works.
fixedImage = regionfill(rgb2gray(he), mask);

imshow(fixedImage);

numColors = 3;
L = imsegkmeans(he,numColors);
B = labeloverlay(he,L);
imshow(B)
title("Labeled Image RGB")