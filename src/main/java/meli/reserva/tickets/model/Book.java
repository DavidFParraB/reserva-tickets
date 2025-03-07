package meli.reserva.tickets.model;

import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.cloud.firestore.annotation.DocumentId;
import com.google.cloud.spring.data.firestore.Document;

import lombok.Data;

@Data
@Document(collectionName = "books")
public class Book {

    @DocumentId
    private String id;
    private String dni;
    private String name;
    private String show;
    private String location;
    @JsonProperty("seat_number")
    private String seatNumber;
    private Integer price;
    @JsonProperty("book_date")
    private Date bookDate;
}
