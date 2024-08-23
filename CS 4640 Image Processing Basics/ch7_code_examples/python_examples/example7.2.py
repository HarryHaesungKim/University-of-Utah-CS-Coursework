import skimage
import numpy as np
import matplotlib.pyplot as plt
import cv2
# Example python script for textbook :
#
#  Fundamentals of Digital Image Processing: A Practical Approach with Examples in Matlab
#  Chris J. Solomon and Toby P. Breckon, Wiley-Blackwell, 2010
#  ISBN: 0470844736, DOI:10.1002/9780470689776, http://www.fundipbook.com
#


img = cv2.imread("../image_files/Projective-Image.jpeg")
gray = cv2.cvtColor(img, cv2.COLOR_BGR2RGB)
pts1 = np.float32([[580,260],[452,141],[152,300],[230,467]])
pts2 = np.float32([[600,300],[600,0],[0,0],[0,300]])

M = cv2.getPerspectiveTransform(pts1,pts2)
dst = cv2.warpPerspective(img,M,(600,300))


fig, ax = plt.subplots(1,2)
ax[0].imshow(img)
ax[0].scatter(pts1[:,0],pts1[:,1],marker='*',color='yellow')
ax[0].set_title('Image')
ax[1].imshow(dst)
ax[1].set_title('Transformed image')

plt.show()
