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

    # 블로그 포스트 키워드 데이터 수집 함수
    def search_blog(self, query, last_date):
        url = 'https://openapi.naver.com/v1/search/blog.json'
        data = dict()
        data['query'] = query
        data['sort'] = 'date'
        data['display'] = 100
        data['start'] = 1
        res = self.get_result(url, data, last_date)
        db = MongoClient(self.db_host, self.db_port)
        blog_db = db['tfdb']
        post = blog_db['blog']
        post.insert_many(res)

    # 최근 블로그 글 부터 마지막으로 검색된 날짜(last_date)까지의 결과를 반환한다.
    def get_result(self, url, data, last_date):
        item_res = list()
        while True:
            res = requests.get(url, headers=self.headers, params=data).json()
            for item in res['items']:
                if item['postdate'] <= last_date:
                    return item_res
                else:
                    item_res.append(item)
            data['start'] += 1
        return item_res

if __name__ == "__main__":
    n = NaverAPI()
    n.search_blog('한이음', '20200101')
    pass