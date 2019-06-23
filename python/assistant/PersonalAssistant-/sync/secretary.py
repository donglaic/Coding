#!/usr/bin/env python
# -*- coding: utf-8 -*-
# @File  : secretary.py
# @Author: Donglai Chen
# @Date  : 2018/9/23
# @Desc  : 编辑试题的书记员

from questionsDbMbms import QuestionsDb
import logging

class Secretary(object):
    def __init__(self):
        self.initLogger()
        self.db = QuestionsDb('XFQuestionsLib.db')
        self.clear()

    def initLogger(self):
        self.logger = logging.getLogger('Assistant')
        self.logger.setLevel(level=logging.INFO)
        handler = logging.FileHandler("log.txt")
        handler.setLevel(logging.INFO)
        formatter = logging.Formatter('%(asctime)s - %(name)s - %(filename)s - %(lineno)d - %(levelname)s - %(funcName)s - %(message)s')
        handler.setFormatter(formatter)
        console = logging.StreamHandler()
        console.setLevel(logging.INFO)
        formatter = logging.Formatter('%(asctime)s - %(name)s - %(filename)s - %(lineno)d - %(levelname)s - %(funcName)s - %(message)s')
        console.setFormatter(formatter)
        self.logger.addHandler(console)
        self.logger.addHandler(handler)

    def recMsg(self,msg):
        self.logger.info('Status {} rec {}'.format(self.status,msg))
        ret = ''
        if self.status == 'WaitQues':
            self.inputQuestion(msg)
            ret = 'Please input answer.'
        elif self.status == 'WaitAnsw':
            self.inputAnswer(msg)
            ret = self.questionContent() +  '\nPress c to clear, press any other key to input next question!'
        elif self.status == 'WaitCommit':
            if 'c' != msg:
                self.commit()
                ret += 'Question commited! '
            self.clear()
            ret += 'Please input next question:'
        return ret

    def clear(self):
        self.logger.info('clear')
        self.question = {}
        self.status = 'WaitQues'

    def inputQuestion(self,ques):
        self.logger.info('input ques: {}'.format(ques))
        self.question['ques'] = ques
        self.status = 'WaitAnsw'

    def inputAnswer(self,answ):
        self.logger.info('input answ: {}'.format(answ))
        self.question['answ'] = answ
        self.status = 'WaitCommit'

    def commit(self):
        self.logger.info('commit')
        self.db.insertAQues(self.question['ques'],self.question['answ'])

    def questionContent(self):
        return str(self.question)

if __name__ == '__main__':
    sec = Secretary()
    print(sec.recMsg('what?'))
    print(sec.recMsg('ans'))
    print(sec.recMsg('c'))
