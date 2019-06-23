#!/usr/bin/env python
# -*- coding: utf-8 -*-
# @File  : financialDbMbms.py
# @Author: Donglai Chen
# @Date  : 2018/10/1
# @Desc  : 个人财务数据库管理系统

import sqlite3
import pprint
import logging

class FinancialDb(object):
    def __init__(self,filename):
        self.initLogger()
        self.conn = sqlite3.connect(filename)

    def initLogger(self):
        self.logger = logging.getLogger('FinancialDb')
        self.logger.setLevel(level=logging.INFO)
        handler = logging.FileHandler("log_FinancialDbMbms.txt",encoding='utf-8')
        handler.setLevel(logging.INFO)
        formatter = logging.Formatter('%(asctime)s - %(name)s - %(filename)s - %(levelname)s - %(funcName)s - %(message)s')
        handler.setFormatter(formatter)
        console = logging.StreamHandler()
        console.setLevel(logging.INFO)
        formatter = logging.Formatter('%(asctime)s - %(name)s - %(filename)s - %(levelname)s - %(funcName)s - %(message)s')
        console.setFormatter(formatter)
        self.logger.addHandler(console)
        self.logger.addHandler(handler)

    def addTableAccounts(self):
        sql = '''
        CREATE TABLE Accounts(
            account_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
            comments TEXT NOT NULL,
            type TEXT NOT NULL,
            position TEXT NOT NULL,
            moneyType TEXT NOT NULL DEFAULT 'RMB',
            count FLOAT NOT NULL,
            interest FLOAT NOT NULL,
            risk FLOAT NOT NULL,
            status INTEGER NOT NULL DEFAULT 'valid',
            cTime TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
            startDate DATE NOT NULL,
            endDate DATE NOT NULL
        )
        '''
        self.conn.execute(sql)

    def addTablePerspectives(self):
        sql = '''
        CREATE TABLE Perspectives(
            pers_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
            dollarRmbExchange FLOAT NOT NULL,
            oneYearFixedInterest FLOAT NOT NULL,
            pflInterest FLOAT NOT NULL,
            clInterest FLOAT NOT NULL,
            jdStockPrice FLOAT NOT NULL,
            amountRmb FLOAT NOT NULL,
            amountDollar FLOAT NOT NULL'            
        )
        '''
        self.conn.execute(sql)

    def tableData(self,tableName):
        sql = '''
        SELECT * FROM table
        '''
        sql = sql.replace('table',tableName)
        cursor = self.conn.cursor()
        cursor = cursor.execute(sql)
        res = cursor.fetchall()
        return res

    def insertAccount(self,type,moneyType,count,position,startDate,endDate,interest,risk,comments = ''):
        sql = '''
        INSERT INTO Accounts(
            type,
            moneyType,
            count,
            position,
            startDate,
            endDate,
            interest,
            risk,
            comments
        )values(
            'typeValue',
            'moneyTypeValue',
            'countValue',
            'positionValue',
            'startDateValue',
            'endDateValue',
            'interestValue',
            'riskValue',
            'commentsValue'
        )
        '''
        sql = sql.replace('typeValue',str(type))\
                .replace('moneyTypeValue',str(moneyType))\
                .replace('countValue',str(count))\
                .replace('positionValue',str(position))\
                .replace('startDateValue',str(startDate))\
                .replace('endDateValue',str(endDate))\
                .replace('interestValue',str(interest))\
                .replace('riskValue',str(risk))\
                .replace('commentsValue',str(comments))
        self.conn.execute(sql)
        self.conn.commit()

    def deleteAccount(self,account_id):
        sql = '''
        DELETE FROM Accounts
        WHERE account_id = accIdValue
        '''
        sql = sql.replace('accIdValue',str(account_id))
        self.conn.execute(sql)
        self.conn.commit()

if __name__ == '__main__':
    db = FinancialDb('FinancialDb.db')
    # db.addTableAccounts()
    # db.insertAccount('理财产品','RMB',100,'招商4331','2018-10-01','2018-10-02',0.02,3,'无备注')
    db.deleteAccount(36)
    print('Accounts:')
    print(db.tableData('Accounts'))
