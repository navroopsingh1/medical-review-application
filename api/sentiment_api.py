from flask import Flask
from textblob import TextBlob
from flask import request, jsonify
app = Flask(__name__)

def analyze(string):
        analysis = TextBlob(string)
        return {'polarity' : analysis.sentiment.polarity}

@app.route("/hello")
def hello():
    return "Hello World!"

@app.route('/', methods=['POST']) #GET requests will be blocked
def json_example():
    req_data = request.get_json()
    text = req_data['text']
    return jsonify(analyze(text))

if __name__ == "__main__":
   app.run(debug=False, port=8080) #run app in debug mode on port 5000
