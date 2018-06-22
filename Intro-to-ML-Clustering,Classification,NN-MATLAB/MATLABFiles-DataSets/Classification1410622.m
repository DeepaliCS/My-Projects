%1410622 - Deepali Kerai
irisvalues = csvread('iris.csv');
irisclass = csvread('iris_real.csv');
    
% Randomising the classifiers to create an unbiased trail
    % Means that the results will never be the same, because the randperm 
    %function make is run at a different time than the last iteration
irisclass= irisclass(randperm(150),:);

% Training 
irtrainc = irisclass(1:120);
irtrainv = irisvalues(1:120,:);

% Testing
irtestc = irisclass(121:150);
irtestv = irisvalues(121:150,:);

% Calculating accuracy
c1 = classify(irtestv, irtrainv, irtrainc, 'linear');
ac = irtestc == c1;
ac = mean(ac);
disp("Tree Accuracy: " +ac);

% Original Decision Tree
iristree = fitctree(irtrainv, irtrainc);
view(iristree, 'Mode', 'graph');
irtreev = predict(iristree, irtestv);
tacc = irtestc == irtreev;
tacc = mean(tacc)

% 1 Pruned Decision Tree
irtreep1 = prune(iristree, 'Level', 1);
view(irtreep1, 'Mode', 'graph');
irp1eval = predict(irtreep1, irtestv);
p1acc = irtestc == irp1eval;
p1acc = mean(p1acc)

% 2 Pruned Decision Tree
irtreep2 = prune(iristree, 'Level', 2);
view(irtreep2, 'Mode', 'graph');
irp2eval = predict(irtreep2, irtestv);
p2acc = irtestc == irp2eval;
p2acc = mean(p2acc)

% Scatterplot Variables 1 & 2
gscatter(irtestv(:,1), irtestv(:,2), irtreev)

% Scatterplot Variables 1 & 3
gscatter(irtestv(:,1), irtestv(:,3), irtreev)

% Scatterplot Variables 1 & 4
gscatter(irtestv(:,1), irtestv(:,4), irtreev)

% Scatterplot Variables 2 & 3
gscatter(irtestv(:,2), irtestv(:,3), irtreev)

% Scatterplot Variables 2 & 4
gscatter(irtestv(:,2), irtestv(:,4), irtreev)

% Scatterplot Variables 3 & 4
gscatter(irtestv(:,3), irtestv(:,4), irtreev)

% K- Nearest Neighbour
%k = 3
k = 3;
class = knnclassify(irtestv, irtrainv, irtrainc, k);
a3 = irtestc == class;
a3 = mean(a3)

%k = 2
k = 2;
class = knnclassify(irtestv, irtrainv, irtrainc, k);
a2 = irtestc == class;
a2 = mean(a2)

%k = 1
k = 1;
class = knnclassify(irtestv, irtrainv, irtrainc, k);
a1 = irtestc == class;
a1 = mean(a1)

%k = 4
k = 4;
class = knnclassify(irtestv, irtrainv, irtrainc, k);
a4 = irtestc == class;
a4 = mean(a4)

%k = 5
k = 5;
class = knnclassify(irtestv, irtrainv, irtrainc, k);
a5 = irtestc == class;
a5 = mean(a5)



