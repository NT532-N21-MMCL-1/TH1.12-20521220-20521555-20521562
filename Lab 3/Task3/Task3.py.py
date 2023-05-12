import paho.mqtt.client as mqtt

# Thiết lập kết nối với MQTT Broker
client = mqtt.Client()
client.connect("172.31.9.10", 1883)
#Gửi tin nhắn tới mmcl/nhom12/check
client.publish("mmcl/nhom12/check", "Test")
print("sent")