package meli.reserva.tickets.repository;

import org.springframework.stereotype.Repository;

import com.google.cloud.spring.data.firestore.FirestoreReactiveRepository;

import meli.reserva.tickets.model.Book;

@Repository
public interface BookRepository extends FirestoreReactiveRepository<Book> {

}