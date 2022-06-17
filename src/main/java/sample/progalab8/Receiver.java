package sample.progalab8;

import java.io.File;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Класс получатель
 */
public class Receiver {
    static List<String> scripts = new ArrayList<String>();
    Reader reader = new Reader();
    static String scriptNow = "";
    static boolean error = false;
    static boolean kek = false;
    static boolean exec = false;
    UDPClient udpClient = new UDPClient();

    /**
     * выводит справку по доступным командам
     */
    public SendThing helpCommand() {
        return udpClient.send(null,"help","help : вывести справку по доступным командам\n" +
                "info : вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)\n" +
                "show : вывести в стандартный поток вывода все элементы коллекции в строковом представлении\n" +
                "add {element} : добавить новый элемент в коллекцию\n" +
                "update id {element} : обновить значение элемента коллекции, id которого равен заданному\n" +
                "remove_by_id id : удалить элемент из коллекции по его id\n" +
                "clear : очистить коллекцию\n" +
                "execute_script file_name : считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.\n" +
                "exit : завершить программу (без сохранения в файл) +\n" +
                "add_if_min {element} : добавить новый элемент в коллекцию, если его значение меньше, чем у наименьшего элемента этой коллекции\n" +
                "remove_greater {element} : удалить из коллекции все элементы, превышающие заданный\n" +
                "history : вывести последние 6 команд (без их аргументов)\n" +
                "remove_any_by_price price : удалить из коллекции один элемент, значение поля price которого эквивалентно заданному\n" +
                "count_by_type type : вывести количество элементов, значение поля type которых равно заданному\n" +
                "filter_less_than_comment comment : вывести элементы, значение поля comment которых меньше заданного", UDPClient.login);
    }

    /**
     * выводит в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов)
     */
    public SendThing infoCommand() {
        return udpClient.send(null,"info","", UDPClient.login);
    }

    /**
     * выводит в стандартный поток вывода все элементы коллекции в строковом представлении
     */
    public SendThing showCommand() {
        //Ticket ticket = new Ticket(null,null,null,null,null,null,true,null,null);
        return udpClient.send(null,"show","", UDPClient.login);
    }

    /**
     * добавляет новый элемент в коллекцию
     */
    public SendThing addCommand() {
        Long id = System.currentTimeMillis();
        System.out.print("Введите имя: ");
        String name = MainController.sc.next();

        float x = 0;
        while (true) {
            System.out.print("Введите x: ");
            try {
                x = Float.parseFloat(MainController.sc.next());
            } catch (Exception e) {
                System.out.println("Некорректное значение. X должен быть типа float");
                if (UDPClient.script){
                    error = true;
                    return null;
                }
                continue;
            }
            if (x <= -536) {
                System.out.println("X должен быть больше -536");
                if (UDPClient.script){
                    error = true;
                    return null;
                }
                continue;
            }
            if (x != 0) break;
        }

        long y = 0;
        while (true) {
            System.out.print("Введите у: ");
            try {
                y = Long.parseLong(MainController.sc.next());
            } catch (Exception e) {
                System.out.println("Некорректное значение. Y должен быть типа long");
                if (UDPClient.script){
                    error = true;
                    return null;
                }
                continue;
            }
            if (y != 0) break;
        }

        Coordinates c = new Coordinates(x, y);

        Double price = 0d;
        while (true) {
            System.out.print("Введите цену: ");
            try {
                price = Double.parseDouble(MainController.sc.next());
            } catch (Exception e) {
                System.out.println("Некорректное значение. Цена должна быть типа Double");
                if (UDPClient.script){
                    error = true;
                    return null;
                }
                continue;
            }
            if (price <= 0) {
                System.out.println("Цена должна быть больше 0");
                if (UDPClient.script){
                    error = true;
                    return null;
                }
                continue;
            }
            if (price != 0) break;
        }

        String comment = "";
        while (true) {
            System.out.print("Хотите ввести коммент? (YES/NO): ");
            String s = MainController.sc.next().toUpperCase();
            if (s.equals("YES")) {
                System.out.print("Введите коммент: ");
                comment = MainController.sc.next();
                break;
            }
            if (s.equals("NO")) {
                comment = null;
                break;
            } else{
                System.out.println("Некорректный ввод");
                if (UDPClient.script){
                    error = true;
                    return null;
                }
            }
        }

        boolean refundable = true;
        while (true) {
            System.out.print("Введите возможность возврата: ");
            try {
                String s = MainController.sc.next().toLowerCase();
                if (s.equals("true") || s.equals("false")){
                    refundable = Boolean.parseBoolean(s);
                    break;
                }
                else{
                    System.out.println("Некорректное значение. Возможность возврата должна быть типа boolean");
                    if (UDPClient.script){
                        error = true;
                        return null;
                    }
                }
            } catch (Exception e) {
                System.out.println("Некорректное значение. Возможность возврата должна быть типа boolean");
                if (UDPClient.script){
                    error = true;
                    return null;
                }
            }
        }

        String type = "";
        while (true) {
            System.out.print("Введите тип (VIP, USUAL, CHEAP): ");
            type = MainController.sc.next().toUpperCase();
            if (type.equals("VIP") || type.equals("USUAL") || type.equals("CHEAP")) break;
            else {
                System.out.println("Некорректное значение");
                if (UDPClient.script){
                    error = true;
                    return null;
                }
            }
        }

        try {
            TimeUnit.MILLISECONDS.sleep(1);
        } catch (InterruptedException e) {
            //e.printStackTrace();
        }
        long id1 = System.currentTimeMillis() - 1;
        System.out.print("Введите название ивента: ");
        String name1 = MainController.sc.next();

        String description = "";
        while (true) {
            System.out.print("Хотите ввести описание (YES/NO): ");
            String s = MainController.sc.next().toUpperCase();
            if (s.equals("YES")) {
                System.out.print("Введите описание: ");
                description = MainController.sc.next();
                break;
            }
            if (s.equals("NO")) {
                description = null;
                break;
            } else {
                System.out.println("Некорректное значение");
                if (UDPClient.script){
                    error = true;
                    return null;
                }
            }
        }

        String eventType = "";
        while (true) {
            System.out.print("Хотите ввести тип ивента (YES/NO): ");
            String s = MainController.sc.next().toUpperCase();
            if (s.equals("YES")) {
                System.out.print("Введите тип ивента (BASEBALL, BASKETBALL, THEATRE_PERFORMANCE, EXPOSITION): ");
                eventType = MainController.sc.next().toUpperCase();
                if (eventType.equals("BASEBALL") || eventType.equals("BASKETBALL") || eventType.equals("THEATRE_PERFORMANCE")
                        || eventType.equals("EXPOSITION")) break;
                else {
                    System.out.println("Некорректное значение");
                    if (UDPClient.script){
                        error = true;
                        return null;
                    }
                    continue;
                }
            }
            if (s.equals("NO")) {
                eventType = null;
                break;
            } else {
                System.out.println("Некорректное значение");
                if (UDPClient.script){
                    error = true;
                    return null;
                }
            }
        }

        Event e;
        if (eventType == null) e = new Event(id1, name1, description, null);
        else e = new Event(id1, name1, description, EventType.valueOf(eventType));

        Ticket ticket = new Ticket(id, name, c, new Date(), price, comment, refundable, TicketType.valueOf(type), e, UDPClient.login);
        return udpClient.send(ticket,"add","", UDPClient.login);
    }

    /**
     * обновляет значение элемента коллекции, id которого равен заданному
     */
    public SendThing updateCommand() {
        Long id = 0L;
        try {
            id = Long.parseLong(MainController.sc.next());
        } catch (Exception e) {
            System.out.println("Некорректный ввод. Id должен быть типа long");
            if (UDPClient.script){
                error = true;
                return null;
            }
            return null;
        }
        System.out.print("Введите имя: ");
        String name = MainController.sc.next();

        float x = 0;
        while (true) {
            System.out.print("Введите x: ");
            try {
                x = Float.parseFloat(MainController.sc.next());
            } catch (Exception e) {
                System.out.println("Некорректное значение. X должен быть типа float");
                if (UDPClient.script){
                    error = true;
                    return null;
                }
                continue;
            }
            if (x <= -536) {
                System.out.println("X должен быть больше -536");
                if (UDPClient.script){
                    error = true;
                    return null;
                }
                continue;
            }
            if (x != 0) break;
        }

        long y = 0;
        while (true) {
            System.out.print("Введите у: ");
            try {
                y = Long.parseLong(MainController.sc.next());
            } catch (Exception e) {
                System.out.println("Некорректное значение. Y должен быть типа long");
                if (UDPClient.script){
                    error = true;
                    return null;
                }
                continue;
            }
            if (y != 0) break;
        }
        Coordinates c = new Coordinates(x, y);

        Double price = 0d;
        while (true) {
            System.out.print("Введите цену: ");
            try {
                price = Double.parseDouble(MainController.sc.next());
            } catch (Exception e) {
                System.out.println("Некорректное значение. Цена должна быть типа Double");
                if (UDPClient.script){
                    error = true;
                    return null;
                }
                continue;
            }
            if (price <= 0) {
                System.out.println("Цена должна быть больше 0");
                if (UDPClient.script){
                    error = true;
                    return null;
                }
                continue;
            }
            break;
        }

        String comment = "";
        while (true) {
            System.out.print("Хотите ввести коммент? (YES/NO): ");
            String s = MainController.sc.next().toUpperCase();
            if (s.equals("YES")) {
                System.out.print("Введите коммент: ");
                comment = MainController.sc.next();
                break;
            }
            if (s.equals("NO")) {
                comment = null;
                break;
            } else {
                System.out.println("Некорректный ввод");
                if (UDPClient.script){
                    error = true;
                    return null;
                }
            }
        }

        boolean refundable = true;
        while (true) {
            System.out.print("Введите возможность возврата: ");
            try {
                String s = MainController.sc.next().toLowerCase();
                if (s.equals("true") || s.equals("false")){
                    refundable = Boolean.parseBoolean(s);
                    break;
                }
                else{
                    System.out.println("Некорректное значение. Возможность возврата должна быть типа boolean");
                    if (UDPClient.script){
                        error = true;
                        return null;
                    }
                }
            } catch (Exception e) {
                System.out.println("Некорректное значение. Возможность возврата должна быть типа boolean");
                if (UDPClient.script){
                    error = true;
                    return null;
                }
            }
        }

        String type = "";
        while (true) {
            System.out.print("Введите тип (VIP, USUAL, CHEAP): ");
            type = MainController.sc.next().toUpperCase();
            if (type.equals("VIP") || type.equals("USUAL") || type.equals("CHEAP")) break;
            else {
                System.out.println("Некорректное значение");
                if (UDPClient.script){
                    error = true;
                    return null;
                }
            }
        }

        try {
            TimeUnit.MILLISECONDS.sleep(1);
        } catch (InterruptedException e) {
            //e.printStackTrace();
        }
        long id1 = System.currentTimeMillis() - 1;
        System.out.print("Введите название ивента: ");
        String name1 = MainController.sc.next();
        String description = "";
        while (true) {
            System.out.print("Хотите ввести описание (YES|NO): ");
            String s = MainController.sc.next().toUpperCase();
            if (s.equals("YES")) {
                System.out.print("Введите описание: ");
                description = MainController.sc.next();
                break;
            }
            if (s.equals("NO")) {
                description = null;
                break;
            } else {
                System.out.println("Некорректное значение");
                if (UDPClient.script){
                    error = true;
                    return null;
                }
            }
        }

        String eventType = "";
        while (true) {
            System.out.print("Хотите ввести тип ивента (YES/NO): ");
            String s = MainController.sc.next().toUpperCase();
            if (s.equals("YES")) {
                System.out.print("Введите тип ивента (BASEBALL, BASKETBALL, THEATRE_PERFORMANCE, EXPOSITION): ");
                eventType = MainController.sc.next().toUpperCase();
                if (eventType.equals("BASEBALL") || eventType.equals("BASKETBALL") || eventType.equals("THEATRE_PERFORMANCE")
                        || eventType.equals("EXPOSITION")) break;
                else {
                    System.out.println("Некорректное значение");
                    if (UDPClient.script){
                        error = true;
                        return null;
                    }
                    continue;
                }
            }
            if (s.equals("NO")) {
                eventType = null;
                break;
            } else {
                System.out.println("Некорректное значение");
                if (UDPClient.script){
                    error = true;
                    return null;
                }
            }
        }
        Event e;
        if (eventType == null) e = new Event(id1, name1, description, null);
        else e = new Event(id1, name1, description, EventType.valueOf(eventType));

        Ticket ticket = new Ticket(id, name, c, new Date(), price, comment, refundable, TicketType.valueOf(type), e, UDPClient.login);

        return udpClient.send(ticket,"update",id.toString(), UDPClient.login);
    }

    /**
     * удаляет элемент из коллекции по его id
     */
    public SendThing remove_by_idCommand() {
        Long id = 0L;
        try {
            id = Long.parseLong(MainController.sc.next());
        } catch (Exception e) {
            System.out.println("Некорректный ввод. Id должен быть типа long");
            if (UDPClient.script){
                error = true;
                return null;
            }
            return null;
        }
        return udpClient.send(null,"remove_by_id",id.toString(), UDPClient.login);
    }

    /**
     * очищает коллекцию
     */
    public SendThing clearCommand() {
        return udpClient.send(null,"clear","", UDPClient.login);
    }


    /**
     * считывает и исполняет скрипт из указанного файла
     */
    public SendThing execute_scriptCommand() {
        String s = "";
        if (UDPClient.script && !exec) {
            s = scriptNow;
        }else {
            while (true) {
                exec = false;
                s = MainController.sc.next();
                File file = new File(s);
                if (file.exists()) break;
                else {
                    System.out.println("Такого файла не существует");
                    if (UDPClient.script) {
                        UDPClient.script = false;
                        UDPClient.lineCounter = 0;
                        UDPClient.str = "";
                        return null;
                    }
                    return null;
                }
            }
        }

        if (s.equals(scriptNow)){

        }
        else {
        for (int i = 0; i < scripts.size(); i++) {
            if (s.equals(scripts.get(i))) {
                System.out.println("Скрипты зацикливаются");
                UDPClient.script = false;
                UDPClient.lineCounter = 0;
                UDPClient.str = "";
                error = true;
                return null;
            }
        }
        }
        scriptNow = s;
        scripts.add(s);
        String s1 = "";
        UDPClient.script = true;
        while (!scriptNow.isEmpty()) {
            if (error){
                System.out.println("В скрипте ошибка");
                UDPClient.script = false;
                UDPClient.lineCounter = 0;
                UDPClient.str = "";
                error = false;
                scriptNow = "";
                kek = true;
                return null;
            }
            kek = false;
            s1 = MainController.sc.next();
            if (s1.equals("execute_script")) exec = true;
            return Computer.loop(s1);
        }
        return null;
    }

    /**
     * завершает программу (без сохранения в файл)
     */
    public SendThing exitCommand() {
        System.exit(0);
        return null;
    }

    /**
     * добавляет новый элемент в коллекцию, если его значение меньше, чем у наименьшего элемента этой коллекции
     */
    public SendThing add_if_minCommand() {
        Long id = System.currentTimeMillis();
        System.out.print("Введите имя: ");
        String name = MainController.sc.next();

        float x = 0;
        while (true) {
            System.out.print("Введите x: ");
            try {
                x = Float.parseFloat(MainController.sc.next());
            } catch (Exception e) {
                System.out.println("Некорректное значение. X должен быть типа float");
                if (UDPClient.script){
                    error = true;
                    return null;
                }
                continue;
            }
            if (x <= -536) {
                System.out.println("X должен быть больше -536");
                if (UDPClient.script){
                    error = true;
                    return null;
                }
                continue;
            }
            if (x != 0) break;
        }

        long y = 0;
        while (true) {
            System.out.print("Введите у: ");
            try {
                y = Long.parseLong(MainController.sc.next());
            } catch (Exception e) {
                System.out.println("Некорректное значение. Y должен быть типа long");
                if (UDPClient.script){
                    error = true;
                    return null;
                }
                continue;
            }
            if (y != 0) break;
        }

        Coordinates c = new Coordinates(x, y);

        Double price = 0d;
        while (true) {
            System.out.print("Введите цену: ");
            try {
                price = Double.parseDouble(MainController.sc.next());
            } catch (Exception e) {
                System.out.println("Некорректное значение. Цена должна быть типа Double");
                if (UDPClient.script){
                    error = true;
                    return null;
                }
                continue;
            }
            if (price <= 0) {
                System.out.println("Цена должна быть больше 0");
                if (UDPClient.script){
                    error = true;
                    return null;
                }
                continue;
            }
            if (price != 0) break;
        }

        String comment = "";
        while (true) {
            System.out.print("Хотите ввести коммент? (YES/NO): ");
            String s = MainController.sc.next().toUpperCase();
            if (s.equals("YES")) {
                System.out.print("Введите коммент: ");
                comment = MainController.sc.next();
                break;
            }
            if (s.equals("NO")) {
                comment = null;
                break;
            } else {
                System.out.println("Некорректный ввод");
                if (UDPClient.script){
                    error = true;
                    return null;
                }
            }
        }

        boolean refundable = true;
        while (true) {
            System.out.print("Введите возможность возврата: ");
            try {
                String s = MainController.sc.next().toLowerCase();
                if (s.equals("true") || s.equals("false")){
                    refundable = Boolean.parseBoolean(s);
                    break;
                }
                else{
                    System.out.println("Некорректное значение. Возможность возврата должна быть типа boolean");
                    if (UDPClient.script){
                        error = true;
                        return null;
                    }
                }
            } catch (Exception e) {
                System.out.println("Некорректное значение. Возможность возврата должна быть типа boolean");
                if (UDPClient.script){
                    error = true;
                    return null;
                }
            }
        }

        String type = "";
        while (true) {
            System.out.print("Введите тип (VIP, USUAL, CHEAP): ");
            type = MainController.sc.next().toUpperCase();
            if (type.equals("VIP") || type.equals("USUAL") || type.equals("CHEAP")) break;
            else System.out.println("Некорректное значение");
        }

        try {
            TimeUnit.MILLISECONDS.sleep(1);
        } catch (InterruptedException e) {
            //e.printStackTrace();
        }
        long id1 = System.currentTimeMillis() - 1;
        System.out.print("Введите название ивента: ");
        String name1 = MainController.sc.next();

        String description = "";
        while (true) {
            System.out.print("Хотите ввести описание (YES|NO): ");
            String s = MainController.sc.next().toUpperCase();
            if (s.equals("YES")) {
                System.out.print("Введите описание: ");
                description = MainController.sc.next();
                break;
            }
            if (s.equals("NO")) {
                description = null;
                break;
            } else {
                System.out.println("Некорректное значение");
                if (UDPClient.script){
                    error = true;
                    return null;
                }
            }
        }

        String eventType = "";
        while (true) {
            System.out.print("Хотите ввести тип ивента (YES/NO): ");
            String s = MainController.sc.next().toUpperCase();
            if (s.equals("YES")) {
                System.out.print("Введите тип ивента (BASEBALL, BASKETBALL, THEATRE_PERFORMANCE, EXPOSITION): ");
                eventType = MainController.sc.next().toUpperCase();
                if (eventType.equals("BASEBALL") || eventType.equals("BASKETBALL") || eventType.equals("THEATRE_PERFORMANCE")
                        || eventType.equals("EXPOSITION")) break;
                else {
                    System.out.println("Некорректное значение");
                    if (UDPClient.script){
                        error = true;
                        return null;
                    }
                    continue;
                }
            }
            if (s.equals("NO")) {
                eventType = null;
                break;
            } else {
                System.out.println("Некорректное значение");
                if (UDPClient.script){
                    error = true;
                    return null;
                }
            }
        }

        Event e;
        if (eventType == null) e = new Event(id1, name1, description, null);
        else e = new Event(id1, name1, description, EventType.valueOf(eventType));

        Ticket ticket = new Ticket(id, name, c, new Date(), price, comment, refundable, TicketType.valueOf(type), e, UDPClient.login);

        return udpClient.send(ticket,"add_if_min","", UDPClient.login);
    }

    /**
     * удаляет из коллекции все элементы, превышающие заданный
     */
    public SendThing remove_greaterCommand() {
        Long id = System.currentTimeMillis();
        System.out.print("Введите имя: ");
        String name = MainController.sc.next();

        float x = 0;
        while (true) {
            System.out.print("Введите x: ");
            try {
                x = Float.parseFloat(MainController.sc.next());
            } catch (Exception e) {
                System.out.println("Некорректное значение. X должен быть типа float");
                if (UDPClient.script){
                    error = true;
                    return null;
                }
                continue;
            }
            if (x <= -536) {
                System.out.println("X должен быть больше -536");
                if (UDPClient.script){
                    error = true;
                    return null;
                }
                continue;
            }
            if (x != 0) break;
        }

        long y = 0;
        while (true) {
            System.out.print("Введите у: ");
            try {
                y = Long.parseLong(MainController.sc.next());
            } catch (Exception e) {
                System.out.println("Некорректное значение. Y должен быть типа long");
                if (UDPClient.script){
                    error = true;
                    return null;
                }
                continue;
            }
            if (y != 0) break;
        }

        Coordinates c = new Coordinates(x, y);

        Double price = 0d;
        while (true) {
            System.out.print("Введите цену: ");
            try {
                price = Double.parseDouble(MainController.sc.next());
            } catch (Exception e) {
                System.out.println("Некорректное значение. Цена должна быть типа Double");
                if (UDPClient.script){
                    error = true;
                    return null;
                }
                continue;
            }
            if (price <= 0) {
                System.out.println("Цена должна быть больше 0");
                if (UDPClient.script){
                    error = true;
                    return null;
                }
                continue;
            }
            break;
        }

        String comment = "";
        while (true) {
            System.out.print("Хотите ввести коммент? (YES/NO): ");
            String s = MainController.sc.next().toUpperCase();
            if (s.equals("YES")) {
                System.out.print("Введите коммент: ");
                comment = MainController.sc.next();
                break;
            }
            if (s.equals("NO")) {
                comment = null;
                break;
            } else {
                System.out.println("Некорректный ввод");
                if (UDPClient.script){
                    error = true;
                    return null;
                }
            }
        }

        boolean refundable = true;
        while (true) {
            System.out.print("Введите возможность возврата: ");
            try {
                String s = MainController.sc.next().toLowerCase();
                if (s.equals("true") || s.equals("false")){
                    refundable = Boolean.parseBoolean(s);
                    break;
                }
                else{
                    System.out.println("Некорректное значение. Возможность возврата должна быть типа boolean");
                    if (UDPClient.script){
                        error = true;
                        return null;
                    }
                }
            } catch (Exception e) {
                System.out.println("Некорректное значение. Возможность возврата должна быть типа boolean");
                if (UDPClient.script){
                    error = true;
                    return null;
                }
            }
        }

        String type = "";
        while (true) {
            System.out.print("Введите тип (VIP, USUAL, CHEAP): ");
            type = MainController.sc.next().toUpperCase();
            if (type.equals("VIP") || type.equals("USUAL") || type.equals("CHEAP")) break;
            else {
                System.out.println("Некорректное значение");
                if (UDPClient.script){
                    error = true;
                    return null;
                }
            }
        }

        try {
            TimeUnit.MILLISECONDS.sleep(1);
        } catch (InterruptedException e) {
            //e.printStackTrace();
        }
        long id1 = System.currentTimeMillis() - 1;
        System.out.print("Введите название ивента: ");
        String name1 = MainController.sc.next();

        String description = "";
        while (true) {
            System.out.print("Хотите ввести описание (YES|NO): ");
            String s = MainController.sc.next().toUpperCase();
            if (s.equals("YES")) {
                System.out.print("Введите описание: ");
                description = MainController.sc.next();
                break;
            }
            if (s.equals("NO")) {
                description = null;
                break;
            } else {
                System.out.println("Некорректное значение");
                if (UDPClient.script){
                    error = true;
                    return null;
                }
            }
        }

        String eventType = "";
        while (true) {
            System.out.print("Хотите ввести тип ивента (YES/NO): ");
            String s = MainController.sc.next().toUpperCase();
            if (s.equals("YES")) {
                System.out.print("Введите тип ивента (BASEBALL, BASKETBALL, THEATRE_PERFORMANCE, EXPOSITION): ");
                eventType = MainController.sc.next().toUpperCase();
                if (eventType.equals("BASEBALL") || eventType.equals("BASKETBALL") || eventType.equals("THEATRE_PERFORMANCE")
                        || eventType.equals("EXPOSITION")) break;
                else {
                    System.out.println("Некорректное значение");
                    if (UDPClient.script){
                        error = true;
                        return null;
                    }
                    continue;
                }
            }
            if (s.equals("NO")) {
                eventType = null;
                break;
            } else {
                System.out.println("Некорректное значение");
                if (UDPClient.script){
                    error = true;
                    return null;
                }
            }
        }

        Event e;
        if (eventType == null) e = new Event(id1, name1, description, null);
        else e = new Event(id1, name1, description, EventType.valueOf(eventType));

        Ticket ticket = new Ticket(id, name, c, new Date(), price, comment, refundable, TicketType.valueOf(type), e, UDPClient.login);

        return udpClient.send(ticket,"remove_greater","", UDPClient.login);
    }

    /**
     * выводит последние 6 команд (без их аргументов)
     */
    public SendThing historyCommand() {
        if (Computer.history1.isEmpty()) System.out.print("");
        else System.out.println(Computer.history1);
        if (Computer.history2.isEmpty()) System.out.print("");
        else System.out.println(Computer.history2);
        if (Computer.history3.isEmpty()) System.out.print("");
        else System.out.println(Computer.history3);
        if (Computer.history4.isEmpty()) System.out.print("");
        else System.out.println(Computer.history4);
        if (Computer.history5.isEmpty()) System.out.print("");
        else System.out.println(Computer.history5);
        if (Computer.history6.isEmpty()) System.out.print("");
        else System.out.println(Computer.history6);
        return null;
    }

    /**
     * удаляет из коллекции один элемент, значение поля price которого эквивалентно заданному
     */
    public SendThing remove_any_by_priceCommand() {
        Double price = 0d;
        try {
            price = Double.parseDouble(MainController.sc.next());
        } catch (Exception e) {
            System.out.println("Некорректное значение. Цена должна быть типа Double");
            if (UDPClient.script){
                error = true;
                return null;
            }
            return null;
        }
        return udpClient.send(null,"remove_any_by_price",price.toString(), UDPClient.login);
    }

    /**
     * выводит количество элементов, значение поля type которых равно заданному
     */
    public SendThing count_by_typeCommand() {
        int k = 0;
        String type = "";
        while (true) {
            type = MainController.sc.next().toUpperCase();
            if (type.equals("VIP") || type.equals("USUAL") || type.equals("CHEAP")) break;
            else {
                System.out.println("Некорректное значение");
                if (UDPClient.script){
                    error = true;
                    return null;
                }
                return null;
            }
        }
        return udpClient.send(null,"count_by_type",type, UDPClient.login);
    }

    /**
     * выводит элементы, значение поля comment которых меньше заданного
     */
    public SendThing filter_less_than_commentCommand() {
        String comment = MainController.sc.next();
        return udpClient.send(null,"filter_less_than_comment",comment, UDPClient.login);
    }
    public SendThing registerCommand(){
        System.out.print("Придумайте логин: ");
        String login = MainController.sc.next();
        System.out.print("Придумайте пароль: ");
        String password = MainController.sc.next();
        password = getHash(password);
        return udpClient.send(null,"reg",password,login);
    }
    public SendThing authCommand(){
        System.out.print("Введите логин: ");
        String login = MainController.sc.next();
        System.out.print("Введите пароль: ");
        String password = MainController.sc.next();
        password = getHash(password);
        return udpClient.send(null,"auth",password,login);
    }
    private String getHash(String s) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-384");
            byte[] bytes = digest.digest(s.getBytes(StandardCharsets.UTF_8));
            BigInteger bigInteger = new BigInteger(1, bytes);
            String hash = bigInteger.toString(16);
            return hash;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
}