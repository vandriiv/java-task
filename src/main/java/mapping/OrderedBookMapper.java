package mapping;

import entities.UserBook;
import dto.OrderedBookDTO;

import java.util.ArrayList;
import java.util.List;

public class OrderedBookMapper {

    public List<UserBook> MapToUserBookList(List<OrderedBookDTO> orderedBooks, long userId){
        List<UserBook> userBookList = new ArrayList<>();


        for(OrderedBookDTO orderItem : orderedBooks){
           UserBook ub = new UserBook();
           ub.setUserId(userId);
           ub.setBookId(orderItem.getBookId());
           ub.setCount(orderItem.getCount());
           userBookList.add(ub);
        }
        return  userBookList;
    }
}
