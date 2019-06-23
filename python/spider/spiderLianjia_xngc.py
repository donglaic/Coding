#!/usr/bin/python3

import requests,time,csv
from pyquery import PyQuery as pq 

def parse_one_page(url):
    headers={
            'User-Agent':'Mozilla/5.0(Macintosh;Intel Mac OS X 10_13_3) AppleWebKit/537.36(KHTML, like Gecko) Chrome/65.03325.162 Safari/537.36'
    }
    doc = pq(url=url,headers=headers)
    houses = doc('.listContent')
    title = [i.text() for i in houses('.title').items()]
    totalPrice = [i.text() for i in houses('.totalPrice .number').items()]
    unitPrice = [i.text() for i in houses('.unitPrice .number').items()]
    dealDate = [i.text() for i in houses('.dealDate').items()]
    
    for i in range(len(title)):
        yield {
                'title': title[i],
                'totalPrice': totalPrice[i],
                'unitPrice': unitPrice[i],
                'dealDate': dealDate[i]
                }

def write_to_file(content):
    with open('lianjiaResult_xngc.csv','a',encoding='utf-8') as f:
        fieldnames = ['title','dealDate','unitPrice','totalPrice']
        writer = csv.DictWriter(f,fieldnames=fieldnames)
        writer.writerow(content)

def main(page):        
    url = 'https://cd.lianjia.com/chengjiao/{page}c1611061024801/'.format(page=page)
    for item in parse_one_page(url):
        print(item)
        write_to_file(item)

if __name__ == '__main__':
    for page in ['','pg2','pg3','pg4']:
        main(page)
        time.sleep(1)
