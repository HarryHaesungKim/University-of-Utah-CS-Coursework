% % For training data:
% myFolders = dir(fullfile("Fishes/Fish_Dataset/",'*'));
% 
% for h = 3:length(myFolders)
% 
%     % disp(myFolders(h).name);
% 
%     currentFish = myFolders(h).name;
% 
%     myFiles = dir(fullfile("Fishes/Fish_Dataset/" + currentFish + "/" + currentFish + "/",'*.png'));
%     
%     for k = 1:length(myFiles)
%     
%         inputImage = imread("Fishes/Fish_Dataset/" + currentFish + "/" + currentFish + "/" + myFiles(k).name);
% 
%         % fprintf("Folder: " + currentFish + ", Filename: " + myFiles(k).name + "\n");
% 
%         % Resize image to 50 by 50.
%         resized = imresize(inputImage, [50 50]);
%         imwrite(resized, "./Fishes_Resized/Fish_Dataset/" + currentFish + "/" + currentFish + "/RS_" + myFiles(k).name);
%     
%     end
% 
%     myFiles = dir(fullfile("Fishes/Fish_Dataset/" + currentFish + "/" + currentFish + "/",'*.jpg'));
%     
%     for k = 1:length(myFiles)
% 
%         [folder, baseFileNameNoExt, extension] = fileparts("Fishes/Fish_Dataset/" + currentFish + "/" + currentFish + "/" + myFiles(k).name);
%     
%         inputImage = imread("Fishes/Fish_Dataset/" + currentFish + "/" + currentFish + "/" + myFiles(k).name);
% 
%         % fprintf("Folder: " + currentFish + ", Filename: " + myFiles(k).name + "\n");
% 
%         % Resize image to 50 by 50.
%         resized = imresize(inputImage, [50 50]);
%         imwrite(resized, "./Fishes_Resized/Fish_Dataset/" + currentFish + "/" + currentFish + "/RS_" + baseFileNameNoExt + ".png");
%     
%     end
%     
% end
% 
% % For testing data:
% myFolders2 = dir(fullfile("Fishes/NA_Fish_Dataset/",'*'));
% 
% for h = 3:length(myFolders2)
% 
%     % disp(myFolders(h).name);
% 
%     currentFish = myFolders2(h).name;
% 
%     myFiles2 = dir(fullfile("Fishes/NA_Fish_Dataset/" + currentFish + "/",'*.png'));
% 
%     % For test data:
%     for k = 1:length(myFiles2)
%     
%         inputImage = imread("Fishes/NA_Fish_Dataset/" + currentFish + "/" + myFiles2(k).name);
% 
%         fprintf("Folder: " + currentFish + ", Filename: " + myFiles2(k).name + "\n");
% 
%         % Resize image to 50 by 50.
%         resized = imresize(inputImage, [50 50]);
%         imwrite(resized, "./Fishes_Resized/NA_Fish_Dataset/" + currentFish + "/RS_" + myFiles2(k).name);
%     
%     end
% 
%     myFiles2 = dir(fullfile("Fishes/NA_Fish_Dataset/" + currentFish + "/",'*.jpg'));
% 
%     % For test data:
%     for k = 1:length(myFiles2)
% 
%         [folder, baseFileNameNoExt, extension] = fileparts("Fishes/NA_Fish_Dataset/" + currentFish + "/" + myFiles2(k).name);
% 
%         inputImage = imread("Fishes/NA_Fish_Dataset/" + currentFish + "/" + myFiles2(k).name);
% 
%         fprintf("Folder: " + currentFish + ", Filename: " + myFiles2(k).name + "\n");
% 
%         % Resize image to 50 by 50.
%         resized = imresize(inputImage, [50 50]);
%         imwrite(resized, "./Fishes_Resized/NA_Fish_Dataset/" + currentFish + "/RS_" + baseFileNameNoExt + ".png");
%     
%     end
%     
% end

%----------------------------------------------------------------------

% Using an image as KNN, assign the class using the nearest neighbor in the intensity domain using 1, 3, 5 or 7 neighbors.

% Training the dataset (100 photos each).
myFolders = dir(fullfile("Fishes_Resized/Fish_Dataset/",'*'));
listSumValues = [];
correnspondingLabelTraining = [];

for h = 3:length(myFolders)

    currentFish = myFolders(h).name;
    myFiles = dir(fullfile("Fishes_Resized/Fish_Dataset/" + currentFish + "/" + currentFish + "/",'*.png'));
    
    % figure();
    for k = 1:length(myFiles)

        fprintf("Folder: " + currentFish + ", Filename: " + myFiles(k).name + "\n");

        inputImage = imread("Fishes_Resized/Fish_Dataset/" + currentFish + "/" + currentFish + "/" + myFiles(k).name);
        grayscale = rgb2gray(inputImage);
        sumVal = sum(grayscale(:));

        listSumValues(end+1) = sumVal;
        correnspondingLabelTraining(end+1) = h - 2;

    end
    
end

scatter(listSumValues, correnspondingLabelTraining)

% Using it for testing.

% Using an image as KNN, assign the class using the nearest neighbor in the intensity domain using 1, 3, 5 or 7 neighbors.

% X = [listSumValues correnspondingLabel];

listSumValues = listSumValues';

% Testing the dataset (50 photos each).
myFolders = dir(fullfile("Fishes_Resized/NA_Fish_Dataset/",'*'));
totalAccuracy = 0;

testlistSumValues = [];
correnspondingLabelTesting = [];

for h = 3:length(myFolders)

    % disp(myFolders(h).name);

    currentFish = myFolders(h).name;

    myFiles = dir(fullfile("Fishes_Resized/NA_Fish_Dataset/" + currentFish + "/",'*.png'));
    accuracySum = 0;
    
    % figure();
    for k = 1:length(myFiles)
    
        inputImage = imread("Fishes_Resized/NA_Fish_Dataset/" + currentFish + "/" + myFiles(k).name);
        grayscale = rgb2gray(inputImage);
        sumVal = sum(grayscale(:));

        testlistSumValues(end+1) = sumVal;
        correnspondingLabelTesting(end+1) = h - 2;

    end
    
end

testlistSumValues = testlistSumValues';

% N nearest neighbor

% Now we have listSumValues and its correnspondingLabel along with
% testlistSumValues to find it's nearest neighbors and calculate best
% fitting label. How do you input it to knnsearch??

% 5 nearest neighbors
Idx1a = knnsearch(listSumValues, testlistSumValues, 'K', 5);

% compute accuracy

nearestNeighborLabel = zeros(429, 5);

for i = 1: size(Idx1a, 1)
    for j = 1: size(Idx1a, 2)
        nearestNeighborLabel(i, j) = correnspondingLabelTraining(Idx1a(i, j));
    end
end

Idx1 = mode(nearestNeighborLabel, 2);

% Idx1 = Idx1';

fid = fopen( './output_folder/knn_results.txt', 'wt' );

% For Black Sea Sprat
correctSum = 0;
for i = 1:50
    % If the nearest neighbor is equal to what it really is
    if Idx1(i, 1) == correnspondingLabelTesting(i)
        correctSum = correctSum + 1;
    end
end
accuracy = (1/50) * correctSum;
fprintf(fid, "Accuracy of k = 5 nearest neighbor for Black Sea Sprat: %d\n", accuracy);

% For Gilt Head Bream
correctSum = 0;
for i = 51:100
    % If the nearest neighbor is equal to what it really is
    if Idx1(i, 1) == correnspondingLabelTesting(i)
        correctSum = correctSum + 1;
    end
end
accuracy = (1/50) * correctSum;
fprintf(fid, "Accuracy of k = 5 nearest neighbor for Gilt Head Bream: %d\n", accuracy);

% For Horse Mackerel
for i = 101:150
    % If the nearest neighbor is equal to what it really is
    if Idx1(i, 1) == correnspondingLabelTesting(i)
        correctSum = correctSum + 1;
    end
end
accuracy = (1/50) * correctSum;
fprintf(fid, "Accuracy of k = 5 nearest neighbor for Horse Mackerel: %d\n", accuracy);

% For Red Mullet
correctSum = 0;
for i = 151:200
    % If the nearest neighbor is equal to what it really is
    if Idx1(i, 1) == correnspondingLabelTesting(i)
        correctSum = correctSum + 1;
    end
end
accuracy = (1/50) * correctSum;
fprintf(fid, "Accuracy of k = 5 nearest neighbor for Red Mullet: %d\n", accuracy);

% For Red Sea Bream
correctSum = 0;
for i = 201:249
    % If the nearest neighbor is equal to what it really is
    if Idx1(i, 1) == correnspondingLabelTesting(i)
        correctSum = correctSum + 1;
    end
end
accuracy = (1/49) * correctSum;
fprintf(fid, "Accuracy of k = 5 nearest neighbor for Red Sea Bream: %d\n", accuracy);

% For Sea Bass
correctSum = 0;
for i = 250:299
    % If the nearest neighbor is equal to what it really is
    if Idx1(i, 1) == correnspondingLabelTesting(i)
        correctSum = correctSum + 1;
    end
end
accuracy = (1/50) * correctSum;
fprintf(fid, "Accuracy of k = 5 nearest neighbor for Sea Bass: %d\n", accuracy);

% For Shrimp
correctSum = 0;
for i = 300:349
    % If the nearest neighbor is equal to what it really is
    if Idx1(i, 1) == correnspondingLabelTesting(i)
        correctSum = correctSum + 1;
    end
end
accuracy = (1/50) * correctSum;
fprintf(fid, "Accuracy of k = 5 nearest neighbor for Shrimp: %d\n", accuracy);

% For Striped Red Mullet
correctSum = 0;
for i = 350:399
    % If the nearest neighbor is equal to what it really is
    if Idx1(i, 1) == correnspondingLabelTesting(i)
        correctSum = correctSum + 1;
    end
end
accuracy = (1/50) * correctSum;
fprintf(fid, "Accuracy of k = 5 nearest neighbor for Striped Red Mullet: %d\n", accuracy);

% For Trout
correctSum = 0;
for i = 400:429
    % If the nearest neighbor is equal to what it really is
    if Idx1(i, 1) == correnspondingLabelTesting(i)
        correctSum = correctSum + 1;
    end
end
accuracy = (1/30) * correctSum;
fprintf(fid, "Accuracy of k = 5 nearest neighbor for Trout: %d\n", accuracy);

fclose(fid);

% Now with the indices, find the corrensponding labels.
% M = mode( A ) returns the sample mode of A , which is the most frequently
% occurring value in A . When there are multiple values occurring equally
% frequently, mode returns the smallest of those values.

fprintf("Hallelugha");

% Now do the same thing except with histograms...



% Funcitons -------------------------------------------------------------

function [] = CS4640_KNN(inputImage, k, data)
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

grayscale = rgb2gray(inputImage);

% meanVal = mean(grayscale(:));

sumVal = sum(grayscale(:));

% Get k nearest neighbors


Idx = knnsearch(X,Y)

end
