import skimage
import numpy as np
import matplotlib.pyplot as plt
from scipy.spatial import procrustes
import scipy.io as sio
# Example python script for textbook :
#
#  Fundamentals of Digital Image Processing: A Practical Approach with Examples in Matlab
#  Chris J. Solomon and Toby P. Breckon, Wiley-Blackwell, 2010
#  ISBN: 0470844736, DOI:10.1002/9780470689776, http://www.fundipbook.com
#

a = np.array([[1, 3], [1, 2], [1, 1], [2, 1]], 'd')

b = np.array([[4, -2], [4, -4], [4, -6], [2, -6]], 'd')

mtx1, mtx2, disparity = procrustes(a, b)

plt.scatter(a[:,0],a[:,1],marker='X',label='base points')
plt.scatter(b[:,0],b[:,1],marker='v',label='input points')
plt.scatter(mtx1[:,0],mtx1[:,1],marker='o',label='standardized version of base points')
plt.scatter(mtx2[:,0],mtx2[:,1],marker='*',label='transformed points',c='black')
plt.legend()
plt.show()


## Example from matlab

mat_contents = sio.loadmat('procrustes_star.mat')
base_points = mat_contents['base_points']
input_points = mat_contents['input_points']

mtx1, mtx2, disparity = procrustes(base_points,input_points)

#print(mtx1, mtx2)
#mtx2 = mtx2*np.max(base_points)
fig,ax = plt.subplots(1,2)
ax[0].scatter(base_points[:,0],base_points[:,1],marker='X',label='base points')
ax[0].scatter(input_points[:,0],input_points[:,1],marker='v',label='input points')
ax[0].legend()
ax[1].scatter(mtx1[:,0],mtx1[:,1],marker='o',label='standardized version of base points')
ax[1].scatter(mtx2[:,0],mtx2[:,1],marker='*',label='transformed input points',c='black')
ax[1].legend()

plt.show()
