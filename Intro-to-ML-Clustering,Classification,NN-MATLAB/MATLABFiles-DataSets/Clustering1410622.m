%1410622 Deepali Kerai 
iris = csvread('iris.csv');
iris_real = csvread('iris_real.csv');

%KMeans
disp('KMeans:')
%2 Clusters
km2cluster = kmeans(iris, 2);
gscatter(iris(:,1), iris(:,2),km2cluster)
wk2cluster = WK(km2cluster', iris_real')


%3 Clusters
km2cluster = kmeans(iris, 3);
gscatter(iris(:,1), iris(:,2),km2cluster)
wkappa_3cluster = WK(km2cluster', iris_real')

%4 Clusters
km4cluster= kmeans(iris, 4);
gscatter(iris(:,1), iris(:,2),km4cluster)
wkappa_4cluster = WK(km4cluster', iris_real')

%5 Clusters
km5cluster = kmeans(iris, 5);
gscatter(iris(:,1), iris(:,2),km5cluster)
wkappa_5cluster = WK(km5cluster', iris_real')

%6 Clusters
km6cluster = kmeans(iris, 6);
gscatter(iris(:,1), iris(:,2),km6cluster)
wkappa_6cluster = WK(km6cluster', iris_real')

%Hierarchical
disp('Hierarchical:')
iris_distances = pdist(iris); %Gets Euclidean Distances

%Average
irav = linkage(iris_distances, 'average');
iraverage = cluster(irav, 'maxclust', 3);
wkaverage = WK(iris_real', iraverage')
dendrogram(irav)
gscatter(iris(:,1), iris(:,2),iraverage)

%Single
irsin = linkage(iris_distances, 'single');
irsingle = cluster(irsin, 'maxclust', 3);
wksingle = WK(iris_real', irsingle')
dendrogram(irsin)
gscatter(iris(:,1), iris(:,2),irsingle)

%Complete
ircom = linkage(iris_distances, 'complete');
ircomplete = cluster(ircom, 'maxclust', 3);
wkcomplete = WK(iris_real', ircomplete')
dendrogram(ircom)
gscatter(iris(:,1), iris(:,2),ircomplete)

%Real
gscatter(iris(:,1), iris(:,2),iris_real)

