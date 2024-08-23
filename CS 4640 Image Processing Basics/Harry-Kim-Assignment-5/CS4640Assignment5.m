% Question #1: Transformations --------------------------

%(a)
% shape1-question1.mat
data = load("Assignment-5-Files\Question1\shape1-question1.mat");
scatter(data.x, data.y, "filled");
xlim([-200 400]);
ylim([-200 400]);
saveas(gcf,'./output_images/question1/shape1-question1_original.png');
figure();

[newX, newY] = CS4640_shiftTransformation(10,10,data);
scatter(newX, newY, "filled");
xlim([-200 400]);
ylim([-200 400]);
saveas(gcf,'./output_images/question1/shape1-question1_shift_10_10.png');
figure();

[newX, newY] = CS4640_shiftTransformation(20,-10,data);
scatter(newX, newY, "filled");
xlim([-200 400]);
ylim([-200 400]);
saveas(gcf,'./output_images/question1/shape1-question1_shift_20_-10.png');
figure();

[newX, newY] = CS4640_shiftTransformation(-10,-20,data);
scatter(newX, newY, "filled");
xlim([-200 400]);
ylim([-200 400]);
saveas(gcf,'./output_images/question1/shape1-question1_shift_-10_-20.png');
figure();

[newX, newY] = CS4640_shiftTransformation(-30,10,data);
scatter(newX, newY, "filled");
xlim([-200 400]);
ylim([-200 400]);
saveas(gcf,'./output_images/question1/shape1-question1_shift_-30_10.png');
figure();

% shape2-question1.mat
data = load("Assignment-5-Files\Question1\shape2-question1.mat");
scatter(data.x, data.y, "filled");
xlim([-200 400]);
ylim([-200 400]);
saveas(gcf,'./output_images/question1/shape2-question1_original.png');
figure();

[newX, newY] = CS4640_shiftTransformation(10,10,data);
scatter(newX, newY, "filled");
xlim([-200 400]);
ylim([-200 400]);
saveas(gcf,'./output_images/question1/shape2-question1_shift_10_10.png');
figure();

[newX, newY] = CS4640_shiftTransformation(20,-10,data);
scatter(newX, newY, "filled");
xlim([-200 400]);
ylim([-200 400]);
saveas(gcf,'./output_images/question1/shape2-question1_shift_20_-10.png');
figure();

[newX, newY] = CS4640_shiftTransformation(-10,-20,data);
scatter(newX, newY, "filled");
xlim([-200 400]);
ylim([-200 400]);
saveas(gcf,'./output_images/question1/shape2-question1_shift_-10_-20.png');
figure();

[newX, newY] = CS4640_shiftTransformation(-30,10,data);
scatter(newX, newY, "filled");
xlim([-200 400]);
ylim([-200 400]);
saveas(gcf,'./output_images/question1/shape2-question1_shift_-30_-10.png');
figure();

%(b)
% shape1-question1.mat
data = load("Assignment-5-Files\Question1\shape1-question1.mat");
[x_rotated, y_rotated] = CS4640_rotationTransformation(45,data);
scatter(x_rotated, y_rotated, "filled");
xlim([-200 400]);
ylim([-200 400]);
saveas(gcf,'./output_images/question1/shape1-question1_rotation_45.png');
figure();

[x_rotated, y_rotated] = CS4640_rotationTransformation(90,data);
scatter(x_rotated, y_rotated, "filled");
xlim([-200 400]);
ylim([-200 400]);
saveas(gcf,'./output_images/question1/shape1-question1_rotation_90.png');
figure();

[x_rotated, y_rotated] = CS4640_rotationTransformation(-45,data);
scatter(x_rotated, y_rotated, "filled");
xlim([-200 400]);
ylim([-200 400]);
saveas(gcf,'./output_images/question1/shape1-question1_rotation_-45.png');
figure();

[x_rotated, y_rotated] = CS4640_rotationTransformation(-90,data);
scatter(x_rotated, y_rotated, "filled");
xlim([-200 400]);
ylim([-200 400]);
saveas(gcf,'./output_images/question1/shape1-question1_rotation_-90.png');
figure();

% shape2-question1.mat

data = load("Assignment-5-Files\Question1\shape2-question1.mat");
[x_rotated, y_rotated] = CS4640_rotationTransformation(45,data);
scatter(x_rotated, y_rotated, "filled");
xlim([-200 400]);
ylim([-200 400]);
saveas(gcf,'./output_images/question1/shape2-question1_rotation_45.png');
figure();

[x_rotated, y_rotated] = CS4640_rotationTransformation(90,data);
scatter(x_rotated, y_rotated, "filled");
xlim([-200 400]);
ylim([-200 400]);
saveas(gcf,'./output_images/question1/shape2-question1_rotation_90.png');
figure();

[x_rotated, y_rotated] = CS4640_rotationTransformation(-45,data);
scatter(x_rotated, y_rotated, "filled");
xlim([-200 400]);
ylim([-200 400]);
saveas(gcf,'./output_images/question1/shape2-question1_rotation_-45.png');
figure();

[x_rotated, y_rotated] = CS4640_rotationTransformation(-90,data);
scatter(x_rotated, y_rotated, "filled");
xlim([-200 400]);
ylim([-200 400]);
saveas(gcf,'./output_images/question1/shape2-question1_rotation_-90.png');
figure();

%(c)
% shape1-question1.mat

data = load("Assignment-5-Files\Question1\shape1-question1.mat");
[x_rotated, y_rotated] = CS4640_scaleTransformation(0.25,data);
scatter(x_rotated, y_rotated, "filled");
xlim([-200 400]);
ylim([-200 400]);
saveas(gcf,'./output_images/question1/shape1-question1_scale_025.png');
figure();

[x_rotated, y_rotated] = CS4640_scaleTransformation(0.5,data);
scatter(x_rotated, y_rotated, "filled");
xlim([-200 400]);
ylim([-200 400]);
saveas(gcf,'./output_images/question1/shape1-question1_scale_050.png');
figure();

[x_rotated, y_rotated] = CS4640_scaleTransformation(2,data);
scatter(x_rotated, y_rotated, "filled");
xlim([-200 400]);
ylim([-200 400]);
saveas(gcf,'./output_images/question1/shape1-question1_scale_200.png');
figure();

scatter(x_rotated, y_rotated, "filled");
saveas(gcf,'./output_images/question1/shape1-question1_scale_200_fit.png');
figure();

[x_rotated, y_rotated] = CS4640_scaleTransformation(8,data);
scatter(x_rotated, y_rotated, "filled");
xlim([-200 400]);
ylim([-200 400]);
saveas(gcf,'./output_images/question1/shape1-question1_scale_800.png');
figure();

scatter(x_rotated, y_rotated, "filled");
saveas(gcf,'./output_images/question1/shape1-question1_scale_800_fit.png');
figure();

% shape2-question1.mat

data = load("Assignment-5-Files\Question1\shape2-question1.mat");
[x_rotated, y_rotated] = CS4640_scaleTransformation(0.25,data);
scatter(x_rotated, y_rotated, "filled");
xlim([-200 400]);
ylim([-200 400]);
saveas(gcf,'./output_images/question1/shape2-question1_scale_025.png');
figure();

[x_rotated, y_rotated] = CS4640_scaleTransformation(0.5,data);
scatter(x_rotated, y_rotated, "filled");
xlim([-200 400]);
ylim([-200 400]);
saveas(gcf,'./output_images/question1/shape2-question1_scale_050.png');
figure();

[x_rotated, y_rotated] = CS4640_scaleTransformation(2,data);
scatter(x_rotated, y_rotated, "filled");
xlim([-200 400]);
ylim([-200 400]);
saveas(gcf,'./output_images/question1/shape2-question1_scale_200.png');
figure();

scatter(x_rotated, y_rotated, "filled");
saveas(gcf,'./output_images/question1/shape2-question1_scale_200_fit.png');
figure();

[x_rotated, y_rotated] = CS4640_scaleTransformation(8,data);
scatter(x_rotated, y_rotated, "filled");
xlim([-200 400]);
ylim([-200 400]);
saveas(gcf,'./output_images/question1/shape2-question1_scale_800.png');
figure();

scatter(x_rotated, y_rotated, "filled");
saveas(gcf,'./output_images/question1/shape2-question1_scale_800_fit.png');

% Question #2: Sequence of transformations --------------------------

% (a)

% shape1-question1.mat
data = load("Assignment-5-Files\Question1\shape1-question1.mat");
CS4640_sequenceTransformations(-20, -10, 25, 1.25, data, "./output_images/question2/shape1-question1");

% shape2-question1.mat
data = load("Assignment-5-Files\Question1\shape2-question1.mat");
CS4640_sequenceTransformations(-20, -10, 25, 1.25, data, "./output_images/question2/shape2-question1");

% (b)

% they are the same. The transformations are linearly independent.

% Question #3: Procrustes alignment --------------------------

% (a)

referenceData = load("Assignment-5-Files\Question3\Question3-shape1a.mat");
movingData = load("Assignment-5-Files\Question3\Question3-shape1b.mat");
CS4640_procrustusAlignment(movingData, referenceData,"Question3-shape1");

% (b)

referenceData = load("Assignment-5-Files\Question3\Question3-shape2a.mat");
movingData = load("Assignment-5-Files\Question3\Question3-shape2b.mat");
CS4640_procrustusAlignment(movingData, referenceData, "Question4-shape1");

% Question #4: Projective transformation --------------------------

% (a)

% shape1-question1.mat
src = load("Assignment-5-Files\Question4\question4_src.mat");

% shape2-question1.mat
dst = load("Assignment-5-Files\Question4\question4_dst.mat");

alphaMatrix = CS4640_findProjectiveTransformation(src, dst);

% (b)

image1 = rgb2gray(imread('./Assignment-5-Files/Question4/image1.jpg'));

transformationMatrix = [alphaMatrix(1,1) alphaMatrix(2,1) alphaMatrix(3,1); alphaMatrix(4,1) alphaMatrix(5,1) alphaMatrix(6,1); alphaMatrix(7,1) alphaMatrix(8,1) 1];
%
% tform = projective2d(transformationMatrix);
% 
% w = images.geotrans.Warper(tform,imref2d(size(image1)));
% 
% warped = warp(w,image1);
%
%warped = imwarp(image1,tform);
%
% figure();
% imshow(image1); title('image1.jpg');
% figure();
% imshow(warped); title('warped image');

src.x = double(src.x);
src.y = double(src.y);
dst.x = double(dst.x);
dst.y = double(dst.y);

input_points = [src.x; src.y]';
base_points = [dst.x; dst.y]';

tform=cp2tform(input_points,base_points,'projective');  %Infer affine transformation from tie points
B=imtransform(image1,tform);
%B = imresize(B,[2173 2927]);
subplot(1,2,1);
imshow(image1);
hold on;
scatter(src.x,src.y,"x","red");
hold off;
subplot(1,2,2);
imshow(B);	%Display
hold on;
scatter(dst.x,dst.y,"x","red");
hold off;
imwrite(B,"./output_images/question4/image1_projective.png");
saveas(gcf,'./output_images/question4/image1_projective_with_points.png');

% (c)

% i
% ii

figure();

% Question #5:  Alignment for population statistics --------------------------

% (a)
% males
myFiles = dir(fullfile("Assignment-5-Files/Question5/Male",'*.mat'));

referenceData = load("Assignment-5-Files\Question5\Male\" + myFiles(1).name);

for k = 2:length(myFiles)
    baseFileName = myFiles(k).name;
    [folder, baseFileNameNoExt, extension] = fileparts(myFiles(k).name);
    movingData = load("Assignment-5-Files\Question5\Male\" + myFiles(k).name);
    aligned_data = CS4640_procrustusAlignmentMatlab(movingData, referenceData, "Male_" + baseFileNameNoExt);
    aligned_data = aligned_data';
    s.x = num2cell(aligned_data(1,:));
    s.y = num2cell(aligned_data(2,:));
    save("Question5alignmentData\Male\" + baseFileName,'s');
end

% average male face data
myFiles = dir(fullfile("Question5alignmentData\Male",'*.mat'));

s = load("Question5alignmentData\Male\" + myFiles(1).name).s;
s.x = cell2mat(s.x);
s.y = cell2mat(s.y);

for k = 2:length(myFiles)
    data = load("Question5alignmentData\Male\" + myFiles(k).name).s;
    data.x = cell2mat(data.x);
    data.y = cell2mat(data.y);
    s.x = s.x + data.x;
    s.y = s.y + data.y;
end

s.x = s.x / length(myFiles);
s.y = s.y / length(myFiles);

figure();
scatter(s.x,s.y, "x");title("Average male:");
hold on;
scatter(cell2mat(referenceData.x), cell2mat(referenceData.y), "o");
legend("Transformed shape","Target shape")
hold off;
saveas(gcf,"./output_images/question5/a/Male_average.png");

s.x = num2cell(aligned_data(1,:));
s.y = num2cell(aligned_data(2,:));
save("Question5alignmentData\average_male.mat",'s');

figure();

%females
myFiles = dir(fullfile("Assignment-5-Files/Question5/Female",'*.mat'));

for k = 1:length(myFiles)
    baseFileName = myFiles(k).name;
    [folder, baseFileNameNoExt, extension] = fileparts(myFiles(k).name);
    movingData = load("Assignment-5-Files\Question5\Female\" + myFiles(k).name);
    aligned_data = CS4640_procrustusAlignmentMatlab(movingData, referenceData, "Female_" + baseFileNameNoExt);
    aligned_data = aligned_data';
    s.x = num2cell(aligned_data(1,:));
    s.y = num2cell(aligned_data(2,:));
    save("Question5alignmentData\Female\" + baseFileName,'s');
end

% average female face data
myFiles = dir(fullfile("Question5alignmentData\Female",'*.mat'));

s = load("Question5alignmentData\Female\" + myFiles(1).name).s;
s.x = cell2mat(s.x);
s.y = cell2mat(s.y);

for k = 2:length(myFiles)
    data = load("Question5alignmentData\Female\" + myFiles(k).name).s;
    data.x = cell2mat(data.x);
    data.y = cell2mat(data.y);
    s.x = s.x + data.x;
    s.y = s.y + data.y;
end

s.x = s.x / length(myFiles);
s.y = s.y / length(myFiles);

figure();
scatter(s.x,s.y, "x");title("Average female:");
hold on;
scatter(cell2mat(referenceData.x), cell2mat(referenceData.y), "o");
legend("Transformed shape","Target shape")
hold off;
saveas(gcf,"./output_images/question5/a/Female_average.png");

s.x = num2cell(aligned_data(1,:));
s.y = num2cell(aligned_data(2,:));
save("Question5alignmentData\average_female.mat",'s');

% average of all face data
maleData = load("Question5alignmentData\average_male.mat").s;
femaleData = load("Question5alignmentData\average_female.mat").s;
s.x = (cell2mat(maleData.x) + cell2mat(femaleData.x))/2;
s.y = (cell2mat(maleData.y) + cell2mat(femaleData.y))/2;
save("Question5alignmentData\average_all.mat",'s');

figure();
scatter(s.x,s.y, "x");title("Average all:");
hold on;
scatter(cell2mat(referenceData.x), cell2mat(referenceData.y), "o");
legend("Transformed shape","Target shape")
hold off;
saveas(gcf,"./output_images/question5/a/All_average.png");

% (b)

figure();

% males
myFiles = dir(fullfile("Assignment-5-Files/Question5/Male/",'*.png'));

for k = 2:length(myFiles)
    baseFileName = myFiles(k).name;
    image1 = imread("./Assignment-5-Files/Question5/Male/" + baseFileName);
    [folder, baseFileNameNoExt, extension] = fileparts(myFiles(k).name);

    src = load("Assignment-5-Files\Question5\Male\" + baseFileNameNoExt + ".mat");
    src.x = cell2mat(src.x);
    src.y = cell2mat(src.y);
    dst = load("Question5alignmentData\Male\" + baseFileNameNoExt + ".mat").s;
    dst.x = cell2mat(dst.x);
    dst.y = cell2mat(dst.y);
    input_points = [src.x; src.y]';
    base_points = [dst.x; dst.y]';

    tform=cp2tform(input_points,base_points,'projective');  %Infer affine transformation from tie points
    B=imtransform(image1,tform);                             %Transform input
    B = imresize(B,[96 96]);
    subplot(1,2,1);
    imshow(image1);
    % hold on;
    % scatter(src.x,src.y,"x");
    % hold off;
    subplot(1,2,2);
    imshow(B);	%Display
    % hold on;
    % scatter(dst.x,dst.y,"x");
    % hold off;
    imwrite(B,"./output_images/question5/b/Male/Male_" + baseFileNameNoExt + "_transformed.png");
    saveas(gcf,"./output_images/question5/b/Comparisons/Male_" + baseFileNameNoExt + "_transformed.png");
end

% females
myFiles = dir(fullfile("Assignment-5-Files/Question5/Female/",'*.png'));

for k = 2:length(myFiles)
    baseFileName = myFiles(k).name;
    image1 = imread("./Assignment-5-Files/Question5/Female/" + baseFileName);
    [folder, baseFileNameNoExt, extension] = fileparts(myFiles(k).name);

    src = load("Assignment-5-Files\Question5\Female\" + baseFileNameNoExt + ".mat");
    src.x = cell2mat(src.x);
    src.y = cell2mat(src.y);
    dst = load("Question5alignmentData\Female\" + baseFileNameNoExt + ".mat").s;
    dst.x = cell2mat(dst.x);
    dst.y = cell2mat(dst.y);
    input_points = [src.x; src.y]';
    base_points = [dst.x; dst.y]';

    tform=cp2tform(input_points,base_points,'projective');  %Infer affine transformation from tie points
    B=imtransform(image1,tform);                             %Transform input
    B = imresize(B,[96 96]);
    subplot(1,2,1);
    imshow(image1);
    % hold on;
    % scatter(src.x,src.y,"x");
    % hold off;
    subplot(1,2,2);
    imshow(B);	%Display
    % hold on;
    % scatter(dst.x,dst.y,"x");
    % hold off;
    imwrite(B,"./output_images/question5/b/Female/Female_" + baseFileNameNoExt + "_transformed.png");
    saveas(gcf,"./output_images/question5/b/Comparisons/Female_" + baseFileNameNoExt + "_transformed.png");
end

figure();

% average before
% males
myFiles = dir(fullfile("Assignment-5-Files/Question5/Male/",'*.png'));
baseFileName = myFiles(1).name;
image1 = imread("./Assignment-5-Files/Question5/Male/" + baseFileName);
sumImage = double(image1); % Inialize to first image.

for k = 2:length(myFiles)
    baseFileName = myFiles(k).name;
    image1 = imread("./Assignment-5-Files/Question5/Male/" + baseFileName);
    sumImage = sumImage + double(image1);
end

maleMeanImage = sumImage / length(myFiles);

% females
myFiles = dir(fullfile("Assignment-5-Files/Question5/Female/",'*.png'));
baseFileName = myFiles(1).name;
image1 = imread("./Assignment-5-Files/Question5/Female/" + baseFileName);
sumImage = double(image1); % Inialize to first image.

for k = 2:length(myFiles)
    baseFileName = myFiles(k).name;
    image1 = imread("./Assignment-5-Files/Question5/Female/" + baseFileName);
    sumImage = sumImage + double(image1);
end

femaleMeanImage = sumImage / length(myFiles);

meanImage = (maleMeanImage + femaleMeanImage) / 2;

meanImage = uint8(255 * mat2gray(meanImage));

subplot(1,2,1);
title('Average face before alignment');
imshow(meanImage);

imwrite(meanImage,"./output_images/question5/b/before_after_alignment/before_alignment.png");

% average after

% males
myFiles = dir(fullfile("output_images/question5/b/Male/",'*.png'));
baseFileName = myFiles(1).name;
image1 = imread("./output_images/question5/b/Male/" + baseFileName);
sumImage = double(image1); % Inialize to first image.

for k = 2:length(myFiles)
    baseFileName = myFiles(k).name;
    image1 = imread("./output_images/question5/b/Male/" + baseFileName);
    sumImage = sumImage + double(image1);
end

maleMeanImage = sumImage / length(myFiles);


% females
myFiles = dir(fullfile("output_images/question5/b/Female/",'*.png'));
baseFileName = myFiles(1).name;
image1 = imread("./output_images/question5/b/Female/" + baseFileName);
sumImage = double(image1); % Inialize to first image.

for k = 2:length(myFiles)
    baseFileName = myFiles(k).name;
    image1 = imread("./output_images/question5/b/Female/" + baseFileName);
    sumImage = sumImage + double(image1);
end

femaleMeanImage = sumImage / length(myFiles);

meanImage = (maleMeanImage + femaleMeanImage) / 2;

meanImage = uint8(255 * mat2gray(meanImage));

subplot(1,2,2);
title('Average face after alignment');
imshow(meanImage);

imwrite(meanImage,"./output_images/question5/b/before_after_alignment/after_alignment.png");

% which one's better? To be honest, they both look about the same. The
% aligned is slightly better because things are lined up first which can be
% shown with the (my) right eye that has less distortion and within a more
% compact area.

% (c)

figure();

% males
myFiles = dir(fullfile("output_images/question5/b/Male/",'*.png'));
baseFileName = myFiles(1).name;
image1 = imread("./output_images/question5/b/Male/" + baseFileName);
sumImage = double(image1); % Inialize to first image.

for k = 2:length(myFiles)
    baseFileName = myFiles(k).name;
    image1 = imread("./output_images/question5/b/Male/" + baseFileName);
    sumImage = sumImage + double(image1);
end

meanImage = sumImage / length(myFiles);
meanImage = uint8(255 * mat2gray(meanImage));

imwrite(meanImage,"./output_images/question5/c/average_male_face.png");

subplot(1,2,1);
title('Average male face');
imshow(meanImage);

% females
myFiles = dir(fullfile("output_images/question5/b/Female/",'*.png'));
baseFileName = myFiles(1).name;
image1 = imread("./output_images/question5/b/Female/" + baseFileName);
sumImage = double(image1); % Inialize to first image.

for k = 2:length(myFiles)
    baseFileName = myFiles(k).name;
    image1 = imread("./output_images/question5/b/Female/" + baseFileName);
    sumImage = sumImage + double(image1);
end

meanImage = sumImage / length(myFiles);
meanImage = uint8(255 * mat2gray(meanImage));

imwrite(meanImage,"./output_images/question5/c/average_female_face.png");

subplot(1,2,2);
title('Average female face');
imshow(meanImage);

% The average face for men seems to be frowning more with a more general
% angry expression while the average face for female seem to be more
% neutral or happier with a faint smile and less angled eyebrows.

% (d) 

% male 3 and 58

image1 = imread("./Assignment-5-Files/Question5/Male/3.png");
image2 = imread("./Assignment-5-Files/Question5/Male/58.png");
a = 0;
for i = 1:11
    newImage = a*image1+(1-a)*image2;
    newImage = uint8(255 * mat2gray(newImage));
    imwrite(newImage,"./output_images/question5/d/Male/" + "male_merged_image_" + num2str(i) + ".png");
    subplot(2,6,i);imshow(newImage);
    a = a + 0.1;
end

figure();

% female 190 and 180

image1 = imread("./Assignment-5-Files/Question5/Female/190.png");
image2 = imread("./Assignment-5-Files/Question5/Female/180.png");
a = 0;
for i = 1:11
    newImage = a*image1+(1-a)*image2;
    newImage = uint8(255 * mat2gray(newImage));
    imwrite(newImage,"./output_images/question5/d/Female/" + "female_merged_image_" + num2str(i) + ".png");
    subplot(2,6,i);imshow(newImage);
    a = a + 0.1;
end

figure();

% (e)

% male 3 and 58
%3
image = imread("./Assignment-5-Files/Question5/Male/3.png");
src = load("Assignment-5-Files\Question5\Male\3.mat");
src.x = cell2mat(src.x);
src.y = cell2mat(src.y);
dst = load("Question5alignmentData\average_all.mat").s;
input_points = [src.x; src.y]';
base_points = [dst.x; dst.y]';

tform=cp2tform(input_points,base_points,'projective');  %Infer affine transformation from tie points
B=imtransform(image,tform);                             %Transform input
B = imresize(B,[96 96]);
subplot(1,2,1);
imshow(image);
% hold on;
% scatter(src.x,src.y,"x");
% hold off;
subplot(1,2,2);
imshow(B);	%Display
% hold on;
% scatter(dst.x,dst.y,"x");
% hold off;
imwrite(B,"./output_images/question5/e/Male/Male_averageall_3_transformed.png");

figure();

%58
image = imread("./Assignment-5-Files/Question5/Male/58.png");
src = load("Assignment-5-Files\Question5\Male\58.mat");
src.x = cell2mat(src.x);
src.y = cell2mat(src.y);
dst = load("Question5alignmentData\average_all.mat").s;
input_points = [src.x; src.y]';
base_points = [dst.x; dst.y]';

tform=cp2tform(input_points,base_points,'projective');  %Infer affine transformation from tie points
B=imtransform(image,tform);                             %Transform input
B = imresize(B,[96 96]);
subplot(1,2,1);
imshow(image);
% hold on;
% scatter(src.x,src.y,"x");
% hold off;
subplot(1,2,2);
imshow(B);	%Display
% hold on;
% scatter(dst.x,dst.y,"x");
% hold off;
imwrite(B,"./output_images/question5/e/Male/Male_averageall_58_transformed.png");

figure();

% female 190 and 180
%190
image = imread("./Assignment-5-Files/Question5/Female/190.png");
src = load("Assignment-5-Files\Question5\Female\190.mat");
src.x = cell2mat(src.x);
src.y = cell2mat(src.y);
dst = load("Question5alignmentData\average_all.mat").s;
input_points = [src.x; src.y]';
base_points = [dst.x; dst.y]';

tform=cp2tform(input_points,base_points,'projective');  %Infer affine transformation from tie points
B=imtransform(image,tform);                             %Transform input
B = imresize(B,[96 96]);
subplot(1,2,1);
imshow(image);
% hold on;
% scatter(src.x,src.y,"x");
% hold off;
subplot(1,2,2);
imshow(B);	%Display
% hold on;
% scatter(dst.x,dst.y,"x");
% hold off;
imwrite(B,"./output_images/question5/e/Female/Female_averageall_190_transformed.png");

figure();

%180
image = imread("./Assignment-5-Files/Question5/Female/180.png");
src = load("Assignment-5-Files\Question5\Female\180.mat");
src.x = cell2mat(src.x);
src.y = cell2mat(src.y);
dst = load("Question5alignmentData\average_all.mat").s;
input_points = [src.x; src.y]';
base_points = [dst.x; dst.y]';

tform=cp2tform(input_points,base_points,'projective');  %Infer affine transformation from tie points
B=imtransform(image,tform);                             %Transform input
B = imresize(B,[96 96]);
subplot(1,2,1);
imshow(image);
% hold on;
% scatter(src.x,src.y,"x");
% hold off;
subplot(1,2,2);
imshow(B);	%Display
% hold on;
% scatter(dst.x,dst.y,"x");
% hold off;
imwrite(B,"./output_images/question5/e/Female/Female_averageall_180_transformed.png");

% male 3 and 58

image1 = imread("./output_images/question5/e/Male/Male_averageall_3_transformed.png");
image2 = imread("./output_images/question5/e/Male/Male_averageall_58_transformed.png");
a = 0;
for i = 1:11
    newImage = a*image1+(1-a)*image2;
    newImage = uint8(255 * mat2gray(newImage));
    imwrite(newImage,"./output_images/question5/e/Male/Male_merged" + "transormed_male_merged_image_" + num2str(i) + ".png");
    subplot(2,6,i);imshow(newImage);
    a = a + 0.1;
end

figure();

% female 190 and 180

image1 = imread("./output_images/question5/e/Female/Female_averageall_190_transformed.png");
image2 = imread("./output_images/question5/e/Female/Female_averageall_180_transformed.png");
a = 0;
for i = 1:11
    newImage = a*image1+(1-a)*image2;
    newImage = uint8(255 * mat2gray(newImage));
    imwrite(newImage,"./output_images/question5/e/Female/Female_merged" + "transormed_male_merged_image_" + num2str(i) + ".png");
    subplot(2,6,i);imshow(newImage);
    a = a + 0.1;
end

figure();

% f

% I would imagine that the algorithm form e would be better, but the points
% didn't seem to line up for some reason. Therefore, out of the results
% that I got, d is the better algorithm.


% Question #6:  Extended projects --------------------------



% Functions ----------------------------------------------------------

function [newX, newY] = CS4640_shiftTransformation(xshift, yshift, data)

newX = data.x + xshift;
newY = data.y + yshift;

end

function [rotatedX, rotatedY] = CS4640_rotationTransformation(theta, data)

matrix = [data.x; data.y];

centerX = mean(data.x);
centerY = mean(data.y);
center = repmat([centerX; centerY], 1, length(data.x));

rotationMatrix = [cosd(theta) -sind(theta); sind(theta) cosd(theta)];

newData = rotationMatrix * (matrix - center) + center;

rotatedX = newData(1,:);
rotatedY = newData(2,:);

end

function [newX, newY] = CS4640_scaleTransformation(scale, data)

matrix = [data.x; data.y];

centerX = (min(data.x) + max(data.x))/2;
centerY = (min(data.y) + max(data.y))/2;
center = repmat([centerX; centerY], 1, length(data.x));

scaleMatrix = [scale 0; 0 scale];

newData = scaleMatrix * (matrix - center) + center;

newX = newData(1,:);
newY = newData(2,:);

end

function [] = CS4640_sequenceTransformations(xshift, yshift, theta, scale, data, filename)

% shift rotate scale
newdata1 = data;
[transformedX, transformedY] = CS4640_shiftTransformation(xshift, yshift, newdata1);
newdata1.x = transformedX;
newdata1.y = transformedY;
[rotatedX, rotatedY] = CS4640_rotationTransformation(theta, newdata1);
newdata1.x = rotatedX;
newdata1.y = rotatedY;
[scaledX, scaledY] = CS4640_scaleTransformation(scale, newdata1);
newdata1.x = scaledX;
newdata1.y = scaledY;
figure();
scatter(newdata1.x, newdata1.y, "filled");
% xlim([-200 400]);
% ylim([-200 400]);
saveas(gcf, filename + "_shift_rotate_scale.png");

% scale rotate shift
newdata2 = data;
[scaledX, scaledY] = CS4640_scaleTransformation(scale, newdata2);
newdata2.x = scaledX;
newdata2.y = scaledY;
[rotatedX, rotatedY] = CS4640_rotationTransformation(theta, newdata2);
newdata2.x = rotatedX;
newdata2.y = rotatedY;
[transformedX, transformedY] = CS4640_shiftTransformation(xshift, yshift, newdata2);
newdata2.x = transformedX;
newdata2.y = transformedY;
saveas(gcf, filename + "_scale_rotate_shift.png");
figure();
% xlim([-200 400]);
% ylim([-200 400]);
scatter(newdata2.x, newdata2.y, "filled");

end

function [] = CS4640_procrustusAlignment(movingData, referenceData, filename)

figure();
scatter(movingData.x, movingData.y, "filled");
hold on;
scatter(referenceData.x, referenceData.y, "filled");
hold off;

saveas(gcf, "./output_images/question3/" + filename + "_original.png");

newdata = movingData;

% translation

xshift = sum(referenceData.x)/length(referenceData.x) - sum(movingData.x)/length(movingData.x);
yshift = sum(referenceData.y)/length(referenceData.y) - sum(movingData.y)/length(movingData.y);
newdata.x = movingData.x + xshift;
newdata.y = movingData.y + yshift;

figure();
scatter(newdata.x, newdata.y, "filled");
hold on
scatter(referenceData.x, referenceData.y, "filled");
hold off

saveas(gcf, "./output_images/question3/" + filename + "_translation.png");

% scaling

X = [newdata.x; newdata.y];
Y = [referenceData.x; referenceData.y];

numerator = 0;

for i=1:length(referenceData.x)
    numerator = numerator + Y(:,i)' * X(:,i);
end

denominator = 0;

for i=1:length(referenceData.x)
    denominator = denominator + X(:,i)' * X(:,i);
end

s = numerator / denominator;
% S = s * I;
% X = S * X;
% newdata.x = X(1,:);
% newdata.y = X(2,:);

[scaledX, scaledY] = CS4640_scaleTransformation(s, newdata);
newdata.x = scaledX;
newdata.y = scaledY;

figure();
scatter(newdata.x, newdata.y, "filled");
hold on
scatter(referenceData.x, referenceData.y, "filled");
hold off

saveas(gcf, "./output_images/question3/" + filename + "_scaling.png");

% rotation
X = [newdata.x; newdata.y];
Y = [referenceData.x; referenceData.y];

XYt = X * Y';

[U,S,V] = svd(XYt);
R = V * U';
X = R * X;

newdata.x = X(1,:);
newdata.y = X(2,:);

figure();
scatter(newdata.x, newdata.y, "filled");
hold on
scatter(referenceData.x, referenceData.y, "filled");
hold off

saveas(gcf, "./output_images/question3/" + filename + "_rotation.png");

% matlab solution

X = [movingData.x; movingData.y]';
Y = [referenceData.x; referenceData.y]';

[d,Z,tr] = procrustes(Y,X,"reflection",false);

newPts = tr.c';

figure();
scatter(Z(:,1),Z(:,2), "x");
hold on;
scatter(referenceData.x, referenceData.y, "o");
hold off;

fprintf("The average error: %d\n", d);

saveas(gcf, "./output_images/question3/" + filename + "_matlab_sol.png");

end

function [Z] = CS4640_procrustusAlignmentMatlab(movingData, referenceData, baseFileName)

movingData.x = cell2mat(movingData.x);
movingData.y = cell2mat(movingData.y);

referenceData.x = cell2mat(referenceData.x);
referenceData.y = cell2mat(referenceData.y);

scatter(movingData.x, movingData.y, "x"); title("Before:");
hold on;
scatter(referenceData.x, referenceData.y, "o");
legend("Comparison shape","Target shape")
hold off;
saveas(gcf, "./output_images/question5/a/" + baseFileName + "_before_alignment.png");

X = [movingData.x; movingData.y]';
Y = [referenceData.x; referenceData.y]';

[d,Z,tr] = procrustes(Y,X);

scatter(Z(:,1),Z(:,2), "x"); title("After:");
hold on;
scatter(referenceData.x, referenceData.y, "o");
legend("Transformed shape","Target shape")
hold off;
saveas(gcf, "./output_images/question5/a/" + baseFileName + "_after_alignment.png");

end

function [alpha] = CS4640_findProjectiveTransformation(src, dst)

A = zeros(2 * length(src.x), 8,'double');

b = zeros(2 * length(src.x), 1,'double');

count = 1;

for i=1:length(src.x)
    A(count,1) = src.x(i);
    A(count,2) = src.y(i);
    A(count,3) = 1;
    A(count,4) = 0;
    A(count,5) = 0;
    A(count,6) = 0;
    A(count,7) = -dst.x(i)*src.x(i);
    A(count,8) = -dst.x(i)*src.y(i);
    b(count) = dst.x(i);

    A(count+1,1) = 0;
    A(count+1,2) = 0;
    A(count+1,3) = 0;
    A(count+1,4) = src.x(i);
    A(count+1,5) = src.y(i);
    A(count+1,6) = 1;
    A(count+1,7) = -dst.y(i)*src.x(i);
    A(count+1,8) = -dst.y(i)*src.y(i);
    b(count+1) = dst.y(i);

    count = count + 2;
end

alpha = inv(A'*A)*A' * b;

% test = A * alpha;

disp(alpha);

end
