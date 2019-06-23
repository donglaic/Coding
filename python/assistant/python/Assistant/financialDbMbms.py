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
        self.logger = logging.getLogger('Assistant.FinancialDb')
        self.conn = sqlite3.connect(filename)

    def AddTableAccounts(self):
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
        self.Execute(sql)

    def Execute(self, sql):
        self.logger.info('Execute sql:{}'.format(sql))
        self.conn.execute(sql)

    def AddTablePerspectives(self):
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
        self.Execute(sql)

    def TableData(self, tableName):
        sql = '''
        SELECT * FROM table
        WHERE status == 'valid'
        '''
        sql = sql.replace('table',tableName)
        cursor = self.conn.cursor()
        cursor = cursor.execute(sql)
        res = cursor.fetchall()
        return res

    def AccountDetail(self):
        sql = '''
        SELECT account_id,
                type,
                moneyType,
                count,
                interest,
                risk,
                startDate,
                endDate,
                position,
                comments 
        FROM Accounts
        WHERE status == 'valid'
        ORDER BY type
        '''
        cursor = self.conn.cursor()
        cursor = cursor.execute(sql)
        res = cursor.fetchall()
        return res

    def AccounTypes(self):
        sql = '''
        SELECT DISTINCT type FROM Accounts
        '''
        cursor = self.conn.cursor()
        cursor = cursor.execute(sql)
        res = cursor.fetchall()
        return [i[0] for i in res]

    def AccountsOfType(self,type):
        sql = '''
        SELECT account_id,
                type,
                moneyType,
                count,
                interest,
                risk,
                startDate,
                endDate,
                position,
                comments 
        FROM Accounts
        WHERE status == 'valid'
        and type == 'typeValue'
        '''
        sql = sql.replace('typeValue', type)
        cursor = self.conn.cursor()
        cursor = cursor.execute(sql)
        res = cursor.fetchall()
        return res

    def InsertAccount(self, type, moneyType, count, position, startDate, endDate, interest, risk, comments =''):
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
        self.Execute(sql)
        self.conn.commit()

    def UpdataAccount(self,record):
        id, type, moneyType, count, interest, risk, startDate, endDate, position, comments = record
        sql = '''
        UPDATE Accounts
        SET type='typeValue',
            moneyType='moneyTypeValue',
            count='countValue',
            position='positionValue',
            startDate='startDateValue',
            endDate='endDateValue',
            interest='interestValue',
            risk='riskValue',
            comments='commentsValue'
        WHERE account_id == idValue
        '''
        sql = sql.replace('typeValue',str(type))\
                .replace('moneyTypeValue',str(moneyType))\
                .replace('countValue',str(count))\
                .replace('positionValue',str(position))\
                .replace('startDateValue',str(startDate))\
                .replace('endDateValue',str(endDate))\
                .replace('interestValue',str(interest))\
                .replace('riskValue',str(risk))\
                .replace('commentsValue',str(comments)) \
                .replace('idValue', str(id))
        self.Execute(sql)
        self.conn.commit()

    def DeleteAccount(self, account_id):
        sql = '''
        DELETE FROM Accounts
        WHERE account_id = accIdValue
        '''
        sql = sql.replace('accIdValue',str(account_id))
        self.Execute(sql)
        self.conn.commit()

if __name__ == '__main__':
    db = FinancialDb('FinancialDb.db')
    # db.addTableAccounts()
    # db.insertAccount('理财产品','RMB',100,'招商4331','2018-10-01','2018-10-02',0.02,3,'无备注')
    # db.deleteAccount(36)
    # print(db.AccounTypes())
    # db.UpdataAccount((13, '货币基金', 'RMB', 47135.37, '4.29%', 1.0, '2018/1/1', '2029/1/1', '招商4331', '朝朝赢'))
    for i in db.AccountsOfType('理财产品'):
        print(i)
    # db.InsertAccount('理财产品','RMB',300000,'招商4321','2018-4-9','2099-1-1',0.0455,2,'睿逸月添利进取型')
    # print('Accounts:')
    # for i in db.AccountDetail():
    #     print(i)

