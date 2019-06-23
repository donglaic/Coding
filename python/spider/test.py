#!/usr/bin/python3

import pymysql

def main():
    db = pymysql.connect(host='localhost',user='root',password='a110110110',port=3306,db='spiders')
    cursor = db.cursor()
    sql = 'CREATE TABLE test (id INT,PRIMARY KEY(id))'
    cursor.execute(sql)
    db.close()

if __name__ == '__main__':
    main()
