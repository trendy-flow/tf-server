import os, json
import requests
from pymongo import MongoClient
from datetime import datetime


class NaverAPI:
    def __init__(self):
        DIR_PATH = os.path.dirname(__file__)
        SECRET_PATH = os.path.join(DIR_PATH, 'secret.json')
        self.secrets = json.loads(open(SECRET_PATH).read())
        self.client_id = self.secrets['client_id']
        self.client_secret = self.secrets['client_secret']
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
        return res

    # 최근 블로그 글 부터 마지막으로 검색된 날짜(last_date)까지의 결과를 반환한다.
    def get_result(self, url, data, last_date):
        result = dict()
        result['data'] = list()
        result['total'] = 0

        # 중복 체크
        duplicate_link = set()
        
        # 최대 1000개, 1 ~ 1000
        for _ in range(10):
            res = requests.get(url, headers=self.headers, params=data)
            if res.status_code == 200:
                res = res.json()
                if not res['items']:
                    return result
                else:
                    for item in res['items']:
                        if item['link'] not in duplicate_link:
                            item['page'] = data['start']
                            item['keyword'] = data['query']
                            result['data'].append(item)
                            duplicate_link.add(item['link'])
                            
            data['start'] += 100
        
        result['total'] = len(result['data'])
        return result


if __name__ == "__main__":
    n = NaverAPI()
    n.search_blog('한이음', '20200701')
    pass
