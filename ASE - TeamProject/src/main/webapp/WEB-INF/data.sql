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
INSERT INTO Event values(default, 1, 'Patrick Test Event', 'Test event for system develpoment' );
INSERT INTO Event values(default, 2, 'Najjar Test Event', 'Test event for system develpoment' );
INSERT INTO Event values(default, 3, 'Phil Test Event', 'Test event for system develpoment' );
INSERT INTO Event values(default, 4, 'Reva Test Event', 'Test event for system develpoment' );

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


INSERT INTO VoteOption values(default, 1, null, 'Patrick Category 1 Option 1' );
INSERT INTO VoteOption values(default, 1, null, 'Patrick Category 1 Option 2' );
INSERT INTO VoteOption values(default, 1, null, 'Patrick Category 1 Option 3' );

INSERT INTO VoteOption values(default, 2, null, 'Patrick Category 2 Option 1' );
INSERT INTO VoteOption values(default, 2, null, 'Patrick Category 2 Option 2' );
INSERT INTO VoteOption values(default, 2, null, 'Patrick Category 2 Option 3' );

INSERT INTO VoteOption values(default, 3, null, 'Patrick Category 3 Option 1' );
INSERT INTO VoteOption values(default, 3, null, 'Patrick Category 3 Option 2' );
INSERT INTO VoteOption values(default, 3, null, 'Patrick Category 3 Option 3' );



INSERT INTO VoteOption values(default, 4, null, 'Najjar Category 1 Option 1' );
INSERT INTO VoteOption values(default, 4, null, 'Najjar Category 1 Option 2' );
INSERT INTO VoteOption values(default, 4, null, 'Najjar Category 1 Option 3' );

INSERT INTO VoteOption values(default, 5, null, 'Najjar Category 2 Option 1' );
INSERT INTO VoteOption values(default, 5, null, 'Najjar Category 2 Option 2' );
INSERT INTO VoteOption values(default, 5, null, 'Najjar Category 2 Option 3' );

INSERT INTO VoteOption values(default, 6, null, 'Najjar Category 3 Option 1' );
INSERT INTO VoteOption values(default, 6, null, 'Najjar Category 3 Option 2' );
INSERT INTO VoteOption values(default, 6, null, 'Najjar Category 3 Option 3' );



INSERT INTO VoteOption values(default, 7, null, 'Phil Category 1 Option 1' );
INSERT INTO VoteOption values(default, 7, null, 'Phil Category 1 Option 2' );
INSERT INTO VoteOption values(default, 7, null, 'Phil Category 1 Option 3' );

INSERT INTO VoteOption values(default, 8, null, 'Phil Category 2 Option 1' );
INSERT INTO VoteOption values(default, 8, null, 'Phil Category 2 Option 2' );
INSERT INTO VoteOption values(default, 8, null, 'Phil Category 2 Option 3' );

INSERT INTO VoteOption values(default, 9, null, 'Phil Category 3 Option 1' );
INSERT INTO VoteOption values(default, 9, null, 'Phil Category 3 Option 2' );
INSERT INTO VoteOption values(default, 9, null, 'Phil Category 3 Option 3' );


-- VoteSubCategory 
INSERT INTO VoteCategory values(default, 1, 'Patrick Test Category 4 w/ Sub', 'Vote Category description' );

INSERT INTO VoteSubCategory values(default, 10, 'Patrick Test Sub Category 1' );
INSERT INTO VoteOption values(default, null, 1, '1' );
INSERT INTO VoteOption values(default, null, 1, '2' );
INSERT INTO VoteOption values(default, null, 1, '3' );

INSERT INTO VoteSubCategory values(default, 10, 'Patrick Test Sub Category 2' );
INSERT INTO VoteOption values(default, null, 2, '1' );
INSERT INTO VoteOption values(default, null, 2, '2' );
INSERT INTO VoteOption values(default, null, 2, '3' );

INSERT INTO VoteSubCategory values(default, 10, 'Patrick Test Sub Category 3' );
INSERT INTO VoteOption values(default, null, 3, '1' );
INSERT INTO VoteOption values(default, null, 3, '2' );
INSERT INTO VoteOption values(default, null, 3, '3' );

INSERT INTO VoteCategory values(default, 1, 'Najjar Test Category 4 w/ Sub', 'Vote Category description' );

INSERT INTO VoteSubCategory values(default, 11, 'Najjar Test Sub Category 1' );
INSERT INTO VoteOption values(default, null, 4, 'a' );
INSERT INTO VoteOption values(default, null, 4, 'b' );
INSERT INTO VoteOption values(default, null, 4, 'c' );

INSERT INTO VoteSubCategory values(default, 11, 'Najjar Test Sub Category 2' );
INSERT INTO VoteOption values(default, null, 5, 'a' );
INSERT INTO VoteOption values(default, null, 5, 'b' );
INSERT INTO VoteOption values(default, null, 5, 'c' );

INSERT INTO VoteSubCategory values(default, 11, 'Najjar Test Sub Category 3' );
INSERT INTO VoteOption values(default, null, 6, 'a' );
INSERT INTO VoteOption values(default, null, 6, 'b' );
INSERT INTO VoteOption values(default, null, 6, 'c' );


INSERT INTO VoteCategory values(default, 1, 'Phil Test Category 4 w/ Sub', 'Vote Category description' );

INSERT INTO VoteSubCategory values(default, 12, 'Phil Test Sub Category 1' );
INSERT INTO VoteOption values(default, null, 7, '1' );
INSERT INTO VoteOption values(default, null, 7, '2' );
INSERT INTO VoteOption values(default, null, 7, '3' );

INSERT INTO VoteSubCategory values(default, 12, 'Phil Test Sub Category 2' );
INSERT INTO VoteOption values(default, null, 8, '1' );
INSERT INTO VoteOption values(default, null, 8, '2' );
INSERT INTO VoteOption values(default, null, 8, '3' );

INSERT INTO VoteSubCategory values(default, 12, 'Phil Test Sub Category 3' );
INSERT INTO VoteOption values(default, null, 9, '1' );
INSERT INTO VoteOption values(default, null, 9, '2' );
INSERT INTO VoteOption values(default, null, 9, '3' );



INSERT INTO VoteCategory values(default, 1, 'Reva Test Category 4 w/ Sub', 'Vote Category description' );

INSERT INTO VoteSubCategory values(default, 13, 'Reva Test Sub Category 1' );
INSERT INTO VoteOption values(default, null, 10, 'a' );
INSERT INTO VoteOption values(default, null, 10, 'b' );
INSERT INTO VoteOption values(default, null, 10, 'c' );

INSERT INTO VoteSubCategory values(default, 13, 'Reva Test Sub Category 2' );
INSERT INTO VoteOption values(default, null, 11, 'a' );
INSERT INTO VoteOption values(default, null, 11, 'b' );
INSERT INTO VoteOption values(default, null, 11, 'c' );

INSERT INTO VoteSubCategory values(default, 13, 'Reva Test Sub Category 3' );
INSERT INTO VoteOption values(default, null, 12, 'a' );
INSERT INTO VoteOption values(default, null, 12, 'b' );
INSERT INTO VoteOption values(default, null, 12, 'c' );


