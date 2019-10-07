# Multiple Linear Regression

# Importing the dataset
dataset = read.csv('50_Startups.csv')

# Encoding categorical data
dataset$State= factor(dataset$State,
                         levels = c('New York', 'California', 'Florida'),
                         labels = c(1, 2, 3))

# Splitting the dataset into the Training set and Test set
# install.packages('caTools')
library(caTools)
set.seed(123)
# Remember to change the dependent variable
split = sample.split(dataset$Profit, SplitRatio = 0.8)
training_set = subset(dataset, split == TRUE)
test_set = subset(dataset, split == FALSE)

# Fitting Multiple Linear Regression to the Training set

# = lm(formula = Profit ~ R.D.Spend + Administration + Marketing.Spend + State)
# The dot just means the rest of the variables (like above)
regressor = lm(formula = Profit ~ .,
               data = training_set)

# R created the dummy variables automatically as state2 and state3 - 
# see the summary stats (summary(regressor)). 
# And it also removed the dummy trap by only keeping 2 variables rather than 3

# Predicting the Test results

# Using all the independant variables
y_pred = predict(regressor, newdata = test_set)
# You can compare the values of the y_pred from the original test set

# Building the optimal model using Backward Elimination
# Find a better team of iv that will better predict the dv
regressor = lm(formula = Profit ~ R.D.Spend + Administration + Marketing.Spend + State,
               data = dataset)
summary(regressor)

# Removing the state V because the it doesn't have any statistical significance
# with the regressor - both states removed (state2 and state3)
regressor = lm(formula = Profit ~ R.D.Spend + Administration + Marketing.Spend,
               data = dataset)
summary(regressor)

# Carrying on with the rest of the backward elimination
regressor = lm(formula = Profit ~ R.D.Spend + Marketing.Spend,
               data = dataset)
summary(regressor)

# R.D Spend is still highly statistically significant given the very small 
# p-value number and the 3 stars next to it. 
# Marketing can be removed, becuase it won't really make much of a difference
# expecially now that there's an independant V that is strongly significant
# Final Optimal iv Team
regressor = lm(formula = Profit ~ R.D.Spend,
               data = dataset)
summary(regressor)


