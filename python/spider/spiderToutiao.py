#!/usr/bin/python3

from urllib.parse import urlencode
import requests,time,json,os
from pyquery import PyQuery as pq 
import pymongo
import pdb
from multiprocessing.pool import Pool
from hashlib import md5

headers = {
        'Host': 'www.toutiao.com',
        'Referer': 'https://m.weibo.cn/u/2830678474https://www.toutiao.com/search/?keyword=%E8%A1%97%E6%8B%8D',
        'User-Agent':'Mozilla/5.0(Macintosh;Intel Mac OS X 10_13_3) AppleWebKit/537.36(KHTML, like Gecko) Chrome/65.03325.162 Safari/537.36',
        'X-Request-With': 'XMLHttpRequest',
        }

def get_page(offset):
    url = 'https://www.toutiao.com/api/search/content/?aid=24&app_name=web_search&offset={offset}&format=json&keyword={keyword}&autoload=true&count=20&en_qc=1&cur_tab=1&from=search_tab&pd=synthesis&timestamp={ts}'\
          .format(offset=offset,keyword='街拍',ts=int(time.time()*1000))
    try:
        response = requests.get(url,headers=headers)
        if response.status_code == 200:
            return response.json()
    except request.ConnectionError as e:
            print('Error',e.args)

def get_images(json):
    if json.get('data'):
        for item in json.get('data'):
            title = item.get('title')
            images = item.get('image_list')
            if images:
                #pdb.set_trace()
                for image in images:
                    yield {
                            'image': image.get('url'),
                            'title': title
                            }
    
def save_image(item):
    if not os.path.exists(item.get('title')):
        os.mkdir(item.get('title'))
    try:
        response = requests.get(item.get('image'))
        if response.status_code == 200:
            file_path = '{0}/{1}.{2}'.format(item.get('title'),md5(response.content).hexdigest(),'jpg')
            if not os.path.exists(file_path):
                with open(file_path,'wb') as f:
                    f.write(response.content)
                print(file_path,'downloaded')
            else:
                print('Already Downloaded',file_path)
    except requests.ConnectionError:
        print('Failed to Save Image')

def main(offset):
    json = get_page(offset)
    for item in get_images(json):
        print(item)
        save_image(item)

GROUP_START = 1
GROUP_END = 100

if __name__ == '__main__':
    pool = Pool()
    groups = ([x*20 for x in range(GROUP_START,GROUP_END+1)])
    pool.map(main,groups)
    pool.close()
    pool.join()
    
