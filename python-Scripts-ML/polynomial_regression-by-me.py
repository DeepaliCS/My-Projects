# Polynomial Regression

# Importing the libraries
import numpy as np
import matplotlib.pyplot as plt
import pandas as pd

# Importing the dataset
dataset = pd.read_csv('Position_Salaries.csv')
X = dataset.iloc[:, 1:2].values
y = dataset.iloc[:, 2].values

# Date is not going to be split into two because there are
# only 10 observations. Predictions might not make much sense
"""
# Splitting the dataset into the Training set and Test set
from sklearn.cross_validation import train_test_split
X_train, X_test, y_train, y_test = train_test_split(X, y, test_size = 0.2, random_state = 0)
"""

# Feature Scaling
"""from sklearn.preprocessing import StandardScaler
sc_X = StandardScaler()
X_train = sc_X.fit_transform(X_train)
X_test = sc_X.transform(X_test)
sc_y = StandardScaler()
y_train = sc_y.fit_transform(y_train)"""

# Fitting Linear Regression to the dataset
from sklearn.linear_model import LinearRegression
lin_reg = LinearRegression()
# Taking both x and y because were using the original dataset
# no train or test
lin_reg.fit(X, y)

# Fitting Polynomial Regression to the dataset
from sklearn.preprocessing import PolynomialFeatures
# degree default is 2
poly_reg = PolynomialFeatures(degree = 4)
X_poly = poly_reg.fit_transform(X)

## Review the X and X_poly, and understand what the columns
## represent
#- column 1 is for multiple linear regression
#- column 2 is for the polynomial regression

# New linear regression object
lin_reg2 = LinearRegression()
lin_reg2.fit(X_poly, y)
# This will regress the X_poly features created before
# poly regression object is made



# The lin_reg2 object can now be used to prove a bluff
# of the guy who says his salary is certain amount

# Visualising the Linear Regression results
# True observation points
plt.scatter(X, y, color = 'red')
plt.plot(X, lin_reg.predict(X), color = 'blue')
plt.title('Truth of Bluff (Linear Regression)')
plt.xlabel('Position Label')
plt.ylabel('Salary')
plt.show()
# blue line is linear regression prediction points
# red line are the true observations


# Visualising the Polynomial Regression results
plt.scatter(X, y, color = 'red')
plt.plot(X, lin_reg2.predict(poly_reg.fit_transform(X)), color = 'blue')
plt.title('Truth of Bluff (Polynomial Regression)')
plt.xlabel('Position Label')
plt.ylabel('Salary')
plt.show()


# blue line is linear regression prediction points
# red line are the true observations

# You can add a better degree to your polynomial regression
# - set degree to 3 instead of 2 and you might get closer 
# results
#1. Run: Fitting Polynomial Regression to the dataset 
# (With degree changed to 3)
#2. Then run the poly visuals again

# You can see that the one with degree =3 has better 
# predictions
# You can continue to increase the degree..

# But when you get much accurate predictions, you can 
# make line be smoother - look at the code: X_grid
X_grid = np.arange(min(X), max(X), 0.1)
X_grid = X_grid.reshape((len(X_grid), 1))
# Run the above, and then run the visuals again
# (Whats happening..)
# Is that the range function is being used to increment
# by 0.1 between each point. And the first line will return
# an vector, what we want is a matrix, so reshape function
# is used
# The 1 after the len stuff is just to process one column

# Prediction a new result with Linear Regression
# Check the prediction of 6.5 with the lin_reg object
lin_reg.predict([[6.5]])
# There are 2 square brackets because its expecting 
# an array in it, so you have to format it like this
# so python can understands that this is one column
# and one row

# Predicting a new result with Polynomial Regression
lin_reg2.predict(poly_reg.fit_transform([[6.5]]))
# The result of this means that the person who claimed
# his salary was a certain amount given his experience
# shows that he was telling the truth - as the predictor 
# says













