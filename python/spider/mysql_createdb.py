#!/usr/bin/python3

import pymysql

def main():
    db = pymysql.connect(host='localhost',user='root',password='a110110110',port=3306)
    cursor = db.cursor()
    cursor.execute('SELECT VERSION()')
    data = cursor.fetchone()
    print('Database version:',data)
    cursor.execute('CREATE DATABASE spiders DEFAULT CHARACTER SET utf8')
    db.close()

if __name__ == '__main__':
    main()
