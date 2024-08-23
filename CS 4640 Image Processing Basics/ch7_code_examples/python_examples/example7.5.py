import skimage
import numpy as np
import matplotlib.pyplot as plt
import cv2
from skimage.transform import estimate_transform
from skimage.transform import warp
import scipy.io as sio


# Example python script for textbook :
#
#  Fundamentals of Digital Image Processing: A Practical Approach with Examples in Matlab
#  Chris J. Solomon and Toby P. Breckon, Wiley-Blackwell, 2010
#  ISBN: 0470844736, DOI:10.1002/9780470689776, http://www.fundipbook.com
#


img1 = cv2.imread("../image_files/18.png")
img2 = cv2.imread("../image_files/190.png")
## Control points
mat_contents1 = sio.loadmat("../image_files/18.mat")
mat_contents2 = sio.loadmat("../image_files/190.mat")


src_points = np.asarray(np.vstack((mat_contents1['x'],mat_contents1['y'])),dtype=np.float32)
dst_points = np.asarray(np.vstack((mat_contents2['x'],mat_contents2['y'])),dtype=np.float32)

tform = estimate_transform('projective',dst_points.T,src_points.T)
dst = warp(img1, inverse_map=tform)
dstpoints_transformed = np.uint8(warp(src_points, inverse_map=tform))

plt.subplot(131)
plt.imshow(img1)
plt.scatter(src_points[0,:],src_points[1,:])
plt.title('Input')

plt.subplot(132)
plt.imshow(img2)
plt.scatter(dst_points[0,:],dst_points[1,:])
plt.title('Input')
  
plt.subplot(133)
plt.imshow(dst)
plt.title('Output')
plt.show()
