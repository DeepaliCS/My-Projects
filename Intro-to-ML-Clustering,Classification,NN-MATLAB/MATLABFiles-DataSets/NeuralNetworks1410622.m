%1410622 - Deepali Kerai

% p = [[1;1] [1;-1] [-1;1] [-1;-1]];
% t = [1 0 0 0];
% myperceptron = train(myperceptron, p, t);
% 
% p = [[1;1] [1;-1] [-1;1] [-1;-1]];
% t = [0 1 1 0];
% myperceptron = train(myperceptron, p, t);
% 
% sim(myperceptron,p)

% Reading the files and setting the input and outputs
winedata = csvread('winedata2.csv',2)
wineoutputs = winedata(:,1);
wineinputs = winedata(:,2:14);

% Transposing them 
p = [wineinputs'];
t = [wineoutputs'];

% Creates network 2 hidden layers
myffnn = newff(p, t, 2);

% Setting (iteration) parameters
myffnn.trainParam.epochs = 300;
myffnn.trainParam.goal = 1e-5;

% Training the inputs and targets
myffnn = train(myffnn, p, t);
sim(myffnn,p)

 % Making sure output is greater than one,
 % Generating weight thresholds
output = sim(myffnn,p)>0.5

% Calculating the accuracy

accuracy = output == t;
accuracy = mean(accuracy)

%______________________________

% Selecting variables again
q3 = winedata(:,2:3);

% Setting test and train varaibles
winetainop=wineoutputs(1:80);
winetrain=q3(1:80,:);
winetestop=wineoutputs(81:129);
winetest=q3(81:129,:);

% Transposing it
ptrain = [winetrain'];
ttrain = [winetainop'];

ptest = [winetest'];
ttest = [winetestop'];

% Creates network 2 hidden layers
myffnn = newff(ptrain, ttrain, 2);

% Setting (iteration) parameters
myffnn.trainParam.epochs = 300;
myffnn.trainParam.goal = 1e-5;

% Training the inputs and targets
myffnn = train(myffnn, ptrain, ttrain);
sim(myffnn,ptrain)

sim(myffnn,ptrain)>0.5
sim(myffnn, ptest)

 % Making sure output is greater than one,
    % Generating weight thresholds
sim(myffnn, ptest)>0.5

% Setting the linear classifier
outputs = classify(winetest, winetrain, winetainop, 'linear');

% Calculating the accuracy
accuracy = winetestop == outputs;
accuracy=mean(accuracy)