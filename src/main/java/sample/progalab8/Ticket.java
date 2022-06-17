package sample.progalab8;

import javafx.beans.property.SimpleStringProperty;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class Ticket implements Comparable<Ticket>, Serializable {
    private Long id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private Date creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private Double price; //Поле не может быть null, Значение поля должно быть больше 0
    private String comment; //Строка не может быть пустой, Поле может быть null
    private boolean refundable;
    private TicketType type; //Поле не может быть null
    private Event event; //Поле не может быть n
    private String login;

    public Ticket(Long id, String name, Coordinates coordinates, Date creationDate, Double price,
                  String comment, boolean refundable, TicketType type, Event event, String login) {
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = creationDate;
        this.price = price;
        this.comment = comment;
        this.refundable = refundable;
        this.type = type;
        this.event = event;
        this.login = login;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ticket ticket = (Ticket) o;
        return refundable == ticket.refundable && Objects.equals(id, ticket.id) && Objects.equals(name, ticket.name) && Objects.equals(coordinates, ticket.coordinates) && Objects.equals(creationDate, ticket.creationDate) && Objects.equals(price, ticket.price) && Objects.equals(comment, ticket.comment) && type == ticket.type && Objects.equals(event, ticket.event);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, coordinates, creationDate, price, comment, refundable, type, event);
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", coordinates=" + coordinates +
                ", creationDate=" + creationDate +
                ", price=" + price +
                ", comment='" + comment + '\'' +
                ", refundable=" + refundable +
                ", type=" + type +
                ", event=" + event +
                '}';
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getPrice() {
        return price;
    }

    public String getComment() {
        if (comment==null){
            return "";
        }
        return comment;
    }

    public TicketType getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public long getCreationDate() {
        return creationDate.getTime();
    }
    public Date getcreationDate(){
        return creationDate;
    }

    public boolean isRefundable() {
        return refundable;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }
    public float getX(){
        return coordinates.getX();
    }
    public long getY(){
        return coordinates.getY();
    }
    public long getId1(){
        return event.getId();
    }
    public String getName1(){
        return event.getName();
    }
    public String getDescription(){
        return event.getDescription();
    }
    public EventType getEventType(){
        return event.getEventType();
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public Event getEvent() {
        return event;
    }

    @Override
    public int compareTo(Ticket o) {
        return (int) (this.getId()-o.getId()/10000);
    }
}
