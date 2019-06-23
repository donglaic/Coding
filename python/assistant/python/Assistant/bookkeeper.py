# encoding: utf-8
"""
@author: donglaic 
@contact: 652559480@qq.com

@version: 1.0
@file: bookkeeper.py
@time: 2018/12/23 18:00

Note: 个人财务管理
"""
from Assistant.financialDbMbms import FinancialDb
from Assistant.processer import Processer
from Config.config import Config

CMD_CMDLIST = "CMD:List"

class Bookkeeper(Processer):
    def __init__(self,name):
        Processer.__init__(self,name)
        self.db = FinancialDb('FinancialDb.db')

    def Init(self):
        pass

    def AccontDetail(self):
        result = ''
        for i in self.db.AccountDetail():
            result = result + str(i) + '\n'
        return result

    def FinancialProductAccontDetail(self):
        result = ''
        for i in self.db.AccountsOfType('理财产品'):
            result = result + str(i) + '\n'
        return result

    def Receive(self,msg):
        self.logger.info('Processer {} rec {}'.format(self.name,msg))
        if msg == CMD_CMDLIST:
            # 将状态转入到等待用户选择要process
            self.state = 'WaitCmdSelection'
            # 用户请求命令列表
            return self.CmdList()
        elif self.state == 'WaitCmdSelection':
            config = Config(self.name + '_config.json').config
            # 判断用户选择的process序号合法
            if int(msg) > 0 and int(msg) <= len(config):
                # 清除状态
                self.__state = None
                result = eval('self.{}()'.format(config[int(msg) - 1]['FunName']))
                return '{} 启动\n{}'.format(config[int(msg) - 1]['Commnet'],result)
            else:
                return None
        else:
            self.logger.warning('DiscardMsg: {}'.format(msg))
            return '对不起，我不懂你的意思。'

if __name__ == '__main__':
    b = Bookkeeper('Bookkeeper')
    print(b.Receive(CMD_CMDLIST))
    # print(b.Receive('1'))
    print(b.Receive('2'))


