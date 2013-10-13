INSERT INTO UserAccount values (default, 'po22207@columbia.edu', 'password', 'Patrick', 'LOCAL' );
INSERT INTO UserAccount values (default, 'an2434@columbia.edu', 'password', 'Najjar', 'LOCAL');
INSERT INTO UserAccount values (default, 'pa2354+w4156@columbia.edu', 'password', 'Phil', 'LOCAL');
INSERT INTO UserAccount values (default, 'ra2659@columbia.edu', 'password', 'Reva', 'LOCAL');
INSERT INTO UserAccount values (default, 'reily@cs.columbia.edu', 'password', 'Riley', 'LOCAL');

-- Patrick
INSERT INTO Permission values(1, 'ROLE_ADMIN' );
INSERT INTO Permission values(1, 'ROLE_USER' );
-- Najjar
INSERT INTO Permission values(2, 'ROLE_ADMIN' );
INSERT INTO Permission values(2, 'ROLE_USER' );
-- Phil
INSERT INTO Permission values(3, 'ROLE_ADMIN' );
INSERT INTO Permission values(3, 'ROLE_USER' );
-- Reva
INSERT INTO Permission values(4, 'ROLE_ADMIN' );
INSERT INTO Permission values(4, 'ROLE_USER' );
-- Riley
INSERT INTO Permission values(5, 'ROLE_ADMIN' );
INSERT INTO Permission values(5, 'ROLE_USER' );

-- Create test events
INSERT INTO Event values(default, 'Patrick Test Event', current_timestamp, current_timestamp + '8' HOUR, 'Test event for system develpoment' );
INSERT INTO Event values(default, 'Najjar Test Event', current_timestamp, current_timestamp + '12' HOUR, 'Test event for system develpoment' );
INSERT INTO Event values(default, 'Phil Test Event', current_timestamp, current_timestamp + '16' HOUR, 'Test event for system develpoment' );
INSERT INTO Event values(default, 'Reva Test Event', current_timestamp, current_timestamp + '24' HOUR, 'Test event for system develpoment' );

-- add every one as voters for testing

-- add voters to Patrick event
INSERT INTO User_Event values(2, 1 );
INSERT INTO User_Event values(3, 1 );
INSERT INTO User_Event values(4, 1 );

-- add voters to Najjar event
INSERT INTO User_Event values(1, 2 );
INSERT INTO User_Event values(3, 2 );
INSERT INTO User_Event values(4, 2 );

-- add voters to Phil event
INSERT INTO User_Event values(1, 3 );
INSERT INTO User_Event values(2, 3 );
INSERT INTO User_Event values(4, 3 );

-- add voters to Reva event
INSERT INTO User_Event values(1, 4 );
INSERT INTO User_Event values(2, 4 );
INSERT INTO User_Event values(3, 4 );


INSERT INTO VoteCategory values(default, 1, 'Patrick Test Category 1', 'Vote Category description' );
INSERT INTO VoteCategory values(default, 1, 'Patrick Test Category 2', 'Vote Category description' );
INSERT INTO VoteCategory values(default, 1, 'Patrick Test Category 3', 'Vote Category description' );

INSERT INTO VoteCategory values(default, 2, 'Najjar Test Category 1', 'Vote Category description' );
INSERT INTO VoteCategory values(default, 2, 'Najjar Test Category 2', 'Vote Category description' );
INSERT INTO VoteCategory values(default, 2, 'Najjar Test Category 3', 'Vote Category description' );

INSERT INTO VoteCategory values(default, 2, 'Phil Test Category 1', 'Vote Category description' );
INSERT INTO VoteCategory values(default, 2, 'Phil Test Category 2', 'Vote Category description' );
INSERT INTO VoteCategory values(default, 2, 'Phil Test Category 3', 'Vote Category description' );

INSERT INTO VoteCategory values(default, 2, 'Reva Test Category 1', 'Vote Category description' );
INSERT INTO VoteCategory values(default, 2, 'Reva Test Category 2', 'Vote Category description' );
INSERT INTO VoteCategory values(default, 2, 'Reva Test Category 3', 'Vote Category description' );


INSERT INTO VoteOption values(default, 1, 'Patrick Category 1 Option 1' );
INSERT INTO VoteOption values(default, 1, 'Patrick Category 1 Option 2' );
INSERT INTO VoteOption values(default, 1, 'Patrick Category 1 Option 3' );

INSERT INTO VoteOption values(default, 2, 'Patrick Category 2 Option 1' );
INSERT INTO VoteOption values(default, 2, 'Patrick Category 2 Option 2' );
INSERT INTO VoteOption values(default, 2, 'Patrick Category 2 Option 3' );

INSERT INTO VoteOption values(default, 3, 'Patrick Category 3 Option 1' );
INSERT INTO VoteOption values(default, 3, 'Patrick Category 3 Option 2' );
INSERT INTO VoteOption values(default, 3, 'Patrick Category 3 Option 3' );



INSERT INTO VoteOption values(default, 4, 'Najjar Category 1 Option 1' );
INSERT INTO VoteOption values(default, 4, 'Najjar Category 1 Option 2' );
INSERT INTO VoteOption values(default, 4, 'Najjar Category 1 Option 3' );

INSERT INTO VoteOption values(default, 5, 'Najjar Category 2 Option 1' );
INSERT INTO VoteOption values(default, 5, 'Najjar Category 2 Option 2' );
INSERT INTO VoteOption values(default, 5, 'Najjar Category 2 Option 3' );

INSERT INTO VoteOption values(default, 6, 'Najjar Category 3 Option 1' );
INSERT INTO VoteOption values(default, 6, 'Najjar Category 3 Option 2' );
INSERT INTO VoteOption values(default, 6, 'Najjar Category 3 Option 3' );



INSERT INTO VoteOption values(default, 7, 'Phil Category 1 Option 1' );
INSERT INTO VoteOption values(default, 7, 'Phil Category 1 Option 2' );
INSERT INTO VoteOption values(default, 7, 'Phil Category 1 Option 3' );

INSERT INTO VoteOption values(default, 8, 'Phil Category 2 Option 1' );
INSERT INTO VoteOption values(default, 8, 'Phil Category 2 Option 2' );
INSERT INTO VoteOption values(default, 8, 'Phil Category 2 Option 3' );

INSERT INTO VoteOption values(default, 9, 'Phil Category 3 Option 1' );
INSERT INTO VoteOption values(default, 9, 'Phil Category 3 Option 2' );
INSERT INTO VoteOption values(default, 9,  'Phil Category 3 Option 3' );




