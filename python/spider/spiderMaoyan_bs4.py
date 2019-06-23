#!/usr/bin/python3

import requests,re,time,json
from bs4 import BeautifulSoup

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
    soup = BeautifulSoup(text,'lxml')
    index = soup.find_all(attrs={"class":"board-index"})
    images = soup.find_all(attrs={"class":"board-img"})
    titles = soup.find_all(class_="name")
    actors = soup.find_all(class_="star")
    times = soup.find_all(class_="releasetime")
    score_ints = soup.find_all(attrs={"class":"integer"})
    score_points = soup.find_all(attrs={"class":"fraction"})

    for i in range(10):
        yield {
                'index': index[i].string,
                'image': images[i]["data-src"],
                'title': titles[i].a.string,
                'actor': actors[i].string.strip()[3:],
                'time':  times[i].string.strip()[5:],
                'score': score_ints[i].string + score_points[i].string
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
