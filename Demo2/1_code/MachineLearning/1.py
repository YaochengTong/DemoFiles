
from sklearn.linear_model import LinearRegression
import pandas as pd

full_data = pd.read_csv("ml1000student.csv")

model = LinearRegression()
model.fit(full_data[['RUID', 'Month', 'Day', 'Hour', 'Minutes']], full_data["FeedBack"])
print model.predict([[152007117, 12, 18, 12, 0]])
