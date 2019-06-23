#!/usr/bin/python3

import requests,re,time,json
from pyquery import PyQuery as pq 


def parse_one_page(url):
    doc = pq(url=url)
    
    indexs = [i.text() for i in doc('.board-index').items()]
    images = [i.attr('data-src') for i in doc('.board-img').items()]
    titles = [i.text() for i in doc('.name a').items()]
    actors = [i.text() for i in doc('.star').items()]
    times = [i.text() for i in doc('.releasetime').items()]
    score_ints = [i.text() for i in doc('.integer').items()]
    score_points = [i.text() for i in doc('.fraction').items()]

    for i in range(10):
        yield {
                'index': indexs[i],
                'image': images[i],
                'title': titles[i],
                'actor': actors[i].strip()[3:],
                'time':  times[i].strip()[5:],
                'score': score_ints[i] + score_points[i]
                }

def write_to_file(content):
    with open('result.txt','a',encoding='utf-8') as f:
        f.write(json.dumps(content,ensure_ascii=False) + '\n')

def main(offset):        
    url = 'http://www.maoyan.com/board/4?offset=' + str(offset)
    for item in parse_one_page(url):
        print(item)
        write_to_file(item)

if __name__ == '__main__':
    for i in range(10):
        main(offset=i*10)
        time.sleep(1)
