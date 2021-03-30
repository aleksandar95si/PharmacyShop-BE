INSERT INTO oauth_client_details (client_id, client_secret, web_server_redirect_uri, scope, access_token_validity, refresh_token_validity, resource_ids, authorized_grant_types, additional_information) VALUES ('frontend', '{bcrypt}$2y$10$2vCCIhe2wXn50lJNqoz3i.TPprS8eVL5HvSN2GhdxlfShkhRqa3S.', 'http://localhost:8282/login', 'READ,WRITE', '36000', '10000', 'angular', 'authorization_code,password,refresh_token,implicit', '{}');
INSERT INTO oauth_client_details (client_id, client_secret, web_server_redirect_uri, scope, access_token_validity, refresh_token_validity, resource_ids, authorized_grant_types, additional_information) VALUES ('product-service', '{bcrypt}$2y$10$ri0sQa1MKRS3Aq9pDiQigueV4tEVNWryu9t3iIrSJgMsOMIQD3o7S', 'http://localhost:8282/login', 'READ,WRITE', '36000', '10000', 'products', 'authorization_code,password,refresh_token,implicit', '{}');
INSERT INTO oauth_client_details (client_id, client_secret, web_server_redirect_uri, scope, access_token_validity, refresh_token_validity, resource_ids, authorized_grant_types, additional_information) VALUES ('shopping-cart-service', '{bcrypt}$2y$10$Lk0WGYyA097Ib.Prd.CwgeGQb1n6zWwgLycTyXlhu8WVMhEWSnq26', 'http://localhost:8282/login', 'READ,WRITE', '36000', '10000', 'carts', 'authorization_code,password,refresh_token,implicit', '{}');
INSERT INTO oauth_client_details (client_id, client_secret, web_server_redirect_uri, scope, access_token_validity, refresh_token_validity, resource_ids, authorized_grant_types, additional_information) VALUES ('order-service', '{bcrypt}$2y$10$pDI6nKcusxxgI5J6DrbDjuyUQ5WFuJIkH67Y.378DV9gjSfqrTfji', 'http://localhost:8282/login', 'READ,WRITE', '36000', '10000', 'orders', 'authorization_code,password,refresh_token,implicit', '{}');

 INSERT INTO PERMISSION (NAME) VALUES
 ('create'),
 ('read'),
 ('update'),
 ('delete');

 INSERT INTO role (NAME) VALUES
		('ROLE_admin'),('ROLE_user');

 INSERT INTO PERMISSION_ROLE (PERMISSION_ID, ROLE_ID) VALUES
     (1,1), /*create admin */
     (2,1), /* read admin */
     (3,1), /* update admin */
     (4,1), /* delete admin */
     (2,2),  /* read user */
     (3,2);  /* update user */
insert into user (id, username,password, email, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked) VALUES ('1', 'aleksandar','{bcrypt}$2y$10$qipcGXoTV9WRqFNYqKV8zeawzsj3vwTS4lZl2sI/s2aoc2nL2U5kW', 'a@fon.com', '1', '1', '1', '1');
insert into  user (id, username,password, email, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked) VALUES ('2', 'stefan', '{bcrypt}$2y$10$S48WAEBE3WgU7LJIiNbghe9Rb66OhExe9iZhErc2QOuoPkMPtuPT2','s@fon.com', '1', '1', '1', '1');
insert into user (id, username,password, email, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked) VALUES ('3', 'ana','{bcrypt}$2a$10$/LAm5njgHNw.5Dq/WCBcsuOCiQNC67G6XJgi1/ev22ZKDrUKUEkLq', 'n@fon.com', '1', '1', '1', '1');


INSERT INTO ROLE_USER (ROLE_ID, USER_ID)
    VALUES
    (2, 1), /* aleksandar-user */
    (2, 2), /* stefan-user */ 
    (2, 3)	/* ana-user */ ;
    