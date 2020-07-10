import os, json
import requests
from pymongo import MongoClient


class NaverAPI:

    def __init__(self):
        DIR_PATH = os.path.dirname(__file__)
        SECRET_PATH = os.path.join(DIR_PATH, 'secret.json')
        self.secrets = json.loads(open(SECRET_PATH).read())
        self.client_id = self.secrets['client_id']
        self.client_secret = self.secrets['client_secret']
        self.db_host = self.secrets['db_host']
        self.db_port = self.secrets['db_port']
        self.headers = {'X-Naver-Client-Id' : self.client_id, 'X-Naver-Client-Secret' : self.client_secret, 'Content-Type' : "application/json"}
        pass

    def search_blog(self):
        url = 'https://openapi.naver.com/v1/search/blog.json'
        data = dict()
        data['query'] = '방탄소년단'
        res = requests.get(url, headers=self.headers, params=data).json()
        db = MongoClient(self.db_host, self.db_port)
        blog_db = db['blogdb']
        post = blog_db['post']
        post.insert_many(res['items'])

if __name__ == "__main__":
    n = NaverAPI()
    n.search_blog()
    pass