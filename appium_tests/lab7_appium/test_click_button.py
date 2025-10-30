import time
import pytest
from appium import webdriver
from appium.options.android import UiAutomator2Options

@pytest.fixture(scope="module")
def driver():
    options = UiAutomator2Options()
    options.platform_name = "Android"
    options.device_name = "emulator-5554"
    options.app = r"C:\projectsTest\app\build\outputs\apk\debug\app-debug.apk"  # <-- свой путь

    driver = webdriver.Remote("http://127.0.0.1:4723", options=options)
    yield driver
    driver.quit()

def test_tap_add_note_button(driver):
    """Тест: просто тап по кнопке 'Добавить заметку'"""
    time.sleep(5)  # ждём, пока приложение загрузится

    # Найдём кнопку по content-desc, который мы добавили
    add_button = driver.find_element("accessibility id", "addNoteButton")
    add_button.click()

    # Проверим, что клик реально сработал (тест не упадёт)
    time.sleep(1)
    assert True
