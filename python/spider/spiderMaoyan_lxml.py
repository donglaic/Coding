#!/usr/bin/python3

import requests,re,time,json
from lxml import etree

def get_one_page(url):
    headers={
            'User-Agent':'Mozilla/5.0(Macintosh;Intel Mac OS X 10_13_3) AppleWebKit/537.36(KHTML, like Gecko) Chrome/65.03325.162 Safari/537.36'
    }
    response = requests.get(url,headers=headers)
    #print(response.status_code)
    if response.status_code == 200:
        #print(response.text)
        return response.text
    return None

def parse_one_page(text):
    html = etree.HTML(text)
    indexs = html.xpath('//i[contains(@class,"board-index")]/text()')
    images = html.xpath('//img[@class="board-img"]//@data-src')
    titles = html.xpath('//p[@class="name"]/a/text()')
    actors = html.xpath('//p[@class="star"]/text()')
    times = html.xpath('//p[@class="releasetime"]/text()')
    score_ints = html.xpath('//i[@class="integer"]/text()')
    score_points = html.xpath('//i[@class="fraction"]/text()')
    for i in range(len(indexs)):
        yield {
                'index': indexs[i],
                'image': images[i],
                'title': titles[i],
                'actor': actors[i].strip()[3:],
                'time': times[i].strip()[5:],
                'score': score_ints[i] + score_points[i]
                }


def write_to_file(content):
    with open('result.txt','a',encoding='utf-8') as f:
        f.write(json.dumps(content,ensure_ascii=False) + '\n')

def main(offset):        
    url = 'http://www.maoyan.com/board/4?offset=' + str(offset)
    html = get_one_page(url)
    for item in parse_one_page(html):
        print(item)
        write_to_file(item)

if __name__ == '__main__':
    for i in range(10):
        main(offset=i*10)
        time.sleep(1)
