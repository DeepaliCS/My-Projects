# Multiple Linear Regression

# Importing the libraries
import numpy as np
import matplotlib.pyplot as plt
import pandas as pd

# Importing the dataset
dataset = pd.read_csv('50_Startups.csv')
X = dataset.iloc[:, :-1].values
y = dataset.iloc[:, 4].values

# Encoding categorical data
# Encoding the Independent Variable
from sklearn.preprocessing import LabelEncoder, OneHotEncoder
labelencoder_X = LabelEncoder()
X[:, 3] = labelencoder_X.fit_transform(X[:, 3])
# 3 categories or 3rd column? for onehotencoder
onehotencoder = OneHotEncoder(categorical_features = [3])
X = onehotencoder.fit_transform(X).toarray()

# Avoiding the Dummy Variable Trap
X = X[:, 1:]
# Removing california variable becauase you can know that from the rest
# of the dummy columns (true,true,false or false,false,true etc.)

# Splitting the dataset into the Training set and Test set
from sklearn.model_selection import train_test_split
X_train, X_test, y_train, y_test = train_test_split(X, y, test_size = 0.2, random_state = 0)

# Fitting Multiple Linear Regression to the Training set
from sklearn.linear_model import LinearRegression
regressor = LinearRegression()
regressor.fit(X_train, y_train)

# Predicting the Test set results
y_pred = regressor.predict(X_test)

# Building the optimal model using BACKWARD ELIMINATION -
# Finding the best team of independant variables that have a good 
# effect on the dependant variable
import statsmodels.formula.api as sm
X = np.append(arr = np.ones((50, 1)). astype(int), values =  X, axis = 1)
X_opt = X[:, [0, 1, 2, 3, 4, 5]]

# Fit the model with all possible predictors
regressor_OLS = sm.OLS(endog = y, exog = X_opt).fit()

# Info about regressor
regressor_OLS.summary()

# Repeating the step, but now with a better team (one v less that
# a high P value)
X_opt = X[:, [0, 1, 3, 4, 5]]
regressor_OLS = sm.OLS(endog = y, exog = X_opt).fit()
regressor_OLS.summary()

X_opt = X[:, [0, 3, 4, 5]]
regressor_OLS = sm.OLS(endog = y, exog = X_opt).fit()
regressor_OLS.summary()

X_opt = X[:, [0, 3, 5]]
regressor_OLS = sm.OLS(endog = y, exog = X_opt).fit()
regressor_OLS.summary()


# Technically, index 5 and 3 are shown to be good predictors given their
# p-values, but to be thorough in this elimination process, you may
# remove the variable that is slightly higher and keep the one that
# has most significance of the independant variable
X_opt = X[:, [0, 3, 5]]
regressor_OLS = sm.OLS(endog = y, exog = X_opt).fit()
regressor_OLS.summary()

# So were going to remove the 5th v
X_opt = X[:, [0, 3]]
regressor_OLS = sm.OLS(endog = y, exog = X_opt).fit()
regressor_OLS.summary()






