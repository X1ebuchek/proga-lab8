package sample.progalab8;

import java.util.ListResourceBundle;

public class loc extends ListResourceBundle {
    @Override
    protected Object[][] getContents() {
        return contents;
    }
    private static final Object[][] contents = {
            {"login_now","Текущий пользователь: "},
            {"addButton","Добавить"},
            {"updateButton","Изменить"},
            {"removeButton","Удалить"},
            {"tableTab","Таблица"},
            {"coordinatesTab","Плоскость"},
            {"name","Имя:"},
            {"price","Цена:"},
            {"comment","Комментарий:"},
            {"refundable","Возможность возврата:"},
            {"type","Тип ивента:"},
            {"name1","Имя ивента:"},
            {"description","Описание:"},
            {"eventType","Тип ивента:"},
            {"countButton","Посчитать"},
            {"outputButton","Вывести"},
            {"login","Логин:"},
            {"password","Пароль:"},
            {"loginButton","Войти"},
            {"registrationButton","Зарегистрироваться"},
            {"backButton","< Назад"},
            {"error","Некорректный ввод"},

            {"loginError","Пользователь с таким логином уже существует"},
            {"regSuccessfully","Регистрация прошла успешно"},
            {"logSuccessfully","Успешная авторизация"},
            {"passwordError","Введён неверный пароль"},
            {"userNotExist","Пользователя с данным логином не существует"},
            {"successfullyAdded","Элемент успешно добавлен"},
            {"notYourElement","Это не ваш элемент"},
            {"successfullyUpdate","Элемент успешно заменён"},
            {"idElementError","Элемента с данным id нет в коллекции"},
            {"info1","Тип: LinkedList   "},
            {"info2","Время инициализации: "},
            {"info3","   Количество элементов: "},
            {"successfullyRemove","Элемент успешно удалён"},
            {"collectionClear","Коллекция очищена"},
            {"priceError","Элемент не был добавлен, т.к. его цена не наименьшая"},
            {"elementsRemove","Элементы удалены"},
            {"priceRemoveSuccessfully","Элемент с заданной ценой удалён"},
            {"priceElementError","Элемента с заданной ценой нет в коллекции"},
            {"server_error","Сервер не доступен"},


            {"help","help : вывести справку по доступным командам\n" +
                    "info : вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)\n" +
                    "show : вывести в стандартный поток вывода все элементы коллекции в строковом представлении\n" +
                    "add {element} : добавить новый элемент в коллекцию\n" +
                    "update id {element} : обновить значение элемента коллекции, id которого равен заданному\n" +
                    "remove_by_id id : удалить элемент из коллекции по его id\n" +
                    "clear : очистить коллекцию\n" +
                    "execute_script file_name : считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде,\n" +
                    " в котором их вводит пользователь в интерактивном режиме.\n" +
                    "exit : завершить программу (без сохранения в файл)\n" +
                    "add_if_min {element} : добавить новый элемент в коллекцию, если его значение меньше, чем у наименьшего элемента этой коллекции\n" +
                    "remove_greater {element} : удалить из коллекции все элементы, превышающие заданный\n" +
                    "history : вывести последние 6 команд (без их аргументов)\n" +
                    "remove_any_by_price price : удалить из коллекции один элемент, значение поля price которого эквивалентно заданному\n" +
                    "count_by_type type : вывести количество элементов, значение поля type которых равно заданному\n" +
                    "filter_less_than_comment comment : вывести элементы, значение поля comment которых меньше заданного"},

    };
}
