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

## There is no cpselect function in python

img = cv2.imread("../image_files/cameraman.tif")
rows, cols, ch = img.shape
  
pts1 = np.float32([[0, 0],
                   [256, 0], 
                   [0, 256]
		   ])
  
pts2 = np.float32([[64, 0],
                   [256, 128],
                   [0,128] 
                   ])
# open cv only allows three points but if you want to use more points for transformation
# you will need to use a different function. 
M = cv2.getAffineTransform(pts1, pts2)
dst = cv2.warpAffine(img, M, (cols, rows))
  
plt.subplot(121)
plt.imshow(img)
plt.title('Input')
  
plt.subplot(122)
plt.imshow(dst)
plt.title('Output')
plt.show()
