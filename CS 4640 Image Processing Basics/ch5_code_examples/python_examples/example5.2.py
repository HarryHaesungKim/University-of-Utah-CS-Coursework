import skimage
import numpy as np
import matplotlib.pyplot as plt
import sys
sys.path.insert(1, '../special_functions')
from special_functions import *

# Example python script for textbook :
#
#  Fundamentals of Digital Image Processing: A Practical Approach with Examples in Matlab
#  Chris J. Solomon and Toby P. Breckon, Wiley-Blackwell, 2010
#  ISBN: 0470844736, DOI:10.1002/9780470689776, http://www.fundipbook.com
#
A = skimage.io.imread('../image_files/cameraman.tif'); # read an image
FT = np.fft.fft2(A)
FT_centered = np.fft.fftshift(FT)


## Display both images and see the range of pixel values on the colorbar
fig, ax = plt.subplots(2,2)
im=ax[0][0].imshow(A,cmap='gray')
ax[0][0].set_title('Original Image')
plt.colorbar(im,ax=ax[0][0])
im=ax[0][1].imshow(np.log(1+np.abs(FT)),cmap='gray')
ax[0][1].set_title('Fourier Transform of image')
plt.colorbar(im,ax=ax[0][1])
im=ax[1][0].imshow(np.log(1+np.abs(FT_centered)),cmap='gray')
ax[1][0].set_title(' CenteredFourier Transform of Image')
plt.colorbar(im,ax=ax[1][0])
plt.show()



Im1 = np.abs(np.fft.ifft2(FT))
Im2 = np.abs(np.fft.ifft2(FT_centered))
## Display both images and see the range of pixel values on the colorbar
fig, ax = plt.subplots(2,2)
im=ax[0][0].imshow(A,cmap='gray')
ax[0][0].set_title('Original Image')
plt.colorbar(im,ax=ax[0][0])
im=ax[0][1].imshow(Im1,cmap='gray')
ax[0][1].set_title('inverse FFT')
plt.colorbar(im,ax=ax[0][1])
im=ax[1][0].imshow(Im2,cmap='gray')
ax[1][0].set_title('Inverse Transform of centered Fourier')
plt.colorbar(im,ax=ax[1][0])
plt.show()


## Filtering

row, col = A.shape
kernel_size=row
row = np.uint8(row/2)
col = np.uint8(col/2)

X, Y = np.meshgrid(np.linspace(-1*row+1, row, kernel_size),np.linspace(-1*col+1, col, kernel_size))
sigma=32
arg = (X**2+Y**2)/sigma**2
frqFilt = np.exp(-arg)
print(X[120:136,120:136])

Im1 = np.abs(np.fft.ifft2(np.multiply(frqFilt,FT)))
Im2 = np.abs(np.fft.ifft2(np.multiply(frqFilt,FT_centered)))


fig, ax = plt.subplots(2,2)
im=ax[0][0].imshow(frqFilt,cmap='gray')
ax[0][0].set_title('Frequency Filter')
plt.colorbar(im,ax=ax[0][0])
im=ax[0][1].imshow(Im1,cmap='gray')
ax[0][1].set_title('inverse FFT not centered')
plt.colorbar(im,ax=ax[0][1])
im=ax[1][0].imshow(Im2,cmap='gray')
ax[1][0].set_title('Inverse FFT centered')
plt.colorbar(im,ax=ax[1][0])
plt.show()


