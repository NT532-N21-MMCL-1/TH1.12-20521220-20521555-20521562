import paho.mqtt.client as mqtt

# Thiết lập kết nối với MQTT Broker
client = mqtt.Client()
client.connect("172.31.9.10", 1883)

# Hàm xử lý sự kiện khi nhận được tin nhắn từ MQTT Broker
def on_message(client, userdata, message):
    if message.topic == "mmcl/nhom12/dht/value":
        # Lấy giá trị nhiệt độ và độ ẩm từ tin nhắn
        values = message.payload.decode().split(";")
        temperature = float(values[0])
        humidity = float(values[1])
        # In ra giá trị nhiệt độ và độ ẩm từ tin nhắn
        print(temperature," ", humidity)
        # Kiểm tra giá trị nhiệt độ và độ ẩm và gửi tin nhắn nếu đạt điều kiện
        if (temperature < 25.0 or temperature > 27.0) or (humidity < 40.0 or humidity > 70.0):
            client.publish("mmcl/nhom12/dht/detected", "Temperature or humidity is abnormal!")
            # Xác nhận đã gửi thành công
            print("sent")


# Đăng ký hàm xử lý sự kiện nhận tin nhắn
client.on_message = on_message

# Theo dõi topic "mmcl/nhom12/dht/value"
client.subscribe("mmcl/nhom12/dht/value")

# Bắt đầu vòng lặp chạy chương trình
client.loop_forever()
