import pandas as pd
from sklearn.model_selection import RepeatedStratifiedKFold
from sklearn.neighbors import KNeighborsClassifier
from sklearn.model_selection import GridSearchCV
from sklearn.model_selection import train_test_split
from sklearn.neighbors import KNeighborsClassifier
import sklearn.metrics as metrics
from sklearn.metrics import confusion_matrix
import pickle
import seaborn as sns

df = pd.read_csv('Weather_Data.csv')
df_select = df[['MinTemp', 'MaxTemp','Humidity9am','Humidity3pm','Temp9am','Temp3pm','RainTomorrow']]
df_select['RainTomorrow'].replace(['No', 'Yes'], [0,1], inplace=True)

features = df_select.drop(columns='RainTomorrow', axis=1)

Y = df_select['RainTomorrow']

x_train, x_test, y_train, y_test = train_test_split(features, Y, test_size=0.2, random_state=10)

knn= KNeighborsClassifier()

n_neighbors = list(range(15,25))

p=[1,2]

weights = ['uniform', 'distance']
metric = ['euclidean', 'manhattan', 'minkowski']

hyperparameters = dict(n_neighbors=n_neighbors, p=p,weights=weights,metric=metric)

cv = RepeatedStratifiedKFold(n_splits=10, n_repeats=3, random_state=1)

grid_search = GridSearchCV(estimator=knn, param_grid=hyperparameters, n_jobs=-1, cv=cv, scoring='f1',error_score=0)

model = grid_search.fit(x_train,y_train)

#đánh giá model
pred = model.predict(x_test)
pred_train = model.predict(x_train)

train_accuracy = metrics.accuracy_score(y_train, pred_train)
test_accuracy = metrics.accuracy_score(y_test, pred)
train_f1_score = metrics.f1_score(y_train, pred_train)
test_f1_score = metrics.f1_score(y_test, pred)
sns.heatmap(confusion_matrix(y_test,pred))

#Tạo weight
best_model = grid_search.best_estimator_
best_model.fit(features, Y)  # Huấn luyện lại trên toàn bộ dữ liệu

weights_path = "lab6.pkl"
with open(weights_path, "wb") as f:
    pickle.dump(best_model, f)