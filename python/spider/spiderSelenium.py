#!/usr/bin/python3
from selenium import webdriver
from selenium.webdriver.common.by import By
from selenium.webdriver.common.keys import Keys
from selenium.webdriver.support import expected_conditions as EC
from selenium.webdriver.support.wait import WebDriverWait

def main():
    browser = webdriver.Firefox()
    try:
        browser.get('https://www.baidu.com')
        inputBox = browser.find_element_by_id('kw')
        inputBox.send_keys('Python')
        inputBox.send_keys(Keys.ENTER)
        wait = WebDriverWait(browser,10)
        wait.until(EC.presence_of_element_located((By.ID,'content_left')))
        print(browser.current_url)
        print(browser.get_cookies())
        print(browser.page_source)
    finally:
        browser.close()

if __name__ == '__main__':
   main() 
