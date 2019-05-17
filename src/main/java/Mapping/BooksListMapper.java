package Mapping;

import DTO.BooksListDTO;
import Entities.Book;

import java.util.List;

public class BooksListMapper {
    public BooksListDTO MapToBooksListDTO(List<Book> books, int limit){
        BooksListDTO booksListDTO = new BooksListDTO();

        if(books.size()<=limit) {
            booksListDTO.setHasMore(false);
            booksListDTO.setBooks(books);
        }
        else{
            booksListDTO.setHasMore(true);
            booksListDTO.setBooks(books.subList(0,books.size()-1));
        }
        return  booksListDTO;
    }
}
