package sample.progalab8;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.List;


/**
 * Класс парсер
 */
public class Parser {
    List<Ticket> list;
    Connection c;

    public Parser(List<Ticket> list, Connection c) {
        this.list = list;
        this.c = c;
    }
    public void parse() {
        try (Statement statement = c.createStatement()) {
            ResultSet rs = statement.executeQuery("SELECT * FROM base");
            while (rs.next()) {
                Long id = rs.getLong("id");
                String name = rs.getString("name");
                Float x = rs.getFloat("x");
                Long y = rs.getLong("y");
                String comment = rs.getString("comment");
                Double price = rs.getDouble("price");
                TicketType type = TicketType.valueOf(rs.getString("type"));
                Boolean refundable = rs.getBoolean("refundable");
                Long id1 = rs.getLong("id1");
                String name1 = rs.getString("name1");
                String description = rs.getString("description");
                String eventType = rs.getString("event_type");
                Date creationDate = new Date(rs.getLong("creationdate"));
                String login = rs.getString("login");
                Coordinates coordinates = new Coordinates(x, y);
                Event e;
                if (eventType == null) e = new Event(id1, name1, description, null);
                else e = new Event(id1, name1, description, EventType.valueOf(eventType));
                if (comment.isEmpty()) comment = null;
                Ticket ticket = new Ticket(id, name, coordinates, creationDate, price, comment, refundable, type, e, login);
                list.add(ticket);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

