# Decision Tree Regression

# Importing the dataset
dataset = read.csv('Position_Salaries.csv')
dataset = dataset[2:3]

# Splitting the dataset into the Training set and Test set
# # install.packages('caTools')
# library(caTools)
# set.seed(123)
# split = sample.split(dataset$Salary, SplitRatio = 2/3)
# training_set = subset(dataset, split == TRUE)
# test_set = subset(dataset, split == FALSE)

# Feature Scaling
# training_set = scale`(training_set)
# test_set = scale(test_set)

# Fitting the Decision Tree Model to the dataset
#install.packages('rpart')
library(rpart)
regressor = rpart(formula = Salary ~.,
                  data = dataset,
                  control = rpart.control(minsplit = 1))

# Predicting a new result
y_pred = predict(regressor, data.frame(Level = 6.5))

# Visualising the Decision Tree Model results
# install.packages('ggplot2')
#library(ggplot2)
#ggplot() +
#  geom_point(aes(x = dataset$Level, y = dataset$Salary),
#             colour = 'red') +
#  geom_line(aes(x = dataset$Level, y = predict(regressor, newdata = dataset)),
#            colour = 'blue') +
#  ggtitle('Truth or Bluff (Decision Tree Model)') +
#  xlab('Level') +
#  ylab('Salary')


# Visualising the Decision Tree Model results (for higher resolution and smoother curve)
# install.packages('ggplot2')
library(ggplot2)
x_grid = seq(min(dataset$Level), max(dataset$Level), 0.01)
ggplot() +
  geom_point(aes(x = dataset$Level, y = dataset$Salary),
             colour = 'red') +
  geom_line(aes(x = x_grid, y = predict(regressor, newdata = data.frame(Level = x_grid))),
            colour = 'blue') +
  ggtitle('Truth or Bluff (Decision Tree Model)') +
  xlab('Level') +
  ylab('Salary')


# There would be a flat line because there is no split - 
# you need a split in the decision tre (nothing to do with
# feature scaling btw)

# Add a parameter in the regression equation that will set a 
# condition on the split

# Then you need to add a higher resolution - because
# this is a non-continous problem

# You need a straight line going upwards (not 
# slanted at all), to better represent the 
# non-continuity - so change from 0.1 to 0.01

# more notes on python

# Next step is a series of decision trees - 
# random forest..



