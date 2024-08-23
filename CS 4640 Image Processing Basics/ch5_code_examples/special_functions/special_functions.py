import numpy as np
import cv2
def motion_blur_kernel_approximation(shape,size=15):
    ## Reference https://subscription.packtpub.com/book/application-development/9781785283932/2/ch02lvl1sec21/motion-blur
    # generating the kernel
    kernel_motion_blur = np.zeros((size, size))
    kernel_motion_blur[int((size-1)/2), :] = np.ones(size)
    kernel_motion_blur = kernel_motion_blur / size

    full_kernel = np.zeros((shape,shape))
    shape_2 = int(shape/2)-1
    size_2  = int(size/2)
    full_kernel[shape_2-size_2-1:shape_2+size_2,shape_2-size_2-1:shape_2+size_2] = kernel_motion_blur 
    return full_kernel

def gaussian_kernel(kernel_size,sigma=1, muu=0):

    x, y = np.meshgrid(np.linspace(-2*sigma, 2*sigma, kernel_size),
                       np.linspace(-2*sigma, 2*sigma, kernel_size))
    dst = np.sqrt(x**2+y**2)
 
    # lower normal part of gaussian
    normal = 1/(2.0 * np.pi * sigma**2)
 
    # Calculating Gaussian filter
    gauss = np.exp(-((dst-muu)**2 / (2.0 * sigma**2))) * normal
    return gauss

def gaussian_kernel_f(kernel_size,sigma=1, muu=0):
    temp = np.uint8(kernel_size/2)
    x, y = np.meshgrid(np.linspace(-1*temp+1, temp, kernel_size),
                       np.linspace(-1*temp+1, temp, kernel_size))
    dst = np.sqrt(x**2+y**2)
 
    # lower normal part of gaussian
    normal = 1/(2.0 * np.pi * sigma**2)
 
    # Calculating Gaussian filter
    gauss = np.exp(-((dst-muu)**2 / (2.0 * sigma**2))) * normal
    return gauss

def createCircleFilter(Shape, radius):
    center_coordinates = (np.uint8(Shape[0]/2),np.uint8(Shape[1]/2))
     
    # Radius of circle
    radius = radius
      
    # Line thickness of -1 px
    thickness = -1
    image = np.zeros(Shape)  
    # Draw a circle of red color of thickness -1 px
    image = cv2.circle(image, center_coordinates, radius, 1, thickness)
    image = image/np.sum(image)
    return image
