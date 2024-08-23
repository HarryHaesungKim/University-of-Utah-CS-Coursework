import skimage
import numpy as np
import matplotlib.pyplot as plt
import scipy
from scipy.ndimage import gaussian_filter
from scipy import ndimage
from skimage.util import random_noise
import cv2


f = np.ones((64))
f = f/np.sum(f)
g = np.convolve(f,f)
g = g/np.sum(g)
h = np.convolve(g,g)
h = h/np.sum(h)
j = np.convolve(h,h)
j = h/np.sum(j)

fig, ax = plt.subplots(2,2)
ax[0][0].plot(f)
ax[0][1].plot(g)
ax[1][0].plot(h)
ax[1][1].plot(j)
plt.show()

