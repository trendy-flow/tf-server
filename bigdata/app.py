from flask import Flask, jsonify, request
from datacollector.naver_api import NaverAPI
from flask_pymongo import PyMongo
import pandas as pd

app = Flask(__name__)
app.config['MONGO_URI'] = 'mongodb://localhost:27017/tfdb'
mongo = PyMongo(app)

naver_api = NaverAPI()

@app.route('/', methods=['POST'])
def collect_blog_data():
    keyword = request.form['keyword']
    last_date = request.form['last_date']

    res = naver_api.search_blog(keyword, last_date)

    blogs_collection = mongo.db.blogs

    # Check empty list
    if res['data']:
        blogs_collection.insert_many(res['data'])

    for item in res['data']:
        item['_id'] = str(item['_id'])

    return jsonify(res)
    