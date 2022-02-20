package model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.util.List;
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FakerResponse {

     String status;
     Integer code;
     Integer total;
     List<Datum> data = null;

}
