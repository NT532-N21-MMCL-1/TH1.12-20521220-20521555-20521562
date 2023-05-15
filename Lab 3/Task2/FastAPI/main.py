from fastapi import FastAPI
from pydantic import BaseModel
from fastapi.encoders import jsonable_encoder

app = FastAPI()

class sensorValue(BaseModel):
    light: float
    distance: int

class Body(BaseModel):
    data: sensorValue

class Response(BaseModel):
    ledsNum: int

@app.post('/')
async def root(body:Body):
    num = 0
    lux = body.data.light
    distance = body.data.distance

    if 0<distance<10:
        if 0<lux<80: num = 3
        elif 80<lux<160: num = 2
        elif 160<lux<240: num = 1
        else: ledsNum = 0

    response = Response(ledsNum = num)

    response_jsonTostr = jsonable_encoder(response)
    return response_jsonTostr