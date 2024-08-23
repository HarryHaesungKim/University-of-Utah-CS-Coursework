% what

% Question #1: Noise removal --------------------------

% (a)

eight = imread('./Assignment-4-Images/eight.tif');

eight_spImage1 = CS4640_addSaltAndPepperNoise(eight, 0.05);
eight_spImage2 = CS4640_addSaltAndPepperNoise(eight, 0.1);
eight_spImage3 = CS4640_addSaltAndPepperNoise(eight, 0.2);

subplot(2,4,1), imshow(eight); title('eight.tif');
subplot(2,4,2), imshow(eight_spImage1); title('5% S&P Noise');
subplot(2,4,3), imshow(eight_spImage2); title('10% S&P Noise');
subplot(2,4,4), imshow(eight_spImage3); title('20% S&P Noise');

eight_gausImage1 = CS4640_addGaussianNoise(eight, 0.1);
eight_gausImage2 = CS4640_addGaussianNoise(eight, 1.0);
eight_gausImage3 = CS4640_addGaussianNoise(eight, 10);
eight_gausImage4 = CS4640_addGaussianNoise(eight, 100);

subplot(2,4,5), imshow(eight_gausImage1); title('Gauss Noise SD: 0.1');
subplot(2,4,6), imshow(eight_gausImage2); title('Gauss Noise SD: 1.0');
subplot(2,4,7), imshow(eight_gausImage3); title('Gauss Noise SD: 10');
subplot(2,4,8), imshow(eight_gausImage4); title('Gauss Noise SD: 100');

figure();

peppers = imread('./Assignment-4-Images/peppers.png');

peppers_spImage1 = CS4640_addSaltAndPepperNoise(peppers, 0.05);
peppers_spImage2 = CS4640_addSaltAndPepperNoise(peppers, 0.1);
peppers_spImage3 = CS4640_addSaltAndPepperNoise(peppers, 0.2);

subplot(2,4,1), imshow(peppers); title('peppers.tif');
subplot(2,4,2), imshow(peppers_spImage1); title('5% S&P Noise');
subplot(2,4,3), imshow(peppers_spImage2); title('10% S&P Noise');
subplot(2,4,4), imshow(peppers_spImage3); title('20% S&P Noise');

peppers_gausImage1 = CS4640_addGaussianNoise(peppers, 0.1);
peppers_gausImage2 = CS4640_addGaussianNoise(peppers, 1.0);
peppers_gausImage3 = CS4640_addGaussianNoise(peppers, 10);
peppers_gausImage4 = CS4640_addGaussianNoise(peppers, 100);

subplot(2,4,5), imshow(peppers_gausImage1); title('Gauss Noise SD: 0.1');
subplot(2,4,6), imshow(peppers_gausImage2); title('Gauss Noise SD: 1.0');
subplot(2,4,7), imshow(peppers_gausImage3); title('Gauss Noise SD: 10');
subplot(2,4,8), imshow(peppers_gausImage4); title('Gauss Noise SD: 100');

figure();

% Saving images to output_images

saveName = 'eight_spImage1.jpg';
path = append('./output_images/question1a_output_images/', saveName);
imwrite(eight_spImage1,path);
saveName = 'eight_spImage2.jpg';
path = append('./output_images/question1a_output_images/', saveName);
imwrite(eight_spImage2,path);
saveName = 'eight_spImage3.jpg';
path = append('./output_images/question1a_output_images/', saveName);
imwrite(eight_spImage3,path);

saveName = 'eight_gausImage1.jpg';
path = append('./output_images/question1a_output_images/', saveName);
imwrite(eight_gausImage1,path);
saveName = 'eight_gausImage2.jpg';
path = append('./output_images/question1a_output_images/', saveName);
imwrite(eight_gausImage2,path);
saveName = 'eight_gausImage3.jpg';
path = append('./output_images/question1a_output_images/', saveName);
imwrite(eight_gausImage3,path);
saveName = 'eight_gausImage4.jpg';
path = append('./output_images/question1a_output_images/', saveName);
imwrite(eight_gausImage4,path);

saveName = 'peppers_spImage1.jpg';
path = append('./output_images/question1a_output_images/', saveName);
imwrite(peppers_spImage1,path);
saveName = 'peppers_spImage2.jpg';
path = append('./output_images/question1a_output_images/', saveName);
imwrite(peppers_spImage2,path);
saveName = 'peppers_spImage3.jpg';
path = append('./output_images/question1a_output_images/', saveName);
imwrite(peppers_spImage3,path);

saveName = 'peppers_gausImage1.jpg';
path = append('./output_images/question1a_output_images/', saveName);
imwrite(peppers_gausImage1,path);
saveName = 'peppers_gausImage2.jpg';
path = append('./output_images/question1a_output_images/', saveName);
imwrite(peppers_gausImage2,path);
saveName = 'peppers_gausImage3.jpg';
path = append('./output_images/question1a_output_images/', saveName);
imwrite(peppers_gausImage3,path);
saveName = 'peppers_gausImage4.jpg';
path = append('./output_images/question1a_output_images/', saveName);
imwrite(peppers_gausImage4,path);

% (b)

inputImagesFolder = './output_images/question1a_output_images/';
filePattern = fullfile(inputImagesFolder, '*.jpg');
files = dir(filePattern);
for k = 1 : length(files)
  filename = files(k).name;
  [filepath,name,ext] = fileparts(filename);

  currentImage = imread(append(inputImagesFolder, filename));

  % mean filtering
  meanFiltered5 = CS4640_meanFiltering(currentImage, 5);
  meanFiltered11 = CS4640_meanFiltering(currentImage, 11);
  meanFiltered21 = CS4640_meanFiltering(currentImage, 21);

  saveName = append(name, '_meanFiltered5.jpg');
  path = append('./output_images/question1b_output_images/', saveName);
  imwrite(meanFiltered5,path);
  saveName = append(name, '_meanFiltered11.jpg');
  path = append('./output_images/question1b_output_images/', saveName);
  imwrite(meanFiltered11,path);
  saveName = append(name, '_meanFiltered21.jpg');
  path = append('./output_images/question1b_output_images/', saveName);
  imwrite(meanFiltered21,path);

  % median filtering
  medianFiltered5 = CS4640_medianFiltering(currentImage, 5);
  medianFiltered11 = CS4640_medianFiltering(currentImage, 11);
  medianFiltered21 = CS4640_medianFiltering(currentImage, 21);

  saveName = append(name, '_medianFiltered5.jpg');
  path = append('./output_images/question1b_output_images/', saveName);
  imwrite(medianFiltered5,path);
  saveName = append(name, '_medianFiltered11.jpg');
  path = append('./output_images/question1b_output_images/', saveName);
  imwrite(medianFiltered11,path);
  saveName = append(name, '_medianFiltered21.jpg');
  path = append('./output_images/question1b_output_images/', saveName);
  imwrite(medianFiltered21,path);

  % gaussian filtering
  gaussianFiltered5 = CS4640_gaussianFiltering(currentImage, 5);
  gaussianFiltered11 = CS4640_gaussianFiltering(currentImage, 11);
  gaussianFiltered21 = CS4640_gaussianFiltering(currentImage, 21);

  saveName = append(name, '_gaussianFiltered5.jpg');
  path = append('./output_images/question1b_output_images/', saveName);
  imwrite(gaussianFiltered5,path);
  saveName = append(name, '_gaussianFiltered11.jpg');
  path = append('./output_images/question1b_output_images/', saveName);
  imwrite(gaussianFiltered11,path);
  saveName = append(name, '_gaussianFiltered21.jpg');
  path = append('./output_images/question1b_output_images/', saveName);
  imwrite(gaussianFiltered21,path);

  % absolute difference mean and gaussian
  absDiffImage5 = imabsdiff(meanFiltered5,gaussianFiltered5);
  absDiffImage11 = imabsdiff(meanFiltered11,gaussianFiltered11);
  absDiffImage21 = imabsdiff(meanFiltered21,gaussianFiltered21);

  saveName = append(name, '_mean_gauss_abs_diff5.jpg');
  path = append('./output_images/question1b_output_images/', saveName);
  imwrite(absDiffImage5,path);
  saveName = append(name, '_mean_gauss_abs_diff11.jpg');
  path = append('./output_images/question1b_output_images/', saveName);
  imwrite(absDiffImage11,path);
  saveName = append(name, '_mean_gauss_abs_diff21.jpg');
  path = append('./output_images/question1b_output_images/', saveName);
  imwrite(absDiffImage21,path);

  % fprintf(filename);
end

% 
% 
% % mean filtering
% eight_spImage2_meanFiltered5 = CS4640_meanFiltering(eight_spImage2, 5);
% eight_spImage2_meanFiltered11 = CS4640_meanFiltering(eight_spImage2, 11);
% eight_spImage2_meanFiltered21 = CS4640_meanFiltering(eight_spImage2, 21);
% 
% subplot(2,4,1), imshow(eight_spImage2); title('10% S&P Noise');
% subplot(2,4,2), imshow(eight_spImage2_meanFiltered5); title('S&P mean filter 5');
% subplot(2,4,3), imshow(eight_spImage2_meanFiltered11); title('S&P mean filter 11');
% subplot(2,4,4), imshow(eight_spImage2_meanFiltered21); title('S&P mean filter 21');
% 
% eight_gausImage2_meanFiltered5 = CS4640_meanFiltering(eight_gausImage2, 5);
% eight_gausImage2_meanFiltered11 = CS4640_meanFiltering(eight_gausImage2, 11);
% eight_gausImage2_meanFiltered21 = CS4640_meanFiltering(eight_gausImage2, 21);
% 
% subplot(2,4,5), imshow(eight_gausImage2); title('Gauss Noise SD: 1.0');
% subplot(2,4,6), imshow(eight_gausImage2_meanFiltered5); title('Gauss mean filter 5');
% subplot(2,4,7), imshow(eight_gausImage2_meanFiltered11); title('Gauss mean filter 11');
% subplot(2,4,8), imshow(eight_gausImage2_meanFiltered21); title('Gauss mean filter 21');
% 
% figure();
% 
% % median filtering
% eight_spImage2_medianFiltered5 = CS4640_medianFiltering(eight_spImage2, 5);
% eight_spImage2_medianFiltered11 = CS4640_medianFiltering(eight_spImage2, 11);
% eight_spImage2_medianFiltered21 = CS4640_medianFiltering(eight_spImage2, 21);
% 
% subplot(2,4,1), imshow(eight_spImage2); title('10% S&P Noise');
% subplot(2,4,2), imshow(eight_spImage2_medianFiltered5); title('S&P median filter 5');
% subplot(2,4,3), imshow(eight_spImage2_medianFiltered11); title('S&P median filter 11');
% subplot(2,4,4), imshow(eight_spImage2_medianFiltered21); title('S&P median filter 21');
% 
% eight_gausImage2_medianFiltered5 = CS4640_medianFiltering(eight_gausImage2, 5);
% eight_gausImage2_medianFiltered11 = CS4640_medianFiltering(eight_gausImage2, 11);
% eight_gausImage2_medianFiltered21 = CS4640_medianFiltering(eight_gausImage2, 21);
% 
% subplot(2,4,5), imshow(eight_gausImage2); title('Gauss Noise SD: 1.0');
% subplot(2,4,6), imshow(eight_gausImage2_medianFiltered5); title('Gauss median filter 5');
% subplot(2,4,7), imshow(eight_gausImage2_medianFiltered11); title('Gauss median filter 11');
% subplot(2,4,8), imshow(eight_gausImage2_medianFiltered21); title('Gauss median filter 21');
% 
% figure();
% 
% % gaussian filtering
% eight_spImage2_gaussianFiltered5 = CS4640_gaussianFiltering(eight_spImage2, 5);
% eight_spImage2_gaussianFiltered11 = CS4640_gaussianFiltering(eight_spImage2, 11);
% eight_spImage2_gaussianFiltered21 = CS4640_gaussianFiltering(eight_spImage2, 21);
% 
% subplot(2,4,1), imshow(eight_spImage2); title('10% S&P Noise');
% subplot(2,4,2), imshow(eight_spImage2_gaussianFiltered5); title('S&P gauss filter 5');
% subplot(2,4,3), imshow(eight_spImage2_gaussianFiltered11); title('S&P gauss filter 11');
% subplot(2,4,4), imshow(eight_spImage2_gaussianFiltered21); title('S&P gauss filter 21');
% 
% eight_gausImage2_gaussianFiltered5 = CS4640_gaussianFiltering(eight_gausImage2, 5);
% eight_gausImage2_gaussianFiltered11 = CS4640_gaussianFiltering(eight_gausImage2, 11);
% eight_gausImage2_gaussianFiltered21 = CS4640_gaussianFiltering(eight_gausImage2, 21);
% 
% subplot(2,4,5), imshow(eight_gausImage2); title('Gauss Noise SD: 1.0');
% subplot(2,4,6), imshow(eight_gausImage2_gaussianFiltered5); title('Gauss gauss filter 5');
% subplot(2,4,7), imshow(eight_gausImage2_gaussianFiltered11); title('Gauss gauss filter 11');
% subplot(2,4,8), imshow(eight_gausImage2_gaussianFiltered21); title('Gauss gauss filter 21');
% 
% figure();
% 
% % absolute diff between mean and gaussian
% 
% subplot(1,4,1), imshow(eight_spImage2); title('10% S&P Noise');
% subplot(1,4,2), imshow(eight_spImage2_meanFiltered5); title('S&P mean filter 5');
% subplot(1,4,3), imshow(eight_spImage2_gaussianFiltered5); title('S&P gauss filter 5');
% 
% absDiffImage = imabsdiff(eight_spImage2_meanFiltered5,eight_spImage2_gaussianFiltered5);
% 
% subplot(1,4,4), imshow(absDiffImage); title('mean guass abs diff');
% 
% figure();

% Question #2:  Edge detection --------------------------

% (a)

cameraman = imread("Assignment-4-Images\cameraman.tif");

[cameramanEdge1, cameramanEdge2, cameramanEdge3] = CS4640_edgeDetection(cameraman);

subplot(2,2,1), imshow(cameraman); title('cameraman.tif');
subplot(2,2,2), imshow(cameramanEdge1); title('filter 1');
subplot(2,2,3), imshow(cameramanEdge2); title('filter 2');
subplot(2,2,4), imshow(cameramanEdge3); title('filter 3');

figure();

% save images
saveName = 'cameraman_filter1.jpg';
path = append('./output_images/question2a_output_images/', saveName);
imwrite(cameramanEdge1,path);
saveName = 'cameraman_filter2.jpg';
path = append('./output_images/question2a_output_images/', saveName);
imwrite(cameramanEdge2,path);
saveName = 'cameraman_filter3.jpg';
path = append('./output_images/question2a_output_images/', saveName);
imwrite(cameramanEdge3,path);

% (b)

% peppers.png

peppers = imread("Assignment-4-Images\peppers.png");

peppersGreyscale = rgb2gray(peppers);

[peppersRobertsEdge, peppersPrewittEdge, peppersSobelEdge] = CS4640_RPSedgeDetection(peppersGreyscale);

subplot(2,2,1), imshow(peppers); title('peppers');
subplot(2,2,2), imshow(peppersRobertsEdge); title('Roberts ED');
subplot(2,2,3), imshow(peppersPrewittEdge); title('Perwitt ED');
subplot(2,2,4), imshow(peppersSobelEdge); title('Sobel ED');

figure();

% save images
saveName = 'peppersRobertsEdge_greyscale.jpg';
path = append('./output_images/question2b_output_images/', saveName);
imwrite(peppersRobertsEdge,path);
saveName = 'peppersPrewittEdge_greyscale.jpg';
path = append('./output_images/question2b_output_images/', saveName);
imwrite(peppersPrewittEdge,path);
saveName = 'peppersSobelEdge_greyscale.jpg';
path = append('./output_images/question2b_output_images/', saveName);
imwrite(peppersSobelEdge,path);

% split into RGB channels and then CS4640_RPSedgeDetection and then reconvene.

peppersR = peppers(:,:,1);
peppersG = peppers(:,:,2);
peppersB = peppers(:,:,3);

[peppersRobertsEdgeR, peppersPrewittEdgeR, peppersSobelEdgeR] = CS4640_RPSedgeDetection(peppersR);
[peppersRobertsEdgeG, peppersPrewittEdgeG, peppersSobelEdgeG] = CS4640_RPSedgeDetection(peppersG);
[peppersRobertsEdgeB, peppersPrewittEdgeB, peppersSobelEdgeB] = CS4640_RPSedgeDetection(peppersB);

% roberts
% peppersRobertsEdge = peppers;
% peppersRobertsEdge(:,:,1) = peppersRobertsEdgeR;
% peppersRobertsEdge(:,:,2) = peppersRobertsEdgeG;
% peppersRobertsEdge(:,:,3) = peppersRobertsEdgeB;
peppersRobertsEdgeAddedUp = peppersRobertsEdgeR + peppersRobertsEdgeG + peppersRobertsEdgeB;

% prewitt
% peppersPrewittEdge = peppers;
% peppersPrewittEdge(:,:,1) = peppersPrewittEdgeR;
% peppersPrewittEdge(:,:,2) = peppersPrewittEdgeG;
% peppersPrewittEdge(:,:,3) = peppersPrewittEdgeB;
peppersPrewittEdgeAddedUp = peppersPrewittEdgeR + peppersPrewittEdgeG + peppersPrewittEdgeB;

% sobel
% peppersSobelEdge = peppers;
% peppersSobelEdge(:,:,1) = peppersSobelEdgeR;
% peppersSobelEdge(:,:,2) = peppersSobelEdgeG;
% peppersSobelEdge(:,:,3) = peppersSobelEdgeB;
peppersSobelEdgeAddedUp = peppersSobelEdgeR + peppersSobelEdgeG + peppersSobelEdgeB;

subplot(2,2,1), imshow(peppers); title('peppers');
subplot(2,2,2), imshow(peppersRobertsEdgeAddedUp); title('Roberts ED');
subplot(2,2,3), imshow(peppersPrewittEdgeAddedUp); title('Perwitt ED');
subplot(2,2,4), imshow(peppersSobelEdgeAddedUp); title('Sobel ED');

% save images
saveName = 'peppersRobertsEdge_RGB_separated.jpg';
path = append('./output_images/question2b_output_images/', saveName);
imwrite(peppersRobertsEdgeAddedUp,path);
saveName = 'peppersPrewittEdge_greyscale_RGB_separated.jpg';
path = append('./output_images/question2b_output_images/', saveName);
imwrite(peppersPrewittEdgeAddedUp,path);
saveName = 'peppersSobelEdge_greyscale_RGB_separated.jpg';
path = append('./output_images/question2b_output_images/', saveName);
imwrite(peppersSobelEdgeAddedUp,path);

% split into HSV channels and then CS4640_RPSedgeDetection and then reconvene.

peppers = rgb2hsv(peppers);

peppersH = peppers(:,:,1);
peppersS = peppers(:,:,2);
peppersV = peppers(:,:,3);

[peppersRobertsEdgeH, peppersPrewittEdgeH, peppersSobelEdgeH] = CS4640_RPSedgeDetection(peppersH);
[peppersRobertsEdgeS, peppersPrewittEdgeS, peppersSobelEdgeS] = CS4640_RPSedgeDetection(peppersS);
[peppersRobertsEdgeV, peppersPrewittEdgeV, peppersSobelEdgeV] = CS4640_RPSedgeDetection(peppersV);

% roberts
peppersRobertsEdgeAddedUp = peppersRobertsEdgeH + peppersRobertsEdgeS + peppersRobertsEdgeV;

% prewitt
peppersPrewittEdgeAddedUp = peppersPrewittEdgeH + peppersPrewittEdgeS + peppersPrewittEdgeV;

% sobel
peppersSobelEdgeAddedUp = peppersSobelEdgeH + peppersSobelEdgeS + peppersSobelEdgeV;

subplot(2,2,1), imshow(peppers); title('peppers');
subplot(2,2,2), imshow(peppersRobertsEdgeAddedUp); title('Roberts ED');
subplot(2,2,3), imshow(peppersPrewittEdgeAddedUp); title('Perwitt ED');
subplot(2,2,4), imshow(peppersSobelEdgeAddedUp); title('Sobel ED');

figure();

% save images
saveName = 'peppersRobertsEdge_HSV_separated.jpg';
path = append('./output_images/question2b_output_images/', saveName);
imwrite(peppersRobertsEdgeAddedUp,path);
saveName = 'peppersPrewittEdge_greyscale_HSV_separated.jpg';
path = append('./output_images/question2b_output_images/', saveName);
imwrite(peppersPrewittEdgeAddedUp,path);
saveName = 'peppersSobelEdge_greyscale_HSV_separated.jpg';
path = append('./output_images/question2b_output_images/', saveName);
imwrite(peppersSobelEdgeAddedUp,path);

% football.png

football = imread("Assignment-4-Images\football.jpg");

footballGreyscale = rgb2gray(football);

[footballRobertsEdge, footballPrewittEdge, footballSobelEdge] = CS4640_RPSedgeDetection(footballGreyscale);

subplot(2,2,1), imshow(football); title('football');
subplot(2,2,2), imshow(footballRobertsEdge); title('Roberts ED');
subplot(2,2,3), imshow(footballPrewittEdge); title('Perwitt ED');
subplot(2,2,4), imshow(footballSobelEdge); title('Sobel ED');

figure();

% save images
saveName = 'footballRobertsEdge_greyscale.jpg';
path = append('./output_images/question2b_output_images/', saveName);
imwrite(footballRobertsEdge,path);
saveName = 'footballPrewittEdge_greyscale.jpg';
path = append('./output_images/question2b_output_images/', saveName);
imwrite(footballPrewittEdge,path);
saveName = 'footballSobelEdge_greyscale.jpg';
path = append('./output_images/question2b_output_images/', saveName);
imwrite(footballSobelEdge,path);

% split into RGB channels and then CS4640_RPSedgeDetection and then reconvene.

footballR = football(:,:,1);
footballG = football(:,:,2);
footballB = football(:,:,3);

[footballRobertsEdgeR, footballPrewittEdgeR, footballSobelEdgeR] = CS4640_RPSedgeDetection(footballR);
[footballRobertsEdgeG, footballPrewittEdgeG, footballSobelEdgeG] = CS4640_RPSedgeDetection(footballG);
[footballRobertsEdgeB, footballPrewittEdgeB, footballSobelEdgeB] = CS4640_RPSedgeDetection(footballB);

% roberts
% footballRobertsEdge = football;
% footballRobertsEdge(:,:,1) = footballRobertsEdgeR;
% footballRobertsEdge(:,:,2) = footballRobertsEdgeG;
% footballRobertsEdge(:,:,3) = footballRobertsEdgeB;
footballRobertsEdgeAddedUp = footballRobertsEdgeR + footballRobertsEdgeG + footballRobertsEdgeB;

% prewitt
% footballPrewittEdge = football;
% footballPrewittEdge(:,:,1) = footballPrewittEdgeR;
% footballPrewittEdge(:,:,2) = footballPrewittEdgeG;
% footballPrewittEdge(:,:,3) = footballPrewittEdgeB;
footballPrewittEdgeAddedUp = footballPrewittEdgeR + footballPrewittEdgeG + footballPrewittEdgeB;

% sobel
% footballSobelEdge = football;
% footballSobelEdge(:,:,1) = footballSobelEdgeR;
% footballSobelEdge(:,:,2) = footballSobelEdgeG;
% footballSobelEdge(:,:,3) = footballSobelEdgeB;
footballSobelEdgeAddedUp = footballSobelEdgeR + footballSobelEdgeG + footballSobelEdgeB;

subplot(2,2,1), imshow(football); title('football');
subplot(2,2,2), imshow(footballRobertsEdgeAddedUp); title('Roberts ED');
subplot(2,2,3), imshow(footballPrewittEdgeAddedUp); title('Perwitt ED');
subplot(2,2,4), imshow(footballSobelEdgeAddedUp); title('Sobel ED');

% save images
saveName = 'footballRobertsEdge_RGB_separated.jpg';
path = append('./output_images/question2b_output_images/', saveName);
imwrite(footballRobertsEdgeAddedUp,path);
saveName = 'footballPrewittEdge_greyscale_RGB_separated.jpg';
path = append('./output_images/question2b_output_images/', saveName);
imwrite(footballPrewittEdgeAddedUp,path);
saveName = 'footballSobelEdge_greyscale_RGB_separated.jpg';
path = append('./output_images/question2b_output_images/', saveName);
imwrite(footballSobelEdgeAddedUp,path);

% split into HSV channels and then CS4640_RPSedgeDetection and then reconvene.

football = rgb2hsv(football);

footballH = football(:,:,1);
footballS = football(:,:,2);
footballV = football(:,:,3);

[footballRobertsEdgeH, footballPrewittEdgeH, footballSobelEdgeH] = CS4640_RPSedgeDetection(footballH);
[footballRobertsEdgeS, footballPrewittEdgeS, footballSobelEdgeS] = CS4640_RPSedgeDetection(footballS);
[footballRobertsEdgeV, footballPrewittEdgeV, footballSobelEdgeV] = CS4640_RPSedgeDetection(footballV);

% roberts
footballRobertsEdgeAddedUp = footballRobertsEdgeH + footballRobertsEdgeS + footballRobertsEdgeV;

% prewitt
footballPrewittEdgeAddedUp = footballPrewittEdgeH + footballPrewittEdgeS + footballPrewittEdgeV;

% sobel
footballSobelEdgeAddedUp = footballSobelEdgeH + footballSobelEdgeS + footballSobelEdgeV;

subplot(2,2,1), imshow(football); title('football');
subplot(2,2,2), imshow(footballRobertsEdgeAddedUp); title('Roberts ED');
subplot(2,2,3), imshow(footballPrewittEdgeAddedUp); title('Perwitt ED');
subplot(2,2,4), imshow(footballSobelEdgeAddedUp); title('Sobel ED');

figure();

% save images
saveName = 'footballRobertsEdge_HSV_separated.jpg';
path = append('./output_images/question2b_output_images/', saveName);
imwrite(footballRobertsEdgeAddedUp,path);
saveName = 'footballPrewittEdge_greyscale_HSV_separated.jpg';
path = append('./output_images/question2b_output_images/', saveName);
imwrite(footballPrewittEdgeAddedUp,path);
saveName = 'footballSobelEdge_greyscale_HSV_separated.jpg';
path = append('./output_images/question2b_output_images/', saveName);
imwrite(footballSobelEdgeAddedUp,path);

% (c)

circuit = imread("Assignment-4-Images\circuit.tif");

[PrewittEdge, SobelEdge] = CS4640_prewittSobelEDManual(circuit);

subplot(2,3,1), imshow(circuit); title('circuit');
subplot(2,3,2), imshow(PrewittEdge); title('Prewitt ED');
subplot(2,3,3), imshow(SobelEdge); title('Sobel ED');

[PrewittEdgeS, SobelEdgeS] = CS4640_proveLinearSeparability(circuit);

subplot(2,3,4), imshow(PrewittEdgeS); title('Prewitt ED LS');
subplot(2,3,5), imshow(SobelEdgeS); title('Sobel ED LS');

figure();

saveName = 'circuitPrewittED_normal.jpg';
path = append('./output_images/question2c_output_images/', saveName);
imwrite(PrewittEdge,path);
saveName = 'circuitSobelED_normal.jpg';
path = append('./output_images/question2c_output_images/', saveName);
imwrite(SobelEdge,path);

saveName = 'circuitPrewittED_linearSep.jpg';
path = append('./output_images/question2c_output_images/', saveName);
imwrite(PrewittEdgeS,path);
saveName = 'circuitSobelED_linearSep.jpg';
path = append('./output_images/question2c_output_images/', saveName);
imwrite(SobelEdgeS,path);

% Question #3: Edge enhancement --------------------------

% (a)

eight = imread('Assignment-4-Images\eight.tif');

eightSharp = CS4640_laplacianEdgeSharpening(eight);

subplot(1,2,1), imshow(eight); title('eight.tif');
subplot(1,2,2), imshow(eightSharp); title('Sharpened');

figure();

saveName = 'eightSharp.jpg';
path = append('./output_images/question3a_output_images/', saveName);
imwrite(eightSharp,path);

% (b)

% peppers

peppers = imread('Assignment-4-Images\peppers.png');

peppersSharp = CS4640_laplacianEdgeSharpeningColor(peppers);

subplot(1,2,1), imshow(peppers); title('peppers.png');
subplot(1,2,2), imshow(peppersSharp); title('Sharpened');

figure();

saveName = 'peppersSharp.jpg';
path = append('./output_images/question3b_output_images/', saveName);
imwrite(peppersSharp,path);

% football

football = imread('Assignment-4-Images\football.jpg');

footballSharp = CS4640_laplacianEdgeSharpeningColor(football);

subplot(1,2,1), imshow(football); title('football.jpg');
subplot(1,2,2), imshow(footballSharp); title('Sharpened');

figure();

saveName = 'footballSharp.jpg';
path = append('./output_images/question3b_output_images/', saveName);
imwrite(footballSharp,path);

% Functions ----------------------------------------------------------

function [spImage] = CS4640_addSaltAndPepperNoise(inputImage, spDensity)
%
% CS4640_addSaltAndPepperNoise - This function adds salt and pepper noise to a given image.
%
% Input:-
%     <input variable inputImage> (image): An input image.
%     <input variable spDensity> (double): The density of the noise.
% Output:-
%     <output variable spImage> (image): The noisy image.
%  Example usage:
%       eight = imread('./Assignment-4-Images/eight.tif');
%		eight_spImage1 = CS4640_addSaltAndPepperNoise(eight, 0.05);
% Author:
%     Harry Kim
%		Fall 2022
%

spImage = imnoise(inputImage,'salt & pepper', spDensity);

end

function [gaussianImage] = CS4640_addGaussianNoise(inputImage, standardDeviation)
%
% CS4640_addSaltAndPepperNoise - This function adds gaussian noise to a given image.
%
% Input:-
%     <input variable inputImage> (image): An input image.
%     <input variable standardDeviation> (double): The standard deviation
%     for the gaussian function.
% Output:-
%     <output variable spImage> (image): The noisy image.
%  Example usage:
%       eight = imread('./Assignment-4-Images/eight.tif');
%		eight_gausImage3 = CS4640_addGaussianNoise(eight, 10);
% Author:
%     Harry Kim
%		Fall 2022
%

gaussianImage = imnoise(inputImage,'gaussian', 0, sqrt(standardDeviation));

end

function [filteredImage] = CS4640_meanFiltering(inputImage, filterSize)
%
% CS4640_meanFiltering - This function applies mean filtering to a given image.
%
% Input:-
%     <input variable inputImage> (image): An input image.
%     <input variable filterSize> (int): The n of an n x n filter.
% Output:-
%     <output variable spImage> (image): The filtered image.
%  Example usage:
%       filtered = CS4640_meanFiltering(currentImage, 21);
% Author:
%     Harry Kim
%		Fall 2022
%

kernel = ones(filterSize, filterSize)/(filterSize^2);

filteredImage = imfilter(inputImage, kernel);

end

function [filteredImage] = CS4640_medianFiltering(inputImage, filterSize)
%
% CS4640_meanFiltering - This function applies median filtering to a given image.
%
% Input:-
%     <input variable inputImage> (image): An input image.
%     <input variable filterSize> (int): The n of an n x n filter.
% Output:-
%     <output variable spImage> (image): The filtered image.
%  Example usage:
%       filtered = CS4640_medianFiltering(currentImage, 21);
% Author:
%     Harry Kim
%		Fall 2022
%

if size(inputImage,3)==3
    filteredImage = inputImage;
    R = inputImage(:,:,1);
    G = inputImage(:,:,2);
    B = inputImage(:,:,3);
    filteredImage(:,:,1) = medfilt2(R);
    filteredImage(:,:,2) = medfilt2(G);
    filteredImage(:,:,3) = medfilt2(B);
    return;
end

filteredImage = medfilt2(inputImage, [filterSize, filterSize]);

end

function [filteredImage] = CS4640_gaussianFiltering(inputImage, filterSize)
%
% CS4640_meanFiltering - This function applies gaussian filtering to a given image.
%
% Input:-
%     <input variable inputImage> (image): An input image.
%     <input variable filterSize> (int): The n of an n x n filter.
% Output:-
%     <output variable spImage> (image): The filtered image.
%  Example usage:
%       filtered = CS4640_gaussianFiltering(currentImage, 21);
% Author:
%     Harry Kim
%		Fall 2022
%

kernel = fspecial('gaussian', [filterSize filterSize], 2);

filteredImage = imfilter(inputImage, kernel);

end

function [edgeImage1, edgeImage2, edgeImage3] = CS4640_edgeDetection(inputImage)
%
% CS4640_edgeDetection - This function applies the specified filters, given
% in question 2a, to an image.
%
% Input:-
%     <input variable inputImage> (image): An input image.
% Output:-
%     <output variable edgeImage1> (image): The filtered image with the
%     first filter.
%     <output variable edgeImage2> (image): The filtered image with the
%     second filter.
%     <output variable edgeImage3> (image): The filtered image with the
%     third filter.
%  Example usage:
%       [cameramanEdge1, cameramanEdge2, cameramanEdge3] = CS4640_edgeDetection(cameraman);
% Author:
%     Harry Kim
%		Fall 2022
%

filter1 = [-1 0 1;-1 0 1;-1 0 1];
filter2 = [-1 -1 -1;0 0 0;1 1 1];
filter3 = [0 -1 0;-1 4 -1;0 -1 0];

edgeImage1 = conv2(inputImage, filter1);
edgeImage2 = conv2(inputImage, filter2);
edgeImage3 = conv2(inputImage, filter3);


end

function [roberts, prewitts, sobels] = CS4640_RPSedgeDetection(inputImage)
%
% CS4640_RPSedgeDetection - This function applies Roberts', Prewitt's, and
% Sobel's edge detection filters to a given image.
%
% Input:-
%     <input variable inputImage> (image): An input image.
% Output:-
%     <output variable roberts> (image): The filtered image with the
%     Robert's filter.
%     <output variable prewitts> (image): The filtered image with the
%     Prewitt's filter.
%     <output variable sobels> (image): The filtered image with the
%     Sobel's filter.
%  Example usage:
%       [peppersRobertsEdge, peppersPrewittEdge, peppersSobelEdge] = CS4640_RPSedgeDetection(peppersGreyscale);
% Author:
%     Harry Kim
%		Fall 2022
%

roberts = edge(inputImage,'Roberts');	%Roberts edges
prewitts = edge(inputImage,'Prewitt');	%Prewitt edges
sobels = edge(inputImage,'Sobel');	    %Sobel edges

end

function [PrewittEdge, SobelEdge] = CS4640_prewittSobelEDManual(inputImage)
%
% CS4640_prewittSobelEDManual - This function applies Prewitt's, and
% Sobel's edge detection filters to a given image manually without built-in
% matlab functions.
%
% Input:-
%     <input variable inputImage> (image): An input image.
% Output:-
%     <output variable roberts> (image): The filtered image with the
%     Robert's filter.
%     <output variable prewitts> (image): The filtered image with the
%     Prewitt's filter.
%  Example usage:
%       [PrewittEdge, SobelEdge] = CS4640_prewittSobelEDManual(circuit);
% Author:
%     Harry Kim
%		Fall 2022
%

% Source: https://www.youtube.com/watch?v=5FJMvBlZIqc

% Prewitt

PrewittYDeriv= [-1 -1 -1;0 0 0;1 1 1];
PrewittYDeriv=flipud(PrewittYDeriv); 
PrewittYDeriv=fliplr(PrewittYDeriv);

PrewittXDeriv= [-1 0 1;-1 0 1;-1 0 1];
PrewittXDeriv=flipud(PrewittXDeriv); 
PrewittXDeriv=fliplr(PrewittXDeriv);

[row col]=size(inputImage);

for x=2:1:row-1
    for y=2:1:col-1
        inputImage1(x,y)=PrewittYDeriv(1)*inputImage(x-1,y-1)+PrewittYDeriv(2)*inputImage(x-1,y)+PrewittYDeriv(3)*...
        inputImage(x-1,y+1)+PrewittYDeriv(4)*inputImage(x,y-1)+PrewittYDeriv(5)*inputImage(x,y)+PrewittYDeriv(6)*...
        inputImage(x,y+1)+PrewittYDeriv(7)*inputImage(x+1,y-1)+PrewittYDeriv(8)*inputImage(x+1,y)+PrewittYDeriv(9)*...
        inputImage(x+1,y+1);

        inputImage2(x,y)=PrewittXDeriv(1)*inputImage(x-1,y-1)+PrewittXDeriv(2)*inputImage(x-1,y)+PrewittXDeriv(3)*...
        inputImage(x-1,y+1)+PrewittXDeriv(4)*inputImage(x,y-1)+PrewittXDeriv(5)*inputImage(x,y)+PrewittXDeriv(6)*...
        inputImage(x,y+1)+PrewittXDeriv(7)*inputImage(x+1,y-1)+PrewittXDeriv(8)*inputImage(x+1,y)+PrewittXDeriv(9)*...
        inputImage(x+1,y+1);
    end
end

PrewittEdge = inputImage1 + inputImage2;

PrewittEdge = imcomplement(PrewittEdge);

% Sobel

SobelYDeriv= [-1 -2 -1;0 0 0;1 2 1];
SobelYDeriv=flipud(SobelYDeriv); 
SobelYDeriv=fliplr(SobelYDeriv);

SobelXDeriv= [-1 0 1;-2 0 2;-1 0 1];
SobelXDeriv=flipud(SobelXDeriv); 
SobelXDeriv=fliplr(SobelXDeriv);

[row col]=size(inputImage);

for x=2:1:row-1
    for y=2:1:col-1
        inputImage1(x,y)=SobelYDeriv(1)*inputImage(x-1,y-1)+SobelYDeriv(2)*inputImage(x-1,y)+SobelYDeriv(3)*...
        inputImage(x-1,y+1)+SobelYDeriv(4)*inputImage(x,y-1)+SobelYDeriv(5)*inputImage(x,y)+SobelYDeriv(6)*...
        inputImage(x,y+1)+SobelYDeriv(7)*inputImage(x+1,y-1)+SobelYDeriv(8)*inputImage(x+1,y)+SobelYDeriv(9)*...
        inputImage(x+1,y+1);

        inputImage2(x,y)=SobelXDeriv(1)*inputImage(x-1,y-1)+SobelXDeriv(2)*inputImage(x-1,y)+SobelXDeriv(3)*...
        inputImage(x-1,y+1)+SobelXDeriv(4)*inputImage(x,y-1)+SobelXDeriv(5)*inputImage(x,y)+SobelXDeriv(6)*...
        inputImage(x,y+1)+SobelXDeriv(7)*inputImage(x+1,y-1)+SobelXDeriv(8)*inputImage(x+1,y)+SobelXDeriv(9)*...
        inputImage(x+1,y+1);
    end
end

SobelEdge = inputImage1 + inputImage2;

SobelEdge = imcomplement(SobelEdge);

end

function [PrewittEdge, SobelEdge] = CS4640_proveLinearSeparability(inputImage)
%
% CS4640_prewittSobelEDManual - This function applies Prewitt's, and
% Sobel's edge detection filters to a given image, but it first multiplies
% the linearly separated matricies of both the X and Y derivative filters
% first, hence proving that Perwitt and Sobel filters are linearly
% separable.
%
% Input:-
%     <input variable inputImage> (image): An input image.
% Output:-
%     <output variable roberts> (image): The filtered image with the
%     Robert's filter.
%     <output variable prewitts> (image): The filtered image with the
%     Prewitt's filter.
%  Example usage:
%       [PrewittEdge, SobelEdge] = CS4640_proveLinearSeparability(circuit);
% Author:
%     Harry Kim
%		Fall 2022
%

% Prewitt

a = [-1; 0; 1];

b = [1 1 1];

PrewittYDeriv= a * b;
PrewittYDeriv=flipud(PrewittYDeriv); 
PrewittYDeriv=fliplr(PrewittYDeriv);

c = [1; 1; 1];

d = [-1 0 1];

PrewittXDeriv = c * d;
PrewittXDeriv=flipud(PrewittXDeriv); 
PrewittXDeriv=fliplr(PrewittXDeriv);

[row col]=size(inputImage);

for x=2:1:row-1
    for y=2:1:col-1
        inputImage1(x,y)=PrewittYDeriv(1)*inputImage(x-1,y-1)+PrewittYDeriv(2)*inputImage(x-1,y)+PrewittYDeriv(3)*...
        inputImage(x-1,y+1)+PrewittYDeriv(4)*inputImage(x,y-1)+PrewittYDeriv(5)*inputImage(x,y)+PrewittYDeriv(6)*...
        inputImage(x,y+1)+PrewittYDeriv(7)*inputImage(x+1,y-1)+PrewittYDeriv(8)*inputImage(x+1,y)+PrewittYDeriv(9)*...
        inputImage(x+1,y+1);

        inputImage2(x,y)=PrewittXDeriv(1)*inputImage(x-1,y-1)+PrewittXDeriv(2)*inputImage(x-1,y)+PrewittXDeriv(3)*...
        inputImage(x-1,y+1)+PrewittXDeriv(4)*inputImage(x,y-1)+PrewittXDeriv(5)*inputImage(x,y)+PrewittXDeriv(6)*...
        inputImage(x,y+1)+PrewittXDeriv(7)*inputImage(x+1,y-1)+PrewittXDeriv(8)*inputImage(x+1,y)+PrewittXDeriv(9)*...
        inputImage(x+1,y+1);
    end
end

PrewittEdge = inputImage1 + inputImage2;

PrewittEdge = imcomplement(PrewittEdge);

% Sobel

a = [-1; 0; 1];

b = [1 2 1];

SobelYDeriv= a * b;
SobelYDeriv=flipud(SobelYDeriv); 
SobelYDeriv=fliplr(SobelYDeriv);

c = [1; 2; 1];
d = [-1 0 1];

SobelXDeriv = c * d;
SobelXDeriv=flipud(SobelXDeriv); 
SobelXDeriv=fliplr(SobelXDeriv);

[row col]=size(inputImage);

for x=2:1:row-1
    for y=2:1:col-1
        inputImage1(x,y)=SobelYDeriv(1)*inputImage(x-1,y-1)+SobelYDeriv(2)*inputImage(x-1,y)+SobelYDeriv(3)*...
        inputImage(x-1,y+1)+SobelYDeriv(4)*inputImage(x,y-1)+SobelYDeriv(5)*inputImage(x,y)+SobelYDeriv(6)*...
        inputImage(x,y+1)+SobelYDeriv(7)*inputImage(x+1,y-1)+SobelYDeriv(8)*inputImage(x+1,y)+SobelYDeriv(9)*...
        inputImage(x+1,y+1);

        inputImage2(x,y)=SobelXDeriv(1)*inputImage(x-1,y-1)+SobelXDeriv(2)*inputImage(x-1,y)+SobelXDeriv(3)*...
        inputImage(x-1,y+1)+SobelXDeriv(4)*inputImage(x,y-1)+SobelXDeriv(5)*inputImage(x,y)+SobelXDeriv(6)*...
        inputImage(x,y+1)+SobelXDeriv(7)*inputImage(x+1,y-1)+SobelXDeriv(8)*inputImage(x+1,y)+SobelXDeriv(9)*...
        inputImage(x+1,y+1);
    end
end

SobelEdge = inputImage1 + inputImage2;

SobelEdge = imcomplement(SobelEdge);
end

function [sharpenedImage] = CS4640_laplacianEdgeSharpening(inputImage)
%
% CS4640_laplacianEdgeSharpening - This function applies a Laplacian edge
% sharpening to a given image.
%
% Input:-
%     <input variable inputImage> (image): An input image.
% Output:-
%     <output variable sharpenedImage> (image): The sharpened image.
%  Example usage:
%       eightSharp = CS4640_laplacianEdgeSharpening(eight);
% Author:
%     Harry Kim
%		Fall 2022
%

LapFilter=[0 1 0; 1 -4 1; 0 1 0];
a1 = conv2(inputImage,LapFilter,'same');

% Normalise the range of intensity.
a2=uint8(a1);

sharpenedImage = abs(inputImage-a2);

end

function [sharpenedImage] = CS4640_laplacianEdgeSharpeningColor(inputImage)
%
% CS4640_laplacianEdgeSharpeningColor - This function applies a Laplacian edge
% sharpening to a given colored image.
%
% Input:-
%     <input variable inputImage> (image): An input image.
% Output:-
%     <output variable sharpenedImage> (image): The sharpened image.
%  Example usage:
%       peppersSharp = CS4640_laplacianEdgeSharpeningColor(peppers);
% Author:
%     Harry Kim
%		Fall 2022
%

LapFilter=[0 1 0; 1 -4 1; 0 1 0];

sharpenedImage = inputImage;

R = inputImage(:,:,1);
G = inputImage(:,:,2);
B = inputImage(:,:,3);

sharpenedImage(:,:,1) = abs(R - uint8(conv2(R,LapFilter,'same')));
sharpenedImage(:,:,2) = abs(G - uint8(conv2(G,LapFilter,'same')));
sharpenedImage(:,:,3) = abs(B - uint8(conv2(B,LapFilter,'same')));

end
