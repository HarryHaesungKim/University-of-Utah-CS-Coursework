clc

% Question 3: Frequency space filtering --------------------------

% need filters for 3, 5, 15, 45 and size 256 x 256

stdList = {3, 5, 15, 45};

coins = imread("./Assignment-6-Images/coins.png");

% do everything within this forloop
for i = 1:length(stdList)
    % coins filter one for every std
    figure()
    gaussKernel = fspecial('Gaussian',[256 265], stdList{i});

    % (1) Use 2d convolution to obtain image.
    gaussImage = imfilter(coins, gaussKernel, 'conv');
    imshow(gaussImage);
    saveName = ['coins_gauss_' num2str(stdList{i}) '.png'];
    path = append('./output_images/Q3/', saveName);
    imwrite(gaussImage,path);

    % (2) Use fft2
    gaussKernel_fft = fftshift(fft2(gaussKernel));
    image_fft = fftshift(fft2(coins));
    fftImageReal = image_fft .* imresize(gaussKernel_fft,size(image_fft),'nearest');
    imshow(fftImageReal);
    saveName = ['coins_fft_real_' num2str(stdList{i}) '.png'];
    path = append('./output_images/Q3/', saveName);
    imwrite(fftImageReal,path);

    % Imaginary
    fftImageImaginary = imag(fftImageReal);
    imshow(fftImageImaginary);
    saveName = ['coins_fft_imaginary_' num2str(stdList{i}) '.png'];
    path = append('./output_images/Q3/', saveName);
    imwrite(fftImageImaginary,path);

    % Magnitude
    imshow(abs(fftImageReal));
    saveName = ['coins_fft_magnitude_' num2str(stdList{i}) '.png'];
    path = append('./output_images/Q3/', saveName);
    imwrite(abs(fftImageReal),path);
    
    % Phase
    % Probably best suited for analysis.
    imshow(angle(fftImageReal));
    saveName = ['coins_fft_phase_' num2str(stdList{i}) '.png'];
    path = append('./output_images/Q3/', saveName);
    imwrite(angle(fftImageReal),path);

    % (3) Use ifft2
    ifftImageReal = ifftshift(ifft2(fftImageReal));
    % ifftImageReal = ifftshift(ifft2(abs(fftImageReal).*angle(fftImageReal)));
    imshow(ifftImageReal);
    saveName = ['coins_ifft_real_' num2str(stdList{i}) '.png'];
    path = append('./output_images/Q3/', saveName);
    imwrite(ifftImageReal,path);

    % Imaginary
    ifftImageImaginary = imag(ifftImageReal);
    imshow(ifftImageImaginary);
    saveName = ['coins_ifft_imaginary_' num2str(stdList{i}) '.png'];
    path = append('./output_images/Q3/', saveName);
    imwrite(ifftImageImaginary,path);

    % Magnitude
    imshow(abs(ifftImageReal));
    saveName = ['coins_ifft_magnitude_' num2str(stdList{i}) '.png'];
    path = append('./output_images/Q3/', saveName);
    imwrite(abs(ifftImageReal),path);
    
    % Phase
    % Probably best suited for analysis.
    imshow(angle(ifftImageReal));
    saveName = ['coins_ifft_phase_' num2str(stdList{i}) '.png'];
    path = append('./output_images/Q3/', saveName);
    imwrite(angle(ifftImageReal),path);

    % (4) Gaussian over image from 3?
    outputImage = imfilter(angle(ifftImageReal), gaussKernel, 'conv');
    imshow(outputImage);
    saveName = ['coins_ifft_real_gauss_' num2str(stdList{i}) '.png'];
    path = append('./output_images/Q3/', saveName);
    imwrite(outputImage,path);

    % (5) Find and display difference between output 3 and 4.
    diffImage = imabsdiff(angle(ifftImageReal), angle(ifftImageReal));
    imshow(diffImage);
    saveName = ['coins_final_absdiff_' num2str(stdList{i}) '.png'];
    path = append('./output_images/Q3/', saveName);
    imwrite(diffImage,path);

end

% do everything again without shift

for i = 1:length(stdList)
    % coins filter one for every std
    figure()
    gaussKernel = fspecial('Gaussian',[256 265], stdList{i});

    % (1) Use 2d convolution to obtain image.
    gaussImage = imfilter(coins, gaussKernel, 'conv');
    imshow(gaussImage);
    saveName = ['coins_noShift_gauss_' num2str(stdList{i}) '.png'];
    path = append('./output_images/Q3/', saveName);
    imwrite(gaussImage,path);

    % (2) Use fft2
    gaussKernel_fft = fft2(gaussKernel);
    image_fft = fft2(coins);
    fftImageReal = image_fft .* imresize(gaussKernel_fft,size(image_fft),'nearest');
    imshow(fftImageReal);
    saveName = ['coins_noShift_fft_real_' num2str(stdList{i}) '.png'];
    path = append('./output_images/Q3/', saveName);
    imwrite(fftImageReal,path);

    % Imaginary
    fftImageImaginary = imag(fftImageReal);
    imshow(fftImageImaginary);
    saveName = ['coins_noShift_fft_imaginary_' num2str(stdList{i}) '.png'];
    path = append('./output_images/Q3/', saveName);
    imwrite(fftImageImaginary,path);

    % Magnitude
    imshow(abs(fftImageReal));
    saveName = ['coins_noShift_fft_magnitude_' num2str(stdList{i}) '.png'];
    path = append('./output_images/Q3/', saveName);
    imwrite(abs(fftImageReal),path);
    
    % Phase
    % Probably best suited for analysis.
    imshow(angle(fftImageReal));
    saveName = ['coins_noShift_fft_phase_' num2str(stdList{i}) '.png'];
    path = append('./output_images/Q3/', saveName);
    imwrite(angle(fftImageReal),path);

    % (3) Use ifft2
    ifftImageReal = ifft2(fftImageReal);
    % ifftImageReal = ifft2(abs(fftImageReal).*angle(fftImageReal));
    imshow(ifftImageReal);
    saveName = ['coins_noShift_ifft_real_' num2str(stdList{i}) '.png'];
    path = append('./output_images/Q3/', saveName);
    imwrite(ifftImageReal,path);

    % Imaginary
    ifftImageImaginary = imag(ifftImageReal);
    imshow(ifftImageImaginary);
    saveName = ['coins_noShift_ifft_imaginary_' num2str(stdList{i}) '.png'];
    path = append('./output_images/Q3/', saveName);
    imwrite(ifftImageImaginary,path);

    % Magnitude
    imshow(abs(ifftImageReal));
    saveName = ['coins_noShift_ifft_magnitude_' num2str(stdList{i}) '.png'];
    path = append('./output_images/Q3/', saveName);
    imwrite(abs(ifftImageReal),path);
    
    % Phase
    % Probably best suited for analysis.
    imshow(angle(ifftImageReal));
    saveName = ['coins_noShift_ifft_phase_' num2str(stdList{i}) '.png'];
    path = append('./output_images/Q3/', saveName);
    imwrite(angle(ifftImageReal),path);

    % (4) Gaussian over image from 3?
    outputImage = imfilter(angle(ifftImageReal), gaussKernel, 'conv');
    imshow(outputImage);
    saveName = ['coins_noShift_ifft_real_gauss_' num2str(stdList{i}) '.png'];
    path = append('./output_images/Q3/', saveName);
    imwrite(outputImage,path);

    % (5) Find and display difference between output 3 and 4.
    diffImage = imabsdiff(angle(ifftImageReal), angle(ifftImageReal));
    imshow(diffImage);
    saveName = ['coins_noShift_final_absdiff_' num2str(stdList{i}) '.png'];
    path = append('./output_images/Q3/', saveName);
    imwrite(diffImage,path);

end

disp('Q3 job done');

% Question 4: Frequency space filtering --------------------------

% Circle inside matrix credit:
% https://www.mathworks.com/matlabcentral/answers/346377-circle-in-a-matrix

% (2)
% filter figure
figure()
x = 1:256;
y = 1:256;
[xx, yy] = meshgrid(x,y);
filter1 = zeros(size(xx));
filter1(((xx - 128).^2+(yy - 128).^2)<25^2) = 1;   % radius 20, center at the origin
filter1(((xx - 128).^2+(yy - 128).^2)<0^2) = 0;   % radius 10, center at the origin
% hard boundary
imagesc(filter1)
saveName = ['./output_images/Q4/filter[0_25]_.png'];
saveas(gcf,saveName);

x = 1:256;
y = 1:256;
[xx, yy] = meshgrid(x,y);
filter2 = zeros(size(xx));
filter2(((xx - 128).^2+(yy - 128).^2)<50^2) = 1;   % radius 20, center at the origin
filter2(((xx - 128).^2+(yy - 128).^2)<25^2) = 0;   % radius 10, center at the origin
% hard boundary
imagesc(filter2)
saveName = ['./output_images/Q4/filter[25_50]_.png'];
saveas(gcf,saveName);

x = 1:256;
y = 1:256;
[xx, yy] = meshgrid(x,y);
filter3 = zeros(size(xx));
filter3(((xx - 128).^2+(yy - 128).^2)<75^2) = 1;   % radius 20, center at the origin
filter3(((xx - 128).^2+(yy - 128).^2)<50^2) = 0;   % radius 10, center at the origin
% hard boundary
imagesc(filter3)
saveName = ['./output_images/Q4/filter[50_75]_.png'];
saveas(gcf,saveName);

x = 1:256;
y = 1:256;
[xx, yy] = meshgrid(x,y);
filter4 = zeros(size(xx));
filter4(((xx - 128).^2+(yy - 128).^2)<100^2) = 1;   % radius 20, center at the origin
filter4(((xx - 128).^2+(yy - 128).^2)<75^2) = 0;   % radius 10, center at the origin
% hard boundary
imagesc(filter4)
saveName = ['./output_images/Q4/filter[75_100]_.png'];
saveas(gcf,saveName)

filters = {filter1, filter2, filter3, filter4};

% onion time
onion = imread("./Assignment-6-Images/onion.png");

% (1)
% onion figure
figure()
onion_fft = fftshift(fft2(onion));
saveName = ['onion_fft.png'];
path = append('./output_images/Q4/', saveName);
imwrite(onion_fft,path);

for i = 1:length(filters)

    % (3)
    currentFilter = filters{i};
    onion_fft_real = onion_fft .* imresize(currentFilter, size(rgb2gray(onion_fft)),'nearest');
    imshow(onion_fft_real);
    saveName = ['onion_fft_filter_' num2str(i) '.png'];
    path = append('./output_images/Q4/', saveName);
    imwrite(onion_fft_real,path);

    % (4)
    onion_ifft = ifftshift(ifft2(onion_fft_real));
    imshow(onion_ifft);
    saveName = ['onion_ifft_filter_' num2str(i) '.png'];
    path = append('./output_images/Q4/', saveName);
    imwrite(onion_ifft,path);

    subplot(1,2,1), imshow(onion); title('Original');
    subplot(1,2,2), imshow(onion_ifft); title('Transformed');
    saveName = ['./output_images/Q4/onion_comparison_filter_' num2str(i) '.png'];
    saveas(gcf,saveName);

    % (5)
    ifftFilter = ifftshift(ifft2(filters{i}));
    imshow(abs(ifftFilter));
    saveName = ['onion_magnitude_filter_' num2str(i) '.png'];
    path = append('./output_images/Q4/', saveName);
    imwrite(filter4,path);

end


% cameraman time
cameraman = imread("./Assignment-6-Images/cameraman.jpg");

% (1)
% cameraman figure
figure()
cameraman_fft = fftshift(fft2(cameraman));
saveName = ['cameraman_fft.png'];
path = append('./output_images/Q4/', saveName);
imwrite(cameraman_fft,path);

for i = 1:length(filters)

    % (3)
    currentFilter = filters{i};
    cameraman_fft_real = cameraman_fft .* imresize(currentFilter, size(cameraman_fft),'nearest');
    imshow(cameraman_fft_real);
    saveName = ['cameraman_fft_filter_' num2str(i) '.png'];
    path = append('./output_images/Q4/', saveName);
    imwrite(cameraman_fft_real,path);

    % (4)
    cameraman_ifft = ifftshift(ifft2(cameraman_fft_real));
    imshow(cameraman_ifft);
    saveName = ['cameraman_ifft_filter_' num2str(i) '.png'];
    path = append('./output_images/Q4/', saveName);
    imwrite(cameraman_ifft,path);

    subplot(1,2,1), imshow(cameraman); title('Original');
    subplot(1,2,2), imshow(cameraman_ifft); title('Transformed');
    saveName = ['./output_images/Q4/cameraman_comparison_filter_' num2str(i) '.png'];
    saveas(gcf,saveName);

    % (5)
    ifftFilter = ifftshift(ifft2(filters{i}));
    imshow(abs(ifftFilter));
    saveName = ['cameraman_magnitude_filter_' num2str(i) '.png'];
    path = append('./output_images/Q4/', saveName);
    imwrite(filter4,path);

end

disp('Q4 job done');

% Functions ----------------------------------------------------------
