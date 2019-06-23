#!/usr/bin/env python
# -*- coding: utf-8 -*-
# @File  : examiner.py
# @Author: Donglai Chen
# @Date  : 2018/9/22
# @Desc  : 生活小助手--陈有钱

from questionsDbMbms import QuestionsDb
from pprint import pprint
import logging

class Examiner(object):
    ''''
    考官的类
    '''
    def __init__(self):
        self.initLogger()
        self.db = QuestionsDb('XFQuestionsLib.db')
        self.init()

    def init(self):
        self.status = 'NULL'

    def initLogger(self):
        self.logger = logging.getLogger('Assistant')
        self.logger.setLevel(level=logging.INFO)
        handler = logging.FileHandler("log.txt",encoding='utf-8')
        handler.setLevel(logging.INFO)
        formatter = logging.Formatter('%(asctime)s - %(name)s - %(filename)s - %(levelname)s - %(funcName)s - %(message)s')
        handler.setFormatter(formatter)
        console = logging.StreamHandler()
        console.setLevel(logging.INFO)
        formatter = logging.Formatter('%(asctime)s - %(name)s - %(filename)s - %(levelname)s - %(funcName)s - %(message)s')
        console.setFormatter(formatter)
        self.logger.addHandler(console)
        self.logger.addHandler(handler)

    def ask(self):
        '''
        推送一条新的问题给用户
        :return:
        '''
        self.question = self.db.question()
        if None == self.question:
            self.logger.info('There is NO question to ask!')
            self.status = 'NULL'
            return 'Examiner: There is NO question to ask!'
        else:
            self.status = 'WaitAnsw'
            return 'No.'+str(self.question[0])+' '+self.question[1] + '\n\nThere are still '\
                   +str(self.db.examQuestionNum())+' questions.'

    def correct(self,answ):
        reply = ''
        if answ != self.question[2]:
            self.logger.info('Correct Wrong!')
            reply += ('Wrong! The correct answer is: \n'+self.question[2])
            self.db.updateQuestion(self.question[0],'FAIL',self.question[4])
        else:
            self.logger.info('Correct Right!')
            reply += 'Right! Congratulation!'
            self.db.updateQuestion(self.question[0],'SUCC',self.question[4])
        reply += ('\n\n'+self.ask())
        return reply

    def recMsg(self,msg):
        self.logger.info('Status {} rec {}'.format(self.status,msg))
        ret = ''
        if self.status == 'WaitAnsw' and msg != '':
            ret = self.correct(msg)
        return ret

if __name__ == '__main__':
    examiner = Examiner()
    print(examiner.ask())
    print(examiner.recMsg('I`m delay.'))
    print(examiner.correct('I`m not delay.'))
