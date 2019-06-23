#!/usr/bin/python3

import pymysql

def main():
    db = pymysql.connect(host='localhost',user='root',password='a110110110',port=3306,db='spiders')
    cursor = db.cursor()
    sql = '''CREATE TABLE IF NOT EXISTS Films_top100 (
        id INT NOT NULL PRIMARY KEY,
        image VARCHAR(255) NOT NULL,
        title VARCHAR(255) NOT NULL,
        actor VARCHAR(255) NOT NULL,
        time VARCHAR(255) NOT NULL,
        score VARCHAR(255) NOT NULL)'''
    cursor.execute(sql)
    db.close()

if __name__ == '__main__':
    main()
