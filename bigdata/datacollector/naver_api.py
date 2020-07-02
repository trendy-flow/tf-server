import os, json, requests

class NaverAPI:

    def __init__(self):
        DIR_PATH = os.path.dirname(__file__)
        SECRET_PATH = os.path.join(DIR_PATH, 'secret.json')
        self.secrets = json.loads(open(SECRET_PATH).read())
        self.client_id = self.secrets['client_id']
        self.client_secret = self.secrets['client_secret']
        self.headers = {'X-Naver-Client-Id' : self.client_id, 'X-Naver-Client-Secret' : self.client_secret, 'Content-Type' : "application/json"}
        pass

    def search_blog(self):
        url = 'https://openapi.naver.com/v1/search/blog.json'
        data = dict()
        data['query'] = '방탄소년단'
        res = requests.get(url, headers=self.headers, params=data).json()
        print(res)


if __name__ == "__main__":
    n = NaverAPI()
    n.search_blog()
    pass