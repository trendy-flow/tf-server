import os, json

class NaverAPI:

    def __init__(self):
        DIR_PATH = os.path.dirname(__file__)
        SECRET_PATH = os.path.join(DIR_PATH, 'secret.json')
        self.secrets = json.loads(open(SECRET_PATH).read())
        self.client_id = self.secrets['client_id']
        self.client_secret = self.secrets['client_secret']
        pass

if __name__ == "__main__":
    n = NaverAPI()
    pass