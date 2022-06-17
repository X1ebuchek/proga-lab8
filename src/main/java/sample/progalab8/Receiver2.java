package sample.progalab8;

import java.sql.*;
import java.util.*;
import java.util.Date;
import java.util.stream.Collectors;

public class Receiver2 {
    //private final ResourceBundle res = ResourceBundle.getBundle(loc.class.getName(), Locale.getDefault());
    public Connection c;

    public void connection() {
        c = null;
        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager
                    .getConnection("jdbc:postgresql://localhost:1111/studs",
                            "s336424", "zml766");
            System.out.println("Opened database successfully");

        } catch (
                Exception e) {
            e.printStackTrace();
        }
    }

    List<Ticket> list;
    Date creationDate;

    public Receiver2(List<Ticket> list, Date creationDate) {
        this.list = list;
        this.creationDate = creationDate;
    }

    public String receive(String command, Ticket ticket, String argument, String login) {
        switch (command) {
            case "add":
                return add(ticket ,login);
            case "update":
                return update(ticket, argument, login);
            case "show":
                return show();
            case "info":
                return info();
            case "clear":
                return clear(login);
            case "add_if_min":
                return add_if_min(ticket,login);
            case "remove_by_id":
                return remove_by_id(argument, login);
            case "remove_greater":
                return remove_greater(ticket, login);
            case "reg":
                return reg(login,argument);
            case "remove_any_by_price":
                return remove_any_by_price(argument, login);
            case "count_by_type":
                return count_by_type(argument);
            case "filter_less_than_comment":
                return filter_less_than_comment(argument);
            case "auth":
                return auth(login,argument);
            case "getCreationDate":
                return getCreationDate();
        }
        return null;
    }

    public synchronized String reg(String login, String argument){
        try (Statement statement = c.createStatement()) {
            ResultSet rs = statement.executeQuery("SELECT * FROM users");
            while (rs.next()) {
                String login1 = rs.getString("login");
                if (login.equals(login1)){
                    return "loginError";
                }
            }
            PreparedStatement statement1 = c.prepareStatement("INSERT INTO users (login,password) VALUES (?,?)");
            statement1.setString(1,login);
            statement1.setString(2,argument);
            statement1.execute();
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return "regSuccessfully";
    }

    public synchronized String auth(String login, String argument){
        try (Statement statement = c.createStatement()) {
            ResultSet rs = statement.executeQuery("SELECT * FROM users");
            while (rs.next()) {
                String login1 = rs.getString("login");
                String password = rs.getString("password");
                if (login.equals(login1)){
                    if (argument.equals(password)){
                        return "logSuccessfully";
                    }
                    return "passwordError";
                }
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return "userNotExist";
    }

    public synchronized String add(Ticket ticket, String login) {
        try(Statement statement1 = c.createStatement()){
            PreparedStatement statement = c.prepareStatement("INSERT INTO base " +
                    "(id,name,x,y,comment,price,refundable,type,id1,name1,description,event_type,login,creationdate) VALUES " +
                    "(?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
                ResultSet set = statement1.executeQuery("SELECT nextval('count')");
                long q = 0;
                if (set.next()) q = set.getLong(1);
                statement.setLong(1,q);
                statement.setString(2,ticket.getName());
                statement.setDouble(3,ticket.getCoordinates().getX());
                statement.setLong(4,ticket.getCoordinates().getY());
                statement.setString(5,ticket.getComment());
                statement.setDouble(6,ticket.getPrice());
                statement.setBoolean(7,ticket.isRefundable());
                statement.setString(8,ticket.getType().toString());
                ResultSet set1 = statement1.executeQuery("SELECT nextval('count')");
                long q1 = 0;
                if (set1.next()) q1 = set1.getLong(1);
                statement.setLong(9,q1);

                statement.setString(10,ticket.getEvent().getName());
                statement.setString(11,ticket.getEvent().getDescription());
                if (ticket.getEvent().getEventType() == null){
                    statement.setString(12,null);
                }else {
                    statement.setString(12, ticket.getEvent().getEventType().toString());
                }
                statement.setString(13,login);
                statement.setLong(14,ticket.getCreationDate());
                statement.execute();

                ticket.setId(q);
                ticket.getEvent().setId(q1);
                ticket.setLogin(login);
                list.add(ticket);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "successfullyAdded";
    }

    public synchronized String update(Ticket ticket, String argument, String login) {


        try {
            Ticket t = list.stream().filter(x -> x.getId().equals(Long.parseLong(argument))).findFirst().get();
            Statement statement1 = c.createStatement();
            ResultSet rs = statement1.executeQuery("SELECT id, login FROM base");
            while (rs.next()) {
                Long id = rs.getLong("id");
                String login1 = rs.getString("login");
                if (t.getId().equals(id)) {
                    if (!login.equals(login1)){
                        return "notYourElement";
                    }
                }
            }
            list.remove(t);
            ticket.setLogin(login);
            list.add(ticket);

            try{
                PreparedStatement statement = c.prepareStatement("UPDATE base SET name = ?,x = ?,y = ?,comment = ?,price = ?," +
                        "type = ?,refundable = ?,name1 = ?,description = ?,event_type = ? WHERE id = ?");
                statement.setString(1,ticket.getName());
                statement.setDouble(2,ticket.getCoordinates().getX());
                statement.setLong(3,ticket.getCoordinates().getY());
                statement.setString(4,ticket.getComment());
                statement.setDouble(5,ticket.getPrice());
                statement.setString(6,ticket.getType().toString());
                statement.setBoolean(7,ticket.isRefundable());
                statement.setString(8,ticket.getEvent().getName());
                statement.setString(9,ticket.getEvent().getDescription());
                if (ticket.getEvent().getEventType() == null){
                    statement.setString(10,null);
                }else {
                    statement.setString(10, ticket.getEvent().getEventType().toString());
                }
                statement.setLong(11,Long.parseLong(argument));
                statement.execute();
            }catch (SQLException e) {
                e.printStackTrace();
            }

            Collections.sort(list);
            return "successfullyUpdate";
        } catch (Exception e) {
            return "elementError";
        }
    }

    public synchronized String show() {
        String s = "";
        for (Ticket ticket : list) {
            s = s + ticket.toString() + "\n";
        }
        return s;
    }

    public synchronized String info() {
        String s = "notYourElement";
//        s += "info1";
//        s += "info2" + creationDate;
//        s += "info3" + list.size();
        return s;
    }

    public synchronized String remove_by_id(String argument,String login) {
        try {
            Ticket t = list.stream().filter(x -> x.getId().equals(Long.parseLong(argument))).findFirst().get();
            Statement statement1 = c.createStatement();
            ResultSet rs = statement1.executeQuery("SELECT id, login FROM base");
            while (rs.next()) {
                Long id = rs.getLong("id");
                String login1 = rs.getString("login");
                if (t.getId().equals(id)) {
                    if (!login.equals(login1)){
                        return "notYourElement";
                    }
                }
            }
            PreparedStatement statement = c.prepareStatement("DELETE FROM base WHERE id = ?");
            statement.setLong(1,Long.parseLong(argument));
            statement.execute();
            list.remove(t);
            return "successfullyRemove";
        } catch (Exception e) {
            return "idElementError";
        }
    }

    public synchronized String clear(String login) {
        try {
            Statement statement = c.createStatement();
            ResultSet rs = statement.executeQuery("SELECT id, login FROM base");
            while (rs.next()) {
                String login1 = rs.getString("login");
                if (login.equals(login1)){
                    Long id = rs.getLong("id");
                    remove_by_id(id.toString(),login);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "collectionClear";
    }

    public synchronized String add_if_min(Ticket ticket, String login) {
        Ticket t = list.stream().min((x1, x2) -> x1.getPrice().compareTo(x2.getPrice())).get();
        if (ticket.getPrice() < t.getPrice()) {
            try (Statement statement1 = c.createStatement()) {
                PreparedStatement statement = c.prepareStatement("INSERT INTO base " +
                        "(id,name,x,y,comment,price,refundable,type,id1,name1,description,event_type,login,creationdate) VALUES " +
                        "(?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
                ResultSet set = statement1.executeQuery("SELECT nextval('count')");
                long q = 0;
                if (set.next()) q = set.getLong(1);
                statement.setLong(1, q);
                statement.setString(2, ticket.getName());
                statement.setDouble(3, ticket.getCoordinates().getX());
                statement.setLong(4, ticket.getCoordinates().getY());
                statement.setString(5, ticket.getComment());
                statement.setDouble(6, ticket.getPrice());
                statement.setBoolean(7, ticket.isRefundable());
                statement.setString(8, ticket.getType().toString());
                ResultSet set1 = statement1.executeQuery("SELECT nextval('count')");
                long q1 = 0;
                if (set1.next()) q1 = set1.getLong(1);
                statement.setLong(9, q1);

                statement.setString(10, ticket.getEvent().getName());
                statement.setString(11, ticket.getEvent().getDescription());
                if (ticket.getEvent().getEventType() == null) {
                    statement.setString(12, null);
                } else {
                    statement.setString(12, ticket.getEvent().getEventType().toString());
                }
                statement.setString(13, login);
                statement.setLong(14, ticket.getCreationDate());
                statement.execute();

                ticket.setId(q);
                ticket.getEvent().setId(q1);
                list.add(ticket);
                return "successfullyAdded";
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return "priceError";
    }

    public synchronized String remove_greater(Ticket ticket, String login) {
        //list = list.stream().filter(x -> x.getPrice() < ticket.getPrice()).collect(Collectors.toList());
        try {
            Statement statement = c.createStatement();
            ResultSet rs = statement.executeQuery("SELECT id,price,login FROM base");
            while (rs.next()) {
                String login1 = rs.getString("login");
                if (login.equals(login1)){
                    Double price = rs.getDouble("price");
                    if (ticket.getPrice() < price) {
                        Long id = rs.getLong("id");
                        remove_by_id(id.toString(),login);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "elementsRemove";
    }

    public synchronized String remove_any_by_price(String argument, String login) {
        try {
            //Ticket t = list.stream().filter(x -> x.getPrice().equals(Double.parseDouble(argument))).findFirst().get();
            Statement statement = c.createStatement();
            ResultSet rs = statement.executeQuery("SELECT id,price,login FROM base");
            while (rs.next()){
                String login1 = rs.getString("login");
                if (login.equals(login1)){
                    Double price = rs.getDouble("price");
                    if (price.equals(Double.parseDouble(argument))){
                        Long id = rs.getLong("id");
                        remove_by_id(id.toString(),login);
                        return "priceRemoveSuccessfully";
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "priceElementError";
    }

    public synchronized String count_by_type(String argument) {
        return String.valueOf(list.stream().filter(x -> x.getType().equals(TicketType.valueOf(argument))).count());
    }

    public synchronized String filter_less_than_comment(String argument) {
        return list.stream().filter(x -> x.getComment().length() < argument.length()).map(Ticket::toString).collect(Collectors.joining("\n"));
    }

    public String getCreationDate(){
        System.out.println(">" + UDPServer.creationDate.toString());
        return ">" + UDPServer.creationDate.toString();
    }
}
