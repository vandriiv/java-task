package Mapping;

import Entities.UserBook;
import ViewModels.OrderedBookViewModel;

import java.util.ArrayList;
import java.util.List;

public class OrderedBookMapper {

    public List<UserBook> MapToUserBookList(List<OrderedBookViewModel> orderedBooks, long userId){
        List<UserBook> userBookList = new ArrayList<>();


        for(OrderedBookViewModel orderItem : orderedBooks){
           UserBook ub = new UserBook();
           ub.getUser().setId(userId);
           ub.getBook().setId(orderItem.getBookId());
           ub.setCount(orderItem.getCount());
           userBookList.add(ub);
        }
        return  userBookList;
    }
}
