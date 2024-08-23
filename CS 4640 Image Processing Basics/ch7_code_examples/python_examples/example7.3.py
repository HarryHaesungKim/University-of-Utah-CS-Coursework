import skimage
import numpy as np
import matplotlib.pyplot as plt
import scipy
import cv2
# Example python script for textbook :
#
#  Fundamentals of Digital Image Processing: A Practical Approach with Examples in Matlab
#  Chris J. Solomon and Toby P. Breckon, Wiley-Blackwell, 2010
#  ISBN: 0470844736, DOI:10.1002/9780470689776, http://www.fundipbook.com
#

# Below Implementation taken from
# https://stackoverflow.com/questions/20924085/python-conversion-between-coordinates
#

def cart2pol(x, y):
    rho = np.sqrt(x**2 + y**2)
    phi = np.arctan2(y, x)
    return(rho, phi)

def pol2cart(rho, phi):
    x = rho * np.cos(phi)
    y = rho * np.sin(phi)
    return(x, y)

img = cv2.imread("../image_files/trui.jpg",0)
row, col = img.shape

X, Y = np.meshgrid(range(0,row),range(0,col))

imid = np.uint8(col/2)
XT = X.ravel()-imid
YT = Y.ravel()-imid

r, theta = cart2pol(XT,YT)
a = 0.0005
s = r + a* np.power(r,3) 

ut, vt = pol2cart(s,theta)
ut = np.asarray(ut.reshape(row,col) + imid)
vt = np.asarray(vt.reshape(row,col) + imid)
distorted = scipy.ndimage.map_coordinates(img, [vt,ut],mode='grid-wrap')


XT = X-imid
YT = Y-imid

r, theta = cart2pol(XT,YT)
a = -0.000015
s = r + a* np.power(r,3)

ut, vt = pol2cart(s,theta)
ut = ut + imid
vt = vt + imid
distorted2 = scipy.ndimage.map_coordinates(img, [vt,ut],mode='grid-wrap')

fig, ax = plt.subplots(1,3)
ax[0].imshow(img)
ax[0].set_title('Image')
ax[1].imshow(distorted)
ax[1].set_title('Barrel distortion image')
ax[2].imshow(distorted2)
ax[2].set_title('Pincushion Distortion image')

plt.show()
