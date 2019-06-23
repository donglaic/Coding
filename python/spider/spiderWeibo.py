#!/usr/bin/python3

from urllib.parse import urlencode
import requests,time,json
from pyquery import PyQuery as pq 
import pymongo
import pdb

base_url = 'https://m.weibo.cn/api/container/getIndex?'

headers = {
        'Host': 'm.weibo.cn',
        'Referer': 'https://m.weibo.cn/u/2830678474',
        'User-Agent':'Mozilla/5.0(Macintosh;Intel Mac OS X 10_13_3) AppleWebKit/537.36(KHTML, like Gecko) Chrome/65.03325.162 Safari/537.36',
        'X-Request-With': 'XMLHttpRequest',
        }

def get_page(page):
    params = {
            'type': 'uid',
            'value': '2830678474',
            'containerid': '1076032830678474',
            'page': page
            }
    url = base_url + urlencode(params)
    try:
        response = requests.get(url,headers=headers)
        if response.status_code == 200:
            return response.json()
    except request.ConnectionError as e:
            print('Error',e.args)

def parse_page(json):
    if json:
        items = json.get('data').get('cards')
        for item in items:
            #pdb.set_trace()
            item = item.get('mblog')
            if item:
                weibo = {}
                weibo['id'] = item.get('id')
                weibo['text'] = pq(item.get('text')).text()
                weibo['attitudes'] = item.get('attitudes_count')
                weibo['comments'] = item.get('comments_count')
                weibo['reposts'] = item.get('reposts_count')
                yield weibo

def save_to_mongo(result):
    if collection.insert(result):
        print('Save to Mongo')

if __name__ == '__main__':
    client = pymongo.MongoClient(host='localhost',port=27017)
    db = client['weibo']
    collection = db['weibo']

    for page in range(1,11):
        json = get_page(page)
        results = parse_page(json)
        for result in results:
            print(result)
            save_to_mongo(result)
        time.sleep(1)
