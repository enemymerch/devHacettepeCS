CREATE TABLE UserType (
    userTypeID int NOT NULL PRIMARY KEY,
    userType varchar(64) NOT NULL
); 


CREATE TABLE Users(
    userID int NOT NULL PRIMARY KEY,
    userName varchar(256) NOT NULL,
    userPassword varchar(256) NOT NULL,
    userTypeID int,
    CONSTRAINT fk_userType FOREIGN KEY (userTypeID) REFERENCES  USERTYPE(userTypeID)
    
);



CREATE TABLE Admin(
    userID NOT NULL,
    adminName varchar(64),
    CONSTRAINT pk_admin PRIMARY KEY (userID),
    CONSTRAINT fk_admin FOREIGN KEY (userID) REFERENCES Users(userID)
);



CREATE TABLE Address(
    addressID int NOT NULL PRIMARY KEY,
    streetAddress varchar(256),
    cityName varchar(256),
    countryID int NOT NULL,
    postCode varchar(12),
    CONSTRAINT fk_country_address FOREIGN KEY (countryID) REFERENCES Country(countryID)
);


CREATE TABLE MemberType(
    memberTypeID int NOT NULL PRIMARY KEY,
    memberType varchar(64)
);

CREATE TABLE Member(
    userID int NOT NULL,
    memberTypeID int NOT NULL,
    addressID int NOT NULL,
    memberName varchar(64),
    memberPhoneNumber int,
    memberEmail varchar(64),
    CONSTRAINT pk_member PRIMARY KEY (userID),
    CONSTRAINT fk_user_member FOREIGN KEY (userID) REFERENCES Users(userID),
    CONSTRAINT fk_type_member FOREIGN KEY (memberTypeID) REFERENCES MemberType(memberTypeID),
    CONSTRAINT fk_address_member FOREIGN KEY (addressID) REFERENCES Address(addressID)
);

CREATE TABLE Discount(
    discountID int NOT NULL PRIMARY KEY,
    dicountPercentage float NOT NULL
);


CREATE TABLE MEMBERLOG(
    logID int NOT NULL PRIMARY KEY,
    userID int NOT NULL,
    logInformation varchar(256),
    logDate date NOT NULL,
    CONSTRAINT fk_user_log FOREIGN KEY (userID) REFERENCES member(userID)
);

CREATE TABLE GOLDMEMBER(
    userID int NOT NULL PRIMARY KEY,
    discountID int NOT NULL,
    CONSTRAINT fk_dis_gold FOREIGN KEY (userID) REFERENCES member(userID) 
);

CREATE TABLE TICKETSTATUS(
    ticketStatusID int NOT NULL PRIMARY KEY,
    tictesStatus varchar(64)
);

CREATE TABLE LOCATIONTYPE(
    locationTypeID int NOT NULL PRIMARY KEY,
    locationType varchar(64)
);


CREATE TABLE Location(
    locationID int NOT NULL PRIMARY KEY,
    locationTypeID int NOT NULL,
    addressID int NOT NULL,
    locationNamne varchar(256),
    locationInformation varchar(256),
    CONSTRAINT fk_locType_log FOREIGN KEY (locationTypeID) REFERENCES LocationType(locationTypeID),
    CONSTRAINT fk_add_log FOREIGN KEY (addressID) REFERENCES Address(addressID)
);

CREATE TABLE EVENTTYPE(
    eventTypeID int NOT NULL PRIMARY KEY,
    eventType varchar(64)
);


CREATE TABLE EVENT(
    eventID int NOT NULL PRIMARY KEY,
    locationID int NOT NULL,
    eventTypeID int NOT NULL,
    eventName varchar(256),
    eventInformation varchar(512),
    eventDate date,
    CONSTRAINT fk_loc_evnt FOREIGN KEY (locationID) REFERENCES LOCATION(locationID),
    CONSTRAINT fk_type_evnt FOREIGN KEY (eventTypeID) REFERENCES EVENTTYPE(eventTypeID)
);


CREATE TABLE TICKET(
    ticketID int NOT NULL PRIMARY KEY,
    eventID int NOT NULL,
    ticketStatusID int NOT NULL,
    ticketPrice float DEFAULT 100.0,
    ticketSeat varchar(12),
    CONSTRAINT fk_evnt_tic FOREIGN KEY (eventID) REFERENCES EVENT(eventID),
    CONSTRAINT fk_sta_tic FOREIGN KEY (ticketStatusID) REFERENCES TICKETSTATUS(ticketStatusID)
);

CREATE TABLE PURCHASE(
    purchaseID int NOT NULL PRIMARY KEY,
    userID int NOT NULL,
    ticketID int NOT NULL,
    CONSTRAINT fk_user_pur FOREIGN KEY (userID) REFERENCES MEMBER(userID),
    CONSTRAINT fk_tic_pur FOREIGN KEY (ticketID) REFERENCES TICKET(ticketID)
);


CREATE TABLE DISCOUNTUSAGE(
    purchaseID int NOT NULL PRIMARY KEY,
    userID int NOT NULL,
    ticketID int NOT NULL,
    CONSTRAINT fk_pur_dis FOREIGN KEY (purchaseID) REFERENCES PURCHASE(purchaseID),
    CONSTRAINT fk_user_dis FOREIGN KEY (userID) REFERENCES MEMBER(userID),
    CONSTRAINT fk_tic_dis FOREIGN KEY (ticketID) REFERENCES TICKET(ticketID)
);