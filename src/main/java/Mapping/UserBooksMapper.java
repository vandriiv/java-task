package Mapping;

import DTO.BookCount;
import DTO.UserBooksDTO;
import Entities.UserBook;

import java.util.ArrayList;
import java.util.List;

public class UserBooksMapper {
    public UserBooksDTO MapToUserBooksDTO(List<UserBook> userBooks){
        UserBooksDTO userBooksDTO = new UserBooksDTO();
        List<BookCount> booksCounts = new ArrayList<>(userBooks.size());
        for (UserBook ub: userBooks ) {

           BookCount temp = new BookCount();
           temp.setBook(ub.getBook());
           temp.setCount(ub.getCount());
           booksCounts.add(temp);
        }
        userBooksDTO.setUser(userBooks.get(0).getUser());
        userBooksDTO.setBooks(booksCounts);
        return  userBooksDTO;
    }
}
