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
FA = np.fft.fft2(A)                                    # Take FFT 
FA = np.fft.fftshift(FA)                               # Center fft

PSF = gaussian_kernel_f(A.shape[0],sigma=6)            # Sample PSF

OTF = np.fft.fft2(PSF)                                 # Calculate correspoonding OTF
OTF = np.fft.fftshift(OTF)                             # Centering the OTF    
Afilt = np.fft.ifft2(np.multiply(OTF,FA))              # Filerting the image using OTF
Afilt = np.fft.fftshift(Afilt)                         # Center alignment after inverse fft


## Display both images and see the range of pixel values on the colorbar
fig, ax = plt.subplots(2,2)
im=ax[0][0].imshow(A,cmap='gray')
ax[0][0].set_title('Original Image')
plt.colorbar(im,ax=ax[0][0])
im=ax[0][1].imshow(np.log(1+PSF),cmap='gray')
ax[0][1].set_title('Gaussian PSF')
plt.colorbar(im,ax=ax[0][1])
im=ax[1][0].imshow(np.log(1+np.abs(OTF)),cmap='gray')
ax[1][0].set_title('Gaussian Frequency Domain')
plt.colorbar(im,ax=ax[1][0])
im=ax[1][1].imshow(np.abs(Afilt),cmap='gray')
ax[1][1].set_title('FilteredImage')
plt.colorbar(im,ax=ax[1][1])
plt.show()


## Finding phase of the transform

PSF = gaussian_kernel_f(A.shape[0],sigma=6)               # PSF function
OTF = np.fft.fft2(PSF)
OTF = np.fft.fftshift(OTF)                                # OTF shifted

rlow  = np.uint8(A.shape[0]/2)-3
rhigh = np.uint8(A.shape[0]/2)+3
clow  = np.uint8(A.shape[1]/2)-3
chigh = np.uint8(A.shape[1]/2)+3

FPhase = np.angle(OTF)                                    # Extracting Phase of the image

FPhase[rlow:rhigh,clow:chigh] = FPhase[rlow:rhigh,clow:chigh] + 0*np.pi* np.random.random() # Add random component to selected phase

OTF = np.abs(OTF)*np.exp((0+1j)*FPhase)                        # Recombine phase and modulus
Afilt = np.fft.ifft2(np.multiply(OTF,FA))                 # Filtered image
Afilt = np.fft.fftshift(Afilt)

psf_new = np.abs((np.fft.ifft2(OTF)))             # FFT shift

## Display both images and see the range of pixel values on the colorbar
fig, ax = plt.subplots(2,2)
im=ax[0][0].imshow(A,cmap='gray')
ax[0][0].set_title('Original Image')
plt.colorbar(im,ax=ax[0][0])
im=ax[0][1].imshow(np.log(1+psf_new),cmap='gray')
ax[0][1].set_title('PSF New')
plt.colorbar(im,ax=ax[0][1])
im=ax[1][0].imshow(np.log(1+np.abs(OTF)),cmap='gray')
ax[1][0].set_title('OTF')
plt.colorbar(im,ax=ax[1][0])
im=ax[1][1].imshow(np.abs(Afilt),cmap='gray')
ax[1][1].set_title('FilteredImage')
plt.colorbar(im,ax=ax[1][1])
plt.show()



## Finding phase of the transform

PSF = motion_blur_kernel_approximation(256)               # PSF function
OTF = np.fft.fft2(PSF)
OTF = np.fft.fftshift(OTF)                                # OTF shifted

Afilt = np.fft.ifft2(np.multiply(OTF,FA))                 # Filtered image
Afilt = np.fft.fftshift(Afilt)

## Display both images and see the range of pixel values on the colorbar
fig, ax = plt.subplots(2,2)
im=ax[0][0].imshow(A,cmap='gray')
ax[0][0].set_title('Original Image')
plt.colorbar(im,ax=ax[0][0])
im=ax[0][1].imshow(np.log(1+PSF),cmap='gray')
ax[0][1].set_title('PSF New')
plt.colorbar(im,ax=ax[0][1])
im=ax[1][0].imshow(np.log(1+np.abs(OTF)),cmap='gray')
ax[1][0].set_title('OTF')
plt.colorbar(im,ax=ax[1][0])
im=ax[1][1].imshow(np.abs(Afilt),cmap='gray')
ax[1][1].set_title('FilteredImage')
plt.colorbar(im,ax=ax[1][1])
plt.show()





