package model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
public class Datum {
    String name;
    Lmd lmd;
    String phoneNumber;
    String description;
}
