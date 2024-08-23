import json
import numpy as np
import matplotlib.pyplot as plt
from skimage import io
import cv2
from skimage import data
from skimage import transform
from scipy.io import savemat
from skimage.color import rgb2gray
with open("mosaic_params.json") as f:
    data = json.load(f)

corrs = data["Correspondences"]

corra = np.asarray(corrs[0][0][1])
corrb = np.asarray(corrs[0][1][1])   

src = {'x':corra[:,0], 'y':corra[:,1]}
dst = {'x':corrb[:,0], 'y':corrb[:,1]}

savemat("question4_src.mat", src)
savemat("question4_dst.mat", src)


print(corra.T)
print(corrb.T)

im1 = rgb2gray(io.imread('image1.jpg'))
im2 = rgb2gray(io.imread('image2.jpg'))

tform3 = transform.ProjectiveTransform()
tform3.estimate(corrb, corra)

warped = transform.warp(im1, tform3, output_shape=(2500, 2500),order=0)

fig, ax = plt.subplots(1,3)
fig.set_size_inches(15,12)
ax[0].imshow(im1)
ax[0].scatter(corra[:,0],corra[:,1],c=range(25,25*len(corra[:,0])+1,25))
ax[1].imshow(im2)
ax[1].scatter(corrb[:,0],corrb[:,1],c=range(25,25*len(corra[:,0])+1,25))
ax[2].imshow(warped)
#ax[2].scatter(corrb[:,0],corrb[:,1],c=range(25,25*len(corra[:,0])+1,25))

plt.show()
fig.savefig('Question4.png')


