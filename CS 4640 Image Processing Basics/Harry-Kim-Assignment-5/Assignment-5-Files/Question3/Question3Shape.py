import numpy as np
import matplotlib
import matplotlib.pyplot as plt
import math
import cv2
from scipy.io import savemat
from skimage import transform
from scipy.ndimage import geometric_transform

import skimage.transform 

# Taken from SuperShapes Code in Shapeworks #
def super_formula_2D(m, n1, n2, n3, a, b, theta):
	r = abs((1 / a) * np.cos(m * theta / 4.0))**n2  +  abs((1 / b) * np.sin(m * theta / 4.0))**n3
	return r**(-1 / n1)

# Adapted from SuperShapes Code in Shapeworks to return 2D shapes rather than 3D #
def super_formula_2D_shape(m=0, n1=1, n2=1, n3=1, a=1, b=1, numPoints=400000):
	numPointsRoot = round(math.sqrt(numPoints))
	theta = np.linspace(-math.pi, math.pi, endpoint=True, num=numPointsRoot)
	theta= theta.flatten()
	r1 = super_formula_2D(m, n1, n2, n3, a, b, theta)
	x = 128 *(r1 * np.cos(theta) +1)
	y = 128 *(r1 * np.sin(theta) +1)
	return x,y

# Define Transformations
def get_rotation(angle):
    angle = np.radians(angle)
    return np.array([
        [np.cos(angle), -np.sin(angle), 0],
        [np.sin(angle),  np.cos(angle), 0],
        [0, 0, 1]
    ])
def get_translation(tx, ty):
    return np.array([
        [1, 0, tx],
        [0, 1, ty],
        [0, 0, 1]
    ])
def get_scale(s):
    return np.array([
        [s, 0, 0],
        [0, s, 0],
        [0, 0, 1]
    ])

if __name__ == "__main__":
    x,y=super_formula_2D_shape(m=9, n1=-0.47, n2=1.61, n3=0.87, a=1.0, b=1.0, numPoints=160000)
    fig, ax = plt.subplots()
    print(x.shape,y.shape)
    ax.scatter(x,y,label='Base Shape')
    mdic = {"x": x, "y": y}
    savemat("Question3-shape1a.mat", mdic)
    R1 = get_rotation(55)
    T1 = get_translation(-200, 200)
    S1 = get_scale(2.25)# Apply transformation x' = Ax
    coords = np.vstack((x,y,np.ones((len(x)))))
    print(coords.shape)
    coords_composite1 = R1 @ T1 @ S1 @ coords
    mdic = {"x": coords_composite1[0,:], "y": coords_composite1[1,:]}
    savemat("Question3-shape1b.mat", mdic)
    ax.scatter(coords_composite1[0,:],coords_composite1[1,:],label='moving points')
    ax.legend()
    fig.savefig("Question3a.png")
    plt.show()
    
    x,y=super_formula_2D_shape(m=15, n1=-0.47, n2=1.61, n3=0.87, a=1.0, b=1.0, numPoints=160000)
    fig, ax = plt.subplots()
    print(x.shape,y.shape)
    ax.scatter(x,y,label='Base Shape')
    mdic = {"x": x, "y": y}
    savemat("Question3-shape2a.mat", mdic)
    R1 = get_rotation(155)
    T1 = get_translation(200, 200)
    S1 = get_scale(0.25)# Apply transformation x' = Ax
    coords = np.vstack((x,y,np.ones((len(x)))))
    print(coords.shape)
    coords_composite1 = R1 @ T1 @ S1 @ coords
    mdic = {"x": coords_composite1[0,:], "y": coords_composite1[1,:]}
    savemat("Question3-shape2b.mat", mdic)
    ax.scatter(coords_composite1[0,:],coords_composite1[1,:],label='moving points')
    ax.legend()
    fig.savefig("Question3b.png")
    plt.show()

