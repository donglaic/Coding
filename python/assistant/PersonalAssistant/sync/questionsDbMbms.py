#!/usr/bin/env python
# -*- coding: utf-8 -*-
# @File  : questionsDbMbms.py
# @Author: Donglai Chen
# @Date  : 2018/9/22
# @Desc  : 题库db mbms

import sqlite3
import pprint
import logging
import random

class QuestionsDb(object):
    def __init__(self,filename):
        self.initLogger()
        self.conn = sqlite3.connect(filename)

    def initLogger(self):
        self.logger = logging.getLogger('Assistant')
        self.logger.setLevel(level=logging.INFO)
        handler = logging.FileHandler("log.txt")
        handler.setLevel(logging.INFO)
        formatter = logging.Formatter('%(asctime)s - %(name)s - %(filename)s - %(levelname)s - %(funcName)s - %(message)s')
        handler.setFormatter(formatter)
        console = logging.StreamHandler()
        console.setLevel(logging.INFO)
        formatter = logging.Formatter('%(asctime)s - %(name)s - %(filename)s - %(levelname)s - %(funcName)s - %(message)s')
        console.setFormatter(formatter)
        self.logger.addHandler(console)
        self.logger.addHandler(handler)

    def addTables(self):
        sql = '''
        CREATE TABLE Questions(
            ques_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
            ques TEXT,
            answ TEXT,
            status INTEGER NOT NULL DEFAULT 'FAIL',
            reviewIndex INTEGER NOT NULL DEFAULT 0,
            cTime TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
            nTime TIMESTAMP NOT NULL
        )
        '''
        self.conn.execute(sql)
        sql = '''
        CREATE TABLE Tests(
            test_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
            ques_id INTEGER NOT NULL REFERENCES Questions(ques_id),
            res TEXT NOT NULL DEFAULT FAIL,
            cTime TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
        )
        '''
        self.conn.execute(sql)

    def insertAQues(self,ques,answ):
        sql = '''
        INSERT INTO Questions(
            ques,
            answ,
            nTime
        )values(
            'quesValue',
            'answValue',
            datetime('now','+5 minutes')
        )
        '''
        sql = sql.replace('quesValue',ques).replace('answValue',answ)
        self.conn.execute(sql)
        self.conn.commit()

    def insertATest(self,ques_id,res):
        if res != 'FAIL' and res != 'SUCC':
            print('Error res: %s' % res)
            return
        sql = '''
        INSERT INTO Tests(
            ques_id,
            res
        )values(
            'quesIdValue',
            'resValue'
        )
        '''
        sql = sql.replace('quesIdValue',str(ques_id)).replace('resValue',res)
        self.conn.execute(sql)
        self.conn.commit()

    def tableData(self,tableName):
        sql = '''
        SELECT * FROM table
        '''
        sql = sql.replace('table',tableName)
        cursor = self.conn.cursor()
        cursor = cursor.execute(sql)
        res = cursor.fetchall()
        return res

    def EbbinghausTime(self,reviewIndex):
        time = ['5 minutes','30 minutes','12 hour','1 day','2 day','4 day','7 day','15 day','30 day']
        if reviewIndex >= len(time):
            return time[len(time)-1]
        else:
            return time[reviewIndex]

    def questions(self):
        sql = '''
        SELECT * FROM Questions
        WHERE nTime < CURRENT_TIMESTAMP
        ORDER BY ques_id 
        '''
        cursor = self.conn.cursor()
        cursor = cursor.execute(sql)
        res = cursor.fetchall()
        return res

    def question(self):
        questions = self.questions()
        if len(questions) != 0:
            return questions[random.randint(0, len(questions)-1)]
        else:
            return None

    def updateQuestion(self,ques_id,res,reviewIndex):
        self.logger.info('updateQuestion ques_id:{} res:{} reviewIndex:{}'.format(
            ques_id,res,reviewIndex
        ))
        if res != 'FAIL' and res != 'SUCC':
            print('Error res: %s' % res)
            return
        if res == 'SUCC':
            sql = '''
            UPDATE Questions
            SET status = 'SUCC', nTime = datetime('now','+{}'), reviewIndex = {} 
            WHERE ques_id = {}
            ''' .format( self.EbbinghausTime(reviewIndex),reviewIndex+1,ques_id)
        else:
            sql = '''
            UPDATE Questions
            SET status = 'FAIL', nTime = datetime('now','+5 minutes'), reviewIndex = 0
            WHERE ques_id = %d
            ''' % ques_id
        self.logger.info('sql:{}'.format(
            sql
        ))
        self.conn.execute(sql)
        self.conn.commit()
        self.insertATest(ques_id,res)

    def questionTested(self,ques_id,res):
        self.updateQuestion(ques_id,res)
        self.insertATest(ques_id,res)

    def currentTime(self):
        sql = '''
        SELECT CURRENT_TIMESTAMP
        '''
        cursor = self.conn.cursor()
        cursor = cursor.execute(sql)
        res = cursor.fetchall()
        return res

if __name__ == '__main__':
    db = QuestionsDb('XFQuestionsLib.db')
    # db.addTables()
    # db.insertAQues('uuu?','I`m delay.')
    # db.insertATest(1,'SUCC')
    # db.updateQuestion(1,'SUCC',3)
    print('Questions:')
    pprint.pprint(db.tableData('Questions'))
    print('\nTests:')
    pprint.pprint(db.tableData('Tests'))
    # pprint.pprint(db.questions())
    # print(db.currentTime())
    # print('\nQuestion to test:')
    # pprint.pprint(db.question())
