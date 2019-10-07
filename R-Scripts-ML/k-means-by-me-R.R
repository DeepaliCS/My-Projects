# K-means Clustering

# Importing the dataset
dataset = read.csv('Mall_Customers.csv')
X = dataset[4:5]

# Using the elbow method to find the optimal number of clusters
set.seed(6)

# fitting X dataset with k means and wcss
wcss <- vector()
for(i in 1:10) wcss[i] <- sum(kmeans(X,i)$withinss)# looping from 1 to 10

plot(1:10, 
     wcss, 
     type = 'b', 
     main = paste('clusters of clients', 
     xlab = "Number of clusers", 
     ylab = "WCSS")
                 )
# Optimal number of clusters is 5 - check elbow point

# Applying k-means to the mall dataset
set.seed(29)
kmeans <- kmeans(X,5,iter.max = 300, nstart = 10)

# Visualising the clusters
library(cluster)
clusplot(X,
         kmeans$cluster,
         lines=0,
         shade = TRUE,
         labels=2, 
         plotchar = FALSE,
         span=TRUE,
         main = paste('Clusters of clients'),
         xlab = 'Annual Income',
         ylab = 'Spending Score')



