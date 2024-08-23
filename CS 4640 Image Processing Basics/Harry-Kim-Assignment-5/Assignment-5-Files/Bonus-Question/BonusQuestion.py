import numpy as np
import matplotlib
import matplotlib.pyplot as plt
import math
import cv2
from scipy.io import savemat
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


if __name__ == "__main__":
    fig, ax = plt.subplots()
    K = {}
    for i in range(1,11):

        x,y=super_formula_2D_shape(m=9, n1=10/i, n2=20/i, n3=10/i, a=1, b=1, numPoints=160000)
        K['shape'+str(i)] = np.vstack((x,y))
        print(x.shape,y.shape)
        ax.scatter(x,y)
        fig.savefig("Bonus-Question"+str(i)+".png")
    savemat("Bonus-part-question.mat", K)
    print(K) 

