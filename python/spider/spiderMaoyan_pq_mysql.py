#!/usr/bin/python3

import requests,re,time,json
from pyquery import PyQuery as pq 
import pymysql
import pdb

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
                'id': int(indexs[i]),
                'image': images[i],
                'title': titles[i],
                'actor': actors[i].strip()[3:],
                'time':  times[i].strip()[5:],
                'score': score_ints[i] + score_points[i]
                }

def insert_to_mysql(db,cursor,table,data):
    keys = ','.join(data.keys())
    values = ','.join(['%s']*len(data))
    sql = 'INSERT INTO {table}({keys}) VALUES ({values}) ON DUPLICATE KEY UPDATE '.format(table=table,keys=keys,values=values)
    update = ','.join(["{key} = %s".format(key=key) for key in data])
    sql += update
    #pdb.set_trace()
    try:
        if cursor.execute(sql,tuple(data.values())*2):
            print('Insert Successful\n')
            db.commit()
    except:
        print('Insert Failed!\n')
        db.rollback()


def main(offset):        
    url = 'http://www.maoyan.com/board/4?offset=' + str(offset)
    db = pymysql.connect(host='localhost',user='root',password='a110110110',port=3306,db='spiders')
    cursor = db.cursor()
    for item in parse_one_page(url):
        print(item)
        insert_to_mysql(db,cursor,'Films_top100',item)
    db.close()

if __name__ == '__main__':
    for i in range(10):
        main(offset=i*10)
        time.sleep(1)
