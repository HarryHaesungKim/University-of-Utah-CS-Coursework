import numpy as np
from skimage.io import imread,imsave
import matplotlib.pyplot as plt
import pandas as pd
from scipy.io import savemat

b = pd.read_csv('training.csv')
for i in range(200):
    #row = b[i,:]
    fig, ax = plt.subplots()
    image = np.asarray(np.uint8(b.iloc[i,30].split(' ')))
    print(image.shape)
    image = np.reshape(image,(96,96))
    x = b.iloc[i,0:30:2]
    y = b.iloc[i,1:30:2]
    ax.imshow(image)
    ax.scatter(x,y,c='black')
    fig.savefig("marks"+str(i)+".png")
    imsave(str(i)+'.png',image)
    mdic = {'x':x,'y':y}
    savemat(str(i)+".mat", mdic)    
print(b.keys())

