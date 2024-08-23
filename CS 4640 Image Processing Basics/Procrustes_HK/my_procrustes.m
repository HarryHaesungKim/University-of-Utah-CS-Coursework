% Question #3: Procrustes alignment --------------------------

% (a)

referenceData = load("Assignment-5-Files\Question3\Question3-shape1a.mat");
movingData = load("Assignment-5-Files\Question3\Question3-shape1b.mat");
CS4640_procrustusAlignment(movingData, referenceData);

% (b)

referenceData = load("Assignment-5-Files\Question3\Question3-shape2a.mat");
movingData = load("Assignment-5-Files\Question3\Question3-shape2b.mat");
CS4640_procrustusAlignment(movingData, referenceData);

% Functions ----------------------------------------------------------

function [newX, newY] = CS4640_shiftTransformation(xshift, yshift, data)

newX = data.x + xshift;
newY = data.y + yshift;

end

function [rotatedX, rotatedY] = CS4640_rotationTransformation(theta, data)

matrix = [data.x; data.y];

centerX = (min(data.x) + max(data.x))/2;
centerY = (min(data.y) + max(data.y))/2;
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


function [] = CS4640_procrustusAlignment(movingData, referenceData)

figure();
scatter(movingData.x, movingData.y, "filled");
hold on;
scatter(referenceData.x, referenceData.y, "filled");
hold off;

newdata = movingData;

% translation

xshift = sum(referenceData.x)/length(referenceData.x) - sum(movingData.x)/length(movingData.x)
yshift = sum(referenceData.y)/length(referenceData.y) - sum(movingData.y)/length(movingData.y)
[shiftedX, shiftedY] = CS4640_shiftTransformation(xshift, yshift, newdata);
newdata.x = shiftedX;
newdata.y = shiftedY;

% xshift = sum(movingData.x)/length(movingData.x) - sum(referenceData.x)/length(referenceData.x);
% yshift = sum(movingData.y)/length(movingData.y) - sum(referenceData.y)/length(referenceData.y);
% [shiftedX, shiftedY] = CS4640_shiftTransformation(-xshift, -yshift, newdata);
% newdata.x = shiftedX;
% newdata.y = shiftedY;

figure();
scatter(newdata.x, newdata.y, "filled");
hold on
scatter(referenceData.x, referenceData.y, "filled");
hold off
% xlim([-200 400]);
% ylim([-200 400]);

% scaling
% transposeRef = transpose(referenceData.x);
% transposeDat = transpose(newdata.x);
% scale = sum(transposeRef(:).*newdata.x(:)) / sum(transposeDat(:).*newdata.x(:));
% [scaledX, scaledY] = CS4640_scaleTransformation(scale, newdata);
% newdata.x = scaledX;
% newdata.y = scaledY;

% transposeRef = transpose(referenceData.x);
% transposeDat = transpose(newdata.x);
% xscale = sum(transposeRef(:).*newdata.x(:)) / sum(transposeDat(:).*newdata.x(:));
% 
% transposeRef = transpose(referenceData.y);
% transposeDat = transpose(newdata.y);
% yscale = sum(transposeRef(:).*newdata.y(:)) / sum(transposeDat(:).*newdata.y(:));
% 
% matrix = [newdata.x; newdata.y];
% centerX = (min(newdata.x) + max(newdata.x))/2;
% centerY = (min(newdata.y) + max(newdata.y))/2;
% center = repmat([centerX; centerY], 1, length(newdata.x));
% scaleMatrix = [xscale 0; 0 yscale];
% newData = scaleMatrix * (matrix - center) + center;
% scaledX = newData(1,:);
% scaledY = newData(2,:);
% 
% newdata.x = scaledX;
% newdata.y = scaledY;

X = [newdata.x; newdata.y];
Y = [referenceData.x; referenceData.y];
I = eye(2);

numerator = [referenceData.x];

for i=1:length(numerator)
    currentTranspose = Y(:,i)';
    numerator(:,i) = currentTranspose * X(:,i);
end

denominator = [referenceData.x];

for i=1:length(denominator)
    currentTranspose = X(:,i)';
    denominator(:,i) = currentTranspose * X(:,i);
end

s = sum(numerator) / sum(denominator);
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
% xlim([-200 400]);
% ylim([-200 400]);

% rotation
X = [newdata.x; newdata.y];
Y = [referenceData.x; referenceData.y];

Yt = transpose(Y);
XYt = X * Yt;
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
% xlim([-200 400]);
% ylim([-200 400]);

end