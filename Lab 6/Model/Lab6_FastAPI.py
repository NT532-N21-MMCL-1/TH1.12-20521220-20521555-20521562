from fastapi import FastAPI
from pydantic import BaseModel
import pandas as pd
import pickle

app = FastAPI()

# Tạo model dữ liệu đầu vào
class InputData(BaseModel):
    MinTemp: float
    MaxTemp: float
    Humidity9am: float
    Humidity3pm: float
    Temp9am: float
    Temp3pm: float

# Đường dẫn đến file weight (.pkl)
weight_file_path = "lab6.pkl"

# Tải file weight và tạo đối tượng mô hình
with open(weight_file_path, 'rb') as f:
    model = pickle.load(f)

@app.post("/predict")
def predict(data: InputData):
    # Chuyển dữ liệu đầu vào thành DataFrame
    input_data = pd.DataFrame([data.dict()])

    # Dự đoán trên dữ liệu mới
    predictions = model.predict(input_data)

    return {"predictions": predictions.tolist()}

