# SVR - Support Vector Regression

# Importing the libraries
import numpy as np
import matplotlib.pyplot as plt
import pandas as pd

# Importing the dataset
dataset = pd.read_csv('Position_Salaries.csv')
X = dataset.iloc[:, 1:2].values
y = dataset.iloc[:, 2].values

# Splitting the dataset into the Training set and Test set
"""from sklearn.cross_validation import train_test_split
X_train, X_test, y_train, y_test = train_test_split(X, y, test_size = 0.2, random_state = 0)"""

# Feature Scaling
from sklearn.preprocessing import StandardScaler
sc_X = StandardScaler()
sc_y = StandardScaler()

# You have to reshape it becauase it thinks its 1d
# (and while it is) you have to say that it is but take
# it as a 2d array
X = sc_X.fit_transform(X.reshape(-1, 1))
y = sc_y.fit_transform(y.reshape(-1, 1))

# Fitting the SVR to the dataset 
from sklearn.svm import SVR
regressor = SVR(kernel = 'rbf')
regressor.fit(X, y)

# Predicting a new result
y_pred = sc_y.inverse_transform(regressor.predict(sc_X.transform(np.array([[6.5]]))))
# using the sc_x object because we used feature scaling
# have to using numpy arrays because thats only how it will work
# apply inverse tranform method to get original salary
# rather than scaled (sc_y becauase thats the overall result)

# Visualising the SVR results
## Test prediction graph with this code instead of resolution one - because 
## you may need to reshape stuff
plt.scatter(X, y, color = 'red')
plt.plot(X, regressor.predict(X), color = 'blue')
plt.title('Truth or Bluff (SVR)')
plt.xlabel('Position level')
plt.ylabel('Salary')
plt.show()

## Looking at the graph, this model is great at getting close to the point
## however, it ignores the outliers - which is a good thing
## This is probably because the boundary lines excluded it and kept the
## line of best fit (hyper place) close to the ones that are within the
## boundary line

